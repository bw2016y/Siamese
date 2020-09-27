package org.apache.spark.daslab.sql.engine.plans.logical


import org.apache.spark.daslab.sql.Row
import org.apache.spark.daslab.sql.engine.{CatalystTypeConverters, InternalRow}
import org.apache.spark.daslab.sql.engine.analysis
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, Literal}
import org.apache.spark.daslab.sql.engine.plans.logical.statsEstimation.EstimationUtils
import org.apache.spark.daslab.sql.types.{StructField, StructType}

object LocalRelation {
  def apply(output: Attribute*): LocalRelation = new LocalRelation(output)

  def apply(output1: StructField, output: StructField*): LocalRelation = {
    new LocalRelation(StructType(output1 +: output).toAttributes)
  }

  def fromExternalRows(output: Seq[Attribute], data: Seq[Row]): LocalRelation = {
    val schema = StructType.fromAttributes(output)
    val converter = CatalystTypeConverters.createToCatalystConverter(schema)
    LocalRelation(output, data.map(converter(_).asInstanceOf[InternalRow]))
  }

  def fromProduct(output: Seq[Attribute], data: Seq[Product]): LocalRelation = {
    val schema = StructType.fromAttributes(output)
    val converter = CatalystTypeConverters.createToCatalystConverter(schema)
    LocalRelation(output, data.map(converter(_).asInstanceOf[InternalRow]))
  }
}

/**
 * Logical plan node for scanning data from a local collection.
 *
 * @param data The local collection holding the data. It doesn't need to be sent to executors
 *             and then doesn't need to be serializable.
 */
case class LocalRelation(
                          output: Seq[Attribute],
                          data: Seq[InternalRow] = Nil,
                          // Indicates whether this relation has data from a streaming source.
                          override val isStreaming: Boolean = false)
  extends LeafNode with analysis.MultiInstanceRelation {

  // A local relation must have resolved output.
  require(output.forall(_.resolved), "Unresolved attributes found when constructing LocalRelation.")

  /**
   * Returns an identical copy of this relation with new exprIds for all attributes.  Different
   * attributes are required when a relation is going to be included multiple times in the same
   * query.
   */
  override final def newInstance(): this.type = {
    LocalRelation(output.map(_.newInstance()), data, isStreaming).asInstanceOf[this.type]
  }

  override protected def stringArgs: Iterator[Any] = {
    if (data.isEmpty) {
      Iterator("<empty>", output)
    } else {
      Iterator(output)
    }
  }

  override def computeStats(): Statistics =
    Statistics(sizeInBytes = EstimationUtils.getSizePerRow(output) * data.length)

  def toSQL(inlineTableName: String): String = {
    require(data.nonEmpty)
    val types = output.map(_.dataType)
    val rows = data.map { row =>
      val cells = row.toSeq(types).zip(types).map { case (v, tpe) => Literal(v, tpe).sql }
      cells.mkString("(", ", ", ")")
    }
    "VALUES " + rows.mkString(", ") +
      " AS " + inlineTableName +
      output.map(_.name).mkString("(", ", ", ")")
  }
}
