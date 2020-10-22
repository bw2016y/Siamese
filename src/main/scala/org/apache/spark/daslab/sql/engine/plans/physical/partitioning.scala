package org.apache.spark.daslab.sql.engine.plans.physical


import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.types.{DataType, IntegerType}


/**
  *  当一个查询在许多机器上并行的执行时，指明这些相同表达式所代表的tuple将会如何分布
  *  具体来讲，Distribution可以用来描述以下两种不同粒度的数据（物理）特征
  *
  *   1. Inter-node （节点间）数据分区信息：
  *      在集群的不同物理节点上tuples是如何分布的
  *      知道这些特性，可以用来判断某些算子例如（Aggregate）能否进行局部计算（Partial operation），避免
  *      全局操作
  *   2. Intra-partition ordering of data(分区内数据排序信息): 单个分区（single partition）内数据是如何的分布
  *
  */
sealed trait Distribution {
  /**
   * The required number of partitions for this distribution. If it's None, then any number of
   * partitions is allowed for this distribution.
   */
  def requiredNumPartitions: Option[Int]

  /**
   * Creates a default partitioning for this distribution, which can satisfy this distribution while
   * matching the given number of partitions.
   */
  def createPartitioning(numPartitions: Int): Partitioning
}

/**
  *  分区内的数据元组没有保证（no promises），即未指定分布，无需确定的位置关系。
 */
case object UnspecifiedDistribution extends Distribution {
  override def requiredNumPartitions: Option[Int] = None

  override def createPartitioning(numPartitions: Int): Partitioning = {
    throw new IllegalStateException("UnspecifiedDistribution does not have default partitioning.")
  }
}

/**
  *  只有一个分区，所有的数据元组存放在一起（Co-located）的分布情况。
  *   例如Global Limit算子在选取前K条数据的时候，这个算子的requiredChildDistribution得到的列表就是AllTuples，
  *   表示执行该算子需要全部的数据参与。
 */
case object AllTuples extends Distribution {
  override def requiredNumPartitions: Option[Int] = Some(1)

  override def createPartitioning(numPartitions: Int): Partitioning = {
    assert(numPartitions == 1, "The default partitioning of AllTuples can only have 1 partition.")
    SinglePartition
  }
}


/**
  *   表示在聚类表达式计算后（clustering，这里起到了哈希函数的效果）有相同值的tuple需要存放在一起（Co-located）
  *   如果是多个分区的情况，则相同的数据会被存放在同一个分区中
  *   如果只能是单个分区，则相同的数据会在分区内连续存放
  *
  *    SortMerge类型的Join操作中，分布的需要就是[ClusteredDistribution(leftKeys),ClusteredDistribution(rightKeys)]
  *    左表数据需要根据leftKeys表达式计算分区，右表同理
  *
  * @param clustering   Seq[Expression]
  * @param requiredNumPartitions
  */
case class ClusteredDistribution(
                                  clustering: Seq[Expression],
                                  requiredNumPartitions: Option[Int] = None) extends Distribution {
  require(
    clustering != Nil,
    "The clustering expressions of a ClusteredDistribution should not be Nil. " +
      "An AllTuples should be used to represent a distribution that only has " +
      "a single partition.")

  override def createPartitioning(numPartitions: Int): Partitioning = {
    assert(requiredNumPartitions.isEmpty || requiredNumPartitions.get == numPartitions,
      s"This ClusteredDistribution requires ${requiredNumPartitions.get} partitions, but " +
        s"the actual number of partitions is $numPartitions.")
    HashPartitioning(clustering, numPartitions)
  }
}

/**
 * Represents data where tuples have been clustered according to the hash of the given
 * `expressions`. The hash function is defined as `HashPartitioning.partitionIdExpression`, so only
 * [[HashPartitioning]] can satisfy this distribution.
 *
 * This is a strictly stronger guarantee than [[ClusteredDistribution]]. Given a tuple and the
 * number of partitions, this distribution strictly requires which partition the tuple should be in.
 */
case class HashClusteredDistribution(
                                      expressions: Seq[Expression],
                                      requiredNumPartitions: Option[Int] = None) extends Distribution {
  require(
    expressions != Nil,
    "The expressions for hash of a HashClusteredDistribution should not be Nil. " +
      "An AllTuples should be used to represent a distribution that only has " +
      "a single partition.")

  override def createPartitioning(numPartitions: Int): Partitioning = {
    assert(requiredNumPartitions.isEmpty || requiredNumPartitions.get == numPartitions,
      s"This HashClusteredDistribution requires ${requiredNumPartitions.get} partitions, but " +
        s"the actual number of partitions is $numPartitions.")
    HashPartitioning(expressions, numPartitions)
  }
}


