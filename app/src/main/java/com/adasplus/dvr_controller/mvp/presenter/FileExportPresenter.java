package com.adasplus.dvr_controller.mvp.presenter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.FileExportModel;
import com.adasplus.base.popup.CommonPopupWindow;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.adapter.ChannelsNumberAdapter;
import com.adasplus.dvr_controller.adapter.StreamTypeAdapter;
import com.adasplus.dvr_controller.mvp.contract.IFileExportContract;
import com.adasplus.dvr_controller.mvp.contract.OnItemChannelNumbersClickListener;
import com.adasplus.dvr_controller.mvp.contract.OnItemStreamTypeClickListener;
import com.adasplus.dvr_controller.mvp.model.ChannelNumbersModel;
import com.adasplus.dvr_controller.mvp.model.StreamTypeModel;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/18 16:44
 * Description :
 */
public class FileExportPresenter implements IFileExportContract.Presenter, View.OnClickListener, OnItemChannelNumbersClickListener, OnItemStreamTypeClickListener {
    
    private IFileExportContract.View mFileExportView;
    private Activity mActivity;
    private ImageView mIvBack;
    private TextView mTvFileType;
    private TextView mTvSelectDate;
    private TextView mTvStartTime;
    private TextView mTvEndTime;
    private TextView mTvChannel;
    private TextView mTvStreamType;
    private TextView mTvExportFile;
    private ProgressBar mPbExportProgressbar;
    private TextView mTvCurrentExportSize;
    private TextView mTvChannelValue;
    private TextView mTvStreamTypeValue;
    private static final String START_TIME_FLAG = "start";
    private static final String END_TIME_FLAG = "end";

    private CommonPopupWindow mFileTypePopupWindow;
    private TextView mTvLog;
    private TextView mTvVideo;
    private List<ChannelNumbersModel> mChannelNumbersList = new ArrayList<>();
    private List<StreamTypeModel> mStreamTypeList = new ArrayList<>();

    private ChannelsNumberAdapter mChannelsNumberAdapter;
    private CommonPopupWindow mChannelNumbersPopupWindow;
    private String mChannels;
    private StreamTypeAdapter mStreamTypeAdapter;
    private int mWidth;
    private CommonPopupWindow mStreamTypePopupWindow;
    private ChannelNumbersModel mCurrentChannelNumbersModel;
    private int mSelectFileType  = 0;

    public FileExportPresenter(Activity activity, IFileExportContract.View view){
        mFileExportView = view;
        mActivity = activity;
    }

    @Override
    public void initData() {

        mWidth = (int) mActivity.getResources().getDimension(R.dimen.dp_200);

        isSelectLog(true);
        String all = mActivity.getResources().getString(R.string.all);
        String main_stream = mActivity.getResources().getString(R.string.main_stream);
        String child_stream = mActivity.getResources().getString(R.string.child_stream);

        String[] streamTypeContent = {all, main_stream, child_stream};
        initStreamTypeData(streamTypeContent);
        //默认显示的是系统的当前的时间
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        mTvSelectDate.setText(format);
        mChannels = mActivity.getResources().getString(R.string.channels);

        BaseWrapper.getInstance().getFileExport().subscribe(new Subscriber<FileExportModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mActivity, e);
            }

