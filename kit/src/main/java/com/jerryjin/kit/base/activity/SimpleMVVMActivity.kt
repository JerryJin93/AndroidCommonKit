package com.jerryjin.kit.base.activity

import androidx.databinding.ViewDataBinding
import com.jerryjin.kit.base.view_model.EmptyViewModel

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:44
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
 * Description: It's used for activity with simple logic, which usually has no need to get ViewModel engaged.
 */
abstract class SimpleMVVMActivity<T : ViewDataBinding> : MVVMActivity<T, EmptyViewModel>()