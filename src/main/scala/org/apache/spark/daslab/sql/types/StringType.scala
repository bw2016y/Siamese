package org.apache.spark.daslab.sql.types

import scala.math.Ordering
import scala.reflect.runtime.universe.typeTag

import org.apache.spark.annotation.InterfaceStability
import org.apache.spark.unsafe.types.UTF8String

/**
  * The data type representing `String` values. Please use the singleton `DataTypes.StringType`.
  *
  * @since 1.3.0
  */
@InterfaceStability.Stable
class StringType private() extends AtomicType {
  // The companion object and this class is separated so the companion object also subclasses
  // this type. Otherwise, the companion object would be of type "StringType$" in byte code.
  // Defined with a private constructor so the companion object is the only possible instantiation.
  private[sql] type InternalType = UTF8String
  @transient private[sql] lazy val tag = typeTag[InternalType]
  private[sql] val ordering = implicitly[Ordering[InternalType]]

  /**
    * The default size of a value of the StringType is 20 bytes.
    */
  override def defaultSize: Int = 20

  private[spark] override def asNullable: StringType = this
}

/**
  * @since 1.3.0
  */
@InterfaceStability.Stable
case object StringType extends StringType


