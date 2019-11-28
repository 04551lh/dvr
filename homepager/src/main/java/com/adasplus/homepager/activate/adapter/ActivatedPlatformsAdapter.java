package com.adasplus.homepager.activate.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.dialog.BasicDialog;
import com.adasplus.base.dialog.CommonDialog;
import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.HttpConstant;
import com.adasplus.base.network.model.SearchServiceRunStatusModel;
import com.adasplus.base.popup.CommonPopupWindow;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.ThreadPoolUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.activate.activity.ActivateDeviceActivity;
import com.adasplus.homepager.activate.mvp.model.GetPlatformInfoModel;
import com.adasplus.homepager.activate.mvp.model.LogoutPlatformsModel;
import com.adasplus.homepager.activate.mvp.model.UpdateDeviceConnectStatus;
import com.adasplus.homepager.network.HomeWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/20 17:00
 * Description :
 */
public class ActivatedPlatformsAdapter extends RecyclerView.Adapter<ActivatedPlatformsAdapter.ActivatedPlatformsViewHolder> {
    private static final String COLON = " : ";

    private List<GetPlatformInfoModel.ArrayBean> mPlatformInfoArray;
    private ActivateDeviceActivity mActivateDeviceActivity;
    private String mConfirmTheCancellation;
    private String mCancellationTextDescription;
    private String mCancel;
    private String mConfirm;
    private float mMargin;
    private String mDisconnect;
    private String mRetryConnect;
    private String mNoConnected = "#FFAB03";
    private BasicDialog mNetRequestDialog;
    private View mLoadView;
    private TextView mTvSetLoadContent;

    public void setData(List<GetPlatformInfoModel.ArrayBean> platformInfoArray, ActivateDeviceActivity activateDeviceActivity) {
        mPlatformInfoArray = platformInfoArray;
        mActivateDeviceActivity = activateDeviceActivity;
        mConfirmTheCancellation = mActivateDeviceActivity.getResources().getString(R.string.confirm_the_cancellation);
        mCancellationTextDescription = mActivateDeviceActivity.getResources().getString(R.string.cancellation_text_description);
        mCancel = mActivateDeviceActivity.getResources().getString(R.string.cancel);
        mConfirm = mActivateDeviceActivity.getResources().getString(R.string.confirm);
        mMargin = mActivateDeviceActivity.getResources().getDimension(R.dimen.dp_12);
        mDisconnect = mActivateDeviceActivity.getString(R.string.disconnect);
        mRetryConnect = mActivateDeviceActivity.getString(R.string.retry_connect);
    }

