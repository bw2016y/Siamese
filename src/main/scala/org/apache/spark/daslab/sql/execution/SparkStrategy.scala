package org.apache.spark.daslab.sql.execution


import org.apache.spark.daslab.sql.{AnalysisException, Strategy, execution}
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.encoders.RowEncoder
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.expressions.aggregate.AggregateExpression
import org.apache.spark.daslab.sql.engine.planning._
import org.apache.spark.daslab.sql.engine.plans._
import org.apache.spark.daslab.sql.engine.plans.logical._
import org.apache.spark.daslab.sql.engine.plans.physical._
import org.apache.spark.daslab.sql.engine.streaming.InternalOutputModes
import org.apache.spark.daslab.sql.execution.columnar.{InMemoryRelation, InMemoryTableScanExec}
import org.apache.spark.daslab.sql.execution.command._
import org.apache.spark.daslab.sql.execution.exchange.ShuffleExchangeExec
import org.apache.spark.daslab.sql.execution.joins.{BuildLeft, BuildRight, BuildSide}
import org.apache.spark.daslab.sql.execution.python._
import org.apache.spark.daslab.sql.execution.streaming._
import org.apache.spark.daslab.sql.execution.streaming.sources.MemoryPlanV2
import org.apache.spark.daslab.sql.internal.SQLConf
import org.apache.spark.daslab.sql.streaming.{OutputMode, StreamingQuery}
import org.apache.spark.daslab.sql.types.{LongType, StructType}
import org.apache.spark.daslab.sql.execution.aggregate.AggUtils
import org.apache.spark.daslab.sql.execution.util.DistinctColumn
//todo
import org.apache.spark.rdd.RDD



/**  TODO　将Logical plan转换成SparkPlan
  * 　　该API只用于测试Query Planner，后续可能会修改
  *     Stable API in[[org.apache.spark.daslab.sql.sources]]
  */
abstract class SparkStrategy extends GenericStrategy[SparkPlan] {

  override protected def planLater(plan: LogicalPlan): SparkPlan = PlanLater(plan)
}

/**
  *  这个用于标识需要替换的PlaceHolder，不可实际执行
  * @param plan
  */
case class PlanLater(plan: LogicalPlan) extends LeafExecNode {

  override def output: Seq[Attribute] = plan.output

  protected override def doExecute(): RDD[InternalRow] = {
    throw new UnsupportedOperationException()
  }
}
//todo 里面有很多对流数据的处理
abstract class SparkStrategies extends QueryPlanner[SparkPlan] {
  self: SparkPlanner =>

