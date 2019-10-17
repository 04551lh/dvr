package com.adasplus.activate.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.activate.R;
import com.adasplus.activate.mvp.contract.IAddNewPlatformsContract;
import com.adasplus.activate.mvp.presenter.AddNewPlatformsPresenter;
import com.adasplus.base.base.BaseActivity;

public class AddNewPlatformsActivity extends BaseActivity implements IAddNewPlatformsContract.View {

    private ImageView mIvBack;
    private EditText mEtPlatformIpAddress;
    private EditText mEtPlatformPort;
    private TextView mTvSave;

    @Override
    protected void init(Bundle savedInstanceState) {
        AddNewPlatformsPresenter addNewPlatformsPresenter = new AddNewPlatformsPresenter(this);
        addNewPlatformsPresenter.initData();
        addNewPlatformsPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_platforms;
    }

    @Override
    protected void initWidget() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mEtPlatformIpAddress = (EditText) findViewById(R.id.et_platform_ip_address);
        mEtPlatformPort = (EditText) findViewById(R.id.et_platform_port);
        mTvSave = (TextView) findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public EditText getEtPlatformIpAddress() {
        return mEtPlatformIpAddress;
    }

    @Override
    public EditText getEtPlatformPort() {
        return mEtPlatformPort;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
    }
}
