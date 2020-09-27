package org.apache.spark.daslab.sql.execution.datasources


import org.apache.spark.daslab.sql._
import org.apache.spark.daslab.sql.engine.plans.QueryPlan
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.execution.command.RunnableCommand
import org.apache.spark.daslab.sql.sources.InsertableRelation


/**
 * Inserts the results of `query` in to a relation that extends [[InsertableRelation]].
 */
case class InsertIntoDataSourceCommand(
                                        logicalRelation: LogicalRelation,
                                        query: LogicalPlan,
                                        overwrite: Boolean)
  extends RunnableCommand {

  override protected def innerChildren: Seq[QueryPlan[_]] = Seq(query)

  override def run(sparkSession: SparkSession): Seq[Row] = {
    val relation = logicalRelation.relation.asInstanceOf[InsertableRelation]
    val data = Dataset.ofRows(sparkSession, query)
    // Data has been casted to the target relation's schema by the PreprocessTableInsertion rule.
    relation.insert(data, overwrite)

    // Re-cache all cached plans(including this relation itself, if it's cached) that refer to this
    // data source relation.
    sparkSession.sharedState.cacheManager.recacheByPlan(sparkSession, logicalRelation)

    Seq.empty[Row]
  }
}
