package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IDeviceFormatContract;
import com.adasplus.homepager.set.mvp.presenter.DeviceFormatPresenter;

public class DeviceFormatActivity extends BaseActivity implements IDeviceFormatContract.View {

    private ImageView mIvBack;
    private RecyclerView mRvDeviceFormatList;
    private TextView mTvDeviceFormatData;

    @Override
    protected void init(Bundle savedInstanceState) {
        DeviceFormatPresenter deviceFormatPresenter = new DeviceFormatPresenter(this);
        deviceFormatPresenter.initData();
        deviceFormatPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_format;
    }

    @Override
    protected void initWidget() {
        mIvBack =  findViewById(R.id.iv_back);
        mRvDeviceFormatList =  findViewById(R.id.rv_device_format_list);
        mTvDeviceFormatData =  findViewById(R.id.tv_device_format_data);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public RecyclerView getRvDeviceFormatList() {
        return mRvDeviceFormatList;
    }

    @Override
    public TextView getTvDeviceFormatData() {
        return mTvDeviceFormatData;
    }
}
