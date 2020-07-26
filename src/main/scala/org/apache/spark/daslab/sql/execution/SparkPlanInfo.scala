package org.apache.spark.daslab.sql.execution

import org.apache.spark.daslab.sql.execution.exchange.ReusedExchangeExec
import org.apache.spark.daslab.sql.execution.metric.SQLMetricInfo
//todo
import org.apache.spark.annotation.DeveloperApi



/**
 * :: DeveloperApi ::
 * Stores information about a SQL SparkPlan.
 */
@DeveloperApi
class SparkPlanInfo(
                     val nodeName: String,
                     val simpleString: String,
                     val children: Seq[SparkPlanInfo],
                     val metadata: Map[String, String],
                     val metrics: Seq[SQLMetricInfo]) {

  override def hashCode(): Int = {
    // hashCode of simpleString should be good enough to distinguish the plans from each other
    // within a plan
    simpleString.hashCode
  }

  override def equals(other: Any): Boolean = other match {
    case o: SparkPlanInfo =>
      nodeName == o.nodeName && simpleString == o.simpleString && children == o.children
    case _ => false
  }
}

private[execution] object SparkPlanInfo {

  def fromSparkPlan(plan: SparkPlan): SparkPlanInfo = {
    val children = plan match {
      case ReusedExchangeExec(_, child) => child :: Nil
      case _ => plan.children ++ plan.subqueries
    }
    val metrics = plan.metrics.toSeq.map { case (key, metric) =>
      new SQLMetricInfo(metric.name.getOrElse(key), metric.id, metric.metricType)
    }

    // dump the file scan metadata (e.g file path) to event log
    val metadata = plan match {
      case fileScan: FileSourceScanExec => fileScan.metadata
      case _ => Map[String, String]()
    }
    new SparkPlanInfo(plan.nodeName, plan.simpleString, children.map(fromSparkPlan),
      metadata, metrics)
  }
}
