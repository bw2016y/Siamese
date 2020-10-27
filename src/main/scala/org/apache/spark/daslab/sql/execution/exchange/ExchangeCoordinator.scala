package org.apache.spark.daslab.sql.execution.exchange


import java.util.{HashMap => JHashMap, Map => JMap}
import javax.annotation.concurrent.GuardedBy

import scala.collection.mutable.ArrayBuffer


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.execution.{ShuffledRowRDD, SparkPlan}

//todo
import org.apache.spark.{MapOutputStatistics, ShuffleDependency, SimpleFutureAction}
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD

/**
  *   coordinator用于决定我们如何在spark sql生成的stage之间shuffle数据。
  *   目前它只用于决定post-shuffle（这里的Post-shuffle是指下游的reduce stage的shuffle Read的过程，
  *   一般需要从一个或者多个Map stage中读取）的分区数。
  *   一般一个coordinator由三个参数构造
  *   - numExchanges : 用于指示有多少[[ShuffleExchangeExec]]将会注册到当前的cordinator中
  *   - targetPostShuffleInputSize : 一个post-shuffle分区的输入数据大小的目标大小，用这个参数我们可以
  *   估计post-shuffle分区的数量，通过spark.sql.adaptive.shuffle.targetPostShuffleInputSize来控制
  *   - minNumPostShufflePartitions : 可选的参数，如果指定了，coordinator就会确保至少会有''minNumPostShufflePartitions''
  *   个post-shuffle分区
  *
  *   coordinator的workflow:
  *   - 在[[SparkPlan]]执行之前，对于一个[[ShuffleExchangeExec]]算子，如果一个[[ExchangeCoordinator]]分配给了它
  *     [[ShuffleExchangeExec]]就将它自己注册到这个coordinator中，这一步在doPrepare方法中进行。
  *   - 当我们开始执行一个物理计划的时候，已经注册的[[ShuffleExchangeExec]]就调用postShuffleRDD来获得
  *   相应post-shuffle的[[ShuffledRowRDD]]
  *    如果这个coordinator已经决定如何shuffle数据，这个[[ShuffleExchangeExec]]就会立刻得到对应的post-shuffle
  *    [[ShuffledRowRDD]]
  *   - 如果这个coordinator还没有决定如何shuffle数据，它会要求这些已经注册的[[ShuffleExchangeExec]]来提交
  *   他们的pre-shuffle stages. 然后基于这些pre-shuffle分区的一些统计信息，这个coordinator会决定post-shuffle
  *   的分区数，而且还会在必要的时候将多个有连续下标的pre-shuffle分区打包为同一个post-shuffle分区
  *   - 最终，这个coordinator就会为所有注册的[[ShuffleExchangeExec]]创建post-shuffle [[ShuffledRowRDD]].
  *     因此，当一个[[ShuffleExchangeExec]] 调用postShuffleRDD方法的时候，这个coordinator就可以查找对应的
  *     [[RDD]]
  *
  *    用于决定post-shuffle分区数的策略如下：
  *    为了决定这个值，我们需要一个post-shuffle分区的输入数据的目标大小。一旦我们从pre-shuffle对应注册的[[ShuffleExchangeExec
  *    的Shuffle Write的stage中拿到统计量的大小，我们就遍历这些统计量，并压缩打包pre-shuffle中连续下标的分区为
  *    一个post-shuffle的分区，直到当再加入一个pre-shuffle分区就会导致post-shuffle分区大小超过目标大小
  *
  *    例如，我们有如下两个pre-shuffle分区大小的统计信息：
  *    stage 1：  [100MB , 20MB, 100MB , 10MB ,30MB]
  *    stage 2:   [10MB , 10 MB, 70MB ,5MB , 5MB]
  *    假设目标输入大小是128MB，我们将会有以下四个post-shuffle分区
  *    - post-shuffle分区0 ：pre-shuffle 分区 0 （110MB）
  *    - post-shuffle分区1 ：pre-shuffle 分区 1 （30MB）
  *    - post-shuffle分区2 ：pre-shuffle 分区 2 （170MB）
  *    - post-shuffle分区3 ：pre-shuffle 分区 3和4 （50MB）
  *
  * @param advisoryTargetPostShuffleInputSize
  * @param minNumPostShufflePartitions
  */
