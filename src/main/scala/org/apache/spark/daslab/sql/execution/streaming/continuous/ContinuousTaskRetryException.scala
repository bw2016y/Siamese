package org.apache.spark.daslab.sql.execution.streaming.continuous


//todo
import org.apache.spark.SparkException

/**
 * An exception thrown when a continuous processing task runs with a nonzero attempt ID.
 */
class ContinuousTaskRetryException
  extends SparkException("Continuous execution does not support task retry", null)

