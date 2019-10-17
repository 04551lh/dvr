package com.adasplus.dvr_controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.activate.activity.ActivateDeviceActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.TerminalInfoModel;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.dvr_controller.R;
import com.adasplus.base.utils.WifiHelper;
import com.adasplus.settings.activity.ParamsSetActivity;
import com.alibaba.android.arouter.launcher.ARouter;

import rx.Subscriber;


/**
 * Author:刘净辉
 * Date : 2019/9/10 11:33
 */
public class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuAdapter.NavigationMenuViewHolder> {

    private int[] mNavigationBarText;
    private int[] mNavigationBarIcon;

    public void setData(int[] navigationBarText, int[] navigationBarIcon) {
        mNavigationBarText = navigationBarText;
        mNavigationBarIcon = navigationBarIcon;
    }

    @NonNull
    @Override
    public NavigationMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation_menu, parent, false);
        return new NavigationMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationMenuViewHolder holder, int position) {
        int navigationMenuName = mNavigationBarText[position];
        int icon = mNavigationBarIcon[position];
        holder.mTvNavigationMenuName.setText(navigationMenuName);
        holder.mIvNavigationMenuIcon.setImageResource(icon);
    }

    @Override
    public int getItemCount() {
        return mNavigationBarText != null ? mNavigationBarText.length : 0;
    }

    class NavigationMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mIvNavigationMenuIcon;
        TextView mTvNavigationMenuName;

        NavigationMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvNavigationMenuIcon = itemView.findViewById(R.id.iv_navigation_menu_icon);
            mTvNavigationMenuName = itemView.findViewById(R.id.tv_navigation_menu_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            final Context context = view.getContext();
            boolean wifiEnabled = WifiHelper.getInstance().isWifiEnabled();
            if (!wifiEnabled) {
                Toast.makeText(context, R.string.please_open_wifi_switch, Toast.LENGTH_SHORT).show();
                return;
            }
            WifiInfo wifiInfo = WifiHelper.getInstance().getConnectionWifiInfo();
            String ssid = wifiInfo.getSSID();
            Log.i("NavigationMenuAdapter", "ssid----->" + ssid);
            if (position == 0) {//设备连接
                ARouter.getInstance()
                        .build(ActivityPathConstant.CONNECT_DEVICE_PATH)
                        .navigation();
            } else {
                //判断当前连接的WiFi是否设备热点WiFi
                if (!ssid.contains("ky_test")) {
                    Toast.makeText(context, R.string.please_connect_device, Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (position) {
                    case 1: //激活设备
                        BaseWrapper.getInstance().getVehicleInfo().subscribe(new Subscriber<TerminalInfoModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                ExceptionUtils.exceptionHandling(context,e);
                            }

                            @Override
                            public void onNext(TerminalInfoModel terminalInfoModel) {
                                String phoneNumber = terminalInfoModel.getPhoneNumber();
                                if (TextUtils.isEmpty(phoneNumber)){
                                    ARouter.getInstance()
                                            .build(ActivityPathConstant.FILL_TERMINAL_INFO_PATH)
                                            .withString("type",ActivityPathConstant.FILL_TERMINAL_INFO)
                                            .withBoolean("isFillTerminalInfo",true)
                                            .navigation();
                                }else {
                                    ARouter.getInstance()
                                            .build(ActivityPathConstant.ACTIVATE_DEVICE_PATH)
                                            .withString("type",ActivityPathConstant.ADD_NEW_PLATFORMS)
                                            .navigation();
                                }
                            }
                        });
                        break;
                    case 2:
                        goToActivity(ActivityPathConstant.PARAMS_PATH);
                        break;
                    case 3: //设置
                        goToActivity(ActivityPathConstant.SETTINGS_PATH);
                        break;
                    case 4:
                        goToActivity(ActivityPathConstant.BASIC_INFO_PATH);
                        break;
                    case 5:
                        goToActivity(ActivityPathConstant.FILE_EXPORT_PATH);
                        break;
                }
            }

        }

        private void goToActivity(String path){
            ARouter.getInstance()
                    .build(path)
                    .navigation();
        }
    }
}
