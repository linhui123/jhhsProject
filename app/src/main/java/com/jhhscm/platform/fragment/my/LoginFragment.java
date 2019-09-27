package com.jhhscm.platform.fragment.my;


import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.h5.H5Activity;
import com.jhhscm.platform.bean.LogingResultBean;
import com.jhhscm.platform.databinding.FragmentLoginBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.action.GetCodeAction;
import com.jhhscm.platform.http.action.GetUserAction;
import com.jhhscm.platform.http.action.LoginAction;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.UrlUtils;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class LoginFragment extends AbsFragment<FragmentLoginBinding> {

    public static LoginFragment instance() {
        LoginFragment view = new LoginFragment();
        return view;
    }

    @Override
    protected FragmentLoginBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLoginBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        mDataBinding.tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDataBinding.tvCode.getText().toString().contains("秒")) {
                    if (mDataBinding.etUser.getText().toString().length() > 0) {
                        getCode(mDataBinding.etUser.getText().toString());
                        mDataBinding.etCode.setFocusable(true);
                        mDataBinding.etCode.setFocusableInTouchMode(true);
                        mDataBinding.etCode.requestFocus();
                        startCountDown();
                    } else {
                        ToastUtils.show(getContext(), "手机号不能为空");
                    }
                }

            }
        });

        mDataBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.etUser.getText().toString().length() > 0
                        && mDataBinding.etCode.getText().toString().length() > 0) {
                    login(mDataBinding.etUser.getText().toString(), mDataBinding.etCode.getText().toString());
                } else {
                    ToastUtils.show(getContext(), "输入不能为空");
                }
            }
        });

        mDataBinding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mDataBinding.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H5Activity.start(getContext(), UrlUtils.FWXY, "服务协议");
            }
        });
    }

    private void getCode(String tel) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("mobile", tel);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "getCode");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetCodeAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    String jsonData = Des.decyptByDes(response.body().getContent());
                                    Gson gson = new Gson();
                                    LogingResultBean logingResultBean = new LogingResultBean();
                                    logingResultBean = gson.fromJson(jsonData, LogingResultBean.class);
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void login(final String tel, String code) {
        showDialog();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("secret", "27f7c720e0f440ce877e69573781d8ea");
        map.put("mobile", tel.trim());
        map.put("veriCode", code.trim());
        map.put("appid", "336abf9e97cd4276bf8aecde9d32ed99");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.LoginSign(getContext(), tel, code);

        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        netBean.setAppid("336abf9e97cd4276bf8aecde9d32ed99");
        onNewRequestCall(LoginAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    LogingResultBean logingResultBean = new LogingResultBean();
                                    String jsonData = Des.decyptByDes(response.body().getContent());
                                    Gson gson = new Gson();
                                    logingResultBean = gson.fromJson(jsonData, LogingResultBean.class);
                                    getUser(tel, logingResultBean);
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void getUser(final String tel, final LogingResultBean logingResultBean) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("mobile", tel);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "getUser");
        NetBean netBean = new NetBean();
        netBean.setToken(logingResultBean.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetUserAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<UserBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<UserBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    UserBean userBean = response.body().getData();

                                    UserSession userSession = new UserSession();
                                    userSession.setToken(logingResultBean.getToken());
                                    userSession.setExpire(logingResultBean.getExpire());
                                    userSession.setAvatar(userBean.getData().getAvatar());
                                    userSession.setMobile(userBean.getData().getMobile());
                                    userSession.setTimestamp(userBean.getTimestamp());
                                    userSession.setUserCode(userBean.getData().getUserCode());
                                    userSession.setStatus(userBean.getData().getStatus() + "");
                                    userSession.setNickname(userBean.getData().getNickname());
                                    userSession.setIs_check(userBean.getData().getIs_check());
                                    ConfigUtils.setCurrentUser(getContext(), userSession);

                                    getActivity().finish();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void startCountDown() {
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onTick(long millisUntilFinished) {
                if (getView() != null) {
                    mDataBinding.tvCode.setTextColor(getResources().getColor(R.color.acc6));
                    mDataBinding.tvCode.setText(getString(R.string.login_already_send_code, millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                if (getView() != null) {
                    mDataBinding.tvCode.setTextColor(getResources().getColor(R.color.a397));
                    mDataBinding.tvCode.setText(getString(R.string.login_send_code_again));
                    mDataBinding.tvCode.setEnabled(true);
                }
            }
        };
        countDownTimer.start();
    }
}
