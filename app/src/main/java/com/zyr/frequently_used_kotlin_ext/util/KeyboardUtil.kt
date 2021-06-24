package com.zyr.frequently_used_kotlin_ext.util

import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.zyr.frequently_used_kotlin_ext.appContext

object KeyboardUtil {

    private var mImm: InputMethodManager? = null

    val isActive: Boolean
        get() = mImm?.isActive ?: false

    init {
        mImm = appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }

    fun hideFromView(v: View, isClearFocus: Boolean = true) {
        v.windowToken?.let {
            hideFromWindow(it)
        }
        if (isClearFocus) {
            v.clearFocus()
        }
    }

    fun hideFromWindow(windowToken: IBinder) {
        mImm?.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 触发开关, 输入法键盘显示相反状态, 即如果当前为显示则隐藏
     */
    fun toggle() {
        mImm?.toggleSoftInput(
            InputMethodManager.RESULT_UNCHANGED_SHOWN,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    /**
     * 强制弹出输入法键盘(无法使用代码控制隐藏输入法)
     */
    fun forceShow(v: View) {
        mImm?.showSoftInput(v, InputMethodManager.SHOW_FORCED)
    }

    /**
     * 弹出输入法键盘
     */
    fun showFromView(v: View?) {
        v ?: return
        v.requestFocus()
        mImm?.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
    }

    fun isActive(v: View): Boolean {
        return mImm?.isActive(v) ?: false
    }

    fun restartInput(v: View) {
        mImm?.restartInput(v)
    }

    /**
     *  自动弹出软键盘
     */
    fun showSoftKeyboard(window: Window) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}
