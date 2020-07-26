package org.apache.spark.daslab.sql.vectorized;



import org.apache.spark.daslab.sql.engine.util.MapData;

/**
 * Map abstraction in {@link ColumnVector}.
 */
public final class ColumnarMap extends MapData {
    private final ColumnarArray keys;
    private final ColumnarArray values;
    private final int length;

    public ColumnarMap(ColumnVector keys, ColumnVector values, int offset, int length) {
        this.length = length;
        this.keys = new ColumnarArray(keys, offset, length);
        this.values = new ColumnarArray(values, offset, length);
    }

    @Override
    public int numElements() { return length; }

    @Override
    public ColumnarArray keyArray() {
        return keys;
    }

    @Override
    public ColumnarArray valueArray() {
        return values;
    }

    @Override
    public ColumnarMap copy() {
        throw new UnsupportedOperationException();
    }
}
