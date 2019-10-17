package com.adasplus.basicinfo.mvp.presenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.TerminalInfoModel;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.basicinfo.R;
import com.adasplus.basicinfo.activity.CarInfoActivity;
import com.adasplus.basicinfo.mvp.contract.ICarInfoContract;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/11 18:21
 * Description :
 */
public class CarInfoPresenter implements ICarInfoContract.Presenter, View.OnClickListener {

    private ICarInfoContract.View mCarInfoView;
    private CarInfoActivity mCarInfoActivity;
    private TextView mTvPhoneNumber;
    private TextView mTvLicensePlateNumber;
    private TextView mTvChassisNumber;
    private TextView mTvLicensePlateColor;
    private TextView mTvTerminalId;
    private TextView mTvProvincialDomainId;
    private TextView mTvCityAndCountyId;
    private LinearLayout mLlCarInfo;
    private TextView mTvNoData;

    public CarInfoPresenter(ICarInfoContract.View view){
        mCarInfoView = view;
        mCarInfoActivity = (CarInfoActivity) view;
    }

    @Override
    public void initData() {
        mTvPhoneNumber = mCarInfoView.getTvPhoneNumber();
        mTvLicensePlateNumber = mCarInfoView.getTvLicensePlateNumber();
        mTvChassisNumber = mCarInfoView.getTvChassisNumber();
        mTvLicensePlateColor = mCarInfoView.getTvLicensePlateColor();
        mTvTerminalId = mCarInfoView.getTvTerminalId();
        mTvProvincialDomainId = mCarInfoView.getTvProvincialDomainId();
        mTvCityAndCountyId = mCarInfoView.getTvCityAndCountyId();
        mLlCarInfo = mCarInfoView.getLlCarInfo();
        mTvNoData = mCarInfoView.getTvNoData();


        BaseWrapper.getInstance().getVehicleInfo().subscribe(new Subscriber<TerminalInfoModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mCarInfoActivity,e);
            }

            @Override
            public void onNext(TerminalInfoModel terminalInfoModel) {
                String phoneNumber = terminalInfoModel.getPhoneNumber();
                String terminalId = terminalInfoModel.getTerminalId();
                String plateNumber = terminalInfoModel.getPlateNumber();
                String plateColor = terminalInfoModel.getPlateColor();
                //车架号
                String vin = terminalInfoModel.getVin();
                String provincialDomain = terminalInfoModel.getProvincialDomain();
                String cityDomain = terminalInfoModel.getCityDomain();

                if (TextUtils.isEmpty(phoneNumber)){
                    mLlCarInfo.setVisibility(View.GONE);
                    mTvNoData.setVisibility(View.VISIBLE);
                }else {
                    mLlCarInfo.setVisibility(View.VISIBLE);
                    mTvNoData.setVisibility(View.GONE);
                }

                //设置手机号
                mTvPhoneNumber.setText(phoneNumber);
                //设置终端Id
                mTvTerminalId.setText(terminalId);
                //设置车牌号
                mTvLicensePlateNumber.setText(plateNumber);
                //设置车辆颜色
                mTvLicensePlateColor.setText(plateColor);
                //车架号
                mTvChassisNumber.setText(vin);
                //省域Id
                mTvProvincialDomainId.setText(provincialDomain);
                //市县域Id
                mTvCityAndCountyId.setText(cityDomain);
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mCarInfoView.getIvBack();
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            mCarInfoActivity.finish();
        }
    }
}
