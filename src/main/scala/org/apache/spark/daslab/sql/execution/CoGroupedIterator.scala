package org.apache.spark.daslab.sql.execution


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.{Ascending, Attribute, SortOrder}
import org.apache.spark.daslab.sql.engine.expressions.codegen.GenerateOrdering

/**
 * Iterates over [[GroupedIterator]]s and returns the cogrouped data, i.e. each record is a
 * grouping key with its associated values from all [[GroupedIterator]]s.
 * Note: we assume the output of each [[GroupedIterator]] is ordered by the grouping key.
 */
class CoGroupedIterator(
                         left: Iterator[(InternalRow, Iterator[InternalRow])],
                         right: Iterator[(InternalRow, Iterator[InternalRow])],
                         groupingSchema: Seq[Attribute])
  extends Iterator[(InternalRow, Iterator[InternalRow], Iterator[InternalRow])] {

  private val keyOrdering =
    GenerateOrdering.generate(groupingSchema.map(SortOrder(_, Ascending)), groupingSchema)

  private var currentLeftData: (InternalRow, Iterator[InternalRow]) = _
  private var currentRightData: (InternalRow, Iterator[InternalRow]) = _

  override def hasNext: Boolean = {
    if (currentLeftData == null && left.hasNext) {
      currentLeftData = left.next()
    }
    if (currentRightData == null && right.hasNext) {
      currentRightData = right.next()
    }

    currentLeftData != null || currentRightData != null
  }

  override def next(): (InternalRow, Iterator[InternalRow], Iterator[InternalRow]) = {
    assert(hasNext)

    if (currentLeftData.eq(null)) {
      // left is null, right is not null, consume the right data.
      rightOnly()
    } else if (currentRightData.eq(null)) {
      // left is not null, right is null, consume the left data.
      leftOnly()
    } else if (currentLeftData._1 == currentRightData._1) {
      // left and right have the same grouping key, consume both of them.
      val result = (currentLeftData._1, currentLeftData._2, currentRightData._2)
      currentLeftData = null
      currentRightData = null
      result
    } else {
      val compare = keyOrdering.compare(currentLeftData._1, currentRightData._1)
      assert(compare != 0)
      if (compare < 0) {
        // the grouping key of left is smaller, consume the left data.
        leftOnly()
      } else {
        // the grouping key of right is smaller, consume the right data.
        rightOnly()
      }
    }
  }

  private def leftOnly(): (InternalRow, Iterator[InternalRow], Iterator[InternalRow]) = {
    val result = (currentLeftData._1, currentLeftData._2, Iterator.empty)
    currentLeftData = null
    result
  }

  private def rightOnly(): (InternalRow, Iterator[InternalRow], Iterator[InternalRow]) = {
    val result = (currentRightData._1, Iterator.empty, currentRightData._2)
    currentRightData = null
    result
  }
}
