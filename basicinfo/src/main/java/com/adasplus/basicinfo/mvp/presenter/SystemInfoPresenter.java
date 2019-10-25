package com.adasplus.basicinfo.mvp.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.SystemInfoModel;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.basicinfo.R;
import com.adasplus.basicinfo.activity.SystemInfoActivity;
import com.adasplus.basicinfo.adapter.StorageAdapter;
import com.adasplus.basicinfo.mvp.contract.ISystemInfoContract;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/14 18:18
 * Description :
 */
public class SystemInfoPresenter implements ISystemInfoContract.Presenter, View.OnClickListener {

    public static final int WHAT = 1;
    public static final int DELAY_MILLIS = 1000;
    private ISystemInfoContract.View mSystemInfoView;
    private SystemInfoActivity mSystemInfoActivity;
    private static SystemInfoHandler mSystemInfoHandler;
    private ImageView mIvBack;
    private TextView mTvSoftwareVersion;
    private TextView mTvHardwareVersion;
    private TextView mTvMcuVersion;
    private TextView mTvDiskTemperature;
    private TextView mTvExternalTemperature;
    private TextView mTvVehicleMileage;
    private TextView mTvSpeed;
    private TextView mTvPulseNumber;
    private TextView mTvGpsModel;
    private TextView mTvDirectionalAntenna;
    private TextView mTvGpsSignal;
    private TextView mTvLatitudeAndLongitude;
    private TextView mTvFourGModel;
    private TextView mTvFourGStatus;
    private TextView mTvFourGSignal;
    private TextView mTvSimCardNumber;
    private TextView mTvCenterConnectStatus;
    private RecyclerView mRvTargetsList;
    private TextView mTvDns;
    private TextView mTvIntercomSerialId;
    private TextView mTvImeiNumber;
    private TextView mTvSerialNumber;
    private TextView mTvCameraStatus;
    private TextView mTvMainPowerSupplyState;
    private TextView mTvLeftTurnSignalState;
    private TextView mTvRightTurnSignalState;
    private TextView mTvDippedHeadlightState;
    private TextView mTvHighBeamState;
    private TextView mTvStateOfTheBrake;
    private TextView mTvStateOfTheMicrophone;
    private TextView mTvStateOfTheSpeaker;
    private TextView mTvStateOfThePrinter;
    private TextView mTvAllInterfaceState;
    private RecyclerView mRvStorageList;
    private TextView mTvBatteryVoltage;
    private TextView mTvBatteryStatus;
    private TextView mTvMchSerial;
    private TextView mTvSerialDevice;
    private String mNoAccess;
    private String mHaveAccess;
    private String mOpen;
    private String mClose;
    private String mNormal;
    private String mException;
    private StorageAdapter mStorageAdapter;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id ==  R.id.iv_back){
            mSystemInfoActivity.finish();
        }
    }

    private static class SystemInfoHandler extends Handler {

        private WeakReference<SystemInfoActivity> mSystemInfoWeak;

        private SystemInfoHandler(SystemInfoActivity systemInfoActivity) {
            mSystemInfoWeak = new WeakReference<>(systemInfoActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            SystemInfoActivity systemInfoActivity = mSystemInfoWeak.get();
            int what = msg.what;
            if (what ==  WHAT){
                if (systemInfoActivity != null){
                    systemInfoActivity.initData();
                }
                mSystemInfoHandler.sendEmptyMessageDelayed(WHAT, DELAY_MILLIS);
            }
        }
    }

    public SystemInfoPresenter(ISystemInfoContract.View view) {
        mSystemInfoView = view;
        mSystemInfoActivity = (SystemInfoActivity) view;
        mSystemInfoHandler = new SystemInfoHandler(mSystemInfoActivity);
    }

    @Override
    public void initWidget() {
        mIvBack = mSystemInfoView.getIvBack();
        mTvSoftwareVersion = mSystemInfoView.getTvSoftwareVersion();
        mTvHardwareVersion = mSystemInfoView.getTvHardwareVersion();
        mTvMcuVersion = mSystemInfoView.getTvMcuVersion();
        mTvDiskTemperature = mSystemInfoView.getTvDiskTemperature();
        mTvExternalTemperature = mSystemInfoView.getTvExternalTemperature();
        mTvVehicleMileage = mSystemInfoView.getTvVehicleMileage();
        mTvSpeed = mSystemInfoView.getTvSpeed();
        mTvPulseNumber = mSystemInfoView.getTvPulseNumber();
        mTvGpsModel = mSystemInfoView.getTvGpsModel();
        mTvDirectionalAntenna = mSystemInfoView.getTvDirectionalAntenna();
        mTvGpsSignal = mSystemInfoView.getTvGpsSignal();
        mTvLatitudeAndLongitude = mSystemInfoView.getTvLatitudeAndLongitude();
        mTvFourGModel = mSystemInfoView.getTvFourGModel();
        mTvFourGStatus = mSystemInfoView.getTvFourGStatus();
        mTvFourGSignal = mSystemInfoView.getTvFourGSignal();
        mTvSimCardNumber = mSystemInfoView.getTvSimCardNumber();
        mTvCenterConnectStatus = mSystemInfoView.getTvCenterConnectStatus();
        mRvTargetsList = mSystemInfoView.getRvTargetsList();
        mTvDns = mSystemInfoView.getTvDns();
        mTvIntercomSerialId = mSystemInfoView.getTvIntercomSerialId();
        mTvImeiNumber = mSystemInfoView.getTvImeiNumber();
        mTvSerialNumber = mSystemInfoView.getTvSerialNumber();
        mTvCameraStatus = mSystemInfoView.getTvCameraStatus();
        mTvMainPowerSupplyState = mSystemInfoView.getTvMainPowerSupplyState();
        mTvLeftTurnSignalState = mSystemInfoView.getTvLeftTurnSignalState();
        mTvRightTurnSignalState = mSystemInfoView.getTvRightTurnSignalState();
        mTvDippedHeadlightState = mSystemInfoView.getTvDippedHeadlightState();
        mTvHighBeamState = mSystemInfoView.getTvHighBeamState();
        mTvStateOfTheBrake = mSystemInfoView.getTvStateOfTheBrake();
        mTvStateOfTheMicrophone = mSystemInfoView.getTvStateOfTheMicrophone();
        mTvStateOfTheSpeaker = mSystemInfoView.getTvStateOfTheSpeaker();
        mTvStateOfThePrinter = mSystemInfoView.getTvStateOfThePrinter();
        mTvAllInterfaceState = mSystemInfoView.getTvAllInterfaceState();
        mRvStorageList = mSystemInfoView.getRvStorageList();
        mTvBatteryVoltage = mSystemInfoView.getTvBatteryVoltage();
        mTvBatteryStatus = mSystemInfoView.getTvBatteryStatus();
        mTvMchSerial = mSystemInfoView.getTvMchSerial();
        mTvSerialDevice = mSystemInfoView.getTvSerialDevice();

        mNoAccess = mSystemInfoActivity.getResources().getString(R.string.no_access);
        mHaveAccess = mSystemInfoActivity.getResources().getString(R.string.have_access);
        mOpen = mSystemInfoActivity.getResources().getString(R.string.open);
        mClose = mSystemInfoActivity.getResources().getString(R.string.close);
        mNormal = mSystemInfoActivity.getResources().getString(R.string.normal);
        mException = mSystemInfoActivity.getResources().getString(R.string.exception);

        mRvStorageList.setLayoutManager(new LinearLayoutManager(mSystemInfoActivity,RecyclerView.VERTICAL,false));
        mRvStorageList.addItemDecoration(new DividerItemDecoration(mSystemInfoActivity,RecyclerView.VERTICAL));
        mStorageAdapter = new StorageAdapter();
    }

    @Override
    public void initData() {

        BaseWrapper.getInstance().getSystemInfo().subscribe(new Subscriber<SystemInfoModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mSystemInfoActivity,e);
            }

            @Override
            public void onNext(SystemInfoModel systemInfoModel) {

                /*------------------------------ 终端信息 -----------------------------*/
                SystemInfoModel.TerminalInfoBean terminalInfo = systemInfoModel.getTerminalInfo();
                //软件版本
                String softWareVersion = terminalInfo.getSoftWareVersion();
                //硬件版本
                String hardWareVersion = terminalInfo.getHardWareVersion();
                //MCU 版本
                String mcuVersion = terminalInfo.getMCUVersion();
                //终端id
                String terminalId = terminalInfo.getTerminalId();

                mTvSoftwareVersion.setText(softWareVersion);
                mTvHardwareVersion.setText(hardWareVersion);
                mTvMcuVersion.setText(mcuVersion);

                /*-------------------------------- 终端信息状态 --------------------------------------------*/
                SystemInfoModel.TerminalStatusInfoBean terminalStatusInfo = systemInfoModel.getTerminalStatusInfo();
                //磁盘温度
                String diskTemp = terminalStatusInfo.getDiskTemp();
                //外部温度
                String externalTemp = terminalStatusInfo.getExternalTemp();
                //麦克风状态
                String micStatus = terminalStatusInfo.getMicStatus();
                //扬声器状态
                String speackerStatus = terminalStatusInfo.getSpeackerStatus();
                //打印机状态
                String printerStatus = terminalStatusInfo.getPrinterStatus();
                // 6 个摄像头状态
                String cameraStatus1 = terminalStatusInfo.getCameraStatus1();
                String cameraStatus2 = terminalStatusInfo.getCameraStatus2();
                String cameraStatus3 = terminalStatusInfo.getCameraStatus3();
                String cameraStatus4 = terminalStatusInfo.getCameraStatus4();
                String cameraStatus5 = terminalStatusInfo.getCameraStatus5();
                String cameraStatus6 = terminalStatusInfo.getCameraStatus6();

                mTvDiskTemperature.setText(diskTemp);
                mTvExternalTemperature.setText(externalTemp);

                mTvStateOfTheMicrophone.setText(isNormal(micStatus));
                mTvStateOfTheSpeaker.setText(isNormal(speackerStatus));
                mTvStateOfThePrinter.setText(isNormal(printerStatus));

                mTvCameraStatus.setText(String.format(" 1 %s; 2 %s; 3 %s; 4 %s; 5 %s; 6 %s",
                        isNormal(cameraStatus1),isNormal(cameraStatus2),isNormal(cameraStatus3),
                        isNormal(cameraStatus4),isNormal(cameraStatus5),isNormal(cameraStatus6)));

                /*--------------------------------- GPS 模块设置-----------------------------------------*/

                SystemInfoModel.GpsBean gps = systemInfoModel.getGps();
                //定位模块
                String gpsModel = gps.getGpsModel();
                //定位天线 返回值:1 : 代表开路  2 : 代表关路
                int gpsAntStatus = gps.getGpsAntStatus();
                //GPS 信号强度 返回值: 0: 无信号 1: 弱 2:强
                int gpsSignalLevel = gps.getGpsSignalLevel();
                //经度
                double longitude = gps.getLongitude();
                //纬度
                double latitude = gps.getLatitude();

                mTvGpsModel.setText(gpsModel);
                if (gpsAntStatus == 1){
                    mTvDirectionalAntenna.setText(R.string.open_circuit);
                }else {
                    mTvDirectionalAntenna.setText(R.string.close_circuit);
                }

                if (gpsSignalLevel == 0){
                    mTvGpsSignal.setText(R.string.no_signal);
                }else if (gpsSignalLevel == 1){
                    mTvGpsSignal.setText(R.string.signal_weak);
                }else if (gpsSignalLevel == 2){
                    mTvGpsSignal.setText(R.string.signal_strong);
                }

                mTvLatitudeAndLongitude.setText(String.format("%s,%s",String.valueOf(longitude),String.valueOf(latitude)));

                /*--------------------------------- 4G 模块 --------------------------------*/
                SystemInfoModel.FourGBean fourG = systemInfoModel.getFourG();
                //4G 模块
                String fourGModel = fourG.getFourGModel();
                // 4G 信号强度 返回值: 0 : 无信号 1: 弱 2: 强
                int fourGSignalLevel = fourG.getFourGSignalLevel();
                //SIM 卡号
                String simNumber = fourG.getSimNumber();
                //IMEI 号
                String imei = fourG.getIMEI();

                mTvFourGModel.setText(fourGModel);
                if (fourGSignalLevel == 0){
                    mTvFourGSignal.setText(R.string.no_signal);
                }else if (fourGSignalLevel == 1){
                    mTvFourGSignal.setText(R.string.signal_weak);
                }else if (fourGSignalLevel == 2){
                    mTvFourGSignal.setText(R.string.signal_strong);
                }

                mTvSimCardNumber.setText(simNumber);
                mTvImeiNumber.setText(imei);

                /*----------------------------- 车辆状态信息 ---------------------------------------*/
                SystemInfoModel.VehicleStatusInfoBean vehicleStatusInfo = systemInfoModel.getVehicleStatusInfo();
                //车辆里程
                int mileage = vehicleStatusInfo.getMileage();
                //车辆速度
                int speed = vehicleStatusInfo.getSpeed();
                //脉冲数
                int pulseCount = vehicleStatusInfo.getPulseCount();
                // 转向灯状态 返回值 : 0: 已接入,关闭 1: 已接入，打开 2: 未接入
                int leftTurnLightStatus = vehicleStatusInfo.getLeftTurnLightStatus();
                int rightTurnLightStatus = vehicleStatusInfo.getRightTurnLightStatus();
                int nearTurnLightStatus = vehicleStatusInfo.getNearTurnLightStatus();
                int farTurnLightStatus = vehicleStatusInfo.getFarTurnLightStatus();
                int brakeStatus = vehicleStatusInfo.getBrakeStatus();

                mTvVehicleMileage.setText(String.format("%s Km",String.valueOf(mileage)));
                mTvSpeed.setText(String.format("%s Km/h",String.valueOf(speed)));
                mTvPulseNumber.setText(String.valueOf(pulseCount));
                turnSignalState(mTvLeftTurnSignalState,leftTurnLightStatus);
                turnSignalState(mTvRightTurnSignalState,rightTurnLightStatus);
                turnSignalState(mTvDippedHeadlightState,nearTurnLightStatus);
                turnSignalState(mTvHighBeamState,farTurnLightStatus);

                if (brakeStatus ==  0){
                    mTvStateOfTheBrake.setText(String.format("%s;%s",mHaveAccess,mNormal));
                }else if (brakeStatus == 1){
                    mTvStateOfTheBrake.setText(String.format("%s;%s",mHaveAccess,mException));
                }else if (brakeStatus == 2){
                    mTvStateOfTheBrake.setText(mNoAccess);
                }

                /*-------------------------------- 磁盘信息 --------------------------------*/
                List<SystemInfoModel.StorageArrayBean> storageArray = systemInfoModel.getStorageArray();
                mStorageAdapter.setData(storageArray);
                mRvStorageList.setAdapter(mStorageAdapter);

                mSystemInfoHandler.sendEmptyMessageDelayed(WHAT, DELAY_MILLIS);
            }
        });
    }

    private void turnSignalState(TextView textView,int turnLightStatus){
        if (turnLightStatus ==  0){
            textView.setText(String.format("%s;%s",mHaveAccess,mClose));
        }else if (turnLightStatus == 1){
            textView.setText(String.format("%s;%s",mHaveAccess,mOpen));
        }else if (turnLightStatus == 2){
            textView.setText(mNoAccess);
        }
    }
    private String isNormal(String text){

        return "1".equals(text)? mNormal:mException;
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        mSystemInfoHandler.removeCallbacksAndMessages(null);
    }
}
