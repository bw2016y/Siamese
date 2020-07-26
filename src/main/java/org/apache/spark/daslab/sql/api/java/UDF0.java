package org.apache.spark.daslab.sql.api.java;



import java.io.Serializable;


//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * A Spark SQL UDF that has 0 arguments.
 */
@InterfaceStability.Stable
public interface UDF0<R> extends Serializable {
    R call() throws Exception;
}
