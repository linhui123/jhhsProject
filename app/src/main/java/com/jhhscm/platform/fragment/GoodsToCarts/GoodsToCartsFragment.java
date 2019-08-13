package com.jhhscm.platform.fragment.GoodsToCarts;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.CreateOrderActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentGoodsToCartsBinding;

import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.action.CreateOrderAction;
import com.jhhscm.platform.fragment.GoodsToCarts.action.DelGoodsToCartsAction;
import com.jhhscm.platform.fragment.GoodsToCarts.action.GetCartGoodsByUserCodeAction;
import com.jhhscm.platform.fragment.GoodsToCarts.action.UpdateGoodsToCartsAction;
import com.jhhscm.platform.fragment.GoodsToCarts.adapter.RecOtherTypeAdapter;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.slideswaphelper.PlusItemSlideCallback;
import com.jhhscm.platform.views.slideswaphelper.WItemTouchHelperPlus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


/**
 * 购物车
 */
public class GoodsToCartsFragment extends AbsFragment<FragmentGoodsToCartsBinding> implements RecOtherTypeAdapter.DeletedItemListener, RecOtherTypeAdapter.CountChangeListener, RecOtherTypeAdapter.SelectedListener {
    private RecOtherTypeAdapter recAdapter;
    List<GetCartGoodsByUserCodeBean.ResultBean> list;
    private boolean total;
    private UserSession userSession;

    public static GoodsToCartsFragment instance() {
        GoodsToCartsFragment view = new GoodsToCartsFragment();
        return view;
    }

