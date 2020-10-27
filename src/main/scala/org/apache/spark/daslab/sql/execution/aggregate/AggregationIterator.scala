package org.apache.spark.daslab.sql.execution.aggregate


import scala.collection.mutable.ArrayBuffer

//todo
import org.apache.spark.internal.Logging

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.expressions.aggregate._

/**
 * [[SortBasedAggregationIterator]] 和 [[TungstenAggregationIterator]]的基类
 * 主要包含两个部分：
  * 1. 初始化聚合函数（aggregate functions）
 *  2. 生成两个函数: 基于聚合函数的模式生成processRow函数和generateOutput函数
  *   processRow函数用于处理输入
  *   generateOutput用于生成结果
 */
abstract class AggregationIterator(
                                    partIndex: Int,
                                    groupingExpressions: Seq[NamedExpression],
                                    inputAttributes: Seq[Attribute],
                                    aggregateExpressions: Seq[AggregateExpression],
                                    aggregateAttributes: Seq[Attribute],
                                    initialInputBufferOffset: Int,
                                    resultExpressions: Seq[NamedExpression],
                                    newMutableProjection: (Seq[Expression], Seq[Attribute]) => MutableProjection)
  extends Iterator[UnsafeRow] with Logging {

  ///////////////////////////////////////////////////////////////////////////
  // Initializing functions.
  ///////////////////////////////////////////////////////////////////////////

  /**
   * The following combinations of AggregationMode are supported:
   * - Partial
   * - PartialMerge (for single distinct)
   * - Partial and PartialMerge (for single distinct)
   * - Final
   * - Complete (for SortAggregate with functions that does not support Partial)
   * - Final and Complete (currently not used)
   *
   * TODO: AggregateMode should have only two modes: Update and Merge, AggregateExpression
   * could have a flag to tell it's final or not.
   */
  {
    val modes = aggregateExpressions.map(_.mode).distinct.toSet
    require(modes.size <= 2,
      s"$aggregateExpressions are not supported because they have more than 2 distinct modes.")
    require(modes.subsetOf(Set(Partial, PartialMerge)) || modes.subsetOf(Set(Final, Complete)),
      s"$aggregateExpressions can't have Partial/PartialMerge and Final/Complete in the same time.")
  }

  // Initialize all AggregateFunctions by binding references if necessary,
  // and set inputBufferOffset and mutableBufferOffset.
  protected def initializeAggregateFunctions(
                                              expressions: Seq[AggregateExpression],
                                              startingInputBufferOffset: Int): Array[AggregateFunction] = {
    var mutableBufferOffset = 0
    var inputBufferOffset: Int = startingInputBufferOffset
    val expressionsLength = expressions.length
    val functions = new Array[AggregateFunction](expressionsLength)
    var i = 0
    while (i < expressionsLength) {
      val func = expressions(i).aggregateFunction
      val funcWithBoundReferences: AggregateFunction = expressions(i).mode match {
        case Partial | Complete if func.isInstanceOf[ImperativeAggregate] =>
          // We need to create BoundReferences if the function is not an
          // expression-based aggregate function (it does not support code-gen) and the mode of
          // this function is Partial or Complete because we will call eval of this
          // function's children in the update method of this aggregate function.
          // Those eval calls require BoundReferences to work.
          BindReferences.bindReference(func, inputAttributes)
        case _ =>
          // We only need to set inputBufferOffset for aggregate functions with mode
          // PartialMerge and Final.
          val updatedFunc = func match {
            case function: ImperativeAggregate =>
              function.withNewInputAggBufferOffset(inputBufferOffset)
            case function => function
          }
          inputBufferOffset += func.aggBufferSchema.length
          updatedFunc
      }
      val funcWithUpdatedAggBufferOffset = funcWithBoundReferences match {
        case function: ImperativeAggregate =>
          // Set mutableBufferOffset for this function. It is important that setting
          // mutableBufferOffset happens after all potential bindReference operations
          // because bindReference will create a new instance of the function.
          function.withNewMutableAggBufferOffset(mutableBufferOffset)
        case function => function
      }
      mutableBufferOffset += funcWithUpdatedAggBufferOffset.aggBufferSchema.length
      functions(i) = funcWithUpdatedAggBufferOffset
      i += 1
    }
    functions
  }

  protected val aggregateFunctions: Array[AggregateFunction] =
    initializeAggregateFunctions(aggregateExpressions, initialInputBufferOffset)

  // Positions of those imperative aggregate functions in allAggregateFunctions.
  // For example, we have func1, func2, func3, func4 in aggregateFunctions, and
  // func2 and func3 are imperative aggregate functions.
  // ImperativeAggregateFunctionPositions will be [1, 2].
  protected[this] val allImperativeAggregateFunctionPositions: Array[Int] = {
    val positions = new ArrayBuffer[Int]()
    var i = 0
    while (i < aggregateFunctions.length) {
      aggregateFunctions(i) match {
        case agg: DeclarativeAggregate =>
        case _ => positions += i
      }
      i += 1
    }
    positions.toArray
  }

  // The projection used to initialize buffer values for all expression-based aggregates.
  protected[this] val expressionAggInitialProjection = {
    val initExpressions = aggregateFunctions.flatMap {
      case ae: DeclarativeAggregate => ae.initialValues
      // For the positions corresponding to imperative aggregate functions, we'll use special
      // no-op expressions which are ignored during projection code-generation.
      case i: ImperativeAggregate => Seq.fill(i.aggBufferAttributes.length)(NoOp)
    }
    newMutableProjection(initExpressions, Nil)
  }

  // All imperative AggregateFunctions.
  protected[this] val allImperativeAggregateFunctions: Array[ImperativeAggregate] =
    allImperativeAggregateFunctionPositions
      .map(aggregateFunctions)
      .map(_.asInstanceOf[ImperativeAggregate])

  // Initializing functions used to process a row.
  protected def generateProcessRow(
                                    expressions: Seq[AggregateExpression],
                                    functions: Seq[AggregateFunction],
                                    inputAttributes: Seq[Attribute]): (InternalRow, InternalRow) => Unit = {
    val joinedRow = new JoinedRow
    if (expressions.nonEmpty) {
      val mergeExpressions = functions.zipWithIndex.flatMap {
        case (ae: DeclarativeAggregate, i) =>
          expressions(i).mode match {
            case Partial | Complete => ae.updateExpressions
            case PartialMerge | Final => ae.mergeExpressions
          }
        case (agg: AggregateFunction, _) => Seq.fill(agg.aggBufferAttributes.length)(NoOp)
      }
      val updateFunctions = functions.zipWithIndex.collect {
        case (ae: ImperativeAggregate, i) =>
          expressions(i).mode match {
            case Partial | Complete =>
              (buffer: InternalRow, row: InternalRow) => ae.update(buffer, row)
            case PartialMerge | Final =>
              (buffer: InternalRow, row: InternalRow) => ae.merge(buffer, row)
          }
      }.toArray
      // This projection is used to merge buffer values for all expression-based aggregates.
      val aggregationBufferSchema = functions.flatMap(_.aggBufferAttributes)
      val updateProjection =
        newMutableProjection(mergeExpressions, aggregationBufferSchema ++ inputAttributes)

      (currentBuffer: InternalRow, row: InternalRow) => {
        // Process all expression-based aggregate functions.
        updateProjection.target(currentBuffer)(joinedRow(currentBuffer, row))
        // Process all imperative aggregate functions.
        var i = 0
        while (i < updateFunctions.length) {
          updateFunctions(i)(currentBuffer, row)
          i += 1
        }
      }
    } else {
      // Grouping only.
      (currentBuffer: InternalRow, row: InternalRow) => {}
    }
  }

  protected val processRow: (InternalRow, InternalRow) => Unit =
    generateProcessRow(aggregateExpressions, aggregateFunctions, inputAttributes)

  protected val groupingProjection: UnsafeProjection =
    UnsafeProjection.create(groupingExpressions, inputAttributes)
  protected val groupingAttributes = groupingExpressions.map(_.toAttribute)

  // Initializing the function used to generate the output row.
  protected def generateResultProjection(): (UnsafeRow, InternalRow) => UnsafeRow = {
    val joinedRow = new JoinedRow
    val modes = aggregateExpressions.map(_.mode).distinct
    val bufferAttributes = aggregateFunctions.flatMap(_.aggBufferAttributes)
    if (modes.contains(Final) || modes.contains(Complete)) {
      val evalExpressions = aggregateFunctions.map {
        case ae: DeclarativeAggregate => ae.evaluateExpression
        case agg: AggregateFunction => NoOp
      }
      val aggregateResult = new SpecificInternalRow(aggregateAttributes.map(_.dataType))
      val expressionAggEvalProjection = newMutableProjection(evalExpressions, bufferAttributes)
      expressionAggEvalProjection.target(aggregateResult)

      val resultProjection =
        UnsafeProjection.create(resultExpressions, groupingAttributes ++ aggregateAttributes)
      resultProjection.initialize(partIndex)

      (currentGroupingKey: UnsafeRow, currentBuffer: InternalRow) => {
        // Generate results for all expression-based aggregate functions.
        expressionAggEvalProjection(currentBuffer)
        // Generate results for all imperative aggregate functions.
        var i = 0
        while (i < allImperativeAggregateFunctions.length) {
          aggregateResult.update(
            allImperativeAggregateFunctionPositions(i),
            allImperativeAggregateFunctions(i).eval(currentBuffer))
          i += 1
        }
        resultProjection(joinedRow(currentGroupingKey, aggregateResult))
      }
    } else if (modes.contains(Partial) || modes.contains(PartialMerge)) {
      val resultProjection = UnsafeProjection.create(
        groupingAttributes ++ bufferAttributes,
        groupingAttributes ++ bufferAttributes)
      resultProjection.initialize(partIndex)

      // TypedImperativeAggregate stores generic object in aggregation buffer, and requires
      // calling serialization before shuffling. See [[TypedImperativeAggregate]] for more info.
      val typedImperativeAggregates: Array[TypedImperativeAggregate[_]] = {
        aggregateFunctions.collect {
          case (ag: TypedImperativeAggregate[_]) => ag
        }
      }

      (currentGroupingKey: UnsafeRow, currentBuffer: InternalRow) => {
        // Serializes the generic object stored in aggregation buffer
        var i = 0
        while (i < typedImperativeAggregates.length) {
          typedImperativeAggregates(i).serializeAggregateBufferInPlace(currentBuffer)
          i += 1
        }
        resultProjection(joinedRow(currentGroupingKey, currentBuffer))
      }
    } else {
      // Grouping-only: we only output values based on grouping expressions.
      val resultProjection = UnsafeProjection.create(resultExpressions, groupingAttributes)
      resultProjection.initialize(partIndex)
      (currentGroupingKey: UnsafeRow, currentBuffer: InternalRow) => {
        resultProjection(currentGroupingKey)
      }
    }
  }

  protected val generateOutput: (UnsafeRow, InternalRow) => UnsafeRow =
    generateResultProjection()

  /** Initializes buffer values for all aggregate functions. */
  protected def initializeBuffer(buffer: InternalRow): Unit = {
    expressionAggInitialProjection.target(buffer)(EmptyRow)
    var i = 0
    while (i < allImperativeAggregateFunctions.length) {
      allImperativeAggregateFunctions(i).initialize(buffer)
      i += 1
    }
  }
}
