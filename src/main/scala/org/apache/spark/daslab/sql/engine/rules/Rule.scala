package org.apache.spark.daslab.sql.engine.rules

import org.apache.spark.daslab.sql.engine.trees.TreeNode

//todo spark core
import org.apache.spark.internal.Logging




abstract class Rule[TreeType <: TreeNode[_]] extends Logging {

  /** Name for this rule, automatically inferred based on class name. */
  val ruleName: String = {
    val className = getClass.getName
    if (className endsWith "$") className.dropRight(1) else className
  }

  def apply(plan: TreeType): TreeType
}
