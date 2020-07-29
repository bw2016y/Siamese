package org.apache.spark.daslab.sql.execution.streaming.continuous



import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.sources.v2.reader._
import org.apache.spark.daslab.sql.sources.v2.reader.streaming.ContinuousInputPartitionReader
//todo
import org.apache.spark.util.NextIterator
import org.apache.spark._
import org.apache.spark.rdd.RDD

class ContinuousDataSourceRDDPartition(
                                        val index: Int,
                                        val inputPartition: InputPartition[InternalRow])
  extends Partition with Serializable {

  // This is semantically a lazy val - it's initialized once the first time a call to
  // ContinuousDataSourceRDD.compute() needs to access it, so it can be shared across
  // all compute() calls for a partition. This ensures that one compute() picks up where the
  // previous one ended.
  // We don't make it actually a lazy val because it needs input which isn't available here.
  // This will only be initialized on the executors.
  private[continuous] var queueReader: ContinuousQueuedDataReader = _
}

/**
 * The bottom-most RDD of a continuous processing read task. Wraps a [[ContinuousQueuedDataReader]]
 * to read from the remote source, and polls that queue for incoming rows.
 *
 * Note that continuous processing calls compute() multiple times, and the same
 * [[ContinuousQueuedDataReader]] instance will/must be shared between each call for the same split.
 */
class ContinuousDataSourceRDD(
                               sc: SparkContext,
                               dataQueueSize: Int,
                               epochPollIntervalMs: Long,
                               private val readerInputPartitions: Seq[InputPartition[InternalRow]])
  extends RDD[InternalRow](sc, Nil) {

  override protected def getPartitions: Array[Partition] = {
    readerInputPartitions.zipWithIndex.map {
      case (inputPartition, index) => new ContinuousDataSourceRDDPartition(index, inputPartition)
    }.toArray
  }

  /**
   * Initialize the shared reader for this partition if needed, then read rows from it until
   * it returns null to signal the end of the epoch.
   */
  override def compute(split: Partition, context: TaskContext): Iterator[InternalRow] = {
    // If attempt number isn't 0, this is a task retry, which we don't support.
    if (context.attemptNumber() != 0) {
      throw new ContinuousTaskRetryException()
    }

    val readerForPartition = {
      val partition = split.asInstanceOf[ContinuousDataSourceRDDPartition]
      if (partition.queueReader == null) {
        partition.queueReader =
          new ContinuousQueuedDataReader(partition, context, dataQueueSize, epochPollIntervalMs)
      }

      partition.queueReader
    }

    new NextIterator[InternalRow] {
      override def getNext(): InternalRow = {
        readerForPartition.next() match {
          case null =>
            finished = true
            null
          case row => row
        }
      }

      override def close(): Unit = {}
    }
  }

  override def getPreferredLocations(split: Partition): Seq[String] = {
    split.asInstanceOf[ContinuousDataSourceRDDPartition].inputPartition.preferredLocations()
  }
}

object ContinuousDataSourceRDD {
  private[continuous] def getContinuousReader(
                                               reader: InputPartitionReader[InternalRow]): ContinuousInputPartitionReader[_] = {
    reader match {
      case r: ContinuousInputPartitionReader[InternalRow] => r
      case _ =>
        throw new IllegalStateException(s"Unknown continuous reader type ${reader.getClass}")
    }
  }
}
