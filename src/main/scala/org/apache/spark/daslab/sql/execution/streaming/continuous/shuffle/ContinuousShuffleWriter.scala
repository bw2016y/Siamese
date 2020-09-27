package org.apache.spark.daslab.sql.execution.streaming.continuous.shuffle



import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow

/**
 * Trait for writing to a continuous processing shuffle.
 */
trait ContinuousShuffleWriter {
  def write(epoch: Iterator[UnsafeRow]): Unit
}
