package com.adasplus.settings.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.IChannelItemListener;
import com.adasplus.settings.mvp.model.CANChannelsModel;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/26 14:03
 * Description :
 */
public class ChannelsNumberAdapter extends RecyclerView.Adapter<ChannelsNumberAdapter.ChannelsNumberViewHolder> {

    private List<CANChannelsModel.ArrayBean> mArray;
    private IChannelItemListener mChannelItemListener;

    public void setData(List<CANChannelsModel.ArrayBean> array) {
        mArray = array;
    }

    public void setOnItemClickListener(IChannelItemListener listener){
        mChannelItemListener = listener;
    }

    @NonNull
    @Override
    public ChannelsNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channels_numbers, parent, false);
        return new ChannelsNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelsNumberViewHolder holder, final int position) {
        final CANChannelsModel.ArrayBean canChannel = mArray.get(position);
        int channelIndex = canChannel.getChannelIndex();
        String channels = holder.itemView.getContext().getResources().getString(R.string.channels);
        holder.mTvChannelName.setText(String.format("%s %s",channels,String.valueOf(channelIndex)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChannelItemListener != null){
                    mChannelItemListener.onItemListener(position,canChannel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArray != null ? mArray.size() : 0;
    }

    class ChannelsNumberViewHolder extends RecyclerView.ViewHolder {

        TextView mTvChannelName;

        ChannelsNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvChannelName = itemView.findViewById(R.id.tv_channel_name);
        }
    }
}
