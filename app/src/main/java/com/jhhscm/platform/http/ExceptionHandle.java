package com.jhhscm.platform.http;

/**
 * Created by Administrator on 2018/9/5/005.
 */

public class ExceptionHandle {
    private static final int UNAUTHORIZED = 500;
    private static final int FORBIDDEN = 501;
    private static final int NOT_FOUND = 502;
    private static final int REQUEST_TIMEOUT = 503;
    private static final int INTERNAL_SERVER_ERROR = 504;
    private static final int BAD_GATEWAY = 505;
    private static final int SERVICE_UNAVAILABLE = 400;
    private static final int GATEWAY_TIMEOUT = 404;

    public static String handleException(int code) {
        String message;
            switch (code) {
                case UNAUTHORIZED:
                    message = "致命错误！";
                    break;
                case FORBIDDEN:
                    message = "错误！";
                    break;
                case NOT_FOUND:
                    message = "异常！";
                    break;
                case REQUEST_TIMEOUT:
                    message = "网络错误";
                    break;
                case GATEWAY_TIMEOUT:
                    message = "服务器异常！";
                    break;
                case INTERNAL_SERVER_ERROR:
                    message = "无效的请求包体！";
                    break;
                default:
                    message = "网络错误";
                    break;
            }
            return message;

    }


    /**
     * 约定异常
     */
    class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;
    }

    public static class ResponeThrowable extends Exception {
        public int code;
        public String message;

        public ResponeThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    /**
     * ServerException发生后，将自动转换为ResponeThrowable返回
     */
    class ServerException extends RuntimeException {
        int code;
        String message;
    }
}
