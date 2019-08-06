package com.jhhscm.platform.tool;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataUtil {

    /**
     * 时间差计算
     * 传入字串类型 pattern
     */
    public static String getTimeExpend(String startTime, String endTime, String pattern) {
        long longStart = getTimeMillis(startTime, pattern); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime, pattern);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
        long longS = (longExpend - longHours * (60 * 60 * 1000) - longMinutes * (60 * 1000)) / (1000);   //根据时间差来计算秒数
        String H = longHours < 10 ? "0" + longHours : longHours + "";
        String M = longMinutes < 10 ? "0" + longMinutes : longMinutes + "";
        String S = longS < 10 ? "0" + longS : longS + "";
        return H + ":" + M + ":" + S;
    }

    public static String getTimeExpend(long startTime, String endTime, String pattern) {
        long longStart = startTime; //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime, pattern);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差
        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
        long longS = (longExpend - longHours * (60 * 60 * 1000) - longMinutes * (60 * 1000)) / (1000);   //根据时间差来计算秒数
        String H = longHours < 10 ? "0" + longHours : longHours + "";
        String M = longMinutes < 10 ? "0" + longMinutes : longMinutes + "";
        String S = longS < 10 ? "0" + longS : longS + "";
        return H + ":" + M + ":" + S;
    }

    /**
     * 时间差计算
     */
    public static String getLongToTime(long longTime, String pattern) {
        long longExpend = longTime;  //获取时间差
        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
        long longS = (longExpend - longHours * (60 * 60 * 1000) - longMinutes * (60 * 1000)) / (1000);   //根据时间差来计算秒数
        String H = longHours < 10 ? "0" + longHours : longHours + "";
        String M = longMinutes < 10 ? "0" + longMinutes : longMinutes + "";
        String S = longS < 10 ? "0" + longS : longS + "";
        return H + ":" + M + ":" + S;
    }

    /**
     * 时间差计算
     */
    public static Long getLongTime(String startTime, String endTime, String pattern) {
        long longStart = getTimeMillis(startTime, pattern); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime, pattern);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差
        return longExpend;
    }

    public static Long getLongTime(long startTime, String endTime, String pattern) {
        long longStart = startTime; //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime, pattern);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差
        return longExpend;
    }

    /**
     * 时间转化毫秒
     */
    public static long getTimeMillis(String strTime, String pattern) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return returnMillis;
    }
}
