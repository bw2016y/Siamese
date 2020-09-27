package org.apache.spark.daslab.sql.execution.streaming



import org.apache.spark.daslab.sql.DataFrame

/**
 * An interface for systems that can collect the results of a streaming query. In order to preserve
 * exactly once semantics a sink must be idempotent in the face of multiple attempts to add the same
 * batch.
 */
trait Sink extends BaseStreamingSink {

  /**
   * Adds a batch of data to this sink. The data for a given `batchId` is deterministic and if
   * this method is called more than once with the same batchId (which will happen in the case of
   * failures), then `data` should only be added once.
   *
   * Note 1: You cannot apply any operators on `data` except consuming it (e.g., `collect/foreach`).
   * Otherwise, you may get a wrong result.
   *
   * Note 2: The method is supposed to be executed synchronously, i.e. the method should only return
   * after data is consumed by sink successfully.
   */
  def addBatch(batchId: Long, data: DataFrame): Unit
}
