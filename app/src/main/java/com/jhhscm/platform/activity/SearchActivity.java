package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.search.SearchFragment;
import com.umeng.analytics.MobclickAgent;

public class SearchActivity extends AbsToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, SearchActivity.class);
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
        mDataBinding.toolbar.setVisibility(View.GONE);
        return "";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return SearchFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("type", getIntent().getIntExtra("type", 0));
        return args;
    }
}
