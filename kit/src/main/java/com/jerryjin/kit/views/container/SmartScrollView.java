package com.jerryjin.kit.views.container;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ScrollView;

/**
 * Author: Jerry
 * Generated at: 2019/7/6 10:39
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public class SmartScrollView extends ScrollView {

    private static final String TAG = "SmartScrollView";
    private static final boolean DEBUG = false;

    private boolean isScrolledToTop = true;
    private boolean isScrolledToBottom;

    /**
     * Scroll down gesture. And the previously invisible content will enter in screen.
     */
    private boolean isScrollDown;
    /**
     * Scroll up gesture. And the previously visible content will be off the screen.
     */
    private boolean isScrollUp;

    private VelocityTracker tracker;

    private OnScrollStateChangedListener listener1;
    private OnScrollDirectionChangedListener listener2;

    public SmartScrollView(Context context) {
        this(context, null);
    }

    public SmartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmartScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        tracker = VelocityTracker.obtain();
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            isScrolledToTop = true;
            isScrolledToBottom = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
        }
        notifyScrollStateChanged();
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            if (getScrollY() == 0) {
                isScrolledToTop = true;
                isScrolledToBottom = false;
            } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
                isScrolledToBottom = true;
                isScrolledToTop = false;
            } else {
                isScrolledToTop = false;
                isScrolledToBottom = false;
            }
            notifyScrollStateChanged();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        tracker.addMovement(ev);
        tracker.computeCurrentVelocity(1000);
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            isScrollDown = tracker.getYVelocity() > 0;
            isScrollUp = tracker.getYVelocity() < 0;
            if (listener1 != null) {
                if (isScrolledToBottom && isScrollUp) {
                    listener1.onOverScrollDown();
                }
                if (isScrolledToTop && isScrollDown) {
                    listener1.onOverScrollUp();
                }
            }
            if (listener2 != null) {
                if (isScrollDown) {
                    listener2.onScrollDown();
                } else if (isScrollUp) {
                    listener2.onScrollUp();
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    public boolean isScrolledToBottom() {
        return isScrolledToBottom;
    }

    public boolean isScrolledToTop() {
        return isScrolledToTop;
    }

    public boolean isScrollDown() {
        return isScrollDown;
    }

    public boolean isScrollUp() {
        return isScrollUp;
    }

    public void setOnScrollStateChangedListener(OnScrollStateChangedListener listener) {
        this.listener1 = listener;
    }

    public void setOnScrollDirectionChangedListener(OnScrollDirectionChangedListener onScrollDirectionChangedListener) {
        this.listener2 = onScrollDirectionChangedListener;
    }

    private void notifyScrollStateChanged() {
        if (listener1 != null) {
            if (isScrolledToTop) {
                listener1.onScrolledToTop();
            }
            if (isScrolledToBottom) {
                listener1.onScrolledToBottom();
            }
        }
    }

    public interface OnScrollStateChangedListener {
        void onScrolledToTop();

        void onScrolledToBottom();

        void onOverScrollUp();

        void onOverScrollDown();
    }

    public interface OnScrollDirectionChangedListener {
        void onScrollDown();

        void onScrollUp();
    }

    public abstract class OnScrollStateChangedImpl implements OnScrollStateChangedListener {
        @Override
        public void onScrolledToTop() {

        }

        @Override
        public void onScrolledToBottom() {

        }

        @Override
        public void onOverScrollUp() {

        }

        @Override
        public void onOverScrollDown() {

        }
    }

    public abstract class OnScrollDirectionChangedListenerImpl implements OnScrollDirectionChangedListener {
        @Override
        public void onScrollDown() {

        }

        @Override
        public void onScrollUp() {

        }
    }
}
