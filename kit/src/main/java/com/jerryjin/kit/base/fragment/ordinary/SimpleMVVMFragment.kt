package com.jerryjin.kit.base.fragment.ordinary

import androidx.databinding.ViewDataBinding
import com.jerryjin.kit.base.view_model.EmptyViewModel

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:48
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
 * Description: It's used for fragments with simple logic, which usually has no ViewModel.
 */
abstract class SimpleMVVMFragment<T : ViewDataBinding> : MVVMFragment<T, EmptyViewModel>()