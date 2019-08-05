package com.jhhscm.platform.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jhhscm.platform.activity.MechanicsH5Activity;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;


/**
 * 微信支付回调
 */
public class WXPayCallbackActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayCallbackActivity";
    CreateOrderResultBean createOrderResultBean;

    public static void start(Context context, CreateOrderResultBean createOrderResultBean) {
        Intent intent = new Intent(context, WXPayCallbackActivity.class);
        intent.putExtra("createOrderResultBean", createOrderResultBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (WXPay.getInstance() != null) {
            WXPay.getInstance().getWXApi().handleIntent(getIntent(), this);
        }/* else {
            finish();
        }*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
       /* if(WXPay.getInstance() != null) {
            WXPay.getInstance().getWXApi().handleIntent(intent, this);
       }*/
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (WXPay.getInstance() != null) {
                if (0 == baseResp.errCode) {
                    Log.d(TAG, "支付成功");
                } else {
                    Log.d(TAG, "onResp: 支付失败");
                }

                WXPay.getInstance().onResp(baseResp.errCode);
                finish();
            }
        }
    }

}
