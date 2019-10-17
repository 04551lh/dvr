package com.adasplus.settings.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.OnChannelItemClickListener;
import com.adasplus.settings.mvp.model.VideoSetModel;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/10/15 18:20
 * Description :
 */
public class VideoChannelsSetAdapter extends RecyclerView.Adapter<VideoChannelsSetAdapter.VideoChannelsSetViewHolder> {

    private int mChannelTotalCount;
    private OnChannelItemClickListener mOnItemClickListener;

    public void setData(int channelTotalCount){
        mChannelTotalCount = channelTotalCount;
    }

    public void setOnItemClickListener(OnChannelItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public VideoChannelsSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channels_numbers, parent, false);
        return new VideoChannelsSetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoChannelsSetViewHolder holder, final int position) {
        String channels = holder.itemView.getContext().getString(R.string.channels);
        holder.mTvChannelName.setText(String.format("%s %s",channels,String.valueOf((position+1))));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChannelTotalCount;
    }

    class VideoChannelsSetViewHolder extends RecyclerView.ViewHolder{

        TextView mTvChannelName;
        VideoChannelsSetViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvChannelName = itemView.findViewById(R.id.tv_channel_name);
        }
    }
}
