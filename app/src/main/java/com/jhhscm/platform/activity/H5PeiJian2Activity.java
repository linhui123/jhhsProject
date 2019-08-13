package com.jhhscm.platform.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsActivity;
import com.jhhscm.platform.databinding.ActivityH5TestBinding;
import com.jhhscm.platform.databinding.FragmentWebBinding;
import com.jhhscm.platform.event.LoginH5Event;
import com.jhhscm.platform.event.WebTitleEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.Mechanics.action.AddGoodsToCartsAction;
import com.jhhscm.platform.fragment.Mechanics.action.FindCategoryDetailAction;
import com.jhhscm.platform.fragment.Mechanics.action.FindCollectByUserCodeAction;
import com.jhhscm.platform.fragment.Mechanics.action.SaveAction;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryDetailBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.SaveBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.NETUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.YXProgressDialog;
import com.jhhscm.platform.views.dialog.AlertDialogs;
import com.jhhscm.platform.views.dialog.ShareDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class H5PeiJian2Activity extends AbsActivity {
    private BridgeWebView webview;
    protected ActivityH5TestBinding mDataBinding;
    private UserSession userSession;

    private FindCategoryDetailBean findCategoryDetailBean;
    private FindCategoryBean findCategoryBean;
    private String goodCode;
    private String picUrl;
    private int type = 0;
    private String count = "1";
    private String url;
    private String SHARE_URL = "";
    private String TITLE = "";
    private String CONTENT = "";
    private String IMG_URL = "";

    /**
     * 配件详情-购物车需要图片地址
     */
    public static void start(Context context, String url, String title, String good_code, String pic_url, int type) {
        Intent intent = new Intent(context, H5PeiJianActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putExtra("good_code", good_code);
        intent.putExtra("pic_url", pic_url);
        context.startActivity(intent);
    }

    @Override
    protected boolean fullScreenMode() {
        return true;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_h5_test);
        setupToolbar();
        setupButtom();

        webview = (BridgeWebView) findViewById(R.id.webview);
        WebSettings settings = mDataBinding.webview.getSettings();
        settings.setJavaScriptEnabled(true); //与js交互必须设置
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        settings.setDomStorageEnabled(true);//开启DOM storage API功能
        settings.setDatabaseEnabled(true);//开启database storeage API功能
        String cacheDirPath = getApplicationContext().getFilesDir().getAbsolutePath() + "/webcache";//缓存路径
        settings.setDatabasePath(cacheDirPath);//设置数据库缓存路径
        settings.setAppCachePath(cacheDirPath);//设置AppCaches缓存路径
        settings.setAppCacheEnabled(true);//开启AppCaches功能

        url = getIntent().getStringExtra("url");
        Log.e("peijian ", "url:" + url);
        //加载动画
        final AnimationDrawable animationDrawable = (AnimationDrawable) mDataBinding.webLoadAnim.getBackground();


        if (!NETUtils.isNetworkConnected(getApplicationContext())) {
            ToastUtils.show(getApplicationContext(), "网络异常，请检查网络连接");
        } else {
            webview.loadUrl(getIntent().getStringExtra("url"));
            webview.setDefaultHandler(new DefaultHandler());
            webview.setWebChromeClient(new WebChromeClient());
            // WebView默认是不支持Android&JS通信的，要在WebView初始化的时候打开这个开关
            webview.getSettings().setJavaScriptEnabled(true);
            // Android初始化代码
            webview.registerHandler("getCount", new BridgeHandler() {
                @Override
                public void handler(String data, CallBackFunction function) {
                    Gson gson = new Gson();
                    ResultBean resultBean = gson.fromJson(data, ResultBean.class);
                    count = resultBean.getCount() + "";
                    Log.e("registerHandler", "data " + resultBean.getCount());
                }
            });

            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    mDataBinding.webLoadAnim.setVisibility(View.VISIBLE);
                    //开启帧动画
                    animationDrawable.start();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    animationDrawable.stop();
                    mDataBinding.webLoadAnim.setVisibility(View.GONE);
                }

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    mDataBinding.webview.setVisibility(View.GONE);
                    mDataBinding.rlError.setVisibility(View.VISIBLE);
                    mDataBinding.tvReload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDataBinding.rlError.setVisibility(View.GONE);
                            mDataBinding.webview.reload();
                        }
                    });
                }
            });
        }
    }

    private void setupButtom() {
        if (ConfigUtils.getCurrentUser(getApplicationContext()) != null
                && ConfigUtils.getCurrentUser(getApplicationContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getApplicationContext());
        }
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
        if (getIntent().hasExtra("good_code")) {
            goodCode = getIntent().getStringExtra("good_code");
        }
        if (getIntent().hasExtra("pic_url")) {
            picUrl = getIntent().getStringExtra("pic_url");
        }
        findCategoryDetailBean = new FindCategoryDetailBean();

        //判断是否收藏
        if (userSession != null
                && userSession.getUserCode() != null
                && userSession.getToken() != null
                && goodCode != null && goodCode.length() > 0) {
            findCollectByUserCode(userSession.getUserCode(), goodCode, userSession.getToken());
            findCategoryDetail(goodCode, true);
        }

        //收藏
        mDataBinding.tvShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSession != null
                        && userSession.getUserCode() != null
                        && userSession.getToken() != null) {
                    if (goodCode != null && goodCode.length() > 0) {
                        save(userSession.getUserCode(), goodCode, userSession.getToken());
                    }
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.tvGouwuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSession != null
                        && userSession.getUserCode() != null
                        && userSession.getToken() != null) {
                    GoodsToCartsActivity.start(H5PeiJian2Activity.this);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });
        //加购
        mDataBinding.tvJiaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSession != null
                        && userSession.getUserCode() != null
                        && userSession.getToken() != null) {
//                    if (picUrl != null && picUrl.length() > 0) {
                    findCategoryDetail(goodCode, false);
//                    }
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.tvGoumai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSession != null
                        && userSession.getUserCode() != null
                        && userSession.getToken() != null) {
                    if (picUrl != null && picUrl.length() > 0) {
                        if (findCategoryDetailBean != null && findCategoryDetailBean.getData() != null) {
                            List<GetCartGoodsByUserCodeBean.ResultBean> list = new ArrayList<>();
                            GetCartGoodsByUserCodeBean.ResultBean resultBean = new GetCartGoodsByUserCodeBean.ResultBean();
                            resultBean.setNumber(count);
                            resultBean.setGoodsCode(goodCode);
                            resultBean.setGoodsName(findCategoryDetailBean.getData().getName());
                            resultBean.setId(findCategoryDetailBean.getData().getId());
                            resultBean.setPicUrl(picUrl);
                            resultBean.setPrice(findCategoryDetailBean.getData().getCounter_price() + "");
                            list.add(resultBean);

                            GetCartGoodsByUserCodeBean g = new GetCartGoodsByUserCodeBean();
                            g.setResult(list);
                            CreateOrderActivity.start(H5PeiJian2Activity.this, g);
                        } else {
                            ToastUtil.show(H5PeiJian2Activity.this, getString(R.string.net_error));
                        }
                    }
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });
    }

    protected void setupToolbar() {
        String title = "";
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
        }
        mDataBinding.toolbar.setTitle("");
        setSupportActionBar(mDataBinding.toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        mDataBinding.toolbarTitle.setText(title);

        RelativeLayout.LayoutParams flParams = (RelativeLayout.LayoutParams) mDataBinding.toolbar.getLayoutParams();
        flParams.height += DisplayUtils.getStatusBarHeight(this);
        mDataBinding.toolbar.setLayoutParams(flParams);
        mDataBinding.toolbar.setPadding(0, DisplayUtils.getStatusBarHeight(this), 0, 0);
        mDataBinding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        mDataBinding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static class H5Fragment extends AbsFragment<FragmentWebBinding> {
        private MechanicsH5Activity.H5Fragment.UploadHandler mUploadHandler;

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
                if (!TextUtils.isEmpty(getArguments().getString("title", ""))) {
                    title = getArguments().getString("title", "");
                }
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
//                mUploadHandler = new H5PeiJian2Activity.H5Fragment.UploadHandler();
//                mUploadHandler.openFileChooser(uploadMsg, acceptType, capture);
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
            mDataBinding.webView.setVisibility(View.GONE);
            WebSettings settings = mDataBinding.webView.getSettings();
            settings.setJavaScriptEnabled(true); //与js交互必须设置
            settings.setAllowFileAccess(true);
            settings.setDomStorageEnabled(true);
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            settings.setSupportZoom(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            settings.setDefaultTextEncodingName("UTF-8");
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

            settings.setDomStorageEnabled(true);//开启DOM storage API功能
            settings.setDatabaseEnabled(true);//开启database storeage API功能
            String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + "/webcache";//缓存路径
            settings.setDatabasePath(cacheDirPath);//设置数据库缓存路径
            settings.setAppCachePath(cacheDirPath);//设置AppCaches缓存路径
            settings.setAppCacheEnabled(true);//开启AppCaches功能

            mDataBinding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            mDataBinding.webView.addJavascriptInterface(new H5PeiJian2Activity.H5Fragment.JSInterface(), "Android");

//            mDataBinding.webView.clearHistory();
//            mDataBinding.webView.clearFormData();
//            mDataBinding.webView.clearCache(true);
            mDataBinding.webView.setWebViewClient(mWebViewClient);
            mDataBinding.webView.setWebChromeClient(mWebChromeClient);
            if (!NETUtils.isNetworkConnected(getActivity())) {
                showLoadingPage(R.id.rl_loading);
                setLoadFailedMessage("网络异常，请检查网络连接");
            } else {

                //加载动画
                final AnimationDrawable animationDrawable = (AnimationDrawable) mDataBinding.webLoadAnim.getBackground();
                mDataBinding.webView.loadUrl(getArguments().getString("url"));
                mDataBinding.webView.setVisibility(View.VISIBLE);
                mDataBinding.webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        closeDialog();
                        animationDrawable.stop();
                        mDataBinding.webLoadAnim.setVisibility(View.GONE);
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        mDataBinding.webLoadAnim.setVisibility(View.VISIBLE);
                        animationDrawable.start();
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                        mDataBinding.webView.setVisibility(View.GONE);
                        mDataBinding.rlError.setVisibility(View.VISIBLE);
                        mDataBinding.tvReload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDataBinding.rlError.setVisibility(View.GONE);
                                mDataBinding.webView.reload();
                            }
                        });
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
//                showDialog();
                closeDialog();
            }

            @JavascriptInterface
            public void shareFriends() {
//                showDialog();
//                YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
//                ShareUtils.shareUrl(getContext(), SHARE_URL,
//                        TITLE, CONTENT, SHARE_MEDIA.WEIXIN_CIRCLE,
//                        ShareUtils.getShareListener(getContext()), IMG_URL);
                closeDialog();
            }

            @JavascriptInterface
            public void shareToWechat(String title, String content, String imageUrl, String shareUrl) {
//                showDialog();
//                YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
//                ShareUtils.shareUrl(getContext(), shareUrl,
//                        title, content, SHARE_MEDIA.WEIXIN,
//                        ShareUtils.getShareListener(getContext()), imageUrl);
                closeDialog();
            }

            @JavascriptInterface
            public void shareToFriends(String title, String content, String imageUrl, String shareUrl) {
//                showDialog();
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
//                    getActivity().startActivityForResult(intent, FILE_SELECTED);
                } catch (ActivityNotFoundException e) {
                    // No installed app was able to handle the intent that
                    // we sent, so fallback to the default file upload control.
                    try {
                        mCaughtActivityNotFoundException = true;
//                        getActivity().startActivityForResult(createDefaultOpenableIntent(),
//                                FILE_SELECTED);
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











    /**
     * 分享
     */
    public void showShare() {
        if (findCategoryDetailBean != null && findCategoryDetailBean.getData() != null) {
            if (findCategoryDetailBean.getData().getPic_gallery_url_list() != null
                    && findCategoryDetailBean.getData().getPic_gallery_url_list().size() > 0) {
                IMG_URL = findCategoryDetailBean.getData().getPic_gallery_url_list().get(0);
            }
            TITLE = findCategoryDetailBean.getData().getName();
            CONTENT = findCategoryDetailBean.getData().getWord_detail();
        } else {
            IMG_URL = "";
            TITLE = "挖矿来";
            CONTENT = "挖矿来";
        }
        Log.e("ShareDialog", "IMG_URL " + IMG_URL);
        new ShareDialog(H5PeiJian2Activity.this, new ShareDialog.CallbackListener() {
            @Override
            public void wechat() {
                YXProgressDialog dialog = new YXProgressDialog(H5PeiJian2Activity.this, "请稍后");
                shareUrlToWx(url, TITLE, CONTENT, IMG_URL, 0);
            }

            @Override
            public void friends() {
                YXProgressDialog dialog = new YXProgressDialog(H5PeiJian2Activity.this, "请稍后");
                shareUrlToWx(url, TITLE, CONTENT, IMG_URL, 1);
            }
        }).show();
    }

    /**
     * 分享url地址
     *
     * @param url   地址
     * @param title 标题
     * @param desc  描述
     */
    public void shareUrlToWx(final String url, final String title, final String desc, final String iconUrl, final int flag) {
        if (((MyApplication) getApplicationContext()).getApi() != null && !((MyApplication) getApplicationContext()).getApi().isWXAppInstalled()) {
            ToastUtil.show(getApplicationContext(), "您还未安装微信客户端,无法使用该功能！");
            return;
        }
        if (iconUrl != null && iconUrl.length() > 0) {
            ImageLoader.getInstance().loadImage(iconUrl, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl = url;
                    WXMediaMessage msg = new WXMediaMessage(webpage);
                    msg.title = title;
                    msg.description = desc;
                    //这里替换一张自己工程里的图片资源
                    msg.thumbData = bmpToByteArray(loadedImage, 32);
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = msg;
                    req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                    ((MyApplication) getApplicationContext()).getApi().sendReq(req);
                }
            });
        } else {
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = desc;
            //这里替换一张自己工程里的图片资源
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            msg.setThumbImage(bitmap);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            ((MyApplication) getApplicationContext()).getApi().sendReq(req);
        }

    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @param maxKb
     * @return
     */
    public static byte[] bmpToByteArray(Bitmap bitmap, int maxKb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxKb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    /**
     * 收藏
     */
    private void save(String user_code, String good_code, String token) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", user_code);
        map.put("good_code", good_code);
        map.put("token", token);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(this, map, "save");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        showDialog();
        onNewRequestCall(SaveAction.newInstance(this, netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response,
                                           BaseErrorInfo baseErrorInfo) {
                        closeDialog();
                        if (new HttpHelper().showError(getApplicationContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            new HttpHelper().showError(getApplicationContext(), response.body().getCode(), response.body().getMessage());
                            if (response.body().getCode().equals("200")) {
                                Drawable rightDrawable = getResources().getDrawable(R.mipmap.ic_shoucang2);
                                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());  // left, top, right, bottom
                                mDataBinding.tvShoucang.setCompoundDrawables(null, rightDrawable, null, null);  // left, top, right, bottom

                                ToastUtils.show(getApplicationContext(), "收藏成功");
                            } else {
                                ToastUtils.show(getApplicationContext(), response.body().getMessage());
                            }
                        }
                    }
                }));
    }

    /**
     * 判断是否收藏
     */
    private void findCollectByUserCode(String user_code, String good_code, String token) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", user_code);
        map.put("good_code", good_code);
        map.put("token", token);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(this, map, "save");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        showDialog();
        onNewRequestCall(FindCollectByUserCodeAction.newInstance(this, netBean)
                .request(new AHttpService.IResCallback<BaseEntity<SaveBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<SaveBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        closeDialog();
                        if (new HttpHelper().showError(getApplicationContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            new HttpHelper().showError(getApplicationContext(), response.body().getCode(), response.body().getMessage());
                            if (response.body().getCode().equals("200")) {
                                if (response.body().getData().isResult()) {
                                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.ic_shoucang2);
                                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());  // left, top, right, bottom
                                    mDataBinding.tvShoucang.setCompoundDrawables(null, rightDrawable, null, null);  // left, top, right, bottom
                                } else {
                                    Drawable rightDrawable = getResources().getDrawable(R.mipmap.ic_shoucang);
                                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());  // left, top, right, bottom
                                    mDataBinding.tvShoucang.setCompoundDrawables(null, rightDrawable, null, null);  // left, top, right, bottom
                                }
                            } else if (response.body().getCode().equals("1003")) {
                                startNewActivity(LoginActivity.class);
                            } else {
                                ToastUtils.show(getApplicationContext(), response.body().getMessage());
                            }
                        }
                    }
                }));
    }

    /**
     * 添加购物车
     */
    private void addGoodsToCarts(String userCode, String picUrl, FindCategoryDetailBean
            findCategoryDetail, String token) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userCode", userCode);
        map.put("goodsCode", goodCode);
        map.put("goodsName", findCategoryDetail.getData().getName());
        map.put("number", count);
        map.put("price", findCategoryDetail.getData().getCounter_price() + "");
        map.put("picUrl", picUrl);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);

        String sign = Sign.getSignKey(H5PeiJian2Activity.this, map, "addGoodsToCarts");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(AddGoodsToCartsAction.newInstance(H5PeiJian2Activity.this, netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (new HttpHelper().showError(getApplicationContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            new HttpHelper().showError(getApplicationContext(), response.body().getCode(), response.body().getMessage());
                            if (response.body().getCode().equals("200")) {
                                ToastUtils.show(getApplicationContext(), "购物车添加成功");
                            } else {
                                ToastUtils.show(getApplicationContext(), response.body().getMessage());
                            }
                        }
                    }
                }));
    }


    /**
     * 获取配件详情
     */
    private void findCategoryDetail(String goodsCode, final boolean finish) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("good_code", goodsCode);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(this, map, "findCategoryDetail");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        showDialog();
        onNewRequestCall(FindCategoryDetailAction.newInstance(this, netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindCategoryDetailBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindCategoryDetailBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        closeDialog();
                        if (new HttpHelper().showError(getApplicationContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            new HttpHelper().showError(getApplicationContext(), response.body().getCode(), response.body().getMessage());
                            if (response.body().getCode().equals("200")) {
                                findCategoryDetailBean = response.body().getData();
                                if (findCategoryDetailBean.getData() != null) {
                                    if (findCategoryDetailBean.getData().getPic_gallery_url_list() != null
                                            && findCategoryDetailBean.getData().getPic_gallery_url_list().size() > 0) {
                                        picUrl = findCategoryDetailBean.getData().getPic_gallery_url_list().get(0);
                                        if (!finish) {
                                            addGoodsToCarts(userSession.getUserCode(), picUrl, findCategoryDetailBean, userSession.getToken());
                                        }
                                    }
                                } else {
                                    ToastUtils.show(getApplicationContext(), "获取不到该商品信息，请联系管理员");
                                }
                            } else {
                                ToastUtils.show(getApplicationContext(), response.body().getMessage());
                            }
                        }
                    }
                }));
    }

    /**
     * 判断是否存在NavigationBar
     *
     * @param context：上下文环境
     * @return：返回是否存在(true/false)
     */
    public boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                //不存在虚拟按键
                hasNavigationBar = false;
//                ToastUtil.show(context, "不存在虚拟按键");
            } else if ("0".equals(navBarOverride)) {
                //存在虚拟按键
                hasNavigationBar = true;
                //手动设置控件的margin
                //linebutton是一个linearlayout,里面包含了两个Button
                Log.e("getNavigationBarHeight", "getNavigationBarHeight:" + getNavigationBarHeight(this));
                RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) mDataBinding.rlBottom.getLayoutParams();
                //setMargins：顺序是左、上、右、下
                layout.setMargins(15, 0, 15, getNavigationBarHeight(this) + 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }


    /**
     * 测量底部导航栏的高度
     *
     * @param mActivity:上下文环境
     * @return：返回测量出的底部导航栏高度
     */
    private int getNavigationBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
