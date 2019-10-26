package com.adasplus.base.network;

import com.adasplus.base.network.model.FileExportModel;
import com.adasplus.base.network.model.SearchServiceRunStatusModel;
import com.adasplus.base.network.model.SystemInfoModel;
import com.adasplus.base.network.model.TerminalInfoModel;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Author:刘净辉
 * Date : 2019/10/10 17:22
 * Description :
 */
public interface IBaseService {

    @POST(HttpConstant.GET_VEHICLE_INFO)
    Observable<BaseResponse<TerminalInfoModel>> getVehicleInfo();

    @POST(HttpConstant.SAVE_VEHICLE_INFO)
    Observable<BaseResponse<TerminalInfoModel>> saveVehicleInfo(@Body RequestBody requestBody);

    @POST(HttpConstant.GET_FILE_EXPORT_REQUEST)
    Observable<BaseResponse<FileExportModel>> getFileExport();

    @POST(HttpConstant.FILE_EXPORT_DATA)
    Observable<BaseResponse<FileExportModel>> exportFileData(@Body RequestBody requestBody);

    @POST(HttpConstant.GET_SYSTEM_INFO)
    Observable<BaseResponse<SystemInfoModel>> getSystemInfo();

    @POST(HttpConstant.SEARCH_SERVICE_RUN_STATUS)
    Observable<BaseResponse<SearchServiceRunStatusModel>> searchServiceRunStatus();
}
