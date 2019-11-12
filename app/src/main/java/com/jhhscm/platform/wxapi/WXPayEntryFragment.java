package com.jhhscm.platform.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.bean.LogingResultBean;
import com.jhhscm.platform.databinding.FragmentCreateOrderBinding;
import com.jhhscm.platform.databinding.FragmentWxpayEntryBinding;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderFragment;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.fragment.GoodsToCarts.action.FindAddressListAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class WXPayEntryFragment extends AbsFragment<FragmentWxpayEntryBinding> {
    CreateOrderResultBean createOrderResultBean;
    UserSession userSession;
    private String prepayid = "";

    public static WXPayEntryFragment instance() {
        WXPayEntryFragment view = new WXPayEntryFragment();
        return view;
    }

    @Override
    protected FragmentWxpayEntryBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentWxpayEntryBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }
        createOrderResultBean = (CreateOrderResultBean) getArguments().getSerializable("createOrderResultBean");
        if (createOrderResultBean != null && createOrderResultBean.getData().getId() != null) {
            wxPrePay();
        }

        mDataBinding.appayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPay();
            }
        });
    }

    private void toPay() {
        mDataBinding.appayBtn.setEnabled(false);
        try {
            PayReq req = new PayReq();
            if (prepayid != null) {
                String time = System.currentTimeMillis() + "";
                req.appId = "wx43d03d3271a1c5d4";//你的微信appid
                req.partnerId = "1544047311";//商户号
                req.prepayId = prepayid;//预支付交易会话ID
                req.nonceStr = "a462b76e7436e98e0ed6e13c64b4fd3c";//随机字符串
                req.timeStamp = "1564630145";//时间戳
                req.packageValue = "Sign=WXPay";//扩展字段, 这里固定填写Sign = WXPay

                Map<String, String> map = new TreeMap<String, String>();
                map.put("appid", "wx43d03d3271a1c5d4");
                map.put("partnerid", "1544047311");
                map.put("noncestr", "a462b76e7436e98e0ed6e13c64b4fd3c");
                map.put("prepayid", prepayid);
                map.put("timestamp", "1564630145");
                map.put("package", "Sign=WXPay");
                String sign = Sign.getWXSignKey(getContext(), map, "wxPrePay");
                Log.e("toPay", "sign : " + sign);
                req.sign = sign;//签名
            }

            Toast.makeText(getContext(), "开始调起支付", Toast.LENGTH_SHORT).show();
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            ((MyApplication) MyApplication.getInstance()).getApi().sendReq(req);
        } catch (Exception e) {
            Log.e("PAY_GET", "异常：" + e.getMessage());
            Toast.makeText(getContext(), "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mDataBinding.appayBtn.setEnabled(true);
    }

    private void wxPrePay() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("orderCode", createOrderResultBean.getData().getId());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "wxPrePay");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(WxPrePayAction.newInstance(getContext(), netBean)
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
                                    ResultBean resultBean = response.body().getData();
                                    if (resultBean != null && resultBean.getPrepay_id() != null) {
                                        prepayid = resultBean.getPrepay_id();
                                        Log.e("wxPrePay", "prepayid : " + prepayid);
                                    }
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));

    }
}
