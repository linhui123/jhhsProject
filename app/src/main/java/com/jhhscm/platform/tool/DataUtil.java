package com.jhhscm.platform.tool;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataUtil {

    public static Date getStringToData(String s, String pattern) {
        java.text.SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = formatter.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

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

    /**
     * 获取当前时间
     *
     * @param pattern
     * @return
     */
    public static String getCurDate(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new java.util.Date());
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate, String pattern) throws ParseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            return 4;//大于三天
        }
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            return 4;//大于三天
        }
    }
}
