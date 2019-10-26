package com.adasplus.base.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author:刘净辉
 * Date : 2019/10/26 15:01
 * Description :
 */
public class SearchServiceRunStatusModel {


    @SerializedName("service808")
    private int jt808Service;

    public int getJt808Service() {
        return jt808Service;
    }

    public void setJt808Service(int jt808Service) {
        this.jt808Service = jt808Service;
    }

}
