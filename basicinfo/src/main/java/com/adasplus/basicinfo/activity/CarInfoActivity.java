package com.adasplus.basicinfo.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.basicinfo.R;
import com.adasplus.basicinfo.mvp.contract.ICarInfoContract;
import com.adasplus.basicinfo.mvp.presenter.CarInfoPresenter;
import com.adasplus.homepager.activate.mvp.model.CarInfoModel;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = ActivityPathConstant.CAR_INFO_PATH)
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
    private CarInfoPresenter mCarInfoPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        mCarInfoPresenter = new CarInfoPresenter(this);
        mCarInfoPresenter.initData();
        mCarInfoPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_info;
    }

    @Override
    protected void initWidget() {
        mIvBack =  findViewById(R.id.iv_back);
        mTvPhoneNumber =  findViewById(R.id.tv_phone_number);
        mTvLicensePlateNumber =  findViewById(R.id.tv_license_plate_number);
        mTvChassisNumber =  findViewById(R.id.tv_chassis_number);
        mTvLicensePlateColor =  findViewById(R.id.tv_license_plate_color);
        mTvTerminalId =  findViewById(R.id.tv_terminal_id);
        mTvProvincialDomainId =  findViewById(R.id.tv_provincial_domain_id);
        mTvCityAndCountyId =  findViewById(R.id.tv_city_and_county_id);
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

    @Override
    public void showDefaultPlateInfo(CarInfoModel carInfoModel) {
        mCarInfoPresenter.showDefaultPlateInfo(carInfoModel);
    }
}
