package com.adasplus.homepager.activate.mvp.presenter;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.homepager.R;
import com.adasplus.homepager.activate.activity.ActivateDeviceActivity;
import com.adasplus.homepager.activate.activity.AddNewPlatformsActivity;
import com.adasplus.homepager.activate.adapter.ActivatedPlatformsAdapter;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.TerminalInfoModel;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.homepager.activate.mvp.contract.IActivateDeviceContract;
import com.adasplus.homepager.activate.mvp.model.GetPlatformInfoModel;
import com.adasplus.homepager.network.HomeWrapper;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/12 14:11
 */
public class ActivateDevicePresenter implements IActivateDeviceContract.Presenter, View.OnClickListener {

    private IActivateDeviceContract.View mActivateDeviceView;
    private ActivateDeviceActivity mActivateDeviceActivity;
    private RecyclerView mRvActivatedPlatforms;
    private TextView mTvNoData;
    private TextView mTvAddNewPlatforms;
    private TextView mTvPhoneNumber;
    private TextView mTvLicensePlateNumber;
    private TextView mTvChassisNumber;
    private TextView mTvLicensePlateColor;
    private TextView mTvTerminalId;
    private TextView mTvProvincialDomainId;
    private TextView mTvCityAndCountyId;
    private ActivatedPlatformsAdapter mActivatedPlatformsAdapter;
    private TextView mTvPlatformList;
    private TextView mTvActionBarAddPlatforms;
    private ImageView mIvEditBasicInfoIcon;
    private String mType;


    public ActivateDevicePresenter(IActivateDeviceContract.View view) {
        mActivateDeviceView = view;
        mActivateDeviceActivity = (ActivateDeviceActivity) mActivateDeviceView;
    }

    @Override
    public void initWidget() {
        mActivatedPlatformsAdapter = new ActivatedPlatformsAdapter();

        mRvActivatedPlatforms = mActivateDeviceView.getRvActivatedPlatforms();
        mTvNoData = mActivateDeviceView.getTvNoData();
        mTvAddNewPlatforms = mActivateDeviceView.getTvAddNewPlatforms();
        mTvPhoneNumber = mActivateDeviceView.getTvPhoneNumber();
        mTvLicensePlateNumber = mActivateDeviceView.getTvLicensePlateNumber();
        mTvChassisNumber = mActivateDeviceView.getTvChassisNumber();
        mTvLicensePlateColor = mActivateDeviceView.getTvLicensePlateColor();
        mTvTerminalId = mActivateDeviceView.getTvTerminalId();
        mTvProvincialDomainId = mActivateDeviceView.getTvProvincialDomainId();
        mTvCityAndCountyId = mActivateDeviceView.getTvCityAndCountyId();
        mTvPlatformList = mActivateDeviceView.getTvPlatformList();
        mTvActionBarAddPlatforms = mActivateDeviceView.getTvActionBarAddPlatforms();
        mIvEditBasicInfoIcon = mActivateDeviceView.getIvEditBasicInfoIcon();

        mRvActivatedPlatforms.setLayoutManager(new LinearLayoutManager(mActivateDeviceActivity, RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    public void initData() {
        //TODO 频繁的一下子请求两个接口，会造成接收不到返回的内容,所以中间加了一个休眠的时间。这个需要进行优化
        //获取终端的基本信息
        BaseWrapper.getInstance().getVehicleInfo().subscribe(new Subscriber<TerminalInfoModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mActivateDeviceActivity, e);
            }

            @Override
            public void onNext(TerminalInfoModel terminalInfoModel) {
                String phoneNumber = terminalInfoModel.getPhoneNumber();
                String terminalId = terminalInfoModel.getTerminalId();
                String plateNumber = terminalInfoModel.getPlateNumber();
                String plateColor = terminalInfoModel.getPlateColor();
                //车架号
                String vin = terminalInfoModel.getVin();
                String provincialDomain = terminalInfoModel.getProvincialDomain();
                String cityDomain = terminalInfoModel.getCityDomain();

                //设置手机号
                mTvPhoneNumber.setText(phoneNumber);
                //设置终端Id
                mTvTerminalId.setText(terminalId);
                //设置车牌号
                mTvLicensePlateNumber.setText(plateNumber);
                //设置车辆颜色
                mTvLicensePlateColor.setText(plateColor);
                //车架号
                mTvChassisNumber.setText(vin);
                //省域Id
                mTvProvincialDomainId.setText(provincialDomain);
                //市县域Id
                mTvCityAndCountyId.setText(cityDomain);
            }
        });

        SystemClock.sleep(50);

        // 获取已经连接的部标平台
        HomeWrapper.getInstance().getPlatformInfoModel().subscribe(new Subscriber<GetPlatformInfoModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mActivateDeviceActivity, e);
            }

