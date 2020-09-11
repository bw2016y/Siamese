package org.apache.spark.daslab.sql.engine.planning


//todo
import org.apache.spark.internal.Logging


import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.engine.trees.TreeNode


/**
  * 对于给定的[[LogicalPlan]]，返回一系列可以用于执行的物理计划，如果这个策略对于给定的逻辑操作不适用，
  *  则返回空的列表
  * @tparam PhysicalPlan
  */
abstract class GenericStrategy[PhysicalPlan <: TreeNode[PhysicalPlan]] extends Logging {


  /**
    *  返回对于无法应用策略的部分返回一个Placeholder，这个placeholder会在[[QueryPlanner]]中被自动收集并使用其他可用执行策略替换
    * @param plan
    * @return
    */
  protected def planLater(plan: LogicalPlan): PhysicalPlan

  def apply(plan: LogicalPlan): Seq[PhysicalPlan]
}

/** 
  * TODO　为了将[[LogicalPlan]]转化为物理计划的抽象类，子类负责申明一系列的[[GenericStrategy]]
  *  这些策略可以返回一系列可能可行的物理计划
  *  如果一个给定的策略不能plan树中剩余的所有的算子，它就直接调用[[GenericStrategy#planLater planLater]]方法，这个方法可以
  *  返回一个placeholder 对象，这个对象可以被[[collectPlaceholders()]]方法收集并使用其他可行的策略填入结果
  *
  *
 * TODO: 目前每次只返回一个可行的Physical plan
 *       可行物理计划解空间的探索需要实现
 *
 * @tparam PhysicalPlan The type of physical plan produced by this [[QueryPlanner]]
 */
abstract class QueryPlanner[PhysicalPlan <: TreeNode[PhysicalPlan]] {
  /** A list of execution strategies that can be used by the planner */
  def strategies: Seq[GenericStrategy[PhysicalPlan]]

  def plan(plan: LogicalPlan): Iterator[PhysicalPlan] = {

    // 收集候选物理计划
    val candidates = strategies.iterator.flatMap(_(plan))


    // 候选计划中可能包含很多的placeholders 被标记为 [[planLater]]，
    // 需要使用他们的子计划来替换他们
    val plans = candidates.flatMap { candidate =>
      val placeholders = collectPlaceholders(candidate)

      if (placeholders.isEmpty) {
        // 因为不包含placeholders 所以可以直接通过
        Iterator(candidate)
      } else {
        // 处理那些被标记为[[PlanLater]]的逻辑计划 ，并将placeholders替换掉
        //todo
        placeholders.iterator.foldLeft(Iterator(candidate)) {
          case (candidatesWithPlaceholders, (placeholder, logicalPlan)) =>

            // 为placeholder来生成物理计划
            val childPlans = this.plan(logicalPlan)

            candidatesWithPlaceholders.flatMap { candidateWithPlaceholders =>
              childPlans.map { childPlan =>
                // Replace the placeholder by the child plan
                candidateWithPlaceholders.transformUp {
                  case p if p.eq(placeholder) => childPlan
                }
              }
            }
        }
      }
    }
    //实际上没有剪枝
    val pruned = prunePlans(plans)
    assert(pruned.hasNext, s"No plan for $plan")
    pruned
  }

  /**
   * Collects placeholders marked using [[GenericStrategy#planLater planLater]]
   * by [[strategies]].
   */
  protected def collectPlaceholders(plan: PhysicalPlan): Seq[(PhysicalPlan, LogicalPlan)]

  /** Prunes bad plans to prevent combinatorial explosion. */
  protected def prunePlans(plans: Iterator[PhysicalPlan]): Iterator[PhysicalPlan]
}

