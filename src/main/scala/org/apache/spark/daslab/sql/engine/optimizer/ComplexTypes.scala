package org.apache.spark.daslab.sql.engine.optimizer



import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.logical.{Aggregate, LogicalPlan}
import org.apache.spark.daslab.sql.engine.rules.Rule

/**
 * Simplify redundant [[CreateNamedStructLike]], [[CreateArray]] and [[CreateMap]] expressions.
 */
object SimplifyExtractValueOps extends Rule[LogicalPlan] {
  override def apply(plan: LogicalPlan): LogicalPlan = plan transform {
    // One place where this optimization is invalid is an aggregation where the select
    // list expression is a function of a grouping expression:
    //
    // SELECT struct(a,b).a FROM tbl GROUP BY struct(a,b)
    //
    // cannot be simplified to SELECT a FROM tbl GROUP BY struct(a,b). So just skip this
    // optimization for Aggregates (although this misses some cases where the optimization
    // can be made).
    case a: Aggregate => a
    case p => p.transformExpressionsUp {
      // Remove redundant field extraction.
      case GetStructField(createNamedStructLike: CreateNamedStructLike, ordinal, _) =>
        createNamedStructLike.valExprs(ordinal)

      // Remove redundant array indexing.
      case GetArrayStructFields(CreateArray(elems), field, ordinal, _, _) =>
        // Instead of selecting the field on the entire array, select it from each member
        // of the array. Pushing down the operation this way may open other optimizations
        // opportunities (i.e. struct(...,x,...).x)
        CreateArray(elems.map(GetStructField(_, ordinal, Some(field.name))))

      // Remove redundant map lookup.
      case ga @ GetArrayItem(CreateArray(elems), IntegerLiteral(idx)) =>
        // Instead of creating the array and then selecting one row, remove array creation
        // altogether.
        if (idx >= 0 && idx < elems.size) {
          // valid index
          elems(idx)
        } else {
          // out of bounds, mimic the runtime behavior and return null
          Literal(null, ga.dataType)
        }
      case GetMapValue(CreateMap(elems), key) => CaseKeyWhen(key, elems)
    }
  }
}
