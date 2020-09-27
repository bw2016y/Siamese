package org.apache.spark.daslab.sql.execution.datasources.v2



import org.apache.commons.lang3.StringUtils

import org.apache.spark.daslab.sql.engine.expressions.{Attribute, Expression}
import org.apache.spark.daslab.sql.sources.DataSourceRegister
import org.apache.spark.daslab.sql.sources.v2.DataSourceV2

//todo
import org.apache.spark.util.Utils

/**
 * A trait that can be used by data source v2 related query plans(both logical and physical), to
 * provide a string format of the data source information for explain.
 */
trait DataSourceV2StringFormat {

  /**
   * The instance of this data source implementation. Note that we only consider its class in
   * equals/hashCode, not the instance itself.
   */
  def source: DataSourceV2

  /**
   * The output of the data source reader, w.r.t. column pruning.
   */
  def output: Seq[Attribute]

  /**
   * The options for this data source reader.
   */
  def options: Map[String, String]

  /**
   * The filters which have been pushed to the data source.
   */
  def pushedFilters: Seq[Expression]

  private def sourceName: String = source match {
    case registered: DataSourceRegister => registered.shortName()
    // source.getClass.getSimpleName can cause Malformed class name error,
    // call safer `Utils.getSimpleName` instead
    case _ => Utils.getSimpleName(source.getClass)
  }

  def metadataString: String = {
    val entries = scala.collection.mutable.ArrayBuffer.empty[(String, String)]

    if (pushedFilters.nonEmpty) {
      entries += "Filters" -> pushedFilters.mkString("[", ", ", "]")
    }

    // TODO: we should only display some standard options like path, table, etc.
    if (options.nonEmpty) {
      entries += "Options" -> Utils.redact(options).map {
        case (k, v) => s"$k=$v"
      }.mkString("[", ",", "]")
    }

    val outputStr = Utils.truncatedString(output, "[", ", ", "]")

    val entriesStr = if (entries.nonEmpty) {
      Utils.truncatedString(entries.map {
        case (key, value) => key + ": " + StringUtils.abbreviate(value, 100)
      }, " (", ", ", ")")
    } else {
      ""
    }

    s"$sourceName$outputStr$entriesStr"
  }
}
