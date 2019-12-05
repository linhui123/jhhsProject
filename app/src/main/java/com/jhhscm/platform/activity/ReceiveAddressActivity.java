package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.address.ReceiveAddressListFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class ReceiveAddressActivity extends AbsToolbarActivity {
    //判断是否需要回调
    private boolean isResult;

    public static void start(Context context, boolean isResult) {
        Intent intent = new Intent(context, ReceiveAddressActivity.class);
        intent.putExtra("isResult", isResult);
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
    protected boolean enableOperateText() {
        return false;
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
        return "地址管理";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return ReceiveAddressListFragment.instance();
    }

    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putBoolean("isResult", getIntent().getBooleanExtra("isResult", false));
        return args;
    }
}
