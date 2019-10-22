package com.adasplus.basicinfo.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.PatternUtils;
import com.adasplus.basicinfo.activity.DriverInfoActivity;
import com.adasplus.basicinfo.mvp.contract.IDriverInfoContract;
import com.adasplus.basicinfo.mvp.model.DriverInfoModel;
import com.adasplus.basicinfo.network.BasicInfoWrapper;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/11 18:53
 * Description :
 */
public class DriverInfoPresenter implements IDriverInfoContract.Presenter, View.OnClickListener {

    private IDriverInfoContract.View mDriverInfoView;
    private DriverInfoActivity mDriverInfoActivity;
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

    public DriverInfoPresenter(IDriverInfoContract.View view){
        mDriverInfoView = view;
        mDriverInfoActivity = (DriverInfoActivity) view;
    }

    @Override
    public void initData() {
        mTvNoData = mDriverInfoView.getTvNoData();
        mLlDriverInfo = mDriverInfoView.getLlDriverInfo();
        mTvDriverName = mDriverInfoView.getTvDriverName();
        mTvDriverSex = mDriverInfoView.getTvDriverSex();
        mTvDriverIdCard = mDriverInfoView.getTvDriverIdCard();
        mTvDriverIsLicense = mDriverInfoView.getTvDriverIsLicense();
        mTvValidityOfDriverIsLicense = mDriverInfoView.getTvValidityOfDriverIsLicense();
        mTvDriverNvq = mDriverInfoView.getTvDriverNvq();
        mTvCertificationAuthority = mDriverInfoView.getTvCertificationAuthority();
        mTvBindVehicle = mDriverInfoView.getTvBindVehicle();

        //获取司机信息
        BasicInfoWrapper.getInstance().getDriverInfo().subscribe(new Subscriber<DriverInfoModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mDriverInfoActivity,e);
            }

            @Override
            public void onNext(DriverInfoModel driverInfoModel) {
                Log.e("driverInfoModel","-----"+driverInfoModel.toString());

                String name = driverInfoModel.getName();

                if (TextUtils.isEmpty(name)){
                    mLlDriverInfo.setVisibility(View.GONE);
                    mTvNoData.setVisibility(View.VISIBLE);
                }else {
                    mLlDriverInfo.setVisibility(View.VISIBLE);
                    mTvNoData.setVisibility(View.GONE);
                }


                int sex = driverInfoModel.getSex();
                String id = driverInfoModel.getId();
                String drivingLicenseId = driverInfoModel.getDrivingLicenseId();
                String drivingLicenseValidTerm = driverInfoModel.getDrivingLicenseValidTerm();
                String qualificationCertificateId = driverInfoModel.getQualificationCertificateId();
                String affiliate = driverInfoModel.getAffiliate();
                String bindVehiclePlate = driverInfoModel.getBindVehiclePlate();

                mTvDriverName.setText(name);
                if (sex ==  0){
                    mTvDriverSex.setText("男");
                }else if (sex == 1){
                    mTvDriverSex.setText("女");
                }
                mTvDriverIdCard.setText(PatternUtils.idFormat(id));
                mTvDriverIsLicense.setText(PatternUtils.idFormat(drivingLicenseId));
                mTvValidityOfDriverIsLicense.setText(drivingLicenseValidTerm);
                mTvDriverNvq.setText(qualificationCertificateId);
                mTvCertificationAuthority.setText(affiliate);
                mTvBindVehicle.setText(bindVehiclePlate);
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mDriverInfoView.getIvBack();
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
