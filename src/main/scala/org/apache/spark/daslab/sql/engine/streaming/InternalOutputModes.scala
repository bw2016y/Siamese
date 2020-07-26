package org.apache.spark.daslab.sql.engine.streaming



import java.util.Locale
import org.apache.spark.daslab.sql.streaming.OutputMode

/**
 * Internal helper class to generate objects representing various `OutputMode`s,
 */
private[sql] object InternalOutputModes {

  /**
   * OutputMode in which only the new rows in the streaming DataFrame/Dataset will be
   * written to the sink. This output mode can be only be used in queries that do not
   * contain any aggregation.
   */
  case object Append extends OutputMode

  /**
   * OutputMode in which all the rows in the streaming DataFrame/Dataset will be written
   * to the sink every time these is some updates. This output mode can only be used in queries
   * that contain aggregations.
   */
  case object Complete extends OutputMode

  /**
   * OutputMode in which only the rows in the streaming DataFrame/Dataset that were updated will be
   * written to the sink every time these is some updates. If the query doesn't contain
   * aggregations, it will be equivalent to `Append` mode.
   */
  case object Update extends OutputMode


  def apply(outputMode: String): OutputMode = {
    outputMode.toLowerCase(Locale.ROOT) match {
      case "append" =>
        OutputMode.Append
      case "complete" =>
        OutputMode.Complete
      case "update" =>
        OutputMode.Update
      case _ =>
        throw new IllegalArgumentException(s"Unknown output mode $outputMode. " +
          "Accepted output modes are 'append', 'complete', 'update'")
    }
  }
}
