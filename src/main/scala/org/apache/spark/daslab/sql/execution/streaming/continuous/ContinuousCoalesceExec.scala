package org.apache.spark.daslab.sql.execution.streaming.continuous


import java.util.UUID

//todo
import org.apache.spark.{HashPartitioner, SparkEnv}
import org.apache.spark.rdd.RDD


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, UnsafeRow}
import org.apache.spark.daslab.sql.engine.plans.physical.{Partitioning, SinglePartition}
import org.apache.spark.daslab.sql.execution.SparkPlan
import org.apache.spark.daslab.sql.execution.streaming.continuous.shuffle.{ContinuousShuffleReadPartition, ContinuousShuffleReadRDD}

/**
 * Physical plan for coalescing a continuous processing plan.
 *
 * Currently, only coalesces to a single partition are supported. `numPartitions` must be 1.
 */
case class ContinuousCoalesceExec(numPartitions: Int, child: SparkPlan) extends SparkPlan {
  override def output: Seq[Attribute] = child.output

  override def children: Seq[SparkPlan] = child :: Nil

  override def outputPartitioning: Partitioning = SinglePartition

  override def doExecute(): RDD[InternalRow] = {
    assert(numPartitions == 1)
    new ContinuousCoalesceRDD(
      sparkContext,
      numPartitions,
      conf.continuousStreamingExecutorQueueSize,
      sparkContext.getLocalProperty(ContinuousExecution.EPOCH_INTERVAL_KEY).toLong,
      child.execute())
  }
}
