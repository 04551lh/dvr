package com.adasplus.basicinfo.mvp.contract;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Author:刘净辉
 * Date : 2019/10/11 18:53
 * Description :
 */
public interface IDriverInfoContract {
    interface Model {
    }

    interface View {
        ImageView getIvBack();

        TextView getTvNoData();

        LinearLayout getLlDriverInfo();

        TextView getTvDriverName();

        TextView getTvDriverSex();

        TextView getTvDriverIdCard();

        TextView getTvDriverIsLicense();

        TextView getTvValidityOfDriverIsLicense();

        TextView getTvDriverNvq();

        TextView getTvCertificationAuthority();

        TextView getTvBindVehicle();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
