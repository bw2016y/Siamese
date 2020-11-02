
package org.apache.spark.daslab.sql.engine.expressions

import java.util.Locale

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.analysis.{TypeCheckResult, TypeCoercion}
import org.apache.spark.daslab.sql.engine.expressions.codegen._
import org.apache.spark.daslab.sql.engine.expressions.codegen.Block._
import org.apache.spark.daslab.sql.engine.trees.TreeNode
import org.apache.spark.daslab.sql.types._

//todo spark core
import org.apache.spark.util.Utils



/**
  * 表达式：不需要触发执行引擎就可以直接计算的单元，如四则运算、逻辑运算、过滤等等
  *
  * If an expression wants to be exposed in the function registry (so users can call it with
  * "name(arguments...)", the concrete implementation must be a case class whose constructor
  * arguments are all Expressions types. See [[Substring]] for an example.
  *
  * 一些重要的特质:
  *
  * - [[Nondeterministic]]: 不确定的表达式
  * - [[Unevaluable]]: 不能被执行的表达式
  * - [[CodegenFallback]]: 不支持代码生成的表达式，涉及第三方实现，例如Hive的UDF
  *
  * - [[LeafExpression]]: 没有子表达式的表达式
  * - [[UnaryExpression]]: 只有一个子表达式的表达式
  * - [[BinaryExpression]]: 有两个子表达式的表达式
  * - [[TernaryExpression]]: 有三个子表达式的表达式
  * - [[BinaryOperator]]: [[BinaryExpression]]的特例，要求两个子表达式的output有相同的datatype
  *
  */
abstract class Expression extends TreeNode[Expression] {

  /**
    * 可折叠：标记该表达式能否在查询之前直接静态计算，如常量表达式Literal
    *
    * 以下条件用于确定是否可折叠
    *  - [[Literal]]可折叠
    *  - 当子表达式可折叠，[[Coalesce]]可折叠
    *  - 当左右子表达式可折叠，[[BinaryExpression]]可折叠
    *  - 当子表达式可折叠，[[Not]], [[IsNull]], or [[IsNotNull]]可折叠
    *  - 当子表达式可折叠，[[Cast]] or [[UnaryMinus]]可折叠
    */
  def foldable: Boolean = false

  /**
    * 确定性：每次执行eval函数输出是否相同，如shuffle、rand都是不确定的。
    *
    * 这意味着当有如下情况时，应被视为不确定：
    * - 它依赖于某种可变的内部状态
    * - 它依赖于某些不属于子表达式列表的隐式输入。
    * - 它有一个或多个不确定的子表达式。
    * - 假定输入通过子运算符满足某些特定条件。
    *
    * 一个例子：`SparkPartitionID`依赖 TaskContext返回的 partition id
    *
    * 默认叶子表达式是确定的，因为 Nil.forall(_.deterministic) 返回 true.
    */
  lazy val deterministic: Boolean = children.forall(_.deterministic)

  /**
    * 标记该表达式是否可能输出Null值
    */
  def nullable: Boolean

  /**
    * 表达式中涉及的属性，默认为所有子表达式属性的集合
    */
  def references: AttributeSet = AttributeSet(children.flatMap(_.references.iterator))

  /** 每种表达式对应的处理逻辑，是表达式的主要接口 */
  def eval(input: InternalRow = null): Any

  /**
    * Returns an [[ExprCode]], that contains the Java source code to generate the result of
    * evaluating the expression on an input row.
    *   返回一个[[ExprCode]]对象，这个对象包含一段可以根据input row生成expression表达式eval结果的Java源码
    * @param ctx a [[CodegenContext]]
    * @return [[ExprCode]]
    */
  def genCode(ctx: CodegenContext): ExprCode = {
    ctx.subExprEliminationExprs.get(this).map { subExprState =>
      // This expression is repeated which means that the code to evaluate it has already been added
      // as a function before. In that case, we just re-use it.
      ExprCode(ctx.registerComment(this.toString), subExprState.isNull, subExprState.value)
    }.getOrElse {
      val isNull = ctx.freshName("isNull")
      val value = ctx.freshName("value")
      val eval = doGenCode(ctx, ExprCode(
        JavaCode.isNullVariable(isNull),
        JavaCode.variable(value, dataType)))
      reduceCodeSize(ctx, eval)
      if (eval.code.toString.nonEmpty) {
        // Add `this` in the comment.
        eval.copy(code = ctx.registerComment(this.toString) + eval.code)
      } else {
        eval
      }
    }
  }

