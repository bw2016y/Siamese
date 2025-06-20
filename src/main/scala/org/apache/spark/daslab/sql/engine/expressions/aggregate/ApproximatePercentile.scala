
package org.apache.spark.daslab.sql.engine.expressions.aggregate

import java.nio.ByteBuffer

import com.google.common.primitives.{Doubles, Ints, Longs}

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult
import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult.{TypeCheckFailure, TypeCheckSuccess}
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.expressions.aggregate.ApproximatePercentile.PercentileDigest
import org.apache.spark.daslab.sql.engine.util.{ArrayData, GenericArrayData}
import org.apache.spark.daslab.sql.engine.util.QuantileSummaries
import org.apache.spark.daslab.sql.engine.util.QuantileSummaries.{defaultCompressThreshold, Stats}
import org.apache.spark.daslab.sql.types._

/**
 * The ApproximatePercentile function returns the approximate percentile(s) of a column at the given
 * percentage(s). A percentile is a watermark value below which a given percentage of the column
 * values fall. For example, the percentile of column `col` at percentage 50% is the median of
 * column `col`.
 *
 * This function supports partial aggregation.
 *
 * @param child child expression that can produce column value with `child.eval(inputRow)`
 * @param percentageExpression Expression that represents a single percentage value or
 *                             an array of percentage values. Each percentage value must be between
 *                             0.0 and 1.0.
 * @param accuracyExpression Integer literal expression of approximation accuracy. Higher value
 *                           yields better accuracy, the default value is
 *                           DEFAULT_PERCENTILE_ACCURACY.
 */
@ExpressionDescription(
  usage = """
    _FUNC_(col, percentage [, accuracy]) - Returns the approximate percentile value of numeric
      column `col` at the given percentage. The value of percentage must be between 0.0
      and 1.0. The `accuracy` parameter (default: 10000) is a positive numeric literal which
      controls approximation accuracy at the cost of memory. Higher value of `accuracy` yields
      better accuracy, `1.0/accuracy` is the relative error of the approximation.
      When `percentage` is an array, each value of the percentage array must be between 0.0 and 1.0.
      In this case, returns the approximate percentile array of column `col` at the given
      percentage array.
  """,
  examples = """
    Examples:
      > SELECT _FUNC_(10.0, array(0.5, 0.4, 0.1), 100);
       [10.0,10.0,10.0]
      > SELECT _FUNC_(10.0, 0.5, 100);
       10.0
  """)
