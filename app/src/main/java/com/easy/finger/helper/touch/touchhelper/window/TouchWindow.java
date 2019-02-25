package com.easy.finger.helper.touch.touchhelper.window;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.easy.finger.helper.touch.touchhelper.R;
import com.easy.finger.helper.touch.touchhelper.util.UtilsKt;


/**
 * 手势
 */
public class TouchWindow {

    private static TouchWindow touchWindow;

    private boolean isShowing;

    //窗口管理器
    private WindowManager wm;
    //view
    private View mView;
    //布局参数
    private WindowManager.LayoutParams layoutParams;

    private Context context;

    private TouchWindow() {

    }

    public static TouchWindow getInstence() {
        if (touchWindow == null) {
            synchronized (TouchWindow.class) {
                if (touchWindow == null) {
                    touchWindow = new TouchWindow();
                }
            }
        }
        return touchWindow;
    }

    public static void destroy() {
        touchWindow = null;
    }

    public void initView(final Context context) {
        if (wm != null) return;
        this.context = context.getApplicationContext();
        //窗口管理器
        wm = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
        //布局参数
        layoutParams = new WindowManager.LayoutParams();

        layoutParams.width = 40;
        layoutParams.height = UtilsKt.getScreen(context).y;

        layoutParams.gravity = Gravity.LEFT;

        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        //格式
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;

        mView = View.inflate(this.context, R.layout.touch_window_layout, null);

        mView.setFocusableInTouchMode(true);

///////////////////////////////初始化控件//////////////////////////////

    }


    /**
     * 显示锁屏
     */
    public void showlockScreen() {
        if (isShowing) return;
        isShowing = true;
        wm.addView(mView, layoutParams);
    }

    /**
     * 取消锁屏
     */
    public void hidelockScreen() {
        if (isShowing) wm.removeView(mView);
        isShowing = false;
    }

    public boolean isShow() {
        return isShowing;
    }

}
