package com.jerryjin.kit.base.fragment.ordinary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jerryjin.kit.base.lifecycle.EventBusObserver
import com.jerryjin.kit.graphics.animation.AnimConfig
import com.jerryjin.kit.graphics.animation.OnAnimationEnd
import com.jerryjin.kit.kotlinext.dip

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:20
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
 * Description: Base fragment for MVVM mode.
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class MVVMFragment<T : ViewDataBinding, VM : ViewModel> : Fragment() {

    protected var mHost: FragmentActivity? = null
    protected lateinit var mBinding: T
    protected var mViewModel: VM? = null
    protected var args : Bundle? = null

    protected open val needEventBus: Boolean = false

    protected open val animConfig: (OnAnimationEnd) -> (AnimConfig.() -> Unit) = {
        {
            repeatCount = REPEAT_COUNT
            duration = DURATION
            onAnimationEnd = it
        }
    }

    companion object {
        const val REPEAT_COUNT = 3
        const val DURATION = 100L
        val HORIZONTAL_SHAKING_OFFSET = 15.dip
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        getContentId().let {
            mBinding = DataBindingUtil.inflate(inflater, it, container, false)
            mBinding.lifecycleOwner = viewLifecycleOwner
            mBinding.root
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mHost = context as FragmentActivity
        args = arguments
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        startBootstrapServices()
        startOtherServices()
    }

    private fun startBootstrapServices() {
        eventBusSupport()
        initViewModel()
    }

    private fun eventBusSupport() {
        if (needEventBus) {
            lifecycle.addObserver(EventBusObserver(this))
        }
    }

    @LayoutRes
    protected abstract fun getContentId(): Int

    protected open fun getViewModelClass(): Class<VM>? = null

    protected open fun initViewModel() {
        mViewModel = getViewModelClass()?.let {
            ViewModelProvider(this).get(it)
        }
    }

    private fun startOtherServices() {
        getDownToBusiness()
    }

    protected open fun getDownToBusiness() {}

    protected open fun getDataBindingVariableMap(): Map<Int, Any>? = null

    override fun onDetach() {
        super.onDetach()
        mHost = null
    }
}