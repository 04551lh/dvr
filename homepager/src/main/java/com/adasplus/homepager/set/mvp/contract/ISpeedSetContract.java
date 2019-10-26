package com.adasplus.homepager.set.mvp.contract;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;

import com.adasplus.base.view.SignSeekBar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Author:刘净辉
 * Date : 2019/9/25 11:32
 * Description :
 */
public interface ISpeedSetContract {
    interface Model {
    }
    interface View {
        ImageView getIvback();

        ImageView getIvPulseSpeed();

        SwipeRefreshLayout getSwipeContainer();

        TextView getTvManualCalibration();

        ImageView getIvManualCalibration();

        TextView getTvCoefficientOfThePulse();

        EditText getEtCoefficientOfThePulseValue();

        TextView getTvAutomaticCalibration();

        ImageView getIvAutomaticCalibration();

        TextView getTvErrorValue();

        Button getBtnSub();

        EditText getEtErrorNumber();

        Button getBtnAdd();

        ImageView getIvSimulationSpeedStatus();

        SignSeekBar getSsbSpeedValue();

        TextView getTvCurrentSpeed();

        TextView getTvSave();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
