package org.apache.spark.daslab.sql.execution.streaming



import java.io.{InterruptedIOException, IOException, UncheckedIOException}
import java.nio.channels.ClosedByInterruptException
import java.util.UUID
import java.util.concurrent.{CountDownLatch, ExecutionException, TimeUnit}
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.{Condition, ReentrantLock}

import scala.collection.mutable.{Map => MutableMap}
import scala.util.control.NonFatal

import com.google.common.util.concurrent.UncheckedExecutionException
import org.apache.hadoop.fs.Path

import org.apache.spark.daslab.sql._
import org.apache.spark.daslab.sql.engine.plans.logical.LogicalPlan
import org.apache.spark.daslab.sql.execution.QueryExecution
import org.apache.spark.daslab.sql.execution.command.StreamingExplainCommand
import org.apache.spark.daslab.sql.internal.SQLConf
import org.apache.spark.daslab.sql.streaming._

//todo
import org.apache.spark.util.{Clock, UninterruptibleThread, Utils}
import org.apache.spark.internal.Logging


/** States for [[StreamExecution]]'s lifecycle. */
trait State
case object INITIALIZING extends State
case object ACTIVE extends State
case object TERMINATED extends State
case object RECONFIGURING extends State

/**
 * Manages the execution of a streaming Spark SQL query that is occurring in a separate thread.
 * Unlike a standard query, a streaming query executes repeatedly each time new data arrives at any
 * [[Source]] present in the query plan. Whenever new data arrives, a [[QueryExecution]] is created
 * and the results are committed transactionally to the given [[Sink]].
 *
 * @param deleteCheckpointOnStop whether to delete the checkpoint if the query is stopped without
 *                               errors
 */
