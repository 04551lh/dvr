package com.adasplus.settings.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.IWarningsSetContract;
import com.adasplus.settings.mvp.presenter.WarningsSetPresenter;

public class WarningsSetActivity extends BaseActivity implements IWarningsSetContract.View {

    private ImageView mIvBack;
    private TextView mTvAdas;
    private TextView mTvDms;

    @Override
    protected void init(Bundle savedInstanceState) {
        WarningsSetPresenter warningsSetPresenter = new WarningsSetPresenter(this);
        warningsSetPresenter.initData();
        warningsSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_warnings_set;
    }

    @Override
    protected void initWidget() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvAdas = (TextView) findViewById(R.id.tv_adas);
        mTvDms = (TextView) findViewById(R.id.tv_dms);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvAdas() {
        return mTvAdas;
    }

    @Override
    public TextView getTvDms() {
        return mTvDms;
    }

}
