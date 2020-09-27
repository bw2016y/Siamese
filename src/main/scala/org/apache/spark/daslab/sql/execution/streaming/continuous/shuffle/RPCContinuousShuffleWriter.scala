package org.apache.spark.daslab.sql.execution.streaming.continuous.shuffle


import scala.concurrent.Future
import scala.concurrent.duration.Duration


import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow
//todo
import org.apache.spark.Partitioner
import org.apache.spark.rpc.RpcEndpointRef
import org.apache.spark.util.ThreadUtils

/**
 * A [[ContinuousShuffleWriter]] sending data to [[RPCContinuousShuffleReader]] instances.
 *
 * @param writerId The partition ID of this writer.
 * @param outputPartitioner The partitioner on the reader side of the shuffle.
 * @param endpoints The [[RPCContinuousShuffleReader]] endpoints to write to. Indexed by
 *                  partition ID within outputPartitioner.
 */
class RPCContinuousShuffleWriter(
                                  writerId: Int,
                                  outputPartitioner: Partitioner,
                                  endpoints: Array[RpcEndpointRef]) extends ContinuousShuffleWriter {

  if (outputPartitioner.numPartitions != 1) {
    throw new IllegalArgumentException("multiple readers not yet supported")
  }

  if (outputPartitioner.numPartitions != endpoints.length) {
    throw new IllegalArgumentException(s"partitioner size ${outputPartitioner.numPartitions} did " +
      s"not match endpoint count ${endpoints.length}")
  }

  def write(epoch: Iterator[UnsafeRow]): Unit = {
    while (epoch.hasNext) {
      val row = epoch.next()
      endpoints(outputPartitioner.getPartition(row)).askSync[Unit](ReceiverRow(writerId, row))
    }

    val futures = endpoints.map(_.ask[Unit](ReceiverEpochMarker(writerId))).toSeq
    implicit val ec = ThreadUtils.sameThread
    ThreadUtils.awaitResult(Future.sequence(futures), Duration.Inf)
  }
}
