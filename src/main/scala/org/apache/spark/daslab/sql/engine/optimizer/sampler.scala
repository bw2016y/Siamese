package org.apache.spark.daslab.sql.engine.optimizer

import org.apache.spark.daslab.sql.engine.expressions.aggregate.{AggregateExpression, Average, Count, Sum}
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, AttributeReference, Expression, NamedExpression}

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
                println("gourping expression....."+groupExps)
                var groupSet : Set[Attribute]=Set()
                groupExps.foreach( e =>{
                   groupSet = (groupSet+e.asInstanceOf[Attribute])
                })
                println(groupSet)
                val aqpSample=AqpSample(errorRate,confidence,(math.random * 1000).toInt,child,groupSet,Set(),1.0,1.0,MyUtils.FRACTION,MyUtils.DELTA,MyUtils.PARALLELNUMS)
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
    case aqp @ AqpSample(errorRate,confidence,seed,child,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel) =>
      child match{
        // TODO 对Filter有特殊处理
        case filter @ Filter (_,grandChild) =>
          println("Pushing down Sampler through Filter")
          filter.copy(child=AqpSample(errorRate,confidence,seed,grandChild,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel))
        // TODO 对Join有特殊处理
        case join @ Join(left,right,joinType,condition) =>
          println("Pushing down Sampler through Join")
          join.copy(
            left,
            //left=AqpSample(errorRate,confidence,seed,left),
           // right,
            right=AqpSample(errorRate,confidence,seed,right,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel),
            joinType,
            condition
          )
          // TODO 下推Project
        case project @ Project(projectList:Seq[NamedExpression],grandchild:LogicalPlan) =>
            println("Pushing down Sampler through Project")
            project.copy(projectList,child=AqpSample(errorRate,confidence,seed,grandchild,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel))
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

          // sampleFraction , delta , parallel

          val sampleFraction :Double = sampleStatus.sampleFraction
          val delta: Int = sampleStatus.delta
          val parallel: Int = sampleStatus.parallel



          dfs(treeToPush,ds,sfm,stratificationSet,universeSet,sampleFraction,delta,parallel,rootPlan)


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
  def dfs(plan:LogicalPlan,ds: Double ,sfm: Double , sSet:Set[Attribute],uSet:Set[Attribute],sampleFrac:Double,delta:Int,parallel:Int,rootPlan: LogicalPlan): Unit = {

/*
      println("plan name   "+plan.getClass)
      println("tags value  "+plan.getTagValue(TreeNodeTag[String]("insert")).getOrElse("null"))*/

      plan match {
        case project @ Project(projectList:Seq[NamedExpression],grandChild:LogicalPlan) =>
          // todo 因为project不会影响到结果
          // 而且一般来说，采样器在Project算子之下可以节省运行时间
          // 但是有一定的概率会在 DistinctSampler 算子运行的时候导致更大的内存消耗 （需要tuning DistinctSampler的具体的参数）
          //

          /* project.setTagValue(TreeNodeTag[String]("insert"),"push from this")
           val copiedPlan: LogicalPlan = rootPlan.clone()
           val copiedSubPlan: LogicalPlan = AqpSample(sampleStatus.errorRate,sampleStatus.confidence,sampleStatus.seed,grandChild,sSet,uSet,ds,sfm,sampleFrac,delta,parallel).clone()
           construct(copiedPlan,copiedSubPlan)
           project.unsetTagValue(TreeNodeTag[String]("insert"))*/

          dfs(grandChild,ds,sfm,sSet,uSet,sampleFrac,delta,parallel,rootPlan)
        //todo 这里需要判断一下是不是SSet中已经包含了Filter涉及的列
        case filter @ Filter(condition:Expression,grandChild) =>
          //todo check
          val pre: String = condition.sql
          val sel: Double = MyUtils.getSel(pre)


          filter.setTagValue(TreeNodeTag[String]("insert"),"push from this")
          // println("filter conditions"+condition.references)
          var newS: Set[WrapAttribute] = sSet.toSet[Attribute].map( a => new WrapAttribute(a))
          condition.references.iterator.foreach(a => {
            newS= (newS + new WrapAttribute(a))
          })
          // println(newS)
          // S集合变化的情况
          val copiedPlan: LogicalPlan = rootPlan.clone()
          val copiedSubPlan: LogicalPlan = AqpSample(sampleStatus.errorRate,sampleStatus.confidence,sampleStatus.seed,grandChild,newS.map(wa => wa.att).toSet,uSet,ds,sfm,sampleFrac,delta,parallel ).clone()
          construct(copiedPlan,copiedSubPlan)
          // S集合不变换的情况 , 这种情况ds需要发生变化
          val copiedPlan1: LogicalPlan = rootPlan.clone()
          val copiedSubPlan1: LogicalPlan =  AqpSample(sampleStatus.errorRate,sampleStatus.confidence,sampleStatus.seed,grandChild,sSet,uSet,ds*sel,sfm,sampleFrac,delta,parallel ).clone()
          construct(copiedPlan1,copiedSubPlan1)

          filter.unsetTagValue(TreeNodeTag[String]("insert"))
          dfs(grandChild,ds*sel,sfm,sSet,uSet,sampleFrac,delta,parallel,rootPlan)
          dfs(grandChild,ds,sfm,newS.map(wa => wa.att).toSet,uSet,sampleFrac,delta,parallel,rootPlan)

        case join @ Join(left,right,joinType,condition) =>
          join.setTagValue(TreeNodeTag[String]("insert"),"push from this")
          println("Join type"+joinType)
          println("split"+MyUtils.splitConjunctivePredicates(condition.get).map( e=> e.references.toSeq))
          println("condition"+condition.get.references.toSeq)
          println("left"+left.output)
          println("right"+right.output)
          // build maps
          var newSfmLeft : Double = sfm
          var newSfmRight : Double = sfm

           val leftPart: Set[WrapAttribute] = left.output.toSet[Attribute].map(a=> new WrapAttribute(a))
           val rightPart: Set[WrapAttribute] = right.output.toSet[Attribute].map(a=> new WrapAttribute(a))

           //val leftPartId: Set[Long] = left.output.toSet.map(a => a.asInstanceOf[AttributeReference].exprId.id)
           //val rightPartId: Set[Long] = right.output.toSet.map(a=> a.asInstanceOf[AttributeReference].exprId.id)

           val l2rMap= mutable.Map[WrapAttribute,WrapAttribute]()
           val r2lMap= mutable.Map[WrapAttribute,WrapAttribute]()

          val cond: Seq[Seq[Attribute]] = MyUtils.splitConjunctivePredicates(condition.get).map(e=> e.references.toSeq)

          cond.foreach( pair => {
                if(leftPart.contains(  new WrapAttribute(pair.head) ) ){
                    l2rMap +=  ( new WrapAttribute(pair.head) -> new WrapAttribute(pair.last))
                    r2lMap +=  ( new WrapAttribute(pair.last) -> new WrapAttribute(pair.head))
                } else {
                    l2rMap += ( new WrapAttribute(pair.last) ->  new WrapAttribute(pair.head))
                    r2lMap += ( new WrapAttribute(pair.head) -> new WrapAttribute(pair.last))
                }
          })
          println("left part"+leftPart)
          println("right part"+rightPart)
       //   println("left partId"+leftPartId)
       //   println("right partId"+rightPartId)
          println("l2rMap"+l2rMap)
          println("r2lMap"+r2lMap)


          // push to left
           val fullSetLeft: mutable.Set[WrapAttribute] = mutable.Set[WrapAttribute]()
           sSet.foreach( a => {
              if(r2lMap.get(new WrapAttribute(a)).nonEmpty){
                fullSetLeft += r2lMap.get(new WrapAttribute(a)).get
              }else{
                fullSetLeft += new WrapAttribute(a)
              }
           })
           println("fullSetLeft********************"+fullSetLeft.toSet)
           // 当前能在左侧采样的列  todo
            //var sSetOnLeft : Set[Attribute] = leftPart.intersect(fullSetLeft.toSet)

          var sSetOnLeft : Set[WrapAttribute] = leftPart.intersect(fullSetLeft.toSet)
           //var sSetOnLeft : Set[Attribute] = (leftPart & (fullSetLeft.toSet))
           println("sSetOnLeft**********************"+sSetOnLeft)
           if((fullSetLeft.toSet -- sSetOnLeft.toSet).size>0){
              // 缺一部分可采样的列，需要将Join Key加入到分层采样的列集中
             // 这种情况会存在 miss group的问题

               // 还存在可以添加的Join Key
               if((l2rMap.toMap.keySet -- sSetOnLeft.toSet).size>0){

                   sSetOnLeft = (sSetOnLeft ++ l2rMap.toMap.keySet)
                    // todo 这里需要修改 sfm
                    // val ssssss: Set[WrapAttribute] = l2rMap.toMap.keySet -- sSetOnLeft.toSet

                    var dv1 :Double = MyUtils.getNumDV(l2rMap.toMap.keySet -- sSetOnLeft.toSet) // set L // left key
                    var dv2 :Double = MyUtils.getNumDV(fullSetLeft.toSet -- sSetOnLeft.toSet)  //  todo need to project to right  // we dont need to

                    var beforeProjectToRight : Set[WrapAttribute] = (l2rMap.toMap.keySet -- sSetOnLeft.toSet)
                    var afterProjectToRight : mutable.Set[WrapAttribute] = mutable.Set[WrapAttribute]()
                    beforeProjectToRight.foreach( col=>{
                        if(l2rMap.get(col).nonEmpty){
                           afterProjectToRight += l2rMap.get(col).get
                        } else{
                           afterProjectToRight += col
                        }
                    })



                    var dv3 :Double = MyUtils.getNumDV(afterProjectToRight.toSet) // todo  need to project to right

                    newSfmLeft  = sfm * ( math.min(dv1,dv2) / dv3 )

               }else{   // 不存在可以添加的Join Key了
                  sSetOnLeft = sSetOnLeft
               }
           }else{
              // 不缺少列，可以直接push到左侧 , 这种情况不会miss group
                sSetOnLeft = sSetOnLeft
           }

          // todo fix ds
          val KremLeft: Set[WrapAttribute] = (l2rMap.toMap.keySet -- sSetOnLeft.toSet)
          var kdv1Left :Double = MyUtils.getNumDV(KremLeft) // L

          var afterProjectToRightRem : mutable.Set[WrapAttribute] = mutable.Set[WrapAttribute]()
          KremLeft.foreach( col=>{
            if(l2rMap.get(col).nonEmpty){
              afterProjectToRightRem += l2rMap.get(col).get
            } else{
              afterProjectToRightRem += col
            }
          })



          var kdv2Left :Double = MyUtils.getNumDV(afterProjectToRightRem.toSet) // R need to project to right


          var newDsLeft = (ds / kdv1Left) * math.min(kdv1Left , kdv2Left)


          val copiedrootPlanLeft : LogicalPlan = rootPlan.clone()
          val copiedLeft: LogicalPlan = AqpSample(sampleStatus.errorRate,sampleStatus.confidence,sampleStatus.seed,left,sSetOnLeft.map(wa => wa.att).toSet,uSet,newDsLeft,newSfmLeft,sampleFrac,delta,parallel).clone()
          construct(copiedrootPlanLeft,copiedLeft,isLeft=true)


          // push to right start
          val fullSetRight: mutable.Set[WrapAttribute]=mutable.Set[WrapAttribute]()
          sSet.foreach( a => {
            if(l2rMap.get(new WrapAttribute(a)).nonEmpty){
              fullSetRight += l2rMap.get(new WrapAttribute(a)).get
            }else{
              fullSetRight += new WrapAttribute(a)
            }
          })
          // 当前可以在右侧采样的列
          var sSetOnRight : Set[WrapAttribute] = rightPart.intersect(fullSetRight.toSet)
          if((fullSetRight.toSet -- sSetOnRight.toSet).size >0){
             if((r2lMap.toMap.keySet -- sSetOnRight.toSet).size >0){
               sSetOnRight = (sSetOnRight ++ r2lMap.toMap.keySet)


               var dv1 :Double = MyUtils.getNumDV(r2lMap.toMap.keySet -- sSetOnRight.toSet) // set R // right key
               var dv2 :Double = MyUtils.getNumDV(fullSetRight.toSet -- sSetOnRight.toSet)  //  todo need to project to left  // we dont need to



               var beforeProjectToLeft : Set[WrapAttribute] = (r2lMap.toMap.keySet -- sSetOnRight.toSet)
               var afterProjectToLeft : mutable.Set[WrapAttribute] = mutable.Set[WrapAttribute]()

               beforeProjectToLeft.foreach( col=>{
                 if(r2lMap.get(col).nonEmpty){
                   afterProjectToLeft += r2lMap.get(col).get
                 } else{
                   afterProjectToLeft += col
                 }
               })


               var dv3 :Double = MyUtils.getNumDV(afterProjectToLeft.toSet) // todo  need to project to left

               newSfmRight  = sfm * ( math.min(dv1,dv2) / dv3 )
             }else{
               sSetOnRight = sSetOnRight
             }
          }else {
              sSetOnRight = sSetOnRight
          }

          // todo fix ds
          val KremRight : Set[WrapAttribute] = (r2lMap.toMap.keySet -- sSetOnRight.toSet)

          var kdv1Right :Double = MyUtils.getNumDV(KremRight) // R



          var afterProjectToLeftRem : mutable.Set[WrapAttribute] = mutable.Set[WrapAttribute]()
          KremRight.foreach( col=>{
            if(r2lMap.get(col).nonEmpty){
              afterProjectToLeftRem += r2lMap.get(col).get
            } else{
              afterProjectToLeftRem += col
            }
          })



          var kdv2Right :Double = MyUtils.getNumDV(afterProjectToLeftRem.toSet) // L need to project to left



          var newDsRight = (ds / kdv1Right) * math.min(kdv1Right , kdv2Right)

          val copiedrootPlanRight : LogicalPlan = rootPlan.clone()
          val copiedRight: LogicalPlan = AqpSample(sampleStatus.errorRate,sampleStatus.confidence,sampleStatus.seed,right,sSetOnRight.map(wa => wa.att).toSet,uSet,newDsRight,newSfmRight,sampleFrac,delta,parallel).clone()
          construct(copiedrootPlanRight,copiedRight,isLeft = false)
          // push to right end

          join.unsetTagValue(TreeNodeTag[String]("insert"))

          println("sSetOnLeft"+sSetOnLeft)
          println("sSetOnRight"+sSetOnRight)

          dfs(left,newDsLeft,newSfmLeft,sSetOnLeft.map(wa => wa.att).toSet,uSet,sampleFrac,delta,parallel,rootPlan)
          dfs(right,newDsRight,newSfmRight,sSetOnRight.map(wa=>wa.att).toSet,uSet,sampleFrac,delta,parallel,rootPlan)

        case _ =>
      }

     // plan.children.foreach(dfs(_))
  }
  def checkAqpSample(plan: LogicalPlan):LogicalPlan= plan transform{
    case  agg @  Aggregate(groupExps :Seq[Expression] ,aggExps: Seq[NamedExpression] ,child: LogicalPlan) =>
      child match{
        case aqp @ AqpSample(errorRate,confidence,seed,child,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel) =>

          sampleStatus=aqp
          treeToPush= child
          Aggregate(groupExps,aggExps,child)

        case _ => agg
      }

  }

}


class WrapAttribute (val att : Attribute){
  override def equals(obj: Any): Boolean = {
    obj.asInstanceOf[WrapAttribute].att.asInstanceOf[AttributeReference].exprId.id == att.asInstanceOf[AttributeReference].exprId.id
     //obj .asInstanceOf[AttributeReference].exprId.id == att.asInstanceOf[AttributeReference].exprId.id
  }

  override def hashCode(): Int = {
    val id: Long = att.asInstanceOf[AttributeReference].exprId.id
    val h: Long = (id >>> 32) ^ id
    h.toInt
  }

  override def toString: String = att.asInstanceOf[AttributeReference].toString
}