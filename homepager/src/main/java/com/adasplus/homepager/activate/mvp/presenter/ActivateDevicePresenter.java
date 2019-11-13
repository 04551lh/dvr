package com.adasplus.homepager.activate.mvp.presenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adasplus.base.network.model.SearchServiceRunStatusModel;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.base.view.RecyclerViewDivider;
import com.adasplus.homepager.R;
import com.adasplus.homepager.activate.activity.ActivateDeviceActivity;
import com.adasplus.homepager.activate.activity.AddNewPlatformsActivity;
import com.adasplus.homepager.activate.adapter.ActivatedPlatformsAdapter;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.TerminalInfoModel;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.homepager.activate.mvp.contract.IActivateDeviceContract;
import com.adasplus.homepager.activate.mvp.model.AdministrativeRegionCodeModel;
import com.adasplus.homepager.activate.mvp.model.CarColorModel;
import com.adasplus.homepager.activate.mvp.model.CarInfoModel;
import com.adasplus.homepager.activate.mvp.model.GetPlatformInfoModel;
import com.adasplus.homepager.activate.mvp.model.LicensePlateColorModel;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.utils.VehicleInfoUtil;
import com.alibaba.android.arouter.launcher.ARouter;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/12 14:11
 */
public class ActivateDevicePresenter implements IActivateDeviceContract.Presenter, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private IActivateDeviceContract.View mActivateDeviceView;
    private ActivateDeviceActivity mActivateDeviceActivity;
    private RecyclerView mRvActivatedPlatforms;
    private TextView mTvNoData;
    private TextView mTvPhoneNumber;
    private TextView mTvLicensePlateNumber;
    private TextView mTvChassisNumber;
    private TextView mTvLicensePlateColor;
    private TextView mTvTerminalId;
    private TextView mTvProvincialDomainId;
    private TextView mTvCityAndCountyId;
    private ActivatedPlatformsAdapter mActivatedPlatformsAdapter;
    private TextView mTvPlatformList;
    private TextView mTvEditBasicInfo;
    private String mType;
    private String mPlatformList;
    private String mPlateColorId = "";
    private String mProvincialDomainId = "";
    private String mCityDomainId = "";
    private LinearLayout mLlAddNewPlatform;
    private CarInfoModel mCarInfoModel;
    private Message mMessage;

    private List<GetPlatformInfoModel.ArrayBean> mPlatformInfoArray;
    private static final int WHAT = 1;
    private VehicleInfoHandler mVehicleInfoHandler;
    private SwipeRefreshLayout mSrlActivatePlatformData;

    private static class VehicleInfoHandler extends Handler {

        private WeakReference<ActivateDeviceActivity> mActivateDeviceActivity;

        VehicleInfoHandler(ActivateDeviceActivity ActivateDeviceActivity){
            mActivateDeviceActivity = new WeakReference<>(ActivateDeviceActivity);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == WHAT) {
                CarInfoModel carInfoModel = (CarInfoModel) msg.obj;
                ActivateDeviceActivity activateDeviceActivity = mActivateDeviceActivity.get();
                if (activateDeviceActivity != null){
                    activateDeviceActivity.showDefaultPlateInfo(carInfoModel);
                }
            }
        }
    }

    public ActivateDevicePresenter(IActivateDeviceContract.View view) {
        mActivateDeviceView = view;
        mActivateDeviceActivity = (ActivateDeviceActivity) mActivateDeviceView;
        mVehicleInfoHandler = new VehicleInfoHandler(mActivateDeviceActivity);
    }

    @Override
    public void initWidget() {
        String licensePlateColorData = VehicleInfoUtil.readPlateFileColor(mActivateDeviceActivity);
        String administrativeRegionData = VehicleInfoUtil.readRegionCodeFileName(mActivateDeviceActivity);

        LicensePlateColorModel licensePlateColorModel = GsonUtils.getInstance().jsonToBean(licensePlateColorData, LicensePlateColorModel.class);
        List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList = GsonUtils.getInstance().jsonToList(administrativeRegionData, AdministrativeRegionCodeModel.class);
        mCarInfoModel = new CarInfoModel(administrativeRegionCodeModelList, licensePlateColorModel.getCar_color());

        mActivatedPlatformsAdapter = new ActivatedPlatformsAdapter();
        mRvActivatedPlatforms = mActivateDeviceView.getRvActivatedPlatforms();
        mTvNoData = mActivateDeviceView.getTvNoData();
        mTvPhoneNumber = mActivateDeviceView.getTvPhoneNumber();
        mTvLicensePlateNumber = mActivateDeviceView.getTvLicensePlateNumber();
        mTvChassisNumber = mActivateDeviceView.getTvChassisNumber();
        mTvLicensePlateColor = mActivateDeviceView.getTvLicensePlateColor();
        mTvTerminalId = mActivateDeviceView.getTvTerminalId();
        mTvProvincialDomainId = mActivateDeviceView.getTvProvincialDomainId();
        mTvCityAndCountyId = mActivateDeviceView.getTvCityAndCountyId();
        mTvEditBasicInfo = mActivateDeviceView.getTvEditBasicInfo();
        mTvPlatformList = mActivateDeviceView.getTvPlatformList();
        mSrlActivatePlatformData = mActivateDeviceView.getSrlActivatePlatformData();
        mSrlActivatePlatformData.setOnRefreshListener(this);
        mSrlActivatePlatformData.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSrlActivatePlatformData.setProgressBackgroundColorSchemeResource(android.R.color.white);

        mRvActivatedPlatforms.setLayoutManager(new LinearLayoutManager(mActivateDeviceActivity, RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        int height = (int) mActivateDeviceActivity.getResources().getDimension(R.dimen.dp_10);
        mRvActivatedPlatforms.addItemDecoration(new RecyclerViewDivider(RecyclerView.VERTICAL,height, Color.parseColor("#EEEEEE")));
    }

    @Override
    public void initData() {
        mPlatformList = mActivateDeviceActivity.getResources().getString(R.string.platform_list);
//        mActivateDeviceActivity.showNetRequestDialog();
        BaseWrapper.getInstance().searchServiceRunStatus().subscribe(new Subscriber<SearchServiceRunStatusModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SearchServiceRunStatusModel searchServiceRunStatusModel) {
                //查询当前 808 服务的运行状态，如果808服务未启动的话，
                // 会获取一些空数据或垃圾数据等，所以进行服务的状态判断
                int jt808ServiceStatus = searchServiceRunStatusModel.getJt808Service();
                if (jt808ServiceStatus == 0){
                    Toast.makeText(mActivateDeviceActivity, R.string.jt_808_service_status, Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO 频繁的一下子请求两个接口，会造成接收不到返回的内容,所以中间加了一个休眠的时间。这个需要进行优化
                getVehicleInfo();
                SystemClock.sleep(50);
                getPlatformInfoModel();
            }
        });

//        mActivateDeviceActivity.dismissNetRequestDialog();
    }

    private void getPlatformInfoModel() {
        // 获取已经连接的部标平台
        HomeWrapper.getInstance().getPlatformInfoModel().subscribe(new Subscriber<GetPlatformInfoModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mActivateDeviceActivity, e);
                mSrlActivatePlatformData.setRefreshing(false);
            }

            @Override
            public void onNext(GetPlatformInfoModel getPlatformInfoModel) {
                mSrlActivatePlatformData.setRefreshing(false);
                mPlatformInfoArray = getPlatformInfoModel.getArray();
                int size = mPlatformInfoArray.size();
                // 判断是否有已连接的平台，如果有，进行显示已连接的WiFi 的列表，否则显示暂无数据
                if (size > 0) {
                    showConnectedPlatforms();
                    mLlAddNewPlatform.setVisibility(View.GONE);
                    mLlAddNewPlatform.setEnabled(false);
                    mActivatedPlatformsAdapter.setData(mPlatformInfoArray, mActivateDeviceActivity);
                    if (!mActivatedPlatformsAdapter.hasObservers()) {
                        mRvActivatedPlatforms.setAdapter(mActivatedPlatformsAdapter);
                    } else {
                        mActivatedPlatformsAdapter.notifyDataSetChanged();
                    }
                    //已连接平台的总数量
                    mTvPlatformList.setText(String.format("%s ( %s )", mPlatformList, String.valueOf(size)));
                } else {
                    mLlAddNewPlatform.setVisibility(View.VISIBLE);
                    mLlAddNewPlatform.setEnabled(true);
                    dismissConnectedPlatforms();
                    showAddNewPlatformBtn();
                }
            }
        });
    }

    private void getVehicleInfo() {
        //获取终端的基本信息
        BaseWrapper.getInstance().getVehicleInfo().subscribe(new Subscriber<TerminalInfoModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mSrlActivatePlatformData.setRefreshing(false);
                ExceptionUtils.exceptionHandling(mActivateDeviceActivity, e);
            }

            @Override
            public void onNext(TerminalInfoModel terminalInfoModel) {
                mSrlActivatePlatformData.setRefreshing(false);
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
                //车架号
                mTvChassisNumber.setText(vin);

                if(!plateColor.equals("")){
                    mPlateColorId = plateColor;
                }
                if(!provincialDomain.equals("")){
                    mProvincialDomainId = provincialDomain;
                }
                if(!cityDomain.equals("")){
                    mCityDomainId = cityDomain;
                }
            }
        });
        mMessage = Message.obtain();
        mMessage.what = WHAT;

        mMessage.obj = mCarInfoModel;
        mVehicleInfoHandler.sendMessage(mMessage);
