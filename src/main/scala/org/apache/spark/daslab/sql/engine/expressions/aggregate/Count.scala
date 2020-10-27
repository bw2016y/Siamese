package org.apache.spark.daslab.sql.engine.expressions.aggregate


import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.types._

/**
 * Base class for all counting aggregators.
 */
abstract class CountLike extends DeclarativeAggregate {
  override def nullable: Boolean = false

  // Return data type.
  override def dataType: DataType = LongType


  protected lazy val count = AttributeReference("count", LongType, nullable = false)()

  // 这些属性有可能在updateExpressions等各种表达式中用到
  override lazy val aggBufferAttributes = count :: Nil

  // 设定函数的初始值，这里count函数的初始值就为0
  override lazy val initialValues = Seq(
    /* count = */ Literal(0L)
  )
  // merge逻辑
  override lazy val mergeExpressions = Seq(
    /* count = */ count.left + count.right
  )

  override lazy val evaluateExpression = count

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
case class Count(children: Seq[Expression]) extends CountLike {

  override lazy val updateExpressions = {
    val nullableChildren = children.filter(_.nullable)
    if (nullableChildren.isEmpty) {
      Seq(
        /* count = */ count + 1L
      )
    } else {
      Seq(
        /* count = */ If(nullableChildren.map(IsNull).reduce(Or), count, count + 1L)
      )
    }
  }
}

object Count {
  def apply(child: Expression): Count = Count(child :: Nil)
}
