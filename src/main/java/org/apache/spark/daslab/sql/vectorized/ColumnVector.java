package org.apache.spark.daslab.sql.vectorized;



import org.apache.spark.daslab.sql.types.DataType;
import org.apache.spark.daslab.sql.types.Decimal;


//todo
import org.apache.spark.annotation.InterfaceStability;
import org.apache.spark.unsafe.types.CalendarInterval;
import org.apache.spark.unsafe.types.UTF8String;

/**
 * An interface representing in-memory columnar data in Spark. This interface defines the main APIs
 * to access the data, as well as their batched versions. The batched versions are considered to be
 * faster and preferable whenever possible.
 *
 * Most of the APIs take the rowId as a parameter. This is the batch local 0-based row id for values
 * in this ColumnVector.
 *
 * Spark only calls specific `get` method according to the data type of this {@link ColumnVector},
 * e.g. if it's int type, Spark is guaranteed to only call {@link #getInt(int)} or
 * {@link #getInts(int, int)}.
 *
 * ColumnVector supports all the data types including nested types. To handle nested types,
 * ColumnVector can have children and is a tree structure. Please refer to {@link #getStruct(int)},
 * {@link #getArray(int)} and {@link #getMap(int)} for the details about how to implement nested
 * types.
 *
 * ColumnVector is expected to be reused during the entire data loading process, to avoid allocating
 * memory again and again.
 *
 * ColumnVector is meant to maximize CPU efficiency but not to minimize storage footprint.
 * Implementations should prefer computing efficiency over storage efficiency when design the
 * format. Since it is expected to reuse the ColumnVector instance while loading data, the storage
 * footprint is negligible.
 */
@InterfaceStability.Evolving
public abstract class ColumnVector implements AutoCloseable {

    /**
     * Returns the data type of this column vector.
     */
    public final DataType dataType() { return type; }

    /**
     * Cleans up memory for this column vector. The column vector is not usable after this.
     *
     * This overwrites `AutoCloseable.close` to remove the `throws` clause, as column vector is
     * in-memory and we don't expect any exception to happen during closing.
     */
    @Override
    public abstract void close();

    /**
     * Returns true if this column vector contains any null values.
     */
    public abstract boolean hasNull();

    /**
     * Returns the number of nulls in this column vector.
     */
    public abstract int numNulls();

    /**
     * Returns whether the value at rowId is NULL.
     */
    public abstract boolean isNullAt(int rowId);

    /**
     * Returns the boolean type value for rowId. The return value is undefined and can be anything,
     * if the slot for rowId is null.
     */
    public abstract boolean getBoolean(int rowId);

    /**
     * Gets boolean type values from [rowId, rowId + count). The return values for the null slots
     * are undefined and can be anything.
     */
    public boolean[] getBooleans(int rowId, int count) {
        boolean[] res = new boolean[count];
        for (int i = 0; i < count; i++) {
            res[i] = getBoolean(rowId + i);
        }
        return res;
    }

    /**
     * Returns the byte type value for rowId. The return value is undefined and can be anything,
     * if the slot for rowId is null.
     */
    public abstract byte getByte(int rowId);

    /**
     * Gets byte type values from [rowId, rowId + count). The return values for the null slots
     * are undefined and can be anything.
     */
    public byte[] getBytes(int rowId, int count) {
        byte[] res = new byte[count];
        for (int i = 0; i < count; i++) {
            res[i] = getByte(rowId + i);
        }
        return res;
    }

    /**
     * Returns the short type value for rowId. The return value is undefined and can be anything,
     * if the slot for rowId is null.
     */
    public abstract short getShort(int rowId);

    /**
     * Gets short type values from [rowId, rowId + count). The return values for the null slots
     * are undefined and can be anything.
     */
    public short[] getShorts(int rowId, int count) {
        short[] res = new short[count];
        for (int i = 0; i < count; i++) {
            res[i] = getShort(rowId + i);
        }
        return res;
    }

    /**
     * Returns the int type value for rowId. The return value is undefined and can be anything,
     * if the slot for rowId is null.
     */
    public abstract int getInt(int rowId);

    /**
     * Gets int type values from [rowId, rowId + count). The return values for the null slots
     * are undefined and can be anything.
     */
    public int[] getInts(int rowId, int count) {
        int[] res = new int[count];
        for (int i = 0; i < count; i++) {
            res[i] = getInt(rowId + i);
        }
        return res;
    }

    /**
     * Returns the long type value for rowId. The return value is undefined and can be anything,
     * if the slot for rowId is null.
     */
    public abstract long getLong(int rowId);

    /**
     * Gets long type values from [rowId, rowId + count). The return values for the null slots
     * are undefined and can be anything.
     */
    public long[] getLongs(int rowId, int count) {
        long[] res = new long[count];
        for (int i = 0; i < count; i++) {
            res[i] = getLong(rowId + i);
        }
        return res;
    }

