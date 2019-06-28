package com.jerryjin.kit.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;

/**
 * Author: Jerry
 * Generated at: 2019/5/2 14:21
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class BitmapHelper {

    private static final String TAG = "BitmapHelper";
    private static final boolean DEBUG = false;

    private static final Paint bitmapPaint;

    static {
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        return drawableToBitmap(drawable, TAG);
    }

    public static Bitmap drawableToBitmap(Drawable drawable, String logTag) {
        if (drawable == null) {
            Log.e(logTag, "Null given drawable.");
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Log.i(logTag, "width: " + width + ", height: " + height + ".");
            int opacity = drawable.getOpacity();
            Bitmap.Config config = opacity != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            Log.i(logTag, "bitmap config: " + config.toString());
            Bitmap outBitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(outBitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            return outBitmap;
        }
    }

    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        return bitmapToDrawable(context, bitmap, TAG);
    }

    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap, String logTag) {
        if (context == null) {
            Log.e(logTag, "Null given context.");
            return null;
        }
        if (bitmap == null) {
            Log.e(logTag, "Null given bitmap");
            return null;
        }
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static byte[] bitmapToBytes(Bitmap bitmap) {
        return bitmapToBytes(bitmap, TAG);
    }

    public static byte[] bitmapToBytes(Bitmap bitmap, String logTag) {
        if (bitmap == null) {
            Log.e(logTag, "Null given bitmap.");
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight) {
        return convertViewToBitmap(view, bitmapWidth, bitmapHeight, TAG);
    }

    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight, String logTag) {
        if (view == null) {
            Log.e(logTag, "Null given view.");
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }


    public static Bitmap convertViewToBitmap(View view) {
        return convertViewToBitmap(view, TAG);
    }

    public static Bitmap convertViewToBitmap(View view, String logTag) {
        if (view == null) {
            Log.e(logTag, "Null given view.");
            return null;
        }
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        return view.getDrawingCache();
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    // ok
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

}
