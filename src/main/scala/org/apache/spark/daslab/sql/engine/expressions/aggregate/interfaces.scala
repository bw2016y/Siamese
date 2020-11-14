package org.apache.spark.daslab.sql.engine.expressions.aggregate


import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.analysis.UnresolvedAttribute
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.expressions.codegen.CodegenFallback
import org.apache.spark.daslab.sql.types._

/**
  *  一个[[AggregateFunction]]的模式
  */
sealed trait AggregateMode


/**
  *  使用[[Partial]]模式的[[AggregateFunction]]就是partial aggregation
  *  这个模式使用偏函数（含有这个聚合的语义）的原始输入来更新指定的 aggregation buffer（可以理解为局部数据的聚合）
  *  当处理完所有的input rows就直接返回aggregation buffer
  */
case object Partial extends AggregateMode

/**
  *  使用[[PartialMerge]]的[[AggregateFunction]]用于合并包含中间结果的aggregation buffers
  *  这个函数通过合并多个aggregation buffers来更新指定的aggregation buffer
  *  当处理完所有的input rows，就返回这个aggregation buffer
  *
  *  一般来说合并的结果仍然不是最终的结果，需要进一步的处理（主要应用在distinct语句中）
  */
case object PartialMerge extends AggregateMode


/**
  *   使用[[Final]]模式的[[AggregateFunction]]主要用于merge aggregation buffers中的中间结果
  *   并生成最终的结果。
  *   这个函数通过合并多个aggregate buffers来更新aggregate buffer
  *   当处理完所有的input rows，就返回最终的结果
  */
case object Final extends AggregateMode


/**
  *  使用[[Complete]]模式的[[AggregateFunction]]意味着不进行局部聚合的情况下直接根据原始的input rows来计算结果
  *  这个函数使用原始的输入来更新给定的aggregation buffer，当处理完所有的input rows，就返回最终的结果
  *  一般来说Complete模式应用在不支持Partial模式的聚合函数中
  */
case object Complete extends AggregateMode

/**
 * 在code-gen中使用的占位表达式，不改变row中对应的值
 * A place holder expressions used in code-gen, it does not change the corresponding value
 * in the row.
 */
case object NoOp extends Expression with Unevaluable {
  override def nullable: Boolean = true
  override def dataType: DataType = NullType
  override def children: Seq[Expression] = Nil
}

object AggregateExpression {
  def apply(
             aggregateFunction: AggregateFunction,
             mode: AggregateMode,
             isDistinct: Boolean): AggregateExpression = {
    AggregateExpression(
      aggregateFunction,
      mode,
      isDistinct,
      NamedExpression.newExprId)
  }
}

/**
 * A container for an [[AggregateFunction]] with its [[AggregateMode]] and a field
 * (`isDistinct`) indicating if DISTINCT keyword is specified for this function.
  *
  *  是一个聚合函数[[AggregateFunction]]和[[AggregateMode]]还有一个标识其是否有DISTINCT关键词
  *  修饰的isDistinct的域的container
  *
 */
case class AggregateExpression(
                                aggregateFunction: AggregateFunction,
                                mode: AggregateMode,
                                isDistinct: Boolean,
                                resultId: ExprId)
  extends Expression
    with Unevaluable {
  // 表示聚合的结果
  lazy val resultAttribute: Attribute = if (aggregateFunction.resolved) {
    AttributeReference(
      aggregateFunction.toString,
      aggregateFunction.dataType,
      aggregateFunction.nullable)(exprId = resultId)
  } else {
    // This is a bit of a hack.  Really we should not be constructing this container and reasoning
    // about datatypes / aggregation mode until after we have finished analysis and made it to
    // planning.
    UnresolvedAttribute(aggregateFunction.toString)
  }

  // We compute the same thing regardless of our final result.
  override lazy val canonicalized: Expression = {
    val normalizedAggFunc = mode match {
      // For PartialMerge or Final mode, the input to the `aggregateFunction` is aggregate buffers,
      // and the actual children of `aggregateFunction` is not used, here we normalize the expr id.
      case PartialMerge | Final => aggregateFunction.transform {
        case a: AttributeReference => a.withExprId(ExprId(0))
      }
      case Partial | Complete => aggregateFunction
    }

    AggregateExpression(
      normalizedAggFunc.canonicalized.asInstanceOf[AggregateFunction],
      mode,
      isDistinct,
      ExprId(0))
  }

  override def children: Seq[Expression] = aggregateFunction :: Nil
  override def dataType: DataType = aggregateFunction.dataType
  override def foldable: Boolean = false
  override def nullable: Boolean = aggregateFunction.nullable

  override def references: AttributeSet = {
    mode match {
      case Partial | Complete => aggregateFunction.references
      case PartialMerge | Final => AttributeSet(aggregateFunction.aggBufferAttributes)
    }
  }

  // 打印mode作为前缀
  override def toString: String = {
    val prefix = mode match {
      case Partial => "partial_"
      case PartialMerge => "merge_"
      case Final | Complete => ""
    }
    prefix + aggregateFunction.toAggString(isDistinct)
  }

  override def sql: String = aggregateFunction.sql(isDistinct)
}

