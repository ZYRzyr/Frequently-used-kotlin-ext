package com.zyr.frequently_used_kotlin_ext.ext

import android.util.Base64
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Pattern

fun String.encodeBase64(): String {
    return if (this.toByteArray().isEmpty()) {
        this
    } else {
        Base64.encode(this.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
            .toString(Charset.defaultCharset())
    }
}

/**
 * 拆分出字符串中的数字
 * */
fun String.splitOutNumber(): String? {
    var str: String? = this
    if (str == null) {
        return null
    } else {
        var pattern = Pattern.compile("(\\d+\\.\\d+)")
        var matcher = pattern.matcher(str)
        if (matcher.find()) {
            str = if (matcher.group(1) == null) "" else matcher.group(1)
        } else {
            pattern = Pattern.compile("(\\d+)")
            matcher = pattern.matcher(str)
            str = if (matcher.find()) {
                if (matcher.group(1) == null) "" else matcher.group(1)
            } else {
                ""
            }
        }
        return str
    }
}

/**
 * 首字母转大写
 */
fun String.firstToUpperCase(): String {
    val str: String = this
    if (str.isNotEmpty()) {
        return str.substring(0, 1).uppercase(Locale.ROOT) + str.substring(1)
    }
    return ""
}

/**
 * 首字母转小写
 */
fun String.firstToLowerCase(): String {
    val str: String = this
    if (str.isNotEmpty()) {
        return str.substring(0, 1).lowercase(Locale.ROOT) + str.substring(1)
    }
    return ""
}
