package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

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

        TextView getTvSpeedSet();

        TextView getTvNetworkSet();

        TextView getTvCanSet();

        TextView getTvCalibrationSet();

        TextView getTvWarningSet();

        TextView getTvCommonSet();

        TextView getTvVideoSet();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
