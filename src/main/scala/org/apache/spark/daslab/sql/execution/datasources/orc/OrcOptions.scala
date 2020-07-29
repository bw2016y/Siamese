package org.apache.spark.daslab.sql.execution.datasources.orc



import java.util.Locale

import org.apache.orc.OrcConf.COMPRESS

import org.apache.spark.daslab.sql.engine.util.CaseInsensitiveMap
import org.apache.spark.daslab.sql.internal.SQLConf

/**
 * Options for the ORC data source.
 */
class OrcOptions(
                  @transient private val parameters: CaseInsensitiveMap[String],
                  @transient private val sqlConf: SQLConf)
  extends Serializable {

  import OrcOptions._

  def this(parameters: Map[String, String], sqlConf: SQLConf) =
    this(CaseInsensitiveMap(parameters), sqlConf)

  /**
   * Compression codec to use.
   * Acceptable values are defined in [[shortOrcCompressionCodecNames]].
   */
  val compressionCodec: String = {
    // `compression`, `orc.compress`(i.e., OrcConf.COMPRESS), and `spark.sql.orc.compression.codec`
    // are in order of precedence from highest to lowest.
    val orcCompressionConf = parameters.get(COMPRESS.getAttribute)
    val codecName = parameters
      .get("compression")
      .orElse(orcCompressionConf)
      .getOrElse(sqlConf.orcCompressionCodec)
      .toLowerCase(Locale.ROOT)
    if (!shortOrcCompressionCodecNames.contains(codecName)) {
      val availableCodecs = shortOrcCompressionCodecNames.keys.map(_.toLowerCase(Locale.ROOT))
      throw new IllegalArgumentException(s"Codec [$codecName] " +
        s"is not available. Available codecs are ${availableCodecs.mkString(", ")}.")
    }
    shortOrcCompressionCodecNames(codecName)
  }
}

object OrcOptions {
  // The ORC compression short names
  private val shortOrcCompressionCodecNames = Map(
    "none" -> "NONE",
    "uncompressed" -> "NONE",
    "snappy" -> "SNAPPY",
    "zlib" -> "ZLIB",
    "lzo" -> "LZO")

  def getORCCompressionCodecName(name: String): String = shortOrcCompressionCodecNames(name)
}
