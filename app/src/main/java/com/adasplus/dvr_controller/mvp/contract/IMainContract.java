package com.adasplus.dvr_controller.mvp.contract;

import android.media.Image;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.dvr_controller.fragment.BasicInfoFragment;
import com.adasplus.dvr_controller.fragment.FileExportFragment;
import com.adasplus.dvr_controller.fragment.HomeFragment;

/**
 * Author:刘净辉
 * Date : 2019/9/10
 * Description :
 */
public interface IMainContract {
    interface Model {
    }

    interface View {

        LinearLayout getLlHomePager();

        LinearLayout getLlBasicInfoPager();

        LinearLayout getLlFileExportPager();

        ImageView getIvHomePager();

        TextView  getTvHomePager();

        ImageView getIvBasicInfoPager();

        TextView getTvBasicInfoPager();

        ImageView getIvFileExportPager();

        TextView getTvFileExportPager();
    }

    interface Presenter {
        void initData();

        void initListener();
    }
}
