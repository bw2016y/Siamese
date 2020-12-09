package org.apache.spark.daslab.sql.engine.optimizer

import org.apache.spark.daslab.sql.engine.expressions.aggregate.{AggregateExpression, Average, Count, Sum}
import org.apache.spark.daslab.sql.engine.expressions.{Expression, NamedExpression}

import scala.collection.mutable
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.engine.rules.Rule
import org.apache.spark.daslab.sql.engine.plans
import org.apache.spark.daslab.sql.engine.plans.logical._
import org.apache.spark.daslab.sql.engine.trees.TreeNodeTag



//import scala.collection.mutable.Set
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
                //todo 后续我希望把所有涉及到对AQP聚合函数的改变都放在这里
                //todo  当前只是在这里传入置信区间和错误率

                val aqpSample=AqpSample(errorRate,confidence,(math.random * 1000).toInt,child,Set(),Set(),1.0,1.0)
                aggExps.foreach(
                   aggExp => {
                      aggExp.foreach{
                        case aggregateExpression : AggregateExpression =>
                          // 目前只有avg/sum/count需要传入
                          // 其实weight也应该设置在这里
                          aggregateExpression.aggregateFunction match {

                            case sum: Sum =>
                             // sum.appendWeight(aqpSample.newAtt)
                              sum.errorRate = errorRate
                              sum.confidence = confidence
                            case avg: Average =>
                              avg.errorRate=errorRate
                              avg.confidence = confidence
                            case count: Count =>
                              count.errorRate = errorRate
                              count.confidence = confidence
                            case _  =>
                          }

                          // 不是聚合表达式就什么也不做
                        case _ =>
                      }
                   }
                )


              Aggregate(groupExps,aggExps,aqpSample)
              }
        case _ => plan
    }
  }
}

object PushDownSampler extends Rule[LogicalPlan] {
  override def apply(plan: LogicalPlan): LogicalPlan = plan transform{
    case aqp @ AqpSample(errorRate,confidence,seed,child,stratificationSet,universeSet,ds,sfm) =>
      child match{
        // TODO 对Filter有特殊处理
        case filter @ Filter (_,grandChild) =>
          println("Pushing down Sampler through Filter")
          filter.copy(child=AqpSample(errorRate,confidence,seed,grandChild,stratificationSet,universeSet,ds,sfm))
        // TODO 对Join有特殊处理
        case join @ Join(left,right,joinType,condition) =>
          println("Pushing down Sampler through Join")
          join.copy(
            left,
            //left=AqpSample(errorRate,confidence,seed,left),
           // right,
            right=AqpSample(errorRate,confidence,seed,right,stratificationSet,universeSet,ds,sfm),
            joinType,
            condition
          )
          // TODO 下推Project
        case project @ Project(projectList:Seq[NamedExpression],grandchild:LogicalPlan) =>
            println("Pushing down Sampler through Project")
            project.copy(projectList,child=AqpSample(errorRate,confidence,seed,grandchild,stratificationSet,universeSet,ds,sfm))
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

object DfsPushDown{
  val res=Seq.empty
  def gen(plan:LogicalPlan): Seq[LogicalPlan]={
        plan.setTagValue(TreeNodeTag[String]("insert"),"insert here")
        dfs(plan)
        val cplan: LogicalPlan = plan.clone()
        dfs(cplan)
        res :+ plan

  }
  def dfs(plan:LogicalPlan): Unit = {

      println("plan name   "+plan.getClass)
      println("tags value  "+plan.getTagValue(TreeNodeTag[String]("insert")).getOrElse("null"))
      plan.children.foreach(dfs(_))
  }
}