package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.book.AddBookingFragment;
import com.jhhscm.platform.fragment.my.book.BookingFragment;

public class AddBookingActivity extends AbsToolbarActivity {

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, AddBookingActivity.class);
        intent.putExtra("type", type);//0收入；1支出
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
        if (getIntent().getIntExtra("type", 0) == 0) {
            return "记账收入";
        } else {
            return "记账支出";
        }
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return AddBookingFragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt("type", getIntent().getIntExtra("type", 0));
        return args;
    }
}