/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.daslab.sql.engine.expressions.aggregate

import org.apache.spark.daslab.sql.engine.analysis.{DecimalPrecision, TypeCheckResult}
import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.logical.{Confidence, ErrorRate}
import org.apache.spark.daslab.sql.engine.util.TypeUtils
import org.apache.spark.daslab.sql.types._

abstract class AverageLike(child: Expression) extends DeclarativeAggregate {

  override def nullable: Boolean = true
  // Return data type.
  override def dataType: DataType = resultType

  private lazy val resultType = child.dataType match {
    case DecimalType.Fixed(p, s) =>
      DecimalType.bounded(p + 4, s + 4)
    case _ => DoubleType
  }

  private lazy val sumDataType = child.dataType match {
    case _ @ DecimalType.Fixed(p, s) => DecimalType.bounded(p + 10, s)
    case _ => DoubleType
  }

  private lazy val sum = AttributeReference("sum", sumDataType)()
  private lazy val count = AttributeReference("count", LongType)()

  override lazy val aggBufferAttributes = sum :: count :: Nil

  override lazy val initialValues = Seq(
    /* sum = */ Literal(0).cast(sumDataType),
    /* count = */ Literal(0L)
  )

  override lazy val mergeExpressions = Seq(
    /* sum = */ sum.left + sum.right,
    /* count = */ count.left + count.right
  )

  // If all input are nulls, count will be 0 and we will get null after the division.
  // 如果所有的输入都是null，count就是0，那么在执行除法的时候就会返回null
  override lazy val evaluateExpression = child.dataType match {
    case _: DecimalType =>
      DecimalPrecision.decimalAndDecimal(sum / count.cast(DecimalType.LongDecimal)).cast(resultType)
    case _ =>
      sum.cast(resultType) / count.cast(resultType)
  }

  //update
  protected def updateExpressionsDef: Seq[Expression] = Seq(
    /* sum = */
    Add(
      sum,
      coalesce(child.cast(sumDataType), Literal(0).cast(sumDataType))),
    /* count = */ If(child.isNull, count, count + 1L)
  )

  override lazy val updateExpressions = updateExpressionsDef
}

@ExpressionDescription(
  usage = "_FUNC_(expr) - Returns the mean calculated from values of a group.")
