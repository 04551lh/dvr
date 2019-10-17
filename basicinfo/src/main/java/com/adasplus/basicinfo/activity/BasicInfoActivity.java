package com.adasplus.basicinfo.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.basicinfo.R;
import com.adasplus.basicinfo.mvp.contract.IBasicInfoContract;
import com.adasplus.basicinfo.mvp.presenter.BasicInfoPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route( path = ActivityPathConstant.BASIC_INFO_PATH)
public class BasicInfoActivity extends BaseActivity implements IBasicInfoContract.View {

    private ImageView mIvBack;
    private TextView mTvCarInfo;
    private TextView mTvDriverInfo;
    private TextView mTvSystemInfo;

    @Override
    protected void init(Bundle savedInstanceState) {
        BasicInfoPresenter basicInfoPresenter = new BasicInfoPresenter(this);
        basicInfoPresenter.initData();
        basicInfoPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_info;
    }

    @Override
    protected void initWidget() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvCarInfo = (TextView) findViewById(R.id.tv_car_info);
        mTvDriverInfo = (TextView) findViewById(R.id.tv_driver_info);
        mTvSystemInfo = (TextView) findViewById(R.id.tv_system_info);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvCarInfo() {
        return mTvCarInfo;
    }

    @Override
    public TextView getTvDriverInfo() {
        return mTvDriverInfo;
    }

    @Override
    public TextView getTvSystemInfo() {
        return mTvSystemInfo;
    }
}
