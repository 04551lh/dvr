package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dell on 2019/11/15 16:41
 * Description:
 * Emain: 1187278976@qq.com
 */
public interface ICameraSetContract {
    interface Model {
    }

    interface View {

        ImageView getIvCameraSetBack();

        ImageView getIvCameraSwitch();

        TextView getTvCameraSave();

        ImageView getIvWarningSoundSwitch();

        TextView getWarningSoundSave();
    }

    interface Presenter {

        void initData();

        void initListener();

    }
}
