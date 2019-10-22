package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Author:刘净辉
 * Date : 2019/9/26 14:53
 * Description :
 */
public interface ICommonSetContract {
    interface Model {
    }

    interface View {
        ImageView getIvBack();

        LinearLayout getLlTimeSet();

        LinearLayout getLlSoundSet();

        LinearLayout getLlDormancySet();

        LinearLayout getLlRebootSet();

        LinearLayout getLlFactoryDataReset();

        LinearLayout getLlDeviceFormat();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
