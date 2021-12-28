package com.jerryjin.kit.graphics.view.container;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.customview.widget.ViewDragHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Jerry
 * Generated at: 2019/7/12 17:36
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public class DragLayout extends FrameLayout {

    private static final String TAG = "DragLayout";
    private static final boolean DEBUG = false;

    private ViewDragHelper dragHelper;
    private View[] targets;
    private boolean needAdjustLayout;
    private Map<View, Point> viewPointMap = new HashMap<>();
    private ViewDragHelperCreateCallback createCallback;

    public DragLayout(@NonNull Context context) {
        super(context);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
    }

    private void init() {
        ViewDragHelper.Callback callback = new DragCallback();
        dragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        adjustChildren();
    }

    protected void adjustChildren() {
        if (targets == null || targets.length == 0) {
            Log.e(TAG, "No targets, skip...");
            return;
        }
        if (needAdjustLayout) {
            for (View target : targets) {
                Point point = viewPointMap.get(target);
                if (point != null) {
                    target.layout(point.x, point.y, point.x + target.getWidth(), point.y + target.getHeight());
                }
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            dragHelper.cancel();
            return false;
        }
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    public void setTargets(View... targets) {
        this.targets = targets;
    }

    private boolean isSuitable(View view) {
        if (targets == null || targets.length == 0) {
            Log.e(TAG, "No targets, skip...");
            return false;
        }
        for (View v : targets) {
            if (v == view) {
                return true;
            }
        }
        return false;
    }

    public void setCreateCallback(ViewDragHelperCreateCallback createCallback) {
        this.createCallback = createCallback;
    }

    public interface ViewDragHelperCreateCallback {
        int clampViewPositionHorizontal(@NonNull View child, int left, int dx);

        int clampViewPositionVertical(@NonNull View child, int top, int dy);

        void onEdgeTouched(int edgeFlags, int pointerId);

        boolean onEdgeLocked(int edgeFlags);

        void onEdgeDragStarted(int edgeFlags, int pointerId);

        int getOrderedChildIndex(int index);

        int getViewHorizontalDragRange(@NonNull View child);

        int getViewVerticalDragRange(@NonNull View child);

        void onViewCaptured(@NonNull View capturedView, int activePointerId);

        void onViewReleased(@NonNull View releasedChild, float xvel, float yvel);

        void onViewDragStateChanged(int state);
    }

    public static abstract class ViewDragHelperCreateCallbackImp implements ViewDragHelperCreateCallback {
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return 0;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
        }

        @Override
        public boolean onEdgeLocked(int edgeFlags) {
            return false;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return index;
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return 0;
        }

        @Override
        public void onViewCaptured(@NonNull View capturedView, int activePointerId) {
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
        }

        @Override
        public void onViewDragStateChanged(int state) {
        }
    }

    private class DragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View view, int pointerId) {
            return isSuitable(view);
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            if (createCallback != null) {
                return createCallback.clampViewPositionHorizontal(child, left, dx);
            }
            int leftBound = getPaddingLeft();
            int rightBound = getWidth() - child.getWidth() - leftBound;
            return Math.min(Math.max(left, leftBound), rightBound);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (createCallback != null) {
                return createCallback.clampViewPositionVertical(child, top, dy);
            }
            int topBound = getPaddingTop();
            int bottomBound = getHeight() - child.getHeight() - topBound;
            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (createCallback != null) {
                createCallback.onViewReleased(releasedChild, xvel, yvel);
            }
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            if (createCallback != null) {
                createCallback.onViewCaptured(capturedChild, activePointerId);
            }
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            if (createCallback != null) {
                createCallback.onEdgeTouched(edgeFlags, pointerId);
            }
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            if (createCallback != null) {
                return createCallback.onEdgeLocked(edgeFlags);
            }
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            if (createCallback != null) {
                createCallback.onEdgeDragStarted(edgeFlags, pointerId);
            }
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            if (createCallback != null) {
                return createCallback.getOrderedChildIndex(index);
            }
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            if (createCallback != null) {
                return createCallback.getViewHorizontalDragRange(child);
            }
            return child.getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            if (createCallback != null) {
                return createCallback.getViewVerticalDragRange(child);
            }
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (createCallback != null) {
                createCallback.onViewDragStateChanged(state);
            }
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            viewPointMap.put(changedView, new Point(left, top));
            needAdjustLayout = true;
        }
    }
}
