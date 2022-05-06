package com.base.toolsframe.networkframe

import android.os.Handler
import android.os.Looper
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

class DefaultExecutor private constructor() : Executor {
    private val mainHandler = Handler(Looper.getMainLooper())
    private val diskIO = Executors.newFixedThreadPool(2, object : ThreadFactory {
        private val THREAD_NAME_STEM = "optional_disk_io_%d"
        private val threadId = AtomicInteger(0)
        override fun newThread(r: Runnable): Thread {
            val t = Thread(r)
            t.name = String.format(
                Locale.getDefault(),
                "optional_disk_io_%d",
                threadId.getAndIncrement()
            )
            return t
        }
    })

    fun executeOnMainThread(runnable: Runnable) {
        if (isMainThread) {
            runnable.run()
        } else {
            mainHandler.post(runnable)
        }
    }

    fun executeOnDiskIO(runnable: Runnable) {
        diskIO.execute(runnable)
    }

    override fun execute(command: Runnable) {
        command.run()
    }

    fun postToMainThread(runnable: Runnable) {
        mainHandler.post(runnable)
    }

    companion object {
        private val EXECUTOR = DefaultExecutor()
        fun get(): DefaultExecutor {
            return EXECUTOR
        }

        val isMainThread: Boolean
            get() = Looper.getMainLooper().thread === Thread.currentThread()
    }
}