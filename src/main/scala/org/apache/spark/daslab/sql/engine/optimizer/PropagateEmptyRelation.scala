package org.apache.spark.daslab.sql.engine.optimizer



import org.apache.spark.daslab.sql.engine.analysis.CastSupport
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans._
import org.apache.spark.daslab.sql.engine.plans.logical._
import org.apache.spark.daslab.sql.engine.rules._
import org.apache.spark.daslab.sql.internal.SQLConf

/**
 * Collapse plans consisting empty local relations generated by [[PruneFilters]].
 * 1. Binary(or Higher)-node Logical Plans
 *    - Union with all empty children.
 *    - Join with one or two empty children (including Intersect/Except).
 * 2. Unary-node Logical Plans
 *    - Project/Filter/Sample/Join/Limit/Repartition with all empty children.
 *    - Aggregate with all empty children and at least one grouping expression.
 *    - Generate(Explode) with all empty children. Others like Hive UDTF may return results.
 */
object PropagateEmptyRelation extends Rule[LogicalPlan] with PredicateHelper with CastSupport {
  private def isEmptyLocalRelation(plan: LogicalPlan): Boolean = plan match {
    case p: LocalRelation => p.data.isEmpty
    case _ => false
  }

  private def empty(plan: LogicalPlan) =
    LocalRelation(plan.output, data = Seq.empty, isStreaming = plan.isStreaming)

  // Construct a project list from plan's output, while the value is always NULL.
  private def nullValueProjectList(plan: LogicalPlan): Seq[NamedExpression] =
    plan.output.map{ a => Alias(cast(Literal(null), a.dataType), a.name)(a.exprId) }

  override def conf: SQLConf = SQLConf.get

  def apply(plan: LogicalPlan): LogicalPlan = plan transformUp {
    case p: Union if p.children.forall(isEmptyLocalRelation) =>
      empty(p)

    // Joins on empty LocalRelations generated from streaming sources are not eliminated
    // as stateful streaming joins need to perform other state management operations other than
    // just processing the input data.
    case p @ Join(_, _, joinType, _)
      if !p.children.exists(_.isStreaming) =>
      val isLeftEmpty = isEmptyLocalRelation(p.left)
      val isRightEmpty = isEmptyLocalRelation(p.right)
      if (isLeftEmpty || isRightEmpty) {
        joinType match {
          case _: InnerLike => empty(p)
          // Intersect is handled as LeftSemi by `ReplaceIntersectWithSemiJoin` rule.
          // Except is handled as LeftAnti by `ReplaceExceptWithAntiJoin` rule.
          case LeftOuter | LeftSemi | LeftAnti if isLeftEmpty => empty(p)
          case LeftSemi if isRightEmpty => empty(p)
          case LeftAnti if isRightEmpty => p.left
          case FullOuter if isLeftEmpty && isRightEmpty => empty(p)
          case LeftOuter | FullOuter if isRightEmpty =>
            Project(p.left.output ++ nullValueProjectList(p.right), p.left)
          case RightOuter if isRightEmpty => empty(p)
          case RightOuter | FullOuter if isLeftEmpty =>
            Project(nullValueProjectList(p.left) ++ p.right.output, p.right)
          case _ => p
        }
      } else {
        p
      }

    case p: UnaryNode if p.children.nonEmpty && p.children.forall(isEmptyLocalRelation) => p match {
      case _: Project => empty(p)
      case _: Filter => empty(p)
      case _: Sample => empty(p)
      case _: Sort => empty(p)
      case _: GlobalLimit => empty(p)
      case _: LocalLimit => empty(p)
      case _: Repartition => empty(p)
      case _: RepartitionByExpression => empty(p)
      // An aggregate with non-empty group expression will return one output row per group when the
      // input to the aggregate is not empty. If the input to the aggregate is empty then all groups
      // will be empty and thus the output will be empty. If we're working on batch data, we can
      // then treat the aggregate as redundant.
      //
      // If the aggregate is over streaming data, we may need to update the state store even if no
      // new rows are processed, so we can't eliminate the node.
      //
      // If the grouping expressions are empty, however, then the aggregate will always produce a
      // single output row and thus we cannot propagate the EmptyRelation.
      //
      // Aggregation on empty LocalRelation generated from a streaming source is not eliminated
      // as stateful streaming aggregation need to perform other state management operations other
      // than just processing the input data.
      case Aggregate(ge, _, _) if ge.nonEmpty && !p.isStreaming => empty(p)
      // Generators like Hive-style UDTF may return their records within `close`.
      case Generate(_: Explode, _, _, _, _, _) => empty(p)
      case _ => p
    }
  }
}
