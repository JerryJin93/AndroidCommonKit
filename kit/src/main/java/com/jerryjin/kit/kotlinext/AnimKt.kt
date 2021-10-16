package com.jerryjin.kit.kotlinext

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Interpolator

/**
 * Author: Jerry
 *
 * Created at: 2021/10/16 20:11
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
 * Description: Kotlin extensions for animations.
 */

fun View.letUsShake(
    vararg interval: Float,
    animConfig: AnimConfig.() -> Unit
) =
    AnimConfig().apply config@{
        // set the configuration
        animConfig(this)
        // let's animate!!!
        ValueAnimator.ofFloat(*interval).apply animator@{
            // initialize the parameters
            this@animator.repeatMode = this@config.repeatMode
            this@animator.repeatCount = this@config.repeatCount
            this@animator.duration = this@config.duration
            this@animator.interpolator = this@config.interpolator

            addUpdateListener { animator ->
                // In case of running before Activity#onResume()
                post {
                    this@config.onAnimatorUpdate?.invoke(animator)
                }

                // listener
                animator.animatedFraction.also {
                    when (it) {
                        0f -> {
                            // onStart
                            this@config.onAnimationStart?.invoke()
                        }

                        1f -> {
                            // the end of the animation
                            this@config.onAnimationEnd?.invoke()
                        }

                        else -> {
                            // onUpgrade
                            this@config.onAnimationUpdate?.invoke(it)
                        }
                    }
                }
            }
        }.start()
    }

fun View.letUsShakeHorizontally(
    offset: Float,
    animConfig: AnimConfig.() -> Unit
) =
    letUsShake(
        0f,
        -offset,
        0f,
        offset,
        0f,
        direction = ShakeHorizontally(),
        animConfig = animConfig
    )

fun View.letUsShakeVertically(
    offset: Float,
    animConfig: AnimConfig.() -> Unit
) =
    letUsShake(
        0f,
        -offset,
        0f,
        offset,
        0f,
        direction = ShakeVertically(),
        animConfig = animConfig
    )


fun View.letUsShake(
    vararg offset: Float,
    direction: ShakeDirection,
    animConfig: AnimConfig.() -> Unit
) = letUsShake(*offset) {
    animConfig(this)
    onAnimatorUpdate = {
        (it.animatedValue as Float).also { `val` ->
            when(direction) {
                is ShakeHorizontally -> {
                    translationX = `val`
                }
                is ShakeVertically -> {
                    translationY = `val`
                }
            }
        }
    }
}

typealias OnAnimatorUpdate = (ValueAnimator) -> Unit
typealias OnAnimationStart = () -> Unit
typealias OnAnimationUpdate = (Float) -> Unit
typealias OnAnimationEnd = () -> Unit

class AnimConfig {

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1

        const val DEFAULT_REPEAT_COUNT = 1
        const val DEFAULT_DURATION: Long = 300
        const val DEFAULT_REPEAT_MODE = ValueAnimator.RESTART
    }


    var repeatCount = DEFAULT_REPEAT_COUNT
    var duration = DEFAULT_DURATION
    var repeatMode = DEFAULT_REPEAT_MODE
    var interpolator: Interpolator? = null

    var onAnimatorUpdate: OnAnimatorUpdate? = null
    var onAnimationStart: OnAnimationStart? = null
    var onAnimationUpdate: OnAnimationUpdate? = null
    var onAnimationEnd: OnAnimationEnd? = null
}


public sealed class ShakeDirection(private val direction: Int)
class ShakeHorizontally : ShakeDirection(AnimConfig.HORIZONTAL)
class ShakeVertically : ShakeDirection(AnimConfig.VERTICAL)