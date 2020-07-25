package org.apache.spark.daslab.sql.types

import scala.collection.mutable

import org.apache.spark.SparkException
import org.apache.spark.internal.Logging
import org.apache.spark.util.Utils

/**
  * This object keeps the mappings between user classes and their User Defined Types (UDTs).
  * Previously we use the annotation `SQLUserDefinedType` to register UDTs for user classes.
  * However, by doing this, we add SparkSQL dependency on user classes. This object provides
  * alternative approach to register UDTs for user classes.
  */
private[spark]
object UDTRegistration extends Serializable with Logging {

  /** The mapping between the Class between UserDefinedType and user classes. */
  private lazy val udtMap: mutable.Map[String, String] = mutable.Map(
    ("org.apache.spark.ml.linalg.Vector", "org.apache.spark.ml.linalg.VectorUDT"),
    ("org.apache.spark.ml.linalg.DenseVector", "org.apache.spark.ml.linalg.VectorUDT"),
    ("org.apache.spark.ml.linalg.SparseVector", "org.apache.spark.ml.linalg.VectorUDT"),
    ("org.apache.spark.ml.linalg.Matrix", "org.apache.spark.ml.linalg.MatrixUDT"),
    ("org.apache.spark.ml.linalg.DenseMatrix", "org.apache.spark.ml.linalg.MatrixUDT"),
    ("org.apache.spark.ml.linalg.SparseMatrix", "org.apache.spark.ml.linalg.MatrixUDT"))

  /**
    * Queries if a given user class is already registered or not.
    * @param userClassName the name of user class
    * @return boolean value indicates if the given user class is registered or not
    */
  def exists(userClassName: String): Boolean = udtMap.contains(userClassName)

  /**
    * Registers an UserDefinedType to an user class. If the user class is already registered
    * with another UserDefinedType, warning log message will be shown.
    * @param userClass the name of user class
    * @param udtClass the name of UserDefinedType class for the given userClass
    */
  def register(userClass: String, udtClass: String): Unit = {
    if (udtMap.contains(userClass)) {
      logWarning(s"Cannot register UDT for ${userClass}, which is already registered.")
    } else {
      // When register UDT with class name, we can't check if the UDT class is an UserDefinedType,
      // or not. The check is deferred.
      udtMap += ((userClass, udtClass))
    }
  }

  /**
    * Returns the Class of UserDefinedType for the name of a given user class.
    * @param userClass class name of user class
    * @return Option value of the Class object of UserDefinedType
    */
  def getUDTFor(userClass: String): Option[Class[_]] = {
    udtMap.get(userClass).map { udtClassName =>
      if (Utils.classIsLoadable(udtClassName)) {
        val udtClass = Utils.classForName(udtClassName)
        if (classOf[UserDefinedType[_]].isAssignableFrom(udtClass)) {
          udtClass
        } else {
          throw new SparkException(
            s"${udtClass.getName} is not an UserDefinedType. Please make sure registering " +
              s"an UserDefinedType for ${userClass}")
        }
      } else {
        throw new SparkException(
          s"Can not load in UserDefinedType ${udtClassName} for user class ${userClass}.")
      }
    }
  }
}
