package org.apache.spark.daslab.sql.execution.aggregate


import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.engine.expressions.aggregate._
import org.apache.spark.daslab.sql.execution.SparkPlan
import org.apache.spark.daslab.sql.execution.streaming.{StateStoreRestoreExec, StateStoreSaveExec}
import org.apache.spark.daslab.sql.internal.SQLConf

/**
 * Utility functions used by the query planner to convert our plan to new aggregation code path.
 */
object AggUtils {
  //构造聚合函数的实现
  /**
    *  总的来说聚合的物理实现的顺序是
    *  Hash >> ObjectHash >> Sort
    * @param requiredChildDistributionExpressions
    * @param groupingExpressions
    * @param aggregateExpressions
    * @param aggregateAttributes
    * @param initialInputBufferOffset
    * @param resultExpressions
    * @param child
    * @return
    */
  private def createAggregate(
                               requiredChildDistributionExpressions: Option[Seq[Expression]] = None,
                               groupingExpressions: Seq[NamedExpression] = Nil,
                               aggregateExpressions: Seq[AggregateExpression] = Nil,
                               aggregateAttributes: Seq[Attribute] = Nil,
                               initialInputBufferOffset: Int = 0,
                               resultExpressions: Seq[NamedExpression] = Nil,
                               child: SparkPlan): SparkPlan = {
    //todo some debug here

   /* println("check aggFunction")
    aggregateExpressions.foreach(ae => println(ae.aggregateFunction.children))
    println("check child output")
    println(child.output)*/


    // retrieve weight
 /*   val weight: Seq[Attribute] = child.output.filter(_.name == "_weight")
    if(weight.length!=0){
       println("do have weight"+weight.head)
       println(weight.length)

      // check flag
      aggregateExpressions.foreach( ae=> {
        ae.aggregateFunction match{
          case s:Sum =>
            println("before fix : "+s.hasWeight)
            println(ae.aggregateFunction.children)

          case _  =>
        }
      })
      //append weight for sum/avg/count only
      aggregateExpressions.foreach( ae => {
        ae.aggregateFunction match {
          case s: Sum => s.appendWeight(weight.head)
          case _ =>
        }
      })
      // check flag
      aggregateExpressions.foreach( ae=> {
         ae.aggregateFunction match{
           case s:Sum =>
             println("after fix " + s.hasWeight)
             println(ae.aggregateFunction.children)

           case _  =>
         }
      })
    }
    else {
      println("do not have weight,we dont need fix anything")
    }*/



    // 首先尝试使用HashAggregateExec
    val useHash = HashAggregateExec.supportsAggregate(
      aggregateExpressions.flatMap(_.aggregateFunction.aggBufferAttributes))

    if (useHash) {

      HashAggregateExec(
        requiredChildDistributionExpressions = requiredChildDistributionExpressions,
        groupingExpressions = groupingExpressions,
        aggregateExpressions = aggregateExpressions,
        aggregateAttributes = aggregateAttributes,
        initialInputBufferOffset = initialInputBufferOffset,
        resultExpressions = resultExpressions,
        child = child)
    } else {
      val objectHashEnabled = child.sqlContext.conf.useObjectHashAggregation
      val useObjectHash = ObjectHashAggregateExec.supportsAggregate(aggregateExpressions)

       // 同时为true才可以使用
      if (objectHashEnabled && useObjectHash) {
        ObjectHashAggregateExec(
          requiredChildDistributionExpressions = requiredChildDistributionExpressions,
          groupingExpressions = groupingExpressions,
          aggregateExpressions = aggregateExpressions,
          aggregateAttributes = aggregateAttributes,
          initialInputBufferOffset = initialInputBufferOffset,
          resultExpressions = resultExpressions,
          child = child)
      } else {
        //否则使用SortAggregateExec
        SortAggregateExec(
          requiredChildDistributionExpressions = requiredChildDistributionExpressions,
          groupingExpressions = groupingExpressions,
          aggregateExpressions = aggregateExpressions,
          aggregateAttributes = aggregateAttributes,
          initialInputBufferOffset = initialInputBufferOffset,
          resultExpressions = resultExpressions,
          child = child)
      }
    }
  }
  //处理没有Distinct的情况
  def planAggregateWithoutDistinct(
                                    groupingExpressions: Seq[NamedExpression],
                                    aggregateExpressions: Seq[AggregateExpression],
                                    resultExpressions: Seq[NamedExpression],
                                    child: SparkPlan): Seq[SparkPlan] = {

    // 检查我们是否可以使用HashAggregate(这里存在问题，实际上对HashAggregate的判断放在上面的createAggregate中)




    //todo debug here
    println("groupingExpressions")
    groupingExpressions.foreach(
       groupingExpression =>
         println("     "+groupingExpression)
    )
    println("aggregateExpressions")
    aggregateExpressions.foreach(
       aggregateExpression =>
         println("     "+aggregateExpression)
    )
    println("resultExpressions")
    resultExpressions.foreach(
      resultExpression =>
        println("     "+resultExpression)
    )


    aggregateExpressions.foreach( aggto =>
      aggto.aggregateFunction match {
        case sum : Sum =>
          println("conf info ==============="+sum.confidence+"  :::"+ sum.errorRate +"  "+sum.hasWeight)
        case _ =>
      }
    )


    val weight: Seq[Attribute] = child.output.filter(_.name == "_weight")
    if(weight.length!=0){
      println("do have weight"+weight.head)
      println(weight.length)

      // check flag
      aggregateExpressions.foreach( ae=> {
        ae.aggregateFunction match{
          case s:Sum =>
            println("before fix : "+s.hasWeight)
            println(ae.aggregateFunction.children)

          case _  =>
        }
      })
      //append weight for sum/avg/count only
      aggregateExpressions.foreach( ae => {
        ae.aggregateFunction match {
          case s: Sum => s.appendWeight(weight.head)
          case _ =>
        }
      })
      // check flag
      aggregateExpressions.foreach( ae=> {
        ae.aggregateFunction match{
          case s:Sum =>
            println("after fix " + s.hasWeight)
            println(ae.aggregateFunction.children)

          case _  =>
        }
      })
    }
    else {
      println("do not have weight,we dont need fix anything")
    }












    // 1. Create an Aggregate Operator for partial aggregations.(
    // 先创建一个用于partial agg的聚合算子)


    val groupingAttributes = groupingExpressions.map(_.toAttribute)

    // Partial agg , 这里是做了复制的
    val partialAggregateExpressions = aggregateExpressions.map(_.copy(mode = Partial))


    // 所有聚合函数的聚合缓冲区所涉及的attributes
    val partialAggregateAttributes = partialAggregateExpressions.flatMap(_.aggregateFunction.aggBufferAttributes)

    println("所有聚合函数的聚合缓冲区所涉及的attributes")
    partialAggregateAttributes.foreach(
       p=> println("所有聚合函数的聚合缓冲区所涉及的attributes      "+p)
    )


    // 聚合算子应当输出的列集,这个列是newInstance构造出来的，因此exprId出现了变化
    val partialResultExpressions = groupingAttributes ++ partialAggregateExpressions.flatMap(_.aggregateFunction.inputAggBufferAttributes)


    println(" 聚合算子应当输出的列集")
    partialResultExpressions.foreach(
      p=> println(" 聚合算子应当输出的列集      "+p)
    )



    // 第一步的partial agg
    val partialAggregate = createAggregate(
      requiredChildDistributionExpressions = None,
      groupingExpressions = groupingExpressions,
      aggregateExpressions = partialAggregateExpressions,
      aggregateAttributes = partialAggregateAttributes,
      initialInputBufferOffset = 0,
      resultExpressions = partialResultExpressions,
      child = child)

    // 2. Create an Aggregate Operator for final aggregations.(
    // 第二步，创建Final Mode的聚合算子用于最后的合并，一般来说就是用于合并聚合缓冲区)



    //todo 第二步 需要删除掉weight，有点bug



   /* val finalAggregateExpressions = aggregateExpressions.map(_.copy(mode = Final))

    finalAggregateExpressions.foreach( aggto =>
      aggto.aggregateFunction match {
        case sum : Sum =>
          println("conf info ==============="+sum.confidence+"  :::"+ sum.errorRate +"  "+sum.hasWeight)
          sum.appendWeight(null)
          println("conf info ==============="+sum.confidence+"  :::"+ sum.errorRate +"  "+sum.hasWeight)
        case _ =>
      }
    )*/



     // 复制生成第二步的聚合表达式

     val finalAggregateExpressions = aggregateExpressions.map(_.copy(mode = Final))
     println("第二步聚合表达式的exprId")
     finalAggregateExpressions.foreach(
       f => println("第二步聚合表达式的exprId"+f.resultId)
     )


    // The attributes of the final aggregation buffer, which is presented as input to the result
    // projection:
    /**
      *  第二步聚合中的聚合缓冲区中的列（resultAttribute，Attributes）就被当作是最终projection的输入
      */
    val finalAggregateAttributes = finalAggregateExpressions.map(_.resultAttribute)


    println(" 最终的聚合算子涉及的列集")
    finalAggregateAttributes.foreach(
      p=> println(" 最终的聚合算子涉及的列集      "+p)
    )


    println(" 最终的聚合算子输出的列集")
    resultExpressions.foreach(
      p=> println(" 最终的聚合算子输出的列集      "+p)
    )



    val finalAggregate = createAggregate(
      requiredChildDistributionExpressions = Some(groupingAttributes),
      groupingExpressions = groupingAttributes,
      aggregateExpressions = finalAggregateExpressions,
      aggregateAttributes = finalAggregateAttributes,
      initialInputBufferOffset = groupingExpressions.length,
      resultExpressions = resultExpressions,
      child = partialAggregate)

    finalAggregate :: Nil
  }

