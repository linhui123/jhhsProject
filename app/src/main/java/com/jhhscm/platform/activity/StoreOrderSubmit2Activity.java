package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.store.StoreOrderSubmit1Fragment;
import com.jhhscm.platform.fragment.my.store.StoreOrderSubmit2Fragment;
import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerBean;

import java.util.List;

public class StoreOrderSubmit2Activity extends AbsToolbarActivity {

    public static void start(Context context, FindUserGoodsOwnerBean dataBean, String userName, String userPhone) {
        Intent intent = new Intent(context, StoreOrderSubmit2Activity.class);
        intent.putExtra("databean", dataBean);
        intent.putExtra("name", userName);
        intent.putExtra("phone", userPhone);
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
        return "提交订单";
    }

    @Override
    protected AbsFragment onCreateContentView() {
        return StoreOrderSubmit2Fragment.instance();
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putSerializable("databean", getIntent().getSerializableExtra("databean"));
        args.putString("name", getIntent().getStringExtra("name"));
        args.putString("phone", getIntent().getStringExtra("phone"));
        return args;
    }
}
