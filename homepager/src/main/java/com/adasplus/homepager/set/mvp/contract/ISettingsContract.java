package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Author:刘净辉
 * Date : 2019/9/25 10:50
 * Description :
 */
public interface ISettingsContract {
    interface Model {
    }

    interface View {
        ImageView getIvBack();

        LinearLayout getLlSpeedSet();

        LinearLayout getLlNetworkSet();

        LinearLayout getLlCanSet();

        LinearLayout getLlCalibrationSet();

        LinearLayout getLlWarningSet();

        LinearLayout getLlCommonSet();

        LinearLayout getLlVideoSet();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
