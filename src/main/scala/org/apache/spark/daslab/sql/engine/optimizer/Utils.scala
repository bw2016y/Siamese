package org.apache.spark.daslab.sql.engine.optimizer

import org.apache.spark.daslab.sql.engine.expressions.{And, Expression}

object Utils {
    def splitConjunctivePredicates(condition:Expression): Seq[Expression] = {
      condition match{
        case And(cond1,cond2) =>
          splitConjunctivePredicates(cond1) ++ splitConjunctivePredicates(cond2)
        case other => other :: Nil
      }
  }
}
