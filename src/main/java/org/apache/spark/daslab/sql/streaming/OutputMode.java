package org.apache.spark.daslab.sql.streaming;

import org.apache.spark.daslab.sql.engine.streaming.InternalOutputModes;

//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * OutputMode describes what data will be written to a streaming sink when there is
 * new data available in a streaming DataFrame/Dataset.
 *
 * @since 2.0.0
 */
@InterfaceStability.Evolving
public class OutputMode {

    /**
     * OutputMode in which only the new rows in the streaming DataFrame/Dataset will be
     * written to the sink. This output mode can be only be used in queries that do not
     * contain any aggregation.
     *
     * @since 2.0.0
     */
    public static OutputMode Append() {
        return InternalOutputModes.Append$.MODULE$;
    }

    /**
     * OutputMode in which all the rows in the streaming DataFrame/Dataset will be written
     * to the sink every time there are some updates. This output mode can only be used in queries
     * that contain aggregations.
     *
     * @since 2.0.0
     */
    public static OutputMode Complete() {
        return InternalOutputModes.Complete$.MODULE$;
    }

    /**
     * OutputMode in which only the rows that were updated in the streaming DataFrame/Dataset will
     * be written to the sink every time there are some updates. If the query doesn't contain
     * aggregations, it will be equivalent to `Append` mode.
     *
     * @since 2.1.1
     */
    public static OutputMode Update() {
        return InternalOutputModes.Update$.MODULE$;
    }
}
