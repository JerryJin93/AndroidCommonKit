package com.jerryjin.kit.keyboard;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.jerryjin.kit.navigationBar.NavigationBarHelper;

import java.lang.ref.WeakReference;

/**
 * Author: Jerry
 * Generated at: 2019/6/18 13:00
 * WeChat: enGrave93
 * Description:
 */
public class KeyboardWatcher {

    private static final String TAG = "KeyboardWatcher";
    private static final boolean DEBUG = false;

    private WeakReference<Activity> activityWeakReference;
    private boolean isNavBarShow;
    private int navBarHeight;
    private Callback mCallback;
    private boolean crossLock;

    public KeyboardWatcher(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
        initNecessaryParams();
    }

    private void initNecessaryParams() {
        if (activityWeakReference == null || activityWeakReference.get() == null) {
            throw new NullPointerException("Null Activity instance reference.");
        }
        Activity activity = activityWeakReference.get();
        isNavBarShow = NavigationBarHelper.isNavBarShow(activity);
        navBarHeight = NavigationBarHelper.getNavBarHeight(activity);
    }

    public void observe() {
        if (activityWeakReference == null || activityWeakReference.get() == null) {
            Log.e(TAG, "The given activity reference is null, we cannot get further info you need, please try it again.");
            return;
        }
        Activity activity = activityWeakReference.get();

        View root = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int keyboardHeight = getKeyboardHeight();
                if (DEBUG) {
                    Log.d(TAG, "keyboardHeight in raw pixels: " + keyboardHeight);
                }
                if (keyboardHeight == 0 && !crossLock) {
                    crossLock = true;
                    if (mCallback != null) {
                        mCallback.onKeyboardClosed();
                    }
                } else if (keyboardHeight > 0 && crossLock) {
                    crossLock = false;
                    if (mCallback != null) {
                        mCallback.onKeyboardOpened(keyboardHeight);
                    }
                }
            }
        });
    }

    private int getKeyboardHeight() {
        if (activityWeakReference == null || activityWeakReference.get() == null) {
            throw new NullPointerException("Null Activity object reference.");
        }
        Activity activity = activityWeakReference.get();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        WindowManager windowManager = activity.getWindowManager();
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        int screenHeight = point.y;
        int rest = isNavBarShow ? navBarHeight : 0;
        return screenHeight - rect.bottom - rest;

    }

    public KeyboardWatcher setKeyboardWatcherCallback(Callback mCallback) {
        this.mCallback = mCallback;
        return this;
    }

    public interface Callback {
        /**
         * Will be invoked when the keyboard is unfold.
         *
         * @param keyboardHeight The height of the unfold soft keyboard.
         */
        void onKeyboardOpened(int keyboardHeight);

        /**
         * Will be invoked when the keyboard is fold.
         */
        void onKeyboardClosed();
    }
}
