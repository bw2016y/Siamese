package org.apache.spark.daslab.sql.execution.streaming

import java.util.concurrent.TimeUnit.NANOSECONDS

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.Attribute
import org.apache.spark.daslab.sql.engine.expressions.GenericInternalRow
import org.apache.spark.daslab.sql.engine.expressions.UnsafeProjection
import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow
import org.apache.spark.daslab.sql.engine.plans.physical.{AllTuples, Distribution, Partitioning}
import org.apache.spark.daslab.sql.engine.streaming.InternalOutputModes
import org.apache.spark.daslab.sql.execution.{SparkPlan, UnaryExecNode}
import org.apache.spark.daslab.sql.execution.streaming.state.StateStoreOps
import org.apache.spark.daslab.sql.streaming.OutputMode
import org.apache.spark.daslab.sql.types.{LongType, NullType, StructField, StructType}

//todo
import org.apache.spark.util.CompletionIterator
import org.apache.spark.rdd.RDD


/**
 * A physical operator for executing a streaming limit, which makes sure no more than streamLimit
 * rows are returned. This operator is meant for streams in Append mode only.
 */
case class StreamingGlobalLimitExec(
                                     streamLimit: Long,
                                     child: SparkPlan,
                                     stateInfo: Option[StatefulOperatorStateInfo] = None,
                                     outputMode: Option[OutputMode] = None)
  extends UnaryExecNode with StateStoreWriter {

  private val keySchema = StructType(Array(StructField("key", NullType)))
  private val valueSchema = StructType(Array(StructField("value", LongType)))

  override protected def doExecute(): RDD[InternalRow] = {
    metrics // force lazy init at driver

    assert(outputMode.isDefined && outputMode.get == InternalOutputModes.Append,
      "StreamingGlobalLimitExec is only valid for streams in Append output mode")

    child.execute().mapPartitionsWithStateStore(
      getStateInfo,
      keySchema,
      valueSchema,
      indexOrdinal = None,
      sqlContext.sessionState,
      Some(sqlContext.streams.stateStoreCoordinator)) { (store, iter) =>
      val key = UnsafeProjection.create(keySchema)(new GenericInternalRow(Array[Any](null)))
      val numOutputRows = longMetric("numOutputRows")
      val numUpdatedStateRows = longMetric("numUpdatedStateRows")
      val allUpdatesTimeMs = longMetric("allUpdatesTimeMs")
      val commitTimeMs = longMetric("commitTimeMs")
      val updatesStartTimeNs = System.nanoTime

      val preBatchRowCount: Long = Option(store.get(key)).map(_.getLong(0)).getOrElse(0L)
      var cumulativeRowCount = preBatchRowCount

      val result = iter.filter { r =>
        val x = cumulativeRowCount < streamLimit
        if (x) {
          cumulativeRowCount += 1
        }
        x
      }

      CompletionIterator[InternalRow, Iterator[InternalRow]](result, {
        if (cumulativeRowCount > preBatchRowCount) {
          numUpdatedStateRows += 1
          numOutputRows += cumulativeRowCount - preBatchRowCount
          store.put(key, getValueRow(cumulativeRowCount))
        }
        allUpdatesTimeMs += NANOSECONDS.toMillis(System.nanoTime - updatesStartTimeNs)
        commitTimeMs += timeTakenMs { store.commit() }
        setStoreMetrics(store)
      })
    }
  }

  override def output: Seq[Attribute] = child.output

  override def outputPartitioning: Partitioning = child.outputPartitioning

  override def requiredChildDistribution: Seq[Distribution] = AllTuples :: Nil

  private def getValueRow(value: Long): UnsafeRow = {
    UnsafeProjection.create(valueSchema)(new GenericInternalRow(Array[Any](value)))
  }
}
