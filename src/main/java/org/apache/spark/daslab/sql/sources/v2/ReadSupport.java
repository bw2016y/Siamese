package org.apache.spark.daslab.sql.sources.v2;


import org.apache.spark.annotation.InterfaceStability;
import org.apache.spark.daslab.sql.sources.DataSourceRegister;
import org.apache.spark.daslab.sql.sources.v2.reader.DataSourceReader;
import org.apache.spark.daslab.sql.types.StructType;

/**
 * A mix-in interface for {@link DataSourceV2}. Data sources can implement this interface to
 * provide data reading ability and scan the data from the data source.
 */
@InterfaceStability.Evolving
public interface ReadSupport extends DataSourceV2 {

    /**
     * Creates a {@link DataSourceReader} to scan the data from this data source.
     *
     * If this method fails (by throwing an exception), the action will fail and no Spark job will be
     * submitted.
     *
     * @param schema the user specified schema.
     * @param options the options for the returned data source reader, which is an immutable
     *                case-insensitive string-to-string map.
     *
     * By default this method throws {@link UnsupportedOperationException}, implementations should
     * override this method to handle user specified schema.
     */
    default DataSourceReader createReader(StructType schema, DataSourceOptions options) {
        String name;
        if (this instanceof DataSourceRegister) {
            name = ((DataSourceRegister) this).shortName();
        } else {
            name = this.getClass().getName();
        }
        throw new UnsupportedOperationException(name + " does not support user specified schema");
    }

    /**
     * Creates a {@link DataSourceReader} to scan the data from this data source.
     *
     * If this method fails (by throwing an exception), the action will fail and no Spark job will be
     * submitted.
     *
     * @param options the options for the returned data source reader, which is an immutable
     *                case-insensitive string-to-string map.
     */
    DataSourceReader createReader(DataSourceOptions options);
}