  private def reduceCodeSize(ctx: CodegenContext, eval: ExprCode): Unit = {
    // TODO: support whole stage codegen too
    if (eval.code.length > 1024 && ctx.INPUT_ROW != null && ctx.currentVars == null) {
      val setIsNull = if (!eval.isNull.isInstanceOf[LiteralValue]) {
        val globalIsNull = ctx.addMutableState(CodeGenerator.JAVA_BOOLEAN, "globalIsNull")
        val localIsNull = eval.isNull
        eval.isNull = JavaCode.isNullGlobal(globalIsNull)
        s"$globalIsNull = $localIsNull;"
      } else {
        ""
      }

      val javaType = CodeGenerator.javaType(dataType)
      val newValue = ctx.freshName("value")

      val funcName = ctx.freshName(nodeName)
      val funcFullName = ctx.addNewFunction(funcName,
        s"""
           |private $javaType $funcName(InternalRow ${ctx.INPUT_ROW}) {
           |  ${eval.code}
           |  $setIsNull
           |  return ${eval.value};
           |}
           """.stripMargin)

      eval.value = JavaCode.variable(newValue, dataType)
      eval.code = code"$javaType $newValue = $funcFullName(${ctx.INPUT_ROW});"
    }
  }

  /**
    * Returns Java source code that can be compiled to evaluate this expression.
    * The default behavior is to call the eval method of the expression. Concrete expression
    * implementations should override this to do actual code generation.
    *
    * @param ctx a [[CodegenContext]]
    * @param ev an [[ExprCode]] with unique terms.
    * @return an [[ExprCode]] containing the Java source code to generate the given expression
    */
  protected def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode

  /**
    * Returns `true` if this expression and all its children have been resolved to a specific schema
    * and input data types checking passed, and `false` if it still contains any unresolved
    * placeholders or has data types mismatch.
    * Implementations of expressions should override this if the resolution of this type of
    * expression involves more than just the resolution of its children and type checking.
    */
  lazy val resolved: Boolean = childrenResolved && checkInputDataTypes().isSuccess

  /**
    * Returns the [[DataType]] of the result of evaluating this expression.  It is
    * invalid to query the dataType of an unresolved expression (i.e., when `resolved` == false).
    */
  def dataType: DataType

  /**
    * Returns true if  all the children of this expression have been resolved to a specific schema
    * and false if any still contains any unresolved placeholders.
    */
  def childrenResolved: Boolean = children.forall(_.resolved)

  /**
    * 规范化处理：在确保输出的结果相同的条件下，对表达式进行改写，消除外观差异（大小写）
    *
    * 具体可查看[[Canonicalize]]
    * 确定性为true的表达式规范化结果也是固定的
    */
  lazy val canonicalized: Expression = {
    val canonicalizedChildren = children.map(_.canonicalized)
    Canonicalize.execute(withNewChildren(canonicalizedChildren))
  }

  /**
    * 判断两个表达式语义是否等价
    *
    * 判断条件：
    * 1、两个表达式确定性为true
    * 2、两个表达式规范化结果相同
    */
  def semanticEquals(other: Expression): Boolean =
    deterministic && other.deterministic && canonicalized == other.canonicalized

  /**
    * 语义hash，返回规范化后的表达式的hashcode
    *
    * See [[Canonicalize]] for more details.
    */
  def semanticHash(): Int = canonicalized.hashCode()

  /**
    * 检查输入数据类型，如果有效则返回TypeCheckResult.success，如果无效则返回错误信息的TypeCheckResult
    * 只有childrenResolved == true才能调用此方法
    */
  def checkInputDataTypes(): TypeCheckResult = TypeCheckResult.TypeCheckSuccess

