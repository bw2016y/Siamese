package org.apache.spark.daslab.sql.execution.datasources.parquet

import org.apache.hadoop.fs.Path
import org.apache.hadoop.mapreduce._
import org.apache.parquet.hadoop.ParquetOutputFormat

import org.apache.spark.daslab.sql.Row
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.execution.datasources.OutputWriter

// NOTE: This class is instantiated and used on executor side only, no need to be serializable.
private[parquet] class ParquetOutputWriter(path: String, context: TaskAttemptContext)
  extends OutputWriter {

  private val recordWriter: RecordWriter[Void, InternalRow] = {
    new ParquetOutputFormat[InternalRow]() {
      override def getDefaultWorkFile(context: TaskAttemptContext, extension: String): Path = {
        new Path(path)
      }
    }.getRecordWriter(context)
  }

  override def write(row: InternalRow): Unit = recordWriter.write(null, row)

  override def close(): Unit = recordWriter.close(context)
}
