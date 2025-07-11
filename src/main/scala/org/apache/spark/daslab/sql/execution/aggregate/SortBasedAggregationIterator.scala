package org.apache.spark.daslab.sql.execution.aggregate


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.expressions.aggregate.{AggregateExpression, AggregateFunction}
import org.apache.spark.daslab.sql.execution.metric.SQLMetric

/**
 * An iterator used to evaluate [[AggregateFunction]]. It assumes the input rows have been
 * sorted by values of [[groupingExpressions]].
 */
class SortBasedAggregationIterator(
                                    partIndex: Int,
                                    groupingExpressions: Seq[NamedExpression],
                                    valueAttributes: Seq[Attribute],
                                    inputIterator: Iterator[InternalRow],
                                    aggregateExpressions: Seq[AggregateExpression],
                                    aggregateAttributes: Seq[Attribute],
                                    initialInputBufferOffset: Int,
                                    resultExpressions: Seq[NamedExpression],
                                    newMutableProjection: (Seq[Expression], Seq[Attribute]) => MutableProjection,
                                    numOutputRows: SQLMetric)
  extends AggregationIterator(
    partIndex,
    groupingExpressions,
    valueAttributes,
    aggregateExpressions,
    aggregateAttributes,
    initialInputBufferOffset,
    resultExpressions,
    newMutableProjection) {

  /**
   * Creates a new aggregation buffer and initializes buffer values
   * for all aggregate functions.
   */
  private def newBuffer: InternalRow = {
    val bufferSchema = aggregateFunctions.flatMap(_.aggBufferAttributes)
    val bufferRowSize: Int = bufferSchema.length

    val genericMutableBuffer = new GenericInternalRow(bufferRowSize)
    val useUnsafeBuffer = bufferSchema.map(_.dataType).forall(UnsafeRow.isMutable)

    val buffer = if (useUnsafeBuffer) {
      val unsafeProjection =
        UnsafeProjection.create(bufferSchema.map(_.dataType))
      unsafeProjection.apply(genericMutableBuffer)
    } else {
      genericMutableBuffer
    }
    initializeBuffer(buffer)
    buffer
  }

  ///////////////////////////////////////////////////////////////////////////
  // Mutable states for sort based aggregation.
  ///////////////////////////////////////////////////////////////////////////

  // The partition key of the current partition.
  private[this] var currentGroupingKey: UnsafeRow = _

  // The partition key of next partition.
  private[this] var nextGroupingKey: UnsafeRow = _

  // The first row of next partition.
  private[this] var firstRowInNextGroup: InternalRow = _

  // Indicates if we has new group of rows from the sorted input iterator
  private[this] var sortedInputHasNewGroup: Boolean = false

  // The aggregation buffer used by the sort-based aggregation.
  private[this] val sortBasedAggregationBuffer: InternalRow = newBuffer

  protected def initialize(): Unit = {
    if (inputIterator.hasNext) {
      initializeBuffer(sortBasedAggregationBuffer)
      val inputRow = inputIterator.next()
      nextGroupingKey = groupingProjection(inputRow).copy()
      firstRowInNextGroup = inputRow.copy()
      sortedInputHasNewGroup = true
    } else {
      // This inputIter is empty.
      sortedInputHasNewGroup = false
    }
  }

  initialize()

  /** Processes rows in the current group. It will stop when it find a new group. */
  protected def processCurrentSortedGroup(): Unit = {
    currentGroupingKey = nextGroupingKey
    // Now, we will start to find all rows belonging to this group.
    // We create a variable to track if we see the next group.
    var findNextPartition = false
    // firstRowInNextGroup is the first row of this group. We first process it.
    processRow(sortBasedAggregationBuffer, firstRowInNextGroup)

    // The search will stop when we see the next group or there is no
    // input row left in the iter.
    while (!findNextPartition && inputIterator.hasNext) {
      // Get the grouping key.
      val currentRow = inputIterator.next()
      val groupingKey = groupingProjection(currentRow)

      // Check if the current row belongs the current input row.
      if (currentGroupingKey == groupingKey) {
        processRow(sortBasedAggregationBuffer, currentRow)
      } else {
        // We find a new group.
        findNextPartition = true
        nextGroupingKey = groupingKey.copy()
        firstRowInNextGroup = currentRow.copy()
      }
    }
    // We have not seen a new group. It means that there is no new row in the input
    // iter. The current group is the last group of the iter.
    if (!findNextPartition) {
      sortedInputHasNewGroup = false
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // Iterator's public methods
  ///////////////////////////////////////////////////////////////////////////

  override final def hasNext: Boolean = sortedInputHasNewGroup

  override final def next(): UnsafeRow = {
    if (hasNext) {
      // Process the current group.
      processCurrentSortedGroup()
      // Generate output row for the current group.
      val outputRow = generateOutput(currentGroupingKey, sortBasedAggregationBuffer)
      // Initialize buffer values for the next group.
      initializeBuffer(sortBasedAggregationBuffer)
      numOutputRows += 1
      outputRow
    } else {
      // no more result
      throw new NoSuchElementException
    }
  }

  def outputForEmptyGroupingKeyWithoutInput(): UnsafeRow = {
    initializeBuffer(sortBasedAggregationBuffer)
    generateOutput(UnsafeRow.createFromByteArray(0, 0), sortBasedAggregationBuffer)
  }
}
