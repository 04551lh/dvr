package com.adasplus.base.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/19 11:15
 * Description : 当Android 6.0 系统点击了拒绝并勾选了禁止询问的按钮（这时候打开权限是让用户跳转到
 * 当前应用的设置界面，进行来让用户打开权限）。由于厂商的更改，所以每个品牌跳转到的设置界面方式不同
 * （需要进行做适配处理）
 */
public class PermissionsPagerUtils {

    private static final String TAG = "PermissionsPagerUtils";

    private static final String HUAWEI = "huawei";
    private static final String HONOR = "honor";
    private static final String VIVO = "vivo";
    private static final String OPPO = "oppo";
    private static final String COOLPAD = "coolpad";
    private static final String MEIZU = "meizu";
    private static final String XIAOMI = "xiaomi";
    private static final String SAMSUNG = "samsung";
    private static final String SONY = "sony";
    private static final String LG = "lg";

    private Activity mActivity;
    private String mPackageName;
    private int mRequestCode;

    public PermissionsPagerUtils(Activity activity, int requestCode) {
        mActivity = activity;
        mRequestCode = requestCode;
        mPackageName = mActivity.getPackageName();
    }

    public void openPermissionsBySettings() {
        String name = Build.MANUFACTURER.toLowerCase();
        Log.e(TAG, "openPermissionsBySettings------>" + name);
        switch (name) {
            case HUAWEI:
            case HONOR:
                goHuaweiManager();
                break;
            case VIVO:
                goVivoManager();
                break;
            case OPPO:
                goOppoManager();
                break;
            case COOLPAD:
                goCoolpadManager();
                break;
            case MEIZU:
                goMeizuManager();
                break;
            case XIAOMI:
                goXiaomiManager();
                break;
            case SAMSUNG:
                goSamsungManager();
                break;
            case SONY:
                goSonyManager();
                break;
            case LG:
                goLgManager();
                break;
            default:
                goToSettings();
                break;
        }
    }

    private void goLgManager() {
        try {
            Intent intent = new Intent(mPackageName);
            ComponentName componentName = new ComponentName("com.android.settings", "com.sec.android.app.cm.ui.CMApplicationListActivity");
            intent.setComponent(componentName);
            mActivity.startActivityForResult(intent, mRequestCode);
        } catch (Exception e) {
            e.printStackTrace();
            goToSettings();
        }
    }

    private void goSonyManager() {
        try {
            Intent intent = new Intent(mPackageName);
            ComponentName componentName = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
            intent.setComponent(componentName);
            mActivity.startActivityForResult(intent, mRequestCode);
        } catch (Exception e) {
            e.printStackTrace();
            goToSettings();
        }
    }

    private void goSamsungManager() {
        try{
            Intent intent = new Intent(mPackageName);
            ComponentName componentName = new ComponentName("com.sec.android.app.capabilitymanager", "com.sec.android.app.cm.ui.CMApplicationListActivity");
            intent.setComponent(componentName);
            mActivity.startActivityForResult(intent,mRequestCode);
        }catch (Exception e){
            e.printStackTrace();
            goToSettings();
        }
    }

    private void goXiaomiManager() {
        String rom = getMiuiVersion();
        Intent intent = new Intent();
        if ("V6".equals(rom) || "V7".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", mPackageName);
        } else if ("V8".equals(rom) || "V9".equals(rom)) {
            intent.setAction("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", mPackageName);
        } else {
            goToSettings();
        }
        mActivity.startActivityForResult(intent, mRequestCode);
    }

    private String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line = null;
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = br.readLine();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    private void goMeizuManager() {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packagename", mPackageName);
            mActivity.startActivityForResult(intent, mRequestCode);
        } catch (Exception e) {
            e.printStackTrace();
            goToSettings();
        }
    }

    private void goCoolpadManager() {
        doStartApplicationWithPackageName("com.yulong.android.security:remote");
    }

    private void goOppoManager() {
        doStartApplicationWithPackageName("com.coloros.safecenter");
    }

    private void goVivoManager() {
        doStartApplicationWithPackageName("com.bairenkeji.icaller");
    }

    private void goHuaweiManager() {
        try {
            Intent intent = new Intent(mPackageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(componentName);
            mActivity.startActivityForResult(intent, mRequestCode);
        } catch (Exception e) {
            e.printStackTrace();
            goToSettings();
        }
    }

    private void goToSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mPackageName, null);
        intent.setData(uri);
        mActivity.startActivityForResult(intent, mRequestCode);
    }

    private void doStartApplicationWithPackageName(String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mActivity.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (packageInfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);
        //通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveInfos = mActivity.getPackageManager().queryIntentActivities(resolveIntent, 0);
        for (int i = 0; i < resolveInfos.size(); i++) {
            ResolveInfo resolveInfo = resolveInfos.get(i);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            Log.i(TAG, activityInfo.packageName + "----" + activityInfo.name);
        }
        ResolveInfo resolveInfo = resolveInfos.iterator().next();
        if (resolveInfo != null) {
            String packageName1 = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(packageName1, className);
            intent.setComponent(componentName);
            try {
                mActivity.startActivityForResult(intent, mRequestCode);
            } catch (Exception e) {
                e.printStackTrace();
                goToSettings();
            }
        }
    }
}
