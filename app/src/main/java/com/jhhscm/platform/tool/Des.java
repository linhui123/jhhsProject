package com.jhhscm.platform.tool;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Des {
    private static final String KEY="77e8de7c098d444b9ca87b20a714e025";

    /**
     * DES加密
     *
     * @param originalStr
     * @return
     */
    public static String encryptByDes(String originalStr) {
        String result = null;
        byte[] tmpOriginalStr = null;
        try {
            if (!TextUtils.isEmpty(originalStr)) {
                tmpOriginalStr = originalStr.getBytes("UTF-8");
                // 创建一个密匙工厂
                SecretKeyFactory keyFactory = SecretKeyFactory
                        .getInstance("Des");
                // 创建一个DESKeySpec对象，KEY为自定义密钥常量
                DESKeySpec dks = new DESKeySpec(KEY.getBytes());
                // 将DESKeySpec对象转换成SecretKey对象
                SecretKey secretKey = keyFactory.generateSecret(dks);
                // 用密匙初始化Cipher对象
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                // Cipher对象实际完成加密操作
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                // 真正开始加密操作
                byte[] tmpEncypt = cipher.doFinal(tmpOriginalStr);
                if (tmpEncypt != null) {
                    result = Base64.encodeToString(tmpEncypt, Base64.NO_WRAP);
                }
            }
        } catch (Exception e) {
            Log.e("Erro", e.getMessage());
        }
        return result;
    }
    /***
     * DES 解密
     *
     * @param desStr
     * @return
     */
    public static String decyptByDes(String desStr) {
        String result = null;
        try {
            if (!TextUtils.isEmpty(desStr)) {
                // 创建一个密匙工厂
                SecretKeyFactory keyFactory = SecretKeyFactory
                        .getInstance("DES");
                // 创建一个DESKeySpec对象,KEY为自定义密钥常量
                DESKeySpec dks = new DESKeySpec(KEY.getBytes());
                // 将DESKeySpec对象转换成SecretKey对象
                SecretKey secretKey = keyFactory.generateSecret(dks);
                // 用密匙初始化Cipher对象
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                // Cipher对象实际完成解密操作
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                // 真正开始解密操作
                byte[] tmpDecypt = cipher.doFinal(Base64.decode(desStr,
                        Base64.NO_WRAP));
                if (tmpDecypt != null) {
                    result = new String(tmpDecypt, "UTF-8");
                }
            }
        } catch (Exception e) {
            Log.e("Erro", e.getMessage());
        }
        return result;
    }

    /**
     * DES加密
     *
     * @param originalStr
     * @return
     */
    public static String encryptByDes(String key,String originalStr) {
        String result = null;
        byte[] tmpOriginalStr = null;
        try {
            if (!TextUtils.isEmpty(originalStr)) {
                tmpOriginalStr = originalStr.getBytes("UTF-8");
                // 创建一个密匙工厂
                SecretKeyFactory keyFactory = SecretKeyFactory
                        .getInstance("Des");
                // 创建一个DESKeySpec对象，KEY为自定义密钥常量
                DESKeySpec dks = new DESKeySpec(key.getBytes());
                // 将DESKeySpec对象转换成SecretKey对象
                SecretKey secretKey = keyFactory.generateSecret(dks);
                // 用密匙初始化Cipher对象
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                // Cipher对象实际完成加密操作
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                // 真正开始加密操作
                byte[] tmpEncypt = cipher.doFinal(tmpOriginalStr);
                if (tmpEncypt != null) {
                    result = Base64.encodeToString(tmpEncypt, Base64.NO_WRAP);
                }
            }
        } catch (Exception e) {
            Log.e("Erro", e.getMessage());
        }
        return result;
    }
    /***
     * DES 解密
     *
     * @param desStr
     * @return
     */
    public static String decyptByDes(String key,String desStr) {
        String result = null;
        try {
            if (!TextUtils.isEmpty(desStr)) {
                // 创建一个密匙工厂
                SecretKeyFactory keyFactory = SecretKeyFactory
                        .getInstance("DES");
                // 创建一个DESKeySpec对象,KEY为自定义密钥常量
                DESKeySpec dks = new DESKeySpec(key.getBytes());
                // 将DESKeySpec对象转换成SecretKey对象
                SecretKey secretKey = keyFactory.generateSecret(dks);
                // 用密匙初始化Cipher对象
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                // Cipher对象实际完成解密操作
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                // 真正开始解密操作
                byte[] tmpDecypt = cipher.doFinal(Base64.decode(desStr,
                        Base64.NO_WRAP));
                if (tmpDecypt != null) {
                    result = new String(tmpDecypt, "UTF-8");
                }
            }
        } catch (Exception e) {
            Log.e("Erro", e.getMessage());
        }
        return result;
    }

    /**
     * 32位MD5加密
     * @param content -- 待加密内容
     * @return
     */
    public static String md5Decode(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException",e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        //对生成的16字节数组进行补零操作
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    //    public static String key_sort(TreeMap<String, String> map) {
//        String key_sort = "";
//        TreeMap<String, String> map2 = new TreeMap<String, String>(new Comparator<String>() {
//            public int compare(String obj1, String obj2) {
//                // 降序排序
//                return obj2.compareTo(obj1);
//            }
//        });
//        map2 = map;
//        Set<String> keySet = map2.keySet();
//        Iterator<String> iter = keySet.iterator();
//        while (iter.hasNext()) {
//            String key = iter.next();
//            key_sort = key_sort + key + "=" + map2.get(key) + "&";
//        }
//        return key_sort.substring(0, key_sort.length() - 1);
//    }
}
