package com.adasplus.homepager.activate.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


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

        ImageView getIvAddNewPlatform();

        TextView getTvEditBasicInfo();
    }

    interface Presenter {

        void initWidget();

        void initData();

        void initListener();

        void notifyPlatformsSizeShow();
    }
}
