package org.apache.spark.daslab.sql.engine.optimizer



import scala.annotation.tailrec

import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.logical._
import org.apache.spark.daslab.sql.engine.rules.Rule


/**
 * If one or both of the datasets in the logical [[Except]] operator are purely transformed using
 * [[Filter]], this rule will replace logical [[Except]] operator with a [[Filter]] operator by
 * flipping the filter condition of the right child.
 * {{{
 *   SELECT a1, a2 FROM Tab1 WHERE a2 = 12 EXCEPT SELECT a1, a2 FROM Tab1 WHERE a1 = 5
 *   ==>  SELECT DISTINCT a1, a2 FROM Tab1 WHERE a2 = 12 AND (a1 is null OR a1 <> 5)
 * }}}
 *
 * Note:
 * Before flipping the filter condition of the right node, we should:
 * 1. Combine all it's [[Filter]].
 * 2. Update the attribute references to the left node;
 * 3. Add a Coalesce(condition, False) (to take into account of NULL values in the condition).
 */
object ReplaceExceptWithFilter extends Rule[LogicalPlan] {

  def apply(plan: LogicalPlan): LogicalPlan = {
    if (!plan.conf.replaceExceptWithFilter) {
      return plan
    }

    plan.transform {
      case e @ Except(left, right, false) if isEligible(left, right) =>
        val filterCondition = combineFilters(skipProject(right)).asInstanceOf[Filter].condition
        if (filterCondition.deterministic) {
          transformCondition(left, filterCondition).map { c =>
            Distinct(Filter(Not(c), left))
          }.getOrElse {
            e
          }
        } else {
          e
        }
    }
  }

  private def transformCondition(plan: LogicalPlan, condition: Expression): Option[Expression] = {
    val attributeNameMap: Map[String, Attribute] = plan.output.map(x => (x.name, x)).toMap
    if (condition.references.forall(r => attributeNameMap.contains(r.name))) {
      val rewrittenCondition = condition.transform {
        case a: AttributeReference => attributeNameMap(a.name)
      }
      // We need to consider as False when the condition is NULL, otherwise we do not return those
      // rows containing NULL which are instead filtered in the Except right plan
      Some(Coalesce(Seq(rewrittenCondition, Literal.FalseLiteral)))
    } else {
      None
    }
  }

  // TODO: This can be further extended in the future.
  private def isEligible(left: LogicalPlan, right: LogicalPlan): Boolean = (left, right) match {
    case (_, right @ (Project(_, _: Filter) | Filter(_, _))) => verifyConditions(left, right)
    case _ => false
  }

  private def verifyConditions(left: LogicalPlan, right: LogicalPlan): Boolean = {
    val leftProjectList = projectList(left)
    val rightProjectList = projectList(right)

    left.output.size == left.output.map(_.name).distinct.size &&
      left.find(_.expressions.exists(SubqueryExpression.hasSubquery)).isEmpty &&
      right.find(_.expressions.exists(SubqueryExpression.hasSubquery)).isEmpty &&
      Project(leftProjectList, nonFilterChild(skipProject(left))).sameResult(
        Project(rightProjectList, nonFilterChild(skipProject(right))))
  }

  private def projectList(node: LogicalPlan): Seq[NamedExpression] = node match {
    case p: Project => p.projectList
    case x => x.output
  }

  private def skipProject(node: LogicalPlan): LogicalPlan = node match {
    case p: Project => p.child
    case x => x
  }

  private def nonFilterChild(plan: LogicalPlan) = plan.find(!_.isInstanceOf[Filter]).getOrElse {
    throw new IllegalStateException("Leaf node is expected")
  }

  private def combineFilters(plan: LogicalPlan): LogicalPlan = {
    @tailrec
    def iterate(plan: LogicalPlan, acc: LogicalPlan): LogicalPlan = {
      if (acc.fastEquals(plan)) acc else iterate(acc, CombineFilters(acc))
    }
    iterate(plan, CombineFilters(plan))
  }
}
