package one.tranic.scorpio.api.util

import com.google.common.util.concurrent.ThreadFactoryBuilder
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Utility object for scheduling and managing asynchronous tasks.
 */
@Deprecated("You should not use this method, each plugin should manually maintain its own asynchronous thread pool instead of using the shared thread pool provided by the API.")
object Scheduler {
    private val dispatcher = Executors.newScheduledThreadPool(
        8, ThreadFactoryBuilder()
            .setThreadFactory(Thread.ofVirtual().factory())
            .setNameFormat("ScorpioBridge Thread - %1\$d")
            .build()
    )

    /**
     * Submits a runnable task for execution in the scheduled thread pool.
     *
     * @param block the runnable task to be executed
     * @return a Future representing the pending results of the task
     */
    @Deprecated("You should not use this method, each plugin should manually maintain its own asynchronous thread pool instead of using the shared thread pool provided by the API.")
    fun put(block: Runnable): Future<*> {
        return dispatcher.submit(block)
    }

    /**
     * Shuts down the scheduled thread pool, stopping all scheduled tasks.
     */
    @Deprecated("You should not use this method, each plugin should manually maintain its own asynchronous thread pool instead of using the shared thread pool provided by the API.")
    fun shutdown() {
        dispatcher.shutdown()
    }
}
