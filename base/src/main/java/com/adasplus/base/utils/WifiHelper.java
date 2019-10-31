package com.adasplus.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:刘净辉
 * Date : 2019/9/10 10:49
 * Description : 管理 wifi 的工具类:
 */
public class WifiHelper {

    private static final String TAG = "WifiHelper";

    private static final String WEP_CIPHER_WAY = "WEP";
    private static final String WPA_CIPHER_WAY = "WPA";
    private static final String WPA_2_CIPHER_WAY = "WPA2";
    private static final String WPS_CIPHER_WAY = "WPS";

    @SuppressLint("StaticFieldLeak")
    private static volatile WifiHelper instance;
    private Context mContext;
    private WifiManager mWifiManager;
    private ConnectivityManager mConnectivityManager;

    private WifiHelper() {

    }

    public static WifiHelper getInstance() {
        if (instance == null) {
            synchronized (WifiHelper.class) {
                if (instance == null) {
                    instance = new WifiHelper();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isWifiEnabled() {
        WifiManager wifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null) {
            Log.e(TAG, "FATAL ERROR! get wifi manager is empty!");
            return false;
        }
        return wifiManager.isWifiEnabled();
    }

    public void startScan() {
        boolean scanResult = mWifiManager.startScan();
        Log.e(TAG, "scanResult is " + scanResult);
    }

    private void disConnectionWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    /**
     * 判断当前 的 wifi 是否已经连接
     *
     * @param ssid
     * @return
     */
    public boolean isGivenWifiConnected(String ssid) {
        return isWifiConnected() && getCurrentSSID().equals(ssid);
    }

    private boolean isWifiConnected() {
        if (mConnectivityManager == null) {
            Log.e(TAG, "FATAL ERROR! connectivity manager is empty!");
            return false;
        }

        NetworkInfo networkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo == null) {
            Log.e(TAG, "FATAL ERROR! Network Info object is empty");
            return false;
        }

        if (networkInfo.isConnected()) {
            return true;
        } else {
            return networkInfo.isAvailable();
        }
    }

    private String getCurrentSSID() {
        String ssid = mWifiManager.getConnectionInfo().getSSID();
        if (ssid.length() > 0) {
            if (ssid.substring(0, 1).equals("\"") && ssid.substring(ssid.length() - 1).equals("\"")) {
                ssid = ssid.substring(1, ssid.length() - 1);
            }
            Log.i(TAG, "current wifi ssid is " + ssid);
        }
        return trimSSID(ssid);
    }

    private String trimSSID(String ssid) {
        if (ssid.length() > 0) {
            if (ssid.substring(0, 1).equals("\"") && ssid.substring(ssid.length() - 1).equals("\"")) {
                ssid = ssid.substring(1, ssid.length() - 1);
            }
            Log.i(TAG, "trim ssid is " + ssid);
        }
        return ssid;
    }

    public void connectWifi(String ssid, String pwd, String capabilities) {
        WifiConfiguration wifiConfiguration = createWifiInfo(ssid, pwd, getWifiCipherWay(capabilities));
        int wcgId = mWifiManager.addNetwork(wifiConfiguration);
        boolean enabled = mWifiManager.enableNetwork(wcgId, true);

        Log.i(TAG, "Enabled network is " + enabled);
        if (enabled) {
            boolean reconnect = mWifiManager.reconnect();
            Log.i(TAG, "Reconnect result is " + reconnect);
        }
    }

    public void removeWifiBySsid(String targetSsid) {
        Log.i(TAG, "Remove wifi by ssid , remove's target ssid is " + targetSsid);
        //返回已经配置的 wifi 连接列表
        List<WifiConfiguration> wifiConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration wifiConfig : wifiConfigs) {
            String ssid = wifiConfig.SSID;
            if (targetSsid.equals(ssid)) {
                disConnectionWifi(wifiConfig.networkId);
                removeNetWork(wifiConfig.networkId);
            }
        }
    }

    private void removeNetWork(int netId) {
        boolean removeNetWork = mWifiManager.removeNetwork(netId);
        Log.i(TAG, "RemoveNetWork:" + removeNetWork);
        mWifiManager.saveConfiguration();
    }

    private WifiConfiguration isExists(String ssid) {
        List<WifiConfiguration> existConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existConfig : existConfigs) {
            if (existConfig.SSID.equals("\"" + ssid + "\"")) {
                return existConfig;
            }
        }
        return null;
    }

