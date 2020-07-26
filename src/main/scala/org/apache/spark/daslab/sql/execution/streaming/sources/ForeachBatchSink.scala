package org.apache.spark.daslab.sql.execution.streaming.sources


import org.apache.spark.daslab.sql._
import org.apache.spark.daslab.sql.engine.encoders.ExpressionEncoder
import org.apache.spark.daslab.sql.execution.streaming.Sink
import org.apache.spark.daslab.sql.streaming.DataStreamWriter

//todo
import org.apache.spark.api.python.PythonException


class ForeachBatchSink[T](batchWriter: (Dataset[T], Long) => Unit, encoder: ExpressionEncoder[T])
  extends Sink {

  override def addBatch(batchId: Long, data: DataFrame): Unit = {
    val resolvedEncoder = encoder.resolveAndBind(
      data.logicalPlan.output,
      data.sparkSession.sessionState.analyzer)
    val rdd = data.queryExecution.toRdd.map[T](resolvedEncoder.fromRow)(encoder.clsTag)
    val ds = data.sparkSession.createDataset(rdd)(encoder)
    batchWriter(ds, batchId)
  }

  override def toString(): String = "ForeachBatchSink"
}


/**
 * Interface that is meant to be extended by Python classes via Py4J.
 * Py4J allows Python classes to implement Java interfaces so that the JVM can call back
 * Python objects. In this case, this allows the user-defined Python `foreachBatch` function
 * to be called from JVM when the query is active.
 * */
trait PythonForeachBatchFunction {
  /** Call the Python implementation of this function */
  def call(batchDF: DataFrame, batchId: Long): Unit
}

object PythonForeachBatchHelper {
  def callForeachBatch(dsw: DataStreamWriter[Row], pythonFunc: PythonForeachBatchFunction): Unit = {
    dsw.foreachBatch(pythonFunc.call _)
  }
}

