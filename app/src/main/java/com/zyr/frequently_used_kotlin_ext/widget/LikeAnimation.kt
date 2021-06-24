package com.zyr.frequently_used_kotlin_ext.widget

import android.animation.*
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.zyr.frequently_used_kotlin_ext.R
import com.zyr.frequently_used_kotlin_ext.ext.dp2px

class LikeAnimation(
    context: Context,
    event: MotionEvent,
    private val viewGroup: ViewGroup,
    @DrawableRes private val drawable: Int = R.drawable.post_ic_big_like
) {
    companion object {
        const val SCALE_X = "scaleX"
        const val SCALE_Y = "scaleY"
        const val ALPHA = "alpha"
        const val ROTATION = "rotation"
        const val TRANSLATION_Y = "translationY"
        private val defaultSize = 100.dp2px()
        private var delayTime = longArrayOf(0, 50, 100, 150, 400, 700, 800, 300)
        private var num = floatArrayOf(-30f, -20f, 0f, 20f, 30f)
    }

    private val imageView = ImageView(context)

    init {
        val params = FrameLayout.LayoutParams(defaultSize, defaultSize)
        params.leftMargin = event.x.toInt() - defaultSize / 2
        params.topMargin = event.y.toInt() - defaultSize
        imageView.setImageDrawable(ContextCompat.getDrawable(context, drawable))
        imageView.layoutParams = params
        viewGroup.addView(imageView)
    }

    fun start() {
        val animatorSet = AnimatorSet()
        animatorSet.play(scale(imageView, SCALE_X, 2f, 0.9f, delayTime[2], delayTime[0]))
            .with(scale(imageView, SCALE_Y, 2f, 0.9f, delayTime[2], delayTime[0]))
            .with(rotation(imageView, 0, 0, num[(num.indices).random()]))
            .with(alpha(imageView, 0f, 1f, delayTime[2], delayTime[0]))
            .with(scale(imageView, SCALE_X, 0.9f, 1f, delayTime[1], delayTime[3]))
            .with(scale(imageView, SCALE_Y, 0.9f, 1f, delayTime[1], delayTime[3]))
            .with(translationY(imageView, 0f, -600f, delayTime[6], delayTime[4]))
            .with(alpha(imageView, 1f, 0f, delayTime[7], delayTime[4]))
            .with(scale(imageView, SCALE_X, 1f, 3f, delayTime[5], delayTime[4]))
            .with(scale(imageView, SCALE_Y, 1f, 3f, delayTime[5], delayTime[4]))
        animatorSet.start()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                viewGroup.removeViewInLayout(imageView)
            }
        })
    }

    private fun scale(
        view: View?,
        propertyName: String?,
        from: Float,
        to: Float,
        time: Long,
        delayTime: Long
    ): ObjectAnimator? {
        val translation: ObjectAnimator = ObjectAnimator.ofFloat(
            view, propertyName, from, to
        )
        translation.interpolator = LinearInterpolator()
        translation.startDelay = delayTime
        translation.duration = time
        return translation
    }

    private fun alpha(
        view: View?,
        from: Float,
        to: Float,
        time: Long,
        delayTime: Long
    ): ObjectAnimator? {
        val translation: ObjectAnimator = ObjectAnimator.ofFloat(
            view, ALPHA, from, to
        )
        translation.interpolator = LinearInterpolator()
        translation.startDelay = delayTime
        translation.duration = time
        return translation
    }

    private fun rotation(
        view: View?,
        time: Long,
        delayTime: Long,
        values: Float
    ): ObjectAnimator? {
        val rotation: ObjectAnimator = ObjectAnimator.ofFloat(view, ROTATION, values)
        rotation.duration = time
        rotation.startDelay = delayTime
        rotation.interpolator = TimeInterpolator { input -> input }
        return rotation
    }

    private fun translationY(
        view: View?,
        from: Float,
        to: Float,
        time: Long,
        delayTime: Long
    ): ObjectAnimator? {
        val translation: ObjectAnimator = ObjectAnimator.ofFloat(
            view, TRANSLATION_Y, from, to
        )
        translation.interpolator = LinearInterpolator()
        translation.startDelay = delayTime
        translation.duration = time
        return translation
    }
}
