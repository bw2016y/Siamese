package org.apache.spark.daslab.sql.execution.streaming.continuous


import scala.collection.mutable


import org.apache.spark.daslab.sql.SparkSession
import org.apache.spark.daslab.sql.sources.v2.reader.streaming.{ContinuousReader, PartitionOffset}
import org.apache.spark.daslab.sql.sources.v2.writer.WriterCommitMessage
import org.apache.spark.daslab.sql.sources.v2.writer.streaming.StreamWriter

//todo
import org.apache.spark.util.RpcUtils
import org.apache.spark.SparkEnv
import org.apache.spark.internal.Logging
import org.apache.spark.rpc.{RpcCallContext, RpcEndpointRef, RpcEnv, ThreadSafeRpcEndpoint}

private[continuous] sealed trait EpochCoordinatorMessage extends Serializable

// Driver epoch trigger message
/**
 * Atomically increment the current epoch and get the new value.
 */
private[sql] case object IncrementAndGetEpoch extends EpochCoordinatorMessage

/**
 * The RpcEndpoint stop() will wait to clear out the message queue before terminating the
 * object. This can lead to a race condition where the query restarts at epoch n, a new
 * EpochCoordinator starts at epoch n, and then the old epoch coordinator commits epoch n + 1.
 * The framework doesn't provide a handle to wait on the message queue, so we use a synchronous
 * message to stop any writes to the ContinuousExecution object.
 */
private[sql] case object StopContinuousExecutionWrites extends EpochCoordinatorMessage

// Init messages
/**
 * Set the reader and writer partition counts. Tasks may not be started until the coordinator
 * has acknowledged these messages.
 */
private[sql] case class SetReaderPartitions(numPartitions: Int) extends EpochCoordinatorMessage
case class SetWriterPartitions(numPartitions: Int) extends EpochCoordinatorMessage

// Partition task messages
/**
 * Get the current epoch.
 */
private[sql] case object GetCurrentEpoch extends EpochCoordinatorMessage
/**
 * Commit a partition at the specified epoch with the given message.
 */
private[sql] case class CommitPartitionEpoch(
                                              partitionId: Int,
                                              epoch: Long,
                                              message: WriterCommitMessage) extends EpochCoordinatorMessage
/**
 * Report that a partition is ending the specified epoch at the specified offset.
 */
private[sql] case class ReportPartitionOffset(
                                               partitionId: Int,
                                               epoch: Long,
                                               offset: PartitionOffset) extends EpochCoordinatorMessage


/** Helper object used to create reference to [[EpochCoordinator]]. */
private[sql] object EpochCoordinatorRef extends Logging {
  private def endpointName(id: String) = s"EpochCoordinator-$id"

  /**
   * Create a reference to a new [[EpochCoordinator]].
   */
  def create(
              writer: StreamWriter,
              reader: ContinuousReader,
              query: ContinuousExecution,
              epochCoordinatorId: String,
              startEpoch: Long,
              session: SparkSession,
              env: SparkEnv): RpcEndpointRef = synchronized {
    val coordinator = new EpochCoordinator(
      writer, reader, query, startEpoch, session, env.rpcEnv)
    val ref = env.rpcEnv.setupEndpoint(endpointName(epochCoordinatorId), coordinator)
    logInfo("Registered EpochCoordinator endpoint")
    ref
  }

  def get(id: String, env: SparkEnv): RpcEndpointRef = synchronized {
    val rpcEndpointRef = RpcUtils.makeDriverRef(endpointName(id), env.conf, env.rpcEnv)
    logDebug("Retrieved existing EpochCoordinator endpoint")
    rpcEndpointRef
  }
}

/**
 * Handles three major epoch coordination tasks for continuous processing:
 *
 * * Maintains a local epoch counter (the "driver epoch"), incremented by IncrementAndGetEpoch
 *   and pollable from executors by GetCurrentEpoch. Note that this epoch is *not* immediately
 *   reflected anywhere in ContinuousExecution.
 * * Collates ReportPartitionOffset messages, and forwards to ContinuousExecution when all
 *   readers have ended a given epoch.
 * * Collates CommitPartitionEpoch messages, and forwards to ContinuousExecution when all readers
 *   have both committed and reported an end offset for a given epoch.
 */
