package org.apache.spark.daslab.sql.execution.datasources

import org.apache.hadoop.fs.Path
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization

import org.apache.spark.daslab.sql.AnalysisException
import org.apache.spark.daslab.sql.types._


object DataSourceUtils {

  /**
   * Verify if the schema is supported in datasource in write path.
   */
  def verifyWriteSchema(format: FileFormat, schema: StructType): Unit = {
    verifySchema(format, schema, isReadPath = false)
  }

  /**
   * Verify if the schema is supported in datasource in read path.
   */
  def verifyReadSchema(format: FileFormat, schema: StructType): Unit = {
    verifySchema(format, schema, isReadPath = true)
  }

  /**
   * The key to use for storing partitionBy columns as options.
   */
  val PARTITIONING_COLUMNS_KEY = "__partition_columns"

  /**
   * Utility methods for converting partitionBy columns to options and back.
   */
  private implicit val formats = Serialization.formats(NoTypeHints)

  def encodePartitioningColumns(columns: Seq[String]): String = {
    Serialization.write(columns)
  }

  def decodePartitioningColumns(str: String): Seq[String] = {
    Serialization.read[Seq[String]](str)
  }

  /**
   * Verify if the schema is supported in datasource. This verification should be done
   * in a driver side.
   */
  private def verifySchema(format: FileFormat, schema: StructType, isReadPath: Boolean): Unit = {
    schema.foreach { field =>
      if (!format.supportDataType(field.dataType, isReadPath)) {
        throw new AnalysisException(
          s"$format data source does not support ${field.dataType.catalogString} data type.")
      }
    }
  }

  // SPARK-24626: Metadata files and temporary files should not be
  // counted as data files, so that they shouldn't participate in tasks like
  // location size calculation.
  private[sql] def isDataPath(path: Path): Boolean = {
    val name = path.getName
    !(name.startsWith("_") || name.startsWith("."))
  }
}
