package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.store.SelectDeviceFragment;
import com.jhhscm.platform.fragment.my.store.StoreOrderSubmit1Fragment;

public class SelectDeviceActivity extends AbsToolbarActivity {

    public static void start(Context context, String phone) {
        Intent intent = new Intent(context, SelectDeviceActivity.class);
        intent.putExtra("phone", phone);
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
        return "选择机子";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return SelectDeviceFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("phone", getIntent().getStringExtra("phone"));
        return args;
    }
}
