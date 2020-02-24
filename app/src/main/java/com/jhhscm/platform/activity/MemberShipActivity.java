package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.Mechanics.MechanicsFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.member.MemberShipFragment;

public class MemberShipActivity extends AbsToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MemberShipActivity.class);
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

//    @Override
//    protected boolean enableShareButton() {
//        return true;
//    }
//
//    @Override
//    protected void onOneKeyShareClick(Context context) {
//        super.onOneKeyShareClick(context);
//    }

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
        return "会员权益";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return MemberShipFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        return args;
    }
}

