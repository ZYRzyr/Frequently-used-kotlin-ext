package com.zyr.frequently_used_kotlin_ext.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.zyr.frequently_used_kotlin_ext.R
import com.zyr.frequently_used_kotlin_ext.ext.dp2px
import com.zyr.frequently_used_kotlin_ext.other.SimpleTextWatcher

class ClearEditText : AppCompatEditText {
    companion object {
        private const val START = 0
        private const val TOP = 1
        private const val BOTTOM = 3
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private val padding = 20f
    private var clearDrawable: Drawable? = null
    var onClearClick: (() -> Unit)? = null
    var isAlwaysShowClear = false

    private fun init() {
        clearDrawable = ContextCompat.getDrawable(context, R.drawable.edit_ic_clear)
        clearDrawable?.setBounds(0, 0, padding.dp2px(), padding.dp2px())

        onFocusChangeListener = OnFocusChangeListener { _: View, hasFocus: Boolean ->
            setClearDrawableVisible((!TextUtils.isEmpty(text) && hasFocus) || isAlwaysShowClear)
        }

        addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val isVisible = !TextUtils.isEmpty(text) || isAlwaysShowClear
                setClearDrawableVisible(isVisible)
            }
        })

        setClearDrawableVisible(isAlwaysShowClear)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val isClean = (event.x > width - totalPaddingRight
                    && event.x < width - paddingRight && clearDrawable != null)
            if (isClean) {
                setText("")
                onClearClick?.invoke()
            }
        }
        return super.onTouchEvent(event)
    }

    fun setClearDrawableVisible(isVisible: Boolean) {
        val endDrawable = if (isVisible) clearDrawable else null
        setCompoundDrawablesRelative(
            compoundDrawablesRelative[START],
            compoundDrawablesRelative[TOP], endDrawable,
            compoundDrawablesRelative[BOTTOM]
        )
    }
}
