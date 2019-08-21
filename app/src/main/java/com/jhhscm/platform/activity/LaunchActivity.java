package com.jhhscm.platform.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsActivity;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.fragment.home.action.GetAdAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class LaunchActivity extends AbsActivity {
    private boolean isNotHasAd;
    private String imUrl;

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
        setContentView(R.layout.activity_launch);
        getAD(1);
        startCountDownTimer(2000);
    }

    private void startCountDownTimer(int time) {
        new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //http://pic31.nipic.com/20130801/11604791_100539834000_2.jpg
                if (isNotHasAd) {
                    Bundle bundle = new Bundle();
                    if (imUrl != null) {
                        bundle.putString("imUrl", imUrl);
                    }
                    startNewActivity(ADActivity.class, bundle);
                } else {
                    startNewActivity(MainActivity.class);
                }
                finish();
            }
        }.start();
    }

    /**
     * 获取轮播、广告
     */
    private void getAD(final int position) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("position", position + "");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getApplicationContext(), map, "getAD");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetAdAction.newInstance(getApplicationContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<AdBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<AdBean>> response, BaseErrorInfo baseErrorInfo) {
                        closeDialog();
                        if (new HttpHelper().showError(getApplicationContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            if (response.body().getCode().equals("200")) {
                                AdBean adBean = response.body().getData();
                                if (adBean != null && adBean.getResult().size() > 0) {
                                    imUrl = adBean.getResult().get(0).getUrl();
                                    isNotHasAd = true;
                                } else {
                                    isNotHasAd = false;
                                }
                            } else {
                                ToastUtils.show(getApplicationContext(), response.body().getMessage());
                            }
                        } else {
                            startNewActivity(MainActivity.class);
                        }
                    }
                }));
    }
}
