package com.adasplus.base.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/10/15 11:38
 * Description :
 */
public class SystemInfoModel implements Parcelable {

    @SerializedName("4g")
    private FourGBean fourG;
    private TerminalInfoBean terminalInfo;
    private TerminalStatusInfoBean terminalStatusInfo;
    private GpsBean gps;
    private VehicleStatusInfoBean vehicleStatusInfo;
    private List<StorageArrayBean> storageArray;
    private List<PlatformStatusArrayBean> platformStatusArray;

    private SystemInfoModel(Parcel in) {
    }

    public SystemInfoModel(){

    }

    public static final Creator<SystemInfoModel> CREATOR = new Creator<SystemInfoModel>() {
        @Override
        public SystemInfoModel createFromParcel(Parcel in) {
            return new SystemInfoModel(in);
        }

        @Override
        public SystemInfoModel[] newArray(int size) {
            return new SystemInfoModel[size];
        }
    };

    public FourGBean getFourG() {
        return fourG;
    }

    public void setFourG(FourGBean fourG) {
        this.fourG = fourG;
    }

    public TerminalInfoBean getTerminalInfo() {
        return terminalInfo;
    }

    public void setTerminalInfo(TerminalInfoBean terminalInfo) {
        this.terminalInfo = terminalInfo;
    }

    public TerminalStatusInfoBean getTerminalStatusInfo() {
        return terminalStatusInfo;
    }

    public void setTerminalStatusInfo(TerminalStatusInfoBean terminalStatusInfo) {
        this.terminalStatusInfo = terminalStatusInfo;
    }

    public GpsBean getGps() {
        return gps;
    }

    public void setGps(GpsBean gps) {
        this.gps = gps;
    }

    public VehicleStatusInfoBean getVehicleStatusInfo() {
        return vehicleStatusInfo;
    }

    public void setVehicleStatusInfo(VehicleStatusInfoBean vehicleStatusInfo) {
        this.vehicleStatusInfo = vehicleStatusInfo;
    }

    public List<StorageArrayBean> getStorageArray() {
        return storageArray;
    }

    public void setStorageArray(List<StorageArrayBean> storageArray) {
        this.storageArray = storageArray;
    }

    public List<PlatformStatusArrayBean> getPlatformStatusArray() {
        return platformStatusArray;
    }

    public void setPlatformStatusArray(List<PlatformStatusArrayBean> platformStatusArray) {
        this.platformStatusArray = platformStatusArray;
    }

