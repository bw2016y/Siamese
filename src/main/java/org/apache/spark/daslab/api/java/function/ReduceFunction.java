package org.apache.spark.daslab.api.java.function;



import java.io.Serializable;

/**
 * Base interface for function used in Dataset's reduce.
 */
@FunctionalInterface
public interface ReduceFunction<T> extends Serializable {
    T call(T v1, T v2) throws Exception;
}
