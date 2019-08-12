package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsActivity;
import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.databinding.ActivityMainBinding;
import com.jhhscm.platform.fragment.Mechanics.PeiJianFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

/**
 * 配件列表
 */
public class PeiJianActivity extends AbsToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PeiJianActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String category_id, String category_namw) {
        Intent intent = new Intent(context, PeiJianActivity.class);
        intent.putExtra("category_id", category_id);
        intent.putExtra("category_namw", category_namw);
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
        return true;
    }

    @Override
    protected String getToolBarTitle() {
        mDataBinding.toolbar.setVisibility(View.GONE);
        return "配件列表";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return PeiJianFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("category_namw",getIntent().getStringExtra("category_namw"));
        args.putString("category_id",getIntent().getStringExtra("category_id"));
        return args;
    }
}
