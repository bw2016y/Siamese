package org.apache.spark.daslab.sql.internal



import java.util.Locale

import org.apache.spark.daslab.sql.engine.catalog.CatalogStorageFormat

//todo const



case class HiveSerDe(
                      inputFormat: Option[String] = None,
                      outputFormat: Option[String] = None,
                      serde: Option[String] = None)

object HiveSerDe {
  val serdeMap = Map(
    "sequencefile" ->
      HiveSerDe(
        inputFormat = Option("org.apache.hadoop.mapred.SequenceFileInputFormat"),
        outputFormat = Option("org.apache.hadoop.mapred.SequenceFileOutputFormat"),
        serde = Option("org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe")),

    "rcfile" ->
      HiveSerDe(
        inputFormat = Option("org.apache.hadoop.hive.ql.io.RCFileInputFormat"),
        outputFormat = Option("org.apache.hadoop.hive.ql.io.RCFileOutputFormat"),
        serde = Option("org.apache.hadoop.hive.serde2.columnar.LazyBinaryColumnarSerDe")),

    "orc" ->
      HiveSerDe(
        inputFormat = Option("org.apache.hadoop.hive.ql.io.orc.OrcInputFormat"),
        outputFormat = Option("org.apache.hadoop.hive.ql.io.orc.OrcOutputFormat"),
        serde = Option("org.apache.hadoop.hive.ql.io.orc.OrcSerde")),

    "parquet" ->
      HiveSerDe(
        inputFormat = Option("org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat"),
        outputFormat = Option("org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat"),
        serde = Option("org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe")),

    "textfile" ->
      HiveSerDe(
        inputFormat = Option("org.apache.hadoop.mapred.TextInputFormat"),
        outputFormat = Option("org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat"),
        serde = Option("org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe")),

    "avro" ->
      HiveSerDe(
        inputFormat = Option("org.apache.hadoop.hive.ql.io.avro.AvroContainerInputFormat"),
        outputFormat = Option("org.apache.hadoop.hive.ql.io.avro.AvroContainerOutputFormat"),
        serde = Option("org.apache.hadoop.hive.serde2.avro.AvroSerDe")))

  /**
   * Get the Hive SerDe information from the data source abbreviation string or classname.
   *
   * @param source Currently the source abbreviation can be one of the following:
   *               SequenceFile, RCFile, ORC, PARQUET, and case insensitive.
   * @return HiveSerDe associated with the specified source
   */
  def sourceToSerDe(source: String): Option[HiveSerDe] = {
    val key = source.toLowerCase(Locale.ROOT) match {
      case s if s.startsWith("org.apache.spark.daslab.sql.parquet") => "parquet"
      case s if s.startsWith("org.apache.spark.daslab.sql.execution.datasources.parquet") => "parquet"
      case s if s.startsWith("org.apache.spark.daslab.sql.orc") => "orc"
      case s if s.startsWith("org.apache.spark.daslab.sql.hive.orc") => "orc"
      case s if s.startsWith("org.apache.spark.daslab.sql.execution.datasources.orc") => "orc"
      case s if s.equals("orcfile") => "orc"
      case s if s.equals("parquetfile") => "parquet"
      case s if s.equals("avrofile") => "avro"
      case s => s
    }

    serdeMap.get(key)
  }

  def getDefaultStorage(conf: SQLConf): CatalogStorageFormat = {
    val defaultStorageType = conf.getConfString("hive.default.fileformat", "textfile")
    val defaultHiveSerde = sourceToSerDe(defaultStorageType)
    CatalogStorageFormat.empty.copy(
      inputFormat = defaultHiveSerde.flatMap(_.inputFormat)
        .orElse(Some("org.apache.hadoop.mapred.TextInputFormat")),
      outputFormat = defaultHiveSerde.flatMap(_.outputFormat)
        .orElse(Some("org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat")),
      serde = defaultHiveSerde.flatMap(_.serde)
        .orElse(Some("org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe")))
  }
}
