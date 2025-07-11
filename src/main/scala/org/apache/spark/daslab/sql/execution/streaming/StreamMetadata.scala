package org.apache.spark.daslab.sql.execution.streaming


import java.io.{InputStreamReader, OutputStreamWriter}
import java.nio.charset.StandardCharsets
import java.util.ConcurrentModificationException

import scala.util.control.NonFatal

import org.apache.commons.io.IOUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileAlreadyExistsException, FSDataInputStream, Path}
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization


import org.apache.spark.daslab.sql.execution.streaming.CheckpointFileManager.CancellableFSDataOutputStream
import org.apache.spark.daslab.sql.streaming.StreamingQuery
//todo
import org.apache.spark.internal.Logging


/**
 * Contains metadata associated with a [[StreamingQuery]]. This information is written
 * in the checkpoint location the first time a query is started and recovered every time the query
 * is restarted.
 *
 * @param id  unique id of the [[StreamingQuery]] that needs to be persisted across restarts
 */
case class StreamMetadata(id: String) {
  def json: String = Serialization.write(this)(StreamMetadata.format)
}

object StreamMetadata extends Logging {
  implicit val format = Serialization.formats(NoTypeHints)

  /** Read the metadata from file if it exists */
  def read(metadataFile: Path, hadoopConf: Configuration): Option[StreamMetadata] = {
    val fs = metadataFile.getFileSystem(hadoopConf)
    if (fs.exists(metadataFile)) {
      var input: FSDataInputStream = null
      try {
        input = fs.open(metadataFile)
        val reader = new InputStreamReader(input, StandardCharsets.UTF_8)
        val metadata = Serialization.read[StreamMetadata](reader)
        Some(metadata)
      } catch {
        case NonFatal(e) =>
          logError(s"Error reading stream metadata from $metadataFile", e)
          throw e
      } finally {
        IOUtils.closeQuietly(input)
      }
    } else None
  }

  /** Write metadata to file */
  def write(
             metadata: StreamMetadata,
             metadataFile: Path,
             hadoopConf: Configuration): Unit = {
    var output: CancellableFSDataOutputStream = null
    try {
      val fileManager = CheckpointFileManager.create(metadataFile.getParent, hadoopConf)
      output = fileManager.createAtomic(metadataFile, overwriteIfPossible = false)
      val writer = new OutputStreamWriter(output)
      Serialization.write(metadata, writer)
      writer.close()
    } catch {
      case e: FileAlreadyExistsException =>
        if (output != null) {
          output.cancel()
        }
        throw new ConcurrentModificationException(
          s"Multiple streaming queries are concurrently using $metadataFile", e)
      case e: Throwable =>
        if (output != null) {
          output.cancel()
        }
        logError(s"Error writing stream metadata $metadata to $metadataFile", e)
        throw e
    }
  }
}
