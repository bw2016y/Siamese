package org.apache.spark.daslab.sql.engine.optimizer


import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.expressions.aggregate.{AggregateExpression, AggregateFunction, Complete}
import org.apache.spark.daslab.sql.engine.plans.logical.{Aggregate, Expand, LogicalPlan}
import org.apache.spark.daslab.sql.engine.rules.Rule
import org.apache.spark.daslab.sql.types.IntegerType

/**
 * This rule rewrites an aggregate query with distinct aggregations into an expanded double
 * aggregation in which the regular aggregation expressions and every distinct clause is aggregated
 * in a separate group. The results are then combined in a second aggregate.
 *
 * For example (in scala):
 * {{{
 *   val data = Seq(
 *     ("a", "ca1", "cb1", 10),
 *     ("a", "ca1", "cb2", 5),
 *     ("b", "ca1", "cb1", 13))
 *     .toDF("key", "cat1", "cat2", "value")
 *   data.createOrReplaceTempView("data")
 *
 *   val agg = data.groupBy($"key")
 *     .agg(
 *       countDistinct($"cat1").as("cat1_cnt"),
 *       countDistinct($"cat2").as("cat2_cnt"),
 *       sum($"value").as("total"))
 * }}}
 *
 * This translates to the following (pseudo) logical plan:
 * {{{
 * Aggregate(
 *    key = ['key]
 *    functions = [COUNT(DISTINCT 'cat1),
 *                 COUNT(DISTINCT 'cat2),
 *                 sum('value)]
 *    output = ['key, 'cat1_cnt, 'cat2_cnt, 'total])
 *   LocalTableScan [...]
 * }}}
 *
 * This rule rewrites this logical plan to the following (pseudo) logical plan:
 * {{{
 * Aggregate(
 *    key = ['key]
 *    functions = [count(if (('gid = 1)) 'cat1 else null),
 *                 count(if (('gid = 2)) 'cat2 else null),
 *                 first(if (('gid = 0)) 'total else null) ignore nulls]
 *    output = ['key, 'cat1_cnt, 'cat2_cnt, 'total])
 *   Aggregate(
 *      key = ['key, 'cat1, 'cat2, 'gid]
 *      functions = [sum('value)]
 *      output = ['key, 'cat1, 'cat2, 'gid, 'total])
 *     Expand(
 *        projections = [('key, null, null, 0, cast('value as bigint)),
 *                       ('key, 'cat1, null, 1, null),
 *                       ('key, null, 'cat2, 2, null)]
 *        output = ['key, 'cat1, 'cat2, 'gid, 'value])
 *       LocalTableScan [...]
 * }}}
 *
 * The rule does the following things here:
 * 1. Expand the data. There are three aggregation groups in this query:
 *    i. the non-distinct group;
 *    ii. the distinct 'cat1 group;
 *    iii. the distinct 'cat2 group.
 *    An expand operator is inserted to expand the child data for each group. The expand will null
 *    out all unused columns for the given group; this must be done in order to ensure correctness
 *    later on. Groups can by identified by a group id (gid) column added by the expand operator.
 * 2. De-duplicate the distinct paths and aggregate the non-aggregate path. The group by clause of
 *    this aggregate consists of the original group by clause, all the requested distinct columns
 *    and the group id. Both de-duplication of distinct column and the aggregation of the
 *    non-distinct group take advantage of the fact that we group by the group id (gid) and that we
 *    have nulled out all non-relevant columns the given group.
 * 3. Aggregating the distinct groups and combining this with the results of the non-distinct
 *    aggregation. In this step we use the group id to filter the inputs for the aggregate
 *    functions. The result of the non-distinct group are 'aggregated' by using the first operator,
 *    it might be more elegant to use the native UDAF merge mechanism for this in the future.
 *
 * This rule duplicates the input data by two or more times (# distinct groups + an optional
 * non-distinct group). This will put quite a bit of memory pressure of the used aggregate and
 * exchange operators. Keeping the number of distinct groups as low as possible should be priority,
 * we could improve this in the current rule by applying more advanced expression canonicalization
 * techniques.
 */
