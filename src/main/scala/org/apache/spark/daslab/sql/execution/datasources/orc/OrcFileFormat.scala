package org.apache.spark.daslab.sql.execution.datasources.orc


import java.io._
import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, Path}
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.mapreduce._
import org.apache.hadoop.mapreduce.lib.input.FileSplit
import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl

import org.apache.orc._
import org.apache.orc.OrcConf.{COMPRESS, MAPRED_OUTPUT_SCHEMA}
import org.apache.orc.mapred.OrcStruct
import org.apache.orc.mapreduce._

import org.apache.spark.daslab.sql.AnalysisException
import org.apache.spark.daslab.sql.SparkSession
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.expressions.codegen.GenerateUnsafeProjection
import org.apache.spark.daslab.sql.execution.datasources._
import org.apache.spark.daslab.sql.internal.SQLConf
import org.apache.spark.daslab.sql.sources._
import org.apache.spark.daslab.sql.types._


//todo
import org.apache.spark.TaskContext

import org.apache.spark.util.SerializableConfiguration

private[sql] object OrcFileFormat {
  private def checkFieldName(name: String): Unit = {
    try {
      TypeDescription.fromString(s"struct<$name:int>")
    } catch {
      case _: IllegalArgumentException =>
        throw new AnalysisException(
          s"""Column name "$name" contains invalid character(s).
             |Please use alias to rename it.
           """.stripMargin.split("\n").mkString(" ").trim)
    }
  }

  def checkFieldNames(names: Seq[String]): Unit = {
    names.foreach(checkFieldName)
  }

  def getQuotedSchemaString(dataType: DataType): String = dataType match {
    case _: AtomicType => dataType.catalogString
    case StructType(fields) =>
      fields.map(f => s"`${f.name}`:${getQuotedSchemaString(f.dataType)}")
        .mkString("struct<", ",", ">")
    case ArrayType(elementType, _) =>
      s"array<${getQuotedSchemaString(elementType)}>"
    case MapType(keyType, valueType, _) =>
      s"map<${getQuotedSchemaString(keyType)},${getQuotedSchemaString(valueType)}>"
    case _ => // UDT and others
      dataType.catalogString
  }
}

/**
 * New ORC File Format based on Apache ORC.
 */
