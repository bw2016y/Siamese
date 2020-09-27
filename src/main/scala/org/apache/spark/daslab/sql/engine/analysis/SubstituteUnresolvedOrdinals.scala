package org.apache.spark.daslab.sql.engine.analysis


import org.apache.spark.daslab.sql.engine.expressions.{Expression, Literal, SortOrder}
import org.apache.spark.daslab.sql.engine.plans.logical.{Aggregate, LogicalPlan, Sort}
import org.apache.spark.daslab.sql.engine.rules.Rule
import org.apache.spark.daslab.sql.engine.trees.CurrentOrigin.withOrigin
import org.apache.spark.daslab.sql.internal.SQLConf
import org.apache.spark.daslab.sql.types.IntegerType

/**
 * Replaces ordinal in 'order by' or 'group by' with UnresolvedOrdinal expression.
 */
class SubstituteUnresolvedOrdinals(conf: SQLConf) extends Rule[LogicalPlan] {
  private def isIntLiteral(e: Expression) = e match {
    case Literal(_, IntegerType) => true
    case _ => false
  }

  def apply(plan: LogicalPlan): LogicalPlan = plan resolveOperators {
    case s: Sort if conf.orderByOrdinal && s.order.exists(o => isIntLiteral(o.child)) =>
      val newOrders = s.order.map {
        case order @ SortOrder(ordinal @ Literal(index: Int, IntegerType), _, _, _) =>
          val newOrdinal = withOrigin(ordinal.origin)(UnresolvedOrdinal(index))
          withOrigin(order.origin)(order.copy(child = newOrdinal))
        case other => other
      }
      withOrigin(s.origin)(s.copy(order = newOrders))

    case a: Aggregate if conf.groupByOrdinal && a.groupingExpressions.exists(isIntLiteral) =>
      val newGroups = a.groupingExpressions.map {
        case ordinal @ Literal(index: Int, IntegerType) =>
          withOrigin(ordinal.origin)(UnresolvedOrdinal(index))
        case other => other
      }
      withOrigin(a.origin)(a.copy(groupingExpressions = newGroups))
  }
}
