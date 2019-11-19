package com.adasplus.homepager.params.mvp.presenter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
public class ParamsSetPresenter implements IParamsSetContract.Presenter, View.OnClickListener {

    private IParamsSetContract.View mParamsSetView;
    private ParamsSetActivity mParamsSetActivity;
    private EditText mEtCameraHeight;
    private EditText mEtBumperDistance;
    private EditText mEtLeftWheelDistance;
    private EditText mEtRightWheelDistance;
    //    private EditText mEtFrontWheelDistance;
    private ParamsSetModel mParamsSetModel;
    private BasicDialog mDialog;
    private TextView mTvRestoreTheDefaultParams;

    public ParamsSetPresenter(IParamsSetContract.View view) {
        mParamsSetView = view;
        mParamsSetActivity = (ParamsSetActivity) view;
    }

    @Override
    public void initWidget() {
        mEtCameraHeight = mParamsSetView.getEtCameraHeight();
        mEtBumperDistance = mParamsSetView.getEtBumperDistance();
        mEtLeftWheelDistance = mParamsSetView.getEtLeftWheelDistance();
        mEtRightWheelDistance = mParamsSetView.getEtRightWheelDistance();
//        mEtFrontWheelDistance = mParamsSetView.getEtFrontWheelDistance();
        mTvRestoreTheDefaultParams = mParamsSetView.getTvRestoreTheDefaultParams();
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
            }

            @Override
            public void onNext(ParamsSetModel paramsSetModel) {
                mParamsSetModel = paramsSetModel;
                float cameraHight = (float) paramsSetModel.getAdasCameraHight() / 1000;
                float bumperDistance = (float) paramsSetModel.getBumperDistance() / 1000;
                float leftWheelDistance = (float) paramsSetModel.getLeftWheelDistance() / 1000;
                float rightWheelDistance = (float) paramsSetModel.getRightWheelDistance() / 1000;
//                float frontWheelDistance = (float) paramsSetModel.getFrontWheelDistance() / 1000;

                String camera = String.format("%.2f", cameraHight);
                String bumper = String.format("%.2f", bumperDistance);
                String leftWheel = String.format("%.2f", leftWheelDistance);
                String rightWheel = String.format("%.2f", rightWheelDistance);
//                String frontWheel = String.format("%.2f", frontWheelDistance);

                //设置镜头高度
                mEtBumperDistance.setText(camera);
                mEtBumperDistance.setSelection(camera.length());
                //设置保险杠距离
                mEtBumperDistance.setText(bumper);
                mEtBumperDistance.setSelection(bumper.length());
                //设置左车轮距离
                mEtLeftWheelDistance.setText(leftWheel);
                mEtLeftWheelDistance.setSelection(leftWheel.length());
                //设置右车轮距离
                mEtRightWheelDistance.setText(rightWheel);
                mEtRightWheelDistance.setSelection(rightWheel.length());
//                //设置前车轮距离
//                mEtFrontWheelDistance.setText(frontWheel);
//                mEtFrontWheelDistance.setSelection(frontWheel.length());
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
        mEtCameraHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) return;
                float distanceFloat;
                if (s.toString().trim().equals(".")) {
                    distanceFloat = Float.valueOf("0.");
                    mEtCameraHeight.setText("0.");
                    mEtCameraHeight.setSelection(mEtCameraHeight.getText().length());
                } else distanceFloat = Float.valueOf(s.toString());
//                int distanceInt = (int) (distanceFloat * 100);
//                if (distanceInt > 250) {
//                    mParamsSetActivity.showToast("最大距离2.5米（m）~");
//                    mEtCameraHeight.setText(String.format("%.2f", 2.5));
//                }
            }
        });
        mEtBumperDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) return;
                float distanceFloat;
                if (s.toString().trim().equals(".")) {
                    distanceFloat = Float.valueOf("0.");
                    mEtCameraHeight.setText("0.");
                    mEtCameraHeight.setSelection(mEtCameraHeight.getText().length());
                } else distanceFloat = Float.valueOf(s.toString());
