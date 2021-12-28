package com.jerryjin.kit.graphics.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jerryjin.kit.graphics.Shape;

/**
 * Author: Jerry
 * Generated at: 2019/7/2 17:18
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public abstract class ShapeView extends View implements Shape.Callback {

    private static final String TAG = "ShapeView";
    private static final boolean DEBUG = false;

    private Shape mShape;

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    protected abstract int measureWidth(int widthMeasureSpec);

    protected abstract int measureHeight(int heightMeasureSpec);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveCount = canvas.save();
        drawImpl(canvas);
        canvas.restoreToCount(saveCount);
    }

    /**
     * Real drawing procedures.
     *
     * @param canvas Canvas to hold draw calls.
     */
    protected void drawImpl(Canvas canvas) {
        // Highly not recommend create any object here.
        if (mShape != null) {
            mShape.draw(canvas);
        }
    }

    public final void setShape(Shape shape) {
        this.mShape = shape;
        // data binding
        mShape.setCallback(this);
        requestLayout();
    }

    @Override
    public void invalidateShape() {
        invalidate();
    }
}
