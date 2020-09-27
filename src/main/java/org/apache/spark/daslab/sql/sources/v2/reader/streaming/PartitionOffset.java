package org.apache.spark.daslab.sql.sources.v2.reader.streaming;



import java.io.Serializable;



//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * Used for per-partition offsets in continuous processing. ContinuousReader implementations will
 * provide a method to merge these into a global Offset.
 *
 * These offsets must be serializable.
 */
@InterfaceStability.Evolving
public interface PartitionOffset extends Serializable {
}
