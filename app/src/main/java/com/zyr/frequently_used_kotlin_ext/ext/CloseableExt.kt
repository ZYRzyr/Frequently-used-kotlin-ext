package com.zyr.frequently_used_kotlin_ext.ext

import java.io.Closeable
import java.io.IOException

/**
 * 安全关闭
 */
fun Closeable?.safeClose() {
    this?.apply {
        try {
            close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

/**
 * 安全使用
 */
inline fun <T : Closeable?, R> T.safeUse(block: (T) -> R): R? {
    return try {
        use(block)
    } catch (e: Throwable) {
        null
    }
}