/**
 * AggregateFunction is the superclass of two aggregation function interfaces:
 *
 *  - [[ImperativeAggregate]] is for aggregation functions that are specified in terms of
 *    initialize(), update(), and merge() functions that operate on Row-based aggregation buffers.
 *  - [[DeclarativeAggregate]] is for aggregation functions that are specified using
 *    Catalyst expressions.
 *
 * In both interfaces, aggregates must define the schema ([[aggBufferSchema]]) and attributes
 * ([[aggBufferAttributes]]) of an aggregation buffer which is used to hold partial aggregate
 * results. At runtime, multiple aggregate functions are evaluated by the same operator using a
 * combined aggregation buffer which concatenates the aggregation buffers of the individual
 * aggregate functions.
 *
 * Code which accepts [[AggregateFunction]] instances should be prepared to handle both types of
 * aggregate functions.
 */
abstract class AggregateFunction extends Expression {

  /** An aggregate function is not foldable. */
  final override def foldable: Boolean = false

  /** The schema of the aggregation buffer. */
  // 聚合缓冲区的Schema信息
  def aggBufferSchema: StructType

  /** Attributes of fields in aggBufferSchema. */
  // 聚合缓冲区的数据列信息
  def aggBufferAttributes: Seq[AttributeReference]

  /**
   * Attributes of fields in input aggregation buffers (immutable aggregation buffers that are
   * merged with mutable aggregation buffers in the merge() function or merge expressions).
   * These attributes are created automatically by cloning the [[aggBufferAttributes]].
   */
  /**
    *     input agg buffers的列的Attributes
    *
    *     这些属性是通过自动通过cloning[[aggBufferAttributes]]来创建的
    * @return
    */
  def inputAggBufferAttributes: Seq[AttributeReference]

  /**
   * Result of the aggregate function when the input is empty. This is currently only used for the
   * proper rewriting of distinct aggregate functions.
    *  当输入是空的时候聚合函数的结果
    *  当前只在适当重写distinct aggregate function的时候被用到了
   */
  def defaultResult: Option[Literal] = None

  /**
   * Creates [[AggregateExpression]] with `isDistinct` flag disabled.
   * 默认是false
   * @see `toAggregateExpression(isDistinct: Boolean)` for detailed description
   */
  def toAggregateExpression(): AggregateExpression = toAggregateExpression(isDistinct = false)

  /**
   * Wraps this [[AggregateFunction]] in an [[AggregateExpression]] and sets `isDistinct`
   * flag of the [[AggregateExpression]] to the given value because
   * [[AggregateExpression]] is the container of an [[AggregateFunction]], aggregation mode,
   * and the flag indicating if this aggregation is distinct aggregation or not.
   * An [[AggregateFunction]] should not be used without being wrapped in
   * an [[AggregateExpression]].
   */
  def toAggregateExpression(isDistinct: Boolean): AggregateExpression = {
    AggregateExpression(aggregateFunction = this, mode = Complete, isDistinct = isDistinct)
  }

  def sql(isDistinct: Boolean): String = {
    val distinct = if (isDistinct) "DISTINCT " else ""
    s"$prettyName($distinct${children.map(_.sql).mkString(", ")})"
  }


