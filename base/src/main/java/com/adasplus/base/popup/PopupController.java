package com.adasplus.base.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Author:刘净辉
 * Date : 2019/9/26 12:09
 * Description :
 */
public class PopupController {

    private int mLayoutResId;
    private Context mContext;
    private PopupWindow mPopupWindow;
    View mPopupView;
    private View mView;
    private Window mWindow;

    PopupController(Context context, PopupWindow popupWindow) {
        mContext = context;
        mPopupWindow = popupWindow;
    }

    public void setView(int layoutResId) {
        mView = null;
        mLayoutResId = layoutResId;
        installContent();
    }

    public void setView(View view) {
        mView = view;
        mLayoutResId = 0;
        installContent();
    }

    private void installContent() {
        if (mLayoutResId != 0) {
            mPopupView = LayoutInflater.from(mContext).inflate(mLayoutResId, null);
        } else if (mView != null) {
            mPopupView = mView;
        }
        mPopupWindow.setContentView(mPopupView);
    }

    /**
     * 设置宽度
     *
     * @param width  宽
     * @param height 高
     */
    private void setWidthAndHeight(int width, int height) {
        if (width == 0 || height == 0) {
            //如果没设置宽高，默认是WRAP_CONTENT
            mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            mPopupWindow.setWidth(width);
            mPopupWindow.setHeight(height);
        }
    }


    /**
     * 设置背景灰色程度
     *
     * @param level 0.0f-1.0f
     */
    void setBackGroundLevel(float level) {
        mWindow = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.alpha = level;
        mWindow.setAttributes(params);
    }


    /**
     * 设置动画
     */
    private void setAnimationStyle(int animationStyle) {
        mPopupWindow.setAnimationStyle(animationStyle);
    }

    /**
     * 设置Outside是否可点击
     *
     * @param touchable 是否可点击
     */
    private void setOutsideTouchable(boolean touchable) {
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置透明背景
        mPopupWindow.setOutsideTouchable(touchable);//设置outside可点击
        mPopupWindow.setFocusable(touchable);
    }


    static class PopupParams {
        public int mLayoutResId;//布局id
        public Context mContext;
        public int mWidth, mHeight;//弹窗的宽和高
        public boolean mIsShowBg, mIsShowAnim;
        public float mBgLevel;//屏幕背景灰色程度
        public int animationStyle;//动画Id
        public View mView;
        public boolean isTouchable = true;

        public PopupParams(Context mContext) {
            this.mContext = mContext;
        }

        public void apply(PopupController controller) {
            if (mView != null) {
                controller.setView(mView);
            } else if (mLayoutResId != 0) {
                controller.setView(mLayoutResId);
            } else {
                throw new IllegalArgumentException("PopupView's contentView is null");
            }
            controller.setWidthAndHeight(mWidth, mHeight);
            controller.setOutsideTouchable(isTouchable);//设置outside可点击
            if (mIsShowBg) {
                //设置背景
                controller.setBackGroundLevel(mBgLevel);
            }
            if (mIsShowAnim) {
                controller.setAnimationStyle(animationStyle);
            }
        }
    }
}
