package org.apache.spark.daslab.sql.engine.util



import java.util.Locale


//todo
import org.apache.spark.internal.Logging

sealed trait ParseMode {
  /**
   * String name of the parse mode.
   */
  def name: String
}

/**
 * This mode permissively parses the records.
 */
case object PermissiveMode extends ParseMode { val name = "PERMISSIVE" }

/**
 * This mode ignores the whole corrupted records.
 */
case object DropMalformedMode extends ParseMode { val name = "DROPMALFORMED" }

/**
 * This mode throws an exception when it meets corrupted records.
 */
case object FailFastMode extends ParseMode { val name = "FAILFAST" }

object ParseMode extends Logging {
  /**
   * Returns the parse mode from the given string.
   */
  def fromString(mode: String): ParseMode = mode.toUpperCase(Locale.ROOT) match {
    case PermissiveMode.name => PermissiveMode
    case DropMalformedMode.name => DropMalformedMode
    case FailFastMode.name => FailFastMode
    case _ =>
      logWarning(s"$mode is not a valid parse mode. Using ${PermissiveMode.name}.")
      PermissiveMode
  }
}
