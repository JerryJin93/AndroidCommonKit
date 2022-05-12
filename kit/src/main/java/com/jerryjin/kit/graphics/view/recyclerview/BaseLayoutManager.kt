package com.jerryjin.kit.graphics.view.recyclerview

import androidx.recyclerview.widget.RecyclerView

/**
 * Author: Jerry
 *
 * Created at: 2022/04/20 10:28
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
 * Description: Base [RecyclerView.LayoutManager] for [BaseRecyclerAdapter].
 */
abstract class BaseLayoutManager : RecyclerView.LayoutManager() {

    companion object {
        const val INVALID_POSITION = -1
    }

    open fun findFirstCompletelyVisibleItemPosition(): Int = INVALID_POSITION
    open fun findLastCompletelyVisibleItemPosition(): Int = INVALID_POSITION
}