package org.apache.spark.daslab.sql.types

import scala.math.{Integral, Numeric, Ordering}
import scala.reflect.runtime.universe.typeTag

import org.apache.spark.annotation.InterfaceStability

/**
  * The data type representing `Long` values. Please use the singleton `DataTypes.LongType`.
  *
  * @since 1.3.0
  */
@InterfaceStability.Stable
class LongType private() extends IntegralType {
  // The companion object and this class is separated so the companion object also subclasses
  // this type. Otherwise, the companion object would be of type "LongType$" in byte code.
  // Defined with a private constructor so the companion object is the only possible instantiation.
  private[sql] type InternalType = Long
  @transient private[sql] lazy val tag = typeTag[InternalType]
  private[sql] val numeric = implicitly[Numeric[Long]]
  private[sql] val integral = implicitly[Integral[Long]]
  private[sql] val ordering = implicitly[Ordering[InternalType]]

  /**
    * The default size of a value of the LongType is 8 bytes.
    */
  override def defaultSize: Int = 8

  override def simpleString: String = "bigint"

  private[spark] override def asNullable: LongType = this
}

/**
  * @since 1.3.0
  */
@InterfaceStability.Stable
case object LongType extends LongType
