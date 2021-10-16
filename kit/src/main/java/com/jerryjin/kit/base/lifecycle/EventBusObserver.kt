package com.jerryjin.kit.base.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.jerryjin.kit.utils.GlobalVal.EVENT_BUS

/**
 * Author: Jerry
 *
 * Created at: 2021/10/16 19:59
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
 * Description: One [LifecycleEventObserver] implementation for [org.greenrobot.eventbus.EventBus].
 */
class EventBusObserver(private val subscriber: Any) : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            if (!EVENT_BUS.isRegistered(subscriber))
                EVENT_BUS.register(subscriber)
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            if (EVENT_BUS.isRegistered(subscriber))
                EVENT_BUS.unregister(subscriber)
        }
    }
}