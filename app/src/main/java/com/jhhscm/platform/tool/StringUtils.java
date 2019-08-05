package com.jhhscm.platform.tool;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jhhscm.platform.R;
import com.jhhscm.platform.permission.YXPermission;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

/**
 * 类说明：字符串操作相关工具类
 * 作者：yangxiaoping on 2016/3/29 09:05
 * 邮箱：82650979@qq.com
 */
public class StringUtils {
    /**
     * 判断是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNullEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 过滤掉空字符串以及前后截取空串
     *
     * @param str
     * @return
     */
    public static String filterNullAndTrim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 请求内容转义
     * (
     * &-----{:chr38:}
     * <p>
     * <p>
     * )
     */
    public static String escParamContent(String content) {
        if (content == null) return "";
        return content.replaceAll("\\&", "{:chr38:}");
    }

    public static String subNickName(String nickName) {
        boolean sub = nickName.length() > 15 ? true : false;
        if (sub) {
            nickName = nickName.substring(0, 15);
            StringBuilder sb = new StringBuilder(nickName);
            return sb.append("...").toString();
        } else {
            return nickName;
        }
    }

    public static String filterDoMainUrl(String str) {
        String regEx = "^(((http)|(https))\\:\\/\\/[a-zA-Z0-9\\.\\-\\_]+\\/)";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(str);
        if (matcher.find()) {
            String result = matcher.group();
            return result.substring(0, result.lastIndexOf("/"));
        }
        return "";
    }

    public static boolean isMobile(String str) {
        Pattern p = Pattern.compile("^[1][0-9][0-9]{9}$"); // 验证手机号;
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 直接拨打电话
     */
    public static boolean intentToCall(final Context context, final String phoneNumber) {
        if (context == null || isNullEmpty(phoneNumber)) {
            return false;
        }
        //6.0权限处理
        YXPermission.getInstance(context).request(new AcpOptions.Builder()
                .setDeniedCloseBtn(context.getString(R.string.permission_dlg_close_txt))
                .setDeniedSettingBtn(context.getString(R.string.permission_dlg_settings_txt))
                .setDeniedMessage(context.getString(R.string.permission_denied_txt, "拨打电话"))
                .setPermissions(Manifest.permission.CALL_PHONE).build(), new AcpListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGranted() {
                Uri uriScheme = Uri.parse("tel:" + phoneNumber);
                Intent it = new Intent(Intent.ACTION_CALL, uriScheme);
                context.startActivity(it);
            }


            @Override
            public void onDenied(List<String> permissions) {

            }
        });

        return true;
    }

    //生成随机数
    public static boolean testRandom() {
        Random random = new Random();
        return random.nextInt(10) % 2 > 0;
    }

    //生成随机数
    public static int Random() {
        Random random = new Random();
        return random.nextInt(3);
    }

    public static String formatNum(long time) {
        return time < 10 ? "0" + time : String.valueOf(time);
    }

