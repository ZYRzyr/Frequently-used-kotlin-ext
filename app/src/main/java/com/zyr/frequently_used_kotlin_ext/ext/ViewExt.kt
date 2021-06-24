package com.zyr.frequently_used_kotlin_ext.ext

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.coordinatorlayout.widget.ViewGroupUtils
import com.zyr.frequently_used_kotlin_ext.util.KeyboardUtil
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * 扩大View的点击区域.
 * @param dx 必须大于0，否则无效。水平左右同时呈矩形扩大
 * @param dy 必须大于0，否则无效。垂直上下同时呈矩形扩大
 * */
@SuppressLint("RestrictedApi")
fun View.expandClickArea(dx: Int, dy: Int) {
    class MultiTouchDelegate(bound: Rect? = null, delegateView: View) :
        TouchDelegate(bound, delegateView) {
        val delegateViewMap = mutableMapOf<View, Rect>()
        private var delegateView: View? = null

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val x = event.x.toInt()
            val y = event.y.toInt()
            var handled = false
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    delegateView = findDelegateViewUnder(x, y)
                }

                MotionEvent.ACTION_CANCEL -> {
                    delegateView = null
                }
            }

            delegateView?.let {
                event.setLocation(it.width / 2f, it.height / 2f)
                handled = it.dispatchTouchEvent(event)
            }
            return handled
        }

        private fun findDelegateViewUnder(x: Int, y: Int): View? {
            delegateViewMap.forEach { entry -> if (entry.value.contains(x, y)) return entry.key }
            return null
        }
    }

    val parentView = parent as? ViewGroup
    parentView ?: return

    if (parentView.touchDelegate == null) {
        parentView.touchDelegate = MultiTouchDelegate(delegateView = this)
        post {
            val rect = Rect()
            ViewGroupUtils.getDescendantRect(parentView, this, rect)
            rect.inset(-dx, -dy)
            (parentView.touchDelegate as? MultiTouchDelegate)?.delegateViewMap?.put(this, rect)
        }
    }
}

fun View?.show() {
    if (this != null) {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
    }
}

fun View?.gone() {
    if (this != null) {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
    }
}

fun View?.hide() {
    if (this != null) {
        if (visibility != View.INVISIBLE) {
            visibility = View.INVISIBLE
        }
    }
}

fun View.setHeight(height: Int) {
    val params = layoutParams ?: return
    params.height = height
    layoutParams = params
}

fun View.setWidth(width: Int) {
    val params = layoutParams ?: return
    params.width = width
    layoutParams = params
}

fun <T : View> T?.onClick(block: (View?) -> Unit) {
    this?.setOnClickListener(object : DebounceClickListener() {
        override fun onDebounceClick(v: View?) {
            block.invoke(v)
        }
    })
}

private abstract class DebounceClickListener : View.OnClickListener {
    private companion object {
        private const val MIN_CLICK_INTERVAL = 300L
    }

    private var lastClickTime: Long = 0

    override fun onClick(v: View?) {
        if (v is CheckBox || v is RadioButton) {
            v.isEnabled = false
        }

        Flowable.intervalRange(0, MIN_CLICK_INTERVAL, 0, 1, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                v?.isEnabled = true
            }
            .subscribe()

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > MIN_CLICK_INTERVAL) {
            lastClickTime = currentTime
            onDebounceClick(v)
        }
    }

    abstract fun onDebounceClick(v: View?)
}

fun View.requestFocus(focus: Boolean) {
    when (focus) {
        true -> {
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }
        false -> clearFocus()
    }
}

fun View.showKeyboard() {
    KeyboardUtil.showFromView(this)
}

fun View.hideKeyboard() {
    if (KeyboardUtil.isActive(this))
        KeyboardUtil.hideFromView(this)
}
