package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.mechanics.AddDeviceFragment;
import com.jhhscm.platform.fragment.my.mechanics.MyMechanicsFragment;

public class AddDeviceActivity extends AbsToolbarActivity {

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, AddDeviceActivity.class);
        intent.putExtra("type", type);
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
        if (getIntent().getIntExtra("type", 0) == 0) {
            return "添加设备";
        } else {
            return "编辑设备";
        }

    }

    @Override
    protected AbsFragment onCreateContentView() {
        return AddDeviceFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("type", getIntent().getIntExtra("type", 0));
        return args;
    }
}
