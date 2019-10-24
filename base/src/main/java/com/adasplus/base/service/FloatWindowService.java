package com.adasplus.base.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.adasplus.base.R;
import com.adasplus.base.utils.WindowUtils;

/**
 * Author:刘净辉
 * Date : 2019/10/22 18:20
 * Description :
 */
public class FloatWindowService extends Service {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;

    @Override
    public void onCreate() {
        super.onCreate();
        int wh = (int) getResources().getDimension(R.dimen.dp_80);
        int x = (int) getResources().getDimension(R.dimen.dp_280);
        int y = (int) getResources().getDimension(R.dimen.dp_472);

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.width = wh;
        mLayoutParams.height = wh;
        mLayoutParams.x = x;
        mLayoutParams.y = y;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatWindow() {
        if (!WindowUtils.canOverDraw(this)){
            //TODO 跳转到设置界面
            Log.e("FloatWindowService","跳转到设置界面");
        }

        View view = View.inflate(this, R.layout.float_window_layout, null);
        view.setOnTouchListener(new FloatingOnTouchListener());
        mWindowManager.addView(view,mLayoutParams);
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {

        private int mX;
        private int mY;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mX = (int) event.getRawX();
                    mY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - mX;
                    int movedY = nowY - mY;
                    mX = nowX;
                    mY = nowY;
                    mLayoutParams.x = mLayoutParams.x + movedX;
                    mLayoutParams.y = mLayoutParams.y + movedY;
                    mWindowManager.updateViewLayout(view, mLayoutParams);
                    break;
            }
            return false;
        }
    }
}
