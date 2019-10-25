package com.adasplus.homepager.set.activity;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ICANSetContract;
import com.adasplus.homepager.set.mvp.presenter.CANSetPresenter;

/**
 * TODO 该版本本类的功能这个版本不做，后续版本有可能会继续实现
 */
public class CANSetActivity extends BaseActivity implements ICANSetContract.View {

    private ImageView mIvBack;
    private TextView mTvSelectChannelNumber;
    private EditText mEtRateValue;
    private TextView mTvSave;

    @Override
    protected void init(Bundle savedInstanceState) {
        CANSetPresenter canSetPresenter = new CANSetPresenter(this);
        canSetPresenter.initData();
        canSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_canset;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mTvSelectChannelNumber =  findViewById(R.id.tv_select_channel_number);
        mEtRateValue =  findViewById(R.id.et_rate_value);
        mTvSave =  findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvSelectChannelNumber() {
        return mTvSelectChannelNumber;
    }

    @Override
    public EditText getEtRateValue() {
        return mEtRateValue;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
    }
}
