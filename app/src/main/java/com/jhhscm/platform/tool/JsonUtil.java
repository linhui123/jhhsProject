package com.jhhscm.platform.tool;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * JSON转换类
 *
 * @author 王成斌
 */
public class JsonUtil {

    private static Gson gson = new Gson();

    /**
     * 将json转换成list
     *
     * @param jsonStr
     * @return
     */
    public static List<Map<String, String>> jsonToList(String jsonStr) {
        List<Map<String, String>> list = null;
        try {
            list = gson.fromJson(jsonStr, new TypeToken<List<Map<String, String>>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将json转换成Map<String, Object>
     *
     * @param jsonStr
     * @return Map<String, Object>
     */
    public static Map<String, Object> jsonToMapObj(String jsonStr) {
        Map<String, Object> map = null;
        try {
            map = gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将json转换成Map<String, String>
     *
     * @param jsonStr
     * @return Map<String, String>
     */
    public static Map<String, String> jsonToMap(String jsonStr) {
        Map<String, String> map = null;
        try {
            map = gson.fromJson(jsonStr, new TypeToken<Map<String, String>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将bean转换成json
     *
     * @param bean
     * @return Map<String, String>
     */
    public static String beanToJson(Object bean) {
        String str = null;
        try {
            str = gson.toJson(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

}
