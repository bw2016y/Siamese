import java.io.{File, FileWriter, PrintWriter}
import java.{lang, util}

import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.optimizer.MyUtils.{ removeAQP}
import org.apache.spark.daslab.sql.engine.optimizer.{DfsPushDown, MyUtils}
import org.apache.spark.daslab.sql.engine.plans.logical.{AqpSample, LogicalPlan}
import org.apache.spark.daslab.sql.execution.{QueryExecution, SparkPlan}
import org.apache.spark.daslab.sql.{DataFrame, Row, SaveMode, SparkSession}
import org.apache.spark.daslab.sql.streaming.StreamingQuery
import org.apache.spark.daslab.sql.functions._
import org.apache.spark.daslab.sql.types.{LongType, StructType}
import org.apache.spark.daslab.thrift.gen.{Errors, Plans, PredictService}
import org.apache.spark.rdd.RDD
import org.apache.thrift.protocol.{TCompactProtocol, TProtocol}
import org.apache.thrift.transport.{TFramedTransport, TSocket, TTransport}

import scala.collection.mutable
class Person(id:Int){
  def getId():Int = {
    id
  }
}

object  ScalaTest{
  def main(args: Array[String]): Unit = {
//    val listToInt: mutable.HashMap[List[Any], Int] = scala.collection.mutable.HashMap.empty[List[Any], Int]
//    val l1: Long = 1234567891011121314L
//    val l2: Long = 1234567891011121314L
//    val s1: String = "hello"
//    val s2: String = "hello"
//    val list1: List[Any] = l1 :: s1 :: Nil
//    val list2: List[Any] = l2 :: s2 :: Nil
//    listToInt += (list1 -> 1)
//    println(listToInt.get(list2))
//    listToInt.put(list2,2)
//    println(listToInt.get(list1))
//    println(list1.equals(list2))
//    println(list1 == list2)
//    println(list1.eq(list2))

    //初始化上下文
    val spark: SparkSession = SparkSession.builder().master("local[*]")
      .appName("test").config("spark.sql.codegen.wholeStage",false)    //考察基本的代码生成功能，关闭了全阶段代码生成
      .getOrCreate()
    spark.sparkContext.setLogLevel("FATAL")

    import spark.implicits._
    //创建视图
    val dataset=spark.read.json("src/test/resources/student.json").toDF()
    dataset.createTempView("data")
    dataset.createTempView("data1")
    val dataset1=spark.read.json("src/test/resources/grade.json").toDF();
    dataset1.createTempView("gradetable")


    MyUtils.setPickMode(1)

  /*  val testinput ="select data.age from data"
    println(spark.sql(testinput).queryExecution.originLogicalPlan)
    println(spark.sql(testinput).queryExecution.analyzedLogicalPlan)
    println(spark.sql(testinput).queryExecution.optimizedLogicalPlan)
    println(spark.sql(testinput).queryExecution.physicalPlan)
    spark.sql(testinput).show()


    val sql1 = "select data.name from data"
    val df: DataFrame = spark.sql(sql1);
    println(df.queryExecution.physicalPlan)
    df.show()
    //UDF的使用
    //udf1
    spark.udf.register("strlen",(str: String)=>str.length())
    spark.sql("select data.name,strlen(data.name) from data").show()
    //udf2
    def isAdult(age: Int) ={
      if(age<18){
        false
      }else{
        true
      }
    }
    spark.udf.register("judge",isAdult _)
    spark.sql("select data.name ,judge(data.age) from data").show()
    //udf3
    val func1=udf((name:String)=>(name.length()))
    df.select(col("*"),func1(col("name")) as "temp").show()



    val sql2 = "SELECT SUM(AGE),strlen(name) FROM data group by name ERROR WITHIN 5% AT CONFIDENCE 95%"
    println(spark.sql(sql2).queryExecution.originLogicalPlan)
    println(spark.sql(sql2).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sql2).queryExecution.optimizedLogicalPlan)
    println(spark.sql(sql2).queryExecution.physicalPlan)
    //spark.sql(sql2).show

    val sql3 = "SELECT data.name FROM data ERROR WITHIN 5% AT CONFIDENCE 95%"
    println(spark.sql(sql3).queryExecution.originLogicalPlan)
    println(spark.sql(sql3).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sql3).queryExecution.optimizedLogicalPlan)

    val sql4 = "SELECT SUM(AGE),MIN(AGE) FROM data where age < 100 ERROR WITHIN 5% AT CONFIDENCE 95%"
    println(spark.sql(sql4).queryExecution.originLogicalPlan)
    println(spark.sql(sql4).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sql4).queryExecution.optimizedLogicalPlan)
    println(spark.sql(sql4).queryExecution.sparkPlan)
    println(spark.sql(sql4).queryExecution.executedPlan)

    val sql5 = "SELECT name,max(age) from data group by name"
    println(spark.sql(sql5).queryExecution.originLogicalPlan)
    println(spark.sql(sql5).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sql5).queryExecution.optimizedLogicalPlan)
    spark.sql(sql5).queryExecution.toRdd.foreach(println(_))
    spark.sql(sql5).show()

    println(spark.sql("select * from data join gradetable  on data.age = gradetable.age  where data.name='zzz'").queryExecution.analyzedLogicalPlan)
    println(spark.sql("select *  from data join gradetable  on data.age = gradetable.age  where data.name='zzz'" ).queryExecution.optimizedLogicalPlan)

    println(spark.sql("select * from data").repartition(50).coalesce(100).filter("data.age >10").queryExecution.analyzedLogicalPlan)
    println(spark.sql("select * from data").repartition(50).coalesce(100).filter("data.age >10").queryExecution.optimizedLogicalPlan)

    println(spark.sql("select data.* ,(select max(g.grade) from gradetable g where g.age=data.age) as grade from data").queryExecution.analyzedLogicalPlan)
    println(spark.sql("select data.* ,(select max(g.grade) from gradetable g where g.age=data.age) as grade from data").queryExecution.optimizedLogicalPlan)

    val frame = spark.sql("select data.age,gradetable.grade from data join gradetable on data.age=gradetable.age")
    println(frame.queryExecution.originLogicalPlan)
    println(frame.queryExecution.analyzedLogicalPlan)
    println(frame.queryExecution.optimizedLogicalPlan)

    println(spark.sql("select data.sex from data full outer join gradetable on data.age = gradetable.age where data.name = 'zzz' and gradetable.grade >2 ").queryExecution.analyzedLogicalPlan)
    println(spark.sql("select data.sex from data full outer join gradetable on data.age = gradetable.age where data.name = 'zzz' and gradetable.grade >2 ").queryExecution.optimizedLogicalPlan)

    println(spark.sql("select name from (select * from data)").queryExecution.analyzedLogicalPlan)
    println(spark.sql("select name from (select * from data)").queryExecution.optimizedLogicalPlan)

    println(spark.sql("select max(data.age) from data error within 5% at confidence 70%").queryExecution.analyzedLogicalPlan)
    println(spark.sql("select max(data.age) from data error within 5% at confidence 70%").queryExecution.optimizedLogicalPlan)



    val sql6 = "SELECT * from data"
    println(spark.sql(sql6).sample(false,0.5).agg(max("age")).queryExecution.originLogicalPlan)
    println(spark.sql(sql6).sample(false,0.5).agg(max("age")).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sql6).sample(false,0.5).agg(max("age")).queryExecution.optimizedLogicalPlan)
    println(spark.sql(sql6).sample(false,0.5).agg(max("age")).queryExecution.physicalPlan)
    println(spark.sql(sql6).sample(false,0.5).agg(max("age")).queryExecution.executedPhysicalPlan)
    spark.sql(sql6).sample(false,0.2).agg(max("age")).show()
    val executedPlan6: SparkPlan = spark.sql(sql6).queryExecution.executedPlan
    val value6: RDD[InternalRow] = executedPlan6.execute()
    value6.foreach(a =>{
      println(a)
    })*/
    val sql9= "select sum(age),count(age),avg(age) from data group by name"
    spark.sql(sql9).show()


    MyUtils.setFraction(0.5)
    val sql7 = "select name, sum(age),count(age),avg(age) from data group by name  ERROR WITHIN 5% AT CONFIDENCE 95% "
    println(spark.sql(sql7).queryExecution.originLogicalPlan)
    println(spark.sql(sql7).queryExecution.analyzedLogicalPlan)
   // val execution: QueryExecution = spark.sql(sql7).queryExecution
    println(spark.sql(sql7).queryExecution.optimizedLogicalPlan)
    println(spark.sql(sql7).queryExecution.physicalPlan)
    println(spark.sql(sql7).queryExecution.executedPhysicalPlan)
   // val executedPlan: SparkPlan = spark.sql(sql7).queryExecution.executedPlan
  //  val value: RDD[InternalRow] = executedPlan.execute()
   /* value.foreach(a => {
      println(a)
      a.getString(0)
    })*/

  /*  println("SCHEMA start")
    println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS  ana"+spark.sql(sql7).queryExecution.analyzedLogicalPlanSchema)
    println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS opti"+spark.sql(sql7).queryExecution.optimizedLogicalPlanSchema)
    println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS phy "+spark.sql(sql7).queryExecution.physicalPlanSchema)
    println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS  exec"+spark.sql(sql7).queryExecution.executedPhysicalPlanSchema)
    println("SCHEMA end")
*/

    spark.sql(sql7).show(20,false)
   // spark.sql(sql7).show()

   //todo 定义流数据源
   /* val studentSchema = new StructType().add("name","string").add("age","long").add("sex","string").add("teacher","string")
    val streamDF: DataFrame = spark.readStream.schema(studentSchema).json("src/test/resources/stream")
    streamDF.createOrReplaceTempView("stream");
    val sql8 = "select sum(age),count(age) from stream group by name ERROR WITHIN 5% AT CONFIDENCE 95%  "
    val resStream: DataFrame = spark.sql(sql8)

    val query: StreamingQuery = resStream.writeStream.outputMode("complete").format("console").start()

    query.awaitTermination()
*/


    spark.sql("select count(1),sum(grade),avg(grade) from  data join gradetable on data.age=gradetable.age group by name").show(20,false)
    //todo 测试push down through join
    val sqljoin= "select count(1),sum(grade),avg(grade) from  data join gradetable on data.age=gradetable.age group by name ERROR WITHIN 5% AT CONFIDENCE 95%"
    println(spark.sql(sqljoin).queryExecution.originLogicalPlan)
    println(spark.sql(sqljoin).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sqljoin).queryExecution.optimizedLogicalPlan)
    println(spark.sql(sqljoin).queryExecution.physicalPlan)
    println(spark.sql(sqljoin).queryExecution.executedPhysicalPlan)

