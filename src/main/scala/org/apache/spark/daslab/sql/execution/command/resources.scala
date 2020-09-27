package org.apache.spark.daslab.sql.execution.command



import java.io.File
import java.net.URI

import org.apache.hadoop.fs.Path


import org.apache.spark.daslab.sql.{Row, SparkSession}
import org.apache.spark.daslab.sql.engine.expressions.{Attribute, AttributeReference}
import org.apache.spark.daslab.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 * Adds a jar to the current session so it can be used (for UDFs or serdes).
 */
case class AddJarCommand(path: String) extends RunnableCommand {
  override val output: Seq[Attribute] = {
    val schema = StructType(
      StructField("result", IntegerType, nullable = false) :: Nil)
    schema.toAttributes
  }

  override def run(sparkSession: SparkSession): Seq[Row] = {
    sparkSession.sessionState.resourceLoader.addJar(path)
    Seq(Row(0))
  }
}

/**
 * Adds a file to the current session so it can be used.
 */
case class AddFileCommand(path: String) extends RunnableCommand {
  override def run(sparkSession: SparkSession): Seq[Row] = {
    sparkSession.sparkContext.addFile(path)
    Seq.empty[Row]
  }
}

/**
 * Returns a list of file paths that are added to resources.
 * If file paths are provided, return the ones that are added to resources.
 */
case class ListFilesCommand(files: Seq[String] = Seq.empty[String]) extends RunnableCommand {
  override val output: Seq[Attribute] = {
    AttributeReference("Results", StringType, nullable = false)() :: Nil
  }
  override def run(sparkSession: SparkSession): Seq[Row] = {
    val fileList = sparkSession.sparkContext.listFiles()
    if (files.size > 0) {
      files.map { f =>
        val uri = new URI(f)
        val schemeCorrectedPath = uri.getScheme match {
          case null | "local" => new File(f).getCanonicalFile.toURI.toString
          case _ => f
        }
        new Path(schemeCorrectedPath).toUri.toString
      }.collect {
        case f if fileList.contains(f) => f
      }.map(Row(_))
    } else {
      fileList.map(Row(_))
    }
  }
}

/**
 * Returns a list of jar files that are added to resources.
 * If jar files are provided, return the ones that are added to resources.
 */
case class ListJarsCommand(jars: Seq[String] = Seq.empty[String]) extends RunnableCommand {
  override val output: Seq[Attribute] = {
    AttributeReference("Results", StringType, nullable = false)() :: Nil
  }
  override def run(sparkSession: SparkSession): Seq[Row] = {
    val jarList = sparkSession.sparkContext.listJars()
    if (jars.nonEmpty) {
      for {
        jarName <- jars.map(f => new Path(f).getName)
        jarPath <- jarList if jarPath.contains(jarName)
      } yield Row(jarPath)
    } else {
      jarList.map(Row(_))
    }
  }
}
