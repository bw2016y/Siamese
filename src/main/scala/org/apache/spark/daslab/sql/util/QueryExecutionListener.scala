package org.apache.spark.daslab.sql.util



import java.util.concurrent.locks.ReentrantReadWriteLock

import scala.collection.mutable.ListBuffer
import scala.util.control.NonFatal


import org.apache.spark.daslab.sql.execution.QueryExecution
import org.apache.spark.daslab.sql.internal.StaticSQLConf._

//todo
import org.apache.spark.util.Utils
import org.apache.spark.SparkConf
import org.apache.spark.annotation.{DeveloperApi, Experimental, InterfaceStability}
import org.apache.spark.internal.Logging

/**
 * :: Experimental ::
 * The interface of query execution listener that can be used to analyze execution metrics.
 *
 * @note Implementations should guarantee thread-safety as they can be invoked by
 * multiple different threads.
 */
@Experimental
@InterfaceStability.Evolving
trait QueryExecutionListener {

  /**
   * A callback function that will be called when a query executed successfully.
   *
   * @param funcName name of the action that triggered this query.
   * @param qe the QueryExecution object that carries detail information like logical plan,
   *           physical plan, etc.
   * @param durationNs the execution time for this query in nanoseconds.
   *
   * @note This can be invoked by multiple different threads.
   */
  @DeveloperApi
  def onSuccess(funcName: String, qe: QueryExecution, durationNs: Long): Unit

  /**
   * A callback function that will be called when a query execution failed.
   *
   * @param funcName the name of the action that triggered this query.
   * @param qe the QueryExecution object that carries detail information like logical plan,
   *           physical plan, etc.
   * @param exception the exception that failed this query.
   *
   * @note This can be invoked by multiple different threads.
   */
  @DeveloperApi
  def onFailure(funcName: String, qe: QueryExecution, exception: Exception): Unit
}


/**
 * :: Experimental ::
 *
 * Manager for [[QueryExecutionListener]]. See `org.apache.spark.daslab.sql.SQLContext.listenerManager`.
 */
@Experimental
@InterfaceStability.Evolving
class ExecutionListenerManager private extends Logging {

  private[sql] def this(conf: SparkConf) = {
    this()
    conf.get(QUERY_EXECUTION_LISTENERS).foreach { classNames =>
      Utils.loadExtensions(classOf[QueryExecutionListener], classNames, conf).foreach(register)
    }
  }

  /**
   * Registers the specified [[QueryExecutionListener]].
   */
  @DeveloperApi
  def register(listener: QueryExecutionListener): Unit = writeLock {
    listeners += listener
  }

  /**
   * Unregisters the specified [[QueryExecutionListener]].
   */
  @DeveloperApi
  def unregister(listener: QueryExecutionListener): Unit = writeLock {
    listeners -= listener
  }

  /**
   * Removes all the registered [[QueryExecutionListener]].
   */
  @DeveloperApi
  def clear(): Unit = writeLock {
    listeners.clear()
  }

  /** Only exposed for testing. */
  private[sql] def listListeners(): Array[QueryExecutionListener] = listeners.toArray

  /**
   * Get an identical copy of this listener manager.
   */
  @DeveloperApi
  override def clone(): ExecutionListenerManager = writeLock {
    val newListenerManager = new ExecutionListenerManager
    listeners.foreach(newListenerManager.register)
    newListenerManager
  }

  private[sql] def onSuccess(funcName: String, qe: QueryExecution, duration: Long): Unit = {
    readLock {
      withErrorHandling { listener =>
        listener.onSuccess(funcName, qe, duration)
      }
    }
  }

  private[sql] def onFailure(funcName: String, qe: QueryExecution, exception: Exception): Unit = {
    readLock {
      withErrorHandling { listener =>
        listener.onFailure(funcName, qe, exception)
      }
    }
  }

  private[this] val listeners = ListBuffer.empty[QueryExecutionListener]

  /** A lock to prevent updating the list of listeners while we are traversing through them. */
  private[this] val lock = new ReentrantReadWriteLock()

  private def withErrorHandling(f: QueryExecutionListener => Unit): Unit = {
    for (listener <- listeners) {
      try {
        f(listener)
      } catch {
        case NonFatal(e) => logWarning("Error executing query execution listener", e)
      }
    }
  }

  /** Acquires a read lock on the cache for the duration of `f`. */
  private def readLock[A](f: => A): A = {
    val rl = lock.readLock()
    rl.lock()
    try f finally {
      rl.unlock()
    }
  }

  /** Acquires a write lock on the cache for the duration of `f`. */
  private def writeLock[A](f: => A): A = {
    val wl = lock.writeLock()
    wl.lock()
    try f finally {
      wl.unlock()
    }
  }
}
