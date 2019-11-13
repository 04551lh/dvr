package com.adasplus.dvr_controller.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.adasplus.base.base.BaseFragment;
import com.adasplus.base.network.model.SystemInfoModel;
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

    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        mHomePresenter = new HomePresenter(getAppCompatActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHomePresenter != null) {
            mHomePresenter.onResume();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        if (mHomePresenter != null) {
            mHomePresenter.findViewById(view);
        }
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        if (mHomePresenter != null) {
            mHomePresenter.setClickEvent(view);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mHomePresenter != null) {
            mHomePresenter.onPause();
        }
    }

    @Override
    public void onDestroy() {
        if (mHomePresenter != null) {
            mHomePresenter.onDestroy();
        }
        super.onDestroy();
    }

    public void initData(SystemInfoModel systemInfoModel) {
        if (mHomePresenter != null){
            mHomePresenter.initData(systemInfoModel);
        }
    }

    public void setUSBStatus(boolean usbStatus) {
        if (mHomePresenter != null){
            mHomePresenter.setUSBStatus(usbStatus);
        }
    }
}
