package com.jhhscm.platform.http.sign;

import android.content.Context;
import android.util.Log;

import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.tool.Des;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sign {

    private static Sign ourInstance = new Sign();
    private static String encryptKey = "77e8de7c098d444b9ca87b20a714e025";
    private static String signKey = "e803c0a2e711435880f73e2744517263";
    private static String appId = "336abf9e97cd4276bf8aecde9d32ed99";
    private static String appSecret = "27f7c720e0f440ce877e69573781d8ea";

    private Sign() {
    }

    public static Sign getInstance() {
        return ourInstance;
    }

    /**
     * 获取签名
     */
    public static String getSignKey(Context context, Map<String, String> map, String method) {
        String sign = "";
        String des = "";
        // key 排序
        List<Map.Entry<String, String>> list = sort(map);
        // 排除空值
        list = delNull(list);
        //排除
        list = Exclude(list);
        //拼接
        sign = Stitching(list, method);
        //除空
        sign = replaceAllBlank(sign, method);
        des = Des.md5Decode(sign).toUpperCase();
        return des;
    }

    /**
     * 登陆签名
     */
    public static String LoginSign(Context context, String mobile,
                                   String veriCode) {
        String sign = "";
        String des = "";
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        map.put("veriCode", veriCode);
        map.put("secret", appSecret);
        map.put("appid", appId);

        // key 排序
        List<Map.Entry<String, String>> list = sort(map);
        //排除
        list = Exclude(list);
        //拼接
        sign = Stitching(list, "login");
        //除空
        sign = replaceAllBlank(sign, "login");
        des = Des.md5Decode(sign).toUpperCase();
        return des;
    }

    /**
     * 获取验证码签名
     */
    public static String GetCodeSign(Context context, String mobile) {
        String sign = "";
        String des = "";
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        // key 排序
        List<Map.Entry<String, String>> list = sort(map);
        //排除
        list = Exclude(list);
        //拼接
        sign = Stitching(list, "GetCode");
        //除空
        sign = replaceAllBlank(sign, "GetCode");
        des = Des.md5Decode(sign).toUpperCase();
        return des;
    }

    /**
     * 排除空值
     */
    private static List<Map.Entry<String, String>> delNull(List<Map.Entry<String, String>> entryList) {
        List<Map.Entry<String, String>> list = new ArrayList<>();
        //查询value为空的值
        for (Map.Entry<String, String> m : entryList) {
            if (m.getValue() == null) {
                list.add(m);
            } else if (m.getValue().length() == 0) {
                list.add(m);
            }
        }
        //删除value为空的值
        for (Map.Entry<String, String> m : list) {
            entryList.remove(m);
        }
        return entryList;
    }

    /**
     * Map<String, String> 排序
     */
    private static List<Map.Entry<String, String>> sort(Map<String, String> map) {
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        return list;
    }

    /**
     * Map<String, String> 排除
     */
    private static List<Map.Entry<String, String>> Exclude(List<Map.Entry<String, String>> list) {
        List<Map.Entry<String, String>> entryList = new ArrayList<>();
        for (Map.Entry<String, String> m : list) {
            if (m.getKey().equals("sign")) {
                entryList.add(m);
            }
            if (m.getKey().equals("token")) {
                entryList.add(m);
            }
        }

        //删除value为空的值
        for (Map.Entry<String, String> m : entryList) {
            list.remove(m);
        }
        return list;
    }

    /**
     * Map<String, String> 拼接
     */
    private static String Stitching(List<Map.Entry<String, String>> list, String method) {
        String sign = "";
        for (int i = 0; i < list.size(); i++) {
            String key = list.get(i).getKey();
            if (i == list.size() - 1) {//拼接时，不包括最后一个&字符
                sign = sign + key + "=" + list.get(i).getValue();
            } else {
                sign = sign + key + "=" + list.get(i).getValue() + "&";
            }
        }
        if (sign.length() > 0) {
            sign = sign + "&key=" + signKey;
        } else {
            sign = sign + "key=" + signKey;
        }


//        if (BuildConfig.DEBUG) {
//            Log.e("http :" + method, " sign :" + sign);
//        }
        return sign;
    }

    /**
     * 获取微信签名
     */
    public static String getWXSignKey(Context context, Map<String, String> map, String method) {
        String sign = "";
        String des = "";
        // key 排序
        List<Map.Entry<String, String>> list = sort(map);
        // 排除空值
        list = delNull(list);
        //排除
        list = Exclude(list);
        //拼接
        sign = WXStitching(list, method);
        //除空
        sign = replaceAllBlank(sign, method);
        des = Des.md5Decode(sign).toUpperCase();
        return des;
    }

    /**
     * Map<String, String> 拼接
     */
    private static String WXStitching(List<Map.Entry<String, String>> list, String method) {
        String sign = "";
        for (int i = 0; i < list.size(); i++) {
            String key = list.get(i).getKey();
            if (i == list.size() - 1) {//拼接时，不包括最后一个&字符
                sign = sign + key + "=" + list.get(i).getValue();
            } else {
                sign = sign + key + "=" + list.get(i).getValue() + "&";
            }
        }
        //商户key
        sign = sign + "&key=" + "abcdefghABCDEFGH1234567812345678";
//        if (BuildConfig.DEBUG) {
//            Log.e("http :" + method, " sign :" + sign);
//        }
        return sign;
    }


    /**
     * 去除所有空格 换行
     */
    public static String replaceAllBlank(String str, String mehtod) {
        String s = "";
        if (str != null) {
//            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//            Pattern p = Pattern.compile("\\t|\r|\n");
           /*\n 回车(\u000a)
          \t 水平制表符(\u0009)
          \s 空格(\u0008)
          \r 换行(\u000d)*/
//            Matcher m = p.matcher(str);
//            s = m.replaceAll("");
            if (BuildConfig.DEBUG) {
                Log.e("http :" + mehtod, " sign :" + str);
            }
        }
        return str;
    }
}