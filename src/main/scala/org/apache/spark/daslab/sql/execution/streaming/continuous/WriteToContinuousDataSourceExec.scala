package org.apache.spark.daslab.sql.execution.streaming.continuous



import scala.util.control.NonFatal


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.Attribute
import org.apache.spark.daslab.sql.execution.SparkPlan
import org.apache.spark.daslab.sql.execution.streaming.StreamExecution
import org.apache.spark.daslab.sql.sources.v2.writer.streaming.StreamWriter
//todo
import org.apache.spark.SparkException
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD

/**
 * The physical plan for writing data into a continuous processing [[StreamWriter]].
 */
case class WriteToContinuousDataSourceExec(writer: StreamWriter, query: SparkPlan)
  extends SparkPlan with Logging {
  override def children: Seq[SparkPlan] = Seq(query)
  override def output: Seq[Attribute] = Nil

  override protected def doExecute(): RDD[InternalRow] = {
    val writerFactory = writer.createWriterFactory()
    val rdd = new ContinuousWriteRDD(query.execute(), writerFactory)

    logInfo(s"Start processing data source writer: $writer. " +
      s"The input RDD has ${rdd.partitions.length} partitions.")
    EpochCoordinatorRef.get(
      sparkContext.getLocalProperty(ContinuousExecution.EPOCH_COORDINATOR_ID_KEY),
      sparkContext.env)
      .askSync[Unit](SetWriterPartitions(rdd.getNumPartitions))

    try {
      // Force the RDD to run so continuous processing starts; no data is actually being collected
      // to the driver, as ContinuousWriteRDD outputs nothing.
      rdd.collect()
    } catch {
      case _: InterruptedException =>
      // Interruption is how continuous queries are ended, so accept and ignore the exception.
      case cause: Throwable =>
        cause match {
          // Do not wrap interruption exceptions that will be handled by streaming specially.
          case _ if StreamExecution.isInterruptionException(cause) => throw cause
          // Only wrap non fatal exceptions.
          case NonFatal(e) => throw new SparkException("Writing job aborted.", e)
          case _ => throw cause
        }
    }

    sparkContext.emptyRDD
  }
}
