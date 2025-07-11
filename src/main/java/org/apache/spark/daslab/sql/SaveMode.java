package org.apache.spark.daslab.sql;

//todo

import org.apache.spark.annotation.InterfaceStability;

/**
 * SaveMode is used to specify the expected behavior of saving a DataFrame to a data source.
 *
 * @since 1.3.0
 */
@InterfaceStability.Stable
public enum SaveMode {
    /**
     * Append mode means that when saving a DataFrame to a data source, if data/table already exists,
     * contents of the DataFrame are expected to be appended to existing data.
     *
     * @since 1.3.0
     */
    Append,
    /**
     * Overwrite mode means that when saving a DataFrame to a data source,
     * if data/table already exists, existing data is expected to be overwritten by the contents of
     * the DataFrame.
     *
     * @since 1.3.0
     */
    Overwrite,
    /**
     * ErrorIfExists mode means that when saving a DataFrame to a data source, if data already exists,
     * an exception is expected to be thrown.
     *
     * @since 1.3.0
     */
    ErrorIfExists,
    /**
     * Ignore mode means that when saving a DataFrame to a data source, if data already exists,
     * the save operation is expected to not save the contents of the DataFrame and to not
     * change the existing data.
     *
     * @since 1.3.0
     */
    Ignore
}
