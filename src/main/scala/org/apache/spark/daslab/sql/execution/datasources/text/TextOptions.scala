package org.apache.spark.daslab.sql.execution.datasources.text


import java.nio.charset.{Charset, StandardCharsets}


import org.apache.spark.daslab.sql.engine.util.{CaseInsensitiveMap, CompressionCodecs}

/**
 * Options for the Text data source.
 */
private[text] class TextOptions(@transient private val parameters: CaseInsensitiveMap[String])
  extends Serializable {

  import TextOptions._

  def this(parameters: Map[String, String]) = this(CaseInsensitiveMap(parameters))

  /**
   * Compression codec to use.
   */
  val compressionCodec = parameters.get(COMPRESSION).map(CompressionCodecs.getCodecClassName)

  /**
   * wholetext - If true, read a file as a single row and not split by "\n".
   */
  val wholeText = parameters.getOrElse(WHOLETEXT, "false").toBoolean

  val encoding: Option[String] = parameters.get(ENCODING)

  val lineSeparator: Option[String] = parameters.get(LINE_SEPARATOR).map { lineSep =>
    require(lineSep.nonEmpty, s"'$LINE_SEPARATOR' cannot be an empty string.")

    lineSep
  }

  // Note that the option 'lineSep' uses a different default value in read and write.
  val lineSeparatorInRead: Option[Array[Byte]] = lineSeparator.map { lineSep =>
    lineSep.getBytes(encoding.map(Charset.forName(_)).getOrElse(StandardCharsets.UTF_8))
  }
  val lineSeparatorInWrite: Array[Byte] =
    lineSeparatorInRead.getOrElse("\n".getBytes(StandardCharsets.UTF_8))
}

private[datasources] object TextOptions {
  val COMPRESSION = "compression"
  val WHOLETEXT = "wholetext"
  val ENCODING = "encoding"
  val LINE_SEPARATOR = "lineSep"
}
