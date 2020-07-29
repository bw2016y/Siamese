package org.apache.spark.daslab.sql.execution.datasources.text



import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, Path}
import org.apache.hadoop.mapreduce.{Job, TaskAttemptContext}


import org.apache.spark.daslab.sql.{AnalysisException, SparkSession}
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow
import org.apache.spark.daslab.sql.engine.expressions.codegen.UnsafeRowWriter
import org.apache.spark.daslab.sql.engine.util.CompressionCodecs
import org.apache.spark.daslab.sql.execution.datasources._
import org.apache.spark.daslab.sql.sources._
import org.apache.spark.daslab.sql.types.{DataType, StringType, StructType}

//todo
import org.apache.spark.util.SerializableConfiguration
import org.apache.spark.TaskContext
import org.apache.spark.broadcast.Broadcast

/**
 * A data source for reading text files.
 */
class TextFileFormat extends TextBasedFileFormat with DataSourceRegister {

  override def shortName(): String = "text"

  override def toString: String = "Text"

  private def verifySchema(schema: StructType): Unit = {
    if (schema.size != 1) {
      throw new AnalysisException(
        s"Text data source supports only a single column, and you have ${schema.size} columns.")
    }
  }

  override def isSplitable(
                            sparkSession: SparkSession,
                            options: Map[String, String],
                            path: Path): Boolean = {
    val textOptions = new TextOptions(options)
    super.isSplitable(sparkSession, options, path) && !textOptions.wholeText
  }

  override def inferSchema(
                            sparkSession: SparkSession,
                            options: Map[String, String],
                            files: Seq[FileStatus]): Option[StructType] = Some(new StructType().add("value", StringType))

  override def prepareWrite(
                             sparkSession: SparkSession,
                             job: Job,
                             options: Map[String, String],
                             dataSchema: StructType): OutputWriterFactory = {
    verifySchema(dataSchema)

    val textOptions = new TextOptions(options)
    val conf = job.getConfiguration

    textOptions.compressionCodec.foreach { codec =>
      CompressionCodecs.setCodecConfiguration(conf, codec)
    }

    new OutputWriterFactory {
      override def newInstance(
                                path: String,
                                dataSchema: StructType,
                                context: TaskAttemptContext): OutputWriter = {
        new TextOutputWriter(path, dataSchema, textOptions.lineSeparatorInWrite, context)
      }

      override def getFileExtension(context: TaskAttemptContext): String = {
        ".txt" + CodecStreams.getCompressionExtension(context)
      }
    }
  }

  override def buildReader(
                            sparkSession: SparkSession,
                            dataSchema: StructType,
                            partitionSchema: StructType,
                            requiredSchema: StructType,
                            filters: Seq[Filter],
                            options: Map[String, String],
                            hadoopConf: Configuration): PartitionedFile => Iterator[InternalRow] = {
    assert(
      requiredSchema.length <= 1,
      "Text data source only produces a single data column named \"value\".")
    val textOptions = new TextOptions(options)
    val broadcastedHadoopConf =
      sparkSession.sparkContext.broadcast(new SerializableConfiguration(hadoopConf))

    readToUnsafeMem(broadcastedHadoopConf, requiredSchema, textOptions)
  }

  private def readToUnsafeMem(
                               conf: Broadcast[SerializableConfiguration],
                               requiredSchema: StructType,
                               textOptions: TextOptions): (PartitionedFile) => Iterator[UnsafeRow] = {

    (file: PartitionedFile) => {
      val confValue = conf.value.value
      val reader = if (!textOptions.wholeText) {
        new HadoopFileLinesReader(file, textOptions.lineSeparatorInRead, confValue)
      } else {
        new HadoopFileWholeTextReader(file, confValue)
      }
      Option(TaskContext.get()).foreach(_.addTaskCompletionListener[Unit](_ => reader.close()))
      if (requiredSchema.isEmpty) {
        val emptyUnsafeRow = new UnsafeRow(0)
        reader.map(_ => emptyUnsafeRow)
      } else {
        val unsafeRowWriter = new UnsafeRowWriter(1)

        reader.map { line =>
          // Writes to an UnsafeRow directly
          unsafeRowWriter.reset()
          unsafeRowWriter.write(0, line.getBytes, 0, line.getLength)
          unsafeRowWriter.getRow()
        }
      }
    }
  }

  override def supportDataType(dataType: DataType, isReadPath: Boolean): Boolean =
    dataType == StringType
}

class TextOutputWriter(
                        path: String,
                        dataSchema: StructType,
                        lineSeparator: Array[Byte],
                        context: TaskAttemptContext)
  extends OutputWriter {

  private val writer = CodecStreams.createOutputStream(context, new Path(path))

  override def write(row: InternalRow): Unit = {
    if (!row.isNullAt(0)) {
      val utf8string = row.getUTF8String(0)
      utf8string.writeTo(writer)
    }
    writer.write(lineSeparator)
  }

  override def close(): Unit = {
    writer.close()
  }
}
