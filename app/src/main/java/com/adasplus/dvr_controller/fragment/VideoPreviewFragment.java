package com.adasplus.dvr_controller.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adasplus.base.base.BaseFragment;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.mvp.contract.IVideoPreviewContract;
import com.adasplus.dvr_controller.mvp.presenter.VideoPreviewPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoPreviewFragment extends BaseFragment implements IVideoPreviewContract.View {

    private VideoPreviewPresenter mVideoPreviewPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_preview;
    }

    @Override
    protected void onFirstUserVisible() {
        if (mVideoPreviewPresenter != null){
            mVideoPreviewPresenter.initData();
        }
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        mVideoPreviewPresenter = new VideoPreviewPresenter(getActivity(), this );
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        if (mVideoPreviewPresenter != null){
            mVideoPreviewPresenter.findViewById(view);
        }
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        if (mVideoPreviewPresenter != null){
            mVideoPreviewPresenter.setClickEvent(view);
        }
    }

}
