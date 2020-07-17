package org.apache.spark.daslab.sql.execution.datasources


import org.apache.spark.daslab.sql.engine.analysis.MultiInstanceRelation
import org.apache.spark.daslab.sql.engine.catalog.CatalogTable
import org.apache.spark.daslab.sql.engine.expressions.{AttributeMap,AttributeReference}
import org.apache.spark.daslab.sql.engine.plans.QueryPlan
import org.apache.spark.daslab.sql.engine.plans.logical.{LeafNode,LogicalPlan,Statistics}
import org.apache.spark.daslab.sql.sources.BaseRelation


//todo spark core
import org.apache.spark.util.Utils

/**
 * Used to link a [[BaseRelation]] in to a logical query plan.
 */
case class LogicalRelation(
                            relation: BaseRelation,
                            output: Seq[AttributeReference],
                            catalogTable: Option[CatalogTable],
                            override val isStreaming: Boolean)
  extends LeafNode with MultiInstanceRelation {

  // Only care about relation when canonicalizing.
  override def doCanonicalize(): LogicalPlan = copy(
    output = output.map(QueryPlan.normalizeExprId(_, output)),
    catalogTable = None)

  override def computeStats(): Statistics = {
    catalogTable
      .flatMap(_.stats.map(_.toPlanStats(output, conf.cboEnabled)))
      .getOrElse(Statistics(sizeInBytes = relation.sizeInBytes))
  }

  /** Used to lookup original attribute capitalization */
  val attributeMap: AttributeMap[AttributeReference] = AttributeMap(output.map(o => (o, o)))

  /**
   * Returns a new instance of this LogicalRelation. According to the semantics of
   * MultiInstanceRelation, this method returns a copy of this object with
   * unique expression ids. We respect the `expectedOutputAttributes` and create
   * new instances of attributes in it.
   */
  override def newInstance(): LogicalRelation = {
    this.copy(output = output.map(_.newInstance()))
  }

  override def refresh(): Unit = relation match {
    case fs: HadoopFsRelation => fs.location.refresh()
    case _ =>  // Do nothing.
  }

  override def simpleString: String = s"Relation[${Utils.truncatedString(output, ",")}] $relation"
}

object LogicalRelation {
  def apply(relation: BaseRelation, isStreaming: Boolean = false): LogicalRelation =
    LogicalRelation(relation, relation.schema.toAttributes, None, isStreaming)

  def apply(relation: BaseRelation, table: CatalogTable): LogicalRelation =
    LogicalRelation(relation, relation.schema.toAttributes, Some(table), false)
}
