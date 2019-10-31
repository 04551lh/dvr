package com.adasplus.homepager.set.mvp.contract;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Author:刘净辉
 * Date : 2019/10/15 16:44
 * Description :
 */
public interface IVideoSetContract {
    interface Model {
    }

    interface View {
        ImageView getIvVideoBack();

        SwipeRefreshLayout getSwipeRefreshLayoutVideoSet();

        TextView getTvSelectChannelsNumber();

        TextView getTvMainStreamSet();

        TextView getTvSubStreamSet();

        ImageView getIvStreamTotalSwitch();

        EditText getEtVideoFrameRate();

        TextView getTvResolutionRatio();

        ImageView getIvDateTime();

        ImageView getIvLicensePlateNumber();

        ImageView getIvChannelName();

        ImageView getIvGpsSignal();

        ImageView getIvSpeed();

        TextView getTvVideoSave();

        Button getBtnSub();

        EditText getEtErrorNumber();

        Button getBtnAdd();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
