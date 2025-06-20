package org.apache.spark.daslab.sql.engine.plans.logical



import java.io.{ByteArrayInputStream, ByteArrayOutputStream, DataInputStream, DataOutputStream}
import java.math.{MathContext, RoundingMode}

import scala.util.control.NonFatal

import net.jpountz.lz4.{LZ4BlockInputStream, LZ4BlockOutputStream}
import org.apache.spark.daslab.sql.AnalysisException
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.catalog.CatalogColumnStat
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.expressions.aggregate._
import org.apache.spark.daslab.sql.engine.util.{ArrayData, DateTimeUtils}
import org.apache.spark.daslab.sql.internal.SQLConf
import org.apache.spark.daslab.sql.types._

// todo spark core
import org.apache.spark.internal.Logging
import org.apache.spark.util.Utils


/**
 * Estimates of various statistics.  The default estimation logic simply lazily multiplies the
 * corresponding statistic produced by the children.  To override this behavior, override
 * `statistics` and assign it an overridden version of `Statistics`.
 *
 * '''NOTE''': concrete and/or overridden versions of statistics fields should pay attention to the
 * performance of the implementations.  The reason is that estimations might get triggered in
 * performance-critical processes, such as query plan planning.
 *
 * Note that we are using a BigInt here since it is easy to overflow a 64-bit integer in
 * cardinality estimation (e.g. cartesian joins).
 *
 * @param sizeInBytes Physical size in bytes. For leaf operators this defaults to 1, otherwise it
 *                    defaults to the product of children's `sizeInBytes`.
 * @param rowCount Estimated number of rows.
 * @param attributeStats Statistics for Attributes.
 * @param hints Query hints.
 */
case class Statistics(
                       sizeInBytes: BigInt,
                       rowCount: Option[BigInt] = None,
                       attributeStats: AttributeMap[ColumnStat] = AttributeMap(Nil),
                       hints: HintInfo = HintInfo()) {

  override def toString: String = "Statistics(" + simpleString + ")"

  /** Readable string representation for the Statistics. */
  def simpleString: String = {
    Seq(s"sizeInBytes=${Utils.bytesToString(sizeInBytes)}",
      if (rowCount.isDefined) {
        // Show row count in scientific notation.
        s"rowCount=${BigDecimal(rowCount.get, new MathContext(3, RoundingMode.HALF_UP)).toString()}"
      } else {
        ""
      },
      s"hints=$hints"
    ).filter(_.nonEmpty).mkString(", ")
  }
}


/**
 * Statistics collected for a column.
 *
 * 1. The JVM data type stored in min/max is the internal data type for the corresponding
 *    Catalyst data type. For example, the internal type of DateType is Int, and that the internal
 *    type of TimestampType is Long.
 * 2. There is no guarantee that the statistics collected are accurate. Approximation algorithms
 *    (sketches) might have been used, and the data collected can also be stale.
 *
 * @param distinctCount number of distinct values
 * @param min minimum value
 * @param max maximum value
 * @param nullCount number of nulls
 * @param avgLen average length of the values. For fixed-length types, this should be a constant.
 * @param maxLen maximum length of the values. For fixed-length types, this should be a constant.
 * @param histogram histogram of the values
 * @param version version of statistics saved to or retrieved from the catalog
 */
case class ColumnStat(
                       distinctCount: Option[BigInt] = None,
                       min: Option[Any] = None,
                       max: Option[Any] = None,
                       nullCount: Option[BigInt] = None,
                       avgLen: Option[Long] = None,
                       maxLen: Option[Long] = None,
                       histogram: Option[Histogram] = None,
                       version: Int = CatalogColumnStat.VERSION) {

  // Are distinctCount and nullCount statistics defined?
  val hasCountStats = distinctCount.isDefined && nullCount.isDefined

  // Are min and max statistics defined?
  val hasMinMaxStats = min.isDefined && max.isDefined

  // Are avgLen and maxLen statistics defined?
  val hasLenStats = avgLen.isDefined && maxLen.isDefined

  def toCatalogColumnStat(colName: String, dataType: DataType): CatalogColumnStat =
    CatalogColumnStat(
      distinctCount = distinctCount,
      min = min.map(CatalogColumnStat.toExternalString(_, colName, dataType)),
      max = max.map(CatalogColumnStat.toExternalString(_, colName, dataType)),
      nullCount = nullCount,
      avgLen = avgLen,
      maxLen = maxLen,
      histogram = histogram,
      version = version)
}

