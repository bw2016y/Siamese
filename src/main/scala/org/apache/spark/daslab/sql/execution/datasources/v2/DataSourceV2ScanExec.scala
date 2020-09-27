package org.apache.spark.daslab.sql.execution.datasources.v2



import scala.collection.JavaConverters._

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.physical
import org.apache.spark.daslab.sql.engine.plans.physical.SinglePartition
import org.apache.spark.daslab.sql.execution.{ColumnarBatchScan, LeafExecNode, WholeStageCodegenExec}
import org.apache.spark.daslab.sql.execution.streaming.continuous._
import org.apache.spark.daslab.sql.sources.v2.DataSourceV2
import org.apache.spark.daslab.sql.sources.v2.reader._
import org.apache.spark.daslab.sql.sources.v2.reader.streaming.ContinuousReader
import org.apache.spark.daslab.sql.vectorized.ColumnarBatch

//todo
import org.apache.spark.rdd.RDD


/**
 * Physical plan node for scanning data from a data source.
 */
case class DataSourceV2ScanExec(
                                 output: Seq[AttributeReference],
                                 @transient source: DataSourceV2,
                                 @transient options: Map[String, String],
                                 @transient pushedFilters: Seq[Expression],
                                 @transient reader: DataSourceReader)
  extends LeafExecNode with DataSourceV2StringFormat with ColumnarBatchScan {

  override def simpleString: String = "ScanV2 " + metadataString

  // TODO: unify the equal/hashCode implementation for all data source v2 query plans.
  override def equals(other: Any): Boolean = other match {
    case other: DataSourceV2ScanExec =>
      output == other.output && reader.getClass == other.reader.getClass && options == other.options
    case _ => false
  }

  override def hashCode(): Int = {
    Seq(output, source, options).hashCode()
  }

  override def outputPartitioning: physical.Partitioning = reader match {
    case r: SupportsScanColumnarBatch if r.enableBatchRead() && batchPartitions.size == 1 =>
      SinglePartition

    case r: SupportsScanColumnarBatch if !r.enableBatchRead() && partitions.size == 1 =>
      SinglePartition

    case r if !r.isInstanceOf[SupportsScanColumnarBatch] && partitions.size == 1 =>
      SinglePartition

    case s: SupportsReportPartitioning =>
      new DataSourcePartitioning(
        s.outputPartitioning(), AttributeMap(output.map(a => a -> a.name)))

    case _ => super.outputPartitioning
  }

  private lazy val partitions: Seq[InputPartition[InternalRow]] = {
    reader.planInputPartitions().asScala
  }

  private lazy val batchPartitions: Seq[InputPartition[ColumnarBatch]] = reader match {
    case r: SupportsScanColumnarBatch if r.enableBatchRead() =>
      assert(!reader.isInstanceOf[ContinuousReader],
        "continuous stream reader does not support columnar read yet.")
      r.planBatchInputPartitions().asScala
  }

  private lazy val inputRDD: RDD[InternalRow] = reader match {
    case _: ContinuousReader =>
      EpochCoordinatorRef.get(
        sparkContext.getLocalProperty(ContinuousExecution.EPOCH_COORDINATOR_ID_KEY),
        sparkContext.env)
        .askSync[Unit](SetReaderPartitions(partitions.size))
      new ContinuousDataSourceRDD(
        sparkContext,
        sqlContext.conf.continuousStreamingExecutorQueueSize,
        sqlContext.conf.continuousStreamingExecutorPollIntervalMs,
        partitions).asInstanceOf[RDD[InternalRow]]

    case r: SupportsScanColumnarBatch if r.enableBatchRead() =>
      new DataSourceRDD(sparkContext, batchPartitions).asInstanceOf[RDD[InternalRow]]

    case _ =>
      new DataSourceRDD(sparkContext, partitions).asInstanceOf[RDD[InternalRow]]
  }

  override def inputRDDs(): Seq[RDD[InternalRow]] = Seq(inputRDD)

  override val supportsBatch: Boolean = reader match {
    case r: SupportsScanColumnarBatch if r.enableBatchRead() => true
    case _ => false
  }

  override protected def needsUnsafeRowConversion: Boolean = false

  override protected def doExecute(): RDD[InternalRow] = {
    if (supportsBatch) {
      WholeStageCodegenExec(this)(codegenStageId = 0).execute()
    } else {
      val numOutputRows = longMetric("numOutputRows")
      inputRDD.map { r =>
        numOutputRows += 1
        r
      }
    }
  }
}
