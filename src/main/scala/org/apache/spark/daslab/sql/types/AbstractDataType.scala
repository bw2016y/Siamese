package org.apache.spark.daslab.sql.types

import scala.reflect.runtime.universe.TypeTag
import org.apache.spark.annotation.InterfaceStability
import org.apache.spark.daslab.sql.engine.expressions.Expression

/**
  * A non-concrete data type, reserved for internal uses.
  */
private[sql] abstract class AbstractDataType {
  /**
    * The default concrete type to use if we want to cast a null literal into this type.
    */
  private[sql] def defaultConcreteType: DataType

  /**
    * 是否接受某种类型
    *
    * {{{
    *   // this should return true
    *   DecimalType.acceptsType(DecimalType(10, 2))
    *
    *   // this should return true as well
    *   NumericType.acceptsType(DecimalType(10, 2))
    * }}}
    */
  private[sql] def acceptsType(other: DataType): Boolean

  /** Readable string representation for the type. */
  private[sql] def simpleString: String
}


/**
  * A collection of types that can be used to specify type constraints. The sequence also specifies
  * precedence: an earlier type takes precedence over a latter type.
  *
  * {{{
  *   TypeCollection(StringType, BinaryType)
  * }}}
  *
  * This means that we prefer StringType over BinaryType if it is possible to cast to StringType.
  */
private[sql] class TypeCollection(private val types: Seq[AbstractDataType])
  extends AbstractDataType {

  require(types.nonEmpty, s"TypeCollection ($types) cannot be empty")

  override private[sql] def defaultConcreteType: DataType = types.head.defaultConcreteType

  override private[sql] def acceptsType(other: DataType): Boolean =
    types.exists(_.acceptsType(other))

  override private[sql] def simpleString: String = {
    types.map(_.simpleString).mkString("(", " or ", ")")
  }
}


private[sql] object TypeCollection {

  /**
    * Types that include numeric types and interval type. They are only used in unary_minus,
    * unary_positive, add and subtract operations.
    */
  val NumericAndInterval = TypeCollection(NumericType, CalendarIntervalType)

  def apply(types: AbstractDataType*): TypeCollection = new TypeCollection(types)

  def unapply(typ: AbstractDataType): Option[Seq[AbstractDataType]] = typ match {
    case typ: TypeCollection => Some(typ.types)
    case _ => None
  }
}


/**
  * An `AbstractDataType` that matches any concrete data types.
  */
protected[sql] object AnyDataType extends AbstractDataType {

  // Note that since AnyDataType matches any concrete types, defaultConcreteType should never
  // be invoked.
  override private[sql] def defaultConcreteType: DataType = throw new UnsupportedOperationException

  override private[sql] def simpleString: String = "any"

  override private[sql] def acceptsType(other: DataType): Boolean = true
}


/**
  * An internal type used to represent everything that is not null, UDTs, arrays, structs, and maps.
  */
protected[sql] abstract class AtomicType extends DataType {
  private[sql] type InternalType
  private[sql] val tag: TypeTag[InternalType]
  private[sql] val ordering: Ordering[InternalType]
}

object AtomicType {
  /**
    * Enables matching against AtomicType for expressions:
    * {{{
    *   case Cast(child @ AtomicType(), StringType) =>
    *     ...
    * }}}
    */
  def unapply(e: Expression): Boolean = e.dataType.isInstanceOf[AtomicType]
}


/**
  * Numeric data types.
  *
  * @since 1.3.0
  */
@InterfaceStability.Stable
abstract class NumericType extends AtomicType {
  // Unfortunately we can't get this implicitly as that breaks Spark Serialization. In order for
  // implicitly[Numeric[JvmType]] to be valid, we have to change JvmType from a type variable to a
  // type parameter and add a numeric annotation (i.e., [JvmType : Numeric]). This gets
  // desugared by the compiler into an argument to the objects constructor. This means there is no
  // longer a no argument constructor and thus the JVM cannot serialize the object anymore.
  private[sql] val numeric: Numeric[InternalType]
}


private[spark] object NumericType extends AbstractDataType {
  /**
    * Enables matching against NumericType for expressions:
    * {{{
    *   case Cast(child @ NumericType(), StringType) =>
    *     ...
    * }}}
    */
  def unapply(e: Expression): Boolean = e.dataType.isInstanceOf[NumericType]

  override private[spark] def defaultConcreteType: DataType = DoubleType

  override private[spark] def simpleString: String = "numeric"

  override private[spark] def acceptsType(other: DataType): Boolean =
    other.isInstanceOf[NumericType]
}


private[sql] object IntegralType extends AbstractDataType {
  /**
    * Enables matching against IntegralType for expressions:
    * {{{
    *   case Cast(child @ IntegralType(), StringType) =>
    *     ...
    * }}}
    */
  def unapply(e: Expression): Boolean = e.dataType.isInstanceOf[IntegralType]

  override private[sql] def defaultConcreteType: DataType = IntegerType

  override private[sql] def simpleString: String = "integral"

  override private[sql] def acceptsType(other: DataType): Boolean = other.isInstanceOf[IntegralType]
}


private[sql] abstract class IntegralType extends NumericType {
  private[sql] val integral: Integral[InternalType]
}


private[sql] object FractionalType {
  /**
    * Enables matching against FractionalType for expressions:
    * {{{
    *   case Cast(child @ FractionalType(), StringType) =>
    *     ...
    * }}}
    */
  def unapply(e: Expression): Boolean = e.dataType.isInstanceOf[FractionalType]
}


private[sql] abstract class FractionalType extends NumericType {
  private[sql] val fractional: Fractional[InternalType]
  private[sql] val asIntegral: Integral[InternalType]
}
