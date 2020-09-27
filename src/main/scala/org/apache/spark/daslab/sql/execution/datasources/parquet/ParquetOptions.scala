package org.apache.spark.daslab.sql.execution.datasources.parquet



import java.util.Locale

import org.apache.parquet.hadoop.ParquetOutputFormat
import org.apache.parquet.hadoop.metadata.CompressionCodecName

import org.apache.spark.daslab.sql.engine.util.CaseInsensitiveMap
import org.apache.spark.daslab.sql.internal.SQLConf

/**
 * Options for the Parquet data source.
 */
class ParquetOptions(
                      @transient private val parameters: CaseInsensitiveMap[String],
                      @transient private val sqlConf: SQLConf)
  extends Serializable {

  import ParquetOptions._

  def this(parameters: Map[String, String], sqlConf: SQLConf) =
    this(CaseInsensitiveMap(parameters), sqlConf)

  /**
   * Compression codec to use. By default use the value specified in SQLConf.
   * Acceptable values are defined in [[shortParquetCompressionCodecNames]].
   */
  val compressionCodecClassName: String = {
    // `compression`, `parquet.compression`(i.e., ParquetOutputFormat.COMPRESSION), and
    // `spark.sql.parquet.compression.codec`
    // are in order of precedence from highest to lowest.
    val parquetCompressionConf = parameters.get(ParquetOutputFormat.COMPRESSION)
    val codecName = parameters
      .get("compression")
      .orElse(parquetCompressionConf)
      .getOrElse(sqlConf.parquetCompressionCodec)
      .toLowerCase(Locale.ROOT)
    if (!shortParquetCompressionCodecNames.contains(codecName)) {
      val availableCodecs =
        shortParquetCompressionCodecNames.keys.map(_.toLowerCase(Locale.ROOT))
      throw new IllegalArgumentException(s"Codec [$codecName] " +
        s"is not available. Available codecs are ${availableCodecs.mkString(", ")}.")
    }
    shortParquetCompressionCodecNames(codecName).name()
  }

  /**
   * Whether it merges schemas or not. When the given Parquet files have different schemas,
   * the schemas can be merged.  By default use the value specified in SQLConf.
   */
  val mergeSchema: Boolean = parameters
    .get(MERGE_SCHEMA)
    .map(_.toBoolean)
    .getOrElse(sqlConf.isParquetSchemaMergingEnabled)
}


object ParquetOptions {
  val MERGE_SCHEMA = "mergeSchema"

  // The parquet compression short names
  private val shortParquetCompressionCodecNames = Map(
    "none" -> CompressionCodecName.UNCOMPRESSED,
    "uncompressed" -> CompressionCodecName.UNCOMPRESSED,
    "snappy" -> CompressionCodecName.SNAPPY,
    "gzip" -> CompressionCodecName.GZIP,
    "lzo" -> CompressionCodecName.LZO,
    "lz4" -> CompressionCodecName.LZ4,
    "brotli" -> CompressionCodecName.BROTLI,
    "zstd" -> CompressionCodecName.ZSTD)

  def getParquetCompressionCodecName(name: String): String = {
    shortParquetCompressionCodecNames(name).name()
  }
}
