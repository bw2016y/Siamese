package org.apache.spark.daslab.sql.execution.vectorized;



import java.math.BigDecimal;

import org.apache.spark.daslab.sql.engine.InternalRow;
import org.apache.spark.daslab.sql.engine.expressions.GenericInternalRow;
import org.apache.spark.daslab.sql.types.*;
import org.apache.spark.daslab.sql.vectorized.ColumnarArray;
import org.apache.spark.daslab.sql.vectorized.ColumnarBatch;
import org.apache.spark.daslab.sql.vectorized.ColumnarMap;
import org.apache.spark.daslab.sql.vectorized.ColumnarRow;
import org.apache.spark.daslab.sql.vectorized.ColumnVector;
import org.apache.spark.unsafe.types.CalendarInterval;
import org.apache.spark.unsafe.types.UTF8String;

/**
 * A mutable version of {@link ColumnarRow}, which is used in the vectorized hash map for hash
 * aggregate, and {@link ColumnarBatch} to save object creation.
 *
 * Note that this class intentionally has a lot of duplicated code with {@link ColumnarRow}, to
 * avoid java polymorphism overhead by keeping {@link ColumnarRow} and this class final classes.
 */
public final class MutableColumnarRow extends InternalRow {
    public int rowId;
    private final ColumnVector[] columns;
    private final WritableColumnVector[] writableColumns;

    public MutableColumnarRow(ColumnVector[] columns) {
        this.columns = columns;
        this.writableColumns = null;
    }

    public MutableColumnarRow(WritableColumnVector[] writableColumns) {
        this.columns = writableColumns;
        this.writableColumns = writableColumns;
    }

    @Override
    public int numFields() { return columns.length; }

    @Override
    public InternalRow copy() {
        GenericInternalRow row = new GenericInternalRow(columns.length);
        for (int i = 0; i < numFields(); i++) {
            if (isNullAt(i)) {
                row.setNullAt(i);
            } else {
                DataType dt = columns[i].dataType();
                if (dt instanceof BooleanType) {
                    row.setBoolean(i, getBoolean(i));
                } else if (dt instanceof ByteType) {
                    row.setByte(i, getByte(i));
                } else if (dt instanceof ShortType) {
                    row.setShort(i, getShort(i));
                } else if (dt instanceof IntegerType) {
                    row.setInt(i, getInt(i));
                } else if (dt instanceof LongType) {
                    row.setLong(i, getLong(i));
                } else if (dt instanceof FloatType) {
                    row.setFloat(i, getFloat(i));
                } else if (dt instanceof DoubleType) {
                    row.setDouble(i, getDouble(i));
                } else if (dt instanceof StringType) {
                    row.update(i, getUTF8String(i).copy());
                } else if (dt instanceof BinaryType) {
                    row.update(i, getBinary(i));
                } else if (dt instanceof DecimalType) {
                    DecimalType t = (DecimalType)dt;
                    row.setDecimal(i, getDecimal(i, t.precision(), t.scale()), t.precision());
                } else if (dt instanceof DateType) {
                    row.setInt(i, getInt(i));
                } else if (dt instanceof TimestampType) {
                    row.setLong(i, getLong(i));
                } else {
                    throw new RuntimeException("Not implemented. " + dt);
                }
            }
        }
        return row;
    }

    @Override
    public boolean anyNull() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isNullAt(int ordinal) { return columns[ordinal].isNullAt(rowId); }

    @Override
    public boolean getBoolean(int ordinal) { return columns[ordinal].getBoolean(rowId); }

    @Override
    public byte getByte(int ordinal) { return columns[ordinal].getByte(rowId); }

    @Override
    public short getShort(int ordinal) { return columns[ordinal].getShort(rowId); }

    @Override
    public int getInt(int ordinal) { return columns[ordinal].getInt(rowId); }

    @Override
    public long getLong(int ordinal) { return columns[ordinal].getLong(rowId); }

    @Override
    public float getFloat(int ordinal) { return columns[ordinal].getFloat(rowId); }

    @Override
    public double getDouble(int ordinal) { return columns[ordinal].getDouble(rowId); }

    @Override
    public Decimal getDecimal(int ordinal, int precision, int scale) {
        return columns[ordinal].getDecimal(rowId, precision, scale);
    }

    @Override
    public UTF8String getUTF8String(int ordinal) {
        return columns[ordinal].getUTF8String(rowId);
    }

    @Override
    public byte[] getBinary(int ordinal) {
        return columns[ordinal].getBinary(rowId);
    }

    @Override
    public CalendarInterval getInterval(int ordinal) {
        return columns[ordinal].getInterval(rowId);
    }

