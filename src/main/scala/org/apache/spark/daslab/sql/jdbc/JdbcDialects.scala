package org.apache.spark.daslab.sql.jdbc

import java.sql.{Connection, Date, Timestamp}

import org.apache.commons.lang3.StringUtils

import org.apache.spark.daslab.sql.execution.datasources.jdbc.JDBCOptions
import org.apache.spark.daslab.sql.types._

//todo
import org.apache.spark.annotation.{DeveloperApi, InterfaceStability, Since}

/**
 * :: DeveloperApi ::
 * A database type definition coupled with the jdbc type needed to send null
 * values to the database.
 * @param databaseTypeDefinition The database type definition
 * @param jdbcNullType The jdbc type (as defined in java.sql.Types) used to
 *                     send a null value to the database.
 */
@DeveloperApi
@InterfaceStability.Evolving
case class JdbcType(databaseTypeDefinition : String, jdbcNullType : Int)

/**
 * :: DeveloperApi ::
 * Encapsulates everything (extensions, workarounds, quirks) to handle the
 * SQL dialect of a certain database or jdbc driver.
 * Lots of databases define types that aren't explicitly supported
 * by the JDBC spec.  Some JDBC drivers also report inaccurate
 * information---for instance, BIT(n{@literal >}1) being reported as a BIT type is quite
 * common, even though BIT in JDBC is meant for single-bit values. Also, there
 * does not appear to be a standard name for an unbounded string or binary
 * type; we use BLOB and CLOB by default but override with database-specific
 * alternatives when these are absent or do not behave correctly.
 *
 * Currently, the only thing done by the dialect is type mapping.
 * `getCatalystType` is used when reading from a JDBC table and `getJDBCType`
 * is used when writing to a JDBC table.  If `getCatalystType` returns `null`,
 * the default type handling is used for the given JDBC type.  Similarly,
 * if `getJDBCType` returns `(null, None)`, the default type handling is used
 * for the given Catalyst type.
 */
@DeveloperApi
@InterfaceStability.Evolving
abstract class JdbcDialect extends Serializable {
  /**
   * Check if this dialect instance can handle a certain jdbc url.
   * @param url the jdbc url.
   * @return True if the dialect can be applied on the given jdbc url.
   * @throws NullPointerException if the url is null.
   */
  def canHandle(url : String): Boolean

  /**
   * Get the custom datatype mapping for the given jdbc meta information.
   * @param sqlType The sql type (see java.sql.Types)
   * @param typeName The sql type name (e.g. "BIGINT UNSIGNED")
   * @param size The size of the type.
   * @param md Result metadata associated with this type.
   * @return The actual DataType (subclasses of [[org.apache.spark.daslab.sql.types.DataType]])
   *         or null if the default type mapping should be used.
   */
  def getCatalystType(
                       sqlType: Int, typeName: String, size: Int, md: MetadataBuilder): Option[DataType] = None

  /**
   * Retrieve the jdbc / sql type for a given datatype.
   * @param dt The datatype (e.g. [[org.apache.spark.daslab.sql.types.StringType]])
   * @return The new JdbcType if there is an override for this DataType
   */
  def getJDBCType(dt: DataType): Option[JdbcType] = None

  /**
   * Quotes the identifier. This is used to put quotes around the identifier in case the column
   * name is a reserved keyword, or in case it contains characters that require quotes (e.g. space).
   */
  def quoteIdentifier(colName: String): String = {
    s""""$colName""""
  }

  /**
   * Get the SQL query that should be used to find if the given table exists. Dialects can
   * override this method to return a query that works best in a particular database.
   * @param table  The name of the table.
   * @return The SQL query to use for checking the table.
   */
  def getTableExistsQuery(table: String): String = {
    s"SELECT * FROM $table WHERE 1=0"
  }

  /**
   * The SQL query that should be used to discover the schema of a table. It only needs to
   * ensure that the result set has the same schema as the table, such as by calling
   * "SELECT * ...". Dialects can override this method to return a query that works best in a
   * particular database.
   * @param table The name of the table.
   * @return The SQL query to use for discovering the schema.
   */
  @Since("2.1.0")
  def getSchemaQuery(table: String): String = {
    s"SELECT * FROM $table WHERE 1=0"
  }

