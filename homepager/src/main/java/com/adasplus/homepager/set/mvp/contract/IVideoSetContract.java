package com.adasplus.homepager.set.mvp.contract;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.view.SlideSwitchView;

/**
 * Author:刘净辉
 * Date : 2019/10/15 16:44
 * Description :
 */
public interface IVideoSetContract {
    interface Model {
    }

    interface View {
        ImageView getIvBack();

        TextView getTvSelectChannelsNumber();

        TextView getTvMainStreamSet();

        TextView getTvSubStreamSet();

        SlideSwitchView getSsvStreamTotalSwitch();

        TextView getTvVideoFrameRate();

        TextView getTvResolutionRatio();

        SlideSwitchView getSsvDateTime();

        SlideSwitchView getSsvLicensePlateNumber();

        SlideSwitchView getSsvChannelName();

        SlideSwitchView getSsvGpsSignal();

        SlideSwitchView getSsvSpeed();

        TextView getTvSave();

        Button getBtnSub();

        EditText getEtErrorNumber();

        Button getBtnAdd();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
