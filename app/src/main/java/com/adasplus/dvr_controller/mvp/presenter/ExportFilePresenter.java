package com.adasplus.dvr_controller.mvp.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.dialog.BasicDialog;
import com.adasplus.base.dialog.CommonDialog;
import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.ExportFileModel;
import com.adasplus.base.network.model.FileExportModel;
import com.adasplus.base.popup.CommonPopupWindow;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.activity.FileExportActivity;
import com.adasplus.dvr_controller.adapter.ChannelsNumberAdapter;
import com.adasplus.dvr_controller.adapter.StreamTypeAdapter;
import com.adasplus.dvr_controller.mvp.contract.IExportFileContract;
import com.adasplus.dvr_controller.mvp.contract.OnItemChannelNumbersClickListener;
import com.adasplus.dvr_controller.mvp.contract.OnItemStreamTypeClickListener;
import com.adasplus.dvr_controller.mvp.model.ChannelNumbersModel;
import com.adasplus.dvr_controller.mvp.model.StreamTypeModel;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.mvp.model.DeviceFormatModel;
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
import java.util.TimeZone;
import java.util.TimerTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Subscriber;

/**
 * Created by dell on 2019/12/6 15:21
 * Description:
 * Emain: 1187278976@qq.com
 */
public class ExportFilePresenter implements IExportFileContract.Presenter, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, OnItemChannelNumbersClickListener, OnItemStreamTypeClickListener {
    private IExportFileContract.View mExportFileView;
    private FileExportActivity mActivity;

    private ImageView mIvExportBack;

    private LinearLayout mLlPwd;
    private EditText mEtOne;
    private EditText mEtTwo;
    private EditText mEtThree;
    private EditText mEtFour;
    private EditText mEtFive;
    private EditText mEtSix;
    private TextView mTvSubmit;


    private SwipeRefreshLayout mSrlRefreshFileExportData;
    private TextView mTvFileType;
    private TextView mTvChannelValue;
    private TextView mTvStreamTypeValue;
    private TextView mTvStartDate;
    private TextView mTvStartTime;
    private TextView mTvEndData;
    private TextView mTvEndTime;
    private TextView mTvStorageTypeValue;
    private TextView mTvStorageNameValue;
    private TextView mTvExportFile;

    private static final String START_TIME_FLAG = "start";
    private static final String END_TIME_FLAG = "end";

    private CommonPopupWindow mFileTypePopupWindow;
    private CommonPopupWindow mStorageTypePopupWindow;
    private CommonPopupWindow mStorageNamePopupWindow;

    private TextView mTvAudioVideo;
    private TextView mTvAudio;
    private TextView mTvVideo;

    private TextView mTvMainEquipment;
    private TextView mTvReserveEquipment;
    private TextView mTvSDOne;
    private TextView mTvSDTwo;
    private TextView mTvSata;
    private TextView mTvUsb;

    private List<ChannelNumbersModel> mChannelNumbersList;
    private List<StreamTypeModel> mStreamTypeList;

    private ChannelsNumberAdapter mChannelsNumberAdapter;
    private CommonPopupWindow mChannelNumbersPopupWindow;
    private String mChannels;
    private StreamTypeAdapter mStreamTypeAdapter;
    private int mWidth;
    private CommonPopupWindow mStreamTypePopupWindow;
    private ChannelNumbersModel mCurrentChannelNumbersModel;
    private int mSelectFileType;
    private int mStorageType;
    private String mStorageName = "";
    private int mWarningFlag;

    private BasicDialog mDialog;

    private java.util.Timer timer;
    private TimerTask task;
    private ProgressBar mProgressbarFileExport;
    private TextView mTvCurrentProgress;
    private String mBeginData;
    private String mBeginTime;
    private String mEndData;
    private String mEndTime;
    private List<DeviceFormatModel.ArrayBean> mArray;

    private JSONObject mJSONObject;

    //密码
    String[] mPwd = new String[]{"8", "8", "8", "8", "8", "8"};


    public ExportFilePresenter(IExportFileContract.View view) {
        mExportFileView = view;
        mActivity = (FileExportActivity) view;
    }

