package org.apache.spark.daslab.sql.execution.python



import java.io.File

import scala.collection.mutable.ArrayBuffer


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.physical.{AllTuples, ClusteredDistribution, Distribution, Partitioning}
import org.apache.spark.daslab.sql.execution.{GroupedIterator, SparkPlan, UnaryExecNode}
import org.apache.spark.daslab.sql.execution.arrow.ArrowUtils
import org.apache.spark.daslab.sql.types.{DataType, StructField, StructType}

//todo
import org.apache.spark.{SparkEnv, TaskContext}
import org.apache.spark.api.python.{ChainedPythonFunctions, PythonEvalType}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.Utils

/**
 * Physical node for aggregation with group aggregate Pandas UDF.
 *
 * This plan works by sending the necessary (projected) input grouped data as Arrow record batches
 * to the python worker, the python worker invokes the UDF and sends the results to the executor,
 * finally the executor evaluates any post-aggregation expressions and join the result with the
 * grouped key.
 */
case class AggregateInPandasExec(
                                  groupingExpressions: Seq[NamedExpression],
                                  udfExpressions: Seq[PythonUDF],
                                  resultExpressions: Seq[NamedExpression],
                                  child: SparkPlan)
  extends UnaryExecNode {

  override val output: Seq[Attribute] = resultExpressions.map(_.toAttribute)

  override def outputPartitioning: Partitioning = child.outputPartitioning

  override def producedAttributes: AttributeSet = AttributeSet(output)

  override def requiredChildDistribution: Seq[Distribution] = {
    if (groupingExpressions.isEmpty) {
      AllTuples :: Nil
    } else {
      ClusteredDistribution(groupingExpressions) :: Nil
    }
  }

  private def collectFunctions(udf: PythonUDF): (ChainedPythonFunctions, Seq[Expression]) = {
    udf.children match {
      case Seq(u: PythonUDF) =>
        val (chained, children) = collectFunctions(u)
        (ChainedPythonFunctions(chained.funcs ++ Seq(udf.func)), children)
      case children =>
        // There should not be any other UDFs, or the children can't be evaluated directly.
        assert(children.forall(_.find(_.isInstanceOf[PythonUDF]).isEmpty))
        (ChainedPythonFunctions(Seq(udf.func)), udf.children)
    }
  }

  override def requiredChildOrdering: Seq[Seq[SortOrder]] =
    Seq(groupingExpressions.map(SortOrder(_, Ascending)))

  override protected def doExecute(): RDD[InternalRow] = {
    val inputRDD = child.execute()

    val sessionLocalTimeZone = conf.sessionLocalTimeZone
    val pythonRunnerConf = ArrowUtils.getPythonRunnerConfMap(conf)

    val (pyFuncs, inputs) = udfExpressions.map(collectFunctions).unzip

    // Filter child output attributes down to only those that are UDF inputs.
    // Also eliminate duplicate UDF inputs.
    val allInputs = new ArrayBuffer[Expression]
    val dataTypes = new ArrayBuffer[DataType]
    val argOffsets = inputs.map { input =>
      input.map { e =>
        if (allInputs.exists(_.semanticEquals(e))) {
          allInputs.indexWhere(_.semanticEquals(e))
        } else {
          allInputs += e
          dataTypes += e.dataType
          allInputs.length - 1
        }
      }.toArray
    }.toArray

    // Schema of input rows to the python runner
    val aggInputSchema = StructType(dataTypes.zipWithIndex.map { case (dt, i) =>
      StructField(s"_$i", dt)
    })

    inputRDD.mapPartitionsInternal { iter =>
      val prunedProj = UnsafeProjection.create(allInputs, child.output)

      val grouped = if (groupingExpressions.isEmpty) {
        // Use an empty unsafe row as a place holder for the grouping key
        Iterator((new UnsafeRow(), iter))
      } else {
        GroupedIterator(iter, groupingExpressions, child.output)
        }.map { case (key, rows) =>
        (key, rows.map(prunedProj))
      }

      val context = TaskContext.get()

      // The queue used to buffer input rows so we can drain it to
      // combine input with output from Python.
      val queue = HybridRowQueue(context.taskMemoryManager(),
        new File(Utils.getLocalDir(SparkEnv.get.conf)), groupingExpressions.length)
      context.addTaskCompletionListener[Unit] { _ =>
        queue.close()
      }

      // Add rows to queue to join later with the result.
      val projectedRowIter = grouped.map { case (groupingKey, rows) =>
        queue.add(groupingKey.asInstanceOf[UnsafeRow])
        rows
      }

      val columnarBatchIter = new ArrowPythonRunner(
        pyFuncs,
        PythonEvalType.SQL_GROUPED_AGG_PANDAS_UDF,
        argOffsets,
        aggInputSchema,
        sessionLocalTimeZone,
        pythonRunnerConf).compute(projectedRowIter, context.partitionId(), context)

      val joinedAttributes =
        groupingExpressions.map(_.toAttribute) ++ udfExpressions.map(_.resultAttribute)
      val joined = new JoinedRow
      val resultProj = UnsafeProjection.create(resultExpressions, joinedAttributes)

      columnarBatchIter.map(_.rowIterator.next()).map { aggOutputRow =>
        val leftRow = queue.remove()
        val joinedRow = joined(leftRow, aggOutputRow)
        resultProj(joinedRow)
      }
    }
  }
}
