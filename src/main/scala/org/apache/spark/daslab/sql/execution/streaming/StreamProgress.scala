package org.apache.spark.daslab.sql.execution.streaming



import scala.collection.{immutable, GenTraversableOnce}

/**
 * A helper class that looks like a Map[Source, Offset].
 */
class StreamProgress(
                      val baseMap: immutable.Map[BaseStreamingSource, Offset] =
                      new immutable.HashMap[BaseStreamingSource, Offset])
  extends scala.collection.immutable.Map[BaseStreamingSource, Offset] {

  def toOffsetSeq(source: Seq[BaseStreamingSource], metadata: OffsetSeqMetadata): OffsetSeq = {
    OffsetSeq(source.map(get), Some(metadata))
  }

  override def toString: String =
    baseMap.map { case (k, v) => s"$k: $v"}.mkString("{", ",", "}")

  override def +[B1 >: Offset](kv: (BaseStreamingSource, B1)): Map[BaseStreamingSource, B1] = {
    baseMap + kv
  }

  override def get(key: BaseStreamingSource): Option[Offset] = baseMap.get(key)

  override def iterator: Iterator[(BaseStreamingSource, Offset)] = baseMap.iterator

  override def -(key: BaseStreamingSource): Map[BaseStreamingSource, Offset] = baseMap - key

  def ++(updates: GenTraversableOnce[(BaseStreamingSource, Offset)]): StreamProgress = {
    new StreamProgress(baseMap ++ updates)
  }
}
