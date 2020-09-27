package org.apache.spark.daslab.sql.execution.ui

import org.apache.spark.SparkConf
import org.apache.spark.scheduler.SparkListener
import org.apache.spark.status.{AppHistoryServerPlugin, ElementTrackingStore}
import org.apache.spark.ui.SparkUI

class SQLHistoryServerPlugin extends AppHistoryServerPlugin {
  override def createListeners(conf: SparkConf, store: ElementTrackingStore): Seq[SparkListener] = {
    Seq(new SQLAppStatusListener(conf, store, live = false))
  }

  override def setupUI(ui: SparkUI): Unit = {
    val sqlStatusStore = new SQLAppStatusStore(ui.store.store)
    if (sqlStatusStore.executionsCount() > 0) {
      new SQLTab(sqlStatusStore, ui)
    }
  }
}
