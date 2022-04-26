package com.jerryjin.kit.utils.kotlin_ext

import android.util.Log
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:05
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
 * Description: Supports for reflection.
 */
const val LOG_TAG = "Reflection"

inline fun <reified T> T.toMap(): Map<String, Any?> =
    T::class.java.let {
        mutableMapOf<String, Any?>().apply {
            try {
                it.declaredFields.forEach {
                    try {
                        it.isAccessible = true
                        put(it.name, it.get(this@toMap))
                    } catch (e: SecurityException) {
                        Log.e(LOG_TAG, e.message ?: SecurityException::class.java.name)
                    } catch (e: IllegalAccessException) {
                        Log.e(LOG_TAG, e.message ?: IllegalAccessException::class.java.name)
                    } catch (e: IllegalArgumentException) {
                        Log.e(LOG_TAG, e.message ?: IllegalArgumentException::class.java.name)
                    } catch (e: NullPointerException) {
                        Log.e(LOG_TAG, e.message ?: NullPointerException::class.java.name)
                    } catch (e: ExceptionInInitializerError) {
                        Log.e(LOG_TAG, e.message ?: ExceptionInInitializerError::class.java.name)
                    }
                }
            } catch (e: SecurityException) {
                Log.e(LOG_TAG, e.message ?: SecurityException::class.java.name)
            }
        }
    }

/**
 * @param name field name to be replaced
 *
 * @param value value to replace the given field
 *
 * Replace the specific field by [name] with the given [value].
 */
inline fun <reified T, V> T.replaceField(name: String, value: V): Unit =
    T::class.java.let {
        try {
            it.getDeclaredField(name).apply {
                isAccessible = true
                set(this@replaceField, value)
            }
        } catch (e: NoSuchFieldException) {
            Log.e(LOG_TAG, e.message ?: NoSuchFieldException::class.java.name)
        } catch (e: NullPointerException) {
            Log.e(LOG_TAG, e.message ?: NullPointerException::class.java.name)
        } catch (e: SecurityException) {
            Log.e(LOG_TAG, e.message ?: SecurityException::class.java.name)
        } catch (e: IllegalAccessException) {
            Log.e(LOG_TAG, e.message ?: IllegalAccessException::class.java.name)
        } catch (e: IllegalArgumentException) {
            Log.e(LOG_TAG, e.message ?: IllegalArgumentException::class.java.name)
        } catch (e: ExceptionInInitializerError) {
            Log.e(LOG_TAG, e.message ?: ExceptionInInitializerError::class.java.name)
        }
    }

fun <T, V> T.replaceField(cls: Class<*>, fieldName: String, value: V) {
    try {
        cls.getDeclaredField(fieldName).apply {
            isAccessible = true
            set(this@replaceField, value)
            Log.d(LOG_TAG, "Replaced successfully.")
        }
    } catch (e: NoSuchFieldException) {
        Log.e(LOG_TAG, e.message ?: NoSuchFieldException::class.java.name)
    } catch (e: NullPointerException) {
        Log.e(LOG_TAG, e.message ?: NullPointerException::class.java.name)
    } catch (e: SecurityException) {
        Log.e(LOG_TAG, e.message ?: SecurityException::class.java.name)
    } catch (e: IllegalAccessException) {
        Log.e(LOG_TAG, e.message ?: IllegalAccessException::class.java.name)
    } catch (e: IllegalArgumentException) {
        Log.e(LOG_TAG, e.message ?: IllegalArgumentException::class.java.name)
    } catch (e: ExceptionInInitializerError) {
        Log.e(LOG_TAG, e.message ?: ExceptionInInitializerError::class.java.name)
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T, V> T.getFieldObj(name: String) : V? =
    T::class.java.let {
        try {
            it.getDeclaredField(name).run {
                isAccessible = true
                get(this@getFieldObj) as V
            }
        } catch (e: NoSuchFieldException) {
            Log.e(LOG_TAG, e.message ?: NoSuchFieldException::class.java.name)
            null
        } catch (e: NullPointerException) {
            Log.e(LOG_TAG, e.message ?: NullPointerException::class.java.name)
            null
        } catch (e: SecurityException) {
            Log.e(LOG_TAG, e.message ?: SecurityException::class.java.name)
            null
        } catch (e: IllegalAccessException) {
            Log.e(LOG_TAG, e.message ?: IllegalAccessException::class.java.name)
            null
        } catch (e: IllegalArgumentException) {
            Log.e(LOG_TAG, e.message ?: IllegalArgumentException::class.java.name)
            null
        } catch (e: ExceptionInInitializerError) {
            Log.e(LOG_TAG, e.message ?: ExceptionInInitializerError::class.java.name)
            null
        }
    }

/**
 * Get the actual parameterized types of the superclass.
 */
inline fun <reified T> T.getSuperClassActualGenericTypes(): Array<Type> =
    T::class.java.genericSuperclass?.let {
        it as? ParameterizedType
    }?.actualTypeArguments ?: emptyArray()

fun Class<*>.toKotlinClass(): KClass<*> = Reflection.createKotlinClass(this)

/**
 * Get the actual parameterized types' map of the given type [T] instance.
 */
inline fun <reified T> T.getInterfacesActualGenericTypes(): Map<Type, Array<Type>> =
    T::class.java.genericInterfaces.filterIsInstance<ParameterizedType>()
        .associateWith { parameterizedType -> (parameterizedType as ParameterizedType).actualTypeArguments }