package org.apache.spark.daslab.sql.engine.util


import org.apache.spark.daslab.sql.engine.analysis.{TypeCheckResult, TypeCoercion}
import org.apache.spark.daslab.sql.engine.expressions.RowOrdering
import org.apache.spark.daslab.sql.types._

/**
 * Functions to help with checking for valid data types and value comparison of various types.
 */
object TypeUtils {
  def checkForNumericExpr(dt: DataType, caller: String): TypeCheckResult = {
    if (dt.isInstanceOf[NumericType] || dt == NullType) {
      TypeCheckResult.TypeCheckSuccess
    } else {
      TypeCheckResult.TypeCheckFailure(s"$caller requires numeric types, not ${dt.catalogString}")
    }
  }

  def checkForOrderingExpr(dt: DataType, caller: String): TypeCheckResult = {
    if (RowOrdering.isOrderable(dt)) {
      TypeCheckResult.TypeCheckSuccess
    } else {
      TypeCheckResult.TypeCheckFailure(
        s"$caller does not support ordering on type ${dt.catalogString}")
    }
  }

  def checkForSameTypeInputExpr(types: Seq[DataType], caller: String): TypeCheckResult = {
    if (TypeCoercion.haveSameType(types)) {
      TypeCheckResult.TypeCheckSuccess
    } else {
      return TypeCheckResult.TypeCheckFailure(
        s"input to $caller should all be the same type, but it's " +
          types.map(_.catalogString).mkString("[", ", ", "]"))
    }
  }

  def getNumeric(t: DataType): Numeric[Any] =
    t.asInstanceOf[NumericType].numeric.asInstanceOf[Numeric[Any]]

  def getInterpretedOrdering(t: DataType): Ordering[Any] = {
    t match {
      case i: AtomicType => i.ordering.asInstanceOf[Ordering[Any]]
      case a: ArrayType => a.interpretedOrdering.asInstanceOf[Ordering[Any]]
      case s: StructType => s.interpretedOrdering.asInstanceOf[Ordering[Any]]
      case udt: UserDefinedType[_] => getInterpretedOrdering(udt.sqlType)
    }
  }

  def compareBinary(x: Array[Byte], y: Array[Byte]): Int = {
    for (i <- 0 until x.length; if i < y.length) {
      val v1 = x(i) & 0xff
      val v2 = y(i) & 0xff
      val res = v1 - v2
      if (res != 0) return res
    }
    x.length - y.length
  }

  /**
   * Returns true if the equals method of the elements of the data type is implemented properly.
   * This also means that they can be safely used in collections relying on the equals method,
   * as sets or maps.
   */
  def typeWithProperEquals(dataType: DataType): Boolean = dataType match {
    case BinaryType => false
    case _: AtomicType => true
    case _ => false
  }
}