    @NonNull
    @Override
    public ActivatedPlatformsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activated_platforms, parent, false);
        return new ActivatedPlatformsViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ActivatedPlatformsViewHolder holder, final int position) {

        String platform_ip_address = mActivateDeviceActivity.getResources().getString(R.string.platform_ip_address);
        String platform_port = mActivateDeviceActivity.getResources().getString(R.string.platform_port);

        holder.mTvPlatformIpAddress.setText(platform_ip_address + COLON);
        holder.mTvPlatformPort.setText(platform_port + COLON);


        final GetPlatformInfoModel.ArrayBean arrayBean = mPlatformInfoArray.get(position);
        String platformIp = arrayBean.getPlatformIp();
        int platformPort = arrayBean.getPlatformPort();
        int connectStatus = arrayBean.getConnectStatus();

        //设置部标平台的IP 地址
        holder.mTvPlatformIpAddressValue.setText(platformIp);
        //设置部标平台的端口号
        holder.mTvPlatformPortValue.setText(platformPort + "");
        //平台列表的更多操作
        holder.mIvPlatformsMoreOperation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                platformMoreOperation(holder.mIvPlatformsMoreOperation, position);
            }
        });

        //终端连接状态
        if (connectStatus == 0) {
            holder.mTvPlatformConnectStatus.setText(R.string.link_disconnection);
            holder.mTvPlatformConnectStatus.setTextColor(Color.RED);
        } else if (connectStatus == 1) {
            holder.mTvPlatformConnectStatus.setText(R.string.not_connected);
            holder.mTvPlatformConnectStatus.setTextColor(Color.parseColor(mNoConnected));
        } else if (connectStatus == 2) {
            holder.mTvPlatformConnectStatus.setText(R.string.connected);
            holder.mTvPlatformConnectStatus.setTextColor(Color.GREEN);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void platformMoreOperation(final ImageView ivPlatformsMoreOperation, final int position) {
        BaseWrapper.getInstance().searchServiceRunStatus().subscribe(new Subscriber<SearchServiceRunStatusModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mActivateDeviceActivity, e);
            }

            @Override
            public void onNext(SearchServiceRunStatusModel searchServiceRunStatusModel) {
                //查询当前 808 服务的运行状态，如果808服务未启动的话，
                // 会获取一些空数据或垃圾数据等，所以进行服务的状态判断
                int jt808ServiceStatus = searchServiceRunStatusModel.getJt808Service();
                if (jt808ServiceStatus == 0) {
                    Toast.makeText(mActivateDeviceActivity, R.string.jt_808_service_status, Toast.LENGTH_SHORT).show();
                    return;
                }

                final GetPlatformInfoModel.ArrayBean arrayBean = mPlatformInfoArray.get(position);
                int connectStatus = arrayBean.getConnectStatus();
                View view = View.inflate(mActivateDeviceActivity, R.layout.item_platforms_more_operation, null);
                TextView tv_logout_platform = view.findViewById(R.id.tv_logout_platform);
                TextView tv_update_connect_status = view.findViewById(R.id.tv_update_connect_status);

                if (connectStatus == 2) {
                    tv_update_connect_status.setText(mDisconnect);
                } else if (connectStatus == 1 || connectStatus == 0) {
                    tv_update_connect_status.setText(mRetryConnect);
                }

                int width = (int) mActivateDeviceActivity.getResources().getDimension(R.dimen.dp_100);
                int height = (int) mActivateDeviceActivity.getResources().getDimension(R.dimen.dp_90);
                final CommonPopupWindow commonPopupWindow = new CommonPopupWindow.Builder(mActivateDeviceActivity)
                        .setWidthAndHeight(width, height)
                        .setOutsideTouchable(true)
                        .setView(view)
                        .create();
                commonPopupWindow.showAsDropDown(ivPlatformsMoreOperation, 0, 20, Gravity.RIGHT);

                mLoadView = View.inflate(mActivateDeviceActivity, com.adasplus.base.R.layout.dialog_load_progress, null);
                mTvSetLoadContent = mLoadView.findViewById(com.adasplus.base.R.id.tv_text_content);
                int wh = (int) mActivateDeviceActivity.getResources().getDimension(com.adasplus.base.R.dimen.dp_65);
                mNetRequestDialog = CommonDialog.init()
                        .setView(mLoadView)
                        .setWidth(wh)
                        .setHeight(wh)
                        .setOutCancel(false);

                tv_logout_platform.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLogoutDialog(arrayBean);
                        commonPopupWindow.dismiss();
                    }
                });

                tv_update_connect_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateDeviceConnectStatus(arrayBean, position);
                        commonPopupWindow.dismiss();
                    }
                });
            }
        });


    }

    private void updateDeviceConnectStatus(final GetPlatformInfoModel.ArrayBean arrayBean, final int position) {
        int connectStatus = arrayBean.getConnectStatus();
        View view = View.inflate(mActivateDeviceActivity, R.layout.dialog_common_styles, null);
        TextView tv_dialog_title = view.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_description = view.findViewById(R.id.tv_dialog_description);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        String disconnect_tips = mActivateDeviceActivity.getString(R.string.disconnect_tips);
        String retry_connect_tips = mActivateDeviceActivity.getString(R.string.retry_connect_tips);

        if (connectStatus == 2) {
            tv_dialog_title.setText(mDisconnect);
            tv_dialog_description.setText(disconnect_tips);
        } else if (connectStatus == 0 || connectStatus == 1) {
            tv_dialog_title.setText(mRetryConnect);
            tv_dialog_description.setText(retry_connect_tips);
        }

        tv_cancel.setText(mCancel);
        tv_confirm.setText(mConfirm);

        final BasicDialog basicDialog = CommonDialog.init()
                .setView(view)
                .setDimAmount(0.8f)
                .setOutCancel(false)
                .setAnimStyle(R.style.BottomAnimStyle)
                .setMargin(mMargin)
                .show(mActivateDeviceActivity.getSupportFragmentManager());

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicDialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int platformPort = arrayBean.getPlatformPort();
                JSONArray jarr = new JSONArray();
                JSONObject jobj = new JSONObject();
                JSONObject jsonObject = new JSONObject();
                try {
                    jobj.put("platformIp", arrayBean.getPlatformIp());
                    jobj.put("platformPort", Short.valueOf(String.valueOf(platformPort)));
                    jobj.put("connectStatus", arrayBean.getConnectStatus() == 2 ? 0 : 1);
                    jarr.put(jobj);
                    jsonObject.put("Array", jarr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HomeWrapper.getInstance().updateDeviceConnectStatus(jsonObject).subscribe(new Subscriber<UpdateDeviceConnectStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionUtils.exceptionHandling(mActivateDeviceActivity, e);
                    }

                    @Override
                    public void onNext(UpdateDeviceConnectStatus updateDeviceConnectStatus) {
                        basicDialog.dismiss();
                        mNetRequestDialog.show(mActivateDeviceActivity.getSupportFragmentManager());
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                /**
                                 *要执行的操作
                                 */
                                HomeWrapper.getInstance().getPlatformInfoModel().subscribe(new Subscriber<GetPlatformInfoModel>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        mActivateDeviceActivity.dismissNetRequestDialog();
                                        ExceptionUtils.exceptionHandling(mActivateDeviceActivity, e);
                                    }

                                    @Override
                                    public void onNext(GetPlatformInfoModel getPlatformInfoModel) {
                                        if (mNetRequestDialog != null && mNetRequestDialog.isAdded())
                                        mNetRequestDialog.dismiss();
                                        mPlatformInfoArray = getPlatformInfoModel.getArray();
                                        mActivateDeviceActivity.notifyPlatformsSizeShow();
                                        arrayBean.setConnectStatus(mPlatformInfoArray.get(position).getConnectStatus());
                                        notifyDataSetChanged();
                                    }
                                });
                            }
                        }, 2000);//2秒后执行Runnable中的run方法


                    }
                });
            }
        });
    }

    private void showLogoutDialog(final GetPlatformInfoModel.ArrayBean arrayBean) {
        View view = View.inflate(mActivateDeviceActivity, R.layout.dialog_common_styles, null);
        TextView tv_dialog_title = view.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_description = view.findViewById(R.id.tv_dialog_description);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);

        tv_dialog_title.setText(mConfirmTheCancellation);
        tv_dialog_description.setText(mCancellationTextDescription);
        tv_cancel.setText(mCancel);
        tv_confirm.setText(mConfirm);

        final BasicDialog basicDialog = CommonDialog.init()
                .setView(view)
                .setDimAmount(0.8f)
                .setOutCancel(false)
                .setAnimStyle(R.style.BottomAnimStyle)
                .setMargin(mMargin)
                .show(mActivateDeviceActivity.getSupportFragmentManager());

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basicDialog.dismiss();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("platformIp", arrayBean.getPlatformIp());
                    jsonObject.put("platformPort", arrayBean.getPlatformPort());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HomeWrapper.getInstance().logoutPlatforms(jsonObject).subscribe(new Subscriber<LogoutPlatformsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mNetRequestDialog != null && mNetRequestDialog.isAdded())
                            mNetRequestDialog.dismiss();
                        ExceptionUtils.exceptionHandling(mActivateDeviceActivity, e);
                    }

                    @Override
                    public void onNext(LogoutPlatformsModel logoutPlatformsModel) {
                        basicDialog.dismiss();
                        mNetRequestDialog.show(mActivateDeviceActivity.getSupportFragmentManager());
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                /**
                                 *要执行的操作
                                 */
                                HomeWrapper.getInstance().getPlatformInfoModel().subscribe(new Subscriber<GetPlatformInfoModel>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        if (mNetRequestDialog != null && mNetRequestDialog.isAdded())
                                            mNetRequestDialog.dismiss();
                                        ExceptionUtils.exceptionHandling(mActivateDeviceActivity, e);
                                    }

                                    @Override
                                    public void onNext(GetPlatformInfoModel getPlatformInfoModel) {
                                        if (mNetRequestDialog != null && mNetRequestDialog.isAdded())
                                            mNetRequestDialog.dismiss();
                                        mPlatformInfoArray = getPlatformInfoModel.getArray();
                                        if (mPlatformInfoArray.size() == 0) {
                                            mActivateDeviceActivity.getLlAddNewPlatform().setEnabled(true);
                                            mActivateDeviceActivity.getLlAddNewPlatform().setVisibility(View.VISIBLE);
                                            mActivateDeviceActivity.getTvPlatformList().setText(String.format("%s",mActivateDeviceActivity.getString(R.string.platform_list)));
                                        }
                                        mPlatformInfoArray.remove(arrayBean);
                                        notifyDataSetChanged();
                                    }
                                });
                            }
                        }, 2000);//3秒后执行Runnable中的run方法


                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlatformInfoArray != null ? mPlatformInfoArray.size() : 0;
    }

    class ActivatedPlatformsViewHolder extends RecyclerView.ViewHolder {

        //        private TextView mTvPlatformCancellation;
        private TextView mTvPlatformIpAddress;
        private TextView mTvPlatformIpAddressValue;
        private TextView mTvPlatformPort;
        private TextView mTvPlatformPortValue;
        private TextView mTvPlatformConnectStatus;
        //        private TextView mTvUpdateConnectStatus;
        private ImageView mIvPlatformsMoreOperation;

        ActivatedPlatformsViewHolder(@NonNull View itemView) {
            super(itemView);
//            mTvPlatformCancellation = itemView.findViewById(R.id.tv_platform_cancellation);
            mTvPlatformIpAddress = itemView.findViewById(R.id.tv_platform_ip_address);
            mTvPlatformIpAddressValue = itemView.findViewById(R.id.tv_platform_ip_address_value);
            mTvPlatformPort = itemView.findViewById(R.id.tv_platform_port);
            mTvPlatformPortValue = itemView.findViewById(R.id.tv_platform_port_value);
            mTvPlatformConnectStatus = itemView.findViewById(R.id.tv_platform_connect_status);
//            mTvUpdateConnectStatus = itemView.findViewById(R.id.tv_update_connect_status);
            mIvPlatformsMoreOperation = itemView.findViewById(R.id.iv_platforms_more_operation);
        }
    }
}
