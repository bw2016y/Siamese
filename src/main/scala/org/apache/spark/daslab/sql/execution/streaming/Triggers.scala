package org.apache.spark.daslab.sql.execution.streaming

import org.apache.spark.daslab.sql.streaming.Trigger


//todo
import org.apache.spark.annotation.{Experimental, InterfaceStability}

/**
 * A [[Trigger]] that processes only one batch of data in a streaming query then terminates
 * the query.
 */
@Experimental
@InterfaceStability.Evolving
case object OneTimeTrigger extends Trigger
