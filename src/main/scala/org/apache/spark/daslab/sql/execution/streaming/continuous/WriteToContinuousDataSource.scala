package org.apache.spark.daslab.sql.execution.streaming.continuous



import org.apache.spark.daslab.sql.engine.expressions.Attribute
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.sources.v2.writer.streaming.StreamWriter

/**
 * The logical plan for writing data in a continuous stream.
 */
case class WriteToContinuousDataSource(
                                        writer: StreamWriter, query: LogicalPlan) extends LogicalPlan {
  override def children: Seq[LogicalPlan] = Seq(query)
  override def output: Seq[Attribute] = Nil
}
