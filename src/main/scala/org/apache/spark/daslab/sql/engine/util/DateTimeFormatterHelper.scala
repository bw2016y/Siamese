package org.apache.spark.daslab.sql.engine.util


import java.time._
import java.time.chrono.IsoChronology
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder, ResolverStyle}
import java.time.temporal.{ChronoField, TemporalAccessor, TemporalQueries}
import java.util.Locale

import com.google.common.cache.CacheBuilder

import org.apache.spark.daslab.sql.engine.util.DateTimeFormatterHelper._

trait DateTimeFormatterHelper {
  protected def toInstantWithZoneId(temporalAccessor: TemporalAccessor, zoneId: ZoneId): Instant = {
    val localTime = if (temporalAccessor.query(TemporalQueries.localTime) == null) {
      LocalTime.ofNanoOfDay(0)
    } else {
      LocalTime.from(temporalAccessor)
    }
    val localDate = LocalDate.from(temporalAccessor)
    val localDateTime = LocalDateTime.of(localDate, localTime)
    val zonedDateTime = ZonedDateTime.of(localDateTime, zoneId)
    Instant.from(zonedDateTime)
  }

  // Gets a formatter from the cache or creates new one. The buildFormatter method can be called
  // a few times with the same parameters in parallel if the cache does not contain values
  // associated to those parameters. Since the formatter is immutable, it does not matter.
  // In this way, synchronised is intentionally omitted in this method to make parallel calls
  // less synchronised.
  // The Cache.get method is not used here to avoid creation of additional instances of Callable.
  protected def getOrCreateFormatter(pattern: String, locale: Locale): DateTimeFormatter = {
    val key = (pattern, locale)
    var formatter = cache.getIfPresent(key)
    if (formatter == null) {
      formatter = buildFormatter(pattern, locale)
      cache.put(key, formatter)
    }
    formatter
  }
}

private object DateTimeFormatterHelper {
  val cache = CacheBuilder.newBuilder()
    .maximumSize(128)
    .build[(String, Locale), DateTimeFormatter]()

  def buildFormatter(pattern: String, locale: Locale): DateTimeFormatter = {
    new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern(pattern)
      .parseDefaulting(ChronoField.ERA, 1)
      .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
      .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
      .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
      .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
      .toFormatter(locale)
      .withChronology(IsoChronology.INSTANCE)
      .withResolverStyle(ResolverStyle.STRICT)
  }
}
