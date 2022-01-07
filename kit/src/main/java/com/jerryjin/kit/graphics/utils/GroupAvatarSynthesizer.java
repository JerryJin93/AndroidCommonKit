package com.jerryjin.kit.graphics.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.jerryjin.kit.utils.ViewUtils;
import com.jerryjin.kit.utils.log.LogPriority;
import com.jerryjin.kit.utils.log.SimpleLogger;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Author: Jerry
 * <br/>
 * Created at: 2022/01/07 9:35
 * <br/>
 * GitHub: https://github.com/JerryJin93
 * <br/>
 * Blog:
 * <br/>
 * Email: jerry93@foxmail.com
 * <br/>
 * WeChat: AcornLake
 * <br/>
 * Version: 1.0.0
 * <br/>
 * Description: Just as its name depicts, synthesizer for group avatar.
 */
public final class GroupAvatarSynthesizer {

    private static final String TAG = "GroupAvatarSynthesizer";

    private static final int MAX_COUNT_PER_ROW = 3;
    private static final int MAX_COUNT_PER_COLUMN = 3;
    private static final int MAX_DISPLAY_COUNT = MAX_COUNT_PER_ROW * MAX_COUNT_PER_COLUMN;

    private static final int DEFAULT_MARGINS = 10;
    private static final int DEFAULT_PADDINGS = 10;
    private static final int DEFAULT_SIZE_IN_DP = 50;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#FFe1e2e3");

    private final WeakReference<Context> contextWeakReference;
    private final int size;
    private final int bgColor;
    private final int margins;
    private final int paddings;
    private final List<Bitmap> bitmaps;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Matrix matrix = new Matrix();

    private GroupAvatarSynthesizer(Builder builder) {
        this.contextWeakReference = builder.contextWeakReference;
        this.size = contextWeakReference == null ? 0
                : (int) ViewUtils.dp2px(builder.size * 1f, contextWeakReference.get());
        this.bgColor = builder.bgColor;
        this.margins = builder.margins;
        this.paddings = builder.paddings;
        this.bitmaps = builder.bitmaps;
        mPaint.setDither(true);
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static class Builder {

        private final WeakReference<Context> contextWeakReference;
        private int size = DEFAULT_SIZE_IN_DP;
        private int bgColor = DEFAULT_BACKGROUND_COLOR;
        private int margins = DEFAULT_MARGINS;
        private int paddings = DEFAULT_PADDINGS;
        private List<Bitmap> bitmaps;

        public Builder(Context context) {
            this.contextWeakReference = new WeakReference<>(context);
        }

        private Builder(GroupAvatarSynthesizer synthesizer) {
            this.contextWeakReference = synthesizer.contextWeakReference;
            this.size = synthesizer.size;
            this.bgColor = synthesizer.bgColor;
            this.margins = synthesizer.margins;
            this.paddings = synthesizer.paddings;
            this.bitmaps = synthesizer.bitmaps;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.bgColor = backgroundColor;
            return this;
        }

        public Builder setMargins(int margins) {
            this.margins = margins;
            return this;
        }

        public Builder setPaddings(int paddings) {
            this.paddings = paddings;
            return this;
        }

        public Builder setBitmaps(List<Bitmap> bitmaps) {
            this.bitmaps = bitmaps;
            return this;
        }

        public GroupAvatarSynthesizer build() {
            return new GroupAvatarSynthesizer(this);
        }
    }

    /**
     * Combine a list of avatar bitmaps to a single one.
     * <br/>
     * Group avatar pattern is as below, the plus in which represents an avatar.
     * <br/>
     * <pre>
     *     1 ->
     *          +
     *
     *     2 ->
     *          + +
     *
     *     3 ->
     *           +
     *          + +
     *
     *     4 ->
     *          + +
     *          + +
     *
     *     5 ->
     *           + +
     *          + + +
     *
     *     6 ->
     *          + + +
     *          + + +
     *
     *     7 ->
     *            +
     *          + + +
     *          + + +
     *
     *     8 ->
     *           + +
     *          + + +
     *          + + +
     *
     *     9 & bigger ->
     *          + + +
     *          + + +
     *          + + +
     * </pre>
     *
     * @param canvas           Canvas
     * @param memberAvatarSize Each member's avatar size.
     * @param memberCount      Group member count.
     */
    private void drawBitmaps(Canvas canvas, int memberAvatarSize, int memberCount) {
        if (memberCount <= 0) return;
        canvas.save();

        boolean fullyStretched = memberCount >= MAX_DISPLAY_COUNT || Math.sqrt(memberCount) % 1 == 0;
        final int rows = memberCount > 6 ? 3 : (memberCount > 2 ? 2 : 1);
        final int maxColumns = memberCount > 4 ? 3 : (memberCount > 1 ? 2 : 1);
        boolean hasVerticalSpace = rows != maxColumns;

        int remnant = memberCount > MAX_DISPLAY_COUNT ? 0 : memberCount % maxColumns;
        int nextLeft = remnant == 0 ? paddings : ((size - remnant * memberAvatarSize - (remnant - 1) * margins) / 2);
        int nextTop = fullyStretched || !hasVerticalSpace ? paddings :
                paddings + (size - paddings * 2 - rows * memberAvatarSize - (rows - 1) * margins) / 2;
        for (int i = 0; i < memberCount; i++) {
            Bitmap bitmap = bitmaps.get(i);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            matrix.reset();
            matrix.postScale(memberAvatarSize * 1f / w, memberAvatarSize * 1f / h);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, false);

            canvas.drawBitmap(bitmap, nextLeft, nextTop, mPaint);

            final boolean shouldNewLine = letMagicDecide(i, maxColumns, remnant);
            SimpleLogger.print(
                    LogPriority.DEBUG,
                    TAG, "index: " + i + ", columns: " + maxColumns
                            + ", remnant: " + remnant + ", shouldNewLine: "
                            + shouldNewLine);
            if (shouldNewLine) {
                nextTop += memberAvatarSize + margins;
                nextLeft = paddings;
            } else {
                nextLeft += memberAvatarSize + margins;
            }
            if (i + 1 == MAX_DISPLAY_COUNT) break;
        }
        canvas.restore();
    }

    /**
     * Determine whether to start a new line or not.
     *
     * @param index      current index.
     * @param maxColumns calculated max columns
     * @param remnant    odd avatar.
     * @return True if should start a new line, false otherwise.
     */
    private boolean letMagicDecide(int index, int maxColumns, int remnant) {
        return remnant == 0 ? index > 0 && (index + 1) % maxColumns == 0
                : index + 1 == remnant || (index + 1 > remnant && (index + 1 - remnant) % maxColumns == 0);
    }

    public Bitmap generateGroupAvatar() {
        Bitmap groupAvatarBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(groupAvatarBitmap);
        canvas.drawColor(bgColor);

        int elementSize;
        int bitmapCount = bitmaps == null ? 0 : bitmaps.size();
        // measure size.
        if (bitmapCount == 0 || bitmapCount == 1) {
            elementSize = size - paddings * 2;
        } else if (bitmapCount <= 4) {
            elementSize = (size - paddings * 2 - margins) / 2;
        } else {
            elementSize = (size - (paddings + margins) * 2) / MAX_COUNT_PER_ROW;
        }
        if (bitmapCount > 0) drawBitmaps(canvas, elementSize, bitmapCount);
        return groupAvatarBitmap;
    }
}
