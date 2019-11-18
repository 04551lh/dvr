package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Author:刘净辉
 * Date : 2019/9/26 17:14
 * Description :
 */
public interface IADASCalibrationSetContract {
    interface Model {
    }

    interface View {
        ImageView getIvADASCalibrationBack();

        LinearLayout getLlADASCalibrationSet();

        LinearLayout getLlADASCalibrationParamSet();

    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
