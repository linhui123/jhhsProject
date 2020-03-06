package com.jhhscm.platform;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.jhhscm.platform.jpush.MyReceiver;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.UrlUtils;
import com.jhhscm.platform.views.AuthImageDownloader;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    private String zulinUrl;
    public static MyApplication instance;
    private static final String TAG = "JIGUANG-Example";
    public IWXAPI api;
    private static String BASE_URL = "";
    private static String BASE_WEB_URL = "";
    private double gaodeLat;
    private double gaodeLon;

    public IWXAPI getApi() {
        return api;
    }

    public void setApi(IWXAPI api) {
        this.api = api;
    }

    //private String umengKey="5d5e074c4ca357dd29000b23";
    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        api = WXAPIFactory.createWXAPI(this, "wx43d03d3271a1c5d4", true);
        api.registerApp("wx43d03d3271a1c5d4");
        initImageLoader(instance);
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(getApplicationContext());
        registerMessageReceiver();
        if (JPushInterface.getRegistrationID(getApplicationContext()) != null) {
            Log.e("JPushInterface", "getRegistrationID : " + JPushInterface.getRegistrationID(getApplicationContext()));
        } else {
            Log.e("JPushInterface", "getRegistrationID 为空");

        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        closeAndroidPDialog();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        //初始化
        UMConfigure.init(this, "5d5e074c4ca357dd29000b23", "", UMConfigure.DEVICE_TYPE_PHONE, "5d5e074c4ca357dd29000b23");
        //开启Log
//        UMConfigure.setLogEnabled(true);
        //打开调试模式
//        MobclickAgent.setDebugMode( true );
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        Log.e("http", "http : " + ConfigUtils.getApiUrl(getInstance().getApplicationContext()));
        BASE_URL = ConfigUtils.getApiUrl(getInstance().getApplicationContext());
        if (ConfigUtils.getWebUrl(getInstance().getApplicationContext()) != null
                && ConfigUtils.getWebUrl(getInstance().getApplicationContext()).length() > 0) {
            BASE_WEB_URL = ConfigUtils.getWebUrl(getInstance().getApplicationContext());
        } else {
            BASE_WEB_URL = "http://api.jhhscm.cn:9095/#/";
        }

//        if (ConfigUtils.getApiUrl(getInstance().getApplicationContext()) != null
//                && ConfigUtils.getApiUrl(getInstance().getApplicationContext()).length() > 0) {
//            BASE_URL = ConfigUtils.getApiUrl(getInstance().getApplicationContext());
//        }
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 解决androidP 第一次打开程序出现莫名弹窗
     * 弹窗内容“detected problems with api ”
     */
    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UnlimitedDiskCache discCache;
    private static final int MAX_MEMORY_CACHE = 8 * 1024 * 1024;

    private synchronized void initImageLoader(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.mipmap.ic_site)
                .showImageOnFail(R.mipmap.ic_site)
                .showImageOnLoading(R.drawable.default_bg)
                .cacheOnDisk(true).considerExifParams(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .memoryCache(new LRULimitedMemoryCache(MAX_MEMORY_CACHE))
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .defaultDisplayImageOptions(options)
                .imageDownloader(new AuthImageDownloader(context))
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().handleSlowNetwork(true);
    }

    public void registerMessageReceiver() {
        MyReceiver mMessageReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("1");
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("1".equals(intent.getAction())) {
                Log.e("JPushInterface", "onReceive : ");

            }
        }
    }

    public String getZulinUrl() {
        return zulinUrl;
    }

    public void setZulinUrl(String zulinUrl) {
        this.zulinUrl = zulinUrl;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static String getBaseWebUrl() {
        return BASE_WEB_URL;
    }

    public static void setBaseWebUrl(String baseWebUrl) {
        BASE_WEB_URL = baseWebUrl;
    }

    public double getGaodeLat() {
        return gaodeLat;
    }

    public void setGaodeLat(double gaodeLat) {
        this.gaodeLat = gaodeLat;
    }

    public double getGaodeLon() {
        return gaodeLon;
    }

    public void setGaodeLon(double gaodeLon) {
        this.gaodeLon = gaodeLon;
    }
}