    spark.sql(sqljoin).show(20,false)



  /*  val sql8 = "SELECT (SELECT (SELECT age FROM data) FROM data) from data"
    println(spark.sql(sql8).queryExecution.originLogicalPlan)
    println(spark.sql(sql8).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sql8).queryExecution.optimizedLogicalPlan)
    println(spark.sql(sql8).queryExecution.physicalPlan)
    println(spark.sql(sql8).queryExecution.executedPhysicalPlan)



    val lll=dataset1.withColumn("const",lit(1))
    val qeee=lll.queryExecution
    println(lll.queryExecution.originLogicalPlan)
    println(lll.queryExecution.analyzedLogicalPlan)
    println(lll.queryExecution.optimizedLogicalPlan)
    println(lll.queryExecution.physicalPlan)
    println(lll.queryExecution.executedPhysicalPlan)


    val sqlproject = "select  age  from data"
    println(spark.sql(sqlproject).queryExecution.originLogicalPlan)
    println(spark.sql(sqlproject).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sqlproject).queryExecution.optimizedLogicalPlan)
    println(spark.sql(sqlproject).queryExecution.physicalPlan)
    println(spark.sql(sqlproject).queryExecution.executedPhysicalPlan)
    spark.sql(sqlproject).show();*/

    val sqlCount = "select count(2) from data"
    spark.sql(sqlCount).show()
    val sqlCountSample= "select count(2) from data ERROR WITHIN 5% AT CONFIDENCE 95%"
    spark.sql(sqlCountSample).show()
    val toughSql = "select sum(age * 2),avg(age*2) from data"
    spark.sql(toughSql).show()