    @Override
    public void initData() {
        mBeginData = "";
        mEndData = "";

        mLlPwd = mExportFileView.getLlPwd();
        mEtOne = mExportFileView.getEtOne();
        mEtTwo = mExportFileView.getEtTwo();
        mEtThree = mExportFileView.getEtThree();
        mEtFour = mExportFileView.getEtFour();
        mEtFive = mExportFileView.getEtFive();
        mEtSix = mExportFileView.getEtSix();
        mTvSubmit = mExportFileView.getTvSubmit();

        mIvExportBack = mExportFileView.getIvExportBack();
        mSrlRefreshFileExportData = mExportFileView.getSrlRefreshFileExportData();
        mTvFileType = mExportFileView.getTvFileType();
        mTvChannelValue = mExportFileView.getTvChannelValue();
        mTvStreamTypeValue = mExportFileView.getTvStreamTypeValue();
        mTvStartDate = mExportFileView.getTvStartDate();
        mTvStartTime = mExportFileView.getTvStartTime();
        mTvEndData = mExportFileView.getTvEndData();
        mTvEndTime = mExportFileView.getTvEndTime();
        mTvStorageTypeValue = mExportFileView.getTvStorageTypeValue();
        mTvStorageNameValue = mExportFileView.getTvStorageNameValue();
        mTvExportFile = mExportFileView.getTvExportFile();

        mSrlRefreshFileExportData.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSrlRefreshFileExportData.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSrlRefreshFileExportData.setOnRefreshListener(this);

        mChannelsNumberAdapter = new ChannelsNumberAdapter();
        mStreamTypeAdapter = new StreamTypeAdapter();


        mChannelNumbersList = new ArrayList<>();
        mStreamTypeList = new ArrayList<>();

        getStorageNetwork();
        mWidth = (int) mActivity.getResources().getDimension(R.dimen.dp_200);

        String all = mActivity.getResources().getString(R.string.all);
        String main_stream = mActivity.getResources().getString(R.string.main_stream);
        String child_stream = mActivity.getResources().getString(R.string.child_stream);

        mSelectFileType = 0;

        String[] streamTypeContent = {all, main_stream, child_stream};
        initStreamTypeData(streamTypeContent);
        //默认显示的是系统的当前的时间
        String mBeginFormatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String mBeginFormatTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        mBeginTime = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
        mBeginData = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date()).substring(2);
        Calendar calendars = Calendar.getInstance();
        calendars.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int year = calendars.get(Calendar.YEAR) - 1900;
        int month = calendars.get(Calendar.MONTH);
        int day = calendars.get(Calendar.DATE);

