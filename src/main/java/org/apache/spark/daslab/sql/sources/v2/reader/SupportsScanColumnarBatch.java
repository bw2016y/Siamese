package org.apache.spark.daslab.sql.sources.v2.reader;



import java.util.List;

//todo
import org.apache.spark.annotation.InterfaceStability;


import org.apache.spark.daslab.sql.engine.InternalRow;
import org.apache.spark.daslab.sql.vectorized.ColumnarBatch;

/**
 * A mix-in interface for {@link DataSourceReader}. Data source readers can implement this
 * interface to output {@link ColumnarBatch} and make the scan faster.
 */
@InterfaceStability.Evolving
public interface SupportsScanColumnarBatch extends DataSourceReader {
    @Override
    default List<InputPartition<InternalRow>> planInputPartitions() {
        throw new IllegalStateException(
                "planInputPartitions not supported by default within SupportsScanColumnarBatch.");
    }

    /**
     * Similar to {@link DataSourceReader#planInputPartitions()}, but returns columnar data
     * in batches.
     */
    List<InputPartition<ColumnarBatch>> planBatchInputPartitions();

    /**
     * Returns true if the concrete data source reader can read data in batch according to the scan
     * properties like required columns, pushes filters, etc. It's possible that the implementation
     * can only support some certain columns with certain types. Users can overwrite this method and
     * {@link #planInputPartitions()} to fallback to normal read path under some conditions.
     */
    default boolean enableBatchRead() {
        return true;
    }
}
