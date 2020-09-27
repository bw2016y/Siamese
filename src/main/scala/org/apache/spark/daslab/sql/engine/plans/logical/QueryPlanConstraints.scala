package org.apache.spark.daslab.sql.engine.plans.logical

//todo expressions
import org.apache.spark.daslab.sql.engine.expressions._


trait QueryPlanConstraints extends ConstraintHelper { self: LogicalPlan =>

  /**
   * An [[ExpressionSet]] that contains invariants about the rows output by this operator. For
   * example, if this set contains the expression `a = 2` then that expression is guaranteed to
   * evaluate to `true` for all rows produced.
   *  对于一个算子能够输出的row一定满足的不变的 ExpressionSet
   */
  lazy val constraints: ExpressionSet = {
    if (conf.constraintPropagationEnabled) {
      ExpressionSet(
        validConstraints
          .union(inferAdditionalConstraints(validConstraints))
          .union(constructIsNotNullConstraints(validConstraints, output))
          .filter { c =>
            c.references.nonEmpty && c.references.subsetOf(outputSet) && c.deterministic
          }
      )
    } else {
      ExpressionSet(Set.empty)
    }
  }

  /**
   * This method can be overridden by any child class of QueryPlan to specify a set of constraints
   * based on the given operator's constraint propagation logic. These constraints are then
   * canonicalized and filtered automatically to contain only those attributes that appear in the
   * [[outputSet]].
   *
   * See [[Canonicalize]] for more details.
   */
  protected def validConstraints: Set[Expression] = Set.empty
}

trait ConstraintHelper {

  /**
   * Infers an additional set of constraints from a given set of equality constraints.
   * For e.g., if an operator has constraints of the form (`a = 5`, `a = b`), this returns an
   * additional constraint of the form `b = 5`.
   */
  def inferAdditionalConstraints(constraints: Set[Expression]): Set[Expression] = {
    var inferredConstraints = Set.empty[Expression]
    constraints.foreach {
      case eq @ EqualTo(l: Attribute, r: Attribute) =>
        val candidateConstraints = constraints - eq
        inferredConstraints ++= replaceConstraints(candidateConstraints, l, r)
        inferredConstraints ++= replaceConstraints(candidateConstraints, r, l)
      case _ => // No inference
    }
    inferredConstraints -- constraints
  }

  private def replaceConstraints(
                                  constraints: Set[Expression],
                                  source: Expression,
                                  destination: Attribute): Set[Expression] = constraints.map(_ transform {
    case e: Expression if e.semanticEquals(source) => destination
  })

  /**
    *  从所有不可为空的表达式和属性中推导出不可为控的限制（IsNotNull）
    *  例如表达式 a > 5 ,就返回a不可为空的约束
   */
  def constructIsNotNullConstraints(
                                     constraints: Set[Expression],
                                     output: Seq[Attribute]): Set[Expression] = {

    // 第一步，从所有不可为空的表达式中生成所有的约束
    var isNotNullConstraints: Set[Expression] = constraints.flatMap(inferIsNotNullConstraints)

    // 第二步，从所有不可为空的属性中推导出该算子输出不为空的约束
    val nonNullableAttributes = output.filterNot(_.nullable)
    isNotNullConstraints ++= nonNullableAttributes.map(IsNotNull).toSet
    //去掉已经存在的约束
    isNotNullConstraints -- constraints
  }

  /**
   * Infer the Attribute-specific IsNotNull constraints from the null intolerant child expressions
   * of constraints.
   */
  private def inferIsNotNullConstraints(constraint: Expression): Seq[Expression] =
    constraint match {
      // When the root is IsNotNull, we can push IsNotNull through the child null intolerant
      // expressions
      case IsNotNull(expr) => scanNullIntolerantAttribute(expr).map(IsNotNull(_))
      // Constraints always return true for all the inputs. That means, null will never be returned.
      // Thus, we can infer `IsNotNull(constraint)`, and also push IsNotNull through the child
      // null intolerant expressions.
      case _ => scanNullIntolerantAttribute(constraint).map(IsNotNull(_))
    }

  /**
   * Recursively explores the expressions which are null intolerant and returns all attributes
   * in these expressions.
   */
  private def scanNullIntolerantAttribute(expr: Expression): Seq[Attribute] = expr match {
    case a: Attribute => Seq(a)
    case _: NullIntolerant => expr.children.flatMap(scanNullIntolerantAttribute)
    case _ => Seq.empty[Attribute]
  }
}
