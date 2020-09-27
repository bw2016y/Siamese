package org.apache.spark.daslab.sql.execution


import java.util.Collections

import scala.collection.JavaConverters._


import org.apache.spark.daslab.sql._
import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.Attribute
import org.apache.spark.daslab.sql.engine.expressions.codegen.{CodeFormatter, CodegenContext, ExprCode}
import org.apache.spark.daslab.sql.engine.plans.physical.Partitioning
import org.apache.spark.daslab.sql.engine.trees.TreeNodeRef
import org.apache.spark.daslab.sql.execution.streaming.{StreamExecution, StreamingQueryWrapper}
import org.apache.spark.daslab.sql.execution.streaming.continuous.WriteToContinuousDataSourceExec
import org.apache.spark.daslab.sql.streaming.StreamingQuery

//todo
import org.apache.spark.util.{AccumulatorV2, LongAccumulator}
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD

/**
 * Contains methods for debugging query execution.
 *
 * Usage:
 * {{{
 *   import org.apache.spark.daslab.sql.execution.debug._
 *   sql("SELECT 1").debug()
 *   sql("SELECT 1").debugCodegen()
 * }}}
 *
 * or for streaming case (structured streaming):
 * {{{
 *   import org.apache.spark.daslab.sql.execution.debug._
 *   val query = df.writeStream.<...>.start()
 *   query.debugCodegen()
 * }}}
 *
 * Note that debug in structured streaming is not supported, because it doesn't make sense for
 * streaming to execute batch once while main query is running concurrently.
 */
