package com.easy.finger.helper.touch.touchhelper.tool

object FunctionTool {

    private val BACK = 1
    private val HOME = 2

    private lateinit var callback: FunctionCallBack

    interface FunctionCallBack {
        fun callBack(funcationNum: Int)
    }

    fun setCallBack(callback: FunctionCallBack) {
        this.callback = callback
    }

    fun funcationBack() {
        callback.callBack(BACK)
    }
}