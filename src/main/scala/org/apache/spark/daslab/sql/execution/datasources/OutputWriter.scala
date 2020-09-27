package org.apache.spark.daslab.sql.execution.datasources


import org.apache.hadoop.mapreduce.TaskAttemptContext



import org.apache.spark.daslab.sql.Row
import org.apache.spark.daslab.sql.engine.{CatalystTypeConverters, InternalRow}
import org.apache.spark.daslab.sql.types.StructType


/**
 * A factory that produces [[OutputWriter]]s.  A new [[OutputWriterFactory]] is created on driver
 * side for each write job issued when writing to a [[HadoopFsRelation]], and then gets serialized
 * to executor side to create actual [[OutputWriter]]s on the fly.
 */
abstract class OutputWriterFactory extends Serializable {

  /** Returns the file extension to be used when writing files out. */
  def getFileExtension(context: TaskAttemptContext): String

  /**
   * When writing to a [[HadoopFsRelation]], this method gets called by each task on executor side
   * to instantiate new [[OutputWriter]]s.
   *
   * @param path Path to write the file.
   * @param dataSchema Schema of the rows to be written. Partition columns are not included in the
   *        schema if the relation being written is partitioned.
   * @param context The Hadoop MapReduce task context.
   */
  def newInstance(
                   path: String,
                   dataSchema: StructType,
                   context: TaskAttemptContext): OutputWriter
}


/**
 * [[OutputWriter]] is used together with [[HadoopFsRelation]] for persisting rows to the
 * underlying file system.  Subclasses of [[OutputWriter]] must provide a zero-argument constructor.
 * An [[OutputWriter]] instance is created and initialized when a new output file is opened on
 * executor side.  This instance is used to persist rows to this single output file.
 */
abstract class OutputWriter {
  /**
   * Persists a single row.  Invoked on the executor side.  When writing to dynamically partitioned
   * tables, dynamic partition columns are not included in rows to be written.
   */
  def write(row: InternalRow): Unit

  /**
   * Closes the [[OutputWriter]]. Invoked on the executor side after all rows are persisted, before
   * the task output is committed.
   */
  def close(): Unit
}