        String mEndFormatData = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String mEndFormatTIme = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        mEndData = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date()).substring(2);
        mEndTime = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date(year, month, day, 23, 59)).substring(2);
        mTvStartDate.setText(mBeginFormatDate);
        mTvStartTime.setText(mBeginFormatTime);
        mTvEndData.setText(mEndFormatData);
        mTvEndTime.setText(mEndFormatTIme);
        mChannels = mActivity.getResources().getString(R.string.channels);

        int channelCount = 6;
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
        mStorageType = 1;
        mTvStorageTypeValue.setText(mActivity.getResources().getString(R.string.main_equipment));
        mWarningFlag = 0;

    }

    @Override
    public void initListener() {
        mIvExportBack.setOnClickListener(this);
//        myRequestFocus();
        mTvSubmit.setOnClickListener(this);

        mTvFileType.setOnClickListener(this);
        mTvStartDate.setOnClickListener(this);
        mTvStartTime.setOnClickListener(this);
        mTvEndData.setOnClickListener(this);
        mTvEndTime.setOnClickListener(this);
        mTvStreamTypeValue.setOnClickListener(this);
        mTvChannelValue.setOnClickListener(this);
        mTvStorageTypeValue.setOnClickListener(this);
        mTvStorageNameValue.setOnClickListener(this);
        mTvExportFile.setOnClickListener(this);

        mTvStreamTypeValue.setOnClickListener(this);
        mChannelsNumberAdapter.setOnItemClickListener(this);
        mStreamTypeAdapter.setOnStreamTypeClickListener(this);
    }

    private void myRequestFocus() {
        mEtOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mEtTwo.requestFocus();
                }
            }
        });
        mEtTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mEtThree.requestFocus();
                }
            }
        });
        mEtThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mEtFour.requestFocus();
                }
            }
        });
        mEtFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mEtFive.requestFocus();
                }
            }
        });
        mEtFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mEtSix.requestFocus();
                }
            }
        });
    }


    private void getStorageNetwork() {
        //获取存储器的列表
        HomeWrapper.getInstance().getStorageInfoList().subscribe(new Subscriber<DeviceFormatModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mSrlRefreshFileExportData.setRefreshing(false);
                ExceptionUtils.exceptionHandling(mActivity, e);
            }


            @Override
            public void onNext(DeviceFormatModel deviceFormatModel) {
                mSrlRefreshFileExportData.setRefreshing(false);
                if (mArray != null) {
                    mArray.clear();
                }
                mArray = deviceFormatModel.getArray();
                if (mArray.size() == 0) {
                    mTvStorageNameValue.setText(R.string.no_export_device);
                } else {
                    String storageName = "";
                    switch (mArray.get(0).getStorageName()) {
                        case "sdcard1":
                            storageName = mActivity.getString(R.string.sdcard_one);
                            break;
                        case "sdcard2":
                            storageName = mActivity.getString(R.string.sdcard_two);
                            break;
                        case "sata":
                            storageName = mActivity.getString(R.string.sata);
                            break;
                        case "usb":
                            storageName = mActivity.getString(R.string.usb);
                            break;
                    }
                    mTvStorageNameValue.setText(storageName);
                }
            }
        });

    }

    /**
     * 初始化码流类型的数据
     *
     * @param streamTypeContent
     */
    private void initStreamTypeData(String[] streamTypeContent) {
        for (int i = 0; i < streamTypeContent.length; i++) {
            mStreamTypeList.add(new StreamTypeModel(streamTypeContent[i], i));
        }
    }

    /**
     * 点击选择后选择对应的码流类型
     *
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

    private boolean submitPwd() {
        String one = mEtOne.getText().toString();
        String two = mEtTwo.getText().toString();
        String three = mEtThree.getText().toString();
        String four = mEtFour.getText().toString();
        String five = mEtFive.getText().toString();
        String six = mEtSix.getText().toString();

        if (TextUtils.isEmpty(one) || TextUtils.isEmpty(two) ||
                TextUtils.isEmpty(three) || TextUtils.isEmpty(four) ||
                TextUtils.isEmpty(five) || TextUtils.isEmpty(six)) {
            Toast.makeText(mActivity, "请输入密码~", Toast.LENGTH_SHORT).show();
            return true;
        }
        Log.i("boolean", "one : " + one.equals(mPwd[0]));
        Log.i("boolean", "two : " + two.equals(mPwd[1]));
        Log.i("boolean", "three : " + three.equals(mPwd[2]));
        Log.i("boolean", "four : " + four.equals(mPwd[3]));
        Log.i("boolean", "five : " + five.equals(mPwd[4]));
        Log.i("boolean", "six : " + six.equals(mPwd[5]));
        if (!one.equals(mPwd[0]) || !two.equals(mPwd[1]) ||
                !three.equals(mPwd[2]) || !four.equals(mPwd[3]) ||
                !five.equals(mPwd[4]) || !six.equals(mPwd[5])) {
            Toast.makeText(mActivity, "密码不正确~", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_export_back) {
            mActivity.finish();
        } else if (id == R.id.tv_submit) { //选择文件类型
            //todo 验证密码
            if (submitPwd()) return;
            mLlPwd.setVisibility(View.GONE);
            mSrlRefreshFileExportData.setVisibility(View.VISIBLE);
        } else if (id == R.id.tv_file_type) { //选择文件类型
            showFileTypePopup();
        } else if (id == R.id.tv_audio_video) { //音视频
            dismissFileTypePopup();
            mSelectFileType = 0;
            mTvFileType.setText(mTvAudioVideo.getText().toString());
//            isSelectLog(true);
        } else if (id == R.id.tv_audio) { //音频
            dismissFileTypePopup();
            mSelectFileType = 1;
            mTvFileType.setText(mTvAudio.getText().toString());
//            isSelectLog(false);
        } else if (id == R.id.tv_video) { //视频
            dismissFileTypePopup();
            mSelectFileType = 2;
            mTvFileType.setText(mTvVideo.getText().toString());
//            isSelectLog(false);
        } else if (id == R.id.tv_start_date) { //选择日期
            selectDate(START_TIME_FLAG);
        } else if (id == R.id.tv_end_date) { //选择日期
            selectDate(END_TIME_FLAG);
        } else if (id == R.id.tv_start_time) { // 开始时间
            selectTime(START_TIME_FLAG);
        } else if (id == R.id.tv_end_time) { //结束时间
            selectTime(END_TIME_FLAG);
        } else if (id == R.id.tv_channel_value) { //选择通道号
            selectChannelNumbers();
        } else if (id == R.id.tv_stream_type_value) { //选择码流类型
            selectStreamType();
        } else if (id == R.id.tv_storage_type_value) { //选择设备类型
            showStorageTypePopup();
        } else if (id == R.id.tv_storage_name_value) { //选择导出设备
            if (mArray == null || mArray.size() == 0) {
                mActivity.showToast(R.string.no_export_device);
                return;
            }
            showDSTStorageNamePopup();
        } else if (id == R.id.tv_main_equipment) { //主设备
            dismissStorageTypePopup();
            mStorageType = 1;
            mTvStorageTypeValue.setText(mTvMainEquipment.getText().toString());
        } else if (id == R.id.tv_reserve_equipment) { //备设备
            dismissStorageTypePopup();
            mStorageType = 2;
            mTvStorageTypeValue.setText(mTvReserveEquipment.getText().toString());
        } else if (id == R.id.tv_sd_one) { //sd1
            dismissStorageNamePopup();
            mStorageName = "sdcard1";
            mTvStorageNameValue.setText(mActivity.getString(R.string.sdcard_one));
        } else if (id == R.id.tv_sd_two) { //sd2
            dismissStorageNamePopup();
            mStorageName = "sdcard1";
            mTvStorageNameValue.setText(mActivity.getString(R.string.sdcard_two));
        } else if (id == R.id.tv_sata) { //sata
            dismissStorageNamePopup();
            mStorageName = "sata";
            mTvStorageNameValue.setText(mActivity.getString(R.string.sata));
        } else if (id == R.id.tv_reserve_equipment) { //备设备
            dismissStorageNamePopup();
            mStorageName = "usb";
            mTvStorageNameValue.setText(mActivity.getString(R.string.usb));
        } else if (id == R.id.tv_export_file) { //导出文件
            exportFiles();
        }
    }

    private void exportFiles() {
        mJSONObject = new JSONObject();
        try {
            mJSONObject.put("stopFlag", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        task = new TimerTask() {
            @Override
            public void run() {
                BaseWrapper.getInstance().getFileExport(mJSONObject).subscribe(new Subscriber<FileExportModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        timer.cancel();
                        ExceptionUtils.exceptionHandling(mActivity, e);
                    }

                    @Override
                    public void onNext(FileExportModel fileExportModel) {
                        //TODO 需要进行通过实时设备数据调试
                        if (fileExportModel.getFileOutNumber() == 0) {
                            timer.cancel();
                            if (mDialog != null && mDialog.isAdded()) {
                                mDialog.dismiss();
                            }
                            return;
                        }
                        Message msg = new Message();
                        msg.what = 0x123;
                        msg.obj = fileExportModel;
                        mHandler.sendMessage(msg);
                    }
                });
            }
        };


        int channelNumber = -1;
        for (int i = 0; i < mChannelNumbersList.size(); i++) {
            ChannelNumbersModel channelNumbersModel = mChannelNumbersList.get(i);
            boolean checked = channelNumbersModel.isChecked();
            if (checked) {
                channelNumber = channelNumbersModel.getChannelNumber();
                break;
            }
        }

        int streamType = 1;
        for (int i = 0; i < mStreamTypeList.size(); i++) {
            StreamTypeModel streamTypeModel = mStreamTypeList.get(i);
            boolean checked = streamTypeModel.isChecked();
            if (checked) {
                streamType = streamTypeModel.getStreamIndex();
                break;
            }
        }

        JSONObject jobj = new JSONObject();
        try {
            jobj.put("resourceType", mSelectFileType);
            jobj.put("storageType", mStorageType);
            jobj.put("warningFlag", mWarningFlag);
//            jobj.put("data", date);
            jobj.put("beginTime", mBeginTime);
            jobj.put("endTime", mEndTime);
            jobj.put("channelNum", channelNumber);
            jobj.put("streamType", streamType);
            jobj.put("dstStorageName", mStorageName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseWrapper.getInstance().exportFileData(jobj).subscribe(new Subscriber<ExportFileModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mActivity, e);
            }

            @Override
            public void onNext(ExportFileModel exportFileModel) {
                //TODO 需要进行通过实时设备数据调试
                if (exportFileModel.getFileOutNumber() == 0) {
                    Toast.makeText(mActivity, mActivity.getString(R.string.no_related_documents), Toast.LENGTH_SHORT).show();
                    return;
                }
                showFileExportDialog(exportFileModel.getFileOutNumber());
                getFileExportNetwork();

//                mActivity.startService(new Intent(mActivity, FloatWindowService.class));
            }
        });
    }

    public Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            FileExportModel fileExportModel = (FileExportModel) msg.obj;
            switch (msg.what) {
                case 0x123:
                    mProgressbarFileExport.setProgress(fileExportModel.getFileOutIndex());
                    mTvCurrentProgress.setText((fileExportModel.getFileOutIndex() + "/" + fileExportModel.getFileOutNumber()) + mActivity.getString(R.string.exporting));
                    break;
            }
        }
    };

    private void getFileExportNetwork() {
        timer = new java.util.Timer(true);
        timer.schedule(task, 0, 1000);
    }


    private void showFileExportDialog(int max) {
        View view = View.inflate(mActivity, R.layout.dialog_file_export, null);
        mProgressbarFileExport = view.findViewById(R.id.progressbar_file_export);
        mTvCurrentProgress = view.findViewById(R.id.tv_file_export);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel_file_export);
        float margin = mActivity.getResources().getDimension(com.adasplus.homepager.R.dimen.dp_12);

        mProgressbarFileExport.setMax(max);
        mTvCurrentProgress.setText((0 + "/" + max) + mActivity.getString(R.string.exporting));

        mDialog = CommonDialog.init()
                .setView(view)
                .setMargin(margin)
                .setOutCancel(false)
                .setAnimStyle(com.adasplus.homepager.R.style.BottomAnimStyle)
                .setDimAmount(0.8f)
                .show(mActivity.getSupportFragmentManager());

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null && mDialog.isAdded()) {
                    try {
                        mJSONObject.put("stopFlag", 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mDialog.dismiss();
                }
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
     *
     * @param type
     */
    private void selectTime(final String type) {
        TimePickerView timePickerView = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
                String time2 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(date);
                String time1 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(date);
                String time3 = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(date);
                if (START_TIME_FLAG.equals(type)) {
                    if (mBeginData.equals("")) {
                        Toast.makeText(mActivity, "请先选择开始日期~", Toast.LENGTH_SHORT);
                        return;
                    }
                    mTvStartTime.setText(time2);
                    mBeginTime = mBeginData + time3;
                } else if (END_TIME_FLAG.equals(type)) {
                    if (mEndData.equals("")) {
                        Toast.makeText(mActivity, "请先选择结束日期~", Toast.LENGTH_SHORT);
                        return;
                    }
                    mTvEndTime.setText(time2);
                    mEndTime = mEndData + time3;
//                    mEndTime = time1.substring(2);
                }
            }
        }).setType(new boolean[]{false, false, false, true, true, true})
                .build();
        timePickerView.show();
    }

    /**
     * 选择日期
     */
    private void selectDate(final String type) {
        String select_date = mActivity.getString(R.string.select_date);
        Calendar date = Calendar.getInstance();
        TimePickerView pickerView = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {
                String selectData = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
                String selectData1 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date);
                if (START_TIME_FLAG.equals(type)) {
                    mTvStartDate.setText(selectData);
                    mBeginData = selectData1.substring(2);
                } else if (END_TIME_FLAG.equals(type)) {
                    mTvEndData.setText(selectData);
                    mEndData = selectData1.substring(2);
                }
            }
        }).setDate(date)
                .setTitleText(select_date)
                .build();
        pickerView.show();
    }

    private void dismissFileTypePopup() {
        if (mFileTypePopupWindow != null && mFileTypePopupWindow.isShowing()) {
            mFileTypePopupWindow.dismiss();
        }
    }

    private void dismissStorageTypePopup() {
        if (mStorageTypePopupWindow != null && mStorageTypePopupWindow.isShowing()) {
            mStorageTypePopupWindow.dismiss();
        }
    }

    private void dismissStorageNamePopup() {
        if (mStorageNamePopupWindow != null && mStorageNamePopupWindow.isShowing()) {
            mStorageNamePopupWindow.dismiss();
        }
    }

    private void showFileTypePopup() {
        View view = View.inflate(mActivity, R.layout.popup_file_type, null);
        mTvAudioVideo = view.findViewById(R.id.tv_audio_video);
        mTvAudio = view.findViewById(R.id.tv_audio);
        mTvVideo = view.findViewById(R.id.tv_video);
        mTvAudioVideo.setOnClickListener(this);
        mTvAudio.setOnClickListener(this);
        mTvVideo.setOnClickListener(this);
        mFileTypePopupWindow = new CommonPopupWindow.Builder(mActivity)
                .setView(view)
                .setWidthAndHeight(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                .create();
        mFileTypePopupWindow.showAsDropDown(mTvFileType);
    }

    private void showStorageTypePopup() {
        View view = View.inflate(mActivity, R.layout.popup_storage_type, null);
        mTvMainEquipment = view.findViewById(R.id.tv_main_equipment);
        mTvReserveEquipment = view.findViewById(R.id.tv_reserve_equipment);
        mTvMainEquipment.setOnClickListener(this);
        mTvReserveEquipment.setOnClickListener(this);
        mStorageTypePopupWindow = new CommonPopupWindow.Builder(mActivity)
                .setView(view)
                .setWidthAndHeight(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                .create();
        mStorageTypePopupWindow.showAsDropDown(mTvStorageTypeValue);
    }

    private void showDSTStorageNamePopup() {
        View view = View.inflate(mActivity, R.layout.popup_storage_name, null);
        mTvSDOne = view.findViewById(R.id.tv_sd_one);
        mTvSDTwo = view.findViewById(R.id.tv_sd_two);
        mTvSata = view.findViewById(R.id.tv_sata);
        mTvUsb = view.findViewById(R.id.tv_usb);
        for (DeviceFormatModel.ArrayBean device : mArray) {
            selectStorageName(device.getStorageName());
        }
        mTvSDOne.setOnClickListener(this);
        mTvSDTwo.setOnClickListener(this);
        mTvUsb.setOnClickListener(this);
        mTvSata.setOnClickListener(this);
        mStorageNamePopupWindow = new CommonPopupWindow.Builder(mActivity)
                .setView(view)
                .setWidthAndHeight(mWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
                .create();
        mStorageNamePopupWindow.showAsDropDown(mTvStorageNameValue);
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

    @Override
    public void onRefresh() {
        initData();
    }

    //设置磁盘名字
    private void selectStorageName(String type) {
        switch (type) {
            case "sdcard1":
                mTvSDOne.setText(mActivity.getString(R.string.sdcard_one));
                mTvSDOne.setVisibility(View.VISIBLE);
                break;
            case "sdcard2":
                mTvSDOne.setText(mActivity.getString(R.string.sdcard_two));
                mTvSDTwo.setVisibility(View.VISIBLE);
                break;
            case "sata":
                mTvSata.setText(mActivity.getString(R.string.sata));
                mTvSata.setVisibility(View.VISIBLE);
                break;
            case "usb":
                mTvUsb.setText(mActivity.getString(R.string.usb));
                mTvUsb.setVisibility(View.VISIBLE);
                break;
        }
    }

}
