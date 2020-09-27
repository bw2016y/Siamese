package org.apache.spark.daslab.sql.execution.exchange



import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, AttributeMap, Expression, SortOrder}
import org.apache.spark.daslab.sql.engine.plans.physical.Partitioning
import org.apache.spark.daslab.sql.engine.rules.Rule
import org.apache.spark.daslab.sql.execution.{LeafExecNode, SparkPlan, UnaryExecNode}
import org.apache.spark.daslab.sql.internal.SQLConf
import org.apache.spark.daslab.sql.types.StructType

//todo
import org.apache.spark.broadcast
import org.apache.spark.rdd.RDD

/**
 * Base class for operators that exchange data among multiple threads or processes.
 *
 * Exchanges are the key class of operators that enable parallelism. Although the implementation
 * differs significantly, the concept is similar to the exchange operator described in
 * "Volcano -- An Extensible and Parallel Query Evaluation System" by Goetz Graefe.
 */
abstract class Exchange extends UnaryExecNode {
  override def output: Seq[Attribute] = child.output
}

/**
 * A wrapper for reused exchange to have different output, because two exchanges which produce
 * logically identical output will have distinct sets of output attribute ids, so we need to
 * preserve the original ids because they're what downstream operators are expecting.
 */
case class ReusedExchangeExec(override val output: Seq[Attribute], child: Exchange)
  extends LeafExecNode {

  // Ignore this wrapper for canonicalizing.
  override def doCanonicalize(): SparkPlan = child.canonicalized

  def doExecute(): RDD[InternalRow] = {
    child.execute()
  }

  override protected[sql] def doExecuteBroadcast[T](): broadcast.Broadcast[T] = {
    child.executeBroadcast()
  }

  // `ReusedExchangeExec` can have distinct set of output attribute ids from its child, we need
  // to update the attribute ids in `outputPartitioning` and `outputOrdering`.
  private lazy val updateAttr: Expression => Expression = {
    val originalAttrToNewAttr = AttributeMap(child.output.zip(output))
    e => e.transform {
      case attr: Attribute => originalAttrToNewAttr.getOrElse(attr, attr)
    }
  }

  override def outputPartitioning: Partitioning = child.outputPartitioning match {
    case e: Expression => updateAttr(e).asInstanceOf[Partitioning]
    case other => other
  }

  override def outputOrdering: Seq[SortOrder] = {
    child.outputOrdering.map(updateAttr(_).asInstanceOf[SortOrder])
  }
}

/**
 * Find out duplicated exchanges in the spark plan, then use the same exchange for all the
 * references.
 */
case class ReuseExchange(conf: SQLConf) extends Rule[SparkPlan] {

  def apply(plan: SparkPlan): SparkPlan = {
    if (!conf.exchangeReuseEnabled) {
      return plan
    }
    // Build a hash map using schema of exchanges to avoid O(N*N) sameResult calls.
    val exchanges = mutable.HashMap[StructType, ArrayBuffer[Exchange]]()
    plan.transformUp {
      case exchange: Exchange =>
        // the exchanges that have same results usually also have same schemas (same column names).
        val sameSchema = exchanges.getOrElseUpdate(exchange.schema, ArrayBuffer[Exchange]())
        val samePlan = sameSchema.find { e =>
          exchange.sameResult(e)
        }
        if (samePlan.isDefined) {
          // Keep the output of this exchange, the following plans require that to resolve
          // attributes.
          ReusedExchangeExec(exchange.output, samePlan.get)
        } else {
          sameSchema += exchange
          exchange
        }
    }
  }
}

