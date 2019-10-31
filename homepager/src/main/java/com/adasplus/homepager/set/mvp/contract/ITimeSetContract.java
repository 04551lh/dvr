package com.adasplus.homepager.set.mvp.contract;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:17
 * Description :
 */
public interface ITimeSetContract {
    interface Model {
    }

    interface View {

        ImageView getIvTimeBack();

        SwipeRefreshLayout getSwipeRefreshLayoutTimeSet();

        ImageView getIvNetworkTime();
        
        ImageView getIvAutomaticCorrectionWhen();

        ImageView getIvGpsTime();

        ImageView getIvWhenManualCalibration();

        EditText getEtYear();

        EditText getEtMonth();

        EditText getEtDay();

        EditText getEtHours();

        EditText getEtMinutes();

        EditText getEtSeconds();

        TextView getTvTimeSave();

    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
