package com.easy.finger.helper.touch.touchhelper.util

import android.annotation.TargetApi
import android.app.Activity
import android.app.AppOpsManager
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.view.KeyEvent
import com.easy.finger.helper.touch.touchhelper.TouchApplication.Companion.context
import java.util.regex.Pattern
import android.view.KeyEvent.KEYCODE_BACK
import com.easy.finger.helper.touch.touchhelper.service.TouchAccessibilityService


/**
 * 获取手机高宽
 */
fun getScreen(context: Context): Point {
    val resources = context.resources
    val dm = resources.displayMetrics
    val point = Point(dm.widthPixels, dm.heightPixels)
    return point
}


/**
 * 检查网络是否可用
 */
fun checkNetWork(context: Context): Boolean {
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo: NetworkInfo = manager.activeNetworkInfo
    return networkInfo.isAvailable
}

/**
 * 邮箱格式验证
 */
fun isEmail(email: String): Boolean {
    val str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" + "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
    val p = Pattern.compile(str)
    val m = p.matcher(email)
    return m.matches()
}

/**
 * 根据手机分辨率从DP转成PX
 */
fun dip2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 获取状态栏高度
 */
private fun getStatuBarHeight(): Int? {
    var statusBarHeight1 = -1
    //获取status_bar_height资源的ID
    val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
            "android")
    if (resourceId > 0) {
        //根据资源ID获取响应的尺寸值
        statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId)
    }
    return statusBarHeight1
}

/**
 * back键模拟
 */
fun clickBack() {
    object : Thread() {
        override fun run() {
            try {
                val inst = Instrumentation()
                inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }.start()
}

/**
 * 判断辅助功能是否开启
 */
fun isAccessibilitySettingsOn(mContext: Context): Boolean {
    var accessibilityEnabled = 0
    val service = mContext.packageName + "/" + TouchAccessibilityService::class.java.getCanonicalName()
    try {
        accessibilityEnabled = Settings.Secure.getInt(
                mContext.applicationContext.contentResolver,
                android.provider.Settings.Secure.ACCESSIBILITY_ENABLED)
    } catch (e: Settings.SettingNotFoundException) {
        e.printStackTrace()
    }

    val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')
    if (accessibilityEnabled == 1) {
        val settingValue = Settings.Secure.getString(
                mContext.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
        if (settingValue != null) {
            mStringColonSplitter.setString(settingValue)
            while (mStringColonSplitter.hasNext()) {
                val accessibilityService = mStringColonSplitter.next()
                if (accessibilityService.equals(service, ignoreCase = true)) {
                    return true
                }
            }
        }
    } else {
    }
    return false
}

/**
 * 开启辅助功能
 */
fun goToOpenAccess(context: Activity) {
    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    context.startActivityForResult(intent,1)
}

/**
 * 检查悬浮窗权限
 */
fun checkFloatPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        checkFloatWindowPermission23(context)
    } else {
        checkFloatWindowPermission(context)
    }
}

/**
 * 检查悬浮窗权限
 */
private fun checkFloatWindowPermission(context: Context): Boolean {
    val version = Build.VERSION.SDK_INT
    if (version >= 19) {
        val manager = context.getSystemService(Context
                .APP_OPS_SERVICE) as AppOpsManager
        try {
            val clazz = AppOpsManager::class.java
            val method = clazz.getDeclaredMethod("checkOp", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, String::class.java)
            val result = method.invoke(manager, 24, Binder.getCallingUid(), context
                    .packageName) as Int
            return AppOpsManager.MODE_ALLOWED == result || result == AppOpsManager.MODE_DEFAULT
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    } else {
        return true
    }
}

/**
 * 检查悬浮窗权限（>=23）
 */
@RequiresApi(Build.VERSION_CODES.M)
private fun checkFloatWindowPermission23(context: Context): Boolean {
    return Settings.canDrawOverlays(context)
}

/**
 * 开启悬浮窗权限（messenger）
 */
@TargetApi(Build.VERSION_CODES.M)
fun openFloatWindowPermission(activity: Activity) {
    try {
        val intent: Intent
        if (android.os.Build.MANUFACTURER == "Meizu") {
            //魅族手机
            intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("packageName", activity.packageName)
        } else {
            intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:" + activity.packageName)
        }
        activity.startActivityForResult(intent, 2)
    } catch (e: Exception) {
        e.printStackTrace()
    }

}
