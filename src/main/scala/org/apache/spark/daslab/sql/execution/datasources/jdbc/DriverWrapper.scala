package org.apache.spark.daslab.sql.execution.datasources.jdbc


import java.sql.{Connection, Driver, DriverPropertyInfo, SQLFeatureNotSupportedException}
import java.util.Properties

/**
 * A wrapper for a JDBC Driver to work around SPARK-6913.
 *
 * The problem is in `java.sql.DriverManager` class that can't access drivers loaded by
 * Spark ClassLoader.
 */
class DriverWrapper(val wrapped: Driver) extends Driver {
  override def acceptsURL(url: String): Boolean = wrapped.acceptsURL(url)

  override def jdbcCompliant(): Boolean = wrapped.jdbcCompliant()

  override def getPropertyInfo(url: String, info: Properties): Array[DriverPropertyInfo] = {
    wrapped.getPropertyInfo(url, info)
  }

  override def getMinorVersion: Int = wrapped.getMinorVersion

  def getParentLogger: java.util.logging.Logger = {
    throw new SQLFeatureNotSupportedException(
      s"${this.getClass.getName}.getParentLogger is not yet implemented.")
  }

  override def connect(url: String, info: Properties): Connection = wrapped.connect(url, info)

  override def getMajorVersion: Int = wrapped.getMajorVersion
}
