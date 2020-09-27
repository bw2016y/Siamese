package org.apache.spark.daslab.sql.engine.parser

import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.daslab.sql.engine.{FunctionIdentifier, TableIdentifier}
import org.apache.spark.daslab.sql.engine.expressions.Expression
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.types.{DataType, StructType}

/**
  * Interface for a parser.
  */
@DeveloperApi
trait ParserInterface {
  /**
    * Parse a string to a [[LogicalPlan]].
    */
  @throws[ParseException]("Text cannot be parsed to a LogicalPlan")
  def parsePlan(sqlText: String): LogicalPlan

  /**
    * Parse a string to an [[Expression]].
    */
  @throws[ParseException]("Text cannot be parsed to an Expression")
  def parseExpression(sqlText: String): Expression

  /**
    * Parse a string to a [[TableIdentifier]].
    */
  @throws[ParseException]("Text cannot be parsed to a TableIdentifier")
  def parseTableIdentifier(sqlText: String): TableIdentifier

  /**
    * Parse a string to a [[FunctionIdentifier]].
    */
  @throws[ParseException]("Text cannot be parsed to a FunctionIdentifier")
  def parseFunctionIdentifier(sqlText: String): FunctionIdentifier

  /**
    * Parse a string to a [[StructType]]. The passed SQL string should be a comma separated list
    * of field definitions which will preserve the correct Hive metadata.
    */
  @throws[ParseException]("Text cannot be parsed to a schema")
  def parseTableSchema(sqlText: String): StructType

  /**
    * Parse a string to a [[DataType]].
    */
  @throws[ParseException]("Text cannot be parsed to a DataType")
  def parseDataType(sqlText: String): DataType
}
