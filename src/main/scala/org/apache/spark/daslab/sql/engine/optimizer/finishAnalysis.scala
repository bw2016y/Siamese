package org.apache.spark.daslab.sql.engine.optimizer



import scala.collection.mutable

import org.apache.spark.daslab.sql.engine.catalog.SessionCatalog
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.plans.logical._
import org.apache.spark.daslab.sql.engine.rules._
import org.apache.spark.daslab.sql.engine.util.DateTimeUtils
import org.apache.spark.daslab.sql.types._


/**
 * Finds all [[RuntimeReplaceable]] expressions and replace them with the expressions that can
 * be evaluated. This is mainly used to provide compatibility with other databases.
 * For example, we use this to support "nvl" by replacing it with "coalesce".
 */
object ReplaceExpressions extends Rule[LogicalPlan] {
  def apply(plan: LogicalPlan): LogicalPlan = plan transformAllExpressions {
    case e: RuntimeReplaceable => e.child
  }
}


/**
 * Computes the current date and time to make sure we return the same result in a single query.
 */
object ComputeCurrentTime extends Rule[LogicalPlan] {
  def apply(plan: LogicalPlan): LogicalPlan = {
    val currentDates = mutable.Map.empty[String, Literal]
    val timeExpr = CurrentTimestamp()
    val timestamp = timeExpr.eval(EmptyRow).asInstanceOf[Long]
    val currentTime = Literal.create(timestamp, timeExpr.dataType)

    plan transformAllExpressions {
      case CurrentDate(Some(timeZoneId)) =>
        currentDates.getOrElseUpdate(timeZoneId, {
          Literal.create(
            DateTimeUtils.millisToDays(timestamp / 1000L, DateTimeUtils.getTimeZone(timeZoneId)),
            DateType)
        })
      case CurrentTimestamp() => currentTime
    }
  }
}


/** Replaces the expression of CurrentDatabase with the current database name. */
case class GetCurrentDatabase(sessionCatalog: SessionCatalog) extends Rule[LogicalPlan] {
  def apply(plan: LogicalPlan): LogicalPlan = {
    plan transformAllExpressions {
      case CurrentDatabase() =>
        Literal.create(sessionCatalog.getCurrentDatabase, StringType)
    }
  }
}

