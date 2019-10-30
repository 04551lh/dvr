package com.adasplus.base.network;

/**
 * Author:刘净辉
 * Date : 2019/9/16 16:16
 */
public class HttpConstant {


    public static final String WIFI_SERVER_IP_ADDRESS = "192.168.1.1:8000";

    public static final String USB_SERVER_IP_ADDRESS = "192.168.42.254:8000";

    static final String BASE_URL = "http://"+ WIFI_SERVER_IP_ADDRESS;

    public static final String GET_PLATFORM_INFO = "/platformInfoRequest";

    public static final String ACTIVATE_NEW_PLATFORM = "/platformInfoConfig";

    public static final String LOGOUT_PLATFORMS = "/platformInfoRemove";

    public static final String GET_SPEEDS_SET = "/speedRequest";

    public static final String UPDATE_SPEEDS_SET = "/speedConfig";

    public static final String GET_NETWORK_SET = "/networkInfoRequest";

    public static final String UPDATE_NETWORK_SET = "/networkInfoConfig";

    public static final String GET_CAN_SET_REQUEST = "/CANRequest";

    public static final String UPDATE_CAN_SET = "/CANConfig";

    public static final String GET_TIME_SET = "/timeRequest";

    public static final String UPDATE_TIME_SET = "/timeConfig";

    public static final String GET_SOUNDS_SET = "/soundRequest";

    public static final String UPDATE_SOUNDS_SET = "/soundConfig";

    public static final String GET_SLEEP_SET = "/sleepRequest";

    public static final String UPDATE_SLEEP_SET = "/sleepConfig";

    public static final String RESTART_DEVICE = "/restartRequest";

    public static final String RESET_FACTORY = "/factoryRequest";

    public static final String GET_PARAMS_DATA = "/paramsRequest";

    public static final String UPDATE_PARAMS_DATA = "/paramsConfig";

    public static final String RESTORE_DEFAULT_PARAMS_SET= "/paramsDefaultConfig";

    public static final String GET_ADAS_SET = "/adasAlarmRequest";

    public static final String UPDATE_ADAS_SET = "/adasAlarmConfig";

    public static final String ADAS_ALARM_CONFIG_DEFAULT="/adasAlarmConfigDefault";

    public static final String GET_DMS_SET = "/dmsAlarmRequest";

    public static final String UPDATE_DMS_SET = "/dmsAlarmConfig";

    public static final String DMS_RESTORE_DEFAULT_SET = "/dmsAlarmConfigDefault";

    public static final String GET_CALIBRATION_SET = "/referenceLineRequest";

    public static final String UPDATE_CALIBRATION_SET = "/referenceLineConfig";

    static final String GET_VEHICLE_INFO = "/vehicleInfoRequest";

    static final String SAVE_VEHICLE_INFO = "/vehicleInfoConfig";

    public static final String GET_STORAGE_INFO_LIST = "/storageDeviceInfoRequest";

    public static final String UPDATE_STORAGE_INFO_LIST = "/storageDeviceFormatRequest";

    static final String GET_FILE_EXPORT_REQUEST = "/fileOutRequest";

    static final String FILE_EXPORT_DATA = "/fileOutConfig";

    public static final String GET_DRIVER_INFO = "/driverInfoRequest";

    static final String GET_SYSTEM_INFO = "/systemInfoRequest";

    public static final String GET_VIDEO_SET_DATA = "/videoRequest";

    public static final String UPDATE_VIDEO_SET = "/videoConfig";

    public static final String UPDATE_DEVICE_CONNECT_STATUS = "/platformInfoConnectConfig";

    public static final String SEARCH_SERVICE_RUN_STATUS = "/softwareStatusRequest";

    public static final String MEDIA_TYPE = "application/json;charset=GBK";

    public static final String DEVICE_WIFI_TAG = "ky_test";

    public static final int SWIPE_REFRESH_DELAYED = 2000;
}
