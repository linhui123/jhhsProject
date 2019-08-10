package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.Mechanics.ComparisonDetailFragment;
import com.jhhscm.platform.fragment.Mechanics.ComparisonFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class ComparisonDetailActivity extends AbsToolbarActivity {

    public static void start(Context context, String good_code1, String good_code2) {
        Intent intent = new Intent(context, ComparisonDetailActivity.class);
        intent.putExtra("good_code1", good_code1);
        intent.putExtra("good_code2", good_code2);
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
        return "机型对比";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return ComparisonDetailFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("good_code1",getIntent().getStringExtra("good_code1"));
        args.putString("good_code2",getIntent().getStringExtra("good_code2"));
        return args;
    }
}