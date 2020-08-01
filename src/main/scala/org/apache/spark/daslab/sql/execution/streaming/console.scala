package org.apache.spark.daslab.sql.execution.streaming

import org.apache.spark.daslab.sql._
import org.apache.spark.daslab.sql.execution.streaming.sources.ConsoleWriter
import org.apache.spark.daslab.sql.sources.{BaseRelation, CreatableRelationProvider, DataSourceRegister}
import org.apache.spark.daslab.sql.sources.v2.{DataSourceOptions, DataSourceV2, StreamWriteSupport}
import org.apache.spark.daslab.sql.sources.v2.writer.streaming.StreamWriter
import org.apache.spark.daslab.sql.streaming.OutputMode
import org.apache.spark.daslab.sql.types.StructType

case class ConsoleRelation(override val sqlContext: SQLContext, data: DataFrame)
  extends BaseRelation {
  override def schema: StructType = data.schema
}

class ConsoleSinkProvider extends DataSourceV2
  with StreamWriteSupport
  with DataSourceRegister
  with CreatableRelationProvider {

  override def createStreamWriter(
                                   queryId: String,
                                   schema: StructType,
                                   mode: OutputMode,
                                   options: DataSourceOptions): StreamWriter = {
    new ConsoleWriter(schema, options)
  }

  def createRelation(
                      sqlContext: SQLContext,
                      mode: SaveMode,
                      parameters: Map[String, String],
                      data: DataFrame): BaseRelation = {
    // Number of rows to display, by default 20 rows
    val numRowsToShow = parameters.get("numRows").map(_.toInt).getOrElse(20)

    // Truncate the displayed data if it is too long, by default it is true
    val isTruncated = parameters.get("truncate").map(_.toBoolean).getOrElse(true)
    data.show(numRowsToShow, isTruncated)

    ConsoleRelation(sqlContext, data)
  }

  def shortName(): String = "console"
}
