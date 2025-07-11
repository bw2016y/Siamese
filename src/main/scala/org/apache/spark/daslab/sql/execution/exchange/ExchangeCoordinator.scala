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
 * A coordinator used to determines how we shuffle data between stages generated by Spark SQL.
 * Right now, the work of this coordinator is to determine the number of post-shuffle partitions
 * for a stage that needs to fetch shuffle data from one or multiple stages.
 *
 * A coordinator is constructed with three parameters, `numExchanges`,
 * `targetPostShuffleInputSize`, and `minNumPostShufflePartitions`.
 *  - `numExchanges` is used to indicated that how many [[ShuffleExchangeExec]]s that will be
 *    registered to this coordinator. So, when we start to do any actual work, we have a way to
 *    make sure that we have got expected number of [[ShuffleExchangeExec]]s.
 *  - `targetPostShuffleInputSize` is the targeted size of a post-shuffle partition's
 *    input data size. With this parameter, we can estimate the number of post-shuffle partitions.
 *    This parameter is configured through
 *    `spark.sql.adaptive.shuffle.targetPostShuffleInputSize`.
 *  - `minNumPostShufflePartitions` is an optional parameter. If it is defined, this coordinator
 *    will try to make sure that there are at least `minNumPostShufflePartitions` post-shuffle
 *    partitions.
 *
 * The workflow of this coordinator is described as follows:
 *  - Before the execution of a [[SparkPlan]], for a [[ShuffleExchangeExec]] operator,
 *    if an [[ExchangeCoordinator]] is assigned to it, it registers itself to this coordinator.
 *    This happens in the `doPrepare` method.
 *  - Once we start to execute a physical plan, a [[ShuffleExchangeExec]] registered to this
 *    coordinator will call `postShuffleRDD` to get its corresponding post-shuffle
 *    [[ShuffledRowRDD]].
 *    If this coordinator has made the decision on how to shuffle data, this [[ShuffleExchangeExec]]
 *    will immediately get its corresponding post-shuffle [[ShuffledRowRDD]].
 *  - If this coordinator has not made the decision on how to shuffle data, it will ask those
 *    registered [[ShuffleExchangeExec]]s to submit their pre-shuffle stages. Then, based on the
 *    size statistics of pre-shuffle partitions, this coordinator will determine the number of
 *    post-shuffle partitions and pack multiple pre-shuffle partitions with continuous indices
 *    to a single post-shuffle partition whenever necessary.
 *  - Finally, this coordinator will create post-shuffle [[ShuffledRowRDD]]s for all registered
 *    [[ShuffleExchangeExec]]s. So, when a [[ShuffleExchangeExec]] calls `postShuffleRDD`, this
 *    coordinator can lookup the corresponding [[RDD]].
 *
 * The strategy used to determine the number of post-shuffle partitions is described as follows.
 * To determine the number of post-shuffle partitions, we have a target input size for a
 * post-shuffle partition. Once we have size statistics of pre-shuffle partitions from stages
 * corresponding to the registered [[ShuffleExchangeExec]]s, we will do a pass of those statistics
 * and pack pre-shuffle partitions with continuous indices to a single post-shuffle partition until
 * adding another pre-shuffle partition would cause the size of a post-shuffle partition to be
 * greater than the target size.
 *
 * For example, we have two stages with the following pre-shuffle partition size statistics:
 * stage 1: [100 MB, 20 MB, 100 MB, 10MB, 30 MB]
 * stage 2: [10 MB,  10 MB, 70 MB,  5 MB, 5 MB]
 * assuming the target input size is 128 MB, we will have four post-shuffle partitions,
 * which are:
 *  - post-shuffle partition 0: pre-shuffle partition 0 (size 110 MB)
 *  - post-shuffle partition 1: pre-shuffle partition 1 (size 30 MB)
 *  - post-shuffle partition 2: pre-shuffle partition 2 (size 170 MB)
 *  - post-shuffle partition 3: pre-shuffle partition 3 and 4 (size 50 MB)
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
