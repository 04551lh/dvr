package com.adasplus.basicinfo.network;

import com.adasplus.base.network.BaseResponse;
import com.adasplus.base.network.HttpConstant;
import com.adasplus.basicinfo.mvp.model.DriverInfoModel;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Author:刘净辉
 * Date : 2019/10/14 17:18
 * Description :
 */
public interface IBasicInfoService {

    @POST(HttpConstant.GET_DRIVER_INFO)
    Observable<BaseResponse<DriverInfoModel>> getDriverInfo();
}
