package org.apache.spark.daslab.sql.engine.expressions.aggregate




import org.apache.spark.daslab.sql.AnalysisException
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.util.HyperLogLogPlusPlusHelper
import org.apache.spark.daslab.sql.types._

// scalastyle:off
/**
 * HyperLogLog++ (HLL++) is a state of the art cardinality estimation algorithm. This class
 * implements the dense version of the HLL++ algorithm as an Aggregate Function.
 *
 * This implementation has been based on the following papers:
 * HyperLogLog: the analysis of a near-optimal cardinality estimation algorithm
 * http://algo.inria.fr/flajolet/Publications/FlFuGaMe07.pdf
 *
 * HyperLogLog in Practice: Algorithmic Engineering of a State of The Art Cardinality Estimation
 * Algorithm
 * http://static.googleusercontent.com/external_content/untrusted_dlcp/research.google.com/en/us/pubs/archive/40671.pdf
 *
 * Appendix to HyperLogLog in Practice: Algorithmic Engineering of a State of the Art Cardinality
 * Estimation Algorithm
 * https://docs.google.com/document/d/1gyjfMHy43U9OWBXxfaeG-3MjGzejW1dlpyMwEYAAWEI/view?fullscreen#
 *
 * @param child to estimate the cardinality of.
 * @param relativeSD the maximum estimation error allowed.
 */
// scalastyle:on
@ExpressionDescription(
  usage = """
    _FUNC_(expr[, relativeSD]) - Returns the estimated cardinality by HyperLogLog++.
      `relativeSD` defines the maximum estimation error allowed.
  """)
case class HyperLogLogPlusPlus(
                                child: Expression,
                                relativeSD: Double = 0.05,
                                mutableAggBufferOffset: Int = 0,
                                inputAggBufferOffset: Int = 0)
  extends ImperativeAggregate {

  def this(child: Expression) = {
    this(child = child, relativeSD = 0.05, mutableAggBufferOffset = 0, inputAggBufferOffset = 0)
  }

  def this(child: Expression, relativeSD: Expression) = {
    this(
      child = child,
      relativeSD = HyperLogLogPlusPlus.validateDoubleLiteral(relativeSD),
      mutableAggBufferOffset = 0,
      inputAggBufferOffset = 0)
  }

  override def prettyName: String = "approx_count_distinct"

  override def withNewMutableAggBufferOffset(newMutableAggBufferOffset: Int): ImperativeAggregate =
    copy(mutableAggBufferOffset = newMutableAggBufferOffset)

  override def withNewInputAggBufferOffset(newInputAggBufferOffset: Int): ImperativeAggregate =
    copy(inputAggBufferOffset = newInputAggBufferOffset)

  override def children: Seq[Expression] = Seq(child)

  override def nullable: Boolean = false

  override def dataType: DataType = LongType

  override def aggBufferSchema: StructType = StructType.fromAttributes(aggBufferAttributes)

  val hllppHelper = new HyperLogLogPlusPlusHelper(relativeSD)

  /** Allocate enough words to store all registers. */
  override val aggBufferAttributes: Seq[AttributeReference] = {
    Seq.tabulate(hllppHelper.numWords) { i =>
      AttributeReference(s"MS[$i]", LongType)()
    }
  }

  // Note: although this simply copies aggBufferAttributes, this common code can not be placed
  // in the superclass because that will lead to initialization ordering issues.
  override val inputAggBufferAttributes: Seq[AttributeReference] =
  aggBufferAttributes.map(_.newInstance())

  /** Fill all words with zeros. */
  override def initialize(buffer: InternalRow): Unit = {
    var word = 0
    while (word < hllppHelper.numWords) {
      buffer.setLong(mutableAggBufferOffset + word, 0)
      word += 1
    }
  }

  /**
   * Update the HLL++ buffer.
   */
  override def update(buffer: InternalRow, input: InternalRow): Unit = {
    val v = child.eval(input)
    if (v != null) {
      hllppHelper.update(buffer, mutableAggBufferOffset, v, child.dataType)
    }
  }

  /**
   * Merge the HLL++ buffers.
   */
  override def merge(buffer1: InternalRow, buffer2: InternalRow): Unit = {
    hllppHelper.merge(buffer1 = buffer1, buffer2 = buffer2,
      offset1 = mutableAggBufferOffset, offset2 = inputAggBufferOffset)
  }

  /**
   * Compute the HyperLogLog estimate.
   */
  override def eval(buffer: InternalRow): Any = {
    hllppHelper.query(buffer, mutableAggBufferOffset)
  }
}

object HyperLogLogPlusPlus {
  def validateDoubleLiteral(exp: Expression): Double = exp match {
    case Literal(d: Double, DoubleType) => d
    case Literal(dec: Decimal, _) => dec.toDouble
    case _ =>
      throw new AnalysisException("The second argument should be a double literal.")
  }
}
