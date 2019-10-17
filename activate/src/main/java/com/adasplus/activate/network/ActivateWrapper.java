package com.adasplus.activate.network;

import com.adasplus.activate.mvp.model.ActivateNewPlatformsModel;
import com.adasplus.activate.mvp.model.GetPlatformInfoModel;
import com.adasplus.activate.mvp.model.LogoutPlatformsModel;
import com.adasplus.base.network.HttpConstant;
import com.adasplus.base.network.RetrofitHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Author:刘净辉
 * Date : 2019/9/23 15:01
 * Description :
 */
public class ActivateWrapper extends RetrofitHelper {

    private static volatile ActivateWrapper instance;
    private ActivateWrapper(){

    }

    public static ActivateWrapper getInstance(){
        if (instance == null){
            synchronized (ActivateWrapper.class){
                if (instance == null){
                    instance = new ActivateWrapper();
                }
            }
        }
        return instance;
    }

    public Observable<GetPlatformInfoModel> getPlatformInfoModel() {
        IActivateService activateService = createServiceFrom(IActivateService.class);
        return activateService.getPlatfromInfo().compose(this.<GetPlatformInfoModel>applySchedulers());
    }


    public Observable<ActivateNewPlatformsModel> activateNewPlatforms(JSONArray jsonArray){
        RequestBody requestBody = RequestBody.create(MediaType.parse(HttpConstant.MEDIA_TYPE), jsonArray.toString());
        IActivateService activateService = createServiceFrom(IActivateService.class);
        return activateService.activateNewPlatforms(requestBody).compose(this.<ActivateNewPlatformsModel>applySchedulers());
    }

    public Observable<LogoutPlatformsModel> logoutPlatforms(JSONObject jsonObject){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        IActivateService activateService = createServiceFrom(IActivateService.class);
        return activateService.logoutPlatforms(requestBody).compose(this.<LogoutPlatformsModel>applySchedulers());
    }

}
