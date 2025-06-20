package org.apache.spark.daslab.sql.execution.streaming.continuous



import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.sources.v2.writer.{DataWriter, DataWriterFactory}

//todo
import org.apache.spark.util.Utils
import org.apache.spark.{Partition, SparkEnv, TaskContext}
import org.apache.spark.rdd.RDD

/**
 * The RDD writing to a sink in continuous processing.
 *
 * Within each task, we repeatedly call prev.compute(). Each resulting iterator contains the data
 * to be written for one epoch, which we commit and forward to the driver.
 *
 * We keep repeating prev.compute() and writing new epochs until the query is shut down.
 */
class ContinuousWriteRDD(var prev: RDD[InternalRow], writeTask: DataWriterFactory[InternalRow])
  extends RDD[Unit](prev) {

  override val partitioner = prev.partitioner

  override def getPartitions: Array[Partition] = prev.partitions

  override def compute(split: Partition, context: TaskContext): Iterator[Unit] = {
    val epochCoordinator = EpochCoordinatorRef.get(
      context.getLocalProperty(ContinuousExecution.EPOCH_COORDINATOR_ID_KEY),
      SparkEnv.get)
    EpochTracker.initializeCurrentEpoch(
      context.getLocalProperty(ContinuousExecution.START_EPOCH_KEY).toLong)
    while (!context.isInterrupted() && !context.isCompleted()) {
      var dataWriter: DataWriter[InternalRow] = null
      // write the data and commit this writer.
      Utils.tryWithSafeFinallyAndFailureCallbacks(block = {
        try {
          val dataIterator = prev.compute(split, context)
          dataWriter = writeTask.createDataWriter(
            context.partitionId(),
            context.taskAttemptId(),
            EpochTracker.getCurrentEpoch.get)
          while (dataIterator.hasNext) {
            dataWriter.write(dataIterator.next())
          }
          logInfo(s"Writer for partition ${context.partitionId()} " +
            s"in epoch ${EpochTracker.getCurrentEpoch.get} is committing.")
          val msg = dataWriter.commit()
          epochCoordinator.send(
            CommitPartitionEpoch(
              context.partitionId(),
              EpochTracker.getCurrentEpoch.get,
              msg)
          )
          logInfo(s"Writer for partition ${context.partitionId()} " +
            s"in epoch ${EpochTracker.getCurrentEpoch.get} committed.")
          EpochTracker.incrementCurrentEpoch()
        } catch {
          case _: InterruptedException =>
          // Continuous shutdown always involves an interrupt. Just finish the task.
        }
      })(catchBlock = {
        // If there is an error, abort this writer. We enter this callback in the middle of
        // rethrowing an exception, so compute() will stop executing at this point.
        logError(s"Writer for partition ${context.partitionId()} is aborting.")
        if (dataWriter != null) dataWriter.abort()
        logError(s"Writer for partition ${context.partitionId()} aborted.")
      })
    }

    Iterator()
  }

  override def clearDependencies() {
    super.clearDependencies()
    prev = null
  }
}
