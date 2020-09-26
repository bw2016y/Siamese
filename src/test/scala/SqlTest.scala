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

    val sql1 = "select data.name from data"
    val df: DataFrame = spark.sql(sql1);
    df.show()
    //使用udf1
    spark.udf.register("strlen",(str: String)=>str.length())
    spark.sql("select data.name,strlen(data.name) from data").show()
    //使用udf2
    def isAdult(age: Int) ={
      if(age<18){
        false
      }else{
        true
      }
    }
    spark.udf.register("judge",isAdult _)
    spark.sql("select data.name ,judge(data.age) from data").show()
    //使用udf3
    val func1=udf((name:String)=>(name.length()))
    df.select(col("*"),func1(col("name")) as "temp").show()

    val sql2 = "SELECT SUM(AGE),strlen(name) FROM data group by name ERROR WITHIN 5% AT CONFIDENCE 95%"
    println(spark.sql(sql2).queryExecution.originLogicalPlan)
    println(spark.sql(sql2).queryExecution.analyzedLogicalPlan)
    println(spark.sql(sql2).queryExecution.optimizedLogicalPlan)
    spark.sql(sql2).show

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
  }

}
