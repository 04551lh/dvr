package com.adasplus.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adasplus.base.utils.DisplayUtils;

/**
 * Author:刘净辉
 * Date : 2019/10/23 21:36
 * Description :
 */
public class SwipeRefreshView extends SwipeRefreshLayout {

    private float mLastX = 0;
    private float mLastY = 0;
    private boolean isMove = false;
    private  Context mContext;

    public SwipeRefreshView(@NonNull Context context) {
        super(context);
    }

    public SwipeRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mLastX = ev.getX();
            mLastY = ev.getY();
            isMove = false;
            return super.onInterceptTouchEvent(ev);
        }

        int newX = (int) Math.abs(ev.getX() - mLastX);
        int newY = (int) Math.abs(ev.getY() - mLastY);
        if (newX > newY) {
            if (newX >= 100) {
                isMove = false;
            }
        }else {
            if (newY >= 100){
                isMove =true;
                return true;
            }
        }
        return isMove;
    }
}
