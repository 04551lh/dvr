package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IDeviceFormatContract;
import com.adasplus.homepager.set.mvp.presenter.DeviceFormatPresenter;

public class DeviceFormatActivity extends BaseActivity implements IDeviceFormatContract.View {

    private ImageView mIvDeviceFormatBack;
    private SwipeRefreshLayout mSwipeRefreshLayoutDeviceFormatSet;
    private RecyclerView mRvDeviceFormatList;
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
        mIvDeviceFormatBack =  findViewById(R.id.iv_device_format_back);
        mSwipeRefreshLayoutDeviceFormatSet =  findViewById(R.id.swipeRefreshLayout_device_format_set);
        mRvDeviceFormatList =  findViewById(R.id.rv_device_format_list);
    }

    @Override
    public ImageView getIvDeviceFormatBack() {
        return mIvDeviceFormatBack;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayoutDeviceFormatSet() {
        return mSwipeRefreshLayoutDeviceFormatSet;
    }

    @Override
    public RecyclerView getRvDeviceFormatList() {
        return mRvDeviceFormatList;
    }

}
