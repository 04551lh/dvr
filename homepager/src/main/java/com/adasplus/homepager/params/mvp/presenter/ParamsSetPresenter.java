package com.adasplus.homepager.params.mvp.presenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adasplus.base.dialog.BasicDialog;
import com.adasplus.base.dialog.CommonDialog;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.params.activity.ParamsSetActivity;
import com.adasplus.homepager.params.mvp.contract.IParamsSetContract;
import com.adasplus.homepager.params.mvp.model.ParamsSetModel;
import com.adasplus.homepager.params.mvp.model.RestoreDefaultParamsSetModel;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/29 18:46
 * Description :
 */
public class ParamsSetPresenter implements IParamsSetContract.Presenter, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private IParamsSetContract.View mParamsSetView;
    private ParamsSetActivity mParamsSetActivity;
    private EditText mEtBumperDistance;
    private EditText mEtLeftWheelDistance;
    private EditText mEtRightWheelDistance;
    private EditText mEtFrontWheelDistance;
    private ParamsSetModel mParamsSetModel;
    private BasicDialog mDialog;
    private TextView mTvRestoreTheDefaultParams;
    private SwipeRefreshLayout mSrlRefreshParamsFill;

    public ParamsSetPresenter(IParamsSetContract.View view) {
        mParamsSetView = view;
        mParamsSetActivity = (ParamsSetActivity) view;
    }

    @Override
    public void initWidget() {
        mEtBumperDistance = mParamsSetView.getEtBumperDistance();
        mEtLeftWheelDistance = mParamsSetView.getEtLeftWheelDistance();
        mEtRightWheelDistance = mParamsSetView.getEtRightWheelDistance();
        mEtFrontWheelDistance = mParamsSetView.getEtFrontWheelDistance();
        mTvRestoreTheDefaultParams = mParamsSetView.getTvRestoreTheDefaultParams();
        mSrlRefreshParamsFill = mParamsSetView.getSrlRefreshParamsFill();
        mSrlRefreshParamsFill.setOnRefreshListener(this);
        mSrlRefreshParamsFill.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSrlRefreshParamsFill.setProgressBackgroundColorSchemeResource(android.R.color.white);

    }

    @Override
    public void initData() {
        //获取部标设备中的参数填写的数据
        HomeWrapper.getInstance().getParamsSet().subscribe(new Subscriber<ParamsSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mParamsSetActivity, e);
                mSrlRefreshParamsFill.setRefreshing(false);
            }

            @Override
            public void onNext(ParamsSetModel paramsSetModel) {
                mSrlRefreshParamsFill.setRefreshing(false);
                mParamsSetModel = paramsSetModel;
                int bumperDistance = paramsSetModel.getBumperDistance();
                int leftWheelDistance = paramsSetModel.getLeftWheelDistance();
                int rightWheelDistance = paramsSetModel.getRightWheelDistance();
                int frontWheelDistance = paramsSetModel.getFrontWheelDistance();

                String bumper = String.valueOf(bumperDistance);
                String leftWheel = String.valueOf(leftWheelDistance);
                String rightWheel = String.valueOf(rightWheelDistance);
                String frontWheel = String.valueOf(frontWheelDistance);

                //设置保险杠距离
                mEtBumperDistance.setText(bumper);
                mEtBumperDistance.setSelection(bumper.length());
                //设置左车轮距离
                mEtLeftWheelDistance.setText(leftWheel);
                mEtLeftWheelDistance.setSelection(leftWheel.length());
                //设置右车轮距离
                mEtRightWheelDistance.setText(rightWheel);
                mEtRightWheelDistance.setSelection(rightWheel.length());
                //设置前车轮距离
                mEtFrontWheelDistance.setText(frontWheel);
                mEtFrontWheelDistance.setSelection(frontWheel.length());
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mParamsSetView.getIvBack();
        TextView tvSaveParamsSetInfo = mParamsSetView.getTvSaveParamsSet();
        ivBack.setOnClickListener(this);
        tvSaveParamsSetInfo.setOnClickListener(this);
        mTvRestoreTheDefaultParams.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String bumperDistanceStr = mEtBumperDistance.getText().toString();
        String leftWheelDistanceStr = mEtLeftWheelDistance.getText().toString();
        String rightWheelDistanceStr = mEtRightWheelDistance.getText().toString();
        String frontWheelDistanceStr = mEtFrontWheelDistance.getText().toString();

        //获取 保险杠距离、左车轮距离、右车轮距离和前车轮距离
        int bumperDistance = TextUtils.isEmpty(bumperDistanceStr) ? 0 : Integer.parseInt(bumperDistanceStr);
        int leftWheelDistance = TextUtils.isEmpty(leftWheelDistanceStr) ? 0 : Integer.parseInt(leftWheelDistanceStr);
        int rightWheelDistance = TextUtils.isEmpty(rightWheelDistanceStr) ? 0 : Integer.parseInt(rightWheelDistanceStr);
        int frontWheelDistance = TextUtils.isEmpty(frontWheelDistanceStr) ? 0 : Integer.parseInt(frontWheelDistanceStr);

        if (id == R.id.iv_back) {
            //点击返回的时候，进行来判断当前更改的数据是否进行保存。如果是保存了，我们直接进行退出
            // 当前界面，否则，我们进行来弹出对话框来提示用户，用户是否进行保存当前更改的数据。如果
            // 点击是，我们进行保存完成后在退出当前界面，否则我们进行直接退出当前的界面
            if (mParamsSetModel != null) {
                if (mParamsSetModel.getBumperDistance() == bumperDistance &&
                        mParamsSetModel.getLeftWheelDistance() == leftWheelDistance &&
                        mParamsSetModel.getRightWheelDistance() == rightWheelDistance &&
                        mParamsSetModel.getFrontWheelDistance() == frontWheelDistance) {
                    mParamsSetActivity.finish();
                } else {
                    showSaveParamsWriteDialog();
                }
            }
        } else if (id == R.id.tv_restore_the_default_params) {
            showDefaultParamsDialog();
        } else if (id == R.id.tv_cancel) {
            if (mDialog != null && mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mParamsSetActivity.finish();
        } else if (id == R.id.tv_save_params_set_info || id == R.id.tv_confirm) {
            if (mParamsSetModel == null) {
                mParamsSetModel = new ParamsSetModel();
            }

            if (mDialog != null && mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mParamsSetModel.setBumperDistance(bumperDistance);
            mParamsSetModel.setLeftWheelDistance(leftWheelDistance);
            mParamsSetModel.setRightWheelDistance(rightWheelDistance);
            mParamsSetModel.setFrontWheelDistance(frontWheelDistance);
            String json = GsonUtils.getInstance().toJson(mParamsSetModel);
            try {
                JSONObject jsonObject = new JSONObject(json);
                //更改参数填写
                HomeWrapper.getInstance().updateParamsSet(jsonObject).subscribe(new Subscriber<ParamsSetModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionUtils.exceptionHandling(mParamsSetActivity, e);
                    }

                    @Override
                    public void onNext(ParamsSetModel paramsSetModel) {
                        Toast.makeText(mParamsSetActivity, R.string.save_params_write_success, Toast.LENGTH_SHORT).show();
                        mParamsSetActivity.finish();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void showDefaultParamsDialog() {
        View view = View.inflate(mParamsSetActivity, R.layout.dialog_common_styles, null);
        TextView tv_dialog_title = view.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_description = view.findViewById(R.id.tv_dialog_description);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);

        String description = mParamsSetActivity.getString(R.string.determine_restore_default_parameters);
        String no = mParamsSetActivity.getString(R.string.no);
        String yes = mParamsSetActivity.getString(R.string.yes);

        float margin = mParamsSetActivity.getResources().getDimension(R.dimen.dp_12);
        int paddingTop = (int) mParamsSetActivity.getResources().getDimension(R.dimen.dp_28);
        int paddingLeft = (int) mParamsSetActivity.getResources().getDimension(R.dimen.dp_19);
        int paddingRight = (int) mParamsSetActivity.getResources().getDimension(R.dimen.dp_19);
        int paddingBottom = (int) mParamsSetActivity.getResources().getDimension(R.dimen.dp_28);

        tv_dialog_title.setVisibility(View.GONE);
        tv_dialog_description.setText(description);
        tv_dialog_description.setTextColor(mParamsSetActivity.getResources().getColor(R.color.font_color_333));
        tv_dialog_description.setPadding(paddingTop, paddingLeft, paddingRight, paddingBottom);

        tv_cancel.setText(no);
        tv_confirm.setText(yes);


        mDialog = CommonDialog.init()
                .setView(view)
                .setMargin(margin)
                .setOutCancel(false)
                .setAnimStyle(R.style.BottomAnimStyle)
                .setDimAmount(0.8f)
                .show(mParamsSetActivity.getSupportFragmentManager());

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null && mDialog.isAdded()) {
                    mDialog.dismiss();
                }
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeWrapper.getInstance().restoreDefaultParamsSet().subscribe(new Subscriber<RestoreDefaultParamsSetModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionUtils.exceptionHandling(mParamsSetActivity, e);
                    }

                    @Override
                    public void onNext(RestoreDefaultParamsSetModel restoreDefaultParamsSetModel) {
                        Toast.makeText(mParamsSetActivity, R.string.restore_default_params_set_success, Toast.LENGTH_SHORT).show();
                        initData();
                        if (mDialog != null && mDialog.isAdded()) {
                            mDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void showSaveParamsWriteDialog() {
        View view = View.inflate(mParamsSetActivity, R.layout.dialog_common_styles, null);
        TextView tv_dialog_title = view.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_description = view.findViewById(R.id.tv_dialog_description);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);

        String description = mParamsSetActivity.getResources().getString(R.string.save_params_write_dialog_description);
        String no = mParamsSetActivity.getResources().getString(R.string.no);
        String yes = mParamsSetActivity.getResources().getString(R.string.yes);

        float margin = mParamsSetActivity.getResources().getDimension(R.dimen.dp_12);
        int paddingTop = (int) mParamsSetActivity.getResources().getDimension(R.dimen.dp_28);
        int paddingLeft = (int) mParamsSetActivity.getResources().getDimension(R.dimen.dp_19);
        int paddingRight = (int) mParamsSetActivity.getResources().getDimension(R.dimen.dp_19);
        int paddingBottom = (int) mParamsSetActivity.getResources().getDimension(R.dimen.dp_28);

        tv_dialog_title.setVisibility(View.GONE);
        tv_dialog_description.setText(description);
        tv_dialog_description.setTextColor(mParamsSetActivity.getResources().getColor(R.color.font_color_333));
        tv_dialog_description.setPadding(paddingTop, paddingLeft, paddingRight, paddingBottom);

        tv_cancel.setText(no);
        tv_confirm.setText(yes);


        mDialog = CommonDialog.init()
                .setView(view)
                .setMargin(margin)
                .setOutCancel(false)
                .setAnimStyle(R.style.BottomAnimStyle)
                .setDimAmount(0.8f)
                .show(mParamsSetActivity.getSupportFragmentManager());

        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        initData();
    }
}
