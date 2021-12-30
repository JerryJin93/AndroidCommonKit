package com.jerryjin.kit.utils.kotlin_ext

import java.util.*

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:01
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
 * Description: Supports for [com.google.auto.service].
 */
fun <S> loadServices(cls: Class<S>) = ServiceLoader.load(cls).iterator().let {
    mutableListOf<S>().apply {
        while (it.hasNext()) {
            add(it.next())
        }
    }
}

fun <S> loadService(cls: Class<S>) = loadServices(cls)[0]

