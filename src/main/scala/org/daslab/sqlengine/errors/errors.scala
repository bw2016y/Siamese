package org.daslab.sqlengine

import org.apache.spark.SparkException
import org.daslab.sqlengine.trees.TreeNode

import scala.util.control.NonFatal

/**
 *  获取和错误有关的树信息
 */
package object errors {

  class TreeNodeException[TreeType <: TreeNode[_]](@transient val tree: TreeType,
                                                    msg: String,
                                                    cause: Throwable)
    extends Exception(msg, cause) {

    val treeString = tree.toString

    // Yes, this is the same as a default parameter, but... those don't seem to work with SBT
    // external project dependencies for some reason.
    def this(tree: TreeType, msg: String) = this(tree, msg, null)

    override def getMessage: String = {
      s"${super.getMessage}, tree:${if (treeString contains "\n") "\n" else " "}$tree"
    }

  }

  /**
   *   将在执行函数f期间抛出的异常封装为TreeNodeException，提供tree的信息
   *
   */
  def attachTree[TreeType <: TreeNode[_], A](tree: TreeType, msg: String = "")(f: => A): A = {
    try f catch {
      // SPARK-16748: We do not want SparkExceptions from job failures in the planning phase
      // to create TreeNodeException. Hence, wrap exception only if it is not SparkException.
      case NonFatal(e) if !e.isInstanceOf[SparkException] =>
        throw new TreeNodeException(tree, msg, e)
    }
  }

}
