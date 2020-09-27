package org.apache.spark.daslab.sql.execution.streaming


import scala.reflect.ClassTag

import org.apache.spark.daslab.sql.SQLContext
import org.apache.spark.daslab.sql.internal.SessionState
import org.apache.spark.daslab.sql.types.StructType

//todo
import org.apache.spark.TaskContext
import org.apache.spark.rdd.RDD


package object state {

  implicit class StateStoreOps[T: ClassTag](dataRDD: RDD[T]) {

    /** Map each partition of an RDD along with data in a [[StateStore]]. */
    def mapPartitionsWithStateStore[U: ClassTag](
                                                  sqlContext: SQLContext,
                                                  stateInfo: StatefulOperatorStateInfo,
                                                  keySchema: StructType,
                                                  valueSchema: StructType,
                                                  indexOrdinal: Option[Int])(
                                                  storeUpdateFunction: (StateStore, Iterator[T]) => Iterator[U]): StateStoreRDD[T, U] = {

      mapPartitionsWithStateStore(
        stateInfo,
        keySchema,
        valueSchema,
        indexOrdinal,
        sqlContext.sessionState,
        Some(sqlContext.streams.stateStoreCoordinator))(
        storeUpdateFunction)
    }

    /** Map each partition of an RDD along with data in a [[StateStore]]. */
    private[streaming] def mapPartitionsWithStateStore[U: ClassTag](
                                                                     stateInfo: StatefulOperatorStateInfo,
                                                                     keySchema: StructType,
                                                                     valueSchema: StructType,
                                                                     indexOrdinal: Option[Int],
                                                                     sessionState: SessionState,
                                                                     storeCoordinator: Option[StateStoreCoordinatorRef])(
                                                                     storeUpdateFunction: (StateStore, Iterator[T]) => Iterator[U]): StateStoreRDD[T, U] = {

      val cleanedF = dataRDD.sparkContext.clean(storeUpdateFunction)
      val wrappedF = (store: StateStore, iter: Iterator[T]) => {
        // Abort the state store in case of error
        TaskContext.get().addTaskCompletionListener[Unit](_ => {
          if (!store.hasCommitted) store.abort()
        })
        cleanedF(store, iter)
      }

      new StateStoreRDD(
        dataRDD,
        wrappedF,
        stateInfo.checkpointLocation,
        stateInfo.queryRunId,
        stateInfo.operatorId,
        stateInfo.storeVersion,
        keySchema,
        valueSchema,
        indexOrdinal,
        sessionState,
        storeCoordinator)
    }
  }
}
