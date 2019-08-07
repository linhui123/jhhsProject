package com.jhhscm.platform.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsActivity;
import com.jhhscm.platform.event.JumpEvent;
import com.jhhscm.platform.fragment.FinancialFragment;
import com.jhhscm.platform.fragment.Mechanics.MechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.PeiJianFragment;
import com.jhhscm.platform.fragment.MsgFragment;
import com.jhhscm.platform.fragment.home.HomePageFragment;
import com.jhhscm.platform.fragment.my.MyFragment;
import com.jhhscm.platform.fragment.sale.SaleMachineFragment;
import com.jhhscm.platform.jpush.ExampleUtil;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.HomeAlterDialog;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import com.jhhscm.platform.databinding.ActivityMainBinding;

public class MainActivity extends AbsActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private static String homepage = "mAnalysisFragment";
    private ActivityMainBinding mDataBinding;
    /**
     * 创建一个集合，存储碎片
     */
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private HomePageFragment homeFragment;
    private MechanicsFragment mechanicsFragment;
    //    private PeiJianFragment msgFragment;
    private FinancialFragment financialFragment;
    private MyFragment mMeFragment;

    private FragmentManager fm;
    FragmentTransaction transaction;
    private FragmentTransaction transaction1;
    public static boolean isForeground = false;
    private long lastClickTime = 0;
    public static MainActivity instance;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.jhhscm.platform.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        instance = this;
        initPermission();
        registerMessageReceiver();
        JPushInterface.init(getApplicationContext());// used for receive msg
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            homeFragment = new HomePageFragment();
            fragmentManager.beginTransaction().add(R.id.fl, homeFragment, homepage).commit();
        }
    }

    @Override
    protected boolean fullScreenMode() {
        return true;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    private void initPermission() {
        YXPermission.getInstance(getApplicationContext()).request(new AcpOptions.Builder()
                .setDeniedCloseBtn(getApplicationContext().getString(R.string.permission_dlg_close_txt))
                .setDeniedSettingBtn(getApplicationContext().getString(R.string.permission_dlg_settings_txt))
                .setDeniedMessage(getApplicationContext().getString(R.string.permission_denied_txt, "定位"))
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).build(), new AcpListener() {
            @Override
            public void onGranted() {
                initView();
            }

            @Override
            public void onDenied(List<String> permissions) {
                finish();
            }
        });
    }

    private void initView() {
        EventBusUtil.registerEvent(this);
        mDataBinding.rgOper.setOnCheckedChangeListener(this);
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        homeFragment = (HomePageFragment) fm.findFragmentByTag("homeFragment");
        mechanicsFragment = (MechanicsFragment) fm.findFragmentByTag("mechanicsFragment");
        financialFragment = (FinancialFragment) fm.findFragmentByTag("mFinancialFragment");
        mMeFragment = (MyFragment) fm.findFragmentByTag("mMeFragment");


        mDataBinding.rdExpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HomeAlterDialog(MainActivity.this, new HomeAlterDialog.CallbackListener() {

                    @Override
                    public void clickF() {
                        SaleMachineActivity.start(MainActivity.this);
                    }

                    @Override
                    public void clickS() {
                        PushZhaoPinActivity.start(MainActivity.this, "", "", 0);
                    }

                    @Override
                    public void clickT() {
                        PushQiuZhiActivity.start(MainActivity.this, "", "", 0);
                    }
                }).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        transaction1 = fm.beginTransaction();
        if (homeFragment != null) {
            transaction1.hide(homeFragment);
        }
        if (mechanicsFragment != null) {
            transaction1.hide(mechanicsFragment);
        }
        if (financialFragment != null) {
            transaction1.hide(financialFragment);
        }
        if (mMeFragment != null) {
            transaction1.hide(mMeFragment);
        }
        if (checkedId == R.id.rd_analysis) {
            if (homeFragment == null) {
                homeFragment = new HomePageFragment();
                transaction1.add(R.id.fl, homeFragment, homepage);
            } else {
                transaction1.show(homeFragment);
            }

        } else if (checkedId == R.id.rd_educationadmin) {
            if (mechanicsFragment == null) {
                mechanicsFragment = new MechanicsFragment();
                transaction1.add(R.id.fl, mechanicsFragment, "mEducationalAdminFragment");
            } else {
                transaction1.show(mechanicsFragment);
            }

        } else if (checkedId == R.id.rd_finance) {
            if (financialFragment == null) {
                financialFragment = new FinancialFragment();
                transaction1.add(R.id.fl, financialFragment, "mFinancialFragment");
            } else {
                transaction1.show(financialFragment);
            }
        } else if (checkedId == R.id.rd_me) {
            if (mMeFragment == null) {
                mMeFragment = new MyFragment();
                transaction1.add(R.id.fl, mMeFragment, "mMeFragment");
            } else {
                transaction1.show(mMeFragment);
            }
        }
//        else if (checkedId == R.id.rd_expend) {
//
//        }
        transaction1.commit();
    }

    @Override
    public void onBackPressed() {
        if (lastClickTime <= 0) {
            Toast.makeText(this, "再点击一下，退出应用", Toast.LENGTH_SHORT).show();
            lastClickTime = System.currentTimeMillis();
        } else {
            if ((System.currentTimeMillis() - lastClickTime) > 3000) {  //这里3000，表示两次点击的间隔时间
                Toast.makeText(this, "再点击一下，退出应用", Toast.LENGTH_SHORT).show();
                lastClickTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }

        }

    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    ToastUtil.show(MainActivity.this, showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    public void onEvent(JumpEvent event) {
        if (event.getType() != null) {
            if ("HOME_PAGE".equals(event.getType())) {//首页
                onCheckedChanged(mDataBinding.rgOper, R.id.rd_analysis);
            } else if ("MECHANICAL".equals(event.getType())) {//机械
                onCheckedChanged(mDataBinding.rgOper, R.id.rd_educationadmin);
            } else if ("PARTS".equals(event.getType())) {//配件
                PeiJianActivity.start(MainActivity.this);
            } else if ("AFTER_SALE".equals(event.getType())) {//售后
                ToastUtils.show(MainActivity.this, "该功能正在建设中");
            } else if ("GOLD".equals(event.getType())) {//金服
                onCheckedChanged(mDataBinding.rgOper, R.id.rd_finance);
            } else if ("STEWARD".equals(event.getType())) {//管家
                ToastUtils.show(MainActivity.this, "该功能正在建设中");
            } else if ("RENT".equals(event.getType())) {//租赁
                ToastUtils.show(MainActivity.this, "该功能正在建设中");
            } else if ("PROJECT".equals(event.getType())) {//工程
                ToastUtils.show(MainActivity.this, "该功能正在建设中");
            } else if ("LABOUR".equals(event.getType())) {//劳务
                LabourActivity.start(MainActivity.this);
            } else if ("WEB".equals(event.getType())) {//外部链接

            }
        }
    }

}
