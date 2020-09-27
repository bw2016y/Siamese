package org.apache.spark.daslab.sql.sources.v2.reader.partitioning;

import org.apache.spark.daslab.sql.sources.v2.reader.InputPartitionReader;

//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * A concrete implementation of {@link Distribution}. Represents a distribution where records that
 * share the same values for the {@link #clusteredColumns} will be produced by the same
 * {@link InputPartitionReader}.
 */
@InterfaceStability.Evolving
public class ClusteredDistribution implements Distribution {

    /**
     * The names of the clustered columns. Note that they are order insensitive.
     */
    public final String[] clusteredColumns;

    public ClusteredDistribution(String[] clusteredColumns) {
        this.clusteredColumns = clusteredColumns;
    }
}
