package org.apache.spark.daslab.sql.api.java;



import java.io.Serializable;


//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * A Spark SQL UDF that has 1 arguments.
 */
@InterfaceStability.Stable
public interface UDF1<T1, R> extends Serializable {
    R call(T1 t1) throws Exception;
}
