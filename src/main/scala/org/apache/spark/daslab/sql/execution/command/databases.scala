package org.apache.spark.daslab.sql.execution.command



import org.apache.spark.daslab.sql.{Row, SparkSession}
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, AttributeReference}
import org.apache.spark.daslab.sql.types.StringType


/**
 * A command for users to list the databases/schemas.
 * If a databasePattern is supplied then the databases that only match the
 * pattern would be listed.
 * The syntax of using this command in SQL is:
 * {{{
 *   SHOW (DATABASES|SCHEMAS) [LIKE 'identifier_with_wildcards'];
 * }}}
 */
case class ShowDatabasesCommand(databasePattern: Option[String]) extends RunnableCommand {

  // The result of SHOW DATABASES has one column called 'databaseName'
  override val output: Seq[Attribute] = {
    AttributeReference("databaseName", StringType, nullable = false)() :: Nil
  }

  override def run(sparkSession: SparkSession): Seq[Row] = {
    val catalog = sparkSession.sessionState.catalog
    val databases =
      databasePattern.map(catalog.listDatabases).getOrElse(catalog.listDatabases())
    databases.map { d => Row(d) }
  }
}


/**
 * Command for setting the current database.
 * {{{
 *   USE database_name;
 * }}}
 */
case class SetDatabaseCommand(databaseName: String) extends RunnableCommand {

  override def run(sparkSession: SparkSession): Seq[Row] = {
    sparkSession.sessionState.catalog.setCurrentDatabase(databaseName)
    Seq.empty[Row]
  }
}
