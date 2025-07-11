package org.apache.spark.daslab.sql


import org.apache.spark.daslab.sql.internal.SQLConf

//todo
import org.apache.spark.annotation.InterfaceStability
import org.apache.spark.internal.config.{ConfigEntry, OptionalConfigEntry}


/**
 * Runtime configuration interface for Spark. To access this, use `SparkSession.conf`.
 *
 * Options set here are automatically propagated to the Hadoop configuration during I/O.
 *
 * @since 2.0.0
 */
@InterfaceStability.Stable
class RuntimeConfig private[sql](sqlConf: SQLConf = new SQLConf) {

  /**
   * Sets the given Spark runtime configuration property.
   *
   * @since 2.0.0
   */
  def set(key: String, value: String): Unit = {
    requireNonStaticConf(key)
    sqlConf.setConfString(key, value)
  }

  /**
   * Sets the given Spark runtime configuration property.
   *
   * @since 2.0.0
   */
  def set(key: String, value: Boolean): Unit = {
    requireNonStaticConf(key)
    set(key, value.toString)
  }

  /**
   * Sets the given Spark runtime configuration property.
   *
   * @since 2.0.0
   */
  def set(key: String, value: Long): Unit = {
    requireNonStaticConf(key)
    set(key, value.toString)
  }

  /**
   * Returns the value of Spark runtime configuration property for the given key.
   *
   * @throws java.util.NoSuchElementException if the key is not set and does not have a default
   *                                          value
   * @since 2.0.0
   */
  @throws[NoSuchElementException]("if the key is not set")
  def get(key: String): String = {
    sqlConf.getConfString(key)
  }

  /**
   * Returns the value of Spark runtime configuration property for the given key.
   *
   * @since 2.0.0
   */
  def get(key: String, default: String): String = {
    sqlConf.getConfString(key, default)
  }

  /**
   * Returns the value of Spark runtime configuration property for the given key.
   */
  @throws[NoSuchElementException]("if the key is not set")
  protected[sql] def get[T](entry: ConfigEntry[T]): T = {
    sqlConf.getConf(entry)
  }

  protected[sql] def get[T](entry: OptionalConfigEntry[T]): Option[T] = {
    sqlConf.getConf(entry)
  }

  /**
   * Returns the value of Spark runtime configuration property for the given key.
   */
  protected[sql] def get[T](entry: ConfigEntry[T], default: T): T = {
    sqlConf.getConf(entry, default)
  }

  /**
   * Returns all properties set in this conf.
   *
   * @since 2.0.0
   */
  def getAll: Map[String, String] = {
    sqlConf.getAllConfs
  }

  /**
   * Returns the value of Spark runtime configuration property for the given key.
   *
   * @since 2.0.0
   */
  def getOption(key: String): Option[String] = {
    try Option(get(key)) catch {
      case _: NoSuchElementException => None
    }
  }

  /**
   * Resets the configuration property for the given key.
   *
   * @since 2.0.0
   */
  def unset(key: String): Unit = {
    requireNonStaticConf(key)
    sqlConf.unsetConf(key)
  }

  /**
   * Indicates whether the configuration property with the given key
   * is modifiable in the current session.
   *
   * @return `true` if the configuration property is modifiable. For static SQL, Spark Core,
   *         invalid (not existing) and other non-modifiable configuration properties,
   *         the returned value is `false`.
   * @since 2.4.0
   */
  def isModifiable(key: String): Boolean = sqlConf.isModifiable(key)

  /**
   * Returns whether a particular key is set.
   */
  protected[sql] def contains(key: String): Boolean = {
    sqlConf.contains(key)
  }

  private def requireNonStaticConf(key: String): Unit = {
    if (SQLConf.staticConfKeys.contains(key)) {
      throw new AnalysisException(s"Cannot modify the value of a static config: $key")
    }
  }
}
