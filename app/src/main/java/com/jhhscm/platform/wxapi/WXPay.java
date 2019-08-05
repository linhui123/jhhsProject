package com.jhhscm.platform.wxapi;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 微信支付
 */
public class WXPay {

    private static final String WX_APPID = "wxef67cd9d70bf047d";
    private static WXPay mWXPay;
    private IWXAPI mWXApi;
    private String mPayParam;
    private WXPayResultCallBack mCallback;

    public static final int NO_OR_LOW_WX = 1;   //未安装微信或微信版本过低
    public static final int ERROR_PAY_PARAM = 2;  //支付参数错误
    public static final int ERROR_PAY = 3;  //支付失败

    public interface WXPayResultCallBack {
        void onSuccess(); //支付成功

        void onError(int error_code);   //支付失败

        void onCancel();    //支付取消
    }

    public WXPay(Context context) {
        mWXApi = WXAPIFactory.createWXAPI(context, WX_APPID, false);
        mWXApi.registerApp(WX_APPID);
    }

    public static void init(Context context) {
        if (mWXPay == null) {
            mWXPay = new WXPay(context);
        }
    }

    public static WXPay getInstance() {
        return mWXPay;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    /**
     * 发起微信支付
     */
    public void doPay(String pay_param, WXPayResultCallBack callback) {
        mPayParam = pay_param;
        mCallback = callback;

        if (!check()) {
            if (mCallback != null) {
                mCallback.onError(NO_OR_LOW_WX);
            }
            return;
        }

        JSONObject param = null;
        try {
            param = new JSONObject(mPayParam);
        } catch (JSONException e) {
            e.printStackTrace();
            if (mCallback != null) {
                mCallback.onError(ERROR_PAY_PARAM);
            }
            return;
        }

        /**
         * data='{
         "sign": "6C9B780239DC0BCF463818526994C88C",
         "return_code": "SUCCESS",
         "trade_type": "APP",
         "result_code": "SUCCESS",
         "appid": "wxdb0c292c9efd55b0",
         "timestamp": 1481079045,
         "mch_id": "1307502901",
         "nonce_str": "I4UsUwS47MNQdC63",
         "prepay_id": "wx2016120710504558ad2eaf9c0703935941",
         "secondsign": "24C033C6828EFF9A606559530BDF0BFB",
         "return_msg": "OK"
         }'
         req.appId			= json.getString("appid");
         req.partnerId		= json.getString("partnerid");
         req.prepayId		= json.getString("prepayid");
         req.nonceStr		= json.getString("noncestr");
         req.timeStamp		= json.getString("timestamp");
         req.packageValue	= json.getString("package");
         req.sign			= json.getString("sign");

         if (TextUtils.isEmpty(param.optString("appid"))
         ||TextUtils.isEmpty(param.optString("partnerid"))
         || TextUtils.isEmpty(param.optString("prepay_id"))
         || TextUtils.isEmpty(param.optString("nonce_str"))
         || TextUtils.isEmpty(param.optString("timestamp"))
         || TextUtils.isEmpty(param.optString("secondsign")))
         {


         */
        if (TextUtils.isEmpty(param.optString("appid"))
                ||TextUtils.isEmpty(param.optString("partnerid"))
                || TextUtils.isEmpty(param.optString("prepayid"))
                || TextUtils.isEmpty(param.optString("noncestr"))
                || TextUtils.isEmpty(param.optString("timestamp"))
                || TextUtils.isEmpty(param.optString("package"))
                || TextUtils.isEmpty(param.optString("sign")))
        {
            if (mCallback != null) {
                mCallback.onError(ERROR_PAY_PARAM);
            }
            return;
        }

        PayReq req = new PayReq();
        req.appId = param.optString("appid");
        req.partnerId = param.optString("partnerid");
        Log.i("YL","WXPay doPay()"+param.optString("appid"));
        req.prepayId = param.optString("prepayid");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = param.optString("noncestr");
        req.timeStamp = param.optString("timestamp");
        req.sign = param.optString("package");
        Log.i("YL","WXPay doPay()"+param.optString("package"));
        req.sign = param.optString("sign");
        mWXApi.sendReq(req);
    }

    //支付回调响应
    public void onResp(int error_code) {
        if (mCallback == null) {
            return;
        }

        if (error_code == 0) {   //成功
            mCallback.onSuccess();
        } else if (error_code == -1) {   //错误
            mCallback.onError(ERROR_PAY);
        } else if (error_code == -2) {   //取消
            mCallback.onCancel();
        }
        if (mWXApi != null) {
            mWXApi.unregisterApp();
        }
        mCallback = null;
    }

    //检测是否支持微信支付
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }
}
