package org.apache.spark.daslab.sql.streaming


//todo
import org.apache.spark.annotation.InterfaceStability

/**
 * Exception that stopped a [[StreamingQuery]]. Use `cause` get the actual exception
 * that caused the failure.
 * @param message     Message of this exception
 * @param cause       Internal cause of this exception
 * @param startOffset Starting offset in json of the range of data in which exception occurred
 * @param endOffset   Ending offset in json of the range of data in exception occurred
 * @since 2.0.0
 */
@InterfaceStability.Evolving
class StreamingQueryException private[sql](
                                            private val queryDebugString: String,
                                            val message: String,
                                            val cause: Throwable,
                                            val startOffset: String,
                                            val endOffset: String)
  extends Exception(message, cause) {

  /** Time when the exception occurred */
  val time: Long = System.currentTimeMillis

  override def toString(): String =
    s"""${classOf[StreamingQueryException].getName}: ${cause.getMessage}
       |$queryDebugString""".stripMargin
}
