package org.apache.spark.daslab.sql.engine.expressions.aggregate



import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult
import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult.{TypeCheckFailure, TypeCheckSuccess}
import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.types._

/**
 * Returns the last value of `child` for a group of rows. If the last value of `child`
 * is `null`, it returns `null` (respecting nulls). Even if [[Last]] is used on an already
 * sorted column, if we do partial aggregation and final aggregation (when mergeExpression
 * is used) its result will not be deterministic (unless the input table is sorted and has
 * a single partition, and we use a single reducer to do the aggregation.).
 */
@ExpressionDescription(
  usage = """
    _FUNC_(expr[, isIgnoreNull]) - Returns the last value of `expr` for a group of rows.
      If `isIgnoreNull` is true, returns only non-null values.
  """)
case class Last(child: Expression, ignoreNullsExpr: Expression)
  extends DeclarativeAggregate with ExpectsInputTypes {

  def this(child: Expression) = this(child, Literal.create(false, BooleanType))

  override def children: Seq[Expression] = child :: ignoreNullsExpr :: Nil

  override def nullable: Boolean = true

  // Last is not a deterministic function.
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
        s"The second argument of Last must be a boolean literal, but got: ${ignoreNullsExpr.sql}")
    } else {
      TypeCheckSuccess
    }
  }

  private def ignoreNulls: Boolean = ignoreNullsExpr.eval().asInstanceOf[Boolean]

  private lazy val last = AttributeReference("last", child.dataType)()

  private lazy val valueSet = AttributeReference("valueSet", BooleanType)()

  override lazy val aggBufferAttributes: Seq[AttributeReference] = last :: valueSet :: Nil

  override lazy val initialValues: Seq[Literal] = Seq(
    /* last = */ Literal.create(null, child.dataType),
    /* valueSet = */ Literal.create(false, BooleanType)
  )

  override lazy val updateExpressions: Seq[Expression] = {
    if (ignoreNulls) {
      Seq(
        /* last = */ If(child.isNull, last, child),
        /* valueSet = */ valueSet || child.isNotNull
      )
    } else {
      Seq(
        /* last = */ child,
        /* valueSet = */ Literal.create(true, BooleanType)
      )
    }
  }

  override lazy val mergeExpressions: Seq[Expression] = {
    // Prefer the right hand expression if it has been set.
    Seq(
      /* last = */ If(valueSet.right, last.right, last.left),
      /* valueSet = */ valueSet.right || valueSet.left
    )
  }

  override lazy val evaluateExpression: AttributeReference = last

  override def toString: String = s"last($child)${if (ignoreNulls) " ignore nulls"}"
}
