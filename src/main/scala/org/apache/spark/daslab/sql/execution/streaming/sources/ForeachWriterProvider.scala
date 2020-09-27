package org.apache.spark.daslab.sql.execution.streaming.sources



import org.apache.spark.daslab.sql.{ForeachWriter, SparkSession}
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.encoders.ExpressionEncoder
import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow
import org.apache.spark.daslab.sql.execution.python.PythonForeachWriter
import org.apache.spark.daslab.sql.sources.v2.{DataSourceOptions, StreamWriteSupport}
import org.apache.spark.daslab.sql.sources.v2.writer.{DataWriter, DataWriterFactory, WriterCommitMessage}
import org.apache.spark.daslab.sql.sources.v2.writer.streaming.StreamWriter
import org.apache.spark.daslab.sql.streaming.OutputMode
import org.apache.spark.daslab.sql.types.StructType

//todo
import org.apache.spark.SparkException


/**
 * A [[org.apache.spark.daslab.sql.sources.v2.DataSourceV2]] for forwarding data into the specified
 * [[ForeachWriter]].
 *
 * @param writer The [[ForeachWriter]] to process all data.
 * @param converter An object to convert internal rows to target type T. Either it can be
 *                  a [[ExpressionEncoder]] or a direct converter function.
 * @tparam T The expected type of the sink.
 */
case class ForeachWriterProvider[T](
                                     writer: ForeachWriter[T],
                                     converter: Either[ExpressionEncoder[T], InternalRow => T]) extends StreamWriteSupport {

  override def createStreamWriter(
                                   queryId: String,
                                   schema: StructType,
                                   mode: OutputMode,
                                   options: DataSourceOptions): StreamWriter = {
    new StreamWriter {
      override def commit(epochId: Long, messages: Array[WriterCommitMessage]): Unit = {}
      override def abort(epochId: Long, messages: Array[WriterCommitMessage]): Unit = {}

      override def createWriterFactory(): DataWriterFactory[InternalRow] = {
        val rowConverter: InternalRow => T = converter match {
          case Left(enc) =>
            val boundEnc = enc.resolveAndBind(
              schema.toAttributes,
              SparkSession.getActiveSession.get.sessionState.analyzer)
            boundEnc.fromRow
          case Right(func) =>
            func
        }
        ForeachWriterFactory(writer, rowConverter)
      }

      override def toString: String = "ForeachSink"
    }
  }
}

object ForeachWriterProvider {
  def apply[T](
                writer: ForeachWriter[T],
                encoder: ExpressionEncoder[T]): ForeachWriterProvider[_] = {
    writer match {
      case pythonWriter: PythonForeachWriter =>
        new ForeachWriterProvider[UnsafeRow](
          pythonWriter, Right((x: InternalRow) => x.asInstanceOf[UnsafeRow]))
      case _ =>
        new ForeachWriterProvider[T](writer, Left(encoder))
    }
  }
}

case class ForeachWriterFactory[T](
                                    writer: ForeachWriter[T],
                                    rowConverter: InternalRow => T)
  extends DataWriterFactory[InternalRow] {
  override def createDataWriter(
                                 partitionId: Int,
                                 taskId: Long,
                                 epochId: Long): ForeachDataWriter[T] = {
    new ForeachDataWriter(writer, rowConverter, partitionId, epochId)
  }
}

/**
 * A [[DataWriter]] which writes data in this partition to a [[ForeachWriter]].
 *
 * @param writer The [[ForeachWriter]] to process all data.
 * @param rowConverter A function which can convert [[InternalRow]] to the required type [[T]]
 * @param partitionId
 * @param epochId
 * @tparam T The type expected by the writer.
 */
class ForeachDataWriter[T](
                            writer: ForeachWriter[T],
                            rowConverter: InternalRow => T,
                            partitionId: Int,
                            epochId: Long)
  extends DataWriter[InternalRow] {

  // If open returns false, we should skip writing rows.
  private val opened = writer.open(partitionId, epochId)
  private var closeCalled: Boolean = false

  override def write(record: InternalRow): Unit = {
    if (!opened) return

    try {
      writer.process(rowConverter(record))
    } catch {
      case t: Throwable =>
        closeWriter(t)
        throw t
    }
  }

  override def commit(): WriterCommitMessage = {
    closeWriter(null)
    ForeachWriterCommitMessage
  }

  override def abort(): Unit = {
    closeWriter(new SparkException("Foreach writer has been aborted due to a task failure"))
  }

  private def closeWriter(errorOrNull: Throwable): Unit = {
    if (!closeCalled) {
      closeCalled = true
      writer.close(errorOrNull)
    }
  }
}

/**
 * An empty [[WriterCommitMessage]]. [[ForeachWriter]] implementations have no global coordination.
 */
case object ForeachWriterCommitMessage extends WriterCommitMessage
