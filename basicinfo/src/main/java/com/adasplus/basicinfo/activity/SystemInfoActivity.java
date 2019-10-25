package com.adasplus.basicinfo.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.basicinfo.R;
import com.adasplus.basicinfo.mvp.contract.ISystemInfoContract;
import com.adasplus.basicinfo.mvp.presenter.SystemInfoPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = ActivityPathConstant.SYSTEM_INFO_PATH)
public class SystemInfoActivity extends BaseActivity implements ISystemInfoContract.View {

    private ImageView mIvBack; // 返回
    private TextView mTvSoftwareVersion; //软件版本
    private TextView mTvHardwareVersion; //硬件版本
    private TextView mTvMcuVersion; //MCU 版本
    private TextView mTvDiskTemperature; //磁盘温度
    private TextView mTvExternalTemperature; // 外部温度
    private TextView mTvVehicleMileage; //车辆里程
    private TextView mTvSpeed; //速度
    private TextView mTvPulseNumber; // 脉冲数
    private TextView mTvGpsModel; // 定位模块
    private TextView mTvDirectionalAntenna; // 定位天线
    private TextView mTvGpsSignal; // GPS 信号强度
    private TextView mTvLatitudeAndLongitude; // 经纬度
    private TextView mTvFourGModel;  // 4G 模块
    private TextView mTvFourGStatus; // 4G 状态
    private TextView mTvFourGSignal; // 4G 信号强度
    private TextView mTvSimCardNumber; // SIM 卡号
    private TextView mTvCenterConnectStatus; // 中心连接状态
    private TextView mTvTargetPlatforms; //部标平台列表
    private TextView mTvDns; //DNS
    private TextView mTvIntercomSerialId; //对讲串口id
    private TextView mTvImeiNumber; // IMEI 号
    private TextView mTvSerialNumber; // 序列号
    private TextView mTvCameraStatus; //相机状态
    private TextView mTvMainPowerSupplyState; //主电源状态
    private TextView mTvLeftTurnSignalState; // 左转灯状态
    private TextView mTvRightTurnSignalState; // 右转灯状态
    private TextView mTvDippedHeadlightState; // 近光灯状态
    private TextView mTvHighBeamState; // 远光灯状态
    private TextView mTvStateOfTheBrake; // 刹车状态
    private TextView mTvStateOfTheMicrophone; // 麦克风状态
    private TextView mTvStateOfTheSpeaker; // 扬声器状态
    private TextView mTvStateOfThePrinter; // 打印机状态
    private TextView mTvAllInterfaceState; // 各接口（外设）状态
    private RecyclerView mRvStorageList; // 磁盘的列表
    private TextView mTvBatteryVoltage; // 电池电压
    private TextView mTvBatteryStatus; // 电池状态
    private TextView mTvMchSerial; // MCH 串口
    private TextView mTvSerialDevice; // 串口设备
    private SwipeRefreshLayout mSrlRefreshSystemInfo;
    private SystemInfoPresenter mSystemInfoPresenter;

