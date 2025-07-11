package org.apache.spark.daslab.sql.execution.columnar.compression


import java.nio.ByteBuffer
import java.nio.ByteOrder

import scala.collection.mutable

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.SpecificInternalRow
import org.apache.spark.daslab.sql.execution.columnar._
import org.apache.spark.daslab.sql.execution.vectorized.WritableColumnVector
import org.apache.spark.daslab.sql.types._


private[columnar] case object PassThrough extends CompressionScheme {
  override val typeId = 0

  override def supports(columnType: ColumnType[_]): Boolean = true

  override def encoder[T <: AtomicType](columnType: NativeColumnType[T]): Encoder[T] = {
    new this.Encoder[T](columnType)
  }

  override def decoder[T <: AtomicType](
                                         buffer: ByteBuffer, columnType: NativeColumnType[T]): Decoder[T] = {
    new this.Decoder(buffer, columnType)
  }

  class Encoder[T <: AtomicType](columnType: NativeColumnType[T]) extends compression.Encoder[T] {
    override def uncompressedSize: Int = 0

    override def compressedSize: Int = 0

    override def compress(from: ByteBuffer, to: ByteBuffer): ByteBuffer = {
      // Writes compression type ID and copies raw contents
      to.putInt(PassThrough.typeId).put(from).rewind()
      to
    }
  }

  class Decoder[T <: AtomicType](buffer: ByteBuffer, columnType: NativeColumnType[T])
    extends compression.Decoder[T] {

    override def next(row: InternalRow, ordinal: Int): Unit = {
      columnType.extract(buffer, row, ordinal)
    }

    override def hasNext: Boolean = buffer.hasRemaining

    private def putBooleans(
                             columnVector: WritableColumnVector, pos: Int, bufferPos: Int, len: Int): Unit = {
      for (i <- 0 until len) {
        columnVector.putBoolean(pos + i, (buffer.get(bufferPos + i) != 0))
      }
    }

    private def putBytes(
                          columnVector: WritableColumnVector, pos: Int, bufferPos: Int, len: Int): Unit = {
      columnVector.putBytes(pos, len, buffer.array, bufferPos)
    }

    private def putShorts(
                           columnVector: WritableColumnVector, pos: Int, bufferPos: Int, len: Int): Unit = {
      columnVector.putShorts(pos, len, buffer.array, bufferPos)
    }

    private def putInts(
                         columnVector: WritableColumnVector, pos: Int, bufferPos: Int, len: Int): Unit = {
      columnVector.putInts(pos, len, buffer.array, bufferPos)
    }

    private def putLongs(
                          columnVector: WritableColumnVector, pos: Int, bufferPos: Int, len: Int): Unit = {
      columnVector.putLongs(pos, len, buffer.array, bufferPos)
    }

    private def putFloats(
                           columnVector: WritableColumnVector, pos: Int, bufferPos: Int, len: Int): Unit = {
      columnVector.putFloats(pos, len, buffer.array, bufferPos)
    }

    private def putDoubles(
                            columnVector: WritableColumnVector, pos: Int, bufferPos: Int, len: Int): Unit = {
      columnVector.putDoubles(pos, len, buffer.array, bufferPos)
    }

    private def decompress0(
                             columnVector: WritableColumnVector,
                             capacity: Int,
                             unitSize: Int,
                             putFunction: (WritableColumnVector, Int, Int, Int) => Unit): Unit = {
      val nullsBuffer = buffer.duplicate().order(ByteOrder.nativeOrder())
      nullsBuffer.rewind()
      val nullCount = ByteBufferHelper.getInt(nullsBuffer)
      var nextNullIndex = if (nullCount > 0) ByteBufferHelper.getInt(nullsBuffer) else capacity
      var pos = 0
      var seenNulls = 0
      var bufferPos = buffer.position()
      while (pos < capacity) {
        if (pos != nextNullIndex) {
          val len = nextNullIndex - pos
          assert(len * unitSize.toLong < Int.MaxValue)
          putFunction(columnVector, pos, bufferPos, len)
          bufferPos += len * unitSize
          pos += len
        } else {
          seenNulls += 1
          nextNullIndex = if (seenNulls < nullCount) {
            ByteBufferHelper.getInt(nullsBuffer)
          } else {
            capacity
          }
          columnVector.putNull(pos)
          pos += 1
        }
      }
    }

    override def decompress(columnVector: WritableColumnVector, capacity: Int): Unit = {
      columnType.dataType match {
        case _: BooleanType =>
          val unitSize = 1
          decompress0(columnVector, capacity, unitSize, putBooleans)
        case _: ByteType =>
          val unitSize = 1
          decompress0(columnVector, capacity, unitSize, putBytes)
        case _: ShortType =>
          val unitSize = 2
          decompress0(columnVector, capacity, unitSize, putShorts)
        case _: IntegerType =>
          val unitSize = 4
          decompress0(columnVector, capacity, unitSize, putInts)
        case _: LongType =>
          val unitSize = 8
          decompress0(columnVector, capacity, unitSize, putLongs)
        case _: FloatType =>
          val unitSize = 4
          decompress0(columnVector, capacity, unitSize, putFloats)
        case _: DoubleType =>
          val unitSize = 8
          decompress0(columnVector, capacity, unitSize, putDoubles)
      }
    }
  }
}

