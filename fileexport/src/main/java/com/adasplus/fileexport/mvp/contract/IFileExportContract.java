package com.adasplus.fileexport.mvp.contract;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Author:刘净辉
 * Date : 2019/9/21 18:23
 * Description :
 */
public interface IFileExportContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        TextView getTvFileType();

        TextView getTvSelectDate();

        TextView getTvStartTime();

        TextView getTvEndTime();

        TextView getTvChannel();

        TextView getTvStreamType();

        TextView getTvExportFile();

        ProgressBar getPbExportProgressbar();

        TextView getTvCurrentExportSize();

        TextView getTvChannelValue();

        TextView getTvStreamTypeValue();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