    val toughSqlSample = "select sum(age * 2),avg(age*2)  from data ERROR WITHIN 5% AT CONFIDENCE 95%"
    spark.sql(toughSqlSample).show()

  //  spark.sql(toughSql).write.format("csv").mode("append").save("outputres")
  //  spark.sql(toughSqlSample).write.format("csv").mode("append").save("outputres")
    //val frame: DataFrame = spark.sql(toughSql)
    ///frame.rdd.saveAsTextFile()


    /*spark.sql(toughSql).coalesce(1).write.mode(SaveMode.Append)
      .option("header","true")
      .csv("outputres/test.csv")*/




    /*val  target = new File("./sqlout/res.txt")
    if(!target.exists()){
      target.createNewFile()
    }
    sqls.zipWithIndex.foreach{
      case (sql,index) =>
        spark.sql(sql).coalesce(1).rdd.collect().foreach(
          x=>{
            val fileWriter = new FileWriter("./sqlout/res.txt", true)
            val out = new PrintWriter(fileWriter)
            val sb = new StringBuilder

            sb.append(index + ",")
            sb.append(x.mkString(","))

            out.println(sb)
            out.flush()

            out.close()
            fileWriter.close()
          }
        )
    }*/

    val plan: LogicalPlan = spark.sessionState.sqlParser.parsePlan("select count(1),sum(grade),avg(grade) from  data join gradetable on data.age=gradetable.age where grade>1 group by name ERROR WITHIN 5% AT CONFIDENCE 95% ")
    val analyzed :LogicalPlan= spark.sessionState.analyzer.executeAndCheck(plan)
    val withCachedData: LogicalPlan = spark.sharedState.cacheManager.useCachedData(analyzed)
    val optimizedPlan: LogicalPlan = spark.sessionState.optimizer.execute(withCachedData)
    val allOptimizedPlan: Seq[LogicalPlan] = DfsPushDown.gen(optimizedPlan)