/**
  *  表示根据ordering排序表达式计算的结果对tuple进行排序。
  *
  *  这个的保证（guarantee）严格的强于[[ClusteredDistribution]]
  *  这个分布可以保证具有相同排序表达式计算结果value的数据是连续存放的，而且绝不会被划分到两个不同的分区（一定是在相同的分区中）
  *
  *
  * @param ordering
  */
case class OrderedDistribution(ordering: Seq[SortOrder]) extends Distribution {
  require(
    ordering != Nil,
    "The ordering expressions of an OrderedDistribution should not be Nil. " +
      "An AllTuples should be used to represent a distribution that only has " +
      "a single partition.")

  override def requiredNumPartitions: Option[Int] = None

  override def createPartitioning(numPartitions: Int): Partitioning = {
    RangePartitioning(ordering, numPartitions)
  }
}


/**
  *        广播分布， 代表数据会被广播到所有节点上，这个模式下所有的tuples被转换为另一种数据结构也是很常见的
  *
  *        如果是Broadcast类型的Join操作，假设左表做广播，那么requiredChildDistribution得到的分布要求就是
  *        [BroadcastDrstribution(mode),UnspecifiedDistribution],表示左表为广播分布
  *
  * @param mode 广播模式：  1. IdentityBroadcastMode 原始数据模式
  *                         2. HashedRelationBroadcastMode 原始数据转换为HashedRelation对象
  */
case class BroadcastDistribution(mode: BroadcastMode) extends Distribution {
  override def requiredNumPartitions: Option[Int] = Some(1)

  override def createPartitioning(numPartitions: Int): Partitioning = {
    assert(numPartitions == 1,
      "The default partitioning of BroadcastDistribution can only have 1 partition.")
    BroadcastPartitioning(mode)
  }
}


/**
  *  描述一个物理算子的数据输出是如何分区的，具体包括子partitionging之间，目标partitioning和Distribution之间的关系
  *
  *   主要包括了两个属性
  *   1. 分区的数量
  *   2. 判断它是否可以满足某个给定的分布
  *
  */
trait Partitioning {
  /** Returns the number of partitions that the data is split across */
  // 指定该SparkPlan输出RDD的分区数目
  val numPartitions: Int


  /**
    *  当前的partitioning操作提供的(guarentee)能否得到所需的数据分布(required[[Distribution]])
    *  当不满足的时候就是false，一般需要进行repartition操作，对数据进行重新组织;否则返回true
    *
    *  满足的情况： 当前的数据集不需要re-partitioned为要求的分布（但是单个partition中的数据需要重新组织（reorganized））
    *
    *  如果numPartitions不能匹配[[Distribution.requiredNumPartitions]]则该[[Partitioning ]]就定义为不能满足该[[Distribution]]
    * @param required
    * @return
    */
  final def satisfies(required: Distribution): Boolean = {
    required.requiredNumPartitions.forall(_ == numPartitions) && satisfies0(required)
  }

  /**
   * The actual method that defines whether this [[Partitioning]] can satisfy the given
   * [[Distribution]], after the `numPartitions` check.
   *
   * By default a [[Partitioning]] can satisfy [[UnspecifiedDistribution]], and [[AllTuples]] if
   * the [[Partitioning]] only have one partition. Implementations can also overwrite this method
   * with special logic.
   */
  protected def satisfies0(required: Distribution): Boolean = required match {
    case UnspecifiedDistribution => true
    case AllTuples => numPartitions == 1
    case _ => false
  }
}
// 不进行分区
case class UnknownPartitioning(numPartitions: Int) extends Partitioning


/**
  *  表示一种数据平均分配到各个分区的分区策略
  *  一般来说是从随机的某个target partition开始，然后以轮询的方式在1-numPartitions内分配
  *  DataFrame.repartition()算子就使用了这种分区实现
  * @param numPartitions
  */
case class RoundRobinPartitioning(numPartitions: Int) extends Partitioning

case object SinglePartition extends Partitioning {
  val numPartitions = 1

  override def satisfies0(required: Distribution): Boolean = required match {
    case _: BroadcastDistribution => false
    case _ => true
  }
}

