package com.jerryjin.kit.utils

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import kotlinx.coroutines.*
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:16
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
 * Description: Extensions for Kotlin.
 */
fun Char.toByteN() = this.code.toByte()

fun <T> T.toUnit(): Unit = let { }

fun Canvas.autoSave(block: Canvas.() -> Unit) = run {
    save()
    block()
    restore()
}

fun RectF.reset() = set(0f, 0f, 0f, 0f)

fun Rect.reset() = run {
    left = 0
    top = 0
    right = 0
    bottom = 0
}

inline fun <T : Any, reified I> T.newProxyInstance(
    vararg interfaces: Class<I>,
    crossinline block: T.(proxy: Any, method: Method, args: Array<Any>?) -> Unit
): I =
    Proxy.newProxyInstance(this::class.java.classLoader, interfaces) { proxy, method, args ->
        block(proxy, method, args)
    } as I

infix fun View?.changeTo(visibility: Int) {
    this?.visibility = visibility
}

val LAZY_JOB_FACTORY: (CoroutineScope, suspend CoroutineScope.() -> Unit) -> Job = { scope, block ->
    scope.launch(start = CoroutineStart.LAZY) {
        block()
    }
}

suspend fun (() -> Unit).repeat(overall: Long, interval: Long) {
    repeat((overall / interval).toInt()) {
        this()
        delay(interval)
    }
}

fun <T : Comparable<T>> List<T>.minItem() : T? =
    if (size == 0) null
    else fold(this[0]) { initial, next ->
        if (initial < next) initial
        else next
    }


fun <T : Comparable<T>> List<T>.maxItem(): T? =
    if (size == 0) null
    else fold(this[0]) { initial, next ->
        if (initial > next) initial
        else next
    }

inline infix fun <reified T> KClass<*>.getTypedValue(item: Any): T = cast(item) as T