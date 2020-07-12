package org.daslab.sqlengine.types

import org.apache.spark.annotation.InterfaceStability

/**
 * The data type representing calendar time intervals. The calendar time interval is stored
 * internally in two components: number of months the number of microseconds.
 *
 * Please use the singleton `DataTypes.CalendarIntervalType`.
 *
 * @note Calendar intervals are not comparable.
 * @since 1.5.0
 */
@InterfaceStability.Stable
class CalendarIntervalType private() extends DataType {

  override def defaultSize: Int = 16

  private[spark] override def asNullable: CalendarIntervalType = this
}

/**
 * @since 1.5.0
 */
@InterfaceStability.Stable
case object CalendarIntervalType extends CalendarIntervalType