    /**
     * 判断2个时间大小
     * yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式）
     *
     * @return
     */
    public static int getTimeCompareSize(String endTime) {
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//年-月-日 时-分
        try {
            Date curDate = new Date(System.currentTimeMillis());
            String startTime = dateFormat.format(curDate);
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime() < date1.getTime()) {
                i = 1;
            } else if (date2.getTime() == date1.getTime()) {
                i = 2;
            } else if (date2.getTime() > date1.getTime()) {
                //正常情况下的逻辑操作.
                i = 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int getTimeCompareSize(String endTime, String pattern) {
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);//年-月-日 时-分
        try {
            Date curDate = new Date(System.currentTimeMillis());
            String startTime = dateFormat.format(curDate);
            Date date1 = dateFormat.parse(startTime);//当前时间
            Date date2 = dateFormat.parse(endTime);//选择时间
            if (date2.getTime() < date1.getTime()) {// 1 小于当前时间
                i = 1;
            } else if (date2.getTime() == date1.getTime()) {// 2 与当前时间相同
                i = 2;
            } else if (date2.getTime() > date1.getTime()) {// 3 大于当前时间
                i = 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = getMac(context);

            json.put("mac", mac);
            if (isEmpty(device_id)) {
                device_id = mac;
            }
            if (isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMac(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        if (Build.VERSION.SDK_INT < 23) {
            mac = getMacBySystemInterface(context);
        } else {
            mac = getMacByJavaAPI();
            if (isEmpty(mac)) {
                mac = getMacBySystemInterface(context);
            }
        }
        return mac;

    }

    @TargetApi(9)
    private static String getMacByJavaAPI() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                if ("wlan0".equals(netInterface.getName()) || "eth0".equals(netInterface.getName())) {
                    byte[] addr = netInterface.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        return null;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    return buf.toString().toLowerCase(Locale.getDefault());
                }
            }
        } catch (Throwable e) {
        }
        return null;
    }

    private static String getMacBySystemInterface(Context context) {
        if (context == null) {
            return "";
        }
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (checkPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
                WifiInfo info = wifi.getConnectionInfo();
                return info.getMacAddress();
            } else {
                return "";
            }
        } catch (Throwable e) {
            return "";
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (context == null) {
            return result;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Throwable e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }


    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    /**
     * 判断给定字符串时间是否为今日(效率不是很高，不过也是一种方法)     * @param sdate     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 将字符串转位日期类型     * @param sdate     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    /**
     * 判断是否为今天(效率比较高)     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以     * @return true今天 false不是     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以     * @return true今天 false不是     * @throws ParseException
     */
    public static boolean IsYesterday(String day) throws ParseException {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @param
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDates(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static String strToDates1(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return formatter.format(strtodate);
    }

    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "星期日";
        }
        if (wek == 2) {
            Week += "星期一";
        }
        if (wek == 3) {
            Week += "星期二";
        }
        if (wek == 4) {
            Week += "星期三";
        }
        if (wek == 5) {
            Week += "星期四";
        }
        if (wek == 6) {
            Week += "星期五";
        }
        if (wek == 7) {
            Week += "星期六";
        }
        return Week;
    }


    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static String strToDates2(String strDate) {
        SimpleDateFormat formatters = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatters.parse(strDate, pos);
        return formatter.format(strtodate);
    }


    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }


    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    public static boolean judgeFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    public static File saveFile(Bitmap bm, String fileName, String path) {
        File myCaptureFile = null;
        try {
            String subForder = path;
            File foder = new File(subForder);
            if (!foder.exists()) {
                foder.mkdirs();
            }
            myCaptureFile = new File(subForder, fileName);
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCaptureFile;
    }

    public static InputFilter inputFilter(final Context context) {
        InputFilter inputFilter = new InputFilter() {

            Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher = pattern.matcher(charSequence);
                if (!matcher.find()) {
                    return null;
                } else {
                    ToastUtils.show(context, "只能输入汉字,英文，数字");
                    return "";
                }

            }
        };
        return inputFilter;
    }


    public static String calculate(String number) {
        try {
            Double money = Double.parseDouble(number);
            money = money / 10000;
            String price = String.format("%.2f", money);
            return price;
        } catch (Exception e) {
            return "";
        }

    }

    public static String percentage(String number) {
        try {
            Double money = Double.parseDouble(number);
            String price = String.format("%.1f", money);
            return price;
        } catch (Exception e) {
            return "";
        }
    }

    public static void setImageViewSize(final ImageView imageView) {
        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams lp1 = imageView.getLayoutParams();
                lp1.width = imageView.getWidth();
                lp1.height = imageView.getWidth();
                imageView.setLayoutParams(lp1);
            }
        });
    }

    public static void setImageViewSizes(final ImageView imageView) {
        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams lp1 = imageView.getLayoutParams();
                lp1.width = imageView.getWidth();
                lp1.height = imageView.getWidth();
                imageView.setLayoutParams(lp1);
                Log.i("imageView", imageView.getWidth() + "");
            }
        });
    }

    public static void setImageViewSize1(final ImageView imageView) {
        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams lp1 = imageView.getLayoutParams();
                lp1.width = imageView.getWidth();
                lp1.height = imageView.getWidth() + 12;
                imageView.setLayoutParams(lp1);
                Log.i("imageView", imageView.getWidth() + "");
            }
        });
    }

    public static List<String> cutout(String str, String conform) {
        List<String> list = new ArrayList<>();
        String[] temp = null;
        temp = stringToArray(str, conform);
        for (int i = 0; i < temp.length; i++) {
            list.add(temp[i]);
        }
        return list;
    }

    private static String[] stringToArray(String str, String key) {
        String[] temp = null;
        temp = str.split(key);
        return temp;
    }

//    public static ArrayList<ImageSelectorItem> imageSelectorItem(List<String> imageUrl) {
//        List<ImageSelectorItem> items = new ArrayList<>();
//        for (int i = 0; i < imageUrl.size(); i++) {
//            items.add(ImageSelectorItem.convert(imageUrl.get(i)));
//        }
//        final ArrayList<ImageSelectorItem> preItems = new ArrayList<>();
//        for (int i = 0; i < items.size(); i++) {
//            ImageSelectorItem item1 = items.get(i);
//            if (!item1.isAddFlag()) {
//                preItems.add(item1);
//            }
//        }
//        return preItems;
//    }
//
//    public static ArrayList<ImageSelectorItem> imageSelectorItems(List<GetExampleLibraryPage2Entity.VarListBean.ListfileBean> listfileBeans) {
//        List<ImageSelectorItem> items = new ArrayList<>();
//        for (int i = 0; i < listfileBeans.size(); i++) {
//            items.add(ImageSelectorItem.convert(listfileBeans.get(i).getIMG_URL()));
//        }
//        final ArrayList<ImageSelectorItem> preItems = new ArrayList<>();
//        for (int i = 0; i < items.size(); i++) {
//            ImageSelectorItem item1 = items.get(i);
//            if (!item1.isAddFlag()) {
//                preItems.add(item1);
//            }
//        }
//        return preItems;
//    }


    /**
     * 获取渠道名称
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        return getAppMetaData(context, "UMENG_CHANNEL");
    }

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值 ， 或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    public static String NumberFormat(float f, int m) {
        return String.format("%." + m + "f", f);
    }

    public static float NumberFormatFloat(float f, int m) {
        String strfloat = NumberFormat(f, m);
        return Float.parseFloat(strfloat);
    }

    public static double conversionRatio(double start, double end, double current) {
        double Proportion = 0;
        Proportion = (current - start) / (end - start) * 100;
        Proportion = (Proportion * 0.2) + 15;
        return Proportion;
    }

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static String getUUID(Context context) {

        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        tmDevice = "" + tm.getDeviceId();
//
//        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
//
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32));
//
//        String uniqueId = deviceUuid.toString();
//
//        Log.e("debug","uuid="+uniqueId);

        return tmDevice;

    }

    //获取手机IMEI号
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
        return imei;
    }


    public static void reflex(final TabLayout tabLayout, final int screenWidth, final int count) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = DisplayUtils.dip2px(tabLayout.getContext(), 15);
                    int dp20 = DisplayUtils.dip2px(tabLayout.getContext(), 20);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        if (count >= 5) {
                            params.leftMargin = dp10;
                            params.rightMargin = dp10;
                        } else {
                            int itemWidth = screenWidth / count;
                            int dp = (itemWidth - width) / 2;
                            params.leftMargin = dp;
                            params.rightMargin = dp;
                        }
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
