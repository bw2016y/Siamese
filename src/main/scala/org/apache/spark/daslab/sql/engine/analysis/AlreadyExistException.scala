package org.apache.spark.daslab.sql.engine.analysis



import org.apache.spark.daslab.sql.AnalysisException
import org.apache.spark.daslab.sql.engine.catalog.CatalogTypes.TablePartitionSpec

/**
 * Thrown by a catalog when an item already exists. The analyzer will rethrow the exception
 * as an [[org.apache.spark.daslab.sql.AnalysisException]] with the correct position information.
 */
class DatabaseAlreadyExistsException(db: String)
  extends AnalysisException(s"Database '$db' already exists")

class TableAlreadyExistsException(db: String, table: String)
  extends AnalysisException(s"Table or view '$table' already exists in database '$db'")

class TempTableAlreadyExistsException(table: String)
  extends AnalysisException(s"Temporary view '$table' already exists")

class PartitionAlreadyExistsException(db: String, table: String, spec: TablePartitionSpec)
  extends AnalysisException(
    s"Partition already exists in table '$table' database '$db':\n" + spec.mkString("\n"))

class PartitionsAlreadyExistException(db: String, table: String, specs: Seq[TablePartitionSpec])
  extends AnalysisException(
    s"The following partitions already exists in table '$table' database '$db':\n"
      + specs.mkString("\n===\n"))

class FunctionAlreadyExistsException(db: String, func: String)
  extends AnalysisException(s"Function '$func' already exists in database '$db'")

