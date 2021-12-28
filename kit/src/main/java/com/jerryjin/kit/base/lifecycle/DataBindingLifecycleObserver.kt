package com.jerryjin.kit.base.lifecycle

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:39
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
 * Description: Lifecycle observer for [ViewDataBinding].
 */
class DataBindingLifecycleObserver<T : ViewDataBinding>(private var binding: T?) :
    LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            binding?.unbind()
            binding = null
        }
    }
}
