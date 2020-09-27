package org.apache.spark.daslab.sql.engine.plans.logical



import org.apache.spark.daslab.sql.engine.expressions.Attribute

/**
 * A logical node that represents a non-query command to be executed by the system.  For example,
 * commands can be used by parsers to represent DDL operations.  Commands, unlike queries, are
 * eagerly executed.
 */
trait Command extends LogicalPlan {
  override def output: Seq[Attribute] = Seq.empty
  override def children: Seq[LogicalPlan] = Seq.empty
}