package object debug {

  /** Helper function to evade the println() linter. */
  private def debugPrint(msg: String): Unit = {
    // scalastyle:off println
    println(msg)
    // scalastyle:on println
  }

  /**
   * Get WholeStageCodegenExec subtrees and the codegen in a query plan into one String
   *
   * @param plan the query plan for codegen
   * @return single String containing all WholeStageCodegen subtrees and corresponding codegen
   */
  def codegenString(plan: SparkPlan): String = {
    val codegenSeq = codegenStringSeq(plan)
    var output = s"Found ${codegenSeq.size} WholeStageCodegen subtrees.\n"
    for (((subtree, code), i) <- codegenSeq.zipWithIndex) {
      output += s"== Subtree ${i + 1} / ${codegenSeq.size} ==\n"
      output += subtree
      output += "\nGenerated code:\n"
      output += s"${code}\n"
    }
    output
  }

  /**
   * Get WholeStageCodegenExec subtrees and the codegen in a query plan
   *
   * @param plan the query plan for codegen
   * @return Sequence of WholeStageCodegen subtrees and corresponding codegen
   */
  def codegenStringSeq(plan: SparkPlan): Seq[(String, String)] = {
    val codegenSubtrees = new collection.mutable.HashSet[WholeStageCodegenExec]()
    plan transform {
      case s: WholeStageCodegenExec =>
        codegenSubtrees += s
        s
      case s => s
    }
    codegenSubtrees.toSeq.map { subtree =>
      val (_, source) = subtree.doCodeGen()
      (subtree.toString, CodeFormatter.format(source))
    }
  }

  /**
   * Get WholeStageCodegenExec subtrees and the codegen in a query plan into one String
   *
   * @param query the streaming query for codegen
   * @return single String containing all WholeStageCodegen subtrees and corresponding codegen
   */
  def codegenString(query: StreamingQuery): String = {
    val w = asStreamExecution(query)
    if (w.lastExecution != null) {
      codegenString(w.lastExecution.executedPlan)
    } else {
      "No physical plan. Waiting for data."
    }
  }

  /**
   * Get WholeStageCodegenExec subtrees and the codegen in a query plan
   *
   * @param query the streaming query for codegen
   * @return Sequence of WholeStageCodegen subtrees and corresponding codegen
   */
  def codegenStringSeq(query: StreamingQuery): Seq[(String, String)] = {
    val w = asStreamExecution(query)
    if (w.lastExecution != null) {
      codegenStringSeq(w.lastExecution.executedPlan)
    } else {
      Seq.empty
    }
  }

  private def asStreamExecution(query: StreamingQuery): StreamExecution = query match {
    case wrapper: StreamingQueryWrapper => wrapper.streamingQuery
    case q: StreamExecution => q
    case _ => throw new IllegalArgumentException("Parameter should be an instance of " +
      "StreamExecution!")
  }

  /**
   * Augments [[Dataset]]s with debug methods.
   */
  implicit class DebugQuery(query: Dataset[_]) extends Logging {
    def debug(): Unit = {
      val visited = new collection.mutable.HashSet[TreeNodeRef]()
      val debugPlan = query.queryExecution.executedPlan transform {
        case s: SparkPlan if !visited.contains(new TreeNodeRef(s)) =>
          visited += new TreeNodeRef(s)
          DebugExec(s)
      }
      debugPrint(s"Results returned: ${debugPlan.execute().count()}")
      debugPlan.foreach {
        case d: DebugExec => d.dumpStats()
        case _ =>
      }
    }

    /**
     * Prints to stdout all the generated code found in this plan (i.e. the output of each
     * WholeStageCodegen subtree).
     */
    def debugCodegen(): Unit = {
      debugPrint(codegenString(query.queryExecution.executedPlan))
    }
  }

  implicit class DebugStreamQuery(query: StreamingQuery) extends Logging {
    def debugCodegen(): Unit = {
      debugPrint(codegenString(query))
    }
  }

  case class DebugExec(child: SparkPlan) extends UnaryExecNode with CodegenSupport {
    def output: Seq[Attribute] = child.output

    class SetAccumulator[T] extends AccumulatorV2[T, java.util.Set[T]] {
      private val _set = Collections.synchronizedSet(new java.util.HashSet[T]())
      override def isZero: Boolean = _set.isEmpty
      override def copy(): AccumulatorV2[T, java.util.Set[T]] = {
        val newAcc = new SetAccumulator[T]()
        newAcc._set.addAll(_set)
        newAcc
      }
      override def reset(): Unit = _set.clear()
      override def add(v: T): Unit = _set.add(v)
      override def merge(other: AccumulatorV2[T, java.util.Set[T]]): Unit = {
        _set.addAll(other.value)
      }
      override def value: java.util.Set[T] = _set
    }

    /**
     * A collection of metrics for each column of output.
     */
    case class ColumnMetrics() {
      val elementTypes = new SetAccumulator[String]
      sparkContext.register(elementTypes)
    }

    val tupleCount: LongAccumulator = sparkContext.longAccumulator

    val numColumns: Int = child.output.size
    val columnStats: Array[ColumnMetrics] = Array.fill(child.output.size)(new ColumnMetrics())

    def dumpStats(): Unit = {
      debugPrint(s"== ${child.simpleString} ==")
      debugPrint(s"Tuples output: ${tupleCount.value}")
      child.output.zip(columnStats).foreach { case (attr, metric) =>
        // This is called on driver. All accumulator updates have a fixed value. So it's safe to use
        // `asScala` which accesses the internal values using `java.util.Iterator`.
        val actualDataTypes = metric.elementTypes.value.asScala.mkString("{", ",", "}")
        debugPrint(s" ${attr.name} ${attr.dataType}: $actualDataTypes")
      }
    }

    protected override def doExecute(): RDD[InternalRow] = {
      child.execute().mapPartitions { iter =>
        new Iterator[InternalRow] {
          def hasNext: Boolean = iter.hasNext

          def next(): InternalRow = {
            val currentRow = iter.next()
            tupleCount.add(1)
            var i = 0
            while (i < numColumns) {
              val value = currentRow.get(i, output(i).dataType)
              if (value != null) {
                columnStats(i).elementTypes.add(value.getClass.getName)
              }
              i += 1
            }
            currentRow
          }
        }
      }
    }

    override def outputPartitioning: Partitioning = child.outputPartitioning

    override def inputRDDs(): Seq[RDD[InternalRow]] = {
      child.asInstanceOf[CodegenSupport].inputRDDs()
    }

    override def doProduce(ctx: CodegenContext): String = {
      child.asInstanceOf[CodegenSupport].produce(ctx, this)
    }

    override def doConsume(ctx: CodegenContext, input: Seq[ExprCode], row: ExprCode): String = {
      consume(ctx, input)
    }
  }
}
