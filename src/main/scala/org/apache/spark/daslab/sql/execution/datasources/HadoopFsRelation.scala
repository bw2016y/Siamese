package org.apache.spark.daslab.sql.execution.datasources



import java.util.Locale

import scala.collection.mutable


import org.apache.spark.daslab.sql.{SparkSession, SQLContext}
import org.apache.spark.daslab.sql.engine.catalog.BucketSpec
import org.apache.spark.daslab.sql.execution.FileRelation
import org.apache.spark.daslab.sql.sources.{BaseRelation, DataSourceRegister}
import org.apache.spark.daslab.sql.types.{StructField, StructType}


/**
 * Acts as a container for all of the metadata required to read from a datasource. All discovery,
 * resolution and merging logic for schemas and partitions has been removed.
 *
 * @param location A [[FileIndex]] that can enumerate the locations of all the files that
 *                 comprise this relation.
 * @param partitionSchema The schema of the columns (if any) that are used to partition the relation
 * @param dataSchema The schema of any remaining columns.  Note that if any partition columns are
 *                   present in the actual data files as well, they are preserved.
 * @param bucketSpec Describes the bucketing (hash-partitioning of the files by some column values).
 * @param fileFormat A file format that can be used to read and write the data in files.
 * @param options Configuration used when reading / writing data.
 */
case class HadoopFsRelation(
                             location: FileIndex,
                             partitionSchema: StructType,
                             dataSchema: StructType,
                             bucketSpec: Option[BucketSpec],
                             fileFormat: FileFormat,
                             options: Map[String, String])(val sparkSession: SparkSession)
  extends BaseRelation with FileRelation {

  override def sqlContext: SQLContext = sparkSession.sqlContext

  private def getColName(f: StructField): String = {
    if (sparkSession.sessionState.conf.caseSensitiveAnalysis) {
      f.name
    } else {
      f.name.toLowerCase(Locale.ROOT)
    }
  }

  val overlappedPartCols = mutable.Map.empty[String, StructField]
  partitionSchema.foreach { partitionField =>
    if (dataSchema.exists(getColName(_) == getColName(partitionField))) {
      overlappedPartCols += getColName(partitionField) -> partitionField
    }
  }

  // When data and partition schemas have overlapping columns, the output
  // schema respects the order of the data schema for the overlapping columns, and it
  // respects the data types of the partition schema.
  val schema: StructType = {
    StructType(dataSchema.map(f => overlappedPartCols.getOrElse(getColName(f), f)) ++
      partitionSchema.filterNot(f => overlappedPartCols.contains(getColName(f))))
  }

  def partitionSchemaOption: Option[StructType] =
    if (partitionSchema.isEmpty) None else Some(partitionSchema)

  override def toString: String = {
    fileFormat match {
      case source: DataSourceRegister => source.shortName()
      case _ => "HadoopFiles"
    }
  }

  override def sizeInBytes: Long = {
    val compressionFactor = sqlContext.conf.fileCompressionFactor
    (location.sizeInBytes * compressionFactor).toLong
  }


  override def inputFiles: Array[String] = location.inputFiles
}
