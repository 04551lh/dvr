package com.adasplus.homepager.activate.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.homepager.R;
import com.adasplus.homepager.activate.mvp.contract.IActivateDeviceContract;
import com.adasplus.homepager.activate.mvp.presenter.ActivateDevicePresenter;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = ActivityPathConstant.ACTIVATE_DEVICE_PATH)
public class ActivateDeviceActivity extends BaseActivity implements IActivateDeviceContract.View {

    private ActivateDevicePresenter mActivateDevicePresenter;
    private ImageView mIvBack;
    private RecyclerView mRvActivatedPlatforms;
    private TextView mTvNoData;
    private TextView mTvAddNewPlatforms;
    private TextView mTvPhoneNumber;
    private TextView mTvLicensePlateNumber;
    private TextView mTvChassisNumber;
    private TextView mTvLicensePlateColor;
    private TextView mTvTerminalId;
    private TextView mTvProvincialDomainId;
    private TextView mTvCityAndCountyId;
    private TextView mTvPlatformList;
    private TextView mTvActionBarAddPlatforms;
    private ImageView mIvEditBasicInfoIcon;

    @Autowired(name = "type")
    String mType;

    @Override
    protected void init(Bundle savedInstanceState) {
        mActivateDevicePresenter = new ActivateDevicePresenter(this);
        mActivateDevicePresenter.initWidget();
        mActivateDevicePresenter.initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivateDevicePresenter.initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activate_device;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mRvActivatedPlatforms = (RecyclerView) findViewById(R.id.rv_activated_platforms);
        mTvNoData = (TextView) findViewById(R.id.tv_no_data);
        mTvAddNewPlatforms = (TextView) findViewById(R.id.tv_add_new_platforms);
        mTvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        mTvLicensePlateNumber = (TextView) findViewById(R.id.tv_license_plate_number);
        mTvChassisNumber = (TextView) findViewById(R.id.tv_chassis_number);
        mTvLicensePlateColor = (TextView) findViewById(R.id.tv_license_plate_color);
        mTvTerminalId = (TextView) findViewById(R.id.tv_terminal_id);
        mTvProvincialDomainId = (TextView) findViewById(R.id.tv_provincial_domain_id);
        mTvCityAndCountyId = (TextView) findViewById(R.id.tv_city_and_county_id);
        mTvPlatformList = (TextView) findViewById(R.id.tv_platform_list);
        mTvActionBarAddPlatforms = (TextView) findViewById(R.id.tv_action_bar_add_platforms);
        mIvEditBasicInfoIcon = findViewById(R.id.iv_edit_basic_info_icon);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public RecyclerView getRvActivatedPlatforms() {
        return mRvActivatedPlatforms;
    }

    @Override
    public TextView getTvNoData() {
        return mTvNoData;
    }

    @Override
    public TextView getTvAddNewPlatforms() {
        return mTvAddNewPlatforms;
    }

    @Override
    public TextView getTvPhoneNumber() {
        return mTvPhoneNumber;
    }

    @Override
    public TextView getTvLicensePlateNumber() {
        return mTvLicensePlateNumber;
    }

    @Override
    public TextView getTvChassisNumber() {
        return mTvChassisNumber;
    }

    @Override
    public TextView getTvLicensePlateColor() {
        return mTvLicensePlateColor;
    }

    @Override
    public TextView getTvTerminalId() {
        return mTvTerminalId;
    }

    @Override
    public TextView getTvProvincialDomainId() {
        return mTvProvincialDomainId;
    }

    @Override
    public TextView getTvCityAndCountyId() {
        return mTvCityAndCountyId;
    }

    @Override
    public TextView getTvPlatformList() {
        return mTvPlatformList;
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public TextView getTvActionBarAddPlatforms() {
        return mTvActionBarAddPlatforms;
    }

    @Override
    public ImageView getIvEditBasicInfoIcon() {
        return mIvEditBasicInfoIcon;
    }


    public void notifyPlatformsSizeShow(){
        mActivateDevicePresenter.notifyPlatformsSizeShow();
    }
}