//                int distanceInt = (int) (distanceFloat * 100);
//                if (distanceInt > 250) {
//                    mParamsSetActivity.showToast("最大距离2.5米（m）~");
//                    mEtBumperDistance.setText(String.format("%.2f", 2.5));
//                }
            }
        });
        mEtLeftWheelDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) return;
                float distanceFloat;
                if (s.toString().trim().equals(".")) {
                    distanceFloat = Float.valueOf("0.");
                    mEtCameraHeight.setText("0.");
                    mEtCameraHeight.setSelection(mEtCameraHeight.getText().length());
                } else distanceFloat = Float.valueOf(s.toString());
//                int distanceInt = (int) (distanceFloat * 100);
//                if (distanceInt > 250) {
//                    mParamsSetActivity.showToast("最大距离2.5米（m）~");
//                    mEtLeftWheelDistance.setText(String.format("%.2f", 2.5));
//                }
            }
        });
        mEtRightWheelDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) return;
                float distanceFloat;
                if (s.toString().trim().equals(".")) {
                    distanceFloat = Float.valueOf("0.");
                    mEtCameraHeight.setText("0.");
                    mEtCameraHeight.setSelection(mEtCameraHeight.getText().length());
                } else distanceFloat = Float.valueOf(s.toString());
//                int distanceInt = (int) (distanceFloat * 100);
//                if (distanceInt > 250) {
//                    mParamsSetActivity.showToast("最大距离2.5米（m）~");
//                    mEtRightWheelDistance.setText(String.format("%.2f", 2.5));
//                }
            }
        });
//        mEtFrontWheelDistance.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (TextUtils.isEmpty(s)) return;
//                float distanceFloat = Float.valueOf(s.toString());
//                int distanceInt = (int) (distanceFloat * 100);
//                if (distanceInt > 250) {
//                    mParamsSetActivity.showToast("最大距离2.5米（m）~");
//                    mEtFrontWheelDistance.setText(String.format("%.2f", 2.5));
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String cameraHeightStr = mEtCameraHeight.getText().toString();
        String bumperDistanceStr = mEtBumperDistance.getText().toString();
        String leftWheelDistanceStr = mEtLeftWheelDistance.getText().toString();
        String rightWheelDistanceStr = mEtRightWheelDistance.getText().toString();
//        String frontWheelDistanceStr = mEtFrontWheelDistance.getText().toString();

        //获取 保险杠距离、左车轮距离、右车轮距离和前车轮距离
        int cameraHeight = (int) (TextUtils.isEmpty(cameraHeightStr) ? 0 : Float.parseFloat(cameraHeightStr) * 1000);
        int bumperDistance = (int) (TextUtils.isEmpty(bumperDistanceStr) ? 0 : Float.parseFloat(bumperDistanceStr) * 1000);
        int leftWheelDistance = (int) (TextUtils.isEmpty(leftWheelDistanceStr) ? 0 : Float.parseFloat(leftWheelDistanceStr) * 1000);
        int rightWheelDistance = (int) (TextUtils.isEmpty(rightWheelDistanceStr) ? 0 : Float.parseFloat(rightWheelDistanceStr) * 1000);
//        int frontWheelDistance = (int)(TextUtils.isEmpty(frontWheelDistanceStr) ? 0 :  Float.parseFloat(frontWheelDistanceStr) * 1000);


        if (id == R.id.iv_back) {
            //点击返回的时候，进行来判断当前更改的数据是否进行保存。如果是保存了，我们直接进行退出
            // 当前界面，否则，我们进行来弹出对话框来提示用户，用户是否进行保存当前更改的数据。如果
            // 点击是，我们进行保存完成后在退出当前界面，否则我们进行直接退出当前的界面
            if (mParamsSetModel != null) {
                if (mParamsSetModel.getAdasCameraHight() == cameraHeight &&
                        mParamsSetModel.getBumperDistance() == bumperDistance &&
                        mParamsSetModel.getLeftWheelDistance() == leftWheelDistance &&
                        mParamsSetModel.getRightWheelDistance() == rightWheelDistance) {
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
            mParamsSetModel.setAdasCameraHight(cameraHeight);
            mParamsSetModel.setBumperDistance(bumperDistance);
            mParamsSetModel.setLeftWheelDistance(leftWheelDistance);
            mParamsSetModel.setRightWheelDistance(rightWheelDistance);
//            mParamsSetModel.setFrontWheelDistance(frontWheelDistance);
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
                        mParamsSetActivity.showToast(R.string.save_params_write_success);
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
                        mParamsSetActivity.showToast(R.string.restore_default_params_set_success);
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
}
