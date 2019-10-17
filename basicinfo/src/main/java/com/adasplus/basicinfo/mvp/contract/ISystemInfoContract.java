package com.adasplus.basicinfo.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:刘净辉
 * Date : 2019/10/14 18:18
 * Description :
 */
public interface ISystemInfoContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        TextView getTvSoftwareVersion();

        TextView getTvHardwareVersion();

        TextView getTvMcuVersion();

        TextView getTvDiskTemperature();

        TextView getTvExternalTemperature();

        TextView getTvVehicleMileage();

        TextView getTvSpeed();

        TextView getTvPulseNumber();

        TextView getTvGpsModel();

        TextView getTvDirectionalAntenna();

        TextView getTvGpsSignal();

        TextView getTvLatitudeAndLongitude();

        TextView getTvFourGModel();

        TextView getTvFourGStatus();

        TextView getTvFourGSignal();

        TextView getTvSimCardNumber();

        TextView getTvCenterConnectStatus();

        RecyclerView getRvTargetsList();

        TextView getTvDns();

        TextView getTvIntercomSerialId();

        TextView getTvImeiNumber();

        TextView getTvSerialNumber();

        TextView getTvCameraStatus();

        TextView getTvMainPowerSupplyState();

        TextView getTvLeftTurnSignalState();

        TextView getTvRightTurnSignalState();

        TextView getTvDippedHeadlightState();

        TextView getTvHighBeamState();

        TextView getTvStateOfTheBrake();

        TextView getTvStateOfTheMicrophone();

        TextView getTvStateOfTheSpeaker();

        TextView getTvStateOfThePrinter();

        TextView getTvAllInterfaceState();

        RecyclerView getRvStorageList();

        TextView getTvBatteryVoltage();

        TextView getTvBatteryStatus();

        TextView getTvMchSerial();

        TextView getTvSerialDevice();

        void initData();
    }

    interface Presenter {

        void initWidget();

        void initData();

        void initListener();

        void onStop();
    }
}