            @Override
            public void onNext(FileExportModel fileExportModel) {
                int channelCount = fileExportModel.getChannelCount();
                for (int i = 0; i < channelCount; i++) {
                    if (i == 0) {
                        mTvChannelValue.setText(String.format("%s %s", mChannels, String.valueOf((i + 1))));
                        showStreamTypeTextContent(i);
                        mCurrentChannelNumbersModel = new ChannelNumbersModel((i + 1), 0);
                        mCurrentChannelNumbersModel.setChecked(true);
                        mChannelNumbersList.add(mCurrentChannelNumbersModel);
                    } else {
                        ChannelNumbersModel channelNumbersModel = new ChannelNumbersModel((i + 1), 0);
                        channelNumbersModel.setChecked(false);
                        mChannelNumbersList.add(channelNumbersModel);
                    }
                }
            }
        });
    }

    /**
     * 初始化码流类型的数据
     * @param streamTypeContent
     */
    private void initStreamTypeData(String[] streamTypeContent) {
        for (int i = 0; i < streamTypeContent.length; i++) {
            mStreamTypeList.add(new StreamTypeModel(streamTypeContent[i], i));
        }
    }

    /**
     * 点击选择后选择对应的码流类型
     * @param streamIndex
     */
    private void showStreamTypeTextContent(int streamIndex) {
        for (int i = 0; i < mStreamTypeList.size(); i++) {
            StreamTypeModel streamTypeModel = mStreamTypeList.get(i);
            if (i == streamIndex) {
                mTvStreamTypeValue.setText(streamTypeModel.getStreamType());
            }
        }
    }

    @Override
    public void findViewById(View view) {
        mTvFileType = (TextView) view.findViewById(R.id.tv_file_type);
        mTvSelectDate = (TextView) view.findViewById(R.id.tv_select_date);
        mTvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
        mTvEndTime = (TextView) view.findViewById(R.id.tv_end_time);
        mTvChannel = (TextView) view.findViewById(R.id.tv_channel);
        mTvStreamType = (TextView) view.findViewById(R.id.tv_stream_type);
        mTvExportFile = (TextView) view.findViewById(R.id.tv_export_file);
        mPbExportProgressbar = (ProgressBar) view.findViewById(R.id.pb_export_progressbar);
        mTvCurrentExportSize = (TextView) view.findViewById(R.id.tv_current_export_size);
        mTvChannelValue = (TextView) view.findViewById(R.id.tv_channel_value);
        mTvStreamTypeValue = view.findViewById(R.id.tv_stream_type_value);

        mChannelsNumberAdapter = new ChannelsNumberAdapter();
        mStreamTypeAdapter = new StreamTypeAdapter();

    }

    @Override
    public void setClickEvent(View view) {
        mTvExportFile.setOnClickListener(this);
        mTvFileType.setOnClickListener(this);
        mTvSelectDate.setOnClickListener(this);
        mTvStartTime.setOnClickListener(this);
        mTvEndTime.setOnClickListener(this);
        mTvStreamTypeValue.setOnClickListener(this);
        mTvChannelValue.setOnClickListener(this);
        mTvStreamTypeValue.setOnClickListener(this);
        mChannelsNumberAdapter.setOnItemClickListener(this);
        mStreamTypeAdapter.setOnStreamTypeClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) { // 返回
            mActivity.finish();
        } else if (id == R.id.tv_file_type) { //选择文件类型
            showFileTypePopup();
        } else if (id == R.id.tv_log) { //日志
            dismissFileTypePopup();
            mSelectFileType = 0;
            mTvFileType.setText(mTvLog.getText().toString());
            isSelectLog(true);
        } else if (id == R.id.tv_video) { //录像
            dismissFileTypePopup();
            mSelectFileType = 1;
            mTvFileType.setText(mTvVideo.getText().toString());
            isSelectLog(false);
        } else if (id == R.id.tv_select_date) { //选择日期
            selectDate();
        } else if (id == R.id.tv_start_time) { // 开始时间
            selectTime(START_TIME_FLAG);
        } else if (id == R.id.tv_end_time) { //结束时间
            selectTime(END_TIME_FLAG);
        } else if (id == R.id.tv_channel_value) { //选择通道号
            selectChannelNumbers();
        } else if (id == R.id.tv_stream_type_value) { //选择码流类型
            selectStreamType();
        } else if (id == R.id.tv_export_file) { //导出文件
            exportFiles();
        }
    }

    private void exportFiles() {
        String date = mTvSelectDate.getText().toString();
        String startTime = mTvStartTime.getText().toString();
        String endTime = mTvEndTime.getText().toString();

        int channelNumber = -1;
        for (int i = 0 ; i < mChannelNumbersList.size();i++){
            ChannelNumbersModel channelNumbersModel = mChannelNumbersList.get(i);
            boolean checked = channelNumbersModel.isChecked();
            if (checked){
                channelNumber = channelNumbersModel.getChannelNumber();
                break;
            }
        }

        int streamType = -1;
        for (int i = 0 ; i < mStreamTypeList.size() ; i++){
            StreamTypeModel streamTypeModel = mStreamTypeList.get(i);
            boolean checked = streamTypeModel.isChecked();
            if (checked){
                streamType = streamTypeModel.getStreamIndex();
                break;
            }
        }
        JSONObject jobj = new JSONObject();
        try {
            jobj.put("type",mSelectFileType);
            jobj.put("data",date);
            jobj.put("timeStart",startTime);
            jobj.put("endStart",endTime);
            jobj.put("channelNumber",channelNumber);
            jobj.put("streamType",streamType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseWrapper.getInstance().exportFileData(jobj).subscribe(new Subscriber<FileExportModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mActivity,e);
            }

            @Override
            public void onNext(FileExportModel fileExportModel) {
                //TODO 需要进行通过实时设备数据调试
            }
        });
    }

    /**
     * 选择码流类型
     */
    private void selectStreamType() {
        View view = View.inflate(mActivity, R.layout.popup_common, null);
        RecyclerView rv_list = view.findViewById(R.id.rv_list);

        rv_list.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
        mStreamTypeAdapter.setData(mStreamTypeList);
        rv_list.setAdapter(mStreamTypeAdapter);

        mStreamTypePopupWindow = new CommonPopupWindow.Builder(mActivity)
                .setView(view)
                .setWidthAndHeight(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)
                .create();
        mStreamTypePopupWindow.showAsDropDown(mTvStreamTypeValue, 0, -15);
    }

    /**
     * 选择通道号
     */
    private void selectChannelNumbers() {
        View view = View.inflate(mActivity, R.layout.popup_common, null);
        RecyclerView rv_list = view.findViewById(R.id.rv_list);

        rv_list.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
        mChannelsNumberAdapter.setData(mChannelNumbersList);
        rv_list.setAdapter(mChannelsNumberAdapter);

        mChannelNumbersPopupWindow = new CommonPopupWindow.Builder(mActivity)
                .setView(view)
                .setWidthAndHeight(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)
                .create();
        mChannelNumbersPopupWindow.showAsDropDown(mTvChannelValue, 0, -15);
    }

    /**
     * 选择时间 （包括 开始时间 和 结束时间）
     * @param type
     */
    private void selectTime(final String type) {
        TimePickerView timePickerView = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(date);
                if (START_TIME_FLAG.equals(type)) {
                    mTvStartTime.setText(time);
                } else if (END_TIME_FLAG.equals(type)) {
                    mTvEndTime.setText(time);
                }
            }
        }).setType(new boolean[]{false, false, false, true, true, true})
                .build();
        timePickerView.show();
    }

    /**
     * 选择日期
     */
    private void selectDate() {
        String select_date = mActivity.getResources().getString(R.string.select_date);
        Calendar date = Calendar.getInstance();
        TimePickerView pickerView = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {
                String selectData = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
                mTvSelectDate.setText(selectData);
            }
        }).setDate(date)
                .setTitleText(select_date)
                .build();
        pickerView.show();
    }

    private void isSelectLog(boolean isLog) {
        if (isLog) {
            mTvChannel.setTextColor(Color.GRAY);
            mTvStreamType.setTextColor(Color.GRAY);
            mTvChannelValue.setTextColor(Color.GRAY);
            mTvStreamTypeValue.setTextColor(Color.GRAY);
            mTvChannel.setEnabled(false);
            mTvChannel.setClickable(false);
            mTvStreamType.setEnabled(false);
            mTvStreamType.setClickable(false);
            mTvChannelValue.setEnabled(false);
            mTvChannelValue.setClickable(false);
            mTvStreamTypeValue.setEnabled(false);
            mTvChannelValue.setClickable(false);
        } else {
            mTvChannel.setTextColor(Color.BLACK);
            mTvStreamType.setTextColor(Color.BLACK);
            mTvChannelValue.setTextColor(Color.BLACK);
            mTvStreamTypeValue.setTextColor(Color.BLACK);
            mTvChannel.setEnabled(true);
            mTvChannel.setClickable(true);
            mTvStreamType.setEnabled(true);
            mTvStreamType.setClickable(true);
            mTvChannelValue.setEnabled(true);
            mTvChannelValue.setClickable(true);
            mTvStreamTypeValue.setEnabled(true);
            mTvChannelValue.setClickable(true);
        }
    }

    private void dismissFileTypePopup() {
        if (mFileTypePopupWindow != null && mFileTypePopupWindow.isShowing()) {
            mFileTypePopupWindow.dismiss();
        }
    }

    private void showFileTypePopup() {
        View view = View.inflate(mActivity, R.layout.popup_file_type, null);
        mTvLog = view.findViewById(R.id.tv_log);
        mTvVideo = view.findViewById(R.id.tv_video);

        mTvLog.setOnClickListener(this);
        mTvVideo.setOnClickListener(this);

        mFileTypePopupWindow = new CommonPopupWindow.Builder(mActivity)
                .setView(view)
                .setWidthAndHeight(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                .create();
        mFileTypePopupWindow.showAsDropDown(mTvFileType);
    }

    @Override
    public void onChannelNumbersClick(int position) {
        //设置当前选择的通道号
        for (int i = 0; i < mChannelNumbersList.size(); i++) {
            ChannelNumbersModel channelNumbersModel = mChannelNumbersList.get(i);
            if (i == position) {
                channelNumbersModel.setChecked(true);
            } else {
                channelNumbersModel.setChecked(false);
            }
        }

        ChannelNumbersModel channelNumbersModel = mChannelNumbersList.get(position);
        mCurrentChannelNumbersModel = channelNumbersModel;
        int channelNumber = channelNumbersModel.getChannelNumber();
        int streamType = channelNumbersModel.getStreamType();
        mTvChannelValue.setText(String.format("%s %s", mChannels, String.valueOf(channelNumber)));
        showStreamTypeTextContent(streamType);
        if (mChannelNumbersPopupWindow != null && mChannelNumbersPopupWindow.isShowing()) {
            mChannelNumbersPopupWindow.dismiss();
        }
    }

    /**
     * 码流类型选择监听
     *
     * @param position
     */
    @Override
    public void streamTypeClickListener(int position) {
        for (int i = 0; i < mStreamTypeList.size(); i++) {
            StreamTypeModel streamTypeModel = mStreamTypeList.get(i);
            if (i == position) {
                streamTypeModel.setChecked(true);
            } else {
                streamTypeModel.setChecked(false);
            }
        }

        StreamTypeModel streamTypeModel = mStreamTypeList.get(position);
        int streamIndex = streamTypeModel.getStreamIndex();
        String streamType = streamTypeModel.getStreamType();
        mTvStreamTypeValue.setText(streamType);
        if (mCurrentChannelNumbersModel != null) {
            mCurrentChannelNumbersModel.setStreamType(streamIndex);
        }

        if (mStreamTypePopupWindow != null && mStreamTypePopupWindow.isShowing()) {
            mStreamTypePopupWindow.dismiss();
        }
    }
}
