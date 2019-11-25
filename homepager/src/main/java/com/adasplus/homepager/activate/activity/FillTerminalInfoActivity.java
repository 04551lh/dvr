package com.adasplus.homepager.activate.activity;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.homepager.R;
import com.adasplus.homepager.activate.mvp.contract.IFillTerminalInfoContract;
import com.adasplus.homepager.activate.mvp.model.AdministrativeRegionCodeModel;
import com.adasplus.homepager.activate.mvp.model.CarColorModel;
import com.adasplus.homepager.activate.mvp.model.LandmarkTypeModel;
import com.adasplus.homepager.activate.mvp.presenter.FillTerminalInfoPresenter;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.List;

@Route(path = ActivityPathConstant.FILL_TERMINAL_INFO_PATH)
public class FillTerminalInfoActivity extends BaseActivity implements IFillTerminalInfoContract.View {

    ImageView mIvBack;
    ImageView mIvCloseHintMessage;
    EditText mEtPlatformPhoneNumber;
    EditText mEtLicensePlateNumber;
    TextView mTvLicensePlateColor;
    EditText mEtTerminalId;
    TextView mTvProvincialDomainId;
    TextView mTvCityAndCountyId;
    TextView mTvSaveTerminalInfo;
    TextView mTvTitle;
    TextView mTvLandmarkType;
    EditText mEtChassisNumber;
    RelativeLayout mRlHintMessage;

    private FillTerminalInfoPresenter mFillTerminalInfoPresenter;

    @Autowired(name = "type")
    String mType;

    @Autowired(name = "isFillTerminalInfo")
    boolean isFillTerminalInfo;

    @Override
    protected void init(Bundle savedInstanceState) {
        mFillTerminalInfoPresenter = new FillTerminalInfoPresenter(this);
        mFillTerminalInfoPresenter.initData();
        mFillTerminalInfoPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fill_terminal_info;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mTvLandmarkType = findViewById(R.id.tv_landmark_type_id);
        mIvCloseHintMessage = findViewById(R.id.iv_close_hint_message);
        mEtPlatformPhoneNumber = findViewById(R.id.et_platform_phone_number);
        mEtLicensePlateNumber = findViewById(R.id.et_license_plate_number);
        mEtChassisNumber = findViewById(R.id.et_chassis_number);
        mTvLicensePlateColor = findViewById(R.id.tv_license_plate_color);
        mEtTerminalId = findViewById(R.id.et_terminal_id);
        mTvProvincialDomainId = findViewById(R.id.tv_provincial_domain_id);
        mTvCityAndCountyId = findViewById(R.id.tv_city_and_county_id);
        mTvSaveTerminalInfo = findViewById(R.id.tv_save_terminal_info);
        mTvTitle = findViewById(R.id.tv_title);
        mRlHintMessage = findViewById(R.id.rl_hint_message);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvLandmarkType() {
        return mTvLandmarkType;
    }

    @Override
    public ImageView getIvCloseHintMessage() {
        return mIvCloseHintMessage;
    }

    @Override
    public RelativeLayout getRlHintMessage() {
        return mRlHintMessage;
    }

    @Override
    public EditText getEtPlatformPhoneNumber() {
        return mEtPlatformPhoneNumber;
    }

    @Override
    public EditText getEtLicensePlateNumber() {
        return mEtLicensePlateNumber;
    }

    @Override
    public TextView getTvLicensePlateColor() {
        return mTvLicensePlateColor;
    }

    @Override
    public EditText getEtTerminalId() {
        return mEtTerminalId;
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
    public TextView getTvSaveTerminalInfo() {
        return mTvSaveTerminalInfo;
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public boolean isFillTerminalInfo() {
        return isFillTerminalInfo;
    }

    @Override
    public TextView getTvTitle() {
        return mTvTitle;
    }

    @Override
    public EditText getEtChassisNumber() {
        return mEtChassisNumber;
    }

    @Override
    public void initLicensePlateColor(List<CarColorModel> car_color) {
        mFillTerminalInfoPresenter.initLicensePlateColor(car_color);
    }

    @Override
    public void initLandmarkType(List<LandmarkTypeModel.LandmarkBean> landmark_type) {
        mFillTerminalInfoPresenter.initLandmarkTypeColor(landmark_type);
    }

    @Override
    public void initProvincialDomainId(List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList) {
        mFillTerminalInfoPresenter.initProvincialDomainId(administrativeRegionCodeModelList);
    }

    @Override
    public void showDefaultCityId(List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList) {
        mFillTerminalInfoPresenter.showDefaultCityId(administrativeRegionCodeModelList);
    }

    @Override
    public List<LandmarkTypeModel.LandmarkBean> getLandmarkTypeList() {
        return mFillTerminalInfoPresenter.getmLandmarkTypeList();
    }

    public void setAdministrativeRegionCodeData(List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList) {
        mFillTerminalInfoPresenter.setAdministrativeRegionCodeData(administrativeRegionCodeModelList);
    }
}