  /**
    * Returns a user-facing string representation of this expression's name.
    * This should usually match the name of the function in SQL.
    */
  def prettyName: String = nodeName.toLowerCase(Locale.ROOT)

  protected def flatArguments: Iterator[Any] = productIterator.flatMap {
    case t: Traversable[_] => t
    case single => single :: Nil
  }

  // Marks this as final, Expression.verboseString should never be called, and thus shouldn't be
  // overridden by concrete classes.
  final override def verboseString: String = simpleString

  override def simpleString: String = toString

  override def toString: String = prettyName + Utils.truncatedString(
    flatArguments.toSeq, "(", ", ", ")")

  /**
    * Returns SQL representation of this expression.  For expressions extending [[NonSQLExpression]],
    * this method may return an arbitrary user facing string.
    */
  def sql: String = {
    val childrenSQL = children.map(_.sql).mkString(", ")
    s"$prettyName($childrenSQL)"
  }
}


/**
  * 非可执行的表达式，用于生命周期不超过analyze和optimize的表达式，例如Star表达式
  * 调用eval()会抛异常
  */
trait Unevaluable extends Expression {

  final override def eval(input: InternalRow = null): Any =
    throw new UnsupportedOperationException(s"Cannot evaluate expression: $this")

  final override protected def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode =
    throw new UnsupportedOperationException(s"Cannot evaluate expression: $this")
}


/**
  * An expression that gets replaced at runtime (currently by the optimizer) into a different
  * expression for evaluation. This is mainly used to provide compatibility with other databases.
  * For example, we use this to support "nvl" by replacing it with "coalesce".
  *
  * A RuntimeReplaceable should have the original parameters along with a "child" expression in the
  * case class constructor, and define a normal constructor that accepts only the original
  * parameters. For an example, see [[Nvl]]. To make sure the explain plan and expression SQL
  * works correctly, the implementation should also override flatArguments method and sql method.
  */
trait RuntimeReplaceable extends UnaryExpression with Unevaluable {
  override def nullable: Boolean = child.nullable
  override def foldable: Boolean = child.foldable
  override def dataType: DataType = child.dataType
  // As this expression gets replaced at optimization with its `child" expression,
  // two `RuntimeReplaceable` are considered to be semantically equal if their "child" expressions
  // are semantically equal.
  override lazy val canonicalized: Expression = child.canonicalized
}


/**
  * Expressions that don't have SQL representation should extend this trait.  Examples are
  * `ScalaUDF`, `ScalaUDAF`, and object expressions like `MapObjects` and `Invoke`.
  */
trait NonSQLExpression extends Expression {
  final override def sql: String = {
    transform {
      case a: Attribute => new PrettyAttribute(a)
      case a: Alias => PrettyAttribute(a.sql, a.dataType)
    }.toString
  }
}


/**
  * 不确定的表达式，deterministic和foldable为false
  * 典型的实现类：MonotonicallyIncreasingID表达式、Rand表达式
  */
trait Nondeterministic extends Expression {
  final override lazy val deterministic: Boolean = false
  final override def foldable: Boolean = false

  @transient
  private[this] var initialized = false

  /**
    * 传入分区index并初始化，initialized标记为true
    * 子类要重写 [[initializeInternal()]].
    */
  final def initialize(partitionIndex: Int): Unit = {
    initializeInternal(partitionIndex)
    initialized = true
  }

  protected def initializeInternal(partitionIndex: Int): Unit

  /**
    * @inheritdoc
    * [[initialize()]]如果没有执行，抛出异常
    * 子类要重写 [[evalInternal()]].
    */
  final override def eval(input: InternalRow = null): Any = {
    require(initialized,
      s"Nondeterministic expression ${this.getClass.getName} should be initialized before eval.")
    evalInternal(input)
  }

  protected def evalInternal(input: InternalRow): Any
}

/**
  * An expression that contains mutable state. A stateful expression is always non-deterministic
  * because the results it produces during evaluation are not only dependent on the given input
  * but also on its internal state.
  *
  * The state of the expressions is generally not exposed in the parameter list and this makes
  * comparing stateful expressions problematic because similar stateful expressions (with the same
  * parameter list) but with different internal state will be considered equal. This is especially
  * problematic during tree transformations. In order to counter this the `fastEquals` method for
  * stateful expressions only returns `true` for the same reference.
  *
  * A stateful expression should never be evaluated multiple times for a single row. This should
  * only be a problem for interpreted execution. This can be prevented by creating fresh copies
  * of the stateful expression before execution, these can be made using the `freshCopy` function.
  */
