package com.adasplus.homepager.set.mvp.contract;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Author:刘净辉
 * Date : 2019/9/26 16:02
 * Description :
 */
public interface IDormancySetContract {
    interface Model {
    }

    interface View {

        ImageView getIvDormancyBack();

        SwipeRefreshLayout getSwipeRefreshLayoutDormancySet();

        ImageView getIvCloseHintMessage();

        RelativeLayout getRlHintMessage();

        TextView getTvDormancySave();

        Button getBtnSub();

        EditText getEtErrorNumber();

        Button getBtnAdd();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
