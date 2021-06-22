package org.apache.spark.daslab.sql.engine.optimizer

import java.{lang, util}

import org.apache.spark.daslab.sql.engine.expressions.{And, AttributeReference, Expression}
import org.apache.spark.daslab.sql.engine.plans.logical.{Aggregate, AqpSample, ErrorRate, Filter, Join, LogicalPlan, Project}
import org.apache.spark.daslab.sql.execution.util.DistinctColumn
import org.apache.spark.daslab.sql.execution.{DistinctSamplerExec, PlanLater, SparkPlan, UniformSamplerExec}
import org.apache.spark.daslab.sql.types.StringType
import org.apache.spark.daslab.thrift.gen.{Errors, Plans, PredictService}
import org.apache.thrift.protocol.{TCompactProtocol, TProtocol}
import org.apache.thrift.transport.{TFramedTransport, TSocket, TTransport}

object MyUtils {
    // todo pickmode
    var PICKMODE = 0



    var FRACTION=1.0

    // todo quickr >= 30 中心极限定理的需要 推荐在 5-100 左右
    var DELTA = 60

    // todo 这个值作废，没有用到 ， 已经使用rdd的partitions数目修正
    var PARALLELNUMS = 1
    var PLANPOS=0
    def setFraction(frac:Double)={
      MyUtils.FRACTION=frac
    }
    def setDelta(delta:Int)={
      MyUtils.DELTA=delta
    }

    def setPickMode(mode: Int)={
      MyUtils.PICKMODE=mode
    }

    def setParallelNums(para:Int)={
      MyUtils.PARALLELNUMS=para
    }
    def setPlan(index:Int)={
       MyUtils.PLANPOS = index
    }

    def getAqpSample(plan:LogicalPlan):LogicalPlan={
        plan match{
          case agg @ Aggregate(groupingExpressions,aggregateExpressions,child)=>
            getAqpSample(child)
          case project @Project(projectList,grandChild) =>
            getAqpSample(grandChild)
          case filter @ Filter(condition,grandChild) =>
            getAqpSample(grandChild)
          case join @ Join(left,right,joinType,condition) =>{
             val leftres = getAqpSample(left)
             val rightres = getAqpSample(right)
             if(leftres == null){
               return rightres
             }else{
               return leftres
             }
          }

          case aqp @ AqpSample (errorRate,confidence,seed,child,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel) =>
            aqp
          case _ =>
            return null
        }
    }

    def removeAQP(plan:LogicalPlan):LogicalPlan={
        plan transform{
          case agg @ Aggregate(groupingExpressions,aggregateExpressions,child)=>
            child match{
              case aqp@ AqpSample(errorRate,confidence,seed,child,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel) =>
                Aggregate(groupingExpressions,aggregateExpressions,child)
              case _ => agg
            }
        }
    }

    // todo 这里需要检查一下是否有可能不是AQP查询也执行了逻辑
    def getDepth(plan:LogicalPlan):Int= {

        plan match{
          case agg @ Aggregate(groupingExpressions,aggregateExpressions,child)=>
            return getDepth(child)
          case project @Project(projectList,grandChild) =>
            return getDepth(grandChild)
          case filter @ Filter(condition,grandChild) =>
            return getDepth(grandChild)
          case join @ Join(left,right,joinType,condition) =>
            return 1 + Math.max(getDepth(left),getDepth(right))
          case aqp @ AqpSample (errorRate,confidence,seed,child,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel) =>
            return 1
          case _ =>
            return -9999
        }

    }

    // 基于规则选择待执行的查询计划
    // RULE : 选择采样器下推最远的逻辑计划，若存在多个则随机选择一个

    def pickPlanByRule(plans : Seq[LogicalPlan]): LogicalPlan  = {
        var resPlan = plans(0)
        var maxDep = -999999
        for(plan <- plans){

            val curDep = getDepth(plan)
            println("curdep    "+curDep)
            if( curDep > maxDep ){
               maxDep = curDep
               resPlan = plan
            }
        }
       return resPlan
    }

    def pickPlanByDistinctRule(plans : Seq[LogicalPlan]): LogicalPlan ={
       var uniformPlans : Seq[LogicalPlan] = Seq.empty
       var distinctPlans : Seq[LogicalPlan] = Seq.empty
       for(waitPlan <- plans){
        val aqpSample = getAqpSample(waitPlan)
        if(aqpSample.asInstanceOf[AqpSample].stratificationSet.nonEmpty){
          distinctPlans  = ( distinctPlans :+ waitPlan )
        }else{
          uniformPlans   = ( uniformPlans :+ waitPlan )
        }
      }

      if(distinctPlans.nonEmpty){
        return pickPlanByRule(distinctPlans)
      }else{
        return pickPlanByRule(uniformPlans)
      }
    }


    def pickPlansByModel(plans: Seq[LogicalPlan]): LogicalPlan = {
          val tTransport:TTransport = new TFramedTransport(new TSocket("10.176.24.40",9990))
          val protocol : TProtocol = new TCompactProtocol(tTransport)
          val client : PredictService.Client = new PredictService.Client(protocol)

          tTransport.open()

          val sendPlans : Plans = new Plans()
          val message: util.List[String] = new util.ArrayList[String]()

          for(plan <- plans){
             message.add(plan.toJSON)
          }

          sendPlans.setPlans(message)

          val result: Errors = client.predict(sendPlans)

          val predict: util.List[lang.Double] = result.result

          //todo 不一定是真正的backup
          // 需要将采样率设置为1
          val status = getAqpSample(plans(0))
          val errorRate: ErrorRate = status.asInstanceOf[AqpSample].errorRate
          val rate: Double = errorRate.getRate


          val backup = removeAQP(plans(0))



          var waitlist: Seq[LogicalPlan] = Seq.empty


          for( index <- 1 to plans.length-1 ){
                //todo 改变条件
                if( predict.get(index) <=  rate ){
                    waitlist=(waitlist:+ plans(index))
                }
          }

          //todo 启发式的选择满足用户要求的执行计划

          if(waitlist.isEmpty){
              // 应该有更好的方式,直接移除aqp
              return backup
          }else{
              // 这里使用

              var uniformPlans : Seq[LogicalPlan] = Seq.empty
              var distinctPlans : Seq[LogicalPlan] = Seq.empty

              for(waitPlan <- waitlist){
                 val aqpSample = getAqpSample(waitPlan)
                 if(aqpSample.asInstanceOf[AqpSample].stratificationSet.nonEmpty){
                    distinctPlans  = ( distinctPlans :+ waitPlan )
                 }else{
                    uniformPlans   = ( uniformPlans :+ waitPlan )
                 }
              }
              if(uniformPlans.nonEmpty){
                return pickPlanByRule(uniformPlans)
              }else{
                return pickPlanByRule(distinctPlans)
              }
          }
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
         DistinctSamplerExec(plan.errorRate,plan.confidence,plan.seed,PlanLater(plan.child),list ,plan.delta,plan.sampleFraction,plan.parallel,plan.nameE)::Nil
         //选择分层采样器
       }else{
         //选择均匀采样器
         UniformSamplerExec(plan.errorRate,plan.confidence,plan.seed, PlanLater(plan.child),plan.nameE,plan.sampleFraction )::Nil
       }
    }

  /**
   * 打印stats信息
   */
  def checkStats(plan:LogicalPlan)={
     plan.foreach(
       p => println(p.simpleString+"  "+p.stats.toString)
     )
  }

}
