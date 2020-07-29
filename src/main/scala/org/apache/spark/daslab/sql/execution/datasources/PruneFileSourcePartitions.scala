package org.apache.spark.daslab.sql.execution.datasources


import org.apache.spark.daslab.sql.engine.catalog.CatalogStatistics
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.planning.PhysicalOperation
import org.apache.spark.daslab.sql.engine.plans.logical.{Filter, LogicalPlan, Project}
import org.apache.spark.daslab.sql.engine.rules.Rule

private[sql] object PruneFileSourcePartitions extends Rule[LogicalPlan] {
  override def apply(plan: LogicalPlan): LogicalPlan = plan transformDown {
    case op @ PhysicalOperation(projects, filters,
    logicalRelation @
      LogicalRelation(fsRelation @
        HadoopFsRelation(
        catalogFileIndex: CatalogFileIndex,
        partitionSchema,
        _,
        _,
        _,
        _),
      _,
      _,
      _))
      if filters.nonEmpty && fsRelation.partitionSchemaOption.isDefined =>
      // The attribute name of predicate could be different than the one in schema in case of
      // case insensitive, we should change them to match the one in schema, so we donot need to
      // worry about case sensitivity anymore.
      val normalizedFilters = filters.map { e =>
        e transform {
          case a: AttributeReference =>
            a.withName(logicalRelation.output.find(_.semanticEquals(a)).get.name)
        }
      }

      val sparkSession = fsRelation.sparkSession
      val partitionColumns =
        logicalRelation.resolve(
          partitionSchema, sparkSession.sessionState.analyzer.resolver)
      val partitionSet = AttributeSet(partitionColumns)
      val partitionKeyFilters =
        ExpressionSet(normalizedFilters
          .filterNot(SubqueryExpression.hasSubquery(_))
          .filter(_.references.subsetOf(partitionSet)))

      if (partitionKeyFilters.nonEmpty) {
        val prunedFileIndex = catalogFileIndex.filterPartitions(partitionKeyFilters.toSeq)
        val prunedFsRelation =
          fsRelation.copy(location = prunedFileIndex)(sparkSession)
        // Change table stats based on the sizeInBytes of pruned files
        val withStats = logicalRelation.catalogTable.map(_.copy(
          stats = Some(CatalogStatistics(sizeInBytes = BigInt(prunedFileIndex.sizeInBytes)))))
        val prunedLogicalRelation = logicalRelation.copy(
          relation = prunedFsRelation, catalogTable = withStats)
        // Keep partition-pruning predicates so that they are visible in physical planning
        val filterExpression = filters.reduceLeft(And)
        val filter = Filter(filterExpression, prunedLogicalRelation)
        Project(projects, filter)
      } else {
        op
      }
  }
}
