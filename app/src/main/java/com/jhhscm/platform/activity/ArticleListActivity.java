package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.msg.NewsListFragment;

public class ArticleListActivity extends AbsToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ArticleListActivity.class);
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
        return "资讯信息";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return NewsListFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        return args;
    }
}

