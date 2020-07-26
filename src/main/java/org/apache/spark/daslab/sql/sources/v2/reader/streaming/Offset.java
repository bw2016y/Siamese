package org.apache.spark.daslab.sql.sources.v2.reader.streaming;


//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * An abstract representation of progress through a {@link MicroBatchReader} or
 * {@link ContinuousReader}.
 * During execution, offsets provided by the data source implementation will be logged and used as
 * restart checkpoints. Each source should provide an offset implementation which the source can use
 * to reconstruct a position in the stream up to which data has been seen/processed.
 *
 * Note: This class currently extends {@link org.apache.spark.daslab.sql.execution.streaming.Offset} to
 * maintain compatibility with DataSource V1 APIs. This extension will be removed once we
 * get rid of V1 completely.
 */
@InterfaceStability.Evolving
public abstract class Offset extends org.apache.spark.daslab.sql.execution.streaming.Offset {
    /**
     * A JSON-serialized representation of an Offset that is
     * used for saving offsets to the offset log.
     * Note: We assume that equivalent/equal offsets serialize to
     * identical JSON strings.
     *
     * @return JSON string encoding
     */
    public abstract String json();

    /**
     * Equality based on JSON string representation. We leverage the
     * JSON representation for normalization between the Offset's
     * in deserialized and serialized representations.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof org.apache.spark.daslab.sql.execution.streaming.Offset) {
            return this.json()
                    .equals(((org.apache.spark.daslab.sql.execution.streaming.Offset) obj).json());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.json().hashCode();
    }

    @Override
    public String toString() {
        return this.json();
    }
}
