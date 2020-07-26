package org.apache.spark.daslab.sql.execution.python

import org.apache.spark.daslab.sql.Column
import org.apache.spark.daslab.sql.engine.expressions.{Expression, PythonUDF}
import org.apache.spark.daslab.sql.types.DataType
//todo
import org.apache.spark.api.python.PythonFunction


/**
 * A user-defined Python function. This is used by the Python API.
 */
case class UserDefinedPythonFunction(
                                      name: String,
                                      func: PythonFunction,
                                      dataType: DataType,
                                      pythonEvalType: Int,
                                      udfDeterministic: Boolean) {

  def builder(e: Seq[Expression]): PythonUDF = {
    PythonUDF(name, func, dataType, e, pythonEvalType, udfDeterministic)
  }

  /** Returns a [[Column]] that will evaluate to calling this UDF with the given input. */
  def apply(exprs: Column*): Column = {
    val udf = builder(exprs.map(_.expr))
    Column(udf)
  }
}
