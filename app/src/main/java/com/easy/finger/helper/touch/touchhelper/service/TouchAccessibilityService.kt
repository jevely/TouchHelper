package com.easy.finger.helper.touch.touchhelper.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.easy.finger.helper.touch.touchhelper.tool.FunctionTool


class TouchAccessibilityService : AccessibilityService(), FunctionTool.FunctionCallBack {

    private val BACK = 1
    private val HOME = 2

    override fun callBack(funcationNum: Int) {
        when (funcationNum) {
            BACK -> performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
            HOME -> performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
        }
    }

    override fun onCreate() {
        super.onCreate()
        FunctionTool.setCallBack(this)
    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val packageName = event?.getPackageName().toString()
        Log.d("LJW", "package name = " + packageName)
    }

}