/**
  *  基于哈希的分区方式
 * Represents a partitioning where rows are split up across partitions based on the hash
 * of `expressions`.  All rows where `expressions` evaluate to the same values are guaranteed to be
 * in the same partition.
 */
case class HashPartitioning(expressions: Seq[Expression], numPartitions: Int)
  extends Expression with Partitioning with Unevaluable {

  override def children: Seq[Expression] = expressions
  override def nullable: Boolean = false
  override def dataType: DataType = IntegerType

  override def satisfies0(required: Distribution): Boolean = {
    super.satisfies0(required) || {
      required match {
        case h: HashClusteredDistribution =>
          expressions.length == h.expressions.length && expressions.zip(h.expressions).forall {
            case (l, r) => l.semanticEquals(r)
          }
        case ClusteredDistribution(requiredClustering, _) =>
          expressions.forall(x => requiredClustering.exists(_.semanticEquals(x)))
        case _ => false
      }
    }
  }

  /**
   * Returns an expression that will produce a valid partition ID(i.e. non-negative and is less
   * than numPartitions) based on hashing expressions.
   */
  def partitionIdExpression: Expression = Pmod(new Murmur3Hash(expressions), Literal(numPartitions))
}

/**
  *  基于范围的分区方式
 * Represents a partitioning where rows are split across partitions based on some total ordering of
 * the expressions specified in `ordering`.  When data is partitioned in this manner the following
 * two conditions are guaranteed to hold:
 *  - All row where the expressions in `ordering` evaluate to the same values will be in the same
 *    partition.
 *  - Each partition will have a `min` and `max` row, relative to the given ordering.  All rows
 *    that are in between `min` and `max` in this `ordering` will reside in this partition.
 *
 * This class extends expression primarily so that transformations over expression will descend
 * into its child.
 */
case class RangePartitioning(ordering: Seq[SortOrder], numPartitions: Int)
  extends Expression with Partitioning with Unevaluable {

  override def children: Seq[SortOrder] = ordering
  override def nullable: Boolean = false
  override def dataType: DataType = IntegerType

  override def satisfies0(required: Distribution): Boolean = {
    super.satisfies0(required) || {
      required match {
        case OrderedDistribution(requiredOrdering) =>
          val minSize = Seq(requiredOrdering.size, ordering.size).min
          requiredOrdering.take(minSize) == ordering.take(minSize)
        case ClusteredDistribution(requiredClustering, _) =>
          ordering.map(_.child).forall(x => requiredClustering.exists(_.semanticEquals(x)))
        case _ => false
      }
    }
  }
}

/**
  *  分区方式的集合，描述物理算子的输出
 * A collection of [[Partitioning]]s that can be used to describe the partitioning
 * scheme of the output of a physical operator. It is usually used for an operator
 * that has multiple children. In this case, a [[Partitioning]] in this collection
 * describes how this operator's output is partitioned based on expressions from
 * a child. For example, for a Join operator on two tables `A` and `B`
 * with a join condition `A.key1 = B.key2`, assuming we use HashPartitioning schema,
 * there are two [[Partitioning]]s can be used to describe how the output of
 * this Join operator is partitioned, which are `HashPartitioning(A.key1)` and
 * `HashPartitioning(B.key2)`. It is also worth noting that `partitionings`
 * in this collection do not need to be equivalent, which is useful for
 * Outer Join operators.
 */
case class PartitioningCollection(partitionings: Seq[Partitioning])
  extends Expression with Partitioning with Unevaluable {

  require(
    partitionings.map(_.numPartitions).distinct.length == 1,
    s"PartitioningCollection requires all of its partitionings have the same numPartitions.")

  override def children: Seq[Expression] = partitionings.collect {
    case expr: Expression => expr
  }

  override def nullable: Boolean = false

  override def dataType: DataType = IntegerType

  override val numPartitions = partitionings.map(_.numPartitions).distinct.head

  /**
   * Returns true if any `partitioning` of this collection satisfies the given
   * [[Distribution]].
   */
  override def satisfies0(required: Distribution): Boolean =
    partitionings.exists(_.satisfies(required))

  override def toString: String = {
    partitionings.map(_.toString).mkString("(", " or ", ")")
  }
}

/**
 * Represents a partitioning where rows are collected, transformed and broadcasted to each
 * node in the cluster.
 */
case class BroadcastPartitioning(mode: BroadcastMode) extends Partitioning {
  override val numPartitions: Int = 1

  override def satisfies0(required: Distribution): Boolean = required match {
    case BroadcastDistribution(m) if m == mode => true
    case _ => false
  }
}
