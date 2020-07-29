package org.apache.spark.daslab.sql.sources.v2;


//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * A mix-in interface for {@link DataSourceV2}. Data sources can implement this interface to
 * propagate session configs with the specified key-prefix to all data source operations in this
 * session.
 */
@InterfaceStability.Evolving
public interface SessionConfigSupport extends DataSourceV2 {

    /**
     * Key prefix of the session configs to propagate. Spark will extract all session configs that
     * starts with `spark.datasource.$keyPrefix`, turn `spark.datasource.$keyPrefix.xxx -&gt; yyy`
     * into `xxx -&gt; yyy`, and propagate them to all data source operations in this session.
     */
    String keyPrefix();
}
