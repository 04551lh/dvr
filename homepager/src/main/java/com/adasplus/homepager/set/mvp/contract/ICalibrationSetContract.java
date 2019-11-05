package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Author:刘净辉
 * Date : 2019/9/26 19:34
 * Description :
 */
public interface ICalibrationSetContract {
    interface Model {
    }

    interface View {

        ImageView getIvCalibrationBack();

        SwipeRefreshLayout getSwipeRefreshLayoutCalibrationSet();

        ImageView getIvAutoCalibration();

        ImageView getIvManualCalibrate();

        TextView getTvCameraHeight();

        EditText getEtCameraHeight();

        TextView getTvStep();

        EditText getEtStep();

        ImageView getIvUp();

        ImageView getIvLeft();

        ImageView getIvRight();

        ImageView getIvDown();

        TextView getTvCalibrationSave();

    }

    interface Presenter {

        void initData();

        void initListener();

        void onMyDestroy();

    }
}
