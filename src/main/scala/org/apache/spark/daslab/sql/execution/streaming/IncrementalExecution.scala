package org.apache.spark.daslab.sql.execution.streaming

import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

import org.apache.spark.daslab.sql.{AnalysisException, SparkSession, Strategy}
import org.apache.spark.daslab.sql.engine.expressions.{CurrentBatchTimestamp, ExpressionWithRandomSeed}
import org.apache.spark.daslab.sql.engine.plans.logical._
import org.apache.spark.daslab.sql.engine.plans.physical.{AllTuples, ClusteredDistribution, HashPartitioning, SinglePartition}
import org.apache.spark.daslab.sql.engine.rules.Rule
import org.apache.spark.daslab.sql.execution.{QueryExecution, SparkPlan, SparkPlanner, UnaryExecNode}
import org.apache.spark.daslab.sql.execution.exchange.ShuffleExchangeExec
import org.apache.spark.daslab.sql.internal.SQLConf
import org.apache.spark.daslab.sql.streaming.OutputMode

//todo
import org.apache.spark.internal.Logging
import org.apache.spark.util.Utils

/**
 * A variant of [[QueryExecution]] that allows the execution of the given [[LogicalPlan]]
 * plan incrementally. Possibly preserving state in between each execution.
 */
class IncrementalExecution(
                            sparkSession: SparkSession,
                            logicalPlan: LogicalPlan,
                            val outputMode: OutputMode,
                            val checkpointLocation: String,
                            val runId: UUID,
                            val currentBatchId: Long,
                            val offsetSeqMetadata: OffsetSeqMetadata)
  extends QueryExecution(sparkSession, logicalPlan) with Logging {

  // Modified planner with stateful operations.
  override val planner: SparkPlanner = new SparkPlanner(
    sparkSession.sparkContext,
    sparkSession.sessionState.conf,
    sparkSession.sessionState.experimentalMethods) {
    override def strategies: Seq[Strategy] =
      extraPlanningStrategies ++
        sparkSession.sessionState.planner.strategies

    override def extraPlanningStrategies: Seq[Strategy] =
      StreamingJoinStrategy ::
        StatefulAggregationStrategy ::
        FlatMapGroupsWithStateStrategy ::
        StreamingRelationStrategy ::
        StreamingDeduplicationStrategy ::
        StreamingGlobalLimitStrategy(outputMode) :: Nil
  }

  private[sql] val numStateStores = offsetSeqMetadata.conf.get(SQLConf.SHUFFLE_PARTITIONS.key)
    .map(SQLConf.SHUFFLE_PARTITIONS.valueConverter)
    .getOrElse(sparkSession.sessionState.conf.numShufflePartitions)

  /**
   * See [SPARK-18339]
   * Walk the optimized logical plan and replace CurrentBatchTimestamp
   * with the desired literal
   */
  override lazy val optimizedPlan: LogicalPlan = {
    sparkSession.sessionState.optimizer.execute(withCachedData) transformAllExpressions {
      case ts @ CurrentBatchTimestamp(timestamp, _, _) =>
        logInfo(s"Current batch timestamp = $timestamp")
        ts.toLiteral
      case e: ExpressionWithRandomSeed => e.withNewSeed(Utils.random.nextLong())
    }
  }

  /**
   * Records the current id for a given stateful operator in the query plan as the `state`
   * preparation walks the query plan.
   */
  private val statefulOperatorId = new AtomicInteger(0)

  /** Get the state info of the next stateful operator */
  private def nextStatefulOperationStateInfo(): StatefulOperatorStateInfo = {
    StatefulOperatorStateInfo(
      checkpointLocation,
      runId,
      statefulOperatorId.getAndIncrement(),
      currentBatchId,
      numStateStores)
  }

  /** Locates save/restore pairs surrounding aggregation. */
  val state = new Rule[SparkPlan] {

    override def apply(plan: SparkPlan): SparkPlan = plan transform {
      case StateStoreSaveExec(keys, None, None, None, stateFormatVersion,
      UnaryExecNode(agg,
      StateStoreRestoreExec(_, None, _, child))) =>
        val aggStateInfo = nextStatefulOperationStateInfo
        StateStoreSaveExec(
          keys,
          Some(aggStateInfo),
          Some(outputMode),
          Some(offsetSeqMetadata.batchWatermarkMs),
          stateFormatVersion,
          agg.withNewChildren(
            StateStoreRestoreExec(
              keys,
              Some(aggStateInfo),
              stateFormatVersion,
              child) :: Nil))

      case StreamingDeduplicateExec(keys, child, None, None) =>
        StreamingDeduplicateExec(
          keys,
          child,
          Some(nextStatefulOperationStateInfo),
          Some(offsetSeqMetadata.batchWatermarkMs))

      case m: FlatMapGroupsWithStateExec =>
        m.copy(
          stateInfo = Some(nextStatefulOperationStateInfo),
          batchTimestampMs = Some(offsetSeqMetadata.batchTimestampMs),
          eventTimeWatermark = Some(offsetSeqMetadata.batchWatermarkMs))

      case j: StreamingSymmetricHashJoinExec =>
        j.copy(
          stateInfo = Some(nextStatefulOperationStateInfo),
          eventTimeWatermark = Some(offsetSeqMetadata.batchWatermarkMs),
          stateWatermarkPredicates =
            StreamingSymmetricHashJoinHelper.getStateWatermarkPredicates(
              j.left.output, j.right.output, j.leftKeys, j.rightKeys, j.condition.full,
              Some(offsetSeqMetadata.batchWatermarkMs)))

      case l: StreamingGlobalLimitExec =>
        l.copy(
          stateInfo = Some(nextStatefulOperationStateInfo),
          outputMode = Some(outputMode))
    }
  }

  override def preparations: Seq[Rule[SparkPlan]] = state +: super.preparations

  /** No need assert supported, as this check has already been done */
  override def assertSupported(): Unit = { }

  /**
   * Should the MicroBatchExecution run another batch based on this execution and the current
   * updated metadata.
   */
  def shouldRunAnotherBatch(newMetadata: OffsetSeqMetadata): Boolean = {
    executedPlan.collect {
      case p: StateStoreWriter => p.shouldRunAnotherBatch(newMetadata)
    }.exists(_ == true)
  }
}
