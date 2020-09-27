package org.apache.spark.daslab.sql.execution



import org.apache.spark.daslab.sql._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.execution.datasources.{DataSourceStrategy, FileSourceStrategy}
import org.apache.spark.daslab.sql.execution.datasources.v2.DataSourceV2Strategy
import org.apache.spark.daslab.sql.internal.SQLConf

//todo
import org.apache.spark.SparkContext

class SparkPlanner(
                    val sparkContext: SparkContext,
                    val conf: SQLConf,
                    val experimentalMethods: ExperimentalMethods)
  extends SparkStrategies {

  def numPartitions: Int = conf.numShufflePartitions

  override def strategies: Seq[Strategy] =
    experimentalMethods.extraStrategies ++
      extraPlanningStrategies ++ (
      PythonEvals ::
        DataSourceV2Strategy ::
        FileSourceStrategy ::
        DataSourceStrategy(conf) ::
        SpecialLimits ::
        Aggregation ::
        Window ::
        JoinSelection ::
        InMemoryScans ::
        BasicOperators :: Nil)

  /**
    *  可以通过重写这个方法为planner添加额外的strategies,这些策略在[[ExperimentalMethods]]方法中定义的策略之后执行，
    *  在常规的策略之前执行
    * @return
    */
  def extraPlanningStrategies: Seq[Strategy] = Nil

  override protected def collectPlaceholders(plan: SparkPlan): Seq[(SparkPlan, LogicalPlan)] = {
    plan.collect {
      case placeholder @ PlanLater(logicalPlan) => placeholder -> logicalPlan
    }
  }

  override protected def prunePlans(plans: Iterator[SparkPlan]): Iterator[SparkPlan] = {
    //TODO： 在探索计划空间的时候，需要去掉糟糕的计划来防止组合空间的爆炸
    plans
  }

  /**
   * Used to build table scan operators where complex projection and filtering are done using
   * separate physical operators.  This function returns the given scan operator with Project and
   * Filter nodes added only when needed.  For example, a Project operator is only used when the
   * final desired output requires complex expressions to be evaluated or when columns can be
   * further eliminated out after filtering has been done.
   *
   * The `prunePushedDownFilters` parameter is used to remove those filters that can be optimized
   * away by the filter pushdown optimization.
   *
   * The required attributes for both filtering and expression evaluation are passed to the
   * provided `scanBuilder` function so that it can avoid unnecessary column materialization.
   */
  def pruneFilterProject(
                          projectList: Seq[NamedExpression],
                          filterPredicates: Seq[Expression],
                          prunePushedDownFilters: Seq[Expression] => Seq[Expression],
                          scanBuilder: Seq[Attribute] => SparkPlan): SparkPlan = {

    val projectSet = AttributeSet(projectList.flatMap(_.references))
    val filterSet = AttributeSet(filterPredicates.flatMap(_.references))
    val filterCondition: Option[Expression] =
      prunePushedDownFilters(filterPredicates).reduceLeftOption(engine.expressions.And)

    // Right now we still use a projection even if the only evaluation is applying an alias
    // to a column.  Since this is a no-op, it could be avoided. However, using this
    // optimization with the current implementation would change the output schema.
    // TODO: Decouple final output schema from expression evaluation so this copy can be
    // avoided safely.

    if (AttributeSet(projectList.map(_.toAttribute)) == projectSet &&
      filterSet.subsetOf(projectSet)) {
      // When it is possible to just use column pruning to get the right projection and
      // when the columns of this projection are enough to evaluate all filter conditions,
      // just do a scan followed by a filter, with no extra project.
      val scan = scanBuilder(projectList.asInstanceOf[Seq[Attribute]])
      filterCondition.map(FilterExec(_, scan)).getOrElse(scan)
    } else {
      val scan = scanBuilder((projectSet ++ filterSet).toSeq)
      ProjectExec(projectList, filterCondition.map(FilterExec(_, scan)).getOrElse(scan))
    }
  }
}
