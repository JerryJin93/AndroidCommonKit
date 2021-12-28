package com.jerryjin.kit.utils

import android.util.Log

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