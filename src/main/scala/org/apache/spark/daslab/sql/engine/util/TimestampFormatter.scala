package org.apache.spark.daslab.sql.engine.util



import java.text.ParseException
import java.time._
import java.time.format.DateTimeParseException
import java.time.temporal.TemporalQueries
import java.util.{Locale, TimeZone}

import org.apache.spark.daslab.sql.engine.util.DateTimeUtils.instantToMicros


sealed trait TimestampFormatter extends Serializable {
  /**
   * Parses a timestamp in a string and converts it to microseconds.
   *
   * @param s - string with timestamp to parse
   * @return microseconds since epoch.
   * @throws ParseException can be thrown by legacy parser
   * @throws DateTimeParseException can be thrown by new parser
   * @throws DateTimeException unable to obtain local date or time
   */
  @throws(classOf[ParseException])
  @throws(classOf[DateTimeParseException])
  @throws(classOf[DateTimeException])
  def parse(s: String): Long
  def format(us: Long): String
}

class Iso8601TimestampFormatter(
                                 pattern: String,
                                 timeZone: TimeZone,
                                 locale: Locale) extends TimestampFormatter with DateTimeFormatterHelper {
  @transient
  private lazy val formatter = getOrCreateFormatter(pattern, locale)

  private def toInstant(s: String): Instant = {
    val temporalAccessor = formatter.parse(s)
    if (temporalAccessor.query(TemporalQueries.offset()) == null) {
      toInstantWithZoneId(temporalAccessor, timeZone.toZoneId)
    } else {
      Instant.from(temporalAccessor)
    }
  }

  override def parse(s: String): Long = instantToMicros(toInstant(s))

  override def format(us: Long): String = {
    val secs = Math.floorDiv(us, DateTimeUtils.MICROS_PER_SECOND)
    val mos = Math.floorMod(us, DateTimeUtils.MICROS_PER_SECOND)
    val instant = Instant.ofEpochSecond(secs, mos * DateTimeUtils.NANOS_PER_MICROS)

    formatter.withZone(timeZone.toZoneId).format(instant)
  }
}

object TimestampFormatter {
  val defaultPattern: String = "yyyy-MM-dd HH:mm:ss"
  val defaultLocale: Locale = Locale.US

  def apply(format: String, timeZone: TimeZone, locale: Locale): TimestampFormatter = {
    new Iso8601TimestampFormatter(format, timeZone, locale)
  }

  def apply(format: String, timeZone: TimeZone): TimestampFormatter = {
    apply(format, timeZone, defaultLocale)
  }

  def apply(timeZone: TimeZone): TimestampFormatter = {
    apply(defaultPattern, timeZone, defaultLocale)
  }
}
