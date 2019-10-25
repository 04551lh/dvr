package com.adasplus.homepager.set.mvp.presenter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.popup.CommonPopupWindow;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.VideoSetActivity;
import com.adasplus.homepager.set.adapter.ResolutionRatioAdapter;
import com.adasplus.homepager.set.adapter.VideoChannelsSetAdapter;
import com.adasplus.homepager.set.mvp.contract.IVideoSetContract;
import com.adasplus.homepager.set.mvp.contract.OnChannelItemClickListener;
import com.adasplus.homepager.set.mvp.contract.OnResolutionRatioListener;
import com.adasplus.homepager.set.mvp.model.VideoSetModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/15 16:44
 * Description :
 */
public class VideoSetPresenter implements IVideoSetContract.Presenter, View.OnClickListener, OnChannelItemClickListener, OnResolutionRatioListener {

    //视频分辨率的名称
    private static final String QCIF_NAME = "QCIF";
    private static final String CIF_NAME = "CIF";
    private static final String WCIF_NAME = "WCIF";
    private static final String DI_NAME = "DI";
    private static final String WDI_NAME = "WDI";
    private static final String P_720_NAME = "720P";
    private static final String P_1080_NAME = "1080P";

    //默认设置通道1，主码流设置

    private int mChannelNumber;
    private static final int mChannelTotalCount = 6;
    private int mStreamType;

    private IVideoSetContract.View mVideoSetView;
    private VideoSetActivity mVideoSetActivity;
    private ImageView mIvBack;
    private TextView mTvSelectChannelsNumber;
    private TextView mTvMainStreamSet;
    private TextView mTvSubStreamSet;
    private ImageView mIvStreamTotalSwitch;
    private TextView mTvVideoFrameRate;
    private TextView mTvResolutionRatio;
    private ImageView mIvDateTime;
    private ImageView mIvLicensePlateNumber;
    private ImageView mIvChannelName;
    private ImageView mIvGpsSignal;
    private ImageView mIvSpeed;
    private TextView mTvSave;
    private VideoChannelsSetAdapter mVideoChannelsSetAdapter;
    private Button mBtnSub;
    private EditText mEtErrorNumber;
    private Button mBtnAdd;
    private CommonPopupWindow mChannelNumberPopupWindow;
    private List<String> mResolutionRatioList = new ArrayList<>();
    private ResolutionRatioAdapter mResolutionRatioAdapter;
    private int mWidth;
    private CommonPopupWindow mResolutionRatioPopupWindow;
    private VideoSetModel mVideoSetModel;

    private int mIvSelectId;
    private int mIvNoSelectId;


    public VideoSetPresenter(IVideoSetContract.View view) {
        mVideoSetView = view;
        mVideoSetActivity = (VideoSetActivity) view;
    }

