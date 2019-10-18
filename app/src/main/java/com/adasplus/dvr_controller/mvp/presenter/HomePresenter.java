package com.adasplus.dvr_controller.mvp.presenter;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.TerminalInfoModel;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.WifiHelper;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.mvp.contract.IHomeContract;
import com.alibaba.android.arouter.launcher.ARouter;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/18 11:36
 * Description :
 */
public class HomePresenter implements IHomeContract.Presenter, View.OnClickListener {

    private Activity mActivity;
    private IHomeContract.View mHomeView;
    private TextView mTvCarSpeed;
    private TextView mTvLocationInfo;
    private LinearLayout mLlDeviceConnect;
    private LinearLayout mLlPlatformsConnect;
    private LinearLayout mLlFillParams;
    private LinearLayout mLlTerminalSet;
    private static final String DEVICE_WIFI_TAG = "ky_test";

    public HomePresenter(Activity activity, IHomeContract.View view) {
        mActivity = activity;
        mHomeView = view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void findViewById(View view) {
        mTvCarSpeed = view.findViewById(R.id.tv_car_speed);
        mTvLocationInfo = view.findViewById(R.id.tv_location_info);
        mLlDeviceConnect = view.findViewById(R.id.ll_device_connect);
        mLlPlatformsConnect = view.findViewById(R.id.ll_platforms_connect);
        mLlFillParams = view.findViewById(R.id.ll_fill_params);
        mLlTerminalSet = view.findViewById(R.id.ll_terminal_set);
    }

    @Override
    public void setClickEvent(View view) {
        mLlDeviceConnect.setOnClickListener(this);
        mLlPlatformsConnect.setOnClickListener(this);
        mLlFillParams.setOnClickListener(this);
        mLlTerminalSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_device_connect:
                ARouter.getInstance()
                        .build(ActivityPathConstant.CONNECT_DEVICE_PATH)
                        .navigation();
                break;
            case R.id.ll_fill_params:

                if (!isDeviceWifi()){
                    Toast.makeText(mActivity, R.string.please_connect_device, Toast.LENGTH_SHORT).show();
                    return;
                }
                ARouter.getInstance()
                        .build(ActivityPathConstant.PARAMS_PATH)
                        .navigation();
                break;
            case R.id.ll_terminal_set:
                if (!isDeviceWifi()){
                    Toast.makeText(mActivity, R.string.please_connect_device, Toast.LENGTH_SHORT).show();
                    return;
                }
                ARouter.getInstance()
                        .build(ActivityPathConstant.SETTINGS_PATH)
                        .navigation();
                break;
            case R.id.ll_platforms_connect:
                if (!isDeviceWifi()){
                    Toast.makeText(mActivity, R.string.please_connect_device, Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseWrapper.getInstance().getVehicleInfo().subscribe(new Subscriber<TerminalInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionUtils.exceptionHandling(mActivity,e);
                    }

                    @Override
                    public void onNext(TerminalInfoModel terminalInfoModel) {
                        String phoneNumber = terminalInfoModel.getPhoneNumber();
                        if (TextUtils.isEmpty(phoneNumber)){
                            ARouter.getInstance()
                                    .build(ActivityPathConstant.FILL_TERMINAL_INFO_PATH)
                                    .withString("type",ActivityPathConstant.FILL_TERMINAL_INFO)
                                    .withBoolean("isFillTerminalInfo",true)
                                    .navigation();
                        }else {
                            ARouter.getInstance()
                                    .build(ActivityPathConstant.ACTIVATE_DEVICE_PATH)
                                    .withString("type",ActivityPathConstant.ADD_NEW_PLATFORMS)
                                    .navigation();
                        }
                    }
                });
                break;
        }
    }

    private boolean isDeviceWifi(){
        WifiInfo wifiInfo = WifiHelper.getInstance().getConnectionWifiInfo();
        String wifiName = wifiInfo.getSSID();
        return wifiName.contains(DEVICE_WIFI_TAG);
    }
}
