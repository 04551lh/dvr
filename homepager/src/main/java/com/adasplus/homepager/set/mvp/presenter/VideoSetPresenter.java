package com.adasplus.homepager.set.mvp.presenter;

import android.content.Intent;
import android.graphics.Color;
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
    private boolean isMainStream = true;
    private int mChannelTotalCount = 0;
    private int mSelectChannelNumber = 0;
    private CommonPopupWindow mChannelNumberPopupWindow;
    private VideoSetModel.MainStreamBean mMainStream;
    private VideoSetModel.SubStreamBean mSubStream;
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

        Intent intent = mVideoSetActivity.getIntent();
        mVideoSetModel = intent.getParcelableExtra("videoSetModel");
        mMainStream = intent.getParcelableExtra("mainStream");
        mSubStream = intent.getParcelableExtra("subStream");

        int channelCount = mVideoSetModel.getChannelCount();
        int channelNumber = mVideoSetModel.getChannelNumber();
        mChannelTotalCount = channelCount;
        mSelectChannelNumber = channelNumber;
        //初始化通道号的数据
        initChannelNumberData();
        //初始化分辨率的数据
        initResolutionRatioData();
        //显示分辨率的弹框
        showResolutionRatioPopup();
        //设置视频开关所有的设置信息
        showVideoSetData();

        //设置默认选择的通道号
        String channels = mVideoSetActivity.getString(R.string.channels);
        mTvSelectChannelsNumber.setText(String.format("%s %s", channels, String.valueOf(channelNumber)));

        mIvSelectId = R.mipmap.switch_open_icon;
        mIvNoSelectId = R.mipmap.switch_close_icon;
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
        if (isMainStream) {
            if (mMainStream != null) {
                int enable = mMainStream.getEnable();
                int videoFrameRate = mMainStream.getVideoFrameRate();
                int pictureQuality = mMainStream.getPictureQuality();
                int resolutionRate = mMainStream.getResolutionRate();
                int dateEnable = mMainStream.getDateEnable();
                int plateNumberEnable = mMainStream.getPlateNumberEnable();
                int channelNameEnable = mMainStream.getChannalNameEnable();
                int locationSignalEnable = mMainStream.getLocaltionSignleEnable();
                int speedEnable = mMainStream.getSpeedEnable();
                widgetStatus(enable, videoFrameRate, pictureQuality, resolutionRate, dateEnable,
                        plateNumberEnable, channelNameEnable, locationSignalEnable, speedEnable);
            }
        } else {
            if (mSubStream != null) {
                int enable = mSubStream.getEnable();
                int videoFrameRate = mSubStream.getVideoFrameRate();
                int pictureQuality = mSubStream.getPictureQuality();
                int resolutionRate = mSubStream.getResolutionRate();
                int dateEnable = mSubStream.getDateEnable();
                int plateNumberEnable = mSubStream.getPlateNumberEnable();
                int channelNameEnable = mSubStream.getChannalNameEnable();
                int locationSignalEnable = mSubStream.getLocaltionSignleEnable();
                int speedEnable = mSubStream.getSpeedEnable();
                widgetStatus(enable, videoFrameRate, pictureQuality, resolutionRate, dateEnable,
                        plateNumberEnable, channelNameEnable, locationSignalEnable, speedEnable);
            }
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
        mVideoChannelsSetAdapter.setOnItemClickListener(this);
        mTvResolutionRatio.setOnClickListener(this);
        mResolutionRatioAdapter.setOnResolutionRatioItemListener(this);
        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);
        mTvSave.setOnClickListener(this);

