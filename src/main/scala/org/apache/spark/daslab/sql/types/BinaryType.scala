package org.apache.spark.daslab.sql.types

import scala.math.Ordering
import scala.reflect.runtime.universe.typeTag

import org.apache.spark.annotation.InterfaceStability
import org.apache.spark.daslab.sql.engine.util.TypeUtils


/**
  * The data type representing `Array[Byte]` values.
  * Please use the singleton `DataTypes.BinaryType`.
  */
@InterfaceStability.Stable
class BinaryType private() extends AtomicType {
  // The companion object and this class is separated so the companion object also subclasses
  // this type. Otherwise, the companion object would be of type "BinaryType$" in byte code.
  // Defined with a private constructor so the companion object is the only possible instantiation.

  private[sql] type InternalType = Array[Byte]

  @transient private[sql] lazy val tag = typeTag[InternalType]

  private[sql] val ordering = new Ordering[InternalType] {
    def compare(x: Array[Byte], y: Array[Byte]): Int = {
      TypeUtils.compareBinary(x, y)
    }
  }

  /**
    * The default size of a value of the BinaryType is 100 bytes.
    */
  override def defaultSize: Int = 100

  private[spark] override def asNullable: BinaryType = this
}

/**
  * @since 1.3.0
  */
@InterfaceStability.Stable
case object BinaryType extends BinaryType
