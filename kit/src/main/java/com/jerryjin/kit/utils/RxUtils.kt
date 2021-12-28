package com.jerryjin.kit.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:15
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
 * Description: RxJava composable.
 */
fun <T : Any> fromMainToMain() = ObservableTransformer<T, T> { upstream ->
    upstream.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(AndroidSchedulers.mainThread())
}

fun <T : Any> fromMainToIO() = ObservableTransformer<T, T> { upstream ->
    upstream.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun <T : Any> fromIOToMain() = ObservableTransformer<T, T> { upstream ->
    upstream.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T : Any> fromIOToIO() = ObservableTransformer<T, T> { upstream ->
    upstream.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}