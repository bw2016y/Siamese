
package org.apache.spark.daslab.sql.engine.expressions.aggregate

import scala.collection.generic.Growable
import scala.collection.mutable

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.analysis.TypeCheckResult
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.util.ArrayData
import org.apache.spark.daslab.sql.engine.util.GenericArrayData
import org.apache.spark.daslab.sql.types._

/**
 * A base class for collect_list and collect_set aggregate functions.
 *
 * We have to store all the collected elements in memory, and so notice that too many elements
 * can cause GC paused and eventually OutOfMemory Errors.
 */
abstract class Collect[T <: Growable[Any] with Iterable[Any]] extends TypedImperativeAggregate[T] {

  val child: Expression

  override def children: Seq[Expression] = child :: Nil

  override def nullable: Boolean = true

  override def dataType: DataType = ArrayType(child.dataType)

  // Both `CollectList` and `CollectSet` are non-deterministic since their results depend on the
  // actual order of input rows.
  override lazy val deterministic: Boolean = false

  protected def convertToBufferElement(value: Any): Any

  override def update(buffer: T, input: InternalRow): T = {
    val value = child.eval(input)

    // Do not allow null values. We follow the semantics of Hive's collect_list/collect_set here.
    // See: org.apache.hadoop.hive.ql.udf.generic.GenericUDAFMkCollectionEvaluator
    if (value != null) {
      buffer += convertToBufferElement(value)
    }
    buffer
  }

  override def merge(buffer: T, other: T): T = {
    buffer ++= other
  }

  protected val bufferElementType: DataType

  private lazy val projection = UnsafeProjection.create(
    Array[DataType](ArrayType(elementType = bufferElementType, containsNull = false)))
  private lazy val row = new UnsafeRow(1)

  override def serialize(obj: T): Array[Byte] = {
    val array = new GenericArrayData(obj.toArray)
    projection.apply(InternalRow.apply(array)).getBytes()
  }

  override def deserialize(bytes: Array[Byte]): T = {
    val buffer = createAggregationBuffer()
    row.pointTo(bytes, bytes.length)
    row.getArray(0).foreach(bufferElementType, (_, x: Any) => buffer += x)
    buffer
  }
}

/**
 * Collect a list of elements.
 */
@ExpressionDescription(
  usage = "_FUNC_(expr) - Collects and returns a list of non-unique elements.")
case class CollectList(
    child: Expression,
    mutableAggBufferOffset: Int = 0,
    inputAggBufferOffset: Int = 0) extends Collect[mutable.ArrayBuffer[Any]] {

  def this(child: Expression) = this(child, 0, 0)

  override lazy val bufferElementType = child.dataType

  override def convertToBufferElement(value: Any): Any = InternalRow.copyValue(value)

  override def withNewMutableAggBufferOffset(newMutableAggBufferOffset: Int): ImperativeAggregate =
    copy(mutableAggBufferOffset = newMutableAggBufferOffset)

  override def withNewInputAggBufferOffset(newInputAggBufferOffset: Int): ImperativeAggregate =
    copy(inputAggBufferOffset = newInputAggBufferOffset)

  override def createAggregationBuffer(): mutable.ArrayBuffer[Any] = mutable.ArrayBuffer.empty

  override def prettyName: String = "collect_list"

  override def eval(buffer: mutable.ArrayBuffer[Any]): Any = {
    new GenericArrayData(buffer.toArray)
  }
}

/**
 * Collect a set of unique elements.
 */
@ExpressionDescription(
  usage = "_FUNC_(expr) - Collects and returns a set of unique elements.")
case class CollectSet(
    child: Expression,
    mutableAggBufferOffset: Int = 0,
    inputAggBufferOffset: Int = 0) extends Collect[mutable.HashSet[Any]] {

  def this(child: Expression) = this(child, 0, 0)

  override lazy val bufferElementType = child.dataType match {
    case BinaryType => ArrayType(ByteType)
    case other => other
  }

  override def convertToBufferElement(value: Any): Any = child.dataType match {
    /*
     * collect_set() of BinaryType should not return duplicate elements,
     * Java byte arrays use referential equality and identity hash codes
     * so we need to use a different catalyst value for arrays
     */
    case BinaryType => UnsafeArrayData.fromPrimitiveArray(value.asInstanceOf[Array[Byte]])
    case _ => InternalRow.copyValue(value)
  }

  override def eval(buffer: mutable.HashSet[Any]): Any = {
    val array = child.dataType match {
      case BinaryType =>
        buffer.iterator.map(_.asInstanceOf[ArrayData].toByteArray).toArray
      case _ => buffer.toArray
    }
    new GenericArrayData(array)
  }

  override def checkInputDataTypes(): TypeCheckResult = {
    if (!child.dataType.existsRecursively(_.isInstanceOf[MapType])) {
      TypeCheckResult.TypeCheckSuccess
    } else {
      TypeCheckResult.TypeCheckFailure("collect_set() cannot have map type data")
    }
  }

  override def withNewMutableAggBufferOffset(newMutableAggBufferOffset: Int): ImperativeAggregate =
    copy(mutableAggBufferOffset = newMutableAggBufferOffset)

  override def withNewInputAggBufferOffset(newInputAggBufferOffset: Int): ImperativeAggregate =
    copy(inputAggBufferOffset = newInputAggBufferOffset)

  override def prettyName: String = "collect_set"

  override def createAggregationBuffer(): mutable.HashSet[Any] = mutable.HashSet.empty
}
