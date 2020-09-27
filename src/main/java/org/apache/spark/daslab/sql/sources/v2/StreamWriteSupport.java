package org.apache.spark.daslab.sql.sources.v2;



import org.apache.spark.annotation.InterfaceStability;
import org.apache.spark.daslab.sql.execution.streaming.BaseStreamingSink;
import org.apache.spark.daslab.sql.sources.v2.writer.DataSourceWriter;
import org.apache.spark.daslab.sql.sources.v2.writer.streaming.StreamWriter;
import org.apache.spark.daslab.sql.streaming.OutputMode;
import org.apache.spark.daslab.sql.types.StructType;

/**
 * A mix-in interface for {@link DataSourceV2}. Data sources can implement this interface to
 * provide data writing ability for structured streaming.
 */
@InterfaceStability.Evolving
public interface StreamWriteSupport extends DataSourceV2, BaseStreamingSink {

    /**
     * Creates an optional {@link StreamWriter} to save the data to this data source. Data
     * sources can return None if there is no writing needed to be done.
     *
     * @param queryId A unique string for the writing query. It's possible that there are many
     *                writing queries running at the same time, and the returned
     *                {@link DataSourceWriter} can use this id to distinguish itself from others.
     * @param schema the schema of the data to be written.
     * @param mode the output mode which determines what successive epoch output means to this
     *             sink, please refer to {@link OutputMode} for more details.
     * @param options the options for the returned data source writer, which is an immutable
     *                case-insensitive string-to-string map.
     */
    StreamWriter createStreamWriter(
            String queryId,
            StructType schema,
            OutputMode mode,
            DataSourceOptions options);
}
