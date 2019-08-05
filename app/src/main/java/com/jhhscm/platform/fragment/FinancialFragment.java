package com.jhhscm.platform.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.MechanicsH5Activity;
import com.jhhscm.platform.databinding.FragmentWebBinding;
import com.jhhscm.platform.event.LoginH5Event;
import com.jhhscm.platform.event.WebTitleEvent;
import com.jhhscm.platform.fragment.Mechanics.PeiJianFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.NETUtils;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.UrlUtils;
import com.jhhscm.platform.views.dialog.AlertDialogs;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

public class FinancialFragment extends AbsFragment<FragmentWebBinding> {
    private UploadHandler mUploadHandler;
    private static final int FILE_SELECTED = 5;

    public static FinancialFragment instance() {
        FinancialFragment view = new FinancialFragment();
        return view;
    }


    @Override
    protected FragmentWebBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentWebBinding.inflate(inflater, container, attachToRoot);
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        public void onProgressChanged(WebView view, int newProgress) {
            mDataBinding.webProgressBar.setProgress(newProgress);
        }

        public void onReceivedTitle(WebView view, String title) {
            closeLoadingPage();
//            if (!TextUtils.isEmpty(getArguments().getString("title", ""))) {
//                title = "金服";
//            }
            title = "金服";
            EventBusUtil.post(new WebTitleEvent(title));
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            String newTitle = getTitleFromUrl(url);

            new AlertDialog.Builder(view.getContext()).setTitle(newTitle).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setCancelable(false).create().show();
            return true;
        }