    @Override
    public String toString() {
        return "SystemInfoModel{" +
                "_$4g=" + fourG +
                ", terminalInfo=" + terminalInfo +
                ", terminalStatusInfo=" + terminalStatusInfo +
                ", gps=" + gps +
                ", vehicleStatusInfo=" + vehicleStatusInfo +
                ", storageArray=" + storageArray +
                ", platformStatusArray=" + platformStatusArray +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class FourGBean implements Parcelable{

        @SerializedName("g4Model")
        private String fourGModel; // 4g 模块
        @SerializedName("g4Status")
        private String fourGStatus; // 4g 状态
        @SerializedName("g4SignalLevel")
        private int fourGSignalLevel; // 4g 信号强度
        private String simNumber;
        private String IMEI;

        FourGBean(Parcel in) {
            fourGModel = in.readString();
            fourGStatus = in.readString();
            fourGSignalLevel = in.readInt();
            simNumber = in.readString();
            IMEI = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(fourGModel);
            dest.writeString(fourGStatus);
            dest.writeInt(fourGSignalLevel);
            dest.writeString(simNumber);
            dest.writeString(IMEI);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<FourGBean> CREATOR = new Creator<FourGBean>() {
            @Override
            public FourGBean createFromParcel(Parcel in) {
                return new FourGBean(in);
            }

            @Override
            public FourGBean[] newArray(int size) {
                return new FourGBean[size];
            }
        };

        public String getFourGModel() {
            return fourGModel;
        }

        public void setFourGModel(String fourGModel) {
            this.fourGModel = fourGModel;
        }

        public String getFourGStatus() {
            return fourGStatus;
        }

        public void setFourGStatus(String fourGStatus) {
            this.fourGStatus = fourGStatus;
        }

        public int getFourGSignalLevel() {
            return fourGSignalLevel;
        }

        public void setFourGSignalLevel(int fourGSignalLevel) {
            this.fourGSignalLevel = fourGSignalLevel;
        }

        public String getSimNumber() {
            return simNumber;
        }

        public void setSimNumber(String simNumber) {
            this.simNumber = simNumber;
        }

        public String getIMEI() {
            return IMEI;
        }

        public void setIMEI(String IMEI) {
            this.IMEI = IMEI;
        }

        @Override
        public String toString() {
            return "FourGBean{" +
                    "fourGModel='" + fourGModel + '\'' +
                    ", fourGStatus='" + fourGStatus + '\'' +
                    ", fourGSignalLevel=" + fourGSignalLevel +
                    ", simNumber='" + simNumber + '\'' +
                    ", IMEI='" + IMEI + '\'' +
                    '}';
        }
    }

    public static class TerminalInfoBean implements Parcelable{

        private String terminalId;
        private String softWareVersion;
        private String hardWareVersion;
        private String MCUVersion;

        TerminalInfoBean(Parcel in) {
            terminalId = in.readString();
            softWareVersion = in.readString();
            hardWareVersion = in.readString();
            MCUVersion = in.readString();
        }

        public static final Creator<TerminalInfoBean> CREATOR = new Creator<TerminalInfoBean>() {
            @Override
            public TerminalInfoBean createFromParcel(Parcel in) {
                return new TerminalInfoBean(in);
            }

            @Override
            public TerminalInfoBean[] newArray(int size) {
                return new TerminalInfoBean[size];
            }
        };

        public String getTerminalId() {
            return terminalId;
        }

        public void setTerminalId(String terminalId) {
            this.terminalId = terminalId;
        }

        public String getSoftWareVersion() {
            return softWareVersion;
        }

        public void setSoftWareVersion(String softWareVersion) {
            this.softWareVersion = softWareVersion;
        }

        public String getHardWareVersion() {
            return hardWareVersion;
        }

        public void setHardWareVersion(String hardWareVersion) {
            this.hardWareVersion = hardWareVersion;
        }

        public String getMCUVersion() {
            return MCUVersion;
        }

        public void setMCUVersion(String MCUVersion) {
            this.MCUVersion = MCUVersion;
        }

        @Override
        public String toString() {
            return "TerminalInfoBean{" +
                    "terminalId='" + terminalId + '\'' +
                    ", softWareVersion='" + softWareVersion + '\'' +
                    ", hardWareVersion='" + hardWareVersion + '\'' +
                    ", MCUVersion='" + MCUVersion + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(terminalId);
            dest.writeString(softWareVersion);
            dest.writeString(hardWareVersion);
            dest.writeString(MCUVersion);
        }
    }

    public static class TerminalStatusInfoBean {

        private String diskTemp;
        private String externalTemp;
        private String micStatus;
        private String speackerStatus;
        private String printerStatus;
        private String batteryVoltage;
        private String batteryStatus;
        private String cameraStatus1;
        private String cameraStatus2;
        private String cameraStatus3;
        private String cameraStatus4;
        private String cameraStatus5;
        private String cameraStatus6;

        public String getDiskTemp() {
            return diskTemp;
        }

        public void setDiskTemp(String diskTemp) {
            this.diskTemp = diskTemp;
        }

        public String getExternalTemp() {
            return externalTemp;
        }

        public void setExternalTemp(String externalTemp) {
            this.externalTemp = externalTemp;
        }

        public String getMicStatus() {
            return micStatus;
        }

        public void setMicStatus(String micStatus) {
            this.micStatus = micStatus;
        }

        public String getSpeackerStatus() {
            return speackerStatus;
        }

        public void setSpeackerStatus(String speackerStatus) {
            this.speackerStatus = speackerStatus;
        }

        public String getPrinterStatus() {
            return printerStatus;
        }

        public void setPrinterStatus(String printerStatus) {
            this.printerStatus = printerStatus;
        }

        public String getBatteryVoltage() {
            return batteryVoltage;
        }

        public void setBatteryVoltage(String batteryVoltage) {
            this.batteryVoltage = batteryVoltage;
        }

        public String getBatteryStatus() {
            return batteryStatus;
        }

        public void setBatteryStatus(String batteryStatus) {
            this.batteryStatus = batteryStatus;
        }

        public String getCameraStatus1() {
            return cameraStatus1;
        }

        public void setCameraStatus1(String cameraStatus1) {
            this.cameraStatus1 = cameraStatus1;
        }

        public String getCameraStatus2() {
            return cameraStatus2;
        }

        public void setCameraStatus2(String cameraStatus2) {
            this.cameraStatus2 = cameraStatus2;
        }

        public String getCameraStatus3() {
            return cameraStatus3;
        }

        public void setCameraStatus3(String cameraStatus3) {
            this.cameraStatus3 = cameraStatus3;
        }

        public String getCameraStatus4() {
            return cameraStatus4;
        }

        public void setCameraStatus4(String cameraStatus4) {
            this.cameraStatus4 = cameraStatus4;
        }

        public String getCameraStatus5() {
            return cameraStatus5;
        }

        public void setCameraStatus5(String cameraStatus5) {
            this.cameraStatus5 = cameraStatus5;
        }

        public String getCameraStatus6() {
            return cameraStatus6;
        }

        public void setCameraStatus6(String cameraStatus6) {
            this.cameraStatus6 = cameraStatus6;
        }

        @Override
        public String toString() {
            return "TerminalStatusInfoBean{" +
                    "diskTemp='" + diskTemp + '\'' +
                    ", externalTemp='" + externalTemp + '\'' +
                    ", micStatus='" + micStatus + '\'' +
                    ", speackerStatus='" + speackerStatus + '\'' +
                    ", printerStatus='" + printerStatus + '\'' +
                    ", batteryVoltage='" + batteryVoltage + '\'' +
                    ", batteryStatus='" + batteryStatus + '\'' +
                    ", cameraStatus1='" + cameraStatus1 + '\'' +
                    ", cameraStatus2='" + cameraStatus2 + '\'' +
                    ", cameraStatus3='" + cameraStatus3 + '\'' +
                    ", cameraStatus4='" + cameraStatus4 + '\'' +
                    ", cameraStatus5='" + cameraStatus5 + '\'' +
                    ", cameraStatus6='" + cameraStatus6 + '\'' +
                    '}';
        }
    }

    public static class GpsBean {

        private int gpsValid;
        private String gpsModel;
        private int gpsAntStatus;
        private int gpsSignalLevel;
        private double longitude;
        private int longitudeDir;
        private double latitude;
        private int latitudeDir;

        public int getGpsValid() {
            return gpsValid;
        }

        public void setGpsValid(int gpsValid) {
            this.gpsValid = gpsValid;
        }

        public String getGpsModel() {
            return gpsModel;
        }

        public void setGpsModel(String gpsModel) {
            this.gpsModel = gpsModel;
        }

        public int getGpsAntStatus() {
            return gpsAntStatus;
        }

        public void setGpsAntStatus(int gpsAntStatus) {
            this.gpsAntStatus = gpsAntStatus;
        }

        public int getGpsSignalLevel() {
            return gpsSignalLevel;
        }

        public void setGpsSignalLevel(int gpsSignalLevel) {
            this.gpsSignalLevel = gpsSignalLevel;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public int getLongitudeDir() {
            return longitudeDir;
        }

        public void setLongitudeDir(int longitudeDir) {
            this.longitudeDir = longitudeDir;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getLatitudeDir() {
            return latitudeDir;
        }

        public void setLatitudeDir(int latitudeDir) {
            this.latitudeDir = latitudeDir;
        }

        @Override
        public String toString() {
            return "GpsBean{" +
                    "gpsModel='" + gpsModel + '\'' +
                    ", gpsAntStatus=" + gpsAntStatus +
                    ", gpsSignalLevel=" + gpsSignalLevel +
                    ", longitude=" + longitude +
                    ", longitudeDir=" + longitudeDir +
                    ", latitude=" + latitude +
                    ", latitudeDir=" + latitudeDir +
                    '}';
        }
    }

    public static class VehicleStatusInfoBean {

        private int mileage;
        private int speed;
        private int pulseCount;
        private int leftTurnLightStatus;
        private int rightTurnLightStatus;
        private int nearTurnLightStatus;
        private int farTurnLightStatus;
        private int brakeStatus;

        public int getMileage() {
            return mileage;
        }

        public void setMileage(int mileage) {
            this.mileage = mileage;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getPulseCount() {
            return pulseCount;
        }

        public void setPulseCount(int pulseCount) {
            this.pulseCount = pulseCount;
        }

        public int getLeftTurnLightStatus() {
            return leftTurnLightStatus;
        }

        public void setLeftTurnLightStatus(int leftTurnLightStatus) {
            this.leftTurnLightStatus = leftTurnLightStatus;
        }

        public int getRightTurnLightStatus() {
            return rightTurnLightStatus;
        }

        public void setRightTurnLightStatus(int rightTurnLightStatus) {
            this.rightTurnLightStatus = rightTurnLightStatus;
        }

        public int getNearTurnLightStatus() {
            return nearTurnLightStatus;
        }

        public void setNearTurnLightStatus(int nearTurnLightStatus) {
            this.nearTurnLightStatus = nearTurnLightStatus;
        }

        public int getFarTurnLightStatus() {
            return farTurnLightStatus;
        }

        public void setFarTurnLightStatus(int farTurnLightStatus) {
            this.farTurnLightStatus = farTurnLightStatus;
        }

        public int getBrakeStatus() {
            return brakeStatus;
        }

        public void setBrakeStatus(int brakeStatus) {
            this.brakeStatus = brakeStatus;
        }

        @Override
        public String toString() {
            return "VehicleStatusInfoBean{" +
                    "mileage=" + mileage +
                    ", speed=" + speed +
                    ", pulseCount=" + pulseCount +
                    ", leftTurnLightStatus=" + leftTurnLightStatus +
                    ", rightTurnLightStatus=" + rightTurnLightStatus +
                    ", nearTurnLightStatus=" + nearTurnLightStatus +
                    ", farTurnLightStatus=" + farTurnLightStatus +
                    ", brakeStatus=" + brakeStatus +
                    '}';
        }
    }

    public static class StorageArrayBean {

        private String storageName;
        private int storageDeviceType;
        private int capacity;
        private int used;
        private int free;
        private int selectEnable;
        private int specialVideoSize;

        public int getSpecialVideoSize() {
            return specialVideoSize;
        }

        public void setSpecialVideoSize(int specialVideoSize) {
            this.specialVideoSize = specialVideoSize;
        }

        public String getStorageName() {
            return storageName;
        }

        public void setStorageName(String storageName) {
            this.storageName = storageName;
        }

        public int getStorageDeviceType() {
            return storageDeviceType;
        }

        public void setStorageDeviceType(int storageDeviceType) {
            this.storageDeviceType = storageDeviceType;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getUsed() {
            return used;
        }

        public void setUsed(int used) {
            this.used = used;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public int getSelectEnable() {
            return selectEnable;
        }

        public void setSelectEnable(int selectEnable) {
            this.selectEnable = selectEnable;
        }

        @Override
        public String toString() {
            return "StorageArrayBean{" +
                    "storageName='" + storageName + '\'' +
                    ", storageDeviceType=" + storageDeviceType +
                    ", capacity=" + capacity +
                    ", used=" + used +
                    ", free=" + free +
                    ", selectEnable=" + selectEnable +
                    '}';
        }
    }

    public static class PlatformStatusArrayBean {

        private String platformIp;
        private int platformPort;
        private int connectStatus;

        public String getPlatformIp() {
            return platformIp;
        }

        public void setPlatformIp(String platformIp) {
            this.platformIp = platformIp;
        }

        public int getPlatformPort() {
            return platformPort;
        }

        public void setPlatformPort(int platformPort) {
            this.platformPort = platformPort;
        }

        public int getConnectStatus() {
            return connectStatus;
        }

        public void setConnectStatus(int connectStatus) {
            this.connectStatus = connectStatus;
        }

        @Override
        public String toString() {
            return "PlatformStatusArrayBean{" +
                    "platformIp='" + platformIp + '\'' +
                    ", platformPort=" + platformPort +
                    ", connectStatus=" + connectStatus +
                    '}';
        }
    }
}
