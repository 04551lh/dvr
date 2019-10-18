package com.adasplus.homepager.network;

import com.adasplus.base.network.BaseResponse;
import com.adasplus.base.network.HttpConstant;
import com.adasplus.homepager.activate.mvp.model.ActivateNewPlatformsModel;
import com.adasplus.homepager.activate.mvp.model.GetPlatformInfoModel;
import com.adasplus.homepager.activate.mvp.model.LogoutPlatformsModel;
import com.adasplus.homepager.params.mvp.model.ParamsSetModel;
import com.adasplus.homepager.set.mvp.model.ADASWarningModel;
import com.adasplus.homepager.set.mvp.model.CANChannelsModel;
import com.adasplus.homepager.set.mvp.model.CalibrationSetModel;
import com.adasplus.homepager.set.mvp.model.DMSWarningModel;
import com.adasplus.homepager.set.mvp.model.DeviceFormatModel;
import com.adasplus.homepager.set.mvp.model.DormancySetModel;
import com.adasplus.homepager.set.mvp.model.NetworkSetModel;
import com.adasplus.homepager.set.mvp.model.ResetFactoryModel;
import com.adasplus.homepager.set.mvp.model.RestartDeviceModel;
import com.adasplus.homepager.set.mvp.model.SoundsModel;
import com.adasplus.homepager.set.mvp.model.SpeedSetModel;
import com.adasplus.homepager.set.mvp.model.TimeSetModel;
import com.adasplus.homepager.set.mvp.model.VideoSetModel;
import com.adasplus.homepager.set.mvp.model.WarningsRestoreDefaultSettingsModel;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author:刘净辉
 * Date : 2019/10/18 14:25
 * Description :
 */
public interface IHomeService {

    /**
     * 获取 已激活平台的接口 请求
     * @return
     */
    @POST(HttpConstant.GET_PLATFORM_INFO)
    Observable<BaseResponse<GetPlatformInfoModel>> getPlatfromInfo();

    /**
     *  激活 新平台的接口 请求
     * @param requestBody
     * @return
     */
    @POST(HttpConstant.ACTIVATE_NEW_PLATFORM)
    Observable<BaseResponse<ActivateNewPlatformsModel>> activateNewPlatforms(@Body RequestBody requestBody);

    /**
     *  注销平台的接口请求
     * @param requestBody
     * @return
     */
    @POST(HttpConstant.LOGOUT_PLATFORMS)
    Observable<BaseResponse<LogoutPlatformsModel>> logoutPlatforms(@Body RequestBody requestBody);

    /**
     * 获取参数填写信息
     * @return
     */
    @POST(HttpConstant.GET_PARAMS_DATA)
    Observable<BaseResponse<ParamsSetModel>> getParamsSet();

    /**
     * 保存更改的参数填写信息
     * @param requestBody
     * @return
     */
    @POST(HttpConstant.UPDATE_PARAMS_DATA)
    Observable<BaseResponse<ParamsSetModel>> updateParamsSet(@Body RequestBody requestBody);

    /**
     * 获取设备中的速度设置
     * @return
     */
    @POST(HttpConstant.GET_SPEEDS_SET)
    Observable<BaseResponse<SpeedSetModel>> getDefaultBySpeedSet();

    /**
     * 更改设备中的速度中
     * @param requestBody
     * @return
     */
    @POST(HttpConstant.UPDATE_SPEEDS_SET)
    Observable<BaseResponse<SpeedSetModel>> updateSpeedSet(@Body RequestBody requestBody);

    /**
     * 获取网络设置中
     * @return
     */
    @POST(HttpConstant.GET_NETWORK_SET)
    Observable<BaseResponse<NetworkSetModel>> getDeviceValueByNetworkRequest();

    /**
     * 更改网络设置中
     * @param requestBody
     * @return
     */
    @POST(HttpConstant.UPDATE_NETWORK_SET)
    Observable<BaseResponse<NetworkSetModel>> updateNetworkSet(@Body RequestBody requestBody);

    /**
     * 获取 CAN 设置
     * @return
     */
    @POST(HttpConstant.GET_CAN_SET_REQUEST)
    Observable<BaseResponse<CANChannelsModel>> getCANSet();