  //处理只有一个Distinct的情况
  def planAggregateWithOneDistinct(
                                    groupingExpressions: Seq[NamedExpression],
                                    functionsWithDistinct: Seq[AggregateExpression],
                                    functionsWithoutDistinct: Seq[AggregateExpression],
                                    resultExpressions: Seq[NamedExpression],
                                    child: SparkPlan): Seq[SparkPlan] = {

    // functionsWithDistinct is guaranteed to be non-empty. Even though it may contain more than one
    // DISTINCT aggregate function, all of those functions will have the same column expressions.
    // For example, it would be valid for functionsWithDistinct to be
    // [COUNT(DISTINCT foo), MAX(DISTINCT foo)], but [COUNT(DISTINCT bar), COUNT(DISTINCT foo)] is
    // disallowed because those two distinct aggregates have different column expressions.
    val distinctExpressions = functionsWithDistinct.head.aggregateFunction.children
    val namedDistinctExpressions = distinctExpressions.map {
      case ne: NamedExpression => ne
      case other => Alias(other, other.toString)()
    }
    val distinctAttributes = namedDistinctExpressions.map(_.toAttribute)
    val groupingAttributes = groupingExpressions.map(_.toAttribute)

    // 1. Create an Aggregate Operator for partial aggregations.
    val partialAggregate: SparkPlan = {
      val aggregateExpressions = functionsWithoutDistinct.map(_.copy(mode = Partial))
      val aggregateAttributes = aggregateExpressions.map(_.resultAttribute)
      // We will group by the original grouping expression, plus an additional expression for the
      // DISTINCT column. For example, for AVG(DISTINCT value) GROUP BY key, the grouping
      // expressions will be [key, value].
      createAggregate(
        groupingExpressions = groupingExpressions ++ namedDistinctExpressions,
        aggregateExpressions = aggregateExpressions,
        aggregateAttributes = aggregateAttributes,
        resultExpressions = groupingAttributes ++ distinctAttributes ++
          aggregateExpressions.flatMap(_.aggregateFunction.inputAggBufferAttributes),
        child = child)
    }

    // 2. Create an Aggregate Operator for partial merge aggregations.
    val partialMergeAggregate: SparkPlan = {
      val aggregateExpressions = functionsWithoutDistinct.map(_.copy(mode = PartialMerge))
      val aggregateAttributes = aggregateExpressions.map(_.resultAttribute)
      createAggregate(
        requiredChildDistributionExpressions =
          Some(groupingAttributes ++ distinctAttributes),
        groupingExpressions = groupingAttributes ++ distinctAttributes,
        aggregateExpressions = aggregateExpressions,
        aggregateAttributes = aggregateAttributes,
        initialInputBufferOffset = (groupingAttributes ++ distinctAttributes).length,
        resultExpressions = groupingAttributes ++ distinctAttributes ++
          aggregateExpressions.flatMap(_.aggregateFunction.inputAggBufferAttributes),
        child = partialAggregate)
    }

    // 3. Create an Aggregate operator for partial aggregation (for distinct)
    val distinctColumnAttributeLookup = distinctExpressions.zip(distinctAttributes).toMap
    val rewrittenDistinctFunctions = functionsWithDistinct.map {
      // Children of an AggregateFunction with DISTINCT keyword has already
      // been evaluated. At here, we need to replace original children
      // to AttributeReferences.
      case agg @ AggregateExpression(aggregateFunction, mode, true, _) =>
        aggregateFunction.transformDown(distinctColumnAttributeLookup)
          .asInstanceOf[AggregateFunction]
      case agg =>
        throw new IllegalArgumentException(
          "Non-distinct aggregate is found in functionsWithDistinct " +
            s"at planAggregateWithOneDistinct: $agg")
    }

    val partialDistinctAggregate: SparkPlan = {
      val mergeAggregateExpressions = functionsWithoutDistinct.map(_.copy(mode = PartialMerge))
      // The attributes of the final aggregation buffer, which is presented as input to the result
      // projection:
      val mergeAggregateAttributes = mergeAggregateExpressions.map(_.resultAttribute)
      val (distinctAggregateExpressions, distinctAggregateAttributes) =
        rewrittenDistinctFunctions.zipWithIndex.map { case (func, i) =>
          // We rewrite the aggregate function to a non-distinct aggregation because
          // its input will have distinct arguments.
          // We just keep the isDistinct setting to true, so when users look at the query plan,
          // they still can see distinct aggregations.
          val expr = AggregateExpression(func, Partial, isDistinct = true)
          // Use original AggregationFunction to lookup attributes, which is used to build
          // aggregateFunctionToAttribute
          val attr = functionsWithDistinct(i).resultAttribute
          (expr, attr)
        }.unzip

      val partialAggregateResult = groupingAttributes ++
        mergeAggregateExpressions.flatMap(_.aggregateFunction.inputAggBufferAttributes) ++
        distinctAggregateExpressions.flatMap(_.aggregateFunction.inputAggBufferAttributes)
      createAggregate(
        groupingExpressions = groupingAttributes,
        aggregateExpressions = mergeAggregateExpressions ++ distinctAggregateExpressions,
        aggregateAttributes = mergeAggregateAttributes ++ distinctAggregateAttributes,
        initialInputBufferOffset = (groupingAttributes ++ distinctAttributes).length,
        resultExpressions = partialAggregateResult,
        child = partialMergeAggregate)
    }

    // 4. Create an Aggregate Operator for the final aggregation.
    val finalAndCompleteAggregate: SparkPlan = {
      val finalAggregateExpressions = functionsWithoutDistinct.map(_.copy(mode = Final))
      // The attributes of the final aggregation buffer, which is presented as input to the result
      // projection:
      val finalAggregateAttributes = finalAggregateExpressions.map(_.resultAttribute)

      val (distinctAggregateExpressions, distinctAggregateAttributes) =
        rewrittenDistinctFunctions.zipWithIndex.map { case (func, i) =>
          // We rewrite the aggregate function to a non-distinct aggregation because
          // its input will have distinct arguments.
          // We just keep the isDistinct setting to true, so when users look at the query plan,
          // they still can see distinct aggregations.
          val expr = AggregateExpression(func, Final, isDistinct = true)
          // Use original AggregationFunction to lookup attributes, which is used to build
          // aggregateFunctionToAttribute
          val attr = functionsWithDistinct(i).resultAttribute
          (expr, attr)
        }.unzip

      createAggregate(
        requiredChildDistributionExpressions = Some(groupingAttributes),
        groupingExpressions = groupingAttributes,
        aggregateExpressions = finalAggregateExpressions ++ distinctAggregateExpressions,
        aggregateAttributes = finalAggregateAttributes ++ distinctAggregateAttributes,
        initialInputBufferOffset = groupingAttributes.length,
        resultExpressions = resultExpressions,
        child = partialDistinctAggregate)
    }

    finalAndCompleteAggregate :: Nil
  }