object RewriteDistinctAggregates extends Rule[LogicalPlan] {

  def apply(plan: LogicalPlan): LogicalPlan = plan transformUp {
    case a: Aggregate => rewrite(a)
  }

  def rewrite(a: Aggregate): Aggregate = {

    // Collect all aggregate expressions.
    val aggExpressions = a.aggregateExpressions.flatMap { e =>
      e.collect {
        case ae: AggregateExpression => ae
      }
    }

    // Extract distinct aggregate expressions.
    val distinctAggGroups = aggExpressions.filter(_.isDistinct).groupBy { e =>
      val unfoldableChildren = e.aggregateFunction.children.filter(!_.foldable).toSet
      if (unfoldableChildren.nonEmpty) {
        // Only expand the unfoldable children
        unfoldableChildren
      } else {
        // If aggregateFunction's children are all foldable
        // we must expand at least one of the children (here we take the first child),
        // or If we don't, we will get the wrong result, for example:
        // count(distinct 1) will be explained to count(1) after the rewrite function.
        // Generally, the distinct aggregateFunction should not run
        // foldable TypeCheck for the first child.
        e.aggregateFunction.children.take(1).toSet
      }
    }

    // Aggregation strategy can handle queries with a single distinct group.
    if (distinctAggGroups.size > 1) {
      // Create the attributes for the grouping id and the group by clause.
      val gid = AttributeReference("gid", IntegerType, nullable = false)()
      val groupByMap = a.groupingExpressions.collect {
        case ne: NamedExpression => ne -> ne.toAttribute
        case e => e -> AttributeReference(e.sql, e.dataType, e.nullable)()
      }
      val groupByAttrs = groupByMap.map(_._2)

      // Functions used to modify aggregate functions and their inputs.
      def evalWithinGroup(id: Literal, e: Expression) = If(EqualTo(gid, id), e, nullify(e))
      def patchAggregateFunctionChildren(
                                          af: AggregateFunction)(
                                          attrs: Expression => Option[Expression]): AggregateFunction = {
        val newChildren = af.children.map(c => attrs(c).getOrElse(c))
        af.withNewChildren(newChildren).asInstanceOf[AggregateFunction]
      }

      // Setup unique distinct aggregate children.
      val distinctAggChildren = distinctAggGroups.keySet.flatten.toSeq.distinct
      val distinctAggChildAttrMap = distinctAggChildren.map(expressionAttributePair)
      val distinctAggChildAttrs = distinctAggChildAttrMap.map(_._2)

      // Setup expand & aggregate operators for distinct aggregate expressions.
      val distinctAggChildAttrLookup = distinctAggChildAttrMap.toMap
      val distinctAggOperatorMap = distinctAggGroups.toSeq.zipWithIndex.map {
        case ((group, expressions), i) =>
          val id = Literal(i + 1)

          // Expand projection
          val projection = distinctAggChildren.map {
            case e if group.contains(e) => e
            case e => nullify(e)
          } :+ id

          // Final aggregate
          val operators = expressions.map { e =>
            val af = e.aggregateFunction
            val naf = patchAggregateFunctionChildren(af) { x =>
              distinctAggChildAttrLookup.get(x).map(evalWithinGroup(id, _))
            }
            (e, e.copy(aggregateFunction = naf, isDistinct = false))
          }

          (projection, operators)
      }

      // Setup expand for the 'regular' aggregate expressions.
      // only expand unfoldable children
      val regularAggExprs = aggExpressions
        .filter(e => !e.isDistinct && e.children.exists(!_.foldable))
      val regularAggChildren = regularAggExprs
        .flatMap(_.aggregateFunction.children.filter(!_.foldable))
        .distinct
      val regularAggChildAttrMap = regularAggChildren.map(expressionAttributePair)

      // Setup aggregates for 'regular' aggregate expressions.
      val regularGroupId = Literal(0)
      val regularAggChildAttrLookup = regularAggChildAttrMap.toMap
      val regularAggOperatorMap = regularAggExprs.map { e =>
        // Perform the actual aggregation in the initial aggregate.
        val af = patchAggregateFunctionChildren(e.aggregateFunction)(regularAggChildAttrLookup.get)
        val operator = Alias(e.copy(aggregateFunction = af), e.sql)()

        // Select the result of the first aggregate in the last aggregate.
        val result = AggregateExpression(
          aggregate.First(evalWithinGroup(regularGroupId, operator.toAttribute), Literal(true)),
          mode = Complete,
          isDistinct = false)

        // Some aggregate functions (COUNT) have the special property that they can return a
        // non-null result without any input. We need to make sure we return a result in this case.
        val resultWithDefault = af.defaultResult match {
          case Some(lit) => Coalesce(Seq(result, lit))
          case None => result
        }

        // Return a Tuple3 containing:
        // i. The original aggregate expression (used for look ups).
        // ii. The actual aggregation operator (used in the first aggregate).
        // iii. The operator that selects and returns the result (used in the second aggregate).
        (e, operator, resultWithDefault)
      }

      // Construct the regular aggregate input projection only if we need one.
      val regularAggProjection = if (regularAggExprs.nonEmpty) {
        Seq(a.groupingExpressions ++
          distinctAggChildren.map(nullify) ++
          Seq(regularGroupId) ++
          regularAggChildren)
      } else {
        Seq.empty[Seq[Expression]]
      }

      // Construct the distinct aggregate input projections.
      val regularAggNulls = regularAggChildren.map(nullify)
      val distinctAggProjections = distinctAggOperatorMap.map {
        case (projection, _) =>
          a.groupingExpressions ++
            projection ++
            regularAggNulls
      }

      // Construct the expand operator.
      val expand = Expand(
        regularAggProjection ++ distinctAggProjections,
        groupByAttrs ++ distinctAggChildAttrs ++ Seq(gid) ++ regularAggChildAttrMap.map(_._2),
        a.child)

      // Construct the first aggregate operator. This de-duplicates all the children of
      // distinct operators, and applies the regular aggregate operators.
      val firstAggregateGroupBy = groupByAttrs ++ distinctAggChildAttrs :+ gid
      val firstAggregate = Aggregate(
        firstAggregateGroupBy,
        firstAggregateGroupBy ++ regularAggOperatorMap.map(_._2),
        expand)

      // Construct the second aggregate
      val transformations: Map[Expression, Expression] =
        (distinctAggOperatorMap.flatMap(_._2) ++
          regularAggOperatorMap.map(e => (e._1, e._3))).toMap

      val patchedAggExpressions = a.aggregateExpressions.map { e =>
        e.transformDown {
          case e: Expression =>
            // The same GROUP BY clauses can have different forms (different names for instance) in
            // the groupBy and aggregate expressions of an aggregate. This makes a map lookup
            // tricky. So we do a linear search for a semantically equal group by expression.
            groupByMap
              .find(ge => e.semanticEquals(ge._1))
              .map(_._2)
              .getOrElse(transformations.getOrElse(e, e))
        }.asInstanceOf[NamedExpression]
      }
      Aggregate(groupByAttrs, patchedAggExpressions, firstAggregate)
    } else {
      a
    }
  }

  private def nullify(e: Expression) = Literal.create(null, e.dataType)

  private def expressionAttributePair(e: Expression) =
  // We are creating a new reference here instead of reusing the attribute in case of a
  // NamedExpression. This is done to prevent collisions between distinct and regular aggregate
  // children, in this case attribute reuse causes the input of the regular aggregate to bound to
  // the (nulled out) input of the distinct aggregate.
    e -> AttributeReference(e.sql, e.dataType, nullable = true)()
}
