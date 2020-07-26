package org.apache.spark.daslab.sql.streaming;

import org.apache.spark.daslab.sql.engine.plans.logical.*;
//todo
import org.apache.spark.annotation.Experimental;
import org.apache.spark.annotation.InterfaceStability;

/**
 * Represents the type of timeouts possible for the Dataset operations
 * `mapGroupsWithState` and `flatMapGroupsWithState`. See documentation on
 * `GroupState` for more details.
 *
 * @since 2.2.0
 */
@Experimental
@InterfaceStability.Evolving
public class GroupStateTimeout {

    /**
     * Timeout based on processing time. The duration of timeout can be set for each group in
     * `map/flatMapGroupsWithState` by calling `GroupState.setTimeoutDuration()`. See documentation
     * on `GroupState` for more details.
     */
    public static GroupStateTimeout ProcessingTimeTimeout() {
        return ProcessingTimeTimeout$.MODULE$;
    }

    /**
     * Timeout based on event-time. The event-time timestamp for timeout can be set for each
     * group in `map/flatMapGroupsWithState` by calling `GroupState.setTimeoutTimestamp()`.
     * In addition, you have to define the watermark in the query using `Dataset.withWatermark`.
     * When the watermark advances beyond the set timestamp of a group and the group has not
     * received any data, then the group times out. See documentation on
     * `GroupState` for more details.
     */
    public static GroupStateTimeout EventTimeTimeout() { return EventTimeTimeout$.MODULE$; }

    /** No timeout. */
    public static GroupStateTimeout NoTimeout() { return NoTimeout$.MODULE$; }

}
