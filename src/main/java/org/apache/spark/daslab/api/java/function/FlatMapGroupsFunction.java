package org.apache.spark.daslab.api.java.function;


import java.io.Serializable;
import java.util.Iterator;

/**
 * A function that returns zero or more output records from each grouping key and its values.
 */
@FunctionalInterface
public interface FlatMapGroupsFunction<K, V, R> extends Serializable {
    Iterator<R> call(K key, Iterator<V> values) throws Exception;
}