  /**
   * The SQL query that should be used to truncate a table. Dialects can override this method to
   * return a query that is suitable for a particular database. For PostgreSQL, for instance,
   * a different query is used to prevent "TRUNCATE" affecting other tables.
   * @param table The table to truncate
   * @return The SQL query to use for truncating a table
   */
  @Since("2.3.0")
  def getTruncateQuery(table: String): String = {
    getTruncateQuery(table, isCascadingTruncateTable)
  }

  /**
   * The SQL query that should be used to truncate a table. Dialects can override this method to
   * return a query that is suitable for a particular database. For PostgreSQL, for instance,
   * a different query is used to prevent "TRUNCATE" affecting other tables.
   * @param table The table to truncate
   * @param cascade Whether or not to cascade the truncation
   * @return The SQL query to use for truncating a table
   */
  @Since("2.4.0")
  def getTruncateQuery(
                        table: String,
                        cascade: Option[Boolean] = isCascadingTruncateTable): String = {
    s"TRUNCATE TABLE $table"
  }

  /**
   * Override connection specific properties to run before a select is made.  This is in place to
   * allow dialects that need special treatment to optimize behavior.
   * @param connection The connection object
   * @param properties The connection properties.  This is passed through from the relation.
   */
  def beforeFetch(connection: Connection, properties: Map[String, String]): Unit = {
  }

  /**
   * Escape special characters in SQL string literals.
   * @param value The string to be escaped.
   * @return Escaped string.
   */
  @Since("2.3.0")
  protected[jdbc] def escapeSql(value: String): String =
    if (value == null) null else StringUtils.replace(value, "'", "''")

  /**
   * Converts value to SQL expression.
   * @param value The value to be converted.
   * @return Converted value.
   */
  @Since("2.3.0")
  def compileValue(value: Any): Any = value match {
    case stringValue: String => s"'${escapeSql(stringValue)}'"
    case timestampValue: Timestamp => "'" + timestampValue + "'"
    case dateValue: Date => "'" + dateValue + "'"
    case arrayValue: Array[Any] => arrayValue.map(compileValue).mkString(", ")
    case _ => value
  }

  /**
   * Return Some[true] iff `TRUNCATE TABLE` causes cascading default.
   * Some[true] : TRUNCATE TABLE causes cascading.
   * Some[false] : TRUNCATE TABLE does not cause cascading.
   * None: The behavior of TRUNCATE TABLE is unknown (default).
   */
  def isCascadingTruncateTable(): Option[Boolean] = None
}

/**
 * :: DeveloperApi ::
 * Registry of dialects that apply to every new jdbc `org.apache.spark.daslab.sql.DataFrame`.
 *
 * If multiple matching dialects are registered then all matching ones will be
 * tried in reverse order. A user-added dialect will thus be applied first,
 * overwriting the defaults.
 *
 * @note All new dialects are applied to new jdbc DataFrames only. Make
 * sure to register your dialects first.
 */
@DeveloperApi
@InterfaceStability.Evolving
object JdbcDialects {

  /**
   * Register a dialect for use on all new matching jdbc `org.apache.spark.daslab.sql.DataFrame`.
   * Reading an existing dialect will cause a move-to-front.
   *
   * @param dialect The new dialect.
   */
  def registerDialect(dialect: JdbcDialect) : Unit = {
    dialects = dialect :: dialects.filterNot(_ == dialect)
  }

  /**
   * Unregister a dialect. Does nothing if the dialect is not registered.
   *
   * @param dialect The jdbc dialect.
   */
  def unregisterDialect(dialect : JdbcDialect) : Unit = {
    dialects = dialects.filterNot(_ == dialect)
  }

  private[this] var dialects = List[JdbcDialect]()

  registerDialect(MySQLDialect)
  //registerDialect(PostgresDialect)
  //registerDialect(DB2Dialect)
  //registerDialect(MsSqlServerDialect)
  //registerDialect(DerbyDialect)
  //registerDialect(OracleDialect)
  //registerDialect(TeradataDialect)

  /**
   * Fetch the JdbcDialect class corresponding to a given database url.
   */
  def get(url: String): JdbcDialect = {
    val matchingDialects = dialects.filter(_.canHandle(url))
    matchingDialects.length match {
      case 0 => NoopDialect
      case 1 => matchingDialects.head
      case _ => new AggregatedDialect(matchingDialects)
    }
  }
}

/**
 * NOOP dialect object, always returning the neutral element.
 */
private object NoopDialect extends JdbcDialect {
  override def canHandle(url : String): Boolean = true
}
