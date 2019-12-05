package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.labour.PushZhaoPinFragment;

public class PushZhaoPinActivity extends AbsToolbarActivity {
    private int type;//0只读；1编辑

    public static void start(Context context, String labour_code,String id, int type) {
        Intent intent = new Intent(context, PushZhaoPinActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        intent.putExtra("labour_code", labour_code);
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
        if (getIntent().getIntExtra("type",0)==0){
            return "发布招聘";
        }else {
            return "编辑招聘";
        }
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return PushZhaoPinFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("type", getIntent().getIntExtra("type", 0));
        args.putString("labour_code", getIntent().getStringExtra("labour_code"));
        args.putString("id", getIntent().getStringExtra("id"));
        return args;
    }
}