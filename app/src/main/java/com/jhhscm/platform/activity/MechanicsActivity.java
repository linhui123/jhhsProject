package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.Mechanics.MechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.PeiJianFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class MechanicsActivity extends AbsToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MechanicsActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String category_id, String category_namw) {
        Intent intent = new Intent(context, MechanicsActivity.class);
        intent.putExtra("brand_id", category_id);
        intent.putExtra("brand_name", category_namw);
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
        mDataBinding.toolbar.setVisibility(View.GONE);
        return "机械列表";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return MechanicsFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("brand_id",getIntent().getStringExtra("brand_id"));
        args.putString("brand_name",getIntent().getStringExtra("brand_name"));
        return args;
    }
}

