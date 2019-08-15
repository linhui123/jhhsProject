package com.jhhscm.platform.activity.h5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.CreateOrderActivity;
import com.jhhscm.platform.activity.GoodsToCartsActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.base.AbsActivity;
import com.jhhscm.platform.databinding.ActivityH5TestBinding;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.Mechanics.action.AddGoodsToCartsAction;
import com.jhhscm.platform.fragment.Mechanics.action.FindCollectByUserCodeAction;
import com.jhhscm.platform.fragment.Mechanics.action.SaveAction;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryDetailBean;
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
import com.jhhscm.platform.tool.NETUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.YXProgressDialog;
import com.jhhscm.platform.views.dialog.ShareDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class H5PeiJianActivity extends AbsActivity {
    private BridgeWebView webview;
    protected ActivityH5TestBinding mDataBinding;
    private UserSession userSession;
    private FindCategoryDetailBean findCategoryDetailBean;

    private String goodCode;
    private String picUrl;
    private String good_name;
    private String price;

    private int type = 0;
    private String count = "1";
    private String url;
    private String SHARE_URL = "";
    private String TITLE = "";
    private String CONTENT = "";
    private String IMG_URL = "";
    AnimationDrawable animationDrawable;

    /**
     * 配件详情-购物车需要图片地址
     */
    public static void start(Context context, String url, String title, String good_name, String good_code, String pic_url, String price, int type) {
        Intent intent = new Intent(context, H5PeiJianActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        intent.putExtra("good_code", good_code);
        intent.putExtra("pic_url", pic_url);
        intent.putExtra("good_name", good_name);
        intent.putExtra("price", price);
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
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
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
        animationDrawable = (AnimationDrawable) mDataBinding.webLoadAnim.getBackground();

        if (!NETUtils.isNetworkConnected(getApplicationContext())) {
            ToastUtils.show(getApplicationContext(), "网络异常，请检查网络连接");
        } else {
            webview.loadUrl(getIntent().getStringExtra("url"));
            webview.setWebViewClient(new MyWebViewClient(webview));
            webview.setDefaultHandler(new DefaultHandler());
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
        }
    }

    public class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
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
        if (getIntent().hasExtra("good_name")) {
            good_name = getIntent().getStringExtra("good_name");
        }
        if (getIntent().hasExtra("price")) {
            price = getIntent().getStringExtra("price");
        }

        findCategoryDetailBean = new FindCategoryDetailBean();

        //判断是否收藏
        if (userSession != null
                && userSession.getUserCode() != null
                && userSession.getToken() != null
                && goodCode != null && goodCode.length() > 0) {
//            findCategoryDetail(goodCode, true);
            findCollectByUserCode(userSession.getUserCode(), goodCode, userSession.getToken());
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
                    GoodsToCartsActivity.start(H5PeiJianActivity.this);
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
                    addGoodsToCarts(userSession.getUserCode(), userSession.getToken());
//                    } else {
//                        findCategoryDetail(goodCode, false);
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
//                        if (findCategoryDetailBean != null && findCategoryDetailBean.getData() != null) {
                        List<GetCartGoodsByUserCodeBean.ResultBean> list = new ArrayList<>();
                        GetCartGoodsByUserCodeBean.ResultBean resultBean = new GetCartGoodsByUserCodeBean.ResultBean();
                        Log.e("CreateOrderViewHolder", "count " + count);
                        resultBean.setNumber(count);
                        resultBean.setGoodsCode(goodCode);
                        resultBean.setGoodsName(good_name);
                        resultBean.setPicUrl(picUrl);
                        resultBean.setPrice(price);
                        list.add(resultBean);

                        GetCartGoodsByUserCodeBean g = new GetCartGoodsByUserCodeBean();
                        g.setResult(list);
                        CreateOrderActivity.start(H5PeiJianActivity.this, g);
//                        } else {
//                            ToastUtil.show(H5PeiJianActivity.this, getString(R.string.net_error));
//                        }
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

    /**
     * 分享
     */
    public void showShare() {
        if (good_name != null) {
            TITLE = good_name;
        } else {
            TITLE = "挖矿来";
        }
        if (picUrl != null) {
            IMG_URL = picUrl;
        } else {
            IMG_URL = "";
        }
        CONTENT = "挖矿来";

        new ShareDialog(H5PeiJianActivity.this, new ShareDialog.CallbackListener() {
            @Override
            public void wechat() {
                YXProgressDialog dialog = new YXProgressDialog(H5PeiJianActivity.this, "请稍后");
                shareUrlToWx(url, TITLE, CONTENT, IMG_URL, 0);
            }

            @Override
            public void friends() {
                YXProgressDialog dialog = new YXProgressDialog(H5PeiJianActivity.this, "请稍后");
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
    private void addGoodsToCarts(String userCode, String token) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userCode", userCode);
        map.put("goodsCode", goodCode);
        map.put("goodsName", good_name);
        map.put("number", count);
        map.put("price", price);
        map.put("picUrl", picUrl);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);

        String sign = Sign.getSignKey(H5PeiJianActivity.this, map, "addGoodsToCarts");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(AddGoodsToCartsAction.newInstance(H5PeiJianActivity.this, netBean)
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
