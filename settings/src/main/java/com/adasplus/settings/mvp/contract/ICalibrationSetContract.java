package com.adasplus.settings.mvp.contract;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Author:刘净辉
 * Date : 2019/9/26 19:34
 * Description :
 */
public interface ICalibrationSetContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        CheckBox getCbAutoCalibration();

        CheckBox getCbManualCalibrate();

        EditText getEtCameraHigh();

        Button getBtnUp();

        Button getBtnLeft();

        Button getBtnRight();

        Button getBtnDown();

        TextView getTvSave();

        LinearLayout getLlAutoCalibrate();

        LinearLayout getLlManualCalibrate();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
