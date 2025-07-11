package org.apache.spark.daslab.sql.execution.datasources.parquet


import java.math.{BigDecimal, BigInteger}
import java.nio.ByteOrder
import java.util.TimeZone

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

import org.apache.parquet.column.Dictionary
import org.apache.parquet.io.api.{Binary, Converter, GroupConverter, PrimitiveConverter}
import org.apache.parquet.schema.{GroupType, MessageType, OriginalType, Type}
import org.apache.parquet.schema.OriginalType.{INT_32, LIST, UTF8}
import org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.{BINARY, DOUBLE, FIXED_LEN_BYTE_ARRAY, INT32, INT64, INT96}

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.util.{ArrayBasedMapData, DateTimeUtils, GenericArrayData}
import org.apache.spark.daslab.sql.engine.util.DateTimeUtils.SQLTimestamp
import org.apache.spark.daslab.sql.types._

//todo
import org.apache.spark.unsafe.types.UTF8String
import org.apache.spark.internal.Logging


/**
 * A [[ParentContainerUpdater]] is used by a Parquet converter to set converted values to some
 * corresponding parent container. For example, a converter for a `StructType` field may set
 * converted values to a [[InternalRow]]; or a converter for array elements may append converted
 * values to an [[ArrayBuffer]].
 */
private[parquet] trait ParentContainerUpdater {
  /** Called before a record field is being converted */
  def start(): Unit = ()

  /** Called after a record field is being converted */
  def end(): Unit = ()

  def set(value: Any): Unit = ()
  def setBoolean(value: Boolean): Unit = set(value)
  def setByte(value: Byte): Unit = set(value)
  def setShort(value: Short): Unit = set(value)
  def setInt(value: Int): Unit = set(value)
  def setLong(value: Long): Unit = set(value)
  def setFloat(value: Float): Unit = set(value)
  def setDouble(value: Double): Unit = set(value)
}

/** A no-op updater used for root converter (who doesn't have a parent). */
private[parquet] object NoopUpdater extends ParentContainerUpdater

private[parquet] trait HasParentContainerUpdater {
  def updater: ParentContainerUpdater
}

/**
 * A convenient converter class for Parquet group types with a [[HasParentContainerUpdater]].
 */
private[parquet] abstract class ParquetGroupConverter(val updater: ParentContainerUpdater)
  extends GroupConverter with HasParentContainerUpdater

/**
 * Parquet converter for Parquet primitive types.  Note that not all Spark SQL atomic types
 * are handled by this converter.  Parquet primitive types are only a subset of those of Spark
 * SQL.  For example, BYTE, SHORT, and INT in Spark SQL are all covered by INT32 in Parquet.
 */
private[parquet] class ParquetPrimitiveConverter(val updater: ParentContainerUpdater)
  extends PrimitiveConverter with HasParentContainerUpdater {

  override def addBoolean(value: Boolean): Unit = updater.setBoolean(value)
  override def addInt(value: Int): Unit = updater.setInt(value)
  override def addLong(value: Long): Unit = updater.setLong(value)
  override def addFloat(value: Float): Unit = updater.setFloat(value)
  override def addDouble(value: Double): Unit = updater.setDouble(value)
  override def addBinary(value: Binary): Unit = updater.set(value.getBytes)
}

