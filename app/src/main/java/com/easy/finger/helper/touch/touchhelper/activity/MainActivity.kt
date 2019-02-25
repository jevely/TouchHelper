package com.easy.finger.helper.touch.touchhelper.activity

import android.content.Intent
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.easy.finger.helper.touch.touchhelper.R
import com.easy.finger.helper.touch.touchhelper.util.*
import com.easy.finger.helper.touch.touchhelper.window.TouchWindow

class MainActivity : AppCompatActivity() {

    lateinit var access_re: RelativeLayout
    lateinit var access_check: ImageView
    lateinit var float_re: RelativeLayout
    lateinit var float_check: ImageView
    lateinit var open_bt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TouchWindow.getInstence().initView(this)
        init()
    }

    private fun init() {
        open_bt = findViewById(R.id.open_bt)
        access_re = findViewById(R.id.access_re)
        access_check = findViewById(R.id.access_check)
        float_re = findViewById(R.id.float_re)
        float_check = findViewById(R.id.float_check)

        val float = checkFloatPermission(this)
        val access = isAccessibilitySettingsOn(this)

        openFunction(access, float)

        if (access) {
            access_check.setImageResource(R.drawable.ic_check_yes)
            access_re.setOnClickListener {
                Toast.makeText(this, "权限已经开启", Toast.LENGTH_SHORT).show()
            }
        } else {
            access_check.setImageResource(R.drawable.ic_check)
            access_re.setOnClickListener {
                goToOpenAccess(this)
            }
        }

        if (float) {
            float_check.setImageResource(R.drawable.ic_check_yes)
            float_re.setOnClickListener {
                Toast.makeText(this, "权限已经开启", Toast.LENGTH_SHORT).show()
            }
        } else {
            float_check.setImageResource(R.drawable.ic_check)
            float_re.setOnClickListener {
                openFloatWindowPermission(this)
            }
        }

        open_bt.setOnClickListener {
            TouchWindow.getInstence().showlockScreen()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PermissionNum.PERMISSION_ACCESS) {
            //辅助功能
            val success = isAccessibilitySettingsOn(this)
            if (success) {
                access_check.setImageResource(R.drawable.ic_check_yes)
                access_re.setOnClickListener {
                    Toast.makeText(this, "权限已经开启", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "权限开启成功", Toast.LENGTH_SHORT).show()
                val flost = checkFloatPermission(this)
                openFunction(success, flost)
            } else {
                Toast.makeText(this, "权限开启失败", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == PermissionNum.PERMISSION_FLOAT) {
            //悬浮窗权限
            Thread(FloatCheckThr()).start()
        }
    }

    inner class FloatCheckThr : Runnable {
        override fun run() {
            try {
                Thread.sleep(200)
                handler.sendEmptyMessage(0);
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 0) {
                //悬浮窗检查
                val success = checkFloatPermission(this@MainActivity)
                if (success) {
                    float_check.setImageResource(R.drawable.ic_check_yes)
                    float_re.setOnClickListener {
                        Toast.makeText(this@MainActivity, "权限已经开启", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(this@MainActivity, "权限开启成功", Toast.LENGTH_SHORT).show()

                    val access = isAccessibilitySettingsOn(this@MainActivity)
                    openFunction(access, success)
                } else {
                    Toast.makeText(this@MainActivity, "权限开启失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun openFunction(access: Boolean, float: Boolean) {
        if (access && float) {
            TouchWindow.getInstence().showlockScreen()
            Toast.makeText(this, "功能已开启，请从手机屏幕左边滑动测试", Toast.LENGTH_SHORT).show()
        }
    }
}
