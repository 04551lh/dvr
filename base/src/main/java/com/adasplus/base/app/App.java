package com.adasplus.base.app;

import android.app.Application;

import com.adasplus.base.utils.WifiHelper;

/**
 * Author:刘净辉
 * Date : 2019/9/21 11:51
 * Description :
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WifiHelper.getInstance().init(this);
    }
}
