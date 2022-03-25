package com.jerryjin.kit.utils.kotlin_ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ScrollView
import com.jerryjin.kit.keyboard.SoftKeyboardHelper
import com.jerryjin.kit.keyboard.SoftKeyboardWatcher
import com.jerryjin.kit.utils.ScreenUtils

/**
 * Author: Jerry
 *
 * Created at: 2021/10/16 19:12
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
 * Description: Kotlin extensions for [View].
 */
fun View.touchDelegate(parent: View, setRect: View.(Rect) -> Unit) =
    post {
        Rect().apply {
            setRect(this)
            parent.touchDelegate = TouchDelegate(this, this@touchDelegate)
        }
    }

fun View.touchDelegate(
    parent: View,
    leftOffset: Int = 0,
    topOffset: Int = 0,
    rightOffset: Int = 0,
    bottomOffset: Int = 0,
) = touchDelegate(parent) { rect ->
    rect.left = this.left - leftOffset
    rect.top = this.top - topOffset
    rect.right = this.right - rightOffset
    rect.bottom = this.bottom - bottomOffset
}

/**
 * Convert the given Device Independent Pixel value in [Int] to pixels in [Float].
 */
val Int.dip
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

/**
 * Convert the given Device Independent Pixel value in [Int] to pixels in [Int].
 */
val Int.intDip
    get() = (dip + 0.5f).toInt()


/***
 * Convert the given scaled pixel value in [Int] to pixels in [Float].
 */
val Int.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

/**
 * Convert the given scaled pixel value in [Int] to pixels in [Int].
 */
val Int.intSp
    get() = (sp + 0.5f).toInt()

/**
 * Convert the given Device Independent Pixel value in [Float] to pixels in [Float].
 */
val Float.dip
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

/**
 * Convert the given Device Independent Pixel value in [Float] to pixels in [Int].
 */
val Float.intDip
    get() = (dip + 0.5f).toInt()

/***
 * Convert the given scaled pixel value in [Float] to pixels in [Float].
 */
val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )

/**
 * Convert the given scaled pixel value in [Float] to pixels in [Int].
 */
val Float.intSp
    get() = (sp + 0.5f).toInt()

fun Activity.getScreenSize() =
    ScreenUtils.getRealSize(this)

/**
 * Clear the focus of the [EditText] automatically when the soft keyboard is closed in [Activity].
 */
infix fun EditText.clearFocusAutomaticallyIn(activity: Activity) =
    SoftKeyboardWatcher(activity)
        .setKeyboardWatcherCallback(object : SoftKeyboardWatcher.EditTextCallbackImpl(this) {
            override fun onKeyboardClosed(ref: EditText) {
                ref.clearFocus()
            }
        }).observe()

/**
 * Close soft keyboard automatically.
 */
@SuppressLint("ClickableViewAccessibility")
fun EditText.autoCloseSoftKeyboard() = (parent as? ViewGroup)?.setOnTouchListener { v, _ ->
    SoftKeyboardHelper.closeSoftKeyboard(context, v)
    false
}

fun ScrollView.scrollBySmoothly(x: Int, y: Int) = post { scrollBy(x, y) }

fun ScrollView.scrollToSmoothly(x: Int, y: Int) = post { scrollTo(x, y) }

fun View.getLocationOnScreen(): IntArray = IntArray(2).apply {
    getLocationOnScreen(this)
}