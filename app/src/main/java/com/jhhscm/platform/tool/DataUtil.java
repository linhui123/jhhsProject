package com.jhhscm.platform.tool;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
     * 时间差计算: H + ":" + M + ":" + S
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
     * 时间差计算  H + ":" + M + ":" + S
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
     * 获取剩余收货时间
     */
    public static Long getLongTimeReceive(long startTime, String endTime, long keepDay, String pattern) {
        long longStart = startTime; //获取开始时间毫秒数
        long keepTime = keepDay * 24 * 60 * 60 * 1000;
        long longEnd = keepTime + getTimeMillis(endTime, pattern);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差
        Log.e("getLongTimeReceive", "keepDay    " + keepDay);
        Log.e("getLongTimeReceive", "longStart  " + longStart);
        Log.e("getLongTimeReceive", "endTime    " + getTimeMillis(endTime, pattern));
        Log.e("getLongTimeReceive", "keepTime   " + keepTime);
        Log.e("getLongTimeReceive", "longEnd    " + longEnd);
        Log.e("getLongTimeReceive", "longExpend " + longExpend);
        return longExpend;
    }

    /**
     * keepDay 默认收货天数
     * 1天=86400000毫秒
     * 获取剩余收货时间-时间差计算（中文提示)
     */
    public static String getReceiveDay(long longTime, String pattern) {
        long longExpend = longTime;//剩余自动收货时间

        long longDay = longExpend / (24 * 60 * 60 * 1000); //根据时间差来计算天数
        long longHours = (longExpend - longDay * (24 * 60 * 60 * 1000)) / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longDay * (24 * 60 * 60 * 1000) - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
        return longDay + "天" + longHours + "小时" + longMinutes + "分钟";
    }

    /**
     * 时间差计算（中文提示hh.mm.ss)
     */
    public static String getLongTZ(long longTime, String pattern) {
        long longExpend = longTime;  //获取时间差
        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
        return longHours + "小时" + longMinutes + "分钟";
    }

    /**
     * 时间差计算(小时差)
     */
    public static String getLongToHours(long longTime, String pattern) {
        long longExpend = longTime;  //获取时间差
        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        return longHours + "";
    }

    /**
     * 时间差计算(分钟差)
     */
    public static String getLongToMintues(long longTime, String pattern) {
        long longExpend = longTime;  //获取时间差
        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
        return longMinutes + "";
    }

    /**
     * 时间差计算(天)
     */
    public static String getLongToDays(long longTime, String pattern) {
        long longExpend = longTime;  //获取时间差
        long longDays = longExpend / (60 * 60 * 1000 * 24); //根据时间差来计算天数
        return longDays + "";
    }

    /**
     * 时间差计算(毫秒)
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


    /**
     * 时间大小比较
     * end 不小于 start
     */
    public static boolean TimeCompare(String start, String end, String pattern) {
        //格式化时间
        SimpleDateFormat CurrentTime = new SimpleDateFormat(pattern);
        try {
            Date beginTime = CurrentTime.parse(start);
            Date endTime = CurrentTime.parse(end);
            //判断是否大于两天
            if (endTime.getTime() >= beginTime.getTime()) {
                //end 不小于 start;
                return true;
            } else {
                //end 小于 start;
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获得上月第一天时间
    public static String getLastMonthmorning() {
        Calendar cal = Calendar.getInstance();
        if (Calendar.MONTH > 1) {//大于一月
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, cal.get(Calendar.DAY_OF_MONTH));
        } else {//一月，提前一年
            cal.set(cal.get(Calendar.YEAR) - 1, cal.get(Calendar.MONTH) - 1, cal.get(Calendar.DAY_OF_MONTH));
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getDateStr(cal.getTime(), "yyyy.MM.dd");
    }

    // 获得上月最后一天时间
    public static String getLastMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDateStr(cal.getTime(), "yyyy.MM.dd");
    }

    // 获得本月第一天时间
    public static String getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getDateStr(cal.getTime(), "yyyy.MM.dd");
    }

    // 获得本月最后一天时间
    public static String getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDateStr(cal.getTime(), "yyyy.MM.dd");
    }

    // 获得本年第一天时间
    public static String getTimesYearmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getDateStr(cal.getTime(), "yyyy.MM.dd");
    }

    // 获得本年最后一天时间
    public static String getTimesYearnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDateStr(cal.getTime(), "yyyy.MM.dd");
    }

    public static String getDateStr(Date date, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static String getDateStr(String date, String format) {
        Date beginTime = new Date();
        //格式化时间
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        beginTime = CurrentTime.parse(date, pos);

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(beginTime);
    }
}
