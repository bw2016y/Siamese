package org.apache.spark.daslab.sql.engine.expressions.aggregate


import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult
import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult.{TypeCheckFailure, TypeCheckSuccess}
import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.types._

/**
 * Returns the first value of `child` for a group of rows. If the first value of `child`
 * is `null`, it returns `null` (respecting nulls). Even if [[First]] is used on an already
 * sorted column, if we do partial aggregation and final aggregation (when mergeExpression
 * is used) its result will not be deterministic (unless the input table is sorted and has
 * a single partition, and we use a single reducer to do the aggregation.).
 */
@ExpressionDescription(
  usage = """
    _FUNC_(expr[, isIgnoreNull]) - Returns the first value of `expr` for a group of rows.
      If `isIgnoreNull` is true, returns only non-null values.
  """)
case class First(child: Expression, ignoreNullsExpr: Expression)
  extends DeclarativeAggregate with ExpectsInputTypes {

  def this(child: Expression) = this(child, Literal.create(false, BooleanType))

  override def children: Seq[Expression] = child :: ignoreNullsExpr :: Nil

  override def nullable: Boolean = true

  // First is not a deterministic function.
  override lazy val deterministic: Boolean = false

  // Return data type.
  override def dataType: DataType = child.dataType

  // Expected input data type.
  override def inputTypes: Seq[AbstractDataType] = Seq(AnyDataType, BooleanType)

  override def checkInputDataTypes(): TypeCheckResult = {
    val defaultCheck = super.checkInputDataTypes()
    if (defaultCheck.isFailure) {
      defaultCheck
    } else if (!ignoreNullsExpr.foldable) {
      TypeCheckFailure(
        s"The second argument of First must be a boolean literal, but got: ${ignoreNullsExpr.sql}")
    } else {
      TypeCheckSuccess
    }
  }

  private def ignoreNulls: Boolean = ignoreNullsExpr.eval().asInstanceOf[Boolean]

  private lazy val first = AttributeReference("first", child.dataType)()

  private lazy val valueSet = AttributeReference("valueSet", BooleanType)()

  override lazy val aggBufferAttributes: Seq[AttributeReference] = first :: valueSet :: Nil

  override lazy val initialValues: Seq[Literal] = Seq(
    /* first = */ Literal.create(null, child.dataType),
    /* valueSet = */ Literal.create(false, BooleanType)
  )

  override lazy val updateExpressions: Seq[Expression] = {
    if (ignoreNulls) {
      Seq(
        /* first = */ If(valueSet || child.isNull, first, child),
        /* valueSet = */ valueSet || child.isNotNull
      )
    } else {
      Seq(
        /* first = */ If(valueSet, first, child),
        /* valueSet = */ Literal.create(true, BooleanType)
      )
    }
  }

  override lazy val mergeExpressions: Seq[Expression] = {
    // For first, we can just check if valueSet.left is set to true. If it is set
    // to true, we use first.right. If not, we use first.right (even if valueSet.right is
    // false, we are safe to do so because first.right will be null in this case).
    Seq(
      /* first = */ If(valueSet.left, first.left, first.right),
      /* valueSet = */ valueSet.left || valueSet.right
    )
  }

  override lazy val evaluateExpression: AttributeReference = first

  override def toString: String = s"first($child)${if (ignoreNulls) " ignore nulls"}"
}
