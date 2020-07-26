package org.apache.spark.daslab.sql.execution.streaming.sources



import org.apache.spark.daslab.sql.{Dataset, SparkSession}
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.plans.logical.LocalRelation
import org.apache.spark.daslab.sql.sources.v2.DataSourceOptions
import org.apache.spark.daslab.sql.sources.v2.writer.{DataWriterFactory, WriterCommitMessage}
import org.apache.spark.daslab.sql.sources.v2.writer.streaming.StreamWriter
import org.apache.spark.daslab.sql.types.StructType

//todo
import org.apache.spark.internal.Logging


/** Common methods used to create writes for the the console sink */
class ConsoleWriter(schema: StructType, options: DataSourceOptions)
  extends StreamWriter with Logging {

  // Number of rows to display, by default 20 rows
  protected val numRowsToShow = options.getInt("numRows", 20)

  // Truncate the displayed data if it is too long, by default it is true
  protected val isTruncated = options.getBoolean("truncate", true)

  assert(SparkSession.getActiveSession.isDefined)
  protected val spark = SparkSession.getActiveSession.get

  def createWriterFactory(): DataWriterFactory[InternalRow] = PackedRowWriterFactory

  override def commit(epochId: Long, messages: Array[WriterCommitMessage]): Unit = {
    // We have to print a "Batch" label for the epoch for compatibility with the pre-data source V2
    // behavior.
    printRows(messages, schema, s"Batch: $epochId")
  }

  def abort(epochId: Long, messages: Array[WriterCommitMessage]): Unit = {}

  protected def printRows(
                           commitMessages: Array[WriterCommitMessage],
                           schema: StructType,
                           printMessage: String): Unit = {
    val rows = commitMessages.collect {
      case PackedRowCommitMessage(rs) => rs
    }.flatten

    // scalastyle:off println
    println("-------------------------------------------")
    println(printMessage)
    println("-------------------------------------------")
    // scalastyle:off println
    Dataset.ofRows(spark, LocalRelation(schema.toAttributes, rows))
      .show(numRowsToShow, isTruncated)
  }

  override def toString(): String = {
    s"ConsoleWriter[numRows=$numRowsToShow, truncate=$isTruncated]"
  }
}
