package com.adasplus.base.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adasplus.base.R;
import com.adasplus.base.utils.DisplayUtils;

/**
 * Author:刘净辉
 * Date : 2019/9/18 14:21
 * Description : Dialog 基类，用来进行来处理所有的对话框显示处理。
 */
public abstract class BasicDialog extends DialogFragment {

    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String GRAVITY = "gravity";
    private static final String CANCEL = "out_cancel";
    private static final String THEME = "theme";
    private static final String ANIM = "anim";
    private static final String LAYOUT = "layoutId";

    private float mMargin;
    private int mWidth;
    private int mHeight;
    private float mDimAmount = 0.5f;
    private int mGravity = Gravity.CENTER;
    private boolean mOutCancel = true;
    private Context mContext;
     View mView;

    @StyleRes
    int mTheme = R.style.BasicDialog;

    @StyleRes
    private int mAnimStyle;

    @LayoutRes
    int mLayoutId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, initTheme());
        if (savedInstanceState != null) {
            mMargin = savedInstanceState.getFloat(MARGIN);
            mWidth = savedInstanceState.getInt(WIDTH);
            mHeight = savedInstanceState.getInt(HEIGHT);
            mDimAmount = savedInstanceState.getFloat(DIM);
            mGravity = savedInstanceState.getInt(GRAVITY);
            mOutCancel = savedInstanceState.getBoolean(CANCEL);
            mTheme = savedInstanceState.getInt(THEME);
            mAnimStyle = savedInstanceState.getInt(ANIM);
            mLayoutId = savedInstanceState.getInt(LAYOUT);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void convertView(ViewHolder viewHolder, BasicDialog dialog);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView != null){
            mContext = mView.getContext();
            convertView(ViewHolder.create(mView), this);
            return mView;
        }else if (mLayoutId != 0){
            mLayoutId = getLayoutId();
            View view = inflater.inflate(mLayoutId, null);
            mContext = view.getContext();
            convertView(ViewHolder.create(view), this);
            return view;
        }else {
            throw new IllegalArgumentException("CommonDialog's contentView is null");
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    /**
     * dialog 显示的基本设置，主要是设置的有：
     * mDimAmount : 对话框弹出来后，后面的背景的透明程度
     * mGravity : 对话框弹出来的位置（包括上下左右）
     * mAnimStyle : 设置的是对话框弹出（进入动画）和销毁（退出动画）的时候
     * mOutCancel: 设置的是点击对话框的空白区域是否取消对话框
     */
    @SuppressLint("RtlHardcoded")
    private void initParams() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.dimAmount = mDimAmount;
                if (mGravity != 0) {
                    windowAttributes.gravity = mGravity;
                }
                switch (mGravity) {
                    case Gravity.LEFT:
                    case (Gravity.LEFT | Gravity.BOTTOM):
                    case (Gravity.LEFT | Gravity.TOP):
                        if (mAnimStyle == 0) {
                            mAnimStyle = R.style.LeftAnimStyle;
                        }
                        break;
                    case Gravity.RIGHT:
                    case (Gravity.RIGHT | Gravity.BOTTOM):
                    case (Gravity.RIGHT | Gravity.TOP):
                        if (mAnimStyle == 0) {
                            mAnimStyle = R.style.RightAnimStyle;
                        }
                        break;
                    case Gravity.BOTTOM:
                        if (mAnimStyle == 0) {
                            mAnimStyle = R.style.BottomAnimStyle;
                        }
                        break;
                    case Gravity.TOP:
                        if (mAnimStyle == 0) {
                            mAnimStyle = R.style.TopAnimStyle;
                        }
                        break;
                }

                if (mWidth == 0) {
                    windowAttributes.width = DisplayUtils.getScreenWidth(mContext) - 2 * DisplayUtils.dp2px(mContext, mMargin);
                } else if (mWidth == -1) {
                    windowAttributes.width = WindowManager.LayoutParams.WRAP_CONTENT;
                } else {
                    windowAttributes.width = DisplayUtils.dp2px(mContext, mWidth);
                }

                if (mHeight == 0) {
                    windowAttributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
                } else {
                    windowAttributes.height = DisplayUtils.dp2px(mContext, mHeight);
                }

                window.setWindowAnimations(mAnimStyle);
                window.setAttributes(windowAttributes);
            }
            setCancelable(mOutCancel);
        }
    }

    /**
     * 进行横竖屏切换的时候 ，进行保存对话框中设置的信息，
     * 切换回来的时候，并进行获取对应的信息
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(MARGIN, mMargin);
        outState.putInt(WIDTH, mWidth);
        outState.putInt(HEIGHT, mHeight);
        outState.putFloat(DIM, mDimAmount);
        outState.putInt(GRAVITY, mGravity);
        outState.putBoolean(CANCEL, mOutCancel);
        outState.putInt(THEME, mTheme);
        outState.putInt(ANIM, mAnimStyle);
        outState.putInt(LAYOUT, mLayoutId);
    }

    public BasicDialog setMargin(float margin) {
        this.mMargin = margin;
        return this;
    }

    public BasicDialog setWidth(int width) {
        this.mWidth = width;
        return this;
    }

    public BasicDialog setHeight(int height) {
        this.mHeight = height;
        return this;
    }

    public BasicDialog setDimAmount(float dimAmount) {
        this.mDimAmount = dimAmount;
        return this;
    }

    public BasicDialog setGravity(int gravity) {
        this.mGravity = gravity;
        return this;
    }

    public BasicDialog setOutCancel(boolean outCancel) {
        this.mOutCancel = outCancel;
        return this;
    }

    public BasicDialog setTheme(int theme) {
        this.mTheme = theme;
        return this;
    }

    public BasicDialog setAnimStyle(int animStyle) {
        this.mAnimStyle = animStyle;
        return this;
    }

    public BasicDialog setLayoutId(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    public int initTheme() {
        return mTheme;
    }

    public BasicDialog show(FragmentManager manager) {
        FragmentTransaction beginTransaction = manager.beginTransaction();
        if (this.isAdded()) {
            beginTransaction.remove(this).commit();
        }
        beginTransaction.add(this, String.valueOf(System.currentTimeMillis()));
        beginTransaction.commitAllowingStateLoss();
        return this;
    }
}
