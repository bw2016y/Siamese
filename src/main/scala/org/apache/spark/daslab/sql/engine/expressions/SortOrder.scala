package org.apache.spark.daslab.sql.engine.expressions

//todo codegen part
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult
import org.apache.spark.daslab.sql.engine.expressions.codegen.{CodegenContext, ExprCode}
import org.apache.spark.daslab.sql.engine.expressions.codegen.Block._
import org.apache.spark.daslab.sql.types._

//todo spark core
import org.apache.spark.unsafe.types.UTF8String
import org.apache.spark.util.collection.unsafe.sort.PrefixComparators._

abstract sealed class SortDirection {
  def sql: String
  def defaultNullOrdering: NullOrdering
}

abstract sealed class NullOrdering {
  def sql: String
}

case object Ascending extends SortDirection {
  override def sql: String = "ASC"
  override def defaultNullOrdering: NullOrdering = NullsFirst
}

case object Descending extends SortDirection {
  override def sql: String = "DESC"
  override def defaultNullOrdering: NullOrdering = NullsLast
}

case object NullsFirst extends NullOrdering{
  override def sql: String = "NULLS FIRST"
}

case object NullsLast extends NullOrdering{
  override def sql: String = "NULLS LAST"
}

/**
 * An expression that can be used to sort a tuple.  This class extends expression primarily so that
 * transformations over expression will descend into its child.
 * `sameOrderExpressions` is a set of expressions with the same sort order as the child. It is
 * derived from equivalence relation in an operator, e.g. left/right keys of an inner sort merge
 * join.
 */
case class SortOrder(
                      child: Expression,
                      direction: SortDirection,
                      nullOrdering: NullOrdering,
                      sameOrderExpressions: Set[Expression])
  extends UnaryExpression with Unevaluable {

  /** Sort order is not foldable because we don't have an eval for it. */
  override def foldable: Boolean = false

  override def checkInputDataTypes(): TypeCheckResult = {
    if (RowOrdering.isOrderable(dataType)) {
      TypeCheckResult.TypeCheckSuccess
    } else {
      TypeCheckResult.TypeCheckFailure(s"cannot sort data type ${dataType.catalogString}")
    }
  }

  override def dataType: DataType = child.dataType
  override def nullable: Boolean = child.nullable

  override def toString: String = s"$child ${direction.sql} ${nullOrdering.sql}"
  override def sql: String = child.sql + " " + direction.sql + " " + nullOrdering.sql

  def isAscending: Boolean = direction == Ascending

  def satisfies(required: SortOrder): Boolean = {
    (sameOrderExpressions + child).exists(required.child.semanticEquals) &&
      direction == required.direction && nullOrdering == required.nullOrdering
  }
}

object SortOrder {
  def apply(
             child: Expression,
             direction: SortDirection,
             sameOrderExpressions: Set[Expression] = Set.empty): SortOrder = {
    new SortOrder(child, direction, direction.defaultNullOrdering, sameOrderExpressions)
  }

  /**
   * Returns if a sequence of SortOrder satisfies another sequence of SortOrder.
   *
   * SortOrder sequence A satisfies SortOrder sequence B if and only if B is an equivalent of A
   * or of A's prefix. Here are examples of ordering A satisfying ordering B:
   * <ul>
   *   <li>ordering A is [x, y] and ordering B is [x]</li>
   *   <li>ordering A is [x(sameOrderExpressions=x1)] and ordering B is [x1]</li>
   *   <li>ordering A is [x(sameOrderExpressions=x1), y] and ordering B is [x1]</li>
   * </ul>
   */
  def orderingSatisfies(ordering1: Seq[SortOrder], ordering2: Seq[SortOrder]): Boolean = {
    if (ordering2.isEmpty) {
      true
    } else if (ordering2.length > ordering1.length) {
      false
    } else {
      ordering2.zip(ordering1).forall {
        case (o2, o1) => o1.satisfies(o2)
      }
    }
  }
}

/**
 * An expression to generate a 64-bit long prefix used in sorting. If the sort must operate over
 * null keys as well, this.nullValue can be used in place of emitted null prefixes in the sort.
 */
