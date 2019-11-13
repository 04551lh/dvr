package com.adasplus.basicinfo.mvp.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.TerminalInfoModel;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.base.utils.ThreadPoolUtils;
import com.adasplus.basicinfo.R;
import com.adasplus.basicinfo.activity.CarInfoActivity;
import com.adasplus.basicinfo.mvp.contract.ICarInfoContract;
import com.adasplus.homepager.activate.mvp.model.AdministrativeRegionCodeModel;
import com.adasplus.homepager.activate.mvp.model.CarColorModel;
import com.adasplus.homepager.activate.mvp.model.CarInfoModel;
import com.adasplus.homepager.activate.mvp.model.LicensePlateColorModel;
import com.adasplus.homepager.utils.VehicleInfoUtil;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
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

    private String mPlateColorId = "null";
    private String mProvincialDomainId = "null";
    private String mCityDomainId = "null";
    private static final int WHAT = 1;
    private VehicleInfoHandler mVehicleInfoHandler;

    private static class VehicleInfoHandler extends Handler {

        private WeakReference<CarInfoActivity> mCarInfoActivity;

        VehicleInfoHandler(CarInfoActivity carInfoActivity){
            mCarInfoActivity = new WeakReference<>(carInfoActivity);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == WHAT) {
                CarInfoModel carInfoModel = (CarInfoModel) msg.obj;
                CarInfoActivity carInfoActivity = mCarInfoActivity.get();
                if (carInfoActivity != null){
                    carInfoActivity.showDefaultPlateInfo(carInfoModel);
                }
            }
        }
    }

    public CarInfoPresenter(ICarInfoContract.View view){
        mCarInfoView = view;
        mCarInfoActivity = (CarInfoActivity) view;
        mVehicleInfoHandler = new VehicleInfoHandler(mCarInfoActivity);
    }

    @Override
    public void initData() {

        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                String licensePlateFileName = "license_plate_color.json";
                String administrativeRegionCodeFileName = "administrative_region_code.json";

                String licensePlateColorData = VehicleInfoUtil.readVehicleData(mCarInfoActivity, licensePlateFileName);
                String administrativeRegionData = VehicleInfoUtil.readVehicleData(mCarInfoActivity, administrativeRegionCodeFileName);

                LicensePlateColorModel licensePlateColorModel = GsonUtils.getInstance().jsonToBean(licensePlateColorData, LicensePlateColorModel.class);
                List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList = GsonUtils.getInstance().jsonToList(administrativeRegionData, AdministrativeRegionCodeModel.class);
                CarInfoModel carInfoModel = new CarInfoModel(administrativeRegionCodeModelList, licensePlateColorModel.getCar_color());

                Message message = Message.obtain();
                message.what = WHAT;
                message.obj = carInfoModel;
                mVehicleInfoHandler.sendMessage(message);
            }
        });
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
                if(!mPlateColorId.equals("")){
                    mPlateColorId = plateColor;
                }


                //车架号
                mTvChassisNumber.setText(vin);
                //省域Id
                if(!mProvincialDomainId.equals("")){
                    mProvincialDomainId = provincialDomain;
                }
                //市县域Id
                if(!mCityDomainId.equals("")){
                    mCityDomainId = cityDomain;
                }
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

    public void showDefaultPlateInfo(CarInfoModel carInfoModel) {
        getDefaultPlateColor(carInfoModel.getCarColor());
        getDefaultProvincialIdOrCityId(carInfoModel.getAdministrativeRegionCodeModelList());
    }

    private void getDefaultPlateColor(List<CarColorModel> carColorModels){
        for (int i = 0; i < carColorModels.size(); i++) {
            if (mPlateColorId.equals(carColorModels.get(i).getPlate_code())){
                mTvLicensePlateColor.setText(carColorModels.get(i).getPlate_color());
            }
        }
    }

    private void getDefaultProvincialIdOrCityId(List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList){
        for (int i = 0; i < administrativeRegionCodeModelList.size(); i++) {
            AdministrativeRegionCodeModel administrativeRegionCodeModel = administrativeRegionCodeModelList.get(i);
            String provincialCode = administrativeRegionCodeModel.getCode();
            String provincialName = administrativeRegionCodeModel.getName();
            if (provincialCode.substring(0, 2).equals(mProvincialDomainId)){
                //省域Id
                mTvProvincialDomainId.setText(provincialName);
                List<AdministrativeRegionCodeModel.CityBean> city = administrativeRegionCodeModel.getCity();
                if (city != null && city.size() > 0) {
                    for (int j = 0; j < city.size(); j++) {
                        AdministrativeRegionCodeModel.CityBean cityBean = city.get(j);
                        List<AdministrativeRegionCodeModel.CityBean.AreaBean> area = cityBean.getArea();
                        if (area != null && area.size() > 0) {
                            for (int z = 0; z < area.size(); z++) {
                                AdministrativeRegionCodeModel.CityBean.AreaBean areaBean = area.get(z);
                                String areaBeanCode = areaBean.getCode();
                                if (mCityDomainId.equals(areaBeanCode.substring(2))){
                                    if (cityBean.getName().equals(mCarInfoActivity.getString(R.string.municipal_districts))){
                                        //市县域Id
                                        mTvCityAndCountyId.setText( String.format("%s-%s",administrativeRegionCodeModel.getName(),areaBean.getName()));
                                    }else {
                                        mTvCityAndCountyId.setText(String.format("%s-%s-%s",administrativeRegionCodeModel.getName(),cityBean.getName(),areaBean.getName()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}
