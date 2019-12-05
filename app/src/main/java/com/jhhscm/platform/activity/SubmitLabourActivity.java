package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.labour.SubmitLabourFragment;

/**
 * 劳务信息提交
 */
public class SubmitLabourActivity extends AbsToolbarActivity {

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, SubmitLabourActivity.class);
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
        int type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            return "证书代办";
        } else if (type == 1) {
            return "职称代评";
        } else if (type == 2) {
            return "学历提升";
        } else if (type == 3) {
            return "职业培训";
        } else if (type == 4) {
            return "委托招聘";
        } else {
            return "保险代理";
        }
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return SubmitLabourFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("type", getIntent().getIntExtra("type", 0));
        return args;
    }
}