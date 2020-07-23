package org.apache.spark.daslab.sql.engine.util


import org.apache.spark.daslab.sql.types.DataType

/**
  * This is an internal data representation for map type in Spark SQL. This should not implement
  * `equals` and `hashCode` because the type cannot be used as join keys, grouping keys, or
  * in equality tests. See SPARK-9415 and PR#13847 for the discussions.
  */
abstract class MapData extends Serializable {

  def numElements(): Int

  def keyArray(): ArrayData

  def valueArray(): ArrayData

  def copy(): MapData

  def foreach(keyType: DataType, valueType: DataType, f: (Any, Any) => Unit): Unit = {
    val length = numElements()
    val keys = keyArray()
    val values = valueArray()
    var i = 0
    while (i < length) {
      f(keys.get(i, keyType), values.get(i, valueType))
      i += 1
    }
  }
}