    public SystemInfoActivity() {
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mSystemInfoPresenter = new SystemInfoPresenter(this);
        mSystemInfoPresenter.initWidget();
        mSystemInfoPresenter.initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_info;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mTvSoftwareVersion = findViewById(R.id.tv_software_version);
        mTvHardwareVersion = findViewById(R.id.tv_hardware_version);
        mTvMcuVersion = findViewById(R.id.tv_mcu_version);
        mTvDiskTemperature = findViewById(R.id.tv_disk_temperature);
        mTvExternalTemperature = findViewById(R.id.tv_external_temperature);
        mTvVehicleMileage = findViewById(R.id.tv_vehicle_mileage);
        mTvSpeed = findViewById(R.id.tv_speed);
        mTvPulseNumber = findViewById(R.id.tv_pulse_number);
        mTvGpsModel = findViewById(R.id.tv_gps_model);
        mTvDirectionalAntenna = findViewById(R.id.tv_directional_antenna);
        mTvGpsSignal = findViewById(R.id.tv_gps_signal);
        mTvLatitudeAndLongitude = findViewById(R.id.tv_latitude_and_longitude);
        mTvFourGModel = findViewById(R.id.tv_four_g_model);
        mTvFourGStatus = findViewById(R.id.tv_four_g_status);
        mTvFourGSignal = findViewById(R.id.tv_four_g_signal);
        mTvSimCardNumber = findViewById(R.id.tv_sim_card_number);
        mTvCenterConnectStatus = findViewById(R.id.tv_center_connect_status);
        mTvTargetPlatforms = findViewById(R.id.tv_target_platforms);
        mTvDns = findViewById(R.id.tv_dns);
        mTvIntercomSerialId = findViewById(R.id.tv_intercom_serial_id);
        mTvImeiNumber = findViewById(R.id.tv_imei_number);
        mTvSerialNumber = findViewById(R.id.tv_serial_number);
        mTvCameraStatus = findViewById(R.id.tv_camera_status);
        mTvMainPowerSupplyState = findViewById(R.id.tv_main_power_supply_state);
        mTvLeftTurnSignalState = findViewById(R.id.tv_left_turn_signal_state);
        mTvRightTurnSignalState = findViewById(R.id.tv_right_turn_signal_state);
        mTvDippedHeadlightState = findViewById(R.id.tv_dipped_headlight_state);
        mTvHighBeamState = findViewById(R.id.tv_high_beam_state);
        mTvStateOfTheBrake = findViewById(R.id.tv_state_of_the_brake);
        mTvStateOfTheMicrophone = findViewById(R.id.tv_state_of_the_microphone);
        mTvStateOfTheSpeaker = findViewById(R.id.tv_state_of_the_speaker);
        mTvStateOfThePrinter = findViewById(R.id.tv_state_of_the_printer);
        mTvAllInterfaceState = findViewById(R.id.tv_all_interface_state);
        mRvStorageList = findViewById(R.id.rv_storage_list);
        mTvBatteryVoltage = findViewById(R.id.tv_battery_voltage);
        mTvBatteryStatus = findViewById(R.id.tv_battery_status);
        mTvMchSerial = findViewById(R.id.tv_mch_serial);
        mTvSerialDevice = findViewById(R.id.tv_serial_device);
        mSrlRefreshSystemInfo = findViewById(R.id.srl_refresh_system_info);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvSoftwareVersion() {
        return mTvSoftwareVersion;
    }

    @Override
    public TextView getTvHardwareVersion() {
        return mTvHardwareVersion;
    }

    @Override
    public TextView getTvMcuVersion() {
        return mTvMcuVersion;
    }

    @Override
    public TextView getTvDiskTemperature() {
        return mTvDiskTemperature;
    }

    @Override
    public TextView getTvExternalTemperature() {
        return mTvExternalTemperature;
    }

    @Override
    public TextView getTvVehicleMileage() {
        return mTvVehicleMileage;
    }

    @Override
    public TextView getTvSpeed() {
        return mTvSpeed;
    }

    @Override
    public TextView getTvPulseNumber() {
        return mTvPulseNumber;
    }

    @Override
    public TextView getTvGpsModel() {
        return mTvGpsModel;
    }

    @Override
    public TextView getTvDirectionalAntenna() {
        return mTvDirectionalAntenna;
    }

    @Override
    public TextView getTvGpsSignal() {
        return mTvGpsSignal;
    }

    @Override
    public TextView getTvLatitudeAndLongitude() {
        return mTvLatitudeAndLongitude;
    }

    @Override
    public TextView getTvFourGModel() {
        return mTvFourGModel;
    }

    @Override
    public TextView getTvFourGStatus() {
        return mTvFourGStatus;
    }

    @Override
    public TextView getTvFourGSignal() {
        return mTvFourGSignal;
    }

    @Override
    public TextView getTvSimCardNumber() {
        return mTvSimCardNumber;
    }

    @Override
    public TextView getTvCenterConnectStatus() {
        return mTvCenterConnectStatus;
    }

    @Override
    public TextView getTvTargetPlatforms() {
        return mTvTargetPlatforms;
    }

    @Override
    public TextView getTvDns() {
        return mTvDns;
    }

    @Override
    public TextView getTvIntercomSerialId() {
        return mTvIntercomSerialId;
    }

    @Override
    public TextView getTvImeiNumber() {
        return mTvImeiNumber;
    }

    @Override
    public TextView getTvSerialNumber() {
        return mTvSerialNumber;
    }

    @Override
    public TextView getTvCameraStatus() {
        return mTvCameraStatus;
    }

    @Override
    public TextView getTvMainPowerSupplyState() {
        return mTvMainPowerSupplyState;
    }

    @Override
    public TextView getTvLeftTurnSignalState() {
        return mTvLeftTurnSignalState;
    }

    @Override
    public TextView getTvRightTurnSignalState() {
        return mTvRightTurnSignalState;
    }

    @Override
    public TextView getTvDippedHeadlightState() {
        return mTvDippedHeadlightState;
    }

    @Override
    public TextView getTvHighBeamState() {
        return mTvHighBeamState;
    }

    @Override
    public TextView getTvStateOfTheBrake() {
        return mTvStateOfTheBrake;
    }

    @Override
    public TextView getTvStateOfTheMicrophone() {
        return mTvStateOfTheMicrophone;
    }

    @Override
    public TextView getTvStateOfTheSpeaker() {
        return mTvStateOfTheSpeaker;
    }

    @Override
    public TextView getTvStateOfThePrinter() {
        return mTvStateOfThePrinter;
    }

    @Override
    public TextView getTvAllInterfaceState() {
        return mTvAllInterfaceState;
    }

    @Override
    public RecyclerView getRvStorageList() {
        return mRvStorageList;
    }

    @Override
    public TextView getTvBatteryVoltage() {
        return mTvBatteryVoltage;
    }

    @Override
    public TextView getTvBatteryStatus() {
        return mTvBatteryStatus;
    }

    @Override
    public TextView getTvMchSerial() {
        return mTvMchSerial;
    }

    @Override
    public TextView getTvSerialDevice() {
        return mTvSerialDevice;
    }

    @Override
    public void initData() {
        mSystemInfoPresenter.initData();
    }

    @Override
    public SwipeRefreshLayout getSrlRefreshSystemInfo() {
        return mSrlRefreshSystemInfo;
    }
}
