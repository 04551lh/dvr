package com.adasplus.base.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.adasplus.base.R;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Author:刘净辉
 * Date : 2019/9/20 12:06
 * Description : 异常处理类
 */
public class ExceptionUtils {

    private static final int JSON_FORMAT_EXCEPTION = -1;
    private static final int FILE_IS_NOT_EXISTS = -3;
    private static final int PARAMS_NO_COMPLETED = -4;
    private static final int INVALID_DATA = -5;
    private static final int INVALID_REQUEST = -6;
    private static final int PLATFORM_CONNECT_FAIL = -7;
    private static final int CAR_REGISTERED = -8;
    private static final int LOCAL_DB_NO_CAR = -9;
    private static final int TERMINAL_REGISTERED = -10;
    private static final int DB_NO_TERMINAL = -11;


    public static void exceptionHandling(Context context
            , Throwable e) {
        if (e instanceof HttpException) {
            // http 请求异常，服务器宕机的时候会进行报这个异常
            onException(context, ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //联网异常 或 不是正确的主机名
            onException(context, ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //网络请求超时
            onException(context, ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            // json 数据的格式解析异常
            onException(context, ExceptionReason.PARSE_ERROR);
        } else {
            //这个是服务器端返回来的异常，根据不同的状态进行做相应的处理
            String code = e.getMessage();
            int statusCode = Integer.parseInt(code);
            if (statusCode == JSON_FORMAT_EXCEPTION) { // json 格式异常
                Toast.makeText(context, R.string.service_back_data_parse_error, Toast.LENGTH_SHORT).show();
            } else if (statusCode == FILE_IS_NOT_EXISTS) { //文件不存在
                Toast.makeText(context, R.string.no_export_file, Toast.LENGTH_SHORT).show();
            } else if (statusCode == PARAMS_NO_COMPLETED) { //参数不完整
                Toast.makeText(context, R.string.params_no_completed, Toast.LENGTH_SHORT).show();
            } else if (statusCode == INVALID_DATA) { //无效数据
                Toast.makeText(context, R.string.invalid_data, Toast.LENGTH_SHORT).show();
            } else if (statusCode == INVALID_REQUEST) { //无效请求
                Toast.makeText(context, R.string.invalid_request, Toast.LENGTH_SHORT).show();
            } else if (statusCode == PLATFORM_CONNECT_FAIL) { //平台连接失败
                Toast.makeText(context, R.string.platform_connect_fail, Toast.LENGTH_SHORT).show();
            } else if (statusCode == CAR_REGISTERED) { //车辆已被注册
                Toast.makeText(context, R.string.car_registered, Toast.LENGTH_SHORT).show();
            } else if (statusCode == LOCAL_DB_NO_CAR) { //数据库中无该车辆
                Toast.makeText(context, R.string.db_no_car, Toast.LENGTH_SHORT).show();
            } else if (statusCode == TERMINAL_REGISTERED) {  //终端已被注册
                Toast.makeText(context, R.string.terminal_registered, Toast.LENGTH_SHORT).show();
            } else if (statusCode == DB_NO_TERMINAL) { //数据库中无该终端
                Toast.makeText(context, R.string.db_no_terminal, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, code, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static void onException(Context context, ExceptionReason reason) {
        switch (reason) {
            case BAD_NETWORK:
                Toast.makeText(context, R.string.service_exception, Toast.LENGTH_SHORT).show();
                break;
            case CONNECT_ERROR:
                Toast.makeText(context, R.string.please_check_network_is_available, Toast.LENGTH_SHORT).show();
                break;
            case CONNECT_TIMEOUT:
                Toast.makeText(context, R.string.service_connect_timeout, Toast.LENGTH_SHORT).show();
                break;
            case PARSE_ERROR:
                Toast.makeText(context, R.string.service_back_data_parse_error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
    }
}
