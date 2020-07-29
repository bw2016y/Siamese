package org.apache.spark.daslab.sql.execution.datasources


import org.apache.hadoop.fs._

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.types.StructType

/**
 * A collection of data files from a partitioned relation, along with the partition values in the
 * form of an [[InternalRow]].
 */
case class PartitionDirectory(values: InternalRow, files: Seq[FileStatus])

/**
 * An interface for objects capable of enumerating the root paths of a relation as well as the
 * partitions of a relation subject to some pruning expressions.
 */
trait FileIndex {

  /**
   * Returns the list of root input paths from which the catalog will get files. There may be a
   * single root path from which partitions are discovered, or individual partitions may be
   * specified by each path.
   */
  def rootPaths: Seq[Path]

  /**
   * Returns all valid files grouped into partitions when the data is partitioned. If the data is
   * unpartitioned, this will return a single partition with no partition values.
   *
   * @param partitionFilters The filters used to prune which partitions are returned. These filters
   *                         must only refer to partition columns and this method will only return
   *                         files where these predicates are guaranteed to evaluate to `true`.
   *                         Thus, these filters will not need to be evaluated again on the
   *                         returned data.
   * @param dataFilters Filters that can be applied on non-partitioned columns. The implementation
   *                    does not need to guarantee these filters are applied, i.e. the execution
   *                    engine will ensure these filters are still applied on the returned files.
   */
  def listFiles(
                 partitionFilters: Seq[Expression], dataFilters: Seq[Expression]): Seq[PartitionDirectory]

  /**
   * Returns the list of files that will be read when scanning this relation. This call may be
   * very expensive for large tables.
   */
  def inputFiles: Array[String]

  /** Refresh any cached file listings */
  def refresh(): Unit

  /** Sum of table file sizes, in bytes */
  def sizeInBytes: Long

  /** Schema of the partitioning columns, or the empty schema if the table is not partitioned. */
  def partitionSchema: StructType

  /**
   * Returns an optional metadata operation time, in nanoseconds, for listing files.
   *
   * We do file listing in query optimization (in order to get the proper statistics) and we want
   * to account for file listing time in physical execution (as metrics). To do that, we save the
   * file listing time in some implementations and physical execution calls it in this method
   * to update the metrics.
   */
  def metadataOpsTimeNs: Option[Long] = None
}
