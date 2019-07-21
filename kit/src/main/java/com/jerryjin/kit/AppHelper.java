package com.jerryjin.kit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

/**
 * Author: Jerry
 * Generated at: 2019/5/8 16:39
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class AppHelper {

    private static final String TAG = "AppHelper";
    private static final boolean DEBUG = false;

    private static final int ERROR_CODE = -1;

    public synchronized static String getAppName(Context context) {
        PackageManager packageManager = getPackageManager(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized static String getVersionName(Context context) {
        PackageManager packageManager = getPackageManager(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized static String getPackageName(Context context) {
        PackageManager packageManager = getPackageManager(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized static int getVersionCode(Context context) {
        PackageManager packageManager = getPackageManager(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    public static List<PackageInfo> getInstalledPackages(Context context) {
        PackageManager packageManager = getPackageManager(context);
        return packageManager.getInstalledPackages(0);
    }

    public static boolean isInstalled(Context context, String packageName) {
        List<PackageInfo> installedPackages = getInstalledPackages(context);
        if (installedPackages != null) {
            if (DEBUG) {
                for (PackageInfo packageInfo : installedPackages) {
                    Log.d(TAG, getPackInfoString(packageInfo));
                }
            }
            for (PackageInfo packageInfo : installedPackages) {
                if (packageInfo.packageName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static PackageManager getPackageManager(Context context) throws NullPointerException {
        if (context == null) {
            throw new NullPointerException("The package manager is not available now because of null given context.");
        }
        return context.getPackageManager();
    }

    private static String getPackInfoString(PackageInfo packageInfo) {
        if (packageInfo == null) {
            Log.e(TAG, "Null given packageInfo.");
            return null;
        }
        return "package name: " + packageInfo.packageName
                + "version code: " + packageInfo.versionCode
                + "version name: " + packageInfo.versionName
                + "";
    }


}
