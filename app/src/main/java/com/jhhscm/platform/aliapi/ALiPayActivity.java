package com.jhhscm.platform.aliapi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.jhhscm.platform.R;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.jhhscm.platform.wxapi.WXEntryActivity;

import java.util.Map;

public class ALiPayActivity extends AppCompatActivity {


    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2019070565716752";

    public static final String RSA_PRIVATE = "";
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC8f9Jdvgk2hqxKwAQ7NvL/co4NT7EnqPNXU3lU0Px+2pobdCA3VzY3iJFxxgyagldRtKEkzzz497H/OqNaMrGIxIUhCRve9LEG1ySB8e9Ees87I5ZHVtgTk/7HJXZuHlZyvePSFV72brEROl1AefqrwTewPifLZAcijlGM5CkzJr/KssRpU4uHU1rEIt9DZSWC2KjSOkIky7TrHur1AOaYmgQ0MOPaJ2RT+Qak8dmnqPmGZGHX2kaAbJb1xZnxND4/I16C+a8YOZePGeIR8qYlWIwZIuLyNuZVnpfunpnNvUe3WyGjcD5/kJgZ7OtwrceGpswyYGlY9Fznu87ABS7bAgMBAAECggEBAJe2XQ8b43hiPgtPrlgmar+UaKZoDJJ6FZikU5QjPAWxVbVg6okABV/+5+jlWMGGxFa7hbMFpPJREY71mAIBqQgF+4xSM39n+48g235GxaedHGthwhMa34AqbjXEfiQ1AKkLEnDEyJCZGKRgECvTwSA5u+N6szatBF0YpKQ8ArjqRFlTzuDffV6hru/JZAThyqO9n9VaLS622ELYnLoLQVJMg51mLxvX/swEgx29TjnXwSrm7Ex982qcqxt85KfFTXDsMCHfO3rol7nfmNIK16WDIgNTnKarkrRrduPB+Ljnoms/PiqdeQH9eCDD8zq84efjYal4W08vV4/sgSjg9CECgYEA9RxZimF8S+9KQzHHiUoIHSaLYX5aM0vEZQbqfYX+0CeXDrcvEbl90YGDpNB/8SwsUU55KMxxUT+NG2renEgyZiCugwFTThAwcvYb5vl7F1yAiB7qVkFeFHmdiejCiowJpePz6x0rFGX0CXH5OuZ/Z0tm2KDpBX4i5LGxMPgt0AsCgYEAxN+g9pDPeh11eXdrR7G1mVocmf14iwMAEO4qHH7xGp9Sta++vXd6jlKuxb6kQcxH5qxPxNL22KoWpu0NPovc7bXgzje4yT3ySk+QabcMF2UlsOK/PXLdGAAI3MFhIabGpWEH1nF66s4f/squoZfLVc67coDuTt3AWSEAeO/bTnECgYB4KeLXzX0DOReeI4xgCPQ7Xkccj7YmybFJmf9hdEx8vbv3keS4eshs6mKgbTsb//zmC2OolbnEDDTgSR9DXL2ghcsoHIE7lwI5ieAZ9xraVBYLJaTajodR4GFUV9Dv3UFpm3xcOluBT445BzAZKSEygau07gXvFGyE03w7tp+3CwKBgQDE0D+NXvVbwgmHtd+0266NMS24sFIKvqQZSM8mj60fDTnVUm1f4grOL1BzdYOmF7+llotkW/bUYS2mEQOVjKL/rTyhS8lavafzrBjV5l2bIc4NSJEgsCzhal9xuY0N5DlgaWE5e1cDV69au2rbNvHaxYJVFRmoFD3PnJNpU3gtsQKBgBZp5iiDmrESCeU5R35YobCUa6O2YSnsNSjcACfQHujYQU5KZEKoeMN5i553Db1Bsh7o4+qVNXB1DZR2FLF1+uotlxYSk9baF3OzBJKaUmN7bxn/Vmo3/OAItXvvSuBnxxVGg1+FaTPamy5fOa8/CZPR/3Ao2PQM+2IaOg9bnhzA";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(ALiPayActivity.this, "成功" + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(ALiPayActivity.this, "失败" + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        showAlert(ALiPayActivity.this, getString(R.string.auth_success) + authResult);
//                    } else {
//                        // 其他状态值则为授权失败
//                        showAlert(ALiPayActivity.this, getString(R.string.auth_failed) + authResult);
//                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    public static void start(Context context, CreateOrderResultBean createOrderResultBean) {
        Intent intent = new Intent(context, ALiPayActivity.class);
        intent.putExtra("createOrderResultBean", createOrderResultBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_pay);
    }

    /**
     * 支付宝支付业务示例
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(this, getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ALiPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
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