class OrcFileFormat
  extends FileFormat
    with DataSourceRegister
    with Serializable {

  override def shortName(): String = "orc"

  override def toString: String = "ORC"

  override def hashCode(): Int = getClass.hashCode()

  override def equals(other: Any): Boolean = other.isInstanceOf[OrcFileFormat]

  override def inferSchema(
                            sparkSession: SparkSession,
                            options: Map[String, String],
                            files: Seq[FileStatus]): Option[StructType] = {
    OrcUtils.readSchema(sparkSession, files)
  }

  override def prepareWrite(
                             sparkSession: SparkSession,
                             job: Job,
                             options: Map[String, String],
                             dataSchema: StructType): OutputWriterFactory = {
    val orcOptions = new OrcOptions(options, sparkSession.sessionState.conf)

    val conf = job.getConfiguration

    conf.set(MAPRED_OUTPUT_SCHEMA.getAttribute, OrcFileFormat.getQuotedSchemaString(dataSchema))

    conf.set(COMPRESS.getAttribute, orcOptions.compressionCodec)

    conf.asInstanceOf[JobConf]
      .setOutputFormat(classOf[org.apache.orc.mapred.OrcOutputFormat[OrcStruct]])

    new OutputWriterFactory {
      override def newInstance(
                                path: String,
                                dataSchema: StructType,
                                context: TaskAttemptContext): OutputWriter = {
        new OrcOutputWriter(path, dataSchema, context)
      }

      override def getFileExtension(context: TaskAttemptContext): String = {
        val compressionExtension: String = {
          val name = context.getConfiguration.get(COMPRESS.getAttribute)
          OrcUtils.extensionsForCompressionCodecNames.getOrElse(name, "")
        }

        compressionExtension + ".orc"
      }
    }
  }

  override def supportBatch(sparkSession: SparkSession, schema: StructType): Boolean = {
    val conf = sparkSession.sessionState.conf
    conf.orcVectorizedReaderEnabled && conf.wholeStageEnabled &&
      schema.length <= conf.wholeStageMaxNumFields &&
      schema.forall(_.dataType.isInstanceOf[AtomicType])
  }

  override def isSplitable(
                            sparkSession: SparkSession,
                            options: Map[String, String],
                            path: Path): Boolean = {
    true
  }

  override def buildReaderWithPartitionValues(
                                               sparkSession: SparkSession,
                                               dataSchema: StructType,
                                               partitionSchema: StructType,
                                               requiredSchema: StructType,
                                               filters: Seq[Filter],
                                               options: Map[String, String],
                                               hadoopConf: Configuration): (PartitionedFile) => Iterator[InternalRow] = {
    if (sparkSession.sessionState.conf.orcFilterPushDown) {
      OrcFilters.createFilter(dataSchema, filters).foreach { f =>
        OrcInputFormat.setSearchArgument(hadoopConf, f, dataSchema.fieldNames)
      }
    }

    val resultSchema = StructType(requiredSchema.fields ++ partitionSchema.fields)
    val sqlConf = sparkSession.sessionState.conf
    val enableOffHeapColumnVector = sqlConf.offHeapColumnVectorEnabled
    val enableVectorizedReader = supportBatch(sparkSession, resultSchema)
    val capacity = sqlConf.orcVectorizedReaderBatchSize
    val copyToSpark = sparkSession.sessionState.conf.getConf(SQLConf.ORC_COPY_BATCH_TO_SPARK)

    val broadcastedConf =
      sparkSession.sparkContext.broadcast(new SerializableConfiguration(hadoopConf))
    val isCaseSensitive = sparkSession.sessionState.conf.caseSensitiveAnalysis

    (file: PartitionedFile) => {
      val conf = broadcastedConf.value.value

      val filePath = new Path(new URI(file.filePath))

      val fs = filePath.getFileSystem(conf)
      val readerOptions = OrcFile.readerOptions(conf).filesystem(fs)
      val reader = OrcFile.createReader(filePath, readerOptions)

      val requestedColIdsOrEmptyFile = OrcUtils.requestedColumnIds(
        isCaseSensitive, dataSchema, requiredSchema, reader, conf)

      if (requestedColIdsOrEmptyFile.isEmpty) {
        Iterator.empty
      } else {
        val requestedColIds = requestedColIdsOrEmptyFile.get
        assert(requestedColIds.length == requiredSchema.length,
          "[BUG] requested column IDs do not match required schema")
        val taskConf = new Configuration(conf)
        taskConf.set(OrcConf.INCLUDE_COLUMNS.getAttribute,
          requestedColIds.filter(_ != -1).sorted.mkString(","))

        val fileSplit = new FileSplit(filePath, file.start, file.length, Array.empty)
        val attemptId = new TaskAttemptID(new TaskID(new JobID(), TaskType.MAP, 0), 0)
        val taskAttemptContext = new TaskAttemptContextImpl(taskConf, attemptId)

        val taskContext = Option(TaskContext.get())
        if (enableVectorizedReader) {
          val batchReader = new OrcColumnarBatchReader(
            enableOffHeapColumnVector && taskContext.isDefined, copyToSpark, capacity)
          // SPARK-23399 Register a task completion listener first to call `close()` in all cases.
          // There is a possibility that `initialize` and `initBatch` hit some errors (like OOM)
          // after opening a file.
          val iter = new RecordReaderIterator(batchReader)
          Option(TaskContext.get()).foreach(_.addTaskCompletionListener[Unit](_ => iter.close()))

          batchReader.initialize(fileSplit, taskAttemptContext)
          batchReader.initBatch(
            reader.getSchema,
            requestedColIds,
            requiredSchema.fields,
            partitionSchema,
            file.partitionValues)

          iter.asInstanceOf[Iterator[InternalRow]]
        } else {
          val orcRecordReader = new OrcInputFormat[OrcStruct]
            .createRecordReader(fileSplit, taskAttemptContext)
          val iter = new RecordReaderIterator[OrcStruct](orcRecordReader)
          Option(TaskContext.get()).foreach(_.addTaskCompletionListener[Unit](_ => iter.close()))

          val fullSchema = requiredSchema.toAttributes ++ partitionSchema.toAttributes
          val unsafeProjection = GenerateUnsafeProjection.generate(fullSchema, fullSchema)
          val deserializer = new OrcDeserializer(dataSchema, requiredSchema, requestedColIds)

          if (partitionSchema.length == 0) {
            iter.map(value => unsafeProjection(deserializer.deserialize(value)))
          } else {
            val joinedRow = new JoinedRow()
            iter.map(value =>
              unsafeProjection(joinedRow(deserializer.deserialize(value), file.partitionValues)))
          }
        }
      }
    }
  }

  override def supportDataType(dataType: DataType, isReadPath: Boolean): Boolean = dataType match {
    case _: AtomicType => true

    case st: StructType => st.forall { f => supportDataType(f.dataType, isReadPath) }

    case ArrayType(elementType, _) => supportDataType(elementType, isReadPath)

    case MapType(keyType, valueType, _) =>
      supportDataType(keyType, isReadPath) && supportDataType(valueType, isReadPath)

    case udt: UserDefinedType[_] => supportDataType(udt.sqlType, isReadPath)

    case _: NullType => isReadPath

    case _ => false
  }
}
