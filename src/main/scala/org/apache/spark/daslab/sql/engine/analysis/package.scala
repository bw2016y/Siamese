package org.apache.spark.daslab.sql.engine

import org.apache.spark.daslab.sql.AnalysisException
import org.apache.spark.daslab.sql.engine.trees.TreeNode



/**
 * Provides a logical query plan [[Analyzer]] and supporting classes for performing analysis.
 * Analysis consists of translating [[UnresolvedAttribute]]s and [[UnresolvedRelation]]s
 * into fully typed objects using information in a schema [[Catalog]].
 */
package object analysis {

  /**
   * Resolver should return true if the first string refers to the same entity as the second string.
   * For example, by using case insensitive equality.
   */
  type Resolver = (String, String) => Boolean

  val caseInsensitiveResolution = (a: String, b: String) => a.equalsIgnoreCase(b)
  val caseSensitiveResolution = (a: String, b: String) => a == b

  implicit class AnalysisErrorAt(t: TreeNode[_]) {
    /** Fails the analysis at the point where a specific tree node was parsed. */
    def failAnalysis(msg: String): Nothing = {
      throw new AnalysisException(msg, t.origin.line, t.origin.startPosition)
    }

    /** Fails the analysis at the point where a specific tree node was parsed. */
    def failAnalysis(msg: String, cause: Throwable): Nothing = {
      throw new AnalysisException(msg, t.origin.line, t.origin.startPosition, cause = Some(cause))
    }
  }

  /** Catches any AnalysisExceptions thrown by `f` and attaches `t`'s position if any. */
  def withPosition[A](t: TreeNode[_])(f: => A): A = {
    try f catch {
      case a: AnalysisException =>
        throw a.withPosition(t.origin.line, t.origin.startPosition)
    }
  }
}
