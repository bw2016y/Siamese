package org.apache.spark.daslab.sql.types

import scala.math.Ordering

import org.json4s.JsonDSL._

import org.apache.spark.annotation.InterfaceStability
import org.apache.spark.daslab.sql.engine.util.ArrayData  //ArrayType依赖于ArrayData体系

/**
  * Companion object for ArrayType.
  *
  * @since 1.3.0
  */
@InterfaceStability.Stable
object ArrayType extends AbstractDataType {
  /**
    * Construct a [[ArrayType]] object with the given element type. The `containsNull` is true.
    */
  def apply(elementType: DataType): ArrayType = ArrayType(elementType, containsNull = true)

  override private[sql] def defaultConcreteType: DataType = ArrayType(NullType, containsNull = true)

  override private[sql] def acceptsType(other: DataType): Boolean = {
    other.isInstanceOf[ArrayType]
  }

  override private[spark] def simpleString: String = "array"
}

/**
  * The data type for collections of multiple values.
  * Internally these are represented as columns that contain a ``scala.collection.Seq``.
  *
  * Please use `DataTypes.createArrayType()` to create a specific instance.
  *
  * An [[ArrayType]] object comprises two fields, `elementType: [[DataType]]` and
  * `containsNull: Boolean`. The field of `elementType` is used to specify the type of
  * array elements. The field of `containsNull` is used to specify if the array has `null` values.
  *
  * @param elementType The data type of values.
  * @param containsNull Indicates if values have `null` values
  *
  * @since 1.3.0
  */
@InterfaceStability.Stable
case class ArrayType(elementType: DataType, containsNull: Boolean) extends DataType {

  /** No-arg constructor for kryo. */
  protected def this() = this(null, false)

  private[sql] def buildFormattedString(prefix: String, builder: StringBuilder): Unit = {
    builder.append(
      s"$prefix-- element: ${elementType.typeName} (containsNull = $containsNull)\n")
    DataType.buildFormattedString(elementType, s"$prefix    |", builder)
  }

  override private[sql] def jsonValue =
    ("type" -> typeName) ~
      ("elementType" -> elementType.jsonValue) ~
      ("containsNull" -> containsNull)

  /**
    * The default size of a value of the ArrayType is the default size of the element type.
    * We assume that there is only 1 element on average in an array. See SPARK-18853.
    */
  override def defaultSize: Int = 1 * elementType.defaultSize

  override def simpleString: String = s"array<${elementType.simpleString}>"

  override def catalogString: String = s"array<${elementType.catalogString}>"

  override def sql: String = s"ARRAY<${elementType.sql}>"

  override private[spark] def asNullable: ArrayType =
    ArrayType(elementType.asNullable, containsNull = true)

  override private[spark] def existsRecursively(f: (DataType) => Boolean): Boolean = {
    f(this) || elementType.existsRecursively(f)
  }

  @transient
  private[sql] lazy val interpretedOrdering: Ordering[ArrayData] = new Ordering[ArrayData] {
    private[this] val elementOrdering: Ordering[Any] = elementType match {
      case dt: AtomicType => dt.ordering.asInstanceOf[Ordering[Any]]
      case a : ArrayType => a.interpretedOrdering.asInstanceOf[Ordering[Any]]
      case s: StructType => s.interpretedOrdering.asInstanceOf[Ordering[Any]]
      case other =>
        throw new IllegalArgumentException(
          s"Type ${other.catalogString} does not support ordered operations")
    }

    def compare(x: ArrayData, y: ArrayData): Int = {
      val leftArray = x
      val rightArray = y
      val minLength = scala.math.min(leftArray.numElements(), rightArray.numElements())
      var i = 0
      while (i < minLength) {
        val isNullLeft = leftArray.isNullAt(i)
        val isNullRight = rightArray.isNullAt(i)
        if (isNullLeft && isNullRight) {
          // Do nothing.
        } else if (isNullLeft) {
          return -1
        } else if (isNullRight) {
          return 1
        } else {
          val comp =
            elementOrdering.compare(
              leftArray.get(i, elementType),
              rightArray.get(i, elementType))
          if (comp != 0) {
            return comp
          }
        }
        i += 1
      }
      if (leftArray.numElements() < rightArray.numElements()) {
        return -1
      } else if (leftArray.numElements() > rightArray.numElements()) {
        return 1
      } else {
        return 0
      }
    }
  }
}