case class SortPrefix(child: SortOrder) extends UnaryExpression {

  val nullValue = child.child.dataType match {
    case BooleanType | DateType | TimestampType | _: IntegralType =>
      if (nullAsSmallest) Long.MinValue else Long.MaxValue
    case dt: DecimalType if dt.precision - dt.scale <= Decimal.MAX_LONG_DIGITS =>
      if (nullAsSmallest) Long.MinValue else Long.MaxValue
    case _: DecimalType =>
      if (nullAsSmallest) {
        DoublePrefixComparator.computePrefix(Double.NegativeInfinity)
      } else {
        DoublePrefixComparator.computePrefix(Double.NaN)
      }
    case _ =>
      if (nullAsSmallest) 0L else -1L
  }

  private def nullAsSmallest: Boolean = {
    (child.isAscending && child.nullOrdering == NullsFirst) ||
      (!child.isAscending && child.nullOrdering == NullsLast)
  }

  private lazy val calcPrefix: Any => Long = child.child.dataType match {
    case BooleanType => (raw) =>
      if (raw.asInstanceOf[Boolean]) 1 else 0
    case DateType | TimestampType | _: IntegralType => (raw) =>
      raw.asInstanceOf[java.lang.Number].longValue()
    case FloatType | DoubleType => (raw) => {
      val dVal = raw.asInstanceOf[java.lang.Number].doubleValue()
      DoublePrefixComparator.computePrefix(dVal)
    }
    case StringType => (raw) =>
      StringPrefixComparator.computePrefix(raw.asInstanceOf[UTF8String])
    case BinaryType => (raw) =>
      BinaryPrefixComparator.computePrefix(raw.asInstanceOf[Array[Byte]])
    case dt: DecimalType if dt.precision <= Decimal.MAX_LONG_DIGITS =>
      _.asInstanceOf[Decimal].toUnscaledLong
    case dt: DecimalType if dt.precision - dt.scale <= Decimal.MAX_LONG_DIGITS =>
      val p = Decimal.MAX_LONG_DIGITS
      val s = p - (dt.precision - dt.scale)
      (raw) => {
        val value = raw.asInstanceOf[Decimal]
        if (value.changePrecision(p, s)) value.toUnscaledLong else Long.MinValue
      }
    case dt: DecimalType => (raw) =>
      DoublePrefixComparator.computePrefix(raw.asInstanceOf[Decimal].toDouble)
    case _ => (Any) => 0L
  }

  override def eval(input: InternalRow): Any = {
    val value = child.child.eval(input)
    if (value == null) {
      null
    } else {
      calcPrefix(value)
    }
  }

  override def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = {
    val childCode = child.child.genCode(ctx)
    val input = childCode.value
    val BinaryPrefixCmp = classOf[BinaryPrefixComparator].getName
    val DoublePrefixCmp = classOf[DoublePrefixComparator].getName
    val StringPrefixCmp = classOf[StringPrefixComparator].getName
    val prefixCode = child.child.dataType match {
      case BooleanType =>
        s"$input ? 1L : 0L"
      case _: IntegralType =>
        s"(long) $input"
      case DateType | TimestampType =>
        s"(long) $input"
      case FloatType | DoubleType =>
        s"$DoublePrefixCmp.computePrefix((double)$input)"
      case StringType => s"$StringPrefixCmp.computePrefix($input)"
      case BinaryType => s"$BinaryPrefixCmp.computePrefix($input)"
      case dt: DecimalType if dt.precision - dt.scale <= Decimal.MAX_LONG_DIGITS =>
        if (dt.precision <= Decimal.MAX_LONG_DIGITS) {
          s"$input.toUnscaledLong()"
        } else {
          // reduce the scale to fit in a long
          val p = Decimal.MAX_LONG_DIGITS
          val s = p - (dt.precision - dt.scale)
          s"$input.changePrecision($p, $s) ? $input.toUnscaledLong() : ${Long.MinValue}L"
        }
      case dt: DecimalType =>
        s"$DoublePrefixCmp.computePrefix($input.toDouble())"
      case _ => "0L"
    }

    ev.copy(code = childCode.code +
      code"""
            |long ${ev.value} = 0L;
            |boolean ${ev.isNull} = ${childCode.isNull};
            |if (!${childCode.isNull}) {
            |  ${ev.value} = $prefixCode;
            |}
      """.stripMargin)
  }

  override def dataType: DataType = LongType
}
