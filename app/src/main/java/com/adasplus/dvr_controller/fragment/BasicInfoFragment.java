package com.adasplus.dvr_controller.fragment;


import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.adasplus.base.base.BaseFragment;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.mvp.contract.IBasicInfoContract;
import com.adasplus.dvr_controller.mvp.presenter.BasicInfoPresenter;

public class BasicInfoFragment extends BaseFragment implements IBasicInfoContract.View {

    private BasicInfoPresenter mBasicInfoPresenter;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        mBasicInfoPresenter = new BasicInfoPresenter(getAppCompatActivity(), this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_basic_info;
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        if (mBasicInfoPresenter != null){
            mBasicInfoPresenter.findViewById(view);
        }
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        if (mBasicInfoPresenter != null){
            mBasicInfoPresenter.setClickEvent(view);
        }
    }
}
