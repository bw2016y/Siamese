package org.apache.spark.daslab.sql.engine.expressions.aggregate




import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult
import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.logical.{Confidence, ErrorRate}
import org.apache.spark.daslab.sql.engine.util.TypeUtils
import org.apache.spark.daslab.sql.types._

@ExpressionDescription(
  usage = "_FUNC_(expr) - Returns the sum calculated from values of a group.")
case class Sum(child: Expression) extends DeclarativeAggregate with ImplicitCastInputTypes {

  // todo Confidence/error 需要保存在这里，后续位置可以修改
  var errorRate: ErrorRate = null
  var confidence: Confidence = null






  //todo append weight here
  var weight:Expression = null
  def appendWeight(w:Expression): Unit = {
    weight=w
  }
  def hasWeight: Boolean ={
      weight!=null
  }

  //todo 这里添加了weight，可能会导致logical部分有冲突
  override def children: Seq[Expression] ={
    if(hasWeight){
       child:: weight ::Nil
    }else{
      child::Nil
    }
  }

  override def nullable: Boolean = true

  // Return data type.
  // 返回的数据类型需要额外定义
  // 这里改成StringType试一下
  //override def dataType: DataType = resultType
  override def dataType: DataType = StringType


  //todo input check也发生了变化
  override def inputTypes: Seq[AbstractDataType] ={
    if(hasWeight){
      Seq(NumericType,NumericType)
    }else{
      Seq(NumericType)
    }
  }

  override def checkInputDataTypes(): TypeCheckResult =
    TypeUtils.checkForNumericExpr(child.dataType, "function sum")

  // 根据子节点的类型来判断是小数类型（DecimalType）/Long/Double
/*  private lazy val resultType = child.dataType match {
    case DecimalType.Fixed(precision, scale) =>
      DecimalType.bounded(precision + 10, scale)
    case _: IntegralType => LongType
    case _ => DoubleType
  }*/



  //todo 默认都是Double/DecimalType 不然会报null
  private lazy val resultType = child.dataType match {
    case DecimalType.Fixed(precision, scale) =>
      DecimalType.bounded(precision + 10, scale)
    case _ => DoubleType
  }

  // 主要是聚合分为两个步骤，需要修改上一个部分的数据类型，如果要修改的话，需要在
  //  AggUtils里面直接调整
 /* private lazy val resultType =  {
     if(hasWeight){
       child.dataType match {
         case DecimalType.Fixed(precision, scale) =>
           DecimalType.bounded(precision + 10, scale)
         case _ => DoubleType
       }
     }else{
       child.dataType match {
         case DecimalType.Fixed(precision, scale) =>
           DecimalType.bounded(precision + 10, scale)
         case _: IntegralType => LongType
         case _ => DoubleType
       }
     }
  }*/

 //todo 真正的返回值
  // private lazy val toPrint = AttributeReference("toPrint",StringType)()

  private lazy val sumDataType = resultType

  private lazy val sum = AttributeReference("sum", sumDataType)()

  //todo 方差  , 目前type也是resultType
  private lazy val sum_var = AttributeReference("sum_var",resultType)()


  // zero
  private lazy val zero = Cast(Literal(0), sumDataType)
  // one
  private lazy val one = Cast(Literal(1),sumDataType)

  // offset
  private lazy val offset=Cast(Literal(10000),sumDataType)

  //缓冲区
  // todo: 这里有可能需要添加sum_var
  override lazy val aggBufferAttributes = {
    if(hasWeight){
      sum :: sum_var :: Nil
    }else{
      sum :: Nil
    }
  }


 //todo 添加对sum_var的初始化
  override lazy val initialValues: Seq[Expression] ={
    if(hasWeight){
      Seq(
        /* sum = */ Literal.create(null, sumDataType),
        /* sum_var = */  Literal(0).cast(resultType)
      )
    }else{
      Seq(
        /* sum = */ Literal.create(null, sumDataType)
      )
    }
  }




  //todo 这里要除weight
/*  override lazy val updateExpressions: Seq[Expression] = {
    if (child.nullable) {
      Seq(
        /* sum = */
        coalesce(coalesce(sum, zero) + child.cast(sumDataType), sum)
      )
    } else {
      Seq(
        /* sum = */
        coalesce(sum, zero) + child.cast(sumDataType)
      )
    }
  }*/
  // sumDataType也需要修改
  // todo 这里魔改 用offset
  override lazy val updateExpressions: Seq[Expression]= {
    if(hasWeight){
       if(child.nullable){
          Seq(
            coalesce(coalesce(sum,zero) + child.cast(sumDataType)/weight.cast(sumDataType),sum),
            //todo 更新方差
            Add( sum_var ,
              coalesce( child.cast(sumDataType)*child.cast(sumDataType)*(one-weight.cast(sumDataType))/weight.cast(sumDataType)/weight.cast(sumDataType)  ,
                Literal(0).cast(sumDataType) )
            )
          )
       }else{
          Seq(
            coalesce(sum,zero) + child.cast(sumDataType)/weight.cast(sumDataType),
            //todo 更新方差
            Add( sum_var ,
              coalesce( child.cast(sumDataType)*child.cast(sumDataType)*(one-weight.cast(sumDataType))/weight.cast(sumDataType)/weight.cast(sumDataType)  ,
                Literal(0).cast(sumDataType) )
            )
          )
       }
    }else{
      if (child.nullable) {
        Seq(
          /* sum = */
          coalesce(coalesce(sum, zero) + child.cast(sumDataType), sum)
        )
      } else {
        Seq(
          /* sum = */
          coalesce(sum, zero) + child.cast(sumDataType)
        )
      }
    }
  }

  /*override lazy val updateExpressions: Seq[Expression]= {
    if(hasWeight){
      if(child.nullable){
        Seq(
          coalesce(coalesce(sum,zero) + child.cast(sumDataType)/weight.cast(sumDataType)+offset*coalesce( child.cast(sumDataType)*child.cast(sumDataType)*(one-weight.cast(sumDataType))/weight.cast(sumDataType)/weight.cast(sumDataType)  ,
            Literal(0).cast(sumDataType) ),sum),
          //todo 更新方差
          Add( sum_var ,
            coalesce( child.cast(sumDataType)*child.cast(sumDataType)*(one-weight.cast(sumDataType))/weight.cast(sumDataType)/weight.cast(sumDataType)  ,
              Literal(0).cast(sumDataType) )
          )
        )
      }else{
        Seq(
          coalesce(sum,zero) + child.cast(sumDataType)/weight.cast(sumDataType)+offset*coalesce( child.cast(sumDataType)*child.cast(sumDataType)*(one-weight.cast(sumDataType))/weight.cast(sumDataType)/weight.cast(sumDataType)  ,
            Literal(0).cast(sumDataType) ),
          //todo 更新方差
          Add( sum_var ,
            coalesce( child.cast(sumDataType)*child.cast(sumDataType)*(one-weight.cast(sumDataType))/weight.cast(sumDataType)/weight.cast(sumDataType)  ,
              Literal(0).cast(sumDataType) )
          )
        )
      }
    }else{
      if (child.nullable) {
        Seq(
          /* sum = */
          coalesce(coalesce(sum, zero) + child.cast(sumDataType), sum)
        )
      } else {
        Seq(
          /* sum = */
          coalesce(sum, zero) + child.cast(sumDataType)
        )
      }
    }
  }*/


  override lazy val mergeExpressions: Seq[Expression] ={
    if(hasWeight){
        Seq(
          /* sum = */
          coalesce(coalesce(sum.left, zero) + sum.right, sum.left),
          /* sum_var = */
          coalesce(sum_var.left + sum_var.right , sum_var.left)
        )
    }else{
      Seq(
        /* sum = */
        coalesce(coalesce(sum.left, zero) + sum.right, sum.left)
      )
    }
  }




  //override lazy val evaluateExpression: Expression = sum
  override lazy val evaluateExpression: Expression = {
      if(hasWeight){
       // sum+sum_var
       // sum.cast(StringType) +" var:" + sum_var.cast(StringType)
        //sum_var.cast(StringType)
       // Literal(one.toString).cast(StringType)
       // Literal(sum.cast(StringType)+" var:" + sum_var.toString).cast(StringType)
        ConcatWs(Seq(Literal("  ").cast(StringType),Literal("E:").cast(StringType),sum.cast(StringType),Literal("Var:").cast(StringType),sum_var.cast(StringType))).cast(StringType)
      }else{
        sum.cast(StringType)
      }
  }
}
