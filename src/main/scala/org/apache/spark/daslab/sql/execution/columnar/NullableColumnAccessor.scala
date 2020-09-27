package org.apache.spark.daslab.sql.execution.columnar



import java.nio.{ByteBuffer, ByteOrder}

import org.apache.spark.daslab.sql.engine.InternalRow

private[columnar] trait NullableColumnAccessor extends ColumnAccessor {
  private var nullsBuffer: ByteBuffer = _
  private var nullCount: Int = _
  private var seenNulls: Int = 0

  private var nextNullIndex: Int = _
  private var pos: Int = 0

  abstract override protected def initialize(): Unit = {
    nullsBuffer = underlyingBuffer.duplicate().order(ByteOrder.nativeOrder())
    nullCount = ByteBufferHelper.getInt(nullsBuffer)
    nextNullIndex = if (nullCount > 0) ByteBufferHelper.getInt(nullsBuffer) else -1
    pos = 0

    underlyingBuffer.position(underlyingBuffer.position() + 4 + nullCount * 4)
    super.initialize()
  }

  abstract override def extractTo(row: InternalRow, ordinal: Int): Unit = {
    if (pos == nextNullIndex) {
      seenNulls += 1

      if (seenNulls < nullCount) {
        nextNullIndex = ByteBufferHelper.getInt(nullsBuffer)
      }

      row.setNullAt(ordinal)
    } else {
      super.extractTo(row, ordinal)
    }

    pos += 1
  }

  abstract override def hasNext: Boolean = seenNulls < nullCount || super.hasNext
}
