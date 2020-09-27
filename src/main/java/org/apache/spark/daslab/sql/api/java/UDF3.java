package org.apache.spark.daslab.sql.api.java;


import java.io.Serializable;


//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * A Spark SQL UDF that has 3 arguments.
 */
@InterfaceStability.Stable
public interface UDF3<T1, T2, T3, R> extends Serializable {
    R call(T1 t1, T2 t2, T3 t3) throws Exception;
}
