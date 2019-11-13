package com.jhhscm.platform.fragment.GoodsToCarts;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.jhhscm.platform.shoppingcast.adapter.CartExpandAdapter;
import com.jhhscm.platform.shoppingcast.callback.OnClickAddCloseListenter;
import com.jhhscm.platform.shoppingcast.callback.OnClickDeleteListenter;
import com.jhhscm.platform.shoppingcast.callback.OnClickListenterModel;
import com.jhhscm.platform.shoppingcast.callback.OnViewItemClickListener;
import com.jhhscm.platform.shoppingcast.entity.CartInfo;
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
    CartInfo cartInfo;
    CartExpandAdapter cartExpandAdapter;
    double price;
    int num;

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

        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

//        mDataBinding.refresh.setEnableLastTime(false);
//        mDataBinding.load.setEnableLastTime(false);
//        mDataBinding.refreshlayout.setEnableRefresh(true);
//        mDataBinding.refreshlayout.setEnableLoadMore(false);
//        mDataBinding.refreshlayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                getCartGoodsByUserCode(userSession.getUserCode(), userSession.getToken(), true);
//            }
//        });
//
//        mDataBinding.refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                getCartGoodsByUserCode(userSession.getUserCode(), userSession.getToken(), false);
//
//            }
//        });
        getCartGoodsByUserCode(ConfigUtils.getCurrentUser(getContext()).getUserCode(), ConfigUtils.getCurrentUser(getContext()).getToken(), true);
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
//                    if (r.isSelect()) {
                        list.add(r);
//                    }
                }
                if (list != null && list.size() > 0) {
                    GetCartGoodsByUserCodeBean g = new GetCartGoodsByUserCodeBean();
                    g.setResult(list);
                    CreateOrderActivity.start(getContext(), g);
                    getActivity().finish();
                } else {
                    ToastUtil.show(getContext(), "请先选择商品");
                }
            }
        });
    }