    /**
     * Returns the float type value for rowId. The return value is undefined and can be anything,
     * if the slot for rowId is null.
     */
    public abstract float getFloat(int rowId);

    /**
     * Gets float type values from [rowId, rowId + count). The return values for the null slots
     * are undefined and can be anything.
     */
    public float[] getFloats(int rowId, int count) {
        float[] res = new float[count];
        for (int i = 0; i < count; i++) {
            res[i] = getFloat(rowId + i);
        }
        return res;
    }

    /**
     * Returns the double type value for rowId. The return value is undefined and can be anything,
     * if the slot for rowId is null.
     */
    public abstract double getDouble(int rowId);

    /**
     * Gets double type values from [rowId, rowId + count). The return values for the null slots
     * are undefined and can be anything.
     */
    public double[] getDoubles(int rowId, int count) {
        double[] res = new double[count];
        for (int i = 0; i < count; i++) {
            res[i] = getDouble(rowId + i);
        }
        return res;
    }

    /**
     * Returns the struct type value for rowId. If the slot for rowId is null, it should return null.
     *
     * To support struct type, implementations must implement {@link #getChild(int)} and make this
     * vector a tree structure. The number of child vectors must be same as the number of fields of
     * the struct type, and each child vector is responsible to store the data for its corresponding
     * struct field.
     */
    public final ColumnarRow getStruct(int rowId) {
        if (isNullAt(rowId)) return null;
        return new ColumnarRow(this, rowId);
    }

    /**
     * Returns the array type value for rowId. If the slot for rowId is null, it should return null.
     *
     * To support array type, implementations must construct an {@link ColumnarArray} and return it in
     * this method. {@link ColumnarArray} requires a {@link ColumnVector} that stores the data of all
     * the elements of all the arrays in this vector, and an offset and length which points to a range
     * in that {@link ColumnVector}, and the range represents the array for rowId. Implementations
     * are free to decide where to put the data vector and offsets and lengths. For example, we can
     * use the first child vector as the data vector, and store offsets and lengths in 2 int arrays in
     * this vector.
     */
    public abstract ColumnarArray getArray(int rowId);

    /**
     * Returns the map type value for rowId. If the slot for rowId is null, it should return null.
     *
     * In Spark, map type value is basically a key data array and a value data array. A key from the
     * key array with a index and a value from the value array with the same index contribute to
     * an entry of this map type value.
     *
     * To support map type, implementations must construct a {@link ColumnarMap} and return it in
     * this method. {@link ColumnarMap} requires a {@link ColumnVector} that stores the data of all
     * the keys of all the maps in this vector, and another {@link ColumnVector} that stores the data
     * of all the values of all the maps in this vector, and a pair of offset and length which
     * specify the range of the key/value array that belongs to the map type value at rowId.
     */
    public abstract ColumnarMap getMap(int ordinal);

    /**
     * Returns the decimal type value for rowId. If the slot for rowId is null, it should return null.
     */
    public abstract Decimal getDecimal(int rowId, int precision, int scale);

    /**
     * Returns the string type value for rowId. If the slot for rowId is null, it should return null.
     * Note that the returned UTF8String may point to the data of this column vector, please copy it
     * if you want to keep it after this column vector is freed.
     */
    public abstract UTF8String getUTF8String(int rowId);

    /**
     * Returns the binary type value for rowId. If the slot for rowId is null, it should return null.
     */
    public abstract byte[] getBinary(int rowId);

    /**
     * Returns the calendar interval type value for rowId. If the slot for rowId is null, it should
     * return null.
     *
     * In Spark, calendar interval type value is basically an integer value representing the number of
     * months in this interval, and a long value representing the number of microseconds in this
     * interval. An interval type vector is the same as a struct type vector with 2 fields: `months`
     * and `microseconds`.
     *
     * To support interval type, implementations must implement {@link #getChild(int)} and define 2
     * child vectors: the first child vector is an int type vector, containing all the month values of
     * all the interval values in this vector. The second child vector is a long type vector,
     * containing all the microsecond values of all the interval values in this vector.
     */
    public final CalendarInterval getInterval(int rowId) {
        if (isNullAt(rowId)) return null;
        final int months = getChild(0).getInt(rowId);
        final long microseconds = getChild(1).getLong(rowId);
        return new CalendarInterval(months, microseconds);
    }

    /**
     * @return child [[ColumnVector]] at the given ordinal.
     */
    protected abstract ColumnVector getChild(int ordinal);

    /**
     * Data type for this column.
     */
    protected DataType type;

    /**
     * Sets up the data type of this column vector.
     */
    protected ColumnVector(DataType type) {
        this.type = type;
    }
}
