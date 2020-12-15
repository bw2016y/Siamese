package org.apache.spark.daslab.sql.execution.util

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.UnsafeProjection
import org.apache.spark.daslab.sql.execution.Util.UniformSampler
import org.apache.spark.rdd.RDD
import org.apache.spark.util.random.BernoulliCellSampler

object SampleUtils {

  def uniformSample(index: Int, rows: Iterator[InternalRow], fraction: Double, seed: Long):Iterator[InternalRow] = {
    //val sampler: BernoulliCellSampler[InternalRow] = new BernoulliCellSampler[InternalRow](0.0,fraction)
    //sampler.setSeed(seed + index)
    //sampler.sample(rows)

    val ms : UniformSampler = new UniformSampler(fraction)
    ms.setSeed(seed+index)
    ms.sample(rows)
  }

  // TODO 如何确定prev的分区数D？
  def distinctSample(index: Int, rows: Iterator[InternalRow], S: Seq[DistinctColumn], delta: Int, fraction: Double, numPartitions: Int, seed: Long):Iterator[InternalRow] = {
    val sampler: DistinctSampler = new DistinctSampler(S, delta, fraction, numPartitions)
    sampler.setSeed(seed + index)
    sampler.sample(rows)
  }
}
