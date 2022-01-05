package com.jerryjin.kit.utils.kotlin_ext

import com.jerryjin.kit.network.BaseResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Author: Jerry
 *
 * Created at: 2022/01/05 12:11
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
 * Description: A light-weighted network service extension for Kotlin.
 */
const val CODE_SUCCESS = 0
const val MSG_SUCCESS = "success"

class NetworkRequest<PureResultType, ResultType : BaseResponse<PureResultType>, APIService> {
    var codeSuccess = CODE_SUCCESS
    var messageSuccess = MSG_SUCCESS
    lateinit var requestBlock: (suspend APIService.() -> ResultType)
    var onStart: (() -> Unit)? = null
    var onSuccess: ((APIResponse.Success<PureResultType>) -> Unit)? = null
    var onError: ((APIResponse.Error) -> Unit)? = null
    var onException: ((APIResponse.WithException) -> Unit)? = null
}

inline fun <PureResultType, reified ResultType : BaseResponse<PureResultType>, APIService> CoroutineScope.fireHttpRequest(
    service: APIService,
    requestConfig: NetworkRequest<PureResultType, ResultType, APIService>.() -> Unit,
) = NetworkRequest<PureResultType, ResultType, APIService>().run {
    requestConfig()
    val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        onException?.invoke(APIResponse.WithException.of(throwable))
    }
    launch(SupervisorJob() + handler) {
        onStart?.invoke()
        val result = service?.requestBlock()
        if (result != null) {
            val code = result.code
            val msg = result.msg
            if (code == codeSuccess && msg == messageSuccess)
                result.data?.run {
                    onSuccess?.invoke(APIResponse.Success.of(this))
                }
            else onError?.invoke(APIResponse.Error.of(code, msg))
        } else onException?.invoke(APIResponse.WithException.of(IllegalArgumentException("You have to pass the specific service!")))
    }
}

sealed class APIResponse {

    class WithException private constructor(val throwable: Throwable?) : APIResponse() {

        companion object {
            @JvmStatic
            fun of(throwable: Throwable?) = WithException(throwable)
        }
    }

    class Success<ReturnType> private constructor(val result: ReturnType) : APIResponse() {
        companion object {
            @JvmStatic
            fun <ReturnType> of(result: ReturnType) = Success(result)
        }
    }

    class Error private constructor(val code: Int, val msg: String) : APIResponse() {
        companion object {
            @JvmStatic
            fun of(code: Int, msg: String) = Error(code, msg)
        }
    }
}