    @Override
    protected FragmentGoodsToCartsBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentGoodsToCartsBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
//        SmartRefreshLayout.LayoutParams llParams = (SmartRefreshLayout.LayoutParams) mDataBinding.rvGouwuche.getLayoutParams();
//        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
//        mDataBinding.rvGouwuche.setLayoutParams(llParams);

        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        mDataBinding.refresh.setEnableLastTime(false);
        mDataBinding.load.setEnableLastTime(false);
        mDataBinding.refreshlayout.setEnableRefresh(true);
        mDataBinding.refreshlayout.setEnableLoadMore(false);
        mDataBinding.refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getCartGoodsByUserCode(userSession.getUserCode(), userSession.getToken(), true);
            }
        });

        mDataBinding.refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getCartGoodsByUserCode(userSession.getUserCode(), userSession.getToken(), false);

            }
        });
        getCartGoodsByUserCode(userSession.getUserCode(), userSession.getToken(), true);
        initView();
        initBottom();
    }

    private void initBottom() {
        mDataBinding.imQuanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total) {
                    if (getCartGoodsByUserCodeBean != null) {
                        for (GetCartGoodsByUserCodeBean.ResultBean r : getCartGoodsByUserCodeBean) {
                            r.setSelect(false);
                        }
                        initData(getCartGoodsByUserCodeBean, true);
                        select(getCartGoodsByUserCodeBean);
                    }
                    total = false;
                    mDataBinding.imQuanxuan.setImageResource(R.mipmap.ic_shoping_s);
                } else {
                    if (getCartGoodsByUserCodeBean != null) {
                        for (GetCartGoodsByUserCodeBean.ResultBean r : getCartGoodsByUserCodeBean) {
                            r.setSelect(true);
                        }
                        initData(getCartGoodsByUserCodeBean, true);
                        select(getCartGoodsByUserCodeBean);
                    }
                    total = true;
                    mDataBinding.imQuanxuan.setImageResource(R.mipmap.ic_shoping_s1);

                }
            }
        });

        mDataBinding.tvJiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GetCartGoodsByUserCodeBean.ResultBean> list = new ArrayList<>();
                for (GetCartGoodsByUserCodeBean.ResultBean r : getCartGoodsByUserCodeBean) {
                    if (r.isSelect()) {
                        list.add(r);
                    }
                }
                if (list != null && list.size() > 0) {
                    GetCartGoodsByUserCodeBean g = new GetCartGoodsByUserCodeBean();
                    g.setResult(list);
                    CreateOrderActivity.start(getContext(), g);
                } else {
                    ToastUtil.show(getContext(), "请先选择商品");
                }
            }
        });
    }

    List<GetCartGoodsByUserCodeBean.ResultBean> getCartGoodsByUserCodeBean;

    private void initData(List<GetCartGoodsByUserCodeBean.ResultBean> resultBeans, boolean refresh) {
        recAdapter.setList(resultBeans, refresh);
        if (refresh) {
            getCartGoodsByUserCodeBean = resultBeans;
            mDataBinding.refreshlayout.finishRefresh(1000);
        } else {
            getCartGoodsByUserCodeBean.addAll(resultBeans);
            mDataBinding.refreshlayout.finishLoadMore(1000);
        }
    }

    private void initView() {
        mDataBinding.rvGouwuche.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.rvGouwuche.setLayoutManager(new LinearLayoutManager(getActivity()));
        recAdapter = new RecOtherTypeAdapter(getContext());
        recAdapter.setDeletedItemListener(this);
        recAdapter.setChangeListener(this);
        recAdapter.setSelectedListener(this);
        mDataBinding.rvGouwuche.setAdapter(recAdapter);

        PlusItemSlideCallback callback = new PlusItemSlideCallback();
        WItemTouchHelperPlus extension = new WItemTouchHelperPlus(callback);
        extension.attachToRecyclerView(mDataBinding.rvGouwuche);
    }


    /**
     * 更新地区选择
     */
    public void onEvent(GetRegionEvent event) {
    }

    /**
     * 获取用户购物车列表
     */
    private void getCartGoodsByUserCode(String userCode, String token, final boolean refrash) {
        Log.e("getCartGoodsByUserCode", " token :" + token);

        Map<String, String> map = new TreeMap<String, String>();
        map.put("userCode", userCode);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getCartGoodsByUserCode");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetCartGoodsByUserCodeAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GetCartGoodsByUserCodeBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GetCartGoodsByUserCodeBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    initData(response.body().getData().getResult(), refrash);
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 删除用户购物车
     */
    private void delGoodsToCarts(final String userCode, String goodsCode, final String token) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userCode", userCode);
        map.put("goodsCode", goodsCode);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "delGoodsToCarts");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(DelGoodsToCartsAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    getCartGoodsByUserCode(userCode, token, true);
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 修改用户购物车
     */
    private void updateGoodsToCarts(final String userCode, GetCartGoodsByUserCodeBean.ResultBean resultBean, final String token) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userCode", userCode);
        map.put("goodsCode", resultBean.getGoodsCode());
        map.put("number", resultBean.getNumber());
        map.put("id", resultBean.getId());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "updateGoodsToCarts");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(UpdateGoodsToCartsAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    Log.e("updateGoodsToCarts", "修改信息成功");
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    @Override
    public void deleted(GetCartGoodsByUserCodeBean.ResultBean resultBean) {
        delGoodsToCarts(userSession.getUserCode(), resultBean.getGoodsCode(), userSession.getToken());
    }

    @Override
    public void select(List<GetCartGoodsByUserCodeBean.ResultBean> resultBeans) {
        List<GetCartGoodsByUserCodeBean.ResultBean> getList = resultBeans;
        double count = 0;
        for (GetCartGoodsByUserCodeBean.ResultBean bean : getList) {
            if (bean.isSelect()) {
                if (bean.getNumber() != null) {
                    count = count + Double.parseDouble(bean.getPrice()) * Double.parseDouble(bean.getNumber());
                }
            }
        }
        total = false;
        mDataBinding.imQuanxuan.setImageResource(R.mipmap.ic_shoping_s);
        mDataBinding.tvSum.setText("￥" + count);
    }

    @Override
    public void changeCount(List<GetCartGoodsByUserCodeBean.ResultBean> resultBeans, int position) {
        updateGoodsToCarts(userSession.getUserCode(), resultBeans.get(position), userSession.getToken());
        select(resultBeans);
    }
}
