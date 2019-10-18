package com.adasplus.homepager.connect.adapter;

import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.utils.WifiHelper;
import com.adasplus.homepager.R;
import com.adasplus.homepager.connect.mvp.contract.IConnectWifiItemListener;
import com.adasplus.homepager.connect.mvp.model.ConnectDeviceModel;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/11 10:41
 */
public class ConnectDeviceAdapter extends RecyclerView.Adapter<ConnectDeviceAdapter.ConnectDeviceViewHolder> {

    private List<ConnectDeviceModel> mRealWifiList;
    private IConnectWifiItemListener mConnectWifiItemListener;

    public void setData(List<ConnectDeviceModel> realWifiList) {
        mRealWifiList = realWifiList;
    }

    public void setOnConnectWifiItemListener(IConnectWifiItemListener listener) {
        mConnectWifiItemListener = listener;
    }

    @NonNull
    @Override
    public ConnectDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_connected_device, parent, false);
        return new ConnectDeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectDeviceViewHolder holder, final int position) {
        final ConnectDeviceModel connectDeviceModel = mRealWifiList.get(position);
        String wifiName = connectDeviceModel.getWifiName();
        int level = connectDeviceModel.getLevel();
        ScanResult scanResult = connectDeviceModel.getScanResult();
        holder.mTvWifiName.setText(wifiName);
        //判断当前是否有密码进行加密
        WifiHelper.WifiCipherType wifiCipherWay = WifiHelper.getInstance().getWifiCipherWay(scanResult.capabilities);
        if (wifiCipherWay ==  WifiHelper.WifiCipherType.WIFICIPHER_INVALID || wifiCipherWay == WifiHelper.WifiCipherType.WIFICIPHER_NOPASS){
            holder.mIvIsEncrypt.setVisibility(View.GONE);
        }else if (wifiCipherWay ==  WifiHelper.WifiCipherType.WIFICIPHER_WEP || wifiCipherWay == WifiHelper.WifiCipherType.WIFICIPHER_WPA){
            holder.mIvIsEncrypt.setVisibility(View.VISIBLE);
        }

        //显示WiFi信号值大小
        if (2 == level){
            holder.mIvWifiSignal.setImageResource(R.mipmap.wifi_signal_2);
        }else if (3 ==  level){
            holder.mIvWifiSignal.setImageResource(R.mipmap.wifi_signal_3);
        }else if (4 == level){
            holder.mIvWifiSignal.setImageResource(R.mipmap.wifi_signal_4);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mConnectWifiItemListener != null) {
                    mConnectWifiItemListener.onConnectWifiItem(view, position, connectDeviceModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRealWifiList != null ? mRealWifiList.size() : 0;
    }

    class ConnectDeviceViewHolder extends RecyclerView.ViewHolder {

        TextView mTvWifiName;
        ImageView mIvWifiSignal;
        ImageView mIvIsEncrypt;

        ConnectDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvWifiName = itemView.findViewById(R.id.tv_wifi_name);
            mIvWifiSignal = itemView.findViewById(R.id.iv_wifi_signal);
            mIvIsEncrypt = itemView.findViewById(R.id.iv_is_encrypt);
        }
    }
}