case class Average(child: Expression)
  extends AverageLike(child) with ImplicitCastInputTypes {

  // todo Confidence/error 需要保存在这里，后续位置可以修改
  var errorRate: ErrorRate = null
  var confidence: Confidence = null


  //append weight here

  var weight: Expression = null

  def appendWeight(w: Expression): Unit = {
    weight = w
  }

  def hasWeight: Boolean = {
    weight != null
  }


  override def prettyName: String = "avg"

  //override def children: Seq[Expression] = child :: Nil
  override def children: Seq[Expression] = {
    if (hasWeight) {
      child :: weight :: Nil
    } else {
      child :: Nil
    }
  }

  override def nullable: Boolean = true

  //override def inputTypes: Seq[AbstractDataType] = Seq(NumericType)
  override def inputTypes: Seq[AbstractDataType] = {
    if (hasWeight) {
      Seq(NumericType, NumericType)
    } else {
      Seq(NumericType)
    }
  }


  override def checkInputDataTypes(): TypeCheckResult =
    TypeUtils.checkForNumericExpr(child.dataType, "function average")


  override def dataType: DataType = StringType

  // add merge ...
  private lazy val resultType = child.dataType match {
    case DecimalType.Fixed(p, s) =>
      DecimalType.bounded(p + 4, s + 4)
    case _ => DoubleType
  }
  private lazy val sumDataType = child.dataType match {
    case _@DecimalType.Fixed(p, s) => DecimalType.bounded(p + 10, s)
    case _ => DoubleType
  }

  // avg_sum
  private lazy val avg_sum = AttributeReference("avg_sum", sumDataType)()
  // avg_sum_var
  private lazy val avg_sum_var = AttributeReference("avg_sum_var", sumDataType)()

  // avg_count
  private lazy val avg_count = AttributeReference("avg_count", sumDataType)()
  // avg_count_var
  private lazy val avg_count_var = AttributeReference("avg_count_var", sumDataType)()

  // cov
  private lazy val cov = AttributeReference("cov", sumDataType)()

  // true cnt
  private lazy val truecnt = AttributeReference("truecnt", sumDataType)()

  // one Literal
  private lazy val one = Cast(Literal(1), sumDataType)
  // zero Literal
  private lazy val zero = Cast(Literal(0), sumDataType)
  // two Literal
  private lazy val two = Cast(Literal(2),sumDataType)

  override lazy val aggBufferAttributes = {
    if(hasWeight){
      avg_sum :: avg_sum_var :: avg_count :: avg_count_var :: cov :: truecnt :: Nil
    }else{
      avg_sum :: avg_count :: Nil
    }
  }
  //初始化
  /**
   * Seq(
   * /* sum = */ Literal(0).cast(sumDataType),
   *     /* count = */ Literal(0L)
   *   )
   */
  override lazy val initialValues :  Seq[Expression] = {
    if(hasWeight){
      Seq(
        /* avg_sum */ Literal(0).cast(sumDataType),
        /* avg_sum_var */ Literal(0).cast(sumDataType),
        /* avg_count */ Literal(0).cast(sumDataType),
        /* avg_count_var */ Literal(0).cast(sumDataType),
        /* cov */ Literal(0).cast(sumDataType),
        /* truecnt */ Literal(0).cast(sumDataType)
      )
    }else {
      Seq(
        /* avg_sum */ Literal(0).cast(sumDataType),
        /* avg_count */ Literal(0).cast(sumDataType)
      )
    }
  }

  // update
  /**
   * //update
   * protected def updateExpressionsDef: Seq[Expression] = Seq(
   * /* sum = */
   *     Add(
   *       sum,
   *       coalesce(child.cast(sumDataType), Literal(0).cast(sumDataType))),
   *     /* count = */ If(child.isNull, count, count + 1L)
   *   )
   */

  override lazy val updateExpressions: Seq[Expression]={
    if(hasWeight){
        Seq(
          /* avg_sum */
          coalesce(avg_sum,zero) + If(child.isNull, zero , child.cast(sumDataType)/weight.cast(sumDataType)) ,
          /* avg_sum_var */
          coalesce(avg_sum_var,zero) + If(child.isNull , zero ,
            child.cast(sumDataType)*child.cast(sumDataType)*(one-weight.cast(sumDataType))/weight.cast(sumDataType)/weight.cast(sumDataType)),
          /* avg_count */
          coalesce(avg_count,zero) + If(child.isNull, zero , one/weight.cast(sumDataType)),
          /* avg_count_var */
          coalesce(avg_count_var,zero) + If(child.isNull, zero,
            (one-weight.cast(sumDataType))/weight.cast(sumDataType)/weight.cast(sumDataType)
          ),
          /* cov */
          coalesce(cov,zero) + If(child.isNull , zero ,
            child.cast(sumDataType)*(one-weight.cast(sumDataType))/weight.cast(sumDataType)/weight.cast(sumDataType)),
          /* truecnt */
          coalesce(truecnt,zero) + If(child.isNull,zero,one)
        )
    }else{
      Seq(
         /* avg_sum */
        Add(avg_sum,coalesce(child.cast(sumDataType),zero)),
        /* avg_count */
        If(child.isNull , avg_count, avg_count + one )
      )
    }
  }


  // merge
  /**
   * override lazy val mergeExpressions = Seq(
   * /* sum = */ sum.left + sum.right,
   *     /* count = */ count.left + count.right
   *   )
   */
  override lazy val mergeExpressions: Seq[Expression] ={
    if(hasWeight){
       Seq(
         /* avg_sum */ avg_sum.left + avg_sum.right ,
         /* avg_sum_var */ avg_sum_var.left + avg_sum_var.right ,
         /* avg_count */ avg_count.left + avg_count.right ,
         /* avg_court_var */ avg_count_var.left + avg_count_var.right ,
         /* cov */ cov.left + cov.right ,
         /* truecnt */ truecnt.left + truecnt.right
       )
    }else{
       Seq(
         /* avg_sum */ avg_sum.left + avg_sum.right ,
         /* avg_count */ avg_count.left + avg_count.right
       )
    }
  }


  // eval
  /**
   * override lazy val evaluateExpression = child.dataType match {
   * case _: DecimalType =>
   *       DecimalPrecision.decimalAndDecimal(sum / count.cast(DecimalType.LongDecimal)).cast(resultType)
   * case _ =>
   *       sum.cast(resultType) / count.cast(resultType)
   * }
   */

  override lazy val evaluateExpression : Expression ={
    if(hasWeight){
        ConcatWs(
          Seq(
            Literal(" ").cast(StringType),
            Literal("E: ").cast(StringType),
            child.dataType match{
              case _ : DecimalType =>
                DecimalPrecision.decimalAndDecimal( avg_sum / avg_count.cast(DecimalType.DoubleDecimal)).cast(resultType).cast(StringType)
              case _ =>
                (avg_sum.cast(resultType)/avg_count.cast(resultType)).cast(StringType)
            },
            Literal("Var: ").cast(StringType),
            child.dataType match{
              case _ : DecimalType =>
                //todo 添加对于大数的处理
                //zero.cast(StringType)
                Literal("not support").cast(StringType)
              case _  =>
                (avg_sum_var.cast(resultType)/avg_count.cast(resultType)/avg_count.cast(resultType) +
                avg_sum.cast(resultType)*avg_sum.cast(resultType)*avg_count_var.cast(resultType)/avg_count.cast(resultType)/avg_count.cast(resultType)/avg_count.cast(resultType)/avg_count.cast(resultType) -
                two.cast(resultType)*avg_sum.cast(resultType)*cov.cast(resultType)/avg_count.cast(resultType)/avg_count.cast(resultType)/avg_count.cast(resultType)).cast(StringType)
            },
            Literal("SumVar:").cast(StringType),
            avg_sum_var.cast(StringType),
            Literal("CountVar:").cast(StringType),
            avg_count_var.cast(StringType),
            Literal("Cov:").cast(StringType),
            cov.cast(StringType),
            Literal("truecnt:").cast(StringType),
            truecnt.cast(StringType)
          )
        ).cast(StringType)
    }else{
       child.dataType match {
         case _ : DecimalType =>
           DecimalPrecision.decimalAndDecimal( avg_sum / avg_count.cast(DecimalType.DoubleDecimal)).cast(resultType).cast(StringType)
         case _ =>
           (avg_sum.cast(resultType)/avg_count.cast(resultType)).cast(StringType)
       }
    }
  }

}
