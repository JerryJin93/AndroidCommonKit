package com.jerryjin.kit.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 14:54
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
 * Description: Convenient functions for Activity.
 */
typealias GetExtras = () -> Bundle?
typealias ErrorCallback = (String?) -> Unit
typealias SetIntent = (Intent) -> Unit

inline fun <reified T : Activity> Activity.startActivity(
    extra: GetExtras = { null },
    error: ErrorCallback = {}
) = goToActivity<T>(extra, error)


inline fun <reified T : Activity> Context.goToActivity(
    extra: GetExtras = { null },
    error: ErrorCallback = {}
) {
    Intent(this, T::class.java).also {

        extra()?.apply {
            if (!isEmpty) {
                it.putExtras(this)
            }
        }

        if (this !is Activity)
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        try {
            startActivity(it)
        } catch (e: ActivityNotFoundException) {
            error(e.message)
        }
    }
}

inline fun Activity.startActivity(setIntent: SetIntent = {}, error: ErrorCallback = {}) =
    goToActivity(setIntent, error)

inline fun Context.goToActivity(
    setIntent: SetIntent = {},
    error: ErrorCallback = {}
) {
    Intent().apply {
        setIntent(this)
    }.also {
        if (this !is Activity)
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            startActivity(it)
        } catch (e: ActivityNotFoundException) {
            error(e.message)
        }
    }
}
