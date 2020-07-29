package org.apache.spark.daslab.sql.sources.v2.reader;

import org.apache.spark.daslab.sql.sources.v2.reader.partitioning.Partitioning;

//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * A mix in interface for {@link DataSourceReader}. Data source readers can implement this
 * interface to report data partitioning and try to avoid shuffle at Spark side.
 *
 * Note that, when the reader creates exactly one {@link InputPartition}, Spark may avoid
 * adding a shuffle even if the reader does not implement this interface.
 */
@InterfaceStability.Evolving
public interface SupportsReportPartitioning extends DataSourceReader {

    /**
     * Returns the output data partitioning that this reader guarantees.
     */
    Partitioning outputPartitioning();
}