private[columnar] case object RunLengthEncoding extends CompressionScheme {
  override val typeId = 1

  override def encoder[T <: AtomicType](columnType: NativeColumnType[T]): Encoder[T] = {
    new this.Encoder[T](columnType)
  }

  override def decoder[T <: AtomicType](
                                         buffer: ByteBuffer, columnType: NativeColumnType[T]): Decoder[T] = {
    new this.Decoder(buffer, columnType)
  }

  override def supports(columnType: ColumnType[_]): Boolean = columnType match {
    case INT | LONG | SHORT | BYTE | STRING | BOOLEAN => true
    case _ => false
  }

  class Encoder[T <: AtomicType](columnType: NativeColumnType[T]) extends compression.Encoder[T] {
    private var _uncompressedSize = 0
    private var _compressedSize = 0

    // Using `MutableRow` to store the last value to avoid boxing/unboxing cost.
    private val lastValue = new SpecificInternalRow(Seq(columnType.dataType))
    private var lastRun = 0

    override def uncompressedSize: Int = _uncompressedSize

    override def compressedSize: Int = _compressedSize

    override def gatherCompressibilityStats(row: InternalRow, ordinal: Int): Unit = {
      val value = columnType.getField(row, ordinal)
      val actualSize = columnType.actualSize(row, ordinal)
      _uncompressedSize += actualSize

      if (lastValue.isNullAt(0)) {
        columnType.copyField(row, ordinal, lastValue, 0)
        lastRun = 1
        _compressedSize += actualSize + 4
      } else {
        if (columnType.getField(lastValue, 0) == value) {
          lastRun += 1
        } else {
          _compressedSize += actualSize + 4
          columnType.copyField(row, ordinal, lastValue, 0)
          lastRun = 1
        }
      }
    }

    override def compress(from: ByteBuffer, to: ByteBuffer): ByteBuffer = {
      to.putInt(RunLengthEncoding.typeId)

      if (from.hasRemaining) {
        val currentValue = new SpecificInternalRow(Seq(columnType.dataType))
        var currentRun = 1
        val value = new SpecificInternalRow(Seq(columnType.dataType))

        columnType.extract(from, currentValue, 0)

        while (from.hasRemaining) {
          columnType.extract(from, value, 0)

          if (value.get(0, columnType.dataType) == currentValue.get(0, columnType.dataType)) {
            currentRun += 1
          } else {
            // Writes current run
            columnType.append(currentValue, 0, to)
            to.putInt(currentRun)

            // Resets current run
            columnType.copyField(value, 0, currentValue, 0)
            currentRun = 1
          }
        }

        // Writes the last run
        columnType.append(currentValue, 0, to)
        to.putInt(currentRun)
      }

      to.rewind()
      to
    }
  }

  class Decoder[T <: AtomicType](buffer: ByteBuffer, columnType: NativeColumnType[T])
    extends compression.Decoder[T] {

    private var run = 0
    private var valueCount = 0
    private var currentValue: T#InternalType = _

    override def next(row: InternalRow, ordinal: Int): Unit = {
      if (valueCount == run) {
        currentValue = columnType.extract(buffer)
        run = ByteBufferHelper.getInt(buffer)
        valueCount = 1
      } else {
        valueCount += 1
      }

      columnType.setField(row, ordinal, currentValue)
    }

    override def hasNext: Boolean = valueCount < run || buffer.hasRemaining

    private def putBoolean(columnVector: WritableColumnVector, pos: Int, value: Long): Unit = {
      columnVector.putBoolean(pos, value == 1)
    }

    private def getByte(buffer: ByteBuffer): Long = {
      buffer.get().toLong
    }

    private def putByte(columnVector: WritableColumnVector, pos: Int, value: Long): Unit = {
      columnVector.putByte(pos, value.toByte)
    }

    private def getShort(buffer: ByteBuffer): Long = {
      buffer.getShort().toLong
    }

    private def putShort(columnVector: WritableColumnVector, pos: Int, value: Long): Unit = {
      columnVector.putShort(pos, value.toShort)
    }

    private def getInt(buffer: ByteBuffer): Long = {
      buffer.getInt().toLong
    }

    private def putInt(columnVector: WritableColumnVector, pos: Int, value: Long): Unit = {
      columnVector.putInt(pos, value.toInt)
    }

    private def getLong(buffer: ByteBuffer): Long = {
      buffer.getLong()
    }

    private def putLong(columnVector: WritableColumnVector, pos: Int, value: Long): Unit = {
      columnVector.putLong(pos, value)
    }

    private def decompress0(
                             columnVector: WritableColumnVector,
                             capacity: Int,
                             getFunction: (ByteBuffer) => Long,
                             putFunction: (WritableColumnVector, Int, Long) => Unit): Unit = {
      val nullsBuffer = buffer.duplicate().order(ByteOrder.nativeOrder())
      nullsBuffer.rewind()
      val nullCount = ByteBufferHelper.getInt(nullsBuffer)
      var nextNullIndex = if (nullCount > 0) ByteBufferHelper.getInt(nullsBuffer) else -1
      var pos = 0
      var seenNulls = 0
      var runLocal = 0
      var valueCountLocal = 0
      var currentValueLocal: Long = 0

      while (valueCountLocal < runLocal || (pos < capacity)) {
        if (pos != nextNullIndex) {
          if (valueCountLocal == runLocal) {
            currentValueLocal = getFunction(buffer)
            runLocal = ByteBufferHelper.getInt(buffer)
            valueCountLocal = 1
          } else {
            valueCountLocal += 1
          }
          putFunction(columnVector, pos, currentValueLocal)
        } else {
          seenNulls += 1
          if (seenNulls < nullCount) {
            nextNullIndex = ByteBufferHelper.getInt(nullsBuffer)
          }
          columnVector.putNull(pos)
        }
        pos += 1
      }
    }

    override def decompress(columnVector: WritableColumnVector, capacity: Int): Unit = {
      columnType.dataType match {
        case _: BooleanType =>
          decompress0(columnVector, capacity, getByte, putBoolean)
        case _: ByteType =>
          decompress0(columnVector, capacity, getByte, putByte)
        case _: ShortType =>
          decompress0(columnVector, capacity, getShort, putShort)
        case _: IntegerType =>
          decompress0(columnVector, capacity, getInt, putInt)
        case _: LongType =>
          decompress0(columnVector, capacity, getLong, putLong)
        case _ => throw new IllegalStateException("Not supported type in RunLengthEncoding.")
      }
    }
  }
}

