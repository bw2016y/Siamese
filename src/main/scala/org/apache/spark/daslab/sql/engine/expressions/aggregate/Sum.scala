package org.apache.spark.daslab.sql.engine.expressions.aggregate




import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult
import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.util.TypeUtils
import org.apache.spark.daslab.sql.types._

@ExpressionDescription(
  usage = "_FUNC_(expr) - Returns the sum calculated from values of a group.")
case class Sum(child: Expression) extends DeclarativeAggregate with ImplicitCastInputTypes {

  override def children: Seq[Expression] = child :: Nil

  override def nullable: Boolean = true

  // Return data type.
  override def dataType: DataType = resultType

  override def inputTypes: Seq[AbstractDataType] = Seq(NumericType)

  override def checkInputDataTypes(): TypeCheckResult =
    TypeUtils.checkForNumericExpr(child.dataType, "function sum")

  private lazy val resultType = child.dataType match {
    case DecimalType.Fixed(precision, scale) =>
      DecimalType.bounded(precision + 10, scale)
    case _: IntegralType => LongType
    case _ => DoubleType
  }

  private lazy val sumDataType = resultType

  private lazy val sum = AttributeReference("sum", sumDataType)()

  private lazy val zero = Cast(Literal(0), sumDataType)

  override lazy val aggBufferAttributes = sum :: Nil

  override lazy val initialValues: Seq[Expression] = Seq(
    /* sum = */ Literal.create(null, sumDataType)
  )

  override lazy val updateExpressions: Seq[Expression] = {
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

  override lazy val mergeExpressions: Seq[Expression] = {
    Seq(
      /* sum = */
      coalesce(coalesce(sum.left, zero) + sum.right, sum.left)
    )
  }

  override lazy val evaluateExpression: Expression = sum
}
