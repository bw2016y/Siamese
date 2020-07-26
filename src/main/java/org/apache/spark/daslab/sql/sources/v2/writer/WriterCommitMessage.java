package org.apache.spark.daslab.sql.sources.v2.writer;



import java.io.Serializable;

import org.apache.spark.annotation.InterfaceStability;

/**
 * A commit message returned by {@link DataWriter#commit()} and will be sent back to the driver side
 * as the input parameter of {@link DataSourceWriter#commit(WriterCommitMessage[])}.
 *
 * This is an empty interface, data sources should define their own message class and use it in
 * their {@link DataWriter#commit()} and {@link DataSourceWriter#commit(WriterCommitMessage[])}
 * implementations.
 */
@InterfaceStability.Evolving
public interface WriterCommitMessage extends Serializable {}
