package com.jhhscm.platform.views.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.aliapi.OrderInfoUtil2_0;
import com.jhhscm.platform.aliapi.PayResult;
import com.jhhscm.platform.databinding.DialogPayBinding;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.event.WXResultEvent;
import com.jhhscm.platform.fragment.repayment.ContractAliPrePayAction;
import com.jhhscm.platform.fragment.repayment.ContractPayCreateOrderAction;
import com.jhhscm.platform.fragment.repayment.ContractPayCreateOrderBean;
import com.jhhscm.platform.fragment.repayment.ContractWXPrePayAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class PayDialog extends BaseDialog {
    private DialogPayBinding mDataBinding;
    private boolean mCancelable = true;
    private LogisticsDialog.CallbackListener mListener;
    private Activity activity;
    private String orderId = "";
    private ContractPayCreateOrderBean.DataBean dataBean;
    private String prepayid = "";
    private String data = "";
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2019070565716752";
    public static final String RSA_PRIVATE = "";
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC8f9Jdvgk2hqxKwAQ7NvL/co4NT7EnqPNXU3lU0Px+2pobdCA3VzY3iJFxxgyagldRtKEkzzz497H/OqNaMrGIxIUhCRve9LEG1ySB8e9Ees87I5ZHVtgTk/7HJXZuHlZyvePSFV72brEROl1AefqrwTewPifLZAcijlGM5CkzJr/KssRpU4uHU1rEIt9DZSWC2KjSOkIky7TrHur1AOaYmgQ0MOPaJ2RT+Qak8dmnqPmGZGHX2kaAbJb1xZnxND4/I16C+a8YOZePGeIR8qYlWIwZIuLyNuZVnpfunpnNvUe3WyGjcD5/kJgZ7OtwrceGpswyYGlY9Fznu87ABS7bAgMBAAECggEBAJe2XQ8b43hiPgtPrlgmar+UaKZoDJJ6FZikU5QjPAWxVbVg6okABV/+5+jlWMGGxFa7hbMFpPJREY71mAIBqQgF+4xSM39n+48g235GxaedHGthwhMa34AqbjXEfiQ1AKkLEnDEyJCZGKRgECvTwSA5u+N6szatBF0YpKQ8ArjqRFlTzuDffV6hru/JZAThyqO9n9VaLS622ELYnLoLQVJMg51mLxvX/swEgx29TjnXwSrm7Ex982qcqxt85KfFTXDsMCHfO3rol7nfmNIK16WDIgNTnKarkrRrduPB+Ljnoms/PiqdeQH9eCDD8zq84efjYal4W08vV4/sgSjg9CECgYEA9RxZimF8S+9KQzHHiUoIHSaLYX5aM0vEZQbqfYX+0CeXDrcvEbl90YGDpNB/8SwsUU55KMxxUT+NG2renEgyZiCugwFTThAwcvYb5vl7F1yAiB7qVkFeFHmdiejCiowJpePz6x0rFGX0CXH5OuZ/Z0tm2KDpBX4i5LGxMPgt0AsCgYEAxN+g9pDPeh11eXdrR7G1mVocmf14iwMAEO4qHH7xGp9Sta++vXd6jlKuxb6kQcxH5qxPxNL22KoWpu0NPovc7bXgzje4yT3ySk+QabcMF2UlsOK/PXLdGAAI3MFhIabGpWEH1nF66s4f/squoZfLVc67coDuTt3AWSEAeO/bTnECgYB4KeLXzX0DOReeI4xgCPQ7Xkccj7YmybFJmf9hdEx8vbv3keS4eshs6mKgbTsb//zmC2OolbnEDDTgSR9DXL2ghcsoHIE7lwI5ieAZ9xraVBYLJaTajodR4GFUV9Dv3UFpm3xcOluBT445BzAZKSEygau07gXvFGyE03w7tp+3CwKBgQDE0D+NXvVbwgmHtd+0266NMS24sFIKvqQZSM8mj60fDTnVUm1f4grOL1BzdYOmF7+llotkW/bUYS2mEQOVjKL/rTyhS8lavafzrBjV5l2bIc4NSJEgsCzhal9xuY0N5DlgaWE5e1cDV69au2rbNvHaxYJVFRmoFD3PnJNpU3gtsQKBgBZp5iiDmrESCeU5R35YobCUa6O2YSnsNSjcACfQHujYQU5KZEKoeMN5i553Db1Bsh7o4+qVNXB1DZR2FLF1+uotlxYSk9baF3OzBJKaUmN7bxn/Vmo3/OAItXvvSuBnxxVGg1+FaTPamy5fOa8/CZPR/3Ao2PQM+2IaOg9bnhzA";

    private static final int ALI_PAY_FLAG = 1;
    private static final int WX_PAY_FLAG = 2;
    private int type = ALI_PAY_FLAG;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ALI_PAY_FLAG: {
//                    /**
//                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    同步通知结果
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(getContext(), "支付宝支付成功");
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        showAlert(getContext(), "正在处理中");
                    } else if (TextUtils.equals(resultStatus, "4000")) {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(getContext(), "支付宝支付失败");
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(getContext(), "支付宝支付取消");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(getContext(), "支付宝支付异常");
                    }
                    EventBusUtil.post(new RefreshEvent());
                    dismiss();
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

    public interface CallbackListener {
        void clickY();
    }

    public PayDialog(Context context) {
        super(context);
    }

    public PayDialog(Context context, Activity activity, String orderId) {
        super(context);
        this.orderId = orderId;
        this.activity = activity;
    }

    public PayDialog(Context context, LogisticsDialog.CallbackListener listener) {
        this(context);
        setCanceledOnTouchOutside(false);
        this.mListener = listener;
    }

    public void setCallbackListener(LogisticsDialog.CallbackListener listener) {
        this.mListener = listener;
    }

    public void setCanBackDismiss(boolean canBackDismiss) {
        this.mCancelable = canBackDismiss;
    }

    @Override
    public void show() {
        super.show();
        Display d = this.getWindow().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getDeviceWidth(getContext()) - getContext().getResources().getDimension(R.dimen.head_title_height));
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_pay, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        EventBusUtil.registerEvent(this);
        type = ALI_PAY_FLAG;
        mDataBinding.imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        dataBean = new ContractPayCreateOrderBean.DataBean();
        contractpayCreateorder(orderId);

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

        mDataBinding.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tvPay", "type " + type);
                if (dataBean != null && dataBean.getOrderCode() != null) {
                    if (type == ALI_PAY_FLAG) {
                        contractPayAliPrePay(dataBean.getOrderCode());
                    } else if (type == WX_PAY_FLAG) {
                        contractPayWxPrePay(dataBean.getOrderCode());
                    }
                } else {
                    contractpayCreateorder(orderId);
                }
//                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 创建合同订单
     */
    private void contractpayCreateorder(final String id) {
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("id", id);
        map.put("mobile", ConfigUtils.getCurrentUser(getContext()).getMobile());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getContext(), map, "contractpayCreateorder");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(ContractPayCreateOrderAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ContractPayCreateOrderBean>>() {

                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ContractPayCreateOrderBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getContext() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getContext().getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    dataBean = response.body().getData().getData();
                                } else if (response.body().getCode().equals("1001")) {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                } else {
                                    ToastUtils.show(getContext(), "网络异常");
                                }
                            }
                        }
                    }
                }));
    }


    /**
     * 获取微信支付订单号 预支付
     */
    private void contractPayWxPrePay(final String orderCode) {
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("orderCode", orderCode);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getContext(), map, "contractPayWxPrePay");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(ContractWXPrePayAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {

                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getContext() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getContext().getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    prepayid = response.body().getData().getPrepay_id();
                                    payV2();
                                } else if (response.body().getCode().equals("1001")) {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                } else {
                                    ToastUtils.show(getContext(), "网络异常");
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 获取支付宝支付订单号 预支付
     */
    private void contractPayAliPrePay(final String orderCode) {
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("orderCode", orderCode);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getContext(), map, "contractPayAliPrePay");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(ContractAliPrePayAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {

                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getContext() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getContext().getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    data = response.body().getData().getData();
                                    payV1();
                                } else if (response.body().getCode().equals("1001")) {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                } else {
                                    ToastUtils.show(getContext(), "网络异常");
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 微信支付业务示例
     */
    public void payV2() {
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
            (MyApplication.getInstance()).getApi().sendReq(req);
        } catch (Exception e) {
            Log.e("PAY_GET", "异常：" + e.getMessage());
            Toast.makeText(getContext(), "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 支付宝支付业务示例
     */
    public void payV1() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(getContext(), getContext().getString(R.string.error_missing_appid_rsa_private));
            return;
        }
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo1 = orderParam + "&" + sign;  //测试
        final String orderInfo = data;
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
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

    /**
     * 微信支付返回
     */
    public void onEvent(WXResultEvent event) {
        if (event.errCode == BaseResp.ErrCode.ERR_OK) {
            showAlert(getContext(), "微信支付成功");
        } else if (event.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            showAlert(getContext(), "微信支付取消");
        } else if (event.errCode == BaseResp.ErrCode.ERR_SENT_FAILED) {
            showAlert(getContext(), "微信支付失败");
        } else {
            showAlert(getContext(), "微信支付异常");
        }
        EventBusUtil.post(new RefreshEvent());
        dismiss();
    }
}

