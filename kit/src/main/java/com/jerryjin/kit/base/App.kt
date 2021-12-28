package com.jerryjin.kit.base

import android.app.Application

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 14:44
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
 * Description: Base [android.app.Application].
 */
open class App : Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}