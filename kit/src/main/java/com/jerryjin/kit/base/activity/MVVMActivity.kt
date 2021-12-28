package com.jerryjin.kit.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.jerryjin.kit.base.lifecycle.DataBindingLifecycleObserver
import com.jerryjin.kit.base.lifecycle.EventBusObserver
import com.jerryjin.kit.utils.NavUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:32
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
 * Description: Base activity for MVVM mode.
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class MVVMActivity<T : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    protected lateinit var mBinding: T
    protected var mViewModel: VM? = null
    protected var mNavController: NavController? = null

    protected open val needEventBus: Boolean = false

    protected val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        startBootstrapServices()
        startOtherServices()
    }

    private fun startBootstrapServices() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.lifecycleOwner = this

        lifecycle.addObserver(DataBindingLifecycleObserver(mBinding))
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_STOP)
                    mainScope.cancel()
            }

        })


        eventBusSupport()
        initNavigation()
        initViewModel()
    }

    private fun eventBusSupport() {
        if (needEventBus) {
            lifecycle.addObserver(EventBusObserver(this))
        }
    }


    private fun initNavigation() {
        mNavController = NavUtils.findNavControllerInActivity(this, getNavHostId())
    }

    protected open fun getViewModelClass(): Class<VM>? = null

    private fun initViewModel() {
        mViewModel = getViewModelClass()?.let {
            ViewModelProvider(this).get(it)
        }
    }

    private fun startOtherServices() {
        getDownToBusiness()
    }

    protected open fun getDownToBusiness() {}

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected open fun getNavHostId(): Int = NavUtils.ERROR_NAV_HOST_ID

    override fun onSupportNavigateUp(): Boolean =
        mNavController?.navigateUp() ?: super.onSupportNavigateUp()
}