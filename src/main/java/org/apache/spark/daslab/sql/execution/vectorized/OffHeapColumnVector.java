package org.apache.spark.daslab.sql.execution.vectorized;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.google.common.annotations.VisibleForTesting;

import org.apache.spark.daslab.sql.types.*;
//todo
import org.apache.spark.unsafe.Platform;
import org.apache.spark.unsafe.types.UTF8String;

/**
 * Column data backed using offheap memory.
 */
public final class OffHeapColumnVector extends WritableColumnVector {

    private static final boolean bigEndianPlatform =
            ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);

    /**
     * Allocates columns to store elements of each field of the schema off heap.
     * Capacity is the initial capacity of the vector and it will grow as necessary. Capacity is
     * in number of elements, not number of bytes.
     */
    public static OffHeapColumnVector[] allocateColumns(int capacity, StructType schema) {
        return allocateColumns(capacity, schema.fields());
    }

    /**
     * Allocates columns to store elements of each field off heap.
     * Capacity is the initial capacity of the vector and it will grow as necessary. Capacity is
     * in number of elements, not number of bytes.
     */
    public static OffHeapColumnVector[] allocateColumns(int capacity, StructField[] fields) {
        OffHeapColumnVector[] vectors = new OffHeapColumnVector[fields.length];
        for (int i = 0; i < fields.length; i++) {
            vectors[i] = new OffHeapColumnVector(capacity, fields[i].dataType());
        }
        return vectors;
    }

    // The data stored in these two allocations need to maintain binary compatible. We can
    // directly pass this buffer to external components.
    private long nulls;
    private long data;

    // Only set if type is Array or Map.
    private long lengthData;
    private long offsetData;

    public OffHeapColumnVector(int capacity, DataType type) {
        super(capacity, type);

        nulls = 0;
        data = 0;
        lengthData = 0;
        offsetData = 0;

        reserveInternal(capacity);
        reset();
    }

    /**
     * Returns the off heap pointer for the values buffer.
     */
    @VisibleForTesting
    public long valuesNativeAddress() {
        return data;
    }

    @Override
    public void close() {
        super.close();
        Platform.freeMemory(nulls);
        Platform.freeMemory(data);
        Platform.freeMemory(lengthData);
        Platform.freeMemory(offsetData);
        nulls = 0;
        data = 0;
        lengthData = 0;
        offsetData = 0;
    }

    //
    // APIs dealing with nulls
    //

    @Override
    public void putNotNull(int rowId) {
        Platform.putByte(null, nulls + rowId, (byte) 0);
    }

    @Override
    public void putNull(int rowId) {
        Platform.putByte(null, nulls + rowId, (byte) 1);
        ++numNulls;
    }

    @Override
    public void putNulls(int rowId, int count) {
        long offset = nulls + rowId;
        for (int i = 0; i < count; ++i, ++offset) {
            Platform.putByte(null, offset, (byte) 1);
        }
        numNulls += count;
    }

    @Override
    public void putNotNulls(int rowId, int count) {
        if (!hasNull()) return;
        long offset = nulls + rowId;
        for (int i = 0; i < count; ++i, ++offset) {
            Platform.putByte(null, offset, (byte) 0);
        }
    }

    @Override
    public boolean isNullAt(int rowId) {
        return Platform.getByte(null, nulls + rowId) == 1;
    }

    //
    // APIs dealing with Booleans
    //

    @Override
    public void putBoolean(int rowId, boolean value) {
        Platform.putByte(null, data + rowId, (byte)((value) ? 1 : 0));
    }

    @Override
    public void putBooleans(int rowId, int count, boolean value) {
        byte v = (byte)((value) ? 1 : 0);
        for (int i = 0; i < count; ++i) {
            Platform.putByte(null, data + rowId + i, v);
        }
    }

    @Override
    public boolean getBoolean(int rowId) { return Platform.getByte(null, data + rowId) == 1; }

    @Override
    public boolean[] getBooleans(int rowId, int count) {
        assert(dictionary == null);
        boolean[] array = new boolean[count];
        for (int i = 0; i < count; ++i) {
            array[i] = (Platform.getByte(null, data + rowId + i) == 1);
        }
        return array;
    }

    //
    // APIs dealing with Bytes
    //

    @Override
    public void putByte(int rowId, byte value) {
        Platform.putByte(null, data + rowId, value);

    }

    @Override
    public void putBytes(int rowId, int count, byte value) {
        for (int i = 0; i < count; ++i) {
            Platform.putByte(null, data + rowId + i, value);
        }
    }

    @Override
    public void putBytes(int rowId, int count, byte[] src, int srcIndex) {
        Platform.copyMemory(src, Platform.BYTE_ARRAY_OFFSET + srcIndex, null, data + rowId, count);
    }

    @Override
    public byte getByte(int rowId) {
        if (dictionary == null) {
            return Platform.getByte(null, data + rowId);
        } else {
            return (byte) dictionary.decodeToInt(dictionaryIds.getDictId(rowId));
        }
    }

    @Override
    public byte[] getBytes(int rowId, int count) {
        assert(dictionary == null);
        byte[] array = new byte[count];
        Platform.copyMemory(null, data + rowId, array, Platform.BYTE_ARRAY_OFFSET, count);
        return array;
    }

    @Override
    protected UTF8String getBytesAsUTF8String(int rowId, int count) {
        return UTF8String.fromAddress(null, data + rowId, count);
    }

    //
    // APIs dealing with shorts
    //

    @Override
    public void putShort(int rowId, short value) {
        Platform.putShort(null, data + 2L * rowId, value);
    }

    @Override
    public void putShorts(int rowId, int count, short value) {
        long offset = data + 2L * rowId;
        for (int i = 0; i < count; ++i, offset += 2) {
            Platform.putShort(null, offset, value);
        }
    }

    @Override
    public void putShorts(int rowId, int count, short[] src, int srcIndex) {
        Platform.copyMemory(src, Platform.SHORT_ARRAY_OFFSET + srcIndex * 2L,
                null, data + 2L * rowId, count * 2L);
    }

    @Override
    public void putShorts(int rowId, int count, byte[] src, int srcIndex) {
        Platform.copyMemory(src, Platform.BYTE_ARRAY_OFFSET + srcIndex,
                null, data + rowId * 2L, count * 2L);
    }

    @Override
    public short getShort(int rowId) {
        if (dictionary == null) {
            return Platform.getShort(null, data + 2L * rowId);
        } else {
            return (short) dictionary.decodeToInt(dictionaryIds.getDictId(rowId));
        }
    }

    @Override
    public short[] getShorts(int rowId, int count) {
        assert(dictionary == null);
        short[] array = new short[count];
        Platform.copyMemory(null, data + rowId * 2L, array, Platform.SHORT_ARRAY_OFFSET, count * 2L);
        return array;
    }

    //
    // APIs dealing with ints
    //

    @Override
    public void putInt(int rowId, int value) {
        Platform.putInt(null, data + 4L * rowId, value);
    }

    @Override
    public void putInts(int rowId, int count, int value) {
        long offset = data + 4L * rowId;
        for (int i = 0; i < count; ++i, offset += 4) {
            Platform.putInt(null, offset, value);
        }
    }

    @Override
    public void putInts(int rowId, int count, int[] src, int srcIndex) {
        Platform.copyMemory(src, Platform.INT_ARRAY_OFFSET + srcIndex * 4L,
                null, data + 4L * rowId, count * 4L);
    }

    @Override
    public void putInts(int rowId, int count, byte[] src, int srcIndex) {
        Platform.copyMemory(src, Platform.BYTE_ARRAY_OFFSET + srcIndex,
                null, data + rowId * 4L, count * 4L);
    }

    @Override
    public void putIntsLittleEndian(int rowId, int count, byte[] src, int srcIndex) {
        if (!bigEndianPlatform) {
            Platform.copyMemory(src, srcIndex + Platform.BYTE_ARRAY_OFFSET,
                    null, data + 4L * rowId, count * 4L);
        } else {
            int srcOffset = srcIndex + Platform.BYTE_ARRAY_OFFSET;
            long offset = data + 4L * rowId;
            for (int i = 0; i < count; ++i, offset += 4, srcOffset += 4) {
                Platform.putInt(null, offset,
                        java.lang.Integer.reverseBytes(Platform.getInt(src, srcOffset)));
            }
        }
    }

    @Override
    public int getInt(int rowId) {
        if (dictionary == null) {
            return Platform.getInt(null, data + 4L * rowId);
        } else {
            return dictionary.decodeToInt(dictionaryIds.getDictId(rowId));
        }
    }

    @Override
    public int[] getInts(int rowId, int count) {
        assert(dictionary == null);
        int[] array = new int[count];
        Platform.copyMemory(null, data + rowId * 4L, array, Platform.INT_ARRAY_OFFSET, count * 4L);
        return array;
    }

    /**
     * Returns the dictionary Id for rowId.
     * This should only be called when the ColumnVector is dictionaryIds.
     * We have this separate method for dictionaryIds as per SPARK-16928.
     */
    public int getDictId(int rowId) {
        assert(dictionary == null)
                : "A ColumnVector dictionary should not have a dictionary for itself.";
        return Platform.getInt(null, data + 4L * rowId);
    }

    //
    // APIs dealing with Longs
    //

    @Override
    public void putLong(int rowId, long value) {
        Platform.putLong(null, data + 8L * rowId, value);
    }

    @Override
    public void putLongs(int rowId, int count, long value) {
        long offset = data + 8L * rowId;
        for (int i = 0; i < count; ++i, offset += 8) {
            Platform.putLong(null, offset, value);
        }
    }

    @Override
    public void putLongs(int rowId, int count, long[] src, int srcIndex) {
        Platform.copyMemory(src, Platform.LONG_ARRAY_OFFSET + srcIndex * 8L,
                null, data + 8L * rowId, count * 8L);
    }

    @Override
    public void putLongs(int rowId, int count, byte[] src, int srcIndex) {
        Platform.copyMemory(src, Platform.BYTE_ARRAY_OFFSET + srcIndex,
                null, data + rowId * 8L, count * 8L);
    }

    @Override
    public void putLongsLittleEndian(int rowId, int count, byte[] src, int srcIndex) {
        if (!bigEndianPlatform) {
            Platform.copyMemory(src, srcIndex + Platform.BYTE_ARRAY_OFFSET,
                    null, data + 8L * rowId, count * 8L);
        } else {
            int srcOffset = srcIndex + Platform.BYTE_ARRAY_OFFSET;
            long offset = data + 8L * rowId;
            for (int i = 0; i < count; ++i, offset += 8, srcOffset += 8) {
                Platform.putLong(null, offset,
                        java.lang.Long.reverseBytes(Platform.getLong(src, srcOffset)));
            }
        }
    }

    @Override
    public long getLong(int rowId) {
        if (dictionary == null) {
            return Platform.getLong(null, data + 8L * rowId);
        } else {
            return dictionary.decodeToLong(dictionaryIds.getDictId(rowId));
        }
    }

    @Override
    public long[] getLongs(int rowId, int count) {
        assert(dictionary == null);
        long[] array = new long[count];
        Platform.copyMemory(null, data + rowId * 8L, array, Platform.LONG_ARRAY_OFFSET, count * 8L);
        return array;
    }

    //
    // APIs dealing with floats
    //

    @Override
    public void putFloat(int rowId, float value) {
        Platform.putFloat(null, data + rowId * 4L, value);
    }

    @Override
    public void putFloats(int rowId, int count, float value) {
        long offset = data + 4L * rowId;
        for (int i = 0; i < count; ++i, offset += 4) {
            Platform.putFloat(null, offset, value);
        }
    }

    @Override
    public void putFloats(int rowId, int count, float[] src, int srcIndex) {
        Platform.copyMemory(src, Platform.FLOAT_ARRAY_OFFSET + srcIndex * 4L,
                null, data + 4L * rowId, count * 4L);
    }

    @Override
    public void putFloats(int rowId, int count, byte[] src, int srcIndex) {
        if (!bigEndianPlatform) {
            Platform.copyMemory(src, Platform.BYTE_ARRAY_OFFSET + srcIndex,
                    null, data + rowId * 4L, count * 4L);
        } else {
            ByteBuffer bb = ByteBuffer.wrap(src).order(ByteOrder.BIG_ENDIAN);
            long offset = data + 4L * rowId;
            for (int i = 0; i < count; ++i, offset += 4) {
                Platform.putFloat(null, offset, bb.getFloat(srcIndex + (4 * i)));
            }
        }
    }

    @Override
    public float getFloat(int rowId) {
        if (dictionary == null) {
            return Platform.getFloat(null, data + rowId * 4L);
        } else {
            return dictionary.decodeToFloat(dictionaryIds.getDictId(rowId));
        }
    }

    @Override
    public float[] getFloats(int rowId, int count) {
        assert(dictionary == null);
        float[] array = new float[count];
        Platform.copyMemory(null, data + rowId * 4L, array, Platform.FLOAT_ARRAY_OFFSET, count * 4L);
        return array;
    }


    //
    // APIs dealing with doubles
    //

    @Override
    public void putDouble(int rowId, double value) {
        Platform.putDouble(null, data + rowId * 8L, value);
    }

    @Override
    public void putDoubles(int rowId, int count, double value) {
        long offset = data + 8L * rowId;
        for (int i = 0; i < count; ++i, offset += 8) {
            Platform.putDouble(null, offset, value);
        }
    }

    @Override
    public void putDoubles(int rowId, int count, double[] src, int srcIndex) {
        Platform.copyMemory(src, Platform.DOUBLE_ARRAY_OFFSET + srcIndex * 8L,
                null, data + 8L * rowId, count * 8L);
    }

    @Override
    public void putDoubles(int rowId, int count, byte[] src, int srcIndex) {
        if (!bigEndianPlatform) {
            Platform.copyMemory(src, Platform.BYTE_ARRAY_OFFSET + srcIndex,
                    null, data + rowId * 8L, count * 8L);
        } else {
            ByteBuffer bb = ByteBuffer.wrap(src).order(ByteOrder.BIG_ENDIAN);
            long offset = data + 8L * rowId;
            for (int i = 0; i < count; ++i, offset += 8) {
                Platform.putDouble(null, offset, bb.getDouble(srcIndex + (8 * i)));
            }
        }
    }

    @Override
    public double getDouble(int rowId) {
        if (dictionary == null) {
            return Platform.getDouble(null, data + rowId * 8L);
        } else {
            return dictionary.decodeToDouble(dictionaryIds.getDictId(rowId));
        }
    }

    @Override
    public double[] getDoubles(int rowId, int count) {
        assert(dictionary == null);
        double[] array = new double[count];
        Platform.copyMemory(null, data + rowId * 8L, array, Platform.DOUBLE_ARRAY_OFFSET, count * 8L);
        return array;
    }

    //
    // APIs dealing with Arrays.
    //
    @Override
    public void putArray(int rowId, int offset, int length) {
        assert(offset >= 0 && offset + length <= childColumns[0].capacity);
        Platform.putInt(null, lengthData + 4L * rowId, length);
        Platform.putInt(null, offsetData + 4L * rowId, offset);
    }

    @Override
    public int getArrayLength(int rowId) {
        return Platform.getInt(null, lengthData + 4L * rowId);
    }

    @Override
    public int getArrayOffset(int rowId) {
        return Platform.getInt(null, offsetData + 4L * rowId);
    }

    // APIs dealing with ByteArrays
    @Override
    public int putByteArray(int rowId, byte[] value, int offset, int length) {
        int result = arrayData().appendBytes(length, value, offset);
        Platform.putInt(null, lengthData + 4L * rowId, length);
        Platform.putInt(null, offsetData + 4L * rowId, result);
        return result;
    }

    // Split out the slow path.
    @Override
    protected void reserveInternal(int newCapacity) {
        int oldCapacity = (nulls == 0L) ? 0 : capacity;
        if (isArray() || type instanceof MapType) {
            this.lengthData =
                    Platform.reallocateMemory(lengthData, oldCapacity * 4L, newCapacity * 4L);
            this.offsetData =
                    Platform.reallocateMemory(offsetData, oldCapacity * 4L, newCapacity * 4L);
        } else if (type instanceof ByteType || type instanceof BooleanType) {
            this.data = Platform.reallocateMemory(data, oldCapacity, newCapacity);
        } else if (type instanceof ShortType) {
            this.data = Platform.reallocateMemory(data, oldCapacity * 2L, newCapacity * 2L);
        } else if (type instanceof IntegerType || type instanceof FloatType ||
                type instanceof DateType || DecimalType.is32BitDecimalType(type)) {
            this.data = Platform.reallocateMemory(data, oldCapacity * 4L, newCapacity * 4L);
        } else if (type instanceof LongType || type instanceof DoubleType ||
                DecimalType.is64BitDecimalType(type) || type instanceof TimestampType) {
            this.data = Platform.reallocateMemory(data, oldCapacity * 8L, newCapacity * 8L);
        } else if (childColumns != null) {
            // Nothing to store.
        } else {
            throw new RuntimeException("Unhandled " + type);
        }
        this.nulls = Platform.reallocateMemory(nulls, oldCapacity, newCapacity);
        Platform.setMemory(nulls + oldCapacity, (byte)0, newCapacity - oldCapacity);
        capacity = newCapacity;
    }

    @Override
    protected OffHeapColumnVector reserveNewColumn(int capacity, DataType type) {
        return new OffHeapColumnVector(capacity, type);
    }
}
