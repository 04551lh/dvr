package com.adasplus.homepager.set.adapter;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.adasplus.base.utils.PatternUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IDeviceFormatItemListener;
import com.adasplus.homepager.set.mvp.model.DeviceFormatModel;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/10/12 15:25
 * Description :
 */
public class DeviceFormatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<DeviceFormatModel.ArrayBean> mArray;
    private IDeviceFormatItemListener mDeviceFormatItemListener;
    private Activity mActivity;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    private static final int BASE_ITEM_TYPE_HEADER = 0x00001;
    private static final int BASE_ITEM_TYPE_FOOTER = 0x00002;


    public DeviceFormatAdapter(Activity activity){
        mActivity = activity;
    }
    public void setData(List<DeviceFormatModel.ArrayBean> array){
        mArray = array;
    }

    public void setOnItemClickListener(IDeviceFormatItemListener listener){
        mDeviceFormatItemListener = listener;
    }
    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getDeviceFormatCount();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFooterView(View view) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    private int getHeadersCount() {
        return mHeaderViews.size();
    }

    private int getFootersCount() {
        return mFooterViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - getDeviceFormatCount());
        }
        return super.getItemViewType(position - getHeadersCount());
    }

    private int getDeviceFormatCount() {
        return mArray != null ? mArray.size() : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            View view = mHeaderViews.get(viewType);
            if (view != null) {
                return new HeaderHolder(view);
            }
        } else if (mFooterViews.get(viewType) != null) {
            View view = mFooterViews.get(viewType);
            if (view != null) {
                return new FooterHolder(view);
            }
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_format, parent, false);
        return new DeviceFormatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (isHeaderViewPos(position))
            return;
        if (isFooterViewPos(position))
            return;
        if (holder instanceof DeviceFormatViewHolder) {
            final DeviceFormatViewHolder deviceFormatViewHolder = (DeviceFormatViewHolder) holder;
            final DeviceFormatModel.ArrayBean arrayBean = mArray.get(position - getHeadersCount());
            String storageName = arrayBean.getStorageName();
            int used = arrayBean.getUsed();
            int capacity = arrayBean.getCapacity();
            int free = arrayBean.getFree();
            int selectEnable = arrayBean.getSelectEnable();

            //设置磁盘名字
            deviceFormatViewHolder.mTvStorageName.setText(storageName);
            //设置总容量
//            deviceFormatViewHolder.mTvTotalCapacity.setText(String.format("%s", (capacity + mActivity.getResources().getString(R.string.storage_unit))));
            deviceFormatViewHolder.mTvTotalCapacity.setText(String.format("%s", PatternUtils.FormetFileSize(capacity)));
            //设置已用空间
            deviceFormatViewHolder.mTvUsedSpace.setText(String.format("%s", PatternUtils.FormetFileSize(used)));
            //设置可用空间
            deviceFormatViewHolder.mTvAvailableSpace.setText(String.format("%s",PatternUtils.FormetFileSize(free)));


            if (selectEnable == 1) {
                deviceFormatViewHolder.mIvSelectFormatStorage.setImageResource(R.mipmap.switch_open_icon);
            } else {
                deviceFormatViewHolder.mIvSelectFormatStorage.setImageResource(R.mipmap.switch_close_icon);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeviceFormatItemListener != null) {
                        mDeviceFormatItemListener.onClickItemListener(position - getHeadersCount());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getDeviceFormatCount() + getFootersCount();
    }

    class DeviceFormatViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvStorageName;
        private TextView mTvTotalCapacity;
        private TextView mTvUsedSpace;
        private TextView mTvAvailableSpace;
        private ImageView mIvSelectFormatStorage;

        private DeviceFormatViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvStorageName =  itemView.findViewById(R.id.tv_storage_name);
            mTvTotalCapacity =  itemView.findViewById(R.id.tv_total_capacity);
            mTvUsedSpace =  itemView.findViewById(R.id.tv_used_space);
            mTvAvailableSpace =  itemView.findViewById(R.id.tv_available_space);
            mIvSelectFormatStorage =  itemView.findViewById(R.id.iv_select_format_storage);

        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        HeaderHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {

        FooterHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
