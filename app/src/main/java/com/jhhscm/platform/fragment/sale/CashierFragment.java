package com.jhhscm.platform.fragment.sale;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.aliapi.ALiPayActivity;
import com.jhhscm.platform.aliapi.AliPrePayAction;
import com.jhhscm.platform.aliapi.AliPrePayBean;
import com.jhhscm.platform.aliapi.OrderInfoUtil2_0;
import com.jhhscm.platform.aliapi.PayResult;
import com.jhhscm.platform.bean.LogingResultBean;
import com.jhhscm.platform.databinding.FragmentCashierBinding;
import com.jhhscm.platform.databinding.FragmentWxpayEntryBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.event.WXResultEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
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
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.wxapi.WXPayEntryFragment;
import com.jhhscm.platform.wxapi.WxPrePayAction;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class CashierFragment extends AbsFragment<FragmentCashierBinding> {
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2019070565716752";

    public static final String RSA_PRIVATE = "";
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC8f9Jdvgk2hqxKwAQ7NvL/co4NT7EnqPNXU3lU0Px+2pobdCA3VzY3iJFxxgyagldRtKEkzzz497H/OqNaMrGIxIUhCRve9LEG1ySB8e9Ees87I5ZHVtgTk/7HJXZuHlZyvePSFV72brEROl1AefqrwTewPifLZAcijlGM5CkzJr/KssRpU4uHU1rEIt9DZSWC2KjSOkIky7TrHur1AOaYmgQ0MOPaJ2RT+Qak8dmnqPmGZGHX2kaAbJb1xZnxND4/I16C+a8YOZePGeIR8qYlWIwZIuLyNuZVnpfunpnNvUe3WyGjcD5/kJgZ7OtwrceGpswyYGlY9Fznu87ABS7bAgMBAAECggEBAJe2XQ8b43hiPgtPrlgmar+UaKZoDJJ6FZikU5QjPAWxVbVg6okABV/+5+jlWMGGxFa7hbMFpPJREY71mAIBqQgF+4xSM39n+48g235GxaedHGthwhMa34AqbjXEfiQ1AKkLEnDEyJCZGKRgECvTwSA5u+N6szatBF0YpKQ8ArjqRFlTzuDffV6hru/JZAThyqO9n9VaLS622ELYnLoLQVJMg51mLxvX/swEgx29TjnXwSrm7Ex982qcqxt85KfFTXDsMCHfO3rol7nfmNIK16WDIgNTnKarkrRrduPB+Ljnoms/PiqdeQH9eCDD8zq84efjYal4W08vV4/sgSjg9CECgYEA9RxZimF8S+9KQzHHiUoIHSaLYX5aM0vEZQbqfYX+0CeXDrcvEbl90YGDpNB/8SwsUU55KMxxUT+NG2renEgyZiCugwFTThAwcvYb5vl7F1yAiB7qVkFeFHmdiejCiowJpePz6x0rFGX0CXH5OuZ/Z0tm2KDpBX4i5LGxMPgt0AsCgYEAxN+g9pDPeh11eXdrR7G1mVocmf14iwMAEO4qHH7xGp9Sta++vXd6jlKuxb6kQcxH5qxPxNL22KoWpu0NPovc7bXgzje4yT3ySk+QabcMF2UlsOK/PXLdGAAI3MFhIabGpWEH1nF66s4f/squoZfLVc67coDuTt3AWSEAeO/bTnECgYB4KeLXzX0DOReeI4xgCPQ7Xkccj7YmybFJmf9hdEx8vbv3keS4eshs6mKgbTsb//zmC2OolbnEDDTgSR9DXL2ghcsoHIE7lwI5ieAZ9xraVBYLJaTajodR4GFUV9Dv3UFpm3xcOluBT445BzAZKSEygau07gXvFGyE03w7tp+3CwKBgQDE0D+NXvVbwgmHtd+0266NMS24sFIKvqQZSM8mj60fDTnVUm1f4grOL1BzdYOmF7+llotkW/bUYS2mEQOVjKL/rTyhS8lavafzrBjV5l2bIc4NSJEgsCzhal9xuY0N5DlgaWE5e1cDV69au2rbNvHaxYJVFRmoFD3PnJNpU3gtsQKBgBZp5iiDmrESCeU5R35YobCUa6O2YSnsNSjcACfQHujYQU5KZEKoeMN5i553Db1Bsh7o4+qVNXB1DZR2FLF1+uotlxYSk9baF3OzBJKaUmN7bxn/Vmo3/OAItXvvSuBnxxVGg1+FaTPamy5fOa8/CZPR/3Ao2PQM+2IaOg9bnhzA";

    private static final int ALI_PAY_FLAG = 1;
    private static final int WX_PAY_FLAG = 2;
    private static int type = ALI_PAY_FLAG;
    //微信
    private CreateOrderResultBean createOrderResultBean;
    private UserSession userSession;
    private String prepayid = "";
    private AliPrePayBean aliPrePayBean;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ALI_PAY_FLAG: {
                    findOrder(true);
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    /**
//                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(getContext(), "支付宝支付成功");
//                    } else if (TextUtils.equals(resultStatus, "8000")) {
//                        showAlert(getContext(), "正在处理中");
//                    } else if (TextUtils.equals(resultStatus, "4000")) {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(getContext(), "支付宝支付失败");
//                    } else if (TextUtils.equals(resultStatus, "6001")) {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(getContext(), "支付宝支付取消");
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(getContext(), "支付宝支付异常");
//                    }
                    break;
                }
                case WX_PAY_FLAG: {
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    public static CashierFragment instance() {
        CashierFragment view = new CashierFragment();
        return view;
    }

    @Override
    protected FragmentCashierBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentCashierBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        createOrderResultBean = (CreateOrderResultBean) getArguments().getSerializable("createOrderResultBean");
        if (createOrderResultBean != null && createOrderResultBean.getData().getOrderCode() != null) {
            mDataBinding.tvPrice.setText(createOrderResultBean.getData().getOrderPrice() + "");
            wxPrePay();
            aliPrePay();
            findOrder(false);
        }

        mDataBinding.rlAli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = ALI_PAY_FLAG;
                mDataBinding.imAli.setImageResource(R.mipmap.ic_shoping_s1);
                mDataBinding.imWx.setImageResource(R.mipmap.ic_shoping_s);
            }
        });

        mDataBinding.rlWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = WX_PAY_FLAG;
                mDataBinding.imAli.setImageResource(R.mipmap.ic_shoping_s);
                mDataBinding.imWx.setImageResource(R.mipmap.ic_shoping_s1);
            }
        });

        mDataBinding.appayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == ALI_PAY_FLAG) {
                    if (aliPrePayBean != null) {
                        payV1(v);
                    } else {
                        aliPrePay();
                    }
                } else if (type == WX_PAY_FLAG) {
                    payV2(v);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 微信支付返回
     */
    public void onEvent(WXResultEvent event) {
        findOrder(true);
//        if (event.errCode == BaseResp.ErrCode.ERR_OK) {
//            showAlert(getContext(), "微信支付成功");
//        } else if (event.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
//            showAlert(getContext(), "微信支付取消");
//        } else if (event.errCode == BaseResp.ErrCode.ERR_SENT_FAILED) {
//            showAlert(getContext(), "微信支付失败");
//        } else {
//            showAlert(getContext(), "微信支付异常");
//        }
    }

    /**
     * 微信支付业务示例
     */
    public void payV2(View v) {
        try {
            PayReq req = new PayReq();
            if (prepayid != null) {
                String time = System.currentTimeMillis() + "";
                req.appId = "wx43d03d3271a1c5d4";//你的微信appid
                req.partnerId = "1544047311";//商户号
                req.prepayId = prepayid;//预支付交易会话ID
                req.nonceStr = "a462b76e7436e98e0ed6e13c64b4fd3c";//随机字符串
                req.timeStamp = time;//时间戳
                req.packageValue = "Sign=WXPay";//扩展字段, 这里固定填写Sign = WXPay

                Map<String, String> map = new TreeMap<String, String>();
                map.put("appid", "wx43d03d3271a1c5d4");
                map.put("partnerid", "1544047311");
                map.put("noncestr", "a462b76e7436e98e0ed6e13c64b4fd3c");
                map.put("prepayid", prepayid);
                map.put("timestamp", time);
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
    }

    /**
     * 支付宝支付业务示例
     */
    public void payV1(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(getContext(), getString(R.string.error_missing_appid_rsa_private));
            return;
        }
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo1 = orderParam + "&" + sign;  //测试
        final String orderInfo = aliPrePayBean.getData();
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = ALI_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void payResult(boolean success, FindOrderBean orderBean) {
        if (type == ALI_PAY_FLAG) {
            if (success) {
                ToastUtil.show(getContext(), "支付宝支付成功");
                OrderDetailActivity.start(getContext(), orderBean.getGoodsList().get(0).getOrderCode(), 2);
                getActivity().finish();
            } else {
                ToastUtil.show(getContext(), "支付宝支付失败");
                OrderDetailActivity.start(getContext(), orderBean.getGoodsList().get(0).getOrderCode(), 1);
                getActivity().finish();
            }
        } else if (type == WX_PAY_FLAG) {
            if (success) {
                ToastUtil.show(getContext(), "微信支付成功");
                OrderDetailActivity.start(getContext(), orderBean.getGoodsList().get(0).getOrderCode(), 2);
                getActivity().finish();
            } else {
                ToastUtil.show(getContext(), "微信支付失败");
                OrderDetailActivity.start(getContext(), orderBean.getGoodsList().get(0).getOrderCode(), 1);
                getActivity().finish();
            }
        }
    }

    /**
     * 微信预支付
     */
    private void wxPrePay() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("orderCode", createOrderResultBean.getData().getOrderCode());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "wxPrePay");
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
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
//                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));

    }

    /**
     * 支付宝预支付
     */
    private void aliPrePay() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("orderCode", createOrderResultBean.getData().getOrderCode());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "aliPrePay");
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(AliPrePayAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<AliPrePayBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<AliPrePayBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    aliPrePayBean = response.body().getData();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));

    }

    FindOrderBean findOrderBean;

    /**
     * 订单查询
     */
    private void findOrder(final boolean finish) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("orderGood", createOrderResultBean.getData().getOrderCode());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "findOrder");
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindOrderAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindOrderBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindOrderBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    findOrderBean = response.body().getData();
                                    if (findOrderBean.getOrder() != null
                                            && findOrderBean.getOrder().getOrder_status() != null) {
                                        if (finish) {//支付后
                                            if (findOrderBean.getOrder().getOrder_status().equals("201")) {//支付成功
                                                payResult(true, findOrderBean);
                                            } else if (findOrderBean.getOrder().getOrder_status().equals("102")) {
                                                payResult(false, findOrderBean);
                                            } else {//支付失败
                                                payResult(false, findOrderBean);
                                            }
                                        } else {//支付前
                                            mDataBinding.tvPrice.setText(findOrderBean.getOrder().getOrder_price() + "");
                                            startCountDown();
                                        }
                                    }
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void startCountDown() {
        long nowTime = System.currentTimeMillis();
        final String lastTime = DataUtil.getTimeExpend(nowTime, findOrderBean.getOrder().getEnd_time(), "yyyy-MM-dd HH:mm:ss");
        final long lastTimeLong = DataUtil.getLongTime(nowTime, findOrderBean.getOrder().getEnd_time(), "yyyy-MM-dd HH:mm:ss");

        CountDownTimer countDownTimer = new CountDownTimer(lastTimeLong, 1000) {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onTick(long millisUntilFinished) {
                if (getView() != null) {
                    final String showTime = DataUtil.getLongToTime(millisUntilFinished, "yyyy-MM-dd HH:mm:ss");
                    mDataBinding.tvTime.setText("支付剩余时间：" + showTime);
                }
            }

            @Override
            public void onFinish() {
                if (getView() != null) {
                    mDataBinding.tvTime.setText("支付时间已超时，请重新下单");
                }
            }
        };
        countDownTimer.start();
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("确认", null)
                .setOnDismissListener(onDismiss)
                .show();
    }
}
