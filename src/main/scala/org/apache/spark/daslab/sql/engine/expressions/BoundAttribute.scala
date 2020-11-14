package org.apache.spark.daslab.sql.engine.expressions

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.errors.attachTree
import org.apache.spark.daslab.sql.engine.expressions.codegen.{CodegenContext, CodeGenerator, ExprCode, FalseLiteral, JavaCode}
import org.apache.spark.daslab.sql.engine.expressions.codegen.Block._
import org.apache.spark.daslab.sql.types._


//todo spark core
import org.apache.spark.internal.Logging

/**
 * A bound reference points to a specific slot in the input tuple, allowing the actual value
 * to be retrieved more efficiently.  However, since operations like column pruning can change
 * the layout of intermediate tuples, BindReferences should be run after all such transformations.
  *
  *  一个BoundReference指向InternalRow中的某个slot，并且根据数据类型来获取accessor,可以更加有效的获取
  *  对应数据。
  *  因为类似列裁剪之类的操作有可能改变intermediate tuples的数据layout，BindReferences应该在所有这些
  *  变换之后执行
 */
case class BoundReference(ordinal: Int, dataType: DataType, nullable: Boolean)
  extends LeafExpression {

  override def toString: String = s"input[$ordinal, ${dataType.simpleString}, $nullable]"


  // 第二个参数是下标，根据下标从Internal中取出具体的数据
  private val accessor: (InternalRow, Int) => Any = InternalRow.getAccessor(dataType)

  // Use special getter for primitive types (for UnsafeRow)
  // 处理可以为null的情况
  override def eval(input: InternalRow): Any = {
    if (nullable && input.isNullAt(ordinal)) {
      null
    } else {
      accessor(input, ordinal)
    }
  }

  override def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = {
    if (ctx.currentVars != null && ctx.currentVars(ordinal) != null){
      val oev = ctx.currentVars(ordinal)
      ev.isNull = oev.isNull
      ev.value = oev.value
      ev.copy(code = oev.code)
    } else {
      assert(ctx.INPUT_ROW != null, "INPUT_ROW and currentVars cannot both be null.")
      val javaType = JavaCode.javaType(dataType)
      val value = CodeGenerator.getValue(ctx.INPUT_ROW, dataType, ordinal.toString)
      if (nullable) {
        ev.copy(code =
          code"""
                |boolean ${ev.isNull} = ${ctx.INPUT_ROW}.isNullAt($ordinal);
                |$javaType ${ev.value} = ${ev.isNull} ?
                |  ${CodeGenerator.defaultValue(dataType)} : ($value);
           """.stripMargin)
      } else {
        ev.copy(code = code"$javaType ${ev.value} = $value;", isNull = FalseLiteral)
      }
    }
  }
}

object BindReferences extends Logging {

  // 这里进行绑定指的是将expression与inputSchema中的某个AttributeSeq进行绑定
  // 绑定的依据是exprId
  def bindReference[A <: Expression](
                                      expression: A,
                                      input: AttributeSeq,
                                      allowFailures: Boolean = false): A = {
    expression.transform { case a: AttributeReference =>
      attachTree(a, "Binding attribute") {
        val ordinal = input.indexOf(a.exprId)
        if (ordinal == -1) {
          if (allowFailures) {
            a
          } else {
            sys.error(s"Couldn't find $a in ${input.attrs.mkString("[", ",", "]")}")
          }
        } else {
          //
          BoundReference(ordinal, a.dataType, input(ordinal).nullable)
        }
      }
    }.asInstanceOf[A] // Kind of a hack, but safe.  TODO: Tighten return type when possible.
  }
}
