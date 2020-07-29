package org.apache.spark.daslab.sql.execution.datasources.parquet;


import org.apache.spark.daslab.sql.execution.vectorized.Dictionary;

public final class ParquetDictionary implements Dictionary {
    private org.apache.parquet.column.Dictionary dictionary;

    public ParquetDictionary(org.apache.parquet.column.Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public int decodeToInt(int id) {
        return dictionary.decodeToInt(id);
    }

    @Override
    public long decodeToLong(int id) {
        return dictionary.decodeToLong(id);
    }

    @Override
    public float decodeToFloat(int id) {
        return dictionary.decodeToFloat(id);
    }

    @Override
    public double decodeToDouble(int id) {
        return dictionary.decodeToDouble(id);
    }

    @Override
    public byte[] decodeToBinary(int id) {
        return dictionary.decodeToBinary(id).getBytes();
    }
}
