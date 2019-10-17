package com.adasplus.base.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

/**
 * Author:刘净辉
 * Date : 2019/9/18 15:57
 */
public class CommonDialog extends BasicDialog {

    private ViewConvertListener mConvertListener;

    public static CommonDialog init() {
        return new CommonDialog();
    }

    @Override
    public int initTheme() {
        return super.initTheme();
    }

    @Override
    protected int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mConvertListener = savedInstanceState.getParcelable("listener");
        }
    }

    public CommonDialog setView(View view){
        mView = view;
        mLayoutId = 0;
        return this;
    }

    @Override
    protected void convertView(ViewHolder viewHolder, BasicDialog dialog) {
        if (mConvertListener != null) {
            mConvertListener.convertView(viewHolder, dialog);
        }
    }


    public CommonDialog setTheme(@StyleRes int theme) {
        mTheme = theme;
        return this;
    }

    public CommonDialog setLayoutId(@LayoutRes int layoutId) {
        mView = null;
        mLayoutId = layoutId;
        return this;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("listener", mConvertListener);
    }

    public CommonDialog setConvertListener(ViewConvertListener listener) {
        mConvertListener = listener;
        return this;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mConvertListener = null;
    }
}
