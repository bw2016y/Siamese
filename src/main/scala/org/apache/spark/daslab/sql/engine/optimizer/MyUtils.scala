package org.apache.spark.daslab.sql.engine.optimizer

import java.{lang, util}

import org.apache.spark.daslab.sql.engine.expressions.{And, AttributeReference, Expression}
import org.apache.spark.daslab.sql.engine.plans.logical.{Aggregate, AqpSample, ErrorRate, Filter, GlobalLimit, Join, LocalLimit, LogicalPlan, Project}
import org.apache.spark.daslab.sql.execution.columnar.InMemoryRelation
import org.apache.spark.daslab.sql.execution.util.DistinctColumn
import org.apache.spark.daslab.sql.execution.{DistinctSamplerExec, PlanLater, SparkPlan, UniformSamplerExec}
import org.apache.spark.daslab.sql.types.StringType
import org.apache.spark.daslab.thrift.gen.{Errors, Plans, PredictService}
import org.apache.thrift.protocol.{TCompactProtocol, TProtocol}
import org.apache.thrift.transport.{TFramedTransport, TSocket, TTransport}

object MyUtils {
    // todo pickmode
    var PICKMODE = 0



    var FRACTION=1.0

    // todo quickr >= 30 中心极限定理的需要 推荐在 5-100 左右
    var DELTA = 60

    // todo 这个值作废，没有用到 ， 已经使用rdd的partitions数目修正
    var PARALLELNUMS = 1
    var PLANPOS=0
    def setFraction(frac:Double)={
      MyUtils.FRACTION=frac
    }
    def setDelta(delta:Int)={
      MyUtils.DELTA=delta
    }

    def setPickMode(mode: Int)={
      MyUtils.PICKMODE=mode
    }

    def setParallelNums(para:Int)={
      MyUtils.PARALLELNUMS=para
    }
    def setPlan(index:Int)={
       MyUtils.PLANPOS = index
    }

    def newGetAqpSample(plan:LogicalPlan):LogicalPlan={
      var res:LogicalPlan = null
      plan transform{
        case aqp@ AqpSample(errorRate,confidence,seed,child,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel) =>
            res = aqp
            aqp
      }
      return res
    }
    def getAggRoot(plan:LogicalPlan):LogicalPlan={
      var res : LogicalPlan = null
      plan transform{
        case agg @ Aggregate(groupingExpressions,aggregateExpressions,child) =>
          res = agg
          agg
      }
      return res
    }



    def removeAQP(plan:LogicalPlan):LogicalPlan={
        plan transform{
          case agg @ Aggregate(groupingExpressions,aggregateExpressions,child)=>
            child match{
              case aqp@ AqpSample(errorRate,confidence,seed,child,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel) =>
                Aggregate(groupingExpressions,aggregateExpressions,child)
              case _ => agg
            }
        }
    }
    // todo bug
    // todo 这里需要检查一下是否有可能不是AQP查询也执行了逻辑
    def depthHelper(plan:LogicalPlan):Int={
      plan match{
        case agg @ Aggregate(groupingExpressions,aggregateExpressions,child)=>
          return depthHelper(child)
        case project @Project(projectList,grandChild) =>
          return depthHelper(grandChild)
        case filter @ Filter(condition,grandChild) =>
          return depthHelper(grandChild)
        case join @ Join(left,right,joinType,condition) =>
          return 1 + Math.max(depthHelper(left),depthHelper(right))
        case aqp @ AqpSample (errorRate,confidence,seed,child,stratificationSet,universeSet,ds,sfm,sampleFraction,delta,parallel) =>
          return 1
        case _ =>
          return -9999
      }
    }

