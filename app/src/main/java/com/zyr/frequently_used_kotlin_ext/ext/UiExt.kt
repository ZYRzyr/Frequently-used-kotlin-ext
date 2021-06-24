package com.zyr.frequently_used_kotlin_ext.ext

import com.blankj.utilcode.util.ConvertUtils

fun Float.dp2px(): Int {
    return ConvertUtils.dp2px(this)
}

fun Int.dp2px(): Int {
    return ConvertUtils.dp2px(this.toFloat())
}

fun Float.px2dp(): Int {
    return ConvertUtils.px2dp(this)
}

fun Int.px2dp(): Int {
    return ConvertUtils.px2dp(this.toFloat())
}

fun Float.px2sp(): Int = ConvertUtils.px2sp(this)
