package org.apache.spark.daslab.sql.catalog



import javax.annotation.Nullable

import org.apache.spark.daslab.sql.engine.DefinedByConstructorParams

//todo
import org.apache.spark.annotation.InterfaceStability

// Note: all classes here are expected to be wrapped in Datasets and so must extend
// DefinedByConstructorParams for the catalog to be able to create encoders for them.

/**
 * A database in Spark, as returned by the `listDatabases` method defined in [[Catalog]].
 *
 * @param name name of the database.
 * @param description description of the database.
 * @param locationUri path (in the form of a uri) to data files.
 * @since 2.0.0
 */
@InterfaceStability.Stable
class Database(
                val name: String,
                @Nullable val description: String,
                val locationUri: String)
  extends DefinedByConstructorParams {

  override def toString: String = {
    "Database[" +
      s"name='$name', " +
      Option(description).map { d => s"description='$d', " }.getOrElse("") +
      s"path='$locationUri']"
  }

}


/**
 * A table in Spark, as returned by the `listTables` method in [[Catalog]].
 *
 * @param name name of the table.
 * @param database name of the database the table belongs to.
 * @param description description of the table.
 * @param tableType type of the table (e.g. view, table).
 * @param isTemporary whether the table is a temporary table.
 * @since 2.0.0
 */
@InterfaceStability.Stable
class Table(
             val name: String,
             @Nullable val database: String,
             @Nullable val description: String,
             val tableType: String,
             val isTemporary: Boolean)
  extends DefinedByConstructorParams {

  override def toString: String = {
    "Table[" +
      s"name='$name', " +
      Option(database).map { d => s"database='$d', " }.getOrElse("") +
      Option(description).map { d => s"description='$d', " }.getOrElse("") +
      s"tableType='$tableType', " +
      s"isTemporary='$isTemporary']"
  }

}


/**
 * A column in Spark, as returned by `listColumns` method in [[Catalog]].
 *
 * @param name name of the column.
 * @param description description of the column.
 * @param dataType data type of the column.
 * @param nullable whether the column is nullable.
 * @param isPartition whether the column is a partition column.
 * @param isBucket whether the column is a bucket column.
 * @since 2.0.0
 */
@InterfaceStability.Stable
class Column(
              val name: String,
              @Nullable val description: String,
              val dataType: String,
              val nullable: Boolean,
              val isPartition: Boolean,
              val isBucket: Boolean)
  extends DefinedByConstructorParams {

  override def toString: String = {
    "Column[" +
      s"name='$name', " +
      Option(description).map { d => s"description='$d', " }.getOrElse("") +
      s"dataType='$dataType', " +
      s"nullable='$nullable', " +
      s"isPartition='$isPartition', " +
      s"isBucket='$isBucket']"
  }

}


/**
 * A user-defined function in Spark, as returned by `listFunctions` method in [[Catalog]].
 *
 * @param name name of the function.
 * @param database name of the database the function belongs to.
 * @param description description of the function; description can be null.
 * @param className the fully qualified class name of the function.
 * @param isTemporary whether the function is a temporary function or not.
 * @since 2.0.0
 */
@InterfaceStability.Stable
class Function(
                val name: String,
                @Nullable val database: String,
                @Nullable val description: String,
                val className: String,
                val isTemporary: Boolean)
  extends DefinedByConstructorParams {

  override def toString: String = {
    "Function[" +
      s"name='$name', " +
      Option(database).map { d => s"database='$d', " }.getOrElse("") +
      Option(description).map { d => s"description='$d', " }.getOrElse("") +
      s"className='$className', " +
      s"isTemporary='$isTemporary']"
  }

}
