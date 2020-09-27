package org.apache.spark.daslab.sql.execution


import org.apache.spark.daslab.sql.engine.expressions.{Expression, GetStructField}
import org.apache.spark.daslab.sql.types.StructField

/**
 * A Scala extractor that extracts the child expression and struct field from a [[GetStructField]].
 * This is in contrast to the [[GetStructField]] case class extractor which returns the field
 * ordinal instead of the field itself.
 */
private[execution] object GetStructFieldObject {
  def unapply(getStructField: GetStructField): Option[(Expression, StructField)] =
    Some((
      getStructField.child,
      getStructField.childSchema(getStructField.ordinal)))
}