    /**
     * 更改 CAN 设置
     * @param requestBody
     * @return
     */
    @POST(HttpConstant.UPDATE_CAN_SET)
    Observable<BaseResponse<CANChannelsModel>> updateCANSet(@Body RequestBody requestBody);

    /**
     * 获取时间设置
     * @return
     */
    @POST(HttpConstant.GET_TIME_SET)
    Observable<BaseResponse<TimeSetModel>> getTimeSet();

    /**
     * 更改时间设置
     * @param requestBody
     * @return
     */
    @POST(HttpConstant.UPDATE_TIME_SET)
    Observable<BaseResponse<TimeSetModel>> updateTimeSet(@Body RequestBody requestBody);

    /**
     * 获取声音设置
     * @return
     */
    @POST(HttpConstant.GET_SOUNDS_SET)
    Observable<BaseResponse<SoundsModel>> getSoundsSet();

    /**
     * 更改声音设置
     * @param requestBody
     * @return
     */
    @POST(HttpConstant.UPDATE_SOUNDS_SET)
    Observable<BaseResponse<SoundsModel>> updateSoundsSet(@Body RequestBody requestBody);

    /**
     * 获取休眠设置
     * @return
     */
    @POST(HttpConstant.GET_SLEEP_SET)
    Observable<BaseResponse<DormancySetModel>> getSleepSet();

    /**
     * 更改休眠设置
     * @param requestBody
     * @return
     */
    @POST(HttpConstant.UPDATE_SLEEP_SET)
    Observable<BaseResponse<DormancySetModel>> updateSleepSet(@Body RequestBody requestBody);

    /**
     * 重启设备
     * @return
     */
    @POST(HttpConstant.RESTART_DEVICE)
    Observable<BaseResponse<RestartDeviceModel>> restartDevice();

    /**
     * 恢复出厂设置
     * @return
     */
    @POST(HttpConstant.RESET_FACTORY)
    Observable<BaseResponse<ResetFactoryModel>> resetFactory();

    @POST(HttpConstant.GET_ADAS_SET)
    Observable<BaseResponse<ADASWarningModel>> getADASSet();

    @POST(HttpConstant.UPDATE_ADAS_SET)
    Observable<BaseResponse<ADASWarningModel>> updateADASSet(@Body RequestBody requestBody);

    @POST(HttpConstant.ADAS_ALARM_CONFIG_DEFAULT)
    Observable<BaseResponse<WarningsRestoreDefaultSettingsModel>> adasRestoreDefaultSettings();


    @POST(HttpConstant.GET_DMS_SET)
    Observable<BaseResponse<DMSWarningModel>> getDMSSet();

    @POST(HttpConstant.UPDATE_DMS_SET)
    Observable<BaseResponse<DMSWarningModel>> updateDMSSet(@Body RequestBody requestBody);

    @POST(HttpConstant.DMS_RESTORE_DEFAULT_SET)
    Observable<BaseResponse<WarningsRestoreDefaultSettingsModel>> dmsRestoreDefaultSettings();

    @POST(HttpConstant.GET_CALIBRATION_SET)
    Observable<BaseResponse<CalibrationSetModel>> getCalibrationSet();

    @POST(HttpConstant.UPDATE_CALIBRATION_SET)
    Observable<BaseResponse<CalibrationSetModel>> updateCalibrationSet(@Body RequestBody requestBody);

    @POST(HttpConstant.GET_STORAGE_INFO_LIST)
    Observable<BaseResponse<DeviceFormatModel>> getStorageInfoList();

    @POST(HttpConstant.UPDATE_STORAGE_INFO_LIST)
    Observable<BaseResponse<DeviceFormatModel>> updateStorageFormatList(@Body RequestBody requestBody);

    @POST(HttpConstant.GET_VIDEO_SET_DATA)
    Observable<BaseResponse<VideoSetModel>>  getVideoSetData(@Body RequestBody requestBody);

    @POST(HttpConstant.UPDATE_VIDEO_SET)
    Observable<BaseResponse<VideoSetModel>> updateVideoSet(@Body RequestBody requestBody);
}
