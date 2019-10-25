package com.adasplus.homepager.activate.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adasplus.homepager.activate.mvp.model.CarInfoModel;


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

        TextView getTvPhoneNumber();

        TextView getTvLicensePlateNumber();

        TextView getTvChassisNumber();

        TextView getTvLicensePlateColor();

        TextView getTvTerminalId();

        TextView getTvProvincialDomainId();

        TextView getTvCityAndCountyId();

        String getType();

        ImageView getIvAddNewPlatform();

        TextView getTvEditBasicInfo();

        void showDefaultPlateInfo(CarInfoModel carInfoModel);

        TextView getTvPlatformList();

        SwipeRefreshLayout getSrlActivatePlatformData();
    }

    interface Presenter {

        void initWidget();

        void initData();

        void initListener();

        void notifyPlatformsSizeShow();
    }
}
