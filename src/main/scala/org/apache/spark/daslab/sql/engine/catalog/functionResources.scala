package org.apache.spark.daslab.sql.engine.catalog



import java.util.Locale

import org.apache.spark.daslab.sql.AnalysisException


/** A trait that represents the type of a resourced needed by a function. */
abstract class FunctionResourceType(val resourceType: String)

object JarResource extends FunctionResourceType("jar")

object FileResource extends FunctionResourceType("file")

// We do not allow users to specify an archive because it is YARN specific.
// When loading resources, we will throw an exception and ask users to
// use --archive with spark submit.
object ArchiveResource extends FunctionResourceType("archive")

object FunctionResourceType {
  def fromString(resourceType: String): FunctionResourceType = {
    resourceType.toLowerCase(Locale.ROOT) match {
      case "jar" => JarResource
      case "file" => FileResource
      case "archive" => ArchiveResource
      case other =>
        throw new AnalysisException(s"Resource Type '$resourceType' is not supported.")
    }
  }
}

case class FunctionResource(resourceType: FunctionResourceType, uri: String)

/**
 * A simple trait representing a class that can be used to load resources used by
 * a function. Because only a SQLContext can load resources, we create this trait
 * to avoid of explicitly passing SQLContext around.
 */
trait FunctionResourceLoader {
  def loadResource(resource: FunctionResource): Unit
}

object DummyFunctionResourceLoader extends FunctionResourceLoader {
  override def loadResource(resource: FunctionResource): Unit = {
    throw new UnsupportedOperationException
  }
}
