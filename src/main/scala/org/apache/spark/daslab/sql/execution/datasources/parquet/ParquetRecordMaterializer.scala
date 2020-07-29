package org.apache.spark.daslab.sql.execution.datasources.parquet


import java.util.TimeZone

import org.apache.parquet.io.api.{GroupConverter, RecordMaterializer}
import org.apache.parquet.schema.MessageType

import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow
import org.apache.spark.daslab.sql.types.StructType

/**
 * A [[RecordMaterializer]] for Catalyst rows.
 *
 * @param parquetSchema Parquet schema of the records to be read
 * @param catalystSchema Catalyst schema of the rows to be constructed
 * @param schemaConverter A Parquet-Catalyst schema converter that helps initializing row converters
 */
private[parquet] class ParquetRecordMaterializer(
                                                  parquetSchema: MessageType,
                                                  catalystSchema: StructType,
                                                  schemaConverter: ParquetToSparkSchemaConverter,
                                                  convertTz: Option[TimeZone])
  extends RecordMaterializer[UnsafeRow] {

  private val rootConverter =
    new ParquetRowConverter(schemaConverter, parquetSchema, catalystSchema, convertTz, NoopUpdater)

  override def getCurrentRecord: UnsafeRow = rootConverter.currentRecord

  override def getRootConverter: GroupConverter = rootConverter
}
