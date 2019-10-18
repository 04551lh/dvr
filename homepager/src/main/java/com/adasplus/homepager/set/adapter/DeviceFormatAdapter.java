package com.adasplus.homepager.set.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IDeviceFormatItemListener;
import com.adasplus.homepager.set.mvp.model.DeviceFormatModel;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/10/12 15:25
 * Description :
 */
public class DeviceFormatAdapter extends RecyclerView.Adapter<DeviceFormatAdapter.DeviceFormatViewHolder>{

    private List<DeviceFormatModel.ArrayBean> mArray;
    private IDeviceFormatItemListener mDeviceFormatItemListener;
    public void setData(List<DeviceFormatModel.ArrayBean> array){
        mArray = array;
    }

    public void setOnItemClickListener(IDeviceFormatItemListener listener){
        mDeviceFormatItemListener = listener;
    }

    @NonNull
    @Override
    public DeviceFormatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_format, parent, false);
        return new DeviceFormatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceFormatViewHolder holder, final int position) {
        final DeviceFormatModel.ArrayBean arrayBean = mArray.get(position);
        String storageName = arrayBean.getStorageName();
        int used = arrayBean.getUsed();
        int capacity = arrayBean.getCapacity();
        int free = arrayBean.getFree();
        int selectEnable = arrayBean.getSelectEnable();

        //设置磁盘名字
        holder.mTvStorageName.setText(storageName);
        //设置总容量
        holder.mTvTotalCapacity.setText(String.valueOf(capacity));
        //设置已用空间
        holder.mTvUsedSpace.setText(String.valueOf(used));
        //设置可用空间
        holder.mTvAvailableSpace.setText(String.valueOf(free));


        if (selectEnable == 1){
            holder.mIvSelectFormatStorage.setVisibility(View.VISIBLE);
        }else {
            holder.mIvSelectFormatStorage.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeviceFormatItemListener != null){
                    mDeviceFormatItemListener.onClickItemListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArray != null ? mArray.size() : 0 ;
    }

    class DeviceFormatViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvStorageName;
        private TextView mTvTotalCapacity;
        private TextView mTvUsedSpace;
        private TextView mTvAvailableSpace;
        private ImageView mIvSelectFormatStorage;

        public DeviceFormatViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvStorageName = (TextView) itemView.findViewById(R.id.tv_storage_name);
            mTvTotalCapacity = (TextView) itemView.findViewById(R.id.tv_total_capacity);
            mTvUsedSpace = (TextView) itemView.findViewById(R.id.tv_used_space);
            mTvAvailableSpace = (TextView) itemView.findViewById(R.id.tv_available_space);
            mIvSelectFormatStorage = (ImageView) itemView.findViewById(R.id.iv_select_format_storage);

        }
    }
}