    for(i<- 0 to allOptimizedPlan.length-1){
      println("00000000000000000000000000000000000"+i)
      println(allOptimizedPlan(i))
    }

    MyUtils.checkStats(allOptimizedPlan(5))

    println(allOptimizedPlan.length)
    MyUtils.setPlan(0)
    MyUtils.setFraction(0.3)
    //println(allOptimizedPlan(0).toJSON)
    println(spark.sql("select count(1),sum(grade),avg(grade) from  data join gradetable on data.age=gradetable.age where grade>1 group by name ERROR WITHIN 5% AT CONFIDENCE 95% ").queryExecution.physicalPlan)
    spark.sql("select count(1),sum(grade),avg(grade) from  data join gradetable on data.age=gradetable.age where grade>1 group by name ERROR WITHIN 5% AT CONFIDENCE 95% ").show(21,false)
    MyUtils.setPlan(0)
    MyUtils.setFraction(0.9)
    println(allOptimizedPlan(1))
    spark.sql(toughSqlSample).show(21,false)
  //   println(spark.sql(toughSqlSample).queryExecution.physicalPlan)
  //  println(spark.sql(toughSqlSample).queryExecution.executedPhysicalPlan)
  //  println(allOptimizedPlan(2))
  //  allOptimizedPlan.foreach(plan => println(plan.toJSON))


