package org.apache.spark.daslab.sql.sources.v2.reader.partitioning;

import org.apache.spark.daslab.sql.sources.v2.reader.InputPartitionReader;

//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * An interface to represent data distribution requirement, which specifies how the records should
 * be distributed among the data partitions (one {@link InputPartitionReader} outputs data for one
 * partition).
 * Note that this interface has nothing to do with the data ordering inside one
 * partition(the output records of a single {@link InputPartitionReader}).
 *
 * The instance of this interface is created and provided by Spark, then consumed by
 * {@link Partitioning#satisfy(Distribution)}. This means data source developers don't need to
 * implement this interface, but need to catch as more concrete implementations of this interface
 * as possible in {@link Partitioning#satisfy(Distribution)}.
 *
 * Concrete implementations until now:
 * <ul>
 *   <li>{@link ClusteredDistribution}</li>
 * </ul>
 */
@InterfaceStability.Evolving
public interface Distribution {}
