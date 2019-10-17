package com.adasplus.base.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/29 11:05
 * Description : 主要用于解析和转换数据
 */
public class GsonUtils {

    private static volatile GsonUtils instance;
    private Gson mGson;

    private GsonUtils(){
        mGson = new Gson();
    }

    public static GsonUtils getInstance(){
        if (instance == null){
            synchronized (GsonUtils.class){
                if (instance == null){
                    instance = new GsonUtils();
                }
            }
        }
        return instance;
    }


    public String toJson(Object obj){
        return mGson.toJson(obj);
    }

    public <T> T jsonToBean(String json,Class<T> clazz){
        T t = mGson.fromJson(json, clazz);
        return t;
    }

    public <T> List<T> jsonToList(String json,Class<T> clazz){
        List<T> jsonToList = new ArrayList<>();
        JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement element : jsonArray){
            jsonToList.add(mGson.fromJson(element,clazz));
        }
        return jsonToList;
    }
}
