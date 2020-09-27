package org.apache.spark.daslab.sql.execution.streaming.continuous



import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration

import org.apache.spark.daslab.sql.streaming.{ProcessingTime, Trigger}

//todo
import org.apache.spark.annotation.{Experimental, InterfaceStability}
import org.apache.spark.unsafe.types.CalendarInterval

/**
 * A [[Trigger]] that continuously processes streaming data, asynchronously checkpointing at
 * the specified interval.
 */
@InterfaceStability.Evolving
case class ContinuousTrigger(intervalMs: Long) extends Trigger {
  require(intervalMs >= 0, "the interval of trigger should not be negative")
}

private[sql] object ContinuousTrigger {
  def apply(interval: String): ContinuousTrigger = {
    val cal = CalendarInterval.fromCaseInsensitiveString(interval)
    if (cal.months > 0) {
      throw new IllegalArgumentException(s"Doesn't support month or year interval: $interval")
    }
    new ContinuousTrigger(cal.microseconds / 1000)
  }

  def apply(interval: Duration): ContinuousTrigger = {
    ContinuousTrigger(interval.toMillis)
  }

  def create(interval: String): ContinuousTrigger = {
    apply(interval)
  }

  def create(interval: Long, unit: TimeUnit): ContinuousTrigger = {
    ContinuousTrigger(unit.toMillis(interval))
  }
}
