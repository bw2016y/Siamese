package org.apache.spark.daslab.sql.engine.plans

import org.apache.spark.daslab.sql.engine.trees.{CurrentOrigin,TreeNode}
import org.apache.spark.daslab.sql.types.{DataType,StructType}
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.internal.SQLConf


abstract class QueryPlan[PlanType <: QueryPlan[PlanType]] extends TreeNode[PlanType] {
  self: PlanType =>


  /**
   *  当前作用域内激活的配置对象
   */
  def conf: SQLConf = SQLConf.get


  /**
   *  返回这个逻辑计划输出的属性的集合
   * @return
   */
  def output: Seq[Attribute]


  /**
    * @return 返回该节点的属性的集合 /Returns the set of attributes that are output by this node.
   */
  def outputSet: AttributeSet = AttributeSet(output)

  /**
   * All Attributes that appear in expressions from this operator.  Note that this set does not
   * include attributes that are implicitly referenced by being passed through to the output tuple.
   */
  def references: AttributeSet = AttributeSet(expressions.flatMap(_.references))

  /**
   * The set of all attributes that are input to this operator by its children.
   *  返回该节点子节点的属性
   */
  def inputSet: AttributeSet =
    AttributeSet(children.flatMap(_.asInstanceOf[QueryPlan[PlanType]].output))

  /**
   * The set of all attributes that are produced by this node.
   * 当前节点生成的所有属性
   */
  def producedAttributes: AttributeSet = AttributeSet.empty

  /**
   * Attributes that are referenced by expressions but not provided by this node's children.
   * Subclasses should override this method if they produce attributes internally as it is used by
   * assertions designed to prevent the construction of invalid plans.
   *  被表达式引用但是没有被子节点提供的属性
   */
  def missingInput: AttributeSet = references -- inputSet -- producedAttributes

  /**
   * Runs [[transformExpressionsDown]] with `rule` on all expressions present
   * in this query operator.
   *
   * Users should not expect a specific directionality. If a specific directionality is needed,
   * transformExpressionsDown or transformExpressionsUp should be used.
   *
   * @param rule the rule to be applied to every expression in this operator.
   */
  def transformExpressions(rule: PartialFunction[Expression, Expression]): this.type = {
    transformExpressionsDown(rule)
  }

  /**
   * Runs [[transformDown]] with `rule` on all expressions present in this query operator.
   *
   * @param rule the rule to be applied to every expression in this operator.
   */
  def transformExpressionsDown(rule: PartialFunction[Expression, Expression]): this.type = {
    mapExpressions(_.transformDown(rule))
  }

  /**
   * Runs [[transformUp]] with `rule` on all expressions present in this query operator.
   *
   * @param rule the rule to be applied to every expression in this operator.
   * @return
   */
  def transformExpressionsUp(rule: PartialFunction[Expression, Expression]): this.type = {
    mapExpressions(_.transformUp(rule))
  }

  /**
   * Apply a map function to each expression present in this query operator, and return a new
   * query operator based on the mapped expressions.
   */
  def mapExpressions(f: Expression => Expression): this.type = {
    var changed = false

    @inline def transformExpression(e: Expression): Expression = {
      val newE = CurrentOrigin.withOrigin(e.origin) {
        f(e)
      }
      if (newE.fastEquals(e)) {
        e
      } else {
        changed = true
        newE
      }
    }

    def recursiveTransform(arg: Any): AnyRef = arg match {
      case e: Expression => transformExpression(e)
      case Some(value) => Some(recursiveTransform(value))
      case m: Map[_, _] => m
      case d: DataType => d // Avoid unpacking Structs
      case stream: Stream[_] => stream.map(recursiveTransform).force
      case seq: Traversable[_] => seq.map(recursiveTransform)
      case other: AnyRef => other
      case null => null
    }

    val newArgs = mapProductIterator(recursiveTransform)

    if (changed) makeCopy(newArgs).asInstanceOf[this.type] else this
  }

  /**
   * Returns the result of running [[transformExpressions]] on this node
   * and all its children.
   */
  def transformAllExpressions(rule: PartialFunction[Expression, Expression]): this.type = {
    transform {
      case q: QueryPlan[_] => q.transformExpressions(rule).asInstanceOf[PlanType]
    }.asInstanceOf[this.type]
  }

  /** Returns all of the expressions present in this query plan operator. */
  final def expressions: Seq[Expression] = {
    // Recursively find all expressions from a traversable.
    def seqToExpressions(seq: Traversable[Any]): Traversable[Expression] = seq.flatMap {
      case e: Expression => e :: Nil
      case s: Traversable[_] => seqToExpressions(s)
      case other => Nil
    }

    productIterator.flatMap {
      case e: Expression => e :: Nil
      case s: Some[_] => seqToExpressions(s.toSeq)
      case seq: Traversable[_] => seqToExpressions(seq)
      case other => Nil
    }.toSeq
  }

  //返回对应的schema信息,output输出属性的schema信息
  //todo not lazy
    //lazy val schema: StructType = StructType.fromAttributes(output)
   lazy  val   schema: StructType = StructType.fromAttributes(output)


  /** Returns the output schema in the tree format. */
  def schemaString: String = schema.treeString

  /** Prints out the schema in the tree format */
  // scalastyle:off println
  def printSchema(): Unit = println(schemaString)
  // scalastyle:on println

