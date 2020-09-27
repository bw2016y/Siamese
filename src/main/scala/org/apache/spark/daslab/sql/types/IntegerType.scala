package org.apache.spark.daslab.sql.types

import scala.math.{Integral, Numeric, Ordering}
import scala.reflect.runtime.universe.typeTag

import org.apache.spark.annotation.InterfaceStability

/**
  * The data type representing `Int` values. Please use the singleton `DataTypes.IntegerType`.
  *
  * @since 1.3.0
  */
@InterfaceStability.Stable
class IntegerType private() extends IntegralType {
  // The companion object and this class is separated so the companion object also subclasses
  // this type. Otherwise, the companion object would be of type "IntegerType$" in byte code.
  // Defined with a private constructor so the companion object is the only possible instantiation.
  private[sql] type InternalType = Int
  @transient private[sql] lazy val tag = typeTag[InternalType]
  private[sql] val numeric = implicitly[Numeric[Int]]
  private[sql] val integral = implicitly[Integral[Int]]
  private[sql] val ordering = implicitly[Ordering[InternalType]]

  /**
    * The default size of a value of the IntegerType is 4 bytes.
    */
  override def defaultSize: Int = 4

  override def simpleString: String = "int"

  private[spark] override def asNullable: IntegerType = this
}

/**
  * @since 1.3.0
  */
@InterfaceStability.Stable
case object IntegerType extends IntegerType
