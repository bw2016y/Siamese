package org.apache.spark.daslab.sql.execution.window



import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.Projection


/**
 * Function for comparing boundary values.
 */
private[window] abstract class BoundOrdering {
  def compare(inputRow: InternalRow, inputIndex: Int, outputRow: InternalRow, outputIndex: Int): Int
}

/**
 * Compare the input index to the bound of the output index.
 */
private[window] final case class RowBoundOrdering(offset: Int) extends BoundOrdering {
  override def compare(
                        inputRow: InternalRow,
                        inputIndex: Int,
                        outputRow: InternalRow,
                        outputIndex: Int): Int =
    inputIndex - (outputIndex + offset)
}

/**
 * Compare the value of the input index to the value bound of the output index.
 */
private[window] final case class RangeBoundOrdering(
                                                     ordering: Ordering[InternalRow],
                                                     current: Projection,
                                                     bound: Projection)
  extends BoundOrdering {

  override def compare(
                        inputRow: InternalRow,
                        inputIndex: Int,
                        outputRow: InternalRow,
                        outputIndex: Int): Int =
    ordering.compare(current(inputRow), bound(outputRow))
}
