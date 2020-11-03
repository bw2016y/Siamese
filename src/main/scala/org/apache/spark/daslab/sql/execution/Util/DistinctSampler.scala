package org.apache.spark.daslab.sql.execution.util

import java.util.Random

import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.AttributeReference
import org.apache.spark.daslab.sql.types.{DataType, LongType}
import org.apache.spark.util.random.{RandomSampler, XORShiftRandom}

import scala.collection.immutable.HashMap

/**
 * :: DeveloperApi ::
 * A sampler based on stratified sampling and improves on weight
 *
 * @param fraction the acceptance fraction
 */
@DeveloperApi
class DistinctSampler(S: Seq[DistinctColumn], delta: Int, fraction: Double, numPartitions: Int)
  extends RandomSampler[InternalRow,InternalRow] {

  /** epsilon slop to avoid failure from floating point jitter. */
  require(
    fraction >= (0.0 - RandomSampler.roundingEpsilon),
    s"$fraction must be >= 0.0")
  require(
    fraction <= (1.0 + RandomSampler.roundingEpsilon),
    s"$fraction must be <= 1.0")

  private val epsilon = delta/numPartitions
  private val rng: Random = new XORShiftRandom
  private val distinctValueCounts = scala.collection.mutable.HashMap.empty[List[Any], Int]

  override def setSeed(seed: Long): Unit = rng.setSeed(seed)

  /**
   * distincSampler的分层采样逻辑
   * @param items
   * @return
   */
  override def sample(items: Iterator[InternalRow]): Iterator[InternalRow] = {
    items.filter(item => {
      /*println("internalrow")
      println(item.numFields)*/
      var distinctValue: List[Any] = List()
      S.foreach(s => {
        distinctValue = distinctValue ::: item.get(s.ordinal, s.datatype) :: Nil
      })
      val count: Int = distinctValueCounts.getOrElse(distinctValue,0)
      if (count < delta/numPartitions + epsilon) {
        distinctValueCounts.put(distinctValue,count+1)
        true
      } else {
        sample() > 0
      }
    })
  }

  /**
   * 按照概率fraction均匀采样的逻辑
   * @return
   */
  override def sample(): Int = {
    if (fraction <= 0.0 + RandomSampler.roundingEpsilon) {
      0
    } else {
      val x = rng.nextDouble()
      val n = if (x < fraction) 1 else 0
      n
    }
  }

  override def clone: DistinctSampler = new DistinctSampler(S, delta, fraction, numPartitions)
}

case class DistinctColumn(ordinal: Int, datatype: DataType, name: String)

