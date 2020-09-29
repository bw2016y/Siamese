package org.apache.spark.daslab.sql.execution

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.Attribute
import org.apache.spark.daslab.sql.engine.expressions.codegen.CodegenContext
import org.apache.spark.daslab.sql.engine.plans.logical.{Confidence, ErrorRate}
import org.apache.spark.rdd.RDD


case class  AqpSampleExec(errorRate: ErrorRate,
                             confidence: Confidence,
                             seed : Long ,
                             child: SparkPlan
                            )extends UnaryExecNode  with CodegenSupport {
  /**
    * Returns all the RDDs of InternalRow which generates the input rows.
    *
    * @note Right now we support up to two RDDs
    */
  override def inputRDDs(): Seq[RDD[InternalRow]] = ???

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
  override protected def doProduce(ctx: CodegenContext): String = ???

  /**
    * Produces the result of the query as an `RDD[InternalRow]`
    *
    * Overridden by concrete implementations of SparkPlan.
    */
  override protected def doExecute(): RDD[InternalRow] = ???

  override def output: Seq[Attribute] = child.output
}

