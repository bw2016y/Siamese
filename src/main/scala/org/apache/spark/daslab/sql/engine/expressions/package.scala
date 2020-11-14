package org.apache.spark.daslab.sql.engine


import java.util.Locale

import com.google.common.collect.Maps


import org.apache.spark.daslab.sql.AnalysisException
import org.apache.spark.daslab.sql.engine.analysis.{Resolver, UnresolvedAttribute}
import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.types.{StructField, StructType}


/**
 * A set of classes that can be used to represent trees of relational expressions.  A key goal of
 * the expression library is to hide the details of naming and scoping from developers who want to
 * manipulate trees of relational operators. As such, the library defines a special type of
 * expression, a [[NamedExpression]] in addition to the standard collection of expressions.
 *
 * ==Standard Expressions==
 * A library of standard expressions (e.g., [[Add]], [[EqualTo]]), aggregates (e.g., SUM, COUNT),
 * and other computations (e.g. UDFs). Each expression type is capable of determining its output
 * schema as a function of its children's output schema.
 *
 * ==Named Expressions==
 * Some expression are named and thus can be referenced by later operators in the dataflow graph.
 * The two types of named expressions are [[AttributeReference]]s and [[Alias]]es.
 * [[AttributeReference]]s refer to attributes of the input tuple for a given operator and form
 * the leaves of some expression trees.  Aliases assign a name to intermediate computations.
 * For example, in the SQL statement `SELECT a+b AS c FROM ...`, the expressions `a` and `b` would
 * be represented by `AttributeReferences` and `c` would be represented by an `Alias`.
 *
 * During [[analysis]], all named expressions are assigned a globally unique expression id, which
 * can be used for equality comparisons.  While the original names are kept around for debugging
 * purposes, they should never be used to check if two attributes refer to the same value, as
 * plan transformations can result in the introduction of naming ambiguity. For example, consider
 * a plan that contains subqueries, both of which are reading from the same table.  If an
 * optimization removes the subqueries, scoping information would be destroyed, eliminating the
 * ability to reason about which subquery produced a given attribute.
 *
 * ==Evaluation==
 * The result of expressions can be evaluated using the `Expression.apply(Row)` method.
 */
