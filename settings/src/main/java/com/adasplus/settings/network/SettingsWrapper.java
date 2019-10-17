package com.adasplus.settings.network;

import com.adasplus.base.network.HttpConstant;
import com.adasplus.base.network.RetrofitHelper;
import com.adasplus.settings.mvp.model.ADASWarningModel;
import com.adasplus.settings.mvp.model.CANChannelsModel;
import com.adasplus.settings.mvp.model.CalibrationSetModel;
import com.adasplus.settings.mvp.model.DMSWarningModel;
import com.adasplus.settings.mvp.model.DeviceFormatModel;
import com.adasplus.settings.mvp.model.DormancySetModel;
import com.adasplus.settings.mvp.model.NetworkSetModel;
import com.adasplus.settings.mvp.model.ParamsSetModel;
import com.adasplus.settings.mvp.model.ResetFactoryModel;
import com.adasplus.settings.mvp.model.RestartDeviceModel;
import com.adasplus.settings.mvp.model.SoundsModel;
import com.adasplus.settings.mvp.model.SpeedSetModel;
import com.adasplus.settings.mvp.model.TimeSetModel;
import com.adasplus.settings.mvp.model.VideoSetModel;
import com.adasplus.settings.mvp.model.WarningsRestoreDefaultSettingsModel;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;


/**
 * Author:刘净辉
 * Date : 2019/9/27 16:12
 * Description :
 */
public class SettingsWrapper extends RetrofitHelper {

    private static volatile SettingsWrapper instance;

    private SettingsWrapper() {

    }

    public static SettingsWrapper getInstance() {
        if (instance == null) {
            synchronized (SettingsWrapper.class) {
                if (instance == null) {
                    instance = new SettingsWrapper();
                }
            }
        }
        return instance;
    }

    public Observable<SpeedSetModel> getDefaultBySpeedSet() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getDefaultBySpeedSet().compose(this.<SpeedSetModel>applySchedulers());
    }

    public Observable<SpeedSetModel> updateSpeedSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateSpeedSet(requestBody).compose(this.<SpeedSetModel>applySchedulers());
    }

    public Observable<NetworkSetModel> getDeviceValueByNetworkRequest() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getDeviceValueByNetworkRequest().compose(this.<NetworkSetModel>applySchedulers());
    }

    public Observable<NetworkSetModel> updateNetworkSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateNetworkSet(requestBody).compose(this.<NetworkSetModel>applySchedulers());
    }

    public Observable<CANChannelsModel> getCANSet() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getCANSet().compose(this.<CANChannelsModel>applySchedulers());
    }

    public Observable<CANChannelsModel> updateCANSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateCANSet(requestBody).compose(this.<CANChannelsModel>applySchedulers());
    }

    public Observable<TimeSetModel> getTimeSet() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getTimeSet().compose(this.<TimeSetModel>applySchedulers());
    }

    public Observable<TimeSetModel> updateTimeSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateTimeSet(requestBody).compose(this.<TimeSetModel>applySchedulers());
    }

    public Observable<SoundsModel> getSoundsSet() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getSoundsSet().compose(this.<SoundsModel>applySchedulers());
    }

    public Observable<SoundsModel> updateSoundsSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateSoundsSet(requestBody).compose(this.<SoundsModel>applySchedulers());
    }

    public Observable<DormancySetModel> getSleepSet() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getSleepSet().compose(this.<DormancySetModel>applySchedulers());
    }

    public Observable<DormancySetModel> updateSleepSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateSleepSet(requestBody).compose(this.<DormancySetModel>applySchedulers());
    }

    public Observable<RestartDeviceModel> restartDevice() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.restartDevice().compose(this.<RestartDeviceModel>applySchedulers());
    }

    public Observable<ResetFactoryModel> resetFactory() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.resetFactory().compose(this.<ResetFactoryModel>applySchedulers());
    }

    public Observable<ParamsSetModel> getParamsSet() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getParamsSet().compose(this.<ParamsSetModel>applySchedulers());
    }

    public Observable<ParamsSetModel> updateParamsSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateParamsSet(requestBody).compose(this.<ParamsSetModel>applySchedulers());
    }


    public Observable<ADASWarningModel> getADASSet() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getADASSet().compose(this.<ADASWarningModel>applySchedulers());
    }

    public Observable<ADASWarningModel> updateADASSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateADASSet(requestBody).compose(this.<ADASWarningModel>applySchedulers());
    }

    public Observable<WarningsRestoreDefaultSettingsModel> adasRestoreDefaultSettings() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.adasRestoreDefaultSettings().compose(this.<WarningsRestoreDefaultSettingsModel>applySchedulers());
    }


    public Observable<DMSWarningModel> getDMSSet() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getDMSSet().compose(this.<DMSWarningModel>applySchedulers());
    }

    public Observable<DMSWarningModel> updateDMSSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateDMSSet(requestBody).compose(this.<DMSWarningModel>applySchedulers());
    }

    public Observable<WarningsRestoreDefaultSettingsModel> dmsRestoreDefaultSettings() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.dmsRestoreDefaultSettings().compose(this.<WarningsRestoreDefaultSettingsModel>applySchedulers());
    }

    public Observable<CalibrationSetModel> getCalibrationSet() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getCalibrationSet().compose(this.<CalibrationSetModel>applySchedulers());
    }

    public Observable<CalibrationSetModel> updateCalibrationSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateCalibrationSet(requestBody).compose(this.<CalibrationSetModel>applySchedulers());
    }

    public Observable<DeviceFormatModel> getStorageInfoList() {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.getStorageInfoList().compose(this.<DeviceFormatModel>applySchedulers());
    }

    public Observable<DeviceFormatModel> updateStorageFormatList(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateStorageFormatList(requestBody).compose(this.<DeviceFormatModel>applySchedulers());
    }

    public Observable<VideoSetModel> getVideoSetData(JSONObject jsonObject) {
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        return settingsService.getVideoSetData(requestBody).compose(this.<VideoSetModel>applySchedulers());
    }

    public Observable<VideoSetModel> updateVideoSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        ISettingsService settingsService = createServiceFrom(ISettingsService.class);
        return settingsService.updateVideoSet(requestBody).compose(this.<VideoSetModel>applySchedulers());
    }
}