/**
 * A [[ParquetRowConverter]] is used to convert Parquet records into Catalyst [[InternalRow]]s.
 * Since Catalyst `StructType` is also a Parquet record, this converter can be used as root
 * converter.  Take the following Parquet type as an example:
 * {{{
 *   message root {
 *     required int32 f1;
 *     optional group f2 {
 *       required double f21;
 *       optional binary f22 (utf8);
 *     }
 *   }
 * }}}
 * 5 converters will be created:
 *
 * - a root [[ParquetRowConverter]] for [[MessageType]] `root`, which contains:
 *   - a [[ParquetPrimitiveConverter]] for required [[INT_32]] field `f1`, and
 *   - a nested [[ParquetRowConverter]] for optional [[GroupType]] `f2`, which contains:
 *     - a [[ParquetPrimitiveConverter]] for required [[DOUBLE]] field `f21`, and
 *     - a [[ParquetStringConverter]] for optional [[UTF8]] string field `f22`
 *
 * When used as a root converter, [[NoopUpdater]] should be used since root converters don't have
 * any "parent" container.
 *
 * @param schemaConverter A utility converter used to convert Parquet types to Catalyst types.
 * @param parquetType Parquet schema of Parquet records
 * @param catalystType Spark SQL schema that corresponds to the Parquet record type. User-defined
 *        types should have been expanded.
 * @param convertTz the optional time zone to convert to for int96 data
 * @param updater An updater which propagates converted field values to the parent container
 */
