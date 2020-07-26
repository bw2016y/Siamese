package org.apache.spark.daslab.sql.engine.plans.logical



import org.apache.spark.daslab.sql.engine.expressions.{Attribute, AttributeSet, Expression}

/**
 * FlatMap groups using an udf: pandas.Dataframe -> pandas.DataFrame.
 * This is used by DataFrame.groupby().apply().
 */
case class FlatMapGroupsInPandas(
                                  groupingAttributes: Seq[Attribute],
                                  functionExpr: Expression,
                                  output: Seq[Attribute],
                                  child: LogicalPlan) extends UnaryNode {

  /**
   * This is needed because output attributes are considered `references` when
   * passed through the constructor.
   *
   * Without this, catalyst will complain that output attributes are missing
   * from the input.
   */
  override val producedAttributes = AttributeSet(output)
}
