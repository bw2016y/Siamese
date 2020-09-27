package org.apache.spark.daslab.sql.sources.v2.reader;


//todo
import org.apache.spark.annotation.InterfaceStability;


import org.apache.spark.daslab.sql.sources.v2.reader.streaming.PartitionOffset;

/**
 * A mix-in interface for {@link InputPartition}. Continuous input partitions can
 * implement this interface to provide creating {@link InputPartitionReader} with particular offset.
 */
@InterfaceStability.Evolving
public interface ContinuousInputPartition<T> extends InputPartition<T> {
    /**
     * Create an input partition reader with particular offset as its startOffset.
     *
     * @param offset offset want to set as the input partition reader's startOffset.
     */
    InputPartitionReader<T> createContinuousReader(PartitionOffset offset);
}
