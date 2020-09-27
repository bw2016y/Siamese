package org.apache.spark.daslab.sql.engine.analysis


import org.apache.spark.daslab.sql.AnalysisException
import org.apache.spark.daslab.sql.engine.catalog.CatalogTypes.TablePartitionSpec


/**
 * Thrown by a catalog when an item cannot be found. The analyzer will rethrow the exception
 * as an [[org.apache.spark.daslab.sql.AnalysisException]] with the correct position information.
 */
class NoSuchDatabaseException(val db: String) extends AnalysisException(s"Database '$db' not found")

class NoSuchTableException(db: String, table: String)
  extends AnalysisException(s"Table or view '$table' not found in database '$db'")

class NoSuchPartitionException(
                                db: String,
                                table: String,
                                spec: TablePartitionSpec)
  extends AnalysisException(
    s"Partition not found in table '$table' database '$db':\n" + spec.mkString("\n"))

class NoSuchPermanentFunctionException(db: String, func: String)
  extends AnalysisException(s"Function '$func' not found in database '$db'")

class NoSuchFunctionException(db: String, func: String)
  extends AnalysisException(
    s"Undefined function: '$func'. This function is neither a registered temporary function nor " +
      s"a permanent function registered in the database '$db'.")

class NoSuchPartitionsException(db: String, table: String, specs: Seq[TablePartitionSpec])
  extends AnalysisException(
    s"The following partitions not found in table '$table' database '$db':\n"
      + specs.mkString("\n===\n"))

class NoSuchTempFunctionException(func: String)
  extends AnalysisException(s"Temporary function '$func' not found")
