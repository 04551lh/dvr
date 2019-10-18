package com.adasplus.dvr_controller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.mvp.contract.OnItemChannelNumbersClickListener;
import com.adasplus.dvr_controller.mvp.model.ChannelNumbersModel;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/26 14:03
 * Description :
 */
public class ChannelsNumberAdapter extends RecyclerView.Adapter<ChannelsNumberAdapter.ChannelsNumberViewHolder> {

    private List<ChannelNumbersModel> mChannelNumbersList;
    private OnItemChannelNumbersClickListener mOnItemClickListener;

    public void setData(List<ChannelNumbersModel> channelNumbersList){
        mChannelNumbersList = channelNumbersList;
    }

    public void setOnItemClickListener(OnItemChannelNumbersClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ChannelsNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_text, parent, false);
        return new ChannelsNumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelsNumberViewHolder holder, final int position) {
        String channels = holder.itemView.getContext().getResources().getString(R.string.channels);
        ChannelNumbersModel channelNumbersModel = mChannelNumbersList.get(position);
        int channelNumber = channelNumbersModel.getChannelNumber();
        holder.mTvChannelName.setText(String.format("%s %s",channels,String.valueOf(channelNumber)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onChannelNumbersClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChannelNumbersList != null ? mChannelNumbersList.size() : 0;
    }

    class ChannelsNumberViewHolder extends RecyclerView.ViewHolder {

        TextView mTvChannelName;

        ChannelsNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvChannelName = itemView.findViewById(R.id.tv_show_text);
        }
    }
}