class ExchangeCoordinator(
                           advisoryTargetPostShuffleInputSize: Long,
                           minNumPostShufflePartitions: Option[Int] = None)
  extends Logging {

  // The registered Exchange operators.
  private[this] val exchanges = ArrayBuffer[ShuffleExchangeExec]()

  // `lazy val` is used here so that we could notice the wrong use of this class, e.g., all the
  // exchanges should be registered before `postShuffleRDD` called first time. If a new exchange is
  // registered after the `postShuffleRDD` call, `assert(exchanges.length == numExchanges)` fails
  // in `doEstimationIfNecessary`.
  private[this] lazy val numExchanges = exchanges.size

  // This map is used to lookup the post-shuffle ShuffledRowRDD for an Exchange operator.
  private[this] lazy val postShuffleRDDs: JMap[ShuffleExchangeExec, ShuffledRowRDD] =
    new JHashMap[ShuffleExchangeExec, ShuffledRowRDD](numExchanges)

  // A boolean that indicates if this coordinator has made decision on how to shuffle data.
  // This variable will only be updated by doEstimationIfNecessary, which is protected by
  // synchronized.
  @volatile private[this] var estimated: Boolean = false

  /**
   * Registers a [[ShuffleExchangeExec]] operator to this coordinator. This method is only allowed
   * to be called in the `doPrepare` method of a [[ShuffleExchangeExec]] operator.
   */
  @GuardedBy("this")
  def registerExchange(exchange: ShuffleExchangeExec): Unit = synchronized {
    exchanges += exchange
  }

  def isEstimated: Boolean = estimated

  /**
   * Estimates partition start indices for post-shuffle partitions based on
   * mapOutputStatistics provided by all pre-shuffle stages.
   */
  def estimatePartitionStartIndices(
                                     mapOutputStatistics: Array[MapOutputStatistics]): Array[Int] = {
    // If minNumPostShufflePartitions is defined, it is possible that we need to use a
    // value less than advisoryTargetPostShuffleInputSize as the target input size of
    // a post shuffle task.
    val targetPostShuffleInputSize = minNumPostShufflePartitions match {
      case Some(numPartitions) =>
        val totalPostShuffleInputSize = mapOutputStatistics.map(_.bytesByPartitionId.sum).sum
        // The max at here is to make sure that when we have an empty table, we
        // only have a single post-shuffle partition.
        // There is no particular reason that we pick 16. We just need a number to
        // prevent maxPostShuffleInputSize from being set to 0.
        val maxPostShuffleInputSize =
        math.max(math.ceil(totalPostShuffleInputSize / numPartitions.toDouble).toLong, 16)
        math.min(maxPostShuffleInputSize, advisoryTargetPostShuffleInputSize)

      case None => advisoryTargetPostShuffleInputSize
    }

    logInfo(
      s"advisoryTargetPostShuffleInputSize: $advisoryTargetPostShuffleInputSize, " +
        s"targetPostShuffleInputSize $targetPostShuffleInputSize.")

    // Make sure we do get the same number of pre-shuffle partitions for those stages.
    val distinctNumPreShufflePartitions =
      mapOutputStatistics.map(stats => stats.bytesByPartitionId.length).distinct
    // The reason that we are expecting a single value of the number of pre-shuffle partitions
    // is that when we add Exchanges, we set the number of pre-shuffle partitions
    // (i.e. map output partitions) using a static setting, which is the value of
    // spark.sql.shuffle.partitions. Even if two input RDDs are having different
    // number of partitions, they will have the same number of pre-shuffle partitions
    // (i.e. map output partitions).
    assert(
      distinctNumPreShufflePartitions.length == 1,
      "There should be only one distinct value of the number pre-shuffle partitions " +
        "among registered Exchange operator.")
    val numPreShufflePartitions = distinctNumPreShufflePartitions.head

    val partitionStartIndices = ArrayBuffer[Int]()
    // The first element of partitionStartIndices is always 0.
    partitionStartIndices += 0

    var postShuffleInputSize = 0L

    var i = 0
    while (i < numPreShufflePartitions) {
      // We calculate the total size of ith pre-shuffle partitions from all pre-shuffle stages.
      // Then, we add the total size to postShuffleInputSize.
      var nextShuffleInputSize = 0L
      var j = 0
      while (j < mapOutputStatistics.length) {
        nextShuffleInputSize += mapOutputStatistics(j).bytesByPartitionId(i)
        j += 1
      }

      // If including the nextShuffleInputSize would exceed the target partition size, then start a
      // new partition.
      if (i > 0 && postShuffleInputSize + nextShuffleInputSize > targetPostShuffleInputSize) {
        partitionStartIndices += i
        // reset postShuffleInputSize.
        postShuffleInputSize = nextShuffleInputSize
      } else postShuffleInputSize += nextShuffleInputSize

      i += 1
    }

    partitionStartIndices.toArray
  }
  //将ShuffleExchange之前的Stage提交到集群执行来获取相关的信息
  @GuardedBy("this")
  private def doEstimationIfNecessary(): Unit = synchronized {
    // It is unlikely that this method will be called from multiple threads
    // (when multiple threads trigger the execution of THIS physical)
    // because in common use cases, we will create new physical plan after
    // users apply operations (e.g. projection) to an existing DataFrame.
    // However, if it happens, we have synchronized to make sure only one
    // thread will trigger the job submission.
    if (!estimated) {
      // Make sure we have the expected number of registered Exchange operators.
      assert(exchanges.length == numExchanges)

      val newPostShuffleRDDs = new JHashMap[ShuffleExchangeExec, ShuffledRowRDD](numExchanges)

      // Submit all map stages
      val shuffleDependencies = ArrayBuffer[ShuffleDependency[Int, InternalRow, InternalRow]]()
      val submittedStageFutures = ArrayBuffer[SimpleFutureAction[MapOutputStatistics]]()
      var i = 0
      while (i < numExchanges) {
        val exchange = exchanges(i)
        val shuffleDependency = exchange.prepareShuffleDependency()
        shuffleDependencies += shuffleDependency
        if (shuffleDependency.rdd.partitions.length != 0) {
          // submitMapStage does not accept RDD with 0 partition.
          // So, we will not submit this dependency.
          submittedStageFutures +=
            exchange.sqlContext.sparkContext.submitMapStage(shuffleDependency)
        }
        i += 1
      }

      // Wait for the finishes of those submitted map stages.
      val mapOutputStatistics = new Array[MapOutputStatistics](submittedStageFutures.length)
      var j = 0
      while (j < submittedStageFutures.length) {
        // This call is a blocking call. If the stage has not finished, we will wait at here.
        mapOutputStatistics(j) = submittedStageFutures(j).get()
        j += 1
      }

      // If we have mapOutputStatistics.length < numExchange, it is because we do not submit
      // a stage when the number of partitions of this dependency is 0.
      assert(mapOutputStatistics.length <= numExchanges)

      // Now, we estimate partitionStartIndices. partitionStartIndices.length will be the
      // number of post-shuffle partitions.
      val partitionStartIndices =
      if (mapOutputStatistics.length == 0) {
        Array.empty[Int]
      } else {
        estimatePartitionStartIndices(mapOutputStatistics)
      }

      var k = 0
      while (k < numExchanges) {
        val exchange = exchanges(k)
        val rdd =
          exchange.preparePostShuffleRDD(shuffleDependencies(k), Some(partitionStartIndices))
        newPostShuffleRDDs.put(exchange, rdd)

        k += 1
      }

      // Finally, we set postShuffleRDDs and estimated.
      assert(postShuffleRDDs.isEmpty)
      assert(newPostShuffleRDDs.size() == numExchanges)
      postShuffleRDDs.putAll(newPostShuffleRDDs)
      estimated = true
    }
  }

  def postShuffleRDD(exchange: ShuffleExchangeExec): ShuffledRowRDD = {
    doEstimationIfNecessary()

    if (!postShuffleRDDs.containsKey(exchange)) {
      throw new IllegalStateException(
        s"The given $exchange is not registered in this coordinator.")
    }

    postShuffleRDDs.get(exchange)
  }

  override def toString: String = {
    s"coordinator[target post-shuffle partition size: $advisoryTargetPostShuffleInputSize]"
  }
}