private[parquet] class ParquetRowConverter(
                                            schemaConverter: ParquetToSparkSchemaConverter,
                                            parquetType: GroupType,
                                            catalystType: StructType,
                                            convertTz: Option[TimeZone],
                                            updater: ParentContainerUpdater)
  extends ParquetGroupConverter(updater) with Logging {

  assert(
    parquetType.getFieldCount == catalystType.length,
    s"""Field counts of the Parquet schema and the Catalyst schema don't match:
       |
       |Parquet schema:
       |$parquetType
       |Catalyst schema:
       |${catalystType.prettyJson}
     """.stripMargin)

  assert(
    !catalystType.existsRecursively(_.isInstanceOf[UserDefinedType[_]]),
    s"""User-defined types in Catalyst schema should have already been expanded:
       |${catalystType.prettyJson}
     """.stripMargin)

  logDebug(
    s"""Building row converter for the following schema:
       |
       |Parquet form:
       |$parquetType
       |Catalyst form:
       |${catalystType.prettyJson}
     """.stripMargin)

  private val UTC = DateTimeUtils.TimeZoneUTC

  /**
   * Updater used together with field converters within a [[ParquetRowConverter]].  It propagates
   * converted filed values to the `ordinal`-th cell in `currentRow`.
   */
  private final class RowUpdater(row: InternalRow, ordinal: Int) extends ParentContainerUpdater {
    override def set(value: Any): Unit = row(ordinal) = value
    override def setBoolean(value: Boolean): Unit = row.setBoolean(ordinal, value)
    override def setByte(value: Byte): Unit = row.setByte(ordinal, value)
    override def setShort(value: Short): Unit = row.setShort(ordinal, value)
    override def setInt(value: Int): Unit = row.setInt(ordinal, value)
    override def setLong(value: Long): Unit = row.setLong(ordinal, value)
    override def setDouble(value: Double): Unit = row.setDouble(ordinal, value)
    override def setFloat(value: Float): Unit = row.setFloat(ordinal, value)
  }

  private val currentRow = new SpecificInternalRow(catalystType.map(_.dataType))

  private val unsafeProjection = UnsafeProjection.create(catalystType)

  /**
   * The [[UnsafeRow]] converted from an entire Parquet record.
   */
  def currentRecord: UnsafeRow = unsafeProjection(currentRow)

  // Converters for each field.
  private val fieldConverters: Array[Converter with HasParentContainerUpdater] = {
    parquetType.getFields.asScala.zip(catalystType).zipWithIndex.map {
      case ((parquetFieldType, catalystField), ordinal) =>
        // Converted field value should be set to the `ordinal`-th cell of `currentRow`
        newConverter(parquetFieldType, catalystField.dataType, new RowUpdater(currentRow, ordinal))
    }.toArray
  }

  override def getConverter(fieldIndex: Int): Converter = fieldConverters(fieldIndex)

  override def end(): Unit = {
    var i = 0
    while (i < currentRow.numFields) {
      fieldConverters(i).updater.end()
      i += 1
    }
    updater.set(currentRow)
  }

  override def start(): Unit = {
    var i = 0
    while (i < currentRow.numFields) {
      fieldConverters(i).updater.start()
      currentRow.setNullAt(i)
      i += 1
    }
  }

  /**
   * Creates a converter for the given Parquet type `parquetType` and Spark SQL data type
   * `catalystType`. Converted values are handled by `updater`.
   */
  private def newConverter(
                            parquetType: Type,
                            catalystType: DataType,
                            updater: ParentContainerUpdater): Converter with HasParentContainerUpdater = {

    catalystType match {
      case BooleanType | IntegerType | LongType | FloatType | DoubleType | BinaryType =>
        new ParquetPrimitiveConverter(updater)

      case ByteType =>
        new ParquetPrimitiveConverter(updater) {
          override def addInt(value: Int): Unit =
            updater.setByte(value.asInstanceOf[ByteType#InternalType])
        }

      case ShortType =>
        new ParquetPrimitiveConverter(updater) {
          override def addInt(value: Int): Unit =
            updater.setShort(value.asInstanceOf[ShortType#InternalType])
        }

      // For INT32 backed decimals
      case t: DecimalType if parquetType.asPrimitiveType().getPrimitiveTypeName == INT32 =>
        new ParquetIntDictionaryAwareDecimalConverter(t.precision, t.scale, updater)

      // For INT64 backed decimals
      case t: DecimalType if parquetType.asPrimitiveType().getPrimitiveTypeName == INT64 =>
        new ParquetLongDictionaryAwareDecimalConverter(t.precision, t.scale, updater)

      // For BINARY and FIXED_LEN_BYTE_ARRAY backed decimals
      case t: DecimalType
        if parquetType.asPrimitiveType().getPrimitiveTypeName == FIXED_LEN_BYTE_ARRAY ||
          parquetType.asPrimitiveType().getPrimitiveTypeName == BINARY =>
        new ParquetBinaryDictionaryAwareDecimalConverter(t.precision, t.scale, updater)

      case t: DecimalType =>
        throw new RuntimeException(
          s"Unable to create Parquet converter for decimal type ${t.json} whose Parquet type is " +
            s"$parquetType.  Parquet DECIMAL type can only be backed by INT32, INT64, " +
            "FIXED_LEN_BYTE_ARRAY, or BINARY.")

      case StringType =>
        new ParquetStringConverter(updater)

      case TimestampType if parquetType.getOriginalType == OriginalType.TIMESTAMP_MICROS =>
        new ParquetPrimitiveConverter(updater) {
          override def addLong(value: Long): Unit = {
            updater.setLong(value)
          }
        }

      case TimestampType if parquetType.getOriginalType == OriginalType.TIMESTAMP_MILLIS =>
        new ParquetPrimitiveConverter(updater) {
          override def addLong(value: Long): Unit = {
            updater.setLong(DateTimeUtils.fromMillis(value))
          }
        }

      // INT96 timestamp doesn't have a logical type, here we check the physical type instead.
      case TimestampType if parquetType.asPrimitiveType().getPrimitiveTypeName == INT96 =>
        new ParquetPrimitiveConverter(updater) {
          // Converts nanosecond timestamps stored as INT96
          override def addBinary(value: Binary): Unit = {
            assert(
              value.length() == 12,
              "Timestamps (with nanoseconds) are expected to be stored in 12-byte long binaries, " +
                s"but got a ${value.length()}-byte binary.")

            val buf = value.toByteBuffer.order(ByteOrder.LITTLE_ENDIAN)
            val timeOfDayNanos = buf.getLong
            val julianDay = buf.getInt
            val rawTime = DateTimeUtils.fromJulianDay(julianDay, timeOfDayNanos)
            val adjTime = convertTz.map(DateTimeUtils.convertTz(rawTime, _, UTC)).getOrElse(rawTime)
            updater.setLong(adjTime)
          }
        }

      case DateType =>
        new ParquetPrimitiveConverter(updater) {
          override def addInt(value: Int): Unit = {
            // DateType is not specialized in `SpecificMutableRow`, have to box it here.
            updater.set(value.asInstanceOf[DateType#InternalType])
          }
        }

      // A repeated field that is neither contained by a `LIST`- or `MAP`-annotated group nor
      // annotated by `LIST` or `MAP` should be interpreted as a required list of required
      // elements where the element type is the type of the field.
      case t: ArrayType if parquetType.getOriginalType != LIST =>
        if (parquetType.isPrimitive) {
          new RepeatedPrimitiveConverter(parquetType, t.elementType, updater)
        } else {
          new RepeatedGroupConverter(parquetType, t.elementType, updater)
        }

      case t: ArrayType =>
        new ParquetArrayConverter(parquetType.asGroupType(), t, updater)

      case t: MapType =>
        new ParquetMapConverter(parquetType.asGroupType(), t, updater)

      case t: StructType =>
        new ParquetRowConverter(
          schemaConverter, parquetType.asGroupType(), t, convertTz, new ParentContainerUpdater {
            override def set(value: Any): Unit = updater.set(value.asInstanceOf[InternalRow].copy())
          })

      case t =>
        throw new RuntimeException(
          s"Unable to create Parquet converter for data type ${t.json} " +
            s"whose Parquet type is $parquetType")
    }
  }

  /**
   * Parquet converter for strings. A dictionary is used to minimize string decoding cost.
   */
  private final class ParquetStringConverter(updater: ParentContainerUpdater)
    extends ParquetPrimitiveConverter(updater) {

    private var expandedDictionary: Array[UTF8String] = null

    override def hasDictionarySupport: Boolean = true

    override def setDictionary(dictionary: Dictionary): Unit = {
      this.expandedDictionary = Array.tabulate(dictionary.getMaxId + 1) { i =>
        UTF8String.fromBytes(dictionary.decodeToBinary(i).getBytes)
      }
    }

    override def addValueFromDictionary(dictionaryId: Int): Unit = {
      updater.set(expandedDictionary(dictionaryId))
    }

    override def addBinary(value: Binary): Unit = {
      // The underlying `ByteBuffer` implementation is guaranteed to be `HeapByteBuffer`, so here we
      // are using `Binary.toByteBuffer.array()` to steal the underlying byte array without copying
      // it.
      val buffer = value.toByteBuffer
      val offset = buffer.arrayOffset() + buffer.position()
      val numBytes = buffer.remaining()
      updater.set(UTF8String.fromBytes(buffer.array(), offset, numBytes))
    }
  }

  /**
   * Parquet converter for fixed-precision decimals.
   */
  private abstract class ParquetDecimalConverter(
                                                  precision: Int, scale: Int, updater: ParentContainerUpdater)
    extends ParquetPrimitiveConverter(updater) {

    protected var expandedDictionary: Array[Decimal] = _

    override def hasDictionarySupport: Boolean = true

    override def addValueFromDictionary(dictionaryId: Int): Unit = {
      updater.set(expandedDictionary(dictionaryId))
    }

    // Converts decimals stored as INT32
    override def addInt(value: Int): Unit = {
      addLong(value: Long)
    }

    // Converts decimals stored as INT64
    override def addLong(value: Long): Unit = {
      updater.set(decimalFromLong(value))
    }

    // Converts decimals stored as either FIXED_LENGTH_BYTE_ARRAY or BINARY
    override def addBinary(value: Binary): Unit = {
      updater.set(decimalFromBinary(value))
    }

    protected def decimalFromLong(value: Long): Decimal = {
      Decimal(value, precision, scale)
    }

    protected def decimalFromBinary(value: Binary): Decimal = {
      if (precision <= Decimal.MAX_LONG_DIGITS) {
        // Constructs a `Decimal` with an unscaled `Long` value if possible.
        val unscaled = ParquetRowConverter.binaryToUnscaledLong(value)
        Decimal(unscaled, precision, scale)
      } else {
        // Otherwise, resorts to an unscaled `BigInteger` instead.
        Decimal(new BigDecimal(new BigInteger(value.getBytes), scale), precision, scale)
      }
    }
  }

  private class ParquetIntDictionaryAwareDecimalConverter(
                                                           precision: Int, scale: Int, updater: ParentContainerUpdater)
    extends ParquetDecimalConverter(precision, scale, updater) {

    override def setDictionary(dictionary: Dictionary): Unit = {
      this.expandedDictionary = Array.tabulate(dictionary.getMaxId + 1) { id =>
        decimalFromLong(dictionary.decodeToInt(id).toLong)
      }
    }
  }

  private class ParquetLongDictionaryAwareDecimalConverter(
                                                            precision: Int, scale: Int, updater: ParentContainerUpdater)
    extends ParquetDecimalConverter(precision, scale, updater) {

    override def setDictionary(dictionary: Dictionary): Unit = {
      this.expandedDictionary = Array.tabulate(dictionary.getMaxId + 1) { id =>
        decimalFromLong(dictionary.decodeToLong(id))
      }
    }
  }

  private class ParquetBinaryDictionaryAwareDecimalConverter(
                                                              precision: Int, scale: Int, updater: ParentContainerUpdater)
    extends ParquetDecimalConverter(precision, scale, updater) {

    override def setDictionary(dictionary: Dictionary): Unit = {
      this.expandedDictionary = Array.tabulate(dictionary.getMaxId + 1) { id =>
        decimalFromBinary(dictionary.decodeToBinary(id))
      }
    }
  }

  /**
   * Parquet converter for arrays.  Spark SQL arrays are represented as Parquet lists.  Standard
   * Parquet lists are represented as a 3-level group annotated by `LIST`:
   * {{{
   *   <list-repetition> group <name> (LIST) {            <-- parquetSchema points here
   *     repeated group list {
   *       <element-repetition> <element-type> element;
   *     }
   *   }
   * }}}
   * The `parquetSchema` constructor argument points to the outermost group.
   *
   * However, before this representation is standardized, some Parquet libraries/tools also use some
   * non-standard formats to represent list-like structures.  Backwards-compatibility rules for
   * handling these cases are described in Parquet format spec.
   *
   * @see https://github.com/apache/parquet-format/blob/master/LogicalTypes.md#lists
   */
  private final class ParquetArrayConverter(
                                             parquetSchema: GroupType,
                                             catalystSchema: ArrayType,
                                             updater: ParentContainerUpdater)
    extends ParquetGroupConverter(updater) {

    private var currentArray: ArrayBuffer[Any] = _

    private val elementConverter: Converter = {
      val repeatedType = parquetSchema.getType(0)
      val elementType = catalystSchema.elementType

      // At this stage, we're not sure whether the repeated field maps to the element type or is
      // just the syntactic repeated group of the 3-level standard LIST layout. Take the following
      // Parquet LIST-annotated group type as an example:
      //
      //    optional group f (LIST) {
      //      repeated group list {
      //        optional group element {
      //          optional int32 element;
      //        }
      //      }
      //    }
      //
      // This type is ambiguous:
      //
      // 1. When interpreted as a standard 3-level layout, the `list` field is just the syntactic
      //    group, and the entire type should be translated to:
      //
      //      ARRAY<STRUCT<element: INT>>
      //
      // 2. On the other hand, when interpreted as a non-standard 2-level layout, the `list` field
      //    represents the element type, and the entire type should be translated to:
      //
      //      ARRAY<STRUCT<element: STRUCT<element: INT>>>
      //
      // Here we try to convert field `list` into a Catalyst type to see whether the converted type
      // matches the Catalyst array element type. If it doesn't match, then it's case 1; otherwise,
      // it's case 2.
      val guessedElementType = schemaConverter.convertField(repeatedType)

      if (DataType.equalsIgnoreCompatibleNullability(guessedElementType, elementType)) {
        // If the repeated field corresponds to the element type, creates a new converter using the
        // type of the repeated field.
        newConverter(repeatedType, elementType, new ParentContainerUpdater {
          override def set(value: Any): Unit = currentArray += value
        })
      } else {
        // If the repeated field corresponds to the syntactic group in the standard 3-level Parquet
        // LIST layout, creates a new converter using the only child field of the repeated field.
        assert(!repeatedType.isPrimitive && repeatedType.asGroupType().getFieldCount == 1)
        new ElementConverter(repeatedType.asGroupType().getType(0), elementType)
      }
    }

    override def getConverter(fieldIndex: Int): Converter = elementConverter

    override def end(): Unit = updater.set(new GenericArrayData(currentArray.toArray))

    // NOTE: We can't reuse the mutable `ArrayBuffer` here and must instantiate a new buffer for the
    // next value.  `Row.copy()` only copies row cells, it doesn't do deep copy to objects stored
    // in row cells.
    override def start(): Unit = currentArray = ArrayBuffer.empty[Any]

    /** Array element converter */
    private final class ElementConverter(parquetType: Type, catalystType: DataType)
      extends GroupConverter {

      private var currentElement: Any = _

      private val converter = newConverter(parquetType, catalystType, new ParentContainerUpdater {
        override def set(value: Any): Unit = currentElement = value
      })

      override def getConverter(fieldIndex: Int): Converter = converter

      override def end(): Unit = currentArray += currentElement

      override def start(): Unit = currentElement = null
    }
  }

  /** Parquet converter for maps */
  private final class ParquetMapConverter(
                                           parquetType: GroupType,
                                           catalystType: MapType,
                                           updater: ParentContainerUpdater)
    extends ParquetGroupConverter(updater) {

    private var currentKeys: ArrayBuffer[Any] = _
    private var currentValues: ArrayBuffer[Any] = _

    private val keyValueConverter = {
      val repeatedType = parquetType.getType(0).asGroupType()
      new KeyValueConverter(
        repeatedType.getType(0),
        repeatedType.getType(1),
        catalystType.keyType,
        catalystType.valueType)
    }

    override def getConverter(fieldIndex: Int): Converter = keyValueConverter

    override def end(): Unit =
      updater.set(ArrayBasedMapData(currentKeys.toArray, currentValues.toArray))

    // NOTE: We can't reuse the mutable Map here and must instantiate a new `Map` for the next
    // value.  `Row.copy()` only copies row cells, it doesn't do deep copy to objects stored in row
    // cells.
    override def start(): Unit = {
      currentKeys = ArrayBuffer.empty[Any]
      currentValues = ArrayBuffer.empty[Any]
    }

    /** Parquet converter for key-value pairs within the map. */
    private final class KeyValueConverter(
                                           parquetKeyType: Type,
                                           parquetValueType: Type,
                                           catalystKeyType: DataType,
                                           catalystValueType: DataType)
      extends GroupConverter {

      private var currentKey: Any = _

      private var currentValue: Any = _

      private val converters = Array(
        // Converter for keys
        newConverter(parquetKeyType, catalystKeyType, new ParentContainerUpdater {
          override def set(value: Any): Unit = currentKey = value
        }),

        // Converter for values
        newConverter(parquetValueType, catalystValueType, new ParentContainerUpdater {
          override def set(value: Any): Unit = currentValue = value
        }))

      override def getConverter(fieldIndex: Int): Converter = converters(fieldIndex)

      override def end(): Unit = {
        currentKeys += currentKey
        currentValues += currentValue
      }

      override def start(): Unit = {
        currentKey = null
        currentValue = null
      }
    }
  }

  private trait RepeatedConverter {
    private var currentArray: ArrayBuffer[Any] = _

    protected def newArrayUpdater(updater: ParentContainerUpdater) = new ParentContainerUpdater {
      override def start(): Unit = currentArray = ArrayBuffer.empty[Any]
      override def end(): Unit = updater.set(new GenericArrayData(currentArray.toArray))
      override def set(value: Any): Unit = currentArray += value
    }
  }

  /**
   * A primitive converter for converting unannotated repeated primitive values to required arrays
   * of required primitives values.
   */
  private final class RepeatedPrimitiveConverter(
                                                  parquetType: Type,
                                                  catalystType: DataType,
                                                  parentUpdater: ParentContainerUpdater)
    extends PrimitiveConverter with RepeatedConverter with HasParentContainerUpdater {

    val updater: ParentContainerUpdater = newArrayUpdater(parentUpdater)

    private val elementConverter: PrimitiveConverter =
      newConverter(parquetType, catalystType, updater).asPrimitiveConverter()

    override def addBoolean(value: Boolean): Unit = elementConverter.addBoolean(value)
    override def addInt(value: Int): Unit = elementConverter.addInt(value)
    override def addLong(value: Long): Unit = elementConverter.addLong(value)
    override def addFloat(value: Float): Unit = elementConverter.addFloat(value)
    override def addDouble(value: Double): Unit = elementConverter.addDouble(value)
    override def addBinary(value: Binary): Unit = elementConverter.addBinary(value)

    override def setDictionary(dict: Dictionary): Unit = elementConverter.setDictionary(dict)
    override def hasDictionarySupport: Boolean = elementConverter.hasDictionarySupport
    override def addValueFromDictionary(id: Int): Unit = elementConverter.addValueFromDictionary(id)
  }

  /**
   * A group converter for converting unannotated repeated group values to required arrays of
   * required struct values.
   */
  private final class RepeatedGroupConverter(
                                              parquetType: Type,
                                              catalystType: DataType,
                                              parentUpdater: ParentContainerUpdater)
    extends GroupConverter with HasParentContainerUpdater with RepeatedConverter {

    val updater: ParentContainerUpdater = newArrayUpdater(parentUpdater)

    private val elementConverter: GroupConverter =
      newConverter(parquetType, catalystType, updater).asGroupConverter()

    override def getConverter(field: Int): Converter = elementConverter.getConverter(field)
    override def end(): Unit = elementConverter.end()
    override def start(): Unit = elementConverter.start()
  }
}

private[parquet] object ParquetRowConverter {
  def binaryToUnscaledLong(binary: Binary): Long = {
    // The underlying `ByteBuffer` implementation is guaranteed to be `HeapByteBuffer`, so here
    // we are using `Binary.toByteBuffer.array()` to steal the underlying byte array without
    // copying it.
    val buffer = binary.toByteBuffer
    val bytes = buffer.array()
    val start = buffer.arrayOffset() + buffer.position()
    val end = buffer.arrayOffset() + buffer.limit()

    var unscaled = 0L
    var i = start

    while (i < end) {
      unscaled = (unscaled << 8) | (bytes(i) & 0xff)
      i += 1
    }

    val bits = 8 * (end - start)
    unscaled = (unscaled << (64 - bits)) >> (64 - bits)
    unscaled
  }

  def binaryToSQLTimestamp(binary: Binary): SQLTimestamp = {
    assert(binary.length() == 12, s"Timestamps (with nanoseconds) are expected to be stored in" +
      s" 12-byte long binaries. Found a ${binary.length()}-byte binary instead.")
    val buffer = binary.toByteBuffer.order(ByteOrder.LITTLE_ENDIAN)
    val timeOfDayNanos = buffer.getLong
    val julianDay = buffer.getInt
    DateTimeUtils.fromJulianDay(julianDay, timeOfDayNanos)
  }
}

