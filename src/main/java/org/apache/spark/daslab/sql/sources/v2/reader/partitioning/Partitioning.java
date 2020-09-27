package org.apache.spark.daslab.sql.sources.v2.reader.partitioning;


//todo
import org.apache.spark.annotation.InterfaceStability;


import org.apache.spark.daslab.sql.sources.v2.reader.InputPartition;
import org.apache.spark.daslab.sql.sources.v2.reader.SupportsReportPartitioning;

/**
 * An interface to represent the output data partitioning for a data source, which is returned by
 * {@link SupportsReportPartitioning#outputPartitioning()}. Note that this should work like a
 * snapshot. Once created, it should be deterministic and always report the same number of
 * partitions and the same "satisfy" result for a certain distribution.
 */
@InterfaceStability.Evolving
public interface Partitioning {

    /**
     * Returns the number of partitions(i.e., {@link InputPartition}s) the data source outputs.
     */
    int numPartitions();

    /**
     * Returns true if this partitioning can satisfy the given distribution, which means Spark does
     * not need to shuffle the output data of this data source for some certain operations.
     *
     * Note that, Spark may add new concrete implementations of {@link Distribution} in new releases.
     * This method should be aware of it and always return false for unrecognized distributions. It's
     * recommended to check every Spark new release and support new distributions if possible, to
     * avoid shuffle at Spark side for more cases.
     */
    boolean satisfy(Distribution distribution);
}
