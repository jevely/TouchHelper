package com.easy.finger.helper.touch.touchhelper.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.easy.finger.helper.touch.touchhelper.R;
import com.easy.finger.helper.touch.touchhelper.tool.FunctionTool;
import com.easy.finger.helper.touch.touchhelper.util.UtilsKt;

import static com.easy.finger.helper.touch.touchhelper.util.UtilsKt.getScreen;

public class FloatBar extends View {

    private float startX, startY;
    private float moveX, moveY;
    private float backX, backY;
    private float endX, endY;
    private Paint paint;
    private Path path;
    private Paint paint2;
    private Point screen;
    private Bitmap bitmap;
    private Boolean showBack;

    public FloatBar(Context context) {
        this(context, null);
    }

    public FloatBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        screen = getScreen(context);

        startX = 0;
        startY = 0;

        endX = 0;
        endY = screen.y;

        moveX = 0;
        moveY = endY / 2;

        backX = 6;
        backY = screen.y / 2;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.touch_back));
        paint.setStrokeWidth(8);
//        paint.setStyle(Paint.Style.STROKE);

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setDither(true);
        paint2.setColor(Color.GREEN);

        path = new Path();

        showBack = false;

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.back_back);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(40, getScreen(getContext()).y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(startX, startY);
        path.quadTo(moveX, moveY, endX, endY);
        canvas.drawPath(path, paint);

//        canvas.drawLine(moveX, moveY, moveX + 2, moveY + 2, paint2);

        if (showBack) {
            //画back
            canvas.drawBitmap(bitmap, backX, backY, new Paint());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setViewSize(getScreen(getContext()).x);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX() / 4.0f;

                if (moveX > (screen.x / 20)) {
                    //显示back按钮
                    showBack = true;
                } else {
                    showBack = false;
                }

                if (moveX >= (screen.x / 12)) {
                    moveX = (screen.x / 12);
                }

                moveY = event.getY();

                backY = moveY;
                if (backY > getHeight() / 3 * 2) {
                    backY = getHeight() / 3 * 2;
                }
                if (backY < getHeight() / 3) {
                    backY = getHeight() / 3;
                }

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (moveX > (screen.x / 20)) {
                    Log.d("LJW", "触发返回");
                    FunctionTool.INSTANCE.funcationBack();
                }
                reset();
                setViewSize(40);
                break;
        }
        return true;
    }

    private void setViewSize(int viewX) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = viewX;
        setLayoutParams(layoutParams);
    }

    public void reset() {
        moveX = 0;
        moveY = endY / 2;
        showBack = false;
        invalidate();
    }
}