//    private void initView() {
//        mDataBinding.rvGouwuche.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
//        mDataBinding.rvGouwuche.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recAdapter = new RecOtherTypeAdapter(getContext());
//        recAdapter.setDeletedItemListener(this);
//        recAdapter.setChangeListener(this);
//        recAdapter.setSelectedListener(this);
//        mDataBinding.rvGouwuche.setAdapter(recAdapter);
//
//        PlusItemSlideCallback callback = new PlusItemSlideCallback();
//        WItemTouchHelperPlus extension = new WItemTouchHelperPlus(callback);
//        extension.attachToRecyclerView(mDataBinding.rvGouwuche);
//    }

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
                                    getCartGoodsByUserCodeBean = response.body().getData().getResult();
                                    initData(response.body().getData().getResult(), refrash);
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    List<GetCartGoodsByUserCodeBean.ResultBean> getCartGoodsByUserCodeBean;

    private void initData(List<GetCartGoodsByUserCodeBean.ResultBean> resultBeans, boolean refresh) {
//        recAdapter.setList(resultBeans, refresh);
//        if (refresh) {
//            getCartGoodsByUserCodeBean = resultBeans;
//            mDataBinding.refreshlayout.finishRefresh(1000);
//        } else {
//            getCartGoodsByUserCodeBean.addAll(resultBeans);
//            mDataBinding.refreshlayout.finishLoadMore(1000);
//        }

//        if (recAdapter.getItemCount() == 0) {
//            mDataBinding.rlCaseBaseNull.setVisibility(View.VISIBLE);
//        } else {
//            mDataBinding.rlCaseBaseNull.setVisibility(View.GONE);
//        }

        initView();
    }


    private void initView() {
        mDataBinding.cartExpandablelistview.setGroupIndicator(null);
        showData();
    }

    private void showData() {
        cartInfo = JSON.parseObject("{\"errcode\":0,\"errmsg\":\"success\",\"data\":[{\"shop_id\":\"1636\",\"shop_name\":\"水城县阳光佳美食材经营部\",\"items\":[{\"itemid\":\"100189\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/06\\/20170609105502145359.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/06\\/20170609105502145359.jpg\",\"price\":\"3.00\",\"title\":\"油菜一斤\"}]},{\"shop_id\":\"1778\",\"shop_name\":\"商品测试专卖店\",\"items\":[{\"itemid\":\"103677\",\"quantity\":\"2\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/09\\/20170926150650687701.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/09\\/20170926150650687701.jpg\",\"price\":\"0.10\",\"title\":\"测试商品1\"},{\"itemid\":\"103629\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/10\\/20171016134627837135.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/10\\/20171016134627837135.jpg\",\"price\":\"2.50\",\"title\":\"测试商品2\"},{\"itemid\":\"104015\",\"quantity\":\"1\",\"thumb\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/thumb\\/2017\\/10\\/20171016135318646327.jpg\",\"image\":\"https:\\/\\/cg.liaidi.com\\/data\\/attachment\\/image\\/photo\\/2017\\/10\\/20171016135318646327.jpg\",\"price\":\"1.00\",\"title\":\"测试商品3\"}]}]}"
                , CartInfo.class);
        if (cartInfo != null && cartInfo.getData().size() > 0) {
            cartExpandAdapter = null;
            showExpandData();
        } else {
            try {
                cartExpandAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                return;
            }
        }
    }

    private void showExpandData() {
        cartExpandAdapter = new CartExpandAdapter(getContext(), mDataBinding.cartExpandablelistview, cartInfo.getData());
        mDataBinding.cartExpandablelistview.setAdapter(cartExpandAdapter);
        int intgroupCount = mDataBinding.cartExpandablelistview.getCount();
        for (int i = 0; i < intgroupCount; i++) {
            mDataBinding.cartExpandablelistview.expandGroup(i);
        }
        /**
         * 全选
         */
        cartExpandAdapter.setOnItemClickListener(new OnViewItemClickListener() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                cartInfo.getData().get(position).setIscheck(isFlang);
                int length = cartInfo.getData().get(position).getItems().size();
                for (int i = 0; i < length; i++) {
                    cartInfo.getData().get(position).getItems().get(i).setIscheck(isFlang);
                }
                cartExpandAdapter.notifyDataSetChanged();
                showCommodityCalculation();
            }
        });

        /**
         * 单选
         */
        cartExpandAdapter.setOnClickListenterModel(new OnClickListenterModel() {
            @Override
            public void onItemClick(boolean isFlang, View view, int onePosition, int position) {
                cartInfo.getData().get(onePosition).getItems().get(position).setIscheck(isFlang);
                int length = cartInfo.getData().get(onePosition).getItems().size();
                for (int i = 0; i < length; i++) {
                    if (!cartInfo.getData().get(onePosition).getItems().get(i).ischeck()) {
                        if (!isFlang) {
                            cartInfo.getData().get(onePosition).setIscheck(isFlang);
                        }
                        cartExpandAdapter.notifyDataSetChanged();
                        showCommodityCalculation();
                        return;
                    } else {
                        if (i == (length - 1)) {
                            cartInfo.getData().get(onePosition).setIscheck(isFlang);
                            cartExpandAdapter.notifyDataSetChanged();
                        }
                    }
                }
                showCommodityCalculation();
            }
        });
        cartExpandAdapter.setOnClickDeleteListenter(new OnClickDeleteListenter() {
            @Override
            public void onItemClick(View view, int onePosition, int position) {

                //具体代码没写， 只要删除商品，刷新数据即可
//                Toast.makeText(MainActivity.this, "删除操作", Toast.LENGTH_LONG).show();
            }
        });

        /***
         * 数量增加和减少
         */
        cartExpandAdapter.setOnClickAddCloseListenter(new OnClickAddCloseListenter() {
            @Override
            public void onItemClick(View view, int index, int onePosition, int position, int num) {
                if (index == 1) {
                    if (num > 1) {
                        cartInfo.getData().get(onePosition).getItems().get(position).setNum((num - 1));
                        cartExpandAdapter.notifyDataSetChanged();
                    }
                } else {
                    cartInfo.getData().get(onePosition).getItems().get(position).setNum((num + 1));
                    cartExpandAdapter.notifyDataSetChanged();
                }
                showCommodityCalculation();
            }
        });
        showCommodityCalculation();
    }

    private void showCommodityCalculation() {
        price = 0;
        num = 0;
        for (int i = 0; i < cartInfo.getData().size(); i++) {
            for (int j = 0; j < cartInfo.getData().get(i).getItems().size(); j++) {
                if (cartInfo.getData().get(i).getItems().get(j).ischeck()) {
                    price += Double.valueOf((cartInfo.getData().get(i).getItems().get(j).getNum() * Double.valueOf(cartInfo.getData().get(i).getItems().get(j).getPrice())));
                    num++;
                }
            }
        }
        if (price == 0.0) {
//            mDataBinding.cartNum.setText("共0件商品");
            mDataBinding.tvSum.setText("¥ 0.0");
            return;
        }
        try {
            String money = String.valueOf(price);
//            cartNum.setText("共" + num + "件商品");
            if (money.substring(money.indexOf("."), money.length()).length() > 2) {
                mDataBinding.tvSum.setText("¥ " + money.substring(0, (money.indexOf(".") + 3)));
                return;
            }
            mDataBinding.tvSum.setText("¥ " + money.substring(0, (money.indexOf(".") + 2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
