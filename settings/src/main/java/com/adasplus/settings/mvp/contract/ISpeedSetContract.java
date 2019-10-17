package com.adasplus.settings.mvp.contract;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.view.SignSeekBar;
import com.adasplus.base.view.SlideSwitchView;

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

        LinearLayout getLlPulseSpeed();

        CheckBox getCbPulseSpeed();

        TextView getTvManualCalibration();

        TextView getTvCoefficientOfThePulse();

        EditText getEtCoefficientOfThePulseValue();

        TextView getTvProportionValue();

        TextView getTvAutomaticCalibration();

        SlideSwitchView getSsvAutomaticCalibrationSwitch();

        TextView getTvErrorValue();

        Button getBtnSub();

        EditText getEtErrorNumber();

        Button getBtnAdd();

        TextView getTvPercent();

        LinearLayout getLlSimulationSpeed();

        CheckBox getCbSimulationSpeedStatus();

        SignSeekBar getSsbSpeedValue();

        TextView getTvSave();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
