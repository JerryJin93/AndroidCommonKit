package com.jerryjin.kit.concurrent;

import android.os.Handler;
import android.os.Looper;

/**
 * Author: Jerry
 * Generated at: 2019/7/18 13:31
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class ThreadHelper {

    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * Execute a {@link Runnable} object on UI thread.
     *
     * @param action The runnable to execute.
     */
    public static void runOnUiThread(Runnable action) {
        runOnUiThread(action, 0);
    }

    /**
     * Execute a {@link Runnable} object on UI Thread after the specific milliseconds of delay.
     *
     * @param action      The runnable to execute.
     * @param delayMillis The milliseconds to wait.
     */
    public static void runOnUiThread(Runnable action, int delayMillis) {
        mHandler.postDelayed(action, delayMillis);
    }

    public void runOnWorkerThread(Runnable action) {
        new Thread(action).start();
    }
}
