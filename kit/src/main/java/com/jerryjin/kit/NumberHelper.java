package com.jerryjin.kit;

import android.text.TextUtils;
import android.util.Log;

/**
 * Author: Jerry
 * Generated at: 2019-07-09 20:54
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public class NumberHelper {

    private static final String TAG = "NumberHelper";
    private static final boolean DEBUG = false;

    public static boolean containsNumber(String string) {
        if (TextUtils.isEmpty(string)) {
            Log.e(TAG, "Empty string, return false.");
            return false;
        }
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (c >= 48 && c <=57) {
                return true;
            }
        }
        return false;
    }
}
