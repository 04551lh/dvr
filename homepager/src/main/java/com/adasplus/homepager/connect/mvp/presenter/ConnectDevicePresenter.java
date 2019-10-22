package com.adasplus.homepager.connect.mvp.presenter;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.dialog.BasicDialog;
import com.adasplus.base.dialog.CommonDialog;
import com.adasplus.base.dialog.ViewConvertListener;
import com.adasplus.base.dialog.ViewHolder;
import com.adasplus.base.network.HttpConstant;
import com.adasplus.base.receiver.WifiBroadcastReceiver;
import com.adasplus.base.utils.PermissionsPagerUtils;
import com.adasplus.base.utils.WifiConnectStatus;
import com.adasplus.base.utils.WifiHelper;
import com.adasplus.homepager.R;
import com.adasplus.homepager.connect.activity.ConnectDeviceActivity;
import com.adasplus.homepager.connect.adapter.ConnectDeviceAdapter;
import com.adasplus.homepager.connect.mvp.contract.IConnectDeviceContract;
import com.adasplus.homepager.connect.mvp.contract.IConnectWifiItemListener;
import com.adasplus.homepager.connect.mvp.model.ConnectDeviceModel;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/10 12:26
 */
public class ConnectDevicePresenter implements IConnectDeviceContract.Presenter, View.OnClickListener, WifiBroadcastReceiver.IWifiChangeListener, IConnectWifiItemListener, TextWatcher {

    private static final int REQUEST_PERMISSIONS_CODE = 0x01;
    private static final int REQUEST_IN_SETTINGS_OPEN_PERMISSIONS = 0x02;
    private static final String TAG = "ConnectDevicePresenter";

    private static final List<String> mIgnoreSsid = Arrays.asList("0x", "<unknown ssid>");
    private static final int WIFI_MAX_TIME_OUT = 20 * 1000;
    private static final int WIFI_INTERVAL = 1000;

    private List<ConnectDeviceModel> mRealWifiList = new ArrayList<>();
    private IConnectDeviceContract.View mConnectDeviceView;
    private Context mContext;
    private ConnectDeviceActivity mConnectDeviceActivity;
    private RecyclerView mRvWifiHotSpots;

    private String[] mPermissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private WifiBroadcastReceiver mWifiBroadcastReceiver;
    private ConnectDeviceAdapter mConnectDeviceAdapter;
    private int mConnectType = 0; //1 : 连接成功 2: 正在连接
    private TextView mTvNoAvailableDevice;
    private BasicDialog mConnectingDialog;
    private PermissionsPagerUtils mPermissionsPagerUtils;
    private LinearLayout mLlShowWifiList;
    private LinearLayout mLlConnectedWifi;
    private TextView mTvWifiName;
    private ImageView mIvIsEncrypt;
    private ImageView mIvWifiSignal;
    private EditText mEtInputWifiPwd;
    private BasicDialog mInputWifiPwdDialog;
    private ConnectDeviceModel mCurrentClickItemModel;
    private TextView mTvConnect;
    //判断WiFi密码是否显示的明文
    private boolean isWifiPasswordVisible = false;
    private ImageView mIvWifiPwdIsVisible;
    private WifiConnectTimeoutCountDown mWifiConnectTimeoutCountDown;


    public ConnectDevicePresenter(IConnectDeviceContract.View view) {
        mConnectDeviceView = view;
        mContext = (Context) mConnectDeviceView;
        mConnectDeviceActivity = (ConnectDeviceActivity) mConnectDeviceView;
    }

