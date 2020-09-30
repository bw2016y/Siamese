package org.apache.spark.daslab.sql.engine.optimizer

import org.apache.spark.daslab.sql.engine.expressions.{Expression, NamedExpression}

import scala.collection.mutable
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.engine.rules.Rule
import org.apache.spark.daslab.sql.engine.plans
import org.apache.spark.daslab.sql.engine.plans.logical._
/**
  *   在Aggregator算子下插入采样器
  *
  */
object InsertSampler extends Rule[LogicalPlan] {
  /**
    * @param plan
    * @return
    */

  override def apply(plan: LogicalPlan): LogicalPlan = {
    plan match{
        case aqpinfo @ AqpInfo(errorRate:ErrorRate,confidence:Confidence,child:LogicalPlan)=> aqpinfo.child transform{
              //AQP QUERY
              case  agg @  Aggregate(groupExps :Seq[Expression] ,aggExps: Seq[NamedExpression] ,child: LogicalPlan) =>
              Aggregate(groupExps,aggExps,AqpSample(errorRate,confidence,(math.random * 1000).toInt,child))
              }
        case _ => plan
    }
  }
}

object PushDownSampler extends Rule[LogicalPlan] {
  override def apply(plan: LogicalPlan): LogicalPlan = plan transform{
    case aqp @ AqpSample(errorRate,confidence,seed,child) =>
      child match{
        // TODO 对Filter有特殊处理
        case filter @ Filter (_,grandChild) =>
          println("Pushing down Sampler through Filter")
          filter.copy(child=AqpSample(errorRate,confidence,seed,grandChild))
        // TODO 对Join有特殊处理
        case join @ Join(left,right,joinType,condition) =>
          println("Pushing down Sampler through Join")
          join.copy(left=AqpSample(errorRate,confidence,seed,left),
            right=AqpSample(errorRate,confidence,seed,right)
          )
          // TODO 下推Project
        case project @ Project(projectList:Seq[NamedExpression],grandchild:LogicalPlan) =>
            println("Pushing down Sampler through Project")
            project.copy(projectList,child=AqpSample(errorRate,confidence,seed,grandchild))
        case _ => aqp
      }
   /* case aqp @ AqpSample(errorRate, confidence, seed, logicalPlan ) =>
      logicalPlan.mapChildren(child => AqpSample(errorRate, confidence, seed, child))*/

  }
}


/**
  * 修改AQPInfo节点的位置
  */
object FindAQPInfo extends Rule[LogicalPlan] {
  /**
    * @param plan
    * @return
    */
  override def apply(plan: LogicalPlan): LogicalPlan ={
    var infoNode:AqpInfo=null
    var after=plan.transform{
      case aqp @ AqpInfo(errorRate,confidence,child) =>
        infoNode=aqp
        child
    }
    if(infoNode!=null){
       AqpInfo(infoNode.errorRate,infoNode.confidence,after)
    }else{
      plan
    }
  }
}