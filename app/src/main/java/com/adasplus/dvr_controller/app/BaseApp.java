package com.adasplus.dvr_controller.app;

import com.adasplus.base.BuildConfig;
import com.adasplus.base.app.App;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Author:刘净辉
 * Date : 2019/9/10 21:04
 */
public class BaseApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