trait Stateful extends Nondeterministic {
  /**
    * Return a fresh uninitialized copy of the stateful expression.
    */
  def freshCopy(): Stateful

  /**
    * Only the same reference is considered equal.
    */
  override def fastEquals(other: TreeNode[_]): Boolean = this eq other
}

/**
  * 叶子表达式：没有子表达式的表达式
  */
abstract class LeafExpression extends Expression {

  override final def children: Seq[Expression] = Nil
}


/**
  * 只有一个子表达式的表达式，例如Abs表达式、UpCast表达式
  */
abstract class UnaryExpression extends Expression {

  def child: Expression

  override final def children: Seq[Expression] = child :: Nil

  override def foldable: Boolean = child.foldable
  override def nullable: Boolean = child.nullable

  /**
    * 子表达式若有一个执行结果为null，则该表达式执行结果为null
    */
  override def eval(input: InternalRow): Any = {
    val value = child.eval(input)
    if (value == null) {
      null
    } else {
      nullSafeEval(value)
    }
  }

  /**
    * 当子表达式执行结果不为null时的执行逻辑
    */
  protected def nullSafeEval(input: Any): Any =
    sys.error(s"UnaryExpressions must override either eval or nullSafeEval")

  /**
    * Called by unary expressions to generate a code block that returns null if its parent returns
    * null, and if not null, use `f` to generate the expression.
    *
    * As an example, the following does a boolean inversion (i.e. NOT).
    * {{{
    *   defineCodeGen(ctx, ev, c => s"!($c)")
    * }}}
    *
    * @param f function that accepts a variable name and returns Java code to compute the output.
    */
  protected def defineCodeGen(
                               ctx: CodegenContext,
                               ev: ExprCode,
                               f: String => String): ExprCode = {
    nullSafeCodeGen(ctx, ev, eval => {
      s"${ev.value} = ${f(eval)};"
    })
  }

  /**
    * Called by unary expressions to generate a code block that returns null if its parent returns
    * null, and if not null, use `f` to generate the expression.
    *
    * @param f function that accepts the non-null evaluation result name of child and returns Java
    *          code to compute the output.
    */
  protected def nullSafeCodeGen(
                                 ctx: CodegenContext,
                                 ev: ExprCode,
                                 f: String => String): ExprCode = {
    val childGen = child.genCode(ctx)
    val resultCode = f(childGen.value)

    if (nullable) {
      val nullSafeEval = ctx.nullSafeExec(child.nullable, childGen.isNull)(resultCode)
      ev.copy(code = code"""
        ${childGen.code}
        boolean ${ev.isNull} = ${childGen.isNull};
        ${CodeGenerator.javaType(dataType)} ${ev.value} = ${CodeGenerator.defaultValue(dataType)};
        $nullSafeEval
      """)
    } else {
      ev.copy(code = code"""
        ${childGen.code}
        ${CodeGenerator.javaType(dataType)} ${ev.value} = ${CodeGenerator.defaultValue(dataType)};
        $resultCode""", isNull = FalseLiteral)
    }
  }
}

/**
  * 有两个子表达式的表达式，例如四则运算、RLike表达式
  */
abstract class BinaryExpression extends Expression {

  def left: Expression
  def right: Expression

  override final def children: Seq[Expression] = Seq(left, right)

  override def foldable: Boolean = left.foldable && right.foldable

  override def nullable: Boolean = left.nullable || right.nullable

  /**
    * 子表达式若有一个执行结果为null，则该表达式执行结果为null
    */
  override def eval(input: InternalRow): Any = {
    val value1 = left.eval(input)
    if (value1 == null) {
      null
    } else {
      val value2 = right.eval(input)
      if (value2 == null) {
        null
      } else {
        nullSafeEval(value1, value2)
      }
    }
  }

