package com.jerryjin.kit.utils.kotlin_ext

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Author: Jerry
 *
 * Created at: 2021/10/16 20:34
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
 * Description: Kotlin extensions for [com.tencent.mmkv.MMKV].
 */
private inline fun <T> MMKV.delegate(
    key: String? = null,
    defVal: T,
    crossinline getter: MMKV.(String, T) -> T,
    crossinline setter: MMKV.(String, T) -> Boolean
) = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        getter(key ?: property.name, defVal)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        setter(key ?: property.name, value)
    }
}

private inline fun <T> MMKV.nullableDelegate(
    key: String? = null,
    defVal: T?,
    crossinline getter: MMKV.(String, T?) -> T,
    crossinline setter: MMKV.(String, T) -> Boolean
) =
    object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T =
            getter(key ?: property.name, defVal)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            setter(key ?: property.name, value)
        }
    }

fun MMKV.boolean(key: String? = null, defVal: Boolean = false) =
    delegate(key, defVal, MMKV::decodeBool, MMKV::encode)

fun MMKV.int(key: String? = null, defVal: Int = 0) =
    delegate(key, defVal, MMKV::decodeInt, MMKV::encode)

fun MMKV.float(key: String? = null, defVal: Float = 0f) =
    delegate(key, defVal, MMKV::decodeFloat, MMKV::encode)

fun MMKV.double(key: String? = null, defVal: Double = 0.0) =
    delegate(key, defVal, MMKV::decodeDouble, MMKV::encode)

fun MMKV.long(key: String? = null, defVal: Long = 0L) =
    delegate(key, defVal, MMKV::decodeLong, MMKV::encode)

fun MMKV.string(key: String? = null, defVal: String? = null) =
    nullableDelegate(key, defVal, MMKV::decodeString, MMKV::encode)

fun MMKV.stringSet(key: String? = null, defVal: Set<String>? = null) =
    nullableDelegate(key, defVal, MMKV::decodeStringSet, MMKV::encode)

fun MMKV.byteArray(key: String? = null, defVal: ByteArray? = null) =
    nullableDelegate(key, defVal, MMKV::decodeBytes, MMKV::encode)

inline fun <reified T : Parcelable> MMKV.parcelable(key: String?, defVal: T? = null) =
    object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T =
            decodeParcelable(key ?: property.name, T::class.java, defVal)!!

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            encode(key ?: property.name, value)
        }
    }