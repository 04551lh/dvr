package com.adasplus.basicinfo.mvp.contract;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.homepager.activate.mvp.model.CarInfoModel;

/**
 * Author:刘净辉
 * Date : 2019/10/11 18:21
 * Description :
 */
public interface ICarInfoContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        TextView getTvPhoneNumber();

        TextView getTvLicensePlateNumber();

        TextView getTvChassisNumber();

        TextView getTvLicensePlateColor();

        TextView getTvTerminalId();

        TextView getTvProvincialDomainId();

        TextView getTvCityAndCountyId();

        LinearLayout getLlCarInfo();

        TextView getTvNoData();

        void showDefaultPlateInfo(CarInfoModel carInfoModel);
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
