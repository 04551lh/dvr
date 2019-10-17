package com.adasplus.activate.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.activate.network.ActivateWrapper;

/**
 * Author:刘净辉
 * Date : 2019/9/12 14:11
 */
public interface IActivateDeviceContract {
    interface Model {
    }

    interface View {
        ImageView getIvBack();

        RecyclerView getRvActivatedPlatforms();

        TextView getTvNoData();

        TextView getTvAddNewPlatforms();

        TextView getTvPhoneNumber();

        TextView getTvLicensePlateNumber();

        TextView getTvChassisNumber();

        TextView getTvLicensePlateColor();

        TextView getTvTerminalId();

        TextView getTvProvincialDomainId();

        TextView getTvCityAndCountyId();

        TextView getTvPlatformList();

        String getType();

        TextView getTvActionBarAddPlatforms();

        ImageView getIvEditBasicInfoIcon();
    }

    interface Presenter {

        void initWidget();

        void initData();

        void initListener();
    }
}