  /**
   * Plans a streaming aggregation using the following progression:
   *  - Partial Aggregation
   *  - Shuffle
   *  - Partial Merge (now there is at most 1 tuple per group)
   *  - StateStoreRestore (now there is 1 tuple from this batch + optionally one from the previous)
   *  - PartialMerge (now there is at most 1 tuple per group)
   *  - StateStoreSave (saves the tuple for the next batch)
   *  - Complete (output the current result of the aggregation)
   */
  def planStreamingAggregation(
                                groupingExpressions: Seq[NamedExpression],
                                functionsWithoutDistinct: Seq[AggregateExpression],
                                resultExpressions: Seq[NamedExpression],
                                stateFormatVersion: Int,
                                child: SparkPlan): Seq[SparkPlan] = {

    val groupingAttributes = groupingExpressions.map(_.toAttribute)

    val partialAggregate: SparkPlan = {
      val aggregateExpressions = functionsWithoutDistinct.map(_.copy(mode = Partial))
      val aggregateAttributes = aggregateExpressions.map(_.resultAttribute)
      createAggregate(
        groupingExpressions = groupingExpressions,
        aggregateExpressions = aggregateExpressions,
        aggregateAttributes = aggregateAttributes,
        resultExpressions = groupingAttributes ++
          aggregateExpressions.flatMap(_.aggregateFunction.inputAggBufferAttributes),
        child = child)
    }

    val partialMerged1: SparkPlan = {
      val aggregateExpressions = functionsWithoutDistinct.map(_.copy(mode = PartialMerge))
      val aggregateAttributes = aggregateExpressions.map(_.resultAttribute)
      createAggregate(
        requiredChildDistributionExpressions =
          Some(groupingAttributes),
        groupingExpressions = groupingAttributes,
        aggregateExpressions = aggregateExpressions,
        aggregateAttributes = aggregateAttributes,
        initialInputBufferOffset = groupingAttributes.length,
        resultExpressions = groupingAttributes ++
          aggregateExpressions.flatMap(_.aggregateFunction.inputAggBufferAttributes),
        child = partialAggregate)
    }

    val restored = StateStoreRestoreExec(groupingAttributes, None, stateFormatVersion,
      partialMerged1)

    val partialMerged2: SparkPlan = {
      val aggregateExpressions = functionsWithoutDistinct.map(_.copy(mode = PartialMerge))
      val aggregateAttributes = aggregateExpressions.map(_.resultAttribute)
      createAggregate(
        requiredChildDistributionExpressions =
          Some(groupingAttributes),
        groupingExpressions = groupingAttributes,
        aggregateExpressions = aggregateExpressions,
        aggregateAttributes = aggregateAttributes,
        initialInputBufferOffset = groupingAttributes.length,
        resultExpressions = groupingAttributes ++
          aggregateExpressions.flatMap(_.aggregateFunction.inputAggBufferAttributes),
        child = restored)
    }
    // Note: stateId and returnAllStates are filled in later with preparation rules
    // in IncrementalExecution.
    val saved =
    StateStoreSaveExec(
      groupingAttributes,
      stateInfo = None,
      outputMode = None,
      eventTimeWatermark = None,
      stateFormatVersion = stateFormatVersion,
      partialMerged2)

    val finalAndCompleteAggregate: SparkPlan = {
      val finalAggregateExpressions = functionsWithoutDistinct.map(_.copy(mode = Final))
      // The attributes of the final aggregation buffer, which is presented as input to the result
      // projection:
      val finalAggregateAttributes = finalAggregateExpressions.map(_.resultAttribute)

      createAggregate(
        requiredChildDistributionExpressions = Some(groupingAttributes),
        groupingExpressions = groupingAttributes,
        aggregateExpressions = finalAggregateExpressions,
        aggregateAttributes = finalAggregateAttributes,
        initialInputBufferOffset = groupingAttributes.length,
        resultExpressions = resultExpressions,
        child = saved)
    }

    finalAndCompleteAggregate :: Nil
  }
}
