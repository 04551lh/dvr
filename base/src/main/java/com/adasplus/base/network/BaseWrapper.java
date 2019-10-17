package com.adasplus.base.network;

import com.adasplus.base.network.model.FileExportModel;
import com.adasplus.base.network.model.SystemInfoModel;
import com.adasplus.base.network.model.TerminalInfoModel;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Author:刘净辉
 * Date : 2019/10/10 17:21
 * Description :
 */
public class BaseWrapper extends RetrofitHelper {

    private static volatile BaseWrapper instance;

    private BaseWrapper() {

    }

    public static BaseWrapper getInstance() {
        if (instance == null) {
            synchronized (BaseWrapper.class) {
                if (instance == null) {
                    instance = new BaseWrapper();
                }
            }
        }
        return instance;
    }


    public Observable<TerminalInfoModel> getVehicleInfo() {
        IBaseService baseService = createServiceFrom(IBaseService.class);
        return baseService.getVehicleInfo().compose(this.<TerminalInfoModel>applySchedulers());
    }


    public Observable<TerminalInfoModel> saveVehicleInfo(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IBaseService baseService = createServiceFrom(IBaseService.class);
        return baseService.saveVehicleInfo(requestBody).compose(this.<TerminalInfoModel>applySchedulers());
    }

    public Observable<FileExportModel> getFileExport() {
        IBaseService baseService = createServiceFrom(IBaseService.class);
        return baseService.getFileExport().compose(this.<FileExportModel>applySchedulers());
    }

    public Observable<FileExportModel> exportFileData(JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonObject.toString());
        IBaseService baseService = createServiceFrom(IBaseService.class);
        return baseService.exportFileData(requestBody).compose(this.<FileExportModel>applySchedulers());
    }

    public Observable<SystemInfoModel> getSystemInfo(){
        IBaseService baseService = createServiceFrom(IBaseService.class);
        return baseService.getSystemInfo().compose(this.<SystemInfoModel>applySchedulers());
    }
}
