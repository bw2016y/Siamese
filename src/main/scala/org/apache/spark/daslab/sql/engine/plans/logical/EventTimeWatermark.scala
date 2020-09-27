package org.apache.spark.daslab.sql.engine.plans.logical

import org.apache.spark.daslab.sql.engine.expressions.Attribute
import org.apache.spark.daslab.sql.types.MetadataBuilder

//todo
import org.apache.spark.unsafe.types.CalendarInterval

object EventTimeWatermark {
  /** The [[org.apache.spark.daslab.sql.types.Metadata]] key used to hold the eventTime watermark delay. */
  val delayKey = "spark.watermarkDelayMs"

  def getDelayMs(delay: CalendarInterval): Long = {
    // We define month as `31 days` to simplify calculation.
    val millisPerMonth = CalendarInterval.MICROS_PER_DAY / 1000 * 31
    delay.milliseconds + delay.months * millisPerMonth
  }
}

/**
 * Used to mark a user specified column as holding the event time for a row.
 */
case class EventTimeWatermark(
                               eventTime: Attribute,
                               delay: CalendarInterval,
                               child: LogicalPlan) extends UnaryNode {

  // Update the metadata on the eventTime column to include the desired delay.
  override val output: Seq[Attribute] = child.output.map { a =>
    if (a semanticEquals eventTime) {
      val delayMs = EventTimeWatermark.getDelayMs(delay)
      val updatedMetadata = new MetadataBuilder()
        .withMetadata(a.metadata)
        .putLong(EventTimeWatermark.delayKey, delayMs)
        .build()
      a.withMetadata(updatedMetadata)
    } else if (a.metadata.contains(EventTimeWatermark.delayKey)) {
      // Remove existing watermark
      val updatedMetadata = new MetadataBuilder()
        .withMetadata(a.metadata)
        .remove(EventTimeWatermark.delayKey)
        .build()
      a.withMetadata(updatedMetadata)
    } else {
      a
    }
  }
}