  /**
    *  在解释逻辑计划的时候用于说明的字符串表示
    * @param isDistinct
    * @return
    */
  def toAggString(isDistinct: Boolean): String = {
    val start = if (isDistinct) "(distinct " else "("
    prettyName + flatArguments.mkString(start, ", ", ")")
  }
}

/**
 * API for aggregation functions that are expressed in terms of imperative initialize(), update(),
 * and merge() functions which operate on Row-based aggregation buffers.
 *
 * Within these functions, code should access fields of the mutable aggregation buffer by adding the
 * bufferSchema-relative field number to `mutableAggBufferOffset` then using this new field number
 * to access the buffer Row. This is necessary because this aggregation function's buffer is
 * embedded inside of a larger shared aggregation buffer when an aggregation operator evaluates
 * multiple aggregate functions at the same time.
 *
 * We need to perform similar field number arithmetic when merging multiple intermediate
 * aggregate buffers together in `merge()` (in this case, use `inputAggBufferOffset` when accessing
 * the input buffer).
 *
 * Correct ImperativeAggregate evaluation depends on the correctness of `mutableAggBufferOffset` and
 * `inputAggBufferOffset`, but not on the correctness of the attribute ids in `aggBufferAttributes`
 * and `inputAggBufferAttributes`.
  *
  *  需要显式实现initialize，update和merge方法来操作聚合缓冲区中的数据
  *  这类聚合函数所处理的聚合缓冲区的本质是基于行（InternalRow类型的）
 */
abstract class ImperativeAggregate extends AggregateFunction with CodegenFallback {

  /**
   * The offset of this function's first buffer value in the underlying shared mutable aggregation
   * buffer.
   *
   * For example, we have two aggregate functions `avg(x)` and `avg(y)`, which share the same
   * aggregation buffer. In this shared buffer, the position of the first buffer value of `avg(x)`
   * will be 0 and the position of the first buffer value of `avg(y)` will be 2:
   * {{{
   *          avg(x) mutableAggBufferOffset = 0
   *                  |
   *                  v
   *                  +--------+--------+--------+--------+
   *                  |  sum1  | count1 |  sum2  | count2 |
   *                  +--------+--------+--------+--------+
   *                                    ^
   *                                    |
   *                     avg(y) mutableAggBufferOffset = 2
   * }}}
   */
  protected val mutableAggBufferOffset: Int

  /**
   * Returns a copy of this ImperativeAggregate with an updated mutableAggBufferOffset.
   * This new copy's attributes may have different ids than the original.
   */
  def withNewMutableAggBufferOffset(newMutableAggBufferOffset: Int): ImperativeAggregate

  /**
   * The offset of this function's start buffer value in the underlying shared input aggregation
   * buffer. An input aggregation buffer is used when we merge two aggregation buffers together in
   * the `update()` function and is immutable (we merge an input aggregation buffer and a mutable
   * aggregation buffer and then store the new buffer values to the mutable aggregation buffer).
   *
   * An input aggregation buffer may contain extra fields, such as grouping keys, at its start, so
   * mutableAggBufferOffset and inputAggBufferOffset are often different.
   *
   * For example, say we have a grouping expression, `key`, and two aggregate functions,
   * `avg(x)` and `avg(y)`. In the shared input aggregation buffer, the position of the first
   * buffer value of `avg(x)` will be 1 and the position of the first buffer value of `avg(y)`
   * will be 3 (position 0 is used for the value of `key`):
   * {{{
   *          avg(x) inputAggBufferOffset = 1
   *                   |
   *                   v
   *          +--------+--------+--------+--------+--------+
   *          |  key   |  sum1  | count1 |  sum2  | count2 |
   *          +--------+--------+--------+--------+--------+
   *                                     ^
   *                                     |
   *                       avg(y) inputAggBufferOffset = 3
   * }}}
   */
  protected val inputAggBufferOffset: Int

  /**
   * Returns a copy of this ImperativeAggregate with an updated mutableAggBufferOffset.
   * This new copy's attributes may have different ids than the original.
   */
  def withNewInputAggBufferOffset(newInputAggBufferOffset: Int): ImperativeAggregate

  // Note: although all subclasses implement inputAggBufferAttributes by simply cloning
  // aggBufferAttributes, that common clone code cannot be placed here in the abstract
  // ImperativeAggregate class, since that will lead to initialization ordering issues.

