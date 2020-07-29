package org.apache.spark.daslab.sql.sources.v2.reader;

//todo
import org.apache.spark.annotation.InterfaceStability;

import org.apache.spark.daslab.sql.sources.Filter;

/**
 * A mix-in interface for {@link DataSourceReader}. Data source readers can implement this
 * interface to push down filters to the data source and reduce the size of the data to be read.
 */
@InterfaceStability.Evolving
public interface SupportsPushDownFilters extends DataSourceReader {

    /**
     * Pushes down filters, and returns filters that need to be evaluated after scanning.
     */
    Filter[] pushFilters(Filter[] filters);

    /**
     * Returns the filters that are pushed to the data source via {@link #pushFilters(Filter[])}.
     *
     * There are 3 kinds of filters:
     *  1. pushable filters which don't need to be evaluated again after scanning.
     *  2. pushable filters which still need to be evaluated after scanning, e.g. parquet
     *     row group filter.
     *  3. non-pushable filters.
     * Both case 1 and 2 should be considered as pushed filters and should be returned by this method.
     *
     * It's possible that there is no filters in the query and {@link #pushFilters(Filter[])}
     * is never called, empty array should be returned for this case.
     */
    Filter[] pushedFilters();
}
