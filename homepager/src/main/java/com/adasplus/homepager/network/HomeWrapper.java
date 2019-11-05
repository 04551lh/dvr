package com.adasplus.homepager.network;

import com.adasplus.base.network.HttpConstant;
import com.adasplus.base.network.RetrofitHelper;
import com.adasplus.homepager.activate.mvp.model.ActivateNewPlatformsModel;
import com.adasplus.homepager.activate.mvp.model.GetPlatformInfoModel;
import com.adasplus.homepager.activate.mvp.model.LogoutPlatformsModel;
import com.adasplus.homepager.activate.mvp.model.UpdateDeviceConnectStatus;
import com.adasplus.homepager.params.mvp.model.ParamsSetModel;
import com.adasplus.homepager.params.mvp.model.RestoreDefaultParamsSetModel;
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
import com.adasplus.homepager.set.mvp.model.VideoShowModel;
import com.adasplus.homepager.set.mvp.model.WarningsRestoreDefaultSettingsModel;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Author:刘净辉
 * Date : 2019/10/18 14:25
 * Description :
 */
public class HomeWrapper extends RetrofitHelper {

    private static volatile HomeWrapper instance;
    private HomeWrapper(){

    }

    public static HomeWrapper getInstance(){
        if (instance == null){
            synchronized (HomeWrapper.class){
                if (instance == null){
                    instance = new HomeWrapper();
                }
            }
        }
        return instance;
    }

    public Observable<ParamsSetModel> getParamsSet() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getParamsSet().compose(this.<ParamsSetModel>applySchedulers());
    }

    public Observable<ParamsSetModel> updateParamsSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateParamsSet(requestBody).compose(this.<ParamsSetModel>applySchedulers());
    }

    public Observable<SpeedSetModel> getDefaultBySpeedSet() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getDefaultBySpeedSet().compose(this.<SpeedSetModel>applySchedulers());
    }

    public Observable<SpeedSetModel> updateSpeedSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateSpeedSet(requestBody).compose(this.<SpeedSetModel>applySchedulers());
    }

    public Observable<NetworkSetModel> getDeviceValueByNetworkRequest() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getDeviceValueByNetworkRequest().compose(this.<NetworkSetModel>applySchedulers());
    }

    public Observable<NetworkSetModel> updateNetworkSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateNetworkSet(requestBody).compose(this.<NetworkSetModel>applySchedulers());
    }

    public Observable<CANChannelsModel> getCANSet() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getCANSet().compose(this.<CANChannelsModel>applySchedulers());
    }

    public Observable<CANChannelsModel> updateCANSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateCANSet(requestBody).compose(this.<CANChannelsModel>applySchedulers());
    }

    public Observable<TimeSetModel> getTimeSet() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getTimeSet().compose(this.<TimeSetModel>applySchedulers());
    }

    public Observable<TimeSetModel> updateTimeSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateTimeSet(requestBody).compose(this.<TimeSetModel>applySchedulers());
    }

    public Observable<SoundsModel> getSoundsSet() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getSoundsSet().compose(this.<SoundsModel>applySchedulers());
    }

    public Observable<SoundsModel> updateSoundsSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateSoundsSet(requestBody).compose(this.<SoundsModel>applySchedulers());
    }

    public Observable<DormancySetModel> getSleepSet() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getSleepSet().compose(this.<DormancySetModel>applySchedulers());
    }

    public Observable<DormancySetModel> updateSleepSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateSleepSet(requestBody).compose(this.<DormancySetModel>applySchedulers());
    }

    public Observable<RestartDeviceModel> restartDevice() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.restartDevice().compose(this.<RestartDeviceModel>applySchedulers());
    }

    public Observable<ResetFactoryModel> resetFactory() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.resetFactory().compose(this.<ResetFactoryModel>applySchedulers());
    }



    public Observable<ADASWarningModel> getADASSet() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getADASSet().compose(this.<ADASWarningModel>applySchedulers());
    }

    public Observable<ADASWarningModel> updateADASSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateADASSet(requestBody).compose(this.<ADASWarningModel>applySchedulers());
    }

    public Observable<WarningsRestoreDefaultSettingsModel> adasRestoreDefaultSettings() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.adasRestoreDefaultSettings().compose(this.<WarningsRestoreDefaultSettingsModel>applySchedulers());
    }


    public Observable<DMSWarningModel> getDMSSet() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getDMSSet().compose(this.<DMSWarningModel>applySchedulers());
    }

    public Observable<DMSWarningModel> updateDMSSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateDMSSet(requestBody).compose(this.<DMSWarningModel>applySchedulers());
    }

    public Observable<WarningsRestoreDefaultSettingsModel> dmsRestoreDefaultSettings() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.dmsRestoreDefaultSettings().compose(this.<WarningsRestoreDefaultSettingsModel>applySchedulers());
    }

    public Observable<CalibrationSetModel> getCalibrationSet() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getCalibrationSet().compose(this.<CalibrationSetModel>applySchedulers());
    }

    public Observable<CalibrationSetModel> getCalibrationEnter() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getCalibrationEnter().compose(this.<CalibrationSetModel>applySchedulers());
    }

    public Observable<CalibrationSetModel> getCalibrationExit() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getCalibrationExit().compose(this.<CalibrationSetModel>applySchedulers());
    }

    public Observable<CalibrationSetModel> updateCalibrationSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateCalibrationSet(requestBody).compose(this.<CalibrationSetModel>applySchedulers());
    }

    public Observable<DeviceFormatModel> getStorageInfoList() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getStorageInfoList().compose(this.<DeviceFormatModel>applySchedulers());
    }

    public Observable<DeviceFormatModel> updateStorageFormatList(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateStorageFormatList(requestBody).compose(this.<DeviceFormatModel>applySchedulers());
    }

    public Observable<VideoSetModel> getVideoSetData(JSONObject jsonObject) {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        return homeService.getVideoSetData(requestBody).compose(this.<VideoSetModel>applySchedulers());
    }
    public Observable<VideoShowModel> getVideoShowData() {
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getVideoShowData().compose(this.<VideoShowModel>applySchedulers());
    }

    public Observable<VideoSetModel> updateVideoShowData(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateVideoShowData(requestBody).compose(this.<VideoSetModel>applySchedulers());
    }

    public Observable<VideoSetModel> updateVideoSet(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateVideoSet(requestBody).compose(this.<VideoSetModel>applySchedulers());
    }


    public Observable<GetPlatformInfoModel> getPlatformInfoModel() {
       IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.getPlatfromInfo().compose(this.<GetPlatformInfoModel>applySchedulers());
    }


    public Observable<ActivateNewPlatformsModel> activateNewPlatforms(JSONObject jsonObject){
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
       IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.activateNewPlatforms(requestBody).compose(this.<ActivateNewPlatformsModel>applySchedulers());
    }

    public Observable<LogoutPlatformsModel> logoutPlatforms(JSONObject jsonObject){
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
       IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.logoutPlatforms(requestBody).compose(this.<LogoutPlatformsModel>applySchedulers());
    }

    public Observable<UpdateDeviceConnectStatus> updateDeviceConnectStatus(JSONObject jsonObject){
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.updateDeviceConnectStatus(requestBody).compose(this.<UpdateDeviceConnectStatus>applySchedulers());
    }

    public Observable<RestoreDefaultParamsSetModel> restoreDefaultParamsSet(){
        IHomeService homeService = createServiceFrom(IHomeService.class);
        return homeService.restoreDefaultParamsSet().compose(this.<RestoreDefaultParamsSetModel>applySchedulers());
    }
}
