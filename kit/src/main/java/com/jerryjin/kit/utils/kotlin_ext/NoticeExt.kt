@file:Suppress("SpellCheckingInspection")

package com.jerryjin.kit.utils.kotlin_ext

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 14:59
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
 * Description: Extensions for [Toast] and [Snackbar].
 */
fun Context.toast(content: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, content, duration).show()

fun View.showSnackbar(
    content: CharSequence,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionText: CharSequence?,
    actionListener: (View) -> Unit = {}
) =
    showSnackbar(content, duration) {
        it.setAction(actionText, actionListener)
    }

fun View.showSnackbar(
    content: CharSequence,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionOption: (Snackbar) -> Unit = {}
) =
    Snackbar.make(this, content, duration).apply {
        actionOption(this)
    }.show()