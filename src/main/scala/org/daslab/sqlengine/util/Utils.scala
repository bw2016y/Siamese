package org.daslab.sqlengine.util

import java.util.concurrent.atomic.AtomicBoolean
import org.apache.spark.SparkEnv
import org.apache.spark.internal.Logging

//TODO spark core utils
private[sqlengine] object Utils extends Logging{
  /**
   * The performance overhead of creating and logging strings for wide schemas can be large. To
   * limit the impact, we bound the number of fields to include by default. This can be overridden
   * by setting the 'spark.debug.maxToStringFields' conf in SparkEnv.
   */
  val DEFAULT_MAX_TO_STRING_FIELDS = 25


  private[spark] def maxNumToStringFields = {
    if (SparkEnv.get != null) {
      SparkEnv.get.conf.getInt("spark.debug.maxToStringFields", DEFAULT_MAX_TO_STRING_FIELDS)
    } else {
      DEFAULT_MAX_TO_STRING_FIELDS
    }
  }

  /** Whether we have warned about plan string truncation yet. */
  private val truncationWarningPrinted = new AtomicBoolean(false)
  /**
   * Format a sequence with semantics similar to calling .mkString(). Any elements beyond
   * maxNumToStringFields will be dropped and replaced by a "... N more fields" placeholder.
   *
   * @return the trimmed and formatted string.
   */
  def truncatedString[T](
                          seq: Seq[T],
                          start: String,
                          sep: String,
                          end: String,
                          maxNumFields: Int = maxNumToStringFields): String = {
    if (seq.length > maxNumFields) {
      if (truncationWarningPrinted.compareAndSet(false, true)) {
        logWarning(
          "Truncated the string representation of a plan since it was too large. This " +
            "behavior can be adjusted by setting 'spark.debug.maxToStringFields' in SparkEnv.conf.")
      }
      val numFields = math.max(0, maxNumFields - 1)
      seq.take(numFields).mkString(
        start, sep, sep + "... " + (seq.length - numFields) + " more fields" + end)
    } else {
      seq.mkString(start, sep, end)
    }
  }

}