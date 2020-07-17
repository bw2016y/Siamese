package org.apache.spark.daslab.sql.engine.plans.logical.statsEstimation

import org.apache.spark.daslab.sql.engine.plans.logical._

//todo add FilterEstimation/JoinEstimation/ProjectEstimation
/**
 * A [[LogicalPlanVisitor]] that computes the statistics for the cost-based optimizer.
 */
object BasicStatsPlanVisitor extends LogicalPlanVisitor[Statistics] {

  /** Falls back to the estimation computed by [[SizeInBytesOnlyStatsPlanVisitor]]. */
  private def fallback(p: LogicalPlan): Statistics = SizeInBytesOnlyStatsPlanVisitor.visit(p)

  override def default(p: LogicalPlan): Statistics = fallback(p)

  override def visitAggregate(p: Aggregate): Statistics = {
    AggregateEstimation.estimate(p).getOrElse(fallback(p))
  }

  override def visitDistinct(p: Distinct): Statistics = fallback(p)

  override def visitExcept(p: Except): Statistics = fallback(p)

  override def visitExpand(p: Expand): Statistics = fallback(p)

  override def visitFilter(p: Filter): Statistics = {
    FilterEstimation(p).estimate.getOrElse(fallback(p))
  }

  override def visitGenerate(p: Generate): Statistics = fallback(p)

  override def visitGlobalLimit(p: GlobalLimit): Statistics = fallback(p)

  override def visitHint(p: ResolvedHint): Statistics = fallback(p)

  override def visitIntersect(p: Intersect): Statistics = fallback(p)

  override def visitJoin(p: Join): Statistics = {
    JoinEstimation(p).estimate.getOrElse(fallback(p))
  }

  override def visitLocalLimit(p: LocalLimit): Statistics = fallback(p)

  override def visitPivot(p: Pivot): Statistics = fallback(p)

  override def visitProject(p: Project): Statistics = {
    ProjectEstimation.estimate(p).getOrElse(fallback(p))
  }

  override def visitRepartition(p: Repartition): Statistics = fallback(p)

  override def visitRepartitionByExpr(p: RepartitionByExpression): Statistics = fallback(p)

  override def visitSample(p: Sample): Statistics = fallback(p)

  override def visitScriptTransform(p: ScriptTransformation): Statistics = fallback(p)

  override def visitUnion(p: Union): Statistics = fallback(p)

  override def visitWindow(p: Window): Statistics = fallback(p)
}
