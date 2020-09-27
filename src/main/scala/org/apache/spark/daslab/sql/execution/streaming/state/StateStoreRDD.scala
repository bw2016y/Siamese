package org.apache.spark.daslab.sql.execution.streaming.state


import java.util.UUID

import scala.reflect.ClassTag


import org.apache.spark.daslab.sql.execution.streaming.StreamExecution
import org.apache.spark.daslab.sql.execution.streaming.continuous.EpochTracker
import org.apache.spark.daslab.sql.internal.SessionState
import org.apache.spark.daslab.sql.types.StructType

//todo
import org.apache.spark.util.SerializableConfiguration
import org.apache.spark.{Partition, TaskContext}
import org.apache.spark.rdd.RDD

/**
 * An RDD that allows computations to be executed against [[StateStore]]s. It
 * uses the [[StateStoreCoordinator]] to get the locations of loaded state stores
 * and use that as the preferred locations.
 */
class StateStoreRDD[T: ClassTag, U: ClassTag](
                                               dataRDD: RDD[T],
                                               storeUpdateFunction: (StateStore, Iterator[T]) => Iterator[U],
                                               checkpointLocation: String,
                                               queryRunId: UUID,
                                               operatorId: Long,
                                               storeVersion: Long,
                                               keySchema: StructType,
                                               valueSchema: StructType,
                                               indexOrdinal: Option[Int],
                                               sessionState: SessionState,
                                               @transient private val storeCoordinator: Option[StateStoreCoordinatorRef])
  extends RDD[U](dataRDD) {

  private val storeConf = new StateStoreConf(sessionState.conf)

  // A Hadoop Configuration can be about 10 KB, which is pretty big, so broadcast it
  private val hadoopConfBroadcast = dataRDD.context.broadcast(
    new SerializableConfiguration(sessionState.newHadoopConf()))

  override protected def getPartitions: Array[Partition] = dataRDD.partitions

  /**
   * Set the preferred location of each partition using the executor that has the related
   * [[StateStoreProvider]] already loaded.
   */
  override def getPreferredLocations(partition: Partition): Seq[String] = {
    val stateStoreProviderId = StateStoreProviderId(
      StateStoreId(checkpointLocation, operatorId, partition.index),
      queryRunId)
    storeCoordinator.flatMap(_.getLocation(stateStoreProviderId)).toSeq
  }

  override def compute(partition: Partition, ctxt: TaskContext): Iterator[U] = {
    var store: StateStore = null
    val storeProviderId = StateStoreProviderId(
      StateStoreId(checkpointLocation, operatorId, partition.index),
      queryRunId)

    // If we're in continuous processing mode, we should get the store version for the current
    // epoch rather than the one at planning time.
    val isContinuous = Option(ctxt.getLocalProperty(StreamExecution.IS_CONTINUOUS_PROCESSING))
      .map(_.toBoolean).getOrElse(false)
    val currentVersion = if (isContinuous) {
      val epoch = EpochTracker.getCurrentEpoch
      assert(epoch.isDefined, "Current epoch must be defined for continuous processing streams.")
      epoch.get
    } else {
      storeVersion
    }

    store = StateStore.get(
      storeProviderId, keySchema, valueSchema, indexOrdinal, currentVersion,
      storeConf, hadoopConfBroadcast.value.value)
    val inputIter = dataRDD.iterator(partition, ctxt)
    storeUpdateFunction(store, inputIter)
  }
}

