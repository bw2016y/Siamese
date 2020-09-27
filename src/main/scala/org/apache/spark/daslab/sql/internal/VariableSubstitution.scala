package org.apache.spark.daslab.sql.internal


//todo
import org.apache.spark.internal.config._

/**
 * A helper class that enables substitution using syntax like
 * `${var}`, `${system:var}` and `${env:var}`.
 *
 * Variable substitution is controlled by `SQLConf.variableSubstituteEnabled`.
 */
class VariableSubstitution(conf: SQLConf) {

  private val provider = new ConfigProvider {
    override def get(key: String): Option[String] = Option(conf.getConfString(key, ""))
  }

  private val reader = new ConfigReader(provider)
    .bind("spark", provider)
    .bind("sparkconf", provider)
    .bind("hivevar", provider)
    .bind("hiveconf", provider)

  /**
   * Given a query, does variable substitution and return the result.
   */
  def substitute(input: String): String = {
    if (conf.variableSubstituteEnabled) {
      reader.substitute(input)
    } else {
      input
    }
  }
}