            @Override
            public void onNext(GetPlatformInfoModel getPlatformInfoModel) {
                List<GetPlatformInfoModel.ArrayBean> platformInfoArray = getPlatformInfoModel.getArray();
                String platform_list = mActivateDeviceActivity.getResources().getString(R.string.platform_list);
                int size = platformInfoArray.size();
                // 判断是否有已连接的平台，如果有，进行显示已连接的WiFi 的列表，否则显示暂无数据
                if (size > 0) {
                    mRvActivatedPlatforms.setVisibility(View.VISIBLE);
                    mTvNoData.setVisibility(View.GONE);
                    //已连接平台的总数量
                    mTvPlatformList.setText(String.format("%s ( %s )", platform_list, String.valueOf(size)));
                    mActivatedPlatformsAdapter.setData(platformInfoArray, mActivateDeviceActivity);
                    if (!mActivatedPlatformsAdapter.hasObservers()) {
                        mRvActivatedPlatforms.setAdapter(mActivatedPlatformsAdapter);
                    } else {
                        mActivatedPlatformsAdapter.notifyDataSetChanged();
                    }

                    mTvActionBarAddPlatforms.setVisibility(View.GONE);
                    mTvAddNewPlatforms.setVisibility(View.GONE);
                } else {
                    mTvPlatformList.setText(platform_list);
                    mRvActivatedPlatforms.setVisibility(View.GONE);
                    mTvNoData.setVisibility(View.VISIBLE);

                    //通过类型进行来判断是否是 填写信息 或者 添加新平台，通过这个类型进行来
                    // 显示不同的添加新平台的按钮
                    mType = mActivateDeviceView.getType();
                    if (mType.equals(ActivityPathConstant.FILL_TERMINAL_INFO)) {
                        mTvActionBarAddPlatforms.setVisibility(View.GONE);
                        mTvAddNewPlatforms.setVisibility(View.VISIBLE);
                    } else if (mType.equals(ActivityPathConstant.ADD_NEW_PLATFORMS)) {
                        mTvActionBarAddPlatforms.setVisibility(View.VISIBLE);
                        mTvAddNewPlatforms.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mActivateDeviceView.getIvBack();
        ivBack.setOnClickListener(this);
        mTvActionBarAddPlatforms.setOnClickListener(this);
        mTvAddNewPlatforms.setOnClickListener(this);
        mIvEditBasicInfoIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            mActivateDeviceActivity.finish();
        } else if (id == R.id.tv_add_new_platforms || id == R.id.tv_action_bar_add_platforms) { //添加新平台
            mActivateDeviceActivity.startActivity(new Intent(mActivateDeviceActivity, AddNewPlatformsActivity.class));
        } else if (id == R.id.iv_edit_basic_info_icon) {
            startFillOrUpdateCarInfo();
        }
    }

    private void startFillOrUpdateCarInfo() {
        ARouter.getInstance()
                .build(ActivityPathConstant.FILL_TERMINAL_INFO_PATH)
                .withBoolean("isFillTerminalInfo", false)
                .withString("type", mType)
                .navigation();
    }
}
