package org.apache.spark.daslab.sql.engine.optimizer

import org.apache.spark.daslab.sql.engine.expressions.aggregate.{AggregateExpression, Average, Count, Sum}
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, Expression, NamedExpression}

import scala.collection.mutable
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.engine.rules.Rule
import org.apache.spark.daslab.sql.engine.plans
import org.apache.spark.daslab.sql.engine.plans.logical._
import org.apache.spark.daslab.sql.engine.trees.{TreeNode, TreeNodeTag}



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
  var res:Seq[LogicalPlan]=Seq.empty
  var sampleStatus : AqpSample  = null
  var treeToPush : LogicalPlan = null
  def gen(plan:LogicalPlan): Seq[LogicalPlan]={
        //reset status
        res= (Seq.empty:+plan)
        sampleStatus = null
        treeToPush = null


        val rootPlan: LogicalPlan = checkAqpSample(plan)
        if(sampleStatus!=null){
          //存在Aqp信息需要处理
          println("sampleStatus"+sampleStatus)
          println("treeToPush"+treeToPush)
          println("rootPlan"+rootPlan)
          val ds: Double = sampleStatus.ds
          val sfm: Double = sampleStatus.sfm
          val stratificationSet: Set[Attribute] = sampleStatus.stratificationSet
          val universeSet: Set[Attribute] = sampleStatus.universeSet
          dfs(treeToPush,ds,sfm,stratificationSet,universeSet,rootPlan)


           println("final gen"+res.length)
          // Seq.empty :+ plan
          res

        }else{
          //无需处理
         //   res :+ plan
            Seq.empty:+plan
        }


  }
  def construct(root: LogicalPlan, sub: LogicalPlan,isLeft:Boolean=true): Unit ={
    val newPlan: LogicalPlan = root.transform {
      case project@Project(projectList: Seq[NamedExpression], grandChild: LogicalPlan) =>
        if (project.getTagValue(TreeNodeTag[String]("insert")).nonEmpty) {
          project.copy(projectList, sub)
        } else {
          project
        }
      case filter@Filter(condition, grandChild) =>
        if (filter.getTagValue(TreeNodeTag[String]("insert")).nonEmpty) {
          filter.copy(condition, sub)
        } else {
          filter
        }
      case join@Join(left, right, joinType, condition) =>
        if (join.getTagValue(TreeNodeTag[String]("insert")).nonEmpty) {
          if (isLeft) {
            join.copy(sub, right, joinType, condition)
          } else {
            join.copy(left, sub, joinType, condition)
          }
        } else {
          join
        }
    }

    res=(res:+newPlan)

    println("construct............."+res.length)
    println(newPlan)

  }
  def dfs(plan:LogicalPlan,ds: Double ,sfm: Double , sSet:Set[Attribute],uSet:Set[Attribute],rootPlan: LogicalPlan): Unit = {

/*
      println("plan name   "+plan.getClass)
      println("tags value  "+plan.getTagValue(TreeNodeTag[String]("insert")).getOrElse("null"))*/

      plan match {
        case project @ Project(projectList:Seq[NamedExpression],grandChild:LogicalPlan) =>
          project.setTagValue(TreeNodeTag[String]("insert"),"push from this")
          val copiedPlan: LogicalPlan = rootPlan.clone()
          val copiedSubPlan: LogicalPlan = AqpSample(sampleStatus.errorRate,sampleStatus.confidence,sampleStatus.seed,grandChild,sSet,uSet,ds,sfm).clone()
          construct(copiedPlan,copiedSubPlan)
          project.unsetTagValue(TreeNodeTag[String]("insert"))
          dfs(grandChild,ds,sfm,sSet,uSet,rootPlan)

        case filter @ Filter(condition:Expression,grandChild) =>
          filter.setTagValue(TreeNodeTag[String]("insert"),"push from this")
          println("filter conditions"+condition.references)
          var newS=sSet.toSet[Attribute]
          condition.references.iterator.foreach(a => {
            newS= (newS+ a)
          })
          println(newS)
          val copiedPlan: LogicalPlan = rootPlan.clone()
          val copiedSubPlan: LogicalPlan = AqpSample(sampleStatus.errorRate,sampleStatus.confidence,sampleStatus.seed,grandChild,sSet,uSet,ds,sfm).clone()
          construct(copiedPlan,copiedSubPlan)
          filter.unsetTagValue(TreeNodeTag[String]("insert"))
          dfs(grandChild,ds,sfm,sSet,uSet,rootPlan)

        case join @ Join(left,right,joinType,condition) =>
          join.setTagValue(TreeNodeTag[String]("insert"),"push from this")
          val copiedPlan: LogicalPlan = rootPlan.clone()
          val copiedLeft: LogicalPlan = AqpSample(sampleStatus.errorRate,sampleStatus.confidence,sampleStatus.seed,left,sSet,uSet,ds,sfm).clone()
          val copiedRight: LogicalPlan = AqpSample(sampleStatus.errorRate,sampleStatus.confidence,sampleStatus.seed,right,sSet,uSet,ds,sfm).clone()
          construct(copiedPlan,copiedLeft,true)
          construct(copiedPlan,copiedRight,false)
          join.unsetTagValue(TreeNodeTag[String]("insert"))
          dfs(left,ds,sfm,sSet,uSet,rootPlan)
          dfs(right,ds,sfm,sSet,uSet,rootPlan)

        case _ =>
      }

     // plan.children.foreach(dfs(_))
  }
  def checkAqpSample(plan: LogicalPlan):LogicalPlan= plan transform{
    case  agg @  Aggregate(groupExps :Seq[Expression] ,aggExps: Seq[NamedExpression] ,child: LogicalPlan) =>
      child match{
        case aqp @ AqpSample(errorRate,confidence,seed,child,stratificationSet,universeSet,ds,sfm) =>

          sampleStatus=aqp
          treeToPush= child
          Aggregate(groupExps,aggExps,child)

        case _ => agg
      }

  }

}