
package org.apache.spark.daslab.sql.engine.expressions.codegen

import org.apache.spark.daslab.sql.engine.expressions.{Expression, LeafExpression, Nondeterministic}
import org.apache.spark.daslab.sql.engine.expressions.codegen.Block._

/**
 * A trait that can be used to provide a fallback mode for expression code generation.
 */
trait CodegenFallback extends Expression {

  protected def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = {
    // LeafNode does not need `input`
    val input = if (this.isInstanceOf[LeafExpression]) "null" else ctx.INPUT_ROW
    val idx = ctx.references.length
    ctx.references += this
    var childIndex = idx
    this.foreach {
      case n: Nondeterministic =>
        // This might add the current expression twice, but it won't hurt.
        ctx.references += n
        childIndex += 1
        ctx.addPartitionInitializationStatement(
          s"""
             |((Nondeterministic) references[$childIndex])
             |  .initialize(partitionIndex);
          """.stripMargin)
      case _ =>
    }
    val objectTerm = ctx.freshName("obj")
    val placeHolder = ctx.registerComment(this.toString)
    val javaType = CodeGenerator.javaType(this.dataType)
    if (nullable) {
      ev.copy(code = code"""
        $placeHolder
        Object $objectTerm = ((Expression) references[$idx]).eval($input);
        boolean ${ev.isNull} = $objectTerm == null;
        $javaType ${ev.value} = ${CodeGenerator.defaultValue(this.dataType)};
        if (!${ev.isNull}) {
          ${ev.value} = (${CodeGenerator.boxedType(this.dataType)}) $objectTerm;
        }""")
    } else {
      ev.copy(code = code"""
        $placeHolder
        Object $objectTerm = ((Expression) references[$idx]).eval($input);
        $javaType ${ev.value} = (${CodeGenerator.boxedType(this.dataType)}) $objectTerm;
        """, isNull = FalseLiteral)
    }
  }
}
