package org.apache.spark.daslab.sql.execution.joins

//todo
import org.apache.spark.TaskContext
import org.apache.spark.rdd.RDD


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.Expression
import org.apache.spark.daslab.sql.engine.plans._
import org.apache.spark.daslab.sql.engine.plans.physical._
import org.apache.spark.daslab.sql.execution.{BinaryExecNode, SparkPlan}
import org.apache.spark.daslab.sql.execution.metric.SQLMetrics

/**
 * Performs a hash join of two child relations by first shuffling the data using the join keys.
 */
case class ShuffledHashJoinExec(
                                 leftKeys: Seq[Expression],
                                 rightKeys: Seq[Expression],
                                 joinType: JoinType,
                                 buildSide: BuildSide,
                                 condition: Option[Expression],
                                 left: SparkPlan,
                                 right: SparkPlan)
  extends BinaryExecNode with HashJoin {

  override lazy val metrics = Map(
    "numOutputRows" -> SQLMetrics.createMetric(sparkContext, "number of output rows"),
    "buildDataSize" -> SQLMetrics.createSizeMetric(sparkContext, "data size of build side"),
    "buildTime" -> SQLMetrics.createTimingMetric(sparkContext, "time to build hash map"))

  override def requiredChildDistribution: Seq[Distribution] =
    HashClusteredDistribution(leftKeys) :: HashClusteredDistribution(rightKeys) :: Nil

  private def buildHashedRelation(iter: Iterator[InternalRow]): HashedRelation = {
    val buildDataSize = longMetric("buildDataSize")
    val buildTime = longMetric("buildTime")
    val start = System.nanoTime()
    val context = TaskContext.get()
    val relation = HashedRelation(iter, buildKeys, taskMemoryManager = context.taskMemoryManager())
    buildTime += (System.nanoTime() - start) / 1000000
    buildDataSize += relation.estimatedSize
    // This relation is usually used until the end of task.
    context.addTaskCompletionListener[Unit](_ => relation.close())
    relation
  }

  protected override def doExecute(): RDD[InternalRow] = {
    val numOutputRows = longMetric("numOutputRows")
    streamedPlan.execute().zipPartitions(buildPlan.execute()) { (streamIter, buildIter) =>
      val hashed = buildHashedRelation(buildIter)
      join(streamIter, hashed, numOutputRows)
    }
  }
}
