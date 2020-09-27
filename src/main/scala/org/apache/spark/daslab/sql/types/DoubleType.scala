package org.apache.spark.daslab.sql.types

import scala.math.{Fractional, Numeric, Ordering}
import scala.math.Numeric.DoubleAsIfIntegral
import scala.reflect.runtime.universe.typeTag

import org.apache.spark.annotation.InterfaceStability
import org.apache.spark.util.Utils

/**
  * The data type representing `Double` values. Please use the singleton `DataTypes.DoubleType`.
  *
  * @since 1.3.0
  */
@InterfaceStability.Stable
class DoubleType private() extends FractionalType {
  // The companion object and this class is separated so the companion object also subclasses
  // this type. Otherwise, the companion object would be of type "DoubleType$" in byte code.
  // Defined with a private constructor so the companion object is the only possible instantiation.
  private[sql] type InternalType = Double
  @transient private[sql] lazy val tag = typeTag[InternalType]
  private[sql] val numeric = implicitly[Numeric[Double]]
  private[sql] val fractional = implicitly[Fractional[Double]]
  private[sql] val ordering = new Ordering[Double] {
    override def compare(x: Double, y: Double): Int = Utils.nanSafeCompareDoubles(x, y)
  }
  private[sql] val asIntegral = DoubleAsIfIntegral

  /**
    * The default size of a value of the DoubleType is 8 bytes.
    */
  override def defaultSize: Int = 8

  private[spark] override def asNullable: DoubleType = this
}

/**
  * @since 1.3.0
  */
@InterfaceStability.Stable
case object DoubleType extends DoubleType
