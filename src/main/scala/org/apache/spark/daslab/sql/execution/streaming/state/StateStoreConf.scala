package org.apache.spark.daslab.sql.execution.streaming.state


import org.apache.spark.daslab.sql.internal.SQLConf

/** A class that contains configuration parameters for [[StateStore]]s. */
class StateStoreConf(@transient private val sqlConf: SQLConf)
  extends Serializable {

  def this() = this(new SQLConf)

  /**
   * Minimum number of delta files in a chain after which HDFSBackedStateStore will
   * consider generating a snapshot.
   */
  val minDeltasForSnapshot: Int = sqlConf.stateStoreMinDeltasForSnapshot

  /** Minimum versions a State Store implementation should retain to allow rollbacks */
  val minVersionsToRetain: Int = sqlConf.minBatchesToRetain

  /** Maximum count of versions a State Store implementation should retain in memory */
  val maxVersionsToRetainInMemory: Int = sqlConf.maxBatchesToRetainInMemory

  /**
   * Optional fully qualified name of the subclass of [[StateStoreProvider]]
   * managing state data. That is, the implementation of the State Store to use.
   */
  val providerClass: String = sqlConf.stateStoreProviderClass

  /**
   * Additional configurations related to state store. This will capture all configs in
   * SQLConf that start with `spark.sql.streaming.stateStore.` */
  val confs: Map[String, String] =
    sqlConf.getAllConfs.filter(_._1.startsWith("spark.sql.streaming.stateStore."))
}

object StateStoreConf {
  val empty = new StateStoreConf()

  def apply(conf: SQLConf): StateStoreConf = new StateStoreConf(conf)
}
