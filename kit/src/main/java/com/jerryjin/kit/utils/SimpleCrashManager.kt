package com.jerryjin.kit.utils

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.util.Log
import com.jerryjin.kit.utils.kotlin_ext.writeTo
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.min
import kotlin.system.exitProcess

/**
 * Author: Jerry
 *
 * Created at: 2022/03/25 12:50
 *
 * GitHub: https://github.com/JerryJin93
 *
 * Blog:
 *
 * Email: jerry93@foxmail.com

 * WeChat: AcornLake
 *
 * Version: 1.0.0
 *
 * Description: A simple crash monitor manager.
 */
class SimpleCrashManager private constructor() : Thread.UncaughtExceptionHandler {

    companion object {

        private const val TAG = "SimpleCrashManager"

        private const val DEFAULT_DEBUG_DELAY = 10_000L

        private const val EXIT_STATUS_ERROR = 10

        private const val CRASH_PREFIX = "crash_log_"

        private const val DEFAULT_DATE_PATTERN = "yyyy.MM.dd HH:mm:ss:sss"

        private const val DEFAULT_BUFFER_SIZE = 4096

        private const val MAX_WORKER_COUNT = 5
        private const val DEFAULT_KEEP_ALIVE_TIME = 30L

        private const val ERR_UNABLE_TO_CREATE_CRASH_ROOT_RIGHT_NOW =
            "Unable to create crash root right now."

        private const val DEFAULT_CRASH_ROOT_DIR = "crashes"
        private val INSTANCE by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { SimpleCrashManager() }

        // When it's true, you can check the log in ten seconds after vanishing.
        var DEBUG = false

        fun init(
            context: Context,
            crashRoot: String = DEFAULT_CRASH_ROOT_DIR,
            fallback: (String) -> Unit
        ) = try {
            INSTANCE.crashRoot = context.applicationContext.getExternalFilesDir(crashRoot)!!
        } catch (e: Exception) {
            fallback(ERR_UNABLE_TO_CREATE_CRASH_ROOT_RIGHT_NOW)
        }
    }

    private val createdWorkerCounter = AtomicInteger()

    private val workerPoolExecutor by lazy {
        val availableProcessors = Runtime.getRuntime().availableProcessors()
        ThreadPoolExecutor(
            min(availableProcessors, 4),
            MAX_WORKER_COUNT,
            DEFAULT_KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            LinkedBlockingQueue()
        ) { runnable ->
            Thread(runnable, "$TAG-worker#${createdWorkerCounter.incrementAndGet()}")
        }
    }

    private lateinit var crashRoot: File

    private lateinit var exitHandler: Handler

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
        workerPoolExecutor.execute {
            Looper.prepare()
            exitHandler = Handler(Looper.myLooper()!!)
            Looper.loop()
        }
    }

    fun redirect(crashRoot: File) {
        val oldCrashRoot = this.crashRoot
        if (!oldCrashRoot.exists() || !oldCrashRoot.isDirectory) return
        oldCrashRoot.listFiles()?.forEach {
            it.writeTo(File(crashRoot, it.name))
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) = writeErrToFile(e.getStackTraceInfo())

    private fun Throwable.getStackTraceInfo(): String =
        StringWriter().run stringWriter@{
            PrintWriter(this@stringWriter).use {
                printStackTrace(it)
                this@stringWriter.toString()
            }
        }

    private fun writeErrToFile(errMessage: String) = workerPoolExecutor.execute {
        Log.e(TAG, "Error occurred, writing to file...")
        BufferedWriter(
            FileWriter(
                File(
                    crashRoot,
                    "$CRASH_PREFIX${System.currentTimeMillis()}"
                ), true
            )
        ).use { bufferedWriter ->
            bufferedWriter.run {
                write("+++----+++Crash Log+++----+++")
                newLine()
                newLine()
                write(
                    "crashed at: ${
                        SimpleDateFormat(
                            DEFAULT_DATE_PATTERN,
                            Locale.getDefault()
                        ).format(System.currentTimeMillis())
                    }"
                )
                newLine()
                newLine()
                write("Brand: ${Build.BRAND}")
                newLine()
                newLine()
                write("Manufacturer: ${Build.MANUFACTURER}")
                newLine()
                newLine()
                write("Model: ${Build.MODEL}")
                newLine()
                newLine()
                write("Identifier: ${Build.FINGERPRINT}")
                newLine()
                newLine()
                write("OS version: Android ${Build.VERSION.SDK_INT}")
                newLine()
                newLine()
                write("Begin of crash ---------->")
                newLine()
                newLine()
                write(errMessage)
                newLine()
                write("End of crash <----------")
            }
        }
        if (DEBUG) Log.e(TAG, "The crash log above will disappear in 10 seconds.")
        // Try everything to make sure this process goes away.
        exitHandler.postDelayed({
            Process.killProcess(Process.myPid())
            exitProcess(EXIT_STATUS_ERROR)
        }, if (DEBUG) DEFAULT_DEBUG_DELAY else 0)
    }

}