private[continuous] class EpochCoordinator(
                                            writer: StreamWriter,
                                            reader: ContinuousReader,
                                            query: ContinuousExecution,
                                            startEpoch: Long,
                                            session: SparkSession,
                                            override val rpcEnv: RpcEnv)
  extends ThreadSafeRpcEndpoint with Logging {

  private var queryWritesStopped: Boolean = false

  private var numReaderPartitions: Int = _
  private var numWriterPartitions: Int = _

  private var currentDriverEpoch = startEpoch

  // (epoch, partition) -> message
  private val partitionCommits =
    mutable.Map[(Long, Int), WriterCommitMessage]()
  // (epoch, partition) -> offset
  private val partitionOffsets =
    mutable.Map[(Long, Int), PartitionOffset]()

  private var lastCommittedEpoch = startEpoch - 1
  // Remembers epochs that have to wait for previous epochs to be committed first.
  private val epochsWaitingToBeCommitted = mutable.HashSet.empty[Long]

  private def resolveCommitsAtEpoch(epoch: Long) = {
    val thisEpochCommits = findPartitionCommitsForEpoch(epoch)
    val nextEpochOffsets =
      partitionOffsets.collect { case ((e, _), o) if e == epoch => o }

    if (thisEpochCommits.size == numWriterPartitions &&
      nextEpochOffsets.size == numReaderPartitions) {

      // Check that last committed epoch is the previous one for sequencing of committed epochs.
      // If not, add the epoch being currently processed to epochs waiting to be committed,
      // otherwise commit it.
      if (lastCommittedEpoch != epoch - 1) {
        logDebug(s"Epoch $epoch has received commits from all partitions " +
          s"and is waiting for epoch ${epoch - 1} to be committed first.")
        epochsWaitingToBeCommitted.add(epoch)
      } else {
        commitEpoch(epoch, thisEpochCommits)
        lastCommittedEpoch = epoch

        // Commit subsequent epochs that are waiting to be committed.
        var nextEpoch = lastCommittedEpoch + 1
        while (epochsWaitingToBeCommitted.contains(nextEpoch)) {
          val nextEpochCommits = findPartitionCommitsForEpoch(nextEpoch)
          commitEpoch(nextEpoch, nextEpochCommits)

          epochsWaitingToBeCommitted.remove(nextEpoch)
          lastCommittedEpoch = nextEpoch
          nextEpoch += 1
        }

        // Cleanup state from before last committed epoch,
        // now that we know all partitions are forever past it.
        for (k <- partitionCommits.keys.filter { case (e, _) => e < lastCommittedEpoch }) {
          partitionCommits.remove(k)
        }
        for (k <- partitionOffsets.keys.filter { case (e, _) => e < lastCommittedEpoch }) {
          partitionOffsets.remove(k)
        }
      }
    }
  }

  /**
   * Collect per-partition commits for an epoch.
   */
  private def findPartitionCommitsForEpoch(epoch: Long): Iterable[WriterCommitMessage] = {
    partitionCommits.collect { case ((e, _), msg) if e == epoch => msg }
  }

  /**
   * Commit epoch to the offset log.
   */
  private def commitEpoch(epoch: Long, messages: Iterable[WriterCommitMessage]): Unit = {
    logDebug(s"Epoch $epoch has received commits from all partitions " +
      s"and is ready to be committed. Committing epoch $epoch.")
    // Sequencing is important here. We must commit to the writer before recording the commit
    // in the query, or we will end up dropping the commit if we restart in the middle.
    writer.commit(epoch, messages.toArray)
    query.commit(epoch)
  }

  override def receive: PartialFunction[Any, Unit] = {
    // If we just drop these messages, we won't do any writes to the query. The lame duck tasks
    // won't shed errors or anything.
    case _ if queryWritesStopped => ()

    case CommitPartitionEpoch(partitionId, epoch, message) =>
      logDebug(s"Got commit from partition $partitionId at epoch $epoch: $message")
      if (!partitionCommits.isDefinedAt((epoch, partitionId))) {
        partitionCommits.put((epoch, partitionId), message)
        resolveCommitsAtEpoch(epoch)
      }

    case ReportPartitionOffset(partitionId, epoch, offset) =>
      partitionOffsets.put((epoch, partitionId), offset)
      val thisEpochOffsets =
        partitionOffsets.collect { case ((e, _), o) if e == epoch => o }
      if (thisEpochOffsets.size == numReaderPartitions) {
        logDebug(s"Epoch $epoch has offsets reported from all partitions: $thisEpochOffsets")
        query.addOffset(epoch, reader, thisEpochOffsets.toSeq)
        resolveCommitsAtEpoch(epoch)
      }
  }

  override def receiveAndReply(context: RpcCallContext): PartialFunction[Any, Unit] = {
    case GetCurrentEpoch =>
      val result = currentDriverEpoch
      logDebug(s"Epoch $result")
      context.reply(result)

    case IncrementAndGetEpoch =>
      currentDriverEpoch += 1
      context.reply(currentDriverEpoch)

    case SetReaderPartitions(numPartitions) =>
      numReaderPartitions = numPartitions
      context.reply(())

    case SetWriterPartitions(numPartitions) =>
      numWriterPartitions = numPartitions
      context.reply(())

    case StopContinuousExecutionWrites =>
      queryWritesStopped = true
      context.reply(())
  }
}
