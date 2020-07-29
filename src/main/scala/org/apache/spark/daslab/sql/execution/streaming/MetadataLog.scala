package org.apache.spark.daslab.sql.execution.streaming



/**
 * A general MetadataLog that supports the following features:
 *
 *  - Allow the user to store a metadata object for each batch.
 *  - Allow the user to query the latest batch id.
 *  - Allow the user to query the metadata object of a specified batch id.
 *  - Allow the user to query metadata objects in a range of batch ids.
 *  - Allow the user to remove obsolete metadata
 */
trait MetadataLog[T] {

  /**
   * Store the metadata for the specified batchId and return `true` if successful. If the batchId's
   * metadata has already been stored, this method will return `false`.
   */
  def add(batchId: Long, metadata: T): Boolean

  /**
   * Return the metadata for the specified batchId if it's stored. Otherwise, return None.
   */
  def get(batchId: Long): Option[T]

  /**
   * Return metadata for batches between startId (inclusive) and endId (inclusive). If `startId` is
   * `None`, just return all batches before endId (inclusive).
   */
  def get(startId: Option[Long], endId: Option[Long]): Array[(Long, T)]

  /**
   * Return the latest batch Id and its metadata if exist.
   */
  def getLatest(): Option[(Long, T)]

  /**
   * Removes all the log entry earlier than thresholdBatchId (exclusive).
   * This operation should be idempotent.
   */
  def purge(thresholdBatchId: Long): Unit
}

