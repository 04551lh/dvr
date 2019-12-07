package com.adasplus.dvr_controller.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Author:刘净辉
 * Date : 2019/10/18 16:44
 * Description :
 */
public interface IExportFileContract {
    interface Model {
    }

    interface View {
        ImageView getIvExportBack();
        SwipeRefreshLayout getSrlRefreshFileExportData();
        TextView getTvFileType();
        TextView getTvChannelValue();
        TextView getTvStreamTypeValue();
        TextView getTvStartDate();
        TextView getTvStartTime();
        TextView getTvEndData();
        TextView getTvEndTime();
        TextView getTvStorageTypeValue();
        TextView getTvStorageNameValue();
        TextView getTvExportFile();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
