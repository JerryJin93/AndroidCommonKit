package com.jerryjin.kit.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;

/**
 * Author: Jerry
 * Generated at: 2019/6/24 10:19
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description: Dimension helper.
 */
@SuppressWarnings("WeakerAccess")
public class DimensionHelper {

    private static final String TAG = "DimensionHelper";
    private static final boolean DEBUG = false;
    private static final int ERROR_CODE = -1;

    /**
     * Convert dip to pixel.
     *
     * @param context The given context.
     * @param dp      The value to convert in dip.
     * @return Converted value.
     */
    public static int dp2px(Context context, float dp) {
        if (context == null) {
            Log.e(TAG, "Null given context, will return #ERROR_CODE.");
            return ERROR_CODE;
        }
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    /**
     * Convert pixel to dip.
     *
     * @param context The given context.
     * @param px      The value to convert in pixel.
     * @return Converted value.
     */
    public static int px2dp(Context context, float px) {
        if (context == null) {
            Log.e(TAG, "Null given context, will return #ERROR_CODE.");
            return ERROR_CODE;
        }
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * Convert scaled pixel to dip.
     *
     * @param context The given context.
     * @param sp      The value to convert in scaled pixel.
     * @return Converted value.
     */
    public static int sp2px(Context context, float sp) {
        if (context == null) {
            Log.e(TAG, "Null given context, will return #ERROR_CODE.");
            return ERROR_CODE;
        }
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    /**
     * Convert pixel to scaled pixel.
     *
     * @param context The given context.
     * @param px      The value to convert in pixel.
     * @return Converted value.
     */
    public static int px2sp(Context context, float px) {
        if (context == null) {
            Log.e(TAG, "Null given context, will return #ERROR_CODE.");
            return ERROR_CODE;
        }
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity + 0.5f);
    }

    /**
     * Get the size of current screen, in pixels.
     * <br/>
     * The decoration area's size of the current display is not included.
     *
     * @param activity The given activity.
     * @param size     The computed size.
     */
    public static void getScreenSize(Activity activity, Point size) {
        if (activity == null || size == null) {
            Log.e(TAG, "Null given parameters, skip.");
            return;
        }
        activity.getWindowManager().getDefaultDisplay().getSize(size);
    }

    /**
     * Get the real size of the current screen without subtracting any window decor or applying any compatibility scaled factors, in pixels.
     *
     * @param activity The given activity.
     * @param realSize The computed size.
     */
    public static void getRealScreenSize(Activity activity, Point realSize) {
        if (activity == null || realSize == null) {
            Log.e(TAG, "Null given parameters, skip.");
            return;
        }
        activity.getWindowManager().getDefaultDisplay().getRealSize(realSize);
    }

    /**
     * Get the width of the current screen, in pixels.
     * <br/>
     * The decoration area's width of the current display is not included.
     *
     * @param activity The given activity.
     * @return The computed width.
     */
    public static int getScreenWidth(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Null given activity, will return #ERROR_CODE.");
            return ERROR_CODE;
        }
        Point point = getInitializedPoint();
        getScreenSize(activity, point);
        return point.x;
    }

    /**
     * Get the real width of the current screen without subtracting any window or applying any compatibility scaled factors, in pixels.
     *
     * @param activity The given activity.
     * @return The real width of current screen.
     */
    public static int getRealScreenWidth(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Null given activity, will return #ERROR_CODE.");
            return ERROR_CODE;
        }
        Point point = getInitializedPoint();
        getRealScreenSize(activity, point);
        return point.x;
    }

    /**
     * Get the height of the current screen, in pixels.
     * <br/>
     * The decoration area's height of the current display is not included.
     *
     * @param activity The given activity.
     * @return The computed height.
     */
    public static int getScreenHeight(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Null given activity, will return #ERROR_CODE.");
            return ERROR_CODE;
        }
        Point point = getInitializedPoint();
        getScreenSize(activity, point);
        return point.y;
    }

    /**
     * Get the real height of the current screen without subtracting any window or applying any compatibility scaled factors, in pixels.
     *
     * @param activity The given activity.
     * @return The real height of the current screen.
     */
    public static int getRealScreenHeight(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Null given activity, will return #ERROR_CODE.");
            return ERROR_CODE;
        }
        Point point = getInitializedPoint();
        getRealScreenSize(activity, point);
        return point.y;
    }

    /**
     * Get initialized point object by using {@link #ERROR_CODE}.
     *
     * @return Initialized point object.
     */
    private static Point getInitializedPoint() {
        return new Point(ERROR_CODE, ERROR_CODE);
    }

}
