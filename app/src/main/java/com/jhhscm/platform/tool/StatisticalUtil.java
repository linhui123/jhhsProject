package com.jhhscm.platform.tool;

import android.content.Context;

import com.jhhscm.platform.BuildConfig;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by Administrator on 2017/10/27.
 */
public class StatisticalUtil {

    private static boolean debug = BuildConfig.DEBUG;

    public static void init(Context context) {
        MobclickAgent.setDebugMode(debug);
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    public static void onPageStart(Context context) {
        MobclickAgent.onResume(context);
    }

    public static void onPageEnd(Context context) {
        MobclickAgent.onPause(context);
    }

    public static void onPageStart(String name) {
        MobclickAgent.onPageStart(name);
    }

    public static void onPageEnd(String name) {
        MobclickAgent.onPageEnd(name);
    }

}
