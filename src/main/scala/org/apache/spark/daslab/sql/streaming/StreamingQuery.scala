package org.apache.spark.daslab.sql.streaming



import java.util.UUID

import org.apache.spark.daslab.sql.SparkSession

//todo
import org.apache.spark.annotation.InterfaceStability

/**
 * A handle to a query that is executing continuously in the background as new data arrives.
 * All these methods are thread-safe.
 * @since 2.0.0
 */
@InterfaceStability.Evolving
trait StreamingQuery {

  /**
   * Returns the user-specified name of the query, or null if not specified.
   * This name can be specified in the `org.apache.spark.daslab.sql.streaming.DataStreamWriter`
   * as `dataframe.writeStream.queryName("query").start()`.
   * This name, if set, must be unique across all active queries.
   *
   * @since 2.0.0
   */
  def name: String

  /**
   * Returns the unique id of this query that persists across restarts from checkpoint data.
   * That is, this id is generated when a query is started for the first time, and
   * will be the same every time it is restarted from checkpoint data. Also see [[runId]].
   *
   * @since 2.1.0
   */
  def id: UUID

  /**
   * Returns the unique id of this run of the query. That is, every start/restart of a query will
   * generated a unique runId. Therefore, every time a query is restarted from
   * checkpoint, it will have the same [[id]] but different [[runId]]s.
   */
  def runId: UUID

  /**
   * Returns the `SparkSession` associated with `this`.
   *
   * @since 2.0.0
   */
  def sparkSession: SparkSession

  /**
   * Returns `true` if this query is actively running.
   *
   * @since 2.0.0
   */
  def isActive: Boolean

  /**
   * Returns the [[StreamingQueryException]] if the query was terminated by an exception.
   * @since 2.0.0
   */
  def exception: Option[StreamingQueryException]

  /**
   * Returns the current status of the query.
   *
   * @since 2.0.2
   */
  def status: StreamingQueryStatus

  /**
   * Returns an array of the most recent [[StreamingQueryProgress]] updates for this query.
   * The number of progress updates retained for each stream is configured by Spark session
   * configuration `spark.sql.streaming.numRecentProgressUpdates`.
   *
   * @since 2.1.0
   */
  def recentProgress: Array[StreamingQueryProgress]

  /**
   * Returns the most recent [[StreamingQueryProgress]] update of this streaming query.
   *
   * @since 2.1.0
   */
  def lastProgress: StreamingQueryProgress

  /**
   * Waits for the termination of `this` query, either by `query.stop()` or by an exception.
   * If the query has terminated with an exception, then the exception will be thrown.
   *
   * If the query has terminated, then all subsequent calls to this method will either return
   * immediately (if the query was terminated by `stop()`), or throw the exception
   * immediately (if the query has terminated with exception).
   *
   * @throws StreamingQueryException if the query has terminated with an exception.
   *
   * @since 2.0.0
   */
  @throws[StreamingQueryException]
  def awaitTermination(): Unit

  /**
   * Waits for the termination of `this` query, either by `query.stop()` or by an exception.
   * If the query has terminated with an exception, then the exception will be thrown.
   * Otherwise, it returns whether the query has terminated or not within the `timeoutMs`
   * milliseconds.
   *
   * If the query has terminated, then all subsequent calls to this method will either return
   * `true` immediately (if the query was terminated by `stop()`), or throw the exception
   * immediately (if the query has terminated with exception).
   *
   * @throws StreamingQueryException if the query has terminated with an exception
   *
   * @since 2.0.0
   */
  @throws[StreamingQueryException]
  def awaitTermination(timeoutMs: Long): Boolean

  /**
   * Blocks until all available data in the source has been processed and committed to the sink.
   * This method is intended for testing. Note that in the case of continually arriving data, this
   * method may block forever. Additionally, this method is only guaranteed to block until data that
   * has been synchronously appended data to a `org.apache.spark.daslab.sql.execution.streaming.Source`
   * prior to invocation. (i.e. `getOffset` must immediately reflect the addition).
   * @since 2.0.0
   */
  def processAllAvailable(): Unit

  /**
   * Stops the execution of this query if it is running. This method blocks until the threads
   * performing execution has stopped.
   * @since 2.0.0
   */
  def stop(): Unit

  /**
   * Prints the physical plan to the console for debugging purposes.
   * @since 2.0.0
   */
  def explain(): Unit

  /**
   * Prints the physical plan to the console for debugging purposes.
   *
   * @param extended whether to do extended explain or not
   * @since 2.0.0
   */
  def explain(extended: Boolean): Unit
}
