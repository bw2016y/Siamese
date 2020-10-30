package org.apache.spark.daslab.sql.execution

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.codegen.{CodegenContext, ExprCode}
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, AttributeSet, NamedExpression, UnsafeRow}
import org.apache.spark.daslab.sql.engine.plans.logical.{Confidence, ErrorRate}
import org.apache.spark.daslab.sql.engine.plans.physical.Partitioning
import org.apache.spark.daslab.sql.execution.metric.{SQLMetric, SQLMetrics}
import org.apache.spark.daslab.sql.execution.util.{DistinctColumn, SampleUtils}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.random.BernoulliCellSampler

/**
  *  目前只是写死作为一个uniform采样器使用
  * @param errorRate
  * @param confidence
  * @param seed
  * @param child
  */
case class  DistinctSamplerExec(errorRate: ErrorRate,
                                confidence: Confidence,
                                seed : Long,
                                child: SparkPlan,
                                S: Seq[DistinctColumn],
                                delta: Int,
                                weight: NamedExpression
                            )extends UnaryExecNode
//  with CodegenSupport
{


  /** Specifies how data is partitioned across different nodes in the cluster. */
  // 当前不改变分区情况
  override def outputPartitioning: Partitioning = child.outputPartitioning


  /**
    * Produces the result of the query as an `RDD[InternalRow]`
    *
    * Overridden by concrete implementations of SparkPlan.
    */
  override protected def doExecute(): RDD[InternalRow] = {
    val numPartitions: Int = child.outputPartitioning.numPartitions
    SampleUtils.distinctSample(child.execute(), S, delta, 0.3, 1, seed)


    /*child.execute().mapPartitionsWithIndexInternal{
      (index,iter) =>
        val
    }*/

  }


  /**
    * The subset of inputSet those should be evaluated before this plan.
    *
    * We will use this to insert some code to access those columns that are actually used by current
    * plan before calling doConsume().
    */
//  override def usedInputs: AttributeSet = AttributeSet.empty

  /**
    * @return All metrics containing metrics of this SparkPlan.
    */
  override def metrics: Map[String, SQLMetric] = Map(
    "numOutputRows" -> SQLMetrics.createMetric(sparkContext,"number of output rows")
  )



  // 输出的结构信息
  // 这里添加了权重列
  override def output: Seq[Attribute] = child.output :+ weight.toAttribute
}

