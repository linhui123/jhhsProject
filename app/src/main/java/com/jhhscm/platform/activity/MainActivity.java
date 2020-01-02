package com.jhhscm.platform.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsActivity;
import com.jhhscm.platform.activity.h5.FinancialH5Activity;
import com.jhhscm.platform.activity.h5.H5Activity;
import com.jhhscm.platform.activity.h5.ZuLinFragment;
import com.jhhscm.platform.databinding.ActivityMainBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.event.JumpEvent;
import com.jhhscm.platform.event.ShowBackEvent;
import com.jhhscm.platform.fragment.Mechanics.MechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.PeiJianFragment;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageFragment;
import com.jhhscm.platform.fragment.my.MyFragment;
import com.jhhscm.platform.jpush.ExampleUtil;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.HomeAlterDialog;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import static android.app.Notification.EXTRA_CHANNEL_ID;
import static android.provider.Settings.EXTRA_APP_PACKAGE;

public class MainActivity extends AbsActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private ActivityMainBinding mDataBinding;
    /**
     * 创建一个集合，存储碎片
     */
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private HomePageFragment homeFragment;
    private MechanicsFragment mechanicsFragment;
    private PeiJianFragment peiJianFragment;
    //        private FinancialFragment financialFragment;
//    private ZuLin2Fragment zuLinFragment;
    private ZuLinFragment zuLinFragment;
    private MyFragment mMeFragment;

    private FragmentManager fm;
    FragmentTransaction transaction;
    private FragmentTransaction transaction1;
    public static boolean isForeground = false;
    private long lastClickTime = 0;
    public static MainActivity instance;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.jhhscm.platform.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        instance = this;
        initView();
        registerMessageReceiver();
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
//            mechanicsFragment = new MechanicsFragment();
///          fragmentManager.beginTransaction().add(R.id.fl, mechanicsFragment, "mechanicsFragment").commit();
            peiJianFragment = new PeiJianFragment();
            fragmentManager.beginTransaction().add(R.id.fl, peiJianFragment, "peiJianFragment").commit();
            homeFragment = new HomePageFragment();
            fragmentManager.beginTransaction().add(R.id.fl, homeFragment, "homeFragment").commit();
        }
        checkNotifySetting();
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
                .setDeniedMessage(getApplicationContext().getString(R.string.permission_denied_txt, "读写"))
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).build(), new AcpListener() {
            @Override
            public void onGranted() {
                initView();
            }

            @Override
            public void onDenied(List<String> permissions) {
                initView();
            }
        });
    }

    private void initView() {
        EventBusUtil.registerEvent(this);
        mDataBinding.rgOper.setOnCheckedChangeListener(this);
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        homeFragment = (HomePageFragment) fm.findFragmentByTag("homeFragment");
        peiJianFragment = (PeiJianFragment) fm.findFragmentByTag("peiJianFragment");
//        mechanicsFragment = (MechanicsFragment) fm.findFragmentByTag("mechanicsFragment");
        zuLinFragment = (ZuLinFragment) fm.findFragmentByTag("zuLinFragment");
        mMeFragment = (MyFragment) fm.findFragmentByTag("mMeFragment");
        mDataBinding.rdExpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getApplicationContext(), "add_Fragment");
                new HomeAlterDialog(MainActivity.this, new HomeAlterDialog.CallbackListener() {

                    @Override
                    public void clickF() {
                        SaleMachineActivity.start(MainActivity.this);
                    }

                    @Override
                    public void clickS() {
                        if (ConfigUtils.getCurrentUser(MainActivity.this) != null
                                && ConfigUtils.getCurrentUser(MainActivity.this).getMobile() != null
                                && ConfigUtils.getCurrentUser(MainActivity.this).getUserCode() != null) {
                            PushZhaoPinActivity.start(MainActivity.this, "", "", 0);
                        } else {
                            startNewActivity(LoginActivity.class);
                        }
                    }

                    @Override
                    public void clickT() {
                        if (ConfigUtils.getCurrentUser(MainActivity.this) != null
                                && ConfigUtils.getCurrentUser(MainActivity.this).getMobile() != null
                                && ConfigUtils.getCurrentUser(MainActivity.this).getUserCode() != null) {
                            PushQiuZhiActivity.start(MainActivity.this, "", "", 0);
                        } else {
                            startNewActivity(LoginActivity.class);
                        }
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
//        if (mechanicsFragment != null) {
//            transaction1.hide(mechanicsFragment);
//        }
        if (peiJianFragment != null) {
            transaction1.hide(peiJianFragment);
        }
        if (zuLinFragment != null) {
            transaction1.hide(zuLinFragment);
        }
        if (mMeFragment != null) {
            transaction1.hide(mMeFragment);
        }

        if (checkedId == R.id.rd_analysis) {
            MobclickAgent.onEvent(getApplicationContext(), "home_Fragment");
            if (homeFragment == null) {
                homeFragment = new HomePageFragment();
                transaction1.add(R.id.fl, homeFragment, "homeFragment");
            } else {
                transaction1.show(homeFragment);
            }

        }
//        else if (checkedId == R.id.rd_educationadmin) {
//            MobclickAgent.onEvent(getApplicationContext(), "mechanics_Fragment");
//            EventBusUtil.post(new ShowBackEvent(0));
//            if (mechanicsFragment == null) {
//                mechanicsFragment = new MechanicsFragment();
//                transaction1.add(R.id.fl, mechanicsFragment, "mechanicsFragment");
//            } else {
//                transaction1.show(mechanicsFragment);
//            }
//
//        }
        else if (checkedId == R.id.rd_peijian) {
            MobclickAgent.onEvent(getApplicationContext(), "peiJianFragment");
            EventBusUtil.post(new ShowBackEvent(0));
            if (peiJianFragment == null) {
                peiJianFragment = new PeiJianFragment();
                transaction1.add(R.id.fl, peiJianFragment, "peiJianFragment");
            } else {
                transaction1.show(peiJianFragment);
            }
        } else if (checkedId == R.id.rd_finance) {
            MobclickAgent.onEvent(getApplicationContext(), "financial_Fragment");
            EventBusUtil.post(new ShowBackEvent(0));
            if (zuLinFragment == null) {
                zuLinFragment = new ZuLinFragment();
                transaction1.add(R.id.fl, zuLinFragment, "zuLinFragment");
            } else {
                transaction1.show(zuLinFragment);
            }
        } else if (checkedId == R.id.rd_me) {
            MobclickAgent.onEvent(getApplicationContext(), "my_Fragment");
            if (mMeFragment == null) {
                mMeFragment = new MyFragment();
                transaction1.add(R.id.fl, mMeFragment, "mMeFragment");
            } else {
                transaction1.show(mMeFragment);
            }
        }
        transaction1.commitAllowingStateLoss();
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
//        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
//        MobclickAgent.onPause(this);
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
                mDataBinding.rdAnalysis.setChecked(true);
            } else if ("MECHANICAL".equals(event.getType())) {//机械
                MobclickAgent.onEvent(getApplicationContext(), "mechanics_button_home");
                if (event.getBrand_id() != null) {
                    EventBusUtil.post(new BrandResultEvent(event.getBrand_id(), event.getBrand_name(), 0));
                    MechanicsActivity.start(MainActivity.this, event.getBrand_id(), event.getBrand_name());
                } else {
                    MechanicsActivity.start(MainActivity.this);
                }
                EventBusUtil.post(new ShowBackEvent(2));
//                onCheckedChanged(mDataBinding.rgOper, R.id.rd_educationadmin);
//                mDataBinding.rdEducationadmin.setChecked(true);
//                EventBusUtil.post(new ShowBackEvent(2));
            } else if ("PARTS".equals(event.getType())) {//配件
                MobclickAgent.onEvent(getApplicationContext(), "parts_button_home");
//                PeiJianActivity.start(MainActivity.this);
                if (event.getBrand_id() != null) {
                    EventBusUtil.post(new BrandResultEvent(event.getBrand_id(), event.getBrand_name(), 2));
                } else {
                    EventBusUtil.post(new BrandResultEvent("", "品类", 2));
                }
                onCheckedChanged(mDataBinding.rgOper, R.id.rd_peijian);
                mDataBinding.rdPeijian.setChecked(true);
                EventBusUtil.post(new ShowBackEvent(2));
            } else if ("AFTER_SALE".equals(event.getType())) {//售后
                MobclickAgent.onEvent(getApplicationContext(), "after_button_home");
                AfterSaleActivity.start(MainActivity.this);
            } else if ("GOLD".equals(event.getType())) {//金服
                MobclickAgent.onEvent(getApplicationContext(), "gold_button_home");
                Gson gson = new Gson();
                AdBean.ResultBean adBean = gson.fromJson(event.getResultBean().getContent(), AdBean.ResultBean.class);
                if (adBean.getPARAM() != null) {
                    //UrlUtils.JF
                    FinancialH5Activity.start(MainActivity.this, adBean.getPARAM().getHREF_URL(), "金服");
                } else {
//                    ToastUtils.show(MainActivity.this, "地址为空");
                }
//                onCheckedChanged(mDataBinding.rgOper, R.id.rd_finance);
//                mDataBinding.rdFinance.setChecked(true);
            } else if ("STEWARD".equals(event.getType())) {//管家
                MobclickAgent.onEvent(getApplicationContext(), "steward_button_home");
                VehicleMonitoringActivity.start(MainActivity.this);
            } else if ("RENT".equals(event.getType())) {//租赁
                MobclickAgent.onEvent(getApplicationContext(), "rent_button_home");
//                ZuLinH5Activity.start(MainActivity.this, UrlUtils.ZL, "租赁");
                onCheckedChanged(mDataBinding.rgOper, R.id.rd_finance);
                mDataBinding.rdFinance.setChecked(true);
                EventBusUtil.post(new ShowBackEvent(1));
            } else if ("PROJECT".equals(event.getType())) {//工程
                MobclickAgent.onEvent(getApplicationContext(), "project_button_home");
                ToastUtils.show(MainActivity.this, "该功能正在建设中");
            } else if ("LABOUR".equals(event.getType())) {//劳务
                MobclickAgent.onEvent(getApplicationContext(), "labour_button_home");
                LabourActivity.start(MainActivity.this);
            } else if ("WEB".equals(event.getType())) {//外部链接
                Gson gson = new Gson();
                AdBean.ResultBean adBean = gson.fromJson(event.getResultBean().getContent(), AdBean.ResultBean.class);
                if (adBean.getPARAM() != null) {
                    Log.e("WEB", "外部链接：" + adBean.getPARAM().getHREF_URL());
                    H5Activity.start(MainActivity.this, adBean.getPARAM().getHREF_URL());
                } else {
//                    ToastUtils.show(MainActivity.this, "地址为空");
                }
            }
        }
    }

    /**
     * 检查是否已经开启了通知权限
     */
    private void checkNotifySetting() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        boolean isOpened = manager.areNotificationsEnabled();
        Log.e("checkNotifySetting", "isOpened :" + isOpened);
        if (!isOpened) {
            try {
                // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                intent.putExtra(EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(EXTRA_CHANNEL_ID, getApplicationInfo().uid);

                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                intent.putExtra("app_package", getPackageName());
                intent.putExtra("app_uid", getApplicationInfo().uid);

                // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"
                if ("MI 6".equals(Build.MODEL)) {
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    // intent.setAction("com.android.settings/.SubSettings");
                }
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
                Intent intent = new Intent();

                //下面这种方案是直接跳转到当前应用的设置界面。
                //https://blog.csdn.net/ysy950803/article/details/71910806
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }

}
