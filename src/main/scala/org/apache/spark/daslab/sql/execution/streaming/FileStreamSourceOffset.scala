package org.apache.spark.daslab.sql.execution.streaming



import scala.util.control.Exception._

import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization

/**
 * Offset for the [[FileStreamSource]].
 *
 * @param logOffset  Position in the [[FileStreamSourceLog]]
 */
case class FileStreamSourceOffset(logOffset: Long) extends Offset {
  override def json: String = {
    Serialization.write(this)(FileStreamSourceOffset.format)
  }
}

object FileStreamSourceOffset {
  implicit val format = Serialization.formats(NoTypeHints)

  def apply(offset: Offset): FileStreamSourceOffset = {
    offset match {
      case f: FileStreamSourceOffset => f
      case SerializedOffset(str) =>
        catching(classOf[NumberFormatException]).opt {
          FileStreamSourceOffset(str.toLong)
        }.getOrElse {
          Serialization.read[FileStreamSourceOffset](str)
        }
      case _ =>
        throw new IllegalArgumentException(
          s"Invalid conversion from offset of ${offset.getClass} to FileStreamSourceOffset")
    }
  }
}


