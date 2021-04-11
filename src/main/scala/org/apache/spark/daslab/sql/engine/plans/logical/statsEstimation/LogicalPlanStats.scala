package org.apache.spark.daslab.sql.engine.plans.logical.statsEstimation

import org.apache.spark.daslab.sql.engine.plans.logical._



/**
 * A trait to add statistics propagation to [[LogicalPlan]].
 */
trait LogicalPlanStats { self: LogicalPlan =>

  /**
   * Returns the estimated statistics for the current logical plan node. Under the hood, this
   * method caches the return value, which is computed based on the configuration passed in the
   * first time. If the configuration changes, the cache can be invalidated by calling
   * [[invalidateStatsCache()]].
   */
  def stats: Statistics = statsCache.getOrElse {
    if (conf.cboEnabled) {
     // println("cbo enabled ****************************")
      statsCache = Option(BasicStatsPlanVisitor.visit(self))
    } else {
     //  println("SizeInBytesOnlyStatsPlanVisitor enabled *****************************")
      statsCache = Option(SizeInBytesOnlyStatsPlanVisitor.visit(self))
    }
    statsCache.get
  }

  /** A cache for the estimated statistics, such that it will only be computed once. */
  protected var statsCache: Option[Statistics] = None

  /** Invalidates the stats cache. See [[stats]] for more information. */
  final def invalidateStatsCache(): Unit = {
    statsCache = None
    children.foreach(_.invalidateStatsCache())
  }
}
