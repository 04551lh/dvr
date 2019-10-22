package com.adasplus.homepager.set.mvp.presenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adasplus.base.dialog.BasicDialog;
import com.adasplus.base.dialog.CommonDialog;
import com.adasplus.base.dialog.ViewConvertListener;
import com.adasplus.base.dialog.ViewHolder;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.CommonSetActivity;
import com.adasplus.homepager.set.activity.DeviceFormatActivity;
import com.adasplus.homepager.set.activity.DormancySetActivity;
import com.adasplus.homepager.set.activity.SoundsActivity;
import com.adasplus.homepager.set.activity.TimeSetActivity;
import com.adasplus.homepager.set.mvp.contract.ICommonSetContract;
import com.adasplus.homepager.set.mvp.model.ResetFactoryModel;
import com.adasplus.homepager.set.mvp.model.RestartDeviceModel;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 14:53
 * Description :
 */
public class CommonSetPresenter implements ICommonSetContract.Presenter, View.OnClickListener {

    private ICommonSetContract.View mCommonSetView;
    private CommonSetActivity mCommonSetActivity;

    public CommonSetPresenter(ICommonSetContract.View view){
        mCommonSetView = view;
        mCommonSetActivity = (CommonSetActivity) view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        ImageView ivBack = mCommonSetView.getIvBack();
        LinearLayout llTimeSet = mCommonSetView.getLlTimeSet();
        LinearLayout llSoundSet = mCommonSetView.getLlSoundSet();
        LinearLayout llDormancySet = mCommonSetView.getLlDormancySet();
        LinearLayout llRebootSet = mCommonSetView.getLlRebootSet();
        LinearLayout llFactoryDataReset = mCommonSetView.getLlFactoryDataReset();
        LinearLayout llDeviceFormat = mCommonSetView.getLlDeviceFormat();

        ivBack.setOnClickListener(this);
        llTimeSet.setOnClickListener(this);
        llSoundSet.setOnClickListener(this);
        llDormancySet.setOnClickListener(this);
        llRebootSet.setOnClickListener(this);
        llFactoryDataReset.setOnClickListener(this);
        llDeviceFormat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            mCommonSetActivity.finish();
        }else if (id ==  R.id.ll_time_set){ //时间设置
            mCommonSetActivity.startActivity(new Intent(mCommonSetActivity, TimeSetActivity.class));
        }else if (id == R.id.ll_sound_set){ //声音设置
            mCommonSetActivity.startActivity(new Intent(mCommonSetActivity, SoundsActivity.class));
        }else if (id == R.id.ll_dormancy_set){ //休眠设置
            mCommonSetActivity.startActivity(new Intent(mCommonSetActivity, DormancySetActivity.class));
        }else if (id == R.id.ll_reboot_set){ //重启设置
            rebootDevice();
        }else if (id == R.id.ll_factory_data_reset){ // 恢复出厂设置
            resetFactoryData();
        }else if (id == R.id.ll_device_format){ //设备格式化设置
            mCommonSetActivity.startActivity(new Intent(mCommonSetActivity, DeviceFormatActivity.class));
        }
    }

    /**
     * 恢复出厂设置
     */
    private void resetFactoryData() {
        float margin = mCommonSetActivity.getResources().getDimension(R.dimen.dp_20);
        CommonDialog.init()
                .setLayoutId(R.layout.dialog_factory_data_reset)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, final BasicDialog dialog) {
                        holder.setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        holder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startResetFactory();
                                dialog.dismiss();
                            }
                        });
                    }
                }).setMargin(margin)
                .setOutCancel(false)
                .setAnimStyle(R.style.BottomAnimStyle)
                .setDimAmount(0.8f)
                .show(mCommonSetActivity.getSupportFragmentManager());
    }

    private void startResetFactory() {
        HomeWrapper.getInstance().resetFactory().subscribe(new Subscriber<ResetFactoryModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mCommonSetActivity,e);
            }

            @Override
            public void onNext(ResetFactoryModel resetFactoryModel) {
                Toast.makeText(mCommonSetActivity, "恢复出厂设置成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 重启设备
     */
    private void rebootDevice() {
        float margin = mCommonSetActivity.getResources().getDimension(R.dimen.dp_20);
        CommonDialog.init()
                .setLayoutId(R.layout.dialog_reboot_device)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, final BasicDialog dialog) {
                        holder.setOnClickListener(R.id.tv_cancel_reboot, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        holder.setOnClickListener(R.id.tv_confirm_reboot, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startRestartDevice();
                                dialog.dismiss();

                            }
                        });
                    }
                })
                .setMargin(margin)
                .setOutCancel(false)
                .setDimAmount(0.8f)
                .setAnimStyle(R.style.BottomAnimStyle)
                .show(mCommonSetActivity.getSupportFragmentManager());

    }

    private void startRestartDevice() {
        HomeWrapper.getInstance().restartDevice().subscribe(new Subscriber<RestartDeviceModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mCommonSetActivity,e);
            }

            @Override
            public void onNext(RestartDeviceModel restartDeviceModel) {
                Toast.makeText(mCommonSetActivity, "重启设备成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
