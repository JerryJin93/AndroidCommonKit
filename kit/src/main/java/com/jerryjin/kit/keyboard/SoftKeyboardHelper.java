package com.jerryjin.kit.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.jerryjin.kit.utils.navigationBar.NavigationBarHelper;

/**
 * Author: Jerry
 * <p>
 * Generated at: 2019-07-05 22:26
 * <p>
 * GitHub: https://github.com/JerryJin93
 * <p>
 * Blog:
 * <p>
 * WeChat: AcornLake
 * <p>
 * Version: 1.0.0
 * <p>
 * Description: Helper class for soft keyboard.
 */
@SuppressWarnings("WeakerAccess")
public class SoftKeyboardHelper {

    private static final String TAG = "SoftKeyboardHelper";
    private static final boolean DEBUG = false;

    public static final int ERROR_CODE = -1;

    /**
     * Open soft keyboard.
     * @param context The context for getting {@link InputMethodManager} object.
     * @param anchor The currently focused view, which would like to receive soft keyboard input.
     */
    public static void openSoftKeyboard(Context context, View anchor) {
        if (context == null) {
            Log.e(TAG, "Null given context, skipping...");
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(anchor, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void closeSoftKeyboard(Context context, View anchor) {
        if (context == null) {
            Log.e(TAG, "Null given context, skipping...");
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(anchor.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static int getSoftKeyboardHeight(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Null given activity, skipping...");
            return ERROR_CODE;
        }
        Rect rect = new Rect();
        boolean isNavBarShow = NavigationBarHelper.isNavBarShown(activity);
        int navBarHeight = NavigationBarHelper.getNavBarHeight(activity);

        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        WindowManager windowManager = activity.getWindowManager();
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        int screenHeight = point.y;
        int rest = isNavBarShow ? navBarHeight : 0;
        return screenHeight - rect.bottom - rest;
    }
}
