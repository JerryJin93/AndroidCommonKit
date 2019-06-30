package com.jerryjin.kit.math;

import android.graphics.Point;
import android.util.Log;

import androidx.annotation.Size;

/**
 * Author: Jerry
 * Generated at: 2019-06-28 23:46
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class MathHelper {

    private static final String TAG = "MathHelper";
    private static final boolean DEBUG = false;

    private static final int ERROR_CODE = -1;

    public static double computeDistance(@Size(2) double[] pointA, @Size(2) double[] pointB) {
        if (pointA == null || pointB == null) {
            Log.e(TAG, "Null given parameters.");
            return ERROR_CODE;
        }
        return computeSlope(pointA[0] - pointB[0], pointA[1] - pointB[1]);
    }

    public static int computeDistance(@Size(2) int[] pointA, @Size(2) int[] pointB) {
        if (pointA == null || pointB == null) {
            Log.e(TAG, "Null given parameters.");
            return ERROR_CODE;
        }
        return computeSlope(pointA[0] - pointB[0], pointA[1] - pointB[1]);
    }

    public static int computeDistance(Point pointA, Point pointB) {
        if (pointA == null || pointB == null) {
            Log.e(TAG, "Null given parameters.");
            return ERROR_CODE;
        }
        return computeSlope(pointA.x - pointB.x, pointA.y - pointB.y);
    }

    public static int computeSlope(int edgeA, int edgeB) {
        return (int) Math.sqrt(Math.pow(edgeA, 2) + Math.pow(edgeB, 2));
    }

    public static float computeSlope(float edgeA, float edgeB) {
        return (float) Math.sqrt(Math.pow(edgeA, 2) + Math.pow(edgeB, 2));
    }

    public static double computeSlope(double edgeA, double edgeB) {
        return Math.sqrt(Math.pow(edgeA, 2) + Math.pow(edgeB, 2));
    }
}
