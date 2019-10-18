package com.adasplus.basicinfo.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.basicinfo.R;
import com.adasplus.basicinfo.mvp.contract.IDriverInfoContract;
import com.adasplus.basicinfo.mvp.presenter.DriverInfoPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = ActivityPathConstant.DRIVER_INFO_PATH)
public class DriverInfoActivity extends BaseActivity implements IDriverInfoContract.View {

    private ImageView mIvBack;
    private TextView mTvNoData;
    private LinearLayout mLlDriverInfo;
    private TextView mTvDriverName;
    private TextView mTvDriverSex;
    private TextView mTvDriverIdCard;
    private TextView mTvDriverIsLicense;
    private TextView mTvValidityOfDriverIsLicense;
    private TextView mTvDriverNvq;
    private TextView mTvCertificationAuthority;
    private TextView mTvBindVehicle;

    @Override
    protected void init(Bundle savedInstanceState) {
        DriverInfoPresenter driverInfoPresenter = new DriverInfoPresenter(this);
        driverInfoPresenter.initData();
        driverInfoPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_driver_info;
    }

    @Override
    protected void initWidget() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvNoData = (TextView) findViewById(R.id.tv_no_data);
        mLlDriverInfo = (LinearLayout) findViewById(R.id.ll_driver_info);
        mTvDriverName = (TextView) findViewById(R.id.tv_driver_name);
        mTvDriverSex = (TextView) findViewById(R.id.tv_driver_sex);
        mTvDriverIdCard = (TextView) findViewById(R.id.tv_driver_id_card);
        mTvDriverIsLicense = (TextView) findViewById(R.id.tv_driver_is_license);
        mTvValidityOfDriverIsLicense = (TextView) findViewById(R.id.tv_validity_of_driver_is_license);
        mTvDriverNvq = (TextView) findViewById(R.id.tv_driver_nvq);
        mTvCertificationAuthority = (TextView) findViewById(R.id.tv_certification_authority);
        mTvBindVehicle = (TextView) findViewById(R.id.tv_bind_vehicle);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvNoData() {
        return mTvNoData;
    }

    @Override
    public LinearLayout getLlDriverInfo() {
        return mLlDriverInfo;
    }

    @Override
    public TextView getTvDriverName() {
        return mTvDriverName;
    }

    @Override
    public TextView getTvDriverSex() {
        return mTvDriverSex;
    }

    @Override
    public TextView getTvDriverIdCard() {
        return mTvDriverIdCard;
    }

    @Override
    public TextView getTvDriverIsLicense() {
        return mTvDriverIsLicense;
    }

    @Override
    public TextView getTvValidityOfDriverIsLicense() {
        return mTvValidityOfDriverIsLicense;
    }

    @Override
    public TextView getTvDriverNvq() {
        return mTvDriverNvq;
    }

    @Override
    public TextView getTvCertificationAuthority() {
        return mTvCertificationAuthority;
    }

    @Override
    public TextView getTvBindVehicle() {
        return mTvBindVehicle;
    }
}
