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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adasplus.base.R;
import com.adasplus.base.utils.WindowUtils;

/**
 * Author:刘净辉
 * Date : 2019/10/22 18:20
 * Description :
 */
public class FloatWindowService extends Service implements View.OnClickListener {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private  View mView;


    private ImageView mIvFileExport;
    private ProgressBar mProgressbarFileExportLeft;
    private TextView mTvCurrentLeft;
    private ProgressBar mProgressbarFileExportRight;
    private TextView mTvCurrentRight;
    private TextView mTvFileExportTip;
    private ImageView mIvCloseRight;
    private int mWidth;




    @Override
    public void onCreate() {
        super.onCreate();
        mWidth = (int) getResources().getDimension(R.dimen.dp_238);
        int h = (int) getResources().getDimension(R.dimen.dp_80);
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
        mLayoutParams.width = mWidth;
        mLayoutParams.height = h;
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

        mView = View.inflate(this, R.layout.float_window_layout, null);
        mIvFileExport = mView.findViewById(R.id.iv_file_export);
        mProgressbarFileExportLeft = mView.findViewById(R.id.progressbar_file_export_left);
        mTvCurrentLeft = mView.findViewById(R.id.tv_current_left);
        mProgressbarFileExportRight = mView.findViewById(R.id.progressbar_file_export_right);
        mTvCurrentRight = mView.findViewById(R.id.tv_current_right );
        mTvFileExportTip = mView.findViewById(R.id.tv_file_export_tip );
        mIvCloseRight = mView.findViewById(R.id.iv_close_right );
        mIvFileExport.setOnClickListener(this);
        mIvCloseRight.setOnClickListener(this);
        mView.setOnTouchListener(new FloatingOnTouchListener());
        mWindowManager.addView(mView,mLayoutParams);
    }

    @Override
    public void onClick(View v) {
       if(v.getId() == R.id.iv_close_right){
           mProgressbarFileExportLeft.setVisibility(View.VISIBLE);
           mTvCurrentLeft.setVisibility(View.VISIBLE);
           mProgressbarFileExportRight.setVisibility(View.GONE);
           mTvCurrentRight.setVisibility(View.GONE);
           mTvFileExportTip.setVisibility(View.GONE);
           mIvCloseRight.setVisibility(View.GONE);

           mWidth = (int) getResources().getDimension(R.dimen.dp_80);
           mLayoutParams.width = mWidth;
           mWindowManager.updateViewLayout(mView, mLayoutParams);
        }else if(v.getId() == R.id.iv_file_export) {
           mProgressbarFileExportLeft.setVisibility(View.GONE);
           mTvCurrentLeft.setVisibility(View.GONE);
           mProgressbarFileExportRight.setVisibility(View.VISIBLE);
           mTvCurrentRight.setVisibility(View.VISIBLE);
           mTvFileExportTip.setVisibility(View.VISIBLE);
           mIvCloseRight.setVisibility(View.VISIBLE);

           mWidth = (int) getResources().getDimension(R.dimen.dp_238);
           mLayoutParams.width = mWidth;
           mWindowManager.updateViewLayout(mView, mLayoutParams);

       }
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
