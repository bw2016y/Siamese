package org.apache.spark.daslab.sql.sources.v2.reader;


import java.util.OptionalLong;


//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * An interface to represent statistics for a data source, which is returned by
 * {@link SupportsReportStatistics#estimateStatistics()}.
 */
@InterfaceStability.Evolving
public interface Statistics {
    OptionalLong sizeInBytes();
    OptionalLong numRows();
}
