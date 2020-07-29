package org.apache.spark.daslab.sql.execution.python


import scala.collection.JavaConverters._

//todo
import org.apache.spark.TaskContext
import org.apache.spark.api.python.{ChainedPythonFunctions, PythonEvalType}


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.logical.{LogicalPlan, UnaryNode}
import org.apache.spark.daslab.sql.execution.SparkPlan
import org.apache.spark.daslab.sql.execution.arrow.ArrowUtils
import org.apache.spark.daslab.sql.types.StructType

/**
 * Grouped a iterator into batches.
 * This is similar to iter.grouped but returns Iterator[T] instead of Seq[T].
 * This is necessary because sometimes we cannot hold reference of input rows
 * because the some input rows are mutable and can be reused.
 */
private class BatchIterator[T](iter: Iterator[T], batchSize: Int)
  extends Iterator[Iterator[T]] {

  override def hasNext: Boolean = iter.hasNext

  override def next(): Iterator[T] = {
    new Iterator[T] {
      var count = 0

      override def hasNext: Boolean = iter.hasNext && count < batchSize

      override def next(): T = {
        if (!hasNext) {
          Iterator.empty.next()
        } else {
          count += 1
          iter.next()
        }
      }
    }
  }
}

/**
 * A logical plan that evaluates a [[PythonUDF]].
 */
case class ArrowEvalPython(udfs: Seq[PythonUDF], output: Seq[Attribute], child: LogicalPlan)
  extends UnaryNode

/**
 * A physical plan that evaluates a [[PythonUDF]].
 */
case class ArrowEvalPythonExec(udfs: Seq[PythonUDF], output: Seq[Attribute], child: SparkPlan)
  extends EvalPythonExec(udfs, output, child) {

  private val batchSize = conf.arrowMaxRecordsPerBatch
  private val sessionLocalTimeZone = conf.sessionLocalTimeZone
  private val pythonRunnerConf = ArrowUtils.getPythonRunnerConfMap(conf)

  protected override def evaluate(
                                   funcs: Seq[ChainedPythonFunctions],
                                   argOffsets: Array[Array[Int]],
                                   iter: Iterator[InternalRow],
                                   schema: StructType,
                                   context: TaskContext): Iterator[InternalRow] = {

    val outputTypes = output.drop(child.output.length).map(_.dataType)

    // DO NOT use iter.grouped(). See BatchIterator.
    val batchIter = if (batchSize > 0) new BatchIterator(iter, batchSize) else Iterator(iter)

    val columnarBatchIter = new ArrowPythonRunner(
      funcs,
      PythonEvalType.SQL_SCALAR_PANDAS_UDF,
      argOffsets,
      schema,
      sessionLocalTimeZone,
      pythonRunnerConf).compute(batchIter, context.partitionId(), context)

    new Iterator[InternalRow] {

      private var currentIter = if (columnarBatchIter.hasNext) {
        val batch = columnarBatchIter.next()
        val actualDataTypes = (0 until batch.numCols()).map(i => batch.column(i).dataType())
        assert(outputTypes == actualDataTypes, "Invalid schema from pandas_udf: " +
          s"expected ${outputTypes.mkString(", ")}, got ${actualDataTypes.mkString(", ")}")
        batch.rowIterator.asScala
      } else {
        Iterator.empty
      }

      override def hasNext: Boolean = currentIter.hasNext || {
        if (columnarBatchIter.hasNext) {
          currentIter = columnarBatchIter.next().rowIterator.asScala
          hasNext
        } else {
          false
        }
      }

      override def next(): InternalRow = currentIter.next()
    }
  }
}