    def getCost(plan:LogicalPlan):Double = {
        val root = newGetAqpSample(plan)

        var cost : Double = 0.0
        root transform{
          case inMemoryRelation @ InMemoryRelation(output,cacheBuilder) =>
            val metaName: String = output(0).asInstanceOf[AttributeReference].qualifier(1)
            println("metaName ....................   "+metaName)
            if(metaName.equals("lineitem")){
               cost = (cost + 6000)
            }else if(metaName.equals("supplier")){
               cost = (cost +  10 )
            }else if(metaName.equals("partsupp")){
              cost = (cost +  800 )
            }else if(metaName.equals("part")){
              cost = (cost +  200 )
            }else if(metaName.equals("order")){
              cost = (cost +  1500 )
            }else if(metaName.equals("customer")){
              cost = (cost +  150 )
            }else if(metaName.equals("nation")){
              cost = (cost +  0.0025 )
            }else if(metaName.equals("region")){
              cost = (cost + 0.0005)
            }
            inMemoryRelation
        }
       return cost
    }

  /**
    *   "stratificationSet":"C_CUSTKEY,C_ACCTBAL"
    * @param cols
    * @return
    */
  def getNumDV(cols:Set[WrapAttribute]):Double = {
    // cols_str
    val cols_str: Set[String] = cols.map(wrapAtt => {
    wrapAtt.asInstanceOf[AttributeReference].toString
    })

    var dv : Double = 1.00

    cols_str.foreach(
      col_str => {
         if(Col_distinctNum.get(col_str).nonEmpty){
              dv = dv*Col_distinctNum.get(col_str).get

         }else{
              dv = dv * 1.0
         }
      }
    )

    var firstCol:String = cols_str.head
    var MaxDV : Double = 0.0

    if(Col_table.get(firstCol).nonEmpty){
        var tableName: String = Col_table.get(firstCol).get
        MaxDV = cardMap.get(tableName).get
    }

    if(MaxDV != 0.0){
        dv = math.min(dv,MaxDV)
    }
    return dv
  }

  /**
   * 这里的选择性可以定义为 ( output rows / input rows )
   * 列与列之间默认拥有独立性，即选择性可以直接相乘
   * @param predicate 默认这里可能会有复合谓词
   * @return
   */
  def getSel(predicate:String):Double ={
     val predicates: Array[String] = predicate.split("AND")
     var sel:Double = 1.0
     for(pre <- predicates){
       sel *= getSelAtom(pre)
     }
     return sel
  }

