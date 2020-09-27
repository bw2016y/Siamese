package org.apache.spark.daslab.sql.types

import scala.math.Ordering
import scala.reflect.runtime.universe.typeTag

import org.apache.spark.annotation.InterfaceStability


/**
  * The data type representing `Boolean` values. Please use the singleton `DataTypes.BooleanType`.
  *
  * @since 1.3.0
  */
@InterfaceStability.Stable
class BooleanType private() extends AtomicType {
  // The companion object and this class is separated so the companion object also subclasses
  // this type. Otherwise, the companion object would be of type "BooleanType$" in byte code.
  // Defined with a private constructor so the companion object is the only possible instantiation.
  private[sql] type InternalType = Boolean
  @transient private[sql] lazy val tag = typeTag[InternalType]
  private[sql] val ordering = implicitly[Ordering[InternalType]]

  /**
    * The default size of a value of the BooleanType is 1 byte.
    */
  override def defaultSize: Int = 1

  private[spark] override def asNullable: BooleanType = this
}

/**
  * @since 1.3.0
  */
@InterfaceStability.Stable
case object BooleanType extends BooleanType
