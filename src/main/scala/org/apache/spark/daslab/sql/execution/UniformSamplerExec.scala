package org.apache.spark.daslab.sql.execution

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, AttributeSet, NamedExpression, UnsafeProjection, UnsafeRow}
import org.apache.spark.daslab.sql.engine.expressions.codegen.{CodegenContext, ExprCode}
import org.apache.spark.daslab.sql.engine.plans.logical.{Confidence, ErrorRate}
import org.apache.spark.daslab.sql.engine.plans.physical.Partitioning
import org.apache.spark.daslab.sql.execution.metric.{SQLMetric, SQLMetrics}
import org.apache.spark.daslab.sql.execution.util.SampleUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.util.random.BernoulliCellSampler

/**
  *  目前只是写死作为一个uniform采样器使用
  * @param errorRate
  * @param confidence
  * @param seed
  * @param child
  */
case class  UniformSamplerExec(errorRate: ErrorRate,
                             confidence: Confidence,
                             seed : Long ,
                             child: SparkPlan,
                             weight: NamedExpression
                              )extends UnaryExecNode  with CodegenSupport {


  /** Specifies how data is partitioned across different nodes in the cluster. */
  // 当前不改变分区情况
  override def outputPartitioning: Partitioning = child.outputPartitioning


  /**
    * Produces the result of the query as an `RDD[InternalRow]`
    *
    * Overridden by concrete implementations of SparkPlan.
    */
  override protected def doExecute(): RDD[InternalRow] = {
    child.execute().mapPartitionsWithIndex{
      (index, iter) =>
        val appender = UnsafeProjection.create(child.output :+ weight, child.output, subexpressionEliminationEnabled)
        SampleUtils.uniformSample(index, iter, 0.3, seed)
    }
  }




  /**
    * The subset of inputSet those should be evaluated before this plan.
    *
    * We will use this to insert some code to access those columns that are actually used by current
    * plan before calling doConsume().
    */
  override def usedInputs: AttributeSet = AttributeSet.empty



  /**
    * Returns all the RDDs of InternalRow which generates the input rows.
    *
    * @note Right now we support up to two RDDs
    */
  override def inputRDDs(): Seq[RDD[InternalRow]] = {
    child.asInstanceOf[CodegenSupport].inputRDDs()
  }

  /**
    * Whether or not the result rows of this operator should be copied before putting into a buffer.
    *
    * If any operator inside WholeStageCodegen generate multiple rows from a single row (for
    * example, Join), this should be true.
    *
    * If an operator starts a new pipeline, this should be false.
    */
  override def needCopyResult: Boolean = {
    child.asInstanceOf[CodegenSupport].needCopyResult
  }

  /**
    * Generate the Java source code to process, should be overridden by subclass to support codegen.
    *
    * doProduce() usually generate the framework, for example, aggregation could generate this:
    *
    * if (!initialized) {
    * # create a hash map, then build the aggregation hash map
    * # call child.produce()
    * initialized = true;
    * }
    * while (hashmap.hasNext()) {
    * row = hashmap.next();
    * # build the aggregation results
    * # create variables for results
    * # call consume(), which will call parent.doConsume()
    * if (shouldStop()) return;
    * }
    */
  override protected def doProduce(ctx: CodegenContext): String = {
    child.asInstanceOf[CodegenSupport].produce(ctx,this)
  }


  /**
    * @return All metrics containing metrics of this SparkPlan.
    */
  override def metrics: Map[String, SQLMetric] = Map(
    "numOutputRows" -> SQLMetrics.createMetric(sparkContext,"number of output rows")
  )

  /**
    * Generate the Java source code to process the rows from child SparkPlan. This should only be
    * called from `consume`.
    *
    * This should be override by subclass to support codegen.
    *
    * Note: The operator should not assume the existence of an outer processing loop,
    * which it can jump from with "continue;"!
    *
    * For example, filter could generate this:
    * # code to evaluate the predicate expression, result is isNull1 and value2
    * if (!isNull1 && value2) {
    * # call consume(), which will call parent.doConsume()
    * }
    *
    * Note: A plan can either consume the rows as UnsafeRow (row), or a list of variables (input).
    * When consuming as a listing of variables, the code to produce the input is already
    * generated and `CodegenContext.currentVars` is already set. When consuming as UnsafeRow,
    * implementations need to put `row.code` in the generated code and set
    * `CodegenContext.INPUT_ROW` manually. Some plans may need more tweaks as they have
    * different inputs(join build side, aggregate buffer, etc.), or other special cases.
    */
  override def doConsume(ctx: CodegenContext, input: Seq[ExprCode], row: ExprCode): String = {
     val numOutput = metricTerm(ctx,"numOutputRows")

     val samplerClass = classOf[BernoulliCellSampler[UnsafeRow]].getName
     val sampler = ctx.addMutableState(s"$samplerClass<UnsafeRow>","sampler",
       v =>
         s"""
            | $v = new $samplerClass<UnsafeRow>(0.0,0.3,false);
            | $v.setSeed(${seed}L+partitionIndex);
          """.stripMargin.trim
     )

    s"""
       | if($sampler.sample()!=0 ){
       |   $numOutput.add(1);
       |   ${consume(ctx,input)}
       | }
     """.stripMargin.trim

  }

  // 输出的结构信息
  override def output: Seq[Attribute] = child.output
}

