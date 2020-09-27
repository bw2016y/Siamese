package org.apache.spark.daslab.sql

/**
  * Contains a type system for attributes produced by relations, including complex types like
  * structs, arrays and maps.
  */
package object types {
  /**
    * Metadata key used to store the raw hive type string in the metadata of StructField. This
    * is relevant for datatypes that do not have a direct Spark SQL counterpart, such as CHAR and
    * VARCHAR. We need to preserve the original type in order to invoke the correct object
    * inspector in Hive.
    */
  val HIVE_TYPE_STRING = "HIVE_TYPE_STRING"
}
