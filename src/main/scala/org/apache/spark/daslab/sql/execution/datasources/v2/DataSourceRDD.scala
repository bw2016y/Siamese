package org.apache.spark.daslab.sql.execution.datasources.v2



import scala.reflect.ClassTag

//todo
import org.apache.spark.{InterruptibleIterator, Partition, SparkContext, TaskContext}
import org.apache.spark.rdd.RDD


import org.apache.spark.daslab.sql.sources.v2.reader.InputPartition

class DataSourceRDDPartition[T : ClassTag](val index: Int, val inputPartition: InputPartition[T])
  extends Partition with Serializable

class DataSourceRDD[T: ClassTag](
                                  sc: SparkContext,
                                  @transient private val inputPartitions: Seq[InputPartition[T]])
  extends RDD[T](sc, Nil) {

  override protected def getPartitions: Array[Partition] = {
    inputPartitions.zipWithIndex.map {
      case (inputPartition, index) => new DataSourceRDDPartition(index, inputPartition)
    }.toArray
  }

  override def compute(split: Partition, context: TaskContext): Iterator[T] = {
    val reader = split.asInstanceOf[DataSourceRDDPartition[T]].inputPartition
      .createPartitionReader()
    context.addTaskCompletionListener[Unit](_ => reader.close())
    val iter = new Iterator[T] {
      private[this] var valuePrepared = false

      override def hasNext: Boolean = {
        if (!valuePrepared) {
          valuePrepared = reader.next()
        }
        valuePrepared
      }

      override def next(): T = {
        if (!hasNext) {
          throw new java.util.NoSuchElementException("End of stream")
        }
        valuePrepared = false
        reader.get()
      }
    }
    new InterruptibleIterator(context, iter)
  }

  override def getPreferredLocations(split: Partition): Seq[String] = {
    split.asInstanceOf[DataSourceRDDPartition[T]].inputPartition.preferredLocations()
  }
}
