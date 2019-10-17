package com.adasplus.basicinfo.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.basicinfo.R;
import com.adasplus.basicinfo.mvp.contract.ICarInfoContract;
import com.adasplus.basicinfo.mvp.presenter.CarInfoPresenter;

public class CarInfoActivity extends BaseActivity implements ICarInfoContract.View {

    private ImageView mIvBack;
    private TextView mTvPhoneNumber;
    private TextView mTvLicensePlateNumber;
    private TextView mTvChassisNumber;
    private TextView mTvLicensePlateColor;
    private TextView mTvTerminalId;
    private TextView mTvProvincialDomainId;
    private TextView mTvCityAndCountyId;
    private LinearLayout mLlCarInfo;
    private TextView mTvNoData;

    @Override
    protected void init(Bundle savedInstanceState) {
        CarInfoPresenter carInfoPresenter = new CarInfoPresenter(this);
        carInfoPresenter.initData();
        carInfoPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_info;
    }

    @Override
    protected void initWidget() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        mTvLicensePlateNumber = (TextView) findViewById(R.id.tv_license_plate_number);
        mTvChassisNumber = (TextView) findViewById(R.id.tv_chassis_number);
        mTvLicensePlateColor = (TextView) findViewById(R.id.tv_license_plate_color);
        mTvTerminalId = (TextView) findViewById(R.id.tv_terminal_id);
        mTvProvincialDomainId = (TextView) findViewById(R.id.tv_provincial_domain_id);
        mTvCityAndCountyId = (TextView) findViewById(R.id.tv_city_and_county_id);
        mLlCarInfo = findViewById(R.id.ll_car_info);
        mTvNoData = findViewById(R.id.tv_no_data);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
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
    public LinearLayout getLlCarInfo() {
        return mLlCarInfo;
    }

    @Override
    public TextView getTvNoData() {
        return mTvNoData;
    }
}
