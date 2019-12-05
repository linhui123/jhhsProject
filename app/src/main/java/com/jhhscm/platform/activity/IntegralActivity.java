package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.invitation.IntegralFragment;

public class IntegralActivity extends AbsToolbarActivity {

    public static void start(Context context, int type, String des) {
        Intent intent = new Intent(context, IntegralActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("des", des);
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
        if ( getIntent().getIntExtra("type", 0)==0){
            return "个人积分规则";
        }else {
            return "店铺积分规则";
        }
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return IntegralFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("type", getIntent().getIntExtra("type", 0));
        args.putString("des", getIntent().getStringExtra("des"));
        return args;
    }
}
