package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.invitation.InvitationRegisterFragment;

/**
 * 邀请注册
 */
public class InvitationRegisterActivity extends AbsToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, InvitationRegisterActivity.class);
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
        return "邀请注册";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return InvitationRegisterFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        return args;
    }
}