  // 这里的谓词是原子的
  // "condition":"((tpch.part.`P_PARTKEY` = 1146411) AND (tpch.part.`P_RETAILPRICE` > 1441.49D))"
  def getSelAtom(predicate:String):Double={
     val p: String = predicate.replaceAll("\\(","").replaceAll("\\)","").replaceAll("`","")
     var sel = 1.0

      // todo check if have other predicate like OR , ... , ...

     if(p.contains("OR")){
       return sel
     }


     if(p.contains("=")){
        val pos: Int = p.indexOf("=")
        val col_all  = p.substring(0,pos).replaceAll(" ","")
        val op = p.substring(pos,pos+1).replaceAll(" ","")
        val oprand = p.substring(pos+1).replaceAll(" ","").replaceAll("D","")

        // todo check oprand should contains digits only


        val cols: Array[String] = col_all.split("\\.")
        val col: String = cols(cols.length-1)

        // todo get sel
       if(col_ifUniform.get(col).nonEmpty){
         if(col_ifUniform(col)==true){

           val card : Double = cardMap(Col_table(col))
           val distinctNum : Double = Col_distinctNum(col)

           sel = 1.0/distinctNum

         }else{

           val card : Double = cardMap(Col_table(col))
           val distinctNum: Double = Col_distinctNum(col)

           sel = 1.0 / distinctNum
         }

       }
     }


    if(p.contains(">")){
      val pos: Int = p.indexOf(">")
      val col_all  = p.substring(0,pos).replaceAll(" ","")
      val op = p.substring(pos,pos+1).replaceAll(" ","")
      val oprand = p.substring(pos+1).replaceAll(" ","").replaceAll("D","")


      val cols: Array[String] = col_all.split("\\.")
      val col: String = cols(cols.length-1)

      // todo get sel
      if(col_ifUniform.get(col).nonEmpty){
        if(col_ifUniform(col)==true){
          val cmin: Double = col_min(col)
          val cmax: Double = col_max(col)
          val oprand_num :Double = oprand.toDouble
          sel = (cmax - oprand_num)/(cmax-cmin)
        }else{
          val cmin: Double = col_min(col)
          val cmax: Double = col_max(col)
          val oprand_num :Double = oprand.toDouble
          val gap : Double = (cmax - cmin)/100.0

          // todo need to +1
          val right_intervals:Int = ((cmax - oprand_num)/gap).toInt + 1
          var cnt:Double = 0.0
          var all:Double = 0.0

          for(i <- 99-right_intervals+1 to 99){
            cnt += nonuniform_col_his(col)(i).toDouble
          }
          for(i <- 0 to 99){
            all += nonuniform_col_his(col)(i).toDouble
          }

          sel = cnt / all
        }
      }

    }

    if(p.contains("<")){
      val pos: Int = p.indexOf("<")
      val col_all  = p.substring(0,pos).replaceAll(" ","")
      val op = p.substring(pos,pos+1).replaceAll(" ","")
      val oprand = p.substring(pos+1).replaceAll(" ","").replaceAll("D","")


      val cols: Array[String] = col_all.split("\\.")
      val col: String = cols(cols.length-1)

      // todo get sel
      if( col_ifUniform.get(col).nonEmpty ){
        if(col_ifUniform(col)==true){
          val cmin: Double = col_min(col)
          val cmax: Double = col_max(col)
          val oprand_num :Double = oprand.toDouble

          sel = (oprand_num-cmin)/(cmax-cmin)

        }else{
          val cmin: Double = col_min(col)
          val cmax: Double = col_max(col)
          val oprand_num :Double = oprand.toDouble
          val gap : Double = (cmax - cmin)/100.0

          //todo need to +1
          val left_intervals:Int = ((oprand_num-cmin)/gap).toInt + 1

          var cnt:Double = 0.0
          var all:Double = 0.0

          for(i <- 0 to 0+left_intervals-1 ){
            cnt += nonuniform_col_his(col)(i).toDouble
          }

          for(i <- 0 to 99){
            all += nonuniform_col_his(col)(i).toDouble
          }

          sel = cnt / all
        }
      }
    }
    return sel
  }

    val cardMap : Map[String,Double] = Map(
      "lineitem" -> 59986052.0 ,
      "nation" -> 25.0 ,
      "region" -> 5.0 ,
      "part" ->  2000000.0 ,
      "supplier" -> 100000.0 ,
      "partsupp" -> 8000000.0 ,
      "customer" -> 1500000.0 ,
      "orders" -> 15000000.0
    )

    val Col_table : Map[String,String] = Map (
      "L_LINENUMBER" -> "lineitem" ,
      "L_QUANTITY" -> "lineitem" ,
      "L_EXTENDEDPRICE" -> "lineitem" , // not uniform
      "L_DISCOUNT" -> "lineitem" ,
      "L_TAX" -> "lineitem" ,

      "L_ORDERKEY" -> "lineitem"  ,           // foreign key
      "L_SUPPKEY" -> "lineitem" ,            // foreign key
      "L_PARTKEY" -> "lineitem" ,            // foreign key


      "N_NATIONKEY" -> "nation" ,
      "N_REGIONKEY" -> "nation" ,          // foreign key

      "R_REGIONKEY" -> "region" ,

      "P_PARTKEY" ->  "part" ,
      "P_SIZE" -> "part" ,
      "P_RETAILPRICE" -> "part" ,   // not uniform

      "S_SUPPKEY" ->  "supplier" ,
      "S_ACCTBAL" -> "supplier" ,
      "S_NATIONKEY" -> "supplier" ,        // foreign key


      "PS_AVAILQTY" -> "partsupp" ,
      "PS_SUPPLYCOST" -> "partsupp" ,
      "PS_SUPPKEY" -> "partsupp",          // foreign key
      "PS_PARTKEY" -> "partsupp",          // foreign key

      "C_CUSTKEY" ->   "customer" ,
      "C_ACCTBAL" ->  "customer" ,
      "C_NATIONKEY" -> "customer" ,        // foreign key

      "O_ORDERKEY" -> "orders" ,
      "O_TOTALPRICE" -> "orders",   // not uniform
      "O_CUSTKEY" -> "orders"             // foreign key
    )

