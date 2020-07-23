package org.apache.spark.daslab.sql.types

import scala.math.Ordering
import scala.reflect.runtime.universe.typeTag

import org.apache.spark.annotation.InterfaceStability

/**
  * The data type representing `java.sql.Timestamp` values.
  * Please use the singleton `DataTypes.TimestampType`.
  *
  * @since 1.3.0
  */
@InterfaceStability.Stable
class TimestampType private() extends AtomicType {
  // The companion object and this class is separated so the companion object also subclasses
  // this type. Otherwise, the companion object would be of type "TimestampType$" in byte code.
  // Defined with a private constructor so the companion object is the only possible instantiation.
  private[sql] type InternalType = Long

  @transient private[sql] lazy val tag = typeTag[InternalType]

  private[sql] val ordering = implicitly[Ordering[InternalType]]

  /**
    * The default size of a value of the TimestampType is 8 bytes.
    */
  override def defaultSize: Int = 8

  private[spark] override def asNullable: TimestampType = this
}

/**
  * @since 1.3.0
  */
@InterfaceStability.Stable
case object TimestampType extends TimestampType

