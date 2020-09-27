package org.apache.spark.daslab.sql.execution.datasources.v2


import scala.util.control.NonFatal


import org.apache.spark.daslab.sql.Row
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.encoders.{ExpressionEncoder, RowEncoder}
import org.apache.spark.daslab.sql.engine.expressions.Attribute
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.execution.SparkPlan
import org.apache.spark.daslab.sql.execution.streaming.MicroBatchExecution
import org.apache.spark.daslab.sql.sources.v2.writer._
import org.apache.spark.daslab.sql.types.StructType

//todo
import org.apache.spark.util.Utils
import org.apache.spark.{SparkEnv, SparkException, TaskContext}
import org.apache.spark.executor.CommitDeniedException
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD

/**
 * Deprecated logical plan for writing data into data source v2. This is being replaced by more
 * specific logical plans, like [[org.apache.spark.daslab.sql.engine.plans.logical.AppendData]].
 */
@deprecated("Use specific logical plans like AppendData instead", "2.4.0")
case class WriteToDataSourceV2(writer: DataSourceWriter, query: LogicalPlan) extends LogicalPlan {
  override def children: Seq[LogicalPlan] = Seq(query)
  override def output: Seq[Attribute] = Nil
}

/**
 * The physical plan for writing data into data source v2.
 */
case class WriteToDataSourceV2Exec(writer: DataSourceWriter, query: SparkPlan) extends SparkPlan {
  override def children: Seq[SparkPlan] = Seq(query)
  override def output: Seq[Attribute] = Nil

  override protected def doExecute(): RDD[InternalRow] = {
    val writeTask = writer.createWriterFactory()
    val useCommitCoordinator = writer.useCommitCoordinator
    val rdd = query.execute()
    val messages = new Array[WriterCommitMessage](rdd.partitions.length)

    logInfo(s"Start processing data source writer: $writer. " +
      s"The input RDD has ${messages.length} partitions.")

    try {
      sparkContext.runJob(
        rdd,
        (context: TaskContext, iter: Iterator[InternalRow]) =>
          DataWritingSparkTask.run(writeTask, context, iter, useCommitCoordinator),
        rdd.partitions.indices,
        (index, message: WriterCommitMessage) => {
          messages(index) = message
          writer.onDataWriterCommit(message)
        }
      )

      logInfo(s"Data source writer $writer is committing.")
      writer.commit(messages)
      logInfo(s"Data source writer $writer committed.")
    } catch {
      case cause: Throwable =>
        logError(s"Data source writer $writer is aborting.")
        try {
          writer.abort(messages)
        } catch {
          case t: Throwable =>
            logError(s"Data source writer $writer failed to abort.")
            cause.addSuppressed(t)
            throw new SparkException("Writing job failed.", cause)
        }
        logError(s"Data source writer $writer aborted.")
        cause match {
          // Only wrap non fatal exceptions.
          case NonFatal(e) => throw new SparkException("Writing job aborted.", e)
          case _ => throw cause
        }
    }

    sparkContext.emptyRDD
  }
}

object DataWritingSparkTask extends Logging {
  def run(
           writeTask: DataWriterFactory[InternalRow],
           context: TaskContext,
           iter: Iterator[InternalRow],
           useCommitCoordinator: Boolean): WriterCommitMessage = {
    val stageId = context.stageId()
    val stageAttempt = context.stageAttemptNumber()
    val partId = context.partitionId()
    val taskId = context.taskAttemptId()
    val attemptId = context.attemptNumber()
    val epochId = Option(context.getLocalProperty(MicroBatchExecution.BATCH_ID_KEY)).getOrElse("0")
    val dataWriter = writeTask.createDataWriter(partId, taskId, epochId.toLong)

    // write the data and commit this writer.
    Utils.tryWithSafeFinallyAndFailureCallbacks(block = {
      while (iter.hasNext) {
        dataWriter.write(iter.next())
      }

      val msg = if (useCommitCoordinator) {
        val coordinator = SparkEnv.get.outputCommitCoordinator
        val commitAuthorized = coordinator.canCommit(stageId, stageAttempt, partId, attemptId)
        if (commitAuthorized) {
          logInfo(s"Commit authorized for partition $partId (task $taskId, attempt $attemptId, " +
            s"stage $stageId.$stageAttempt)")
          dataWriter.commit()
        } else {
          val message = s"Commit denied for partition $partId (task $taskId, attempt $attemptId, " +
            s"stage $stageId.$stageAttempt)"
          logInfo(message)
          // throwing CommitDeniedException will trigger the catch block for abort
          throw new CommitDeniedException(message, stageId, partId, attemptId)
        }

      } else {
        logInfo(s"Writer for partition ${context.partitionId()} is committing.")
        dataWriter.commit()
      }

      logInfo(s"Committed partition $partId (task $taskId, attempt $attemptId, " +
        s"stage $stageId.$stageAttempt)")

      msg

    })(catchBlock = {
      // If there is an error, abort this writer
      logError(s"Aborting commit for partition $partId (task $taskId, attempt $attemptId, " +
        s"stage $stageId.$stageAttempt)")
      dataWriter.abort()
      logError(s"Aborted commit for partition $partId (task $taskId, attempt $attemptId, " +
        s"stage $stageId.$stageAttempt)")
    })
  }
}
