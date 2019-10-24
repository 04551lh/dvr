package com.adasplus.homepager.activate.mvp.contract;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.adasplus.homepager.activate.mvp.model.AdministrativeRegionCodeModel;
import com.adasplus.homepager.activate.mvp.model.CarColorModel;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/12 15:00
 * Description :
 */
public interface IFillTerminalInfoContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        ImageView getIvCloseHintMessage();

        RelativeLayout getRlHintMessage();

        EditText getEtPlatformPhoneNumber();

        EditText getEtLicensePlateNumber();

        TextView getTvLicensePlateColor();

        EditText getEtTerminalId();

        TextView getTvProvincialDomainId();

        TextView getTvCityAndCountyId();

        TextView getTvSaveTerminalInfo();

        String getType();

        boolean isFillTerminalInfo();

        TextView getTvTitle();

        EditText getEtChassisNumber();

        void initLicensePlateColor(List<CarColorModel> car_color);

        void initProvincialDomainId(List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList);

        void showDefaultCityId(List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList);

    }

    interface Presenter {
        void initData();

        void initListener();

        void setAdministrativeRegionCodeData(List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList);
    }
}
