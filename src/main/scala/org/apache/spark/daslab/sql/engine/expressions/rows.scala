
package org.apache.spark.daslab.sql.engine.expressions

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.Row
import org.apache.spark.daslab.sql.engine.util.{ArrayData, MapData}
import org.apache.spark.daslab.sql.types._
import org.apache.spark.unsafe.types.{CalendarInterval, UTF8String}

/**
  * [[InternalRow]]的派生版本，通过`genericGet`，实现了special getters, toString, equals/hashCode .
  */
trait BaseGenericInternalRow extends InternalRow {

  protected def genericGet(ordinal: Int): Any

  // 默认实现
  private def getAs[T](ordinal: Int) = genericGet(ordinal).asInstanceOf[T]
  override def isNullAt(ordinal: Int): Boolean = getAs[AnyRef](ordinal) eq null
  override def get(ordinal: Int, dataType: DataType): AnyRef = getAs(ordinal)
  override def getBoolean(ordinal: Int): Boolean = getAs(ordinal)
  override def getByte(ordinal: Int): Byte = getAs(ordinal)
  override def getShort(ordinal: Int): Short = getAs(ordinal)
  override def getInt(ordinal: Int): Int = getAs(ordinal)
  override def getLong(ordinal: Int): Long = getAs(ordinal)
  override def getFloat(ordinal: Int): Float = getAs(ordinal)
  override def getDouble(ordinal: Int): Double = getAs(ordinal)
  override def getDecimal(ordinal: Int, precision: Int, scale: Int): Decimal = getAs(ordinal)
  override def getUTF8String(ordinal: Int): UTF8String = getAs(ordinal)
  override def getBinary(ordinal: Int): Array[Byte] = getAs(ordinal)
  override def getArray(ordinal: Int): ArrayData = getAs(ordinal)
  override def getInterval(ordinal: Int): CalendarInterval = getAs(ordinal)
  override def getMap(ordinal: Int): MapData = getAs(ordinal)
  override def getStruct(ordinal: Int, numFields: Int): InternalRow = getAs(ordinal)

  override def toString: String = {
    if (numFields == 0) {
      "[empty row]"
    } else {
      val sb = new StringBuilder
      sb.append("[")
      sb.append(genericGet(0))
      val len = numFields
      var i = 1
      while (i < len) {
        sb.append(",")
        sb.append(genericGet(i))
        i += 1
      }
      sb.append("]")
      sb.toString()
    }
  }

  override def copy(): GenericInternalRow = {
    val len = numFields
    val newValues = new Array[Any](len)
    var i = 0
    while (i < len) {
      newValues(i) = InternalRow.copyValue(genericGet(i))
      i += 1
    }
    new GenericInternalRow(newValues)
  }

  override def equals(o: Any): Boolean = {
    if (!o.isInstanceOf[BaseGenericInternalRow]) {
      return false
    }

    val other = o.asInstanceOf[BaseGenericInternalRow]
    if (other eq null) {
      return false
    }

    val len = numFields
    if (len != other.numFields) {
      return false
    }

    var i = 0
    while (i < len) {
      if (isNullAt(i) != other.isNullAt(i)) {
        return false
      }
      if (!isNullAt(i)) {
        val o1 = genericGet(i)
        val o2 = other.genericGet(i)
        o1 match {
          case b1: Array[Byte] =>
            if (!o2.isInstanceOf[Array[Byte]] ||
              !java.util.Arrays.equals(b1, o2.asInstanceOf[Array[Byte]])) {
              return false
            }
          case f1: Float if java.lang.Float.isNaN(f1) =>
            if (!o2.isInstanceOf[Float] || ! java.lang.Float.isNaN(o2.asInstanceOf[Float])) {
              return false
            }
          case d1: Double if java.lang.Double.isNaN(d1) =>
            if (!o2.isInstanceOf[Double] || ! java.lang.Double.isNaN(o2.asInstanceOf[Double])) {
              return false
            }
          case _ => if (o1 != o2) {
            return false
          }
        }
      }
      i += 1
    }
    true
  }

  // Custom hashCode function that matches the efficient code generated version.
  override def hashCode: Int = {
    var result: Int = 37
    var i = 0
    val len = numFields
    while (i < len) {
      val update: Int =
        if (isNullAt(i)) {
          0
        } else {
          genericGet(i) match {
            case b: Boolean => if (b) 0 else 1
            case b: Byte => b.toInt
            case s: Short => s.toInt
            case i: Int => i
            case l: Long => (l ^ (l >>> 32)).toInt
            case f: Float => java.lang.Float.floatToIntBits(f)
            case d: Double =>
              val b = java.lang.Double.doubleToLongBits(d)
              (b ^ (b >>> 32)).toInt
            case a: Array[Byte] => java.util.Arrays.hashCode(a)
            case other => other.hashCode()
          }
        }
      result = 37 * result + update
      i += 1
    }
    result
  }
}

/**
  * A row implementation that uses an array of objects as the underlying storage.  Note that, while
  * the array is not copied, and thus could technically be mutated after creation, this is not
  * allowed.
  */
class GenericRow(protected[sql] val values: Array[Any]) extends Row {
  /** No-arg constructor for serialization. */
  protected def this() = this(null)

  def this(size: Int) = this(new Array[Any](size))

  override def length: Int = values.length

  override def get(i: Int): Any = values(i)

  override def toSeq: Seq[Any] = values.clone()

  override def copy(): GenericRow = this
}

class GenericRowWithSchema(values: Array[Any], override val schema: StructType)
  extends GenericRow(values) {

  /** No-arg constructor for serialization. */
  protected def this() = this(null, null)

  override def fieldIndex(name: String): Int = schema.fieldIndex(name)
}

/**
  * An internal row implementation that uses an array of objects as the underlying storage.
  * Note that, while the array is not copied, and thus could technically be mutated after creation,
  * this is not allowed.
  */
class GenericInternalRow(val values: Array[Any]) extends BaseGenericInternalRow {
  /** No-arg constructor for serialization. */
  protected def this() = this(null)

  def this(size: Int) = this(new Array[Any](size))

  override protected def genericGet(ordinal: Int) = values(ordinal)

  override def toSeq(fieldTypes: Seq[DataType]): Seq[Any] = values.clone()

  override def numFields: Int = values.length

  override def setNullAt(i: Int): Unit = { values(i) = null}

  override def update(i: Int, value: Any): Unit = { values(i) = value }
}
