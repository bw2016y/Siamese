package org.apache.spark.daslab.sql.engine.analysis

import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan


trait NamedRelation extends LogicalPlan {
  def name: String
}
