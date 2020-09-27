package org.apache.spark.daslab.sql.execution.datasources.json


import org.apache.spark.daslab.sql.Dataset
import org.apache.spark.daslab.sql.engine.json.JSONOptions

//todo
import org.apache.spark.input.PortableDataStream
import org.apache.spark.rdd.RDD

object JsonUtils {
  /**
   * Sample JSON dataset as configured by `samplingRatio`.
   */
  def sample(json: Dataset[String], options: JSONOptions): Dataset[String] = {
    require(options.samplingRatio > 0,
      s"samplingRatio (${options.samplingRatio}) should be greater than 0")
    if (options.samplingRatio > 0.99) {
      json
    } else {
      json.sample(withReplacement = false, options.samplingRatio, 1)
    }
  }

  /**
   * Sample JSON RDD as configured by `samplingRatio`.
   */
  def sample(json: RDD[PortableDataStream], options: JSONOptions): RDD[PortableDataStream] = {
    require(options.samplingRatio > 0,
      s"samplingRatio (${options.samplingRatio}) should be greater than 0")
    if (options.samplingRatio > 0.99) {
      json
    } else {
      json.sample(withReplacement = false, options.samplingRatio, 1)
    }
  }
}
