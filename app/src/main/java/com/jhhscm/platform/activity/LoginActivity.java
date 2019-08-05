package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsActivity;
import com.jhhscm.platform.fragment.my.LoginFragment;

public class LoginActivity extends AbsActivity {

    @Override
    protected boolean fullScreenMode() {
        return true;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context,boolean isSkip) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("isSkip",isSkip);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginFragment loginFragment=LoginFragment.instance();
        loginFragment.setArguments(onPutArguments());
        setContentView(R.layout.activity_login);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerId,loginFragment, null).commitAllowingStateLoss();
    }

    @Override
    public void finish() {
        super.finish();
    }

    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putBoolean("isSkip", getIntent().getBooleanExtra("isSkip",false));
        return args;
    }
}
