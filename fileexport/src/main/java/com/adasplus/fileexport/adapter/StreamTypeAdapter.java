package com.adasplus.fileexport.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.fileexport.R;
import com.adasplus.fileexport.mvp.contract.OnItemStreamTypeClickListener;
import com.adasplus.fileexport.mvp.model.StreamTypeModel;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/10/14 14:59
 * Description :
 */
public class StreamTypeAdapter extends RecyclerView.Adapter<StreamTypeAdapter.StreamTypeViewHolder> {

    private List<StreamTypeModel> mStreamTypeList;
    private OnItemStreamTypeClickListener mOnStreamTypeClickListener;

    public void setData(List<StreamTypeModel> streamTypeList) {
        mStreamTypeList = streamTypeList;
    }

    public void setOnStreamTypeClickListener(OnItemStreamTypeClickListener onStreamTypeClickListener){
        mOnStreamTypeClickListener = onStreamTypeClickListener;
    }

    @NonNull
    @Override
    public StreamTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_text, parent, false);
        return new StreamTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StreamTypeViewHolder holder, final int position) {
        StreamTypeModel streamTypeModel = mStreamTypeList.get(position);
        String streamType = streamTypeModel.getStreamType();
        holder.mTvShowText.setText(streamType);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnStreamTypeClickListener != null){
                    mOnStreamTypeClickListener.streamTypeClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStreamTypeList != null ? mStreamTypeList.size() : 0;
    }

    class StreamTypeViewHolder extends RecyclerView.ViewHolder {

        TextView mTvShowText;

        StreamTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvShowText = itemView.findViewById(R.id.tv_show_text);
        }
    }
}