    @Override
    public void initData() {
        ImageView ivBack = mConnectDeviceView.getIvBack();
        mRvWifiHotSpots = mConnectDeviceView.getRvWifiHotSpots();
        mTvNoAvailableDevice = mConnectDeviceView.getTvNoAvailableDevice();
        mLlShowWifiList = mConnectDeviceView.getLlShowWifiList();
        mLlConnectedWifi = mConnectDeviceView.getLlConnectedWifi();
        mTvWifiName = mConnectDeviceView.getTvWifiName();
        mIvIsEncrypt = mConnectDeviceView.getIvIsEncrypt();
        mIvWifiSignal = mConnectDeviceView.getIvWifiSignal();

        mRvWifiHotSpots.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        mConnectDeviceAdapter = new ConnectDeviceAdapter();
        mPermissionsPagerUtils = new PermissionsPagerUtils(mConnectDeviceActivity, REQUEST_IN_SETTINGS_OPEN_PERMISSIONS);

        ivBack.setOnClickListener(this);
        mLlConnectedWifi.setOnClickListener(this);
        requestPermissions();
    }

    @Override
    public void onResume() {
        mWifiBroadcastReceiver = new WifiBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        //监听 WiFi 的开关状态
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        //监听 WiFi 连接状态广播，是否连接了一个有效网络
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        //监听 WiFi 扫描的列表的状态
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        mContext.registerReceiver(mWifiBroadcastReceiver, filter);
        mWifiBroadcastReceiver.setOnWifiChangeListener(this);
    }

