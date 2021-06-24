package com.zyr.frequently_used_kotlin_ext.ext

import java.io.File
import java.util.*

/**
 * 获取文件(夹)大小
 * @return 文件大小(字节)
 */
fun File.getSize(): Long {
    if (this.exists()) {
        if (this.isFile) {
            return this.length()
        } else {
            val deque = ArrayDeque<File>()
            deque.addLast(this)

            var size = 0L
            var file: File
            while (deque.isNotEmpty()) {
                file = deque.first
                if (file.isFile) {
                    size += file.length()
                } else {
                    file.listFiles()?.forEach {
                        deque.addLast(it)
                    }
                }
                deque.pollFirst()
            }

            return size
        }
    } else {
        return 0
    }
}

fun File.rename(newName: String): String {
    if (exists()) {
        val name = name.replaceBeforeLast(".", newName)
        val newPath = parent + File.separator + name
        val newFile = File(newPath)
        return if (renameTo(newFile)) {
            newPath
        } else {
            ""
        }
    }
    return ""
}