    @Override
    public void initData() {
        mChannelNumber = 1;
        mStreamType = 1;
        mIvBack = mVideoSetView.getIvBack();
        mTvSelectChannelsNumber = mVideoSetView.getTvSelectChannelsNumber();
        mTvMainStreamSet = mVideoSetView.getTvMainStreamSet();
        mTvSubStreamSet = mVideoSetView.getTvSubStreamSet();
        mIvStreamTotalSwitch = mVideoSetView.getIvStreamTotalSwitch();
        mTvVideoFrameRate = mVideoSetView.getTvVideoFrameRate();
        mTvResolutionRatio = mVideoSetView.getTvResolutionRatio();
        mIvDateTime = mVideoSetView.getIvDateTime();
        mIvLicensePlateNumber = mVideoSetView.getIvLicensePlateNumber();
        mIvChannelName = mVideoSetView.getIvChannelName();
        mIvGpsSignal = mVideoSetView.getIvGpsSignal();
        mIvSpeed = mVideoSetView.getIvSpeed();
        mTvSave = mVideoSetView.getTvSave();
        mBtnSub = mVideoSetView.getBtnSub();
        mEtErrorNumber = mVideoSetView.getEtErrorNumber();
        mBtnAdd = mVideoSetView.getBtnAdd();
        mWidth = (int) mVideoSetActivity.getResources().getDimension(R.dimen.dp_60);

        mVideoChannelsSetAdapter = new VideoChannelsSetAdapter();
        mResolutionRatioAdapter = new ResolutionRatioAdapter();

        JSONObject jobj = new JSONObject();
        try {
            jobj.put("channelNumber", mChannelNumber);
            jobj.put("streamType", mStreamType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //初始化通道号的数据
        initChannelNumberData();
        //初始化分辨率的数据
        initResolutionRatioData();
        //显示分辨率的弹框
        showResolutionRatioPopup();

        getNetworkData(jobj);

        //设置默认选择的通道号
        String channels = mVideoSetActivity.getString(R.string.channels);
        mTvSelectChannelsNumber.setText(String.format("%s %s", channels, String.valueOf(mChannelNumber)));


        mIvSelectId = R.mipmap.switch_open_icon;
        mIvNoSelectId = R.mipmap.switch_close_icon;
    }

    private void getNetworkData(JSONObject jsonObject) {
        HomeWrapper.getInstance().getVideoSetData(jsonObject).subscribe(new Subscriber<VideoSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mVideoSetActivity, e);
            }

            @Override
            public void onNext(VideoSetModel videoSetModel) {
                mVideoSetModel = videoSetModel;
                showVideoSetData();
            }
        });
    }

    private void showResolutionRatioPopup() {
        View view = View.inflate(mVideoSetActivity, R.layout.popup_common_style, null);
        RecyclerView rv_common_style_list = view.findViewById(R.id.rv_common_style_list);
        rv_common_style_list.setLayoutManager(new LinearLayoutManager(mVideoSetActivity, RecyclerView.VERTICAL, false));
        mResolutionRatioAdapter.setData(mResolutionRatioList);
        rv_common_style_list.setAdapter(mResolutionRatioAdapter);
        mResolutionRatioPopupWindow = new CommonPopupWindow.Builder(mVideoSetActivity)
                .setView(view)
                .setWidthAndHeight(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)
                .create();
    }

    private void initResolutionRatioData() {
        mResolutionRatioList.add(QCIF_NAME);
        mResolutionRatioList.add(CIF_NAME);
        mResolutionRatioList.add(WCIF_NAME);
        mResolutionRatioList.add(DI_NAME);
        mResolutionRatioList.add(WDI_NAME);
        mResolutionRatioList.add(P_720_NAME);
        mResolutionRatioList.add(P_1080_NAME);
    }

    private void initChannelNumberData() {
        View view = View.inflate(mVideoSetActivity, R.layout.popup_common_style, null);
        RecyclerView rv_common_style_list = view.findViewById(R.id.rv_common_style_list);
        rv_common_style_list.setLayoutManager(new LinearLayoutManager(mVideoSetActivity, RecyclerView.VERTICAL, false));
        mVideoChannelsSetAdapter.setData(mChannelTotalCount);
        rv_common_style_list.setAdapter(mVideoChannelsSetAdapter);

        mChannelNumberPopupWindow = new CommonPopupWindow.Builder(mVideoSetActivity)
                .setView(view)
                .setWidthAndHeight(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)
                .create();
    }

    private void showVideoSetData() {
        if (mVideoSetModel != null) {
            isMainStream();
            int enable = mVideoSetModel.getStreamEnable();
            int videoFrameRate = mVideoSetModel.getVideoFrameRate();
            int pictureQuality = mVideoSetModel.getPictureQuality();
            int resolutionRate = mVideoSetModel.getResolutionRate();
            int dateEnable = mVideoSetModel.getDateEnable();
            int plateNumberEnable = mVideoSetModel.getPlateNumberEnable();
            int channelNameEnable = mVideoSetModel.getChannalNameEnable();
            int locationSignalEnable = mVideoSetModel.getLocaltionSignleEnable();
            int speedEnable = mVideoSetModel.getSpeedEnable();
            widgetStatus(enable, videoFrameRate, pictureQuality, resolutionRate, dateEnable,
                    plateNumberEnable, channelNameEnable, locationSignalEnable, speedEnable);
        }
    }

    private void widgetStatus(int enable, int videoFrameRate, int pictureQuality, int resolutionRate, int dateEnable, int plateNumberEnable, int channelNameEnable, int localtionSignleEnable, int speedEnable) {
        //设置主码流的总开关
        if (enable == 1) {
            mIvStreamTotalSwitch.setImageResource(mIvSelectId);
        } else {
            mIvStreamTotalSwitch.setImageResource(mIvNoSelectId);
        }
        //设置视频码率
        mTvVideoFrameRate.setText(String.valueOf(videoFrameRate));
        //设置画质
        mEtErrorNumber.setText(String.valueOf(pictureQuality));

        if (resolutionRate >= 0) {
            String resolutionRatioName = mResolutionRatioList.get(resolutionRate);
            //设置分辨率
            mTvResolutionRatio.setText(resolutionRatioName);
        }

        //设置日期时间开关
        if (dateEnable == 1) {
            mIvDateTime.setImageResource(mIvSelectId);
        } else {
            mIvDateTime.setImageResource(mIvNoSelectId);
        }

        //设置车牌号开关
        if (plateNumberEnable == 1) {
            mIvLicensePlateNumber.setImageResource(mIvSelectId);
        } else {
            mIvLicensePlateNumber.setImageResource(mIvNoSelectId);
        }

        //设置通道名称开关
        if (channelNameEnable == 1) {
            mIvChannelName.setImageResource(mIvSelectId);
        } else {
            mIvChannelName.setImageResource(mIvNoSelectId);
        }

        //设置定位信号开关
        if (localtionSignleEnable == 1) {
            mIvGpsSignal.setImageResource(mIvSelectId);
        } else {
            mIvGpsSignal.setImageResource(mIvNoSelectId);
        }

        //设置速度开关
        if (speedEnable == 1) {
            mIvSpeed.setImageResource(mIvSelectId);
        } else {
            mIvSpeed.setImageResource(mIvNoSelectId);
        }
    }

    @Override
    public void initListener() {
        mIvBack.setOnClickListener(this);
        mTvSelectChannelsNumber.setOnClickListener(this);
        mTvMainStreamSet.setOnClickListener(this);
        mTvSubStreamSet.setOnClickListener(this);
        mIvStreamTotalSwitch.setOnClickListener(this);
        mIvDateTime.setOnClickListener(this);
        mIvLicensePlateNumber.setOnClickListener(this);
        mIvChannelName.setOnClickListener(this);
        mIvGpsSignal.setOnClickListener(this);
        mIvSpeed.setOnClickListener(this);


        mVideoChannelsSetAdapter.setOnItemClickListener(this);
        mTvResolutionRatio.setOnClickListener(this);
        mResolutionRatioAdapter.setOnResolutionRatioItemListener(this);
        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
    }

    private void carSpeedStatus(int enable) {
        if (mVideoSetModel != null) {
            mVideoSetModel.setSpeedEnable(enable);
        }
        showVideoSetData();
    }

    private void locationSignalStatus(int enable) {
        if (mVideoSetModel != null) {
            mVideoSetModel.setLocaltionSignleEnable(enable);
        }
        showVideoSetData();
    }

    private void channelNumberStatus(int enable) {
        if (mVideoSetModel != null) {
            mVideoSetModel.setChannalNameEnable(enable);
        }
        showVideoSetData();
    }

    private void plateNumberStatus(int enable) {
        if (mVideoSetModel != null) {
            mVideoSetModel.setPlateNumberEnable(enable);
        }
        showVideoSetData();
    }

    private void dateTimeSwitchStatus(int enable) {
        if (mVideoSetModel != null) {
            mVideoSetModel.setDateEnable(enable);
        }
        showVideoSetData();
    }

    /**
     * 设置 主码流 或 子码流总开关的状态
     *
     * @param enable
     */
    private void switchTotalStatus(int enable) {
        if (mVideoSetModel != null) {
            mVideoSetModel.setStreamEnable(enable);
            mStreamSwitchSet(enable);
        }
        showVideoSetData();
    }

    /**
     * 设置所有的主，子码流的滑块开关状态
     *
     * @param enable
     */
    private void mStreamSwitchSet(int enable) {
        mVideoSetModel.setStreamEnable(enable);
        mVideoSetModel.setPlateNumberEnable(enable);
        mVideoSetModel.setChannalNameEnable(enable);
        mVideoSetModel.setLocaltionSignleEnable(enable);
        mVideoSetModel.setSpeedEnable(enable);
        mVideoSetModel.setDateEnable(enable);
    }

    @Override
    public void onClick(View v) {
        String pictureQuality = mEtErrorNumber.getText().toString();
        //画质等级
        int pictureQualityLevel = Integer.valueOf(pictureQuality);

        int id = v.getId();
        if (id == R.id.iv_back) {
            mVideoSetActivity.finish();
        } else if (id == R.id.tv_select_channels_number) {
            if (mChannelNumberPopupWindow != null) {
                mChannelNumberPopupWindow.showAsDropDown(mTvSelectChannelsNumber, 0, 0);
            }
        } else if (id == R.id.tv_main_stream_set) { //切换为主码流
            if (mVideoSetModel.getStreamType() == 2) {
                isMainStream();
                mStreamType = 1;
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("channelNumber", mChannelNumber);
                    jobj.put("streamType", mStreamType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                requestChannelNumberData(mChannelNumber);
                getNetworkData(jobj);
            }
        } else if (id == R.id.tv_sub_stream_set) { //切换为子码流
            if (mVideoSetModel.getStreamType() == 1) {
                mStreamType = 2;
                isMainStream();
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("channelNumber", mChannelNumber);
                    jobj.put("streamType", mStreamType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getNetworkData(jobj);
            }
        } else if (id == R.id.iv_stream_total_switch) {
            if (mVideoSetModel.getStreamEnable() == 1) {
                switchTotalStatus(0);
            } else {
                switchTotalStatus(1);
            }
        } else if (id == R.id.iv_date_time) {
            if (mVideoSetModel.getStreamEnable() == 1) {
                if (mVideoSetModel.getDateEnable() == 1) {
                    dateTimeSwitchStatus(0);
                } else {
                    dateTimeSwitchStatus(1);
                }
            }
        } else if (id == R.id.iv_license_plate_number) {
            if (mVideoSetModel.getStreamEnable() == 1) {
                if (mVideoSetModel.getPlateNumberEnable() == 1) {
                    plateNumberStatus(0);
                } else {
                    plateNumberStatus(1);
                }
            }
        } else if (id == R.id.iv_channel_name) {
            if (mVideoSetModel.getStreamEnable() == 1) {

                if (mVideoSetModel.getChannalNameEnable() == 1 && mVideoSetModel.getStreamEnable() == 1) {
                    channelNumberStatus(0);
                } else {
                    channelNumberStatus(1);
                }
            }
        } else if (id == R.id.iv_gps_signal) {
            if (mVideoSetModel.getStreamEnable() == 1) {

                if (mVideoSetModel.getLocaltionSignleEnable() == 1 && mVideoSetModel.getStreamEnable() == 1) {
                    locationSignalStatus(0);
                } else {
                    locationSignalStatus(1);
                }
            }
        } else if (id == R.id.iv_speed) {
            if (mVideoSetModel.getStreamEnable() == 1) {

                if (mVideoSetModel.getSpeedEnable() == 1 && mVideoSetModel.getStreamEnable() == 1) {
                    carSpeedStatus(0);
                } else {
                    carSpeedStatus(1);
                }
            }
        } else if (id == R.id.tv_resolution_ratio) {
            if (mResolutionRatioPopupWindow != null) {
                mResolutionRatioPopupWindow.showAsDropDown(mTvResolutionRatio, 0, 0);
            }
        } else if (id == R.id.btn_sub) { //降低画质等级
            if (pictureQualityLevel <= 1) {
                Toast.makeText(mVideoSetActivity, R.string.picture_quality_min_level_tips, Toast.LENGTH_SHORT).show();
                return;
            }
            pictureQualityLevel--;
            setPictureQualityLevel(pictureQualityLevel);
            mEtErrorNumber.setText(String.valueOf(pictureQualityLevel));
        } else if (id == R.id.btn_add) { //增加画质等级
            if (pictureQualityLevel >= 7) {
                Toast.makeText(mVideoSetActivity, R.string.picture_quality_max_level_tips, Toast.LENGTH_SHORT).show();
                return;
            }
            pictureQualityLevel++;
            setPictureQualityLevel(pictureQualityLevel);
            mEtErrorNumber.setText(String.valueOf(pictureQualityLevel));
        } else if (id == R.id.tv_save) { //保存视频设置的数据
            if (mVideoSetModel != null) {
                mVideoSetModel.setStreamType(mStreamType);
                String json = GsonUtils.getInstance().toJson(mVideoSetModel);
                try {
                    JSONObject jobj = new JSONObject(json);
                    HomeWrapper.getInstance().updateVideoSet(jobj).subscribe(new Subscriber<VideoSetModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ExceptionUtils.exceptionHandling(mVideoSetActivity, e);
                        }

                        @Override
                        public void onNext(VideoSetModel videoSetModel) {
                            Toast.makeText(mVideoSetActivity, R.string.video_set_success, Toast.LENGTH_SHORT).show();
                            mVideoSetActivity.finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 设置视频画面的画质等级
     *
     * @param pictureQualityLevel
     */
    private void setPictureQualityLevel(int pictureQualityLevel) {
        if (mVideoSetModel != null) {
            mVideoSetModel.setPictureQuality(pictureQualityLevel);
        }
        showVideoSetData();
    }

    private void isMainStream() {
        if (mVideoSetModel.getStreamType() == 1) {
            mTvMainStreamSet.setBackgroundResource(R.drawable.ll_left_bg_rectangle_style);
            mTvMainStreamSet.setTextColor(mVideoSetActivity.getResources().getColor(R.color.video_set_font_color));
            mTvSubStreamSet.setTextColor(mVideoSetActivity.getResources().getColor(R.color.font_color_333));
            mTvSubStreamSet.setBackgroundResource(R.drawable.ll_right_white_bg_rectangle_style);
        } else {
            mTvMainStreamSet.setBackgroundResource(R.drawable.ll_left_white_bg_rectangle_style);
            mTvSubStreamSet.setBackgroundResource(R.drawable.ll_right_bg_rectangle_style);
            mTvMainStreamSet.setTextColor(mVideoSetActivity.getResources().getColor(R.color.font_color_333));
            mTvSubStreamSet.setTextColor(mVideoSetActivity.getResources().getColor(R.color.video_set_font_color));
        }
    }

    @Override
    public void onItemClick(int position) {
        int channelNumber = position + 1;
        mChannelNumber = channelNumber;
        String channels = mVideoSetActivity.getString(R.string.channels);
        mTvSelectChannelsNumber.setText(String.format("%s %s", channels, String.valueOf(channelNumber)));
        requestChannelNumberData(channelNumber);

        if (mChannelNumberPopupWindow != null && mChannelNumberPopupWindow.isShowing()) {
            mChannelNumberPopupWindow.dismiss();
        }
    }

    /**
     * 切换通道号的时候进行请求数据
     *
     * @param channelNumber
     */
    private void requestChannelNumberData(int channelNumber) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("channelNumber", channelNumber);
            jobj.put("streamType", mStreamType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HomeWrapper.getInstance().getVideoSetData(jobj).subscribe(new Subscriber<VideoSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mVideoSetActivity, e);
            }

            @Override
            public void onNext(VideoSetModel videoSetModel) {
                //设置当前选择的通道号
                mChannelNumber = videoSetModel.getChannelNumber();

                showVideoSetData();
            }
        });
    }

    //设置
    @Override
    public void onResolutionRatioItemListener(int position) {
        String resolutionRatioName = mResolutionRatioList.get(position);
        mTvResolutionRatio.setText(resolutionRatioName);
        //判断当前选择的是主码流还是子码流
        if (mVideoSetModel != null) {
            mVideoSetModel.setResolutionRate(position);
        }

        if (mResolutionRatioPopupWindow != null && mResolutionRatioPopupWindow.isShowing()) {
            mResolutionRatioPopupWindow.dismiss();
        }
    }
}
