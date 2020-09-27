package org.apache.spark.daslab.sql.execution.r

//todo
import org.apache.spark.api.r._
import org.apache.spark.broadcast.Broadcast


import org.apache.spark.daslab.sql.Row
import org.apache.spark.daslab.sql.api.r.SQLUtils._
import org.apache.spark.daslab.sql.types.StructType

/**
 * A function wrapper that applies the given R function to each partition.
 */
case class MapPartitionsRWrapper(
                                  func: Array[Byte],
                                  packageNames: Array[Byte],
                                  broadcastVars: Array[Broadcast[Object]],
                                  inputSchema: StructType,
                                  outputSchema: StructType) extends (Iterator[Any] => Iterator[Any]) {
  def apply(iter: Iterator[Any]): Iterator[Any] = {
    // If the content of current DataFrame is serialized R data?
    val isSerializedRData = inputSchema == SERIALIZED_R_DATA_SCHEMA

    val (newIter, deserializer, colNames) =
      if (!isSerializedRData) {
        // Serialize each row into a byte array that can be deserialized in the R worker
        (iter.asInstanceOf[Iterator[Row]].map {row => rowToRBytes(row)},
          SerializationFormats.ROW, inputSchema.fieldNames)
      } else {
        (iter.asInstanceOf[Iterator[Row]].map { row => row(0) }, SerializationFormats.BYTE, null)
      }

    val serializer = if (outputSchema != SERIALIZED_R_DATA_SCHEMA) {
      SerializationFormats.ROW
    } else {
      SerializationFormats.BYTE
    }

    val runner = new RRunner[Array[Byte]](
      func, deserializer, serializer, packageNames, broadcastVars,
      isDataFrame = true, colNames = colNames, mode = RRunnerModes.DATAFRAME_DAPPLY)
    // Partition index is ignored. Dataset has no support for mapPartitionsWithIndex.
    val outputIter = runner.compute(newIter, -1)

    if (serializer == SerializationFormats.ROW) {
      outputIter.map { bytes => bytesToRow(bytes, outputSchema) }
    } else {
      outputIter.map { bytes => Row.fromSeq(Seq(bytes)) }
    }
  }
}
