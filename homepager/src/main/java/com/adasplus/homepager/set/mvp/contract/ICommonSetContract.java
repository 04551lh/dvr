package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

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

        TextView getTvTimeSet();

        TextView getTvSoundSet();

        TextView getTvDormancySet();

        TextView getTvRebootSet();

        TextView getTvFactoryDataReset();

        TextView getTvDeviceFormat();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
