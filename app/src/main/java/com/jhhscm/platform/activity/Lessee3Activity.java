package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.lessee.Lessee3Fragment;
import com.jhhscm.platform.fragment.lessee.LesseeBean;
import com.jhhscm.platform.fragment.my.set.FeedbackFragment;

public class Lessee3Activity extends AbsToolbarActivity {

    public static void start(Context context, LesseeBean lesseeBean) {
        Intent intent = new Intent(context, Lessee3Activity.class);
        intent.putExtra("lesseeBean", lesseeBean);
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
        return "承租人信息";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return Lessee3Fragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("lesseeBean", getIntent().getSerializableExtra("lesseeBean"));
        return args;
    }
}