package com.jhhscm.platform.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.http.bean.UserSession;

import java.util.List;


/**
 * Created by Administrator on 2016/5/12.
 */
public class ConfigUtils {
    private static final String CONFIG_VER = "ver";
    private static final String PREFERENCE_NAME = "config";
    private static final String CURRENT_USER = "current_user";
    private static final String LOCATION_BEAN = "location_bean";
    private static final String VIDEO = "video";
    private static final String SHOPPINGDETAILS = "ShoppingDetails";
    private static final String MATERIALSBEAN = "materialsBean";
    private static final String SYSTEM_CONFIG = "system_config";
    private static final String HANDLE_INVALID = "handle_invalid";
    private static final String HTTP_HEADERS_COOKIE = "http_headers_cookie";
    private static final String COOKIES = "cookies";
    private static final String IS_LAUNCH = "is_launch";
    private static final String Depart_Ment_Id = "depart_ment_id";
    private static final String Depart_Ment_Name = "depart_ment_name";
    private static final String Device_Token = "Device_Token";
    private static final String API_URL = "api_url";
    private static final String UPLOAD_URL = "upload_url";
    private static final String USERNAME = "username";
    private static final String MOBILE = "mobile";
    private static final String HOSPITAIL = "Hospitail";
    private static final String C_IM_IS_CAN_USE = "C_IM_IS_CAN_USE";
    private static final String IsCheck = "ISCHECK";
    private static final String ORDERID = "OrderId";
    private static final String HOSPITAILNAME = "HospitailName";
    private static final String HOSPITAILADDRESS = "HospitailAddress";
    private static final String isSkip = "isSkip";
    private static final String HISTORY_SEARCH = "HistorySearch";
    private static final String NEW_MECHANICS = "newMechanics";
    private static final String HOME = "home";
    private static UserSession mCurrentUser;
    private static GetGoodsPageListBean dataBean;//新机浏览历史；最多只存5个
    private static HomePageItem homePageItem;//首页缓存数据

    public static boolean getIsLaunch(Context context) {
        return getSharedPreferences(context).getBoolean(IS_LAUNCH, false);
    }

    public static void setIsLaunch(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putBoolean(IS_LAUNCH, true);
        edit.commit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return getSharedPreferences(context, PREFERENCE_NAME);
    }

    private static SharedPreferences getSharedPreferences(Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static void setCurrentUser(Context context, UserSession user) {
        removeCurrentUser(context);
//        PushAgent.getInstance(context).setAlias(user.getMobile(), "PHONE_TYPE", new UTrack.ICallBack() {
//            @Override
//            public void onMessage(boolean b, String s) {
//                Log.i("", "setAlias: " + s);
//            }
//        });
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

    public static void setHospitail(Context context, String apiUrl) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(HOSPITAIL, apiUrl);
        edit.commit();
    }

    public static String getHospitail(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(HOSPITAIL, "");
    }

    public static void setC_IM_IS_CAN_USE(Context context, String C_IM_IS_CAN_USEs) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(C_IM_IS_CAN_USE, C_IM_IS_CAN_USEs);
        edit.commit();
    }

    public static String getC_IM_IS_CAN_USE(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(C_IM_IS_CAN_USE, "");
    }

    public static void setOrderId(Context context, String apiUrl) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(ORDERID, apiUrl);
        edit.commit();
    }

    public static String getOrderId(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(ORDERID, "");
    }


    public static void setHospitailName(Context context, String apiUrl) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(HOSPITAILNAME, apiUrl);
        edit.commit();
    }

    public static String getHospitailName(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(HOSPITAILNAME, "");
    }

    public static void setHospitailADDRESS(Context context, String apiUrl) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(HOSPITAILADDRESS, apiUrl);
        edit.commit();
    }

    public static String getHospitailADDRESS(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(HOSPITAILADDRESS, "");
    }


    public static void setApiUrl(Context context, String apiUrl) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(API_URL, apiUrl);
        edit.commit();
    }

    public static String getApiUrl(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(API_URL, "");
    }


    public static void setIsSkip(Context context, boolean apiUrl) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putBoolean(isSkip, apiUrl);
        edit.commit();
    }

    public static boolean getIsSkip(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getBoolean(isSkip, false);
    }


    public static void setDepartMentId(Context context, String departMentId) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(Depart_Ment_Id, departMentId);
        edit.commit();
    }

    public static String getDepartMentId(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(Depart_Ment_Id, "");
    }


    public static void setDepartMentName(Context context, String departMentName) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(Depart_Ment_Name, departMentName);
        edit.commit();
    }

    public static String getDepartMentName(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(Depart_Ment_Name, "");
    }


    public static void setDeviceToken(Context context, String deviceToken) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(Device_Token, deviceToken);
        edit.commit();
    }

    public static String getDeviceToken(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(Device_Token, "");
    }


    public static void setUploadUrl(Context context, String apiUrl) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putString(UPLOAD_URL, apiUrl);
        edit.commit();
    }

    public static String getUploadUrl(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getString(UPLOAD_URL, "");
    }

    public static void setHandledInvalid(Context context, boolean handled) {
        SharedPreferences.Editor edit = getSharedPreferences(context, SYSTEM_CONFIG).edit();
        edit.putBoolean(HANDLE_INVALID, handled);
        edit.commit();
    }

    public static boolean isHandledInvalid(Context context) {
        return getSharedPreferences(context, SYSTEM_CONFIG).getBoolean(HANDLE_INVALID, false);
    }


    public static void setHttpHeadersCookie(Context context, String cookie) {
        SharedPreferences.Editor edit = getSharedPreferences(context, COOKIES).edit();
        edit.putString(HTTP_HEADERS_COOKIE, cookie);
        edit.commit();
    }

    public static String getHttpHeadersCookie(Context context) {
        return getSharedPreferences(context, COOKIES).getString(HTTP_HEADERS_COOKIE, "");
    }

    public static void setMoblie(Context context, String moblie) {
        SharedPreferences.Editor edit = getSharedPreferences(context, MOBILE).edit();
        edit.putString(HTTP_HEADERS_COOKIE, moblie);
        edit.commit();
    }

    public static String getMoblie(Context context) {
        return getSharedPreferences(context, MOBILE).getString(HTTP_HEADERS_COOKIE, "");
    }

    public static void setUserName(Context context, String username) {
        SharedPreferences.Editor edit = getSharedPreferences(context, USERNAME).edit();
        edit.putString(HTTP_HEADERS_COOKIE, username);
        edit.commit();
    }

    public static String getUserName(Context context) {
        return getSharedPreferences(context, USERNAME).getString(HTTP_HEADERS_COOKIE, "");
    }

}