    private WifiConfiguration createWifiInfo(String ssid, String pwd, WifiCipherType type) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + ssid + "\"";
        WifiConfiguration tempConfig = isExists(ssid);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        if (type == WifiCipherType.WIFICIPHER_NOPASS) { //wifi 无密码
            wifiConfiguration.wepKeys[0] = "";
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfiguration.wepTxKeyIndex = 0;
        }

        if (type == WifiCipherType.WIFICIPHER_WEP) {
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.wepKeys[0] = "\"" + pwd + "\"";
            wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfiguration.wepTxKeyIndex = 0;
        }

        if (type == WifiCipherType.WIFICIPHER_WPA) {
            wifiConfiguration.preSharedKey = "\"" + pwd + "\"";
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
        }
        return wifiConfiguration;
    }

    public enum WifiCipherType {
        WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
    }

    public WifiCipherType getWifiCipherWay(String capabilities) {
        if (TextUtils.isEmpty(capabilities)) {
            return WifiCipherType.WIFICIPHER_INVALID; //无效
        } else if (capabilities.contains(WEP_CIPHER_WAY)) {
            return WifiCipherType.WIFICIPHER_WEP;
        } else if (capabilities.contains(WPA_CIPHER_WAY) || capabilities.contains(WPA_2_CIPHER_WAY) || capabilities.contains(WPS_CIPHER_WAY)) {
            return WifiCipherType.WIFICIPHER_WPA;
        } else {
            return WifiCipherType.WIFICIPHER_NOPASS;
        }
    }

    private List<ScanResult> getScanResults() {
        return mWifiManager.getScanResults();
    }

    /**
     * 将 wifi 信号弱的进行过滤掉
     *
     * @param scanResults
     * @return
     */
    private List<ScanResult> filterScanResults(List<ScanResult> scanResults) {
        Map<String, ScanResult> filterMap = new LinkedHashMap<>();
        for (ScanResult scanResult : scanResults) {
            if (!TextUtils.isEmpty(scanResult.SSID)) {
                if (filterMap.containsKey(scanResult.SSID)) {
                    ScanResult scanResult1 = filterMap.get(scanResult.SSID);
                    if (scanResult1 == null) {
                        filterMap.put(scanResult.SSID, scanResult);
                    } else {
                        if (scanResult.level > scanResult1.level) {
                            filterMap.put(scanResult.SSID, scanResult);
                        }
                    }
                    continue;
                }
                filterMap.put(scanResult.SSID, scanResult);
            }
        }
        scanResults.clear();
        scanResults.addAll(filterMap.values());
        return scanResults;
    }

    private List<ScanResult> filterScanResults(String targetSsid, List<ScanResult> scanResults) {
        Map<String, ScanResult> filterMap = new LinkedHashMap<>();
        for (ScanResult scanResult : scanResults) {
            if (!TextUtils.isEmpty(scanResult.SSID)) {
                if (scanResult.SSID.contains(targetSsid)) {
                    filterMap.put(scanResult.SSID, scanResult);
                }
            }
        }
        scanResults.clear();
        scanResults.addAll(filterMap.values());
        return scanResults;
    }

    public List<ScanResult> getFilterScanResults() {
        return filterScanResults(getScanResults());
    }

    public List<ScanResult> getFilterScanResultsBySsid(String targetSsid) {
        return filterScanResults(targetSsid, getScanResults());
    }


    public WifiInfo getConnectionWifiInfo() {
        return mWifiManager.getConnectionInfo();
    }

    public ScanResult getScanResult(String wifiName){
        ScanResult scanResult = null;
        List<ScanResult> scanResults = getScanResults();
        for (ScanResult result:scanResults){
            if (("\"" + result.SSID +"\"").equals(wifiName)){
                scanResult = result;
                break;
            }
        }
        return scanResult;
    }

    public List<WifiConfiguration> getConfigurationNetworks() {
        return mWifiManager.getConfiguredNetworks();
    }


    public String wifiToIpAddress(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    public int getLevel(int level) {
        if (Math.abs(level) < 50) {
            return 1;
        } else if (Math.abs(level) < 75) {
            return 2;
        } else if (Math.abs(level) < 90) {
            return 3;
        } else {
            return 4;
        }
    }
}
