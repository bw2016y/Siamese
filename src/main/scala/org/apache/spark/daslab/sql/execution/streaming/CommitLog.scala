package org.apache.spark.daslab.sql.execution.streaming

import java.io.{InputStream, OutputStream}
import java.nio.charset.StandardCharsets._

import scala.io.{Source => IOSource}

import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization

import org.apache.spark.daslab.sql.SparkSession

/**
 * Used to write log files that represent batch commit points in structured streaming.
 * A commit log file will be written immediately after the successful completion of a
 * batch, and before processing the next batch. Here is an execution summary:
 * - trigger batch 1
 * - obtain batch 1 offsets and write to offset log
 * - process batch 1
 * - write batch 1 to completion log
 * - trigger batch 2
 * - obtain batch 2 offsets and write to offset log
 * - process batch 2
 * - write batch 2 to completion log
 * ....
 *
 * The current format of the batch completion log is:
 * line 1: version
 * line 2: metadata (optional json string)
 */
class CommitLog(sparkSession: SparkSession, path: String)
  extends HDFSMetadataLog[CommitMetadata](sparkSession, path) {

  import CommitLog._

  override protected def deserialize(in: InputStream): CommitMetadata = {
    // called inside a try-finally where the underlying stream is closed in the caller
    val lines = IOSource.fromInputStream(in, UTF_8.name()).getLines()
    if (!lines.hasNext) {
      throw new IllegalStateException("Incomplete log file in the offset commit log")
    }
    parseVersion(lines.next.trim, VERSION)
    val metadataJson = if (lines.hasNext) lines.next else EMPTY_JSON
    CommitMetadata(metadataJson)
  }

  override protected def serialize(metadata: CommitMetadata, out: OutputStream): Unit = {
    // called inside a try-finally where the underlying stream is closed in the caller
    out.write(s"v${VERSION}".getBytes(UTF_8))
    out.write('\n')

    // write metadata
    out.write(metadata.json.getBytes(UTF_8))
  }
}

object CommitLog {
  private val VERSION = 1
  private val EMPTY_JSON = "{}"
}


case class CommitMetadata(nextBatchWatermarkMs: Long = 0) {
  def json: String = Serialization.write(this)(CommitMetadata.format)
}

object CommitMetadata {
  implicit val format = Serialization.formats(NoTypeHints)

  def apply(json: String): CommitMetadata = Serialization.read[CommitMetadata](json)
}
