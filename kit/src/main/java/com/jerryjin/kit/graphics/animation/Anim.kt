package com.jerryjin.kit.graphics.animation

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Interpolator

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 15:03
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
 * Description: Animation extensions for [View].
 */
fun View.letUsShake(
    vararg interval: Float,
    animConfig: AnimConfig.() -> Unit
) =
    AnimConfig().apply config@{
        // set the config
        animConfig(this)
        // let's animate!!!
        ValueAnimator.ofFloat(*interval).apply animator@{
            // initialize the parameters
            this@animator.repeatMode = this@config.repeatMode
            this@animator.repeatCount = this@config.repeatCount
            this@animator.duration = this@config.duration
            this@animator.interpolator = this@config.interpolator

            addUpdateListener { animator ->
                this@letUsShake.apply {
                    // in case of running before Activity#onResume()
                    post {
                        this@config.onAnimatorUpdate?.invoke(animator)
                    }
                }
                // listener
                animator.animatedFraction.also {
                    when (it) {
                        0f -> {
                            // onStart
                            this@config.onAnimationStart?.invoke()
                        }

                        1f -> {
                            // end of the animation
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
        direction = AnimConfig.Horizontal(),
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
        direction = AnimConfig.Vertical(),
        animConfig = animConfig
    )

fun View.letUsShake(
    vararg offset: Float,
    direction: AnimConfig.Direction,
    animConfig: AnimConfig.() -> Unit
) = letUsShake(*offset) {
    animConfig(this)
    onAnimatorUpdate = {
        (it.animatedValue as Float).also { `val` ->
            when (direction) {
                is AnimConfig.Horizontal -> {
                    translationX = `val`
                }
                is AnimConfig.Vertical -> {
                    translationY = `val`
                }
            }
        }
    }
}

typealias OnAnimatorUpdate = (ValueAnimator) -> Unit
typealias OnAnimationStart = (() -> Unit)
typealias OnAnimationUpdate = ((Float) -> Unit)
typealias OnAnimationEnd = (() -> Unit)

class AnimConfig {

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
    }

    sealed class Direction(private val dir: Int)
    class Horizontal : Direction(HORIZONTAL)
    class Vertical : Direction(VERTICAL)

    var repeatCount: Int = 0
    var duration: Long = 300
    var repeatMode: Int = ValueAnimator.RESTART
    var interpolator: Interpolator? = null

    var onAnimatorUpdate: OnAnimatorUpdate? = null
    var onAnimationStart: OnAnimationStart? = null
    var onAnimationUpdate: OnAnimationUpdate? = null
    var onAnimationEnd: OnAnimationEnd? = null
}