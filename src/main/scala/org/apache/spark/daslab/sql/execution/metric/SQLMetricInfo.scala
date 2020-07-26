package org.apache.spark.daslab.sql.execution.metric

//todo
import org.apache.spark.annotation.DeveloperApi

/**
 * :: DeveloperApi ::
 * Stores information about a SQL Metric.
 */
@DeveloperApi
class SQLMetricInfo(
                     val name: String,
                     val accumulatorId: Long,
                     val metricType: String)
