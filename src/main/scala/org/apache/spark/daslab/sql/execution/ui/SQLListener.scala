package org.apache.spark.daslab.sql.execution.ui


import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.`type`.TypeFactory
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.util.Converter



import org.apache.spark.daslab.sql.execution.SparkPlanInfo
import org.apache.spark.daslab.sql.execution.metric._
//todo
import org.apache.spark.annotation.DeveloperApi
import org.apache.spark.scheduler._

@DeveloperApi
case class SparkListenerSQLExecutionStart(
                                           executionId: Long,
                                           description: String,
                                           details: String,
                                           physicalPlanDescription: String,
                                           sparkPlanInfo: SparkPlanInfo,
                                           time: Long)
  extends SparkListenerEvent

@DeveloperApi
case class SparkListenerSQLExecutionEnd(executionId: Long, time: Long)
  extends SparkListenerEvent

/**
 * A message used to update SQL metric value for driver-side updates (which doesn't get reflected
 * automatically).
 *
 * @param executionId The execution id for a query, so we can find the query plan.
 * @param accumUpdates Map from accumulator id to the metric value (metrics are always 64-bit ints).
 */
@DeveloperApi
case class SparkListenerDriverAccumUpdates(
                                            executionId: Long,
                                            @JsonDeserialize(contentConverter = classOf[LongLongTupleConverter])
                                            accumUpdates: Seq[(Long, Long)])
  extends SparkListenerEvent

/**
 * Jackson [[Converter]] for converting an (Int, Int) tuple into a (Long, Long) tuple.
 *
 * This is necessary due to limitations in how Jackson's scala module deserializes primitives;
 * see the "Deserializing Option[Int] and other primitive challenges" section in
 * https://github.com/FasterXML/jackson-module-scala/wiki/FAQ for a discussion of this issue and
 * SPARK-18462 for the specific problem that motivated this conversion.
 */
private class LongLongTupleConverter extends Converter[(Object, Object), (Long, Long)] {

  override def convert(in: (Object, Object)): (Long, Long) = {
    def toLong(a: Object): Long = a match {
      case i: java.lang.Integer => i.intValue()
      case l: java.lang.Long => l.longValue()
    }
    (toLong(in._1), toLong(in._2))
  }

  override def getInputType(typeFactory: TypeFactory): JavaType = {
    val objectType = typeFactory.uncheckedSimpleType(classOf[Object])
    typeFactory.constructSimpleType(classOf[(_, _)], classOf[(_, _)], Array(objectType, objectType))
  }

  override def getOutputType(typeFactory: TypeFactory): JavaType = {
    val longType = typeFactory.uncheckedSimpleType(classOf[Long])
    typeFactory.constructSimpleType(classOf[(_, _)], classOf[(_, _)], Array(longType, longType))
  }
}
