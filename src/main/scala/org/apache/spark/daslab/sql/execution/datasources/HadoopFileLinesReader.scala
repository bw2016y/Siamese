package org.apache.spark.daslab.sql.execution.datasources


import java.io.Closeable
import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce._
import org.apache.hadoop.mapreduce.lib.input.{FileSplit, LineRecordReader}
import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl

/**
 * An adaptor from a [[PartitionedFile]] to an [[Iterator]] of [[Text]], which are all of the lines
 * in that file.
 *
 * @param file A part (i.e. "block") of a single file that should be read line by line.
 * @param lineSeparator A line separator that should be used for each line. If the value is `None`,
 *                      it covers `\r`, `\r\n` and `\n`.
 * @param conf Hadoop configuration
 *
 * @note The behavior when `lineSeparator` is `None` (covering `\r`, `\r\n` and `\n`) is defined
 * by [[LineRecordReader]], not within Spark.
 */
class HadoopFileLinesReader(
                             file: PartitionedFile,
                             lineSeparator: Option[Array[Byte]],
                             conf: Configuration) extends Iterator[Text] with Closeable {

  def this(file: PartitionedFile, conf: Configuration) = this(file, None, conf)

  private val iterator = {
    val fileSplit = new FileSplit(
      new Path(new URI(file.filePath)),
      file.start,
      file.length,
      // TODO: Implement Locality
      Array.empty)
    val attemptId = new TaskAttemptID(new TaskID(new JobID(), TaskType.MAP, 0), 0)
    val hadoopAttemptContext = new TaskAttemptContextImpl(conf, attemptId)

    val reader = lineSeparator match {
      case Some(sep) => new LineRecordReader(sep)
      // If the line separator is `None`, it covers `\r`, `\r\n` and `\n`.
      case _ => new LineRecordReader()
    }

    reader.initialize(fileSplit, hadoopAttemptContext)
    new RecordReaderIterator(reader)
  }

  override def hasNext: Boolean = iterator.hasNext

  override def next(): Text = iterator.next()

  override def close(): Unit = iterator.close()
}
