package com.jhhscm.platform.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsActivity;
import com.jhhscm.platform.event.WXResultEvent;
import com.jhhscm.platform.tool.EventBusUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends AbsActivity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        textView = (TextView) findViewById(R.id.tv_result);
        textView.setVisibility(View.GONE);
        ((MyApplication) MyApplication.getInstance()).getApi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        ((MyApplication) MyApplication.getInstance()).getApi().handleIntent(getIntent(), this);
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            textView.setVisibility(View.VISIBLE);
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                EventBusUtil.post(new WXResultEvent(resp.errCode, "success"));
                textView.setText("微信支付返回:支付成功");
                finish();
            } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                EventBusUtil.post(new WXResultEvent(resp.errCode, "cancle"));
                textView.setText("微信支付返回:支付取消");
                finish();
            } else if (resp.errCode == BaseResp.ErrCode.ERR_SENT_FAILED) {
                EventBusUtil.post(new WXResultEvent(resp.errCode, "fail"));
                textView.setText("微信支付返回:支付失败");
                finish();
            } else {
                EventBusUtil.post(new WXResultEvent(resp.errCode, "exception"));
                textView.setText("微信支付返回:支付异常");
                finish();
            }
        }
    }

    @Override
    public void onReq(BaseReq req) {
    }
}
