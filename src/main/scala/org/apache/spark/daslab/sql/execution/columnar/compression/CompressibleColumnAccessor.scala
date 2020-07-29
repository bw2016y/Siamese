package org.apache.spark.daslab.sql.execution.columnar.compression


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.execution.columnar.{ColumnAccessor, NativeColumnAccessor}
import org.apache.spark.daslab.sql.execution.vectorized.WritableColumnVector
import org.apache.spark.daslab.sql.types.AtomicType

private[columnar] trait CompressibleColumnAccessor[T <: AtomicType] extends ColumnAccessor {
  this: NativeColumnAccessor[T] =>

  private var decoder: Decoder[T] = _

  abstract override protected def initialize(): Unit = {
    super.initialize()
    decoder = CompressionScheme(underlyingBuffer.getInt()).decoder(buffer, columnType)
  }

  abstract override def hasNext: Boolean = super.hasNext || decoder.hasNext

  override def extractSingle(row: InternalRow, ordinal: Int): Unit = {
    decoder.next(row, ordinal)
  }

  def decompress(columnVector: WritableColumnVector, capacity: Int): Unit =
    decoder.decompress(columnVector, capacity)
}