abstract class StreamExecution(
                                override val sparkSession: SparkSession,
                                override val name: String,
                                private val checkpointRoot: String,
                                analyzedPlan: LogicalPlan,
                                val sink: BaseStreamingSink,
                                val trigger: Trigger,
                                val triggerClock: Clock,
                                val outputMode: OutputMode,
                                deleteCheckpointOnStop: Boolean)
  extends StreamingQuery with ProgressReporter with Logging {

  import org.apache.spark.daslab.sql.streaming.StreamingQueryListener._

  protected val pollingDelayMs: Long = sparkSession.sessionState.conf.streamingPollingDelay

  protected val minLogEntriesToMaintain: Int = sparkSession.sessionState.conf.minBatchesToRetain
  require(minLogEntriesToMaintain > 0, "minBatchesToRetain has to be positive")

  /**
   * A lock used to wait/notify when batches complete. Use a fair lock to avoid thread starvation.
   */
  protected val awaitProgressLock = new ReentrantLock(true)
  protected val awaitProgressLockCondition = awaitProgressLock.newCondition()

  private val initializationLatch = new CountDownLatch(1)
  private val startLatch = new CountDownLatch(1)
  private val terminationLatch = new CountDownLatch(1)

  val resolvedCheckpointRoot = {
    val checkpointPath = new Path(checkpointRoot)
    val fs = checkpointPath.getFileSystem(sparkSession.sessionState.newHadoopConf())
    fs.mkdirs(checkpointPath)
    checkpointPath.makeQualified(fs.getUri, fs.getWorkingDirectory).toUri.toString
  }

  def logicalPlan: LogicalPlan

  /**
   * Tracks how much data we have processed and committed to the sink or state store from each
   * input source.
   * Only the scheduler thread should modify this field, and only in atomic steps.
   * Other threads should make a shallow copy if they are going to access this field more than
   * once, since the field's value may change at any time.
   */
  @volatile
  var committedOffsets = new StreamProgress

  /**
   * Tracks the offsets that are available to be processed, but have not yet be committed to the
   * sink.
   * Only the scheduler thread should modify this field, and only in atomic steps.
   * Other threads should make a shallow copy if they are going to access this field more than
   * once, since the field's value may change at any time.
   */
  @volatile
  var availableOffsets = new StreamProgress

  /** The current batchId or -1 if execution has not yet been initialized. */
  protected var currentBatchId: Long = -1

  /** Metadata associated with the whole query */
  protected val streamMetadata: StreamMetadata = {
    val metadataPath = new Path(checkpointFile("metadata"))
    val hadoopConf = sparkSession.sessionState.newHadoopConf()
    StreamMetadata.read(metadataPath, hadoopConf).getOrElse {
      val newMetadata = new StreamMetadata(UUID.randomUUID.toString)
      StreamMetadata.write(newMetadata, metadataPath, hadoopConf)
      newMetadata
    }
  }

  /** Metadata associated with the offset seq of a batch in the query. */
  protected var offsetSeqMetadata = OffsetSeqMetadata(
    batchWatermarkMs = 0, batchTimestampMs = 0, sparkSession.conf)

  /**
   * A map of current watermarks, keyed by the position of the watermark operator in the
   * physical plan.
   *
   * This state is 'soft state', which does not affect the correctness and semantics of watermarks
   * and is not persisted across query restarts.
   * The fault-tolerant watermark state is in offsetSeqMetadata.
   */
  protected val watermarkMsMap: MutableMap[Int, Long] = MutableMap()

  override val id: UUID = UUID.fromString(streamMetadata.id)

  override val runId: UUID = UUID.randomUUID

  /**
   * Pretty identified string of printing in logs. Format is
   * If name is set "queryName [id = xyz, runId = abc]" else "[id = xyz, runId = abc]"
   */
  protected val prettyIdString =
    Option(name).map(_ + " ").getOrElse("") + s"[id = $id, runId = $runId]"

  /**
   * A list of unique sources in the query plan. This will be set when generating logical plan.
   */
  @volatile protected var uniqueSources: Seq[BaseStreamingSource] = Seq.empty

  /** Defines the internal state of execution */
  protected val state = new AtomicReference[State](INITIALIZING)

  @volatile
  var lastExecution: IncrementalExecution = _

  /** Holds the most recent input data for each source. */
  protected var newData: Map[BaseStreamingSource, LogicalPlan] = _

  @volatile
  protected var streamDeathCause: StreamingQueryException = null

  /* Get the call site in the caller thread; will pass this into the micro batch thread */
  private val callSite = Utils.getCallSite()

  /** Used to report metrics to coda-hale. This uses id for easier tracking across restarts. */
  lazy val streamMetrics = new MetricsReporter(
    this, s"spark.streaming.${Option(name).getOrElse(id)}")

  /** Isolated spark session to run the batches with. */
  private val sparkSessionForStream = sparkSession.cloneSession()

  /**
   * The thread that runs the micro-batches of this stream. Note that this thread must be
   * [[org.apache.spark.util.UninterruptibleThread]] to workaround KAFKA-1894: interrupting a
   * running `KafkaConsumer` may cause endless loop.
   */
  val queryExecutionThread: QueryExecutionThread =
    new QueryExecutionThread(s"stream execution thread for $prettyIdString") {
      override def run(): Unit = {
        // To fix call site like "run at <unknown>:0", we bridge the call site from the caller
        // thread to this micro batch thread
        sparkSession.sparkContext.setCallSite(callSite)
        runStream()
      }
    }

  /**
   * A write-ahead-log that records the offsets that are present in each batch. In order to ensure
   * that a given batch will always consist of the same data, we write to this log *before* any
   * processing is done.  Thus, the Nth record in this log indicated data that is currently being
   * processed and the N-1th entry indicates which offsets have been durably committed to the sink.
   */
  val offsetLog = new OffsetSeqLog(sparkSession, checkpointFile("offsets"))

  /**
   * A log that records the batch ids that have completed. This is used to check if a batch was
   * fully processed, and its output was committed to the sink, hence no need to process it again.
   * This is used (for instance) during restart, to help identify which batch to run next.
   */
  val commitLog = new CommitLog(sparkSession, checkpointFile("commits"))

  /** Whether all fields of the query have been initialized */
  private def isInitialized: Boolean = state.get != INITIALIZING

  /** Whether the query is currently active or not */
  override def isActive: Boolean = state.get != TERMINATED

  /** Returns the [[StreamingQueryException]] if the query was terminated by an exception. */
  override def exception: Option[StreamingQueryException] = Option(streamDeathCause)

  /** Returns the path of a file with `name` in the checkpoint directory. */
  protected def checkpointFile(name: String): String =
    new Path(new Path(resolvedCheckpointRoot), name).toUri.toString

  /**
   * Starts the execution. This returns only after the thread has started and [[QueryStartedEvent]]
   * has been posted to all the listeners.
   */
  def start(): Unit = {
    logInfo(s"Starting $prettyIdString. Use $resolvedCheckpointRoot to store the query checkpoint.")
    queryExecutionThread.setDaemon(true)
    queryExecutionThread.start()
    startLatch.await()  // Wait until thread started and QueryStart event has been posted
  }

  /**
   * Run the activated stream until stopped.
   */
  protected def runActivatedStream(sparkSessionForStream: SparkSession): Unit

  /**
   * Activate the stream and then wrap a callout to runActivatedStream, handling start and stop.
   *
   * Note that this method ensures that [[QueryStartedEvent]] and [[QueryTerminatedEvent]] are
   * posted such that listeners are guaranteed to get a start event before a termination.
   * Furthermore, this method also ensures that [[QueryStartedEvent]] event is posted before the
   * `start()` method returns.
   */
  private def runStream(): Unit = {
    try {
      sparkSession.sparkContext.setJobGroup(runId.toString, getBatchDescriptionString,
        interruptOnCancel = true)
      sparkSession.sparkContext.setLocalProperty(StreamExecution.QUERY_ID_KEY, id.toString)
      if (sparkSession.sessionState.conf.streamingMetricsEnabled) {
        sparkSession.sparkContext.env.metricsSystem.registerSource(streamMetrics)
      }

      // `postEvent` does not throw non fatal exception.
      postEvent(new QueryStartedEvent(id, runId, name))

      // Unblock starting thread
      startLatch.countDown()

      // While active, repeatedly attempt to run batches.
      SparkSession.setActiveSession(sparkSession)

      updateStatusMessage("Initializing sources")
      // force initialization of the logical plan so that the sources can be created
      logicalPlan

      // Adaptive execution can change num shuffle partitions, disallow
      sparkSessionForStream.conf.set(SQLConf.ADAPTIVE_EXECUTION_ENABLED.key, "false")
      // Disable cost-based join optimization as we do not want stateful operations to be rearranged
      sparkSessionForStream.conf.set(SQLConf.CBO_ENABLED.key, "false")
      offsetSeqMetadata = OffsetSeqMetadata(
        batchWatermarkMs = 0, batchTimestampMs = 0, sparkSessionForStream.conf)

      if (state.compareAndSet(INITIALIZING, ACTIVE)) {
        // Unblock `awaitInitialization`
        initializationLatch.countDown()
        runActivatedStream(sparkSessionForStream)
        updateStatusMessage("Stopped")
      } else {
        // `stop()` is already called. Let `finally` finish the cleanup.
      }
    } catch {
      case e if isInterruptedByStop(e) =>
        // interrupted by stop()
        updateStatusMessage("Stopped")
      case e: IOException if e.getMessage != null
        && e.getMessage.startsWith(classOf[InterruptedException].getName)
        && state.get == TERMINATED =>
        // This is a workaround for HADOOP-12074: `Shell.runCommand` converts `InterruptedException`
        // to `new IOException(ie.toString())` before Hadoop 2.8.
        updateStatusMessage("Stopped")
      case e: Throwable =>
        streamDeathCause = new StreamingQueryException(
          toDebugString(includeLogicalPlan = isInitialized),
          s"Query $prettyIdString terminated with exception: ${e.getMessage}",
          e,
          committedOffsets.toOffsetSeq(sources, offsetSeqMetadata).toString,
          availableOffsets.toOffsetSeq(sources, offsetSeqMetadata).toString)
        logError(s"Query $prettyIdString terminated with error", e)
        updateStatusMessage(s"Terminated with exception: ${e.getMessage}")
        // Rethrow the fatal errors to allow the user using `Thread.UncaughtExceptionHandler` to
        // handle them
        if (!NonFatal(e)) {
          throw e
        }
    } finally queryExecutionThread.runUninterruptibly {
      // The whole `finally` block must run inside `runUninterruptibly` to avoid being interrupted
      // when a query is stopped by the user. We need to make sure the following codes finish
      // otherwise it may throw `InterruptedException` to `UncaughtExceptionHandler` (SPARK-21248).

      // Release latches to unblock the user codes since exception can happen in any place and we
      // may not get a chance to release them
      startLatch.countDown()
      initializationLatch.countDown()

      try {
        stopSources()
        state.set(TERMINATED)
        currentStatus = status.copy(isTriggerActive = false, isDataAvailable = false)

        // Update metrics and status
        sparkSession.sparkContext.env.metricsSystem.removeSource(streamMetrics)

        // Notify others
        sparkSession.streams.notifyQueryTermination(StreamExecution.this)
        postEvent(
          new QueryTerminatedEvent(id, runId, exception.map(_.cause).map(Utils.exceptionString)))

        // Delete the temp checkpoint only when the query didn't fail
        if (deleteCheckpointOnStop && exception.isEmpty) {
          val checkpointPath = new Path(resolvedCheckpointRoot)
          try {
            val fs = checkpointPath.getFileSystem(sparkSession.sessionState.newHadoopConf())
            fs.delete(checkpointPath, true)
          } catch {
            case NonFatal(e) =>
              // Deleting temp checkpoint folder is best effort, don't throw non fatal exceptions
              // when we cannot delete them.
              logWarning(s"Cannot delete $checkpointPath", e)
          }
        }
      } finally {
        awaitProgressLock.lock()
        try {
          // Wake up any threads that are waiting for the stream to progress.
          awaitProgressLockCondition.signalAll()
        } finally {
          awaitProgressLock.unlock()
        }
        terminationLatch.countDown()
      }
    }
  }

  private def isInterruptedByStop(e: Throwable): Boolean = {
    if (state.get == TERMINATED) {
      StreamExecution.isInterruptionException(e)
    } else {
      false
    }
  }

  override protected def postEvent(event: StreamingQueryListener.Event): Unit = {
    sparkSession.streams.postListenerEvent(event)
  }

  /** Stops all streaming sources safely. */
  protected def stopSources(): Unit = {
    uniqueSources.foreach { source =>
      try {
        source.stop()
      } catch {
        case NonFatal(e) =>
          logWarning(s"Failed to stop streaming source: $source. Resources may have leaked.", e)
      }
    }
  }

  /**
   * Blocks the current thread until processing for data from the given `source` has reached at
   * least the given `Offset`. This method is intended for use primarily when writing tests.
   */
  private[sql] def awaitOffset(sourceIndex: Int, newOffset: Offset, timeoutMs: Long): Unit = {
    assertAwaitThread()
    def notDone = {
      val localCommittedOffsets = committedOffsets
      if (sources == null) {
        // sources might not be initialized yet
        false
      } else {
        val source = sources(sourceIndex)
        !localCommittedOffsets.contains(source) || localCommittedOffsets(source) != newOffset
      }
    }

    while (notDone) {
      awaitProgressLock.lock()
      try {
        awaitProgressLockCondition.await(timeoutMs, TimeUnit.MILLISECONDS)
        if (streamDeathCause != null) {
          throw streamDeathCause
        }
      } finally {
        awaitProgressLock.unlock()
      }
    }
    logDebug(s"Unblocked at $newOffset for ${sources(sourceIndex)}")
  }

  /** A flag to indicate that a batch has completed with no new data available. */
  @volatile protected var noNewData = false

  /**
   * Assert that the await APIs should not be called in the stream thread. Otherwise, it may cause
   * dead-lock, e.g., calling any await APIs in `StreamingQueryListener.onQueryStarted` will block
   * the stream thread forever.
   */
  private def assertAwaitThread(): Unit = {
    if (queryExecutionThread eq Thread.currentThread) {
      throw new IllegalStateException(
        "Cannot wait for a query state from the same thread that is running the query")
    }
  }

  /**
   * Await until all fields of the query have been initialized.
   */
  def awaitInitialization(timeoutMs: Long): Unit = {
    assertAwaitThread()
    require(timeoutMs > 0, "Timeout has to be positive")
    if (streamDeathCause != null) {
      throw streamDeathCause
    }
    initializationLatch.await(timeoutMs, TimeUnit.MILLISECONDS)
    if (streamDeathCause != null) {
      throw streamDeathCause
    }
  }

  override def processAllAvailable(): Unit = {
    assertAwaitThread()
    if (streamDeathCause != null) {
      throw streamDeathCause
    }
    if (!isActive) return
    awaitProgressLock.lock()
    try {
      noNewData = false
      while (true) {
        awaitProgressLockCondition.await(10000, TimeUnit.MILLISECONDS)
        if (streamDeathCause != null) {
          throw streamDeathCause
        }
        if (noNewData || !isActive) {
          return
        }
      }
    } finally {
      awaitProgressLock.unlock()
    }
  }

  override def awaitTermination(): Unit = {
    assertAwaitThread()
    terminationLatch.await()
    if (streamDeathCause != null) {
      throw streamDeathCause
    }
  }

  override def awaitTermination(timeoutMs: Long): Boolean = {
    assertAwaitThread()
    require(timeoutMs > 0, "Timeout has to be positive")
    terminationLatch.await(timeoutMs, TimeUnit.MILLISECONDS)
    if (streamDeathCause != null) {
      throw streamDeathCause
    } else {
      !isActive
    }
  }

  /** Expose for tests */
  def explainInternal(extended: Boolean): String = {
    if (lastExecution == null) {
      "No physical plan. Waiting for data."
    } else {
      val explain = StreamingExplainCommand(lastExecution, extended = extended)
      sparkSession.sessionState.executePlan(explain).executedPlan.executeCollect()
        .map(_.getString(0)).mkString("\n")
    }
  }

  override def explain(extended: Boolean): Unit = {
    // scalastyle:off println
    println(explainInternal(extended))
    // scalastyle:on println
  }

  override def explain(): Unit = explain(extended = false)

  override def toString: String = {
    s"Streaming Query $prettyIdString [state = $state]"
  }

  private def toDebugString(includeLogicalPlan: Boolean): String = {
    val debugString =
      s"""|=== Streaming Query ===
          |Identifier: $prettyIdString
          |Current Committed Offsets: $committedOffsets
          |Current Available Offsets: $availableOffsets
          |
          |Current State: $state
          |Thread State: ${queryExecutionThread.getState}""".stripMargin
    if (includeLogicalPlan) {
      debugString + s"\n\nLogical Plan:\n$logicalPlan"
    } else {
      debugString
    }
  }

  protected def getBatchDescriptionString: String = {
    val batchDescription = if (currentBatchId < 0) "init" else currentBatchId.toString
    Option(name).map(_ + "<br/>").getOrElse("") +
      s"id = $id<br/>runId = $runId<br/>batch = $batchDescription"
  }
}

