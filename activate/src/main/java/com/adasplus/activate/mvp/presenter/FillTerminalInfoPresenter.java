package com.adasplus.activate.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.adasplus.activate.R;
import com.adasplus.activate.activity.ActivateDeviceActivity;
import com.adasplus.activate.activity.FillTerminalInfoActivity;
import com.adasplus.activate.mvp.contract.IFillTerminalInfoContract;
import com.adasplus.activate.mvp.model.ActivateNewPlatformsModel;
import com.adasplus.activate.mvp.model.AdministrativeRegionCodeModel;
import com.adasplus.activate.mvp.model.CarColorModel;
import com.adasplus.activate.mvp.model.CarInfoModel;
import com.adasplus.activate.mvp.model.GetPlatformInfoModel;
import com.adasplus.activate.mvp.model.LicensePlateColorModel;
import com.adasplus.activate.network.ActivateWrapper;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.TerminalInfoModel;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.base.utils.PatternUtils;
import com.adasplus.base.utils.ThreadPoolUtils;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/10 17:00
 * Description :
 */
public class FillTerminalInfoPresenter implements IFillTerminalInfoContract.Presenter, View.OnClickListener {
    private static final String TAG = "ActivateNewPlatformsPresenter";
    private static final int WHAT = 1;

    private IFillTerminalInfoContract.View mFillTerminalInfoView;
    private Context mContext;
    private FillTerminalInfoActivity mActivateNewPlatformsActivity;
    private EditText mEtPlatformPhoneNumber;
    private EditText mEtLicensePlateNumber;
    private EditText mEtTerminalId;
    private TextView mTvLicensePlateColor;
    private TextView mTvProvincialDomainId;
    private TextView mTvCityAndCountyId;
    private OptionsPickerView<String> mLicensePlateColorPickerView;
    private int mLicensePlateColor = 0;
    private String mProvincialDomainId = "0";
    private String mAreaId = "0";
    private OptionsPickerView<String> mProvincialDomainIdPickerView;
    private OptionsPickerView mCityAndCountyIdPickerView;

    private String mType;
    private EditText mEtChassisNumber;
    private boolean mIsFillTerminalInfo;
    private InitDataHandler mInitDataHandler;


    private static class InitDataHandler extends Handler {
        private WeakReference<FillTerminalInfoActivity> mFillTerminalInfoWeakReference;

        InitDataHandler(FillTerminalInfoActivity activity) {
            mFillTerminalInfoWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            FillTerminalInfoActivity fillTerminalInfoActivity = mFillTerminalInfoWeakReference.get();
            int what = msg.what;
            if (what == WHAT) {
                CarInfoModel carInfoModel = (CarInfoModel) msg.obj;
                List<CarColorModel> carColor = carInfoModel.getCarColor();
                List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList = carInfoModel.getAdministrativeRegionCodeModelList();
                if (fillTerminalInfoActivity != null) {
                    //初始化 车辆颜色 数据
                    fillTerminalInfoActivity.initLicensePlateColor(carColor);
                    //初始化 省域ID 数据
                    fillTerminalInfoActivity.initProvincialDomainId(administrativeRegionCodeModelList);
                    //初始化 市县域ID 数据
                    fillTerminalInfoActivity.initCityAndCountyId(administrativeRegionCodeModelList);
                }
            }
        }
    }

    public FillTerminalInfoPresenter(IFillTerminalInfoContract.View view) {
        mFillTerminalInfoView = view;
        mContext = (Context) mFillTerminalInfoView;
        mActivateNewPlatformsActivity = (FillTerminalInfoActivity) mFillTerminalInfoView;
        mInitDataHandler = new InitDataHandler(mActivateNewPlatformsActivity);
    }

