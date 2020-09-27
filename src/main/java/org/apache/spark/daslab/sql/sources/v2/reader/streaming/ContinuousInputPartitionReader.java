package org.apache.spark.daslab.sql.sources.v2.reader.streaming;


 import org.apache.spark.daslab.sql.sources.v2.reader.InputPartitionReader;

 //todo
 import org.apache.spark.annotation.InterfaceStability;

/**
 * A variation on {@link InputPartitionReader} for use with streaming in continuous processing mode.
 */
@InterfaceStability.Evolving
public interface ContinuousInputPartitionReader<T> extends InputPartitionReader<T> {
    /**
     * Get the offset of the current record, or the start offset if no records have been read.
     *
     * The execution engine will call this method along with get() to keep track of the current
     * offset. When an epoch ends, the offset of the previous record in each partition will be saved
     * as a restart checkpoint.
     */
    PartitionOffset getOffset();
}
