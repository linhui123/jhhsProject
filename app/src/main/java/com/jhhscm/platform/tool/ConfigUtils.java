package com.jhhscm.platform.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.coupon.GetNewCouponslistBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.http.bean.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/5/12.
 */
public class ConfigUtils {
    private static final String PREFERENCE_NAME = "config";
    private static final String CURRENT_USER = "current_user";
    private static final String SYSTEM_CONFIG = "system_config";
    private static final String HTTP_HEADERS_COOKIE = "http_headers_cookie";
    private static final String COOKIES = "cookies";
    private static final String IS_LAUNCH = "is_launch";
    private static final String API_URL = "api_url";
    private static final String NEW_MECHANICS = "newMechanics";
    private static final String HOME = "home";
    private static final String LOGIN_TIME = "login_time";//登录时间
    private static final String UPDATA_TIME = "updata_time";//更新提示时间
    private static final String UPDATA_URL = "updata_url";//更新下载安装包地址

    private static final String COUPON_NO_SEE_DATA = "COUPON_NO_SEE_DATA";
    private static List<String> couponList;
    private static String url;
    private static UserSession mCurrentUser;
    private static GetGoodsPageListBean dataBean;//新机浏览历史；最多只存5个
    private static HomePageItem homePageItem;//首页缓存数据

    private static SharedPreferences getSharedPreferences(Context context) {
        return getSharedPreferences(context, PREFERENCE_NAME);
    }

    private static SharedPreferences getSharedPreferences(Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static boolean getIsLaunch(Context context) {
        return getSharedPreferences(context).getBoolean(IS_LAUNCH, false);
    }

    public static void setIsLaunch(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putBoolean(IS_LAUNCH, true);
        edit.commit();
    }

    public static void setCurrentUser(Context context, UserSession user) {
        removeCurrentUser(context);
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putString(CURRENT_USER, userJson);
        edit.commit();
    }

    public static void removeCurrentUser(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.remove(CURRENT_USER);
        edit.commit();
        mCurrentUser = null;
    }

    public synchronized static UserSession getCurrentUser(Context context) {
        if (mCurrentUser == null) {
            String userJson = getSharedPreferences(context).getString(CURRENT_USER, "");
            if (!TextUtils.isEmpty(userJson)) {
                Gson gson = new Gson();
                mCurrentUser = gson.fromJson(userJson, UserSession.class);
            }
        }
        return mCurrentUser;
    }

    public static void setCoupon(Context context, List<String> coupon_list) {
        removeCoupon(context);
        Gson gson = new Gson();
        String userJson = gson.toJson(coupon_list);
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putString(COUPON_NO_SEE_DATA, userJson);
        edit.commit();
    }

    public static void removeCoupon(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.remove(COUPON_NO_SEE_DATA);
        edit.commit();
        couponList = null;
    }

    public synchronized static List<String> getCoupon(Context context) {
        if (couponList == null) {
            String userJson = getSharedPreferences(context).getString(COUPON_NO_SEE_DATA, "");
            if (!TextUtils.isEmpty(userJson)) {
                try {
                    Gson gson = new Gson();
                    couponList = gson.fromJson(userJson, new TypeToken<List<String>>() {
                    }.getType());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return couponList;
    }

    public static void setHomePageItem(Context context, HomePageItem homePageItem) {
        removeHomePageItem(context);
        Gson gson = new Gson();
        String homeJson = gson.toJson(homePageItem);
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putString(HOME, homeJson);
        edit.commit();
    }

    public static void removeHomePageItem(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.remove(HOME);
        edit.commit();
        homePageItem = null;
    }

    public synchronized static HomePageItem getHomePageItem(Context context) {
        if (homePageItem == null) {
            String userJson = getSharedPreferences(context).getString(HOME, "");
            if (!TextUtils.isEmpty(userJson)) {
                Gson gson = new Gson();
                homePageItem = gson.fromJson(userJson, HomePageItem.class);
            }
        }
        return homePageItem;
    }

    public static void setNewMechanics(Context context, GetGoodsPageListBean dataBean) {
        Gson gson = new Gson();
        String userJson = gson.toJson(dataBean);
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putString(NEW_MECHANICS, userJson);
        edit.commit();
    }

    public static void removeNewMechanics(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.remove(NEW_MECHANICS);
        edit.commit();
        dataBean = null;
    }

    public synchronized static GetGoodsPageListBean getNewMechanics(Context context) {
        String userJson = getSharedPreferences(context).getString(NEW_MECHANICS, "");
        if (!TextUtils.isEmpty(userJson)) {
            Gson gson = new Gson();
            dataBean = gson.fromJson(userJson, GetGoodsPageListBean.class);
        }
        return dataBean;
    }

    public static void setApiUrl(Context context, String apiUrl) {
        removeApiUrl(context);
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(API_URL, apiUrl);
        edit.commit();
    }

    public static String getApiUrl(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(API_URL, "");
    }

    public static void removeApiUrl(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.remove(API_URL);
        edit.commit();
    }

    public static void setUpdataTime(Context context, String apiUrl) {
        removeUpdataTime(context);
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(UPDATA_TIME, apiUrl);
        edit.commit();
    }

    public static String getUpdataTime(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(UPDATA_TIME, "");
    }

    public static void removeUpdataTime(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.remove(UPDATA_TIME);
        edit.commit();
    }

    public static void setUpdataUrl(Context context, String apiUrl) {
        removeUpdataUrl(context);
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(UPDATA_URL, apiUrl);
        edit.commit();
    }

    public static String getUpdataUrl(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(UPDATA_URL, "");
    }

    public static void removeUpdataUrl(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.remove(UPDATA_URL);
        edit.commit();
    }

    public static void setHttpHeadersCookie(Context context, String cookie) {
        SharedPreferences.Editor edit = getSharedPreferences(context, COOKIES).edit();
        edit.putString(HTTP_HEADERS_COOKIE, cookie);
        edit.commit();
    }

    public static String getHttpHeadersCookie(Context context) {
        return getSharedPreferences(context, COOKIES).getString(HTTP_HEADERS_COOKIE, "");
    }

}