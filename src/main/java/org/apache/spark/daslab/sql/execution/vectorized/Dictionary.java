package org.apache.spark.daslab.sql.execution.vectorized;



/**
 * The interface for dictionary in ColumnVector to decode dictionary encoded values.
 */
public interface Dictionary {

    int decodeToInt(int id);

    long decodeToLong(int id);

    float decodeToFloat(int id);

    double decodeToDouble(int id);

    byte[] decodeToBinary(int id);
}
