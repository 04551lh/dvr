package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:48
 * Description :
 */
public interface ISoundsContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        SeekBar getSbSoundsSize();

        TextView getTvSave();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
