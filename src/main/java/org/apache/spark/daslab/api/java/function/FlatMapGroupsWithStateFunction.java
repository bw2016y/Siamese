package org.apache.spark.daslab.api.java.function;


import java.io.Serializable;
import java.util.Iterator;

import org.apache.spark.daslab.sql.streaming.GroupState;
//todo
import org.apache.spark.annotation.Experimental;
import org.apache.spark.annotation.InterfaceStability;

/**
 * ::Experimental::
 * Base interface for a map function used in
 * {@code org.apache.spark.daslab.sql.KeyValueGroupedDataset.flatMapGroupsWithState(
 * FlatMapGroupsWithStateFunction, org.apache.spark.daslab.sql.streaming.OutputMode,
 * org.apache.spark.daslab.sql.Encoder, org.apache.spark.daslab.sql.Encoder)}
 * @since 2.1.1
 */
@Experimental
@InterfaceStability.Evolving
public interface FlatMapGroupsWithStateFunction<K, V, S, R> extends Serializable {
    Iterator<R> call(K key, Iterator<V> values, GroupState<S> state) throws Exception;
}
