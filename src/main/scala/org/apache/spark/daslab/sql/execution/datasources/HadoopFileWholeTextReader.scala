package org.apache.spark.daslab.sql.execution.datasources



import java.io.Closeable
import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce._
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit
import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl


//todo
import org.apache.spark.input.WholeTextFileRecordReader

/**
 * An adaptor from a [[PartitionedFile]] to an [[Iterator]] of [[Text]], which is all of the lines
 * in that file.
 */
class HadoopFileWholeTextReader(file: PartitionedFile, conf: Configuration)
  extends Iterator[Text] with Closeable {
  private val iterator = {
    val fileSplit = new CombineFileSplit(
      Array(new Path(new URI(file.filePath))),
      Array(file.start),
      Array(file.length),
      // TODO: Implement Locality
      Array.empty[String])
    val attemptId = new TaskAttemptID(new TaskID(new JobID(), TaskType.MAP, 0), 0)
    val hadoopAttemptContext = new TaskAttemptContextImpl(conf, attemptId)
    val reader = new WholeTextFileRecordReader(fileSplit, hadoopAttemptContext, 0)
    reader.initialize(fileSplit, hadoopAttemptContext)
    new RecordReaderIterator(reader)
  }

  override def hasNext: Boolean = iterator.hasNext

  override def next(): Text = iterator.next()

  override def close(): Unit = iterator.close()
}
