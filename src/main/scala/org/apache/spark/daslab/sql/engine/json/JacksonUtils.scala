

package org.apache.spark.daslab.sql.engine.json

import com.fasterxml.jackson.core.{JsonParser, JsonToken}

import org.apache.spark.daslab.sql.types._

object JacksonUtils {
  /**
   * Advance the parser until a null or a specific token is found
   */
  def nextUntil(parser: JsonParser, stopOn: JsonToken): Boolean = {
    parser.nextToken() match {
      case null => false
      case x => x != stopOn
    }
  }

  def verifyType(name: String, dataType: DataType): Unit = {
    dataType match {
      case NullType | BooleanType | ByteType | ShortType | IntegerType | LongType | FloatType |
           DoubleType | StringType | TimestampType | DateType | BinaryType | _: DecimalType =>

      case st: StructType => st.foreach(field => verifyType(field.name, field.dataType))

      case at: ArrayType => verifyType(name, at.elementType)

      // For MapType, its keys are treated as a string (i.e. calling `toString`) basically when
      // generating JSON, so we only care if the values are valid for JSON.
      case mt: MapType => verifyType(name, mt.valueType)

      case udt: UserDefinedType[_] => verifyType(name, udt.sqlType)

      case _ =>
        throw new UnsupportedOperationException(
          s"Unable to convert column $name of type ${dataType.catalogString} to JSON.")
    }
  }

  /**
   * Verify if the schema is supported in JSON parsing.
   */
  def verifySchema(schema: StructType): Unit = {
    schema.foreach(field => verifyType(field.name, field.dataType))
  }
}
