package com.jhhscm.platform.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.jhhscm.platform.R;
import com.jhhscm.platform.aliapi.PayResult;
import com.jhhscm.platform.bean.PayResultZhiFBean;
import com.jhhscm.platform.bean.PayReusltBean;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;

import com.jhhscm.platform.tool.JsonUtil;
import com.jhhscm.platform.tool.Utils;
import com.jhhscm.platform.wxapi.WXPay;
import com.jhhscm.platform.wxapi.WXPayCallbackActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class TestActivity extends Activity {
    @BindView(R.id.weix)
    ImageView weix;
    @BindView(R.id.pay_btn)
    Button payBtn;
    private String payType = "1";
    private static final int SDK_PAY_FLAG = 1;
    PayResultZhiFBean payResultZhiFBean;
    PayResult payReusltBean;

    public static void start(Context context, CreateOrderResultBean createOrderResultBean) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra("createOrderResultBean", createOrderResultBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        MobclickAgent.onResume(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPause(this);
        super.onResume();
    }

    @OnClick({R.id.weix, R.id.pay_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.weix:
                payType = "1";
                break;
            case R.id.pay_btn:

                if ("1".equals(payType)) {
//                    payReusltBean = new PayResult();
//                    Log.d("patey", payType);
//                    if ("true".equals(payReusltBean.getFlag())) {
//                        JSONObject jsonObjectStr = new JSONObject();
//                        jsonObjectStr.put("sign","123456789a123456789a123456789abc");
//                        jsonObjectStr.put("timestamp", payReusltBean.getObj().getTimestamp());
//                        jsonObjectStr.put("noncestr", payReusltBean.getObj().getNoncestr());
//                        jsonObjectStr.put("partnerid", payReusltBean.getObj().getPartnerid());
//                        jsonObjectStr.put("prepayid", payReusltBean.getObj().getPrepayid());
//                        jsonObjectStr.put("package", "Sign=WXPay");
//                        jsonObjectStr.put("appid", "wx02461b836d0dd962");
//                        if ("1".equals(payType)) {
//                            String jsonStr = JsonUtil.beanToJson(jsonObjectStr);
//                            Log.i("buyzds", "ReChargeMoneryActivity onNext()" + jsonStr);
//                            wxPay(jsonStr);
//                        }
//                    }

                }
                if ("2".equals(payType)) {
                    payResultZhiFBean = new PayResultZhiFBean();
                    if ("true".equals(payResultZhiFBean.getFlag())) {
                        Log.d("payReusltBean", payResultZhiFBean.getObj());
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(TestActivity.this);
                                Map<String, String> result = alipay.payV2(payResultZhiFBean.getObj(), true);
                                Log.i("msp", result.toString());
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                }
                break;
        }
    }

    private void wxPay(String pay_param) {
        WXPay.init(this);      //要在支付前调用
        WXPay.getInstance().doPay(pay_param, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                Utils.toast(TestActivity.this, "支付成功");
            }

            @Override
            public void onError(int error_code) {
                if (3 == error_code) {
                    Toast.makeText(TestActivity.this, "充值失败", Toast.LENGTH_SHORT).show();

                }
                if (1 == error_code) {
                    Utils.toast(TestActivity.this, "未安装微信或微信版本过低");
                }
            }

            @Override
            public void onCancel() {
                //Toast.makeText(UIUtils.getContext(), "取消充值", Toast.LENGTH_SHORT).show();
                Utils.toast(TestActivity.this, "取消支付");
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    //Log.d("codeStr",resultStatus+"--------"+resultInfo);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //Utils.toast(content,driverLineId);
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(TestActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(TestActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
}