    @Override
    public void onPause() {
        if (mWifiBroadcastReceiver != null) {
            mContext.unregisterReceiver(mWifiBroadcastReceiver);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //得到监听请求的权限，知道用户是否接受了所有所需要请求的权限，如果有未接受的权限，判断是否用户在弹出权限的对话框
        //中勾选了禁止询问的按钮，如果勾选了，进行跳转到当前应用的打开权限的界面，让用户进行手动打开所需要的权限。如果没有打开的
        //退出当前界面
        int accpetPermissionsCount = 0;
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            requestPermissionResult:for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    accpetPermissionsCount++;
                } else if (grantResult == PackageManager.PERMISSION_DENIED) {
                    for (String permission : permissions) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(mConnectDeviceActivity, permission)) {
                            Log.e(TAG, "The Forbidden Question button was selected in the dialog box for permission application.");
                            Toast.makeText(mContext, R.string.please_open_the_request_permissions, Toast.LENGTH_SHORT).show();
                            mPermissionsPagerUtils.openPermissionsBySettings();
                        } else {
                            Toast.makeText(mContext, R.string.please_open_the_request_permissions_in_app_manager, Toast.LENGTH_SHORT).show();
                            mConnectDeviceActivity.finish();
                        }
                        break requestPermissionResult;
                    }
                }
            }

            Log.e(TAG, "acceptPermissionsCount :" + accpetPermissionsCount);
            if (accpetPermissionsCount == mPermissions.length) {
                startScanWifiHotSpots();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IN_SETTINGS_OPEN_PERMISSIONS) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, R.string.please_open_permissions_failed, Toast.LENGTH_SHORT).show();
                mConnectDeviceActivity.finish();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (AppOpsManager.MODE_IGNORED != checkOption()) {
                        startScanWifiHotSpots();
                    }
                }
            }
        }
    }


    private void requestPermissions() {
        //判断 Android 系统是6.0或6.0以上 ，如果是 6.0 或 6.0 以上 ，检查定位的权限是否开启。没有开启的话，进行动态请求权限
        //如果 Android 系统是6.0以下，检查 4.4 以上的 Android 系统是否点击了拒绝的按钮（有的手机在点击了拒绝按钮的时候，
        // 默认是勾选了禁止询问的按钮），允许使用定位功能的时候，进行开始扫描附近的 WiFi 热点
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Android System 6.0 or more，Start request permissions");
            ActivityCompat.requestPermissions(mConnectDeviceActivity, mPermissions, REQUEST_PERMISSIONS_CODE);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (AppOpsManager.MODE_IGNORED == checkOption()) {
                    Log.e(TAG, "Has clicked the button to deny permission");
                    mPermissionsPagerUtils.openPermissionsBySettings();
                } else {
                    Log.e(TAG, "start scan wifi hot spots");
                    startScanWifiHotSpots();
                }
            }
        }

    }

    private int checkOption() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Object object = mContext.getSystemService(Context.APP_OPS_SERVICE);
            if (object == null) {
                return -1;
            }
            Class<?> c = object.getClass();
            try {
                Class[] cArg = new Class[3];
                cArg[0] = int.class;
                cArg[1] = int.class;
                cArg[2] = String.class;
                Method checkOp = c.getDeclaredMethod("checkOp", cArg);
                Object invoke = checkOp.invoke(object, 1, Binder.getCallingUid(), mContext.getPackageName());
                if (invoke == null) {
                    return -1;
                }
                return (int) invoke;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    private void startScanWifiHotSpots() {
        if (WifiHelper.getInstance().isWifiEnabled()) {
            WifiHelper.getInstance().startScan();
        }
        mConnectDeviceAdapter.setOnConnectWifiItemListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) { // 返回
            mConnectDeviceActivity.onBackPressed();
        }else if (id == R.id.ll_connected_wifi){
            String wifiName = mTvWifiName.getText().toString().trim();
            boolean givenWifiConnected = WifiHelper.getInstance().isGivenWifiConnected(wifiName);
            if (givenWifiConnected){
                showDisconnectWifiDialog(wifiName);
            }
        }else if (id == R.id.tv_cancel){ // 取消输入WiFi密码的对话框
            if (mInputWifiPwdDialog!=null && mInputWifiPwdDialog.isAdded()){
                mInputWifiPwdDialog.dismiss();
            }
        }else if (id == R.id.tv_connect){ // 开始进行连接WiFi热点
            if (mInputWifiPwdDialog!=null && mInputWifiPwdDialog.isAdded()){
                mInputWifiPwdDialog.dismiss();
            }
            String wifiPwd = mEtInputWifiPwd.getText().toString().trim();
            if (mCurrentClickItemModel != null){
                String wifiName = mCurrentClickItemModel.getWifiName();
                WifiHelper.getInstance().connectWifi(wifiName, wifiPwd, mCurrentClickItemModel.getCapabilities());
                showConnectingDialog();
            }
            mWifiConnectTimeoutCountDown = new WifiConnectTimeoutCountDown(WIFI_MAX_TIME_OUT, WIFI_INTERVAL);
            mWifiConnectTimeoutCountDown.start();
        }else if (id == R.id.iv_wifi_pwd_is_visible){ //显示WiFi的密码是明文还是密文
            if (isWifiPasswordVisible){
                isWifiPasswordVisible = false;
                mIvWifiPwdIsVisible.setImageResource(R.mipmap.wifi_password_gone);
                mEtInputWifiPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }else {
                isWifiPasswordVisible = true;
                mIvWifiPwdIsVisible.setImageResource(R.mipmap.wifi_password_visible);
                mEtInputWifiPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }

            //设置光标的位置
            mEtInputWifiPwd.setSelection(mEtInputWifiPwd.getText().length());
        }
    }

    @Override
    public void onWifiSwitchState(int state) {
        switch (state) {
            case WifiManager.WIFI_STATE_DISABLED: // WiFi开关已经关闭
                Log.i(TAG, "Wifi have already closed");
                mTvNoAvailableDevice.setText(R.string.open_wifi_tips);
                mRealWifiList.clear();
                if (mRealWifiList.size() == 0) {
                    hideRealWifiList();
                } else {
                    showRealWifiList();
                    mConnectDeviceAdapter.notifyDataSetChanged();
                }
                break;
            case WifiManager.WIFI_STATE_DISABLING: // WiFi开关正在关闭
                Log.i(TAG, "Wifi is closing...");
                break;
            case WifiManager.WIFI_STATE_ENABLED: // WiFi开关已经打开
                Log.i(TAG, "Wifi already open");
                WifiHelper.getInstance().startScan();
                break;
            case WifiManager.WIFI_STATE_ENABLING: //WiFi开关正在打开
                Log.i(TAG, "Wifi is opening ...");
                break;
            case WifiManager.WIFI_STATE_UNKNOWN: // 未知错误
                Log.i(TAG, "Unknown error");
                break;
        }
    }

    /**
     * 扫描到有设备的热点，实时进行来显示WiFi的列表
     */
    private void showRealWifiList() {
        mTvNoAvailableDevice.setVisibility(View.GONE);
        mLlShowWifiList.setVisibility(View.VISIBLE);
    }

    /**
     * 没有搜索到可用的设备的时候，进行隐藏实时WiFi列表的显示。并提示没有搜索到可用的设备
     */
    private void hideRealWifiList() {
        mTvNoAvailableDevice.setVisibility(View.VISIBLE);
        mLlShowWifiList.setVisibility(View.GONE);
    }

    @Override
    public void onWifiConnectStatus(NetworkInfo networkInfo) {
        if (NetworkInfo.State.DISCONNECTED == networkInfo.getState()) { // WiFi断开连接
            WifiInfo wifiInfo = WifiHelper.getInstance().getConnectionWifiInfo();
            if (mIgnoreSsid.contains(wifiInfo.getSSID())) {
                return;
            }

            mLlConnectedWifi.setVisibility(View.GONE);
            if (mConnectDeviceAdapter != null) {
                mConnectDeviceAdapter.notifyDataSetChanged();
            }
        } else if (NetworkInfo.State.CONNECTED == networkInfo.getState()) { // WiFi已经连接
            Log.i(TAG, "Wifi  is connected");
            //销毁正在弹出的对话框，停止计时操作
            dismissConnectingDialog();
            cancelWifiConnectTimer();

            //设置当前已经连接的WiFi的 名称、信号值和是否加密
            WifiInfo wifiInfo = WifiHelper.getInstance().getConnectionWifiInfo();
            Log.e(TAG, "current connected wifi name is: " + wifiInfo.getSSID());
            String wifiName = wifiInfo.getSSID();

            setConnectedWifiInfo(wifiName);
            mConnectType = 1;
            showWifiHotSpotsList(wifiName, mConnectType);
        } else if (NetworkInfo.State.CONNECTING == networkInfo.getState()) {
            Log.i(TAG, " Wifi is connecting");
            WifiInfo wifiInfo = WifiHelper.getInstance().getConnectionWifiInfo();
            if (mIgnoreSsid.contains(wifiInfo.getSSID())) {
                Log.e(TAG, "Unknown error!");
                return;
            }
            mConnectType = 2;
            showWifiHotSpotsList(wifiInfo.getSSID(), mConnectType);
        }
    }

    /**
     * 设置已连接 WIFI 信息
     * @param wifiName
     */
    private void setConnectedWifiInfo(String wifiName) {
        ScanResult scanResult = WifiHelper.getInstance().getScanResult(wifiName);
        if (scanResult != null){
            if (scanResult.SSID.contains(HttpConstant.DEVICE_WIFI_TAG)){
                mLlConnectedWifi.setVisibility(View.VISIBLE);
            }
            mTvWifiName.setText(scanResult.SSID);
            int level = WifiHelper.getInstance().getLevel(scanResult.level);
            //显示WiFi信号值大小
            if (2 == level){
                mIvWifiSignal.setImageResource(R.mipmap.wifi_signal_2);
            }else if (3 ==  level){
                mIvWifiSignal.setImageResource(R.mipmap.wifi_signal_3);
            }else if (4 == level){
                mIvWifiSignal.setImageResource(R.mipmap.wifi_signal_4);
            }

            //判断当前是否有密码进行加密
            WifiHelper.WifiCipherType wifiCipherWay = WifiHelper.getInstance().getWifiCipherWay(scanResult.capabilities);
            if (wifiCipherWay ==  WifiHelper.WifiCipherType.WIFICIPHER_INVALID || wifiCipherWay == WifiHelper.WifiCipherType.WIFICIPHER_NOPASS){
                mIvIsEncrypt.setVisibility(View.GONE);
            }else if (wifiCipherWay ==  WifiHelper.WifiCipherType.WIFICIPHER_WEP || wifiCipherWay == WifiHelper.WifiCipherType.WIFICIPHER_WPA){
                mIvIsEncrypt.setVisibility(View.VISIBLE);
            }
        }
    }

    private void cancelWifiConnectTimer() {
        if (mWifiConnectTimeoutCountDown != null) {
            mWifiConnectTimeoutCountDown.cancel();
        }
    }

    @Override
    public void onScanWifiResult() {
        convertScanResult();
        WifiInfo wifiInfo = WifiHelper.getInstance().getConnectionWifiInfo();
        showWifiHotSpotsList(wifiInfo.getSSID(), mConnectType);
    }

    @Override
    public void onWifiPwdError(int wifiResult, String wifiName) {
        if (wifiResult == WifiManager.ERROR_AUTHENTICATING) {
            dismissConnectingDialog();
            //取消倒计时
            cancelWifiConnectTimer();
            Toast.makeText(mContext, R.string.please_input_wifi_pwd_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void showWifiHotSpotsList(String wifiName, int connectType) {
        if (isNullOrEmpty(mRealWifiList)) {
            Log.e(TAG, "real wifi list is empty");
            startScanWifiHotSpots();
            return;
        }

        Collections.sort(mRealWifiList);
        for (int i = 0; i < mRealWifiList.size(); i++) {
            ConnectDeviceModel connectDeviceModel = mRealWifiList.get(i);
            if (("\"" + connectDeviceModel.getWifiName() + "\"").equals(wifiName)) {
                if (connectType == 1) {
                    connectDeviceModel.setState(WifiConnectStatus.WIFI_STATE_CONNECT);
                } else {
                    connectDeviceModel.setState(WifiConnectStatus.WIFI_STATE_ON_CONNECTING);
                }
            }
        }

        if (mConnectDeviceAdapter != null) {
            if (mRealWifiList.size() == 0) {
                mTvNoAvailableDevice.setText(R.string.no_available_device);
                hideRealWifiList();
            } else {
                showRealWifiList();
                if (mConnectType ==  1){
                    for (int i = 0 ; i < mRealWifiList.size() ; i++){
                        ConnectDeviceModel connectDeviceModel = mRealWifiList.get(i);
                        if (("\"" + connectDeviceModel.getWifiName() + "\"").equals(wifiName) ){
                            mRealWifiList.remove(i);
                            break;
                        }
                    }
                }

                mConnectDeviceAdapter.setData(mRealWifiList);
                if (!mConnectDeviceAdapter.hasObservers()) {
                    mRvWifiHotSpots.setAdapter(mConnectDeviceAdapter);
                } else {
                    mConnectDeviceAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void convertScanResult() {
        List<ScanResult> scanResults = WifiHelper.getInstance().getFilterScanResultsBySsid(HttpConstant.DEVICE_WIFI_TAG);
        if (scanResults.size() > 0) {
            mRealWifiList.clear();
            if (!isNullOrEmpty(scanResults)) {
                for (int i = 0; i < scanResults.size(); i++) {
                    ScanResult scanResult = scanResults.get(i);
                    ConnectDeviceModel connectDeviceModel = new ConnectDeviceModel();
                    connectDeviceModel.setWifiName(scanResult.SSID);
                    connectDeviceModel.setState(WifiConnectStatus.WIFI_STATE_UNCONNECT);
                    connectDeviceModel.setCapabilities(scanResult.capabilities);
                    connectDeviceModel.setLevel(WifiHelper.getInstance().getLevel(scanResult.level));
                    connectDeviceModel.setScanResult(scanResult);
                    mRealWifiList.add(connectDeviceModel);
                }
            }
        } else {
            hideRealWifiList();
            Log.e(TAG, "No available equipment was found.");
        }
    }

    private boolean isNullOrEmpty(Collection c) {
        return null == c || c.isEmpty();
    }

    @Override
    public void onConnectWifiItem(View view, int position, ConnectDeviceModel connectDeviceModel) {
        mCurrentClickItemModel = connectDeviceModel;
        String capabilities = connectDeviceModel.getCapabilities();
        String wifiName = connectDeviceModel.getWifiName();
        boolean givenWifiConnected = WifiHelper.getInstance().isGivenWifiConnected(wifiName);
        if (!givenWifiConnected) {
            if (WifiHelper.getInstance().getWifiCipherWay(capabilities).equals(WifiHelper.WifiCipherType.WIFICIPHER_NOPASS)) {
                WifiHelper.getInstance().connectWifi(wifiName, null, capabilities);
            } else {
                showWifiPwdDialog(wifiName);
            }
        }
    }

    private void showWifiPwdDialog(final String wifiName) {
        View view = View.inflate(mContext, R.layout.dialog_input_wifi_pwd, null);
        TextView tv_wifi_name = view.findViewById(R.id.tv_wifi_name);
        mEtInputWifiPwd = view.findViewById(R.id.et_input_wifi_pwd);
        mIvWifiPwdIsVisible = view.findViewById(R.id.iv_wifi_pwd_is_visible);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        mTvConnect = view.findViewById(R.id.tv_connect);

        tv_wifi_name.setText(wifiName);
        tv_cancel.setOnClickListener(this);
        mTvConnect.setOnClickListener(this);
        mIvWifiPwdIsVisible.setOnClickListener(this);
        mEtInputWifiPwd.addTextChangedListener(this);

        float margin = mContext.getResources().getDimension(R.dimen.dp_20);
        mInputWifiPwdDialog = CommonDialog.init()
                .setView(view)
                .setAnimStyle(R.style.BottomAnimStyle)
                .setMargin(margin)
                .setOutCancel(false)
                .show(mConnectDeviceActivity.getSupportFragmentManager());
    }

    private void showConnectingDialog() {
        int wh = (int) mContext.getResources().getDimension(R.dimen.dp_65);
        mConnectingDialog = CommonDialog.init()
                .setLayoutId(R.layout.dialog_load_progress)
                .setAnimStyle(R.style.BottomAnimStyle)
                .setWidth(wh)
                .setHeight(wh)
                .setOutCancel(false)
                .show(mConnectDeviceActivity.getSupportFragmentManager());
    }

    private void showDisconnectWifiDialog(final String wifiName) {
        float dp_20 = mContext.getResources().getDimension(R.dimen.dp_20);
        CommonDialog.init()
                .setLayoutId(R.layout.dialog_disconnect_wifi)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, final BasicDialog dialog) {
                        holder.setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        holder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WifiHelper.getInstance().removeWifiBySsid(wifiName);
                                WifiHelper.getInstance().startScan();
                                mLlConnectedWifi.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        });
                    }
                }).setAnimStyle(R.style.BottomAnimStyle)
                .setMargin(dp_20)
                .setOutCancel(false)
                .show(mConnectDeviceActivity.getSupportFragmentManager());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int length = s.length();
        if (length > 0){
            mTvConnect.setEnabled(true);
            mTvConnect.setClickable(true);
            mTvConnect.setTextColor(Color.BLACK);
        }else {
            mTvConnect.setEnabled(false);
            mTvConnect.setClickable(false);
            mTvConnect.setTextColor(mContext.getResources().getColor(R.color.wifi_pwd_bg_border_color));
        }
    }

    private class WifiConnectTimeoutCountDown extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        WifiConnectTimeoutCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            Log.e(TAG,"current wifi connect time"+(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            Toast.makeText(mConnectDeviceActivity, "wifi连接超时，请重新连接", Toast.LENGTH_SHORT).show();
            dismissConnectingDialog();
            if (mCurrentClickItemModel != null){
                Log.i(TAG,"WIFI Connect timeout,please retry connect!");
                String wifiName = mCurrentClickItemModel.getWifiName();
                WifiHelper.getInstance().removeWifiBySsid(wifiName);
            }
        }
    }

    private void dismissConnectingDialog() {
        if (mConnectingDialog!=null && mConnectingDialog.isAdded()){
            mConnectingDialog.dismiss();
        }
    }
}
