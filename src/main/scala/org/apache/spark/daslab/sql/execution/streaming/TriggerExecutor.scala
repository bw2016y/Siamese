package org.apache.spark.daslab.sql.execution.streaming



import org.apache.spark.daslab.sql.streaming.ProcessingTime

//todo
import org.apache.spark.internal.Logging
import org.apache.spark.util.{Clock, SystemClock}

trait TriggerExecutor {

  /**
   * Execute batches using `batchRunner`. If `batchRunner` runs `false`, terminate the execution.
   */
  def execute(batchRunner: () => Boolean): Unit
}

/**
 * A trigger executor that runs a single batch only, then terminates.
 */
case class OneTimeExecutor() extends TriggerExecutor {

  /**
   * Execute a single batch using `batchRunner`.
   */
  override def execute(batchRunner: () => Boolean): Unit = batchRunner()
}

/**
 * A trigger executor that runs a batch every `intervalMs` milliseconds.
 */
case class ProcessingTimeExecutor(processingTime: ProcessingTime, clock: Clock = new SystemClock())
  extends TriggerExecutor with Logging {

  private val intervalMs = processingTime.intervalMs
  require(intervalMs >= 0)

  override def execute(triggerHandler: () => Boolean): Unit = {
    while (true) {
      val triggerTimeMs = clock.getTimeMillis
      val nextTriggerTimeMs = nextBatchTime(triggerTimeMs)
      val terminated = !triggerHandler()
      if (intervalMs > 0) {
        val batchElapsedTimeMs = clock.getTimeMillis - triggerTimeMs
        if (batchElapsedTimeMs > intervalMs) {
          notifyBatchFallingBehind(batchElapsedTimeMs)
        }
        if (terminated) {
          return
        }
        clock.waitTillTime(nextTriggerTimeMs)
      } else {
        if (terminated) {
          return
        }
      }
    }
  }

  /** Called when a batch falls behind */
  def notifyBatchFallingBehind(realElapsedTimeMs: Long): Unit = {
    logWarning("Current batch is falling behind. The trigger interval is " +
      s"${intervalMs} milliseconds, but spent ${realElapsedTimeMs} milliseconds")
  }

  /**
   * Returns the start time in milliseconds for the next batch interval, given the current time.
   * Note that a batch interval is inclusive with respect to its start time, and thus calling
   * `nextBatchTime` with the result of a previous call should return the next interval. (i.e. given
   * an interval of `100 ms`, `nextBatchTime(nextBatchTime(0)) = 200` rather than `0`).
   */
  def nextBatchTime(now: Long): Long = {
    if (intervalMs == 0) now else now / intervalMs * intervalMs + intervalMs
  }
}
