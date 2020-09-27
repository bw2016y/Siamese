package org.apache.spark.daslab.sql.execution.datasources


import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path


import org.apache.spark.daslab.sql.SparkSession
import org.apache.spark.daslab.sql.engine.catalog.CatalogTable
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.types.StructType


/**
 * A [[FileIndex]] for a metastore catalog table.
 *
 * @param sparkSession a [[SparkSession]]
 * @param table the metadata of the table
 * @param sizeInBytes the table's data size in bytes
 */
class CatalogFileIndex(
                        sparkSession: SparkSession,
                        val table: CatalogTable,
                        override val sizeInBytes: Long) extends FileIndex {

  protected val hadoopConf: Configuration = sparkSession.sessionState.newHadoopConf()

  /** Globally shared (not exclusive to this table) cache for file statuses to speed up listing. */
  private val fileStatusCache = FileStatusCache.getOrCreate(sparkSession)

  assert(table.identifier.database.isDefined,
    "The table identifier must be qualified in CatalogFileIndex")

  private val baseLocation: Option[URI] = table.storage.locationUri

  override def partitionSchema: StructType = table.partitionSchema

  override def rootPaths: Seq[Path] = baseLocation.map(new Path(_)).toSeq

  override def listFiles(
                          partitionFilters: Seq[Expression], dataFilters: Seq[Expression]): Seq[PartitionDirectory] = {
    filterPartitions(partitionFilters).listFiles(Nil, dataFilters)
  }

  override def refresh(): Unit = fileStatusCache.invalidateAll()

  /**
   * Returns a [[InMemoryFileIndex]] for this table restricted to the subset of partitions
   * specified by the given partition-pruning filters.
   *
   * @param filters partition-pruning filters
   */
  def filterPartitions(filters: Seq[Expression]): InMemoryFileIndex = {
    if (table.partitionColumnNames.nonEmpty) {
      val startTime = System.nanoTime()
      val selectedPartitions = sparkSession.sessionState.catalog.listPartitionsByFilter(
        table.identifier, filters)
      val partitions = selectedPartitions.map { p =>
        val path = new Path(p.location)
        val fs = path.getFileSystem(hadoopConf)
        PartitionPath(
          p.toRow(partitionSchema, sparkSession.sessionState.conf.sessionLocalTimeZone),
          path.makeQualified(fs.getUri, fs.getWorkingDirectory))
      }
      val partitionSpec = PartitionSpec(partitionSchema, partitions)
      val timeNs = System.nanoTime() - startTime
      new PrunedInMemoryFileIndex(
        sparkSession, new Path(baseLocation.get), fileStatusCache, partitionSpec, Option(timeNs))
    } else {
      new InMemoryFileIndex(
        sparkSession, rootPaths, table.storage.properties, userSpecifiedSchema = None)
    }
  }

  override def inputFiles: Array[String] = filterPartitions(Nil).inputFiles

  // `CatalogFileIndex` may be a member of `HadoopFsRelation`, `HadoopFsRelation` may be a member
  // of `LogicalRelation`, and `LogicalRelation` may be used as the cache key. So we need to
  // implement `equals` and `hashCode` here, to make it work with cache lookup.
  override def equals(o: Any): Boolean = o match {
    case other: CatalogFileIndex => this.table.identifier == other.table.identifier
    case _ => false
  }

  override def hashCode(): Int = table.identifier.hashCode()
}

/**
 * An override of the standard HDFS listing based catalog, that overrides the partition spec with
 * the information from the metastore.
 *
 * @param tableBasePath The default base path of the Hive metastore table
 * @param partitionSpec The partition specifications from Hive metastore
 */
private class PrunedInMemoryFileIndex(
                                       sparkSession: SparkSession,
                                       tableBasePath: Path,
                                       fileStatusCache: FileStatusCache,
                                       override val partitionSpec: PartitionSpec,
                                       override val metadataOpsTimeNs: Option[Long])
  extends InMemoryFileIndex(
    sparkSession,
    partitionSpec.partitions.map(_.path),
    Map.empty,
    Some(partitionSpec.partitionColumns),
    fileStatusCache)
