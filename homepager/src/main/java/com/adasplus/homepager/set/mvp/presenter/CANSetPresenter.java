package com.adasplus.homepager.set.mvp.presenter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.popup.CommonPopupWindow;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.CANSetActivity;
import com.adasplus.homepager.set.adapter.ChannelsNumberAdapter;
import com.adasplus.homepager.set.mvp.contract.ICANSetContract;
import com.adasplus.homepager.set.mvp.contract.IChannelItemListener;
import com.adasplus.homepager.set.mvp.model.CANChannelsModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 10:23
 * Description :
 */
public class CANSetPresenter implements ICANSetContract.Presenter, View.OnClickListener, IChannelItemListener {

    private ICANSetContract.View mCanSetView;
    private CANSetActivity mCanSetActivity;
    private TextView mTvSelectChannelNumber;
    private List<CANChannelsModel.ArrayBean> mArray;
    private CommonPopupWindow mCommonPopupWindow;
    private ChannelsNumberAdapter mChannelsNumberAdapter;
    private EditText mEtRateValue;
    private String mChannels;

    //通过一个 Map 集合，进行来存储 通道号速率更改的值
    private Map<Integer,String> mEditTextValueMap = new LinkedHashMap<>();
    private int mCurrentChannel = 0;

    public CANSetPresenter(ICANSetContract.View view){
        mCanSetView = view;
        mCanSetActivity = (CANSetActivity) view;
    }


    @Override
    public void initData() {

        mTvSelectChannelNumber = mCanSetView.getTvSelectChannelNumber();
        mEtRateValue = mCanSetView.getEtRateValue();
        mChannels = mCanSetActivity.getResources().getString(R.string.channels);

        mChannelsNumberAdapter = new ChannelsNumberAdapter();

        //获取设备中的通道
        HomeWrapper.getInstance().getCANSet().subscribe(new Subscriber<CANChannelsModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mCanSetActivity,e);
            }

            @Override
            public void onNext(CANChannelsModel canChannelsModel) {
                mArray = canChannelsModel.getArray();
                //TODO 需要接口默认返回所选择的通道号
                if (mArray != null && mArray.size()>0){
                    CANChannelsModel.ArrayBean arrayBean = mArray.get(0);
                    int channelIndex = arrayBean.getChannelIndex();
                    int speed = arrayBean.getSpeed();
                    mTvSelectChannelNumber.setText(mChannels+channelIndex);
                    mEtRateValue.setText(String.valueOf(speed));
                }
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mCanSetView.getIvBack();
        TextView tvSave = mCanSetView.getTvSave();

        ivBack.setOnClickListener(this);
        mTvSelectChannelNumber.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        mChannelsNumberAdapter.setOnItemClickListener(this);

        //监听当前输入的文本变化
        mEtRateValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //存放当前通道速率更改的值，mCurrentChannel 是当前所选择的通道
                mEditTextValueMap.put(mCurrentChannel,s.toString());
            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){ // 返回
            mCanSetActivity.finish();
        }else if (id == R.id.tv_select_channel_number){ //选择点击的通道
            View view = View.inflate(mCanSetActivity, R.layout.popup_common_style, null);
            RecyclerView rv_common_style_list = view.findViewById(R.id.rv_common_style_list);
            rv_common_style_list.setLayoutManager(new LinearLayoutManager(mCanSetActivity,RecyclerView.VERTICAL,false));
            mChannelsNumberAdapter.setData(mArray);

            rv_common_style_list.setAdapter(mChannelsNumberAdapter);
            float dp_150 = mCanSetActivity.getResources().getDimension(R.dimen.dp_150);
            mCommonPopupWindow = new CommonPopupWindow.Builder(mCanSetActivity)
                    .setView(view)
                    .setWidthAndHeight((int) dp_150,LinearLayout.LayoutParams.WRAP_CONTENT)
                    .setOutsideTouchable(true)
                    .create();
            mCommonPopupWindow.showAsDropDown(mTvSelectChannelNumber,0,-15);
        }else if (id == R.id.tv_save){ // 保存 CAN 设置
            Integer rateValues = Integer.valueOf(mEtRateValue.getText().toString());
            if (rateValues < 0 || rateValues > 1024){
                Toast.makeText(mCanSetActivity, "请输入的速率值在0-1024范围内", Toast.LENGTH_SHORT).show();
                return;
            }

            Gson gson = new Gson();
            CANChannelsModel canChannelsModel = new CANChannelsModel();
            if (mArray != null && mArray.size() > 0){
                for (int i = 0 ; i < mArray.size();i++){
                    CANChannelsModel.ArrayBean arrayBean = mArray.get(i);
                    int channelIndex = arrayBean.getChannelIndex();
                    String value = mEditTextValueMap.get(channelIndex);
                    if (!TextUtils.isEmpty(value)){
                        arrayBean.setSpeed(Integer.valueOf(value));
                    }
                    mArray.set(i,arrayBean);
                }
            }
            canChannelsModel.setArray(mArray);
            String s = gson.toJson(canChannelsModel);
            try {
                JSONObject jsonObject = new JSONObject(s);
                HomeWrapper.getInstance().updateCANSet(jsonObject).subscribe(new Subscriber<CANChannelsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionUtils.exceptionHandling(mCanSetActivity,e);
                    }

                    @Override
                    public void onNext(CANChannelsModel canChannelsModel) {
                        Toast.makeText(mCanSetActivity, "CAN 设置保存成功", Toast.LENGTH_SHORT).show();
                        mCanSetActivity.finish();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemListener( int position,  CANChannelsModel.ArrayBean canChannelsModel) {
         int channelIndex = canChannelsModel.getChannelIndex();
        int speed = canChannelsModel.getSpeed();
        mCurrentChannel = channelIndex;
        mTvSelectChannelNumber.setText(mChannels+channelIndex);
        mEtRateValue.setText(String.valueOf(speed));

        if (mCommonPopupWindow != null && mCommonPopupWindow.isShowing()){
            mCommonPopupWindow.dismiss();
        }
    }
}