  /**
    *  对于limit操作符进行特别的处理
   */
  object SpecialLimits extends Strategy {
    override def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case ReturnAnswer(rootPlan) => rootPlan match {
        case Limit(IntegerLiteral(limit), Sort(order, true, child))
          if limit < conf.topKSortFallbackThreshold =>
          TakeOrderedAndProjectExec(limit, order, child.output, planLater(child)) :: Nil
        case Limit(IntegerLiteral(limit), Project(projectList, Sort(order, true, child)))
          if limit < conf.topKSortFallbackThreshold =>
          TakeOrderedAndProjectExec(limit, order, projectList, planLater(child)) :: Nil
        case Limit(IntegerLiteral(limit), child) =>
          CollectLimitExec(limit, planLater(child)) :: Nil
        case other => planLater(other) :: Nil
      }
      case Limit(IntegerLiteral(limit), Sort(order, true, child))
        if limit < conf.topKSortFallbackThreshold =>
        TakeOrderedAndProjectExec(limit, order, child.output, planLater(child)) :: Nil
      case Limit(IntegerLiteral(limit), Project(projectList, Sort(order, true, child)))
        if limit < conf.topKSortFallbackThreshold =>
        TakeOrderedAndProjectExec(limit, order, projectList, planLater(child)) :: Nil
      case _ => Nil
    }
  }

  /**
   *        根据join key和logical plan的大小来为join选择合适的物理计划
    *
    *
   * At first, uses the [[ExtractEquiJoinKeys]] pattern to find joins where at least some of the
   * predicates can be evaluated by matching join keys. If found, join implementations are chosen
   * with the following precedence:
   *
   * - Broadcast hash join (BHJ):
    *    BHJ不支持full outer join.对于right outer join，我们只能广播左侧的表。
    *    对于Left outer,left semi,left anti和internal join type ExistenceJoin我们都只能广播右侧的表
    *    对于Inner like join可以广播两侧的表
   *     通常来说，当需要广播的表比较小的时候BHJ可以表现的比其他join算发更快，然而广播一张表是网络密集（network-intensive）操作。
    *    有可能导致OOM或者表现的比其他的join算发更加糟糕，尤其是当build/broadcast side比较大的时候
   *
    *     对于一些案例来说，用于可以指定broadcast hint(用户对DataFrame应用org.apache.spark.daslab.sql.functions.broadcast()函数)
    *     和指定SQLConf.AUTO_BROADCASTJOIN_ThRESHOLD 的阈值来调整是否应用BHJ和广播哪一侧
    *
   *      1. 可以使用hint指示广播哪一侧，即便这一侧的大小大于[[SQLConf.AUTO_BROADCASTJOIN_THRESHOLD]].
    *      如果两侧都有这样的hint（只有是inner like join类型），预估物理大小更小的一侧会被广播。
    *     2. 优先遵循[[SQLConf.AUTO_BROADCASTJOIN_THRESHOLD]]这个阈值，永远广播小于这一阈值的一侧。
    *     如果两侧都低于这个阈值，就广播更小的一侧，如果两侧都没有更小，就不使用BHJ
   *
   *
   * - Shuffle hash join:
    *   如果单个分区的平均大小都小到可以构造一个内存中的哈希表（Hash table）则应用
   *
   * - Sort merge:
    *  如果需要匹配的join keys是可排序的
   *
    * 如果没有join key,就使用如下的优先级来选择join的实现
   * - BroadcastNestedLoopJoin (BNLJ):
    *   BNLJ支持所有join类型，但是其实现对于如下的场景做了优化：
    *   对于right outer join,广播左侧
    *   left outer,left semi,left anti,internal join type exsitenceJoin， 广播右侧
    *   inner like join,两侧都被广播
    *
    *   同 BHJ一样，用户可以指定broadcast hint和session-based的参数[[SQLConf.AUTO_BROADCASTJOIN_THRESHOLD]]来决定广播哪一侧
   *
    *    1)  广播有hint的那一侧，即使大小超过了[[SQLConf.AUTO_BROADCASTJOIN_THRESHOLD]]的阈值。
    *    如果两侧都有hint(比如 对于inner-like join),预计物理大小更小的那一侧会被广播
    *    2)优先遵循[[SQLConf.AUTO_BROADCASTJOIN_THRESHOLD]]这个阈值，永远广播小于这一阈值的一侧。
    *     如果两侧都低于这个阈值，就广播更小的一侧，如果两侧都没有更小，就不使用BNLJ
   *
   * - CartesianProduct:
    *   对于inner like join，CartesianProduct是失败备选
    *   for inner like join, CartesianProduct is the fallback option.
   *
   * - BroadcastNestedLoopJoin (BNLJ):
    *   对于其他join类型，BNLJ是失败备选。这里我们按照hint选择需要广播的那一侧。
    *   如果两侧都没有，就广播预计物理大小更小的那一侧
   */
  object JoinSelection extends Strategy with PredicateHelper {

    /**
     * Matches a plan whose output should be small enough to be used in broadcast join.
     */
    private def canBroadcast(plan: LogicalPlan): Boolean = {
      plan.stats.sizeInBytes >= 0 && plan.stats.sizeInBytes <= conf.autoBroadcastJoinThreshold
    }

    /**
     * Matches a plan whose single partition should be small enough to build a hash table.
     *
     * Note: this assume that the number of partition is fixed, requires additional work if it's
     * dynamic.
     */
    private def canBuildLocalHashMap(plan: LogicalPlan): Boolean = {
      plan.stats.sizeInBytes < conf.autoBroadcastJoinThreshold * conf.numShufflePartitions
    }

    /**
     * Returns whether plan a is much smaller (3X) than plan b.
     *
     * The cost to build hash map is higher than sorting, we should only build hash map on a table
     * that is much smaller than other one. Since we does not have the statistic for number of rows,
     * use the size of bytes here as estimation.
     */
    private def muchSmaller(a: LogicalPlan, b: LogicalPlan): Boolean = {
      a.stats.sizeInBytes * 3 <= b.stats.sizeInBytes
    }

    private def canBuildRight(joinType: JoinType): Boolean = joinType match {
      case _: InnerLike | LeftOuter | LeftSemi | LeftAnti | _: ExistenceJoin => true
      case _ => false
    }

    private def canBuildLeft(joinType: JoinType): Boolean = joinType match {
      case _: InnerLike | RightOuter => true
      case _ => false
    }

    private def broadcastSide(
                               canBuildLeft: Boolean,
                               canBuildRight: Boolean,
                               left: LogicalPlan,
                               right: LogicalPlan): BuildSide = {

      def smallerSide =
        if (right.stats.sizeInBytes <= left.stats.sizeInBytes) BuildRight else BuildLeft

      if (canBuildRight && canBuildLeft) {
        // Broadcast smaller side base on its estimated physical size
        // if both sides have broadcast hint
        smallerSide
      } else if (canBuildRight) {
        BuildRight
      } else if (canBuildLeft) {
        BuildLeft
      } else {
        // for the last default broadcast nested loop join
        smallerSide
      }
    }

    private def canBroadcastByHints(joinType: JoinType, left: LogicalPlan, right: LogicalPlan)
    : Boolean = {
      val buildLeft = canBuildLeft(joinType) && left.stats.hints.broadcast
      val buildRight = canBuildRight(joinType) && right.stats.hints.broadcast
      buildLeft || buildRight
    }

    private def broadcastSideByHints(joinType: JoinType, left: LogicalPlan, right: LogicalPlan)
    : BuildSide = {
      val buildLeft = canBuildLeft(joinType) && left.stats.hints.broadcast
      val buildRight = canBuildRight(joinType) && right.stats.hints.broadcast
      broadcastSide(buildLeft, buildRight, left, right)
    }

    private def canBroadcastBySizes(joinType: JoinType, left: LogicalPlan, right: LogicalPlan)
    : Boolean = {
      val buildLeft = canBuildLeft(joinType) && canBroadcast(left)
      val buildRight = canBuildRight(joinType) && canBroadcast(right)
      buildLeft || buildRight
    }

    private def broadcastSideBySizes(joinType: JoinType, left: LogicalPlan, right: LogicalPlan)
    : BuildSide = {
      val buildLeft = canBuildLeft(joinType) && canBroadcast(left)
      val buildRight = canBuildRight(joinType) && canBroadcast(right)
      broadcastSide(buildLeft, buildRight, left, right)
    }

    def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {

      // --- BroadcastHashJoin --------------------------------------------------------------------

      // broadcast hints were specified
      case ExtractEquiJoinKeys(joinType, leftKeys, rightKeys, condition, left, right)
        if canBroadcastByHints(joinType, left, right) =>
        val buildSide = broadcastSideByHints(joinType, left, right)
        Seq(joins.BroadcastHashJoinExec(
          leftKeys, rightKeys, joinType, buildSide, condition, planLater(left), planLater(right)))

      // broadcast hints were not specified, so need to infer it from size and configuration.
      case ExtractEquiJoinKeys(joinType, leftKeys, rightKeys, condition, left, right)
        if canBroadcastBySizes(joinType, left, right) =>
        val buildSide = broadcastSideBySizes(joinType, left, right)
        Seq(joins.BroadcastHashJoinExec(
          leftKeys, rightKeys, joinType, buildSide, condition, planLater(left), planLater(right)))

      // --- ShuffledHashJoin ---------------------------------------------------------------------

      case ExtractEquiJoinKeys(joinType, leftKeys, rightKeys, condition, left, right)
        if !conf.preferSortMergeJoin && canBuildRight(joinType) && canBuildLocalHashMap(right)
          && muchSmaller(right, left) ||
          !RowOrdering.isOrderable(leftKeys) =>
        Seq(joins.ShuffledHashJoinExec(
          leftKeys, rightKeys, joinType, BuildRight, condition, planLater(left), planLater(right)))

      case ExtractEquiJoinKeys(joinType, leftKeys, rightKeys, condition, left, right)
        if !conf.preferSortMergeJoin && canBuildLeft(joinType) && canBuildLocalHashMap(left)
          && muchSmaller(left, right) ||
          !RowOrdering.isOrderable(leftKeys) =>
        Seq(joins.ShuffledHashJoinExec(
          leftKeys, rightKeys, joinType, BuildLeft, condition, planLater(left), planLater(right)))

      // --- SortMergeJoin ------------------------------------------------------------

      case ExtractEquiJoinKeys(joinType, leftKeys, rightKeys, condition, left, right)
        if RowOrdering.isOrderable(leftKeys) =>
        joins.SortMergeJoinExec(
          leftKeys, rightKeys, joinType, condition, planLater(left), planLater(right)) :: Nil

      // --- Without joining keys ------------------------------------------------------------

      // Pick BroadcastNestedLoopJoin if one side could be broadcast
      case j @ logical.Join(left, right, joinType, condition)
        if canBroadcastByHints(joinType, left, right) =>
        val buildSide = broadcastSideByHints(joinType, left, right)
        joins.BroadcastNestedLoopJoinExec(
          planLater(left), planLater(right), buildSide, joinType, condition) :: Nil

      case j @ logical.Join(left, right, joinType, condition)
        if canBroadcastBySizes(joinType, left, right) =>
        val buildSide = broadcastSideBySizes(joinType, left, right)
        joins.BroadcastNestedLoopJoinExec(
          planLater(left), planLater(right), buildSide, joinType, condition) :: Nil

      // Pick CartesianProduct for InnerJoin
      case logical.Join(left, right, _: InnerLike, condition) =>
        joins.CartesianProductExec(planLater(left), planLater(right), condition) :: Nil

      case logical.Join(left, right, joinType, condition) =>
        val buildSide = broadcastSide(
          left.stats.hints.broadcast, right.stats.hints.broadcast, left, right)
        // This join could be very slow or OOM
        joins.BroadcastNestedLoopJoinExec(
          planLater(left), planLater(right), buildSide, joinType, condition) :: Nil

      // --- Cases where this strategy does not apply ---------------------------------------------

      case _ => Nil
    }
  }

  /**
   * Used to plan streaming aggregation queries that are computed incrementally as part of a
   * [[StreamingQuery]]. Currently this rule is injected into the planner
   * on-demand, only when planning in a [[org.apache.spark.daslab.sql.execution.streaming.StreamExecution]]
   */
  object StatefulAggregationStrategy extends Strategy {
    override def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case _ if !plan.isStreaming => Nil

      case EventTimeWatermark(columnName, delay, child) =>
        EventTimeWatermarkExec(columnName, delay, planLater(child)) :: Nil

      case PhysicalAggregation(
      namedGroupingExpressions, aggregateExpressions, rewrittenResultExpressions, child) =>

        if (aggregateExpressions.exists(PythonUDF.isGroupedAggPandasUDF)) {
          throw new AnalysisException(
            "Streaming aggregation doesn't support group aggregate pandas UDF")
        }

        val stateVersion = conf.getConf(SQLConf.STREAMING_AGGREGATION_STATE_FORMAT_VERSION)

        AggUtils.planStreamingAggregation(
          namedGroupingExpressions,
          aggregateExpressions.map(expr => expr.asInstanceOf[AggregateExpression]),
          rewrittenResultExpressions,
          stateVersion,
          planLater(child))

      case _ => Nil
    }
  }

  /**
   * Used to plan the streaming deduplicate operator.
   */
  object StreamingDeduplicationStrategy extends Strategy {
    override def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case Deduplicate(keys, child) if child.isStreaming =>
        StreamingDeduplicateExec(keys, planLater(child)) :: Nil

      case _ => Nil
    }
  }

  /**
   * Used to plan the streaming global limit operator for streams in append mode.
   * We need to check for either a direct Limit or a Limit wrapped in a ReturnAnswer operator,
   * following the example of the SpecialLimits Strategy above.
   * Streams with limit in Append mode use the stateful StreamingGlobalLimitExec.
   * Streams with limit in Complete mode use the stateless CollectLimitExec operator.
   * Limit is unsupported for streams in Update mode.
   */
  case class StreamingGlobalLimitStrategy(outputMode: OutputMode) extends Strategy {
    override def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case ReturnAnswer(rootPlan) => rootPlan match {
        case Limit(IntegerLiteral(limit), child)
          if plan.isStreaming && outputMode == InternalOutputModes.Append =>
          StreamingGlobalLimitExec(limit, LocalLimitExec(limit, planLater(child))) :: Nil
        case _ => Nil
      }
      case Limit(IntegerLiteral(limit), child)
        if plan.isStreaming && outputMode == InternalOutputModes.Append =>
        StreamingGlobalLimitExec(limit, LocalLimitExec(limit, planLater(child))) :: Nil
      case _ => Nil
    }
  }

  object StreamingJoinStrategy extends Strategy {
    override def apply(plan: LogicalPlan): Seq[SparkPlan] = {
      plan match {
        case ExtractEquiJoinKeys(joinType, leftKeys, rightKeys, condition, left, right)
          if left.isStreaming && right.isStreaming =>

          new StreamingSymmetricHashJoinExec(
            leftKeys, rightKeys, joinType, condition, planLater(left), planLater(right)) :: Nil

        case Join(left, right, _, _) if left.isStreaming && right.isStreaming =>
          throw new AnalysisException(
            "Stream-stream join without equality predicate is not supported", plan = Some(plan))

        case _ => Nil
      }
    }
  }

  /**
    *  用来处理使用了AggregateFunction2接口的表达式的聚合算子
    *  生成SparkPlan
   */
  object Aggregation extends Strategy {
    def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case PhysicalAggregation(groupingExpressions, aggExpressions, resultExpressions, child)
        // 如果aggExpressions都是聚合表达式
        if aggExpressions.forall(expr => expr.isInstanceOf[AggregateExpression]) =>
        val aggregateExpressions = aggExpressions.map(expr =>
          expr.asInstanceOf[AggregateExpression])


        //根据是否是Distinct分别处理
        val (functionsWithDistinct, functionsWithoutDistinct) =
          aggregateExpressions.partition(_.isDistinct)
        if (functionsWithDistinct.map(_.aggregateFunction.children.toSet).distinct.length > 1) {
          // 这是一个合理性的检查，代码不应该执行到这里（当我们有多个distinct column sets
          // This is a sanity check. We should not reach here when we have multiple distinct
          // column sets. Our `RewriteDistinctAggregates` should take care this case.
          sys.error("You hit a query analyzer bug. Please report your query to " +
            "Spark user mailing list.")
        }

        val aggregateOperator =
          if (functionsWithDistinct.isEmpty) {
            // without distinct
            // 这是目前我们处理的主要情况
            AggUtils.planAggregateWithoutDistinct(
              groupingExpressions,
              aggregateExpressions,
              resultExpressions,
              planLater(child))
          } else {
            AggUtils.planAggregateWithOneDistinct(
              groupingExpressions,
              functionsWithDistinct,
              functionsWithoutDistinct,
              resultExpressions,
              planLater(child))
          }

        aggregateOperator

      case PhysicalAggregation(groupingExpressions, aggExpressions, resultExpressions, child)
        if aggExpressions.forall(expr => expr.isInstanceOf[PythonUDF]) =>
        val udfExpressions = aggExpressions.map(expr => expr.asInstanceOf[PythonUDF])

        Seq(execution.python.AggregateInPandasExec(
          groupingExpressions,
          udfExpressions,
          resultExpressions,
          planLater(child)))

      case PhysicalAggregation(_, _, _, _) =>
        // 如果不能匹配到前两种情况，就是错误
        // If cannot match the two cases above, then it's an error
        throw new AnalysisException(
          "Cannot use a mixture of aggregate function and group aggregate pandas UDF")

      case _ => Nil
    }
  }

  object Window extends Strategy {
    def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case PhysicalWindow(
      WindowFunctionType.SQL, windowExprs, partitionSpec, orderSpec, child) =>
        execution.window.WindowExec(
          windowExprs, partitionSpec, orderSpec, planLater(child)) :: Nil

      case PhysicalWindow(
      WindowFunctionType.Python, windowExprs, partitionSpec, orderSpec, child) =>
        execution.python.WindowInPandasExec(
          windowExprs, partitionSpec, orderSpec, planLater(child)) :: Nil

      case _ => Nil
    }
  }

  protected lazy val singleRowRdd = sparkContext.parallelize(Seq(InternalRow()), 1)
  //todo PhysicalOperation
  /**
    *  针对InMemoryRelation这个LogicalPlan节点
    */
  object InMemoryScans extends Strategy {
    def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case PhysicalOperation(projectList, filters, mem: InMemoryRelation) =>
        pruneFilterProject(
          projectList,
          filters,
          identity[Seq[Expression]], // All filters still need to be evaluated.
          InMemoryTableScanExec(_, filters, mem)) :: Nil
      case _ => Nil
    }
  }

  /**
   * This strategy is just for explaining `Dataset/DataFrame` created by `spark.readStream`.
   * It won't affect the execution, because `StreamingRelation` will be replaced with
   * `StreamingExecutionRelation` in `StreamingQueryManager` and `StreamingExecutionRelation` will
   * be replaced with the real relation using the `Source` in `StreamExecution`.
   */
  object StreamingRelationStrategy extends Strategy {
    def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case s: StreamingRelation =>
        StreamingRelationExec(s.sourceName, s.output) :: Nil
      case s: StreamingExecutionRelation =>
        StreamingRelationExec(s.toString, s.output) :: Nil
      case s: StreamingRelationV2 =>
        StreamingRelationExec(s.sourceName, s.output) :: Nil
      case _ => Nil
    }
  }

  /**
   * Strategy to convert [[FlatMapGroupsWithState]] logical operator to physical operator
   * in streaming plans. Conversion for batch plans is handled by [[BasicOperators]].
   */
  object FlatMapGroupsWithStateStrategy extends Strategy {
    override def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case FlatMapGroupsWithState(
      func, keyDeser, valueDeser, groupAttr, dataAttr, outputAttr, stateEnc, outputMode, _,
      timeout, child) =>
        val stateVersion = conf.getConf(SQLConf.FLATMAPGROUPSWITHSTATE_STATE_FORMAT_VERSION)
        val execPlan = FlatMapGroupsWithStateExec(
          func, keyDeser, valueDeser, groupAttr, dataAttr, outputAttr, None, stateEnc, stateVersion,
          outputMode, timeout, batchTimestampMs = None, eventTimeWatermark = None, planLater(child))
        execPlan :: Nil
      case _ =>
        Nil
    }
  }

  /**
    *  将EvalPython的逻辑算子转换成物理算子的策略
   */
  object PythonEvals extends Strategy {
    override def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {
      case ArrowEvalPython(udfs, output, child) =>
        ArrowEvalPythonExec(udfs, output, planLater(child)) :: Nil
      case BatchEvalPython(udfs, output, child) =>
        BatchEvalPythonExec(udfs, output, planLater(child)) :: Nil
      case _ =>
        Nil
    }
  }

  /**
   * 对于基本算子的处理，一般一对一的映射为物理计划算子，如 Sort -> SortExec。
   */
  object BasicOperators extends Strategy {
    def apply(plan: LogicalPlan): Seq[SparkPlan] = plan match {

      case d: DataWritingCommand => DataWritingCommandExec(d, planLater(d.query)) :: Nil
      case r: RunnableCommand => ExecutedCommandExec(r) :: Nil

      case MemoryPlan(sink, output) =>
        val encoder = RowEncoder(sink.schema)
        LocalTableScanExec(output, sink.allData.map(r => encoder.toRow(r).copy())) :: Nil
      case MemoryPlanV2(sink, output) =>
        val encoder = RowEncoder(StructType.fromAttributes(output))
        LocalTableScanExec(output, sink.allData.map(r => encoder.toRow(r).copy())) :: Nil

      case logical.Distinct(child) =>
        throw new IllegalStateException(
          "logical distinct operator should have been replaced by aggregate in the optimizer")
      case logical.Intersect(left, right, false) =>
        throw new IllegalStateException(
          "logical intersect  operator should have been replaced by semi-join in the optimizer")
      case logical.Intersect(left, right, true) =>
        throw new IllegalStateException(
          "logical intersect operator should have been replaced by union, aggregate" +
            " and generate operators in the optimizer")
      case logical.Except(left, right, false) =>
        throw new IllegalStateException(
          "logical except operator should have been replaced by anti-join in the optimizer")
      case logical.Except(left, right, true) =>
        throw new IllegalStateException(
          "logical except (all) operator should have been replaced by union, aggregate" +
            " and generate operators in the optimizer")

      case logical.DeserializeToObject(deserializer, objAttr, child) =>
        execution.DeserializeToObjectExec(deserializer, objAttr, planLater(child)) :: Nil
      case logical.SerializeFromObject(serializer, child) =>
        execution.SerializeFromObjectExec(serializer, planLater(child)) :: Nil
      case logical.MapPartitions(f, objAttr, child) =>
        execution.MapPartitionsExec(f, objAttr, planLater(child)) :: Nil
      case logical.MapPartitionsInR(f, p, b, is, os, objAttr, child) =>
        execution.MapPartitionsExec(
          execution.r.MapPartitionsRWrapper(f, p, b, is, os), objAttr, planLater(child)) :: Nil
      case logical.FlatMapGroupsInR(f, p, b, is, os, key, value, grouping, data, objAttr, child) =>
        execution.FlatMapGroupsInRExec(f, p, b, is, os, key, value, grouping,
          data, objAttr, planLater(child)) :: Nil
      case logical.FlatMapGroupsInPandas(grouping, func, output, child) =>
        execution.python.FlatMapGroupsInPandasExec(grouping, func, output, planLater(child)) :: Nil
      case logical.MapElements(f, _, _, objAttr, child) =>
        execution.MapElementsExec(f, objAttr, planLater(child)) :: Nil
      case logical.AppendColumns(f, _, _, in, out, child) =>
        execution.AppendColumnsExec(f, in, out, planLater(child)) :: Nil
      case logical.AppendColumnsWithObject(f, childSer, newSer, child) =>
        execution.AppendColumnsWithObjectExec(f, childSer, newSer, planLater(child)) :: Nil
      case logical.MapGroups(f, key, value, grouping, data, objAttr, child) =>
        execution.MapGroupsExec(f, key, value, grouping, data, objAttr, planLater(child)) :: Nil
      case logical.FlatMapGroupsWithState(
      f, key, value, grouping, data, output, _, _, _, timeout, child) =>
        execution.MapGroupsExec(
          f, key, value, grouping, data, output, timeout, planLater(child)) :: Nil
      case logical.CoGroup(f, key, lObj, rObj, lGroup, rGroup, lAttr, rAttr, oAttr, left, right) =>
        execution.CoGroupExec(
          f, key, lObj, rObj, lGroup, rGroup, lAttr, rAttr, oAttr,
          planLater(left), planLater(right)) :: Nil

      case logical.Repartition(numPartitions, shuffle, child) =>
        if (shuffle) {
          ShuffleExchangeExec(RoundRobinPartitioning(numPartitions), planLater(child)) :: Nil
        } else {
          execution.CoalesceExec(numPartitions, planLater(child)) :: Nil
        }
      case logical.Sort(sortExprs, global, child) =>
        execution.SortExec(sortExprs, global, planLater(child)) :: Nil
      case p@ logical.Project(projectList, child) =>
        execution.ProjectExec(p.afterAppendList, planLater(child)) :: Nil
      case logical.Filter(condition, child) =>
        execution.FilterExec(condition, planLater(child)) :: Nil
      case f: logical.TypedFilter =>
        execution.FilterExec(f.typedCondition(f.deserializer), planLater(f.child)) :: Nil
      case e @ logical.Expand(_, _, child) =>
        execution.ExpandExec(e.projections, e.output, planLater(child)) :: Nil
      case logical.Sample(lb, ub, withReplacement, seed, child) =>
        execution.SampleExec(lb, ub, withReplacement, seed, planLater(child)) :: Nil
        //todo add physical plan here
      case s @ logical.AqpSample(errorRate,confidence,seed,child) =>
//        execution.AqpSampleExec(errorRate,confidence,seed,planLater(child))::Nil
        //execution.UniformSamplerExec(errorRate, confidence, seed, planLater(child))::Nil
        execution.DistinctSamplerExec(errorRate, confidence, seed, planLater(child), List(new DistinctColumn(0, LongType, "age")), 1,s.nameE) :: Nil
      case logical.LocalRelation(output, data, _) =>
        LocalTableScanExec(output, data) :: Nil
      case logical.LocalLimit(IntegerLiteral(limit), child) =>
        execution.LocalLimitExec(limit, planLater(child)) :: Nil
      case logical.GlobalLimit(IntegerLiteral(limit), child) =>
        execution.GlobalLimitExec(limit, planLater(child)) :: Nil
      case logical.Union(unionChildren) =>
        execution.UnionExec(unionChildren.map(planLater)) :: Nil
      case g @ logical.Generate(generator, _, outer, _, _, child) =>
        execution.GenerateExec(
          generator, g.requiredChildOutput, outer,
          g.qualifiedGeneratorOutput, planLater(child)) :: Nil
      case _: logical.OneRowRelation =>
        execution.RDDScanExec(Nil, singleRowRdd, "OneRowRelation") :: Nil
      case r: logical.Range =>
        execution.RangeExec(r) :: Nil
      case r: logical.RepartitionByExpression =>
        exchange.ShuffleExchangeExec(r.partitioning, planLater(r.child)) :: Nil
      case ExternalRDD(outputObjAttr, rdd) => ExternalRDDScanExec(outputObjAttr, rdd) :: Nil
      case r: LogicalRDD =>
        RDDScanExec(r.output, r.rdd, "ExistingRDD", r.outputPartitioning, r.outputOrdering) :: Nil
      case h: ResolvedHint => planLater(h.child) :: Nil
      case _ => Nil
    }
  }
}
