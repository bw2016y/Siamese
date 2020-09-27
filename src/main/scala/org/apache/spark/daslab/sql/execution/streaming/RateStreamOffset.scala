package org.apache.spark.daslab.sql.execution.streaming


import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization

import org.apache.spark.daslab.sql.sources.v2

case class RateStreamOffset(partitionToValueAndRunTimeMs: Map[Int, ValueRunTimeMsPair])
  extends v2.reader.streaming.Offset {
  implicit val defaultFormats: DefaultFormats = DefaultFormats
  override val json = Serialization.write(partitionToValueAndRunTimeMs)
}


case class ValueRunTimeMsPair(value: Long, runTimeMs: Long)
