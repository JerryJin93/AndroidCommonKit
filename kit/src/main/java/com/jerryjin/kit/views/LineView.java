package com.jerryjin.kit.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.jerryjin.kit.R;
import com.jerryjin.kit.graphics.DimensionHelper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Jerry
 * Generated at: 2019-07-02 21:57
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 0.0.1
 * Description:
 */
public class LineView extends View {

    private static final String TAG = "LineView";
    private static final boolean DEBUG = false;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_DASH = 1;

    private static final int NO_DASH_HOLDER = -1;
    private static final int DEFAULT_DASH_WIDTH = 10;
    private static final float DEFAULT_DASH_WIDTH_TO_DASH_GAP_RATIO = 2f;

    public static final int LINE_DIRECTION_PORTRAIT = 0;
    public static final int LINE_DIRECTION_LANDSCAPE = 1;

    public static final int LINE_GRAVITY_LEFT = 0;
    public static final int LINE_GRAVITY_TOP = 1;
    public static final int LINE_GRAVITY_RIGHT = 2;
    public static final int LINE_GRAVITY_BOTTOM = 3;
    public static final int LINE_GRAVITY_CENTER = 4;
    public static final int LINE_GRAVITY_CENTER_HORIZONTAL = 5;
    public static final int LINE_GRAVITY_CENTER_VERTICAL = 6;

    private static final int DEFAULT_LINE_COLOR = Color.parseColor("#C4C4C4");

    private Paint mPaint;
    @LineType
    private int mLineType;
    private int mDashWidth;
    private float mDashWidthToDashGapRatio;
    private int mLineColor;
    private int mLineDirection;
    private int mLineWidth;
    @LineGravity
    private int mLineGravity;
    private int mLineOffsetX;
    private int mLineOffsetY;
    private Path mPath = new Path();
    private PathEffect mPathEffect;


    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineView);
            mLineType = ta.getInt(R.styleable.LineView_lineType, TYPE_NORMAL);
            mLineDirection = ta.getInt(R.styleable.LineView_lineDirection, LINE_DIRECTION_LANDSCAPE);
            if (mLineType == TYPE_NORMAL) {
                mDashWidth = NO_DASH_HOLDER;
                mDashWidthToDashGapRatio = NO_DASH_HOLDER;
            } else {
                mDashWidth = ta.getDimensionPixelSize(R.styleable.LineView_dashWidth, DEFAULT_DASH_WIDTH);
                mDashWidthToDashGapRatio = ta.getFloat(R.styleable.LineView_dashRatio, DEFAULT_DASH_WIDTH_TO_DASH_GAP_RATIO);
            }
            mLineGravity = ta.getInt(R.styleable.LineView_lineGravity, -1);
            mLineWidth = ta.getDimensionPixelSize(R.styleable.LineView_lineWidth, 1);
            mLineColor = ta.getColor(R.styleable.LineView_lineColor, DEFAULT_LINE_COLOR);
            mLineOffsetX = ta.getDimensionPixelSize(R.styleable.LineView_lineOffsetX, 0);
            mLineOffsetY = ta.getDimensionPixelSize(R.styleable.LineView_lineOffsetY, 0);
            ta.recycle();
        } else {
            mLineType = TYPE_NORMAL;
            mLineDirection = LINE_DIRECTION_LANDSCAPE;
            mDashWidth = NO_DASH_HOLDER;
            mDashWidthToDashGapRatio = NO_DASH_HOLDER;
            mLineWidth = 1;
            mLineColor = DEFAULT_LINE_COLOR;
            mLineOffsetX = 0;
            mLineOffsetY = 0;
        }
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(mLineWidth);
        if (mLineType == TYPE_DASH) {
            Log.i(TAG, "About to set path effect...");
            setPathEffect();
        }
        if (DEBUG) {
            Log.d(TAG, "...to do...");
        }
    }

    private void setPathEffect() {
        mPathEffect = new DashPathEffect(new float[]{mDashWidth, mDashWidth / mDashWidthToDashGapRatio}, 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        if (specMode == MeasureSpec.EXACTLY) {
            width = specSize + getPaddingLeft() + getPaddingRight();
        } else {
            int size;
            if (mLineDirection == LINE_DIRECTION_LANDSCAPE) {
                size = DimensionHelper.dp2px(getContext(), 100);
            } else {
                size = DimensionHelper.dp2px(getContext(), 1);
            }
            width = size + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                width = Math.min(specSize, width);
            }
        }
        if (DEBUG) {
            Log.d(TAG, "measured width: " + width);
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize + getPaddingTop() + getPaddingBottom();
        } else {
            int size;
            if (mLineDirection == LINE_DIRECTION_LANDSCAPE) {
                size = DimensionHelper.dp2px(getContext(), 1);
            } else {
                size = DimensionHelper.dp2px(getContext(), 100);
            }
            height = size + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                height = Math.min(specSize, height);
            }
        }
        if (DEBUG) {
            Log.d(TAG, "measured height: " + height);
        }
        return height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveCount = canvas.save();
        drawLine(canvas);
        canvas.restoreToCount(saveCount);
    }

    private void drawLine(Canvas canvas) {
        float x, y;
        int width = getWidth();
        int height = getHeight();
        switch (mLineGravity) {
            case LINE_GRAVITY_CENTER_HORIZONTAL:
                x = width / 2f;
                y = 0;
                break;
            case LINE_GRAVITY_CENTER_VERTICAL:
                x = 0;
                y = height / 2f;
                break;
            case LINE_GRAVITY_CENTER:
                x = width / 2f;
                y = height / 2f;
                break;
            default:
                x = y = 0;
                break;
        }
        // There are only two kinds of direction of line.
        if (mLineDirection == LINE_DIRECTION_PORTRAIT) {
            if (mLineType == TYPE_NORMAL) {
                canvas.drawLine(x + mLineOffsetX, y + mLineOffsetY, x + mLineOffsetX, height, mPaint);
            } else {
                mPath.moveTo(x + mLineOffsetX, y + mLineOffsetY);
                mPath.lineTo(x + mLineOffsetX, height);
                mPaint.setPathEffect(mPathEffect);
                canvas.drawPath(mPath, mPaint);
            }
        } else {
            if (mLineType == TYPE_NORMAL) {
                canvas.drawLine(x + mLineOffsetX, y + mLineOffsetY, x + mLineOffsetX, height, mPaint);
            } else {
                mPath.moveTo(x + mLineOffsetX, y + mLineOffsetY);
                mPath.lineTo(width, y + mLineOffsetY);
                mPaint.setPathEffect(mPathEffect);
                canvas.drawPath(mPath, mPaint);
            }
        }
    }

    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
        invalidate();
    }

    @SuppressWarnings("WeakerAccess")
    @IntDef({TYPE_NORMAL, TYPE_DASH})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface LineType {
    }

    @SuppressWarnings("WeakerAccess")
    @IntDef({LINE_GRAVITY_LEFT, LINE_GRAVITY_TOP, LINE_GRAVITY_RIGHT, LINE_GRAVITY_BOTTOM,
            LINE_GRAVITY_CENTER, LINE_GRAVITY_CENTER_HORIZONTAL, LINE_GRAVITY_CENTER_VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface LineGravity {
    }

}
