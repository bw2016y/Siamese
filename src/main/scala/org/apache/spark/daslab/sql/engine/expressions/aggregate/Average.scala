package org.apache.spark.daslab.sql.engine.expressions.aggregate


import org.apache.spark.daslab.sql.engine.analysis.{DecimalPrecision,TypeCheckResult}
import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
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
  override lazy val evaluateExpression = child.dataType match {
    case _: DecimalType =>
      DecimalPrecision.decimalAndDecimal(sum / count.cast(DecimalType.LongDecimal)).cast(resultType)
    case _ =>
      sum.cast(resultType) / count.cast(resultType)
  }

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

  override def prettyName: String = "avg"

  override def children: Seq[Expression] = child :: Nil

  override def inputTypes: Seq[AbstractDataType] = Seq(NumericType)

  override def checkInputDataTypes(): TypeCheckResult =
    TypeUtils.checkForNumericExpr(child.dataType, "function average")
}