  /**
    * 当子表达式执行结果不为null时的执行逻辑
    */
  protected def nullSafeEval(input1: Any, input2: Any): Any =
    sys.error(s"BinaryExpressions must override either eval or nullSafeEval")

  /**
    * Short hand for generating binary evaluation code.
    * If either of the sub-expressions is null, the result of this computation
    * is assumed to be null.
    *
    * @param f accepts two variable names and returns Java code to compute the output.
    */
  protected def defineCodeGen(
                               ctx: CodegenContext,
                               ev: ExprCode,
                               f: (String, String) => String): ExprCode = {
    nullSafeCodeGen(ctx, ev, (eval1, eval2) => {
      s"${ev.value} = ${f(eval1, eval2)};"
    })
  }

  /**
    * Short hand for generating binary evaluation code.
    * If either of the sub-expressions is null, the result of this computation
    * is assumed to be null.
    *
    * @param f function that accepts the 2 non-null evaluation result names of children
    *          and returns Java code to compute the output.
    */
  protected def nullSafeCodeGen(
                                 ctx: CodegenContext,
                                 ev: ExprCode,
                                 f: (String, String) => String): ExprCode = {
    val leftGen = left.genCode(ctx)
    val rightGen = right.genCode(ctx)
    val resultCode = f(leftGen.value, rightGen.value)

    if (nullable) {
      val nullSafeEval =
        leftGen.code + ctx.nullSafeExec(left.nullable, leftGen.isNull) {
          rightGen.code + ctx.nullSafeExec(right.nullable, rightGen.isNull) {
            s"""
              ${ev.isNull} = false; // resultCode could change nullability.
              $resultCode
            """
          }
        }

      ev.copy(code = code"""
        boolean ${ev.isNull} = true;
        ${CodeGenerator.javaType(dataType)} ${ev.value} = ${CodeGenerator.defaultValue(dataType)};
        $nullSafeEval
      """)
    } else {
      ev.copy(code = code"""
        ${leftGen.code}
        ${rightGen.code}
        ${CodeGenerator.javaType(dataType)} ${ev.value} = ${CodeGenerator.defaultValue(dataType)};
        $resultCode""", isNull = FalseLiteral)
    }
  }
}


/**
  * A [[BinaryExpression]] that is an operator, with two properties:
  *
  * 1. The string representation is "x symbol y", rather than "funcName(x, y)".
  * 2. Two inputs are expected to be of the same type. If the two inputs have different types,
  *    the analyzer will find the tightest common type and do the proper type casting.
  */
abstract class BinaryOperator extends BinaryExpression with ExpectsInputTypes {

  /**
    * Expected input type from both left/right child expressions, similar to the
    * [[ImplicitCastInputTypes]] trait.
    */
  def inputType: AbstractDataType

  def symbol: String

  def sqlOperator: String = symbol

  override def toString: String = s"($left $symbol $right)"

  override def inputTypes: Seq[AbstractDataType] = Seq(inputType, inputType)

  override def checkInputDataTypes(): TypeCheckResult = {
    // First check whether left and right have the same type, then check if the type is acceptable.
    if (!left.dataType.sameType(right.dataType)) {
      TypeCheckResult.TypeCheckFailure(s"differing types in '$sql' " +
        s"(${left.dataType.catalogString} and ${right.dataType.catalogString}).")
    } else if (!inputType.acceptsType(left.dataType)) {
      TypeCheckResult.TypeCheckFailure(s"'$sql' requires ${inputType.simpleString} type," +
        s" not ${left.dataType.catalogString}")
    } else {
      TypeCheckResult.TypeCheckSuccess
    }
  }

  override def sql: String = s"(${left.sql} $sqlOperator ${right.sql})"
}


object BinaryOperator {
  def unapply(e: BinaryOperator): Option[(Expression, Expression)] = Some((e.left, e.right))
}

/**
  * 三元表达式，例如substring，子节点为字符串、下标、长度
  */
abstract class TernaryExpression extends Expression {

  override def foldable: Boolean = children.forall(_.foldable)

  override def nullable: Boolean = children.exists(_.nullable)

