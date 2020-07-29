package org.apache.spark.daslab.sql.execution


//todo
import org.apache.spark.rdd.RDD

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, UnsafeProjection}
import org.apache.spark.daslab.sql.execution.metric.SQLMetrics


/**
 * Physical plan node for scanning data from a local collection.
 *
 * `Seq` may not be serializable and ideally we should not send `rows` and `unsafeRows`
 * to the executors. Thus marking them as transient.
 */
case class LocalTableScanExec(
                               output: Seq[Attribute],
                               @transient rows: Seq[InternalRow]) extends LeafExecNode {

  override lazy val metrics = Map(
    "numOutputRows" -> SQLMetrics.createMetric(sparkContext, "number of output rows"))

  @transient private lazy val unsafeRows: Array[InternalRow] = {
    if (rows.isEmpty) {
      Array.empty
    } else {
      val proj = UnsafeProjection.create(output, output)
      rows.map(r => proj(r).copy()).toArray
    }
  }

  private lazy val numParallelism: Int = math.min(math.max(unsafeRows.length, 1),
    sqlContext.sparkContext.defaultParallelism)

  private lazy val rdd = sqlContext.sparkContext.parallelize(unsafeRows, numParallelism)

  protected override def doExecute(): RDD[InternalRow] = {
    val numOutputRows = longMetric("numOutputRows")
    rdd.map { r =>
      numOutputRows += 1
      r
    }
  }

  override protected def stringArgs: Iterator[Any] = {
    if (rows.isEmpty) {
      Iterator("<empty>", output)
    } else {
      Iterator(output)
    }
  }

  override def executeCollect(): Array[InternalRow] = {
    longMetric("numOutputRows").add(unsafeRows.size)
    unsafeRows
  }

  override def executeTake(limit: Int): Array[InternalRow] = {
    val taken = unsafeRows.take(limit)
    longMetric("numOutputRows").add(taken.size)
    taken
  }
}
