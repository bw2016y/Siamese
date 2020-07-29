package org.apache.spark.daslab.sql.execution.command

//todo
import org.apache.spark.SparkException


import org.apache.spark.daslab.sql._
import org.apache.spark.daslab.sql.engine.catalog._
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.execution.datasources._

/**
 * A command used to write the result of a query to a directory.
 *
 * The syntax of using this command in SQL is:
 * {{{
 *   INSERT OVERWRITE DIRECTORY (path=STRING)?
 *   USING format OPTIONS ([option1_name "option1_value", option2_name "option2_value", ...])
 *   SELECT ...
 * }}}
 *
 * @param storage storage format used to describe how the query result is stored.
 * @param provider the data source type to be used
 * @param query the logical plan representing data to write to
 * @param overwrite whthere overwrites existing directory
 */
case class InsertIntoDataSourceDirCommand(
                                           storage: CatalogStorageFormat,
                                           provider: String,
                                           query: LogicalPlan,
                                           overwrite: Boolean) extends RunnableCommand {

  override protected def innerChildren: Seq[LogicalPlan] = query :: Nil

  override def run(sparkSession: SparkSession): Seq[Row] = {
    assert(storage.locationUri.nonEmpty, "Directory path is required")
    assert(provider.nonEmpty, "Data source is required")

    // Create the relation based on the input logical plan: `query`.
    val pathOption = storage.locationUri.map("path" -> CatalogUtils.URIToString(_))

    val dataSource = DataSource(
      sparkSession,
      className = provider,
      options = storage.properties ++ pathOption,
      catalogTable = None)

    val isFileFormat = classOf[FileFormat].isAssignableFrom(dataSource.providingClass)
    if (!isFileFormat) {
      throw new SparkException(
        "Only Data Sources providing FileFormat are supported: " + dataSource.providingClass)
    }

    val saveMode = if (overwrite) SaveMode.Overwrite else SaveMode.ErrorIfExists
    try {
      sparkSession.sessionState.executePlan(dataSource.planForWriting(saveMode, query)).toRdd
    } catch {
      case ex: AnalysisException =>
        logError(s"Failed to write to directory " + storage.locationUri.toString, ex)
        throw ex
    }

    Seq.empty[Row]
  }
}
