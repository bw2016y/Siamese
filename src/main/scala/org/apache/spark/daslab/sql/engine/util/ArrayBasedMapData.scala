package org.apache.spark.daslab.sql.engine.util

import java.util.{Map => JavaMap}

class ArrayBasedMapData(val keyArray: ArrayData, val valueArray: ArrayData) extends MapData {
  require(keyArray.numElements() == valueArray.numElements())

  override def numElements(): Int = keyArray.numElements()

  override def copy(): MapData = new ArrayBasedMapData(keyArray.copy(), valueArray.copy())

  override def toString: String = {
    s"keys: $keyArray, values: $valueArray"
  }
}

object ArrayBasedMapData {
  /**
   * Creates a [[ArrayBasedMapData]] by applying the given converters over
   * each (key -> value) pair of the input [[java.util.Map]]
   *
   * @param javaMap Input map
   * @param keyConverter This function is applied over all the keys of the input map to
   *                     obtain the output map's keys
   * @param valueConverter This function is applied over all the values of the input map to
   *                       obtain the output map's values
   */
  def apply(
             javaMap: JavaMap[_, _],
             keyConverter: (Any) => Any,
             valueConverter: (Any) => Any): ArrayBasedMapData = {
    import scala.language.existentials

    val keys: Array[Any] = new Array[Any](javaMap.size())
    val values: Array[Any] = new Array[Any](javaMap.size())

    var i: Int = 0
    val iterator = javaMap.entrySet().iterator()
    while (iterator.hasNext) {
      val entry = iterator.next()
      keys(i) = keyConverter(entry.getKey)
      values(i) = valueConverter(entry.getValue)
      i += 1
    }
    ArrayBasedMapData(keys, values)
  }

  /**
   * Creates a [[ArrayBasedMapData]] by applying the given converters over
   * each (key -> value) pair of the input map
   *
   * @param map Input map
   * @param keyConverter This function is applied over all the keys of the input map to
   *                     obtain the output map's keys
   * @param valueConverter This function is applied over all the values of the input map to
   *                       obtain the output map's values
   */
  def apply(
             map: scala.collection.Map[_, _],
             keyConverter: (Any) => Any = identity,
             valueConverter: (Any) => Any = identity): ArrayBasedMapData = {
    ArrayBasedMapData(map.iterator, map.size, keyConverter, valueConverter)
  }

  /**
   * Creates a [[ArrayBasedMapData]] by applying the given converters over
   * each (key -> value) pair from the given iterator
   *
   * @param iterator Input iterator
   * @param size Number of elements
   * @param keyConverter This function is applied over all the keys extracted from the
   *                     given iterator to obtain the output map's keys
   * @param valueConverter This function is applied over all the values extracted from the
   *                       given iterator to obtain the output map's values
   */
  def apply(
             iterator: Iterator[(_, _)],
             size: Int,
             keyConverter: (Any) => Any,
             valueConverter: (Any) => Any): ArrayBasedMapData = {

    val keys: Array[Any] = new Array[Any](size)
    val values: Array[Any] = new Array[Any](size)

    var i = 0
    for ((key, value) <- iterator) {
      keys(i) = keyConverter(key)
      values(i) = valueConverter(value)
      i += 1
    }
    ArrayBasedMapData(keys, values)
  }

  def apply(keys: Array[_], values: Array[_]): ArrayBasedMapData = {
    new ArrayBasedMapData(new GenericArrayData(keys), new GenericArrayData(values))
  }

  def toScalaMap(map: ArrayBasedMapData): Map[Any, Any] = {
    val keys = map.keyArray.asInstanceOf[GenericArrayData].array
    val values = map.valueArray.asInstanceOf[GenericArrayData].array
    keys.zip(values).toMap
  }

  def toScalaMap(keys: Array[Any], values: Array[Any]): Map[Any, Any] = {
    keys.zip(values).toMap
  }

  def toScalaMap(keys: Seq[Any], values: Seq[Any]): Map[Any, Any] = {
    keys.zip(values).toMap
  }

  def toJavaMap(keys: Array[Any], values: Array[Any]): java.util.Map[Any, Any] = {
    import scala.collection.JavaConverters._
    keys.zip(values).toMap.asJava
  }
}
