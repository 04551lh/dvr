package com.adasplus.homepager.set.mvp.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.DeviceFormatActivity;
import com.adasplus.homepager.set.adapter.DeviceFormatAdapter;
import com.adasplus.homepager.set.mvp.contract.IDeviceFormatContract;
import com.adasplus.homepager.set.mvp.contract.IDeviceFormatItemListener;
import com.adasplus.homepager.set.mvp.model.DeviceFormatModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/12 14:33
 * Description :
 */
public class DeviceFormatPresenter implements IDeviceFormatContract.Presenter, View.OnClickListener, IDeviceFormatItemListener {

    private IDeviceFormatContract.View mDeviceFormatView;
    private DeviceFormatActivity mDeviceFormatActivity;
    private RecyclerView mRvDeviceFormatList;
    private DeviceFormatAdapter mDeviceFormatAdapter;
    private List<DeviceFormatModel.ArrayBean> mArray;

    public DeviceFormatPresenter(IDeviceFormatContract.View view) {
        mDeviceFormatView = view;
        mDeviceFormatActivity = (DeviceFormatActivity) view;
    }

    @Override
    public void initData() {
        mDeviceFormatAdapter = new DeviceFormatAdapter();
        mRvDeviceFormatList = mDeviceFormatView.getRvDeviceFormatList();
        mRvDeviceFormatList.setLayoutManager(new LinearLayoutManager(mDeviceFormatActivity, RecyclerView.VERTICAL, false));

        //获取存储器的列表
        HomeWrapper.getInstance().getStorageInfoList().subscribe(new Subscriber<DeviceFormatModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mDeviceFormatActivity, e);
            }

            @Override
            public void onNext(DeviceFormatModel deviceFormatModel) {
                List<DeviceFormatModel.ArrayBean> array = deviceFormatModel.getArray();
                mArray = array;
                mDeviceFormatAdapter.setData(mArray);
                mRvDeviceFormatList.setAdapter(mDeviceFormatAdapter);
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mDeviceFormatView.getIvBack();
        TextView tvDeviceFormatData = mDeviceFormatView.getTvDeviceFormatData();

        ivBack.setOnClickListener(this);
        tvDeviceFormatData.setOnClickListener(this);
        mDeviceFormatAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            mDeviceFormatActivity.finish();
        } else if (id == R.id.tv_device_format_data) {
            if (mArray != null && mArray.size() > 0) {

                JSONObject storageDeviceArray = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                try {
                    if (mArray != null){
                        for (int i = 0; i < mArray.size();i++){
                            DeviceFormatModel.ArrayBean arrayBean = mArray.get(i);
                            String storageName = arrayBean.getStorageName();
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("storageName",storageName);
                            jsonArray.put(i,jsonArray);
                        }
                    }
                    storageDeviceArray.put("storageDeviceArray", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //开始格式化磁盘的接口请求
                HomeWrapper.getInstance().updateStorageFormatList(storageDeviceArray).subscribe(new Subscriber<DeviceFormatModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionUtils.exceptionHandling(mDeviceFormatActivity, e);
                    }

                    @Override
                    public void onNext(DeviceFormatModel deviceFormatModel) {
                        Toast.makeText(mDeviceFormatActivity, R.string.device_format_success, Toast.LENGTH_SHORT).show();
                        mDeviceFormatActivity.finish();
                    }
                });
            }

        }
    }

    @Override
    public void onClickItemListener(int position) {
        //选择要格式化的磁盘
        if (mArray != null && mArray.size() > 0) {
            DeviceFormatModel.ArrayBean arrayBean = mArray.get(position);
            arrayBean.setSelectEnable(arrayBean.getSelectEnable() == 1 ? 0 : 1);

            if (mDeviceFormatAdapter != null) {
                mDeviceFormatAdapter.notifyDataSetChanged();
            }
        }
    }
}
