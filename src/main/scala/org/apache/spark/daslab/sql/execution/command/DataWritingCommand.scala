package org.apache.spark.daslab.sql.execution.command



import org.apache.hadoop.conf.Configuration

import org.apache.spark.daslab.sql.{Row, SparkSession}
import org.apache.spark.daslab.sql.engine.expressions.Attribute
import org.apache.spark.daslab.sql.engine.plans.logical.{Command, LogicalPlan}
import org.apache.spark.daslab.sql.execution.SparkPlan
import org.apache.spark.daslab.sql.execution.datasources.BasicWriteJobStatsTracker
import org.apache.spark.daslab.sql.execution.datasources.FileFormatWriter
import org.apache.spark.daslab.sql.execution.metric.SQLMetric

//todo
import org.apache.spark.util.SerializableConfiguration

/**
 * A special `Command` which writes data out and updates metrics.
 */
trait DataWritingCommand extends Command {
  /**
   * The input query plan that produces the data to be written.
   * IMPORTANT: the input query plan MUST be analyzed, so that we can carry its output columns
   *            to [[FileFormatWriter]].
   */
  def query: LogicalPlan

  override final def children: Seq[LogicalPlan] = query :: Nil

  // Output column names of the analyzed input query plan.
  def outputColumnNames: Seq[String]

  // Output columns of the analyzed input query plan.
  def outputColumns: Seq[Attribute] =
    DataWritingCommand.logicalPlanOutputWithNames(query, outputColumnNames)

  lazy val metrics: Map[String, SQLMetric] = BasicWriteJobStatsTracker.metrics

  def basicWriteJobStatsTracker(hadoopConf: Configuration): BasicWriteJobStatsTracker = {
    val serializableHadoopConf = new SerializableConfiguration(hadoopConf)
    new BasicWriteJobStatsTracker(serializableHadoopConf, metrics)
  }

  def run(sparkSession: SparkSession, child: SparkPlan): Seq[Row]
}

object DataWritingCommand {
  /**
   * Returns output attributes with provided names.
   * The length of provided names should be the same of the length of [[LogicalPlan.output]].
   */
  def logicalPlanOutputWithNames(
                                  query: LogicalPlan,
                                  names: Seq[String]): Seq[Attribute] = {
    // Save the output attributes to a variable to avoid duplicated function calls.
    val outputAttributes = query.output
    assert(outputAttributes.length == names.length,
      "The length of provided names doesn't match the length of output attributes.")
    outputAttributes.zip(names).map { case (attr, outputName) =>
      attr.withName(outputName)
    }
  }
}
