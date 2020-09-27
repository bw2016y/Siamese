package org.apache.spark.daslab.sql.execution



import org.apache.spark.daslab.sql.ExperimentalMethods
import org.apache.spark.daslab.sql.engine.catalog.SessionCatalog
import org.apache.spark.daslab.sql.engine.optimizer.Optimizer
import org.apache.spark.daslab.sql.execution.datasources.PruneFileSourcePartitions
import org.apache.spark.daslab.sql.execution.datasources.parquet.ParquetSchemaPruning
import org.apache.spark.daslab.sql.execution.python.{ExtractPythonUDFFromAggregate, ExtractPythonUDFs}

class SparkOptimizer(catalog: SessionCatalog,
                      experimentalMethods: ExperimentalMethods)
  extends Optimizer(catalog) {

  override def defaultBatches: Seq[Batch] = (preOptimizationBatches ++ super.defaultBatches :+
    Batch("Optimize Metadata Only Query", Once, OptimizeMetadataOnlyQuery(catalog)) :+
    Batch("Extract Python UDFs", Once,
      Seq(ExtractPythonUDFFromAggregate, ExtractPythonUDFs): _*) :+
    Batch("Prune File Source Table Partitions", Once, PruneFileSourcePartitions) :+
    Batch("Parquet Schema Pruning", Once, ParquetSchemaPruning)) ++
    postHocOptimizationBatches :+
    Batch("User Provided Optimizers", fixedPoint, experimentalMethods.extraOptimizations: _*)

  override def nonExcludableRules: Seq[String] =
    super.nonExcludableRules :+ ExtractPythonUDFFromAggregate.ruleName

  /**
   * Optimization batches that are executed before the regular optimization batches (also before
   * the finish analysis batch).
   */
  def preOptimizationBatches: Seq[Batch] = Nil

  /**
   * Optimization batches that are executed after the regular optimization batches, but before the
   * batch executing the [[ExperimentalMethods]] optimizer rules. This hook can be used to add
   * custom optimizer batches to the Spark optimizer.
   */
  def postHocOptimizationBatches: Seq[Batch] = Nil
}
