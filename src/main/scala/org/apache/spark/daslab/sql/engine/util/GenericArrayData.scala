package org.apache.spark.daslab.sql.engine.util

import scala.collection.JavaConverters._

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.types.{DataType, Decimal}

//todo spark core
import org.apache.spark.unsafe.types.{CalendarInterval, UTF8String}

private object GenericArrayData {

  // SPARK-16634: Workaround for JVM bug present in some 1.7 versions.
  def anyToSeq(seqOrArray: Any): Seq[Any] = seqOrArray match {
    case seq: Seq[Any] => seq
    case array: Array[_] => array.toSeq
  }

}

class GenericArrayData(val array: Array[Any]) extends ArrayData {

  def this(seq: Seq[Any]) = this(seq.toArray)
  def this(list: java.util.List[Any]) = this(list.asScala)

  // TODO: This is boxing.  We should specialize.
  def this(primitiveArray: Array[Int]) = this(primitiveArray.toSeq)
  def this(primitiveArray: Array[Long]) = this(primitiveArray.toSeq)
  def this(primitiveArray: Array[Float]) = this(primitiveArray.toSeq)
  def this(primitiveArray: Array[Double]) = this(primitiveArray.toSeq)
  def this(primitiveArray: Array[Short]) = this(primitiveArray.toSeq)
  def this(primitiveArray: Array[Byte]) = this(primitiveArray.toSeq)
  def this(primitiveArray: Array[Boolean]) = this(primitiveArray.toSeq)

  def this(seqOrArray: Any) = this(GenericArrayData.anyToSeq(seqOrArray))

  override def copy(): ArrayData = {
    val newValues = new Array[Any](array.length)
    var i = 0
    while (i < array.length) {
      newValues(i) = InternalRow.copyValue(array(i))
      i += 1
    }
    new GenericArrayData(newValues)
  }

  override def numElements(): Int = array.length

  private def getAs[T](ordinal: Int) = array(ordinal).asInstanceOf[T]
  override def isNullAt(ordinal: Int): Boolean = getAs[AnyRef](ordinal) eq null
  override def get(ordinal: Int, elementType: DataType): AnyRef = getAs(ordinal)
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
  override def getInterval(ordinal: Int): CalendarInterval = getAs(ordinal)
  override def getStruct(ordinal: Int, numFields: Int): InternalRow = getAs(ordinal)
  override def getArray(ordinal: Int): ArrayData = getAs(ordinal)
  override def getMap(ordinal: Int): MapData = getAs(ordinal)

  override def setNullAt(ordinal: Int): Unit = array(ordinal) = null

  override def update(ordinal: Int, value: Any): Unit = array(ordinal) = value

  override def toString(): String = array.mkString("[", ",", "]")

  override def equals(o: Any): Boolean = {
    if (!o.isInstanceOf[GenericArrayData]) {
      return false
    }

    val other = o.asInstanceOf[GenericArrayData]
    if (other eq null) {
      return false
    }

    val len = numElements()
    if (len != other.numElements()) {
      return false
    }

    var i = 0
    while (i < len) {
      if (isNullAt(i) != other.isNullAt(i)) {
        return false
      }
      if (!isNullAt(i)) {
        val o1 = array(i)
        val o2 = other.array(i)
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
          case _ => if (!o1.equals(o2)) {
            return false
          }
        }
      }
      i += 1
    }
    true
  }

  override def hashCode: Int = {
    var result: Int = 37
    var i = 0
    val len = numElements()
    while (i < len) {
      val update: Int =
        if (isNullAt(i)) {
          0
        } else {
          array(i) match {
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
