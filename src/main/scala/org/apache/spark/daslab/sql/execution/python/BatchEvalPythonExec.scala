package org.apache.spark.daslab.sql.execution.python



import scala.collection.JavaConverters._

import net.razorvine.pickle.{Pickler, Unpickler}


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.logical.{LogicalPlan, UnaryNode}
import org.apache.spark.daslab.sql.execution.SparkPlan
import org.apache.spark.daslab.sql.types.{StructField, StructType}

//todo
import org.apache.spark.TaskContext
import org.apache.spark.api.python.{ChainedPythonFunctions, PythonEvalType}

/**
 * A logical plan that evaluates a [[PythonUDF]]
 */
case class BatchEvalPython(udfs: Seq[PythonUDF], output: Seq[Attribute], child: LogicalPlan)
  extends UnaryNode

/**
 * A physical plan that evaluates a [[PythonUDF]]
 */
case class BatchEvalPythonExec(udfs: Seq[PythonUDF], output: Seq[Attribute], child: SparkPlan)
  extends EvalPythonExec(udfs, output, child) {

  protected override def evaluate(
                                   funcs: Seq[ChainedPythonFunctions],
                                   argOffsets: Array[Array[Int]],
                                   iter: Iterator[InternalRow],
                                   schema: StructType,
                                   context: TaskContext): Iterator[InternalRow] = {
    EvaluatePython.registerPicklers()  // register pickler for Row

    val dataTypes = schema.map(_.dataType)
    val needConversion = dataTypes.exists(EvaluatePython.needConversionInPython)

    // enable memo iff we serialize the row with schema (schema and class should be memorized)
    val pickle = new Pickler(needConversion)
    // Input iterator to Python: input rows are grouped so we send them in batches to Python.
    // For each row, add it to the queue.
    val inputIterator = iter.map { row =>
      if (needConversion) {
        EvaluatePython.toJava(row, schema)
      } else {
        // fast path for these types that does not need conversion in Python
        val fields = new Array[Any](row.numFields)
        var i = 0
        while (i < row.numFields) {
          val dt = dataTypes(i)
          fields(i) = EvaluatePython.toJava(row.get(i, dt), dt)
          i += 1
        }
        fields
      }
    }.grouped(100).map(x => pickle.dumps(x.toArray))

    // Output iterator for results from Python.
    val outputIterator = new PythonUDFRunner(funcs, PythonEvalType.SQL_BATCHED_UDF, argOffsets)
      .compute(inputIterator, context.partitionId(), context)

    val unpickle = new Unpickler
    val mutableRow = new GenericInternalRow(1)
    val resultType = if (udfs.length == 1) {
      udfs.head.dataType
    } else {
      StructType(udfs.map(u => StructField("", u.dataType, u.nullable)))
    }

    val fromJava = EvaluatePython.makeFromJava(resultType)

    outputIterator.flatMap { pickedResult =>
      val unpickledBatch = unpickle.loads(pickedResult)
      unpickledBatch.asInstanceOf[java.util.ArrayList[Any]].asScala
    }.map { result =>
      if (udfs.length == 1) {
        // fast path for single UDF
        mutableRow(0) = fromJava(result)
        mutableRow
      } else {
        fromJava(result).asInstanceOf[InternalRow]
      }
    }
  }
}
