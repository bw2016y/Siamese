package org.apache.spark.daslab.sql.execution.streaming



import java.text.SimpleDateFormat

import com.codahale.metrics.{Gauge, MetricRegistry}

import org.apache.spark.daslab.sql.engine.util.DateTimeUtils
import org.apache.spark.daslab.sql.streaming.StreamingQueryProgress
//todo
import org.apache.spark.internal.Logging
import org.apache.spark.metrics.source.{Source => CodahaleSource}


/**
 * Serves metrics from a [[org.apache.spark.daslab.sql.streaming.StreamingQuery]] to
 * Codahale/DropWizard metrics
 */
class MetricsReporter(
                       stream: StreamExecution,
                       override val sourceName: String) extends CodahaleSource with Logging {

  override val metricRegistry: MetricRegistry = new MetricRegistry

  // Metric names should not have . in them, so that all the metrics of a query are identified
  // together in Ganglia as a single metric group
  registerGauge("inputRate-total", _.inputRowsPerSecond, 0.0)
  registerGauge("processingRate-total", _.processedRowsPerSecond, 0.0)
  registerGauge("latency", _.durationMs.get("triggerExecution").longValue(), 0L)

  private val timestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // ISO8601
  timestampFormat.setTimeZone(DateTimeUtils.getTimeZone("UTC"))

  registerGauge("eventTime-watermark",
    progress => convertStringDateToMillis(progress.eventTime.get("watermark")), 0L)

  registerGauge("states-rowsTotal", _.stateOperators.map(_.numRowsTotal).sum, 0L)
  registerGauge("states-usedBytes", _.stateOperators.map(_.memoryUsedBytes).sum, 0L)

  private def convertStringDateToMillis(isoUtcDateStr: String) = {
    if (isoUtcDateStr != null) {
      timestampFormat.parse(isoUtcDateStr).getTime
    } else {
      0L
    }
  }

  private def registerGauge[T](
                                name: String,
                                f: StreamingQueryProgress => T,
                                default: T): Unit = {
    synchronized {
      metricRegistry.register(name, new Gauge[T] {
        override def getValue: T = Option(stream.lastProgress).map(f).getOrElse(default)
      })
    }
  }
}