case class ApproximatePercentile(
    child: Expression,
    percentageExpression: Expression,
    accuracyExpression: Expression,
    override val mutableAggBufferOffset: Int,
    override val inputAggBufferOffset: Int)
  extends TypedImperativeAggregate[PercentileDigest] with ImplicitCastInputTypes {

  def this(child: Expression, percentageExpression: Expression, accuracyExpression: Expression) = {
    this(child, percentageExpression, accuracyExpression, 0, 0)
  }

  def this(child: Expression, percentageExpression: Expression) = {
    this(child, percentageExpression, Literal(ApproximatePercentile.DEFAULT_PERCENTILE_ACCURACY))
  }

  // Mark as lazy so that accuracyExpression is not evaluated during tree transformation.
  private lazy val accuracy: Int = accuracyExpression.eval().asInstanceOf[Int]

  override def inputTypes: Seq[AbstractDataType] = {
    // Support NumericType, DateType and TimestampType since their internal types are all numeric,
    // and can be easily cast to double for processing.
    Seq(TypeCollection(NumericType, DateType, TimestampType),
      TypeCollection(DoubleType, ArrayType(DoubleType)), IntegerType)
  }

  // Mark as lazy so that percentageExpression is not evaluated during tree transformation.
  private lazy val (returnPercentileArray: Boolean, percentages: Array[Double]) =
    percentageExpression.eval() match {
      // Rule ImplicitTypeCasts can cast other numeric types to double
      case num: Double => (false, Array(num))
      case arrayData: ArrayData => (true, arrayData.toDoubleArray())
    }

  override def checkInputDataTypes(): TypeCheckResult = {
    val defaultCheck = super.checkInputDataTypes()
    if (defaultCheck.isFailure) {
      defaultCheck
    } else if (!percentageExpression.foldable || !accuracyExpression.foldable) {
      TypeCheckFailure(s"The accuracy or percentage provided must be a constant literal")
    } else if (accuracy <= 0) {
      TypeCheckFailure(
        s"The accuracy provided must be a positive integer literal (current value = $accuracy)")
    } else if (percentages.exists(percentage => percentage < 0.0D || percentage > 1.0D)) {
      TypeCheckFailure(
        s"All percentage values must be between 0.0 and 1.0 " +
          s"(current = ${percentages.mkString(", ")})")
    } else {
      TypeCheckSuccess
    }
  }

  override def createAggregationBuffer(): PercentileDigest = {
    val relativeError = 1.0D / accuracy
    new PercentileDigest(relativeError)
  }

  override def update(buffer: PercentileDigest, inputRow: InternalRow): PercentileDigest = {
    val value = child.eval(inputRow)
    // Ignore empty rows, for example: percentile_approx(null)
    if (value != null) {
      // Convert the value to a double value
      val doubleValue = child.dataType match {
        case DateType => value.asInstanceOf[Int].toDouble
        case TimestampType => value.asInstanceOf[Long].toDouble
        case n: NumericType => n.numeric.toDouble(value.asInstanceOf[n.InternalType])
        case other: DataType =>
          throw new UnsupportedOperationException(s"Unexpected data type ${other.catalogString}")
      }
      buffer.add(doubleValue)
    }
    buffer
  }

  override def merge(buffer: PercentileDigest, other: PercentileDigest): PercentileDigest = {
    buffer.merge(other)
    buffer
  }

  override def eval(buffer: PercentileDigest): Any = {
    val doubleResult = buffer.getPercentiles(percentages)
    val result = child.dataType match {
      case DateType => doubleResult.map(_.toInt)
      case TimestampType => doubleResult.map(_.toLong)
      case ByteType => doubleResult.map(_.toByte)
      case ShortType => doubleResult.map(_.toShort)
      case IntegerType => doubleResult.map(_.toInt)
      case LongType => doubleResult.map(_.toLong)
      case FloatType => doubleResult.map(_.toFloat)
      case DoubleType => doubleResult
      case _: DecimalType => doubleResult.map(Decimal(_))
      case other: DataType =>
        throw new UnsupportedOperationException(s"Unexpected data type ${other.catalogString}")
    }
    if (result.length == 0) {
      null
    } else if (returnPercentileArray) {
      new GenericArrayData(result)
    } else {
      result(0)
    }
  }

  override def withNewMutableAggBufferOffset(newOffset: Int): ApproximatePercentile =
    copy(mutableAggBufferOffset = newOffset)

  override def withNewInputAggBufferOffset(newOffset: Int): ApproximatePercentile =
    copy(inputAggBufferOffset = newOffset)

  override def children: Seq[Expression] = Seq(child, percentageExpression, accuracyExpression)

  // Returns null for empty inputs
  override def nullable: Boolean = true

  // The result type is the same as the input type.
  override def dataType: DataType = {
    if (returnPercentileArray) ArrayType(child.dataType, false) else child.dataType
  }

  override def prettyName: String = "percentile_approx"

  override def serialize(obj: PercentileDigest): Array[Byte] = {
    ApproximatePercentile.serializer.serialize(obj)
  }

  override def deserialize(bytes: Array[Byte]): PercentileDigest = {
    ApproximatePercentile.serializer.deserialize(bytes)
  }
}

object ApproximatePercentile {

  // Default accuracy of Percentile approximation. Larger value means better accuracy.
  // The default relative error can be deduced by defaultError = 1.0 / DEFAULT_PERCENTILE_ACCURACY
  val DEFAULT_PERCENTILE_ACCURACY: Int = 10000

