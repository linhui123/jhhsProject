package com.jhhscm.platform.tool;

/**
 * Created by Administrator on 2019/3/1/001.
 */

public class UrlUtils {
    private static String BASE_URL = "http://192.168.0.233:8080/#/";//测试专用
//    private static String BASE_URL=" http://www.blsc.zhifama.com/blsappc/";//线上

    //新机详情   http://192.168.0.234:8080/#/product/productDetail?good_code=1
    public static String XJXQ = BASE_URL + "product/productDetail?isShowGetPhone=2";
    //二手机详情  http://192.168.0.234:8080/#/product/oldProductDetail?good_code=8
    public static String ESJXQ = BASE_URL + "product/oldProductDetail?isShowGetPhone=2";
    //配件详情 http://192.168.0.234:8080/#/parts/partsDetail?good_code=21
    public static String PJXQ = BASE_URL + "parts/partsDetail?isShowGetPhone=2";
}
