package com.jerryjin.kit.graphics.view.recyclerview

/**
 * Author: Jerry
 *
 * Created at: 2021/09/30 15:07
 *
 * GitHub: https://github.com/JerryJin93
 *
 * Blog:
 *
 * Email: jerry93@foxmail.com
 *
 * WeChat: AcornLake
 *
 * Version: 1.0.0
 *
 * Description: Interface for [androidx.recyclerview.widget.DiffUtil.Callback].
 */
interface IDiffCallback<T> {
    fun areItemsTheSame(that: T): Boolean
    fun areContentsTheSame(that: T): Boolean
}