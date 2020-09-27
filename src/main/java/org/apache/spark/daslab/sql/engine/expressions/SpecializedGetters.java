package org.apache.spark.daslab.sql.engine.expressions;

import org.apache.spark.daslab.sql.engine.InternalRow;
import org.apache.spark.daslab.sql.engine.util.ArrayData;
import org.apache.spark.daslab.sql.types.DataType;
import org.apache.spark.daslab.sql.types.Decimal;
import org.apache.spark.daslab.sql.engine.util.MapData;
import org.apache.spark.unsafe.types.CalendarInterval;
import org.apache.spark.unsafe.types.UTF8String;

public interface SpecializedGetters {

    boolean isNullAt(int ordinal);

    boolean getBoolean(int ordinal);

    byte getByte(int ordinal);

    short getShort(int ordinal);

    int getInt(int ordinal);

    long getLong(int ordinal);

    float getFloat(int ordinal);

    double getDouble(int ordinal);

    Decimal getDecimal(int ordinal, int precision, int scale);

    UTF8String getUTF8String(int ordinal);

    byte[] getBinary(int ordinal);

    CalendarInterval getInterval(int ordinal);

    InternalRow getStruct(int ordinal, int numFields);

    ArrayData getArray(int ordinal);

    MapData getMap(int ordinal);

    Object get(int ordinal, DataType dataType);
}
