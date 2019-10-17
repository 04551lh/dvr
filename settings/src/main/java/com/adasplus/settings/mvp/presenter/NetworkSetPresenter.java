package com.adasplus.settings.mvp.presenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.PatternUtils;
import com.adasplus.settings.R;
import com.adasplus.settings.activity.NetworkSetActivity;
import com.adasplus.settings.mvp.contract.INetworkSetContract;
import com.adasplus.settings.mvp.model.NetworkSetModel;
import com.adasplus.settings.network.SettingsWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/25 18:05
 * Description :
 */
public class NetworkSetPresenter implements INetworkSetContract.Presenter, View.OnClickListener {

    private INetworkSetContract.View mNetworkSetView;
    private NetworkSetActivity mNetworkSetActivity;
    private EditText mEtWirelessIpAddress;
    private EditText mEtWirelessMaskCode;
    private EditText mEtWirelessGateway;
    private EditText mEtWirelessMacAddress;
    private EditText mEtWiredIpAddress;
    private EditText mEtWiredMaskCode;
    private EditText mEtWiredGateway;
    private EditText mEtWiredMacAddress;

    public NetworkSetPresenter(INetworkSetContract.View view){
        mNetworkSetView = view;
        mNetworkSetActivity = (NetworkSetActivity) view;
    }

    @Override
    public void initData() {

        mEtWirelessIpAddress = mNetworkSetView.getEtWirelessIpAddress();
        mEtWirelessMaskCode = mNetworkSetView.getEtWirelessMaskCode();
        mEtWirelessGateway = mNetworkSetView.getEtWirelessGateway();
        mEtWirelessMacAddress = mNetworkSetView.getEtWirelessMacAddress();
        mEtWiredIpAddress = mNetworkSetView.getEtWiredIpAddress();
        mEtWiredMaskCode = mNetworkSetView.getEtWiredMaskCode();
        mEtWiredGateway = mNetworkSetView.getEtWiredGateway();
        mEtWiredMacAddress = mNetworkSetView.getEtWiredMacAddress();

        //获取设备中的网络设置的相关信息，分为无线网络配置和有线网络设置
        SettingsWrapper.getInstance().getDeviceValueByNetworkRequest().subscribe(new Subscriber<NetworkSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mNetworkSetActivity,e);
            }

            @Override
            public void onNext(NetworkSetModel networkSetModel) {
                //无线网络设置
                NetworkSetModel.WirelessNetworkConfigBean wirelessNetworkConfig = networkSetModel.getWirelessNetworkConfig();
                //有线网络设置
                NetworkSetModel.WiredNetworkConfigBean wiredNetworkConfig = networkSetModel.getWiredNetworkConfig();

                //设置无线网络的ip地址
                mEtWirelessIpAddress.setText(wirelessNetworkConfig.getIpAddress());
                //设置无线网络的掩码
                mEtWirelessMaskCode.setText(wirelessNetworkConfig.getMask());
                //设置无线网络的网关
                mEtWirelessGateway.setText(wirelessNetworkConfig.getGateway());
                //设置无线网络的MAC地址
                mEtWirelessMacAddress.setText(wirelessNetworkConfig.getMac());

                //设置有线网络的ip地址
                mEtWiredIpAddress.setText(wiredNetworkConfig.getIpAddress());
                //设置有线网络的掩码
                mEtWiredMaskCode.setText(wiredNetworkConfig.getMask());
                //设置有线网络的网关
                mEtWiredGateway.setText(wiredNetworkConfig.getGateway());
                //设置有线网络的MAC地址
                mEtWiredMacAddress.setText(wiredNetworkConfig.getMac());
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mNetworkSetView.getIvBack();
        TextView tvSave = mNetworkSetView.getTvSave();

        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            mNetworkSetActivity.finish();
        }else if (id == R.id.tv_save){
            //获取无线网络设置中的 ip地址、掩码、网关和MAC地址，有线网络设置中的 ip地址、掩码、
            // 网关和MAC地址
            String wirelessIpAddress = mEtWirelessIpAddress.getText().toString().trim();
            String wirelessMaskCode = mEtWirelessMaskCode.getText().toString().trim();
            String wirelessGateway = mEtWirelessGateway.getText().toString().trim();
            String wirelessMacAddress = mEtWirelessMacAddress.getText().toString().trim();
            String wiredIpAddress = mEtWiredIpAddress.getText().toString().trim();
            String wiredMaskCode = mEtWiredMaskCode.getText().toString().trim();
            String wiredGateway = mEtWiredGateway.getText().toString().trim();
            String wiredMacAddress = mEtWiredMacAddress.getText().toString().trim();

            if (!TextUtils.isEmpty(wirelessIpAddress)&&!PatternUtils.checkIpAddress(wirelessIpAddress)){
                Toast.makeText(mNetworkSetActivity, R.string.wireless_ip_address_format_error, Toast.LENGTH_SHORT).show();
                return;
            }

            if (!TextUtils.isEmpty(wiredIpAddress)&&!PatternUtils.checkIpAddress(wiredIpAddress)){
                Toast.makeText(mNetworkSetActivity, R.string.wired_ip_address_format_error, Toast.LENGTH_SHORT).show();
                return;
            }


            JSONObject jsonObject = new JSONObject();
            JSONObject wirelessNetworkConfig = new JSONObject();
            JSONObject wiredNetworkConfig = new JSONObject();

            try {
                wirelessNetworkConfig.put("ipAddress",wirelessIpAddress);
                wirelessNetworkConfig.put("mask",wirelessMaskCode);
                wirelessNetworkConfig.put("gateway",wirelessGateway);
                wirelessNetworkConfig.put("mac",wirelessMacAddress);

                wiredNetworkConfig.put("ipAddress",wiredIpAddress);
                wiredNetworkConfig.put("mask",wiredMaskCode);
                wiredNetworkConfig.put("gateway",wiredGateway);
                wiredNetworkConfig.put("mac",wiredMacAddress);

                jsonObject.put("wirelessNetworkConfig",wirelessNetworkConfig);
                jsonObject.put("wiredNetworkConfig",wiredNetworkConfig);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //保存更改最新的网络设置数据
            SettingsWrapper.getInstance().updateNetworkSet(jsonObject).subscribe(new Subscriber<NetworkSetModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ExceptionUtils.exceptionHandling(mNetworkSetActivity,e);
                }

                @Override
                public void onNext(NetworkSetModel networkSetModel) {
                    Toast.makeText(mNetworkSetActivity, "保存联网设置成功", Toast.LENGTH_SHORT).show();
                    mNetworkSetActivity.finish();
                }
            });
        }
    }
}
