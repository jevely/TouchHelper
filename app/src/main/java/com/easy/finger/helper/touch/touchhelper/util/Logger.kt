package com.easy.finger.helper.touch.touchhelper.util

import android.util.Log

/**
 * 日志打印
 */
object Logger {

    private var showLogger = true

    fun d(tag: String, content: String) {
        if (showLogger) Log.d(tag, content)
    }

    fun e(tag: String, content: String) {
        if (showLogger) Log.e(tag, content)
    }

    fun i(tag: String, content: String) {
        if (showLogger) Log.i(tag, content)
    }

    fun v(tag: String, content: String) {
        if (showLogger) Log.v(tag, content)
    }

    fun w(tag: String, content: String) {
        if (showLogger) Log.w(tag, content)
    }

}