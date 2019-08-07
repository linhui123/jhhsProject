package com.jhhscm.platform.tool;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by intel on 2018/11/28.
 */

public class DateUtils {
    /**
     * 获取系统时间戳
     *
     * @return
     */
    public static long getCurTimeLong() {
        long time = System.currentTimeMillis();
        return time;
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
     * 获取月初日期 (yyyy-MM-dd)
     */
    public static String getMonthStartDate(String time, String pattern) {
        Calendar ca = Calendar.getInstance();
        Date date = new Date();
        date.setTime(getStringToDate(time, pattern));
        ca.setTime(date);
        int month = ca.get(Calendar.MONTH) + 1;
        String monthTime = "";
        String dayTime = "";
        if (month < 10) {
            monthTime = "0" + String.valueOf(month);
        } else {
            monthTime = String.valueOf(month);
        }
        if (ca.getActualMinimum(Calendar.DAY_OF_MONTH) < 10) {
            dayTime = "0" + ca.getActualMinimum(Calendar.DAY_OF_MONTH);
        } else {
            dayTime = String.valueOf(ca.getActualMinimum(Calendar.DAY_OF_MONTH));
        }
        String startTime = ca.get(Calendar.YEAR) + "-" + monthTime + "-" + dayTime;
        return startTime;
    }

    /**
     * 获取月末日期
     */
    public static String getMonthEndDate(String time, String pattern) {
        Calendar ca = Calendar.getInstance();
        Date date = new Date();
        date.setTime(getStringToDate(time, pattern));
        ca.setTime(date);
        int month = ca.get(Calendar.MONTH) + 1;

        String monthTime = "";
        String dayTime = "";
        if (month < 10) {
            monthTime = "0" + String.valueOf(month);
        } else {
            monthTime = String.valueOf(month);
        }
        String endTime = ca.get(Calendar.YEAR) + "-" + monthTime + "-" + ca.getActualMaximum(Calendar.DAY_OF_MONTH);
        return endTime;
    }

    /**
     * 获取上个月的月末日期
     *
     * @return
     */
    public static String getLastMonthEndDate(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        //获取今天的日期
        int currentDate = Integer.parseInt(getCurDate("dd"));
        ca.add(Calendar.DAY_OF_MONTH, -currentDate);
        Date lastMonth = ca.getTime(); //结果
        return sDateFormat.format(lastMonth);
    }

    /**
     * 获取上个月月初的日期,格式最后要01  "yyyy-MM-01"
     *
     * @param pattern
     * @return
     */
    public static String getLastMonthStartDate(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.MONTH, -1);
        Date lastMonth = ca.getTime(); //结果
        return sDateFormat.format(lastMonth);
    }

    /**
     * 将短时间格式字符串  转换为其他时间格式
     *
     * @param strDate
     * @return
     */
    public static String strToDate(String strDate, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return formatter.format(strtodate);
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param milSecond
     * @param pattern
     * @return
     */
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 获取本月月末时间
     */
    public static String getMonthEndDate(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        //获取今天的日期
        int currentDate = Integer.parseInt(getCurDate("dd"));
        ca.add(Calendar.DAY_OF_MONTH, 1 - currentDate);//现在变成了本月的一号

        //一下一个月月份
        ca.add(Calendar.MONTH, 1);//加一个月
        ca.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = ca.getTime(); //结果
        return sDateFormat.format(monthEnd);
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateString
     * @param pattern    例如yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static int getWeek(long time) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(time));

        int year = cd.get(Calendar.YEAR); //获取年份
        int month = cd.get(Calendar.MONTH); //获取月份
        int day = cd.get(Calendar.DAY_OF_MONTH); //获取日期
        int week = cd.get(Calendar.DAY_OF_WEEK); //获取星期

        int weekString;
        switch (week) {
            case Calendar.SUNDAY:
                weekString = 7;
                break;
            case Calendar.MONDAY:
                weekString = 1;
                break;
            case Calendar.TUESDAY:
                weekString = 2;
                break;
            case Calendar.WEDNESDAY:
                weekString = 3;
                break;
            case Calendar.THURSDAY:
                weekString = 4;
                break;
            case Calendar.FRIDAY:
                weekString = 5;
                break;
            default:
                weekString = 6;
                break;

        }
        return weekString;
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
