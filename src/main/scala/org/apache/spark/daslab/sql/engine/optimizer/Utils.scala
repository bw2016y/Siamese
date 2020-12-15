package org.apache.spark.daslab.sql.engine.optimizer

import org.apache.spark.daslab.sql.engine.expressions.{And, Expression}
import org.apache.spark.daslab.sql.engine.plans.logical.{AqpSample, LogicalPlan}
import org.apache.spark.daslab.sql.execution.util.DistinctColumn
import org.apache.spark.daslab.sql.execution.{DistinctSamplerExec, PlanLater, SparkPlan, UniformSamplerExec}
import org.apache.spark.daslab.sql.types.StringType

object Utils {
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
       if(plan.stratificationSet.nonEmpty){
         // Seq[DistinctColumn]
         // case class DistinctColumn(ordinal:Int,datatype: DataType, name: String)
         DistinctSamplerExec(plan.errorRate,plan.confidence,plan.seed,PlanLater(plan.child),List(new DistinctColumn(1,StringType,"name")),1,plan.nameE)::Nil
         //选择分层采样器
       }else{
         //选择均匀采样器
         UniformSamplerExec(plan.errorRate,plan.confidence,plan.seed, PlanLater(plan.child),plan.nameE,0.2)::Nil
       }
    }
}
