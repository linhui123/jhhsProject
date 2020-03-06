package com.jhhscm.platform.fragment.my.set;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentAboutBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.event.ForceCloseEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.CheckVersionAction;
import com.jhhscm.platform.fragment.my.CheckVersionBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiServiceModule;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.jpush.ExampleUtil;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.UpdateDialog;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class AboutFragment extends AbsFragment<FragmentAboutBinding> {

    public static AboutFragment instance() {
        AboutFragment view = new AboutFragment();
        return view;
    }

    @Override
    protected FragmentAboutBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAboutBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        mDataBinding.version.setText("当前版本号：" + ExampleUtil.GetVersion(getContext()));
        mDataBinding.version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVersion();
            }
        });
        if (BuildConfig.DEBUG) {
            mDataBinding.llTest.setVisibility(View.VISIBLE);
            if (MyApplication.getBaseUrl().length() > 0) {
                mDataBinding.http.setText("当前地址：" + MyApplication.getBaseUrl());
            } else {
                mDataBinding.http.setText("当前地址：" + ApiServiceModule.BASE_URL5);
            }

            mDataBinding.etHttp.setText(ApiServiceModule.BASE_URL3);
            mDataBinding.confirm1.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
                @Override
                public void onClick(View v) {
                    if (mDataBinding.etHttp.getText().toString().length() > 0) {
                        String http = mDataBinding.etHttp.getText().toString();
                        ConfigUtils.setApiUrl(getContext(), http);
//                        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(getContext().getPackageName());
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.putExtra("REBOOT", "reboot");
//                        startActivity(intent);
                        //                        System.exit(0);
//                        EventBusUtil.post(new FinishEvent());
//                        EventBusUtil.post(new ForceCloseEvent());
//                        getActivity().finish();
                        ToastUtil.show(getContext(), "设置成功，请重启应用");
                    } else {
                        ToastUtil.show(getContext(), "请输入完整信息");
                    }
                }
            });

            mDataBinding.confirm2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfigUtils.setApiUrl(getContext(), ApiServiceModule.BASE_URL5);
                    ToastUtil.show(getContext(), "设置成功，请重启应用");
//                    EventBusUtil.post(new FinishEvent());
//                    EventBusUtil.post(new ForceCloseEvent());
//                    getActivity().finish();
//                    Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(getContext().getPackageName());
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("REBOOT", "reboot");
//                    startActivity(intent);
//                    System.exit(0);
                }
            });
        } else {
            mDataBinding.llTest.setVisibility(View.GONE);
        }
    }


    /**
     * 版本更新查询
     */
    private void checkVersion() {
        Map<String, String> map = new TreeMap<String, String>();
        String version = ExampleUtil.GetVersion(getContext()).contains("V")
                ? ExampleUtil.GetVersion(getContext()).replace("V", "")
                : ExampleUtil.GetVersion(getContext());
        Log.e("", "version " + version);
        map.put("app_version", version);
        map.put("app_type", "0");
        map.put("app_platform", StringUtils.getChannel(MyApplication.getInstance()));
        map.put("appid", "336abf9e97cd4276bf8aecde9d32ed99");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "checkVersion");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(CheckVersionAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<CheckVersionBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<CheckVersionBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    CheckVersionBean checkVersionBean = response.body().getData();
                                    updateToast(checkVersionBean);
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 更新提示
     * 相同更新地址；一周提醒一次
     */
    private void updateToast(CheckVersionBean checkVersionBean) {
        if ("0".equals(checkVersionBean.getIs_update())) {//需要更新
            if ("0".equals(checkVersionBean.getIs_must_update())) {//需要强制更新
                final UpdateDialog alertDialog = new UpdateDialog(getContext(),
                        checkVersionBean.getUrl(), new UpdateDialog.CallbackListener() {
                    @Override
                    public void clickYes() {
                    }
                }, true);
                alertDialog.show();
            } else {
                final UpdateDialog alertDialog = new UpdateDialog(getContext(),
                        checkVersionBean.getUrl(), new UpdateDialog.CallbackListener() {
                    @Override
                    public void clickYes() {
                    }
                }, false);
                alertDialog.show();
            }
        } else {
            ConfigUtils.setUpdataUrl(getContext(), "");
            ConfigUtils.setUpdataTime(getContext(), "");
            ToastUtil.show(getContext(), "已经是最新版本");
        }
    }
}
