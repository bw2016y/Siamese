package org.apache.spark.daslab.sql.api.java;



import java.io.Serializable;

import org.apache.spark.annotation.InterfaceStability;

/**
 * A Spark SQL UDF that has 13 arguments.
 */
@InterfaceStability.Stable
public interface UDF13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R> extends Serializable {
    R call(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) throws Exception;
}
