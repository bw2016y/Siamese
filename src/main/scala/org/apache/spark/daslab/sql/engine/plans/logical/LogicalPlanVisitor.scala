package org.apache.spark.daslab.sql.engine.plans.logical



/**
 * A visitor pattern for traversing a [[LogicalPlan]] tree and computing some properties.
 */
trait LogicalPlanVisitor[T] {

  def visit(p: LogicalPlan): T = p match {
    case p: Aggregate => visitAggregate(p)
    case p: Distinct => visitDistinct(p)
    case p: Except => visitExcept(p)
    case p: Expand => visitExpand(p)
    case p: Filter => visitFilter(p)
    case p: Generate => visitGenerate(p)
    case p: GlobalLimit => visitGlobalLimit(p)
    case p: Intersect => visitIntersect(p)
    case p: Join => visitJoin(p)
    case p: LocalLimit => visitLocalLimit(p)
    case p: Pivot => visitPivot(p)
    case p: Project => visitProject(p)
    case p: Repartition => visitRepartition(p)
    case p: RepartitionByExpression => visitRepartitionByExpr(p)
    case p: ResolvedHint => visitHint(p)
    case p: Sample => visitSample(p)
    case p: ScriptTransformation => visitScriptTransform(p)
    case p: Union => visitUnion(p)
    case p: Window => visitWindow(p)
    case p: LogicalPlan => default(p)
  }

  def default(p: LogicalPlan): T

  def visitAggregate(p: Aggregate): T

  def visitDistinct(p: Distinct): T

  def visitExcept(p: Except): T

  def visitExpand(p: Expand): T

  def visitFilter(p: Filter): T

  def visitGenerate(p: Generate): T

  def visitGlobalLimit(p: GlobalLimit): T

  def visitHint(p: ResolvedHint): T

  def visitIntersect(p: Intersect): T

  def visitJoin(p: Join): T

  def visitLocalLimit(p: LocalLimit): T

  def visitPivot(p: Pivot): T

  def visitProject(p: Project): T

  def visitRepartition(p: Repartition): T

  def visitRepartitionByExpr(p: RepartitionByExpression): T

  def visitSample(p: Sample): T

  def visitScriptTransform(p: ScriptTransformation): T

  def visitUnion(p: Union): T

  def visitWindow(p: Window): T
}
