package org.apache.spark.daslab.sql.sources.v2.reader;

//todo
import org.apache.spark.annotation.InterfaceStability;


import org.apache.spark.daslab.sql.types.StructType;

/**
 * A mix-in interface for {@link DataSourceReader}. Data source readers can implement this
 * interface to push down required columns to the data source and only read these columns during
 * scan to reduce the size of the data to be read.
 */
@InterfaceStability.Evolving
public interface SupportsPushDownRequiredColumns extends DataSourceReader {

    /**
     * Applies column pruning w.r.t. the given requiredSchema.
     *
     * Implementation should try its best to prune the unnecessary columns or nested fields, but it's
     * also OK to do the pruning partially, e.g., a data source may not be able to prune nested
     * fields, and only prune top-level columns.
     *
     * Note that, data source readers should update {@link DataSourceReader#readSchema()} after
     * applying column pruning.
     */
    void pruneColumns(StructType requiredSchema);
}
