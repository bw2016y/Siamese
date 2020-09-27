package org.apache.spark.daslab.sql.execution.datasources.orc


import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.mapreduce.TaskAttemptContext
import org.apache.orc.mapred.OrcStruct
import org.apache.orc.mapreduce.OrcOutputFormat



import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.execution.datasources.OutputWriter
import org.apache.spark.daslab.sql.types._

private[orc] class OrcOutputWriter(
                                    path: String,
                                    dataSchema: StructType,
                                    context: TaskAttemptContext)
  extends OutputWriter {

  private[this] val serializer = new OrcSerializer(dataSchema)

  private val recordWriter = {
    new OrcOutputFormat[OrcStruct]() {
      override def getDefaultWorkFile(context: TaskAttemptContext, extension: String): Path = {
        new Path(path)
      }
    }.getRecordWriter(context)
  }

  override def write(row: InternalRow): Unit = {
    recordWriter.write(NullWritable.get(), serializer.serialize(row))
  }

  override def close(): Unit = {
    recordWriter.close(context)
  }
}

