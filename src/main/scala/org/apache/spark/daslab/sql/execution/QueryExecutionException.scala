package org.apache.spark.daslab.sql.execution



class QueryExecutionException(message: String, cause: Throwable = null)
  extends Exception(message, cause)
