package org.apache.spark.daslab.sql.execution.python



import java.io._
import java.net._
import java.util.concurrent.atomic.AtomicBoolean
//todo
import org.apache.spark._
import org.apache.spark.api.python._

/**
 * A helper class to run Python UDFs in Spark.
 */
class PythonUDFRunner(
                       funcs: Seq[ChainedPythonFunctions],
                       evalType: Int,
                       argOffsets: Array[Array[Int]])
  extends BasePythonRunner[Array[Byte], Array[Byte]](
    funcs, evalType, argOffsets) {

  protected override def newWriterThread(
                                          env: SparkEnv,
                                          worker: Socket,
                                          inputIterator: Iterator[Array[Byte]],
                                          partitionIndex: Int,
                                          context: TaskContext): WriterThread = {
    new WriterThread(env, worker, inputIterator, partitionIndex, context) {

      protected override def writeCommand(dataOut: DataOutputStream): Unit = {
        PythonUDFRunner.writeUDFs(dataOut, funcs, argOffsets)
      }

      protected override def writeIteratorToStream(dataOut: DataOutputStream): Unit = {
        PythonRDD.writeIteratorToStream(inputIterator, dataOut)
        dataOut.writeInt(SpecialLengths.END_OF_DATA_SECTION)
      }
    }
  }

  protected override def newReaderIterator(
                                            stream: DataInputStream,
                                            writerThread: WriterThread,
                                            startTime: Long,
                                            env: SparkEnv,
                                            worker: Socket,
                                            releasedOrClosed: AtomicBoolean,
                                            context: TaskContext): Iterator[Array[Byte]] = {
    new ReaderIterator(stream, writerThread, startTime, env, worker, releasedOrClosed, context) {

      protected override def read(): Array[Byte] = {
        if (writerThread.exception.isDefined) {
          throw writerThread.exception.get
        }
        try {
          stream.readInt() match {
            case length if length > 0 =>
              val obj = new Array[Byte](length)
              stream.readFully(obj)
              obj
            case 0 => Array.empty[Byte]
            case SpecialLengths.TIMING_DATA =>
              handleTimingData()
              read()
            case SpecialLengths.PYTHON_EXCEPTION_THROWN =>
              throw handlePythonException()
            case SpecialLengths.END_OF_DATA_SECTION =>
              handleEndOfDataSection()
              null
          }
        } catch handleException
      }
    }
  }
}

object PythonUDFRunner {

  def writeUDFs(
                 dataOut: DataOutputStream,
                 funcs: Seq[ChainedPythonFunctions],
                 argOffsets: Array[Array[Int]]): Unit = {
    dataOut.writeInt(funcs.length)
    funcs.zip(argOffsets).foreach { case (chained, offsets) =>
      dataOut.writeInt(offsets.length)
      offsets.foreach { offset =>
        dataOut.writeInt(offset)
      }
      dataOut.writeInt(chained.funcs.length)
      chained.funcs.foreach { f =>
        dataOut.writeInt(f.command.length)
        dataOut.write(f.command)
      }
    }
  }
}
