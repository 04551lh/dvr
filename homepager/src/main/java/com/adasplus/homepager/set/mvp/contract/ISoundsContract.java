package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.view.SignSeekBar;

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

        ImageView getIvSoundsReduce();

        ImageView getIvSoundsAdd();

        SignSeekBar getSsbSoundsValue();

        TextView getTvCurrentSounds();

        TextView getTvSave();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
