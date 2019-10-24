package com.adasplus.homepager.set.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IWarningsSetContract;
import com.adasplus.homepager.set.mvp.presenter.WarningsSetPresenter;

public class WarningsSetActivity extends BaseActivity implements IWarningsSetContract.View {

    private ImageView mIvBack;
    private LinearLayout mLlAdas;
    private LinearLayout mLlDms;

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
        mIvBack =  findViewById(R.id.iv_back);
        mLlAdas =  findViewById(R.id.ll_adas);
        mLlDms =  findViewById(R.id.ll_dms);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public LinearLayout getLlAdas() {
        return mLlAdas;
    }

    @Override
    public LinearLayout getLlDms() {
        return mLlDms;
    }

}
