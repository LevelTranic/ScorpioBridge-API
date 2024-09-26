package one.tranic.scorpio.api.util

import com.google.common.util.concurrent.ThreadFactoryBuilder
import java.util.concurrent.Executors
import java.util.concurrent.Future

object Scheduler {
    private val dispatcher = Executors.newScheduledThreadPool(
        8, ThreadFactoryBuilder()
            .setThreadFactory(Thread.ofVirtual().factory())
            .setNameFormat("ScorpioBridge Thread - %1\$d")
            .build()
    )

    fun put(block: Runnable): Future<*> {
        return dispatcher.submit(block)
    }

    fun shutdown() {
        dispatcher.shutdown()
    }
}