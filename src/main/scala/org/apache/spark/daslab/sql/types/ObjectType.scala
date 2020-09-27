package org.apache.spark.daslab.sql.types

import scala.language.existentials

import org.apache.spark.annotation.InterfaceStability

@InterfaceStability.Evolving
object ObjectType extends AbstractDataType {
  override private[sql] def defaultConcreteType: DataType =
    throw new UnsupportedOperationException(
      s"null literals can't be casted to ${ObjectType.simpleString}")

  override private[sql] def acceptsType(other: DataType): Boolean = other match {
    case ObjectType(_) => true
    case _ => false
  }

  override private[sql] def simpleString: String = "Object"
}

/**
  * Represents a JVM object that is passing through Spark SQL expression evaluation.
  */
@InterfaceStability.Evolving
case class ObjectType(cls: Class[_]) extends DataType {
  override def defaultSize: Int = 4096

  def asNullable: DataType = this

  override def simpleString: String = cls.getName

  override def acceptsType(other: DataType): Boolean = other match {
    case ObjectType(otherCls) => cls.isAssignableFrom(otherCls)
    case _ => false
  }
}
