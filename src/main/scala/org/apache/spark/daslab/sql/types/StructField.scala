package org.apache.spark.daslab.sql.types



import org.json4s.JsonAST.JValue
import org.json4s.JsonDSL._

import org.apache.spark.annotation.InterfaceStability
import org.apache.spark.daslab.sql.engine.util.{escapeSingleQuotedString, quoteIdentifier}

/**
 * A field inside a StructType.
 * @param name The name of this field.
 * @param dataType The data type of this field.
 * @param nullable Indicates if values of this field can be `null` values.
 * @param metadata The metadata of this field. The metadata should be preserved during
 *                 transformation if the content of the column is not modified, e.g, in selection.
 *
 * @since 1.3.0
 */
@InterfaceStability.Stable
case class StructField(
                        name: String,
                        dataType: DataType,
                        nullable: Boolean = true,
                        metadata: Metadata = Metadata.empty) {

  /** No-arg constructor for kryo. */
  protected def this() = this(null, null)

  private[sql] def buildFormattedString(prefix: String, builder: StringBuilder): Unit = {
    builder.append(s"$prefix-- $name: ${dataType.typeName} (nullable = $nullable)\n")
    DataType.buildFormattedString(dataType, s"$prefix    |", builder)
  }

  // override the default toString to be compatible with legacy parquet files.
  override def toString: String = s"StructField($name,$dataType,$nullable)"

  private[sql] def jsonValue: JValue = {
    ("name" -> name) ~
      ("type" -> dataType.jsonValue) ~
      ("nullable" -> nullable) ~
      ("metadata" -> metadata.jsonValue)
  }

  /**
   * Updates the StructField with a new comment value.
   */
  def withComment(comment: String): StructField = {
    val newMetadata = new MetadataBuilder()
      .withMetadata(metadata)
      .putString("comment", comment)
      .build()
    copy(metadata = newMetadata)
  }

  /**
   * Return the comment of this StructField.
   */
  def getComment(): Option[String] = {
    if (metadata.contains("comment")) Option(metadata.getString("comment")) else None
  }

  /**
   * Returns a string containing a schema in DDL format. For example, the following value:
   * `StructField("eventId", IntegerType)` will be converted to `eventId` INT.
   *
   * @since 2.4.0
   */
  def toDDL: String = {
    val comment = getComment()
      .map(escapeSingleQuotedString)
      .map(" COMMENT '" + _ + "'")

    s"${quoteIdentifier(name)} ${dataType.sql}${comment.getOrElse("")}"
  }
}