package object expressions  {

  /**
   * Used as input into expressions whose output does not depend on any input value.
   */
  val EmptyRow: InternalRow = null

  /*abstract class Append extends ()*/
  /**
   * Converts a [[InternalRow]] to another Row given a sequence of expression that define each
   * column of the new row. If the schema of the input row is specified, then the given expression
   * will be bound to that schema.
    *
    *  根据定义了新行每个列信息的表达式来将[[InternalRow]]转换到另一个Row
    *  如果input row的schema信息被指定了，那么给定的表达式就会被绑定到对应的schema
    *  TODO  catalog
   */
  abstract class Projection extends (InternalRow => InternalRow) {

    /**
     * Initializes internal states given the current partition index.
     * This is used by nondeterministic expressions to set initial states.
     * The default implementation does nothing.
      *
      *  用当前给定的partition index来初始化内部状态.
      *  一般是nondeterministic表达式用来设置一些初始状态
      *  默认的实现什么也没做
     */
    def initialize(partitionIndex: Int): Unit = {}
  }

  /**
    * 一个identity投影，可以直接返回input row
   */
  object IdentityProjection extends Projection {
    override def apply(row: InternalRow): InternalRow = row
  }

  /**
   * Converts a [[InternalRow]] to another Row given a sequence of expression that define each
   * column of the new row. If the schema of the input row is specified, then the given expression
   * will be bound to that schema.
   *
   * In contrast to a normal projection, a MutableProjection reuses the same underlying row object
   * each time an input row is added.  This significantly reduces the cost of calculating the
   * projection, but means that it is not safe to hold on to a reference to a [[InternalRow]] after
   * `next()` has been called on the [[Iterator]] that produced it. Instead, the user must call
   * `InternalRow.copy()` and hold on to the returned [[InternalRow]] before calling `next()`.
    *
   */
  abstract class MutableProjection extends Projection {
    def currentValue: InternalRow

    /** Uses the given row to store the output of the projection. */
    def target(row: InternalRow): MutableProjection
  }


  /**
    *  用于处理Seq[Attribute]的Helper functions
   */
  implicit class AttributeSeq(val attrs: Seq[Attribute]) extends Serializable {
    /**
      *  根据Seq[Attribute]来构造一个StructType类型的schema
      * @return
      */
    def toStructType: StructType = {
      StructType(attrs.map(a => StructField(a.name, a.dataType, a.nullable, a.metadata)))
    }

    /**
      *    构造函数中的attrs可能是链表，这可能会在使用下标访问attributes时导致一个O（n^2）的时间复杂度
      *    因此为了避免这个性能开销，将其转换为array
      */
    @transient private lazy val attrsArray = attrs.toArray

    //一个HashMap存放ExprId -> Ordinal
    @transient private lazy val exprIdToOrdinal = {
      val arr = attrsArray
      val map = Maps.newHashMapWithExpectedSize[ExprId, Int](arr.length)
      // Iterate over the array in reverse order so that the final map value is the first attribute
      // with a given expression id.
      var index = arr.length - 1
      while (index >= 0) {
        map.put(arr(index).exprId, index)
        index -= 1
      }
      map
    }

    /**
      *  给定一个下标返回对应的attribute
     */
    def apply(ordinal: Int): Attribute = attrsArray(ordinal)

    /**
     * Returns the index of first attribute with a matching expression id, or -1 if no match exists.
      *
     */
    def indexOf(exprId: ExprId): Int = {
      Option(exprIdToOrdinal.get(exprId)).getOrElse(-1)
    }

    private def unique[T](m: Map[T, Seq[Attribute]]): Map[T, Seq[Attribute]] = {
      m.mapValues(_.distinct).map(identity)
    }

    /** Map to use for direct case insensitive attribute lookups. */
    @transient private lazy val direct: Map[String, Seq[Attribute]] = {
      unique(attrs.groupBy(_.name.toLowerCase(Locale.ROOT)))
    }

    /** Map to use for qualified case insensitive attribute lookups with 2 part key */
    @transient private lazy val qualified: Map[(String, String), Seq[Attribute]] = {
      // key is 2 part: table/alias and name
      val grouped = attrs.filter(_.qualifier.nonEmpty).groupBy {
        a => (a.qualifier.last.toLowerCase(Locale.ROOT), a.name.toLowerCase(Locale.ROOT))
      }
      unique(grouped)
    }

    /** Map to use for qualified case insensitive attribute lookups with 3 part key */
    @transient private val qualified3Part: Map[(String, String, String), Seq[Attribute]] = {
      // key is 3 part: database name, table name and name
      val grouped = attrs.filter(_.qualifier.length == 2).groupBy { a =>
        (a.qualifier.head.toLowerCase(Locale.ROOT),
          a.qualifier.last.toLowerCase(Locale.ROOT),
          a.name.toLowerCase(Locale.ROOT))
      }
      unique(grouped)
    }

    /** Perform attribute resolution given a name and a resolver. */
    def resolve(nameParts: Seq[String], resolver: Resolver): Option[NamedExpression] = {
      // Collect matching attributes given a name and a lookup.
      def collectMatches(name: String, candidates: Option[Seq[Attribute]]): Seq[Attribute] = {
        candidates.toSeq.flatMap(_.collect {
          case a if resolver(a.name, name) => a.withName(name)
        })
      }

      // Find matches for the given name assuming that the 1st two parts are qualifier
      // (i.e. database name and table name) and the 3rd part is the actual column name.
      //
      // For example, consider an example where "db1" is the database name, "a" is the table name
      // and "b" is the column name and "c" is the struct field name.
      // If the name parts is db1.a.b.c, then Attribute will match
      // Attribute(b, qualifier("db1,"a")) and List("c") will be the second element
      var matches: (Seq[Attribute], Seq[String]) = nameParts match {
        case dbPart +: tblPart +: name +: nestedFields =>
          val key = (dbPart.toLowerCase(Locale.ROOT),
            tblPart.toLowerCase(Locale.ROOT), name.toLowerCase(Locale.ROOT))
          val attributes = collectMatches(name, qualified3Part.get(key)).filter {
            a => (resolver(dbPart, a.qualifier.head) && resolver(tblPart, a.qualifier.last))
          }
          (attributes, nestedFields)
        case all =>
          (Seq.empty, Seq.empty)
      }

      // If there are no matches, then find matches for the given name assuming that
      // the 1st part is a qualifier (i.e. table name, alias, or subquery alias) and the
      // 2nd part is the actual name. This returns a tuple of
      // matched attributes and a list of parts that are to be resolved.
      //
      // For example, consider an example where "a" is the table name, "b" is the column name,
      // and "c" is the struct field name, i.e. "a.b.c". In this case, Attribute will be "a.b",
      // and the second element will be List("c").
      if (matches._1.isEmpty) {
        matches = nameParts match {
          case qualifier +: name +: nestedFields =>
            val key = (qualifier.toLowerCase(Locale.ROOT), name.toLowerCase(Locale.ROOT))
            val attributes = collectMatches(name, qualified.get(key)).filter { a =>
              resolver(qualifier, a.qualifier.last)
            }
            (attributes, nestedFields)
          case all =>
            (Seq.empty[Attribute], Seq.empty[String])
        }
      }

      // If none of attributes match database.table.column pattern or
      // `table.column` pattern, we try to resolve it as a column.
      val (candidates, nestedFields) = matches match {
        case (Seq(), _) =>
          val name = nameParts.head
          val attributes = collectMatches(name, direct.get(name.toLowerCase(Locale.ROOT)))
          (attributes, nameParts.tail)
        case _ => matches
      }

      def name = UnresolvedAttribute(nameParts).name
      candidates match {
        case Seq(a) if nestedFields.nonEmpty =>
          // One match, but we also need to extract the requested nested field.
          // The foldLeft adds ExtractValues for every remaining parts of the identifier,
          // and aliased it with the last part of the name.
          // For example, consider "a.b.c", where "a" is resolved to an existing attribute.
          // Then this will add ExtractValue("c", ExtractValue("b", a)), and alias the final
          // expression as "c".
          val fieldExprs = nestedFields.foldLeft(a: Expression) { (e, name) =>
            ExtractValue(e, Literal(name), resolver)
          }
          Some(Alias(fieldExprs, nestedFields.last)())

        case Seq(a) =>
          // One match, no nested fields, use it.
          Some(a)

        case Seq() =>
          // No matches.
          None

        case ambiguousReferences =>
          // More than one match.
          val referenceNames = ambiguousReferences.map(_.qualifiedName).mkString(", ")
          throw new AnalysisException(s"Reference '$name' is ambiguous, could be: $referenceNames.")
      }
    }
  }

  /**
   * When an expression inherits this, meaning the expression is null intolerant (i.e. any null
   * input will result in null output). We will use this information during constructing IsNotNull
   * constraints.
   */
  trait NullIntolerant extends Expression
}
