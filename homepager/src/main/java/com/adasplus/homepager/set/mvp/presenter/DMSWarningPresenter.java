package com.adasplus.homepager.set.mvp.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.dialog.BasicDialog;
import com.adasplus.base.dialog.CommonDialog;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.DMSWarningActivity;
import com.adasplus.homepager.set.adapter.WarningsAdapter;
import com.adasplus.homepager.set.mvp.contract.IDMSWarningContract;
import com.adasplus.homepager.set.mvp.contract.ISwitchItemClickListener;
import com.adasplus.homepager.set.mvp.model.ConvertWarningsModel;
import com.adasplus.homepager.set.mvp.model.DMSWarningModel;
import com.adasplus.homepager.set.mvp.model.WarningsRestoreDefaultSettingsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 19:31
 * Description :
 */
public class DMSWarningPresenter implements IDMSWarningContract.Presenter, View.OnClickListener, ISwitchItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private IDMSWarningContract.View mDMSWarningView;
    private DMSWarningActivity mDmsWarningActivity;
    private SwipeRefreshLayout mSwipeRefreshLayoutDMSSet;
    private RecyclerView mRvDmsList;
    private WarningsAdapter mWarningsAdapter;
    private ImageView mIvDMSTotalSwitch;
    private TextView mTvSave;
    private TextView mTvRestoreTheDefaultSettings;
    private int mCloseWarningCount;
    private int mCloseTotalCount;
    private DMSWarningModel mDmsWarningModel;
    private List<ConvertWarningsModel> mConvertWarningsList = new ArrayList<>();
    private BasicDialog mDialog;

    private  int mDmsEnable;

    public DMSWarningPresenter(IDMSWarningContract.View view) {
        mDMSWarningView = view;
        mDmsWarningActivity = (DMSWarningActivity) view;
    }

    @Override
    public void initData() {
        mSwipeRefreshLayoutDMSSet = mDMSWarningView.getSwipeRefreshLayoutDMSSet();
        mSwipeRefreshLayoutDMSSet.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayoutDMSSet.setProgressBackgroundColorSchemeResource(android.R.color.white);


        mRvDmsList = mDMSWarningView.getRvDmsList();
        View headerView = View.inflate(mDmsWarningActivity, R.layout.item_head_view, null);
        View footerView = View.inflate(mDmsWarningActivity, R.layout.item_footer_view, null);

        mIvDMSTotalSwitch = headerView.findViewById(R.id.iv_adas_total_switch);
        TextView tv_warning_type = headerView.findViewById(R.id.tv_warning_type);
        tv_warning_type.setText(R.string.dms);
        mTvSave = footerView.findViewById(R.id.tv_save);
        mTvRestoreTheDefaultSettings = footerView.findViewById(R.id.tv_restore_the_default_settings);

        headerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        footerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        mWarningsAdapter = new WarningsAdapter();
        mWarningsAdapter.addHeaderView(headerView);
        mWarningsAdapter.addFooterView(footerView);

        mRvDmsList.setLayoutManager(new LinearLayoutManager(mDmsWarningActivity, RecyclerView.VERTICAL, false));
        getDMSDefaultSet();
    }

    @Override
    public void initListener() {
        ImageView ivBack = mDMSWarningView.getIvBack();
        ivBack.setOnClickListener(this);
        mSwipeRefreshLayoutDMSSet.setOnRefreshListener(this);
        mTvRestoreTheDefaultSettings.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
        mIvDMSTotalSwitch.setOnClickListener(this);
        mWarningsAdapter.setOnSwitchClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) { //点击返回按钮的监听
            //点击返回按钮判断是否 dms 设置是否有未保存的数据。如果有未保存的设置，进行弹出
            // 对话框来提示，选择是，保存并退出，否的话直接进行退出
            boolean isSaveSet = false;
            for (ConvertWarningsModel convertWarningsModel : mConvertWarningsList) {
                int warningNameResId = convertWarningsModel.getWarningNameResId();
                if (warningNameResId == R.string.fatigue_driving) {
                    //疲劳驾驶报警
                    DMSWarningModel.LongTimeDriveAlarmBean longTimeDriveAlarm = mDmsWarningModel.getLongTimeDriveAlarm();
                    if (convertWarningsModel.getEnable() != longTimeDriveAlarm.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != longTimeDriveAlarm.getLowestSpeed()
                            || convertWarningsModel.getSensitivityLevel() != longTimeDriveAlarm.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                } else if (warningNameResId == R.string.call_phone) {
                    //接打电话报警
                    DMSWarningModel.TakePhoneAlarmBean takePhoneAlarm = mDmsWarningModel.getTakePhoneAlarm();
                    if (convertWarningsModel.getEnable() != takePhoneAlarm.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != takePhoneAlarm.getLowestSpeed()
                            || convertWarningsModel.getSensitivityLevel() != takePhoneAlarm.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                } else if (warningNameResId == R.string.smoking_warning) {
                    //吸烟报警
                    DMSWarningModel.SmokingAlarmBean smokingAlarm = mDmsWarningModel.getSmokingAlarm();
                    if (convertWarningsModel.getEnable() != smokingAlarm.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != smokingAlarm.getLowestSpeed()
                            || convertWarningsModel.getSensitivityLevel() != smokingAlarm.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                } else if (warningNameResId == R.string.distracted_warning) {
                    //分神报警
                    DMSWarningModel.DistractAlarmBean distractAlarm = mDmsWarningModel.getDistractAlarm();
                    if (convertWarningsModel.getEnable() != distractAlarm.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != distractAlarm.getLowestSpeed()
                            || convertWarningsModel.getSensitivityLevel() != distractAlarm.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                } else if (warningNameResId == R.string.driver_abnormal) {
                    //驾驶员异常报警
                    DMSWarningModel.DriverErrorAlarmBean driverErrorAlarm = mDmsWarningModel.getDriverErrorAlarm();
                    if (convertWarningsModel.getEnable() != driverErrorAlarm.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != driverErrorAlarm.getLowestSpeed()
                            || convertWarningsModel.getSensitivityLevel() != driverErrorAlarm.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                } else if (warningNameResId == R.string.driver_change) {
                    //驾驶员变更报警
                    DMSWarningModel.DriverChangeEventBean driverChangeEvent = mDmsWarningModel.getDriverChangeEvent();
                    if (convertWarningsModel.getEnable() != driverChangeEvent.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != driverChangeEvent.getLowestSpeed()
                            || convertWarningsModel.getSensitivityLevel() != driverChangeEvent.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                }
            }

            if (isSaveSet) {
                showSaveDMSSetDialog();
            } else {
                mDmsWarningActivity.finish();
            }
        } else if (id == R.id.iv_adas_total_switch) {
            if(mDmsEnable == 1){
                mIvDMSTotalSwitch.setImageResource(R.mipmap.switch_close_icon);
                mDmsEnable = 0;
                mCloseWarningCount = mCloseTotalCount;
                for (ConvertWarningsModel convertWarningsModel : mConvertWarningsList) {
                    convertWarningsModel.setEnable(0);
                }
                if (mWarningsAdapter != null) {
                    mWarningsAdapter.notifyDataSetChanged();
                }
            }else{
                mCloseWarningCount = 0;
                mIvDMSTotalSwitch.setImageResource(R.mipmap.switch_open_icon);
                mDmsEnable = 1;
//                getDMSDefaultSet();
                for (ConvertWarningsModel convertWarningsModel : mConvertWarningsList) {
                    convertWarningsModel.setEnable(1);
                }
                if (mWarningsAdapter != null) {
                    mWarningsAdapter.notifyDataSetChanged();
                }
            }
        }
        else if (id == R.id.tv_restore_the_default_settings) { //恢复默认设置的监听
            //恢复报警的默认设置
            HomeWrapper.getInstance().dmsRestoreDefaultSettings().subscribe(new Subscriber<WarningsRestoreDefaultSettingsModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ExceptionUtils.exceptionHandling(mDmsWarningActivity, e);
                }

                @Override
                public void onNext(WarningsRestoreDefaultSettingsModel warningsRestoreDefaultSettingsModel) {
                    Toast.makeText(mDmsWarningActivity, "DMS 报警恢复默认设置成功!", Toast.LENGTH_SHORT).show();
                    getDMSDefaultSet();
                }
            });
        }
        else if (id == R.id.tv_cancel) { // 不保存更改的配置
            if (mDialog != null && mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mDmsWarningActivity.finish();
        }
        else if (id == R.id.tv_save || id == R.id.tv_confirm) { //保存更改的设置 和 确认保存设置
            if (mDmsWarningModel != null){
                if (mDialog != null && mDialog.isAdded()){
                    mDialog.dismiss();
                }

                mDmsWarningModel.setDmsEnable(mDmsEnable);

                //通过对应的 id 来设置对应的报警数据。并保存
                for (ConvertWarningsModel convertWarningsModel : mConvertWarningsList) {
                    int warningNameResId = convertWarningsModel.getWarningNameResId();
                    if (warningNameResId == R.string.fatigue_driving) {
                        //疲劳驾驶报警
                        DMSWarningModel.LongTimeDriveAlarmBean longTimeDriveAlarm = mDmsWarningModel.getLongTimeDriveAlarm();
                        longTimeDriveAlarm.setEnable(convertWarningsModel.getEnable());
                        longTimeDriveAlarm.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        longTimeDriveAlarm.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mDmsWarningModel.setLongTimeDriveAlarm(longTimeDriveAlarm);
                    } else if (warningNameResId == R.string.call_phone) {
                        //接打电话报警
                        DMSWarningModel.TakePhoneAlarmBean takePhoneAlarm = mDmsWarningModel.getTakePhoneAlarm();
                        takePhoneAlarm.setEnable(convertWarningsModel.getEnable());
                        takePhoneAlarm.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        takePhoneAlarm.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mDmsWarningModel.setTakePhoneAlarm(takePhoneAlarm);
                    } else if (warningNameResId == R.string.smoking_warning) {
                        //吸烟报警
                        DMSWarningModel.SmokingAlarmBean smokingAlarm = mDmsWarningModel.getSmokingAlarm();
                        smokingAlarm.setEnable(convertWarningsModel.getEnable());
                        smokingAlarm.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        smokingAlarm.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mDmsWarningModel.setSmokingAlarm(smokingAlarm);
                    } else if (warningNameResId == R.string.distracted_warning) {
                        //分神报警
                        DMSWarningModel.DistractAlarmBean distractAlarm = mDmsWarningModel.getDistractAlarm();
                        distractAlarm.setEnable(convertWarningsModel.getEnable());
                        distractAlarm.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        distractAlarm.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mDmsWarningModel.setDistractAlarm(distractAlarm);
                    } else if (warningNameResId == R.string.driver_abnormal) {
                        //驾驶员异常报警
                        DMSWarningModel.DriverErrorAlarmBean driverErrorAlarm = mDmsWarningModel.getDriverErrorAlarm();
                        driverErrorAlarm.setEnable(convertWarningsModel.getEnable());
                        driverErrorAlarm.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        driverErrorAlarm.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mDmsWarningModel.setDriverErrorAlarm(driverErrorAlarm);
                    } else if (warningNameResId == R.string.driver_change) {
                        //驾驶员变更报警
                        DMSWarningModel.DriverChangeEventBean driverChangeEvent = mDmsWarningModel.getDriverChangeEvent();
                        driverChangeEvent.setEnable(convertWarningsModel.getEnable());
                        driverChangeEvent.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        driverChangeEvent.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mDmsWarningModel.setDriverChangeEvent(driverChangeEvent);
                    }
                }

                String json = GsonUtils.getInstance().toJson(mDmsWarningModel);
                try {
                    //更改 DMS 报警设置
                    JSONObject jsonObject = new JSONObject(json);
                    HomeWrapper.getInstance().updateDMSSet(jsonObject).subscribe(new Subscriber<DMSWarningModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ExceptionUtils.exceptionHandling(mDmsWarningActivity, e);
                        }

                        @Override
                        public void onNext(DMSWarningModel dmsWarningModel) {
                            Toast.makeText(mDmsWarningActivity, "DMS设置信息保存成功", Toast.LENGTH_SHORT).show();
                            mDmsWarningActivity.finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showSaveDMSSetDialog() {
        View view = View.inflate(mDmsWarningActivity, R.layout.dialog_common_styles, null);
        TextView tv_dialog_title = view.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_description = view.findViewById(R.id.tv_dialog_description);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);

        String description = mDmsWarningActivity.getResources().getString(R.string.save_params_write_dialog_description);
        String no = mDmsWarningActivity.getResources().getString(R.string.no);
        String yes = mDmsWarningActivity.getResources().getString(R.string.yes);

        float margin = mDmsWarningActivity.getResources().getDimension(R.dimen.dp_12);
        int paddingTop = (int) mDmsWarningActivity.getResources().getDimension(R.dimen.dp_28);
        int paddingLeft = (int)mDmsWarningActivity.getResources().getDimension(R.dimen.dp_19);
        int paddingRight = (int)mDmsWarningActivity.getResources().getDimension(R.dimen.dp_19);
        int paddingBottom = (int)mDmsWarningActivity.getResources().getDimension(R.dimen.dp_28);

        tv_dialog_title.setVisibility(View.GONE);
        tv_dialog_description.setText(description);
        tv_dialog_description.setTextColor(mDmsWarningActivity.getResources().getColor(R.color.font_color_333));
        tv_dialog_description.setPadding(paddingTop, paddingLeft, paddingRight, paddingBottom);
        tv_cancel.setText(no);
        tv_confirm.setText(yes);

        mDialog = CommonDialog.init()
                .setView(view)
                .setMargin(margin)
                .setOutCancel(false)
                .setAnimStyle(R.style.BottomAnimStyle)
                .setDimAmount(0.8f)
                .show(mDmsWarningActivity.getSupportFragmentManager());

        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    private void getDMSDefaultSet() {
        HomeWrapper.getInstance().getDMSSet().subscribe(new Subscriber<DMSWarningModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mSwipeRefreshLayoutDMSSet.setRefreshing(false); // close refresh animator
                ExceptionUtils.exceptionHandling(mDmsWarningActivity, e);
            }

            @Override
            public void onNext(DMSWarningModel dmsWarningModel) {
                mSwipeRefreshLayoutDMSSet.setRefreshing(false); // close refresh animator
                mDmsWarningModel = dmsWarningModel;
                //清除缓存
                if (!mConvertWarningsList.isEmpty()) {
                    mConvertWarningsList.clear();
                }

               mDmsEnable = dmsWarningModel.getDmsEnable();
                if (mDmsEnable == 1){
                    mIvDMSTotalSwitch.setImageResource(R.mipmap.switch_open_icon);
                    mCloseWarningCount = 0;
                }else {
                    mIvDMSTotalSwitch.setImageResource(R.mipmap.switch_close_icon);
                    mCloseWarningCount = mConvertWarningsList.size();
                }

                //疲劳驾驶报警
                DMSWarningModel.LongTimeDriveAlarmBean longTimeDriveAlarm = dmsWarningModel.getLongTimeDriveAlarm();
                //接打电话报警
                DMSWarningModel.TakePhoneAlarmBean takePhoneAlarm = dmsWarningModel.getTakePhoneAlarm();
                //吸烟报警
                DMSWarningModel.SmokingAlarmBean smokingAlarm = dmsWarningModel.getSmokingAlarm();
                //分神报警
                DMSWarningModel.DistractAlarmBean distractAlarm = dmsWarningModel.getDistractAlarm();
                //驾驶员异常报警
                DMSWarningModel.DriverErrorAlarmBean driverErrorAlarm = dmsWarningModel.getDriverErrorAlarm();
                //驾驶员变更报警
                DMSWarningModel.DriverChangeEventBean driverChangeEvent = dmsWarningModel.getDriverChangeEvent();

                mConvertWarningsList.add(new ConvertWarningsModel(R.string.fatigue_driving, longTimeDriveAlarm.getEnable(), longTimeDriveAlarm.getSensitivityLevel(), longTimeDriveAlarm.getLowestSpeed()));
                mConvertWarningsList.add(new ConvertWarningsModel(R.string.call_phone, takePhoneAlarm.getEnable(), takePhoneAlarm.getSensitivityLevel(), takePhoneAlarm.getLowestSpeed()));
                mConvertWarningsList.add(new ConvertWarningsModel(R.string.smoking_warning, smokingAlarm.getEnable(), smokingAlarm.getSensitivityLevel(), smokingAlarm.getLowestSpeed()));
                mConvertWarningsList.add(new ConvertWarningsModel(R.string.distracted_warning, distractAlarm.getEnable(), distractAlarm.getSensitivityLevel(), distractAlarm.getLowestSpeed()));
                mConvertWarningsList.add(new ConvertWarningsModel(R.string.driver_abnormal, driverErrorAlarm.getEnable(), driverErrorAlarm.getSensitivityLevel(), driverErrorAlarm.getLowestSpeed()));
                mConvertWarningsList.add(new ConvertWarningsModel(R.string.driver_change, driverChangeEvent.getEnable(), driverChangeEvent.getSensitivityLevel(), driverChangeEvent.getLowestSpeed()));

                mCloseWarningCount = 0;
                for (ConvertWarningsModel convertWarningsModel : mConvertWarningsList) {
                    int enable = convertWarningsModel.getEnable();
                    if (enable == 0) {
                        mCloseWarningCount += 1;
                    }
                }

                mCloseTotalCount = mConvertWarningsList.size();
                mWarningsAdapter.setData(mDmsWarningActivity, mConvertWarningsList);
                if (!mWarningsAdapter.hasObservers()) {
                    mRvDmsList.setAdapter(mWarningsAdapter);
                } else {
                    mWarningsAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public void onSwitchListener(int position, ConvertWarningsModel convertWarningsModel, boolean status) {
        //mCloseWarningCount 用于统计当前关闭报警开关的数量，当关闭的数量 和 总数量一样的时候，将总的报警的开关进行
        // 关闭。当有其中一个报警进行打开，总开关的按钮同样也要进行打开
        if (status) {
            mCloseWarningCount--;
            mIvDMSTotalSwitch.setImageResource(R.mipmap.switch_open_icon);
            convertWarningsModel.setEnable(1);
        } else {
            mCloseWarningCount++;
            if (mCloseTotalCount == mCloseWarningCount) {
                mIvDMSTotalSwitch.setImageResource(R.mipmap.switch_close_icon);
            }
            convertWarningsModel.setEnable(0);
        }
        if (mWarningsAdapter != null) {
            mWarningsAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onRefresh() {
        getDMSDefaultSet();
    }
}
