package com.adasplus.dvr_controller.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;

import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.SearchServiceRunStatusModel;
import com.adasplus.base.network.model.SystemInfoModel;
import com.adasplus.base.network.model.TerminalInfoModel;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.ToastUtil;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.mvp.contract.IHomeContract;
import com.alibaba.android.arouter.launcher.ARouter;
import java.util.List;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/18 11:36
 * Description :
 */
public class HomePresenter implements IHomeContract.Presenter, View.OnClickListener {

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;

    private Activity mActivity;
    private TextView mTvCarSpeed;
    private TextView mTvLocationInfo;
    private LinearLayout mLlDeviceConnect;
    private LinearLayout mLlVideoShow;
    private LinearLayout mLlPlatformsConnect;
    private LinearLayout mLlADASCalibration;
    private LinearLayout mLlTerminalSet;
    private ImageView mIvFourGSignalStatus;
    private ImageView mIvLocationStatus;
    private ImageView mIvFarLightStatus;
    private ImageView mIvNearLightStatus;
    private ImageView mIvLeftTurnStatus;
    private ImageView mIvRightTurnStatus;
    private ImageView mIvBrakeStatus;
    private ImageView mIvTargetsPlatformStatus;
    private boolean mUSB = true;

    public HomePresenter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void initData(SystemInfoModel systemInfoModel) {
        SystemInfoModel.GpsBean gps = systemInfoModel.getGps();
        double longitude = gps.getLongitude();
        double latitude = gps.getLatitude();
        List<SystemInfoModel.PlatformStatusArrayBean> platformStatusArray = systemInfoModel.getPlatformStatusArray();
        SystemInfoModel.VehicleStatusInfoBean vehicleStatusInfo = systemInfoModel.getVehicleStatusInfo();
        int nearTurnLightStatus = vehicleStatusInfo.getNearTurnLightStatus();
        int farTurnLightStatus = vehicleStatusInfo.getFarTurnLightStatus();
        int leftTurnLightStatus = vehicleStatusInfo.getLeftTurnLightStatus();
        int rightTurnLightStatus = vehicleStatusInfo.getRightTurnLightStatus();
        int brakeStatus = vehicleStatusInfo.getBrakeStatus();
        int speed = vehicleStatusInfo.getSpeed();
        SystemInfoModel.FourGBean fourG = systemInfoModel.getFourG();
        int fourGLSignalLevel = fourG.getFourGSignalLevel() / 6 ;

        //定位状态
        int gpsValid = gps.getGpsValid();
        if (ONE ==  gpsValid){
            mIvLocationStatus.setImageResource(R.drawable.location_signal_checked_icon);
        }else if (ZERO == gpsValid){
            mIvLocationStatus.setImageResource(R.drawable.location_signal_unchecked_icon);
        }

        //近光灯状态 0:已接入，未开启 1: 已接入，打开 2: 已接入，关闭
        if (ZERO == nearTurnLightStatus || TWO == nearTurnLightStatus){
            mIvNearLightStatus.setImageResource(R.drawable.near_light_unchecked_icon);
        }else if (ONE == nearTurnLightStatus){
            mIvNearLightStatus.setImageResource(R.drawable.near_light_checked_icon);
        }

        //远光灯状态
        if (ZERO == farTurnLightStatus || TWO == farTurnLightStatus){
            mIvFarLightStatus.setImageResource(R.drawable.far_light_unchecked_icon);
        }else if (ONE == farTurnLightStatus){
            mIvFarLightStatus.setImageResource(R.drawable.far_light_checked_icon);
        }

        //左转向灯状态
        if (ZERO == leftTurnLightStatus || TWO == leftTurnLightStatus){
            mIvLeftTurnStatus.setImageResource(R.drawable.left_turn_signal_unchecked_icon);
        }else if (ONE == leftTurnLightStatus){
            mIvLeftTurnStatus.setImageResource(R.drawable.left_turn_signal_checked_icon);
        }

        //右转向灯状态
        if (ZERO == rightTurnLightStatus || TWO == rightTurnLightStatus){
            mIvRightTurnStatus.setImageResource(R.drawable.right_turn_signal_unchecked_icon);
        }else if (ONE == rightTurnLightStatus){
            mIvRightTurnStatus.setImageResource(R.drawable.right_turn_signal_checked_icon);
        }

        //刹车状态
        if (ONE == brakeStatus){
            mIvBrakeStatus.setImageResource(R.drawable.brake_checked_icon);
        }else if (0 == brakeStatus){
            mIvBrakeStatus.setImageResource(R.drawable.brake_unchecked_icon);
        }

        //部标平台是否有连接的状态
        boolean isConnectTargetsPlatforms = false;
        for (int i = 0 ; i < platformStatusArray.size();i++){
            SystemInfoModel.PlatformStatusArrayBean platformStatusArrayBean = platformStatusArray.get(i);
            int connectStatus = platformStatusArrayBean.getConnectStatus();
            if (connectStatus == 2){
                isConnectTargetsPlatforms = true;
                break;
            }
        }

        if (isConnectTargetsPlatforms){
            mIvTargetsPlatformStatus.setImageResource(R.drawable.targets_platform_checked_icon);
        }else {
            mIvTargetsPlatformStatus.setImageResource(R.drawable.targets_platform_unchecked_icon);
        }

        //经纬度信息的展示
        if ( ZERO == longitude && ZERO == latitude){
            mTvLocationInfo.setText(R.string.no_car_info);
        }else {
            mTvLocationInfo.setText(String.format("%s°N,%s°E",String.valueOf((int)latitude),String.valueOf((int)longitude)));
        }

        //车辆速度的
        if (speed > 0){
            mTvCarSpeed.setText(String.format("%s km/h",String.valueOf(speed)));
        }else {
            mTvCarSpeed.setText(R.string.no_car_info);
        }

        if (ZERO == fourGLSignalLevel){
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_no_signal);
        }else if (ONE == fourGLSignalLevel){
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_signal_1);
        }else if (TWO == fourGLSignalLevel){
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_signal_2);
        }else if (THREE == fourGLSignalLevel){
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_signal_3);
        }else if (FOUR == fourGLSignalLevel){
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_signal_4);
        }else if (FIVE == fourGLSignalLevel){
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_signal_full_icon);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void findViewById(final View view) {
        mTvCarSpeed = view.findViewById(R.id.tv_car_speed);
        mTvLocationInfo = view.findViewById(R.id.tv_location_info);
        mLlDeviceConnect = view.findViewById(R.id.ll_device_connect);
        mLlVideoShow = view.findViewById(R.id.ll_video_show);
        mLlPlatformsConnect = view.findViewById(R.id.ll_platforms_connect);
        mLlADASCalibration = view.findViewById(R.id.ll_adas_calibration);
        mLlTerminalSet = view.findViewById(R.id.ll_terminal_set);
        mIvFourGSignalStatus = view.findViewById(R.id.iv_four_g_signal_status);
        mIvLocationStatus = view.findViewById(R.id.iv_location_status);
        mIvFarLightStatus = view.findViewById(R.id.iv_far_light_status);
        mIvNearLightStatus = view.findViewById(R.id.iv_near_light_status);
        mIvLeftTurnStatus = view.findViewById(R.id.iv_left_turn_status);
        mIvRightTurnStatus = view.findViewById(R.id.iv_right_turn_status);
        mIvBrakeStatus = view.findViewById(R.id.iv_brake_status);
        mIvTargetsPlatformStatus = view.findViewById(R.id.iv_targets_platform_status);
        CardView cr_platforms_basic_info = view.findViewById(R.id.cr_platforms_basic_info);
        cr_platforms_basic_info.bringToFront();
    }

    @Override
    public void setClickEvent(View view) {
        mLlDeviceConnect.setOnClickListener(this);
        mLlVideoShow.setOnClickListener(this);
        mLlPlatformsConnect.setOnClickListener(this);
        mLlADASCalibration.setOnClickListener(this);
        mLlTerminalSet.setOnClickListener(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setUSBStatus(boolean usbStatus) {
        mUSB = usbStatus;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_device_connect:
                startActivity(ActivityPathConstant.CONNECT_DEVICE_PATH);
                break;
            case R.id.ll_video_show:
                if (mUSB){
                    ToastUtil.showToast(mActivity,R.string.please_open_usb_network_share);
                    return;
                }
                startActivity(ActivityPathConstant.VIDEO_SHOW_PATH);
                break;
            case R.id.ll_adas_calibration:
                if (mUSB){
                    ToastUtil.showToast(mActivity,R.string.please_open_usb_network_share);
                    return;
                }
                startActivity(ActivityPathConstant.PARAMS_PATH);
                break;
            case R.id.ll_terminal_set:
                if (mUSB){
                    ToastUtil.showToast(mActivity,R.string.please_open_usb_network_share);
                    return;
                }
                startActivity(ActivityPathConstant.SETTINGS_PATH);
                break;
            case R.id.ll_platforms_connect:
                if (mUSB){
                    ToastUtil.showToast(mActivity,R.string.please_open_usb_network_share);
                    return;
                }
                BaseWrapper.getInstance().searchServiceRunStatus().subscribe(new Subscriber<SearchServiceRunStatusModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionUtils.exceptionHandling(mActivity,e);
                    }

                    @Override
                    public void onNext(SearchServiceRunStatusModel searchServiceRunStatusModel) {
                        //查询当前 808 服务的运行状态，如果808服务未启动的话，
                        // 会获取一些空数据或垃圾数据等，所以进行服务的状态判断
                        int jt808ServiceStatus = searchServiceRunStatusModel.getJt808Service();
                        if (jt808ServiceStatus == 0){
                            ToastUtil.showToast(mActivity,R.string.jt_808_service_status);
                            return;
                        }
                        getVehicleInfo();
                    }
                });
                break;
        }
    }

    private void getVehicleInfo() {
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
    }

    private void startActivity(String path){
        ARouter.getInstance()
                .build(path)
                .navigation();
    }

}
