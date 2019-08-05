package com.jhhscm.platform.wxapi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.labour.LabourFragment;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends AbsToolbarActivity implements IWXAPIEventHandler {
    public static void start(Context context, CreateOrderResultBean createOrderResultBean) {
        Intent intent = new Intent(context, WXEntryActivity.class);
        intent.putExtra("createOrderResultBean", createOrderResultBean);
        context.startActivity(intent);
    }

    @Override
    protected boolean enableHomeButton() {
        return true;
    }

    @Override
    protected boolean enableDisplayHomeAsUp() {
        return true;
    }

    @Override
    protected boolean fullScreenMode() {
        return true;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected String getToolBarTitle() {
        return "";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        ((MyApplication) MyApplication.getInstance()).getApi().handleIntent(getIntent(), this);
        return WXPayEntryFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("createOrderResultBean", (CreateOrderResultBean) getIntent().getSerializableExtra("createOrderResultBean"));
        return args;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        ((MyApplication) MyApplication.getInstance()).getApi().handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.e("ac onReq", "openid：" + req.openId);
        Toast.makeText(this, "openid = " + req.openId, Toast.LENGTH_SHORT).show();
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_PAY_BY_WX:
                Toast.makeText(this, "Launch From Weixin", Toast.LENGTH_SHORT).show();
                break;
            case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
                Toast.makeText(this, "Launch From Weixin", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        Toast.makeText(this, "openid = " + resp.openId, Toast.LENGTH_SHORT).show();
        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            Toast.makeText(this, "code = " + resp.errCode, Toast.LENGTH_SHORT).show();
        }

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Toast.makeText(this, "code = " + resp.errCode, Toast.LENGTH_SHORT).show();

        }
        int result = 0;

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        finish();
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}
