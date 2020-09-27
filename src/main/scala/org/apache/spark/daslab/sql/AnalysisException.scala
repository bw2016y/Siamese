package org.apache.spark.daslab.sql




import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan

// todo spark-tags
import org.apache.spark.annotation.InterfaceStability


/**
 * Thrown when a query fails to analyze, usually because the query itself is invalid.
 *
 * @since 1.3.0
 */
@InterfaceStability.Stable
class AnalysisException protected[sql] (
                                         val message: String,
                                         val line: Option[Int] = None,
                                         val startPosition: Option[Int] = None,
                                         // Some plans fail to serialize due to bugs in scala collections.
                                         @transient val plan: Option[LogicalPlan] = None,
                                         val cause: Option[Throwable] = None)
  extends Exception(message, cause.orNull) with Serializable {

  def withPosition(line: Option[Int], startPosition: Option[Int]): AnalysisException = {
    val newException = new AnalysisException(message, line, startPosition)
    newException.setStackTrace(getStackTrace)
    newException
  }

  override def getMessage: String = {
    val planAnnotation = Option(plan).flatten.map(p => s";\n$p").getOrElse("")
    getSimpleMessage + planAnnotation
  }

  // Outputs an exception without the logical plan.
  // For testing only
  def getSimpleMessage: String = {
    val lineAnnotation = line.map(l => s" line $l").getOrElse("")
    val positionAnnotation = startPosition.map(p => s" pos $p").getOrElse("")
    s"$message;$lineAnnotation$positionAnnotation"
  }
}
