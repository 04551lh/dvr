package com.adasplus.homepager.activate.mvp.presenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.PatternUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.activate.activity.AddNewPlatformsActivity;
import com.adasplus.homepager.activate.mvp.contract.IAddNewPlatformsContract;
import com.adasplus.homepager.activate.mvp.model.ActivateNewPlatformsModel;
import com.adasplus.homepager.network.HomeWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/10/11 16:26
 * Description :
 */
public class AddNewPlatformsPresenter implements IAddNewPlatformsContract.Presenter, View.OnClickListener {

    private IAddNewPlatformsContract.View mAddNewPlatformsView;
    private AddNewPlatformsActivity mAddNewPlatformsActivity;
    private EditText mEtPlatformIpAddress;
    private EditText mEtPlatformPort;

    public AddNewPlatformsPresenter(IAddNewPlatformsContract.View view){
        mAddNewPlatformsView = view;
        mAddNewPlatformsActivity = (AddNewPlatformsActivity) view;
    }

    @Override
    public void initData() {
        mEtPlatformIpAddress = mAddNewPlatformsView.getEtPlatformIpAddress();
        mEtPlatformPort = mAddNewPlatformsView.getEtPlatformPort();
    }

    @Override
    public void initListener() {
        ImageView ivBack = mAddNewPlatformsView.getIvBack();
        TextView tvSave = mAddNewPlatformsView.getTvSave();

        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            mAddNewPlatformsActivity.finish();
        }else if (id == R.id.tv_save){
             String ipAddress = mEtPlatformIpAddress.getText().toString().trim();
             String port = mEtPlatformPort.getText().toString().trim();
            if (TextUtils.isEmpty(ipAddress)) {
                Toast.makeText(mAddNewPlatformsActivity, R.string.ip_address_is_not_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            if (isIpAddress(ipAddress)) {
                if (!PatternUtils.checkIpAddress(ipAddress)) {
                    Toast.makeText(mAddNewPlatformsActivity, R.string.input_ip_address_format_error, Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (TextUtils.isEmpty(port)){
                Toast.makeText(mAddNewPlatformsActivity, R.string.port_is_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            JSONArray jarr = new JSONArray();
            JSONObject jobj = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            try {
                jobj.put("platformIp", ipAddress);
                jobj.put("platformPort", Short.valueOf(port));
                jarr.put(jobj);
                jsonObject.put("Array",jarr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            HomeWrapper.getInstance().activateNewPlatforms(jsonObject).subscribe(new Subscriber<ActivateNewPlatformsModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ExceptionUtils.exceptionHandling(mAddNewPlatformsActivity,e);
                }

                @Override
                public void onNext(ActivateNewPlatformsModel activateNewPlatformsModel) {
                    Toast.makeText(mAddNewPlatformsActivity, "数据保存成功", Toast.LENGTH_SHORT).show();
                    mAddNewPlatformsActivity.finish();
                }
            });
        }
    }

    /**
     * 由于输入的 部标平台IP 有可能是ip 或 域名，进行检验的输入的是否是IP地址，如果是IP地址，检查IP 地址
     * 输入的是否合法，不是IP 的话直接进行目前不做校验
     *
     * @param ipAddress
     * @return
     */
    private boolean isIpAddress(String ipAddress) {
        while (ipAddress.startsWith(" ")) {
            ipAddress = ipAddress.substring(1).trim();
        }

        while (ipAddress.endsWith(" ")) {
            ipAddress = ipAddress.substring(0, ipAddress.length() - 1).trim();
        }

        boolean isIp = false;
        if (ipAddress.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] split = ipAddress.split("\\.");
            if (Integer.parseInt(split[0]) < 255) {
                if (Integer.parseInt(split[1]) < 255) {
                    if (Integer.parseInt(split[2]) < 255) {
                        if (Integer.parseInt(split[3]) < 255) {
                            isIp = true;
                        }
                    }
                }
            }
        }
        return isIp;
    }
}
