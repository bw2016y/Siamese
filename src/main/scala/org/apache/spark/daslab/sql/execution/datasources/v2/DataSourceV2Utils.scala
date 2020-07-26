package org.apache.spark.daslab.sql.execution.datasources.v2


import java.util.regex.Pattern

import org.apache.spark.daslab.sql.internal.SQLConf
import org.apache.spark.daslab.sql.sources.v2.{DataSourceV2, SessionConfigSupport}

//todo
import org.apache.spark.internal.Logging


private[sql] object DataSourceV2Utils extends Logging {

  /**
   * Helper method that extracts and transforms session configs into k/v pairs, the k/v pairs will
   * be used to create data source options.
   * Only extract when `ds` implements [[SessionConfigSupport]], in this case we may fetch the
   * specified key-prefix from `ds`, and extract session configs with config keys that start with
   * `spark.datasource.$keyPrefix`. A session config `spark.datasource.$keyPrefix.xxx -> yyy` will
   * be transformed into `xxx -> yyy`.
   *
   * @param ds a [[DataSourceV2]] object
   * @param conf the session conf
   * @return an immutable map that contains all the extracted and transformed k/v pairs.
   */
  def extractSessionConfigs(ds: DataSourceV2, conf: SQLConf): Map[String, String] = ds match {
    case cs: SessionConfigSupport =>
      val keyPrefix = cs.keyPrefix()
      require(keyPrefix != null, "The data source config key prefix can't be null.")

      val pattern = Pattern.compile(s"^spark\\.datasource\\.$keyPrefix\\.(.+)")

      conf.getAllConfs.flatMap { case (key, value) =>
        val m = pattern.matcher(key)
        if (m.matches() && m.groupCount() > 0) {
          Seq((m.group(1), value))
        } else {
          Seq.empty
        }
      }

    case _ => Map.empty
  }
}
