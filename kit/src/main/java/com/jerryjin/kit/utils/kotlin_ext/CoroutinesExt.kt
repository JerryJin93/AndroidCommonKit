package com.jerryjin.kit.utils.kotlin_ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resumeWithException

/**
 * Author: Jerry
 *
 * Created at: 2021/12/30 16:34
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
 * Description: Supports for Kotlin Coroutines.
 */
suspend infix fun <T> ExecutorService.submitWithCoroutines(callable: Callable<T>) =
    suspendCancellableCoroutine<T> {
        Thread.currentThread().setUncaughtExceptionHandler { _, e -> it.resumeWithException(e) }
        it.resumeWith(Result.success(callable.call()))
    }

infix fun <T> CoroutineScope.flowWith(flowParameter: Pair<CoroutineContext, Pair<suspend () -> T, suspend (T) -> Unit>>) =
    launch {
        flowParameter.run {
            flow {
                emit(second.first())
            }.flowOn(first)
                .collect {
                    second.second(it)
                }
        }
    }