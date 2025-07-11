package org.apache.spark.daslab.sql.execution.streaming


import scala.util.Try

import org.apache.spark.daslab.sql.engine.util.CaseInsensitiveMap

//todo
import org.apache.spark.util.Utils
import org.apache.spark.internal.Logging


/**
 * User specified options for file streams.
 */
class FileStreamOptions(parameters: CaseInsensitiveMap[String]) extends Logging {

  def this(parameters: Map[String, String]) = this(CaseInsensitiveMap(parameters))

  val maxFilesPerTrigger: Option[Int] = parameters.get("maxFilesPerTrigger").map { str =>
    Try(str.toInt).toOption.filter(_ > 0).getOrElse {
      throw new IllegalArgumentException(
        s"Invalid value '$str' for option 'maxFilesPerTrigger', must be a positive integer")
    }
  }

  /**
   * Maximum age of a file that can be found in this directory, before it is ignored. For the
   * first batch all files will be considered valid. If `latestFirst` is set to `true` and
   * `maxFilesPerTrigger` is set, then this parameter will be ignored, because old files that are
   * valid, and should be processed, may be ignored. Please refer to SPARK-19813 for details.
   *
   * The max age is specified with respect to the timestamp of the latest file, and not the
   * timestamp of the current system. That this means if the last file has timestamp 1000, and the
   * current system time is 2000, and max age is 200, the system will purge files older than
   * 800 (rather than 1800) from the internal state.
   *
   * Default to a week.
   */
  val maxFileAgeMs: Long =
    Utils.timeStringAsMs(parameters.getOrElse("maxFileAge", "7d"))

  /** Options as specified by the user, in a case-insensitive map, without "path" set. */
  val optionMapWithoutPath: Map[String, String] =
    parameters.filterKeys(_ != "path")

  /**
   * Whether to scan latest files first. If it's true, when the source finds unprocessed files in a
   * trigger, it will first process the latest files.
   */
  val latestFirst: Boolean = withBooleanParameter("latestFirst", false)

  /**
   * Whether to check new files based on only the filename instead of on the full path.
   *
   * With this set to `true`, the following files would be considered as the same file, because
   * their filenames, "dataset.txt", are the same:
   * - "file:///dataset.txt"
   * - "s3://a/dataset.txt"
   * - "s3n://a/b/dataset.txt"
   * - "s3a://a/b/c/dataset.txt"
   */
  val fileNameOnly: Boolean = withBooleanParameter("fileNameOnly", false)

  private def withBooleanParameter(name: String, default: Boolean) = {
    parameters.get(name).map { str =>
      try {
        str.toBoolean
      } catch {
        case _: IllegalArgumentException =>
          throw new IllegalArgumentException(
            s"Invalid value '$str' for option '$name', must be 'true' or 'false'")
      }
    }.getOrElse(default)
  }
}
