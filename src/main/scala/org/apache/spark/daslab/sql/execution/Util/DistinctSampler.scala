package org.apache.spark.daslab.sql.execution.util

import java.util.Random

import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow
import org.apache.spark.daslab.sql.types.DataType
import org.apache.spark.util.random.{RandomSampler, XORShiftRandom}


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
  private val partitionDelta = delta/numPartitions + epsilon
  private val reservoirAmount = 2
  private val rng: Random = new XORShiftRandom
  private val distinctValueCounts = scala.collection.mutable.HashMap.empty[List[Any], Int]
  private val distinctValueReservoirs = scala.collection.mutable.HashMap.empty[List[Any], Array[InternalRow]]

  override def setSeed(seed: Long): Unit = rng.setSeed(seed)

  /**
   * distincSampler的分层采样逻辑
   * @param rows
   * @return
   */
  override def sample(rows: Iterator[InternalRow]): Iterator[InternalRow] = {
    var new_rows: Iterator[InternalRow] = rows.filter(row => {
      /*println("internalrow")
      println(item.numFields)*/
      var distinctValue: List[Any] = List()
      S.foreach(s => {
        distinctValue = distinctValue ::: row.get(s.ordinal, s.datatype) :: Nil
      })
      val count: Int = distinctValueCounts.getOrElse(distinctValue, 0)
      if (count < partitionDelta) {
        // 属于第一区间，直接采样，权重为1
        distinctValueCounts.put(distinctValue, count + 1)
        true
      } else if (count < partitionDelta + reservoirAmount / fraction) {
        // 属于第二区间，存入采样池，权重最后再算
        distinctValueCounts.put(distinctValue, count + 1)
        val reservoir: Array[InternalRow] = distinctValueReservoirs.getOrElse(distinctValue, new Array[InternalRow](reservoirAmount))
        var index = count - partitionDelta
        if (index >= reservoirAmount) {
          index = (rng.nextDouble * reservoirAmount).toInt
        }
        reservoir(index) = row
        distinctValueReservoirs.put(distinctValue, reservoir)
        false
      } else {
        // 属于第三区间，按概率p采样，权重1/p
        if (sample() > 0) {
          row.asInstanceOf[UnsafeRow].setDouble(row.numFields - 1, 1 / fraction)
          true
        } else {
          false
        }
      }
    })
    // 给采样池中的行计算权重并加入总样本中
    distinctValueReservoirs.map{
      case (key,reservoir) => {
        val count: Int = distinctValueCounts.getOrElse(key,0)
        reservoir.take(count - partitionDelta)
          .foreach(row => row.asInstanceOf[UnsafeRow].setDouble(row.numFields - 1, (count-partitionDelta)/reservoirAmount))
        new_rows = new_rows ++ reservoir
        (key,reservoir)
      }
    }
    new_rows
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

