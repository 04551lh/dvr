package com.adasplus.homepager.set.mvp.presenter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.popup.CommonPopupWindow;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.VideoShowActivity;
import com.adasplus.homepager.set.adapter.VideoChannelsSetAdapter;
import com.adasplus.homepager.set.mvp.contract.IVideoShowContract;
import com.adasplus.homepager.set.mvp.contract.OnChannelItemClickListener;
import com.adasplus.homepager.set.mvp.model.VideoSetModel;
import com.adasplus.homepager.set.mvp.model.VideoShowModel;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rx.Subscriber;

public class VideoShowPresenter implements IVideoShowContract.Presenter, View.OnClickListener, OnChannelItemClickListener {

    private VideoShowActivity mVideoShowActivity;
    private IVideoShowContract.View mVideoShowView;

    private ImageView mImVideoShowBack;
    private TextView mTvVideoShowChannel;
    private ImageView mImFullScreen;
    private TextView mTvVideoShowSave;

    private static final int mChannelTotalCount = 6;
    private VideoChannelsSetAdapter mVideoChannelsSetAdapter;
    private CommonPopupWindow mChannelNumberPopupWindow;
    private int mChannelNumber;

    private int mFullScreen;
    private int mWidth;
    private String channels;

    public VideoShowPresenter(IVideoShowContract.View view){
        mVideoShowView = view;
        mVideoShowActivity = (VideoShowActivity) view;
    }

    @Override
    public void initData() {
        mImVideoShowBack = mVideoShowView.getImVideoShowBack();
        mTvVideoShowChannel = mVideoShowView.getTvVideoShowChannel();
        mImFullScreen = mVideoShowView.getImFullScreen();
        mTvVideoShowSave = mVideoShowView.getTvVideoShowSave();
        channels = mVideoShowActivity.getString(R.string.channels);
        mWidth = (int) mVideoShowActivity.getResources().getDimension(R.dimen.dp_60);
        mVideoChannelsSetAdapter = new VideoChannelsSetAdapter();
        getNetWork();
        initChannelNumberData();

    }

    @Override
    public void initListener() {
        mImVideoShowBack.setOnClickListener(this);
        mImFullScreen.setOnClickListener(this);
        mTvVideoShowSave.setOnClickListener(this);
        mVideoChannelsSetAdapter.setOnItemClickListener(this);
        mTvVideoShowChannel.setOnClickListener(this);

    }

    private void initChannelNumberData() {
        View view = View.inflate(mVideoShowActivity, R.layout.popup_common_style, null);
        RecyclerView rv_common_style_list = view.findViewById(R.id.rv_common_style_list);
        rv_common_style_list.setLayoutManager(new LinearLayoutManager(mVideoShowActivity, RecyclerView.VERTICAL, false));
        mVideoChannelsSetAdapter.setData(mChannelTotalCount);
        rv_common_style_list.setAdapter(mVideoChannelsSetAdapter);

        mChannelNumberPopupWindow = new CommonPopupWindow.Builder(mVideoShowActivity)
                .setView(view)
                .setWidthAndHeight(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)
                .create();
    }

    @Override
    public void getNetWork() {
        HomeWrapper.getInstance().getVideoShowData().subscribe(new Subscriber<VideoShowModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mVideoShowActivity, e);
            }

            @Override
            public void onNext(VideoShowModel videoShowModel) {
                mFullScreen = videoShowModel.getShowFullScreen();
                mChannelNumber = videoShowModel.getChannelNumber();
                mTvVideoShowChannel.setText(String.format("%s %s", channels, String.valueOf(mChannelNumber)));
                if(mFullScreen == 1){
                    mImFullScreen.setImageResource(R.mipmap.switch_open_icon);
                }else{
                    mImFullScreen.setImageResource(R.mipmap.switch_close_icon);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.iv_video_show_back){
            mVideoShowActivity.finish();
        }
        else if(id == R.id.iv_full_screen){
            if(mFullScreen == 1){
                mImFullScreen.setImageResource(R.mipmap.switch_close_icon);
                mFullScreen = 0;
            }else{
                mImFullScreen.setImageResource(R.mipmap.switch_open_icon);
                mFullScreen = 1;
            }
        }else if (id == R.id.tv_video_show_channel) {
                if (mChannelNumberPopupWindow != null) {
                    mChannelNumberPopupWindow.showAsDropDown(mTvVideoShowChannel, 0, 0);
                }
            }
        else if(id == R.id.tv_video_show_save){
            if(mChannelNumber< 0 || mChannelNumber >6){
                mVideoShowActivity.showToast(R.string.channel_number_one_six);
                return;
            }
            JSONObject jobj = new JSONObject();
            try {
                jobj.put("channelNumber", mChannelNumber);
                jobj.put("showFullScreen", mFullScreen);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            HomeWrapper.getInstance().updateVideoShowData(jobj).subscribe(new Subscriber<VideoSetModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(VideoSetModel videoSetModel) {
                    mVideoShowActivity.showToast(R.string.hannel_number_set_save_success);
                    mVideoShowActivity.finish();
                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {
        mChannelNumber = position+1;
        mTvVideoShowChannel.setText(String.format("%s %s", channels, String.valueOf(position+1)));
        if (mChannelNumberPopupWindow != null && mChannelNumberPopupWindow.isShowing()) {
            mChannelNumberPopupWindow.dismiss();
        }

    }
}
