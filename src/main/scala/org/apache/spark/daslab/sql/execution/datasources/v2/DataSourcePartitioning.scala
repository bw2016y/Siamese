package org.apache.spark.daslab.sql.execution.datasources.v2


import org.apache.spark.daslab.sql.engine.expressions.{Attribute, AttributeMap, Expression}
import org.apache.spark.daslab.sql.engine.plans.physical
import org.apache.spark.daslab.sql.sources.v2.reader.partitioning.{ClusteredDistribution, Partitioning}

/**
 * An adapter from public data source partitioning to catalyst internal `Partitioning`.
 */
class DataSourcePartitioning(
                              partitioning: Partitioning,
                              colNames: AttributeMap[String]) extends physical.Partitioning {

  override val numPartitions: Int = partitioning.numPartitions()

  override def satisfies0(required: physical.Distribution): Boolean = {
    super.satisfies0(required) || {
      required match {
        case d: physical.ClusteredDistribution if isCandidate(d.clustering) =>
          val attrs = d.clustering.map(_.asInstanceOf[Attribute])
          partitioning.satisfy(
            new ClusteredDistribution(attrs.map { a =>
              val name = colNames.get(a)
              assert(name.isDefined, s"Attribute ${a.name} is not found in the data source output")
              name.get
            }.toArray))

        case _ => false
      }
    }
  }

  private def isCandidate(clustering: Seq[Expression]): Boolean = {
    clustering.forall {
      case a: Attribute => colNames.contains(a)
      case _ => false
    }
  }
}

