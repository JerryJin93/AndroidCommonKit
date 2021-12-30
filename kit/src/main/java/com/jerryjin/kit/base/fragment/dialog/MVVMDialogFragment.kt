package com.jerryjin.kit.base.fragment.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jerryjin.kit.base.lifecycle.EventBusObserver
import com.jerryjin.kit.graphics.animation.AnimConfig
import com.jerryjin.kit.graphics.animation.OnAnimationEnd
import com.jerryjin.kit.utils.kotlin_ext.dip

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:27
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
 * Description: Base [DialogFragment] for MVVM mode.
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class MVVMDialogFragment<T : ViewDataBinding, VM : ViewModel> : DialogFragment() {

    protected var mHost: FragmentActivity? = null
    protected lateinit var mBinding: T
    protected var mViewModel: VM? = null
    protected var args: Bundle? = null

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
            initWindow()
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

    private fun initViewModel() {
        mViewModel = getViewModelClass()?.let {
            ViewModelProvider(this).get(it)
        }
    }

    private fun initWindow() =
        dialog!!.window!!.let {
            it.requestFeature(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.apply {
            initWindow(attributes)
            attributes = attributes
        }
    }

    protected open fun initWindow(windowAttributes: WindowManager.LayoutParams) {
    }

    private fun startOtherServices() {
        getDownToBusiness()
    }

    protected open fun getDownToBusiness() {}

    override fun onDetach() {
        super.onDetach()
        mHost = null
    }
}
