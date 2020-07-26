package org.apache.spark.daslab.api.java.function;



import java.io.Serializable;

/**
 * Base interface for a map function used in Dataset's map function.
 */
@FunctionalInterface
public interface MapFunction<T, U> extends Serializable {
    U call(T value) throws Exception;
}
