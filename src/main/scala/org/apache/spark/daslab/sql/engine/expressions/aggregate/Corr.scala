package org.apache.spark.daslab.sql.engine.expressions.aggregate




import org.apache.spark.daslab.sql.engine.dsl.expressions._
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.types._

/**
 * Base class for computing Pearson correlation between two expressions.
 * When applied on empty data (i.e., count is zero), it returns NULL.
 *
 * Definition of Pearson correlation can be found at
 * http://en.wikipedia.org/wiki/Pearson_product-moment_correlation_coefficient
 */
abstract class PearsonCorrelation(x: Expression, y: Expression)
  extends DeclarativeAggregate with ImplicitCastInputTypes {

  override def children: Seq[Expression] = Seq(x, y)
  override def nullable: Boolean = true
  override def dataType: DataType = DoubleType
  override def inputTypes: Seq[AbstractDataType] = Seq(DoubleType, DoubleType)

  protected val n = AttributeReference("n", DoubleType, nullable = false)()
  protected val xAvg = AttributeReference("xAvg", DoubleType, nullable = false)()
  protected val yAvg = AttributeReference("yAvg", DoubleType, nullable = false)()
  protected val ck = AttributeReference("ck", DoubleType, nullable = false)()
  protected val xMk = AttributeReference("xMk", DoubleType, nullable = false)()
  protected val yMk = AttributeReference("yMk", DoubleType, nullable = false)()

  override val aggBufferAttributes: Seq[AttributeReference] = Seq(n, xAvg, yAvg, ck, xMk, yMk)

  override val initialValues: Seq[Expression] = Array.fill(6)(Literal(0.0))

  override lazy val updateExpressions: Seq[Expression] = updateExpressionsDef

  override val mergeExpressions: Seq[Expression] = {
    val n1 = n.left
    val n2 = n.right
    val newN = n1 + n2
    val dx = xAvg.right - xAvg.left
    val dxN = If(newN === 0.0, 0.0, dx / newN)
    val dy = yAvg.right - yAvg.left
    val dyN = If(newN === 0.0, 0.0, dy / newN)
    val newXAvg = xAvg.left + dxN * n2
    val newYAvg = yAvg.left + dyN * n2
    val newCk = ck.left + ck.right + dx * dyN * n1 * n2
    val newXMk = xMk.left + xMk.right + dx * dxN * n1 * n2
    val newYMk = yMk.left + yMk.right + dy * dyN * n1 * n2

    Seq(newN, newXAvg, newYAvg, newCk, newXMk, newYMk)
  }

  protected def updateExpressionsDef: Seq[Expression] = {
    val newN = n + 1.0
    val dx = x - xAvg
    val dxN = dx / newN
    val dy = y - yAvg
    val dyN = dy / newN
    val newXAvg = xAvg + dxN
    val newYAvg = yAvg + dyN
    val newCk = ck + dx * (y - newYAvg)
    val newXMk = xMk + dx * (x - newXAvg)
    val newYMk = yMk + dy * (y - newYAvg)

    val isNull = x.isNull || y.isNull
    Seq(
      If(isNull, n, newN),
      If(isNull, xAvg, newXAvg),
      If(isNull, yAvg, newYAvg),
      If(isNull, ck, newCk),
      If(isNull, xMk, newXMk),
      If(isNull, yMk, newYMk)
    )
  }
}


// scalastyle:off line.size.limit
@ExpressionDescription(
  usage = "_FUNC_(expr1, expr2) - Returns Pearson coefficient of correlation between a set of number pairs.")
// scalastyle:on line.size.limit
case class Corr(x: Expression, y: Expression)
  extends PearsonCorrelation(x, y) {

  override val evaluateExpression: Expression = {
    If(n === 0.0, Literal.create(null, DoubleType),
      If(n === 1.0, Double.NaN, ck / sqrt(xMk * yMk)))
  }

  override def prettyName: String = "corr"
}
