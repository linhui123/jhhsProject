package com.jhhscm.platform.http;

import android.content.Context;

import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.tool.BroadcastUtils;
import com.jhhscm.platform.tool.NETUtils;
import com.jhhscm.platform.tool.ResourceUtils;
import com.jhhscm.platform.tool.ToastUtils;

/**
 * 类说明：处理信息
 */
public class HttpHelper {


    public String showErrorMsg(Context context, int resultCode,
                               BaseErrorInfo baseErrorInfo, int defErrorResId) {
        return showErrorMsg(context, resultCode, baseErrorInfo, ResourceUtils.getResourceString(context, defErrorResId));
    }

    public String showErrorMsg(Context context, int resultCode,
                               BaseErrorInfo baseErrorInfo, String defError) {
        String msg = null;
        if (resultCode != AHttpService.IResCallback.RESULT_OK) {
            if (resultCode == AHttpService.IResCallback.RESULT_NET_ERROR) {
                msg = ResourceUtils.getResourceString(context, "error_net");
            } else if (resultCode == AHttpService.IResCallback.RESULT_NOT_NET_ERROR) {
                msg = ResourceUtils.getResourceString(context, "error_notnet");
            } else if (resultCode != 1 && baseErrorInfo != null) {
                if (baseErrorInfo.getCode() == ServerErrorCode.USER_LOGOUT_FAILED.getCode()) {
                    BroadcastUtils.sendLoginInvalidBroadCast(context, baseErrorInfo.getMsg());
                }
//                if (baseErrorInfo.getCode() == ServerErrorCode.USER_REGISTER_MOBILE_OCCUPY.getCode()) {
//                    BroadcastUtils.sendMobileOccupyBroadCast(context);
//                }
                msg = ExceptionHandle.handleException(baseErrorInfo.getCode());
//                if (!TextUtils.isEmpty(msg)) {
//
//                } else {
//                    msg = defError;
//                }
//            } else {
//                msg = defError;
//            }
            }
        }
        return msg;
    }


    public void showError(Context context, String resultCode, String msg) {
        try {
            int code = Integer.parseInt(resultCode);
            if (code == ServerErrorCode.USER_LOGOUT_FAILED.getCode()) {
                BroadcastUtils.sendLoginInvalidBroadCast(context, msg);
            }
        } catch (Exception e) {

        }
    }


    public boolean showError(Context context, int resultCode,
                             BaseErrorInfo baseErrorInfo, String defError) {
        if (!NETUtils.isNetworkConnected(context) || baseErrorInfo == null) return false;
        String msg = showErrorMsg(context, resultCode, baseErrorInfo, defError);
        if (msg == null) {
            return false;
        }
        boolean ifShow = baseErrorInfo.getCode() == ServerErrorCode.USER_LOGOUT_FAILED.getCode();
        if (!ifShow) ToastUtils.show(context, msg);
        return true;
    }

    public boolean showError(Context context, int resultCode,
                             BaseErrorInfo baseErrorInfo, String defError, boolean showMsg) {
        String msg = showErrorMsg(context, resultCode, baseErrorInfo, defError);
        if (msg == null) {
            return false;
        }
        if (showMsg) {
            ToastUtils.show(context, msg);
        }
        return true;
    }

    public boolean showError(Context context, int resultCode, BaseErrorInfo baseErrorInfo, int defErrorResId) {
        if (!NETUtils.isNetworkConnected(context) || baseErrorInfo == null) return false;
        return showError(context, resultCode, baseErrorInfo, ResourceUtils.getResourceString(context, defErrorResId));
    }

    public boolean showError(Context context, int resultCode, BaseErrorInfo baseErrorInfo, int defErrorResId, boolean showMsg) {
        return showError(context, resultCode, baseErrorInfo, ResourceUtils.getResourceString(context, defErrorResId), showMsg);
    }
//    public static boolean showError(Context context, int resultCode, UploadImageResponse response, String defError) {
//        String msg = showErrorMsg(context, resultCode, response, defError);
//        if (msg == null) {
//            return false;
//        }
//        if(UrlConfig.isDebugMode()) {
//            ToastUtils.show(context, msg);
//        }
//        return true;
//    }
//    public static String showErrorMsg(Context context, int resultCode, UploadImageResponse response, String defError) {
//        String msg = null;
//        if (resultCode != UploadImageAction.IResCallback.RESULT_OK) {
//            if (resultCode == UploadImageAction.IResCallback.RESULT_FILE_NOTEXIT_ERROR) {
//                msg = ResourceUtils.getResourceString(context, "error_file_notexit");
//            } else if (resultCode == UploadImageAction.IResCallback.RESULT_UPIMAGE_ERROR) {
//                msg = ResourceUtils.getResourceString(context, "error_image_error");
//            } else if (resultCode == UploadImageAction.IResCallback.RESULT_NOT_NET_ERROR) {
//                msg = ResourceUtils.getResourceString(context, "error_notnet");
//            } else if (response != null && response.getCode() != 1) {
//                if (!TextUtils.isEmpty(response.getMsg())) {
//                    msg = response.getMsg();
//                } else {
//                    msg = defError;
//                }
//            } else {
//                msg = defError;
//            }
//        }
//        return msg;
//    }
}
