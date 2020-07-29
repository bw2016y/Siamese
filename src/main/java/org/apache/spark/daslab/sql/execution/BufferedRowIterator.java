package org.apache.spark.daslab.sql.execution;


import java.io.IOException;
import java.util.LinkedList;

import scala.collection.Iterator;

//todo
import org.apache.spark.TaskContext;


import org.apache.spark.daslab.sql.engine.InternalRow;
import org.apache.spark.daslab.sql.engine.expressions.UnsafeRow;

/**
 * An iterator interface used to pull the output from generated function for multiple operators
 * (whole stage codegen).
 */
public abstract class BufferedRowIterator {
    protected LinkedList<InternalRow> currentRows = new LinkedList<>();
    // used when there is no column in output
    protected UnsafeRow unsafeRow = new UnsafeRow(0);
    private long startTimeNs = System.nanoTime();

    protected int partitionIndex = -1;

    public boolean hasNext() throws IOException {
        if (currentRows.isEmpty()) {
            processNext();
        }
        return !currentRows.isEmpty();
    }

    public InternalRow next() {
        return currentRows.remove();
    }

    /**
     * Returns the elapsed time since this object is created. This object represents a pipeline so
     * this is a measure of how long the pipeline has been running.
     */
    public long durationMs() {
        return (System.nanoTime() - startTimeNs) / (1000 * 1000);
    }

    /**
     * Initializes from array of iterators of InternalRow.
     */
    public abstract void init(int index, Iterator<InternalRow>[] iters);

    /*
     * Attributes of the following four methods are public. Thus, they can be also accessed from
     * methods in inner classes. See SPARK-23598
     */
    /**
     * Append a row to currentRows.
     */
    public void append(InternalRow row) {
        currentRows.add(row);
    }

    /**
     * Returns whether this iterator should stop fetching next row from [[CodegenSupport#inputRDDs]].
     *
     * If it returns true, the caller should exit the loop that [[InputAdapter]] generates.
     * This interface is mainly used to limit the number of input rows.
     */
    public boolean stopEarly() {
        return false;
    }

    /**
     * Returns whether `processNext()` should stop processing next row from `input` or not.
     *
     * If it returns true, the caller should exit the loop (return from processNext()).
     */
    public boolean shouldStop() {
        return !currentRows.isEmpty();
    }

    /**
     * Increase the peak execution memory for current task.
     */
    public void incPeakExecutionMemory(long size) {
        TaskContext.get().taskMetrics().incPeakExecutionMemory(size);
    }

    /**
     * Processes the input until have a row as output (currentRow).
     *
     * After it's called, if currentRow is still null, it means no more rows left.
     */
    protected abstract void processNext() throws IOException;
}
