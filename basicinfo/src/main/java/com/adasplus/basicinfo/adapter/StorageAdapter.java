package com.adasplus.basicinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.network.model.SystemInfoModel;
import com.adasplus.basicinfo.R;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/10/15 14:55
 * Description :
 */
public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.StorageViewHolder> {

    private List<SystemInfoModel.StorageArrayBean> mStorageArray;
    public void setData(List<SystemInfoModel.StorageArrayBean> storageArray){
        mStorageArray = storageArray;
    }

    @NonNull
    @Override
    public StorageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storage_info, parent, false);
        return new StorageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        String main_disk = context.getResources().getString(R.string.main_disk);
        String iso_disk = context.getResources().getString(R.string.iso_disk);
        String backup_disk = context.getResources().getString(R.string.backup_disk);

        SystemInfoModel.StorageArrayBean storageArrayBean = mStorageArray.get(position);
        // 磁盘类型 返回值: 0 : 主存储 1: 镜像盘 2: 备份盘
        int storageDeviceType = storageArrayBean.getStorageDeviceType();
        //总容量
        int capacityStorage = storageArrayBean.getCapacity() / 1024;
        //已使用
        int usedStorage = storageArrayBean.getUsed() / 1024;
        //可用
        int freeStorage = storageArrayBean.getFree() / 1024;
        //特殊录像
        int specialVideoSize = storageArrayBean.getSpecialVideoSize();


        if (storageDeviceType == 0){
            holder.mTvStorageType.setText(main_disk);
        }else if (storageDeviceType == 1){
            holder.mTvStorageType.setText(iso_disk);
        }else if (storageDeviceType == 2){
            holder.mTvStorageType.setText(backup_disk);
        }

        holder.mTvCapacityStorage.setText(String.format("%sG",String.valueOf(capacityStorage)));
        holder.mTvUsedStorage.setText(String.format("%sG",String.valueOf(usedStorage)));
        holder.mTvFreeStorage.setText(String.format("%sG",String.valueOf(freeStorage)));

        if (usedStorage < capacityStorage){
            holder.mTvDiskStatus.setText(R.string.disk_status_used);
        }else {
            holder.mTvDiskStatus.setText(R.string.disk_status_no);
        }

        holder.mTvSpecialVideo.setText(String.format("%sM",String.valueOf(specialVideoSize)));
    }

    @Override
    public int getItemCount() {
        return mStorageArray != null ? mStorageArray.size() : 0;
    }

    class StorageViewHolder extends RecyclerView.ViewHolder{
        
        private TextView mTvStorageType;
        private TextView mTvCapacityStorage;
        private TextView mTvUsedStorage;
        private TextView mTvFreeStorage;
        private TextView mTvDiskStatus;
        private TextView mTvSpecialVideo;

        public StorageViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvStorageType = (TextView) itemView.findViewById(R.id.tv_storage_type);
            mTvCapacityStorage = (TextView) itemView.findViewById(R.id.tv_capacity_storage);
            mTvUsedStorage = (TextView) itemView.findViewById(R.id.tv_used_storage);
            mTvFreeStorage = (TextView) itemView.findViewById(R.id.tv_free_storage);
            mTvDiskStatus = (TextView) itemView.findViewById(R.id.tv_disk_status);
            mTvSpecialVideo = (TextView) itemView.findViewById(R.id.tv_special_video);
        }
    }
}
