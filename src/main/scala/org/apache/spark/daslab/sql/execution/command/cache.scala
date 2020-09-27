package org.apache.spark.daslab.sql.execution.command

import org.apache.spark.daslab.sql.{Dataset, Row, SparkSession}
import org.apache.spark.daslab.sql.engine.TableIdentifier
import org.apache.spark.daslab.sql.engine.analysis.NoSuchTableException
import org.apache.spark.daslab.sql.engine.plans.QueryPlan
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan

case class CacheTableCommand(
                              tableIdent: TableIdentifier,
                              plan: Option[LogicalPlan],
                              isLazy: Boolean) extends RunnableCommand {
  require(plan.isEmpty || tableIdent.database.isEmpty,
    "Database name is not allowed in CACHE TABLE AS SELECT")

  override protected def innerChildren: Seq[QueryPlan[_]] = plan.toSeq

  override def run(sparkSession: SparkSession): Seq[Row] = {
    plan.foreach { logicalPlan =>
      Dataset.ofRows(sparkSession, logicalPlan).createTempView(tableIdent.quotedString)
    }
    sparkSession.catalog.cacheTable(tableIdent.quotedString)

    if (!isLazy) {
      // Performs eager caching
      sparkSession.table(tableIdent).count()
    }

    Seq.empty[Row]
  }
}


case class UncacheTableCommand(
                                tableIdent: TableIdentifier,
                                ifExists: Boolean) extends RunnableCommand {

  override def run(sparkSession: SparkSession): Seq[Row] = {
    val tableId = tableIdent.quotedString
    if (!ifExists || sparkSession.catalog.tableExists(tableId)) {
      sparkSession.catalog.uncacheTable(tableId)
    }
    Seq.empty[Row]
  }
}

/**
 * Clear all cached data from the in-memory cache.
 */
case class ClearCacheCommand() extends RunnableCommand {

  override def run(sparkSession: SparkSession): Seq[Row] = {
    sparkSession.catalog.clearCache()
    Seq.empty[Row]
  }

  /** [[org.apache.spark.daslab.sql.engine.trees.TreeNode.makeCopy()]] does not support 0-arg ctor. */
  override def makeCopy(newArgs: Array[AnyRef]): ClearCacheCommand = ClearCacheCommand()
}
