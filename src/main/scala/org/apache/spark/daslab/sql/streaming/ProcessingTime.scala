package org.apache.spark.daslab.sql.streaming



import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration


//todo
import org.apache.spark.annotation.InterfaceStability
import org.apache.spark.unsafe.types.CalendarInterval

/**
 * A trigger that runs a query periodically based on the processing time. If `interval` is 0,
 * the query will run as fast as possible.
 *
 * Scala Example:
 * {{{
 *   df.writeStream.trigger(ProcessingTime("10 seconds"))
 *
 *   import scala.concurrent.duration._
 *   df.writeStream.trigger(ProcessingTime(10.seconds))
 * }}}
 *
 * Java Example:
 * {{{
 *   df.writeStream.trigger(ProcessingTime.create("10 seconds"))
 *
 *   import java.util.concurrent.TimeUnit
 *   df.writeStream.trigger(ProcessingTime.create(10, TimeUnit.SECONDS))
 * }}}
 *
 * @since 2.0.0
 */
@InterfaceStability.Evolving
@deprecated("use Trigger.ProcessingTime(intervalMs)", "2.2.0")
case class ProcessingTime(intervalMs: Long) extends Trigger {
  require(intervalMs >= 0, "the interval of trigger should not be negative")
}

/**
 * Used to create [[ProcessingTime]] triggers for [[StreamingQuery]]s.
 *
 * @since 2.0.0
 */
@InterfaceStability.Evolving
@deprecated("use Trigger.ProcessingTime(intervalMs)", "2.2.0")
object ProcessingTime {

  /**
   * Create a [[ProcessingTime]]. If `interval` is 0, the query will run as fast as possible.
   *
   * Example:
   * {{{
   *   df.writeStream.trigger(ProcessingTime("10 seconds"))
   * }}}
   *
   * @since 2.0.0
   * @deprecated use Trigger.ProcessingTime(interval)
   */
  @deprecated("use Trigger.ProcessingTime(interval)", "2.2.0")
  def apply(interval: String): ProcessingTime = {
    val cal = CalendarInterval.fromCaseInsensitiveString(interval)
    if (cal.months > 0) {
      throw new IllegalArgumentException(s"Doesn't support month or year interval: $interval")
    }
    new ProcessingTime(cal.microseconds / 1000)
  }

  /**
   * Create a [[ProcessingTime]]. If `interval` is 0, the query will run as fast as possible.
   *
   * Example:
   * {{{
   *   import scala.concurrent.duration._
   *   df.writeStream.trigger(ProcessingTime(10.seconds))
   * }}}
   *
   * @since 2.0.0
   * @deprecated use Trigger.ProcessingTime(interval)
   */
  @deprecated("use Trigger.ProcessingTime(interval)", "2.2.0")
  def apply(interval: Duration): ProcessingTime = {
    new ProcessingTime(interval.toMillis)
  }

  /**
   * Create a [[ProcessingTime]]. If `interval` is 0, the query will run as fast as possible.
   *
   * Example:
   * {{{
   *   df.writeStream.trigger(ProcessingTime.create("10 seconds"))
   * }}}
   *
   * @since 2.0.0
   * @deprecated use Trigger.ProcessingTime(interval)
   */
  @deprecated("use Trigger.ProcessingTime(interval)", "2.2.0")
  def create(interval: String): ProcessingTime = {
    apply(interval)
  }

  /**
   * Create a [[ProcessingTime]]. If `interval` is 0, the query will run as fast as possible.
   *
   * Example:
   * {{{
   *   import java.util.concurrent.TimeUnit
   *   df.writeStream.trigger(ProcessingTime.create(10, TimeUnit.SECONDS))
   * }}}
   *
   * @since 2.0.0
   * @deprecated use Trigger.ProcessingTime(interval, unit)
   */
  @deprecated("use Trigger.ProcessingTime(interval, unit)", "2.2.0")
  def create(interval: Long, unit: TimeUnit): ProcessingTime = {
    new ProcessingTime(unit.toMillis(interval))
  }
}
