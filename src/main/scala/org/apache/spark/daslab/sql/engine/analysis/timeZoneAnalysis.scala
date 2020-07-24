package org.apache.spark.daslab.sql.engine.analysis



import org.apache.spark.daslab.sql.engine.expressions.{Cast, Expression, ListQuery, TimeZoneAwareExpression}
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.engine.rules.Rule
import org.apache.spark.daslab.sql.internal.SQLConf
import org.apache.spark.daslab.sql.types.DataType

/**
 * Replace [[TimeZoneAwareExpression]] without timezone id by its copy with session local
 * time zone.
 */
case class ResolveTimeZone(conf: SQLConf) extends Rule[LogicalPlan] {
  private val transformTimeZoneExprs: PartialFunction[Expression, Expression] = {
    case e: TimeZoneAwareExpression if e.timeZoneId.isEmpty =>
      e.withTimeZone(conf.sessionLocalTimeZone)
    // Casts could be added in the subquery plan through the rule TypeCoercion while coercing
    // the types between the value expression and list query expression of IN expression.
    // We need to subject the subquery plan through ResolveTimeZone again to setup timezone
    // information for time zone aware expressions.
    case e: ListQuery => e.withNewPlan(apply(e.plan))
  }

  override def apply(plan: LogicalPlan): LogicalPlan =
    plan.resolveExpressions(transformTimeZoneExprs)

  def resolveTimeZones(e: Expression): Expression = e.transform(transformTimeZoneExprs)
}

/**
 * Mix-in trait for constructing valid [[Cast]] expressions.
 */
trait CastSupport {
  /**
   * Configuration used to create a valid cast expression.
   */
  def conf: SQLConf

  /**
   * Create a Cast expression with the session local time zone.
   */
  def cast(child: Expression, dataType: DataType): Cast = {
    Cast(child, dataType, Option(conf.sessionLocalTimeZone))
  }
}
