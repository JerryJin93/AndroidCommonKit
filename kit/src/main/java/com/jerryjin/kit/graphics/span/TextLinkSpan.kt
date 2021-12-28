package com.jerryjin.kit.graphics.span

import android.text.Selection
import android.text.Spannable
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 14:52
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
 * Description: Encapsulated class for [ClickableSpan].
 */
class TextLinkSpan(configBlock: TextLinkSpanConfig.() -> Unit) : ClickableSpan() {

    private val mConfig by lazy {
        TextLinkSpanConfig()
    }

    init {
        configBlock(mConfig)
    }

    override fun updateDrawState(ds: TextPaint) =
        mConfig.let {
            if (!it.totallyOverrideTextPaintSetting)
                super.updateDrawState(ds)
            if (!it.isTextPaintInitialized())
                return
            it.lazyTextPaint(ds)
        }


    override fun onClick(widget: View) =
        mConfig.let {
            if (widget is TextView && it.needCancelSelectionAfterClick)
                (widget.text as Spannable).also { spannable ->
                    // Let's remove the selection
                    Selection.setSelection(spannable, 0)
                }
            if (!it.isListenerInitialized())
                return
            it.listener(widget)
        }
}

/**
 * Configuration for [TextLinkSpan].
 */
class TextLinkSpanConfig {
    var needCancelSelectionAfterClick = true
    var totallyOverrideTextPaintSetting = true

    // use lambda to avoid anonymous class object instantiating
    // so that the code will be more concise
    lateinit var listener: (View) -> Unit
    lateinit var lazyTextPaint: (TextPaint) -> Unit

    internal fun isListenerInitialized() = ::listener.isInitialized
    internal fun isTextPaintInitialized() = ::lazyTextPaint.isInitialized
}