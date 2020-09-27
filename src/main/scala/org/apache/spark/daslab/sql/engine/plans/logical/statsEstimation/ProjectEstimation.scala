package org.apache.spark.daslab.sql.engine.plans.logical.statsEstimation


import org.apache.spark.daslab.sql.engine.expressions.{Alias, Attribute, AttributeMap}
import org.apache.spark.daslab.sql.engine.plans.logical.{Project, Statistics}

object ProjectEstimation {
  import EstimationUtils._

  def estimate(project: Project): Option[Statistics] = {
    if (rowCountsExist(project.child)) {
      val childStats = project.child.stats
      val inputAttrStats = childStats.attributeStats
      // Match alias with its child's column stat
      val aliasStats = project.expressions.collect {
        case alias @ Alias(attr: Attribute, _) if inputAttrStats.contains(attr) =>
          alias.toAttribute -> inputAttrStats(attr)
      }
      val outputAttrStats =
        getOutputMap(AttributeMap(inputAttrStats.toSeq ++ aliasStats), project.output)
      Some(childStats.copy(
        sizeInBytes = getOutputSize(project.output, childStats.rowCount.get, outputAttrStats),
        attributeStats = outputAttrStats))
    } else {
      None
    }
  }
}
