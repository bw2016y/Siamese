package org.apache.spark.daslab.sql.engine.plans.logical


import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.types.StructType
import org.apache.spark.daslab.sql.engine.plans.QueryPlan
import org.apache.spark.daslab.sql.AnalysisException
import org.apache.spark.daslab.sql.engine.analysis._
import org.apache.spark.daslab.sql.engine.plans.logical.statsEstimation.LogicalPlanStats

//todo spark core
import org.apache.spark.internal.Logging


//逻辑计划
abstract class LogicalPlan
  extends QueryPlan[LogicalPlan]
    with AnalysisHelper
    with LogicalPlanStats
    with QueryPlanConstraints
    with Logging {

   /**
   *   如果子树中有流数据源的数据 就返回true
   * @return
   */
  def isStreaming: Boolean = children.exists(_.isStreaming == true)


  override def verboseStringWithSuffix: String = {
    super.verboseString + statsCache.map(", " + _.toString).getOrElse("")
  }

  /**
   * 返回该计划可能计算的最大的行数
   * 常用于Limit算子
   * 任何一个可以通过限制的算子都应该重写这个函数，例如（Union）
   * 任何一个可以被下推的限制算子都应该重写这个函数 ，例如（Project）
   */
  def maxRows: Option[Long] = None

  /**
   * 返回该计划在每个分区上可能计算的最大行数
   */
  def maxRowsPerPartition: Option[Long] = maxRows


  /**
   * 如果该表达式和它所有的子及表达式都已经解析到了一个具体的schema就返回true
   * 如果其中还包含未解析的placeholders就返回false
   */
  lazy val resolved: Boolean = expressions.forall(_.resolved) && childrenResolved

  override protected def statePrefix = if (!resolved) "'" else super.statePrefix

  /**
   * 该计划的所有子节点都已经被解析了就返回true
   */
  def childrenResolved: Boolean = children.forall(_.resolved)


  /**
   *  用给定的schema解析逻辑计划中的Attribute，使之变为具体的Attribute references
   *  该函数应该只用于解析逻辑计划，因为对于未解析的Attribute就会抛出AnalysisException
   * @param schema
   * @param resolver
   * @return
   */
  def resolve(schema: StructType, resolver: Resolver): Seq[Attribute] = {
    schema.map { field =>
      resolve(field.name :: Nil, resolver).map {
        case a: AttributeReference => a
        case _ => sys.error(s"can not handle nested schema yet...  plan $this")
      }.getOrElse {
        throw new AnalysisException(
          s"Unable to resolve ${field.name} given [${output.map(_.name).mkString(", ")}]")
      }
    }
  }

  private[this] lazy val childAttributes = AttributeSeq(children.flatMap(_.output))

  private[this] lazy val outputAttributes = AttributeSeq(output)

  /**
   * Optionally resolves the given strings to a [[NamedExpression]] using the input from all child
   * nodes of this LogicalPlan. The attribute is expressed as
   * as string in the following form: `[scope].AttributeName.[nested].[fields]...`.
   */
  def resolveChildren(
                       nameParts: Seq[String],
                       resolver: Resolver): Option[NamedExpression] =
    childAttributes.resolve(nameParts, resolver)

  /**
   * Optionally resolves the given strings to a [[NamedExpression]] based on the output of this
   * LogicalPlan. The attribute is expressed as string in the following form:
   * `[scope].AttributeName.[nested].[fields]...`.
   */
  def resolve(
               nameParts: Seq[String],
               resolver: Resolver): Option[NamedExpression] =
    outputAttributes.resolve(nameParts, resolver)

  /**
   * Given an attribute name, split it to name parts by dot, but
   * don't split the name parts quoted by backticks, for example,
   * `ab.cd`.`efg` should be split into two parts "ab.cd" and "efg".
   */
  def resolveQuoted(
                     name: String,
                     resolver: Resolver): Option[NamedExpression] = {
    outputAttributes.resolve(UnresolvedAttribute.parseAttributeName(name), resolver)
  }

  /**
   * Refreshes (or invalidates) any metadata/data cached in the plan recursively.
    *
    *   refresh方法会递归地刷新当前计划中的元数据等信息。
   */
  def refresh(): Unit = children.foreach(_.refresh())

  /**
   * Returns the output ordering that this plan generates.
   */
  def outputOrdering: Seq[SortOrder] = Nil
}

/**
 * 无子节点的逻辑计划
 */
abstract class LeafNode extends LogicalPlan {
  override final def children: Seq[LogicalPlan] = Nil
  override def producedAttributes: AttributeSet = outputSet

  /** Leaf nodes that can survive analysis must define their own statistics. */
  def computeStats(): Statistics = throw new UnsupportedOperationException
}

/**
 * 只有一个子节点的逻辑计划
 */
abstract class UnaryNode extends LogicalPlan {
  def child: LogicalPlan

  override final def children: Seq[LogicalPlan] = child :: Nil

  /**
   * Generates an additional set of aliased constraints by replacing the original constraint
   * expressions with the corresponding alias
   */
  protected def getAliasedConstraints(projectList: Seq[NamedExpression]): Set[Expression] = {
    var allConstraints = child.constraints.asInstanceOf[Set[Expression]]
    projectList.foreach {
      case a @ Alias(l: Literal, _) =>
        allConstraints += EqualNullSafe(a.toAttribute, l)
      case a @ Alias(e, _) =>
        // For every alias in `projectList`, replace the reference in constraints by its attribute.
        allConstraints ++= allConstraints.map(_ transform {
          case expr: Expression if expr.semanticEquals(e) =>
            a.toAttribute
        })
        allConstraints += EqualNullSafe(e, a.toAttribute)
      case _ => // Don't change.
    }

    allConstraints -- child.constraints
  }

  override protected def validConstraints: Set[Expression] = child.constraints
}

/**
 * 拥有左子节点和右子节点的逻辑计划
 */
abstract class BinaryNode extends LogicalPlan {
  def left: LogicalPlan
  def right: LogicalPlan

  override final def children: Seq[LogicalPlan] = Seq(left, right)
}

abstract class OrderPreservingUnaryNode extends UnaryNode {
  override final def outputOrdering: Seq[SortOrder] = child.outputOrdering
}
