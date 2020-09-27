package org.apache.spark.daslab.sql.engine.plans.logical.statsEstimation

import org.apache.spark.daslab.sql.engine.expressions.Attribute
import org.apache.spark.daslab.sql.engine.plans.logical.{Aggregate, Statistics}



object AggregateEstimation {
  import EstimationUtils._

  /**
   * Estimate the number of output rows based on column stats of group-by columns, and propagate
   * column stats for aggregate expressions.
   */
  def estimate(agg: Aggregate): Option[Statistics] = {
    val childStats = agg.child.stats
    // Check if we have column stats for all group-by columns.
    val colStatsExist = agg.groupingExpressions.forall { e =>
      e.isInstanceOf[Attribute] &&
        childStats.attributeStats.get(e.asInstanceOf[Attribute]).exists(_.hasCountStats)
    }
    if (rowCountsExist(agg.child) && colStatsExist) {
      // Multiply distinct counts of group-by columns. This is an upper bound, which assumes
      // the data contains all combinations of distinct values of group-by columns.
      var outputRows: BigInt = agg.groupingExpressions.foldLeft(BigInt(1))(
        (res, expr) => {
          val columnStat = childStats.attributeStats(expr.asInstanceOf[Attribute])
          val distinctCount = columnStat.distinctCount.get
          val distinctValue: BigInt = if (columnStat.nullCount.get > 0) {
            distinctCount + 1
          } else {
            distinctCount
          }
          res * distinctValue
        })

      outputRows = if (agg.groupingExpressions.isEmpty) {
        // If there's no group-by columns, the output is a single row containing values of aggregate
        // functions: aggregated results for non-empty input or initial values for empty input.
        1
      } else {
        // Here we set another upper bound for the number of output rows: it must not be larger than
        // child's number of rows.
        outputRows.min(childStats.rowCount.get)
      }

      val outputAttrStats = getOutputMap(childStats.attributeStats, agg.output)
      Some(Statistics(
        sizeInBytes = getOutputSize(agg.output, outputRows, outputAttrStats),
        rowCount = Some(outputRows),
        attributeStats = outputAttrStats,
        hints = childStats.hints))
    } else {
      None
    }
  }
}
