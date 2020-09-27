package org.apache.spark.daslab.sql.execution.datasources


import org.apache.spark.daslab.sql.{Dataset, Row, SaveMode, SparkSession}
import org.apache.spark.daslab.sql.engine.plans.QueryPlan
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.execution.command.RunnableCommand
import org.apache.spark.daslab.sql.internal.SQLConf
import org.apache.spark.daslab.sql.sources.CreatableRelationProvider

/**
 * Saves the results of `query` in to a data source.
 *
 * Note that this command is different from [[InsertIntoDataSourceCommand]]. This command will call
 * `CreatableRelationProvider.createRelation` to write out the data, while
 * [[InsertIntoDataSourceCommand]] calls `InsertableRelation.insert`. Ideally these 2 data source
 * interfaces should do the same thing, but as we've already published these 2 interfaces and the
 * implementations may have different logic, we have to keep these 2 different commands.
 */
case class SaveIntoDataSourceCommand(
                                      query: LogicalPlan,
                                      dataSource: CreatableRelationProvider,
                                      options: Map[String, String],
                                      mode: SaveMode) extends RunnableCommand {

  override protected def innerChildren: Seq[QueryPlan[_]] = Seq(query)

  override def run(sparkSession: SparkSession): Seq[Row] = {
    dataSource.createRelation(
      sparkSession.sqlContext, mode, options, Dataset.ofRows(sparkSession, query))

    Seq.empty[Row]
  }

  override def simpleString: String = {
    val redacted = SQLConf.get.redactOptions(options)
    s"SaveIntoDataSourceCommand ${dataSource}, ${redacted}, ${mode}"
  }
}