  /**
    * 子表达式若有一个执行结果为null，则该表达式执行结果为null
    */
  override def eval(input: InternalRow): Any = {
    val exprs = children
    val value1 = exprs(0).eval(input)
    if (value1 != null) {
      val value2 = exprs(1).eval(input)
      if (value2 != null) {
        val value3 = exprs(2).eval(input)
        if (value3 != null) {
          return nullSafeEval(value1, value2, value3)
        }
      }
    }
    null
  }

  /**
    * 当子表达式执行结果不为null时的执行逻辑
    */
  protected def nullSafeEval(input1: Any, input2: Any, input3: Any): Any =
    sys.error(s"TernaryExpressions must override either eval or nullSafeEval")

  /**
    * Short hand for generating ternary evaluation code.
    * If either of the sub-expressions is null, the result of this computation
    * is assumed to be null.
    *
    * @param f accepts three variable names and returns Java code to compute the output.
    */
  protected def defineCodeGen(
                               ctx: CodegenContext,
                               ev: ExprCode,
                               f: (String, String, String) => String): ExprCode = {
    nullSafeCodeGen(ctx, ev, (eval1, eval2, eval3) => {
      s"${ev.value} = ${f(eval1, eval2, eval3)};"
    })
  }

  /**
    * Short hand for generating ternary evaluation code.
    * If either of the sub-expressions is null, the result of this computation
    * is assumed to be null.
    *
    * @param f function that accepts the 3 non-null evaluation result names of children
    *          and returns Java code to compute the output.
    */
  protected def nullSafeCodeGen(
                                 ctx: CodegenContext,
                                 ev: ExprCode,
                                 f: (String, String, String) => String): ExprCode = {
    val leftGen = children(0).genCode(ctx)
    val midGen = children(1).genCode(ctx)
    val rightGen = children(2).genCode(ctx)
    val resultCode = f(leftGen.value, midGen.value, rightGen.value)

    if (nullable) {
      val nullSafeEval =
        leftGen.code + ctx.nullSafeExec(children(0).nullable, leftGen.isNull) {
          midGen.code + ctx.nullSafeExec(children(1).nullable, midGen.isNull) {
            rightGen.code + ctx.nullSafeExec(children(2).nullable, rightGen.isNull) {
              s"""
                ${ev.isNull} = false; // resultCode could change nullability.
                $resultCode
              """
            }
          }
        }

      ev.copy(code = code"""
        boolean ${ev.isNull} = true;
        ${CodeGenerator.javaType(dataType)} ${ev.value} = ${CodeGenerator.defaultValue(dataType)};
        $nullSafeEval""")
    } else {
      ev.copy(code = code"""
        ${leftGen.code}
        ${midGen.code}
        ${rightGen.code}
        ${CodeGenerator.javaType(dataType)} ${ev.value} = ${CodeGenerator.defaultValue(dataType)};
        $resultCode""", isNull = FalseLiteral)
    }
  }
}

/**
  * A trait resolving nullable, containsNull, valueContainsNull flags of the output date type.
  * This logic is usually utilized by expressions combining data from multiple child expressions
  * of non-primitive types (e.g. [[CaseWhen]]).
  */
trait ComplexTypeMergingExpression extends Expression {

  /**
    * A collection of data types used for resolution the output type of the expression. By default,
    * data types of all child expressions. The collection must not be empty.
    */
  @transient
  lazy val inputTypesForMerging: Seq[DataType] = children.map(_.dataType)

  def dataTypeCheck: Unit = {
    require(
      inputTypesForMerging.nonEmpty,
      "The collection of input data types must not be empty.")
    require(
      TypeCoercion.haveSameType(inputTypesForMerging),
      "All input types must be the same except nullable, containsNull, valueContainsNull flags." +
        s" The input types found are\n\t${inputTypesForMerging.mkString("\n\t")}")
  }

  override def dataType: DataType = {
    dataTypeCheck
    inputTypesForMerging.reduceLeft(TypeCoercion.findCommonTypeDifferentOnlyInNullFlags(_, _).get)
  }
}

/**
  * Common base trait for user-defined functions, including UDF/UDAF/UDTF of different languages
  * and Hive function wrappers.
  */
trait UserDefinedExpression