    @Override
    public ColumnarRow getStruct(int ordinal, int numFields) {
        return columns[ordinal].getStruct(rowId);
    }

    @Override
    public ColumnarArray getArray(int ordinal) {
        return columns[ordinal].getArray(rowId);
    }

    @Override
    public ColumnarMap getMap(int ordinal) {
        return columns[ordinal].getMap(rowId);
    }

    @Override
    public Object get(int ordinal, DataType dataType) {
        if (dataType instanceof BooleanType) {
            return getBoolean(ordinal);
        } else if (dataType instanceof ByteType) {
            return getByte(ordinal);
        } else if (dataType instanceof ShortType) {
            return getShort(ordinal);
        } else if (dataType instanceof IntegerType) {
            return getInt(ordinal);
        } else if (dataType instanceof LongType) {
            return getLong(ordinal);
        } else if (dataType instanceof FloatType) {
            return getFloat(ordinal);
        } else if (dataType instanceof DoubleType) {
            return getDouble(ordinal);
        } else if (dataType instanceof StringType) {
            return getUTF8String(ordinal);
        } else if (dataType instanceof BinaryType) {
            return getBinary(ordinal);
        } else if (dataType instanceof DecimalType) {
            DecimalType t = (DecimalType) dataType;
            return getDecimal(ordinal, t.precision(), t.scale());
        } else if (dataType instanceof DateType) {
            return getInt(ordinal);
        } else if (dataType instanceof TimestampType) {
            return getLong(ordinal);
        } else if (dataType instanceof ArrayType) {
            return getArray(ordinal);
        } else if (dataType instanceof StructType) {
            return getStruct(ordinal, ((StructType)dataType).fields().length);
        } else if (dataType instanceof MapType) {
            return getMap(ordinal);
        } else {
            throw new UnsupportedOperationException("Datatype not supported " + dataType);
        }
    }

    @Override
    public void update(int ordinal, Object value) {
        if (value == null) {
            setNullAt(ordinal);
        } else {
            DataType dt = columns[ordinal].dataType();
            if (dt instanceof BooleanType) {
                setBoolean(ordinal, (boolean) value);
            } else if (dt instanceof IntegerType) {
                setInt(ordinal, (int) value);
            } else if (dt instanceof ShortType) {
                setShort(ordinal, (short) value);
            } else if (dt instanceof LongType) {
                setLong(ordinal, (long) value);
            } else if (dt instanceof FloatType) {
                setFloat(ordinal, (float) value);
            } else if (dt instanceof DoubleType) {
                setDouble(ordinal, (double) value);
            } else if (dt instanceof DecimalType) {
                DecimalType t = (DecimalType) dt;
                Decimal d = Decimal.apply((BigDecimal) value, t.precision(), t.scale());
                setDecimal(ordinal, d, t.precision());
            } else {
                throw new UnsupportedOperationException("Datatype not supported " + dt);
            }
        }
    }

    @Override
    public void setNullAt(int ordinal) {
        writableColumns[ordinal].putNull(rowId);
    }

    @Override
    public void setBoolean(int ordinal, boolean value) {
        writableColumns[ordinal].putNotNull(rowId);
        writableColumns[ordinal].putBoolean(rowId, value);
    }

    @Override
    public void setByte(int ordinal, byte value) {
        writableColumns[ordinal].putNotNull(rowId);
        writableColumns[ordinal].putByte(rowId, value);
    }

    @Override
    public void setShort(int ordinal, short value) {
        writableColumns[ordinal].putNotNull(rowId);
        writableColumns[ordinal].putShort(rowId, value);
    }

    @Override
    public void setInt(int ordinal, int value) {
        writableColumns[ordinal].putNotNull(rowId);
        writableColumns[ordinal].putInt(rowId, value);
    }

    @Override
    public void setLong(int ordinal, long value) {
        writableColumns[ordinal].putNotNull(rowId);
        writableColumns[ordinal].putLong(rowId, value);
    }

    @Override
    public void setFloat(int ordinal, float value) {
        writableColumns[ordinal].putNotNull(rowId);
        writableColumns[ordinal].putFloat(rowId, value);
    }

    @Override
    public void setDouble(int ordinal, double value) {
        writableColumns[ordinal].putNotNull(rowId);
        writableColumns[ordinal].putDouble(rowId, value);
    }

    @Override
    public void setDecimal(int ordinal, Decimal value, int precision) {
        writableColumns[ordinal].putNotNull(rowId);
        writableColumns[ordinal].putDecimal(rowId, value, precision);
    }
}
