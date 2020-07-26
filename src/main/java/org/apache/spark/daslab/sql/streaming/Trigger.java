package org.apache.spark.daslab.sql.streaming;



import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;

import org.apache.spark.daslab.sql.execution.streaming.continuous.ContinuousTrigger;
import org.apache.spark.daslab.sql.execution.streaming.OneTimeTrigger$;

//todo
import org.apache.spark.annotation.InterfaceStability;


/**
 * Policy used to indicate how often results should be produced by a [[StreamingQuery]].
 *
 * @since 2.0.0
 */
@InterfaceStability.Evolving
public class Trigger {

    /**
     * A trigger policy that runs a query periodically based on an interval in processing time.
     * If `interval` is 0, the query will run as fast as possible.
     *
     * @since 2.2.0
     */
    public static Trigger ProcessingTime(long intervalMs) {
        return ProcessingTime.create(intervalMs, TimeUnit.MILLISECONDS);
    }

    /**
     * (Java-friendly)
     * A trigger policy that runs a query periodically based on an interval in processing time.
     * If `interval` is 0, the query will run as fast as possible.
     *
     * {{{
     *    import java.util.concurrent.TimeUnit
     *    df.writeStream().trigger(Trigger.ProcessingTime(10, TimeUnit.SECONDS))
     * }}}
     *
     * @since 2.2.0
     */
    public static Trigger ProcessingTime(long interval, TimeUnit timeUnit) {
        return ProcessingTime.create(interval, timeUnit);
    }

    /**
     * (Scala-friendly)
     * A trigger policy that runs a query periodically based on an interval in processing time.
     * If `duration` is 0, the query will run as fast as possible.
     *
     * {{{
     *    import scala.concurrent.duration._
     *    df.writeStream.trigger(Trigger.ProcessingTime(10.seconds))
     * }}}
     * @since 2.2.0
     */
    public static Trigger ProcessingTime(Duration interval) {
        return ProcessingTime.apply(interval);
    }

    /**
     * A trigger policy that runs a query periodically based on an interval in processing time.
     * If `interval` is effectively 0, the query will run as fast as possible.
     *
     * {{{
     *    df.writeStream.trigger(Trigger.ProcessingTime("10 seconds"))
     * }}}
     * @since 2.2.0
     */
    public static Trigger ProcessingTime(String interval) {
        return ProcessingTime.apply(interval);
    }

    /**
     * A trigger that process only one batch of data in a streaming query then terminates
     * the query.
     *
     * @since 2.2.0
     */
    public static Trigger Once() {
        return OneTimeTrigger$.MODULE$;
    }

    /**
     * A trigger that continuously processes streaming data, asynchronously checkpointing at
     * the specified interval.
     *
     * @since 2.3.0
     */
    public static Trigger Continuous(long intervalMs) {
        return ContinuousTrigger.apply(intervalMs);
    }

    /**
     * A trigger that continuously processes streaming data, asynchronously checkpointing at
     * the specified interval.
     *
     * {{{
     *    import java.util.concurrent.TimeUnit
     *    df.writeStream.trigger(Trigger.Continuous(10, TimeUnit.SECONDS))
     * }}}
     *
     * @since 2.3.0
     */
    public static Trigger Continuous(long interval, TimeUnit timeUnit) {
        return ContinuousTrigger.create(interval, timeUnit);
    }

    /**
     * (Scala-friendly)
     * A trigger that continuously processes streaming data, asynchronously checkpointing at
     * the specified interval.
     *
     * {{{
     *    import scala.concurrent.duration._
     *    df.writeStream.trigger(Trigger.Continuous(10.seconds))
     * }}}
     * @since 2.3.0
     */
    public static Trigger Continuous(Duration interval) {
        return ContinuousTrigger.apply(interval);
    }

    /**
     * A trigger that continuously processes streaming data, asynchronously checkpointing at
     * the specified interval.
     *
     * {{{
     *    df.writeStream.trigger(Trigger.Continuous("10 seconds"))
     * }}}
     * @since 2.3.0
     */
    public static Trigger Continuous(String interval) {
        return ContinuousTrigger.apply(interval);
    }
}
