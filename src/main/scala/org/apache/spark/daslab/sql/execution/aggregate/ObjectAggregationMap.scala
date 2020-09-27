package org.apache.spark.daslab.sql.execution.aggregate



import java.{util => ju}


//todo
import org.apache.spark.{SparkEnv, TaskContext}
import org.apache.spark.internal.config
import org.apache.spark.util.collection.unsafe.sort.UnsafeExternalSorter


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, UnsafeProjection, UnsafeRow}
import org.apache.spark.daslab.sql.engine.expressions.aggregate.{AggregateFunction, TypedImperativeAggregate}
import org.apache.spark.daslab.sql.execution.UnsafeKVExternalSorter
import org.apache.spark.daslab.sql.types.StructType

/**
 * An aggregation map that supports using safe `SpecificInternalRow`s aggregation buffers, so that
 * we can support storing arbitrary Java objects as aggregate function states in the aggregation
 * buffers. This class is only used together with [[ObjectHashAggregateExec]].
 */
class ObjectAggregationMap() {
  private[this] val hashMap = new ju.LinkedHashMap[UnsafeRow, InternalRow]

  def getAggregationBuffer(groupingKey: UnsafeRow): InternalRow = {
    hashMap.get(groupingKey)
  }

  def putAggregationBuffer(groupingKey: UnsafeRow, aggBuffer: InternalRow): Unit = {
    hashMap.put(groupingKey, aggBuffer)
  }

  def size: Int = hashMap.size()

  def iterator: Iterator[AggregationBufferEntry] = {
    val iter = hashMap.entrySet().iterator()
    new Iterator[AggregationBufferEntry] {

      override def hasNext: Boolean = {
        iter.hasNext
      }
      override def next(): AggregationBufferEntry = {
        val entry = iter.next()
        new AggregationBufferEntry(entry.getKey, entry.getValue)
      }
    }
  }

  /**
   * Dumps all entries into a newly created external sorter, clears the hash map, and returns the
   * external sorter.
   */
  def dumpToExternalSorter(
                            groupingAttributes: Seq[Attribute],
                            aggregateFunctions: Seq[AggregateFunction]): UnsafeKVExternalSorter = {
    val aggBufferAttributes = aggregateFunctions.flatMap(_.aggBufferAttributes)
    val sorter = new UnsafeKVExternalSorter(
      StructType.fromAttributes(groupingAttributes),
      StructType.fromAttributes(aggBufferAttributes),
      SparkEnv.get.blockManager,
      SparkEnv.get.serializerManager,
      TaskContext.get().taskMemoryManager().pageSizeBytes,
      SparkEnv.get.conf.get(config.SHUFFLE_SPILL_NUM_ELEMENTS_FORCE_SPILL_THRESHOLD),
      null
    )

    val mapIterator = iterator
    val unsafeAggBufferProjection =
      UnsafeProjection.create(aggBufferAttributes.map(_.dataType).toArray)

    while (mapIterator.hasNext) {
      val entry = mapIterator.next()
      aggregateFunctions.foreach {
        case agg: TypedImperativeAggregate[_] =>
          agg.serializeAggregateBufferInPlace(entry.aggregationBuffer)
        case _ =>
      }

      sorter.insertKV(
        entry.groupingKey,
        unsafeAggBufferProjection(entry.aggregationBuffer)
      )
    }

    hashMap.clear()
    sorter
  }

  def clear(): Unit = {
    hashMap.clear()
  }
}

// Stores the grouping key and aggregation buffer
class AggregationBufferEntry(var groupingKey: UnsafeRow, var aggregationBuffer: InternalRow)

