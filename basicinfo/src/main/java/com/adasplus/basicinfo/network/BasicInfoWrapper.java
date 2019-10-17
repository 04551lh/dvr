package com.adasplus.basicinfo.network;

import com.adasplus.base.network.RetrofitHelper;
import com.adasplus.basicinfo.mvp.model.DriverInfoModel;

import rx.Observable;

/**
 * Author:刘净辉
 * Date : 2019/10/14 17:26
 * Description :
 */
public class BasicInfoWrapper extends RetrofitHelper {

    private static volatile  BasicInfoWrapper instance;

    private BasicInfoWrapper(){

    }

    public static BasicInfoWrapper getInstance(){
        if (instance == null){
            synchronized (BasicInfoWrapper.class){
                if (instance == null){
                    instance = new BasicInfoWrapper();
                }
            }
        }
        return instance;
    }


    public Observable<DriverInfoModel> getDriverInfo(){
        IBasicInfoService basicInfoService = createServiceFrom(IBasicInfoService.class);
        return basicInfoService.getDriverInfo().compose(this.<DriverInfoModel>applySchedulers());
    }
}
