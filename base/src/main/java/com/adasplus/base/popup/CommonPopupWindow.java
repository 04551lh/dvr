package com.adasplus.base.popup;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Author:刘净辉
 * Date : 2019/9/26 12:13
 * Description :
 */
public class CommonPopupWindow extends PopupWindow {
    final PopupController mController;

    @Override
    public int getWidth() {
        return mController.mPopupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return mController.mPopupView.getMeasuredHeight();
    }

    public interface ViewInterface {
        void getChildView(View view, int layoutResId);
    }

    private CommonPopupWindow(Context context) {
        mController = new PopupController(context, this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mController.setBackGroundLevel(1.0f);
    }

    public static class Builder {
        private final PopupController.PopupParams mParams;
        private ViewInterface mListener;

        public Builder(Context context) {
            mParams = new PopupController.PopupParams(context);
        }

        /**
         * @param layoutResId 设置PopupWindow 布局ID
         * @return Builder
         */
        public Builder setView(int layoutResId) {
            mParams.mView = null;
            mParams.mLayoutResId = layoutResId;
            return this;
        }

        /**
         * @param view 设置PopupWindow布局
         * @return Builder
         */
        public Builder setView(View view) {
            mParams.mView = view;
            mParams.mLayoutResId = 0;
            return this;
        }

        /**
         * 设置子View
         *
         * @param listener ViewInterface
         * @return Builder
         */
        public Builder setViewOnclickListener(ViewInterface listener) {
            this.mListener = listener;
            return this;
        }

        /**
         * 设置宽度和高度 如果不设置 默认是wrap_content
         *
         * @param width 宽
         * @return Builder
         */
        public Builder setWidthAndHeight(int width, int height) {
            mParams.mWidth = width;
            mParams.mHeight = height;
            return this;
        }

        /**
         * 设置背景灰色程度
         *
         * @param level 0.0f-1.0f
         * @return Builder
         */
        public Builder setBackGroundLevel(float level) {
            mParams.mIsShowBg = true;
            mParams.mBgLevel = level;
            return this;
        }

        /**
         * 是否可点击Outside消失
         *
         * @param touchable 是否可点击
         * @return Builder
         */
        public Builder setOutsideTouchable(boolean touchable) {
            mParams.isTouchable = touchable;
            return this;
        }

        /**
         * 设置动画
         *
         * @return Builder
         */
        public Builder setAnimationStyle(int animationStyle) {
            mParams.mIsShowAnim = true;
            mParams.animationStyle = animationStyle;
            return this;
        }

        public CommonPopupWindow create() {
            final CommonPopupWindow popupWindow = new CommonPopupWindow(mParams.mContext);
            mParams.apply(popupWindow.mController);
            if (mListener != null && mParams.mLayoutResId != 0) {
                mListener.getChildView(popupWindow.mController.mPopupView, mParams.mLayoutResId);
            }
            measureWidthAndHeight(popupWindow.mController.mPopupView);
            return popupWindow;
        }

        private void measureWidthAndHeight(View view) {
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
