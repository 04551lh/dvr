package com.adasplus.settings.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.INetworkSetContract;
import com.adasplus.settings.mvp.presenter.NetworkSetPresenter;

public class NetworkSetActivity extends BaseActivity implements INetworkSetContract.View {

    private ImageView mIvBack;
    private EditText mEtWirelessIpAddress;
    private EditText mEtWirelessMaskCode;
    private EditText mEtWirelessGateway;
    private EditText mEtWirelessMacAddress;
    private EditText mEtWiredIpAddress;
    private EditText mEtWiredMaskCode;
    private EditText mEtWiredGateway;
    private EditText mEtWiredMacAddress;
    private TextView mTvSave;

    @Override
    protected void init(Bundle savedInstanceState) {
        NetworkSetPresenter networkSetPresenter = new NetworkSetPresenter(this);
        networkSetPresenter.initData();
        networkSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_network_set;
    }

    @Override
    protected void initWidget() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mEtWirelessIpAddress = (EditText) findViewById(R.id.et_wireless_ip_address);
        mEtWirelessMaskCode = (EditText) findViewById(R.id.et_wireless_mask_code);
        mEtWirelessGateway = (EditText) findViewById(R.id.et_wireless_gateway);
        mEtWirelessMacAddress = (EditText) findViewById(R.id.et_wireless_mac_address);
        mEtWiredIpAddress = (EditText) findViewById(R.id.et_wired_ip_address);
        mEtWiredMaskCode = (EditText) findViewById(R.id.et_wired_mask_code);
        mEtWiredGateway = (EditText) findViewById(R.id.et_wired_gateway);
        mEtWiredMacAddress = (EditText) findViewById(R.id.et_wired_mac_address);
        mTvSave = (TextView) findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public EditText getEtWirelessIpAddress() {
        return mEtWirelessIpAddress;
    }

    @Override
    public EditText getEtWirelessMaskCode() {
        return mEtWirelessMaskCode;
    }

    @Override
    public EditText getEtWirelessGateway() {
        return mEtWirelessGateway;
    }

    @Override
    public EditText getEtWirelessMacAddress() {
        return mEtWirelessMacAddress;
    }

    @Override
    public EditText getEtWiredIpAddress() {
        return mEtWiredIpAddress;
    }

    @Override
    public EditText getEtWiredMaskCode() {
        return mEtWiredMaskCode;
    }

    @Override
    public EditText getEtWiredGateway() {
        return mEtWiredGateway;
    }

    @Override
    public EditText getEtWiredMacAddress() {
        return mEtWiredMacAddress;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
    }
}
