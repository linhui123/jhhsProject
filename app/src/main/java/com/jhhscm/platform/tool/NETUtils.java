package com.jhhscm.platform.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * 类说明：网络工具
 * 作者：yangxiaoping on 2016/3/29 09:14
 */
public class NETUtils {
    private final static String TAG = "NETUtils";

    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;
    private static final int CONNECT_TIMEOUT = 15000;
    private static final int READ_TIMEOUT = 10000;

    /**
     * 域名解析
     * 注意：此方法不能在主线程（UI线程）中执行，会抛异常！！！！！
     * 需要在事件响应代码或其他线程中执行
     *
     * @param dnsName
     *            需要解析的域名，如 market.hiapk.com
     * @return
     */
    public static String dnsNameToip(String dnsName) {
        String ip = null;
        InetAddress[] addrs = null;
        try {
            addrs = InetAddress.getAllByName(dnsName);

        } catch (Exception e) {
            Log.e(TAG, "cannot DNS translate :" + dnsName);
        }

        if (addrs != null && addrs.length >= 1) {
            ip = addrs[0].getHostAddress();
        }

        return ip;
    }

    /**
     * 输入url的地址，解析出对应的域名
     *
     * @param urlString
     * @return
     */
    public static String getUrlDomain(String urlString) {
        String uriDomain = null;
        try {
            URI uri = new URI(urlString);
            uriDomain = uri.getHost();

        } catch (Exception e) {
            Log.e(TAG, "cannot get Host :" + urlString);
        }

        return uriDomain;
    }

    /**
     * 获取当前网络类型
     * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
     */
    public static int getNetworkType(Context context) {
//        Assert.assertNotNull(context);

        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if(!StringUtils.isNullEmpty(extraInfo)){
                if ("cmnet".equalsIgnoreCase(extraInfo)) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }
    /**
     * 判断当前网络是否WIFI网络
     * @param context
     * @return
     */
    public static boolean isWifiNetwork(Context context){

        return getNetworkType(context) == NETTYPE_WIFI;
    }

    /**
     * 获取当前网络连接的类型信息
     * @return -1：没有网络  1：WIFI网络2：wap网络3：net网络
     */
    public static int getConnectedType(Context context) {
//        Assert.assertNotNull(context);

        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * @param context
     * @param url CommonConstants
     * @return URL返回的二进制内容
     */
    public static byte[] getUrl(Context context, String url) {
        URLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            // 1、代理
            // 2、解码问题
            conn = openUrl(context, url);
            if (conn != null) {
                is = (InputStream)conn.getContent();
                os = new ByteArrayOutputStream();
                // 每次最大读取1kb
                byte[] bytes = new byte[1024];
                int length = 0;
                // 读取所有内容到数组内
                while ((length = is.read(bytes)) != -1) {
                    os.write(bytes, 0, length);
                }
                return bytes = os.toByteArray();
            }
            return null;
        } catch (Throwable e) {
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
            }
        }
    }

    private static HttpURLConnection openUrl(Context context, String urlStr) {
        URL urlURL = null;
        HttpURLConnection httpConn = null;
        try {
            urlURL = new URL(urlStr);
            // 需要android.permission.ACCESS_NETWORK_STATE
            // 在没有网络的情况下，返回值为null。
            NetworkInfo networkInfo = ((ConnectivityManager)context
                    .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            // 如果是使用的运营商网络
            if (networkInfo != null) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // 获取默认代理主机ip
                    String host = android.net.Proxy.getDefaultHost();
                    // 获取端口
                    int port = android.net.Proxy.getDefaultPort();
                    if (host != null && port != -1) {
                        // 封装代理連接主机IP与端口号。
                        InetSocketAddress inetAddress = new InetSocketAddress(host, port);
                        // 根据URL链接获取代理类型，本链接适用于TYPE.HTTP
                        java.net.Proxy.Type proxyType = java.net.Proxy.Type.valueOf(urlURL
                                .getProtocol().toUpperCase());
                        java.net.Proxy javaProxy = new java.net.Proxy(proxyType, inetAddress);

                        httpConn = (HttpURLConnection)urlURL.openConnection(javaProxy);
                    } else {
                        httpConn = (HttpURLConnection)urlURL.openConnection();
                    }
                } else {
                    httpConn = (HttpURLConnection)urlURL.openConnection();
                }
                httpConn.setConnectTimeout(CONNECT_TIMEOUT);
                httpConn.setReadTimeout(READ_TIMEOUT);
                httpConn.setDoInput(true);
            } else {
                // LogOut.out(this, "No Avaiable Network");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpConn;
    }

    private static boolean isProxyNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo mobNetInfo = connectivityManager.getActiveNetworkInfo();
        if (mobNetInfo == null || mobNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return false;
        }

        if (mobNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return hasProxySetting();
        }

        return false;
    }

    public static boolean isWifiConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo mobNetInfo = connectivityManager.getActiveNetworkInfo();
        if (mobNetInfo != null && mobNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }

        return false;
    }

    public static boolean isMobileConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo mobNetInfo = connectivityManager.getActiveNetworkInfo();
        if (mobNetInfo != null && mobNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }

        return false;
    }

    /**
     * 检测网络是否可用
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context==null) return false;
        ConnectivityManager connectivityManager = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isConnectedOrConnecting();
        }

        return false;
    }

    private static boolean hasProxySetting() {
        if (android.net.Proxy.getDefaultHost() != null && android.net.Proxy.getDefaultPort() != -1) {
            return true;
        }
        return false;
    }
}
