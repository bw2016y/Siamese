package org.apache.spark.daslab.sql.streaming


import org.json4s._
import org.json4s.JsonAST.JValue
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._



//todo
import org.apache.spark.annotation.InterfaceStability

/**
 * Reports information about the instantaneous status of a streaming query.
 *
 * @param message A human readable description of what the stream is currently doing.
 * @param isDataAvailable True when there is new data to be processed.
 * @param isTriggerActive True when the trigger is actively firing, false when waiting for the
 *                        next trigger time.
 *
 * @since 2.1.0
 */
@InterfaceStability.Evolving
class StreamingQueryStatus protected[sql](
                                           val message: String,
                                           val isDataAvailable: Boolean,
                                           val isTriggerActive: Boolean) extends Serializable {

  /** The compact JSON representation of this status. */
  def json: String = compact(render(jsonValue))

  /** The pretty (i.e. indented) JSON representation of this status. */
  def prettyJson: String = pretty(render(jsonValue))

  override def toString: String = prettyJson

  private[sql] def copy(
                         message: String = this.message,
                         isDataAvailable: Boolean = this.isDataAvailable,
                         isTriggerActive: Boolean = this.isTriggerActive): StreamingQueryStatus = {
    new StreamingQueryStatus(
      message = message,
      isDataAvailable = isDataAvailable,
      isTriggerActive = isTriggerActive)
  }

  private[sql] def jsonValue: JValue = {
    ("message" -> JString(message.toString)) ~
      ("isDataAvailable" -> JBool(isDataAvailable)) ~
      ("isTriggerActive" -> JBool(isTriggerActive))
  }
}