    spark.sessionState.catalog.tempViews.foreach{
      case (name,plan) =>
        println(name+"   "+plan)
    }

    //MyUtils.setPlan(0)
    //val checkRule: DataFrame = spark.sql("select count(1) from  data join gradetable on data.age=gradetable.age")
    //checkRule.show()
    //println(checkRule.queryExecution.physicalPlan)


    //val arr = Array(1,2,3,4,5)
    //val rdd = spark.sparkContext.parallelize(arr)
    //println(spark.sparkContext.defaultParallelism)
    //println(rdd.getNumPartitions)

    //MyUtils.setPlan(0)
    //MyUtils.setFraction(0.5)
    //spark.sql("select name, sum(age),count(age),avg(age) from data group by name  ERROR WITHIN 5% AT CONFIDENCE 95% ").show()

    //spark.sqlContext.cacheTable("data")
    //spark.sql("CACHE TABLE data")
    //println(spark.sql("select * from data").queryExecution.executedPhysicalPlan)
    //spark.sql("select * from data").show()

    //println(spark.sql("select * from data").queryExecution.executedPhysicalPlan)
    //spark.sql("select * from data").show()



   /* MyUtils.setPlan(0)
    MyUtils.setFraction(0.5)
    var bbres = spark.sql("select count(1),sum(grade),avg(grade) from  data join gradetable on data.age=gradetable.age where grade>1 group by name ERROR WITHIN 5% AT CONFIDENCE 95% ")
    bbres.show()*/

   //  bbres.explain(true)
   //  println(bbres.queryExecution.stringWithStats)

    /*spark.sql(toughSqlSample).coalesce(1).rdd.collect().foreach(x => {
      val  fileWriter= new FileWriter("./sqlout/res.txt",true)
      val  out =new PrintWriter(fileWriter)
      val sb:StringBuilder = new StringBuilder()

      sb.append(id+",")
      sb.append(x.mkString(","))
      out.println(sb)
      out.flush()

      out.close()
      fileWriter.close()

    })*/
    /*
    id+=1
    spark.sql(toughSql).coalesce(1).rdd.collect().foreach(x => {
      val  fileWriter= new FileWriter("./sqlout/res.txt",true)
      val  out =new PrintWriter(fileWriter)
      val sb:StringBuilder = new StringBuilder()

      sb.append(id+",")
      sb.append(x.mkString(","))
      out.println(sb)
      out.flush()

      out.close()
      fileWriter.close()

    })*/
    MyUtils.setPickMode(3)
    val rowtest: Array[Row] = spark.sql("select sum(grade) from  data join gradetable on data.age=gradetable.age  ERROR WITHIN 7% AT CONFIDENCE 95% ").coalesce(1).rdd.collect()
    rowtest.foreach(
       x=>{
         println(x.mkString(","))
       }
    )
    // pickPlansByModelTest()

    // spark.sql(toughSql).coalesce(1).write.mode(SaveMode.Append).option("header","true").csv("outputres")
    //spark.sql(toughSql).coalesce(1).rdd.map(r => r.mkString(",")).saveAsTextFile("outpures/res.csv")


