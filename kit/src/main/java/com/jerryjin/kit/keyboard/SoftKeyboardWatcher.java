package com.jerryjin.kit.keyboard;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.jerryjin.kit.utils.navigationBar.NavigationBarHelper;

import java.lang.ref.WeakReference;

/**
 * Author: Jerry
 * <p>
 * Generated at: 2019/6/18 13:00
 * <p>
 * GitHub: https://github.com/JerryJin93
 * <p>
 * Blog:
 * <p>
 * WeChat: AcornLake
 * <p>
 * Version: 1.0.2
 * <p>
 * Description: Supervisor of soft keyboard.
 */
public class SoftKeyboardWatcher {

    private static final String TAG = "SoftKeyboardWatcher";
    private static final boolean DEBUG = false;

    private final WeakReference<Activity> activityWeakReference;
    private boolean isNavBarShow;
    private int navBarHeight;
    private Callback mCallback;
    private boolean crossLock;

    public SoftKeyboardWatcher(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
        initNecessaryParams();
    }

    private void initNecessaryParams() {
        if (activityWeakReference == null || activityWeakReference.get() == null) {
            throw new NullPointerException("Null Activity instance reference.");
        }
        Activity activity = activityWeakReference.get();
        isNavBarShow = NavigationBarHelper.isNavBarShown(activity);
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
                int keyboardHeight = getSoftKeyboardHeight();
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

    private int getSoftKeyboardHeight() {
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

    public SoftKeyboardWatcher setKeyboardWatcherCallback(Callback callback) {
        this.mCallback = callback;
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

    public static abstract class CallbackImpl<T> implements Callback {

        private final WeakReference<T> weakRef;

        public CallbackImpl(WeakReference<T> weakRef) {
            this.weakRef = weakRef;
        }

        @Override
        public void onKeyboardOpened(int keyboardHeight) {
            T ref = weakRef.get();
            if (ref == null) return;
            onKeyboardOpened(keyboardHeight, ref);
        }

        @Override
        public void onKeyboardClosed() {
            T ref = weakRef.get();
            if (ref == null) return;
            onKeyboardClosed(ref);
        }

        public abstract void onKeyboardOpened(int keyboardHeight, @NonNull T ref);

        public abstract void onKeyboardClosed(@NonNull T ref);
    }

    public static abstract class EditTextCallbackImpl extends CallbackImpl<EditText> {

        public EditTextCallbackImpl(EditText editText) {
            super(new WeakReference<>(editText));
        }

        @Override
        public void onKeyboardOpened(int keyboardHeight, @NonNull EditText ref) {

        }

        @Override
        public void onKeyboardClosed(@NonNull EditText ref) {

        }
    }
}
