package org.apache.spark.daslab.sql.execution.command



import org.apache.spark.daslab.sql.{AnalysisException, Column, Row, SparkSession}
import org.apache.spark.daslab.sql.engine.TableIdentifier
import org.apache.spark.daslab.sql.engine.analysis.{NoSuchPartitionException, UnresolvedAttribute}
import org.apache.spark.daslab.sql.engine.catalog.{CatalogTable, CatalogTableType, ExternalCatalogUtils}
import org.apache.spark.daslab.sql.engine.catalog.CatalogTypes.TablePartitionSpec
import org.apache.spark.daslab.sql.engine.expressions.{And, EqualTo, Literal}
import org.apache.spark.daslab.sql.execution.datasources.PartitioningUtils

/**
 * Analyzes a given set of partitions to generate per-partition statistics, which will be used in
 * query optimizations.
 *
 * When `partitionSpec` is empty, statistics for all partitions are collected and stored in
 * Metastore.
 *
 * When `partitionSpec` mentions only some of the partition columns, all partitions with
 * matching values for specified columns are processed.
 *
 * If `partitionSpec` mentions unknown partition column, an `AnalysisException` is raised.
 *
 * By default, total number of rows and total size in bytes are calculated. When `noscan`
 * is `true`, only total size in bytes is computed.
 */
case class AnalyzePartitionCommand(
                                    tableIdent: TableIdentifier,
                                    partitionSpec: Map[String, Option[String]],
                                    noscan: Boolean = true) extends RunnableCommand {

  private def getPartitionSpec(table: CatalogTable): Option[TablePartitionSpec] = {
    val normalizedPartitionSpec =
      PartitioningUtils.normalizePartitionSpec(partitionSpec, table.partitionColumnNames,
        table.identifier.quotedString, conf.resolver)

    // Report an error if partition columns in partition specification do not form
    // a prefix of the list of partition columns defined in the table schema
    val isNotSpecified =
    table.partitionColumnNames.map(normalizedPartitionSpec.getOrElse(_, None).isEmpty)
    if (isNotSpecified.init.zip(isNotSpecified.tail).contains((true, false))) {
      val tableId = table.identifier
      val schemaColumns = table.partitionColumnNames.mkString(",")
      val specColumns = normalizedPartitionSpec.keys.mkString(",")
      throw new AnalysisException("The list of partition columns with values " +
        s"in partition specification for table '${tableId.table}' " +
        s"in database '${tableId.database.get}' is not a prefix of the list of " +
        "partition columns defined in the table schema. " +
        s"Expected a prefix of [${schemaColumns}], but got [${specColumns}].")
    }

    val filteredSpec = normalizedPartitionSpec.filter(_._2.isDefined).mapValues(_.get)
    if (filteredSpec.isEmpty) {
      None
    } else {
      Some(filteredSpec)
    }
  }

  override def run(sparkSession: SparkSession): Seq[Row] = {
    val sessionState = sparkSession.sessionState
    val db = tableIdent.database.getOrElse(sessionState.catalog.getCurrentDatabase)
    val tableIdentWithDB = TableIdentifier(tableIdent.table, Some(db))
    val tableMeta = sessionState.catalog.getTableMetadata(tableIdentWithDB)
    if (tableMeta.tableType == CatalogTableType.VIEW) {
      throw new AnalysisException("ANALYZE TABLE is not supported on views.")
    }

    val partitionValueSpec = getPartitionSpec(tableMeta)

    val partitions = sessionState.catalog.listPartitions(tableMeta.identifier, partitionValueSpec)

    if (partitions.isEmpty) {
      if (partitionValueSpec.isDefined) {
        throw new NoSuchPartitionException(db, tableIdent.table, partitionValueSpec.get)
      } else {
        // the user requested to analyze all partitions for a table which has no partitions
        // return normally, since there is nothing to do
        return Seq.empty[Row]
      }
    }

    // Compute statistics for individual partitions
    val rowCounts: Map[TablePartitionSpec, BigInt] =
      if (noscan) {
        Map.empty
      } else {
        calculateRowCountsPerPartition(sparkSession, tableMeta, partitionValueSpec)
      }

    // Update the metastore if newly computed statistics are different from those
    // recorded in the metastore.
    val newPartitions = partitions.flatMap { p =>
      val newTotalSize = CommandUtils.calculateLocationSize(
        sessionState, tableMeta.identifier, p.storage.locationUri)
      val newRowCount = rowCounts.get(p.spec)
      val newStats = CommandUtils.compareAndGetNewStats(p.stats, newTotalSize, newRowCount)
      newStats.map(_ => p.copy(stats = newStats))
    }

    if (newPartitions.nonEmpty) {
      sessionState.catalog.alterPartitions(tableMeta.identifier, newPartitions)
    }

    Seq.empty[Row]
  }

  private def calculateRowCountsPerPartition(
                                              sparkSession: SparkSession,
                                              tableMeta: CatalogTable,
                                              partitionValueSpec: Option[TablePartitionSpec]): Map[TablePartitionSpec, BigInt] = {
    val filter = if (partitionValueSpec.isDefined) {
      val filters = partitionValueSpec.get.map {
        case (columnName, value) => EqualTo(UnresolvedAttribute(columnName), Literal(value))
      }
      filters.reduce(And)
    } else {
      Literal.TrueLiteral
    }

    val tableDf = sparkSession.table(tableMeta.identifier)
    val partitionColumns = tableMeta.partitionColumnNames.map(Column(_))

    val df = tableDf.filter(Column(filter)).groupBy(partitionColumns: _*).count()

    df.collect().map { r =>
      val partitionColumnValues = partitionColumns.indices.map { i =>
        if (r.isNullAt(i)) {
          ExternalCatalogUtils.DEFAULT_PARTITION_NAME
        } else {
          r.get(i).toString
        }
      }
      val spec = tableMeta.partitionColumnNames.zip(partitionColumnValues).toMap
      val count = BigInt(r.getLong(partitionColumns.size))
      (spec, count)
    }.toMap
  }
}
