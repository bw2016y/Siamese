import org.apache.spark.daslab.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.daslab.sql.functions._
object Test{
  def main(args: Array[String]): Unit = {
    println("ass")

    val spark: SparkSession = SparkSession.builder().master("local[*]")
      .appName("test")
      .getOrCreate()
    spark.sparkContext.setLogLevel("FATAL")

    import spark.implicits._

    val dataset=spark.read.json("src/test/resources/student.json").toDF();
    dataset.createTempView("data")

    val df: DataFrame = spark.sql("select data.name from data");

    df.show()
    println(spark.sql("select data.name from data").queryExecution)


    spark.udf.register("strlen",(str: String)=>str.length())

    spark.sql("select data.name,strlen(data.name) from data").show()

    def isAdult(age: Int) ={
      if(age<18){
        false
      }else{
        true
      }
    }
    spark.udf.register("judge",isAdult _)
    spark.sql("select data.name ,judge(data.age) from data").show()

    val func1=udf((name:String)=>(name.length()))
    df.show()
    df.select(col("*"),func1(col("name")) as "temp").show()

    println(spark.sql("SELECT SUM(AGE),strlen(name) FROM data group by name ERROR WITHIN 5% AT CONFIDENCE 95%").queryExecution.logical)
    println(spark.sql("SELECT SUM(AGE),strlen(name) FROM data group by name ERROR WITHIN 5% AT CONFIDENCE 95%").queryExecution.analyzed)
    println(spark.sql("SELECT SUM(AGE),strlen(name) FROM data group by name ERROR WITHIN 5% AT CONFIDENCE 95%").queryExecution.optimizedPlan)

    println(spark.sql("SELECT data.name FROM data ERROR WITHIN 5% AT CONFIDENCE 95%").queryExecution.logical)
    println(spark.sql("SELECT data.name FROM data ERROR WITHIN 5% AT CONFIDENCE 95%").queryExecution.analyzed)
    println(spark.sql("SELECT data.name FROM data ERROR WITHIN 5% AT CONFIDENCE 95%").queryExecution.optimizedPlan)

    println(spark.sql("SELECT SUM(AGE),MIN(AGE) FROM data ERROR WITHIN 5% AT CONFIDENCE 95%").queryExecution.logical)
    println(spark.sql("SELECT SUM(AGE),MIN(AGE) FROM data ERROR WITHIN 5% AT CONFIDENCE 95%").queryExecution.analyzed)
    println(spark.sql("SELECT SUM(AGE),MIN(AGE) FROM data ERROR WITHIN 5% AT CONFIDENCE 95%").queryExecution.optimizedPlan)

  }

}
