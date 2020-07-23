package org.apache.spark.daslab.sql.engine

import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.util.{ArrayData, MapData}
import org.apache.spark.daslab.sql.types._
import org.apache.spark.unsafe.types.UTF8String

/**
  * Spark SQL在内部使用的行的抽象类，仅包含列作为内部类型。
  */
abstract class InternalRow extends SpecializedGetters with Serializable {

  def numFields: Int

  // 仅用于测试输出
  def getString(ordinal: Int): String = getUTF8String(ordinal).toString

  def setNullAt(i: Int): Unit

  /**
    * 更新第i列的值。请注意，更新后，给定值将保留在此行中，并且调用方应保证此后此值不会更改。
    */
  def update(i: Int, value: Any): Unit

  // 默认实现
  def setBoolean(i: Int, value: Boolean): Unit = update(i, value)
  def setByte(i: Int, value: Byte): Unit = update(i, value)
  def setShort(i: Int, value: Short): Unit = update(i, value)
  def setInt(i: Int, value: Int): Unit = update(i, value)
  def setLong(i: Int, value: Long): Unit = update(i, value)
  def setFloat(i: Int, value: Float): Unit = update(i, value)
  def setDouble(i: Int, value: Double): Unit = update(i, value)

  /**
    * 更新Decimal类型的第i列
    *
    * Note: In order to support update decimal with precision > 18 in UnsafeRow,
    * CAN NOT call setNullAt() for decimal column on UnsafeRow, call setDecimal(i, null, precision).
    */
  def setDecimal(i: Int, value: Decimal, precision: Int) { update(i, value) }

  /**
    * copy当前的[[InternalRow]]对象
    */
  def copy(): InternalRow

  /** 如果当前行中是否有null值 */
  def anyNull: Boolean = {
    val len = numFields
    var i = 0
    while (i < len) {
      if (isNullAt(i)) { return true }
      i += 1
    }
    false
  }

  /* ---------------------- Scala的实用方法 ---------------------- */

//  /**
//    * 返回该Row对应的Seq，元素顺序相同
//    */
//  def toSeq(fieldTypes: Seq[DataType]): Seq[Any] = {
//    val len = numFields
//    assert(len == fieldTypes.length)
//
//    val values = new Array[Any](len)
//    var i = 0
//    while (i < len) {
//      values(i) = get(i, fieldTypes(i))
//      i += 1
//    }
//    values
//  }
//
//  def toSeq(schema: StructType): Seq[Any] = toSeq(schema.map(_.dataType))
}

object InternalRow {
  /**
    * This method can be used to construct a [[InternalRow]] with the given values.
    */
  def apply(values: Any*): InternalRow = new GenericInternalRow(values.toArray)

  /**
    * This method can be used to construct a [[InternalRow]] from a [[Seq]] of values.
    */
  def fromSeq(values: Seq[Any]): InternalRow = new GenericInternalRow(values.toArray)

  /** Returns an empty [[InternalRow]]. */
  val empty = apply()

  /**
    * Copies the given value if it's string/struct/array/map type.
    */
  def copyValue(value: Any): Any = value match {
    case v: UTF8String => v.copy()
    case v: InternalRow => v.copy()
    case v: ArrayData => v.copy()
    case v: MapData => v.copy()
    case _ => value
  }

  /**
    * Returns an accessor for an `InternalRow` with given data type. The returned accessor
    * actually takes a `SpecializedGetters` input because it can be generalized to other classes
    * that implements `SpecializedGetters` (e.g., `ArrayData`) too.
    */
  def getAccessor(dataType: DataType): (SpecializedGetters, Int) => Any = dataType match {
    case BooleanType => (input, ordinal) => input.getBoolean(ordinal)
    case ByteType => (input, ordinal) => input.getByte(ordinal)
    case ShortType => (input, ordinal) => input.getShort(ordinal)
    case IntegerType | DateType => (input, ordinal) => input.getInt(ordinal)
    case LongType | TimestampType => (input, ordinal) => input.getLong(ordinal)
    case FloatType => (input, ordinal) => input.getFloat(ordinal)
    case DoubleType => (input, ordinal) => input.getDouble(ordinal)
    case StringType => (input, ordinal) => input.getUTF8String(ordinal)
    case BinaryType => (input, ordinal) => input.getBinary(ordinal)
    case CalendarIntervalType => (input, ordinal) => input.getInterval(ordinal)
    case t: DecimalType => (input, ordinal) => input.getDecimal(ordinal, t.precision, t.scale)
    case t: StructType => (input, ordinal) => input.getStruct(ordinal, t.size)
    case _: ArrayType => (input, ordinal) => input.getArray(ordinal)
    case _: MapType => (input, ordinal) => input.getMap(ordinal)
    case u: UserDefinedType[_] => getAccessor(u.sqlType)
    case _ => (input, ordinal) => input.get(ordinal, dataType)
  }
}

