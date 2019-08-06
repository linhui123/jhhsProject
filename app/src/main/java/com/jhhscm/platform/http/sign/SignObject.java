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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignObject {

    private static SignObject ourInstance = new SignObject();
    private static String encryptKey = "77e8de7c098d444b9ca87b20a714e025";
    private static String signKey = "e803c0a2e711435880f73e2744517263";
    private static String appId = "336abf9e97cd4276bf8aecde9d32ed0a";
    private static String appSecret = "27f7c720e0f440ce877e69573781d8ea";

    private SignObject() {
    }

    public static SignObject getInstance() {
        return ourInstance;
    }

    /**
     * 获取签名
     */
    public static String getSignKey(Context context, Map<String, Object> map, String method) {
        String sign = "";
        String des = "";
        // key 排序
        List<Map.Entry<String, Object>> list = sort(map);
        // 排除空值
        list = delNull(list);
        //排除
        list = Exclude(list);
        //拼接
        sign = Stitching(list, method);
        //除空
        sign = replaceAllBlank(sign);
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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        map.put("veriCode", veriCode);
        map.put("secret", appSecret);
        map.put("appid", appId);

        // key 排序
        List<Map.Entry<String, Object>> list = sort(map);
        //排除
        list = Exclude(list);
        //拼接
        sign = Stitching(list, "login");
        //除空
        sign = replaceAllBlank(sign);
        des = Des.md5Decode(sign).toUpperCase();
        return des;
    }

    /**
     * 获取验证码签名
     */
    public static String GetCodeSign(Context context, String mobile) {
        String sign = "";
        String des = "";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        // key 排序
        List<Map.Entry<String, Object>> list = sort(map);
        //排除
        list = Exclude(list);
        //拼接
        sign = Stitching(list, "");
        //除空
        sign = replaceAllBlank(sign);
        des = Des.md5Decode(sign).toUpperCase();
        return des;
    }

    /**
     * 排除空值
     */
    private static List<Map.Entry<String, Object>> delNull(List<Map.Entry<String, Object>> entryList) {
        List<Map.Entry<String, Object>> list = new ArrayList<>();
        //查询value为空的值
        for (Map.Entry<String, Object> m : entryList) {
            if (m.getValue() == null) {
                list.add(m);
            }
            if (m.getValue() instanceof String) {
                if (((String)m.getValue()).length() == 0){
                    list.add(m);
                }
            }
        }
        //删除value为空的值
        for (Map.Entry<String, Object> m : list) {
            entryList.remove(m);
        }
        return entryList;
    }

    /**
     * Map<String, String> 排序
     */
    private static List<Map.Entry<String, Object>> sort(Map<String, Object> map) {
        List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        return list;
    }

    /**
     * Map<String, String> 排除
     */
    private static List<Map.Entry<String, Object>> Exclude(List<Map.Entry<String, Object>> list) {
        List<Map.Entry<String, Object>> entryList = new ArrayList<>();
        for (Map.Entry<String, Object> m : list) {
            if (m.getKey().equals("sign")) {
                entryList.add(m);
            }
            if (m.getKey().equals("token")) {
                entryList.add(m);
            }
        }

        //删除value为空的值
        for (Map.Entry<String, Object> m : entryList) {
            list.remove(m);
        }
        return list;
    }

    /**
     * Map<String, String> 拼接
     */
    private static String Stitching(List<Map.Entry<String, Object>> list, String method) {
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


        if (BuildConfig.DEBUG) {
            Log.e("http :" + method, " sign :" + sign);
        }
        return sign;
    }

    /**
     * 获取微信签名
     */
    public static String getWXSignKey(Context context, Map<String, Object> map, String method) {
        String sign = "";
        String des = "";
        // key 排序
        List<Map.Entry<String, Object>> list = sort(map);
        // 排除空值
        list = delNull(list);
        //排除
        list = Exclude(list);
        //拼接
        sign = WXStitching(list, method);
        //除空
        sign = replaceAllBlank(sign);
        des = Des.md5Decode(sign).toUpperCase();
        return des;
    }

    /**
     * Map<String, String> 拼接
     */
    private static String WXStitching(List<Map.Entry<String, Object>> list, String method) {
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
        if (BuildConfig.DEBUG) {
            Log.e("http :" + method, " sign :" + sign);
        }
        return sign;
    }


    /**
     * 去除所有空格 换行
     */
    public static String replaceAllBlank(String str) {
        String s = "";
        if (str != null) {
//            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Pattern p = Pattern.compile("\\|\t|\r|\n");
           /*\n 回车(\u000a)
          \t 水平制表符(\u0009)
          \s 空格(\u0008)
          \r 换行(\u000d)*/
            Matcher m = p.matcher(str);
            s = m.replaceAll("");
        }
        return s;
    }
}
