package org.apache.spark.daslab.sql.execution.util

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.UnsafeProjection
import org.apache.spark.rdd.RDD
import org.apache.spark.util.random.BernoulliCellSampler

object SampleUtils {

  def uniformSample(prev: RDD[InternalRow], fraction: Double, seed: Long):RDD[InternalRow] = {
    prev.mapPartitionsWithIndex( { (index,partition) =>
      val sampler: BernoulliCellSampler[InternalRow] = new BernoulliCellSampler[InternalRow](0.0,fraction)
      sampler.setSeed(seed + index)
      sampler.sample(partition)
    }, isOrderSensitive = true, preservesPartitioning = true)
  }

  // TODO 如何确定prev的分区数D？
  def distinctSample(prev: RDD[InternalRow], S: Seq[DistinctColumn], delta: Int, fraction: Double, numPartitions: Int, seed: Long):RDD[InternalRow] = {
    prev.mapPartitionsWithIndex( { (index,partition) =>
      val sampler: DistinctSampler = new DistinctSampler(S, delta, fraction, numPartitions)
      sampler.setSeed(seed + index)
      sampler.sample(partition)
    }, isOrderSensitive = true, preservesPartitioning = true)
  }
}
