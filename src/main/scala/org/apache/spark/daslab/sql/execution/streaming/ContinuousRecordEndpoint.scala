package org.apache.spark.daslab.sql.execution.streaming

//todo

import org.apache.spark.SparkEnv
import org.apache.spark.rpc.{RpcCallContext, RpcEnv, ThreadSafeRpcEndpoint}


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.sources.v2.reader.streaming.PartitionOffset

case class ContinuousRecordPartitionOffset(partitionId: Int, offset: Int) extends PartitionOffset
case class GetRecord(offset: ContinuousRecordPartitionOffset)

/**
 * A RPC end point for continuous readers to poll for
 * records from the driver.
 *
 * @param buckets the data buckets. Each bucket contains a sequence of items to be
 *                returned for a partition. The number of buckets should be equal to
 *                to the number of partitions.
 * @param lock a lock object for locking the buckets for read
 */
class ContinuousRecordEndpoint(buckets: Seq[Seq[Any]], lock: Object)
  extends ThreadSafeRpcEndpoint {

  private var startOffsets: Seq[Int] = List.fill(buckets.size)(0)

  /**
   * Sets the start offset.
   *
   * @param offsets the base offset per partition to be used
   *                while retrieving the data in {#receiveAndReply}.
   */
  def setStartOffsets(offsets: Seq[Int]): Unit = {
    lock.synchronized {
      startOffsets = offsets
    }
  }

  override val rpcEnv: RpcEnv = SparkEnv.get.rpcEnv

  /**
   * Process messages from `RpcEndpointRef.ask`. If receiving a unmatched message,
   * `SparkException` will be thrown and sent to `onError`.
   */
  override def receiveAndReply(context: RpcCallContext): PartialFunction[Any, Unit] = {
    case GetRecord(ContinuousRecordPartitionOffset(partitionId, offset)) =>
      lock.synchronized {
        val bufOffset = offset - startOffsets(partitionId)
        val buf = buckets(partitionId)
        val record = if (buf.size <= bufOffset) None else Some(buf(bufOffset))

        context.reply(record.map(InternalRow(_)))
      }
  }
}
