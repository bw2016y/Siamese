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
object insertSampler extends Rule[LogicalPlan] {


  /**
    *  todo 需要从LoggicalPlan的根部获取AQP信息
    * @param plan
    * @return
    */
  override def apply(plan: LogicalPlan): LogicalPlan = plan transform{
    case  agg @  Aggregate(groupExps :Seq[Expression] ,aggExps: Seq[NamedExpression] ,child: LogicalPlan) =>
      Aggregate(groupExps,aggExps,AqpSample(ErrorRate(1.0),Confidence(1.0),(math.random * 1000).toInt,child))
  }
}