package org.apache.spark.daslab.sql.execution


/**
 * Physical execution operators for join operations.
 */
package object joins {

  sealed abstract class BuildSide

  case object BuildRight extends BuildSide

  case object BuildLeft extends BuildSide

}
