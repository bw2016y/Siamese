package org.apache.spark.daslab.sql.execution.streaming.sources


import scala.collection.mutable

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.sources.v2.writer.{DataSourceWriter, DataWriter, DataWriterFactory, WriterCommitMessage}


//todo
import org.apache.spark.internal.Logging


/**
 * A simple [[DataWriterFactory]] whose tasks just pack rows into the commit message for delivery
 * to a [[DataSourceWriter]] on the driver.
 *
 * Note that, because it sends all rows to the driver, this factory will generally be unsuitable
 * for production-quality sinks. It's intended for use in tests.
 */
case object PackedRowWriterFactory extends DataWriterFactory[InternalRow] {
  override def createDataWriter(
                                 partitionId: Int,
                                 taskId: Long,
                                 epochId: Long): DataWriter[InternalRow] = {
    new PackedRowDataWriter()
  }
}

/**
 * Commit message for a [[PackedRowDataWriter]], containing all the rows written in the most
 * recent interval.
 */
case class PackedRowCommitMessage(rows: Array[InternalRow]) extends WriterCommitMessage

/**
 * A simple [[DataWriter]] that just sends all the rows it's received as a commit message.
 */
class PackedRowDataWriter() extends DataWriter[InternalRow] with Logging {
  private val data = mutable.Buffer[InternalRow]()

  // Spark reuses the same `InternalRow` instance, here we copy it before buffer it.
  override def write(row: InternalRow): Unit = data.append(row.copy())

  override def commit(): PackedRowCommitMessage = {
    val msg = PackedRowCommitMessage(data.toArray)
    data.clear()
    msg
  }

  override def abort(): Unit = data.clear()
}
