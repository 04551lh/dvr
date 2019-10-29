package com.adasplus.homepager.set.mvp.presenter;

import android.graphics.Color;
import android.util.Log;
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
import com.adasplus.homepager.set.activity.ADASWarningActivity;
import com.adasplus.homepager.set.adapter.WarningsAdapter;
import com.adasplus.homepager.set.mvp.contract.IADASWarningContract;
import com.adasplus.homepager.set.mvp.contract.ISwitchItemClickListener;
import com.adasplus.homepager.set.mvp.model.ADASWarningModel;
import com.adasplus.homepager.set.mvp.model.ConvertWarningsModel;
import com.adasplus.homepager.set.mvp.model.WarningsRestoreDefaultSettingsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 18:12
 * Description :
 */
public class ADASWarningPresenter implements IADASWarningContract.Presenter, View.OnClickListener, ISwitchItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private IADASWarningContract.View mADASWarningView;
    private ADASWarningActivity mAdasWarningActivity;
    private SwipeRefreshLayout mSwipeRefreshLayoutADASSet;
    private ImageView mIvAdasTotalSwitch;
    private RecyclerView mRvAdasList;
    private List<ConvertWarningsModel> mConvertWarningsList = new ArrayList<>();
    private WarningsAdapter mWarningsAdapter;
    private TextView mTvSave;
    private TextView mTvRestoreTheDefaultSettings;
    private ADASWarningModel mAdasWarningModel;
    private int mCloseWarningCount;
    private int mCloseTotalCount;
    private BasicDialog mDialog;

    private  int mAdasEnable;

    public ADASWarningPresenter(IADASWarningContract.View view) {
        mADASWarningView = view;
        mAdasWarningActivity = (ADASWarningActivity) view;
    }

    @Override
    public void initData() {
        mSwipeRefreshLayoutADASSet = mAdasWarningActivity.getSwipeRefreshLayoutADASSet();
        mSwipeRefreshLayoutADASSet.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayoutADASSet.setProgressBackgroundColorSchemeResource(android.R.color.white);

        mRvAdasList = mADASWarningView.getRvAdasList();

        View headerView = View.inflate(mAdasWarningActivity, R.layout.item_head_view, null);
        View footerView = View.inflate(mAdasWarningActivity, R.layout.item_footer_view, null);

        mIvAdasTotalSwitch = headerView.findViewById(R.id.iv_adas_total_switch);
        TextView tv_warning_type = headerView.findViewById(R.id.tv_warning_type);
        tv_warning_type.setText(R.string.adas);
        mTvSave = footerView.findViewById(R.id.tv_save);
        mTvRestoreTheDefaultSettings = footerView.findViewById(R.id.tv_restore_the_default_settings);

        headerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT));
        footerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        mWarningsAdapter = new WarningsAdapter();
        mWarningsAdapter.addHeaderView(headerView);
        mWarningsAdapter.addFooterView(footerView);

        mRvAdasList.setLayoutManager(new LinearLayoutManager(mAdasWarningActivity, RecyclerView.VERTICAL, false));
        //获取 ADAS 默认设置
        getADASDefaultSet();
    }

    /**
     * 获取 部标设备中系统的默认的adas设置
     */
    private void getADASDefaultSet() {
        HomeWrapper.getInstance().getADASSet().subscribe(new Subscriber<ADASWarningModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mSwipeRefreshLayoutADASSet.setRefreshing(false); // close refresh animator
                ExceptionUtils.exceptionHandling(mAdasWarningActivity, e);
            }

            @Override
            public void onNext(ADASWarningModel adasWarningModel) {
                mSwipeRefreshLayoutADASSet.setRefreshing(false); // close refresh animator
                mAdasWarningModel = adasWarningModel;
                if (!mConvertWarningsList.isEmpty()) {
                    mConvertWarningsList.clear();
                }
               mAdasEnable = adasWarningModel.getAdasEnable();
                //判断 ADAS 开关是开着 1:代表着打开 0:代表着关闭
                if (mAdasEnable == 1) {
                    mIvAdasTotalSwitch.setImageResource(R.mipmap.switch_open_icon);
                    mCloseWarningCount = 0;
                } else {
                    mIvAdasTotalSwitch.setImageResource(R.mipmap.switch_close_icon);
                    mCloseWarningCount = mConvertWarningsList.size();
                }

                //前车碰撞报警
                ADASWarningModel.FrontCarCollisionAlarmBean frontCarCollisionAlarm = adasWarningModel.getFrontCarCollisionAlarm();
                //车距过近报警
                ADASWarningModel.NearDistanceAlarmBean nearDistanceAlarm = adasWarningModel.getNearDistanceAlarm();
                //行人碰撞报警
                ADASWarningModel.PedestrianCollisionAlarmBean pedestrianCollisionAlarm = adasWarningModel.getPedestrianCollisionAlarm();
                //车道偏离报警
                ADASWarningModel.VehicleLaneDeviateAlarmBean vehicleLaneDeviateAlarm = adasWarningModel.getVehicleLaneDeviateAlarm();
                //频繁变道报警
                ADASWarningModel.FrequentlyChangeLaneAlarmBean frequentlyChangeLaneAlarm = adasWarningModel.getFrequentlyChangeLaneAlarm();
                //道路标识报警
                ADASWarningModel.LaneFlagDistinguishBean laneFlagDistinguish = adasWarningModel.getLaneFlagDistinguish();

                mConvertWarningsList.add(new ConvertWarningsModel(R.string.vehicle_collision_warning, frontCarCollisionAlarm.getEnable(), frontCarCollisionAlarm.getSensitivityLevel(), frontCarCollisionAlarm.getLowestSpeed()));
                mConvertWarningsList.add(new ConvertWarningsModel(R.string.distance_too_close, nearDistanceAlarm.getEnable(), nearDistanceAlarm.getSensitivityLevel(), nearDistanceAlarm.getLowestSpeed()));
                mConvertWarningsList.add(new ConvertWarningsModel(R.string.safety_alarm, pedestrianCollisionAlarm.getEnable(), pedestrianCollisionAlarm.getSensitivityLevel(), pedestrianCollisionAlarm.getLowestSpeed()));
                mConvertWarningsList.add(new ConvertWarningsModel(R.string.lane_departure_warning, vehicleLaneDeviateAlarm.getEnable(), vehicleLaneDeviateAlarm.getSensitivityLevel(), vehicleLaneDeviateAlarm.getLowestSpeed()));
                mConvertWarningsList.add(new ConvertWarningsModel(R.string.frequently_change_lanes, frequentlyChangeLaneAlarm.getEnable(), frequentlyChangeLaneAlarm.getSensitivityLevel(), frequentlyChangeLaneAlarm.getLowestSpeed()));
                mConvertWarningsList.add(new ConvertWarningsModel(R.string.road_marking, laneFlagDistinguish.getEnable(), laneFlagDistinguish.getSensitivityLevel(), laneFlagDistinguish.getLowestSpeed()));

                //统计默认设备没有进行打开报警的开关
                mCloseWarningCount = 0;
                for (ConvertWarningsModel convertWarningsModel : mConvertWarningsList) {
                    int enable = convertWarningsModel.getEnable();
                    if (enable == 0) {
                        mCloseWarningCount =mCloseWarningCount + 1;
                    }
                }
                mCloseTotalCount = mConvertWarningsList.size();

                mWarningsAdapter.setData(mAdasWarningActivity, mConvertWarningsList);
                if (!mWarningsAdapter.hasObservers()) {
                    mRvAdasList.setAdapter(mWarningsAdapter);
                } else {
                    mWarningsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mADASWarningView.getIvBack();
        ivBack.setOnClickListener(this);
        mSwipeRefreshLayoutADASSet.setOnRefreshListener(this);
        mTvSave.setOnClickListener(this);
        mTvRestoreTheDefaultSettings.setOnClickListener(this);
        mWarningsAdapter.setOnSwitchClickListener(this);
        mIvAdasTotalSwitch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            // 点击返回判断是否有设置的未保存，如果有，进行弹出对话框进行来提示
            boolean isSaveSet = false;
            for (ConvertWarningsModel convertWarningsModel : mConvertWarningsList) {
                int warningNameResId = convertWarningsModel.getWarningNameResId();
                if (warningNameResId == R.string.vehicle_collision_warning) {
                    //前车碰撞报警
                    ADASWarningModel.FrontCarCollisionAlarmBean frontCarCollisionAlarm = mAdasWarningModel.getFrontCarCollisionAlarm();
                    if (convertWarningsModel.getEnable() != frontCarCollisionAlarm.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != frontCarCollisionAlarm.getLowestSpeed() ||
                            convertWarningsModel.getSensitivityLevel() != frontCarCollisionAlarm.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                } else if (warningNameResId == R.string.distance_too_close) {
                    //车距过近报警
                    ADASWarningModel.NearDistanceAlarmBean nearDistanceAlarm = mAdasWarningModel.getNearDistanceAlarm();
                    if (convertWarningsModel.getEnable() != nearDistanceAlarm.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != nearDistanceAlarm.getLowestSpeed() ||
                            convertWarningsModel.getSensitivityLevel() != nearDistanceAlarm.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                } else if (warningNameResId == R.string.safety_alarm) {
                    //行人碰撞报警
                    ADASWarningModel.PedestrianCollisionAlarmBean pedestrianCollisionAlarm = mAdasWarningModel.getPedestrianCollisionAlarm();
                    if (convertWarningsModel.getEnable() != pedestrianCollisionAlarm.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != pedestrianCollisionAlarm.getLowestSpeed() ||
                            convertWarningsModel.getSensitivityLevel() != pedestrianCollisionAlarm.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                } else if (warningNameResId == R.string.lane_departure_warning) {
                    //车道偏离报警
                    ADASWarningModel.VehicleLaneDeviateAlarmBean vehicleLaneDeviateAlarm = mAdasWarningModel.getVehicleLaneDeviateAlarm();
                    if (convertWarningsModel.getEnable() != vehicleLaneDeviateAlarm.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != vehicleLaneDeviateAlarm.getLowestSpeed() ||
                            convertWarningsModel.getSensitivityLevel() != vehicleLaneDeviateAlarm.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                } else if (warningNameResId == R.string.frequently_change_lanes) {
                    //频繁变道报警
                    ADASWarningModel.FrequentlyChangeLaneAlarmBean frequentlyChangeLaneAlarm = mAdasWarningModel.getFrequentlyChangeLaneAlarm();
                    if (convertWarningsModel.getEnable() != frequentlyChangeLaneAlarm.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != frequentlyChangeLaneAlarm.getLowestSpeed() ||
                            convertWarningsModel.getSensitivityLevel() != frequentlyChangeLaneAlarm.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                } else if (warningNameResId == R.string.road_marking) {
                    //道路标识报警
                    ADASWarningModel.LaneFlagDistinguishBean laneFlagDistinguish = mAdasWarningModel.getLaneFlagDistinguish();
                    if (convertWarningsModel.getEnable() != laneFlagDistinguish.getEnable() ||
                            convertWarningsModel.getLowestSpeed() != laneFlagDistinguish.getLowestSpeed() ||
                            convertWarningsModel.getSensitivityLevel() != laneFlagDistinguish.getSensitivityLevel()) {
                        isSaveSet = true;
                        break;
                    }
                }
            }

            if (isSaveSet) {
                showSaveADASSetDialog();
            } else {
                mAdasWarningActivity.finish();
            }
        } else if (id == R.id.iv_adas_total_switch) {
            if(mAdasEnable == 1){
                mIvAdasTotalSwitch.setImageResource(R.mipmap.switch_close_icon);
                mAdasEnable = 0;
                mCloseWarningCount = mCloseTotalCount;
                for (ConvertWarningsModel convertWarningsModel : mConvertWarningsList) {
                    convertWarningsModel.setEnable(0);
                }
                if (mWarningsAdapter != null) {
                    mWarningsAdapter.notifyDataSetChanged();
                }
            }else{
                mIvAdasTotalSwitch.setImageResource(R.mipmap.switch_open_icon);
                mAdasEnable = 1;
                mCloseWarningCount = 0;
                for (ConvertWarningsModel convertWarningsModel : mConvertWarningsList) {
                    convertWarningsModel.setEnable(1);
                }
                if (mWarningsAdapter != null) {
                    mWarningsAdapter.notifyDataSetChanged();
                }
            }
        } else if (id == R.id.tv_cancel) {
            if (mDialog != null && mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mAdasWarningActivity.finish();
        } else if (id == R.id.tv_restore_the_default_settings) {
            //恢复 adas 报警默认设置
            HomeWrapper.getInstance().adasRestoreDefaultSettings().subscribe(new Subscriber<WarningsRestoreDefaultSettingsModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ExceptionUtils.exceptionHandling(mAdasWarningActivity, e);
                }

                @Override
                public void onNext(WarningsRestoreDefaultSettingsModel warningsRestoreDefaultSettingsModel) {
                    Toast.makeText(mAdasWarningActivity, R.string.restore_default_set_tips, Toast.LENGTH_SHORT).show();
                    getADASDefaultSet();
                }
            });
        } else if (id == R.id.tv_save || id == R.id.tv_confirm) {
            if (mAdasWarningModel != null) {
                if (mDialog != null && mDialog.isAdded()) {
                    mDialog.dismiss();
                }

                //设置 ADAS 总开关状态
                mAdasWarningModel.setAdasEnable(mAdasEnable);

                for (ConvertWarningsModel convertWarningsModel : mConvertWarningsList) {
                    int warningNameResId = convertWarningsModel.getWarningNameResId();
                    if (warningNameResId == R.string.vehicle_collision_warning) {
                        //前车碰撞报警
                        ADASWarningModel.FrontCarCollisionAlarmBean frontCarCollisionAlarm = mAdasWarningModel.getFrontCarCollisionAlarm();
                        frontCarCollisionAlarm.setEnable(convertWarningsModel.getEnable());
                        frontCarCollisionAlarm.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        frontCarCollisionAlarm.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mAdasWarningModel.setFrontCarCollisionAlarm(frontCarCollisionAlarm);
                    } else if (warningNameResId == R.string.distance_too_close) {
                        //车距过近报警
                        ADASWarningModel.NearDistanceAlarmBean nearDistanceAlarm = mAdasWarningModel.getNearDistanceAlarm();
                        nearDistanceAlarm.setEnable(convertWarningsModel.getEnable());
                        nearDistanceAlarm.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        nearDistanceAlarm.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mAdasWarningModel.setNearDistanceAlarm(nearDistanceAlarm);
                    } else if (warningNameResId == R.string.safety_alarm) {
                        //行人碰撞报警
                        ADASWarningModel.PedestrianCollisionAlarmBean pedestrianCollisionAlarm = mAdasWarningModel.getPedestrianCollisionAlarm();
                        pedestrianCollisionAlarm.setEnable(convertWarningsModel.getEnable());
                        pedestrianCollisionAlarm.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        pedestrianCollisionAlarm.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mAdasWarningModel.setPedestrianCollisionAlarm(pedestrianCollisionAlarm);
                    } else if (warningNameResId == R.string.lane_departure_warning) {
                        //车道偏离报警
                        ADASWarningModel.VehicleLaneDeviateAlarmBean vehicleLaneDeviateAlarm = mAdasWarningModel.getVehicleLaneDeviateAlarm();
                        vehicleLaneDeviateAlarm.setEnable(convertWarningsModel.getEnable());
                        vehicleLaneDeviateAlarm.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        vehicleLaneDeviateAlarm.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mAdasWarningModel.setVehicleLaneDeviateAlarm(vehicleLaneDeviateAlarm);
                    } else if (warningNameResId == R.string.frequently_change_lanes) {
                        //频繁变道报警
                        ADASWarningModel.FrequentlyChangeLaneAlarmBean frequentlyChangeLaneAlarm = mAdasWarningModel.getFrequentlyChangeLaneAlarm();
                        frequentlyChangeLaneAlarm.setEnable(convertWarningsModel.getEnable());
                        frequentlyChangeLaneAlarm.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        frequentlyChangeLaneAlarm.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mAdasWarningModel.setFrequentlyChangeLaneAlarm(frequentlyChangeLaneAlarm);
                    } else if (warningNameResId == R.string.road_marking) {
                        //道路标识报警
                        ADASWarningModel.LaneFlagDistinguishBean laneFlagDistinguish = mAdasWarningModel.getLaneFlagDistinguish();
                        laneFlagDistinguish.setEnable(convertWarningsModel.getEnable());
                        laneFlagDistinguish.setLowestSpeed(convertWarningsModel.getLowestSpeed());
                        laneFlagDistinguish.setSensitivityLevel(convertWarningsModel.getSensitivityLevel());
                        mAdasWarningModel.setLaneFlagDistinguish(laneFlagDistinguish);
                    }
                }

                String json = GsonUtils.getInstance().toJson(mAdasWarningModel);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    HomeWrapper.getInstance().updateADASSet(jsonObject).subscribe(new Subscriber<ADASWarningModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ExceptionUtils.exceptionHandling(mAdasWarningActivity, e);
                        }

                        @Override
                        public void onNext(ADASWarningModel adasWarningModel) {
                            Toast.makeText(mAdasWarningActivity, R.string.warnings_set_save_success, Toast.LENGTH_SHORT).show();
                            mAdasWarningActivity.finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 显示未保存 adas 设置的对话框
     */
    private void showSaveADASSetDialog() {
        View view = View.inflate(mAdasWarningActivity, R.layout.dialog_common_styles, null);
        TextView tv_dialog_title = view.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_description = view.findViewById(R.id.tv_dialog_description);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);

        String description = mAdasWarningActivity.getString(R.string.save_params_write_dialog_description);
        String no = mAdasWarningActivity.getString(R.string.no);
        String yes = mAdasWarningActivity.getString(R.string.yes);

        float margin = mAdasWarningActivity.getResources().getDimension(R.dimen.dp_20);
        int padding = (int) margin;

        tv_dialog_title.setVisibility(View.GONE);
        tv_dialog_description.setText(description);
        tv_dialog_description.setTextColor(Color.BLACK);
        tv_dialog_description.setPadding(padding, padding, padding, padding);
        tv_cancel.setText(no);
        tv_confirm.setText(yes);


        mDialog = CommonDialog.init()
                .setView(view)
                .setMargin(margin)
                .setOutCancel(false)
                .setAnimStyle(R.style.BottomAnimStyle)
                .setDimAmount(0.8f)
                .show(mAdasWarningActivity.getSupportFragmentManager());

        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    public void onSwitchListener(int position, ConvertWarningsModel convertWarningsModel, boolean status) {
        //mCloseWarningCount 用于统计当前关闭报警开关的数量，当关闭的数量 和 总数量一样的时候，将总的报警的开关进行
        // 关闭。当有其中一个报警进行打开，总开关的按钮同样也要进行打开
        if (status) {
            mCloseWarningCount--;
            mIvAdasTotalSwitch.setImageResource(R.mipmap.switch_open_icon);
            convertWarningsModel.setEnable(1);
        } else {
            mCloseWarningCount++;
            if (mCloseTotalCount == mCloseWarningCount) {
                mIvAdasTotalSwitch.setImageResource(R.mipmap.switch_close_icon);

            }
            convertWarningsModel.setEnable(0);
        }
        if (mWarningsAdapter != null) {
            mWarningsAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onRefresh() {
        getADASDefaultSet();
    }
}
