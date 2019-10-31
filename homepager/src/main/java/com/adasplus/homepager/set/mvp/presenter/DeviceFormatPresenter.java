package com.adasplus.homepager.set.mvp.presenter;

import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/12 14:33
 * Description :
 */
public class DeviceFormatPresenter implements IDeviceFormatContract.Presenter, View.OnClickListener, IDeviceFormatItemListener, SwipeRefreshLayout.OnRefreshListener {

    private IDeviceFormatContract.View mDeviceFormatView;
    private DeviceFormatActivity mDeviceFormatActivity;
    private RecyclerView mRvDeviceFormatList;
    private DeviceFormatAdapter mDeviceFormatAdapter;
    private List<DeviceFormatModel.ArrayBean> mArray;
    private SwipeRefreshLayout mSwipeRefreshLayoutDeviceFormatSet;

    public DeviceFormatPresenter(IDeviceFormatContract.View view) {
        mDeviceFormatView = view;
        mDeviceFormatActivity = (DeviceFormatActivity) view;
    }

    @Override
    public void initData() {
        mSwipeRefreshLayoutDeviceFormatSet = mDeviceFormatActivity.getSwipeRefreshLayoutDeviceFormatSet();
        mSwipeRefreshLayoutDeviceFormatSet.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayoutDeviceFormatSet.setProgressBackgroundColorSchemeResource(android.R.color.white);

        mArray = new ArrayList<>();
        mDeviceFormatAdapter = new DeviceFormatAdapter();
        mRvDeviceFormatList = mDeviceFormatView.getRvDeviceFormatList();
        mRvDeviceFormatList.setLayoutManager(new LinearLayoutManager(mDeviceFormatActivity, RecyclerView.VERTICAL, false));

        getNetwork();
     }

     private void getNetwork(){
         //获取存储器的列表
         HomeWrapper.getInstance().getStorageInfoList().subscribe(new Subscriber<DeviceFormatModel>() {
             @Override
             public void onCompleted() {

             }

             @Override
             public void onError(Throwable e) {
                 mSwipeRefreshLayoutDeviceFormatSet.setRefreshing(false);
                 ExceptionUtils.exceptionHandling(mDeviceFormatActivity, e);
             }

             @Override
             public void onNext(DeviceFormatModel deviceFormatModel) {
                 mSwipeRefreshLayoutDeviceFormatSet.setRefreshing(false);
                 if(mArray != null){
                     mArray.clear();
                 }
                 mArray = deviceFormatModel.getArray();
                 mDeviceFormatAdapter.setData(mArray);
                 mRvDeviceFormatList.setAdapter(mDeviceFormatAdapter);
             }
         });

     }

    @Override
    public void initListener() {
        ImageView ivDeviceFormatBack = mDeviceFormatView.getIvDeviceFormatBack();
        TextView tvDeviceFormatData = mDeviceFormatView.getTvDeviceFormatData();

        ivDeviceFormatBack.setOnClickListener(this);
        mSwipeRefreshLayoutDeviceFormatSet.setOnRefreshListener(this);
        tvDeviceFormatData.setOnClickListener(this);
        mDeviceFormatAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_device_format_back) {
            mDeviceFormatActivity.finish();
        } else if (id == R.id.tv_device_format_data) {
            if (mArray != null && mArray.size() > 0) {
                JSONObject storageDeviceArray = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                try {
                    for (int i = 0; i < mArray.size(); i++) {
                        if (mArray.get(i).getSelectEnable() == 1) {
                            String storageName = mArray.get(i).getStorageName();
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("storageName", storageName);
                            jsonArray.put(jsonObject);
                            storageDeviceArray.put("storageDeviceArray", jsonArray);
                        }
                    }
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

    @Override
    public void onRefresh() {
        if(mSwipeRefreshLayoutDeviceFormatSet!=null){
            getNetwork();
        }
    }
}