  /**
   * Initializes the mutable aggregation buffer located in `mutableAggBuffer`.
   *
   * Use `fieldNumber + mutableAggBufferOffset` to access fields of `mutableAggBuffer`.
   */
  def initialize(mutableAggBuffer: InternalRow): Unit

  /**
   * Updates its aggregation buffer, located in `mutableAggBuffer`, based on the given `inputRow`.
   *
   * Use `fieldNumber + mutableAggBufferOffset` to access fields of `mutableAggBuffer`.
   *
   * Note that, the input row may be produced by unsafe projection and it may not be safe to cache
   * some fields of the input row, as the values can be changed unexpectedly.
   */
  def update(mutableAggBuffer: InternalRow, inputRow: InternalRow): Unit

  /**
   * Combines new intermediate results from the `inputAggBuffer` with the existing intermediate
   * results in the `mutableAggBuffer.`
   *
   * Use `fieldNumber + mutableAggBufferOffset` to access fields of `mutableAggBuffer`.
   * Use `fieldNumber + inputAggBufferOffset` to access fields of `inputAggBuffer`.
   *
   * Note that, the input row may be produced by unsafe projection and it may not be safe to cache
   * some fields of the input row, as the values can be changed unexpectedly.
    *
    *
    *  这里引入InputAggBuffer(输入聚合缓冲区)的概念，这个缓冲区是不可变的，处理聚合的过程中进行合并的时候的实现
    *  就是将输入聚合缓冲区的值更新到可变的聚合缓冲区中。
   */
  def merge(mutableAggBuffer: InternalRow, inputAggBuffer: InternalRow): Unit
}

/**
 *  这类聚合函数是直接由engine中Expression类型构建的聚合函数
 *
 *  当实现一个新的基于表达式构建的聚合函数时，需要首先实现 `bufferAttributes` (定义mutable aggregation buffer
  * 涉及的attributes for the fields，需要聚合的属性，这里应该是 `aggBufferAttributes` ). 然后就可以使用这些attributes来定义`updateExpressions`, `mergeExpressions`, and
  * `evaluateExpressions`.
  *
 * Please note that children of an aggregate function can be unresolved (it will happen when
 * we create this function in DataFrame API). So, if there is any fields in
 * the implemented class that need to access fields of its children, please make
 * those fields `lazy val`s.
  *
  *  一个aggregate function的children节点也可能是unresolved （当我们使用DataFrame API来创建这个function的时候就会发生）
  *  因此，如果在这个实现类中有哪些fields需要访问它的子节点的fields，需要将其标识为lazy val
  *
 */
abstract class DeclarativeAggregate
  extends AggregateFunction
    with Serializable
    with Unevaluable {

  /**
    *  聚合缓冲区的初始化表达式
   * Expressions for initializing empty aggregation buffers.
   */
  val initialValues: Seq[Expression]

  /**
    *  聚合缓冲区的更新表达式
   * Expressions for updating the mutable aggregation buffer based on an input row.
   */
  val updateExpressions: Seq[Expression]

  /**
    *  聚合缓冲区的合并表达式
    *  当定义这些表达式的时候，可以使用 attributeName.left/ attributeName.right 来指代待合并的缓存区中所涉及到的Attribute
    *  由implicit class [[RichAttribute]]实现
    *
    *
   * A sequence of expressions for merging two aggregation buffers together. When defining these
   * expressions, you can use the syntax `attributeName.left` and `attributeName.right` to refer
   * to the attributes corresponding to each of the buffers being merged (this magic is enabled
   * by the [[RichAttribute]] implicit class).
   */
  val mergeExpressions: Seq[Expression]

  /**
    *  最终结果生成表达式
   * An expression which returns the final value for this aggregate function. Its data type should
   * match this expression's [[dataType]].
   */
  val evaluateExpression: Expression

  /** An expression-based aggregate's bufferSchema is derived from bufferAttributes. */
  /**
    *  一个基于表达式的聚合缓冲区的bufferSchema
    * @return
    */
  final override def aggBufferSchema: StructType = StructType.fromAttributes(aggBufferAttributes)

  final lazy val inputAggBufferAttributes: Seq[AttributeReference] =
    aggBufferAttributes.map( _.newInstance() )

  /**
   * A helper class for representing an attribute used in merging two
   * aggregation buffers. When merging two buffers, `bufferLeft` and `bufferRight`,
   * we merge buffer values and then update bufferLeft. A [[RichAttribute]]
   * of an [[AttributeReference]] `a` has two functions `left` and `right`,
   * which represent `a` in `bufferLeft` and `bufferRight`, respectively.
    *
    *   用于在合并两个聚合缓冲区时代表attribute所使用的辅助类
    *   当我们合并两个bufferLeft/bufferRight缓冲区时，我们合并并更新bufferLeft
   */
  implicit class RichAttribute(a: AttributeReference) {
    /** Represents this attribute at the mutable buffer side. */
    def left: AttributeReference = a

    /** Represents this attribute at the input buffer side (the data value is read-only). */
    def right: AttributeReference = inputAggBufferAttributes(aggBufferAttributes.indexOf(a))
  }
}


