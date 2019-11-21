package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.book.BookingDetailFragment;
import com.jhhscm.platform.fragment.my.book.BookingFragment;

public class BookingDetailActivity extends AbsToolbarActivity {

    public static void start(Context context, int type, String data_code) {
        Intent intent = new Intent(context, BookingDetailActivity.class);
        intent.putExtra("data_code", data_code);
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
        return "详情";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return BookingDetailFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("type", getIntent().getIntExtra("type", 0));
        args.putString("data_code",getIntent().getStringExtra("data_code"));
        return args;
    }
}
