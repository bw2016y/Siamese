import org.apache.spark.daslab.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.daslab.sql.functions._
object  ScalaTest{
  def main(args: Array[String]): Unit = {
    //初始化上下文
    val spark: SparkSession = SparkSession.builder().master("local[*]")
      .appName("test")
      .getOrCreate()
    spark.sparkContext.setLogLevel("FATAL")

    import spark.implicits._
    //创建视图
    val dataset=spark.read.json("src/test/resources/student.json").toDF();
    dataset.createTempView("data")
    dataset.createTempView("data1")
    val dataset1=spark.read.json("src/test/resources/grade.json").toDF();
    dataset1.createTempView("gradetable")

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

    val sql5 = "SELECT max(age) from data"
    println(spark.sql(sql5).queryExecution.originLogicalPlan)
    println(spark.sql(sql5).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sql5).queryExecution.optimizedLogicalPlan)

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

    val sql7 = "select max(age) from data ERROR WITHIN 5% AT CONFIDENCE 95%"
    println(spark.sql(sql7).queryExecution.originLogicalPlan)
    println(spark.sql(sql7).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sql7).queryExecution.optimizedLogicalPlan)
    println(spark.sql(sql7).queryExecution.physicalPlan)
    println(spark.sql(sql7).queryExecution.executedPhysicalPlan)
    spark.sql(sql7).show()

  }

}
