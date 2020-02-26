package com.jhhscm.platform.tool;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/5/9.
 */
public class UdaUtils {

    private static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] LOWER_CHAR = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] UPPER_CHAR = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static int convertDipToPx(Context context, float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    public static int getId(Context context, String name) {
        Resources res = context.getResources();
        final String packageName = context.getPackageName();
        return res.getIdentifier(name, "id", packageName);
    }

    public static String getFileNameForUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String[] aStr = url.split("/");
        if (aStr.length > 1) {
            return aStr[aStr.length - 1];
        }
        return null;
    }

    public static void hideInputMethod(Context context, View view) {
        try {
            if (view != null) {
                InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showInputMethod(Context context, View view) {
        try {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.showSoftInputFromInputMethod(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isMobile(String str) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号;
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } finally {
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    public static boolean needProxy() {
        return Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;
    }

    public static String hiddenPhoneNumber(String phone) {
        try {
            return phone.substring(0, 3) + "****" + phone.substring(7);
        } catch (Exception e) {
            e.printStackTrace();
            return phone;
        }
    }

    public static String hiddenIDNumber(String id) {
        try {
            if (id.length() == 18) {
                return id.substring(0, 6) + "********" + id.substring(14);
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        }
    }

    public static String hiddenNameString(String name) {
        try {
            if (name.length() == 2) {
                return name.substring(0, 1) + "*";
            } else if (name.length() > 2) {
                return name.substring(0, 1) + "*" + name.substring(name.length() - 1, name.length());
            }
            return name.substring(0, 3) + "****" + name.substring(7);
        } catch (Exception e) {
            e.printStackTrace();
            return name;
        }
    }

    public static String filterChinese(String str) {
        String regEx = "^[\\u4E00-\\u9FA5\\p{P}‘’“”]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(str);
        return matcher.find() ? filterEmoji(matcher.replaceAll("")) : filterEmoji(str);
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

    public static String filterEmoji(String str) {
        String regEx = "^[A-Za-z\\d\\u4E00-\\u9FA5\\p{P}‘’“”]+$";
        Pattern p = Pattern.compile(regEx);
        StringBuilder sb = new StringBuilder(str);
        for (int len = str.length(), i = len - 1; i >= 0; --i) {

            if (!Pattern.matches(regEx, String.valueOf(str.charAt(i)))) {
                sb.deleteCharAt(i);
            }
        }
        return sb.toString();
    }

    //    public static CharSequence filterEmoji(CharSequence source) {
    //        Pattern emoji = Pattern.compile(
    //                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
    //                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    //        Matcher emojiMatcher = emoji.matcher(source);
    //        if (emojiMatcher.find()) {
    //            source = emojiMatcher.replaceAll("");
    //        }
    //        return source;
    //    }
    public static String filterAtUserString(String str) {
        String regEx = "回复@(.*):";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(str);
        return matcher.replaceAll("").trim();
    }

    public static String getRandomString(int length, boolean repeatAllowed) {
        return getRandomString(length, repeatAllowed, NUMBERS, LOWER_CHAR, UPPER_CHAR);
    }

    private static String getRandomString(int length, boolean repeatable, char[]... arrays) {
        if (length > 0 && arrays.length > 0) {
            StringBuilder builder = new StringBuilder();
            Random random = new Random();
            if (!repeatable) {
                int totalLength = 0;
                for (char[] array : arrays) {
                    totalLength += array.length;
                }
                if (length > totalLength) {
                    length = totalLength;
                }
            }
            while (builder.length() < length) {
                int sourceIndex = random.nextInt(arrays.length);
                int dataIndex = random.nextInt(arrays[sourceIndex].length);
                char value = arrays[sourceIndex][dataIndex];
                if (repeatable) {
                    builder.append(value);
                } else {
                    if (builder.indexOf(value + "") == -1) {
                        builder.append(value);
                    }
                }
            }
            return builder.toString();
        }
        return null;
    }

    public static String getRandomNumer() {
        return getRandomString(4, true, NUMBERS);
    }

}
