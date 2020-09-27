package org.apache.spark.daslab.sql.execution.streaming.sources

import org.apache.spark.daslab.sql.engine.InternalRow

import org.apache.spark.daslab.sql.sources.v2.writer.{DataSourceWriter, DataWriterFactory, WriterCommitMessage}
import org.apache.spark.daslab.sql.sources.v2.writer.streaming.StreamWriter

/**
 * A [[DataSourceWriter]] used to hook V2 stream writers into a microbatch plan. It implements
 * the non-streaming interface, forwarding the batch ID determined at construction to a wrapped
 * streaming writer.
 */
class MicroBatchWriter(batchId: Long, val writer: StreamWriter) extends DataSourceWriter {
  override def commit(messages: Array[WriterCommitMessage]): Unit = {
    writer.commit(batchId, messages)
  }

  override def abort(messages: Array[WriterCommitMessage]): Unit = writer.abort(batchId, messages)

  override def createWriterFactory(): DataWriterFactory[InternalRow] = writer.createWriterFactory()
}
