package com.jerryjin.kit.utils.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 14:56
 *
 * GitHub: https://github.com/JerryJin93
 *
 * Blog:
 *
 * Email: jerry93@foxmail.com

 * WeChat: AcornLake
 *
 * Version: 1.0.0
 *
 * Description: A easy-to-use permission helper for Android.
 */
class SimplePermissionHelper {
    companion object {

        @JvmStatic
        fun checkPermission(context: Context, permission: String) =
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED

        @JvmStatic
        fun checkPermissions(
            context: Context,
            vararg permissions: String,
            whichOnesFallBack: (List<String>) -> Unit
        ) = permissions.let {
            val fallbacks = mutableListOf<String>()
            for (item in it) {
                if (!checkPermission(context, item))
                    fallbacks.add(item)
            }
            whichOnesFallBack(fallbacks)
        }

        @JvmStatic
        fun requestPermissions(
            activity: Activity,
            vararg permissions: String,
            requestCode: Int = SIMPLE_REQUEST_CODE
        ) = checkPermissions(activity, *permissions) {
            if (it.isNotEmpty())
                ActivityCompat.requestPermissions(activity, it.toTypedArray(), requestCode)
        }
    }
}