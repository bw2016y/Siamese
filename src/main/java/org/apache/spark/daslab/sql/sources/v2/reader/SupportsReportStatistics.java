package org.apache.spark.daslab.sql.sources.v2.reader;



//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * A mix in interface for {@link DataSourceReader}. Data source readers can implement this
 * interface to report statistics to Spark.
 *
 * As of Spark 2.4, statistics are reported to the optimizer before any operator is pushed to the
 * DataSourceReader. Implementations that return more accurate statistics based on pushed operators
 * will not improve query performance until the planner can push operators before getting stats.
 */
@InterfaceStability.Evolving
public interface SupportsReportStatistics extends DataSourceReader {

    /**
     * Returns the estimated statistics of this data source.
     */
    Statistics estimateStatistics();
}
