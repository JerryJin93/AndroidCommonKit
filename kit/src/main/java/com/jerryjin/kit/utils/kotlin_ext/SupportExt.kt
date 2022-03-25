package com.jerryjin.kit.utils.kotlin_ext

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.util.ArrayMap
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
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

/**
 * Mirror this map.
 *
 * Warning: If each value in current map is not unique, some keys will be missing after mirroring.
 */
fun <K, V> Map<K, V>.mirror(): Map<V, K> =
    mutableMapOf<V, K>().apply result@{
        this@mirror.entries.forEach {
            this@result[it.value] = it.key
        }
    }

fun Canvas.autoSave(block: Canvas.() -> Unit) = run {
    save()
    block()
    restore()
}

fun <T> List<T>.inverse(): List<T> = mutableListOf<T>().apply {
    for (index in this@inverse.size - 1 downTo 0) {
        add(this@inverse[index])
    }
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

fun <K, V> arrayMapOf(vararg pairs: Pair<K, V>) = ArrayMap<K, V>().apply {
    for ((key, value) in pairs) {
        put(key, value)
    }
}

fun <K, V> ArrayMap<K, V>.last(): V = valueAt(size - 1)

fun <T> Array<T>.subArray(start: Int, count: Int) = copyOfRange(start, start + count)

fun <DF : DialogFragment> DF.showSafely(fragmentManager: FragmentManager, tag: String) {
    if (!isVisible) show(fragmentManager, tag)
}

fun <DF : DialogFragment> DF.dismissSafely() {
    if (isVisible) dismissAllowingStateLoss()
}

fun <T> List<T>.copy() = mutableListOf<T>().apply {
    addAll(this)
} as List<T>

fun <T> List<T>.sliceBy(segment: Int): List<List<T>> {
    val fCountPerSegment = size / segment * 1f
    if (size.toFloat() != fCountPerSegment * segment) throw IllegalArgumentException("Illegal segment argument!")
    val result = mutableListOf<List<T>>()
    val countPerSegment = fCountPerSegment.toInt()
    for (i in 0 until segment) {
        result.add(safeSubList(countPerSegment * i, countPerSegment * i + countPerSegment))
    }
    return result
}

/**
 * The [ConcurrentModificationException] would be thrown if iterates and modifies the sublist for the modCount is changed
 * after every modification-like operation.
 *
 * The simple solution is just to create a new list instance and pass the old data to it.
 */
fun <T> List<T>.safeSubList(fromIndex: Int, toIndex: Int) =
    ArrayList<T>(subList(fromIndex, toIndex))

fun <T> T.toWeakRef() = WeakReference(this)