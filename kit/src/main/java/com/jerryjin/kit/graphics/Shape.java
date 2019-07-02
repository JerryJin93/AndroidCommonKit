package com.jerryjin.kit.graphics;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import androidx.annotation.NonNull;

/**
 * Author: Jerry
 * Generated at: 2019/7/2 17:44
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version:
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public abstract class Shape {

    private static final String TAG = "Shape";
    private static final boolean DEBUG = false;

    private Callback mCallback;
    private RectF mContour = new RectF();
    private float mDegrees;

    public Shape() {
        init();
    }

    private void init() {
        getContourRect(mContour);
        if (DEBUG) {
            Log.d(TAG, "ShapeRect: " + mContour.toString());
        }
    }

    public final void draw(@NonNull Canvas canvas) {
        int saveCount = canvas.save();
        canvas.rotate(mDegrees, mContour.width() / 2f, mContour.height() / 2f);
        drawImpl(canvas);
        canvas.restoreToCount(saveCount);
    }

    protected abstract void drawImpl(@NonNull Canvas canvas);

    public abstract void getContourRect(RectF outRect);

    public void setDegrees(float degrees) {
        this.mDegrees = degrees;
        invalidateSelf();
    }

    public void setContour(RectF contour) {
        this.mContour = contour;
        invalidateSelf();
    }

    public final float getWidth() {
        if (mContour != null) {
            return mContour.width();
        } else {
            return 0;
        }
    }

    public final float getHeight() {
        if (mContour != null) {
            return mContour.height();
        } else {
            return 0;
        }
    }

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    public void invalidateSelf() {
        if (mCallback != null) {
            mCallback.invalidateShape();
        }
    }

    public interface Callback {

        /**
         * Called when the shape needs to be redrawn.
         */
        void invalidateShape();
    }
}
