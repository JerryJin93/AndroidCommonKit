package com.jerryjin.kit.base.fragment.dialog

import androidx.databinding.ViewDataBinding
import com.jerryjin.kit.base.view_model.EmptyViewModel

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:50
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
 * Description: Implementation for [MVVMDialogFragment] without [androidx.lifecycle.ViewModel].
 */
abstract class SimpleMVVMDialogFragment<DB : ViewDataBinding> : MVVMDialogFragment<DB, EmptyViewModel>()