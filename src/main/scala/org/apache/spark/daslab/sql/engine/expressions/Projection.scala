package org.apache.spark.daslab.sql.engine.expressions

import scala.util.control.NonFatal

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions.codegen.{GenerateSafeProjection, GenerateUnsafeProjection}
import org.apache.spark.daslab.sql.types.{DataType, StructType}

/**
  *   一个通过调用每个指定的expressions的eval方法来计算[[Projection]]的投影实现
  * @param expressions a sequence of expressions that determine the value of each column of the
  *                    output row.
  */
class InterpretedProjection(expressions: Seq[Expression]) extends Projection {
  def this(expressions: Seq[Expression], inputSchema: Seq[Attribute]) =
    this(expressions.map(BindReferences.bindReference(_, inputSchema)))

  override def initialize(partitionIndex: Int): Unit = {
    expressions.foreach(_.foreach {
      case n: Nondeterministic => n.initialize(partitionIndex)
      case _ =>
    })
  }


  // 当Kryo（序列化机制）调用无参的构造器的时候，null check是需要的
  protected val exprArray = if ( expressions != null )  expressions.toArray else null

  def apply(input: InternalRow): InternalRow = {
    val outputArray = new Array[Any](exprArray.length)
    var i = 0
    while (i < exprArray.length) {
      outputArray(i) = exprArray(i).eval(input)
      i += 1
    }
    new GenericInternalRow(outputArray)
  }

  override def toString(): String = s"Row => [${exprArray.mkString(",")}]"
}

/**
  * A [[MutableProjection]] that is calculated by calling `eval` on each of the specified
  * expressions.
  *
  * @param expressions a sequence of expressions that determine the value of each column of the
  *                    output row.
  */
case class InterpretedMutableProjection(expressions: Seq[Expression]) extends MutableProjection {
  def this(expressions: Seq[Expression], inputSchema: Seq[Attribute]) =
    this(expressions.map(BindReferences.bindReference(_, inputSchema)))

  private[this] val buffer = new Array[Any](expressions.size)

  override def initialize(partitionIndex: Int): Unit = {
    expressions.foreach(_.foreach {
      case n: Nondeterministic => n.initialize(partitionIndex)
      case _ =>
    })
  }

  private[this] val exprArray = expressions.toArray
  private[this] var mutableRow: InternalRow = new GenericInternalRow(exprArray.length)
  def currentValue: InternalRow = mutableRow

  override def target(row: InternalRow): MutableProjection = {
    mutableRow = row
    this
  }

  override def apply(input: InternalRow): InternalRow = {
    var i = 0
    while (i < exprArray.length) {
      // Store the result into buffer first, to make the projection atomic (needed by aggregation)
      buffer(i) = exprArray(i).eval(input)
      i += 1
    }
    i = 0
    while (i < exprArray.length) {
      mutableRow(i) = buffer(i)
      i += 1
    }
    mutableRow
  }
}

/**
  *  返回UnsafeRow的projection
  *
  * CAUTION: the returned projection object should *not* be assumed to be thread-safe.
  */
abstract class UnsafeProjection extends Projection {
  override def apply(row: InternalRow): UnsafeRow
}

/**
  *  UnsafeProjection的工厂对象
  */
object UnsafeProjection
  extends CodeGeneratorWithInterpretedFallback[Seq[Expression], UnsafeProjection] {

  override protected def createCodeGeneratedObject(in: Seq[Expression]): UnsafeProjection = {
    GenerateUnsafeProjection.generate(in)
  }

  override protected def createInterpretedObject(in: Seq[Expression]): UnsafeProjection = {
    InterpretedUnsafeProjection.createProjection(in)
  }


  // 和schema进行绑定
  protected def toBoundExprs(
                              exprs: Seq[Expression],
                              inputSchema: Seq[Attribute]): Seq[Expression] = {
    exprs.map(BindReferences.bindReference(_, inputSchema))
  }


  protected def toUnsafeExprs(exprs: Seq[Expression]): Seq[Expression] = {
    exprs.map(_ transform {
      case CreateNamedStruct(children) => CreateNamedStructUnsafe(children)
    })
  }

  /**
    * 根据给定的StructType来返回一个UnsafeProjection
    * CAUTION: the returned projection object is *not* thread-safe.
    */
  def create(schema: StructType): UnsafeProjection = create(schema.fields.map(_.dataType))

  /**
    * 根据给定的DataTypes数组来返回UnsafeProjection
    * CAUTION: the returned projection object is *not* thread-safe.
    */
  def create(fields: Array[DataType]): UnsafeProjection = {
    create(fields.zipWithIndex.map(x => BoundReference(x._2, x._1, true)))
  }

  /**
    *  根据给定的bound Expression序列来返回UnsafeProjection
    *  todo
    */
  def create(exprs: Seq[Expression]): UnsafeProjection = {
    createObject(toUnsafeExprs(exprs))
  }

  def create(expr: Expression): UnsafeProjection = create(Seq(expr))

  /**
    * Returns an UnsafeProjection for given sequence of Expressions, which will be bound to
    * `inputSchema`.
    *  根据给定的Expression序列和inputSchema来构造UnsafeProjection
    */
  def create(exprs: Seq[Expression], inputSchema: Seq[Attribute]): UnsafeProjection = {
    create(toBoundExprs(exprs, inputSchema))
  }

  /**
    *  和其他的工厂方法一样，但是允许开启/关闭 subexpression 消除
    *  subexpressionEliminationEnabled参数不能保证一定可以执行
    *  当执行遇到异常退回到 interpreted执行的时候，就不再支持
    */
  def create(
              exprs: Seq[Expression],
              inputSchema: Seq[Attribute],
              subexpressionEliminationEnabled: Boolean): UnsafeProjection = {
    val unsafeExprs = toUnsafeExprs(toBoundExprs(exprs, inputSchema))
    try {
      GenerateUnsafeProjection.generate(unsafeExprs, subexpressionEliminationEnabled)
    } catch {
      case NonFatal(_) =>
        // We should have already seen the error message in `CodeGenerator`
        logWarning("Expr codegen error and falling back to interpreter mode")
        InterpretedUnsafeProjection.createProjection(unsafeExprs)
    }
  }
}

/**
  * A projection that could turn UnsafeRow into GenericInternalRow
  */
object FromUnsafeProjection {

  /**
    * Returns a Projection for given StructType.
    */
  def apply(schema: StructType): Projection = {
    apply(schema.fields.map(_.dataType))
  }

  /**
    * Returns an UnsafeProjection for given Array of DataTypes.
    */
  def apply(fields: Seq[DataType]): Projection = {
    create(fields.zipWithIndex.map(x => new BoundReference(x._2, x._1, true)))
  }

  /**
    * Returns a Projection for given sequence of Expressions (bounded).
    */
  private def create(exprs: Seq[Expression]): Projection = {
    GenerateSafeProjection.generate(exprs)
  }
}
