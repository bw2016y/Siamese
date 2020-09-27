package org.apache.spark.daslab.sql.engine.expressions.aggregate



import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult
import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.util.TypeUtils
import org.apache.spark.daslab.sql.types._

@ExpressionDescription(
  usage = "_FUNC_(expr) - Returns the minimum value of `expr`.")
case class Min(child: Expression) extends DeclarativeAggregate {

  override def children: Seq[Expression] = child :: Nil

  override def nullable: Boolean = true

  // Return data type.
  override def dataType: DataType = child.dataType

  override def checkInputDataTypes(): TypeCheckResult =
    TypeUtils.checkForOrderingExpr(child.dataType, "function min")

  private lazy val min = AttributeReference("min", child.dataType)()

  override lazy val aggBufferAttributes: Seq[AttributeReference] = min :: Nil

  override lazy val initialValues: Seq[Expression] = Seq(
    /* min = */ Literal.create(null, child.dataType)
  )

  override lazy val updateExpressions: Seq[Expression] = Seq(
    /* min = */ least(min, child)
  )

  override lazy val mergeExpressions: Seq[Expression] = {
    Seq(
      /* min = */ least(min.left, min.right)
    )
  }

  override lazy val evaluateExpression: AttributeReference = min
}