  /**
   * PercentileDigest is a probabilistic data structure used for approximating percentiles
   * with limited memory. PercentileDigest is backed by [[QuantileSummaries]].
   *
   * @param summaries underlying probabilistic data structure [[QuantileSummaries]].
   */
  class PercentileDigest(private var summaries: QuantileSummaries) {

    def this(relativeError: Double) = {
      this(new QuantileSummaries(defaultCompressThreshold, relativeError, compressed = true))
    }

    private[sql] def isCompressed: Boolean = summaries.compressed

    /** Returns compressed object of [[QuantileSummaries]] */
    def quantileSummaries: QuantileSummaries = {
      if (!isCompressed) compress()
      summaries
    }

    /** Insert an observation value into the PercentileDigest data structure. */
    def add(value: Double): Unit = {
      summaries = summaries.insert(value)
    }

    /** In-place merges in another PercentileDigest. */
    def merge(other: PercentileDigest): Unit = {
      if (!isCompressed) compress()
      summaries = summaries.merge(other.quantileSummaries)
    }

    /**
     * Returns the approximate percentiles of all observation values at the given percentages.
     * A percentile is a watermark value below which a given percentage of observation values fall.
     * For example, the following code returns the 25th, median, and 75th percentiles of
     * all observation values:
     *
     * {{{
     *   val Array(p25, median, p75) = percentileDigest.getPercentiles(Array(0.25, 0.5, 0.75))
     * }}}
     */
    def getPercentiles(percentages: Array[Double]): Array[Double] = {
      if (!isCompressed) compress()
      if (summaries.count == 0 || percentages.length == 0) {
        Array.empty[Double]
      } else {
        val result = new Array[Double](percentages.length)
        var i = 0
        while (i < percentages.length) {
          // Since summaries.count != 0, the query here never return None.
          result(i) = summaries.query(percentages(i)).get
          i += 1
        }
        result
      }
    }

    private final def compress(): Unit = {
      summaries = summaries.compress()
    }
  }

  /**
   * Serializer  for class [[PercentileDigest]]
   *
   * This class is thread safe.
   */
  class PercentileDigestSerializer {

    private final def length(summaries: QuantileSummaries): Int = {
      // summaries.compressThreshold, summary.relativeError, summary.count
      Ints.BYTES + Doubles.BYTES + Longs.BYTES +
      // length of summary.sampled
      Ints.BYTES +
      // summary.sampled, Array[Stat(value: Double, g: Long, delta: Long)]
      summaries.sampled.length * (Doubles.BYTES + Longs.BYTES + Longs.BYTES)
    }

    final def serialize(obj: PercentileDigest): Array[Byte] = {
      val summary = obj.quantileSummaries
      val buffer = ByteBuffer.wrap(new Array(length(summary)))
      buffer.putInt(summary.compressThreshold)
      buffer.putDouble(summary.relativeError)
      buffer.putLong(summary.count)
      buffer.putInt(summary.sampled.length)

      var i = 0
      while (i < summary.sampled.length) {
        val stat = summary.sampled(i)
        buffer.putDouble(stat.value)
        buffer.putLong(stat.g)
        buffer.putLong(stat.delta)
        i += 1
      }
      buffer.array()
    }

    final def deserialize(bytes: Array[Byte]): PercentileDigest = {
      val buffer = ByteBuffer.wrap(bytes)
      val compressThreshold = buffer.getInt()
      val relativeError = buffer.getDouble()
      val count = buffer.getLong()
      val sampledLength = buffer.getInt()
      val sampled = new Array[Stats](sampledLength)

      var i = 0
      while (i < sampledLength) {
        val value = buffer.getDouble()
        val g = buffer.getLong()
        val delta = buffer.getLong()
        sampled(i) = Stats(value, g, delta)
        i += 1
      }
      val summary = new QuantileSummaries(compressThreshold, relativeError, sampled, count, true)
      new PercentileDigest(summary)
    }
  }

  val serializer: PercentileDigestSerializer = new PercentileDigestSerializer
}
