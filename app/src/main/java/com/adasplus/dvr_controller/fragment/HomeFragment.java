package com.adasplus.dvr_controller.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.adasplus.base.base.BaseFragment;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.mvp.contract.IHomeContract;
import com.adasplus.dvr_controller.mvp.presenter.HomePresenter;

/**
 * Author:刘净辉
 * Date : 2019/10/17 21:46
 * Description :
 */
public class HomeFragment extends BaseFragment implements IHomeContract.View {

    private HomePresenter mHomePresenter;

    @Override
    protected void onFirstUserVisible() {
        if (mHomePresenter != null){
            mHomePresenter.initData();
        }
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        mHomePresenter = new HomePresenter(getAppCompatActivity(), this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        if (mHomePresenter != null){
            mHomePresenter.findViewById(view);
        }
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        if (mHomePresenter != null){
            mHomePresenter.setClickEvent(view);
        }
    }
}
