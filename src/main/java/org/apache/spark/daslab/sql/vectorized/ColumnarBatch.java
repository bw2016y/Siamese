package org.apache.spark.daslab.sql.vectorized;


import java.util.*;


//todo
import org.apache.spark.annotation.InterfaceStability;


import org.apache.spark.daslab.sql.engine.InternalRow;
import org.apache.spark.daslab.sql.execution.vectorized.MutableColumnarRow;

/**
 * This class wraps multiple ColumnVectors as a row-wise table. It provides a row view of this
 * batch so that Spark can access the data row by row. Instance of it is meant to be reused during
 * the entire data loading process.
 */
@InterfaceStability.Evolving
public final class ColumnarBatch {
    private int numRows;
    private final ColumnVector[] columns;

    // Staging row returned from `getRow`.
    private final MutableColumnarRow row;

    /**
     * Called to close all the columns in this batch. It is not valid to access the data after
     * calling this. This must be called at the end to clean up memory allocations.
     */
    public void close() {
        for (ColumnVector c: columns) {
            c.close();
        }
    }

    /**
     * Returns an iterator over the rows in this batch.
     */
    public Iterator<InternalRow> rowIterator() {
        final int maxRows = numRows;
        final MutableColumnarRow row = new MutableColumnarRow(columns);
        return new Iterator<InternalRow>() {
            int rowId = 0;

            @Override
            public boolean hasNext() {
                return rowId < maxRows;
            }

            @Override
            public InternalRow next() {
                if (rowId >= maxRows) {
                    throw new NoSuchElementException();
                }
                row.rowId = rowId++;
                return row;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Sets the number of rows in this batch.
     */
    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    /**
     * Returns the number of columns that make up this batch.
     */
    public int numCols() { return columns.length; }

    /**
     * Returns the number of rows for read, including filtered rows.
     */
    public int numRows() { return numRows; }

    /**
     * Returns the column at `ordinal`.
     */
    public ColumnVector column(int ordinal) { return columns[ordinal]; }

    /**
     * Returns the row in this batch at `rowId`. Returned row is reused across calls.
     */
    public InternalRow getRow(int rowId) {
        assert(rowId >= 0 && rowId < numRows);
        row.rowId = rowId;
        return row;
    }

    public ColumnarBatch(ColumnVector[] columns) {
        this.columns = columns;
        this.row = new MutableColumnarRow(columns);
    }
}
