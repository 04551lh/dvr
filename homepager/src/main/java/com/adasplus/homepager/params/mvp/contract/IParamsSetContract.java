package com.adasplus.homepager.params.mvp.contract;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Author:刘净辉
 * Date : 2019/9/29 18:46
 * Description :
 */
public interface IParamsSetContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        EditText getEtBumperDistance();

        EditText getEtLeftWheelDistance();

        EditText getEtRightWheelDistance();

//        EditText getEtFrontWheelDistance();

        EditText getEtCameraHeight();

        TextView getTvSaveParamsSet();

        TextView getTvRestoreTheDefaultParams();

    }

    interface Presenter {

        void initWidget();

        void initData();

        void initListener();
    }
}
