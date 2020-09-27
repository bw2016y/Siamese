package org.apache.spark.daslab.sql.execution.columnar;


import org.apache.spark.daslab.sql.execution.vectorized.Dictionary;

public final class ColumnDictionary implements Dictionary {
    private int[] intDictionary;
    private long[] longDictionary;

    public ColumnDictionary(int[] dictionary) {
        this.intDictionary = dictionary;
    }

    public ColumnDictionary(long[] dictionary) {
        this.longDictionary = dictionary;
    }

    @Override
    public int decodeToInt(int id) {
        return intDictionary[id];
    }

    @Override
    public long decodeToLong(int id) {
        return longDictionary[id];
    }

    @Override
    public float decodeToFloat(int id) {
        throw new UnsupportedOperationException("Dictionary encoding does not support float");
    }

    @Override
    public double decodeToDouble(int id) {
        throw new UnsupportedOperationException("Dictionary encoding does not support double");
    }

    @Override
    public byte[] decodeToBinary(int id) {
        throw new UnsupportedOperationException("Dictionary encoding does not support String");
    }
}
