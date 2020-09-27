package org.apache.spark.daslab.sql.execution


import org.apache.spark.daslab.sql.engine.expressions._
import org.apache.spark.daslab.sql.types._

/**
 * A Scala extractor that projects an expression over a given schema. Data types,
 * field indexes and field counts of complex type extractors and attributes
 * are adjusted to fit the schema. All other expressions are left as-is. This
 * class is motivated by columnar nested schema pruning.
 */
private[execution] case class ProjectionOverSchema(schema: StructType) {
  private val fieldNames = schema.fieldNames.toSet

  def unapply(expr: Expression): Option[Expression] = getProjection(expr)

  private def getProjection(expr: Expression): Option[Expression] =
    expr match {
      case a: AttributeReference if fieldNames.contains(a.name) =>
        Some(a.copy(dataType = schema(a.name).dataType)(a.exprId, a.qualifier))
      case GetArrayItem(child, arrayItemOrdinal) =>
        getProjection(child).map { projection => GetArrayItem(projection, arrayItemOrdinal) }
      case a: GetArrayStructFields =>
        getProjection(a.child).map(p => (p, p.dataType)).map {
          case (projection, ArrayType(projSchema @ StructType(_), _)) =>
            GetArrayStructFields(projection,
              projSchema(a.field.name),
              projSchema.fieldIndex(a.field.name),
              projSchema.size,
              a.containsNull)
          case (_, projSchema) =>
            throw new IllegalStateException(
              s"unmatched child schema for GetArrayStructFields: ${projSchema.toString}"
            )
        }
      case GetMapValue(child, key) =>
        getProjection(child).map { projection => GetMapValue(projection, key) }
      case GetStructFieldObject(child, field: StructField) =>
        getProjection(child).map(p => (p, p.dataType)).map {
          case (projection, projSchema: StructType) =>
            GetStructField(projection, projSchema.fieldIndex(field.name))
          case (_, projSchema) =>
            throw new IllegalStateException(
              s"unmatched child schema for GetStructField: ${projSchema.toString}"
            )
        }
      case _ =>
        None
    }
}