    val Col_distinctNum : Map[String,Double] = Map(
      "L_LINENUMBER" -> 7.0 ,
      "L_QUANTITY" -> 50.0 ,
      "L_EXTENDEDPRICE" -> 1351462.0 , // not uniform
      "L_DISCOUNT" -> 11.0 ,
      "L_TAX" -> 9.0 ,
      "L_ORDERKEY" -> 15000000.0  ,           // foreign key
      "L_SUPPKEY" -> 100000.0 ,            // foreign key
      "L_PARTKEY" -> 2000000.0 ,            // foreign key

      "N_NATIONKEY" -> 25.0 ,
      "N_REGIONKEY" -> 5.0  ,          // foreign key


      "R_REGIONKEY" -> 5.0 ,

      "P_PARTKEY" -> 2000000.0 ,
      "P_SIZE" -> 50.0 ,
      "P_RETAILPRICE" -> 31681.0 ,   // not uniform

      "S_SUPPKEY" ->  100000.0 ,
      "S_ACCTBAL" -> 95588.0 ,
      "S_NATIONKEY" -> 25.0 ,        // foreign key


      "PS_AVAILQTY" -> 9999.0 ,
      "PS_SUPPLYCOST" -> 99901.0 ,
      "PS_SUPPKEY" -> 100000.0,          // foreign key
      "PS_PARTKEY" -> 2000000.0,          // foreign key

      "C_CUSTKEY" ->   1500000.0 ,
      "C_ACCTBAL" ->  818834.0 ,
      "C_NATIONKEY" -> 25.0 ,        // foreign key

      "O_ORDERKEY" -> 15000000.0 ,
      "O_TOTALPRICE" -> 11944103.0 ,   // not uniform
      "O_CUSTKEY" -> 999982.0             // foreign key
    )

    val col_ifUniform : Map[String,Boolean] = Map(
      "L_LINENUMBER" -> true ,
      "L_QUANTITY" -> true ,
      "L_EXTENDEDPRICE" -> false , // not uniform
      "L_DISCOUNT" -> true ,
      "L_TAX" -> true ,

      "N_NATIONKEY" -> true ,

      "R_REGIONKEY" -> true ,

      "P_PARTKEY" -> true ,
      "P_SIZE" -> true ,
      "P_RETAILPRICE" -> false ,   // not uniform

      "S_SUPPKEY" ->  true ,
      "S_ACCTBAL" -> true ,


      "PS_AVAILQTY" -> true ,
      "PS_SUPPLYCOST" -> true ,

      "C_CUSTKEY" ->   true ,
      "C_ACCTBAL" ->  true ,

      "O_ORDERKEY" -> true ,
      "O_TOTALPRICE" -> false   // not uniform
    )

    val col_min : Map[String,Double] = Map(
      "L_LINENUMBER" -> 1.0 ,
      "L_QUANTITY" -> 1.0  ,
      "L_EXTENDEDPRICE" ->  900.0 , // not uniform  900.0   900.91是真实的最小值，这里为了方便取了近似的 900.0
      "L_DISCOUNT" -> 0.0 ,
      "L_TAX" -> 0.0 ,

      "N_NATIONKEY" -> 0.0 ,

      "R_REGIONKEY" -> 0.0 ,

      "P_PARTKEY" -> 1.0 ,
      "P_SIZE" -> 1.0 ,
      "P_RETAILPRICE" -> 900.0 ,   // not uniform 900.0   同样的 900.91是最小值，这里同样取了近似的900.0

      "S_SUPPKEY" ->  1.0 ,
      "S_ACCTBAL" -> -999.92 ,


      "PS_AVAILQTY" -> 1.0 ,
      "PS_SUPPLYCOST" -> 1.0  ,

      "C_CUSTKEY" ->   1.0 ,
      "C_ACCTBAL" ->  -999.99 ,

      "O_ORDERKEY" -> 1.0 ,
      "O_TOTALPRICE" -> 838.0   // not uniform  838.0    这里真实值是 838.05 ,  这里取了 838.0
    )

