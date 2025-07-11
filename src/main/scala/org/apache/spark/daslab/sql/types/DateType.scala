package org.apache.spark.daslab.sql.types

import scala.math.Ordering
import scala.reflect.runtime.universe.typeTag

import org.apache.spark.annotation.InterfaceStability

/**
  * A date type, supporting "0001-01-01" through "9999-12-31".
  *
  * Please use the singleton `DataTypes.DateType`.
  *
  * Internally, this is represented as the number of days from 1970-01-01.
  *
  * @since 1.3.0
  */
@InterfaceStability.Stable
class DateType private() extends AtomicType {
  // The companion object and this class is separated so the companion object also subclasses
  // this type. Otherwise, the companion object would be of type "DateType$" in byte code.
  // Defined with a private constructor so the companion object is the only possible instantiation.
  private[sql] type InternalType = Int

  @transient private[sql] lazy val tag = typeTag[InternalType]

  private[sql] val ordering = implicitly[Ordering[InternalType]]

  /**
    * The default size of a value of the DateType is 4 bytes.
    */
  override def defaultSize: Int = 4

  private[spark] override def asNullable: DateType = this
}

/**
  * @since 1.3.0
  */
@InterfaceStability.Stable
case object DateType extends DateType
