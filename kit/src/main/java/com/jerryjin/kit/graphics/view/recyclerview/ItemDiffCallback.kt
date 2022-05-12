package com.jerryjin.kit.graphics.view.recyclerview

import androidx.recyclerview.widget.DiffUtil

/**
 * Author: Jerry
 *
 * Created at: 2021/09/30 15:39
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
 * Description: Item diff callback for [androidx.recyclerview.widget.DiffUtil.ItemCallback].
 */
class ItemDiffCallback<T : IDiffCallback<T>> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.areContentsTheSame(newItem)
}