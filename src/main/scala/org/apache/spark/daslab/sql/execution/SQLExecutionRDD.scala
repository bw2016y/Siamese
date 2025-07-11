package org.apache.spark.daslab.sql.execution


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.internal.SQLConf
//todo
import org.apache.spark.{Partition, TaskContext}
import org.apache.spark.rdd.RDD


/**
 * It is just a wrapper over `sqlRDD`, which sets and makes effective all the configs from the
 * captured `SQLConf`.
 * Please notice that this means we may miss configurations set after the creation of this RDD and
 * before its execution.
 *
 * @param sqlRDD the `RDD` generated by the SQL plan
 * @param conf the `SQLConf` to apply to the execution of the SQL plan
 */
class SQLExecutionRDD(
                       var sqlRDD: RDD[InternalRow], conf: SQLConf) extends RDD[InternalRow](sqlRDD) {
  private val sqlConfigs = conf.getAllConfs
  private lazy val sqlConfExecutorSide = {
    val newConf = new SQLConf()
    sqlConfigs.foreach { case (k, v) => newConf.setConfString(k, v) }
    newConf
  }

  override val partitioner = firstParent[InternalRow].partitioner

  override def getPartitions: Array[Partition] = firstParent[InternalRow].partitions

  override def compute(split: Partition, context: TaskContext): Iterator[InternalRow] = {
    // If we are in the context of a tracked SQL operation, `SQLExecution.EXECUTION_ID_KEY` is set
    // and we have nothing to do here. Otherwise, we use the `SQLConf` captured at the creation of
    // this RDD.
    if (context.getLocalProperty(SQLExecution.EXECUTION_ID_KEY) == null) {
      SQLConf.withExistingConf(sqlConfExecutorSide) {
        firstParent[InternalRow].iterator(split, context)
      }
    } else {
      firstParent[InternalRow].iterator(split, context)
    }
  }
}
