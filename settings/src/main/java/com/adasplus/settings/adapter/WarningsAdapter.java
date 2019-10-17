package com.adasplus.settings.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.view.SlideSwitchView;
import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.ISwitchItemClickListener;
import com.adasplus.settings.mvp.model.ConvertWarningsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/30 12:13
 * Description : ADAS 或 DMS 报警适配器
 */
public class WarningsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ConvertWarningsModel> mConvertWarningsList;
    private ISwitchItemClickListener mSwitchClickListener;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    private static final int BASE_ITEM_TYPE_HEADER = 0x00001;
    private static final int BASE_ITEM_TYPE_FOOTER = 0x00002;

    public void setData(Context context, List<ConvertWarningsModel> convertWarningsList) {
        mContext = context;
        mConvertWarningsList = convertWarningsList;
    }

    public void setOnSwitchClickListener(ISwitchItemClickListener switchClickListener) {
        mSwitchClickListener = switchClickListener;
    }


    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }


    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getWarningsCount();
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
            return mFooterViews.keyAt(position - getHeadersCount() - getWarningsCount());
        }
        return super.getItemViewType(position - getHeadersCount());
    }

    private int getWarningsCount() {
        return mConvertWarningsList != null ? mConvertWarningsList.size() : 0;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adas_or_dms_list, parent, false);
        return new WarningsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (isHeaderViewPos(position))
            return;
        if (isFooterViewPos(position))
            return;
        if (holder instanceof WarningsViewHolder) {
            final WarningsViewHolder warningsViewHolder = (WarningsViewHolder) holder;
            final ConvertWarningsModel convertWarningsModel = mConvertWarningsList.get(position - getHeadersCount());
            int enable = convertWarningsModel.getEnable();
            int warningNameResId = convertWarningsModel.getWarningNameResId();
            int sensitivityLevel = convertWarningsModel.getSensitivityLevel();
            int lowestSpeed = convertWarningsModel.getLowestSpeed();

            warningsViewHolder.mTvWarningType.setText(warningNameResId);
            String speed = String.valueOf(lowestSpeed);
            warningsViewHolder.mEtErrorNumber.setText(speed);
            warningsViewHolder.mEtErrorNumber.setSelection(speed.length());

            //判断报警的类型 enable 值 1：代表的是打开报警类型的开关， 0 代表的关闭类型的开关
            if (enable == 1) {
                //设置条目显示的字体颜色
                widgetTextColor(warningsViewHolder, Color.BLACK);
                //设置按钮的状态
                widgetStatus(warningsViewHolder, true);
            } else {
                int color = mContext.getResources().getColor(R.color.split_line_color);
                widgetTextColor(warningsViewHolder, color);
                widgetStatus(warningsViewHolder, false);
            }

            // 选择报警 灵敏度的等级 0：低 1：中 2：高
            if (sensitivityLevel == 0) {
                warningsViewHolder.mRbSensitivityLow.setChecked(true);
            } else if (sensitivityLevel == 1) {
                warningsViewHolder.mRbSensitivityMiddle.setChecked(true);
            } else if (sensitivityLevel == 2) {
                warningsViewHolder.mRbSensitivityHigh.setChecked(true);
            }

            //每个条目报警的总报警的开关
            warningsViewHolder.mSsvWarningTypeSwitch.setOnSwitchStatusChangeListener(new SlideSwitchView.OnSwitchStatusChangeListener() {
                @Override
                public void onSwitchStatus(boolean status) {
                    if (mSwitchClickListener != null) {
                        mSwitchClickListener.onSwitchListener(position, convertWarningsModel, status);
                    }
                }
            });

            //选择 低 灵敏度
            warningsViewHolder.mRbSensitivityLow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isCheck = warningsViewHolder.mRbSensitivityLow.isChecked();
                    if (isCheck) {
                        convertWarningsModel.setSensitivityLevel(0);
                    }
                    notifyItemChanged(position);
                }
            });

            //选择 中 灵敏度
            warningsViewHolder.mRbSensitivityMiddle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = warningsViewHolder.mRbSensitivityMiddle.isChecked();
                    if (isChecked) {
                        convertWarningsModel.setSensitivityLevel(1);
                    }
                    notifyItemChanged(position);
                }
            });

            // 选择 高 灵敏度
            warningsViewHolder.mRbSensitivityHigh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = warningsViewHolder.mRbSensitivityHigh.isChecked();
                    if (isChecked) {
                        convertWarningsModel.setSensitivityLevel(2);
                    }
                    notifyItemChanged(position);
                }
            });


            //提高速度
            warningsViewHolder.mBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentSpeed = Integer.valueOf(warningsViewHolder.mEtErrorNumber.getText().toString());
                    if (currentSpeed >= 60) {
                        Toast.makeText(mContext, R.string.warnings_max_speed, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    warningsViewHolder.mEtErrorNumber.setText(String.valueOf(currentSpeed += 1));
                    convertWarningsModel.setLowestSpeed(currentSpeed);
                }
            });

            //降低速度
            warningsViewHolder.mBtnSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentSpeed = Integer.valueOf(warningsViewHolder.mEtErrorNumber.getText().toString());
                    if (currentSpeed <= 10) {
                        Toast.makeText(mContext, R.string.warnings_min_speed, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    warningsViewHolder.mEtErrorNumber.setText(String.valueOf(currentSpeed -= 1));
                    convertWarningsModel.setLowestSpeed(currentSpeed);
                }
            });
        }

    }

    private void widgetTextColor(WarningsViewHolder warningsViewHolder, int color) {
        warningsViewHolder.mTvWarningType.setTextColor(color);
        warningsViewHolder.mTvSensitivity.setTextColor(color);
        warningsViewHolder.mRbSensitivityLow.setTextColor(color);
        warningsViewHolder.mRbSensitivityMiddle.setTextColor(color);
        warningsViewHolder.mRbSensitivityHigh.setTextColor(color);
        warningsViewHolder.mTvMinimumSpeed.setTextColor(color);
    }

    private void widgetStatus(WarningsViewHolder warningsViewHolder, boolean status) {
        warningsViewHolder.mSsvWarningTypeSwitch.setOpen(status);
        warningsViewHolder.mRbSensitivityLow.setEnabled(status);
        warningsViewHolder.mRbSensitivityLow.setClickable(status);
        warningsViewHolder.mRbSensitivityMiddle.setEnabled(status);
        warningsViewHolder.mRbSensitivityMiddle.setClickable(status);
        warningsViewHolder.mRbSensitivityHigh.setEnabled(status);
        warningsViewHolder.mRbSensitivityHigh.setClickable(status);
        warningsViewHolder.mBtnAdd.setEnabled(status);
        warningsViewHolder.mBtnSub.setEnabled(status);
        warningsViewHolder.mBtnAdd.setClickable(status);
        warningsViewHolder.mBtnSub.setClickable(status);
        warningsViewHolder.mEtErrorNumber.setEnabled(status);
        warningsViewHolder.mEtErrorNumber.setClickable(status);
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getWarningsCount() + getFootersCount();
    }

    class WarningsViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvWarningType;
        private SlideSwitchView mSsvWarningTypeSwitch;
        private TextView mTvSensitivity;
        private RadioGroup mRgSensitivity;
        private RadioButton mRbSensitivityLow;
        private RadioButton mRbSensitivityMiddle;
        private RadioButton mRbSensitivityHigh;
        private TextView mTvMinimumSpeed;
        private Button mBtnSub;
        private EditText mEtErrorNumber;
        private Button mBtnAdd;

        WarningsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvWarningType = (TextView) itemView.findViewById(R.id.tv_warning_type);
            mSsvWarningTypeSwitch = (SlideSwitchView) itemView.findViewById(R.id.ssv_warning_type_switch);
            mTvSensitivity = (TextView) itemView.findViewById(R.id.tv_sensitivity);
            mRgSensitivity = (RadioGroup) itemView.findViewById(R.id.rg_sensitivity);
            mRbSensitivityLow = (RadioButton) itemView.findViewById(R.id.rb_sensitivity_low);
            mRbSensitivityMiddle = (RadioButton) itemView.findViewById(R.id.rb_sensitivity_middle);
            mRbSensitivityHigh = (RadioButton) itemView.findViewById(R.id.rb_sensitivity_high);
            mTvMinimumSpeed = (TextView) itemView.findViewById(R.id.tv_minimum_speed);
            mBtnSub = (Button) itemView.findViewById(R.id.btn_sub);
            mEtErrorNumber = (EditText) itemView.findViewById(R.id.et_error_number);
            mBtnAdd = (Button) itemView.findViewById(R.id.btn_add);
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