object StreamExecution {
  val QUERY_ID_KEY = "sql.streaming.queryId"
  val IS_CONTINUOUS_PROCESSING = "__is_continuous_processing"

  def isInterruptionException(e: Throwable): Boolean = e match {
    // InterruptedIOException - thrown when an I/O operation is interrupted
    // ClosedByInterruptException - thrown when an I/O operation upon a channel is interrupted
    case _: InterruptedException | _: InterruptedIOException | _: ClosedByInterruptException =>
      true
    // The cause of the following exceptions may be one of the above exceptions:
    //
    // UncheckedIOException - thrown by codes that cannot throw a checked IOException, such as
    //                        BiFunction.apply
    // ExecutionException - thrown by codes running in a thread pool and these codes throw an
    //                      exception
    // UncheckedExecutionException - thrown by codes that cannot throw a checked
    //                               ExecutionException, such as BiFunction.apply
    case e2 @ (_: UncheckedIOException | _: ExecutionException | _: UncheckedExecutionException)
      if e2.getCause != null =>
      isInterruptionException(e2.getCause)
    case _ =>
      false
  }
}

/**
 * A special thread to run the stream query. Some codes require to run in the QueryExecutionThread
 * and will use `classOf[QueryxecutionThread]` to check.
 */
abstract class QueryExecutionThread(name: String) extends UninterruptibleThread(name)