  val col_max : Map[String,Double] = Map(
    "L_LINENUMBER" -> 7.0 ,
    "L_QUANTITY" -> 50.0  ,
    "L_EXTENDEDPRICE" ->  104950.0  , // not uniform   104950.0    真实值为： 104949.5
    "L_DISCOUNT" -> 0.1  ,
    "L_TAX" -> 0.08  ,

    "N_NATIONKEY" -> 24.0 ,

    "R_REGIONKEY" -> 4.0 ,

    "P_PARTKEY" -> 2000000.0  ,
    "P_SIZE" -> 50.0  ,
    "P_RETAILPRICE" ->  2099.0 ,   // not uniform 2099.0  真实值为 2098.99

    "S_SUPPKEY" ->  100000.0 ,
    "S_ACCTBAL" -> 9999.93 ,


    "PS_AVAILQTY" -> 9999.0 ,
    "PS_SUPPLYCOST" -> 1000.0 ,

    "C_CUSTKEY" ->   1500000.0  ,
    "C_ACCTBAL" ->  9999.99  ,

    "O_ORDERKEY" -> 60000000.0 ,
    "O_TOTALPRICE" ->  558823.0  // not uniform 558823.0  真实值 558822.6
  )

  // print(get_list(900.0 , 104950.0 , 100))

  val his_l : Array[Int] = Array(
    1138180 , 676511 , 1004550 , 763224 , 943575 , 806124 , 910203, 829909, 893874, 845825,
    882944 , 857742 , 874651 , 860042 , 871257 , 864350 , 868575, 864901 , 867073 , 863205,
    870971 , 864380 , 871825, 862349 , 873761 , 862484, 870133, 864180, 869085, 864690,
    872312 , 862446 , 867194, 865890,  867326 , 866944, 868963, 869199, 864992, 869438,
    868833 , 868528 , 865251, 867116, 863386, 855549,  847523, 837173, 823361, 808927,
    789655, 767915, 745516, 723653, 698604, 677959, 656080, 630973, 612774, 593982,
    571626, 550212, 530233, 510953, 492951, 472829, 453295, 436803, 421348, 399625,
    383969, 365088, 348738, 331428, 316959, 298536, 283806, 265733, 249158, 237365,
    218971, 203460, 191511, 172185, 161432, 143907, 130182, 117559, 102340, 88066,
    74320, 62463, 48527, 37573, 28235, 19606, 13503, 8120, 4040, 1362
  )


  //  print(get_list(900.0 , 2099.0 , 100))

  val his_p : Array[Int] = Array(
    779, 2196, 3612, 5028, 6444, 7896, 9360, 10824, 12280, 13730,
    15180, 16620, 18060, 19500, 20940, 22380, 23757, 24102, 24088, 24074,
    24058, 24044, 24030, 24016, 24002, 24000, 24000, 24000, 24000, 24000,
    24000, 24000, 24000, 24000, 24000, 24000, 24000, 24000, 24000, 24000,
    24000, 24000, 24000, 24000, 24000, 23998, 23960, 23912, 23864, 23816,
    23780, 23780, 23780, 23780, 23780, 23816, 23864, 23912, 23958, 24000,
    24000, 24000, 24000, 24000, 24000, 24000, 24000, 24000, 24000, 24000,
    24000, 24000, 24000, 24000, 24000, 24000, 24000, 24000, 24000, 24000,
    24000, 24000, 24000, 23720, 22380, 20940, 19500, 18060, 16620, 15180,
    13740, 12300, 10860, 9420, 7980, 6540, 5100, 3660, 2220, 780
  )

