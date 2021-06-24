package com.zyr.frequently_used_kotlin_ext.ext

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView

/**
 * 反色
 * */
fun ImageView.reverseColor() {
    val cm = ColorMatrix(
        floatArrayOf(
            -1f, 0f, 0f, 0f, 255f,
            0f, -1f, 0f, 0f, 255f,
            0f, 0f, -1f, 0f, 255f,
            0f, 0f, 0f, 1f, 0f
        )
    )
    this.colorFilter = ColorMatrixColorFilter(cm)
}
