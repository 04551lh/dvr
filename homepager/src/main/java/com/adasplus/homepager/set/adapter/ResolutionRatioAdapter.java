package com.adasplus.homepager.set.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.OnResolutionRatioListener;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/10/17 10:42
 * Description :
 */
public class ResolutionRatioAdapter extends RecyclerView.Adapter<ResolutionRatioAdapter.ResolutionRatioViewHolder> {

    private List<String> mResolutionRatioList;
    private OnResolutionRatioListener mOnResolutionRatioItemListener;

    public void setData(List<String> resolutionRatioList){
        mResolutionRatioList = resolutionRatioList;
    }

    public void setOnResolutionRatioItemListener(OnResolutionRatioListener onResolutionRatioItemListener){
        mOnResolutionRatioItemListener = onResolutionRatioItemListener;
    }

    @NonNull
    @Override
    public ResolutionRatioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resolution_ratio, parent, false);
        return new ResolutionRatioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResolutionRatioViewHolder holder, final int position) {
        String resolutionName = mResolutionRatioList.get(position);
        holder.mTvResolutionRatioName.setText(resolutionName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnResolutionRatioItemListener != null){
                    mOnResolutionRatioItemListener.onResolutionRatioItemListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResolutionRatioList != null ? mResolutionRatioList.size() : 0;
    }

    class ResolutionRatioViewHolder extends RecyclerView.ViewHolder{

        TextView mTvResolutionRatioName;
        ResolutionRatioViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvResolutionRatioName = itemView.findViewById(R.id.tv_resolution_ratio_name);
        }
    }
}
