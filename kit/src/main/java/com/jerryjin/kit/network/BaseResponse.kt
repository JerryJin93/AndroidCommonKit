package com.jerryjin.kit.network

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:24
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
 * Description: Base network response.
 */
data class BaseResponse<T>(
    val data: T?,
    val code: Int,
    val msg: String,
)
