package com.adasplus.base.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

/**
 * Author:刘净辉
 * Date : 2019/10/22 17:59
 * Description :
 */
public class WindowUtils {

    public static boolean canOverDraw(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            boolean canDrawOverlays = Settings.canDrawOverlays(context);
            return canDrawOverlays || canDrawOverlaysO(context);
        }
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(context);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean canDrawOverlaysO(Context context){
        AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        if (appOpsMgr == null){
            return false;
        }
        return false;
    }
}
