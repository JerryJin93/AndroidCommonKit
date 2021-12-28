package com.jerryjin.kit.utils

import android.os.Handler
import android.os.Looper
import com.tencent.mmkv.MMKV
import org.greenrobot.eventbus.EventBus

/**
 * Author: Jerry
 *
 * Created at: 2021/10/16 20:06
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
 * Description: Global values.
 */
object GlobalVal {

    val MAIN_HANDLER by lazy {
        Handler(Looper.getMainLooper())
    }

    val EVENT_BUS = EventBus.getDefault()

    val MMKV_HANDLE = MMKV.defaultMMKV()
}