  /**
   * A prefix string used when printing the plan.
   *
   * We use "!" to indicate an invalid plan, and "'" to indicate an unresolved plan.
   */
  protected def statePrefix = if (missingInput.nonEmpty && children.nonEmpty) "!" else ""

  override def simpleString: String = statePrefix + super.simpleString

  override def verboseString: String = simpleString

  /**
   * All the subqueries of current plan.
    *  当前Query Plan节点中包含的所有子查询。
   */
  def subqueries: Seq[PlanType] = {
    expressions.flatMap(_.collect {
      case e: PlanExpression[_] => e.plan.asInstanceOf[PlanType]
    })
  }

  override protected def innerChildren: Seq[QueryPlan[_]] = subqueries

  /**
   * A private mutable variable to indicate whether this plan is the result of canonicalization.
   * This is used solely for making sure we wouldn't execute a canonicalized plan.
   * See [[canonicalized]] on how this is set.
   */
  @transient private var _isCanonicalizedPlan: Boolean = false

  protected def isCanonicalizedPlan: Boolean = _isCanonicalizedPlan

  /**
   * Returns a plan where a best effort attempt has been made to transform `this` in a way
   * that preserves the result but removes cosmetic variations (case sensitivity, ordering for
   * commutative operations, expression id, etc.)
   * 在保留执行结果不变的前提下，转换逻辑计划消除差异（大小写，可交换顺序算子的排序，表达式id）
   * Plans where `this.canonicalized == other.canonicalized` will always evaluate to the same
   * result.
   *
   * Plan nodes that require special canonicalization should override [[doCanonicalize()]].
   * They should remove expressions cosmetic variations themselves.
   */
  @transient final lazy val canonicalized: PlanType = {
    var plan = doCanonicalize()
    // If the plan has not been changed due to canonicalization, make a copy of it so we don't
    // mutate the original plan's _isCanonicalizedPlan flag.
    if (plan eq this) {
      plan = plan.makeCopy(plan.mapProductIterator(x => x.asInstanceOf[AnyRef]))
    }
    plan._isCanonicalizedPlan = true
    plan
  }

  /**
   * Defines how the canonicalization should work for the current plan.
   * 标准化
   */
  protected def doCanonicalize(): PlanType = {
    val canonicalizedChildren = children.map(_.canonicalized)
    var id = -1
    mapExpressions {
      case a: Alias =>
        id += 1
        // As the root of the expression, Alias will always take an arbitrary exprId, we need to
        // normalize that for equality testing, by assigning expr id from 0 incrementally. The
        // alias name doesn't matter and should be erased.
        val normalizedChild = QueryPlan.normalizeExprId(a.child, allAttributes)
        Alias(normalizedChild, "")(ExprId(id), a.qualifier)

      case ar: AttributeReference if allAttributes.indexOf(ar.exprId) == -1 =>
        // Top level `AttributeReference` may also be used for output like `Alias`, we should
        // normalize the epxrId too.
        id += 1
        ar.withExprId(ExprId(id)).canonicalized

      case other => QueryPlan.normalizeExprId(other, allAttributes)
    }.withNewChildren(canonicalizedChildren)
  }

  /**
   * Returns true when the given query plan will return the same results as this query plan.
   * 判断另一个query plan是否与当前的query plan会生成一样的结果
   * Since its likely undecidable to generally determine if two given plans will produce the same
   * results, it is okay for this function to return false, even if the results are actually
   * the same.  Such behavior will not affect correctness, only the application of performance
   * enhancements like caching.  However, it is not acceptable to return true if the results could
   * possibly be different.
   *
   * This function performs a modified version of equality that is tolerant of cosmetic
   * differences like attribute naming and or expression id differences.
   */
  final def sameResult(other: PlanType): Boolean = this.canonicalized == other.canonicalized

  /**
   * Returns a `hashCode` for the calculation performed by this plan. Unlike the standard
   * `hashCode`, an attempt has been made to eliminate cosmetic differences.
   */
  final def semanticHash(): Int = canonicalized.hashCode()

  /**
   * All the attributes that are used for this plan.
    *  该节点涉及的所有属性集合
   */
  lazy val allAttributes: AttributeSeq = children.flatMap(_.output)
}

object QueryPlan extends PredicateHelper {
  /**
   * Normalize the exprIds in the given expression, by updating the exprId in `AttributeReference`
   * with its referenced ordinal from input attributes. It's similar to `BindReferences` but we
   * do not use `BindReferences` here as the plan may take the expression as a parameter with type
   * `Attribute`, and replace it with `BoundReference` will cause error.
   */
  def normalizeExprId[T <: Expression](e: T, input: AttributeSeq): T = {
    e.transformUp {
      case s: SubqueryExpression => s.canonicalize(input)
      case ar: AttributeReference =>
        val ordinal = input.indexOf(ar.exprId)
        if (ordinal == -1) {
          ar
        } else {
          ar.withExprId(ExprId(ordinal)).canonicalized
        }
    }.canonicalized.asInstanceOf[T]
  }

  /**
   * Composes the given predicates into a conjunctive predicate, which is normalized and reordered.
   * Then returns a new sequence of predicates by splitting the conjunctive predicate.
   */
  def normalizePredicates(predicates: Seq[Expression], output: AttributeSeq): Seq[Expression] = {
    if (predicates.nonEmpty) {
      val normalized = normalizeExprId(predicates.reduce(And), output)
      splitConjunctivePredicates(normalized)
    } else {
      Nil
    }
  }
}
