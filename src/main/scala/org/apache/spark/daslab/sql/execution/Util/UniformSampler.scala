package org.apache.spark.daslab.sql.execution.Util

import java.util.Random

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow
import org.apache.spark.util.random.{RandomSampler, XORShiftRandom}

/**
 *  uniform sampler
 *
 * @param fraction : acceptance
 */
class UniformSampler(fraction:Double) extends RandomSampler[InternalRow,InternalRow]{

  /** epsilon slop to avoid failure from floating point jitter. */
  require(
    fraction >= (0.0 - RandomSampler.roundingEpsilon),
    s"$fraction must be >= 0.0")
  require(
    fraction <= (1.0 + RandomSampler.roundingEpsilon),
    s"$fraction must be <= 1.0")


  private val randomS : Random = new XORShiftRandom


  override def setSeed(seed: Long): Unit = randomS.setSeed(seed)


  /**
   * uniformSampler的采样逻辑
   * @param rows
   * @return
   */
  override def sample(rows: Iterator[InternalRow]): Iterator[InternalRow] = {
      val res:Iterator[InternalRow] = rows.filter{
        row =>
          if(sample()>0){
            row.asInstanceOf[UnsafeRow].setDouble(row.numFields-1,fraction)
            true
          }else{
            false
          }
      }
      res
  }

  /**
   *  根据概率fraction均匀采样的逻辑
   * @return
   */
  override def sample(): Int = {
    if (fraction <= 0.0 + RandomSampler.roundingEpsilon ){
      0
    }else {
      val x = randomS.nextDouble()
      val n = if (x<fraction) 1 else 0
      n
    }
  }


  override def clone: UniformSampler = new UniformSampler(fraction)

}
