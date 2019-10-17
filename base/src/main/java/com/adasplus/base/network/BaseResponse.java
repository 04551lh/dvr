package com.adasplus.base.network;

/**
 * Author:刘净辉
 * Date : 2019/9/19 17:06
 */
public class BaseResponse<T> {
    private int StatusCode;
    private T Result;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statuesCode) {
        StatusCode = statuesCode;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "StatusCode=" + StatusCode +
                ", Result=" + Result +
                '}';
    }
}
