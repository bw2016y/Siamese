package org.apache.spark.daslab.sql.engine.expressions

import org.apache.spark.daslab.sql.engine.expressions.codegen._
import org.apache.spark.daslab.sql.types._



/**
 * A function that calculates bitwise and(&) of two numbers.
 *
 * Code generation inherited from BinaryArithmetic.
 */
@ExpressionDescription(
  usage = "expr1 _FUNC_ expr2 - Returns the result of bitwise AND of `expr1` and `expr2`.",
  examples = """
    Examples:
      > SELECT 3 _FUNC_ 5;
       1
  """)
case class BitwiseAnd(left: Expression, right: Expression) extends BinaryArithmetic {

  override def inputType: AbstractDataType = IntegralType

  override def symbol: String = "&"

  private lazy val and: (Any, Any) => Any = dataType match {
    case ByteType =>
      ((evalE1: Byte, evalE2: Byte) => (evalE1 & evalE2).toByte).asInstanceOf[(Any, Any) => Any]
    case ShortType =>
      ((evalE1: Short, evalE2: Short) => (evalE1 & evalE2).toShort).asInstanceOf[(Any, Any) => Any]
    case IntegerType =>
      ((evalE1: Int, evalE2: Int) => evalE1 & evalE2).asInstanceOf[(Any, Any) => Any]
    case LongType =>
      ((evalE1: Long, evalE2: Long) => evalE1 & evalE2).asInstanceOf[(Any, Any) => Any]
  }

  protected override def nullSafeEval(input1: Any, input2: Any): Any = and(input1, input2)
}

/**
 * A function that calculates bitwise or(|) of two numbers.
 *
 * Code generation inherited from BinaryArithmetic.
 */
@ExpressionDescription(
  usage = "expr1 _FUNC_ expr2 - Returns the result of bitwise OR of `expr1` and `expr2`.",
  examples = """
    Examples:
      > SELECT 3 _FUNC_ 5;
       7
  """)
case class BitwiseOr(left: Expression, right: Expression) extends BinaryArithmetic {

  override def inputType: AbstractDataType = IntegralType

  override def symbol: String = "|"

  private lazy val or: (Any, Any) => Any = dataType match {
    case ByteType =>
      ((evalE1: Byte, evalE2: Byte) => (evalE1 | evalE2).toByte).asInstanceOf[(Any, Any) => Any]
    case ShortType =>
      ((evalE1: Short, evalE2: Short) => (evalE1 | evalE2).toShort).asInstanceOf[(Any, Any) => Any]
    case IntegerType =>
      ((evalE1: Int, evalE2: Int) => evalE1 | evalE2).asInstanceOf[(Any, Any) => Any]
    case LongType =>
      ((evalE1: Long, evalE2: Long) => evalE1 | evalE2).asInstanceOf[(Any, Any) => Any]
  }

  protected override def nullSafeEval(input1: Any, input2: Any): Any = or(input1, input2)
}

/**
 * A function that calculates bitwise xor({@literal ^}) of two numbers.
 *
 * Code generation inherited from BinaryArithmetic.
 */
@ExpressionDescription(
  usage = "expr1 _FUNC_ expr2 - Returns the result of bitwise exclusive OR of `expr1` and `expr2`.",
  examples = """
    Examples:
      > SELECT 3 _FUNC_ 5;
       6
  """)
case class BitwiseXor(left: Expression, right: Expression) extends BinaryArithmetic {

  override def inputType: AbstractDataType = IntegralType

  override def symbol: String = "^"

  private lazy val xor: (Any, Any) => Any = dataType match {
    case ByteType =>
      ((evalE1: Byte, evalE2: Byte) => (evalE1 ^ evalE2).toByte).asInstanceOf[(Any, Any) => Any]
    case ShortType =>
      ((evalE1: Short, evalE2: Short) => (evalE1 ^ evalE2).toShort).asInstanceOf[(Any, Any) => Any]
    case IntegerType =>
      ((evalE1: Int, evalE2: Int) => evalE1 ^ evalE2).asInstanceOf[(Any, Any) => Any]
    case LongType =>
      ((evalE1: Long, evalE2: Long) => evalE1 ^ evalE2).asInstanceOf[(Any, Any) => Any]
  }

  protected override def nullSafeEval(input1: Any, input2: Any): Any = xor(input1, input2)
}

/**
 * A function that calculates bitwise not(~) of a number.
 */
@ExpressionDescription(
  usage = "_FUNC_ expr - Returns the result of bitwise NOT of `expr`.",
  examples = """
    Examples:
      > SELECT _FUNC_ 0;
       -1
  """)
case class BitwiseNot(child: Expression) extends UnaryExpression with ExpectsInputTypes {

  override def inputTypes: Seq[AbstractDataType] = Seq(IntegralType)

  override def dataType: DataType = child.dataType

  override def toString: String = s"~$child"

  private lazy val not: (Any) => Any = dataType match {
    case ByteType =>
      ((evalE: Byte) => (~evalE).toByte).asInstanceOf[(Any) => Any]
    case ShortType =>
      ((evalE: Short) => (~evalE).toShort).asInstanceOf[(Any) => Any]
    case IntegerType =>
      ((evalE: Int) => ~evalE).asInstanceOf[(Any) => Any]
    case LongType =>
      ((evalE: Long) => ~evalE).asInstanceOf[(Any) => Any]
  }

  override def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = {
    defineCodeGen(ctx, ev, c => s"(${CodeGenerator.javaType(dataType)}) ~($c)")
  }

  protected override def nullSafeEval(input: Any): Any = not(input)

  override def sql: String = s"~${child.sql}"
}

