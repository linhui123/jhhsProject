package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.store.ServiceRecordFragment;

public class ServiceRecordActivity extends AbsToolbarActivity {

    public static void start(Context context, String bus_code) {
        Intent intent = new Intent(context, ServiceRecordActivity.class);
        intent.putExtra("code", bus_code);
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
        return "服务记录";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return ServiceRecordFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("code", getIntent().getStringExtra("code"));
        return args;
    }
}