/**
 * Aggregation function which allows **arbitrary** user-defined java object to be used as internal
 * aggregation buffer.
 *  这种聚合允许使用用户自定义的Java对象T作为内部的聚合缓冲区，因此这种类型的聚合函数是最灵活的。
 * {{{
 *  aggregation buffer for normal aggregation function `avg`            aggregate buffer for `sum`
 *            |                                                                  |
 *            v                                                                  v
 *          +--------------+---------------+-----------------------------------+-------------+
 *          |  sum1 (Long) | count1 (Long) | generic user-defined java objects | sum2 (Long) |
 *          +--------------+---------------+-----------------------------------+-------------+
 *                                           ^
 *                                           |
 *            aggregation buffer object for `TypedImperativeAggregate` aggregation function
 * }}}
 *
 * General work flow:
 *
 * Stage 1: initialize aggregate buffer object.
 *
 *   1. The framework calls `initialize(buffer: MutableRow)` to set up the empty aggregate buffer.
 *   2. In `initialize`, we call `createAggregationBuffer(): T` to get the initial buffer object,
 *      and set it to the global buffer row.
 *
 *
 * Stage 2: process input rows.
 *
 *   If the aggregate mode is `Partial` or `Complete`:
 *     1. The framework calls `update(buffer: MutableRow, input: InternalRow)` to process the input
 *        row.
 *     2. In `update`, we get the buffer object from the global buffer row and call
 *        `update(buffer: T, input: InternalRow): Unit`.
 *
 *   If the aggregate mode is `PartialMerge` or `Final`:
 *     1. The framework call `merge(buffer: MutableRow, inputBuffer: InternalRow)` to process the
 *        input row, which are serialized buffer objects shuffled from other nodes.
 *     2. In `merge`, we get the buffer object from the global buffer row, and get the binary data
 *        from input row and deserialize it to buffer object, then we call
 *        `merge(buffer: T, input: T): Unit` to merge these 2 buffer objects.
 *
 *
 * Stage 3: output results.
 *
 *   If the aggregate mode is `Partial` or `PartialMerge`:
 *     1. The framework calls `serializeAggregateBufferInPlace` to replace the buffer object in the
 *        global buffer row with binary data.
 *     2. In `serializeAggregateBufferInPlace`, we get the buffer object from the global buffer row
 *        and call `serialize(buffer: T): Array[Byte]` to serialize the buffer object to binary.
 *     3. The framework outputs buffer attributes and shuffle them to other nodes.
 *
 *   If the aggregate mode is `Final` or `Complete`:
 *     1. The framework calls `eval(buffer: InternalRow)` to calculate the final result.
 *     2. In `eval`, we get the buffer object from the global buffer row and call
 *        `eval(buffer: T): Any` to get the final result.
 *     3. The framework outputs these final results.
 *
 *
 * Window function work flow:
 *   The framework calls `update(buffer: MutableRow, input: InternalRow)` several times and then
 *   call `eval(buffer: InternalRow)`, so there is no need for window operator to call
 *   `serializeAggregateBufferInPlace`.
 *
 *
 * NOTE: SQL with TypedImperativeAggregate functions is planned in sort based aggregation,
 * instead of hash based aggregation, as TypedImperativeAggregate use BinaryType as aggregation
 * buffer's storage format, which is not supported by hash based aggregation. Hash based
 * aggregation only support aggregation buffer of mutable types (like LongType, IntType that have
 * fixed length and can be mutated in place in UnsafeRow).
 * NOTE: The newly added ObjectHashAggregateExec supports TypedImperativeAggregate functions in
 * hash based aggregation under some constraints.
 */
