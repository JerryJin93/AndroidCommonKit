package com.jerryjin.kit.graphics.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.ViewCompat
import kotlin.math.min

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 14:49
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
 * Description: Shadow drawable.
 */
open class ShadowDrawable private constructor() : Drawable() {

    private val getPaint = {
        Paint().apply {
            isAntiAlias = true
            isDither = true
        }
    }

    private val mPaint by lazy { getPaint() }

    private val mShadowPaint by lazy { getPaint() }

    private val mRect by lazy { RectF() }

    private var mShape: Modifier.Shape = Modifier.Shape.RoundRect

    private var mShadowRadius = 0f
    private var mShadowColor = Color.TRANSPARENT
    private var mShapeRadius = 0f
    private var mOffsetX = 0f
    private var mOffsetY = 0f
    private var mGradientOrientation: Modifier.GradientOrientation =
        Modifier.GradientOrientation.Vertical
    private var mBgColor: IntArray? = null
    private var mShaderHook: (() -> Shader)? = null

    constructor(init: Modifier.() -> Unit) : this() {
        Modifier().run {
            init()
            mShape = shape
            mShadowRadius = shadowRadius
            mShapeRadius = shapeRadius
            mOffsetX = offsetX
            mOffsetY = offsetY
            mGradientOrientation = gradientOrientation
            mBgColor = bgColor
            mShaderHook = shaderHook
        }
    }

    init {
        mPaint.run {
            color = Color.TRANSPARENT
            setShadowLayer(mShadowRadius, mOffsetX, mOffsetY, mShadowColor)
            setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_ATOP))
        }
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        mRect.set(
            left + mShadowRadius - mOffsetX,
            top + mShadowRadius - mOffsetY,
            right - mShadowRadius - mOffsetX,
            bottom - mShadowRadius - mOffsetY
        )
    }

    override fun draw(canvas: Canvas) {
        mBgColor?.run {
            if (size == 1)
                mShadowPaint.color = this[0]
            else {
                mShadowPaint.setShader(
                    if (mShaderHook == null) when (mGradientOrientation) {
                        Modifier.GradientOrientation.Horizontal -> {
                            LinearGradient(
                                mRect.left, mRect.height() / 2, mRect.right, mRect.height() / 2,
                                this,
                                null, Shader.TileMode.CLAMP
                            )
                        }
                        Modifier.GradientOrientation.Vertical -> {
                            LinearGradient(
                                mRect.left / 2, mRect.top, mRect.left / 2, mRect.bottom,
                                this,
                                null, Shader.TileMode.CLAMP
                            )
                        }
                    }
                    else mShaderHook!!()
                )
            }
        }

        when (mShape) {
            Modifier.Shape.Circle -> {
                canvas.drawCircle(
                    mRect.centerX(),
                    mRect.centerY(),
                    min(mRect.width(), mRect.height()) / 2,
                    mPaint
                )
                canvas.drawCircle(
                    mRect.centerX(),
                    mRect.centerY(),
                    min(mRect.width(), mRect.height()) / 2,
                    mShadowPaint
                )
            }
            Modifier.Shape.RoundRect -> {
                canvas.drawRoundRect(mRect, mShapeRadius, mShapeRadius, mPaint)
                canvas.drawRoundRect(mRect, mShapeRadius, mShapeRadius, mShadowPaint)
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    class Modifier {
        sealed class Shape {
            object Circle : Shape()
            object RoundRect : Shape()
        }

        sealed class GradientOrientation {
            object Horizontal : GradientOrientation()
            object Vertical : GradientOrientation()
        }

        var shape = Shape.RoundRect
        var shadowRadius = 0f
        var shapeRadius = 0f
        var offsetX = 0f
        var offsetY = 0f

        var gradientOrientation = GradientOrientation.Vertical
        var bgColor: IntArray? = null

        var shaderHook: (() -> Shader)? = null
    }
}

infix fun View.renderShadow(initBlock: ShadowDrawable.Modifier.() -> Unit) {
    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    ViewCompat.setBackground(this, ShadowDrawable(initBlock))
}