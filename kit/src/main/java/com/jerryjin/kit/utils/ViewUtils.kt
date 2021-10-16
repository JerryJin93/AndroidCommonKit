package com.jerryjin.kit.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager

/**
 * Author: Jerry
 *
 * Created at: 2021/10/16 19:20
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
 * Description: Utility class for [View] related use.
 */
class ViewUtils {

    companion object {

        /**
         * Get the [DisplayMetrics] object according to the given [Context].
         */
        @JvmStatic
        fun getDisplayMetrics(context: Context) =
            DisplayMetrics().apply {
                (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.defaultDisplay?.getRealMetrics(
                    this
                )
            }

        /**
         * Convert value in the form of Device Independent Pixel to that in pixel.
         *
         * @param dp value in dip
         * @param context the context
         *
         * @return result value in pixel(s).
         */
        @JvmStatic
        fun dp2px(dp: Float, context: Context): Float =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getDisplayMetrics(context))

        /**
         * Convert value in the form of pixel to that in dip.
         *
         * @param px value in pixel(s)
         * @param context the context
         *
         * @return result value in dip(s).
         */
        @JvmStatic
        fun px2dp(px: Float, context: Context): Float =
            px / getDisplayMetrics(context).density

        /**
         * Convert value in the form of scaled pixel to that in pixel.
         *
         * @param sp value in scaled pixel(s)
         * @param context the context
         *
         * @return result value in pixel(s).
         */
        @JvmStatic
        fun sp2px(sp: Float, context: Context): Float =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getDisplayMetrics(context))

        /**
         * Convert value in the form of pixel to that in scaled pixel.
         *
         * @param px value in pixel(s)
         * @param context the context
         *
         * @return result value in scaled pixel(s).
         */
        @JvmStatic
        fun px2sp(px: Float, context: Context): Float =
            px / getDisplayMetrics(context).scaledDensity

    }
}