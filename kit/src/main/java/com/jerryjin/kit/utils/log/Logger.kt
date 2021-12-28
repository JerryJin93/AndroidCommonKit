package com.jerryjin.kit.utils.log

import android.util.Log
import com.jerryjin.kit.base.App
import kotlin.reflect.KCallable

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 14:42
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
 * Description: A simple, convenient logger.
 */
enum class LogPriority {
    DEBUG,
    ERROR,
    INFO,
    VERBOSE,
    WARNING,
}

inline fun <reified R> getLogPrefixWithKTMethodName(
    prefix: String = "In method: ",
    kCallable: KCallable<R>,
) =
    "$prefix ${kCallable.name} "

inline fun <reified T> getFormattedLog(
    prefix: String? = null,
    kCallable: KCallable<T>,
    log: () -> String
) =
    getLogPrefixWithKTMethodName<T>(
        prefix ?: "",
        kCallable
    ) + log()

class SimpleLogger private constructor() {

    companion object {

        var isDebug = false

        var tag: String = App.instance.packageName

        @JvmStatic
        fun print(
            priority: LogPriority = LogPriority.DEBUG,
            tag: String = Companion.tag,
            msg: String
        ) {
            if (isDebug) {
                // In order to be seen more clearly in the logcat of Android Studio,
                // we choose Log#e
                Log.e(tag, msg)
            } else {
                when (priority) {
                    LogPriority.DEBUG -> Log.d(tag, msg)
                    LogPriority.ERROR -> Log.e(tag, msg)
                    LogPriority.INFO -> Log.i(tag, msg)
                    LogPriority.VERBOSE -> Log.v(tag, msg)
                    LogPriority.WARNING -> Log.w(tag, msg)
                }
            }
        }

    }
}
