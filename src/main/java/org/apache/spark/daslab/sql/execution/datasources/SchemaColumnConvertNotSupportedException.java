package org.apache.spark.daslab.sql.execution.datasources;

//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * Exception thrown when the parquet reader find column type mismatches.
 */
@InterfaceStability.Unstable
public class SchemaColumnConvertNotSupportedException extends RuntimeException {

    /**
     * Name of the column which cannot be converted.
     */
    private String column;
    /**
     * Physical column type in the actual parquet file.
     */
    private String physicalType;
    /**
     * Logical column type in the parquet schema the parquet reader use to parse all files.
     */
    private String logicalType;

    public String getColumn() {
        return column;
    }

    public String getPhysicalType() {
        return physicalType;
    }

    public String getLogicalType() {
        return logicalType;
    }

    public SchemaColumnConvertNotSupportedException(
            String column,
            String physicalType,
            String logicalType) {
        super();
        this.column = column;
        this.physicalType = physicalType;
        this.logicalType = logicalType;
    }
}
