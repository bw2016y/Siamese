package org.apache.spark.daslab.sql.execution.ui


//todo
import org.apache.spark.internal.Logging
import org.apache.spark.ui.{SparkUI, SparkUITab}

class SQLTab(val sqlStore: SQLAppStatusStore, sparkUI: SparkUI)
  extends SparkUITab(sparkUI, "SQL") with Logging {

  val parent = sparkUI

  attachPage(new AllExecutionsPage(this))
  attachPage(new ExecutionPage(this))
  parent.attachTab(this)

  parent.addStaticHandler(SQLTab.STATIC_RESOURCE_DIR, "/static/sql")
}

object SQLTab {
  private val STATIC_RESOURCE_DIR = "org/apache/spark/sql/execution/ui/static"
}
