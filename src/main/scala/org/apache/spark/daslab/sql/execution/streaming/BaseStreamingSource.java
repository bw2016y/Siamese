package org.apache.spark.daslab.sql.execution.streaming;


/**
 * The shared interface between V1 streaming sources and V2 streaming readers.
 *
 * This is a temporary interface for compatibility during migration. It should not be implemented
 * directly, and will be removed in future versions.
 */
public interface BaseStreamingSource {
    /** Stop this source and free any resources it has allocated. */
    void stop();
}
