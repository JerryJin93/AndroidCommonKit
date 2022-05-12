package com.jerryjin.kit.graphics.view.recyclerview

import androidx.recyclerview.widget.DiffUtil

/**
 * Author: Jerry
 *
 * Created at: 2021/09/30 15:06
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
 * Description: List diff callback for [androidx.recyclerview.widget.DiffUtil].
 */
class DiffCallback<T : IDiffCallback<T>> : DiffUtil.Callback() {

    lateinit var oldItems: List<T>
    private val _oldItems: List<T>
        get() = oldItems
    lateinit var newItems: List<T>
    private val _newItems
        get() = newItems

    override fun getOldListSize(): Int = _oldItems.size

    override fun getNewListSize(): Int = _newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        _oldItems[oldItemPosition].areItemsTheSame(_newItems[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        _oldItems[oldItemPosition].areContentsTheSame(_newItems[newItemPosition])
}