package org.apache.spark.daslab.sql.execution.streaming


import java.util.UUID

import org.apache.spark.daslab.sql.SparkSession
import org.apache.spark.daslab.sql.streaming.{StreamingQuery, StreamingQueryException, StreamingQueryProgress, StreamingQueryStatus}

/**
 * Wrap non-serializable StreamExecution to make the query serializable as it's easy for it to
 * get captured with normal usage. It's safe to capture the query but not use it in executors.
 * However, if the user tries to call its methods, it will throw `IllegalStateException`.
 */
class StreamingQueryWrapper(@transient private val _streamingQuery: StreamExecution)
  extends StreamingQuery with Serializable {

  def streamingQuery: StreamExecution = {
    /** Assert the codes run in the driver. */
    if (_streamingQuery == null) {
      throw new IllegalStateException("StreamingQuery cannot be used in executors")
    }
    _streamingQuery
  }

  override def name: String = {
    streamingQuery.name
  }

  override def id: UUID = {
    streamingQuery.id
  }

  override def runId: UUID = {
    streamingQuery.runId
  }

  override def awaitTermination(): Unit = {
    streamingQuery.awaitTermination()
  }

  override def awaitTermination(timeoutMs: Long): Boolean = {
    streamingQuery.awaitTermination(timeoutMs)
  }

  override def stop(): Unit = {
    streamingQuery.stop()
  }

  override def processAllAvailable(): Unit = {
    streamingQuery.processAllAvailable()
  }

  override def isActive: Boolean = {
    streamingQuery.isActive
  }

  override def lastProgress: StreamingQueryProgress = {
    streamingQuery.lastProgress
  }

  override def explain(): Unit = {
    streamingQuery.explain()
  }

  override def explain(extended: Boolean): Unit = {
    streamingQuery.explain(extended)
  }

  /**
   * This method is called in Python. Python cannot call "explain" directly as it outputs in the JVM
   * process, which may not be visible in Python process.
   */
  def explainInternal(extended: Boolean): String = {
    streamingQuery.explainInternal(extended)
  }

  override def sparkSession: SparkSession = {
    streamingQuery.sparkSession
  }

  override def recentProgress: Array[StreamingQueryProgress] = {
    streamingQuery.recentProgress
  }

  override def status: StreamingQueryStatus = {
    streamingQuery.status
  }

  override def exception: Option[StreamingQueryException] = {
    streamingQuery.exception
  }
}
