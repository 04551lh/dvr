package com.adasplus.base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Author:刘净辉
 * Date : 2019/10/18 10:25
 * Description :
 */
public abstract class BaseFragment extends Fragment {

    private Activity mActivity;
    private View mView = null;
    private boolean isPrepared;
    private boolean isVisible = true;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mView){
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null){
                parent.removeView(mView);
            }
        }else {
            mView = inflater.inflate(getLayoutId(), container, false);
            init(mView,savedInstanceState);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()){
            if (isVisible){
                isVisible = false;
                initPrepare();
            }
        }else {
            isVisible = true;
        }
    }

    protected abstract void onFirstUserVisible();//加载数据

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
        setViewData(view);
        setClickEvent(view);
    }

    //初始化控件
    protected abstract void init(View view,Bundle savedInstanceState);

    public void findViewById(View view){};

    public void setViewData(View view){};

    public void setClickEvent(View view){};

    protected abstract int getLayoutId();

    public Activity getAppCompatActivity() {
        return mActivity;
    }
}