/**
 * This class is an implementation of equi-height histogram.
 * Equi-height histogram represents the distribution of a column's values by a sequence of bins.
 * Each bin has a value range and contains approximately the same number of rows.
 *
 * @param height number of rows in each bin
 * @param bins equi-height histogram bins
 */
case class Histogram(height: Double, bins: Array[HistogramBin]) {

  // Only for histogram equality test.
  override def equals(other: Any): Boolean = other match {
    case otherHgm: Histogram =>
      height == otherHgm.height && bins.sameElements(otherHgm.bins)
    case _ => false
  }

  override def hashCode(): Int = {
    val temp = java.lang.Double.doubleToLongBits(height)
    var result = (temp ^ (temp >>> 32)).toInt
    result = 31 * result + java.util.Arrays.hashCode(bins.asInstanceOf[Array[AnyRef]])
    result
  }
}

/**
 * A bin in an equi-height histogram. We use double type for lower/higher bound for simplicity.
 *
 * @param lo lower bound of the value range in this bin
 * @param hi higher bound of the value range in this bin
 * @param ndv approximate number of distinct values in this bin
 */
case class HistogramBin(lo: Double, hi: Double, ndv: Long)

object HistogramSerializer {
  /**
   * Serializes a given histogram to a string. For advanced statistics like histograms, sketches,
   * etc, we don't provide readability for their serialized formats in metastore
   * (string-to-string table properties). This is because it's hard or unnatural for these
   * statistics to be human readable. For example, a histogram usually cannot fit in a single,
   * self-described property. And for count-min-sketch, it's essentially unnatural to make it
   * a readable string.
   */
  final def serialize(histogram: Histogram): String = {
    val bos = new ByteArrayOutputStream()
    val out = new DataOutputStream(new LZ4BlockOutputStream(bos))
    out.writeDouble(histogram.height)
    out.writeInt(histogram.bins.length)
    // Write data with same type together for compression.
    var i = 0
    while (i < histogram.bins.length) {
      out.writeDouble(histogram.bins(i).lo)
      i += 1
    }
    i = 0
    while (i < histogram.bins.length) {
      out.writeDouble(histogram.bins(i).hi)
      i += 1
    }
    i = 0
    while (i < histogram.bins.length) {
      out.writeLong(histogram.bins(i).ndv)
      i += 1
    }
    out.writeInt(-1)
    out.flush()
    out.close()

    org.apache.commons.codec.binary.Base64.encodeBase64String(bos.toByteArray)
  }

  /** Deserializes a given string to a histogram. */
  final def deserialize(str: String): Histogram = {
    val bytes = org.apache.commons.codec.binary.Base64.decodeBase64(str)
    val bis = new ByteArrayInputStream(bytes)
    val ins = new DataInputStream(new LZ4BlockInputStream(bis))
    val height = ins.readDouble()
    val numBins = ins.readInt()

    val los = new Array[Double](numBins)
    var i = 0
    while (i < numBins) {
      los(i) = ins.readDouble()
      i += 1
    }
    val his = new Array[Double](numBins)
    i = 0
    while (i < numBins) {
      his(i) = ins.readDouble()
      i += 1
    }
    val ndvs = new Array[Long](numBins)
    i = 0
    while (i < numBins) {
      ndvs(i) = ins.readLong()
      i += 1
    }
    ins.close()

    val bins = new Array[HistogramBin](numBins)
    i = 0
    while (i < numBins) {
      bins(i) = HistogramBin(los(i), his(i), ndvs(i))
      i += 1
    }
    Histogram(height, bins)
  }
}