    //println(MyUtils.getSel("((tpch.part.`P_PARTKEY` = 1146411) AND (tpch.part.`P_RETAILPRICE` > 1441.49D))"))
    println(MyUtils.getSel("((tpch.part.`P_PARTKEY` = 1146411)"))
    println(MyUtils.getSelAtom("((tpch.part.`P_PARTKEY` = 1146411) "))
    println(MyUtils.getSelAtom(" (tpch.part.`P_RETAILPRICE` > 1441.49D))"))
  }
  def pickPlansByModelTest() = {
    val tTransport:TTransport = new TFramedTransport(new TSocket("10.176.24.40",9990))
    val protocol : TProtocol = new TCompactProtocol(tTransport)
    val client : PredictService.Client = new PredictService.Client(protocol)

    tTransport.open()

    val sendPlans : Plans = new Plans()
    val message: util.List[String] = new util.ArrayList[String]()


    message.add("[{\"class\":\"Aggregate\",\"num-children\":1,\"aggregateExpressions\":\"avg(L_EXTENDEDPRICE)\"},{\"class\":\"Project\",\"num-children\":1,\"projectList\":\"l_extendedprice\"},{\"class\":\"Join\",\"num-children\":2,\"joinType\":\"Inner$\",\"condition\":\"(tpch.supplier.`S_SUPPKEY` = tpch.lineitem.`L_SUPPKEY`)\"},{\"class\":\"AqpSample\",\"num-children\":1,\"stratificationSet\":\"\",\"universeSet\":\"\",\"ds\":1.0,\"sfm\":1.0,\"sampleFraction\":0.07148519364289466,\"delta\":60,\"parallel\":1},{\"class\":\"Project\",\"num-children\":1,\"projectList\":\"s_suppkey\"},{\"class\":\"InMemoryRelation\",\"num-children\":0,\"tableMeta\":\"supplier\"},{\"class\":\"Project\",\"num-children\":1,\"projectList\":\"l_suppkey,l_extendedprice\"},{\"class\":\"InMemoryRelation\",\"num-children\":0,\"tableMeta\":\"lineitem\"}]")
    message.add("[{\"class\":\"Aggregate\",\"num-children\":1,\"aggregateExpressions\":\"avg(L_EXTENDEDPRICE)\"},{\"class\":\"Project\",\"num-children\":1,\"projectList\":\"l_extendedprice\"},{\"class\":\"Join\",\"num-children\":2,\"joinType\":\"Inner$\",\"condition\":\"(tpch.supplier.`S_SUPPKEY` = tpch.lineitem.`L_SUPPKEY`)\"},{\"class\":\"Project\",\"num-children\":1,\"projectList\":\"s_suppkey\"},{\"class\":\"InMemoryRelation\",\"num-children\":0,\"tableMeta\":\"supplier\"},{\"class\":\"AqpSample\",\"num-children\":1,\"stratificationSet\":\"\",\"universeSet\":\"\",\"ds\":1.0,\"sfm\":1.0,\"sampleFraction\":3.4937044157036973E-4,\"delta\":60,\"parallel\":1},{\"class\":\"Project\",\"num-children\":1,\"projectList\":\"l_suppkey,l_extendedprice\"},{\"class\":\"InMemoryRelation\",\"num-children\":0,\"tableMeta\":\"lineitem\"}]")
    message.add("[{\"class\":\"Aggregate\",\"num-children\":1,\"aggregateExpressions\":\"count(C_ACCTBAL)\"},{\"class\":\"Project\",\"num-children\":1,\"projectList\":\"c_acctbal\"},{\"class\":\"Join\",\"num-children\":2,\"joinType\":\"Inner$\",\"condition\":\"(tpch.nation.`N_NATIONKEY` = tpch.customer.`C_NATIONKEY`)\"},{\"class\":\"AqpSample\",\"num-children\":1,\"stratificationSet\":\"\",\"universeSet\":\"\",\"ds\":1.0,\"sfm\":1.0,\"sampleFraction\":0.009130285339766887,\"delta\":60,\"parallel\":1},{\"class\":\"Project\",\"num-children\":1,\"projectList\":\"n_nationkey\"},{\"class\":\"InMemoryRelation\",\"num-children\":0,\"tableMeta\":\"nation\"},{\"class\":\"Project\",\"num-children\":1,\"projectList\":\"c_nationkey,c_acctbal\"},{\"class\":\"InMemoryRelation\",\"num-children\":0,\"tableMeta\":\"customer\"}]")

    sendPlans.setPlans(message)

    val result: Errors = client.predict(sendPlans)

    val predict: util.List[lang.Double] = result.result

    println(predict)

  }
  val sqls= "select sum(age * 2),avg(age*2) from data" ::
    "select sum(age * 2),avg(age*2)  from data ERROR WITHIN 5% AT CONFIDENCE 95%" ::
    Nil
}
