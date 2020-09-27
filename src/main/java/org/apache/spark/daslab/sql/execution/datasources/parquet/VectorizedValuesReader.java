package org.apache.spark.daslab.sql.execution.datasources.parquet;


import org.apache.spark.daslab.sql.execution.vectorized.WritableColumnVector;

import org.apache.parquet.io.api.Binary;

/**
 * Interface for value decoding that supports vectorized (aka batched) decoding.
 * TODO: merge this into parquet-mr.
 */
public interface VectorizedValuesReader {
    boolean readBoolean();
    byte readByte();
    int readInteger();
    long readLong();
    float readFloat();
    double readDouble();
    Binary readBinary(int len);

    /*
     * Reads `total` values into `c` start at `c[rowId]`
     */
    void readBooleans(int total, WritableColumnVector c, int rowId);
    void readBytes(int total, WritableColumnVector c, int rowId);
    void readIntegers(int total, WritableColumnVector c, int rowId);
    void readLongs(int total, WritableColumnVector c, int rowId);
    void readFloats(int total, WritableColumnVector c, int rowId);
    void readDoubles(int total, WritableColumnVector c, int rowId);
    void readBinary(int total, WritableColumnVector c, int rowId);
}
