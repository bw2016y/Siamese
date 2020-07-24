package org.apache.spark.daslab.sql.engine.expressions.aggregate



import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult
import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.util.TypeUtils
import org.apache.spark.daslab.sql.types._

@ExpressionDescription(
  usage = "_FUNC_(expr) - Returns the maximum value of `expr`.")
case class Max(child: Expression) extends DeclarativeAggregate {

  override def children: Seq[Expression] = child :: Nil

  override def nullable: Boolean = true

  // Return data type.
  override def dataType: DataType = child.dataType

  override def checkInputDataTypes(): TypeCheckResult =
    TypeUtils.checkForOrderingExpr(child.dataType, "function max")

  private lazy val max = AttributeReference("max", child.dataType)()

  override lazy val aggBufferAttributes: Seq[AttributeReference] = max :: Nil

  override lazy val initialValues: Seq[Literal] = Seq(
    /* max = */ Literal.create(null, child.dataType)
  )

  override lazy val updateExpressions: Seq[Expression] = Seq(
    /* max = */ greatest(max, child)
  )

  override lazy val mergeExpressions: Seq[Expression] = {
    Seq(
      /* max = */ greatest(max.left, max.right)
    )
  }

  override lazy val evaluateExpression: AttributeReference = max
}
