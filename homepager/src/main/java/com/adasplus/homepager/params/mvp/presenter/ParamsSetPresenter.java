package com.adasplus.homepager.params.mvp.presenter;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.dialog.BasicDialog;
import com.adasplus.base.dialog.CommonDialog;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.params.activity.ParamsSetActivity;
import com.adasplus.homepager.params.mvp.contract.IParamsSetContract;
import com.adasplus.homepager.params.mvp.model.ParamsSetModel;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/29 18:46
 * Description :
 */
public class ParamsSetPresenter implements IParamsSetContract.Presenter, View.OnClickListener {

    private IParamsSetContract.View mParamsSetView;
    private ParamsSetActivity mParamsSetActivity;
    private EditText mEtBumperDistance;
    private EditText mEtLeftWheelDistance;
    private EditText mEtRightWheelDistance;
    private EditText mEtFrontWheelDistance;
    private ParamsSetModel mParamsSetModel;
    private BasicDialog mDialog;

    public ParamsSetPresenter(IParamsSetContract.View view) {
        mParamsSetView = view;
        mParamsSetActivity = (ParamsSetActivity) view;
    }

    @Override
    public void initData() {

        mEtBumperDistance = mParamsSetView.getEtBumperDistance();
        mEtLeftWheelDistance = mParamsSetView.getEtLeftWheelDistance();
        mEtRightWheelDistance = mParamsSetView.getEtRightWheelDistance();
        mEtFrontWheelDistance = mParamsSetView.getEtFrontWheelDistance();

        //获取部标设备中的参数填写的数据
        HomeWrapper.getInstance().getParamsSet().subscribe(new Subscriber<ParamsSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mParamsSetActivity, e);
            }

            @Override
            public void onNext(ParamsSetModel paramsSetModel) {
                mParamsSetModel = paramsSetModel;
                int bumperDistance = paramsSetModel.getBumperDistance();
                int leftWheelDistance = paramsSetModel.getLeftWheelDistance();
                int rightWheelDistance = paramsSetModel.getRightWheelDistance();
                int frontWheelDistance = paramsSetModel.getFrontWheelDistance();

                //设置保险杠距离
                mEtBumperDistance.setText(String.valueOf(bumperDistance));
                //设置左车轮距离
                mEtLeftWheelDistance.setText(String.valueOf(leftWheelDistance));
                //设置右车轮距离
                mEtRightWheelDistance.setText(String.valueOf(rightWheelDistance));
                //设置前车轮距离
                mEtFrontWheelDistance.setText(String.valueOf(frontWheelDistance));
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mParamsSetView.getIvBack();
        TextView tvSave = mParamsSetView.getTvSave();
        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //获取 保险杠距离、左车轮距离、右车轮距离和前车轮距离
        int bumperDistance = Integer.parseInt(mEtBumperDistance.getText().toString());
        int leftWheelDistance = Integer.parseInt(mEtLeftWheelDistance.getText().toString());
        int rightWheelDistance = Integer.parseInt(mEtRightWheelDistance.getText().toString());
        int frontWheelDistance = Integer.parseInt(mEtFrontWheelDistance.getText().toString());

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
        }else if (id == R.id.tv_cancel){
            if (mDialog != null && mDialog.isAdded()){
                mDialog.dismiss();
            }
            mParamsSetActivity.finish();
        } else if (id == R.id.tv_save || id == R.id.tv_confirm) {
            if (mParamsSetModel != null) {
                if (mDialog != null && mDialog.isAdded()){
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

        float margin = mParamsSetActivity.getResources().getDimension(R.dimen.dp_20);
        int padding = (int) margin;

        tv_dialog_title.setVisibility(View.GONE);
        tv_dialog_description.setText(description);
        tv_dialog_description.setTextColor(Color.BLACK);
        tv_dialog_description.setPadding(padding,padding,padding,padding);
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
}
