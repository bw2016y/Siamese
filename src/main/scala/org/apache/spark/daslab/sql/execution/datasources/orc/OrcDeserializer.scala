package org.apache.spark.daslab.sql.execution.datasources.orc

import org.apache.hadoop.io._
import org.apache.orc.mapred.{OrcList, OrcMap, OrcStruct, OrcTimestamp}
import org.apache.orc.storage.serde2.io.{DateWritable, HiveDecimalWritable}

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.{SpecificInternalRow, UnsafeArrayData}
import org.apache.spark.daslab.sql.engine.util._
import org.apache.spark.daslab.sql.types._


//todo
import org.apache.spark.unsafe.types.UTF8String

/**
 * A deserializer to deserialize ORC structs to Spark rows.
 */
class OrcDeserializer(
                       dataSchema: StructType,
                       requiredSchema: StructType,
                       requestedColIds: Array[Int]) {

  private val resultRow = new SpecificInternalRow(requiredSchema.map(_.dataType))

  // `fieldWriters(index)` is
  // - null if the respective source column is missing, since the output value
  //   is always null in this case
  // - a function that updates target column `index` otherwise.
  private val fieldWriters: Array[WritableComparable[_] => Unit] = {
    requiredSchema.zipWithIndex
      .map { case (f, index) =>
        if (requestedColIds(index) == -1) {
          null
        } else {
          val writer = newWriter(f.dataType, new RowUpdater(resultRow))
          (value: WritableComparable[_]) => writer(index, value)
        }
      }.toArray
  }

  def deserialize(orcStruct: OrcStruct): InternalRow = {
    var targetColumnIndex = 0
    while (targetColumnIndex < fieldWriters.length) {
      if (fieldWriters(targetColumnIndex) != null) {
        val value = orcStruct.getFieldValue(requestedColIds(targetColumnIndex))
        if (value == null) {
          resultRow.setNullAt(targetColumnIndex)
        } else {
          fieldWriters(targetColumnIndex)(value)
        }
      }
      targetColumnIndex += 1
    }
    resultRow
  }

  /**
   * Creates a writer to write ORC values to Catalyst data structure at the given ordinal.
   */
  private def newWriter(
                         dataType: DataType, updater: CatalystDataUpdater): (Int, WritableComparable[_]) => Unit =
    dataType match {
      case NullType => (ordinal, _) =>
        updater.setNullAt(ordinal)

      case BooleanType => (ordinal, value) =>
        updater.setBoolean(ordinal, value.asInstanceOf[BooleanWritable].get)

      case ByteType => (ordinal, value) =>
        updater.setByte(ordinal, value.asInstanceOf[ByteWritable].get)

      case ShortType => (ordinal, value) =>
        updater.setShort(ordinal, value.asInstanceOf[ShortWritable].get)

      case IntegerType => (ordinal, value) =>
        updater.setInt(ordinal, value.asInstanceOf[IntWritable].get)

      case LongType => (ordinal, value) =>
        updater.setLong(ordinal, value.asInstanceOf[LongWritable].get)

      case FloatType => (ordinal, value) =>
        updater.setFloat(ordinal, value.asInstanceOf[FloatWritable].get)

      case DoubleType => (ordinal, value) =>
        updater.setDouble(ordinal, value.asInstanceOf[DoubleWritable].get)

      case StringType => (ordinal, value) =>
        updater.set(ordinal, UTF8String.fromBytes(value.asInstanceOf[Text].copyBytes))

      case BinaryType => (ordinal, value) =>
        val binary = value.asInstanceOf[BytesWritable]
        val bytes = new Array[Byte](binary.getLength)
        System.arraycopy(binary.getBytes, 0, bytes, 0, binary.getLength)
        updater.set(ordinal, bytes)

      case DateType => (ordinal, value) =>
        updater.setInt(ordinal, DateTimeUtils.fromJavaDate(value.asInstanceOf[DateWritable].get))

      case TimestampType => (ordinal, value) =>
        updater.setLong(ordinal, DateTimeUtils.fromJavaTimestamp(value.asInstanceOf[OrcTimestamp]))

      case DecimalType.Fixed(precision, scale) => (ordinal, value) =>
        val decimal = value.asInstanceOf[HiveDecimalWritable].getHiveDecimal()
        val v = Decimal(decimal.bigDecimalValue, decimal.precision(), decimal.scale())
        v.changePrecision(precision, scale)
        updater.set(ordinal, v)

      case st: StructType => (ordinal, value) =>
        val result = new SpecificInternalRow(st)
        val fieldUpdater = new RowUpdater(result)
        val fieldConverters = st.map(_.dataType).map { dt =>
          newWriter(dt, fieldUpdater)
        }.toArray
        val orcStruct = value.asInstanceOf[OrcStruct]

        var i = 0
        while (i < st.length) {
          val value = orcStruct.getFieldValue(i)
          if (value == null) {
            result.setNullAt(i)
          } else {
            fieldConverters(i)(i, value)
          }
          i += 1
        }

        updater.set(ordinal, result)

      case ArrayType(elementType, _) => (ordinal, value) =>
        val orcArray = value.asInstanceOf[OrcList[WritableComparable[_]]]
        val length = orcArray.size()
        val result = createArrayData(elementType, length)
        val elementUpdater = new ArrayDataUpdater(result)
        val elementConverter = newWriter(elementType, elementUpdater)

        var i = 0
        while (i < length) {
          val value = orcArray.get(i)
          if (value == null) {
            result.setNullAt(i)
          } else {
            elementConverter(i, value)
          }
          i += 1
        }

        updater.set(ordinal, result)

      case MapType(keyType, valueType, _) => (ordinal, value) =>
        val orcMap = value.asInstanceOf[OrcMap[WritableComparable[_], WritableComparable[_]]]
        val length = orcMap.size()
        val keyArray = createArrayData(keyType, length)
        val keyUpdater = new ArrayDataUpdater(keyArray)
        val keyConverter = newWriter(keyType, keyUpdater)
        val valueArray = createArrayData(valueType, length)
        val valueUpdater = new ArrayDataUpdater(valueArray)
        val valueConverter = newWriter(valueType, valueUpdater)

        var i = 0
        val it = orcMap.entrySet().iterator()
        while (it.hasNext) {
          val entry = it.next()
          keyConverter(i, entry.getKey)
          val value = entry.getValue
          if (value == null) {
            valueArray.setNullAt(i)
          } else {
            valueConverter(i, value)
          }
          i += 1
        }

        updater.set(ordinal, new ArrayBasedMapData(keyArray, valueArray))

      case udt: UserDefinedType[_] => newWriter(udt.sqlType, updater)

      case _ =>
        throw new UnsupportedOperationException(s"$dataType is not supported yet.")
    }

  private def createArrayData(elementType: DataType, length: Int): ArrayData = elementType match {
    case BooleanType => UnsafeArrayData.fromPrimitiveArray(new Array[Boolean](length))
    case ByteType => UnsafeArrayData.fromPrimitiveArray(new Array[Byte](length))
    case ShortType => UnsafeArrayData.fromPrimitiveArray(new Array[Short](length))
    case IntegerType => UnsafeArrayData.fromPrimitiveArray(new Array[Int](length))
    case LongType => UnsafeArrayData.fromPrimitiveArray(new Array[Long](length))
    case FloatType => UnsafeArrayData.fromPrimitiveArray(new Array[Float](length))
    case DoubleType => UnsafeArrayData.fromPrimitiveArray(new Array[Double](length))
    case _ => new GenericArrayData(new Array[Any](length))
  }

  /**
   * A base interface for updating values inside catalyst data structure like `InternalRow` and
   * `ArrayData`.
   */
  sealed trait CatalystDataUpdater {
    def set(ordinal: Int, value: Any): Unit

    def setNullAt(ordinal: Int): Unit = set(ordinal, null)
    def setBoolean(ordinal: Int, value: Boolean): Unit = set(ordinal, value)
    def setByte(ordinal: Int, value: Byte): Unit = set(ordinal, value)
    def setShort(ordinal: Int, value: Short): Unit = set(ordinal, value)
    def setInt(ordinal: Int, value: Int): Unit = set(ordinal, value)
    def setLong(ordinal: Int, value: Long): Unit = set(ordinal, value)
    def setDouble(ordinal: Int, value: Double): Unit = set(ordinal, value)
    def setFloat(ordinal: Int, value: Float): Unit = set(ordinal, value)
  }

  final class RowUpdater(row: InternalRow) extends CatalystDataUpdater {
    override def setNullAt(ordinal: Int): Unit = row.setNullAt(ordinal)
    override def set(ordinal: Int, value: Any): Unit = row.update(ordinal, value)

    override def setBoolean(ordinal: Int, value: Boolean): Unit = row.setBoolean(ordinal, value)
    override def setByte(ordinal: Int, value: Byte): Unit = row.setByte(ordinal, value)
    override def setShort(ordinal: Int, value: Short): Unit = row.setShort(ordinal, value)
    override def setInt(ordinal: Int, value: Int): Unit = row.setInt(ordinal, value)
    override def setLong(ordinal: Int, value: Long): Unit = row.setLong(ordinal, value)
    override def setDouble(ordinal: Int, value: Double): Unit = row.setDouble(ordinal, value)
    override def setFloat(ordinal: Int, value: Float): Unit = row.setFloat(ordinal, value)
  }

  final class ArrayDataUpdater(array: ArrayData) extends CatalystDataUpdater {
    override def setNullAt(ordinal: Int): Unit = array.setNullAt(ordinal)
    override def set(ordinal: Int, value: Any): Unit = array.update(ordinal, value)

    override def setBoolean(ordinal: Int, value: Boolean): Unit = array.setBoolean(ordinal, value)
    override def setByte(ordinal: Int, value: Byte): Unit = array.setByte(ordinal, value)
    override def setShort(ordinal: Int, value: Short): Unit = array.setShort(ordinal, value)
    override def setInt(ordinal: Int, value: Int): Unit = array.setInt(ordinal, value)
    override def setLong(ordinal: Int, value: Long): Unit = array.setLong(ordinal, value)
    override def setDouble(ordinal: Int, value: Double): Unit = array.setDouble(ordinal, value)
    override def setFloat(ordinal: Int, value: Float): Unit = array.setFloat(ordinal, value)
  }
}