  // print(get_list(838.0 ,  558823.0 , 100))
  val his_o : Array[Int] = Array(
    176947, 187828, 203404, 218717, 238319, 256913, 276491, 299039, 319958, 326123,
    328541, 327665, 327220, 325623, 323784, 318508, 315661, 311229, 310830, 312132,
    314382, 315913, 315499, 315266, 314729, 313422, 312193,  309620, 307200, 305536,
    303916, 299391, 297163, 291177, 287089, 282164, 276585, 269347, 263214, 255697,
    246481, 238285, 229339, 219533, 208393, 198717, 187525, 176431, 165506, 154482,
    142817, 131534, 121993, 111293, 101281, 91484, 82762, 73534, 65611, 58231,
    51350, 44933, 39096, 34012, 28740, 24741, 21119, 17842, 14963, 12532,
    10265, 8316, 6887, 5584, 4426, 3555, 2826, 2186, 1722, 1326,
    1032, 741, 570, 422, 326, 230, 190, 106, 83, 57,
    54, 42, 18, 13, 6, 9, 4, 2, 3, 4
  )

  val nonuniform_col_his : Map[String,Array[Int]] = Map(
    "L_EXTENDEDPRICE" -> his_l,
    "P_RETAILPRICE" -> his_p,
    "O_TOTALPRICE" -> his_o
  )
    def getDepth(plan:LogicalPlan):Int= {
        var aggRoot:LogicalPlan  = null

        plan transform{
          case agg @ Aggregate(groupingExpressions,aggregateExpressions,child)=>
            aggRoot = agg
            agg
        }



        return depthHelper(aggRoot)

    }

    // 基于规则选择待执行的查询计划
    // RULE : 选择采样器下推最远的逻辑计划，若存在多个则随机选择一个
    // todo 这里需要设计一个cost函数，相同深度的时候，采样器下方的数据量越大效果越好
    def pickPlanByRule(plans : Seq[LogicalPlan]): LogicalPlan  = {
        var resPlan = plans(0)

        var maxCost:Double = 0.0
        var maxDep = -999999

        for( plan <- plans ){

            val curDep = getDepth(plan)
            val curCost = getCost(plan)

            println("curdep    "+curDep)
            if( curDep > maxDep ){
               maxDep = curDep
               maxCost = curCost
               resPlan = plan
            }else if(curDep == maxDep){
              if(curCost > maxCost){
                  maxCost = curCost
                resPlan = plan
              }
            }
        }
       return resPlan
    }

    def pickPlanWithOutPushDown(plans : Seq[LogicalPlan]):LogicalPlan = {
       plans(0)
    }


    def pickPlanByDistinctRule(plans : Seq[LogicalPlan]): LogicalPlan ={
       var uniformPlans : Seq[LogicalPlan] = Seq.empty
       var distinctPlans : Seq[LogicalPlan] = Seq.empty
       for(waitPlan <- plans){
        val aqpSample = newGetAqpSample(waitPlan)
        if(aqpSample.asInstanceOf[AqpSample].stratificationSet.nonEmpty){
          distinctPlans  = ( distinctPlans :+ waitPlan )
        }else{
          uniformPlans   = ( uniformPlans :+ waitPlan )
        }
      }

      if(distinctPlans.nonEmpty){
        return pickPlanByRule(distinctPlans)
      }else{
        return pickPlanByRule(uniformPlans)
      }
    }


