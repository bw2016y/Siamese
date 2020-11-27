package org.apache.spark.daslab.sql.engine.expressions.aggregate


import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.logical.{Confidence, ErrorRate}
import org.apache.spark.daslab.sql.types._

/**
  *  所有counting聚合的基类
 */
abstract class CountLike extends DeclarativeAggregate {
  override def nullable: Boolean = false

  // Return data type.
  override def dataType: DataType = LongType

  // 新建一个count作为返回的类型
  private  lazy val count = AttributeReference("count", LongType, nullable = false)()

  // 这些属性有可能在updateExpressions等各种表达式中用到
  override lazy val aggBufferAttributes = count :: Nil

  // 设定函数的初始值，这里count函数的初始值就为0
  override lazy val initialValues:Seq[Expression] = Seq(
    /* count = */ Literal(0L)
  )
  // merge逻辑
  override lazy val mergeExpressions = Seq(
    /* count = */ count.left + count.right
  )

  override lazy val evaluateExpression : Expression  = count

  override def defaultResult: Option[Literal] = Option(Literal(0L))
}

// scalastyle:off line.size.limit
@ExpressionDescription(
  usage = """
    _FUNC_(*) - Returns the total number of retrieved rows, including rows containing null.

    _FUNC_(expr[, expr...]) - Returns the number of rows for which the supplied expression(s) are all non-null.

    _FUNC_(DISTINCT expr[, expr...]) - Returns the number of rows for which the supplied expression(s) are unique and non-null.
  """)
// scalastyle:on line.size.limit
case class Count(child : Seq[Expression]) extends CountLike {


  // todo Confidence/error 需要保存在这里，后续位置可以修改
  var errorRate: ErrorRate = null
  var confidence: Confidence = null

  // append weight here
  var weight:Expression = null
  def appendWeight(w:Expression): Unit = {
    weight=w
  }
  def hasWeight: Boolean ={
    weight!=null
  }

  override def children: Seq[Expression]={
     if(hasWeight){
      child :+ weight
     }else{
       child
     }
  }

  // Return data type.
  override def dataType: DataType = StringType

  //需要返回的统计值，被权重修正过
  private lazy val count = AttributeReference("count",DoubleType)()

  // 方差
  private lazy val count_var = AttributeReference("count_var",DoubleType)()

  //one Literal
  private lazy val one = Cast(Literal(1),DoubleType)


  // 缓冲区
  //override lazy val aggBufferAttributes = count :: Nil
  override lazy val aggBufferAttributes = {
    if(hasWeight){
      count :: count_var :: Nil
    }else{
      count :: Nil
    }
  }

  // 初始化
  override lazy val initialValues: Seq[Expression] = {
    if(hasWeight){
      Seq(
        /* count= */ Literal(0).cast(DoubleType),
        /* count_var */ Literal(0).cast(DoubleType)
      )
    }else{
      Seq(
        /* count= */ Literal(0).cast(DoubleType)
      )
    }
  }

  // 如何根据input row更新聚合缓冲区
  /*override lazy val updateExpressions = {
    val nullableChildren = child.filter(_.nullable)
    if (nullableChildren.isEmpty) {
      Seq(
        /* count = */ count + 1L
      )
    } else {
      Seq(
        /* count = */ If(nullableChildren.map(IsNull).reduce(Or), count, count + 1L)
      )
    }
  }*/
  override lazy val updateExpressions : Seq[Expression] = {
    val nullableChildren = child.filter(_.nullable)
    if(hasWeight){
      if(nullableChildren.isEmpty){
          Seq(
            /* count = */
            Add(
              count,
              one/weight.cast(DoubleType)
            ),
            /* count_var = */
            Add(
               count_var ,
              (one-weight.cast(DoubleType))/weight.cast(DoubleType)/weight.cast(DoubleType)
            )
          )
      } else{
        Seq(
          /* count = */
          If(nullableChildren.map(IsNull).reduce(Or),
            count,
            Add(
            count,
            one/weight.cast(DoubleType)
          )
          )
         ,
          /* count_var = */
          If(nullableChildren.map(IsNull).reduce(Or), count_var,
            Add(
              count_var ,
              (one-weight.cast(DoubleType))/weight.cast(DoubleType)/weight.cast(DoubleType)
            )
          )

        )
      }
    }else{
      if(nullableChildren.isEmpty){
        Seq(
          /* count = */  count + one
        )
      }else{
        Seq(
          /* count = */ If(nullableChildren.map(IsNull).reduce(Or), count, count + one)
        )
      }
    }

  }



  // merge逻辑
  override lazy val mergeExpressions : Seq[Expression] = {
     if(hasWeight){
       Seq(
         /* count */
         count.left + count.right ,
         /* count_var */
         count_var.left + count_var.right
       )
     }else{
       Seq(
         /* count */
         count.left + count.right
       )
     }
  }

  // evaluate
  override lazy val evaluateExpression : Expression  = {
    if(hasWeight){
      ConcatWs(Seq(Literal(" ").cast(StringType),Literal("E: ").cast(StringType),count.cast(StringType),Literal("Var: ").cast(StringType) ,count_var.cast(StringType))).cast(StringType)
    }else {
      count.cast(StringType)
    }
  }

  //todo  这里设置为StringType
  override def defaultResult: Option[Literal] = Option(Literal("0"))
}

object Count {
  def apply(child: Expression): Count = Count(child :: Nil)
}