    @Override
    public void initData() {
        mEtPlatformPhoneNumber = mFillTerminalInfoView.getEtPlatformPhoneNumber();
        mEtLicensePlateNumber = mFillTerminalInfoView.getEtLicensePlateNumber();
        mEtTerminalId = mFillTerminalInfoView.getEtTerminalId();
        mTvLicensePlateColor = mFillTerminalInfoView.getTvLicensePlateColor();
        mTvProvincialDomainId = mFillTerminalInfoView.getTvProvincialDomainId();
        mTvCityAndCountyId = mFillTerminalInfoView.getTvCityAndCountyId();
        mEtChassisNumber = mFillTerminalInfoView.getEtChassisNumber();
        mType = mFillTerminalInfoView.getType();
        TextView tvTitle = mFillTerminalInfoView.getTvTitle();
        String fill_terminal_info = mActivateNewPlatformsActivity.getResources().getString(R.string.fill_terminal_info);
        String update_terminal_info = mActivateNewPlatformsActivity.getResources().getString(R.string.update_terminal_info);
        mIsFillTerminalInfo = mFillTerminalInfoView.isFillTerminalInfo();


        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                String licensePlateFileName = "license_plate_color.json";
                //读取本地的 车辆颜色 数据
                String plateColor = readData(licensePlateFileName);
                String administrativeRegionCodeFileName = "administrative_region_code.json";
                //读取本地的 行政区域代码 数据
                String administrativeRegionCode = readData(administrativeRegionCodeFileName);
                LicensePlateColorModel licensePlateColorModel = GsonUtils.getInstance().jsonToBean(plateColor, LicensePlateColorModel.class);
                List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList = GsonUtils.getInstance().jsonToList(administrativeRegionCode, AdministrativeRegionCodeModel.class);
                CarInfoModel carInfoModel = new CarInfoModel(administrativeRegionCodeModelList, licensePlateColorModel.getCar_color());

                Message message = Message.obtain();
                message.what = WHAT;
                message.obj = carInfoModel;
                mInitDataHandler.sendMessage(message);
            }
        });

        if (mIsFillTerminalInfo) {
            tvTitle.setText(fill_terminal_info);
        } else {
            tvTitle.setText(update_terminal_info);
            //获取终端的车辆信息
            BaseWrapper.getInstance().getVehicleInfo().subscribe(new Subscriber<TerminalInfoModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ExceptionUtils.exceptionHandling(mActivateNewPlatformsActivity, e);
                }

                @Override
                public void onNext(TerminalInfoModel terminalInfoModel) {
                    String phoneNumber = terminalInfoModel.getPhoneNumber();
                    String plateNumber = terminalInfoModel.getPlateNumber();
                    String vin = terminalInfoModel.getVin();
                    String plateColor = terminalInfoModel.getPlateColor();
                    String provincialDomain = terminalInfoModel.getProvincialDomain();
                    String cityDomain = terminalInfoModel.getCityDomain();
                    String terminalId = terminalInfoModel.getTerminalId();

                    //设置车牌颜色的初始值 :
                    mLicensePlateColor = Integer.parseInt(plateColor);
                    //设置省域初始值
                    mProvincialDomainId = provincialDomain;
                    //设置市县域初始值
                    mAreaId = cityDomain;

                    //设置手机号
                    mEtPlatformPhoneNumber.setText(phoneNumber);
                    //设置车牌号
                    mEtLicensePlateNumber.setText(plateNumber);
                    //设置 终端id
                    mEtTerminalId.setText(terminalId);
                    //设置车辆颜色
                    mTvLicensePlateColor.setText(plateColor);
                    //设置省域id
                    mTvProvincialDomainId.setText(provincialDomain);
                    //设置市县域id
                    mTvCityAndCountyId.setText(cityDomain);
                    //设置车架号
                    mEtChassisNumber.setText(vin);
                }
            });
        }
    }

    //这个是市县域 ID，根据 GB2260 协议 中的行政区域代码进行定义，行政区域代码的后四位代表的是市县域Id
    public void initCityAndCountyId(final List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList) {
        final List<String> provincialIdList = new ArrayList<>();
        List<ArrayList<String>> cityIdList = new ArrayList<>();
        final List<ArrayList<ArrayList<String>>> areaIdList = new ArrayList<>();
        for (int i = 0; i < administrativeRegionCodeModelList.size(); i++) {
            AdministrativeRegionCodeModel administrativeRegionCodeModel = administrativeRegionCodeModelList.get(i);
            String name = administrativeRegionCodeModel.getName();
            List<AdministrativeRegionCodeModel.CityBean> city = administrativeRegionCodeModel.getCity();
            provincialIdList.add(name);
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            if (city != null && city.size() > 0) {
                for (int j = 0; j < city.size(); j++) {
                    AdministrativeRegionCodeModel.CityBean cityBean = city.get(j);
                    cityList.add(cityBean.getName());
                    List<AdministrativeRegionCodeModel.CityBean.AreaBean> area = cityBean.getArea();
                    ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                    if (area != null && area.size() > 0) {
                        for (int z = 0; z < area.size(); z++) {
                            AdministrativeRegionCodeModel.CityBean.AreaBean areaBean = area.get(z);
                            city_AreaList.add(areaBean.getName());
                        }
                        province_AreaList.add(city_AreaList);
                    }
                }
            }
            cityIdList.add(cityList);
            areaIdList.add(province_AreaList);
        }

        mCityAndCountyIdPickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                AdministrativeRegionCodeModel administrativeRegionCodeModel = administrativeRegionCodeModelList.get(options1);
                List<AdministrativeRegionCodeModel.CityBean> city = administrativeRegionCodeModel.getCity();
                if (city.size() > 0) {
                    AdministrativeRegionCodeModel.CityBean cityBean = city.get(options2);
                    List<AdministrativeRegionCodeModel.CityBean.AreaBean> area = cityBean.getArea();
                    if (area.size() > 0) {
                        AdministrativeRegionCodeModel.CityBean.AreaBean areaBean = area.get(options3);
                        String code = areaBean.getCode();
                        mAreaId = code.substring(2);
                        mTvCityAndCountyId.setText(mAreaId);
                    }
                } else {
                    String code = administrativeRegionCodeModel.getCode();
                    mAreaId = code.substring(2);
                    mTvCityAndCountyId.setText(mAreaId);
                }
            }
        }).setTitleText(mContext.getString(R.string.please_choose_area))
                .setOutSideCancelable(false)
                .build();
        mCityAndCountyIdPickerView.setPicker(provincialIdList, cityIdList, areaIdList);
    }

    // 这个是省域Id ，GB2260 协议 中的行政区域代码进行定义，行政区域代码中的前两位代表的是省域Id
    public void initProvincialDomainId(final List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList) {
        List<String> provincialDomainId = new ArrayList<>();
        for (int i = 0; i < administrativeRegionCodeModelList.size(); i++) {
            provincialDomainId.add(administrativeRegionCodeModelList.get(i).getName());
        }
        mProvincialDomainIdPickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                AdministrativeRegionCodeModel administrativeRegionCodeModel = administrativeRegionCodeModelList.get(options1);
                String code = administrativeRegionCodeModel.getCode();
                mProvincialDomainId = code.substring(0, 2);
                mTvProvincialDomainId.setText(mProvincialDomainId);
            }
        }).setCancelText(mContext.getString(R.string.cancel))
                .setSubmitText(mContext.getString(R.string.confirm))
                .setTitleText(mContext.getString(R.string.please_choose_province))
                .setOutSideCancelable(false)
                .build();
        mProvincialDomainIdPickerView.setPicker(provincialDomainId);
    }

    //车牌颜色 是根据 JT/T 415-2006 协议中的 5.4.12 的表42 进行定义的
    public void initLicensePlateColor(final List<CarColorModel> car_color) {
        List<String> carColor = new ArrayList<>();
        for (int i = 0; i < car_color.size(); i++) {
            String plate_color = car_color.get(i).getPlate_color();
            carColor.add(plate_color);
        }
        mLicensePlateColorPickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                CarColorModel carColorModel = car_color.get(options1);
                String plate_color = carColorModel.getPlate_color();
                mLicensePlateColor = carColorModel.getPlate_code();
                Log.i(TAG, "选择的车辆颜色是:" + plate_color + " ---- 车辆颜色的代码是:" + mLicensePlateColor);
                mTvLicensePlateColor.setText(plate_color);
            }
        })
                .setCancelText(mContext.getString(R.string.cancel))
                .setSubmitText(mContext.getString(R.string.confirm))
                .setTitleText(mContext.getString(R.string.license_plate_color))
                .setOutSideCancelable(false)
                .build();
        mLicensePlateColorPickerView.setPicker(carColor);
    }

    private String readData(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = mContext.getAssets().open(fileName);
            String standardCharsets = "UTF-8";
            BufferedReader br = new BufferedReader(new InputStreamReader(is, standardCharsets));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void initListener() {
        ImageView ivBack = mFillTerminalInfoView.getIvBack();
        TextView tvSaveTerminalInfo = mFillTerminalInfoView.getTvSaveTerminalInfo();
        ivBack.setOnClickListener(this);
        tvSaveTerminalInfo.setOnClickListener(this);
        mTvLicensePlateColor.setOnClickListener(this);
        mTvProvincialDomainId.setOnClickListener(this);
        mTvCityAndCountyId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_back) {
            mActivateNewPlatformsActivity.finish();
        } else if (i == R.id.tv_save_terminal_info) { // 保存终端信息数据
            String phoneNumber = mEtPlatformPhoneNumber.getText().toString().trim();
            final String plateNumber = mEtLicensePlateNumber.getText().toString().trim();
            final String terminalId = mEtTerminalId.getText().toString().trim();
            String chassisNumber = mEtChassisNumber.getText().toString().trim();

            if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(mContext, R.string.phone_number_is_not_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            //判断输入的手机号是否是 11 位，如果是 11 位的手机号，进行检查输入的手机号的格式是否合法，
            // 并自动前面拼接一个 0 （根据 JT 808 协议 表7），否则是不做校验
            if (phoneNumber.length() == 11) {
                if (!PatternUtils.checkPhoneNumber(phoneNumber)) {
                    Toast.makeText(mContext, R.string.phone_number_format_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                phoneNumber = "0" + phoneNumber;
            }

            JSONObject jobj = new JSONObject();
            try {
                jobj.put("phoneNumber", phoneNumber);
                jobj.put("terminalId", terminalId);
                jobj.put("plateNumber", plateNumber);
                jobj.put("plateColor", String.valueOf(mLicensePlateColor));
                jobj.put("provincialDomain", mProvincialDomainId);
                jobj.put("cityDomain", mAreaId);
                jobj.put("vin", chassisNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            BaseWrapper.getInstance().saveVehicleInfo(jobj).subscribe(new Subscriber<TerminalInfoModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ExceptionUtils.exceptionHandling(mContext, e);
                }

                @Override
                public void onNext(TerminalInfoModel terminalInfoModel) {
                    if (mIsFillTerminalInfo) {
                        ARouter.getInstance()
                                .build(ActivityPathConstant.ACTIVATE_DEVICE_PATH)
                                .withString("type", mType)
                                .navigation();
                    }
                    mActivateNewPlatformsActivity.finish();
                }
            });
        } else if (i == R.id.tv_license_plate_color) {
            if (mLicensePlateColorPickerView != null) {
                mLicensePlateColorPickerView.show();
            }
        } else if (i == R.id.tv_provincial_domain_id) {
            if (mProvincialDomainIdPickerView != null) {
                mProvincialDomainIdPickerView.show();
            }
        } else if (i == R.id.tv_city_and_county_id) {
            if (mCityAndCountyIdPickerView != null) {
                mCityAndCountyIdPickerView.show();
            }
        }
    }


}
