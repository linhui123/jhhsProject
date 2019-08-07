package com.jhhscm.platform.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsActivity;
import com.jhhscm.platform.bean.LogingResultBean;
import com.jhhscm.platform.databinding.ActivityH5TestBinding;
import com.jhhscm.platform.databinding.ActivityMechanicsH5Binding;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.Mechanics.action.AddGoodsToCartsAction;
import com.jhhscm.platform.fragment.Mechanics.action.FindCategoryDetailAction;
import com.jhhscm.platform.fragment.Mechanics.action.FindCollectByUserCodeAction;
import com.jhhscm.platform.fragment.Mechanics.action.SaveAction;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryDetailBean;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
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
import com.jhhscm.platform.tool.ShareUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.YXProgressDialog;
import com.jhhscm.platform.views.dialog.ShareDialog;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class H5PeiJianActivity extends AbsActivity {
    private BridgeWebView webview;
    protected ActivityH5TestBinding mDataBinding;
    private UserSession userSession;

    private FindCategoryDetailBean findCategoryDetailBean;
    private FindCategoryBean findCategoryBean;
    private String goodCode;
    private String picUrl;
    private int type = 0;
    private String count = "1";

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
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_h5_test);
        setupToolbar();
        setupButtom();
        checkDeviceHasNavigationBar(H5PeiJianActivity.this);
        webview = (BridgeWebView) findViewById(R.id.webview);

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
                    count = resultBean.getCount()+"";
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
                    //判断是否在运行
                    if(!animationDrawable.isRunning()){
                        //开启帧动画
                        animationDrawable.start();
                    }
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    animationDrawable.stop();
                    mDataBinding.webLoadAnim.setVisibility(View.GONE);
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
        findCategoryDetail(goodCode, false);

        //判断是否收藏
        if (userSession != null
                && userSession.getUserCode() != null
                && userSession.getToken() != null
                && goodCode != null && goodCode.length() > 0) {
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
                GoodsToCartsActivity.start(H5PeiJianActivity.this);
            }
        });
        //加购
        mDataBinding.tvJiaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSession != null
                        && userSession.getUserCode() != null
                        && userSession.getToken() != null) {
                    if (picUrl != null && picUrl.length() > 0) {
                        if (findCategoryDetailBean != null) {
                            addGoodsToCarts(userSession.getUserCode(), picUrl, findCategoryDetailBean, userSession.getToken());
                        } else {
                            findCategoryDetail(goodCode, true);
                        }
                    }
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
                        if (findCategoryDetailBean != null) {
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
                            CreateOrderActivity.start(H5PeiJianActivity.this, g);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
    }

    /**
     * 分享
     */
    public void showShare() {
        new ShareDialog(H5PeiJianActivity.this, new ShareDialog.CallbackListener() {
            @Override
            public void wechat() {
                YXProgressDialog dialog = new YXProgressDialog(H5PeiJianActivity.this, "请稍后");
                ShareUtils.shareUrl(H5PeiJianActivity.this, SHARE_URL,
                        TITLE, CONTENT, SHARE_MEDIA.WEIXIN,
                        ShareUtils.getShareListener(H5PeiJianActivity.this), IMG_URL);
//                HttpUtils.shareCommonContent(H5Activity.this, hospId, "13");
            }

            @Override
            public void friends() {
//                YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
//                ShareUtils.shareUrl(getContext(), SHARE_URL,
//                        TITLE, CONTENT, SHARE_MEDIA.WEIXIN_CIRCLE,
//                        ShareUtils.getShareListener(getContext()), IMG_URL);
//                HttpUtils.shareCommonContent(getContext(), hospId, "13");
            }
        }).show();
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
        map.put("goodsCode", findCategoryDetail.getData().getId());
        map.put("goodsName", findCategoryDetail.getData().getName());
        map.put("number", count);
        map.put("price", findCategoryDetail.getData().getCounter_price() + "");
        map.put("picUrl", picUrl);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(this, map, "addGoodsToCarts");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        showDialog();
        onNewRequestCall(AddGoodsToCartsAction.newInstance(this, netBean)
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
                                if (finish) {
                                    addGoodsToCarts(userSession.getUserCode(), picUrl, findCategoryDetailBean, userSession.getToken());
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
                ToastUtil.show(context,"不存在虚拟按键");
            } else if ("0".equals(navBarOverride)) {
                //存在虚拟按键
                hasNavigationBar = true;
                //手动设置控件的margin
                //linebutton是一个linearlayout,里面包含了两个Button
                RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) mDataBinding.rlBottom.getLayoutParams();
                //setMargins：顺序是左、上、右、下
                layout.setMargins(15,0,15,getNavigationBarHeight(this)+10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }



    /**
     * 测量底部导航栏的高度
     * @param mActivity:上下文环境
     * @return：返回测量出的底部导航栏高度
     */
    private int getNavigationBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
