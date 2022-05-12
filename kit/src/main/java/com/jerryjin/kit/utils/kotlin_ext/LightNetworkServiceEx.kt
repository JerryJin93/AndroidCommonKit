package com.jerryjin.kit.utils.kotlin_ext

import com.jerryjin.kit.network.BaseResponse
import kotlinx.coroutines.*

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
    var onStart: (suspend () -> Unit)? = null
    var onSuccess: (suspend (APIResponse.Success<PureResultType>) -> Unit)? = null
    var onError: (suspend (APIResponse.Error) -> Unit)? = null
    var onException: ((APIResponse.WithException) -> Unit)? = null
}

inline fun <PureResultType, reified ResultType : BaseResponse<PureResultType>, APIService> CoroutineScope.fireHttpRequest(
    service: APIService,
    crossinline requestConfig: NetworkRequest<PureResultType, ResultType, APIService>.() -> Unit,
) = launch {
    if (isActive)
        fireHttpRequestWithSuspension(service, requestConfig)
}

suspend inline fun <PureResultType, reified ResultType : BaseResponse<PureResultType>, APIService> fireHttpRequestWithSuspension(
    service: APIService,
    requestConfig: NetworkRequest<PureResultType, ResultType, APIService>.() -> Unit,
) = NetworkRequest<PureResultType, ResultType, APIService>().run {
    requestConfig()
    // useless CoroutineExceptionHandler, see more details in its comments.
    val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        onException?.invoke(APIResponse.WithException.of(throwable))
    }
    withContext(Dispatchers.Main + SupervisorJob() + handler) {
        onStart?.invoke()
        val result = try {
            withContext(Dispatchers.IO) { service?.requestBlock() }
        } catch (e: Exception) {
            onError?.invoke(APIResponse.ServerError(e.run { message ?: toString() }))
                ?: return@withContext
            return@withContext
        }
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

    open class Error(val code: Int, val msg: String) : APIResponse() {

        companion object {
            const val CODE_INTERNAL_SERVER_ERROR = 500

            @JvmStatic
            fun of(code: Int, msg: String) = Error(code, msg)
        }
    }

    class ServerError(message: String) : APIResponse.Error(CODE_INTERNAL_SERVER_ERROR, message)
}