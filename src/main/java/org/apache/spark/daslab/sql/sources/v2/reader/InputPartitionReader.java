package org.apache.spark.daslab.sql.sources.v2.reader;


import java.io.Closeable;
import java.io.IOException;
//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * An input partition reader returned by {@link InputPartition#createPartitionReader()} and is
 * responsible for outputting data for a RDD partition.
 *
 * Note that, Currently the type `T` can only be {@link org.apache.spark.daslab.sql.engine.InternalRow}
 * for normal data source readers, {@link org.apache.spark.daslab.sql.vectorized.ColumnarBatch} for data
 * source readers that mix in {@link SupportsScanColumnarBatch}.
 */
@InterfaceStability.Evolving
public interface InputPartitionReader<T> extends Closeable {

    /**
     * Proceed to next record, returns false if there is no more records.
     *
     * If this method fails (by throwing an exception), the corresponding Spark task would fail and
     * get retried until hitting the maximum retry times.
     *
     * @throws IOException if failure happens during disk/network IO like reading files.
     */
    boolean next() throws IOException;

    /**
     * Return the current record. This method should return same value until `next` is called.
     *
     * If this method fails (by throwing an exception), the corresponding Spark task would fail and
     * get retried until hitting the maximum retry times.
     */
    T get();
}

