package org.apache.spark.daslab.sql

//todo
import org.apache.spark.annotation.InterfaceStability

/**
 * A container for a [[Dataset]], used for implicit conversions in Scala.
 *
 * To use this, import implicit conversions in SQL:
 * {{{
 *   val spark: SparkSession = ...
 *   import spark.implicits._
 * }}}
 *
 * @since 1.6.0
 */
@InterfaceStability.Stable
case class DatasetHolder[T] private[sql](private val ds: Dataset[T]) {

  // This is declared with parentheses to prevent the Scala compiler from treating
  // `rdd.toDS("1")` as invoking this toDS and then apply on the returned Dataset.
  def toDS(): Dataset[T] = ds

  // This is declared with parentheses to prevent the Scala compiler from treating
  // `rdd.toDF("1")` as invoking this toDF and then apply on the returned DataFrame.
  def toDF(): DataFrame = ds.toDF()

  def toDF(colNames: String*): DataFrame = ds.toDF(colNames : _*)
}
