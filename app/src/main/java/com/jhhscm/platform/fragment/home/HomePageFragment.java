package com.jhhscm.platform.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentHomePageBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.FindBrandHomePageAction;
import com.jhhscm.platform.fragment.home.action.FindCategoryHomePageAction;
import com.jhhscm.platform.fragment.home.action.FindLabourReleaseHomePageAction;
import com.jhhscm.platform.fragment.home.action.GetAdAction;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindLabourReleaseHomePageBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.ShareDialog;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class HomePageFragment extends AbsFragment<FragmentHomePageBinding> {
    private HomePageItem homePageItem;
    private HomePageAdapter mAdapter;

    public static HomePageFragment instance() {
        HomePageFragment view = new HomePageFragment();
        return view;
    }

    @Override
    protected FragmentHomePageBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentHomePageBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        EventBusUtil.registerEvent(this);

        mDataBinding.wrvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HomePageAdapter(getContext());
        mDataBinding.wrvRecycler.setAdapter(mAdapter);
        mDataBinding.wrvRecycler.loadComplete(true,false);
        mDataBinding.wrvRecycler.hideLoad();
        mDataBinding.wrvRecycler.autoRefresh();
        mDataBinding.wrvRecycler.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                getAD(2);
                getAD(3);
                getAD(4);
                findBrandHomePage();;
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                getAD(2);
                getAD(3);
                getAD(4);
                findBrandHomePage();
            }
        });

        initTel();
    }

    private void initTel() {
        mDataBinding.wrvRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    /**new State 一共有三种状态
                     * SCROLL_STATE_IDLE：目前RecyclerView不是滚动，也就是静止
                     * SCROLL_STATE_DRAGGING：RecyclerView目前被外部输入如用户触摸输入。
                     * SCROLL_STATE_SETTLING：RecyclerView目前动画虽然不是在最后一个位置外部控制。**/

                    mDataBinding.tel.setVisibility(View.VISIBLE);
                    imgTranslateAnimation(100,0);
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    imgTranslateAnimation(0,100);
                    mDataBinding.tel.setVisibility(View.GONE);
                }
            }
        });

        mDataBinding.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TelPhoneDialog(getContext(), new TelPhoneDialog.CallbackListener() {

                    @Override
                    public void clickYes(String phone) {
                        saveMsg(phone);
                    }
                }).show();
            }
        });
    }

    //动画的左右进出平移动画
    private void imgTranslateAnimation(float fromXDelta, float toXDelta) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, 0, 0);
        translateAnimation.setFillAfter(true);//这句话会造成imageView.setVisibility(GONE)的时候，会停留在动画最后的地方，导致还没有隐藏的假象。
        translateAnimation.setDuration(800);
        mDataBinding.tel.setAnimation(translateAnimation);
        mDataBinding.tel.startAnimation(translateAnimation);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 获取轮播、广告
     */
    private void getAD(final int position) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("position", position + "");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getAD");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetAdAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<AdBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<AdBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if (position == 2) {
                                        HomePageItem.adBean1 = response.body().getData();
                                    } else if (position == 3) {
                                        HomePageItem.adBean3 = response.body().getData();
                                    } else {
                                        HomePageItem.adBean2 = response.body().getData();
                                    }
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 获取品牌
     */
    private void findBrandHomePage() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("app_version", "v1.0.0");
        map.put("brand_type", "1");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findBrandHomePage");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindBrandHomePageAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindBrandHomePageBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindBrandHomePageBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    HomePageItem.findBrandHomePageBean = response.body().getData();
                                    findCategoryHomePage();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                    findCategoryHomePage();
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 获取配件
     */
    private void findCategoryHomePage() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("app_version", "v1.0.0");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findCategoryHomePage");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindCategoryHomePageAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindCategoryHomePageBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindCategoryHomePageBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    HomePageItem.findCategoryHomePageBean = response.body().getData();
                                    findLabourReleaseHomePage();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                    findLabourReleaseHomePage();
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 获取劳务资讯
     */
    private void findLabourReleaseHomePage() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("app_version", "v1.0.0");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findLabourReleaseHomePage");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindLabourReleaseHomePageAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindLabourReleaseHomePageBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindLabourReleaseHomePageBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    HomePageItem.findLabourReleaseHomePageBean = response.body().getData();
                                    setView();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                    setView();
                                }
                            }
                        }
                    }
                }));
    }

    private void setView() {
        mAdapter.setDetail(homePageItem);
        mDataBinding.wrvRecycler.loadComplete(true,false);
    }

    /**
     * 咨询
     */
    public void onEvent(ConsultationEvent event) {
        if (event.phone != null) {
            saveMsg(event.phone);
        }
    }

    /**
     * 信息咨询
     */
    private void saveMsg(final String phone) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("mobile", phone);
        map.put("type", "1");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "saveMsg");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(SaveMsgAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    new SimpleDialog(getContext(), phone, new SimpleDialog.CallbackListener() {
                                        @Override
                                        public void clickYes() {

                                        }
                                    }).show();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 分享
     */
    public void showShare() {
        new ShareDialog(getContext(), new ShareDialog.CallbackListener() {
            @Override
            public void wechat() {
//                YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
//                ShareUtils.shareUrl(getContext(), SHARE_URL,
//                        TITLE, CONTENT, SHARE_MEDIA.WEIXIN,
//                        ShareUtils.getShareListener(getContext()), IMG_URL);
//                HttpUtils.shareCommonContent(getContext(), hospId, "13");
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

}
