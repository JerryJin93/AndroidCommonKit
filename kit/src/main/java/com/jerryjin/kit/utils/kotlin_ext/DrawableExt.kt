package com.jerryjin.kit.utils.kotlin_ext

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:06
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
 * Description: Utils for [Drawable].
 */
fun Drawable.toBitmap(): Bitmap =
    when (this) {
        is BitmapDrawable -> {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            bitmap
        }
        else -> {
            Bitmap.createBitmap(
                intrinsicWidth,
                intrinsicHeight,
                if (opacity == PixelFormat.OPAQUE)
                    Bitmap.Config.ARGB_8888
                else Bitmap.Config.RGB_565
            ).also {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                draw(Canvas(it))
            }
        }
    }

fun Drawable.zoom(width: Int, height: Int): Bitmap =
    Bitmap.createBitmap(
        toBitmap(),
        0,
        0,
        intrinsicWidth,
        intrinsicHeight,
        Matrix().apply {
            postScale(width * 1f / intrinsicWidth, height * 1f / intrinsicHeight)
        },
        true
    )