abstract class TypedImperativeAggregate[T] extends ImperativeAggregate {

  /**
   * Creates an empty aggregation buffer object. This is called before processing each key group
   * (group by key).
   *
   * @return an aggregation buffer object
   */
  def createAggregationBuffer(): T

  /**
   * Updates the aggregation buffer object with an input row and returns a new buffer object. For
   * performance, the function may do in-place update and return it instead of constructing new
   * buffer object.
   *
   * This is typically called when doing Partial or Complete mode aggregation.
   *
   * @param buffer The aggregation buffer object.
   * @param input an input row
   */
  def update(buffer: T, input: InternalRow): T

  /**
   * Merges an input aggregation object into aggregation buffer object and returns a new buffer
   * object. For performance, the function may do in-place merge and return it instead of
   * constructing new buffer object.
   *
   * This is typically called when doing PartialMerge or Final mode aggregation.
   *
   * @param buffer the aggregation buffer object used to store the aggregation result.
   * @param input an input aggregation object. Input aggregation object can be produced by
   *              de-serializing the partial aggregate's output from Mapper side.
   */
  def merge(buffer: T, input: T): T

  /**
   * Generates the final aggregation result value for current key group with the aggregation buffer
   * object.
   *
   * Developer note: the only return types accepted by Spark are:
   *   - primitive types
   *   - InternalRow and subclasses
   *   - ArrayData
   *   - MapData
   *
   * @param buffer aggregation buffer object.
   * @return The aggregation result of current key group
   */
  def eval(buffer: T): Any

  /** Serializes the aggregation buffer object T to Array[Byte] */
  def serialize(buffer: T): Array[Byte]

  /** De-serializes the serialized format Array[Byte], and produces aggregation buffer object T */
  def deserialize(storageFormat: Array[Byte]): T

  final override def initialize(buffer: InternalRow): Unit = {
    buffer(mutableAggBufferOffset) = createAggregationBuffer()
  }

  final override def update(buffer: InternalRow, input: InternalRow): Unit = {
    buffer(mutableAggBufferOffset) = update(getBufferObject(buffer), input)
  }

  final override def merge(buffer: InternalRow, inputBuffer: InternalRow): Unit = {
    val bufferObject = getBufferObject(buffer)
    // The inputBuffer stores serialized aggregation buffer object produced by partial aggregate
    val inputObject = deserialize(inputBuffer.getBinary(inputAggBufferOffset))
    buffer(mutableAggBufferOffset) = merge(bufferObject, inputObject)
  }

  final override def eval(buffer: InternalRow): Any = {
    eval(getBufferObject(buffer))
  }

  private[this] val anyObjectType = ObjectType(classOf[AnyRef])
  private def getBufferObject(bufferRow: InternalRow): T = {
    bufferRow.get(mutableAggBufferOffset, anyObjectType).asInstanceOf[T]
  }

  final override lazy val aggBufferAttributes: Seq[AttributeReference] = {
    // Underlying storage type for the aggregation buffer object
    Seq(AttributeReference("buf", BinaryType)())
  }

  final override lazy val inputAggBufferAttributes: Seq[AttributeReference] =
    aggBufferAttributes.map(_.newInstance())

  final override def aggBufferSchema: StructType = StructType.fromAttributes(aggBufferAttributes)

  /**
   * In-place replaces the aggregation buffer object stored at buffer's index
   * `mutableAggBufferOffset`, with SparkSQL internally supported underlying storage format
   * (BinaryType).
   *
   * This is only called when doing Partial or PartialMerge mode aggregation, before the framework
   * shuffle out aggregate buffers.
   */
  final def serializeAggregateBufferInPlace(buffer: InternalRow): Unit = {
    buffer(mutableAggBufferOffset) = serialize(getBufferObject(buffer))
  }
}
