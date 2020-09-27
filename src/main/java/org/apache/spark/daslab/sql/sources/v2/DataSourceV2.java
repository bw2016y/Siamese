package org.apache.spark.daslab.sql.sources.v2;


//todo
import org.apache.spark.annotation.InterfaceStability;

/**
 * The base interface for data source v2. Implementations must have a public, 0-arg constructor.
 *
 * Note that this is an empty interface. Data source implementations should mix-in at least one of
 * the plug-in interfaces like {@link ReadSupport} and {@link WriteSupport}. Otherwise it's just
 * a dummy data source which is un-readable/writable.
 */
@InterfaceStability.Evolving
public interface DataSourceV2 {}
