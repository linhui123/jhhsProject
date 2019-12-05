package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.invitation.MyInviteFragment;

public class MyInviteActivity extends AbsToolbarActivity {

    public static void start(Context context, int num) {
        Intent intent = new Intent(context, MyInviteActivity.class);
        intent.putExtra("num", num);
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
        return "我邀请的人(" + getIntent().getIntExtra("num", 0) + ")";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return MyInviteFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("num", getIntent().getIntExtra("num", 0));
        return args;
    }
}