private[columnar] case object DictionaryEncoding extends CompressionScheme {
  override val typeId = 2

  // 32K unique values allowed
  val MAX_DICT_SIZE = Short.MaxValue

  override def decoder[T <: AtomicType](buffer: ByteBuffer, columnType: NativeColumnType[T])
  : Decoder[T] = {
    new this.Decoder(buffer, columnType)
  }

  override def encoder[T <: AtomicType](columnType: NativeColumnType[T]): Encoder[T] = {
    new this.Encoder[T](columnType)
  }

  override def supports(columnType: ColumnType[_]): Boolean = columnType match {
    case INT | LONG | STRING => true
    case _ => false
  }

  class Encoder[T <: AtomicType](columnType: NativeColumnType[T]) extends compression.Encoder[T] {
    // Size of the input, uncompressed, in bytes. Note that we only count until the dictionary
    // overflows.
    private var _uncompressedSize = 0

    // If the number of distinct elements is too large, we discard the use of dictionary encoding
    // and set the overflow flag to true.
    private var overflow = false

    // Total number of elements.
    private var count = 0

    // The reverse mapping of _dictionary, i.e. mapping encoded integer to the value itself.
    private var values = new mutable.ArrayBuffer[T#InternalType](1024)

    // The dictionary that maps a value to the encoded short integer.
    private val dictionary = mutable.HashMap.empty[Any, Short]

    // Size of the serialized dictionary in bytes. Initialized to 4 since we need at least an `Int`
    // to store dictionary element count.
    private var dictionarySize = 4

    override def gatherCompressibilityStats(row: InternalRow, ordinal: Int): Unit = {
      val value = columnType.getField(row, ordinal)

      if (!overflow) {
        val actualSize = columnType.actualSize(row, ordinal)
        count += 1
        _uncompressedSize += actualSize

        if (!dictionary.contains(value)) {
          if (dictionary.size < MAX_DICT_SIZE) {
            val clone = columnType.clone(value)
            values += clone
            dictionarySize += actualSize
            dictionary(clone) = dictionary.size.toShort
          } else {
            overflow = true
            values.clear()
            dictionary.clear()
          }
        }
      }
    }

    override def compress(from: ByteBuffer, to: ByteBuffer): ByteBuffer = {
      if (overflow) {
        throw new IllegalStateException(
          "Dictionary encoding should not be used because of dictionary overflow.")
      }

      to.putInt(DictionaryEncoding.typeId)
        .putInt(dictionary.size)

      var i = 0
      while (i < values.length) {
        columnType.append(values(i), to)
        i += 1
      }

      while (from.hasRemaining) {
        to.putShort(dictionary(columnType.extract(from)))
      }

      to.rewind()
      to
    }

    override def uncompressedSize: Int = _uncompressedSize

    override def compressedSize: Int = if (overflow) Int.MaxValue else dictionarySize + count * 2
  }

  class Decoder[T <: AtomicType](buffer: ByteBuffer, columnType: NativeColumnType[T])
    extends compression.Decoder[T] {
    val elementNum = ByteBufferHelper.getInt(buffer)
    private val dictionary: Array[Any] = new Array[Any](elementNum)
    private var intDictionary: Array[Int] = null
    private var longDictionary: Array[Long] = null

    columnType.dataType match {
      case _: IntegerType =>
        intDictionary = new Array[Int](elementNum)
        for (i <- 0 until elementNum) {
          val v = columnType.extract(buffer).asInstanceOf[Int]
          intDictionary(i) = v
          dictionary(i) = v
        }
      case _: LongType =>
        longDictionary = new Array[Long](elementNum)
        for (i <- 0 until elementNum) {
          val v = columnType.extract(buffer).asInstanceOf[Long]
          longDictionary(i) = v
          dictionary(i) = v
        }
      case _: StringType =>
        for (i <- 0 until elementNum) {
          val v = columnType.extract(buffer).asInstanceOf[Any]
          dictionary(i) = v
        }
    }

    override def next(row: InternalRow, ordinal: Int): Unit = {
      columnType.setField(row, ordinal, dictionary(buffer.getShort()).asInstanceOf[T#InternalType])
    }

    override def hasNext: Boolean = buffer.hasRemaining

    override def decompress(columnVector: WritableColumnVector, capacity: Int): Unit = {
      val nullsBuffer = buffer.duplicate().order(ByteOrder.nativeOrder())
      nullsBuffer.rewind()
      val nullCount = ByteBufferHelper.getInt(nullsBuffer)
      var nextNullIndex = if (nullCount > 0) ByteBufferHelper.getInt(nullsBuffer) else -1
      var pos = 0
      var seenNulls = 0
      columnType.dataType match {
        case _: IntegerType =>
          val dictionaryIds = columnVector.reserveDictionaryIds(capacity)
          columnVector.setDictionary(new ColumnDictionary(intDictionary))
          while (pos < capacity) {
            if (pos != nextNullIndex) {
              dictionaryIds.putInt(pos, buffer.getShort())
            } else {
              seenNulls += 1
              if (seenNulls < nullCount) nextNullIndex = ByteBufferHelper.getInt(nullsBuffer)
              columnVector.putNull(pos)
            }
            pos += 1
          }
        case _: LongType =>
          val dictionaryIds = columnVector.reserveDictionaryIds(capacity)
          columnVector.setDictionary(new ColumnDictionary(longDictionary))
          while (pos < capacity) {
            if (pos != nextNullIndex) {
              dictionaryIds.putInt(pos, buffer.getShort())
            } else {
              seenNulls += 1
              if (seenNulls < nullCount) {
                nextNullIndex = ByteBufferHelper.getInt(nullsBuffer)
              }
              columnVector.putNull(pos)
            }
            pos += 1
          }
        case _ => throw new IllegalStateException("Not supported type in DictionaryEncoding.")
      }
    }
  }
}

private[columnar] case object BooleanBitSet extends CompressionScheme {
  override val typeId = 3

  val BITS_PER_LONG = 64

  override def decoder[T <: AtomicType](buffer: ByteBuffer, columnType: NativeColumnType[T])
  : compression.Decoder[T] = {
    new this.Decoder(buffer).asInstanceOf[compression.Decoder[T]]
  }

  override def encoder[T <: AtomicType](columnType: NativeColumnType[T]): compression.Encoder[T] = {
    (new this.Encoder).asInstanceOf[compression.Encoder[T]]
  }

  override def supports(columnType: ColumnType[_]): Boolean = columnType == BOOLEAN

  class Encoder extends compression.Encoder[BooleanType.type] {
    private var _uncompressedSize = 0

    override def gatherCompressibilityStats(row: InternalRow, ordinal: Int): Unit = {
      _uncompressedSize += BOOLEAN.defaultSize
    }

    override def compress(from: ByteBuffer, to: ByteBuffer): ByteBuffer = {
      to.putInt(BooleanBitSet.typeId)
        // Total element count (1 byte per Boolean value)
        .putInt(from.remaining)

      while (from.remaining >= BITS_PER_LONG) {
        var word = 0: Long
        var i = 0

        while (i < BITS_PER_LONG) {
          if (BOOLEAN.extract(from)) {
            word |= (1: Long) << i
          }
          i += 1
        }

        to.putLong(word)
      }

      if (from.hasRemaining) {
        var word = 0: Long
        var i = 0

        while (from.hasRemaining) {
          if (BOOLEAN.extract(from)) {
            word |= (1: Long) << i
          }
          i += 1
        }

        to.putLong(word)
      }

      to.rewind()
      to
    }

    override def uncompressedSize: Int = _uncompressedSize

    override def compressedSize: Int = {
      val extra = if (_uncompressedSize % BITS_PER_LONG == 0) 0 else 1
      (_uncompressedSize / BITS_PER_LONG + extra) * 8 + 4
    }
  }

  class Decoder(buffer: ByteBuffer) extends compression.Decoder[BooleanType.type] {
    private val count = ByteBufferHelper.getInt(buffer)

    private var currentWord = 0: Long

    private var visited: Int = 0

    override def next(row: InternalRow, ordinal: Int): Unit = {
      val bit = visited % BITS_PER_LONG

      visited += 1
      if (bit == 0) {
        currentWord = ByteBufferHelper.getLong(buffer)
      }

      row.setBoolean(ordinal, ((currentWord >> bit) & 1) != 0)
    }

    override def hasNext: Boolean = visited < count

    override def decompress(columnVector: WritableColumnVector, capacity: Int): Unit = {
      val countLocal = count
      var currentWordLocal: Long = 0
      var visitedLocal: Int = 0
      val nullsBuffer = buffer.duplicate().order(ByteOrder.nativeOrder())
      nullsBuffer.rewind()
      val nullCount = ByteBufferHelper.getInt(nullsBuffer)
      var nextNullIndex = if (nullCount > 0) ByteBufferHelper.getInt(nullsBuffer) else -1
      var pos = 0
      var seenNulls = 0

      while (visitedLocal < countLocal) {
        if (pos != nextNullIndex) {
          val bit = visitedLocal % BITS_PER_LONG

          visitedLocal += 1
          if (bit == 0) {
            currentWordLocal = ByteBufferHelper.getLong(buffer)
          }

          columnVector.putBoolean(pos, ((currentWordLocal >> bit) & 1) != 0)
        } else {
          seenNulls += 1
          if (seenNulls < nullCount) {
            nextNullIndex = ByteBufferHelper.getInt(nullsBuffer)
          }
          columnVector.putNull(pos)
        }
        pos += 1
      }
    }
  }
}

private[columnar] case object IntDelta extends CompressionScheme {
  override def typeId: Int = 4

  override def decoder[T <: AtomicType](buffer: ByteBuffer, columnType: NativeColumnType[T])
  : compression.Decoder[T] = {
    new Decoder(buffer, INT).asInstanceOf[compression.Decoder[T]]
  }

  override def encoder[T <: AtomicType](columnType: NativeColumnType[T]): compression.Encoder[T] = {
    (new Encoder).asInstanceOf[compression.Encoder[T]]
  }

  override def supports(columnType: ColumnType[_]): Boolean = columnType == INT

  class Encoder extends compression.Encoder[IntegerType.type] {
    protected var _compressedSize: Int = 0
    protected var _uncompressedSize: Int = 0

    override def compressedSize: Int = _compressedSize
    override def uncompressedSize: Int = _uncompressedSize

    private var prevValue: Int = _

    override def gatherCompressibilityStats(row: InternalRow, ordinal: Int): Unit = {
      val value = row.getInt(ordinal)
      val delta = value - prevValue

      _compressedSize += 1

      // If this is the first integer to be compressed, or the delta is out of byte range, then give
      // up compressing this integer.
      if (_uncompressedSize == 0 || delta <= Byte.MinValue || delta > Byte.MaxValue) {
        _compressedSize += INT.defaultSize
      }

      _uncompressedSize += INT.defaultSize
      prevValue = value
    }

    override def compress(from: ByteBuffer, to: ByteBuffer): ByteBuffer = {
      to.putInt(typeId)

      if (from.hasRemaining) {
        var prev = from.getInt()
        to.put(Byte.MinValue)
        to.putInt(prev)

        while (from.hasRemaining) {
          val current = from.getInt()
          val delta = current - prev
          prev = current

          if (Byte.MinValue < delta && delta <= Byte.MaxValue) {
            to.put(delta.toByte)
          } else {
            to.put(Byte.MinValue)
            to.putInt(current)
          }
        }
      }

      to.rewind().asInstanceOf[ByteBuffer]
    }
  }

  class Decoder(buffer: ByteBuffer, columnType: NativeColumnType[IntegerType.type])
    extends compression.Decoder[IntegerType.type] {

    private var prev: Int = _

    override def hasNext: Boolean = buffer.hasRemaining

    override def next(row: InternalRow, ordinal: Int): Unit = {
      val delta = buffer.get()
      prev = if (delta > Byte.MinValue) prev + delta else ByteBufferHelper.getInt(buffer)
      row.setInt(ordinal, prev)
    }

    override def decompress(columnVector: WritableColumnVector, capacity: Int): Unit = {
      var prevLocal: Int = 0
      val nullsBuffer = buffer.duplicate().order(ByteOrder.nativeOrder())
      nullsBuffer.rewind()
      val nullCount = ByteBufferHelper.getInt(nullsBuffer)
      var nextNullIndex = if (nullCount > 0) ByteBufferHelper.getInt(nullsBuffer) else -1
      var pos = 0
      var seenNulls = 0

      while (pos < capacity) {
        if (pos != nextNullIndex) {
          val delta = buffer.get
          prevLocal = if (delta > Byte.MinValue) { prevLocal + delta } else
          { ByteBufferHelper.getInt(buffer) }
          columnVector.putInt(pos, prevLocal)
        } else {
          seenNulls += 1
          if (seenNulls < nullCount) {
            nextNullIndex = ByteBufferHelper.getInt(nullsBuffer)
          }
          columnVector.putNull(pos)
        }
        pos += 1
      }
    }
  }
}

private[columnar] case object LongDelta extends CompressionScheme {
  override def typeId: Int = 5

  override def decoder[T <: AtomicType](buffer: ByteBuffer, columnType: NativeColumnType[T])
  : compression.Decoder[T] = {
    new Decoder(buffer, LONG).asInstanceOf[compression.Decoder[T]]
  }

  override def encoder[T <: AtomicType](columnType: NativeColumnType[T]): compression.Encoder[T] = {
    (new Encoder).asInstanceOf[compression.Encoder[T]]
  }

  override def supports(columnType: ColumnType[_]): Boolean = columnType == LONG

  class Encoder extends compression.Encoder[LongType.type] {
    protected var _compressedSize: Int = 0
    protected var _uncompressedSize: Int = 0

    override def compressedSize: Int = _compressedSize
    override def uncompressedSize: Int = _uncompressedSize

    private var prevValue: Long = _

    override def gatherCompressibilityStats(row: InternalRow, ordinal: Int): Unit = {
      val value = row.getLong(ordinal)
      val delta = value - prevValue

      _compressedSize += 1

      // If this is the first long integer to be compressed, or the delta is out of byte range, then
      // give up compressing this long integer.
      if (_uncompressedSize == 0 || delta <= Byte.MinValue || delta > Byte.MaxValue) {
        _compressedSize += LONG.defaultSize
      }

      _uncompressedSize += LONG.defaultSize
      prevValue = value
    }

    override def compress(from: ByteBuffer, to: ByteBuffer): ByteBuffer = {
      to.putInt(typeId)

      if (from.hasRemaining) {
        var prev = from.getLong()
        to.put(Byte.MinValue)
        to.putLong(prev)

        while (from.hasRemaining) {
          val current = from.getLong()
          val delta = current - prev
          prev = current

          if (Byte.MinValue < delta && delta <= Byte.MaxValue) {
            to.put(delta.toByte)
          } else {
            to.put(Byte.MinValue)
            to.putLong(current)
          }
        }
      }

      to.rewind().asInstanceOf[ByteBuffer]
    }
  }

  class Decoder(buffer: ByteBuffer, columnType: NativeColumnType[LongType.type])
    extends compression.Decoder[LongType.type] {

    private var prev: Long = _

    override def hasNext: Boolean = buffer.hasRemaining

    override def next(row: InternalRow, ordinal: Int): Unit = {
      val delta = buffer.get()
      prev = if (delta > Byte.MinValue) prev + delta else ByteBufferHelper.getLong(buffer)
      row.setLong(ordinal, prev)
    }

    override def decompress(columnVector: WritableColumnVector, capacity: Int): Unit = {
      var prevLocal: Long = 0
      val nullsBuffer = buffer.duplicate().order(ByteOrder.nativeOrder())
      nullsBuffer.rewind
      val nullCount = ByteBufferHelper.getInt(nullsBuffer)
      var nextNullIndex = if (nullCount > 0) ByteBufferHelper.getInt(nullsBuffer) else -1
      var pos = 0
      var seenNulls = 0

      while (pos < capacity) {
        if (pos != nextNullIndex) {
          val delta = buffer.get()
          prevLocal = if (delta > Byte.MinValue) { prevLocal + delta } else
          { ByteBufferHelper.getLong(buffer) }
          columnVector.putLong(pos, prevLocal)
        } else {
          seenNulls += 1
          if (seenNulls < nullCount) {
            nextNullIndex = ByteBufferHelper.getInt(nullsBuffer)
          }
          columnVector.putNull(pos)
        }
        pos += 1
      }
    }
  }
}
