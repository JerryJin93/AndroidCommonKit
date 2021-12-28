package com.jerryjin.kit.utils

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:42
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
 * Description: Utility for Jetpack Navigation.
 */
class NavUtils {

    companion object {

        @Suppress("MemberVisibilityCanBePrivate")
        const val ERROR_NAV_HOST_ID = -1

        @JvmField
        val findNavControllerInActivity: (Activity, Int) -> NavController? = { activity, viewId ->
            viewId.let {
                if (it != ERROR_NAV_HOST_ID)
                    activity.findNavController(it)
                else
                    null
            }
        }
    }
}