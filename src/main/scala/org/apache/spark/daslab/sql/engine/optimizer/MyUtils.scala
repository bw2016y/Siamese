package org.apache.spark.daslab.sql.engine.optimizer

import org.apache.spark.daslab.sql.engine.expressions.{And, AttributeReference, Expression}
import org.apache.spark.daslab.sql.engine.plans.logical.{AqpSample, LogicalPlan}
import org.apache.spark.daslab.sql.execution.util.DistinctColumn
import org.apache.spark.daslab.sql.execution.{DistinctSamplerExec, PlanLater, SparkPlan, UniformSamplerExec}
import org.apache.spark.daslab.sql.types.StringType

object MyUtils {
    var FRACTION=1.0
    var DELTA = 1
    var PARALLELNUMS = 1
    def setFraction(frac:Double)={
      MyUtils.FRACTION=frac
    }
    def setDelta(delta:Int)={
      MyUtils.DELTA=delta
    }
    def setParallelNums(para:Int)={
    MyUtils.PARALLELNUMS=para
    }
    def splitConjunctivePredicates(condition:Expression): Seq[Expression] = {
      condition match{
        case And(cond1,cond2) =>
          splitConjunctivePredicates(cond1) ++ splitConjunctivePredicates(cond2)
        case other => other :: Nil
      }
    }

  /**
   * 根据 AqpSample的状态来选择具体的物理采样器的实现（UniformSamplerExec/DistinctSamplerExec）
   *
   * @param plan
   * @return
   */
    def choosePhysicalSampler(plan : AqpSample) : Seq[SparkPlan] = {
      println("choosing plan child output"+plan.child.output)
      plan.child.output.zipWithIndex.foreach{
        case (c,index) =>
          println("index"+index+"dataType"+c.dataType+"name"+c.name)
      }
       if(plan.stratificationSet.nonEmpty){
         // Seq[DistinctColumn]
         // case class DistinctColumn(ordinal:Int,datatype: DataType, name: String)
         //  (child.output :+ weight).zipWithIndex.foreach{case (exp,ti) => println(ti+"  "+row.get(ti,exp.dataType))}



         var list: List[DistinctColumn]=List()
         plan.stratificationSet.foreach( s => {
           plan.child.output.zipWithIndex.foreach{
             case (c,index) =>
               println("index"+index+"dataType"+c.dataType+"name"+c.name)
               if(c.asInstanceOf[AttributeReference].exprId.id == s.asInstanceOf[AttributeReference].exprId.id){
                  list = (list ::: List(new DistinctColumn(index,c.dataType,c.name)))
               }
           }
         })
         println("Distinct List......------------"+list)
        //List(new DistinctColumn(1,StringType,"name"))
         DistinctSamplerExec(plan.errorRate,plan.confidence,plan.seed,PlanLater(plan.child),list ,MyUtils.DELTA,MyUtils.FRACTION,MyUtils.PARALLELNUMS,plan.nameE)::Nil
         //选择分层采样器
       }else{
         //选择均匀采样器
         UniformSamplerExec(plan.errorRate,plan.confidence,plan.seed, PlanLater(plan.child),plan.nameE,MyUtils.FRACTION)::Nil
       }
    }
}
