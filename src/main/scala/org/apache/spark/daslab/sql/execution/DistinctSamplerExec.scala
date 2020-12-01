package org.apache.spark.daslab.sql.execution

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.codegen.{CodegenContext, ExprCode}
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, AttributeSet, NamedExpression, UnsafeProjection, UnsafeRow}
import org.apache.spark.daslab.sql.engine.plans.logical.{Confidence, ErrorRate}
import org.apache.spark.daslab.sql.engine.plans.physical.Partitioning
import org.apache.spark.daslab.sql.execution.metric.{SQLMetric, SQLMetrics}
import org.apache.spark.daslab.sql.execution.util.{DistinctColumn, SampleUtils}
import org.apache.spark.daslab.sql.types.DoubleType
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
    *
    * todo  rewrite this method to add physical weight column
    */
  override protected def doExecute(): RDD[InternalRow] = {
    val numPartitions: Int = child.outputPartitioning.numPartitions
    child.execute().mapPartitionsWithIndex{
      (index, iter) =>
        val appender = UnsafeProjection.create(child.output :+ weight, child.output, subexpressionEliminationEnabled)
        val rows: Iterator[InternalRow] = SampleUtils.distinctSample(index, iter.map(appender), S, delta, 0.3, 1, seed)
        rows.filter{ row =>
          println(row.getString(1) + " " + row.getLong(0) + " " + row.getDouble(row.numFields-1))
          true
        }
    }

    //todo add weight
//    (child.output:+weight).foreach(println)
//    val temp = res.mapPartitionsWithIndexInternal {
//      (index, iter) =>
//        val append = UnsafeProjection.create(child.output :+ weight, child.output, subexpressionEliminationEnabled)
//        append.initialize(index)
//        iter.map(append).filter(row => {
//          // row.setDouble(row.numFields-1,0.7)
//           row.asInstanceOf[UnsafeRow].setDouble(row.numFields-1,0.8)
//       //    row.setDouble(4,0.5)
//        //  (child.output :+ weight).zipWithIndex.foreach{case (exp,ti) => println(ti+"  "+row.get(ti,exp.dataType))}
//        //   row.setDouble(4,0.1)
//        //  (child.output :+ weight).zipWithIndex.foreach{case (exp,ti) => println(ti+"  "+row.get(ti,exp.dataType))}
//          true
//        })
//    }
   // println(temp.count())
  /*  println("todo")
    println(child.output.length)
    child.output.foreach(println)
    def trans(row:InternalRow):InternalRow={

       val  preRowByteSize=UnsafeRow.calculateBitSetWidthInBytes(row.numFields+1) + row.asInstanceOf[UnsafeRow].getSizeInBytes + DoubleType.defaultSize
       val  newRow= UnsafeRow.createFromByteArray(preRowByteSize,row.numFields+1)
       newRow.copyFrom(row.asInstanceOf[UnsafeRow])
      // newRow.setDouble(row.numFields,1.00)
       newRow
    }

    val temp: RDD[InternalRow] = res.mapPartitionsWithIndex(
      {(index, iter) => {
        // iter.foreach(row => println(row.numFields))

           iter.map(trans).filter(row=>{
              println(row.getInt(0))
             true
           })

      }}, isOrderSensitive=true,preservesPartitioning = true
    )
    println(temp.count())
    val work: RDD[InternalRow] = temp.mapPartitionsWithIndex(
      (index, iter) => {
        iter.filter((row)=>{


          println(row.asInstanceOf[UnsafeRow].numFields())


          println(row.asInstanceOf[UnsafeRow].toString)

          true
        })
      }
    )


    println(work.count())
    println("return ")
    */
    // return
    //res
//    temp
   /* res.mapPartitionsWithIndexInternal{
      (index,iter) => {
        val append = UnsafeProjection.create(weight,child.output, subexpressionEliminationEnabled )
        append.initialize(index)
        iter.map(append)
      }
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