//        ThreadPoolUtils.execute(new Runnable() {
//            @Override
//            public void run() {
//                String licensePlateFileName = "license_plate_color.json";
//                String administrativeRegionCodeFileName = "administrative_region_code.json";
//
//                String licensePlateColorData = VehicleInfoUtil.readVehicleData(mActivateDeviceActivity, licensePlateFileName);
//                String administrativeRegionData = VehicleInfoUtil.readVehicleData(mActivateDeviceActivity, administrativeRegionCodeFileName);
//
//                LicensePlateColorModel licensePlateColorModel = GsonUtils.getInstance().jsonToBean(licensePlateColorData, LicensePlateColorModel.class);
//                List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList = GsonUtils.getInstance().jsonToList(administrativeRegionData, AdministrativeRegionCodeModel.class);
//                mCarInfoModel = new CarInfoModel(administrativeRegionCodeModelList, licensePlateColorModel.getCar_color());
//                mMessage.obj = mCarInfoModel;
//                mVehicleInfoHandler.sendMessage(mMessage);
//            }
//        });

    }

    private void dismissConnectedPlatforms() {
        mRvActivatedPlatforms.setVisibility(View.GONE);
        mTvNoData.setVisibility(View.VISIBLE);
    }

    private void showConnectedPlatforms() {
        mRvActivatedPlatforms.setVisibility(View.VISIBLE);
        mTvNoData.setVisibility(View.GONE);
    }

    private void showAddNewPlatformBtn() {
        mType = mActivateDeviceView.getType();
    }

    @Override
    public void notifyPlatformsSizeShow() {
        if (mPlatformInfoArray != null){
            int size = mPlatformInfoArray.size();
            if (size > 0){
                showConnectedPlatforms();
                //已连接平台的总数量
                mTvPlatformList.setText(String.format("%s ( %s )", mPlatformList, String.valueOf(size)));
            }else {
                dismissConnectedPlatforms();
                mTvPlatformList.setText(String.format("%s", mPlatformList));
                showAddNewPlatformBtn();
            }
        }else {
            dismissConnectedPlatforms();
            mTvPlatformList.setText(String.format("%s", mPlatformList));
            showAddNewPlatformBtn();
        }

    }

    @Override
    public void initListener() {
        ImageView ivBack = mActivateDeviceView.getIvBack();
        mLlAddNewPlatform = mActivateDeviceView.getLlAddNewPlatform();
        ivBack.setOnClickListener(this);
        mTvEditBasicInfo.setOnClickListener(this);
        mLlAddNewPlatform.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            mActivateDeviceActivity.finish();
        } else if (id == R.id.ll_add_new_platform) { //添加新平台
            mActivateDeviceActivity.startActivity(new Intent(mActivateDeviceActivity, AddNewPlatformsActivity.class));
        } else if (id == R.id.tv_edit_basic_info) {
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

    public void showDefaultPlateInfo(CarInfoModel carInfoModel) {
        getDefaultPlateColor(carInfoModel.getCarColor());
        getDefaultProvincialIdOrCityId(carInfoModel.getAdministrativeRegionCodeModelList());
    }

    private void getDefaultPlateColor(List<CarColorModel> carColorModels){
        for (int i = 0; i < carColorModels.size(); i++) {
            if (mPlateColorId.equals(carColorModels.get(i).getPlate_code())){
                mTvLicensePlateColor.setText(carColorModels.get(i).getPlate_color());
            }
        }
    }

    private void getDefaultProvincialIdOrCityId(List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList){
        for (int i = 0; i < administrativeRegionCodeModelList.size(); i++) {
            AdministrativeRegionCodeModel administrativeRegionCodeModel = administrativeRegionCodeModelList.get(i);
            String provincialCode = administrativeRegionCodeModel.getCode();
            String provincialName = administrativeRegionCodeModel.getName();
            if (provincialCode.substring(0, 2).equals(mProvincialDomainId)){
                //省域Id
                mTvProvincialDomainId.setText(provincialName);
                List<AdministrativeRegionCodeModel.CityBean> city = administrativeRegionCodeModel.getCity();
                if (city != null && city.size() > 0) {
                    for (int j = 0; j < city.size(); j++) {
                        AdministrativeRegionCodeModel.CityBean cityBean = city.get(j);
                        List<AdministrativeRegionCodeModel.CityBean.AreaBean> area = cityBean.getArea();
                        if (area != null && area.size() > 0) {
                            for (int z = 0; z < area.size(); z++) {
                                AdministrativeRegionCodeModel.CityBean.AreaBean areaBean = area.get(z);
                                String areaBeanCode = areaBean.getCode();
                                if (mCityDomainId.equals(areaBeanCode.substring(2))){
                                    if (cityBean.getName().equals(mActivateDeviceActivity.getString(R.string.municipal_districts))){
                                        //市县域Id
                                        mTvCityAndCountyId.setText( String.format("%s-%s",administrativeRegionCodeModel.getName(),areaBean.getName()));
                                    }else {
                                        mTvCityAndCountyId.setText(String.format("%s-%s-%s",administrativeRegionCodeModel.getName(),cityBean.getName(),areaBean.getName()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public void onRefresh() {
        initData();
    }

}
