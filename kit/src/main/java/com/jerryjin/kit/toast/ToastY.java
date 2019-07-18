package com.jerryjin.kit.toast;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IntDef;

import com.jerryjin.kit.concurrent.ThreadHelper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Jerry
 * Generated at: 2019/7/18 15:05
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description: In memory of YCZ in high school.
 */
public class ToastY {

    private static final String TAG = "ToastY";
    private static final boolean DEBUG = false;
    private Application mContext;

    private ToastY() {
    }

    public static void init(Application context) {
        TYH.instance.mContext = context;
    }

    public static void show(CharSequence msg) {
        checkContext();
        ThreadHelper.runOnUiThread(() -> Toast.makeText(TYH.instance.mContext, msg, Toast.LENGTH_SHORT).show());
    }

    public static void show(CharSequence msg, @ToastYDuration int duration) {
        checkContext();
        ThreadHelper.runOnUiThread(() -> Toast.makeText(TYH.instance.mContext, msg, duration).show());
    }

    private static void checkContext() {
        if (TYH.instance.mContext == null) {
            Log.e(TAG, "Null context.");
            return;
        }
        Log.i(TAG, "Non null context.");
    }

    public static Toast makeToast(View contentView) {
        checkContext();
        Toast toast = new Toast(TYH.instance.mContext);
        toast.setView(contentView);
        return toast;
    }

    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @interface ToastYDuration {
    }

    private static class TYH {
        private static final ToastY instance = new ToastY();
    }

}