        private String getTitleFromUrl(String url) {
            String title = url;
            try {
                URL urlObj = new URL(url);
                String host = urlObj.getHost();
                if (host != null && !host.isEmpty()) {
                    return urlObj.getProtocol() + "://" + host;
                }
                if (url.startsWith("file:")) {
                    String fileName = urlObj.getFile();
                    if (fileName != null && !fileName.isEmpty()) {
                        return fileName;
                    }
                }
            } catch (Exception e) {
            }
            return title;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            String newTitle = getTitleFromUrl(url);
            new AlertDialog.Builder(view.getContext()).setTitle(newTitle).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            }).setCancelable(false).create().show();
            return true;
        }

        // Android 5.0.1
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(
                WebView webView, ValueCallback<Uri[]> filePathCallback,
                FileChooserParams fileChooserParams) {

            String acceptTypes[] = fileChooserParams.getAcceptTypes();

            String acceptType = "";
            for (int i = 0; i < acceptTypes.length; ++i) {
                if (acceptTypes[i] != null && acceptTypes[i].length() != 0)
                    acceptType += acceptTypes[i] + ";";
            }
            if (acceptType.length() == 0)
                acceptType = "*/*";

            final ValueCallback<Uri[]> finalFilePathCallback = filePathCallback;

            ValueCallback<Uri> vc = new ValueCallback<Uri>() {

                @Override
                public void onReceiveValue(Uri value) {
                    Uri[] result;
                    if (value != null)
                        result = new Uri[]{value};
                    else
                        result = null;

                    finalFilePathCallback.onReceiveValue(result);
                }
            };
            openFileChooser(vc, acceptType, "filesystem");
            return true;
        }

        // Android 2.x
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "image/*");
        }

        // Android 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, "image/*", "filesystem");
        }

        // Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadHandler = new UploadHandler();
            mUploadHandler.openFileChooser(uploadMsg, acceptType, capture);
        }
    };

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
            mDataBinding.webProgressBar.setVisibility(View.VISIBLE);
        }

        public void onPageFinished(WebView view, String url) {
            mDataBinding.webProgressBar.setVisibility(View.GONE);
            String title;
            if (!TextUtils.isEmpty(getArguments().getString("title", ""))) {
                title = getArguments().getString("title", "");
            } else {
                title = view.getTitle();
            }
            EventBusUtil.post(new WebTitleEvent(title));
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            String errMsg = NETUtils.isNetworkConnected(view.getContext()) ? "很抱歉，出错啦!" : "网络异常，请检查网络连接";
            if (getView() != null) {
                showLoadingPage(R.id.rl_loading);
                setLoadFailedMessage(errMsg);
            }
        }
    };

    @Override
    protected void setupViews() {
        initViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    public void onEvent(LoginH5Event event) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static String injectIsParams(String url) {
        return url;
    }

    private void initViews() {
        EventBusUtil.registerEvent(this);
        showDialog();
        mDataBinding.webView.setVisibility(View.GONE);
        WebSettings settings = mDataBinding.webView.getSettings();
        settings.setJavaScriptEnabled(true); //与js交互必须设置
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mDataBinding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mDataBinding.webView.addJavascriptInterface(new JSInterface(), "Android");

        mDataBinding.webView.clearHistory();
        mDataBinding.webView.clearFormData();
        mDataBinding.webView.clearCache(true);
        mDataBinding.webView.setWebViewClient(mWebViewClient);
        mDataBinding.webView.setWebChromeClient(mWebChromeClient);
        if (!NETUtils.isNetworkConnected(getActivity())) {
            showLoadingPage(R.id.rl_loading);
            setLoadFailedMessage("网络异常，请检查网络连接");
        } else {
            mDataBinding.webView.loadUrl(UrlUtils.JF);
            mDataBinding.webView.setVisibility(View.VISIBLE);
            mDataBinding.webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    closeDialog();
                    mDataBinding.webView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }
            });
        }
    }

    class JSInterface {

        @JavascriptInterface
        public void showDoctor() {
            showOut();
        }

        @JavascriptInterface
        public void logins() {
        }

        @JavascriptInterface
        public void IntelligenceReport() {
        }

        @JavascriptInterface
        public void lotusTools() {
            getActivity().finish();
        }

        @JavascriptInterface
        public void shareWechat() {
            showDialog();
            closeDialog();
        }

        @JavascriptInterface
        public void shareFriends() {
            showDialog();
//                YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
//                ShareUtils.shareUrl(getContext(), SHARE_URL,
//                        TITLE, CONTENT, SHARE_MEDIA.WEIXIN_CIRCLE,
//                        ShareUtils.getShareListener(getContext()), IMG_URL);
            closeDialog();
        }

        @JavascriptInterface
        public void shareToWechat(String title, String content, String imageUrl, String shareUrl) {
            showDialog();
//                YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
//                ShareUtils.shareUrl(getContext(), shareUrl,
//                        title, content, SHARE_MEDIA.WEIXIN,
//                        ShareUtils.getShareListener(getContext()), imageUrl);
            closeDialog();
        }

        @JavascriptInterface
        public void shareToFriends(String title, String content, String imageUrl, String shareUrl) {
            showDialog();
//                YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
//                ShareUtils.shareUrl(getContext(), shareUrl,
//                        title, content, SHARE_MEDIA.WEIXIN_CIRCLE,
//                        ShareUtils.getShareListener(getContext()), imageUrl);
            closeDialog();
        }

    }

    private void showOut() {

    }

    private void showAlertNotice(final String PackageName, final String ClassName) {
        final AlertDialogs alertDialog = new AlertDialogs(getContext(), "文字已复制到剪切板\n 是否打开微信", new AlertDialogs.CallbackListener() {
            @Override
            public void clickYes() {
                getWechatApi(PackageName, ClassName);
            }
        });
        alertDialog.show();
    }

    /**
     * 跳转到微信
     */
    private void getWechatApi(String PackageName, String ClassName) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName(PackageName, ClassName);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            ToastUtils.show(getContext(), "检查到您手机没有安装微信，请安装后使用该功能");
        }
    }

    private final static Object methodInvoke(Object obj, String method, Class<?>[] parameterTypes, Object[] args) {
        try {
            Method m = obj.getClass().getMethod(method, boolean.class);
            m.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onReload(Context context) {
        super.onReload(context);
        if (!NETUtils.isNetworkConnected(getActivity())) {
            showLoadingPage(R.id.rl_loading);
            setLoadFailedMessage("网络异常，请检查网络连接");
        } else {
            mDataBinding.webView.reload();
        }
    }

    public void onFileChooserResult(int resultCode, Intent data) {
        if (mUploadHandler != null) {
            mUploadHandler.onResult(resultCode, data);
        }
    }

    public boolean canWebViewGoBack() {
        return mDataBinding.webView.canGoBack();
    }

    public void webViewGoBack() {
        mDataBinding.webView.goBack();
    }

    class UploadHandler {
        /*
         * The Object used to inform the WebView of the file to upload.
         */
        private ValueCallback<Uri> mUploadMessage;
        private String mCameraFilePath;
        private boolean mHandled;
        private boolean mCaughtActivityNotFoundException;

        public UploadHandler() {
        }

        String getFilePath() {
            return mCameraFilePath;
        }

        boolean handled() {
            return mHandled;
        }

        void onResult(int resultCode, Intent intent) {
            if (resultCode == Activity.RESULT_CANCELED && mCaughtActivityNotFoundException) {
                // Couldn't resolve an activity, we are going to try again so skip
                // this result.
                mCaughtActivityNotFoundException = false;
                return;
            }
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null
                    : intent.getData();
            // As we ask the camera to save the result of the user taking
            // a picture, the camera application does not return anything other
            // than RESULT_OK. So we need to check whether the file we expected
            // was written to disk in the in the case that we
            // did not get an intent returned but did get a RESULT_OK. If it was,
            // we assume that this result has came back from the camera.
            if (result == null && intent == null && resultCode == Activity.RESULT_OK) {
                File cameraFile = new File(mCameraFilePath);
                if (cameraFile.exists()) {
                    result = Uri.fromFile(cameraFile);
                    // Broadcast to the media scanner that we have a new photo
                    // so it will be added into the gallery for the user.
                    getActivity().sendBroadcast(
                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
                }
            }
            mUploadMessage.onReceiveValue(result);
            mHandled = true;
            mCaughtActivityNotFoundException = false;
        }

        void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            final String imageMimeType = "image/*";
            final String videoMimeType = "video/*";
            final String audioMimeType = "audio/*";
            final String mediaSourceKey = "capture";
            final String mediaSourceValueCamera = "camera";
            final String mediaSourceValueFileSystem = "filesystem";
            final String mediaSourceValueCamcorder = "camcorder";
            final String mediaSourceValueMicrophone = "microphone";
            // According to the spec, media source can be 'filesystem' or 'camera' or 'camcorder'
            // or 'microphone' and the default value should be 'filesystem'.
            String mediaSource = mediaSourceValueFileSystem;
            if (mUploadMessage != null) {
                // Already a file picker operation in progress.
                return;
            }
            mUploadMessage = uploadMsg;
            // Parse the accept type.
            String params[] = acceptType.split(";");
            String mimeType = params[0];
            if (capture.length() > 0) {
                mediaSource = capture;
            }
            if (capture.equals(mediaSourceValueFileSystem)) {
                // To maintain backwards compatibility with the previous implementation
                // of the media capture API, if the value of the 'capture' attribute is
                // "filesystem", we should examine the accept-type for a MIME type that
                // may specify a different capture value.
                for (String p : params) {
                    String[] keyValue = p.split("=");
                    if (keyValue.length == 2) {
                        // Process key=value parameters.
                        if (mediaSourceKey.equals(keyValue[0])) {
                            mediaSource = keyValue[1];
                        }
                    }
                }
            }
            //Ensure it is not still set from a previous upload.
            mCameraFilePath = null;
            if (mimeType.equals(imageMimeType)) {
                if (mediaSource.equals(mediaSourceValueCamera)) {
                    // Specified 'image/*' and requested the camera, so go ahead and launch the
                    // camera directly.
                    startActivity(createCameraIntent());
                    return;
                } else {
                    // Specified just 'image/*', capture=filesystem, or an invalid capture parameter.
                    // In all these cases we show a traditional picker filetered on accept type
                    // so launch an intent for both the Camera and image/* OPENABLE.
                    Intent chooser = createChooserIntent(createCameraIntent());
                    chooser.putExtra(Intent.EXTRA_INTENT, createOpenableIntent(imageMimeType));
                    startActivity(chooser);
                    return;
                }
            } else if (mimeType.equals(videoMimeType)) {
                if (mediaSource.equals(mediaSourceValueCamcorder)) {
                    // Specified 'video/*' and requested the camcorder, so go ahead and launch the
                    // camcorder directly.
                    startActivity(createCamcorderIntent());
                    return;
                } else {
                    // Specified just 'video/*', capture=filesystem or an invalid capture parameter.
                    // In all these cases we show an intent for the traditional file picker, filtered
                    // on accept type so launch an intent for both camcorder and video/* OPENABLE.
                    Intent chooser = createChooserIntent(createCamcorderIntent());
                    chooser.putExtra(Intent.EXTRA_INTENT, createOpenableIntent(videoMimeType));
                    startActivity(chooser);
                    return;
                }
            } else if (mimeType.equals(audioMimeType)) {
                if (mediaSource.equals(mediaSourceValueMicrophone)) {
                    // Specified 'audio/*' and requested microphone, so go ahead and launch the sound
                    // recorder.
                    startActivity(createSoundRecorderIntent());
                    return;
                } else {
                    // Specified just 'audio/*',  capture=filesystem of an invalid capture parameter.
                    // In all these cases so go ahead and launch an intent for both the sound
                    // recorder and audio/* OPENABLE.
                    Intent chooser = createChooserIntent(createSoundRecorderIntent());
                    chooser.putExtra(Intent.EXTRA_INTENT, createOpenableIntent(audioMimeType));
                    startActivity(chooser);
                    return;
                }
            }
            // No special handling based on the accept type was necessary, so trigger the default
            // file upload chooser.
            startActivity(createDefaultOpenableIntent());
        }

        private void startActivity(Intent intent) {
            try {
                getActivity().startActivityForResult(intent, FILE_SELECTED);
            } catch (ActivityNotFoundException e) {
                // No installed app was able to handle the intent that
                // we sent, so fallback to the default file upload control.
                try {
                    mCaughtActivityNotFoundException = true;
                    getActivity().startActivityForResult(createDefaultOpenableIntent(),
                            FILE_SELECTED);
                } catch (ActivityNotFoundException e2) {
                    // Nothing can return us a file, so file upload is effectively disabled.
                    Toast.makeText(getActivity(), "文件上传被禁用",
                            Toast.LENGTH_LONG).show();
                }
            }
        }

        private Intent createDefaultOpenableIntent() {
            // Create and return a chooser with the default OPENABLE
            // actions including the camera, camcorder and sound
            // recorder where available.
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            Intent chooser = createChooserIntent(createCameraIntent());
            chooser.putExtra(Intent.EXTRA_INTENT, i);
            return chooser;
        }

        private Intent createChooserIntent(Intent... intents) {
            Intent chooser = new Intent(Intent.ACTION_CHOOSER);
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
            chooser.putExtra(Intent.EXTRA_TITLE, "选取图片");
            return chooser;
        }

        private Intent createOpenableIntent(String type) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType(type);
            return i;
        }

        private Intent createCameraIntent() {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File externalDataDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM);
            File cameraDataDir = new File(externalDataDir.getAbsolutePath() +
                    File.separator + "browser-photos");
            cameraDataDir.mkdirs();
            mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator +
                    System.currentTimeMillis() + ".jpg";
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
            return cameraIntent;
        }

        private Intent createCamcorderIntent() {
            return new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        }

        private Intent createSoundRecorderIntent() {
            return new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        }
    }

}