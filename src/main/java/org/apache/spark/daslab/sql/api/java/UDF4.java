package org.apache.spark.daslab.sql.api.java;



import java.io.Serializable;


//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * A Spark SQL UDF that has 4 arguments.
 */
@InterfaceStability.Stable
public interface UDF4<T1, T2, T3, T4, R> extends Serializable {
    R call(T1 t1, T2 t2, T3 t3, T4 t4) throws Exception;
}
