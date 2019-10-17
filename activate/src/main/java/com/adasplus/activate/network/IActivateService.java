package com.adasplus.activate.network;

import com.adasplus.activate.mvp.model.ActivateNewPlatformsModel;
import com.adasplus.activate.mvp.model.GetPlatformInfoModel;
import com.adasplus.activate.mvp.model.LogoutPlatformsModel;
import com.adasplus.base.network.BaseResponse;
import com.adasplus.base.network.HttpConstant;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author:刘净辉
 * Date : 2019/9/23 14:59
 * Description : 这个接口类 用于进行来封装一些与激活模块相关的一些接口
 */
public interface IActivateService {

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

}
