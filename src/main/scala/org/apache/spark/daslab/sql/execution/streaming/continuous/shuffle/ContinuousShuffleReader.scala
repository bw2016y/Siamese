package org.apache.spark.daslab.sql.execution.streaming.continuous.shuffle

import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow

/**
 * Trait for reading from a continuous processing shuffle.
 */
trait ContinuousShuffleReader {
  /**
   * Returns an iterator over the incoming rows in an epoch. Implementations should block waiting
   * for new rows to arrive, and end the iterator once they've received epoch markers from all
   * shuffle writers.
   */
  def read(): Iterator[UnsafeRow]
}