    def pickPlansByModel(plans: Seq[LogicalPlan]): LogicalPlan = {
          val tTransport:TTransport = new TFramedTransport(new TSocket("10.176.24.40",9990))
          val protocol : TProtocol = new TCompactProtocol(tTransport)
          val client : PredictService.Client = new PredictService.Client(protocol)

          tTransport.open()

          val sendPlans : Plans = new Plans()
          val message: util.List[String] = new util.ArrayList[String]()

          for(plan <- plans){
             message.add(MyUtils.getAggRoot(plan).toJSON)
             //message.add(plan.toJSON)
          }

          sendPlans.setPlans(message)

          val result: Errors = client.predict(sendPlans)

          val predict: util.List[lang.Double] = result.result

          //todo 不一定是真正的backup
          // 需要将采样率设置为1
          val status = newGetAqpSample(plans(0))
          val errorRate: ErrorRate = status.asInstanceOf[AqpSample].errorRate
          val rate: Double = errorRate.getRate


          val backup = removeAQP(plans(0))



          var waitlist: Seq[LogicalPlan] = Seq.empty


          for( index <- 1 to plans.length-1 ){
                //todo 改变条件
                if( predict.get(index) <=  rate ){
                    waitlist=(waitlist:+ plans(index))
                }
          }

          //todo 启发式的选择满足用户要求的执行计划

          if(waitlist.isEmpty){
              // 应该有更好的方式,直接移除aqp
              return backup
          }else{
              // 这里使用

              var uniformPlans : Seq[LogicalPlan] = Seq.empty
              var distinctPlans : Seq[LogicalPlan] = Seq.empty

              for(waitPlan <- waitlist){
                 val aqpSample = newGetAqpSample(waitPlan)
                 if(aqpSample.asInstanceOf[AqpSample].stratificationSet.nonEmpty){
                    distinctPlans  = ( distinctPlans :+ waitPlan )
                 }else{
                    uniformPlans   = ( uniformPlans :+ waitPlan )
                 }
              }
              if(uniformPlans.nonEmpty){
                return pickPlanByRule(uniformPlans)
              }else{
                return pickPlanByRule(distinctPlans)
              }
          }
    }



    def splitConjunctivePredicates(condition:Expression): Seq[Expression] = {
      condition match{
        case And(cond1,cond2) =>
          splitConjunctivePredicates(cond1) ++ splitConjunctivePredicates(cond2)
        case other => other :: Nil
      }
    }

  /**
   * 根据 AqpSample的状态来选择具体的物理采样器的实现（UniformSamplerExec/DistinctSamplerExec）
   *
   * @param plan
   * @return
   */
    def choosePhysicalSampler(plan : AqpSample) : Seq[SparkPlan] = {
      println("choosing plan child output"+plan.child.output)
      plan.child.output.zipWithIndex.foreach{
        case (c,index) =>
          println("index"+index+"dataType"+c.dataType+"name"+c.name)
      }
       if(plan.stratificationSet.nonEmpty){
         // Seq[DistinctColumn]
         // case class DistinctColumn(ordinal:Int,datatype: DataType, name: String)
         //  (child.output :+ weight).zipWithIndex.foreach{case (exp,ti) => println(ti+"  "+row.get(ti,exp.dataType))}



         var list: List[DistinctColumn]=List()
         plan.stratificationSet.foreach( s => {
           plan.child.output.zipWithIndex.foreach{
             case (c,index) =>
               println("index"+index+"dataType"+c.dataType+"name"+c.name)
               if(c.asInstanceOf[AttributeReference].exprId.id == s.asInstanceOf[AttributeReference].exprId.id){
                  list = (list ::: List(new DistinctColumn(index,c.dataType,c.name)))
               }
           }
         })
         println("Distinct List......------------"+list)
        //List(new DistinctColumn(1,StringType,"name"))
         DistinctSamplerExec(plan.errorRate,plan.confidence,plan.seed,PlanLater(plan.child),list ,plan.delta,plan.sampleFraction,plan.parallel,plan.nameE)::Nil
         //选择分层采样器
       }else{
         //选择均匀采样器
         UniformSamplerExec(plan.errorRate,plan.confidence,plan.seed, PlanLater(plan.child),plan.nameE,plan.sampleFraction )::Nil
       }
    }

  /**
   * 打印stats信息
   */
  def checkStats(plan:LogicalPlan)={
     plan.foreach(
       p => println(p.simpleString+"  "+p.stats.toString)
     )
  }

}
