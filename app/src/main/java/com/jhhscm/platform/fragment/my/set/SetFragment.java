package com.jhhscm.platform.fragment.my.set;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AboutActivity;
import com.jhhscm.platform.activity.FeedbackActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.ReceiveAddressActivity;
import com.jhhscm.platform.databinding.FragmentSetBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.event.LesseeFinishEvent;
import com.jhhscm.platform.event.LoginOutEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.DataCleanManager;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.LoginOutDialog;
import com.jhhscm.platform.views.dialog.OrderSuccessDialog;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class SetFragment extends AbsFragment<FragmentSetBinding> {

    public static SetFragment instance() {
        SetFragment view = new SetFragment();
        return view;
    }

    @Override
    protected FragmentSetBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentSetBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        try {
            showDialog();
            DataCleanManager.getTotalCacheSize(getContext());
            mDataBinding.tvCache.setText(DataCleanManager.getTotalCacheSize(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
            mDataBinding.tvCache.setText("0.0M");
        } finally {
            closeDialog();
        }

        mDataBinding.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.start(getActivity());
            }
        });

        mDataBinding.rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DataCleanManager.clearAllCache(getContext());
                    ConfigUtils.removeCoupon(getContext());
                    ConfigUtils.removePTime(getContext());
                    ConfigUtils.removeUpdataTime(getContext());
                    ConfigUtils.removeNewMechanics(getContext());
                    ConfigUtils.removeUpdataUrl(getContext());
                    mDataBinding.tvCache.setText("0.0M");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        mDataBinding.rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    ReceiveAddressActivity.start(getActivity(), false);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });


        mDataBinding.rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6.0权限处理
                YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
                        .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
                        .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
                        .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "拨打电话"))
                        .setPermissions(Manifest.permission.CALL_PHONE).build(), new AcpListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onGranted() {
                        Uri uriScheme = Uri.parse("tel:" + "0591-83590001");
                        Intent it = new Intent(Intent.ACTION_CALL, uriScheme);
                        getContext().startActivity(it);
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });
            }
        });

        mDataBinding.rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //意见反馈
                FeedbackActivity.start(getContext());
            }
        });

        mDataBinding.rlOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
                    new LoginOutDialog(getContext(), new LoginOutDialog.CallbackListener() {
                        @Override
                        public void clickResult() {
                            loginOut();
                        }
                    }).show();
                } else {
                    ToastUtil.show(getContext(), "请先登录");
                }
            }
        });
    }

    /**
     * 意见反馈
     */
    private void loginOut() {
        if (getContext() != null) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("mobile", ConfigUtils.getCurrentUser(getContext()).getMobile());
            map.put("appid", "336abf9e97cd4276bf8aecde9d32ed99");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "loginOut");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(LoginOutAciton.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        ToastUtil.show(getContext(), "退出成功");
                                        ConfigUtils.removeCurrentUser(getContext());
                                        EventBusUtil.post(new LoginOutEvent());
                                        getActivity().finish();
                                    } else if (response.body().getCode().equals("1003")) {
                                        ToastUtil.show(getContext(), "退出成功");
                                        ConfigUtils.removeCurrentUser(getContext());
                                        EventBusUtil.post(new LoginOutEvent());
                                        getActivity().finish();
                                    } else if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                        ToastUtils.show(getContext(), "网络错误");
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    public void onEvent(FinishEvent event) {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        EventBusUtil.unregisterEvent(this);
        super.onDestroy();
    }
}
