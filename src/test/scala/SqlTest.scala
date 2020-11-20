import org.apache.spark.daslab.sql.engine.InternalRow
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.execution.{QueryExecution, SparkPlan}
import org.apache.spark.daslab.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.daslab.sql.functions._
import org.apache.spark.daslab.sql.types.LongType
import org.apache.spark.rdd.RDD
import scala.collection.mutable


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
    val sql9= "select sum(age) from data group by name"
    spark.sql(sql9).show()

    val sql7 = "select sum(age) from data group by name  ERROR WITHIN 5% AT CONFIDENCE 95% "
    println(spark.sql(sql7).queryExecution.originLogicalPlan)
    println(spark.sql(sql7).queryExecution.analyzedLogicalPlan)
    val execution: QueryExecution = spark.sql(sql7).queryExecution
    println(spark.sql(sql7).queryExecution.optimizedLogicalPlan)
    println(spark.sql(sql7).queryExecution.physicalPlan)
    println(spark.sql(sql7).queryExecution.executedPhysicalPlan)
   // val executedPlan: SparkPlan = spark.sql(sql7).queryExecution.executedPlan
  //  val value: RDD[InternalRow] = executedPlan.execute()
   /* value.foreach(a => {
      println(a)
      a.getString(0)
    })*/

    println("SCHEMA start")
    println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS  ana"+spark.sql(sql7).queryExecution.analyzedLogicalPlanSchema)
    println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS opti"+spark.sql(sql7).queryExecution.optimizedLogicalPlanSchema)
    println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS phy "+spark.sql(sql7).queryExecution.physicalPlanSchema)
    println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS  exec"+spark.sql(sql7).queryExecution.executedPhysicalPlanSchema)
    println("SCHEMA end")


    spark.sql(sql7).show(20,false)
    spark.sql(sql7).filter()
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

  }

}