//        //设置总开关的逻辑处理
//        mIvStreamTotalSwitch.setOnSwitchStatusChangeListener(new ImageView.OnSwitchStatusChangeListener() {
//            @Override
//            public void onSwitchStatus(boolean status) {
//                switchTotalStatus(status);
//            }
//        });
//
//        //日期时间开关
//        mIvDateTime.setOnSwitchStatusChangeListener(new ImageView.OnSwitchStatusChangeListener() {
//            @Override
//            public void onSwitchStatus(boolean status) {
//                dateTimeSwitchStatus(status);
//            }
//        });
//
//        //车牌号开关状态
//        mIvLicensePlateNumber.setOnSwitchStatusChangeListener(new ImageView.OnSwitchStatusChangeListener() {
//            @Override
//            public void onSwitchStatus(boolean status) {
//                plateNumberStatus(status);
//            }
//        });
//
//        //通道名称的开关状态监听
//        mIvChannelName.setOnSwitchStatusChangeListener(new ImageView.OnSwitchStatusChangeListener() {
//            @Override
//            public void onSwitchStatus(boolean status) {
//                channelNumberStatus(status);
//            }
//        });
//
//        //设置定位开关状态监听
//        mIvGpsSignal.setOnSwitchStatusChangeListener(new ImageView.OnSwitchStatusChangeListener() {
//            @Override
//            public void onSwitchStatus(boolean status) {
//                locationSignalStatus(status);
//            }
//        });
//
//        //设置速度开关状态监听
//        mIvSpeed.setOnSwitchStatusChangeListener(new ImageView.OnSwitchStatusChangeListener() {
//            @Override
//            public void onSwitchStatus(boolean status) {
//                carSpeedStatus(status);
//            }
//        });
    }

    private void carSpeedStatus(boolean status) {
        if (isMainStream) {
            if (mMainStream != null) {
                mMainStream.setEnable(status ? 1 : 0);
                mMainStream.setSpeedEnable(status ? 1 : 0);
            }
        } else {
            if (mSubStream != null) {
                mSubStream.setEnable(status ? 1 : 0);
                mSubStream.setSpeedEnable(status ? 1 : 0);
            }
        }
        showVideoSetData();
    }

    private void locationSignalStatus(boolean status) {
        if (isMainStream) {
            if (mMainStream != null) {
                mMainStream.setEnable(status ? 1 : 0);
                mMainStream.setLocaltionSignleEnable(status ? 1 : 0);
            }
        } else {
            if (mSubStream != null) {
                mSubStream.setEnable(status ? 1 : 0);
                mSubStream.setLocaltionSignleEnable(status ? 1 : 0);
            }
        }
        showVideoSetData();
    }

    private void channelNumberStatus(boolean status) {
        if (isMainStream) {
            if (mMainStream != null) {
                mMainStream.setEnable(status ? 1 : 0);
                mMainStream.setChannalNameEnable(status ? 1 : 0);
            }
        } else {
            if (mSubStream != null) {
                mSubStream.setEnable(status ? 1 : 0);
                mSubStream.setChannalNameEnable(status ? 1 : 0);
            }
        }
        showVideoSetData();
    }

    private void plateNumberStatus(boolean status) {
        if (isMainStream) {
            if (mMainStream != null) {
                mMainStream.setEnable(status ? 1 : 0);
                mMainStream.setPlateNumberEnable(status ? 1 : 0);
            }
        } else {
            if (mSubStream != null) {
                mSubStream.setEnable(status ? 1 : 0);
                mSubStream.setPlateNumberEnable(status ? 1 : 0);
            }
        }
        showVideoSetData();
    }

    private void dateTimeSwitchStatus(boolean status) {
        if (isMainStream) {
            if (mMainStream != null) {
                mMainStream.setEnable(status ? 1 : 0);
                mMainStream.setDateEnable(status ? 1 : 0);
            }
        } else {
            if (mSubStream != null) {
                mSubStream.setEnable(status ? 1 : 0);
                mSubStream.setDateEnable(status ? 1 : 0);
            }
        }
        showVideoSetData();
    }

    /**
     * 设置 主码流 或 子码流总开关的状态
     *
     * @param status
     */
    private void switchTotalStatus(boolean status) {
        if (isMainStream) {
            if (mMainStream != null) {
                if (status) {
                    mainStreamSwitchSet(1);
                } else {
                    mainStreamSwitchSet(0);
                }
            }
        } else {
            if (mSubStream != null) {
                if (status) {
                    subStreamSwitchSet(1);
                } else {
                    subStreamSwitchSet(0);
                }
            }
        }
        showVideoSetData();
    }

    /**
     * 设置所有的子码流的滑块开关状态
     * @param value
     */
    private void subStreamSwitchSet(int value) {
        mSubStream.setEnable(value);
        mSubStream.setEnable(value);
        mSubStream.setPlateNumberEnable(value);
        mSubStream.setChannalNameEnable(value);
        mSubStream.setLocaltionSignleEnable(value);
        mSubStream.setSpeedEnable(value);
        mSubStream.setDateEnable(value);
    }

    /**
     * 设置所有的主码流的滑块开关状态
     * @param value
     */
    private void mainStreamSwitchSet(int value) {
        mMainStream.setEnable(value);
        mMainStream.setEnable(value);
        mMainStream.setPlateNumberEnable(value);
        mMainStream.setChannalNameEnable(value);
        mMainStream.setLocaltionSignleEnable(value);
        mMainStream.setSpeedEnable(value);
        mMainStream.setDateEnable(value);
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
            isMainStream = true;
            isMainStream();
            showVideoSetData();
        } else if (id == R.id.tv_sub_stream_set) { //切换为子码流
            isMainStream = false;
            isMainStream();
            showVideoSetData();
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
                mVideoSetModel.setChannelNumber(mSelectChannelNumber);
                mVideoSetModel.setMainStream(mMainStream);
                mVideoSetModel.setSubStream(mSubStream);
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
        if (isMainStream) {
            if (mMainStream != null) {
                mMainStream.setPictureQuality(pictureQualityLevel);
            }
        } else {
            if (mSubStream != null) {
                mSubStream.setPictureQuality(pictureQualityLevel);
            }
        }
        showVideoSetData();
    }

    private void isMainStream() {
        if (isMainStream) {
            mTvMainStreamSet.setBackgroundResource(R.color.system_navigation_bar_color);
            mTvSubStreamSet.setBackgroundColor(Color.WHITE);
            mTvMainStreamSet.setTextColor(Color.WHITE);
            mTvSubStreamSet.setTextColor(Color.BLACK);
        } else {
            mTvMainStreamSet.setBackgroundColor(Color.WHITE);
            mTvSubStreamSet.setBackgroundResource(R.color.system_navigation_bar_color);
            mTvMainStreamSet.setTextColor(Color.BLACK);
            mTvSubStreamSet.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onItemClick(int position) {
        int channelNumber = position + 1;
        String channels = mVideoSetActivity.getString(R.string.channels);
        mTvSelectChannelsNumber.setText(String.format("%s %s", channels, String.valueOf(channelNumber)));
        isMainStream = true;
        requestChannelNumberData(channelNumber);

        if (mChannelNumberPopupWindow != null && mChannelNumberPopupWindow.isShowing()) {
            mChannelNumberPopupWindow.dismiss();
        }
    }

    /**
     * 切换通道号的时候进行请求数据
     * @param channelNumber
     */
    private void requestChannelNumberData(int channelNumber) {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("channelNumber", channelNumber);
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
                mSelectChannelNumber = videoSetModel.getChannelNumber();
                //设置当前选择的主码流
                mMainStream = videoSetModel.getMainStream();
                //设置当前选择的子码流
                mSubStream = videoSetModel.getSubStream();
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
        if (isMainStream) {
            if (mMainStream != null) {
                mMainStream.setResolutionRate(position);
            }
        } else {
            if (mSubStream != null) {
                mSubStream.setResolutionRate(position);
            }
        }

        if (mResolutionRatioPopupWindow != null && mResolutionRatioPopupWindow.isShowing()) {
            mResolutionRatioPopupWindow.dismiss();
        }
    }
}
