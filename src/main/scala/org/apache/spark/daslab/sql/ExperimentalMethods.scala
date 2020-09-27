package org.apache.spark.daslab.sql



import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.engine.rules.Rule

//todo
import org.apache.spark.annotation.{Experimental, InterfaceStability}


/**
 * :: Experimental ::
 * Holder for experimental methods for the bravest. We make NO guarantee about the stability
 * regarding binary compatibility and source compatibility of methods here.
 *
 * {{{
 *   spark.experimental.extraStrategies += ...
 * }}}
 *
 * @since 1.3.0
 */
@Experimental
@InterfaceStability.Unstable
class ExperimentalMethods private[sql]() {

  /**
   * Allows extra strategies to be injected into the query planner at runtime.  Note this API
   * should be considered experimental and is not intended to be stable across releases.
   *
   * @since 1.3.0
   */
  @volatile var extraStrategies: Seq[Strategy] = Nil

  @volatile var extraOptimizations: Seq[Rule[LogicalPlan]] = Nil

  override def clone(): ExperimentalMethods = {
    val result = new ExperimentalMethods
    result.extraStrategies = extraStrategies
    result.extraOptimizations = extraOptimizations
    result
  }
}
