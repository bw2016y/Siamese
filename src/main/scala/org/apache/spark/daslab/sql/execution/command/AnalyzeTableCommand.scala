package org.apache.spark.daslab.sql.execution.command



import org.apache.spark.daslab.sql.{AnalysisException, Row, SparkSession}
import org.apache.spark.daslab.sql.engine.TableIdentifier
import org.apache.spark.daslab.sql.engine.catalog.CatalogTableType


/**
 * Analyzes the given table to generate statistics, which will be used in query optimizations.
 */
case class AnalyzeTableCommand(
                                tableIdent: TableIdentifier,
                                noscan: Boolean = true) extends RunnableCommand {

  override def run(sparkSession: SparkSession): Seq[Row] = {
    val sessionState = sparkSession.sessionState
    val db = tableIdent.database.getOrElse(sessionState.catalog.getCurrentDatabase)
    val tableIdentWithDB = TableIdentifier(tableIdent.table, Some(db))
    val tableMeta = sessionState.catalog.getTableMetadata(tableIdentWithDB)
    if (tableMeta.tableType == CatalogTableType.VIEW) {
      throw new AnalysisException("ANALYZE TABLE is not supported on views.")
    }

    // Compute stats for the whole table
    val newTotalSize = CommandUtils.calculateTotalSize(sparkSession, tableMeta)
    val newRowCount =
      if (noscan) None else Some(BigInt(sparkSession.table(tableIdentWithDB).count()))

    // Update the metastore if the above statistics of the table are different from those
    // recorded in the metastore.
    val newStats = CommandUtils.compareAndGetNewStats(tableMeta.stats, newTotalSize, newRowCount)
    if (newStats.isDefined) {
      sessionState.catalog.alterTableStats(tableIdentWithDB, newStats)
    }

    Seq.empty[Row]
  }
}
