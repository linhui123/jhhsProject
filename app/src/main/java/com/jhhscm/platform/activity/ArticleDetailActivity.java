package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.msg.ArticleDetailFragment;
import com.jhhscm.platform.fragment.msg.NewsListFragment;

public class ArticleDetailActivity extends AbsToolbarActivity {

    public static void start(Context context,String id) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra("id",id);
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
        return "资讯信息";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return ArticleDetailFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putString("id",getIntent().getStringExtra("id"));
        return args;
    }
}
